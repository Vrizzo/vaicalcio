package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.Country;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 *
 * DAO per la gestione delle nazioni. Contiene tutti i metodi che lavorano sulla tabella COUNTRIES.
 */
public class CountryDAO extends AGenericDAO<Country, Integer>
{

  /**
   *
   * @param session
   */
  public CountryDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param onlyWithUsers indica se solo con iscritti in stato enabled
   * @param onlyWithSportCenters indica se solo con centri sportivi approvati e abilitati
   * @return lista di nazioni ordinate per posizione,nome
   * @throws Exception
   */
  public List<Country> getAllOrdered(boolean onlyWithUsers, boolean onlyWithSportCenters) throws Exception
  {
    return getAllOrdered(onlyWithUsers, onlyWithSportCenters, false,0);
  }
  
  /**
   *
   * @param onlyWithUsers indica se solo con iscritti in stato enabled
   * @param onlyWithSportCenters indica se solo con centri sportivi approvati e abilitati
   * @return lista di nazioni ordinate per posizione,nome
   * @throws Exception
   */
  public List<Country> getAllOrdered(boolean onlyWithUsers, boolean onlyWithSportCenters,int idCountryFilter) throws Exception
  {
    return getAllOrdered(onlyWithUsers, onlyWithSportCenters, false,idCountryFilter);
  }
  
  public List<Country> getAllOrdered(boolean onlyWithUsers, boolean onlyWithSportCenters,boolean onlyWithMatch) throws Exception
  {
    return getAllOrdered(onlyWithUsers, onlyWithSportCenters, onlyWithMatch,0);
  }

  /**
   *
   * @param onlyWithUsers indica se solo con iscritti in stato enabled
   * @param onlyWithSportCenters indica se solo con centri sportivi approvati e abilitati
   * @param onlyWithMatch indica se solo con match futuri
   * @return lista di nazioni ordinate per posizione,nome
   * @throws Exception
   */
  public List<Country> getAllOrdered(boolean onlyWithUsers, boolean onlyWithSportCenters, boolean onlyWithMatch,int idCountryFilter) throws Exception
  {
    return getAllOrdered(onlyWithUsers, onlyWithSportCenters, onlyWithMatch, false, true, false,idCountryFilter);
  }
  
  public List<Country> getAllOrdered(boolean onlyWithUsers, boolean onlyWithSportCenters, boolean onlyWithMatch, boolean recorded, boolean onlyWithProvince,boolean justUsersOnMarket) throws Exception
  {
    return getAllOrdered( onlyWithUsers,  onlyWithSportCenters,  onlyWithMatch,  recorded,  onlyWithProvince, justUsersOnMarket,0);
  }
  

  /**
   *
   * @param onlyWithUsers indica se solo con iscritti in stato enabled
   * @param onlyWithSportCenters indica se solo con centri sportivi approvati e abilitati
   * @param onlyWithMatch indica se solo con match
   * @param recorded "true" per match passati, "false" per match futuri
   * @param onlyWithProvince solo quelli per cui sono indicate province
   * @param onlyUsersOnMarket solo utenti sul mercato
   * @return
   * @throws Exception
   */
  public List<Country> getAllOrdered(boolean onlyWithUsers, boolean onlyWithSportCenters, boolean onlyWithMatch, boolean recorded, boolean onlyWithProvince,boolean justUsersOnMarket,int idCountryFilter) throws Exception
  {
    String sqlSelect = "select distinct c ";
    String sqlFrom = "from Country c ";
    String sqlWhere = "";
    String sqlOrder = "order by c.position, c.name asc";

    if (onlyWithProvince)
    {
      sqlFrom = sqlFrom.concat("inner join c.provinceList pl ");
    }
    if (onlyWithUsers)
    {
      sqlFrom = sqlFrom.concat("inner join c.userList ul ");
      sqlWhere = sqlWhere.concat("where (ul.userStatus = '").concat(EnumUserStatus.Enabled.getValue()).concat("' and ul.anonymousEnabled = 0 ) ");
      if(justUsersOnMarket)
          sqlWhere = sqlWhere.concat("and (ul.marketEnabled = 1 ) ");
    }
    if (onlyWithSportCenters)
    {
      sqlFrom = sqlFrom.concat("inner join c.sportCenterList sl ");
      sqlWhere = sqlWhere.concat(onlyWithUsers ? "and " : "where ");
      sqlWhere = sqlWhere.concat("(sl.enabled = 1 and sl.approved = 1) ");
    }
    
    if (onlyWithMatch)
    {
      sqlFrom = sqlFrom.concat("inner join c.sportCenterList scl ");
      sqlFrom = sqlFrom.concat("inner join scl.matchList ml ");
      sqlWhere = sqlWhere.concat(onlyWithUsers || onlyWithSportCenters ? "and " : "where ");
      if (recorded)
        sqlWhere = sqlWhere.concat("(ml.matchStart < NOW()) ");
      else
      {
        sqlWhere = sqlWhere.concat("(ml.matchStart >= NOW()) ");
        sqlWhere = sqlWhere.concat("and (ml.recorded = 0) ");

      }
    }
    
    if (idCountryFilter > 0)
    {
      if (StringUtils.isBlank(sqlWhere))
        sqlWhere = sqlWhere.concat("where c.id = " + idCountryFilter );
      else
        sqlWhere = sqlWhere.concat("and (c.id = " + idCountryFilter +") ");
    }

    String sql = sqlSelect.concat(sqlFrom).concat(sqlWhere).concat(sqlOrder);

    Query query = getSession().createQuery(sql).setCacheable(true);
    return query.list();

  }

  /**
   *
   * @param onlyWithUsers indica se solo con iscritti in qualsiasi stato
   * @param onlyWithSportCenters indica se solo con centri sportivi in qualsiasi stato
   * @return tutte le nazioni ordinate per posizione e nome, in cui ci sono Users/SportCenters in qualsiasi stato
   * @throws Exception
   */
  public List<Country> getAll(boolean onlyWithUsers, boolean onlyWithSportCenters) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Country.class);
    if (onlyWithUsers)
    {
      criteria.createCriteria("userList");
    }
    if (onlyWithSportCenters)
    {
      criteria.createCriteria("sportCenterList");
    }
    
    criteria.addOrder(Order.asc("name"));
    //sempre solo con province
    criteria.createCriteria("provinceList");

    criteria.addOrder(Order.asc("position"));
    criteria.addOrder(Order.asc("name"));
    
    criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("id"), "id")
                                                                            .add(Projections.property("name"), "name")
                                                                            .add(Projections.property("position"), "position")
                                                                            .add(Projections.property("totUsers"), "totUsers")));
    criteria.setResultTransformer(new AliasToBeanResultTransformer(Country.class));

    return criteria.list();
  }

}
