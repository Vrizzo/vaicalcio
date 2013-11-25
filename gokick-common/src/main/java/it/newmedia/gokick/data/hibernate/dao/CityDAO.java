package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.City;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 *
 * DAO per la gestione delle città. Contiene tutti i metodi che lavorano sulla tabella CITIES.
 */
public class CityDAO extends AGenericDAO<City, Integer>
{

  /**
   *
   * @param session
   */
  public CityDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param idProvince id provincia d'interesse
   * @param onlyWithUsers indica se solo con iscritti Enabled
   * @param onlyWithSportCenters indica se solo con centri sportivi abilitati e approvati
   * @return lista di citta ordinate per nome
   * @throws Exception
   */
  public List<City> getAllOrdered(int idProvince, boolean onlyWithUsers, boolean onlyWithSportCenters,boolean justUsersOnMarket) throws Exception
  {
    String sqlSelect = "select distinct c ";
    String sqlFrom = "from City c ";
    String sqlWhere = "where c.province.id = :idProvince ";
    String sqlOrder = "order by c.name asc";

    if (onlyWithUsers)
    {
      sqlFrom = sqlFrom.concat("inner join c.userList ul ");
      sqlWhere = sqlWhere.concat("and (ul.userStatus = '").concat(EnumUserStatus.Enabled.getValue()).concat("' and ul.anonymousEnabled = 0 ) ");
      if(justUsersOnMarket)
          sqlWhere = sqlWhere.concat(" and (ul.marketEnabled = 1 ) ");
    }
    if (onlyWithSportCenters)
    {
      sqlFrom = sqlFrom.concat("inner join c.sportCenterList sl ");
      sqlWhere = sqlWhere.concat("and (sl.enabled = 1 and sl.approved = 1) ");
    }

    String sql = sqlSelect.concat(sqlFrom).concat(sqlWhere).concat(sqlOrder);

    Query query = getSession().createQuery(sql).setParameter("idProvince", idProvince).setCacheable(true);
    return query.list();

  }

  /**
   *
   * @param idProvince id provincia d'interesse
   * @param onlyWithUsers indica se solo con iscritti in qualsiasi stato
   * @param onlyWithSportCenters indica se solo con centri sportivi in qualsiasi stato
   * @return tutti le città in cui ci sono Users/SportCenters in qualsiasi stato
   * @throws Exception
   */
  public List<City> getAll(int idProvince, boolean onlyWithUsers, boolean onlyWithSportCenters) throws Exception
  {
    Criteria criteria = getSession().createCriteria(City.class);
    if (idProvince > 0)
    {
      criteria.add(Restrictions.eq("province.id", idProvince));
    }
    if (onlyWithUsers)
    {
      criteria.createCriteria("userList");
    }
    if (onlyWithSportCenters)
    {
      criteria.createCriteria("sportCenterList");
    }
    criteria.addOrder(Order.asc("name"));
    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    return criteria.list();
  }

  /**
   *
   * @param idProvince provincia d'iteresse
   * @return lista di città con match futuri, ordinata per nome
   * @throws Exception
   */
  public List<City> getAllOrderedWithMatches(int idProvince) throws Exception
  {
    Criteria criteria = getSession().createCriteria(City.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
            add(Projections.id(), "id").
            add(Projections.property("name"), "name").
            add(Projections.property("province"), "province").
            add(Projections.property("totUsers"), "totUsers")));

    if (idProvince > 0)
      criteria.add(Restrictions.eq("province.id", idProvince));

    criteria.createAlias("sportCenterList", "scl", Criteria.INNER_JOIN);

    criteria.createAlias("scl.matchList", "ml", Criteria.INNER_JOIN);

    criteria.add(Restrictions.ge("ml.matchStart", new Date()));
    criteria.add(Restrictions.eq("ml.recorded", false));
    criteria.addOrder(Order.asc("name"));
    criteria.setResultTransformer(new AliasToBeanResultTransformer(City.class));

