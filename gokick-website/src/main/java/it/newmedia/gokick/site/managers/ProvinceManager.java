package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni relative alle province.
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
  /**
   *
   * @param id
   * @return Province
   */
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

  /**
   *
   * @param idCountry
   * @return lista delle province in cui ci sono users iscritti al sito in stato ENABLED e non anonimi
   */
  public static List<Province> getAllWithUsers(int idCountry)
  {
    return getAll(idCountry, true, false);
  }

  public static List<Province> getAllWithUsersOnMarket(int idCountry)
  {
    List<Province> provinceList= new ArrayList<Province>();
    try
    {
      provinceList= DAOFactory.getInstance().getProvinceDAO().getAllOrdered(idCountry, true, false, true);
    }
    catch (Exception ex)
    {
      logger.error("error retriving province", ex);
    }
    return provinceList;
  }

  /**
   *
   * @param idCountry
   * @return lista delle province in cui ci sono centri sportivi in stato ENABLED e approvati
   */
  public static List<Province> getAllWithSportCenters(int idCountry)
  {
    return getAll(idCountry, false, true);
  }

  /**
   *
   * @param idCountry
   * @return lista delle province in cui ci sono partite future
   */
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

  /**
   *
   * @param idCountry
   * @return lista delle province in cui ci sono partite passate
   */
  public static List<Province> getAllWithPastMatches(int idCountry)
  {
    try
    {
      return DAOFactory.getInstance().getProvinceDAO().getAllOrderedWithMatches(idCountry,true);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Province>();
    }
  }


  /**
   *
   * @param idCountry id nazione
   * @return lista di tutte le province della nazione indicata
   */
  public static List<Province> getAll(int idCountry)
  {
    return getAll(idCountry, false, false);
  }

  /**
   *
   * @param idCountry id nazione
   * @param onlyWithUsers "true" per ricerca province con utenti registrati
   * @param onlyWithSportCenters "true" per ricerca province con centri sportivi
   * @return lista di tutte le province della nazione secondo i parametri indicati
   */
  public static List<Province> getAll(int idCountry, boolean onlyWithUsers, boolean onlyWithSportCenters)
  {
    try
    {
      return DAOFactory.getInstance().getProvinceDAO().getAllOrdered(idCountry, onlyWithUsers, onlyWithSportCenters,false);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Province>();
    }
  }

  /**
   *
   * @param abbreviation
   * @return la provincia cui corrisponde l'abbreviazione indicata
   */
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

  /**
   *
   * @param idCountry id nazione
   * @return lista di oggetti contenti id e nome provincie coon utenti registrati
   */
  public static List<Object[]> getAllWithUsersProjection(int idCountry)
  {
    return getAllProjection(idCountry, true, false,false);
  }

  /**
   *
   * @param idCountry id nazione
   * @return lista di oggetti contenti id e nome provincie coon centri sportivi
   */
  public static List<Object[]> getAllWithSportCentersProjection(int idCountry)
  {
    return getAllProjection(idCountry, false, true,false);
  }

  /**
   *
   * @param idCountry id nazione
   * @return lista di oggetti contenti id e nome provincie coon partite fututre
   */
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

  /**
   *
   * @param idCountry id nazione
   * @return lista di oggetti contenti id e nome provincie coon partite passate
   */
  public static List<Object[]> getAllWithPastMatchesProjection(int idCountry)
  {
    try
    {
      return DAOFactory.getInstance().getProvinceDAO().getAllOrderedWithMatchesProjection(idCountry,true);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Object[]>();
    }
  }



  /**
   *
   * @param idCountry id nazione
   * @param onlyWithUsers "true" per province con utenti registrati
   * @param onlyWithSportCenters "true" per province con centri sportivi
   * @return lista di oggetti contenti id e nome provincie secondo i parametri specificati
   */
  public static List<Object[]> getAllProjection(int idCountry, boolean onlyWithUsers, boolean onlyWithSportCenters,boolean onlyUsersOnMarket)
  {
    try
    {
      return DAOFactory.getInstance().getProvinceDAO().getAllOrderedProjection(idCountry, onlyWithUsers, onlyWithSportCenters, onlyUsersOnMarket);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Object[]>();
    }
  }

  /**
   * aggiunge una provincia alla lista di province passata se non la contiene
   * @param provinceList lista alla quale aggiungere un altra provincia
   * @param currentUserProvince provincia da aggiungere
   * @return lista di province
   */
  public static List<Province> addProvince(List<Province> provinceList, Province currentUserProvince)
  {
    JXPathContext jxpathContext = JXPathContext.newContext(provinceList);
    Province province = null;
    try
    {
      province = (Province) jxpathContext.getValue(".[@id = " + currentUserProvince.getId() + "]");
    }
    catch (Exception e)
    {
    }
    if( province==null)
    {
      provinceList.add(currentUserProvince);
      Collections.sort(provinceList, Province.POSITION_NAME_ORDER);
    }
    return provinceList;
  }

  /**
   * aggiunge una oggetto (idProvince,name) alla lista di oggetti passati se non lo contiene
   * @param provinceList  lista di oggetti alla quale aggiungere un nuovo oggetto (idProvince,name)
   * @param currentUserProvince oggetto da aggiungere (idProvince,name)
   * @return lista di oggetti (idProvince,name)
   */
  public static List<Object[]> addProvinceObject(List<Object[]> provinceList, Province currentUserProvince)
  {
      boolean found=false;
      Iterator<Object[]> itProvList = provinceList.iterator();
      while (itProvList.hasNext())
      {
        Object[] provObj =itProvList.next();
        if ( provObj[0].equals(currentUserProvince.getId()))
          found=true;
      }
    
    if( !found)
    {
      Object[] provinceObj = {currentUserProvince.getId(),currentUserProvince.getName()};
      provinceList.add(provinceObj);
      //Collections.sort(cityList, City.POSITION_NAME_ORDER);
    }
    return provinceList;
  }

  // </editor-fold>
}
