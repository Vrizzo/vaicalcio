package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello il salvataggio dei messaggi relativi alle azioni fatte dagli utenti nel sito.
 */
public class CityManager
{
  // <editor-fold defaultstate="collapsed" desc="-- Members --">

  private static Logger logger = Logger.getLogger(CityManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private CityManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public static City getCity(int id)
  {
    try
    {
      return DAOFactory.getInstance().getCityDAO().get(id);

    } catch (Exception ex)
    {
      logger.error("Error get City", ex);
    }
    return null;
  }

  public static List<City> getAllWithUsers(int idProvince)
  {
    return getAll(idProvince, true, false);
  }

  public static List<City> getAllWithSportCenters(int idProvince)
  {
    return getAll(idProvince, false, true);
  }

  public static List<City> getAllWithMatches(int idProvince)
  {
    try
    {
      return DAOFactory.getInstance().getCityDAO().getAllOrderedWithMatches(idProvince);
    } catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<City>();
    }
  }

  public static List<City> getAll(int idProvince)
  {
    return getAll(idProvince, false, false);
  }

  public static List<City> getAll(int idProvince, boolean onlyWithUsers, boolean onlyWithSportCenters)
  {
    try
    {
      return DAOFactory.getInstance().getCityDAO().getAll(idProvince, onlyWithUsers, onlyWithSportCenters);
    } catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<City>();
    }
  }

  public static List<Object[]> getAllWithUsersProjection(int idProvince)
  {
    return getAllProjection(idProvince, true, false);
  }

  public static List<Object[]> getAllWithSportCentersProjection(int idProvince)
  {
    return getAllProjection(idProvince, false, true);
  }

  public static List<Object[]> getAllWithMatchesProjection(int idProvince)
  {
    try
    {
      return DAOFactory.getInstance().getCityDAO().getAllOrderedWithMatchesProjection(idProvince);
    } catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Object[]>();
    }
  }

  public static List<Object[]> getAllProjection(int idProvince, boolean onlyWithUsers, boolean onlyWithSportCenters)
  {
    try
    {
      return DAOFactory.getInstance().getCityDAO().getAllProjection(idProvince, onlyWithUsers, onlyWithSportCenters);
    } catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Object[]>();
    }
  }

  public static void increaseTotUsers(int idCity)
  {
    try
    {
      DAOFactory.getInstance().getCityDAO().increaseTotUsers(idCity);
    } catch (Exception ex)
    {
      logger.error(ex, ex);
    }
  }

  public static void decreaseTotUsers(int idCity)
  {
    try
    {
      DAOFactory.getInstance().getCityDAO().decreaseTotUsers(idCity);
    } catch (Exception ex)
    {
      logger.error(ex, ex);
    }
  }
  // </editor-fold>
}
