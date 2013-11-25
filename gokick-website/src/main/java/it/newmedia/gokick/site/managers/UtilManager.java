package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.*;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.PlayerRoleInfo;
import it.newmedia.gokick.site.providers.TranslationProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le richieste verso di oggetti presi dal database.
 */
public class UtilManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  /**
   *
   */
  public static final int RANDOM_CODE_LENGHT = 20;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UtilManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private UtilManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   * incrementa di un'unità il campo TOT_USER dellta tb PROVINCE
   * @param idProvince id provincia
   */
  public static void increaseProvinceUserTot(int idProvince)
  {
    Province province;

    try
    {
      province = DAOFactory.getInstance().getProvinceDAO().get(idProvince);
      if (province != null)
      {
        province.setTotUsers(province.getTotUsers() + 1);
      }
      DAOFactory.getInstance().getProvinceDAO().makePersistent(province);
    } catch (Exception e)
    {
      logger.error(e, e);
    }
  }

  /**
   * decrementa di un'unità il campo TOT_USER dellta tb PROVINCE
   * @param idProvince id provincia
   */
  public static void decreaseProvinceUserTot(int idProvince)
  {
    Province province;

    try
    {
      province = DAOFactory.getInstance().getProvinceDAO().get(idProvince);
      if (province != null)
      {
        province.setTotUsers(province.getTotUsers() - 1);
      }
      DAOFactory.getInstance().getProvinceDAO().makePersistent(province);
    } catch (Exception e)
    {
      logger.error(e, e);
    }
  }

  /**
   * incrementa di un'unità il campo TOT_USER dellta tb COUNTRY
   * @param idCountry id nazione
   */
  public static void increaseCountryUserTot(int idCountry)
  {
    Country country;

    try
    {
      country = DAOFactory.getInstance().getCountryDAO().get(idCountry);
      if (country != null)
      {
        country.setTotUsers(country.getTotUsers() + 1);
      }
      DAOFactory.getInstance().getCountryDAO().makePersistent(country);
    } catch (Exception e)
    {
      logger.error(e, e);
    }
  }

  /**
   * decrementa di un'unità il campo TOT_USER dellta tb COUNTRY
   * @param idCountry id nazione
   */
  public static void decreaseCountryUserTot(int idCountry)
  {
    Country country;

    try
    {
      country = DAOFactory.getInstance().getCountryDAO().get(idCountry);
      if (country != null)
      {
        country.setTotUsers(country.getTotUsers() - 1);
      }
      DAOFactory.getInstance().getCountryDAO().makePersistent(country);
    } catch (Exception e)
    {
      logger.error(e, e);
    }
  }

  /**
   * metodo per recuperare l'oggetto user dell'utente che ha dimenticato e richiesto la password
   * @param idUser id User
   * @param checkPassword
   * @return oggetto user con id e codice(checkPassword) indicati
   */
  public static User getUserByIdAndCheckPassword(int idUser, String checkPassword)
  {
    User user;

    try
    {
      user = DAOFactory.getInstance().getUserDAO().getByIdAndCheckPassword(idUser, checkPassword);
    } catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }

    return user;
  }

  /**
   * metodo che controlla l'esatezza dell'associazione utente e password
   * @param password
   * @param idUser id utente
   * @return "true" in caso positivo
   */
  public static boolean checkUserPassword(String password, int idUser)
  {
    boolean exist = false;

    try
    {
      exist = DAOFactory.getInstance().getUserDAO().checkRightUserPassword(password, idUser);
    } catch (Exception e)
    {
      logger.error(e, e);
      return exist;
    }

    return exist;
  }

  /**
   *
   * @param idUser id utente
   * @param checkPending codice stato pending
   * @return utente indicato se esiste lo user associato al codice pending indicato
   */
  public static User getUserByIdAndCheckPending(int idUser, String checkPending)
  {
    User user;

    try
    {
      user = DAOFactory.getInstance().getUserDAO().getByIdAndCheckPending(idUser, checkPending);
    } catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }

    return user;
  }

  /**
   *
   * @param forumNickname nickname scelto per il forum
   * @param idUser id utente
   * @return "false" se il nickname non è già stato utilizzato da altri utenti
   */
  public static boolean checkExistingForumNickname(String forumNickname, int idUser)
  {
    boolean exist = false;

    try
    {
      exist = DAOFactory.getInstance().getUserDAO().checkExistingForumNickname(forumNickname, idUser);
    } catch (Exception e)
    {
      logger.error(e, e);
      return exist;
    }

    return exist;
  }

  /**
   *
   * @param email
   * @param idUser id utente
   * @return "false" se la mail indicata non è già stata utilizzata da altri utenti
   */
  public static boolean checkExistingEmail(String email, int idUser)
  {
    boolean exist = false;

    try
    {
      exist = DAOFactory.getInstance().getUserDAO().checkExistingEmail(email, idUser);
    } catch (Exception e)
    {
      logger.error(e, e);
      return exist;
    }

    return exist;
  }

  /**
   * genera un codice casuale (check pending, check password, invitation)
   * @return un codice randomico di lunghezza = RANDOM_CODE_LENGHT
   */
  public static String getRandomCode()
  {
    return RandomStringUtils.randomAlphanumeric(RANDOM_CODE_LENGHT).toUpperCase();
  }

  /**
   * Return una lista di oggetti VPlayerMatchChallangeArchived che rappresentano i giocatori associati a sfide o partite archiviate
   * @param idUser id dell'utente di cui avere i dati
   * @param startDate data di inizio del periodo interessato
   * @param endDate data di fine del periodo interessato
   * @return una lista di oggetti VPlayerMatchChallangeArchived
   */
  public static List<VPlayerMatchChallangeArchived> getPmcaByParam(int idUser, Date startDate, Date endDate)
  {
    List<VPlayerMatchChallangeArchived> playerMatchChallangeArchivedList = new ArrayList();
    try
    {
      playerMatchChallangeArchivedList = DAOFactory.getInstance().getVPlayerMatchChallangeArchivedDAO().getAllByParams(idUser, startDate, endDate);
    } catch (Exception ex)
    {
      logger.error(ex, ex);
      return playerMatchChallangeArchivedList;
    }
    return playerMatchChallangeArchivedList;
  }

  /**
   *
   * @param language
   * @return lista di ruoli
   */
  public static List<PlayerRoleInfo> getPlayerRoleNoCache(Language language, Cobrand currentCobrand )
  {
    List<PlayerRoleInfo> playerRoleInfoList = null;
    try
    {
      List<PlayerRole> playerRoleList = DAOFactory.getInstance().getPlayerRoleDAO().getAll();
      playerRoleInfoList = new ArrayList<PlayerRoleInfo>();
      for (PlayerRole playerRole : playerRoleList)
      {
        String playerRoleName = TranslationProvider.getTranslationValue(playerRole.getKeyName(), language, currentCobrand);
        PlayerRoleInfo playerRoleInfo = new PlayerRoleInfo(playerRole.getId(), playerRoleName);
        playerRoleInfoList.add(playerRoleInfo);
      }
    }
    catch (Exception ex)
    {
      java.util.logging.Logger.getLogger(UtilManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    return playerRoleInfoList;

  }
  // </editor-fold>
}
