package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.enums.EnumUserSquadStatus;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione degli utenti. Contiene tutti i metodi che lavorano sulla tabella USERS.
 */
public class UserDAO extends AGenericDAO<User, Integer>
{

  /**
   *
   * @param session
   */
  public UserDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param email email cercata
   * @param id > 0  per ricerca specifica per User
   * @return "true" se esiste già la mail specificata
   * @throws Exception
   */
  public Boolean checkExistingEmail(String email, Integer id) throws Exception
  {
    String sql = " FROM User AS u WHERE u.email = :email";
    if (id != null && id > 0)
    {
      sql += " AND u.id <> :id";
    }
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("email", email);
    if (id != null && id > 0)
    {
      query.setParameter("id", id);
    }
    if (!query.list().isEmpty())
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  /**
   *
   * @param forumNickname nick da ricercare
   * @param id  > 0  per ricerca specifica per User
   * @return "true" se esiste già il nick specificato
   * @throws Exception
   */
  public Boolean checkExistingForumNickname(String forumNickname, Integer id) throws Exception
  {
    String sql = " FROM User AS u WHERE u.forumNickname = :forumNickname";
    if (id != null && id > 0)
    {
      sql += " AND u.id <> :id";
    }
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("forumNickname", forumNickname);
    if (id != null && id > 0)
    {
      query.setParameter("id", id);
    }
    if (!query.list().isEmpty())
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  /**
   *
   * @param id utente d'interesse
   * @param checkPending codice attivazione registrazione
   * @return utente
   * @throws Exception
   */
  public User getByIdAndCheckPending(Integer id, String checkPending) throws Exception
  {
    return findEntityByCriteria(
            Restrictions.eq("id", id),
            Restrictions.eq("checkPending", checkPending));
  }

  /**
   *
   * @param id utente d'interesse
   * @param checkPassword codice richiesta password dimenticata
   * @return utente
   * @throws Exception
   */
  public User getByIdAndCheckPassword(Integer id, String checkPassword) throws Exception
  {
    Criteria criteria = getSession().createCriteria(User.class);
    criteria.add(Restrictions.eq("id", id));
    criteria.add(Restrictions.eq("checkPassword", checkPassword));
    criteria.add(Restrictions.isNotNull("checkPasswordExpire"));
    return (User) criteria.uniqueResult();
  }

  /**
   *
   * @param password pwd utente
   * @param id utente d'interesse
   * @return utente
   * @throws Exception
   */
  public Boolean checkRightUserPassword(String password, Integer id) throws Exception
  {
    String sql = " FROM User AS u WHERE u.id = :id AND u.password = :password ";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("id", id);
    query.setParameter("password", password);
    return (!query.list().isEmpty());
  }

  /**
   *
   * @param email email dell'utente cercato
   * @return utente
   * @throws Exception
   */
  public User getByEmail(String email) throws Exception
  {
    Criteria criteria = getSession().createCriteria(User.class);
    criteria.add(Restrictions.eq("email", email));
    criteria.setMaxResults(1);
    return (User) criteria.uniqueResult();
  }

  /**
   *
   * @param id utente d'interesse
   * @return utente non anonimo, abilitato
   * @throws Exception
   */
  public User getByIdAndNotAnonymous(int id) throws Exception
  {
    Criteria criteria = getSession().createCriteria(User.class);
    criteria.add(Restrictions.eq("id", id));
    criteria.add(Restrictions.eq("anonymousEnabled", false));
    criteria.add(Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()));
    criteria.setMaxResults(1);
    return (User) criteria.uniqueResult();
  }

  /**
   *
   * @param email email utente
   * @param password pwd utente
   * @return utente specificato sia abilitato che pending
   * @throws Exception
   */
  public User login(String email, String password) throws Exception
  {
    Criteria criteria = getSession().createCriteria(User.class);
    criteria.add(Restrictions.eq("email", email));
    criteria.add(Restrictions.eq("password", password));
    criteria.add(Restrictions.in("userStatus", new String[]
            {
              EnumUserStatus.Enabled.getValue(), EnumUserStatus.Pending.getValue()
            }));
    criteria.setMaxResults(1);
    return (User) criteria.uniqueResult();
  }

  /**
   *
   * @param idProvince filtro provincia
   * @param idUserAlreadyPresentList lista utenti da escludere dalla ricerca
   * @return lista di id utente sul mercato,abilitati,nella provincia specificata che non siano contenuti nella lista idUserAlreadyPresentList
   * @throws Exception
   */
  public List getLimitByIdProvinceAndOpenMarket(int idProvince, List idUserAlreadyPresentList) throws Exception
  {
    Criteria criteria = getSession().createCriteria(User.class);
    criteria.setProjection(Projections.id());
    criteria.add(Restrictions.eq("province.id", idProvince));
    criteria.add(Restrictions.eq("marketEnabled", true));
    criteria.add(Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()));
    criteria.add(Restrictions.not(Restrictions.in("id", idUserAlreadyPresentList)));
    criteria.setMaxResults(10);
    return criteria.list();
  }

