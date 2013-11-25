package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.*;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.infos.SportCenterInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.results.Result;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.newmedia.utils.DateUtil;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni relative ai centri sportivi.
 */
public class SportCenterManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(SportCenterManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private SportCenterManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   * Inserisce un nuovo centro sportivo e invia l'email di notifica in un'unica transazione
   * @param sportCenterToInsert Centro sportivo da inserire
   * @param nameTypePitch  Tipo di Campo
   * @param idCountry id della nazione del centro sportivo
   * @param idProvince id della provincia del centro sportivo
   * @param idCity id della città del centro sportivo
   * @param idUserAuthor id dell'autore dell'inserimento
   * @return Ritorna true in caso di successo altrimenti false
   */
  public static boolean insert(SportCenter sportCenterToInsert, String nameTypePitch, int idCountry, int idProvince, int idCity, int idUserAuthor, Cobrand currentCobrand)
  {
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    try
    {
      sportCenterToInsert.setApproved(false);
      sportCenterToInsert.setCap("");
      sportCenterToInsert.setDescription("");
      sportCenterToInsert.setTransports("");
      sportCenterToInsert.setPromo("");
      sportCenterToInsert.setEnabled(false);
      sportCenterToInsert.setCreated(new Date());
      User userAuthor = DAOFactory.getInstance().getUserDAO().load(idUserAuthor);
      sportCenterToInsert.setUserAuthor(userAuthor);
      Country country = DAOFactory.getInstance().getCountryDAO().load(idCountry);
      sportCenterToInsert.setCountry(country);
      Province province = DAOFactory.getInstance().getProvinceDAO().load(idProvince);
      sportCenterToInsert.setProvince(province);
      City city = DAOFactory.getInstance().getCityDAO().load(idCity);
      sportCenterToInsert.setCity(city);

      DAOFactory.getInstance().getSportCenterDAO().makePersistent(sportCenterToInsert);

      Result<Boolean> rEmailSend = EmailManager.sendSportCenterInsertedEmail(sportCenterToInsert, nameTypePitch, InfoProvider.getDefaultLanguage(), currentCobrand,idUserAuthor );
      if (!rEmailSend.isSuccessNotNull() || !rEmailSend.getValue())
      {
        HibernateSessionHolder.rollbackTransaction();
        return false;
      }

      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));

    }
    catch (Exception e)
    {
      logger.error(e, e);
      HibernateSessionHolder.rollbackTransaction();
      return false;
    }

    return true;
  }

  /**
   *
   * @param id del centro sportivo richiesto
   * @return il centro sportivo cercato
   */
  public static SportCenter getSportCenter(int id)
  {
    try
    {
      return DAOFactory.getInstance().getSportCenterDAO().get(id);

    }
    catch (Exception ex)
    {
      logger.error("Error get Sport Center", ex);
    }
    return null;
  }

  /**
   *
   * @param idSportCenter id centro sportivo
   * @return l'oggetto sportCenterInfo del centro sportivo indicato e lo memorizza in cache nel caso non ci fosse
   */
  public static SportCenterInfo getSportCenterInfo(int idSportCenter)
  {
    return InfoProvider.getSportCenterInfo(idSportCenter);
  }

  /**
   *
   * @param name nome centro sportivo
   * @param idCountry id nazione centro sportivo
   * @param idProvince id provincia centro sportivo
   * @param idCity id città centro sportivo
   * @param idMatchType id tipo di partita (a 5,7,11 ..)
   * @param idPitchCover id tipo copertura campo
   * @return lista di centri sportivi secondo i paramteri indicati
   */
  public static List<SportCenterInfo> searchSportCenterInfo(String name, int idCountry, int idProvince, int idCity, int idMatchType, int idPitchCover)
  {
    ArrayList<SportCenterInfo> result = new ArrayList<SportCenterInfo>();
    try
    {
      List<Integer> idList = DAOFactory.getInstance().getSportCenterDAO().getBySearchParameters(name, idCountry, idProvince, idCity, idMatchType, idPitchCover);
      for (Integer id : idList)
      {
        SportCenterInfo sportCenterInfo = InfoProvider.getSportCenterInfo(id);
        result.add(sportCenterInfo);
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return result;
    }
    return result;
  }

  /**
   *
   * @param name nome centro sportivo
   * @param idCountry id nazione centro sportivo
   * @param idProvince id provincia centro sportivo
   * @param idCity id città centro sportivo
   * @param idMatchType id tipo di partita (a 5,7,11 ..)
   * @param idPitchCover id tipo copertura campo
   * @return lista di centri sportivi secondo i paramteri indicati
   */
  public static List<SportCenterInfo> getAllSportCenterInfo()
  {
    ArrayList<SportCenterInfo> result = new ArrayList<SportCenterInfo>();
    try
    {
      List<Integer> idList = DAOFactory.getInstance().getSportCenterDAO().getAllId();
      for (Integer id : idList)
      {
        SportCenterInfo sportCenterInfo = InfoProvider.getSportCenterInfo(id);
        result.add(sportCenterInfo);
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return result;
    }
    return result;
  }

  /**
   *
   * @param idCountry id nazione ( 0 per estendere la ricerca a tutte)
   * @param idProvince id provincia ( 0 per estendere la ricerca a tutte)
   * @param idCity id città ( 0 per estendere la ricerca a tutte)
   * @return lista di sportCenter ENABLED e approvati
   */
  public static List<SportCenter> getAll(int idCountry, int idProvince, int idCity)
  {
    try
    {
      return DAOFactory.getInstance().getSportCenterDAO().getAll(idCountry, idProvince, idCity);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<SportCenter>();
    }
  }

  /**
   * 
   * @param idCountry id nazione ( 0 per estendere la ricerca a tutte)
   * @param idProvince id provincia ( 0 per estendere la ricerca a tutte)
   * @param idCity id città ( 0 per estendere la ricerca a tutte)
   * @return lista di oggetti (id SportCenter, nome ) secondo i parametri geografici richiesti
   */
  public static List<Object[]> getAllProjection(int idCountry, int idProvince, int idCity)
  {
    try
    {
      return DAOFactory.getInstance().getSportCenterDAO().getAllProjection(idCountry, idProvince, idCity);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Object[]>();
    }
  }

  // </editor-fold>
}
