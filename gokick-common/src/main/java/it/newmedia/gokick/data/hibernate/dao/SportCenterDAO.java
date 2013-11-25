package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;

/**
 *
 * DAO per la gestione delle rose. Contiene tutti i metodi che lavorano sulla tabella SQUADS.
 */
public class SportCenterDAO extends AGenericDAO<SportCenter, Integer>
{

  /**
   *
   * @param session
   */
  public SportCenterDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * 
   * @return lista id centri sportivi 
   * @throws Exception
   */
  public List<Integer> getAllId() throws Exception
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.id()));
    criteria.add(Restrictions.eq("enabled", true));
    criteria.add(Restrictions.eq("approved", true));
    return criteria.list();
  }

  /**
   *
   * @param name nome centro
   * @param idCountry filtro nazione centro
   * @param idProvince filtro provincia centro
   * @param idCity filtro città centro
   * @param idMatchType filtro tipo match
   * @param idPitchCover filtro tipo copertura centro
   * @return lista id centri sportivi secondo i filtri specificati,approvati e abilitati,ordinati per nome
   * @throws Exception
   */
  public List<Integer> getBySearchParameters(String name,
                                             int idCountry,
                                             int idProvince,
                                             int idCity,
                                             int idMatchType,
                                             int idPitchCover) throws Exception
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.id()));
    criteria.add(Restrictions.eq("enabled", true));
    criteria.add(Restrictions.eq("approved", true));

    if (name != null && !name.isEmpty())
    {
      criteria.add(Restrictions.like("name", "%" + name + "%"));
    }
    if (idCity > 0)
      criteria.add(Restrictions.eq("city.id", idCity));
    else if (idProvince > 0)
      criteria.add(Restrictions.eq("province.id", idProvince));
    else if (idCountry > 0)
      criteria.add(Restrictions.eq("country.id", idCountry));
    if (idMatchType > 0 && idPitchCover > 0)
    {
      criteria.createCriteria("pitchList").add(
              Restrictions.and(
              Restrictions.eq("matchType.id", idMatchType),
              Restrictions.eq("pitchCover.id", idPitchCover)));
    }
    else if (idMatchType > 0)
    {
      criteria.createCriteria("pitchList").add(Restrictions.eq("matchType.id", idMatchType));
    }
    else if (idPitchCover > 0)
    {
      criteria.createCriteria("pitchList").add(Restrictions.eq("pitchCover.id", idPitchCover));
    }
    criteria.addOrder(Order.asc("name"));
    return criteria.list();
  }

  /**
   *
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @return lista di oggetti contenenti id e nome di centri sportivi approvati e abilitati, secondo i filtri specificato, ordinati per nome
   * @throws Exception
   */
  public List<Object[]> getAllProjection(Integer idCountry, Integer idProvince, Integer idCity) throws Exception
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
            add(Projections.id()).
            add(Projections.property("name"))));
    if (idCity > 0)
      criteria.add(Restrictions.eq("city.id", idCity));
    else if (idProvince > 0)
      criteria.add(Restrictions.eq("province.id", idProvince));
    else if (idCountry > 0)
      criteria.add(Restrictions.eq("country.id", idCountry));
    criteria.add(Restrictions.eq("approved", true));
    criteria.add(Restrictions.eq("enabled", true));
    return criteria.list();
  }

  /**
   *
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @return lista di oggetti contenenti id e nome di centri sportivi in qualsiasi stato, secondo i filtri specificati, ordinati per nome
   * @throws Exception
   */
  public List<SportCenter> getAll(Integer idCountry, Integer idProvince, Integer idCity) throws Exception
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);
    if (idCity > 0)
      criteria.add(Restrictions.eq("city.id", idCity));
    else if (idProvince > 0)
      criteria.add(Restrictions.eq("province.id", idProvince));
    else if (idCountry > 0)
      criteria.add(Restrictions.eq("country.id", idCountry));
    criteria.add(Restrictions.eq("approved", true));
    criteria.add(Restrictions.eq("enabled", true));
    return criteria.list();
  }
  
  /**
   *
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @param name filtro nome
   * @param idMatchtype filtro tipo match
   * @param idPitchCover filtro tipo copertura centro
   * @param approved "true" per soli centri approvati
   * @param enabled "true" per soli campi abilitati
   * @param searchStatus "true" per attivare filtro approved e enabled
   * @param justConventioned "true" per soli centri con convenzioni
   * @return lista centri sportivi secondo i parametri specificati
   * @throws Exception
   */
  public List<SportCenter> getAllByParameters(Integer idCountry,
                                              Integer idProvince, 
                                              Integer idCity,
                                              String name,
                                              int idMatchtype,
                                              int idPitchCover,
                                              boolean approved,
                                              boolean enabled,
                                              boolean searchStatus,
                                              boolean justConventioned
                                              ) throws Exception
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);

    if (idCity > 0)
      criteria.add(Restrictions.eq("city.id", idCity));
    else if (idProvince > 0)
      criteria.add(Restrictions.eq("province.id", idProvince));
    else if (idCountry > 0)
      criteria.add(Restrictions.eq("country.id", idCountry));
    
    if (!StringUtils.isEmpty(name))
      criteria.add(Restrictions.like("name", "%" + name + "%"));


    if (idMatchtype > 0 && idPitchCover > 0)
    {
      criteria.createCriteria("pitchList").add(
              Restrictions.and(
              Restrictions.eq("matchType.id", idMatchtype),
              Restrictions.eq("pitchCover.id", idPitchCover)));
    }
    else if (idMatchtype > 0)
    {
      criteria.createCriteria("pitchList").add(Restrictions.eq("matchType.id", idMatchtype));
    }
    else if (idPitchCover > 0)
    {
      criteria.createCriteria("pitchList").add(Restrictions.eq("pitchCover.id", idPitchCover));
    }

    if (searchStatus)
    {
      criteria.add(Restrictions.eq("enabled", enabled));
      criteria.add(Restrictions.eq("approved", approved));
    }

    if (justConventioned)
      criteria.add(Restrictions.ge("conventionTo", new Date() ));

    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

    return criteria.list();
  }
  
  /**
   *
   * @return lista centri sportivi ordinati per nome desc
   * @throws Exception
   */
  public List<SportCenter> getAll() throws Exception
  {
    String sql = " FROM SportCenter AS s ORDER BY s.created DESC";
    Query query = getSession().createQuery(sql.toString());
    return query.list();

  }

  /**
   *
   * @return lista centri sportivi (con fetch di hibernate) ordinati per nome
   * @throws Exception
   */
  public List<SportCenter> getAllFetchUser(int idCountryFilter) throws Exception
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);
    criteria.setFetchMode("userAuthor", FetchMode.JOIN);
    criteria.setFetchMode("city", FetchMode.JOIN);
    criteria.setFetchMode("province", FetchMode.JOIN);
    if (idCountryFilter > 0)
    {
      criteria.setFetchMode("country", FetchMode.JOIN);
      criteria.add(Restrictions.eq("country.id", idCountryFilter));
    }
    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    criteria.addOrder(Order.desc("name"));
    return criteria.list();
  }

  /**
   *
   * @return numero centri sportivi approvati e abilitati
   * @throws Exception
   */
  public int getCount() throws Exception
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);
    criteria.setProjection(Projections.rowCount());
    criteria.add(Restrictions.eq("approved", true));
    criteria.add(Restrictions.eq("enabled", true));
    return ((Integer) criteria.list().get(0)).intValue();
  }

  /**
   *
   * @return numero dei centri sportivi in qualsiasi stato
   * @throws Exception
   */
  public int getCountAll() throws Exception
  {
    return getCountAll(0);
  }
  
  public int getCountAll(int idCountryFilter) throws Exception
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);
    if(idCountryFilter > 0)
    {
      criteria.createCriteria("country").add(Restrictions.eq("id", idCountryFilter));
    }
    criteria.setProjection(Projections.rowCount());
    return ((Integer) criteria.list().get(0)).intValue();
  }

  /**
   *
   * @param approved "true" se solo approvati
   * @param enabled "true" se solo abilitati
   * @return numero centri sportivi nello stato specificato dai filtri
   * @throws Exception
   */
  public int getCountAllbyStatus(boolean approved, boolean enabled) throws Exception
  {
    return getCountAllbyStatus(approved,enabled,0);
  }
  
  public int getCountAllbyStatus(boolean approved, boolean enabled,int idCountryFilter) throws Exception
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);
    if(idCountryFilter > 0)
    {
      criteria.createCriteria("country").add(Restrictions.eq("id", idCountryFilter));
    }
    criteria.add(Restrictions.eq("approved", approved));
    criteria.add(Restrictions.eq("enabled", enabled));
    criteria.setProjection(Projections.rowCount());
    return ((Integer) criteria.list().get(0)).intValue();
  }

  /**
   * 
   * @return numero centri convenzionati
   * @throws Exception
   */
  public int getCountAllConvetioned() throws Exception
  {
    
    return getCountAllConvetioned(0);
  }
  
  public int getCountAllConvetioned(int idCountryFilter) throws Exception
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);
    if(idCountryFilter > 0)
    {
      criteria.createCriteria("country").add(Restrictions.eq("id", idCountryFilter));
    }
    criteria.add(Restrictions.ge("conventionTo", new Date() ));
    criteria.setProjection(Projections.rowCount());
    return ((Integer) criteria.list().get(0)).intValue();
  }

  /**
   *
   * @param IdSportCenterList lista id centri sportivi richiesti
   * @return lista di centri sportivi
   */
  public List<SportCenter> getByIdSportCenterList(List<Integer> IdSportCenterList)
  {
    Criteria criteria = getSession().createCriteria(SportCenter.class);
    criteria.add(Restrictions.in("id", IdSportCenterList));
    criteria.setFetchMode("userAuthor", FetchMode.JOIN);
    criteria.addOrder(Order.desc("name"));
    return criteria.list();
  }


}
