package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.Province;
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
 * DAO per la gestione delle province. Contiene tutti i metodi che lavorano sulla tabella PROVINCES.
 */
public class ProvinceDAO extends AGenericDAO<Province, Integer>
{

  /**
   *
   * @param session
   */
  public ProvinceDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param idCountry nazione d'interesse
   * @param onlyWithUsers indica se solo con iscritti Enabled
   * @param onlyWithSportCenters indica se solo con centri sportivi abilitati e approvati
   * @return lista delle province ordinate per nome, secondo i parametri specificati
   * @throws Exception
   */
  public List<Province> getAllOrdered(Integer idCountry, boolean onlyWithUsers, boolean onlyWithSportCenters, boolean justUsersOnMarket) throws Exception
  {

    String sqlSelect = "select distinct p ";
    String sqlFrom = "from Province p ";
    String sqlWhere = "";
    String sqlOrder = "order by p.name asc ";

    if (idCountry > 0)
    {
      sqlWhere = sqlWhere.concat("where (p.country.id = :idCountry) ");
    }
    if (onlyWithUsers)
    {
      sqlFrom = sqlFrom.concat("inner join p.userList ul ");
      
      sqlWhere = sqlWhere.concat(idCountry > 0 ? "and " : "where ");
      
      sqlWhere = sqlWhere.concat("(ul.userStatus = '").concat(EnumUserStatus.Enabled.getValue()).concat("' and ul.anonymousEnabled = 0 ) ");
      
      if(justUsersOnMarket)
          sqlWhere = sqlWhere.concat(" and (ul.marketEnabled = 1 ) ");

    }
    if (onlyWithSportCenters)
    {
      sqlWhere = sqlWhere.concat(idCountry > 0 || onlyWithUsers ? "and " : "where ");

      sqlFrom = sqlFrom.concat("inner join p.sportCenterList sl ");
      
      sqlWhere = sqlWhere.concat("(sl.enabled = 1 and sl.approved = 1) ");
    }
    
    String sql = sqlSelect.concat(sqlFrom).concat(sqlWhere).concat(sqlOrder);

    Query query = getSession().createQuery(sql).setParameter("idCountry", idCountry).setCacheable(true);
    return query.list();

  }

  /**
   *
   * @param idCountry
   * @param onlyWithUsers indica se solo con iscritti
   * @param onlyWithSportCenters indica se solo con centri sportivi 
   * @return tutte le province in cui ci sono Users/SportCenters in qualsiasi stato
   * @throws Exception
   */
  public List<Province> getAll(Integer idCountry, boolean onlyWithUsers, boolean onlyWithSportCenters) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Province.class);
    if (idCountry > 0)
      criteria.add(Restrictions.eq("country.id", idCountry));
    if (onlyWithUsers)
    {
      criteria.createCriteria("userList");
    }
    if (onlyWithSportCenters)
    {
      criteria.createCriteria("sportCenterList");
    }
    criteria.addOrder(Order.asc("name"));

    criteria.addOrder(Order.asc("name"));

    criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("id"), "id")
                                                                            .add(Projections.property("name"), "name")
                                                                            .add(Projections.property("abbreviation"), "abbreviation")
                                                                            .add(Projections.property("country"), "country")));
    criteria.setResultTransformer(new AliasToBeanResultTransformer(Province.class));

    return criteria.list();
  }


  /**
   *
   * @param abbreviation abbreviazione della provincia cercata
   * @return provincia
   * @throws Exception
   */
  public Province getByAbbreviation(String abbreviation) throws Exception
  {
    return findEntityByCriteria(Restrictions.eq("abbreviation", abbreviation));
  }


   /**
    *
    * @param idCountry nazione d'interesse
    * @return lista province ordinate per nome con partite future
    * @throws Exception
    */
   public List<Province> getAllOrderedWithMatches(Integer idCountry) throws Exception
  {
    return getAllOrderedWithMatches( idCountry,false);
  }

   /**
    *
    * @param idCountry
    * @param recorded "true" con partite passate,"false" con partite future
    * @return lista province ordinate per nome
    * @throws Exception
    */
   public List<Province> getAllOrderedWithMatches(Integer idCountry,boolean recorded) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Province.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
              add(Projections.id(), "id").
              add(Projections.property("name"), "name").
              add(Projections.property("country"), "country").
              add(Projections.property("abbreviation"), "abbreviation").
              add(Projections.property("totUsers"), "totUsers")));
    if (idCountry > 0)
    {
    criteria.add(Restrictions.eq("country.id", idCountry));
    }
      
    criteria.createAlias("sportCenterList", "scl", Criteria.INNER_JOIN);

    criteria.createAlias("scl.matchList", "ml", Criteria.INNER_JOIN);
    
    if(recorded) 
    {
        criteria.add(Restrictions.lt("ml.matchStart", new Date()));
    }
    else
    {
        criteria.add(Restrictions.ge("ml.matchStart", new Date()));
        criteria.add(Restrictions.eq("ml.recorded", false));
    }
    
    criteria.addOrder(Order.asc("name"));
    criteria.setResultTransformer(new AliasToBeanResultTransformer(Province.class));

    return criteria.list();
  }

  
   /**
    *
    * @param idCountry nazione d'interesse
    * @param onlyWithUsers indica se solo con iscritti Enabled e non anonimi
   * @param onlyWithSportCenters indica se solo con centri sportivi abilitati e approvati
    * @return lista di oggetti contenenti id e nome di province, ordinate per nome
    * @throws Exception
    */
   public List<Object[]> getAllOrderedProjection(int idCountry, boolean onlyWithUsers, boolean onlyWithSportCenters,boolean onlyUsersOnMarket) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Province.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
            add(Projections.id()).
            add(Projections.property("name"))));
    if (idCountry > 0)
      criteria.add(Restrictions.eq("country.id", idCountry));
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
   * @param idCountry nazione d'interesse
   * @param onlyWithUsers indica se solo con iscritti in qualsiasi stato
   * @param onlyWithSportCenters indica se solo con centri sportivi in qualsiasi stato
   * @return lista di oggetti contenenti id e nome di province, ordinate per nome
   * @throws Exception
   */
  public List<Object[]> getAllProjection(int idCountry, boolean onlyWithUsers, boolean onlyWithSportCenters) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Province.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
            add(Projections.id()).
            add(Projections.property("name"))));
    if (idCountry > 0)
      criteria.add(Restrictions.eq("country.id", idCountry));
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
   * @param idCountry filtro nazione d'interesse
   * @return lista di oggetti contenenti id e nome di province con partite future, ordinate per nome
   * @throws Exception
   */
  public List<Object[]> getAllOrderedWithMatchesProjection(Integer idCountry) throws Exception
  {
    return getAllOrderedWithMatchesProjection(idCountry,false);
  }

  /**
   *
   * @param idCountry filtro nazione d'interesse
   * @param past "true" con partite passate, "false" con partite future
   * @return lista di oggetti contenenti id e nome di province secondo il filtro partite specificato, ordinate per nome
   * @throws Exception
   */
  public List<Object[]> getAllOrderedWithMatchesProjection(Integer idCountry,boolean past) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Province.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
            add(Projections.id()).
            add(Projections.property("name"))));
    if (idCountry > 0)
      criteria.add(Restrictions.eq("country.id", idCountry));

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

}
