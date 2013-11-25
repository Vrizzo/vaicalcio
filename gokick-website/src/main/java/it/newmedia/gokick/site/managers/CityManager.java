package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le operazione relative alle città.
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
  /**
   *
   * @param id
   * @return
   */
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

  /**
   *
   * @param idProvince
   * @return lista delle città della provincia indicata con utenti registrati ed ENABLED ordinate per nome
   */
  public static List<City> getAllWithUsers(int idProvince)
  {
    return getAll(idProvince, true, false);
  }

  /**
   *
   * @param idProvince
   * @return lista delle città della provincia indicata con utenti registrati ed ENABLED ordinate per nome
   */
  public static List<City> getAllWithUsersOnMarket(int idProvince)
  {
    List<City> cityList=new ArrayList<City>();
    try
    {
      //return getAll(idProvince, true, false,true);
      cityList= DAOFactory.getInstance().getCityDAO().getAllOrdered(idProvince, true, false, true);
    }
    catch (Exception ex)
    {
      logger.error("error retriving cities", ex);
    }
    return cityList;
  }

  /**
   *
   * @param idProvince
   * @return lista delle città della provincia indicata con SportCenter Enabled e Approved ordinate per nome
   */
  public static List<City> getAllWithSportCenters(int idProvince)
  {
    try
    {
      //return DAOFactory.getInstance().getCityDAO().getAllOrderedHql(idProvince);
      return getAll(idProvince, false, true);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<City>();
    }
  }

  /**
   *
   * @param idProvince
   * @return lista delle città della provincia indicata con Match futuri ordinate per nome
   */
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

  /**
   *
   * @param idProvince
   * @return lista delle città della provincia indicata ordinate per nome
   */
  public static List<City> getAll(int idProvince)
  {
    return getAll(idProvince, false, false);
  }

  /**
   *
   * @param idProvince
   * @param onlyWithUsers "true" per città con User
   * @param onlyWithSportCenters "true" per città con SportCenter
   * @return lista delle città della provincia indicata ordinate per nome
   */
  public static List<City> getAll(int idProvince, boolean onlyWithUsers, boolean onlyWithSportCenters)
  {
    try
    {
      return DAOFactory.getInstance().getCityDAO().getAllOrdered(idProvince, onlyWithUsers, onlyWithSportCenters,false);
    } catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<City>();
    }
  }

  /**
   *
   * @param idProvince
   * @return lista di oggetti contenti id e nome delle città della provincia indicata, con User registrati Enabled e ordinate per nome
   */
  public static List<Object[]> getAllWithUsersProjection(int idProvince)
  {
    return getAllProjection(idProvince, true, false,false);
  }

  /**
   *
   * @param idProvince
   * @return lista di oggetti contenti id e nome delle città della provincia indicata, con sportCenter Enabled e Approved  e ordinate per nome
   */
  public static List<Object[]> getAllWithSportCentersProjection(int idProvince)
  {
    return getAllProjection(idProvince, false, true,false);
  }

  /**
   *
   * @param idProvince
   * @return lista di oggetti contenti id e nome delle città della provincia indicata, con Match futuri e ordinate per nome
   */
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

  /**
   *
   * @param idProvince
   * @return lista di oggetti contenti id e nome delle città della provincia indicata, con Match passati e ordinate per nome
   */
  public static List<Object[]> getAllWithPastMatchesProjection(int idProvince)
  {
    try
    {
      return DAOFactory.getInstance().getCityDAO().getAllOrderedWithMatchesProjection(idProvince,true);
    } catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Object[]>();
    }
  }

  /**
   *
   * @param idProvince
   * @param onlyWithUsers "true" per città con User
   * @param onlyWithSportCenters "true" per città con SportCenter
   * @return @return lista di oggetti contenti id e nome delle città della provincia indicata, ordinate per nome
   */
  public static List<Object[]> getAllProjection(int idProvince, boolean onlyWithUsers, boolean onlyWithSportCenters,boolean onlyUsersOnMarket)
  {
    try
    {
      return DAOFactory.getInstance().getCityDAO().getAllOrderedProjection(idProvince, onlyWithUsers, onlyWithSportCenters, onlyUsersOnMarket);
    } catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Object[]>();
    }
  }

  /**
   * incrementa di una unità il campo totUsers della città indicata
   * @param idCity
   */
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

  /**
   * devrementa di una unità il campo totUsers della città indicata
   * @param idCity
   */
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

  /**
   *
   * @param cityList
   * @param currentUserCity
   * @return lista di città, aggiunta della città indicata nel caso non fosse in lista
   */
  public static List<City> addCity(List<City> cityList, City currentUserCity)
  {
    JXPathContext jxpathContext = JXPathContext.newContext(cityList);
    City city = null;
    try
    {
      city = (City) jxpathContext.getValue(".[@id = " + currentUserCity.getId() + "]");
    }
    catch (Exception e)
    {
    }
    if( city==null)
    {
      cityList.add(currentUserCity);
      Collections.sort(cityList, City.POSITION_NAME_ORDER);
    }
    return cityList;
  }

  /**
   *
   * @param cityList
   * @param currentUserCity
   * @return lista di oggetti contenenti id e nome di città, aggiunta dell'oggetto contente la città indicata se non fosse in lista
   */
  public static List<Object[]> addCityObject(List<Object[]> cityList, City currentUserCity)
  {
    
    boolean found=false;
      Iterator<Object[]> itCityList = cityList.iterator();
      while (itCityList.hasNext())
      {
        Object[] provObj =itCityList.next();
        if (provObj[0].equals(currentUserCity.getId()))
          found=true;
      }

    if( !found)
    {
      Object[] cityObj = {currentUserCity.getId(),currentUserCity.getName()};
      cityList.add(cityObj);
      //Collections.sort(cityList, City.POSITION_NAME_ORDER);
    }
    return cityList;

  }

  
  // </editor-fold>
}
