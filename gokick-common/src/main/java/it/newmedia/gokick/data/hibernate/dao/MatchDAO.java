package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Match;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

/**
 *
 * DAO per la gestione delle partite. Contiene tutti i metodi che lavorano sulla tabella match.
 */
public class MatchDAO extends AGenericDAO<Match, Integer>
{

  /**
   *
   * @param session
   */
  public MatchDAO(Session session)
  {
    super(session);
  }

  /**
   * Return la lista degli id degli utenti appartenenti ad una partita
   * @param idMatch id della partita
   * @return la lista degli id degli utenti appartenenti
   * @throws Exception
   */
  public List getAllRegisteredPlayerByIdMatch(int idMatch) throws Exception
  {
    String sql = "SELECT pl.user.id FROM Player AS pl WHERE pl.match.id = :idMatch " +
            "AND pl.playerStatus = :playerMatchStatus1 " +
            "AND pl.playerStatus = :playerMatchStatus2 " +
            "AND pl.playerStatus = :playerMatchStatus3 " +
            "ORDER BY  pl.user.lastName ASC";   //us.owner DESC,
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idMatch", idMatch);
    query.setParameter("playerMatchStatus1", EnumPlayerStatus.OwnerRegistered.getValue());
    query.setParameter("playerMatchStatus2", EnumPlayerStatus.UserRegistered.getValue());
    query.setParameter("playerMatchStatus3", EnumPlayerStatus.UserRequestRegistered.getValue());
    return query.list();
  }

