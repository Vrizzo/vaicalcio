package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce informazioni sulle partite.
 */
public class MatchInfoManager {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(MatchInfoManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >

  private MatchInfoManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   * restituisce un MatchInfo e se non presente lo memorizza in cache
   * @param idMatch id mach d'interesse
   * @return oggetto MatchInfo
   */
  public static MatchInfo getMatchInfo(int idMatch)
  {
    return InfoProvider.getMatchInfo(idMatch);
  }

  /**
   *
   * @return numero partite future non cancellate
   */
  public static int countAllPlayable()
  {
    List<Integer> idAllPlayableMatchList;
    try
    {
      idAllPlayableMatchList = DAOFactory.getInstance().getMatchDAO().getAllPlayable();
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
    return idAllPlayableMatchList.size();
  }

  /**
   *
   * @param idProvince provincia di interesse
   * @param idFriendList lista degli amici dell'utente
   * @return numero partite giocabili nella provincia indicata
   */
  public static int countAllVeryPlayable(int idProvince,List idFriendList)
  {
    List<Integer> idAllPlayableMatchList;
    try
    {
      idAllPlayableMatchList = DAOFactory.getInstance().getMatchDAO().getAllVeryPlayable(idProvince, idFriendList);
      Iterator idAllPlayableIterator = idAllPlayableMatchList.iterator();
      while (idAllPlayableIterator.hasNext())
      {
        int idMatch = (Integer) idAllPlayableIterator.next() ;
        int playersNumber = MatchManager.countMatchPlayers(idMatch, false,  EnumPlayerStatus.OwnerRegistered,
                                                                            EnumPlayerStatus.UserRegistered,
                                                                            EnumPlayerStatus.UserRequestRegistered,
                                                                            EnumPlayerStatus.Out);
        if (playersNumber >= InfoProvider.getMatchInfo(idMatch).getMaxPlayers() )
          idAllPlayableIterator.remove();

      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
    return idAllPlayableMatchList.size();
  }
  
  /**
   *
   * @param friendsIdList id degli amici dell'utente
   * @param idCurrentUser id dell'utente d'interesse
   * @return numero partite organizzate dagli amici di un utente, 0 in caso di errore
   */
  public static int countFriends(List friendsIdList, int idCurrentUser)
  {
    friendsIdList.remove((Integer) idCurrentUser);
    if (CollectionUtils.isEmpty(friendsIdList))
    {
      return 0;
    }
    //TODO: valutare la possibilità di fare un metodo che faccia direttamente la count su db
    try
    {
      List<Integer> idMatchList = DAOFactory.getInstance().getMatchDAO().getFriends(friendsIdList);
      return idMatchList.size();
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
  }

  /**
   *
   * @param idCurrentUser id user relativo
   * @return numero partite future a cui l'utente è registrato, 0 in caso di errore o nessuna registrazione
   */
  public static int countRegistered(int idCurrentUser)
  {
    List<Integer> idMatchList;
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getRegistered(idCurrentUser);
      if (idMatchList == null)
      {
        return 0;
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
    return idMatchList.size();
  }

  /**
   *
   * @param idCurrentUser user d'interesse
   * @return numero partite giocate,0 in caso di erore o nessuna giocata
   */
  public static int countPlayed(int idCurrentUser)
  {
    List<Integer> idMatchList;
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getPlayed(idCurrentUser);
      if (idMatchList == null)
      {
        return 0;
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
    return idMatchList.size();
  }

  /**
   *
   * @param idUserOwner user d'interesse
   * @return numero di partite future organizzate dall'utente
   */
  public static int countOrganized(int idUserOwner)
  {
    List<Integer> idMatchList;
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getOrganized(idUserOwner);
      if (idMatchList == null)
      {
        return 0;
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
    return idMatchList.size();
  }

  /**
   *
   * @param idUser id user in oggetto
   * @return il num di match futuri in cui l'utente è registrato o ha fatto richiesta
   */
  public static int countWhereUserInPlayerList(int idUser)
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getCountWhereUserInPlayerList(idUser);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
  }


  /**
   *
   * @param idUserOwner
   * @return num partite organizzate e archiviate da un utente, 0 in caso di errore o nessuna
   */
  public static int countOrganizedRecorded(int idUserOwner)
  {
    List<Integer> idMatchList;
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getOrganizedRecorded(idUserOwner);
      if (idMatchList == null)
      {
        return 0;
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
    return idMatchList.size();
  }

  /**
   *
   * @param idCurrentUser user in oggetto
   * @param startDate data inizio periodo
   * @param endDate data fine periodo
   * @return la lista delle partite organizzate da un utente in un certo periodo
   */
  public static int countOrganizedByPeriod(int idCurrentUser, Date startDate, Date endDate)
  {
    List<Integer> idMatchList;
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getOrganizedByPeriod(idCurrentUser, startDate, endDate);
      if (idMatchList == null)
      {
        return 0;
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
    return idMatchList.size();
  }

  /**
   *
   * @param idCurrentUser user in oggetto
   * @return numero delle partite archiviate di un utente
   */
  public static int countRecorded(int idCurrentUser)
  {
    return countRecordedByPeriod(idCurrentUser, null, null);
  }

  /**
   *
   * @param idCurrentUser user in oggetto
   * @param startDate inzio periodo
   * @param endDate fine periodo
   * @return numero delle partite archiviate di un utente in un periodo dato
   */
  public static int countRecordedByPeriod(int idCurrentUser, Date startDate, Date endDate)
  {
    List<Integer> idMatchList;
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getRecordedByPeriod(idCurrentUser, startDate, endDate);
      if (idMatchList == null)
      {
        return 0;
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
    return idMatchList.size();
  }

  /**
   *
   * @param idUserOwner user d'interesse
   * @return lista oggetti matchInfo delle partite non archiviate di un utente
   */
  public static List<MatchInfo> getNotRecorded(int idUserOwner)
  {
    List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
    try
    {
      List<Integer> idMatchList = DAOFactory.getInstance().getMatchDAO().getNotRecorded(idUserOwner);
      if (idMatchList == null)
      {
        return matchInfoList;
      }

      for (int i = 0; i < idMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchInfoList;
    }
    return matchInfoList;
  }

  /**
   *
   * @param friendsIdList lista id amici dell'utente in oggetto
   * @param idCurrentUser id utente in oggetto
   * @return lista di oggetti matchInfo delle partite organizzate dagli amici di un utente
   */
  public static List<MatchInfo> getFriends(List friendsIdList, int idCurrentUser)
  {
    List<Integer> idMatchList;
    List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
    try
    {
      friendsIdList.remove((Integer) idCurrentUser);
      idMatchList = DAOFactory.getInstance().getMatchDAO().getFriends(friendsIdList);

      for (int i = 0; i < idMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchInfoList;
    }
    return matchInfoList;
  }

  /**
   *
   * @param idCurrentUser user d'interesse
   * @return lista di oggetti matchInfo delle partite future a cui si è iscritto un utente
   */
  public static List<MatchInfo> getRegistered(int idCurrentUser)
  {
    List<Integer> idMatchList;
    List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getRegistered(idCurrentUser);

      for (int i = 0; i < idMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchInfoList;
    }
    return matchInfoList;
  }

  /**
   *
   * @param idCurrentUser id utente in oggetto
   * @return la lista delle partite registrate che l'utente ha  giocato
   */
  public static List<MatchInfo> getPlayed(int idCurrentUser)
  {
    List<Integer> idMatchList;
    List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getPlayed(idCurrentUser);

      for (int i = 0; i < idMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchInfoList;
    }
    return matchInfoList;
  }

  /**
   *
   * @param idUserOwner id user d'interesse
   * @return lista oggetti matchInfo delle partite organizzate e archiviate di un utente
   */
  public static List<MatchInfo> getOrganizedRecorded(int idUserOwner)
  {
    List<Integer> idMatchList;
    List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getOrganizedRecorded(idUserOwner);

      for (int i = 0; i < idMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchInfoList;
    }
    return matchInfoList;
  }

  /**
   *
   * @param idUserOwner utente relativo
   * @return lista oggeti matchInfo delle partite organizzate da un utente
   */
  public static List<MatchInfo> getOrganized(int idUserOwner)
  {
    List<Integer> idMatchList;
    List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getOrganized(idUserOwner);

      for (int i = 0; i < idMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchInfoList;
    }
    return matchInfoList;
  }

  /**
   *
   * @param filterIdCountry id nazione oggetto della ricerca
   * @param filterIdProvince id provincia oggetto della ricerca
   * @param filterIdCity id città oggetto della ricerca
   * @param filterIdSportCenter id sportcenter oggetto della ricerca
   * @param idCurrentUser id user oggetto della ricerca
   * @param friendsIdList lista id amici dell'utente in oggetto
   * @return lista oggetti matchInfo dele partite archiviate che un utente può vedere, secondo parametri di ricerca
   */
  public static List<MatchInfo> getRecordedBySearchParameters(int filterIdCountry,
          int filterIdProvince,
          int filterIdCity,
          int filterIdSportCenter,
          int idCurrentUser,
          List friendsIdList)
  {
    List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
    try
    {
      List<Integer> idMatchList = DAOFactory.getInstance().getMatchDAO().getRecordedBySearchParameters(filterIdCountry,
              filterIdProvince,
              filterIdCity,
              filterIdSportCenter,
              idCurrentUser,
              friendsIdList);

      if (idMatchList == null)
      {
        return matchInfoList;
      }

      for (int i = 0; i < idMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
      //partite private a cui potresti essere iscritto
      List<Integer> idPrivateMatchList = DAOFactory.getInstance().getMatchDAO().getRecordedPrivateBySearchParameters(filterIdCountry,
              filterIdProvince,
              filterIdCity,
              filterIdSportCenter,
              idCurrentUser,
              friendsIdList);
      
      if (idPrivateMatchList == null)
      {
        return matchInfoList;
      }

      for (int i = 0; i < idPrivateMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idPrivateMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchInfoList;
    }
    return matchInfoList;
  }


  /**
   *
   * @param filterIdCountry id nazione oggetto della ricerca
   * @param filterIdProvince id provincia oggetto della ricerca
   * @param filterIdCity id città oggetto della ricerca
   * @param filterIdSportCenter id sportcenter oggetto della ricerca
   * @param idCurrentUser id user oggetto della ricerca
   * @param friendsIdList lista id amici dell'utente in oggetto
   * @param withSuggested Indica se devono essere incluse le partite suggerite (quelle degli amici senza vincolo geografico)
   * @param recorded Indica se si ricercano partite archiviate 
   * @return lista oggetti matchInfo delle partite secondo parametri di ricerca
   */
  public static List<MatchInfo> getBySearchParameters(int filterIdCountry,
          int filterIdProvince,
          int filterIdCity,
          int filterIdSportCenter,
          int idCurrentUser,
          List friendsIdList,
          boolean withSuggested,
          boolean recorded)
  {
    List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
    try
    {
      List<Integer> idMatchList = DAOFactory.getInstance().getMatchDAO().getBySearchParameters(filterIdCountry,
              filterIdProvince,
              filterIdCity,
              filterIdSportCenter,
              idCurrentUser,
              friendsIdList,
              withSuggested,
              recorded);
      for (int i = 0; i < idMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchInfoList;
    }
    return matchInfoList;
  }

  /**
   *
   * @param filterIdCountry id nazione oggetto della ricerca
   * @param filterIdProvince id provincia oggetto della ricerca
   * @param filterIdCity id città oggetto della ricerca
   * @param filterIdSportCenter id sportcenter oggetto della ricerca
   * @param idCurrentUser id user oggetto della ricerca
   * @param friendsIdList lista id amici dell'utente in oggetto
   * @param withSuggested Indica se devono essere incluse le partite suggerite (quelle degli amici senza vincolo geografico)
   * @param recorded Indica se si ricercano partite archiviate
   * @return lista oggetti matchInfo delle partite secondo parametri di ricerca
   */
  public static List<MatchInfo> getBySearchParameters(int filterIdCountry,
          int filterIdProvince,
          int filterIdCity,
          int filterIdSportCenter,
          int idCurrentUser,
          List friendsIdList,
          Date end,
          boolean withSuggested,
          boolean recorded)
  {
    List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
    try
    {
      List<Integer> idMatchList = DAOFactory.getInstance().getMatchDAO().getBySearchParameters(filterIdCountry,
              filterIdProvince,
              filterIdCity,
              filterIdSportCenter,
              idCurrentUser,
              friendsIdList,
              end,
              withSuggested,
              recorded);
      for (int i = 0; i < idMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchInfoList;
    }
    return matchInfoList;
  }

  /**
   *
   *
   * @return lista di tutti gli oggetti matchInfo
   */
  public static List<MatchInfo> getAll()
  {
    List<MatchInfo> matchInfoList = new ArrayList<MatchInfo>();
    try
    {
      List<Integer> idMatchList = DAOFactory.getInstance().getMatchDAO().getAll();
      for (int i = 0; i < idMatchList.size(); i++)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatchList.get(i));
        matchInfoList.add(matchInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchInfoList;
    }
    return matchInfoList;
  }

  // </editor-fold>
}
