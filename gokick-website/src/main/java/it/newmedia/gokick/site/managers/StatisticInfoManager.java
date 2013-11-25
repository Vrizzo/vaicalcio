package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.SquadInfo;
import it.newmedia.gokick.site.infos.StatisticInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni per gestire la visualizzazione delle statistiche.
 */
public class StatisticInfoManager {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(StatisticInfoManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private StatisticInfoManager()
  {
  }

  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   * recupera lista di oggeti StatisticInfo per la squadra indicata e memorizza in cache se non ci fossero
   * @param idSquad id squadra
   * @param statisticPeriod intervallo di tempo
   * @return lista di oggeti StatisticInfo
   */
  public static List<StatisticInfo> getAllByIdSquadAndPeriod(int idSquad, String statisticPeriod)
  {
    List<StatisticInfo> statisticInfoList = new ArrayList<StatisticInfo>();
    try
    {
      List userSquadIdUserList = DAOFactory.getInstance().getUserSquadDAO().getAllConfirmedUserByIdSquad(idSquad);
      if (userSquadIdUserList == null)
      {
        return statisticInfoList;
      }

      for (int i = 0; i < userSquadIdUserList.size(); i++)
      {
        StatisticInfo statisticInfo = InfoProvider.getStatisticInfo((Integer) userSquadIdUserList.get(i), statisticPeriod);
        UserInfo userInfo = InfoProvider.getUserInfo((Integer) userSquadIdUserList.get(i));
        if (userInfo.isValid())
        {
          statisticInfo.setUserInfo(userInfo);
        }
        statisticInfoList.add(statisticInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return statisticInfoList;
    }
    return statisticInfoList;
  }

  /**
   * recupera lista di oggeti StatisticInfo i partecipanti di una partita e memorizza in cache se non ci fossero
   * @param idMatch id partita in oggetto
   * @param statisticPeriod intervallo di tempo
   * @return lista di oggeti StatisticInfo
   */
  public static List<StatisticInfo> getAllByIdMatchAndPeriod(Integer idMatch, String statisticPeriod)
  {
    List<StatisticInfo> statisticInfoList = new ArrayList<StatisticInfo>();
    try
    {
      List playerMatchIdList = DAOFactory.getInstance().getMatchDAO().getAllRegisteredPlayerByIdMatch(idMatch);
      for (int i = 0; i < playerMatchIdList.size(); i++)
      {
        StatisticInfo statisticInfo = InfoProvider.getStatisticInfo((Integer) playerMatchIdList.get(i), statisticPeriod);
        UserInfo userInfo = InfoProvider.getUserInfo((Integer) playerMatchIdList.get(i));
        if (userInfo.isValid())
        {
          statisticInfo.setUserInfo(userInfo);
        }
        statisticInfoList.add(statisticInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return statisticInfoList;
    }
    return statisticInfoList;
  }

  /**
   * recupera lista di oggeti StatisticInfo per utenti che hanno fatto richiesta di amicizia e memorizza in cache se non ci fossero
   * @param idUser id utente
   * @param statisticPeriod intervallo di tempo
   * @return lista di oggeti StatisticInfo
   */
  public static List<StatisticInfo> getAllRequestsReceivedByIdUserOwnerAndPeriod(int idUser, String statisticPeriod)
  {
    List<StatisticInfo> statisticInfoList = new ArrayList<StatisticInfo>();
    try
    {
      List userSquadIdSquadList = DAOFactory.getInstance().getUserSquadDAO().getRequestsReceivedByIdUser(idUser);
      for (int i = 0; i < userSquadIdSquadList.size(); i++)
      {
        SquadInfo squadInfo = InfoProvider.getSquadInfo((Integer) userSquadIdSquadList.get(i));
        if (squadInfo.isValid())
        {
          StatisticInfo statisticInfo = InfoProvider.getStatisticInfo((Integer) squadInfo.getIdOwner(), statisticPeriod);
          UserInfo userInfo = InfoProvider.getUserInfo((Integer) squadInfo.getIdOwner());
          if (userInfo.isValid())
          {
            statisticInfo.setUserInfo(userInfo);
          }
          statisticInfoList.add(statisticInfo);
        }
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return statisticInfoList;
    }
    return statisticInfoList;
  }

  /**
   * recupera lista di oggeti StatisticInfo per utenti a cui è stata fatta richiesta di amicizia e memorizza in cache se non ci fossero
   * @param idUser id utente
   * @param statisticPeriod intervallo di tempo
   * @return lista di oggeti StatisticInfo
   */
  public static List<StatisticInfo> getAllRequestsMadeByIdUserOwnerAndPeriod(int idUser, String statisticPeriod)
  {
    List<StatisticInfo> statisticInfoList = new ArrayList<StatisticInfo>();
    try
    {
      Squad squad = DAOFactory.getInstance().getSquadDAO().getFirstByIdUser(idUser);
      if (squad == null)
      {
        return statisticInfoList;
      }

      List userSquadIdUserList = DAOFactory.getInstance().getUserSquadDAO().getAllRequestsMadeByIdSquad(squad.getId());
      for (int i = 0; i < userSquadIdUserList.size(); i++)
      {
        StatisticInfo statisticInfo = InfoProvider.getStatisticInfo((Integer) userSquadIdUserList.get(i), statisticPeriod);
        UserInfo userInfo = InfoProvider.getUserInfo((Integer) userSquadIdUserList.get(i));
        if (userInfo.isValid())
        {
          statisticInfo.setUserInfo(userInfo);
        }
        statisticInfoList.add(statisticInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return statisticInfoList;
    }
    return statisticInfoList;
  }

  /**
   * recupera lista di oggeti StatisticInfo per utenti a cui il creatore della squadra ha fatto richiesta di amicizia e memorizza in cache se non ci fossero
   * @param squad squadra
   * @param statisticPeriod intervallo di tempo
   * @return lista di oggeti StatisticInfo
   */
  public static List<StatisticInfo> getAllRequestsMadeBySquadOwnerAndPeriod(Squad squad, String statisticPeriod)
  {
    List<StatisticInfo> statisticInfoList = new ArrayList<StatisticInfo>();
    try
    {
      List userSquadIdUserList = DAOFactory.getInstance().getUserSquadDAO().getAllRequestsMadeByIdSquad(squad.getId());
      for (int i = 0; i < userSquadIdUserList.size(); i++)
      {
        StatisticInfo statisticInfo = InfoProvider.getStatisticInfo((Integer) userSquadIdUserList.get(i), statisticPeriod);
        UserInfo userInfo = InfoProvider.getUserInfo((Integer) userSquadIdUserList.get(i));
        if (userInfo.isValid())
        {
          statisticInfo.setUserInfo(userInfo);
        }
        statisticInfoList.add(statisticInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return statisticInfoList;
    }
    return statisticInfoList;
  }


  /**
   * recupera lista di oggeti StatisticInfo per utenti aperti al mercato e per provincia,memorizza in cache se non ci fossero
   * @param idProvince id provincia
   * @param idUserList lista id user
   * @param statisticPeriod intervallo di tempo
   * @return lista di oggeti StatisticInfo
   */
  public static List<StatisticInfo> getLimitUserOpenMarketByIdProvince(int idProvince, List idUserList, String statisticPeriod)
  {
    List<StatisticInfo> statisticInfoList = new ArrayList<StatisticInfo>();
    try
    {
      List idOpenMarketUserList = DAOFactory.getInstance().getUserDAO().getLimitByIdProvinceAndOpenMarket(idProvince, idUserList);
      for (int i = 0; i < idOpenMarketUserList.size(); i++)
      {
        StatisticInfo statisticInfo = InfoProvider.getStatisticInfo((Integer) idOpenMarketUserList.get(i), statisticPeriod);
        UserInfo userInfo = InfoProvider.getUserInfo((Integer) idOpenMarketUserList.get(i));
        if (userInfo.isValid())
        {
          statisticInfo.setUserInfo(userInfo);
        }
        statisticInfoList.add(statisticInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return statisticInfoList;
    }
    return statisticInfoList;
  }

  /**
   * recupera lista di oggeti StatisticInfo secondo i parametri di ricerca indicati,memorizza in cache se non ci fossero
   * @param firstName nome
   * @param lastName cognome
   * @param minBirthDate data nascita minima
   * @param maxBirthDate data nascita massima
   * @param idCountry id nazione
   * @param idProvince id provincia
   * @param idCity id città
   * @param roles ruolo/i
   * @param marketEnabled indica se cercare solo utenti con mercato aperto
   * @param statisticPeriod intervallo di tempo
   * @return lista di oggeti StatisticInfo
   */
  public static List<StatisticInfo> getBySearchParameters(String firstName,
          String lastName,
          Date minBirthDate,
          Date maxBirthDate,
          int idCountry,
          int idProvince,
          int idCity,
          List<Integer> roles,
          boolean marketEnabled,
          String statisticPeriod)
  {
    return getBySearchParameters(firstName, lastName, minBirthDate, maxBirthDate, idCountry, idProvince, idCity, roles, marketEnabled,statisticPeriod, false, null);

  }

  /**
   * recupera lista di oggeti StatisticInfo secondo i parametri di ricerca indicati,memorizza in cache se non ci fossero
   * @param firstName nome
   * @param lastName cognome
   * @param minBirthDate data nascita minima
   * @param maxBirthDate data nascita massima
   * @param idCountry id nazione
   * @param idProvince id provincia
   * @param idCity id città
   * @param roles ruolo/i
   * @param marketEnabled indica se cercare solo utenti con mercato aperto
   * @param statisticPeriod intervallo di tempo
   * @param newsLetterEnabled  indica se cercare solo utenti iscritti alla newsLetter
   * @param status
   * @return
   */
  public static List<StatisticInfo> getBySearchParameters(String firstName,
          String lastName,
          Date minBirthDate,
          Date maxBirthDate,
          int idCountry,
          int idProvince,
          int idCity,
          List<Integer> roles,
          boolean marketEnabled,
          String statisticPeriod,
          boolean newsLetterEnabled,
          String status)
  {
    List<StatisticInfo> statisticInfoList = new ArrayList<StatisticInfo>();
    try
    {
      List<Integer> idUserList = DAOFactory.getInstance().getUserDAO().getBySearchParameters(firstName,
              lastName,
              minBirthDate,
              maxBirthDate,
              idCountry,
              idProvince,
              idCity,
              roles,
              marketEnabled,
              newsLetterEnabled,
              status);
      int numResults = Math.min(idUserList.size(), AppContext.getInstance().getMaxUserResults());
      for (int i = 0; i < numResults; i++)
      {
        StatisticInfo statisticInfo = InfoProvider.getStatisticInfo(idUserList.get(i), statisticPeriod);
        UserInfo userInfo = InfoProvider.getUserInfo(idUserList.get(i));
        if (userInfo.isValid())
        {
          statisticInfo.setUserInfo(userInfo);
        }
        statisticInfoList.add(statisticInfo);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return statisticInfoList;
    }
    return statisticInfoList;
  }

  /**
   *
   * @param idUser id utente
   * @param period intervallo di tempo
   * @return restituisce l'oggetto statisticInfo per l'utente ed il periodo indicato
   */
  public static StatisticInfo getByIdUserAndPeriod(int idUser, String period)
  {
    return InfoProvider.getStatisticInfo(idUser, period);
  }
  // </editor-fold>
}