    return criteria.list();
  }

  /**
   *
   * @param idProvince id provincia d'interesse
   * @param onlyWithUsers indica se solo con iscritti Enabled
   * @param onlyWithSportCenters indica se solo con centri sportivi abilitati e approvati
   * @return lista di oggetti contenenti id e nome di città, ordinate per nome
   * @throws Exception
   */
  public List<Object[]> getAllOrderedProjection(int idProvince, boolean onlyWithUsers, boolean onlyWithSportCenters,boolean onlyUsersOnMarket) throws Exception
  {
    Criteria criteria = getSession().createCriteria(City.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
            add(Projections.id()).
            add(Projections.property("name"))));
    if (idProvince > 0)
    {
      criteria.add(Restrictions.eq("province.id", idProvince));
    }
    if (onlyWithUsers)
    {
      criteria.createCriteria("userList","ul").add(
              Restrictions.and(
              Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()),
              Restrictions.eq("anonymousEnabled", false)));
      if (onlyUsersOnMarket)
          criteria.add(Restrictions.eq("ul.marketEnabled", true));
    }
    if (onlyWithSportCenters)
    {
      criteria.createCriteria("sportCenterList").add(
              Restrictions.and(
              Restrictions.eq("enabled", true),
              Restrictions.eq("approved", true)));
    }
    criteria.addOrder(Order.asc("name"));
    return criteria.list();
  }

  /**
   *
   * @param idProvince id provincia d'interesse
   * @param onlyWithUsers indica se solo con iscritti in qualsiasi stato
   * @param onlyWithSportCenters indica se solo con centri sportivi in qualsiasi stato
   * @return lista di oggetti contenenti id e nome di città, ordinate per nome
   * @throws Exception
   */
  public List<Object[]> getAllProjection(int idProvince, boolean onlyWithUsers, boolean onlyWithSportCenters) throws Exception
  {
    Criteria criteria = getSession().createCriteria(City.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
            add(Projections.id()).
            add(Projections.property("name"))));
    if (idProvince > 0)
    {
      criteria.add(Restrictions.eq("province.id", idProvince));
    }
    if (onlyWithUsers)
    {
      criteria.createCriteria("userList");
    }
    if (onlyWithSportCenters)
    {
      criteria.createCriteria("sportCenterList");
    }
    criteria.addOrder(Order.asc("name"));
    return criteria.list();
  }

  /**
   *
   * @param idProvince id provoncia d'interesse
   * @return lista di oggetti contenenti id e nome di città con match futuri, ordinate per nome
   * @throws Exception
   */
  public List<Object[]> getAllOrderedWithMatchesProjection(int idProvince) throws Exception
  {
    return getAllOrderedWithMatchesProjection(idProvince, false);
  }

  /**
   *
   * @param idProvince provoncia d'interesse
   * 
   * @param past "true" per partite passate, false per partite future
   * @return lista di oggetti contenenti id e nome di città con match, ordinate per nome
   * @throws Exception
   */
  public List<Object[]> getAllOrderedWithMatchesProjection(int idProvince, boolean past) throws Exception
  {
    Criteria criteria = getSession().createCriteria(City.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
            add(Projections.id()).
            add(Projections.property("name"))));
    if (idProvince > 0)
      criteria.add(Restrictions.eq("province.id", idProvince));

    criteria.createAlias("sportCenterList", "scl", Criteria.INNER_JOIN);

    criteria.createAlias("scl.matchList", "ml", Criteria.INNER_JOIN);

    if (past)
      criteria.add(Restrictions.lt("ml.matchStart", new Date()));
    else
    {
      criteria.add(Restrictions.ge("ml.matchStart", new Date()));
      criteria.add(Restrictions.eq("ml.recorded", false));
    }

    criteria.addOrder(Order.asc("name"));

    return criteria.list();
  }

  /**
   * incrementa il campo totUsers di un'unità
   * @param idCity
   * @throws Exception
   */
  public void increaseTotUsers(int idCity) throws Exception
  {
    changeTotUsers(idCity, 1);
  }

  /**
   * decrementa il campo totUsers di un'unità
   * @param idCity
   * @throws Exception
   */
  public void decreaseTotUsers(int idCity) throws Exception
  {
    changeTotUsers(idCity, -1);
  }

  private void changeTotUsers(int idCity, int offset) throws Exception
  {
    City city = get(idCity);
    if (city == null)
    {
      return;
    }
    city.setTotUsers(city.getTotUsers() + offset);
    makePersistent(city);
  }

}
