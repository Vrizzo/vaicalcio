package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello il salvataggio dei messaggi relativi alle azioni fatte dagli utenti nel sito.
 */
public class CountryManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(CountryManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private CountryManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public static Country getCountry(int id)
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().get(id);
    }
    catch (Exception ex)
    {
      logger.error("Error get Country", ex);
    }
    return null;
  }

  public static List<Country> getAll()
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().getAllOrdered(false, false);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }

  public static List<Country> getAllCountry()
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().findAll();
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }

  public static List<Country> getAllCountryOrdered()
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().getAllOrdered(false,false,false,false,false,false,0);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }


  public static List<Country> getAllWithUsers()
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().getAll(true, false);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }

  public static List<Country> getAllWithSportCenters(int idCountryFilter)
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().getAllOrdered(false, true,idCountryFilter);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }

  public static List<Country> getAllWithMatches()
  {
    return getAllWithMatches(0);
  }
  
  public static List<Country> getAllWithMatches(int idCountryFilter)
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().getAllOrdered(false, false,true,idCountryFilter);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }
  // </editor-fold>
}
