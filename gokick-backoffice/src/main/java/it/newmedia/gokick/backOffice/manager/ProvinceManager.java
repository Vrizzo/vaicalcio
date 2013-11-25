package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello il salvataggio dei messaggi relativi alle azioni fatte dagli utenti nel sito.
 */
public class ProvinceManager
{

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(ProvinceManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private ProvinceManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public static Province getProvince(int id)
  {
    try
    {
      return DAOFactory.getInstance().getProvinceDAO().get(id);

    }
    catch (Exception ex)
    {
      logger.error("Error get Province", ex);
    }
    return null;
  }

  public static List<Province> getAllWithUsers(int idCountry)
  {
    return getAll(idCountry, true, false);
  }

  public static List<Province> getAllWithSportCenters(int idCountry)
  {
    return getAll(idCountry, false, true);
  }

  public static List<Province> getAllWithMatches(int idCountry)
  {
    try
    {
      return DAOFactory.getInstance().getProvinceDAO().getAllOrderedWithMatches(idCountry);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Province>();
    }
  }

  public static List<Province> getAll(int idCountry)
  {
    return getAll(idCountry, false, false);
  }

  public static List<Province> getAll(int idCountry, boolean onlyWithUsers, boolean onlyWithSportCenters)
  {
    try
    {
      return DAOFactory.getInstance().getProvinceDAO().getAll(idCountry, onlyWithUsers, onlyWithSportCenters);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Province>();
    }
  }

  public static Province getByAbbreviation(String abbreviation)
  {
    try
    {
      return DAOFactory.getInstance().getProvinceDAO().getByAbbreviation(abbreviation);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }
  }


  public static List<Object[]> getAllWithUsersProjection(int idCountry)
  {
    return getAllProjection(idCountry, true, false);
  }

  public static List<Object[]> getAllWithSportCentersProjection(int idCountry)
  {
    return getAllProjection(idCountry, false, true);
  }

  public static List<Object[]> getAllWithMatchesProjection(int idCountry)
  {
    try
    {
      return DAOFactory.getInstance().getProvinceDAO().getAllOrderedWithMatchesProjection(idCountry);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Object[]>();
    }
  }

  public static List<Object[]> getAllProjection(int idCountry, boolean onlyWithUsers, boolean onlyWithSportCenters)
  {
    try
    {
      return DAOFactory.getInstance().getProvinceDAO().getAllProjection(idCountry, onlyWithUsers, onlyWithSportCenters);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Object[]>();
    }
  }

  // </editor-fold>
}