  /**
   *
   * @param idProvince filtro provincia
   * @return lista di id utente sul mercato,abilitati,nella provincia specificata
   * @throws Exception
   */
  public List<Integer> getByIdProvinceAndOpenMarket(int idProvince) throws Exception
  {
    Criteria criteria = getSession().createCriteria(User.class);
    criteria.setProjection(Projections.id());
    criteria.add(Restrictions.eq("province.id", idProvince));
    criteria.add(Restrictions.eq("marketEnabled", true));
    criteria.add(Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()));
    criteria.addOrder(Order.asc("playedMatches")).addOrder(Order.desc("created"));
    return criteria.list();
  }

  /**
   *
   * @param idProvince filtro provincia
   * @return lista id utente filtrati per provincia.abilitati
   * @throws Exception
   */
  public List getAllByIdProvince(int idProvince) throws Exception
  {
    String sql = "SELECT u.id FROM User AS u WHERE u.province.id = :idProvince AND u.userStatus = :userStatusEnabled ORDER BY u.lastName ASC";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idProvince", idProvince);
    query.setParameter("userStatusEnabled", EnumUserStatus.Enabled.getValue());
    return query.list();
  }

  /**
   * @return lista id utente abilitati
   * @throws Exception
   */
  public List getAllId() throws Exception
  {
    String sql = "SELECT u.id FROM User AS u WHERE u.userStatus = :userStatusEnabled";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("userStatusEnabled", EnumUserStatus.Enabled.getValue());
    return query.list();
  }

  /**
   *
   * @param firstName filtro nome
   * @param lastName filtro cognome
   * @param minBirthDate filtro data nascita minima
   * @param maxBirthDate filtro data nascita massima
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @param roles filtro ruolo
   * @param marketEnabled "true" per ricerca "sul mercato"
   * @param searchNewsLetterEnabled "true" per ricerca iscritti newsletter
   * @param status filtro stato utente
   * @param permission filtro permessi
   * @return lista utenti secondo i parametri specificati ordinati per id
   * @throws Exception
   */
  public List<User> getUserBySearchParameters(String firstName,
                                             String lastName,
                                             Date minBirthDate,
                                             Date maxBirthDate,
                                             int idCountry,
                                             int idProvince,
                                             int idCity,
                                             List<Integer> roles,
                                             boolean marketEnabled,
                                             boolean searchNewsLetterEnabled,
                                             String status,
                                             String permission) throws Exception
  {
    Criteria criteria = getSession().createCriteria(User.class);

    if (firstName != null && firstName.length() > 0)
    {
      criteria.add(Restrictions.like("firstName", firstName + "%"));
    }
    if (lastName != null && lastName.length() > 0)
    {
      criteria.add(Restrictions.like("lastName", lastName + "%"));
    }
    if (minBirthDate != null)
    {
      criteria.add(Restrictions.le("birthDay", minBirthDate));
    }
    if (maxBirthDate != null)
    {
      criteria.add(Restrictions.ge("birthDay", maxBirthDate));
    }
    if (idCountry > 0)
    {
      criteria.add(Restrictions.eq("country.id", idCountry));
    }
    if (idProvince > 0)
    {
      criteria.add(Restrictions.eq("province.id", idProvince));
    }
    if (idCity > 0)
    {
      criteria.add(Restrictions.eq("city.id", idCity));
    }

    criteria.setFetchMode("playerRole", FetchMode.JOIN);
    if (roles != null && roles.size() > 0)
    {
      criteria.createCriteria("playerRole").add(Restrictions.in("id", roles));
    }
    if (marketEnabled)
    {
      criteria.add(Restrictions.eq("marketEnabled", marketEnabled));
    }
    if (searchNewsLetterEnabled)
    {
      criteria.add(Restrictions.eq("newsletterEnabled", searchNewsLetterEnabled));
    }
    if (StringUtils.isNotEmpty(status))
    {
      criteria.add(Restrictions.eq("userStatus", status));
    }

    criteria.setFetchMode("userPermissions", FetchMode.JOIN);
    if (StringUtils.isNotBlank(permission))
    {
      List<String> permissionList = new ArrayList<String>();
      permissionList.add(permission);
      criteria.createCriteria("userPermissions").add(Restrictions.in("permission", permissionList));
    }

    criteria.setFetchMode("pictureCards", FetchMode.JOIN);

    criteria.setFetchMode("squads", FetchMode.JOIN);
    criteria.setFetchMode("squads.userSquadList", FetchMode.JOIN);

    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    criteria.addOrder(Order.desc("id"));
    //criteria.setCacheable(true);

    return criteria.list();
  }

  /**
   *
   * @param firstName filtro nome
   * @param lastName filtro cognome
   * @param minBirthDate filtro data nascita minima
   * @param maxBirthDate filtro data nascita massima
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @param roles filtro ruolo
   * @param marketEnabled "true" per ricerca "sul mercato"
   * @return lista id utente secondo i parametri specificati ordinati per id
   * @throws Exception
   */
  public List<Integer> getBySearchParameters(String firstName,
                                             String lastName,
                                             Date minBirthDate,
                                             Date maxBirthDate,
                                             int idCountry,
                                             int idProvince,
                                             int idCity,
                                             List<Integer> roles,
                                             boolean marketEnabled) throws Exception
  {
    return getBySearchParameters(firstName, lastName, minBirthDate, maxBirthDate, idCountry, idProvince, idCity, roles, marketEnabled, false, null);
  }

  /**
   *
   * @param firstName filtro nome
   * @param lastName filtro cognome
   * @param minBirthDate filtro data nascita minima
   * @param maxBirthDate filtro data nascita massima
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @param roles filtro ruolo
   * @param marketEnabled "true" per ricerca "sul mercato"
   * @param searchNewsLetterEnabled "true" per ricerca iscritti newsletter
   * @param status filtro stato utente
   * @return lista id utente secondo i parametri specificati ordinati per id
   * @throws Exception
   */
  public List<Integer> getBySearchParameters(String firstName,
                                             String lastName,
                                             Date minBirthDate,
                                             Date maxBirthDate,
                                             int idCountry,
                                             int idProvince,
                                             int idCity,
                                             List<Integer> roles,
                                             boolean marketEnabled,
                                             boolean searchNewsLetterEnabled,
                                             String status
                                             ) throws Exception
  {
    return getBySearchParameters (firstName,lastName,minBirthDate,maxBirthDate,idCountry,idProvince,idCity,roles,marketEnabled,searchNewsLetterEnabled,status,null);
  }


  /**
   *
   * @param firstName filtro nome
   * @param lastName filtro cognome
   * @param minBirthDate filtro data nascita minima
   * @param maxBirthDate filtro data nascita massima
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @param roles filtro ruolo
   * @param marketEnabled "true" per ricerca "sul mercato"
   * @param searchNewsLetterEnabled "true" per ricerca iscritti newsletter
   * @param status filtro stato utente
   * @param permission filtro permessi backoffice
   * @return lista id utente secondo i parametri specificati ordinati per id
   * @throws Exception
   */
  public List<Integer> getBySearchParameters(String firstName,
                                             String lastName,
                                             Date minBirthDate,
                                             Date maxBirthDate,
                                             int idCountry,
                                             int idProvince,
                                             int idCity,
                                             List<Integer> roles,
                                             boolean marketEnabled,
                                             boolean searchNewsLetterEnabled,
                                             String status,
                                             String permission) throws Exception
  {
    return getBySearchParametersOrdered(firstName, lastName, minBirthDate, maxBirthDate, idCountry, idProvince, idCity, roles, marketEnabled, searchNewsLetterEnabled, status, permission, "id", null);
  }

  /**
   *
   * @param firstName filtro nome
   * @param lastName filtro cognome
   * @param minBirthDate filtro data nascita minima
   * @param maxBirthDate filtro data nascita massima
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @param roles filtro ruolo
   * @param marketEnabled "true" per ricerca "sul mercato"
   * @param searchNewsLetterEnabled "true" per ricerca iscritti newsletter
   * @param status filtro stato utente
   * @param permission filtro permessi backoffice
   * @param firstOrder primo criterio di ordinamento
   * @param secondOrder secondo criterio di ordinamento
   * @return lista id utente secondo i parametri specificati ordinati per id
   * @throws Exception
   */
  public List<Integer> getSearchLightOrdered(String firstName,
                                             String lastName,
                                             int idCountry,
                                             int idProvince,
                                             int idCity,
                                             List<Integer> roles,
                                             String firstOrder,
                                             String secondOrder) throws Exception
  {
    return getBySearchParametersOrdered(firstName, lastName, null, null, idCountry, idProvince, idCity, roles, true, false, EnumUserStatus.Enabled.getValue(), null, firstOrder, secondOrder);
  }

  /**
   *
   * @param firstName filtro nome
   * @param lastName filtro cognome
   * @param minBirthDate filtro data nascita minima
   * @param maxBirthDate filtro data nascita massima
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @param roles filtro ruolo
   * @param marketEnabled "true" per ricerca "sul mercato"
   * @param searchNewsLetterEnabled "true" per ricerca iscritti newsletter
   * @param status filtro stato utente
   * @param permission filtro permessi backoffice
   * @return lista id utente secondo i parametri specificati ordinati per id
   * @throws Exception
   */
  public List<Integer> getBySearchParametersOrdered( String firstName,
                                                     String lastName,
                                                     Date minBirthDate,
                                                     Date maxBirthDate,
                                                     int idCountry,
                                                     int idProvince,
                                                     int idCity,
                                                     List<Integer> roles,
                                                     boolean marketEnabled,
                                                     boolean searchNewsLetterEnabled,
                                                     String status,
                                                     String permission,
                                                     String firstOrder,
                                                     String secondOrder) throws Exception
  {
    Criteria criteria = getSession().createCriteria(User.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
            add(Projections.id())));
    //criteria.add(Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()));
    if (firstName != null && firstName.length() > 0)
    {
      criteria.add(Restrictions.like("firstName", firstName + "%"));
    }
    if (lastName != null && lastName.length() > 0)
    {
      criteria.add(Restrictions.like("lastName", lastName + "%"));
    }
    if (minBirthDate != null)
    {
      criteria.add(Restrictions.le("birthDay", minBirthDate));
    }
    if (maxBirthDate != null)
    {
      criteria.add(Restrictions.ge("birthDay", maxBirthDate));
    }
    if (idCountry > 0)
    {
      criteria.add(Restrictions.eq("country.id", idCountry));
    }
    if (idProvince > 0)
    {
      criteria.add(Restrictions.eq("province.id", idProvince));
    }
    if (idCity > 0)
    {
      criteria.add(Restrictions.eq("city.id", idCity));
    }
    if (roles != null && roles.size() > 0)
    {
      criteria.createCriteria("playerRole").add(Restrictions.in("id", roles));
    }
    if (marketEnabled)
    {
      criteria.add(Restrictions.eq("marketEnabled", marketEnabled));
    }
    if (searchNewsLetterEnabled)
    {
      criteria.add(Restrictions.eq("newsletterEnabled", searchNewsLetterEnabled));
    }
    if (StringUtils.isNotEmpty(status))
    {
      criteria.add(Restrictions.eq("userStatus", status));
    }

    if (StringUtils.isNotBlank(permission))
    {
      List<String> permissionList = new ArrayList<String>();
      permissionList.add(permission);
      criteria.createCriteria("userPermissions").add(Restrictions.in("permission", permissionList));
    }
    if(StringUtils.isNotBlank(firstOrder) && StringUtils.isBlank(secondOrder))
    {
        criteria.addOrder(Order.desc(firstOrder));
    }
    else if(StringUtils.isNotBlank(secondOrder) && StringUtils.isBlank(firstOrder))
    {
        criteria.addOrder(Order.desc(secondOrder));
    }
    else if(StringUtils.isNotBlank(firstOrder) && StringUtils.isNotBlank(secondOrder))
    {
        criteria.addOrder(Order.asc(firstOrder)).addOrder(Order.desc(secondOrder));

    }
    return criteria.list();
  }


  /**
   *
   * @return solo User con userStatus Enabled
   * @throws Exception
   */
  public int getCount() throws Exception
  {
    Criteria criteria = getSession().createCriteria(User.class);
    criteria.setProjection(Projections.rowCount());
    criteria.add(Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()));
    return ((Integer) criteria.list().get(0)).intValue();
  }

  /**
   *
   * @return tutti gli User, di qualsiasi userStatus
   * @throws Exception
   */
  public int getAllCount(int idCountryFilter) throws Exception
  {
    Criteria criteria = getSession().createCriteria(User.class);
    if (idCountryFilter > 0)
    {
      criteria.add(Restrictions.eq("country.id", idCountryFilter));
    }
    criteria.setProjection(Projections.rowCount());
    return ((Integer) criteria.list().get(0)).intValue();
  }
  
  public int getAllCount() throws Exception
  {
    return getAllCount(0); 
  }

  /**
   *
   * @param howMany indicare quanti record restituire
   * @param noAnonymous "true" per escludere utenti anonimi
   * @return lista id utente con figurina in stato current
   * @throws Exception
   */
  public List<Integer> getAllWithCurrentPic(int howMany,boolean noAnonymous) throws Exception
  {
    Criteria crit = getSession().createCriteria(User.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()));

    if (noAnonymous)
      crit.add(Restrictions.eq("anonymousEnabled", false));

    crit.createCriteria("pictureCards")
            .add(Restrictions.eq("pictureCardStatus", EnumPictureCardStatus.Current.getValue()));

    crit.addOrder(Order.desc("created"));
    crit.setMaxResults(howMany);
    return crit.list();
  }

  /**
   *
   * @param howMany se valorizzato limita la lista restituita
   * @param matchOrganizedNum limite minimo di match organizzati
   * @return lista id utente secondo i filtri impostati con figurina attiva e non anonimi,
   * ordinati decrescentemente per Data pagellazione terzo match
   * @throws Exception
   */
  public List<Integer> getOrganizerWithCurrentPic(Integer howMany,int matchOrganizedNum) throws Exception
  {
    
    return getOrganizerByFilters(howMany,matchOrganizedNum,true,false);
  }

  /**
   *
   * @param howMany se valorizzato limita la lista restituita
   * @param matchOrganizedNum limite minimo di match organizzati
   * @param withPic filtro users con figurina
   * @param withAnonimous filtro users anche in stato anonimo
   * @return lista id utente secondo i filtri impostati, ordinati decrescentemente per Data pagellazione terzo match
   * @throws Exception
   */
  public List<Integer> getOrganizerByFilters(Integer howMany,int matchOrganizedNum,boolean withPic,boolean withAnonimous) throws Exception
  {
    Criteria crit = getSession().createCriteria(User.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()));
    crit.add(Restrictions.ge("recordedMatches", matchOrganizedNum));
    crit.add(Restrictions.eq("organizeEnabled", true));
    if(withPic)
    {
      crit.createCriteria("pictureCards").add(Restrictions.eq("pictureCardStatus", EnumPictureCardStatus.Current.getValue()));
    }
    if(withAnonimous)
    {
      crit.add(Restrictions.eq("anonymousEnabled", false));
    }
    if(howMany!=null && howMany>0)
    {
      crit.setMaxResults(howMany);
    }
    crit.addOrder(Order.desc("thirdOrganizedMatch"));
    return crit.list();
  }

  /**
   *
   * @return lista utenti con hibernate fetch in permission (backoffice)
   * @throws Exception
   */
  public List<User> getAllWithPermissions(int idCountryFilter) throws Exception
  {
    Criteria crit = getSession().createCriteria(User.class);
    if (idCountryFilter > 0)
    {
      crit.add(Restrictions.eq("country.id", idCountryFilter));
    }
    
    crit.setFetchMode("userPermissions", FetchMode.JOIN);
    crit.setFetchMode("playerRole", FetchMode.JOIN);
    
    
    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    crit.addOrder(Order.desc("created"));
    return crit.list();
  }

  /**
   *
   * @return lista utenti attivi
   * @throws Exception
   */
  public List<User> getAllEnabled() throws Exception
  {
    Criteria crit = getSession().createCriteria(User.class);
    crit.add(Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()));
    return crit.list();
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return utente con hibernate fetch sui campi utili (backoffice)
   * @throws Exception
   */
  public User getFetched(int idUser) throws Exception
  {
    Criteria crit = getSession().createCriteria(User.class);
    crit.add(Restrictions.eq("id", idUser));
    crit.setFetchMode("country", FetchMode.JOIN);
    crit.setFetchMode("province", FetchMode.JOIN);
    crit.setFetchMode("city", FetchMode.JOIN);
    crit.setFetchMode("playerRole", FetchMode.JOIN);
    crit.setFetchMode("squads", FetchMode.JOIN);
    crit.setFetchMode("pictureCards", FetchMode.JOIN);
    crit.setFetchMode("playerFoot", FetchMode.JOIN);
    crit.setFetchMode("physicalCondition", FetchMode.JOIN);
    crit.setFetchMode("footballTeam", FetchMode.JOIN);
    return (User) crit.uniqueResult();
  }

  /**
   *
   * @return lista utenti ordinati per lastLogin Desc
   * @throws Exception
   */
  public List<User> findOrderByLastLogin()throws Exception
  {
    return findOrderBy(Order.desc("lastLogin"));
  }

  /**
   *
   * @return lista utenti ordinati per data creazione/id Desc
   * @throws Exception
   */
  public List<User> findOrderByCreated()throws Exception
  {
    return findOrderBy(Order.desc("id"));
  }

  /**
   *
   * @param idSquad
   * @return lista di utenti di una squadra con abilitazione notifica apertura registrazioni partite amici
   */
  public List<User> getAllFriendsToNotifyMatchStart(int idSquad)
  {
    Criteria criteria = getSession().createCriteria(User.class);
    criteria.createAlias("squads", "s");
    criteria.createAlias("s.userSquadList", "usl");
    criteria.setProjection(Projections.property("usl.user"));
    criteria.add(Restrictions.eq("usl.squad.id", idSquad));
    criteria.add(Restrictions.eq("usl.userSquadStatus", EnumUserSquadStatus.Confirmed.getValue()));
    criteria.add(Restrictions.eq("usl.owner", false));
    return criteria.list();
  }

  /**
   *
   * @param idSquad
   * @param name stringa filtro per il nome o cognome
   * @param howMany quanti risultati ottenere
   * @return lista di utenti di una squadra filtrati e ordinati per nome
   */
  public List<Integer> getIdFriends(int idSquad,String name,int howMany)
  {
    name=name.trim();
    String[] splitName=StringUtils.split(name, " ");
    
    boolean allName=false;
    if (splitName.length > 1)
    {
      allName=true;
      name="";
      for(String split : splitName)
      {
        name+=split + " ";
      }
      name=(name.replace("'", "''")).trim();
    }
    
    Criteria crit = getSession().createCriteria(User.class);
    crit.createAlias("squads", "s");
    crit.createAlias("s.userSquadList", "usl");
    crit.createAlias("usl.user", "uslUser");
    crit.setProjection(Projections.property("usl.user.id"));
    crit.add(Restrictions.eq("usl.squad.id", idSquad));
    crit.add(Restrictions.eq("usl.userSquadStatus", EnumUserSquadStatus.Confirmed.getValue()));

    if(allName)
    {
      Criterion one =     Restrictions.sqlRestriction(" concat( {alias}.FIRST_NAME, ' ', {alias}.LAST_NAME) like '"+name +"%' ");
      Criterion two =     Restrictions.sqlRestriction(" concat( {alias}.LAST_NAME, ' ', {alias}.FIRST_NAME) like '"+name +"%' ");
      Criterion three =     Restrictions.sqlRestriction(" {alias}.LAST_NAME like '"+name +"%' ");
      Disjunction disjunction = Restrictions.disjunction();
      disjunction.add(one);
      disjunction.add(two);
      disjunction.add(three);
      crit.add(disjunction);
    }
    else
    {
      crit.add(Restrictions.or(Restrictions.ilike("uslUser.firstName",name,MatchMode.START), Restrictions.ilike("uslUser.lastName",name,MatchMode.START)));
    }

    crit.add(Restrictions.eq("uslUser.userStatus", EnumUserStatus.Enabled.getValue()));
    //crit.add(Restrictions.eq("uslUser.marketEnabled", true));
    crit.addOrder(Order.asc("uslUser.firstName")).addOrder(Order.asc("uslUser.lastName"));
    crit.setMaxResults(howMany);
    return crit.list();

  }

  /**
   *
   * @param idFriends lista amici dell'utente
   * @param idProvince filtro provincia
   * @param exludeProvince filtro esclude provincia indicata
   * @param idCountry filtro nazione
   * @param exludeCountry filtro esclude nazione indicata
   * @param name stringa filtro per il nome o cognome
   * @param howMany quanti risultati ottenere
   * @return lista di utenti di una squadra filtrati e ordinati per nome
   */
  public List<Integer> getIdNotFriends(List<Integer> idFriends,String name,int howMany,int idProvince,boolean excludeProvince,int idCountry,boolean excludeCountry)
  {
    name=name.trim();
    String[] splitName=StringUtils.split(name, " ");
    boolean allName=false;
    if (splitName.length > 1)
    {
      allName=true;
      name="";
      for(String split : splitName)
      {
        name+=split + " ";
      }
      name=(name.replace("'", "''")).trim();
    }

    Criteria crit = getSession().createCriteria(User.class);
    crit.setProjection(Projections.id());
    //crit.add(Restrictions.eq("marketEnabled", true));
    crit.add(Restrictions.eq("userStatus", EnumUserStatus.Enabled.getValue()));
    if (excludeProvince)
      crit.add(Restrictions.ne("province.id", idProvince));
    else
      crit.add(Restrictions.eq("province.id", idProvince));
    if (excludeCountry)
      crit.add(Restrictions.ne("country.id", idCountry));
    else
      crit.add(Restrictions.eq("country.id", idCountry));
    if (idFriends.size()>0)
      crit.add(Restrictions.not(Restrictions.in("id",idFriends )));

    if(allName)
    {

      Criterion one =     Restrictions.sqlRestriction(" concat( {alias}.FIRST_NAME, ' ', {alias}.LAST_NAME) like '"+name +"%' ");
      Criterion two =     Restrictions.sqlRestriction(" concat( {alias}.LAST_NAME, ' ', {alias}.FIRST_NAME) like '"+name +"%' ");
      Criterion three =     Restrictions.sqlRestriction(" {alias}.LAST_NAME like '"+name +"%' ");
      Disjunction disjunction = Restrictions.disjunction();
      disjunction.add(one);
      disjunction.add(two);
      disjunction.add(three);
      crit.add(disjunction);
    }
    else
    {
      crit.add(Restrictions.or(Restrictions.ilike("firstName",name,MatchMode.START), Restrictions.ilike("lastName",name,MatchMode.START)));
    }
    crit.addOrder(Order.asc("firstName")).addOrder(Order.asc("lastName"));
    crit.setMaxResults(howMany);
    return crit.list();

  }

  /**
   *
   * @param idUserList lista id utenti da cercare
   * @return lista utenti relativi alla lista id utenti passata
   */
  public List<User> getByIdUserList(List<Integer> idUserList)
  {
    Criteria criteria = getSession().createCriteria(User.class);
    criteria.add(Restrictions.in("id", idUserList));
    criteria.addOrder(Order.desc("email"));
    return criteria.list();
  }



    public List<Object[]> getIdUserNotInMatch(int idMatch,String name,int idOwnerSquad)
    {
    String sql=" (SELECT DISTINCT USERS.ID_USER, USERS.FIRST_NAME AS name " +
                        " FROM USERS " +
                        "       LEFT JOIN PLAYERS " +
                          "        ON PLAYERS.ID_USER = USERS.ID_USER " +
                            "   LEFT JOIN PLAYERS_TO_MATCHES " +
                              "    ON PLAYERS_TO_MATCHES.ID_PLAYER = :idMatch " +
                              " LEFT JOIN USERS_SQUADS " +
                                "  ON USERS_SQUADS.ID_USER = USERS.ID_USER " +
                        " WHERE (USERS.FIRST_NAME LIKE CONCAT(:name, '%') " +
                          "      OR USERS.LAST_NAME LIKE CONCAT(:name, '%')) " +
                            "   AND PLAYERS_TO_MATCHES.ID_MATCH NOT IN " +
                              "        (SELECT ID_MATCH " +
                                "         FROM PLAYERS_TO_MATCHES " +
                                  "      WHERE ID_PLAYER IN (SELECT ID_PLAYER " +
                                    "                          FROM PLAYERS " +
                                      "                       WHERE PLAYERS.ID_USER = USERS.ID_USER)) " +
                              " AND (USERS.USER_STATUS = 'ENABLED') " +
                              " AND (USERS_SQUADS.ID_SQUAD = :idOwnerSquad " +
                                "    AND USERS_SQUADS.USER_SQUAD_STATUS = 'CONFIRMED')) " +
                      " UNION " +
                      " (SELECT DISTINCT USERS.ID_USER, USERS.FIRST_NAME AS name " +
                        "  FROM USERS " +
                          "     LEFT JOIN PLAYERS " +
                            "      ON PLAYERS.ID_USER = USERS.ID_USER " +
                              " LEFT JOIN PLAYERS_TO_MATCHES " +
                                "  ON PLAYERS_TO_MATCHES.ID_PLAYER = PLAYERS.ID_PLAYER " +
                              " LEFT JOIN USERS_SQUADS " +
                                "  ON USERS_SQUADS.ID_USER = USERS.ID_USER " +
                        " WHERE (USERS.FIRST_NAME LIKE CONCAT(:name, '%') " +
                          "      OR USERS.LAST_NAME LIKE CONCAT(:name, '%')) " +
                            "   AND (USERS_SQUADS.ID_SQUAD = :idOwnerSquad " +
                              "      AND USERS_SQUADS.USER_SQUAD_STATUS = 'CONFIRMED') " +
                              " AND (PLAYERS_TO_MATCHES.ID_MATCH = :idMatch ) " +
                              " AND (   PLAYERS.PLAYER_STATUS = 'USER_CALLED' " +
                                "    OR PLAYERS.PLAYER_STATUS = 'USER_REQUEST' " +
                                  "  OR PLAYERS.PLAYER_TYPE = 'MISSING')) " +
                      " ORDER BY name ASC " +
                      "  LIMIT 5;" ;

    Query query = getSession().createSQLQuery(sql);
                              query.setParameter("idMatch", idMatch);
                              query.setParameter("name", name);
                              query.setParameter("idOwnerSquad", idOwnerSquad);
    return query.list();
    }


  public User getUserByFacebookIdUser(String facebookIdUser) throws Exception
  {
    return findEntityByCriteria(Restrictions.eq("facebookIdUser", facebookIdUser));
}

  public User getUserByEmail(String email) throws Exception
  {
    return findEntityByCriteria(Restrictions.eq("email", email));
  }
}