  /**
   *
   * @param idUserOwner utente d'interesse
   * @return numero partite organizzate dall'utente
   * @throws Exception
   */
  public int getCountByIdUser(int idUserOwner) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Match.class);
    criteria.setProjection(Projections.rowCount());
    criteria.add(Restrictions.eq("idUserOwner", idUserOwner));
    return ((Integer)criteria.list().get(0)).intValue();
  }

  /**
   *
   * @param idUserOwner utente d'interesse
   * @return lista partite organizzate dall'utente
   * @throws Exception
   */
  public List<Match> getAllByIdUserOwner(int idUserOwner) throws Exception
  {
    String sql = " FROM Match AS s WHERE s.idUserOwner = :idUserOwner";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idUserOwner", idUserOwner);
    return (List<Match>) query.list();
  }
  
  /**
   *
   * @param onlyFuture "true" per match futuri
   * @param onlyPaste "true" per match passati
   * @param onlyAlert
   * @return tutti i match
   * @throws Exception
   */
  public int getAllCount(boolean onlyFuture,boolean onlyPaste,boolean onlyAlert,int idCountryFilter) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Match.class);
    if (idCountryFilter > 0)
    {
      criteria.createCriteria("sportCenter").add(Restrictions.eq("country.id", idCountryFilter));
    }
    criteria.setProjection(Projections.rowCount());
    if (onlyFuture)
      criteria.add(Restrictions.ge("matchStart", new Date()));
    if (onlyPaste)
      criteria.add(Restrictions.lt("matchStart", new Date()));
    if (onlyAlert)
    {
      //TODO - match con segnalazioni
    }
    return ((Integer) criteria.list().get(0)).intValue();
  }
  
  public int getAllCount(boolean onlyFuture,boolean onlyPaste,boolean onlyAlert) throws Exception
  {
    
    return getAllCount(onlyFuture,onlyPaste,onlyAlert,0);
  }

  /**
   * Return la lista di tutte le partite giocabili
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getAllPlayable() throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.add(Restrictions.eq("canceled", false));
    return crit.list();
  }

  /**
   * Return la lista degli ID di tutte le partite
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getAll() throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    return crit.list();
  }
  
  /**
   *
   * @param idUser utente d'interesse
   * @return lista id match in cui l'utente è organizzatore o pagellatore
   * @throws Exception
   */
  public List<Integer> getOwnerOrReporter(int idUser) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.distinct(Projections.id()));
    crit.createCriteria("playerList","pl");

    SimpleExpression exp1 =Restrictions.eq("userOwner.id", idUser);
    LogicalExpression exp2 =Restrictions.and(Restrictions.eq("pl.user.id", idUser),
                                              Restrictions.eq("pl.reporterEnabled", true));

    crit.add(Restrictions.or(exp1, exp2));
    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                                     
    return crit.list();
  }

  /**
   *
   * @param idProvince provincia d'interesse
   * @param friendsIdList lista amici utente d'interesse
   * @return lista id match delle partite giocabili(consigliate) dall'utente
   * @throws Exception
   */
  public List<Integer> getAllVeryPlayable(int idProvince, List friendsIdList) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.add(Restrictions.eq("canceled", false));

    LogicalExpression exp1 = Restrictions.and(Restrictions.isNotNull("registrationStart"),
                              Restrictions.le("registrationStart", new Date()));

    LogicalExpression exp2 = Restrictions.and(Restrictions.isNull("registrationStart"),
                              Restrictions.eq("registrationClosed", false));
    
    crit.add(Restrictions.or(exp1, exp2));
    crit.createCriteria("sportCenter").add(Restrictions.eq("province.id", idProvince));

    Criterion ownerFriends = Restrictions.in("userOwner.id", friendsIdList);
    Criterion publicMatch = Restrictions.eq("squadOutEnabled", true);

    LogicalExpression orExp = Restrictions.or(ownerFriends,publicMatch);
    crit.add(orExp);

    return crit.list();
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return id Match prossima partita giocata dall'utente
   * @throws Exception
   */
  public Integer getNextOne(int idUser) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.add(Restrictions.eq("canceled", false));
    crit.createCriteria("playerList").add(Restrictions.eq("user.id", idUser))
                                     .add(Restrictions.ne("playerStatus", EnumPlayerStatus.UserCalled.getValue()))
                                     .add(Restrictions.ne("playerStatus", EnumPlayerStatus.UserRequest.getValue()))
                                     .add(Restrictions.ne("playerStatus", EnumPlayerStatus.Undefined.getValue()));

            ;
    crit.addOrder(Order.asc("matchStart"));
    return (crit.list().size() > 0 ? (Integer)crit.list().get(0) : 0 );
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return id Match prossima partita giocata dall'utente
   * @throws Exception
   */
  public Integer getByOrganizedDatePosition(int idUser,int position) throws Exception
  {
    List<Integer> idMatchList = new ArrayList<Integer>();
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ne("canceled", true));
    crit.add(Restrictions.eq("recorded", true));
    crit.add(Restrictions.eq("userOwner.id", idUser));
    crit.setMaxResults(position);
    crit.addOrder(Order.asc("recordedDate"));
    idMatchList= (List<Integer>) crit.list();
    if (idMatchList.size()>0)
    {
      return idMatchList.get(position - 1);
    }
    return 0;
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return id match dell'ultima partita archiviata visualizzabile dall'utente
   * @throws Exception
   */
  public Integer getLastValidResult(int idUser) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.le("matchStart", new Date()));
    crit.add(Restrictions.ne("canceled", true));
    crit.add(Restrictions.eq("recorded", true));
    crit.createCriteria("playerList").add(Restrictions.eq("user.id", idUser))
                                     .add(Restrictions.ne("playerStatus", EnumPlayerStatus.UserCalled.getValue()))
                                     .add(Restrictions.or(Restrictions.ne("playerStatus", EnumPlayerStatus.UserRequestRegistered.getValue()),
                                                          Restrictions.ne("playerStatus", EnumPlayerStatus.UserRequest.getValue())));

    crit.addOrder(Order.desc("recordedDate"));
    return (crit.list().size() > 0 ? (Integer)crit.list().get(0) : 0 );
    
  }

  /**
   * Return la lista delle partite a cui è convocato un utente organizzate da lui stesso e dai suoi amici
   * @param friendsIdList lista degli id degli amici
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getFriendsConvened(List friendsIdList) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.add(Restrictions.in("userOwner.id", friendsIdList));
    return crit.list();
  }
  
  /**
   * Return la lista delle partite a cui è convocato un utente come fuori rosa
   * @param friendsIdList lista degli id degli amici
   * @param idProvince id della provincia in cui l'utente gioca a calcio
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getSquadOutConvened(List friendsIdList, int idProvince) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.add(Restrictions.not(Restrictions.in("userOwner.id", friendsIdList)));
    crit.createCriteria("sportCenter").add(Restrictions.eq("province.id", idProvince));
    crit.add(Restrictions.eq("squadOutEnabled", true));
    return crit.list();
  }

  /**
   *
   * @param friendsIdList lista amici utente d'interesse
   * @param idProvince provincia d'interesse
   * @param idUser user d'interesse
   * @return lista id partite future pubbliche di non amici, cui l'utente è stato convocato
   * @throws Exception
   */
  public List<Integer> getNotSquadOutConvened(List friendsIdList, int idProvince,int idUser) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.add(Restrictions.not(Restrictions.in("userOwner.id", friendsIdList)));
    crit.createCriteria("sportCenter").add(Restrictions.eq("province.id", idProvince));
    crit.createCriteria("playerList").add(Restrictions.eq("user.id", idUser));
    crit.add(Restrictions.eq("squadOutEnabled", false));
    return crit.list();
  }

  /**
   *
   * @param idUser user da controllare
   * @return il num di match futuri in cui l'utente è registrato o ha fatto richiesta
   * @throws Exception
   */
  public Integer getCountWhereUserInPlayerList(int idUser) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.rowCount());
    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.createCriteria("playerList").add(Restrictions.eq("user.id", idUser))
                                     .add(Restrictions.ne("playerType", EnumPlayerType.Missing.getValue()));
    crit.add(Restrictions.ne("userOwner.id", idUser));
    crit.add(Restrictions.eq("canceled", false));
    return ((Integer)crit.list().get(0)).intValue();
  }

  /**
   * Return la lista delle partite organizzate dagli amici di un utente
   * @param friendsIdList lista degli id degli amici
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getFriends(List friendsIdList) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.add(Restrictions.in("userOwner.id", friendsIdList));
    crit.add(Restrictions.eq("canceled", false));
    return crit.list();
  }

  /**
   * Return la lista delle partite a cui si è iscritto un utente
   * @param idCurrentUser id utente corrente
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getRegistered(int idCurrentUser) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.createCriteria("playerList","pl").add(Restrictions.eq("user.id", idCurrentUser));

    Criterion one =     Restrictions.eq("pl.playerStatus", EnumPlayerStatus.OwnerRegistered.getValue());
    Criterion two =     Restrictions.eq("pl.playerStatus", EnumPlayerStatus.UserRegistered.getValue());
    Criterion three =   Restrictions.eq("pl.playerStatus", EnumPlayerStatus.UserRequestRegistered.getValue());

    Disjunction disjunction = Restrictions.disjunction();
    disjunction.add(one);
    disjunction.add(two);
    disjunction.add(three);
    crit.add(disjunction);


    crit.add(Restrictions.eq("canceled", false));
    crit.addOrder(Order.asc("matchStart"));
    return crit.list();
  }

  /**
   * Return la lista delle partite che hai giocato
   * @param idCurrentUser id utente corrente
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getPlayed(int idCurrentUser) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.le("matchStart", new Date()));
    crit.createCriteria("playerList").add(Restrictions.eq("user.id", idCurrentUser))
                                     .add(Restrictions.ne("playerType", EnumPlayerType.Missing.getValue()))//aggiunto per corergere conteggio partite giocate
                                     .add(Restrictions.ne("playerStatus", EnumPlayerStatus.UserCalled.getValue()));//aggiunto per corergere conteggio partite giocate
  
    crit.add(Restrictions.eq("canceled", false));
    crit.add(Restrictions.eq("recorded", true));//aggiunto per corregere conteggio partite giocate
    crit.addOrder(Order.desc("matchStart"));
    return crit.list();
  }

  /**
   * Return la lista delle partite organizzate da un utente
   * @param idUserOwner id utente proprietario
   * @return lista degli id delle partite organizzate da un utente
   * @throws Exception
   */
  public List<Integer> getOrganized(int idUserOwner) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.add(Restrictions.eq("userOwner.id", idUserOwner));
    crit.add(Restrictions.eq("canceled", false));
    crit.addOrder(Order.asc("matchStart"));
    return crit.list();
  }
  
  /**
   * Return la lista delle partite organizzate e archiviate da un utente
   * @param idUserOwner id utente proprietario
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getOrganizedRecorded(int idUserOwner) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.le("matchStart", new Date()));
    crit.add(Restrictions.eq("userOwner.id", idUserOwner));
    crit.add(Restrictions.eq("canceled", false));
    crit.addOrder(Order.desc("matchStart"));
    return crit.list();
  }

  /**
   * Return la lista delle partite organizzate da un utente in un certo periodo
   * @param idUserOwner id utente proprietario
   * @param startDate data di inizio del periodo
   * @param endDate data di fine del periodo
   * @return lista degli id delle partite organizzate da un utente in un certo periodo
   * @throws Exception
   */
  public List<Integer> getOrganizedByPeriod(int idUserOwner, Date startDate, Date endDate) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.ge("matchStart", startDate));
    crit.add(Restrictions.le("matchStart", endDate));
    crit.add(Restrictions.eq("userOwner.id", idUserOwner));
    crit.add(Restrictions.eq("canceled", false));
    crit.addOrder(Order.asc("matchStart"));
    return crit.list();
  }

  /**
   * Return la lista delle partite non archiviate da un utente
   * @param idUserOwner id utente proprietario
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getNotRecorded(int idUserOwner) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.distinct(Projections.id()));
    crit.add(Restrictions.le("matchStart", new Date()));
    crit.createAlias("playerList", "pl", Criteria.LEFT_JOIN);
    Criterion ownerFriends = Restrictions.eq("userOwner.id", idUserOwner);
    Criterion reporterId = Restrictions.eq("pl.user.id", idUserOwner);
    Criterion reporterStatus = Restrictions.eq("pl.reporterEnabled", true);
    LogicalExpression andExp = Restrictions.and(reporterId, reporterStatus);
    LogicalExpression orExp = Restrictions.or(ownerFriends, andExp);
    crit.add(orExp);
    crit.add(Restrictions.eq("recorded", false));
    crit.add(Restrictions.eq("canceled", false));
    crit.addOrder(Order.asc("matchStart"));
    return crit.list();
  }

  /**
   * Return la lista delle partite archiviate da un utente in un certo periodo
   * @param idUserOwner id utente proprietario
   * @param startDate data di inizio del periodo
   * @param endDate data di fine del periodo
   * @return lista degli id delle partite archiviate da un utente in un certo periodo
   * @throws Exception
   */
  public List<Integer> getRecordedByPeriod(int idUserOwner, Date startDate, Date endDate) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    if( startDate != null )
      crit.add(Restrictions.ge("matchStart", startDate));
    if( endDate != null )
      crit.add(Restrictions.le("matchStart", endDate));
    crit.add(Restrictions.eq("recorded", true));
    crit.add(Restrictions.eq("userOwner.id", idUserOwner));
    crit.add(Restrictions.eq("canceled", false));
    crit.addOrder(Order.asc("matchStart"));
    return crit.list();
  }

  public List<Integer> getRecorded() throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.eq("recorded", true));
    crit.add(Restrictions.eq("canceled", false));
    return crit.list();
  }

  /**
   * Return la lista delle partite archiviate in base ai filtri di ricerca
   * @param idCountry id nazione
   * @param idProvince id provincia
   * @param idCity id città
   * @param idSportCenter id del centro sportivo
   * @param friendsIdList lista degli id degli amici
   * @param idCurrentUser id dell'utente corrente
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getRecordedBySearchParameters(int idCountry,
          int idProvince,
          int idCity,
          int idSportCenter,
          int idCurrentUser,
          List friendsIdList
          ) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.le("matchStart", new Date()));
    //crit.add(Restrictions.eq("canceled", false));
    if (idCity > 0)
    {
      crit.createCriteria("sportCenter").add(Restrictions.eq("city.id", idCity));
    }
    else if (idProvince > 0)
    {
      crit.createCriteria("sportCenter").add(Restrictions.eq("province.id", idProvince));
    }
    else if (idCountry > 0)
    {
      crit.createCriteria("sportCenter").add(Restrictions.eq("country.id", idCountry));
    }
    if (idSportCenter > 0)
    {
      crit.add(Restrictions.eq("sportCenter.id", idSportCenter));
    }
    //TODO: è corretto questo?? ..da rivedere....
    Criterion ownerFriends = Restrictions.in("userOwner.id", friendsIdList);
    Criterion publicMatch = Restrictions.eq("squadOutEnabled", true);
    
    LogicalExpression orExp = Restrictions.or(ownerFriends,publicMatch);
    crit.add(orExp);
    crit.addOrder(Order.desc("matchStart"));
    return crit.list();
  }

  /**
   *
   * @param idCountry nazione d'interesse
   * @param idProvince provincia d'interesse
   * @param idCity città d'interesse
   * @param idSportCenter centro sportivo d'interesse
   * @param idCurrentUser utente d'interesse
   * @param friendsIdList id utente amici 
   * @return lista id match partite private passate di non amici delle quali l'utente è giocatore, secondo parametri specificati
   * @throws Exception
   */
  public List<Integer> getRecordedPrivateBySearchParameters(int idCountry,
          int idProvince,
          int idCity,
          int idSportCenter,
          int idCurrentUser,
          List friendsIdList
          ) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.le("matchStart", new Date()));
    crit.add(Restrictions.eq("canceled", false));
    if (idCity > 0)
    {
      crit.createCriteria("sportCenter").add(Restrictions.eq("city.id", idCity));
    }
    else if (idProvince > 0)
    {
      crit.createCriteria("sportCenter").add(Restrictions.eq("province.id", idProvince));
    }
    else if (idCountry > 0)
    {
      crit.createCriteria("sportCenter").add(Restrictions.eq("country.id", idCountry));
    }
    if (idSportCenter > 0)
    {
      crit.add(Restrictions.eq("sportCenter.id", idSportCenter));
    }
    
    crit.createCriteria("playerList").add(Restrictions.eq("user.id", idCurrentUser));
    crit.add(Restrictions.eq("squadOutEnabled", false));
    crit.add(Restrictions.not(Restrictions.in("userOwner.id", friendsIdList)));

    crit.addOrder(Order.desc("matchStart"));
    return crit.list();
  }

  /**
   * Return la lista delle partite in base ai filtri di ricerca
   * @param idCountry id nazione
   * @param idProvince id provincia
   * @param idCity id città
   * @param idSportCenter id del centro sportivo
   * @param idCurrentUser Id dell'utente che effettua la ricerca
   * @param friendsIdList lista degli id degli amici
   * @param withSuggested Indica se devono essere incluse le partite suggerite (quelle degli amici senza vincolo geografico)
   * @param recorded    Indica se si ricercano partite archiviate 
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getBySearchParameters(int idCountry,
          int idProvince,
          int idCity,
          int idSportCenter,
          int idCurrentUser,
          List friendsIdList,
          boolean withSuggested,
          boolean recorded) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.distinct(Projections.id()));

    Criterion geo = null;
    if (idSportCenter > 0)
    {
      geo = Restrictions.eq("sportCenter.id", idSportCenter);
    }
    else
    {
      crit.createCriteria("sportCenter");
      if (idCity > 0)
      {
        crit.createAlias("sportCenter.city", "sportCenterGeo");
        geo = Restrictions.eq("sportCenterGeo.id", idCity);
      }
      else if (idProvince > 0)
      {
        crit.createAlias("sportCenter.province", "sportCenterGeo");
        geo = Restrictions.eq("sportCenterGeo.id", idProvince);
      }
      else if (idCountry > 0)
      {
        crit.createAlias("sportCenter.country", "sportCenterGeo");
        geo = Restrictions.eq("sportCenterGeo.id", idCountry);
      }
    }

    Disjunction nestedOrCondition = Restrictions.disjunction();

    //Tutte le partite giocate dall'utente!
    crit.createCriteria("playerList", Criteria.LEFT_JOIN);
    crit.createAlias("playerList.user", "playerListUser", Criteria.LEFT_JOIN);
    Criterion userPlayed = null;
    if( withSuggested )
    {
      userPlayed = Restrictions.eq("playerListUser.id", idCurrentUser);
    }
    else
    {
      userPlayed = Restrictions.conjunction().add(geo).add(Restrictions.eq("playerListUser.id", idCurrentUser));
    }
    nestedOrCondition.add(userPlayed);

    //Tutte le partite pubbliche!
    Criterion matchPublic = Restrictions.conjunction().add(geo).add(Restrictions.eq("squadOutEnabled", true));
    nestedOrCondition.add(matchPublic);

    //Tutte le partite degli amici!
    Criterion matchFriends = null;
    if( withSuggested )
    {
      matchFriends = Restrictions.in("userOwner.id", friendsIdList);
    }
    else
    {
      matchFriends = Restrictions.conjunction().add(geo).add(Restrictions.in("userOwner.id", friendsIdList));
    }
    nestedOrCondition.add(matchFriends);

    Conjunction mainAndCondition = Restrictions.conjunction();
    if (recorded)
      mainAndCondition.add(Restrictions.le("matchStart", new Date()));
    else
      mainAndCondition.add(Restrictions.ge("matchStart", new Date()));

    mainAndCondition.add(nestedOrCondition);

    crit.add(mainAndCondition);

    if (recorded)
      crit.addOrder(Order.desc("matchStart"));
    else
      crit.addOrder(Order.asc("matchStart"));

    return crit.list();
  }

  /**
   * Return la lista delle partite in base ai filtri di ricerca
   * @param idCountry id nazione
   * @param idProvince id provincia
   * @param idCity id città
   * @param idSportCenter id del centro sportivo
   * @param idCurrentUser Id dell'utente che effettua la ricerca
   * @param friendsIdList lista degli id degli amici
   * @param withSuggested Indica se devono essere incluse le partite suggerite (quelle degli amici senza vincolo geografico)
   * @param recorded    Indica se si ricercano partite archiviate
   * @return lista degli id delle partite
   * @throws Exception
   */
  public List<Integer> getBySearchParameters(int idCountry,
          int idProvince,
          int idCity,
          int idSportCenter,
          int idCurrentUser,
          List friendsIdList,
          Date end,
          boolean withSuggested,
          boolean recorded) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.distinct(Projections.id()));

    Criterion geo = null;
    if (idSportCenter > 0)
    {
      geo = Restrictions.eq("sportCenter.id", idSportCenter);
    }
    else
    {
      crit.createCriteria("sportCenter");
      if (idCity > 0)
      {
        crit.createAlias("sportCenter.city", "sportCenterGeo");
        geo = Restrictions.eq("sportCenterGeo.id", idCity);
      }
      else if (idProvince > 0)
      {
        crit.createAlias("sportCenter.province", "sportCenterGeo");
        geo = Restrictions.eq("sportCenterGeo.id", idProvince);
      }
      else if (idCountry > 0)
      {
        crit.createAlias("sportCenter.country", "sportCenterGeo");
        geo = Restrictions.eq("sportCenterGeo.id", idCountry);
      }
    }

    Disjunction nestedOrCondition = Restrictions.disjunction();

    //Tutte le partite giocate dall'utente!
    crit.createCriteria("playerList", Criteria.LEFT_JOIN);
    crit.createAlias("playerList.user", "playerListUser", Criteria.LEFT_JOIN);
    Criterion userPlayed = null;
    if( withSuggested )
    {
      userPlayed = Restrictions.eq("playerListUser.id", idCurrentUser);
    }
    else
    {
      userPlayed = Restrictions.conjunction().add(geo).add(Restrictions.eq("playerListUser.id", idCurrentUser));
    }
    nestedOrCondition.add(userPlayed);

    //Tutte le partite pubbliche!
    Criterion matchPublic = Restrictions.conjunction().add(geo).add(Restrictions.eq("squadOutEnabled", true));
    nestedOrCondition.add(matchPublic);

    //Tutte le partite degli amici!
    Criterion matchFriends = null;
    if( withSuggested )
    {
      matchFriends = Restrictions.in("userOwner.id", friendsIdList);
    }
    else
    {
      matchFriends = Restrictions.conjunction().add(geo).add(Restrictions.in("userOwner.id", friendsIdList));
    }
    nestedOrCondition.add(matchFriends);

    Conjunction mainAndCondition = Restrictions.conjunction();
    if (recorded)
      mainAndCondition.add(Restrictions.le("matchStart", new Date()));
    else
      mainAndCondition.add(Restrictions.ge("matchStart", new Date()));

    mainAndCondition.add(nestedOrCondition);

    crit.add(mainAndCondition);
    
    crit.add(Restrictions.ge("matchStart", end));

    if (recorded)
      crit.addOrder(Order.desc("matchStart"));
    else
      crit.addOrder(Order.asc("matchStart"));

    return crit.list();
  }

  /**
   *
   * @param idUserOwner utente d'interesse
   * @param startDate inizio ricerca
   * @param endDate fine ricerca
   * @return lista id partite in cui l'organizzatore non è giocatore,con data match nel periodo specificato
   * @throws Exception
   */
  public List<Integer> getWithOwnerNotPlayer(int idUserOwner,Date startDate,Date endDate) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.distinct(Projections.id()));
    crit.add(Restrictions.between("matchStart", startDate, endDate));
    crit.add(Restrictions.eq("recorded", true));
    return crit.list();
  }

  /**
   *
   * @param idUserOwner utente d'interesse
   * @param startDate inizio ricerca
   * @param endDate fine ricerca
   * @return numero partite in cui l'organizzatore non è giocatore,con data match nel periodo specificato
   * @throws Exception
   */
  public int countOwnerMatchPlayer(int idUserOwner,Date startDate,Date endDate) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.rowCount());
    crit.add(Restrictions.eq("userOwner.id", idUserOwner));
    crit.add(Restrictions.eq("recorded", true));
    crit.add(Restrictions.between("matchStart", startDate, endDate));
    crit.createCriteria("playerList").add(Restrictions.eq("user.id", idUserOwner));//.add(Restrictions.ne("playerType", EnumPlayerType.Missing.getValue()));
    return ((Integer)crit.list().get(0)).intValue();
  }

  public int countOwnerMatch(int idUserOwner,Date startDate,Date endDate) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    crit.setProjection(Projections.rowCount());
    crit.add(Restrictions.eq("userOwner.id", idUserOwner));
    crit.add(Restrictions.eq("recorded", true));
    crit.add(Restrictions.between("matchStart", startDate, endDate));
    return ((Integer)crit.list().get(0)).intValue();
  }



  /**
   *
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @param idSportCenter filtro centro sportivo
   * @param onlyPastMatch "true" per  match passati
   * @param onlyFutureMatch "true" per match futuri
   * @param onlyCanceledMatch "true" per match annullati
   * @return lista match secondo i parametri specificati, ordinata per data inizio partita
   * @throws Exception
   */
  public List<Match> getMatchBySearchParameters(int idCountry,
                                                int idProvince,
                                                int idCity,
                                                int idSportCenter,
                                                boolean onlyPastMatch,
                                                boolean onlyFutureMatch,
                                                boolean onlyCanceledMatch) throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);

    if (idSportCenter > 0)
    {
      crit.add(Restrictions.eq("sportCenter.id", idSportCenter));
    }
    else
    {
      crit.createCriteria("sportCenter");
      if (idCity > 0)
      {
        crit.createAlias("sportCenter.city", "sportCenterGeo")
             .add(Restrictions.eq("sportCenterGeo.id", idCity));
      }
      else if (idProvince > 0)
      {
        crit.createAlias("sportCenter.province", "sportCenterGeo")
            .add(Restrictions.eq("sportCenterGeo.id", idProvince));
      }
      else if (idCountry > 0)
      {
        crit.createAlias("sportCenter.country", "sportCenterGeo")
            .add(Restrictions.eq("sportCenterGeo.id", idCountry));
      }
    }

    if (onlyPastMatch)
      crit.add(Restrictions.lt("matchStart", new Date()));

    if (onlyFutureMatch)
      crit.add(Restrictions.ge("matchStart", new Date()));

    if (onlyCanceledMatch)
      crit.add(Restrictions.eq("canceled", true));

    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    crit.addOrder(Order.desc("matchStart"));
    return crit.list();
  }

  /**
   *
   * @return lista match futuri di cui non è stata notificata l'apertura registrazione
   * @throws Exception
   */
  public List<Match> getMatchesToNotify() throws Exception
  {

    Criteria crit = getSession().createCriteria(Match.class);

    crit.add(Restrictions.ge("matchStart", new Date()));
    crit.add(Restrictions.ne("canceled", true));
    crit.add(Restrictions.ne("notified", true));
    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    crit.addOrder(Order.desc("matchStart"));
    return crit.list();
  }

  /**
   *
   * @return lista matches ordinata per Date inizio Desc
   * @throws Exception
   */
  public List<Match> findOrderByDateStart()throws Exception
  {
    Criteria crit = getSession().createCriteria(Match.class);
    //crit.setFetchMode("playerList", FetchMode.JOIN);
    //crit.setFetchMode("commentList", FetchMode.JOIN);
    crit.setFetchMode("userOwner", FetchMode.JOIN);
    crit.setFetchMode("sportCenter", FetchMode.JOIN);
    crit.setFetchMode("sportCenter.city", FetchMode.JOIN);
    crit.setFetchMode("matchType", FetchMode.JOIN);
    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    crit.addOrder(Order.desc("matchStart"));
    return crit.list();
  }

   public List<Object[]> findOrderByDate(int idCountryFilter)throws  Exception
  {
    return findOrderByDate(idCountryFilter,0,0,0,false,false,false);
  }

  public List<Object[]> findOrderByDate()throws  Exception
  {
    return findOrderByDate(0,0,0,0,false,false,false);
  }

  /**
   *
   * @param idCountry filtro nazione
   * @param idProvince filtro provincia
   * @param idCity filtro città
   * @param idSportCenter filtro centro sportivo
   * @param onlyPastMatch "true" per  match passati
   * @param onlyFutureMatch "true" per match futuri
   * @param onlyCanceledMatch "true" per match annullati
   * @return una lista di Oggetti Array, i cui campi sono quelli richiesti dalla query,
   * usata nel BO per ottenere i dati del report partite
   * ordinate per Date inizio Desc
   * @throws Exception
   */
  public List<Object[]> findOrderByDate(int idCountry,
                                        int idProvince,
                                        int idCity,
                                        int idSportCenter,
                                        boolean onlyPastMatch,
                                        boolean onlyFutureMatch,
                                        boolean onlyCanceledMatch)throws Exception
  {
    List<Object[]> objList = new ArrayList<Object[]>();
    String sql = " SELECT "
                + "   MATCHES.ID_MATCH , "                                    //index[0]
                + "   MATCHES.MAX_PLAYERS ,"                                  //index[1]
                + "   COUNT(players.ID_PLAYER),"                              //index[2]
                + "   MATCHES.MAX_PLAYERS - COUNT(players.ID_PLAYER), "       //index[3]
                + "   MATCHES.MATCH_START , "                                 //index[4]
                + "   MATCHES.RECORDED , "                                    //index[5]
                + "   MATCHES.REGISTRATION_CLOSED , "                         //index[6]
                + "   MATCHES.REGISTRATION_START , "                          //index[7]
                + "   MATCHES.SPORTS_CENTER_ADDRESS , "                       //index[8]
                + "   MATCHES.SPORTS_CENTER_CITY , "                          //index[9]
                + "   ( SELECT "
                + "       COUNT(MATCH_COMMENTS.ID_MATCH_COMMENT) "
                + "     FROM "
                + "       MATCH_COMMENTS "
                + "     WHERE "
                + "       ID_MATCH = MATCHES.ID_MATCH) ,"                     //index[10]
                + "   owner.ID_USER, "                                        //index[11]
                + "   owner.PASSWORD, "                                       //index[12]
                + "   CONCAT(owner.FIRST_NAME, ' ', owner.LAST_NAME)  , "     //index[13]
                + "   matchtype.ID_MATCH_TYPE, "                              //index[14]
                + "   matchtype.KEY_NAME , "                                  //index[15]
                + "   sportcenter.ID_SPORTS_CENTER, "                         //index[16]
                + "   sportcenter.NAME ,"                                     //index[17]
                + "   sportcenter.ADDRESS ,"                                  //index[18]
                + "   ( SELECT "
                + "       CITIES.NAME as CITY_NAME "
                + "     FROM "
                + "       CITIES "
                + "     WHERE "
                + "       CITIES.ID_CITY=sportcenter.ID_CITY ), "             //index[19]
                + "   ( SELECT "
                + "       PROVINCES.NAME as PROVINCE_NAME "
                + "     FROM "
                + "       PROVINCES "
                + "     WHERE "
                + "       PROVINCES.ID_PROVINCE=sportcenter.ID_PROVINCE ), "  //index[20]
                + "   MATCHES.ID_SPORTS_CENTER, "                             //index[21]
                + "   COALESCE( "
                + "      (SELECT "
                + "         CONCAT(USERS.FIRST_NAME,' ',USERS.LAST_NAME) "
                + "       FROM "
                + "         USERS "
                + "       WHERE "
                + "         ID_USER = ( SELECT "
                + "                       ID_USER "
                + "                     FROM "
                + "                       PLAYERS "
                + "                     INNER JOIN "
                + "                       PLAYERS_TO_MATCHES "
                + "                         ON PLAYERS_TO_MATCHES.ID_PLAYER = PLAYERS.ID_PLAYER "
                + "                     WHERE "
                + "                         PLAYERS_TO_MATCHES.ID_MATCH = MATCHES.ID_MATCH "
                + "                       AND "
                + "                         PLAYERS.REPORTER_ENABLED = 1 "
                + "                     LIMIT 1)),''), "                      //index[22]
                + "   MATCHES.SPORTS_CENTER_NAME, "                           //index[23]
                + "   matchtype.TOT_TEAM_PLAYERS, "                           //index[24]
                + "   MATCHES.CANCELED, "                                     //index[25]
                + "   MATCHES.RECORDED_DATE "                                 //index[26]
                + " FROM MATCHES "
                + "   LEFT JOIN USERS owner  ON MATCHES.ID_USER_OWNER  = owner.ID_USER"
                + "   LEFT JOIN PLAYERS_TO_MATCHES playerlist ON MATCHES.ID_MATCH = playerlist.ID_MATCH "
                + "   LEFT JOIN PLAYERS players ON (players.ID_PLAYER = playerlist.ID_PLAYER "
                + "     AND (players.PLAYER_STATUS IN('OUT','OWNER_REGISTERED','USER_REGISTERED','USER_REQUEST_REGISTERED') )) "
                + "   INNER JOIN MATCH_TYPES matchtype ON MATCHES.ID_MATCH_TYPE = matchtype.ID_MATCH_TYPE "
                + "   LEFT JOIN SPORTS_CENTERS sportcenter ON MATCHES.ID_SPORTS_CENTER = sportcenter.ID_SPORTS_CENTER "
                + "   LEFT JOIN CITIES city ON sportcenter.ID_CITY = city.ID_CITY "
                + "   LEFT JOIN PROVINCES province ON sportcenter.ID_PROVINCE = province.ID_PROVINCE ";

      String where= " WHERE ";
      String and= " AND ";
      int andCount=0;
      
      if (idCity > 0)
      {
        where = where + " sportcenter.ID_CITY = '"+idCity+"' " ;
        andCount++;
      }
      else if (idProvince > 0)
      {
        where = where + " sportcenter.ID_PROVINCE = '"+idProvince+"' " ;
        andCount++;
      }
      else if (idCountry > 0)
      {
        where = where + " sportcenter.ID_COUNTRY = '"+idCountry+"' " ;
        andCount++;
      }
      
      if (onlyPastMatch)
      {
        if (andCount>=1)
        {
          where = where + and + " MATCHES.MATCH_START < NOW() ";
        }
        else
        {
          where = where + " MATCHES.MATCH_START < NOW() ";
        }
        andCount++;
      }
      

      if (onlyFutureMatch)
      {
        if (andCount>=1)
        {
          where = where + and + " MATCHES.MATCH_START >= NOW() ";
        }
        else
        {
          where = where + " MATCHES.MATCH_START >= NOW() ";
        }
        andCount++;
      }
        

      if (onlyCanceledMatch)
      {
        if (andCount>=1)
        {
          where = where + and + " MATCHES.CANCELED = 1 ";
        }
        else
        {
          where = where + " MATCHES.CANCELED = 1 ";
        }
        andCount++;
      }

      if (andCount>0)
        sql= sql + where ;



    String group= " GROUP BY MATCHES.ID_MATCH "
                  + " ORDER BY MATCHES.MATCH_START DESC ";

    sql= sql + group;
    
    Query query = getSession().createSQLQuery(sql.toString());
    objList = (List<Object[]>) query.list();
    return objList;
  }

  /**
   *
   * @param idMatchList lista id match delle partite da  restituire
   * @return lista di match
   */
  public List<Match> getByIdList(List<Integer> idMatchList)
  {
    Criteria criteria = getSession().createCriteria(Match.class);
    criteria.setFetchMode("userOwner", FetchMode.JOIN);
    criteria.setFetchMode("playerList", FetchMode.JOIN);
    criteria.setFetchMode("playerList.user", FetchMode.JOIN);
    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    criteria.add(Restrictions.in("id", idMatchList));
    return criteria.list();
  }

  /**
   *
   * @param idMatch match d'interesse
   * @return lista di match con fetch di Hibernate ad Hoc per costruire l'oggetto matchInfo
   */
  public Match getForMatchInfo(int idMatch)
  {
    Criteria criteria = getSession().createCriteria(Match.class);
    criteria.setFetchMode("playerList", FetchMode.JOIN);
    criteria.setFetchMode("playerList.user", FetchMode.JOIN);
    criteria.setFetchMode("sportCenter", FetchMode.JOIN);
    criteria.setFetchMode("commentList", FetchMode.JOIN);
    criteria.setFetchMode("sportCenter.city", FetchMode.JOIN);
    criteria.setFetchMode("sportCenter.province", FetchMode.JOIN);
    criteria.setFetchMode("matchType", FetchMode.JOIN);
    criteria.add(Restrictions.eq("id", idMatch));
    return criteria.list().isEmpty() ? null : (Match) criteria.list().get(0);
  }

  public List<Match> findMatchToPlayByCity(City city)
  {
    return null;
  }

  public List<Integer> lastSportCenterUsed(List<Integer> lastUserIsPlayer)
  {
    return null;
  }

  public List<Match> findMatchToPlayBySportCenter(List<Integer> lastSportCenterUsed)
  {
    return null;
  }
}
