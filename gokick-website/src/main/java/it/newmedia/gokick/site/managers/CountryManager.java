package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le operazione relative alle Country.
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
  /**
   *
   * @param id
   * @return Country
   */
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

  /**
   *
   * @return lista di Country ordinata per Position,Name e che abbiano province relazionate
   */
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

  /**
   *
   * @return lista di Country 
   */
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

  /**
   *
   * @return lista di Country con Users Enabled e non Anonymous, ordinata per Position,Name
   */
  public static List<Country> getAllWithUsers()
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().getAllOrdered(true, false);

    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }

  /**
   *
   * @return lista di Country con Users Enabled e non Anonymous,sul mercato e ordinata per Position,Name
   */
  public static List<Country> getAllWithUsersOnMarket()
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().getAllOrdered(true, false, false,false, true, true);

    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }

  /**
   *
   * @return lista di Country con sportCenter Enabled e Approved, ordinata per Position,Name
   */
  public static List<Country> getAllWithSportCenters()
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().getAllOrdered(false, true);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }

  /**
   *
   * @param countryList
   * @param currentUserCountry
   * @return lista di Country, aggiunta della Country indicata nel caso non fosse in lista
   */
  public static List<Country> addCountry(List<Country> countryList, Country currentUserCountry)
  {
    JXPathContext jxpathContext = JXPathContext.newContext(countryList);
    Country country = null;
    try
    {
      country = (Country) jxpathContext.getValue(".[@id = " + currentUserCountry.getId() + "]");
    }
    catch (Exception e)
    {
    }
    if( country==null)
    {
      countryList.add(currentUserCountry);
      Collections.sort(countryList, Country.POSITION_NAME_ORDER);
    }
    return countryList;
  }

  /**
   *
   * @return lista di Country con Match futuri, ordinata per Position,Name
   */
  public static List<Country> getAllWithMatches()
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().getAllOrdered(false, false,true);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }

  /**
   *
   * @return lista di Country con Match futuri, ordinata per Position,Name
   */
  public static List<Country> getAllWithPastMatches()
  {
    try
    {
      return DAOFactory.getInstance().getCountryDAO().getAllOrdered(false, false,true,true,true,false);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Country>();
    }
  }
  // </editor-fold>
}
