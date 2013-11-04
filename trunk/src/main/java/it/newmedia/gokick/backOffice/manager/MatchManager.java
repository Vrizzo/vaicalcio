package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.dao.PlayerDAO;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni per gestire l'organizzazione della partite.
 */
public class MatchManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(MatchManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private MatchManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  public static List<Match> getAllOrderedByDateStart()
  {
    List<Match> matchList= new ArrayList<Match>();
    try
    {
      return DAOFactory.getInstance().getMatchDAO().findOrderByDateStart();
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return matchList;
    }
  }

  public static List<Object[]> getAllOrderedByDate(int idCountryFilter)
  {
    List<Object[]> objectList= new ArrayList<Object[]>();
    try
    {
      return DAOFactory.getInstance().getMatchDAO().findOrderByDate(idCountryFilter);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return objectList;
    }
  }

  public static List<Object[]> getAllOrderedByDate( int idCountry,
                                                    int idProvince,
                                                    int idCity,
                                                    int idSportCenter,
                                                    boolean onlyPastMatch,
                                                    boolean onlyFutureMatch,
                                                    boolean onlyCanceledMatch)
  {
    List<Object[]> objectList= new ArrayList<Object[]>();
    try
    {
      return DAOFactory.getInstance().getMatchDAO().findOrderByDate(idCountry,
                                                                    idProvince,
                                                                    idCity,
                                                                    idSportCenter,
                                                                    onlyPastMatch,
                                                                    onlyFutureMatch,
                                                                    onlyCanceledMatch);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return objectList;
    }
  }

  
  public static Match getById(int idMatch)
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().get(idMatch);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }
  }

   public static List<Match> getByIdList(List<Integer> idMatchList)
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getByIdList(idMatchList);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }
  }

  public static List<Match> getAllMatches()
  {
    List<Match> matchList = new ArrayList<Match>();
    try
    {
      return DAOFactory.getInstance().getMatchDAO().findAll();
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return matchList;
    }
  }

  public static int getAllCount(int idCountryFilter)
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getAllCount(false,false,false,idCountryFilter);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  public static int getAllFutureCount(int idCountryFilter)
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getAllCount(true,false,false,idCountryFilter);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  public static int getAllPastCount()
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getAllCount(false,true,false);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  public static int countMatchPlayers(int idMatch, boolean withMissing, EnumPlayerStatus... playerStatuses)
  {
    try
    {
      return DAOFactory.getInstance().getPlayerDAO().countPlayerByMatch(idMatch, withMissing, playerStatuses);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  public static Player getReporter(int idMatch)
  {
    try
    {
      PlayerDAO playerDAO = DAOFactory.getInstance().getPlayerDAO();
      return playerDAO.getReporterByMatch(idMatch);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }
  }

  public static List<Match> getMatchBySearchParameters( int idCountry,
                                                        int idProvince,
                                                        int idCity,
                                                        int idSportCenter,
                                                        boolean onlyPastMatch,
                                                        boolean onlyFutureMatch,
                                                        boolean onlyCanceledMatch)
  {
    List<Match> matchList = new ArrayList<Match>();
    try
    {
      matchList = DAOFactory.getInstance().getMatchDAO().getMatchBySearchParameters( idCountry,
                                                                                     idProvince,
                                                                                     idCity,
                                                                                     idSportCenter,
                                                                                     onlyPastMatch,
                                                                                     onlyFutureMatch,
                                                                                     onlyCanceledMatch);
     
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return matchList;
    }
    return matchList;
  }

  // </editor-fold>
}
