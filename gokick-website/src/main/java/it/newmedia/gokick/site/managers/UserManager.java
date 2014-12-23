package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.enums.*;
import it.newmedia.gokick.data.hibernate.beans.*;
import it.newmedia.gokick.data.hibernate.dao.CityDAO;
import it.newmedia.gokick.data.hibernate.dao.UserDAO;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiPlayerInfo;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.net.HttpConnection;
import it.newmedia.results.Result;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.newmedia.utils.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni fatte dall'utente.
 */
public class UserManager
{

  public static final int HOW_MANY_AUTOCOMPLETE_RESULTS = 5;
  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private UserManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public static boolean insert(User userToInsert, int idCountry, int idProvince, int idCity, Language language, Cobrand currentCobrand, String invitationCode)
  {
    return insert(userToInsert, idCountry, idProvince, idCity, language, currentCobrand, invitationCode, "", "");
  }
  
  /**
   * salva user (registrazione)
   * @param userToInsert utente da slavare
   * @param idCountry id nazione
   * @param idProvince id provincia
   * @param idCity id città
   * @param language
   * @param invitationCode codice invito ricevuto (se richiesto)
   * @return "true" in caso positivo
   */
  public static boolean insert(User userToInsert, int idCountry, int idProvince, int idCity, Language language, Cobrand currentCobrand, String invitationCode, String facebookIdUser, String facebookAccessToken)
  {
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    int idUserToRemoveInfo = 0;
    try
    {
      DAOFactory factory = DAOFactory.getInstance();

      //Imposto il cobrand relativo al sito sul quale l'utente si sta iscrivendo
      userToInsert.setCobrandCode(currentCobrand.getCode());

      if(StringUtils.isNotBlank(facebookIdUser))
      {
        userToInsert.setFacebookIdUser(facebookIdUser);
      }
      if(StringUtils.isNotBlank(facebookAccessToken))
      {
        userToInsert.setFacebookAccessToken(facebookAccessToken);
      }

      userToInsert.setFirstName(WordUtils.capitalizeFully(userToInsert.getFirstName()));
      userToInsert.setLastName(WordUtils.capitalizeFully(userToInsert.getLastName()));
      userToInsert.setEnumUserStatus(EnumUserStatus.Pending);
      userToInsert.setCheckPending(UtilManager.getRandomCode());
      userToInsert.setCreated(new Date());
      
      //<editor-fold defaultstate="collapsed" desc="SETTING MAIL PREFERENCES">
      userToInsert.setAlertOnSquadRequest(true);
      userToInsert.setAlertOnSquadInsert(false);
      userToInsert.setAlertOnRegistrationStart(true);
      userToInsert.setAlertOnChange(true);
      userToInsert.setAlertOnSquadOutAccepted(true);
      userToInsert.setAlertOnReportCreated(true);

      userToInsert.setFacebookPostOnMatchCreation(true);
      userToInsert.setFacebookPostOnMatchRecorded(true);
      userToInsert.setFacebookPostOnMatchRegistration(true);
      //</editor-fold>
      
      userToInsert.setMarketEnabled(true);
      Country country = factory.getCountryDAO().load(idCountry);
      //La nazionalità di default è la stessa di dove si gioca a calcio
      userToInsert.setNationalityCountry(country);
      userToInsert.setCountry(country);
      Province province = factory.getProvinceDAO().load(idProvince);
      userToInsert.setProvince(province);
      City city = factory.getCityDAO().load(idCity);
      userToInsert.setCity(city);

      PlayerRole playerRole = null;
      playerRole = factory.getPlayerRoleDAO().load(userToInsert.getPlayerRole().getId());
      userToInsert.setPlayerRole(playerRole);

      //TODO: Non va bene cablare qui la condizione di default!
      //la condizione di default è "media"
      PhysicalCondition physicalCondition = new PhysicalCondition();
      physicalCondition.setId(3);
      userToInsert.setPhysicalCondition(physicalCondition);

      //Numero di inviti di default associati ad un nuovo iscritto
      userToInsert.setMaxInvitations(AppContext.getInstance().getInvitationsInitial());

      Squad squadToInsert = Squad.createDefault();
      squadToInsert.setUser(userToInsert);
      userToInsert.getSquads().add(squadToInsert);


      UserSquad userSquad = new UserSquad();
      userSquad.setUser(userToInsert);
      userSquad.setSquad(squadToInsert);
      userSquad.setEnumUserSquadStatus(EnumUserSquadStatus.Confirmed);
      userSquad.setOwner(true);

      squadToInsert.getUserSquadList().add(userSquad);

      if (StringUtils.isNotBlank(invitationCode))
      {
        SpecialInvitation specialInvitation = UserManager.getSpecialInvitationByCode(invitationCode);
        if (specialInvitation != null)
        {
          userToInsert.setInvited_by(invitationCode);
        }
        else
        {
          UserInvitation userInvitation = factory.getUserInvitationDAO().getByCode(invitationCode);
          userInvitation.setUser(userToInsert);
          userToInsert.setInvited_by(userInvitation.getUserOwner().getId().toString());
          idUserToRemoveInfo = userInvitation.getUserOwner().getId();
        }
      }

      userToInsert = factory.getUserDAO().makePersistent(userToInsert);

      Result<Boolean> rEmailSend = EmailManager.sendUserRegistrationEmail(userToInsert, language, currentCobrand);
      if (!rEmailSend.isSuccessNotNull() || !rEmailSend.getValue())
      {
        HibernateSessionHolder.rollbackTransaction();
        return false;
      }

      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));


      User userToReload = factory.getUserDAO().login(userToInsert.getEmail(), userToInsert.getPassword());
      UserContext.getInstance().reload(userToReload.getId());
      InfoProvider.removeUserInfo(userToReload.getId());
      InfoProvider.removeUserInfo(idUserToRemoveInfo);
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
   * aggiorna un utente
   * @param userModified user aggiornato
   * @param idCoutry  nazione
   * @param idProvince id provincia
   * @param idCity id città
   * @param newPassword nuova password (se cambiata)
   * @return "true" in caso positivo
   */
  public static boolean update(User userModified, int idCoutry, int idProvince, int idCity, String newPassword)
  {
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    StringBuilder sbError = new StringBuilder();
    try
    {
      CityDAO cityDAO = DAOFactory.getInstance().getCityDAO();

      User usertoUpdate = DAOFactory.getInstance().getUserDAO().get(userModified.getId());

      if (usertoUpdate == null)
      {
        logger.warn("Cannot retrieve user to update from database, id: " + userModified.getId());
        HibernateSessionHolder.rollbackTransaction();
        return false;
      }

      usertoUpdate.setId(userModified.getId());
      String[] firstNameArr = StringUtils.split(userModified.getFirstName());
      String firstName = "";
      for (int i = 0; i < firstNameArr.length; i++)
      {
        firstName = (firstName + " " + firstNameArr[i]).trim();
      }
      usertoUpdate.setFirstName(WordUtils.capitalizeFully(firstName));

      String[] lastNameArr = StringUtils.split(userModified.getLastName());
      String lastName = "";
      for (int i = 0; i < lastNameArr.length; i++)
      {
        lastName = (lastName + " " + lastNameArr[i]).trim();
      }
      usertoUpdate.setLastName(WordUtils.capitalizeFully(lastName));

      usertoUpdate.setSex(userModified.getSex());
      usertoUpdate.setCap(userModified.getCap());
      usertoUpdate.setForumNickname(userModified.getForumNickname());
      usertoUpdate.setEmail(userModified.getEmail());
      usertoUpdate.setMobile(userModified.getMobile());
      if (!newPassword.equals(""))
      {
        usertoUpdate.setPassword(newPassword);
      }
      if (!usertoUpdate.getCountry().getId().equals(idCoutry))
      {
        Country country = DAOFactory.getInstance().getCountryDAO().load(idCoutry);
        usertoUpdate.setCountry(country);
        UtilManager.increaseCountryUserTot(usertoUpdate.getCountry().getId());
      }
      if (!usertoUpdate.getProvince().getId().equals(idProvince))
      {
        Province province = DAOFactory.getInstance().getProvinceDAO().load(idProvince);
        usertoUpdate.setProvince(province);
        UtilManager.increaseProvinceUserTot(usertoUpdate.getProvince().getId());
      }
      if (!usertoUpdate.getCity().getId().equals(idCity))
      {
        City city = cityDAO.load(idCity);
        usertoUpdate.setCity(city);
        cityDAO.increaseTotUsers(usertoUpdate.getCity().getId());
      }

      DAOFactory.getInstance().getUserDAO().makePersistent(usertoUpdate);

      UserContext.getInstance().reload(usertoUpdate.getId());

      // <editor-fold defaultstate="collapsed" desc="-- Update Image List--">
      List<PictureCard> imageList = DAOFactory.getInstance().getPictureCardDAO().getAllByIdUser(usertoUpdate.getId());
      for (PictureCard image : imageList)
      {
        String filePathMiniature = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), image.getFilename());

        // <editor-fold defaultstate="collapsed" desc="-- CreatePicConnection --">
        if (new File(filePathMiniature).exists())
        {
          String playerName = usertoUpdate.getFirstName() + " " + usertoUpdate.getLastName();

          Map paramsMap = new HashMap();
          paramsMap.put("cardPath", filePathMiniature);
          paramsMap.put("name", playerName);
          HttpConnection httpConn = new HttpConnection(AppContext.getInstance().getEditNamePicUrl());
          httpConn.doPost(paramsMap);

          if (httpConn.getResponseCode() != HttpConnection.HTTP_RESPONSECODE_200_OK)
          {
            logger.error("Error editing pic name from php service (http status != 200) for " + filePathMiniature + "[" + playerName + "]");
          }
          if (httpConn.getResponse().equals("0"))
          {
            logger.error("Error editing pic name from php service (response is 0) for " + filePathMiniature + "[" + playerName + "]");
          }
          if (httpConn.getResponse().equals("1"))
          {
            logger.info("Name edited correctly from php service for " + filePathMiniature + "[" + playerName + "]");
          }
        }
        // </editor-fold>

      }
      // </editor-fold>

      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));

      //rimozione dati in cache che dipendono da questo update
      InfoProvider.removeUserInfo(usertoUpdate.getId());

      List<Integer> idMatchListToRemove = MatchManager.getOwnerOrReporter(usertoUpdate.getId());
      for (int idMatchToRemove : idMatchListToRemove)
      {
        InfoProvider.removeMatchInfo(idMatchToRemove);
      }

    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return false;
    }
    return true;
  }

   /**
   * aggiorna un utente
   * @param userModified user aggiornato
   * @return "true" in caso positivo
   */
  public static boolean update(User userModified)
  {
    try
    {
      DAOFactory.getInstance().getUserDAO().makePersistent(userModified);
      UserContext.getInstance().reload(userModified.getId());

      //rimozione dati in cache che dipendono da questo update
      InfoProvider.removeUserInfo(userModified.getId());
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }
    return true;
  }


  /**
   *aggiorna le preferenze dell'utente riguardo (notifiche,all'anonimato, etc.)
   * @param userModified user aggiornato
   * @param squadModified squadra aggiornata
   * @return "true" in caso positivo
   */
  public static boolean updateUserPreference(User userModified, Squad squadModified)
  {
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    try
    {
      User userToUpdate = DAOFactory.getInstance().getUserDAO().get(UserContext.getInstance().getUser().getId());

      userToUpdate.setAlertOnMessages(userModified.getAlertOnMessages());
      userToUpdate.setAlertOnWakeup(userModified.getAlertOnWakeup());
      userToUpdate.setAlertOnChange(userModified.getAlertOnChange());
      userToUpdate.setAlertOnSquadRequest(userModified.getAlertOnSquadRequest());
      userToUpdate.setAlertOnRegistrationStart(userModified.getAlertOnRegistrationStart());
      userToUpdate.setAlertOnReportCreated(userModified.getAlertOnReportCreated());
      userToUpdate.setAlertOnSquadInsert(userModified.getAlertOnSquadInsert());
      userToUpdate.setAlertOnSquadOutAccepted(userModified.getAlertOnSquadOutAccepted());
      userToUpdate.setAnonymousEnabled(userModified.getAnonymousEnabled());
      userToUpdate.setNewsletterEnabled(userModified.getNewsletterEnabled());

      //Facebook
      userToUpdate.setFacebookPostOnMatchCreation(userModified.isFacebookPostOnMatchCreation());
      userToUpdate.setFacebookPostOnMatchRecorded(userModified.isFacebookPostOnMatchRecorded());
      userToUpdate.setFacebookPostOnMatchRegistration(userModified.isFacebookPostOnMatchRegistration());

      Squad squadToUpdate = userToUpdate.getFirstSquad();
      if (squadToUpdate != null)
      {
        squadToUpdate.setHiddenEnabled(squadModified.getHiddenEnabled());
      }
      else
      {
        logger.error(String.format("Error on data about iduser %s: User must have almost one squad!!", userToUpdate.getId()));
      }

      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));


      InfoProvider.removeUserInfo(userToUpdate.getId());
      InfoProvider.removeSquadInfo(squadToUpdate.getId());
      UserContext.getInstance().reload(userToUpdate.getId());
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error("erroe savin preferences in userPreferenceAction", e);
      return false;
    }
    return true;
  }

  /**
   * cancella utente
   * @param idUser id utente da eliminare
   * @return "true" in caso positivo
   */
  public static boolean cancel(int idUser)
  {
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    try
    {
      User usertoToCancel = DAOFactory.getInstance().getUserDAO().get(idUser);
      //setto stato user a DELETED e cancello i vari campi di interesse
      usertoToCancel.setEnumUserStatus(EnumUserStatus.Deleted);
      usertoToCancel.setFirstName(Constants.USER_DELETED_FIRSTNAME);
      usertoToCancel.setLastName(Constants.USER_DELETED_LASTNAME);
      usertoToCancel.setPassword(Constants.USER_DELETED_NULLFIELD);
      usertoToCancel.setEmail(Constants.USER_DELETED_EMAIL);
      usertoToCancel.setAddress(Constants.USER_DELETED_NULLFIELD);
      usertoToCancel.setMobile(Constants.USER_DELETED_NULLFIELD);
      usertoToCancel.setTelephone(Constants.USER_DELETED_NULLFIELD);
      usertoToCancel.setLastIP(Constants.USER_DELETED_NULLFIELD);
      usertoToCancel.setBirthDay(null);
      usertoToCancel.setAnonymousEnabled(false);

      // <editor-fold defaultstate="collapsed" desc="-- remove Picture card from DB and fisically--"  >
      List<PictureCard> picList = getPictureCards(idUser,
              EnumPictureCardStatus.Current,
              EnumPictureCardStatus.Pending,
              EnumPictureCardStatus.Refused);

      for (PictureCard pictureCard : picList)
      {
        if (pictureCard != null)
        {
          //cancello file
          String filenameMiniature = pictureCard.getFilename();
          String filenameAvatar = pictureCard.getFilename().replace(".", "_a.");
          String filePathMiniature = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), filenameMiniature);
          String filePathAvatar = String.format("%1$s%2$s", AppContext.getInstance().getAvatarPath(), filenameAvatar);
          File file;
          try
          {
            //cancello miniatura
            file = new File(filePathMiniature);
            if (file.exists())
            {
              FileUtils.forceDelete(file);
            }
            //cancello avatar
            file = new File(filePathAvatar);
            if (file.exists())
            {
              FileUtils.forceDelete(file);
            }
          }
          catch (IOException iOException)
          {
            logger.error("error deleting picture", iOException);
          }
          UserManager.removePictureCard(pictureCard);
        }
      }
      // </editor-fold>

      // <editor-fold defaultstate="collapsed" desc="-- remove Statistic--"  >
      StatisticManager.deleteUserStatistics(idUser);
      // </editor-fold>

      //      <editor-fold defaultstate="collapsed" desc="-- recupero squadra user ed elimino eventuali amici (non utilizzato) --"  >
//      Squad squad = usertoToCancel.getFirstSquad();
//      Iterator<UserSquad> userSquadIterator = squad.getUserSquadList().iterator();
//      while(userSquadIterator.hasNext())
//      {
//        UserSquad userSquad = userSquadIterator.next();
//        if (!userSquad.getOwner())
//        {
//          userSquadIterator.remove();
//        }
//      }
      // </editor-fold>

      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));

      InfoProvider.cancelCache();
      //InfoProvider.removeUserInfo(usertoToCancel.getId());
      //TODO chiamata per cancellare tutta la cache
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error("error deleting user", e);
      return false;
    }
    return true;
  }

  /**
   * aggiorna utente (scheda personale)
   * @param userModified user aggiornato
   * @param idBirthdayCountry id nazione nascita
   * @param idBirthdayProvince id provincia nascita
   * @param idBirthdayCity id città nascita
   * @param birthToModify data di nascita
   * @param idNationalityCountry id nazionalità
   * @return "true" in caso positivo
   */
  public static boolean userPlayerUpdate(User userModified, int idBirthdayCountry, int idBirthdayProvince, int idBirthdayCity, Date birthToModify, int idNationalityCountry)
  {
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    try
    {
      DAOFactory factory = DAOFactory.getInstance();
      Country country = null;
      Province province = null;
      City city = null;

      User usertoUpdate = factory.getUserDAO().get(userModified.getId());
      if (usertoUpdate == null)
      {
        HibernateSessionHolder.rollbackTransaction();
        return false;
      }

      if (birthToModify != null)
      {
        usertoUpdate.setBirthDay(birthToModify);
      }

      usertoUpdate.setPlayerTitle(userModified.getPlayerTitle());
      usertoUpdate.setPlayerMainFeature(userModified.getPlayerMainFeature());
      if (userModified.getPlayerWeight() != null && userModified.getPlayerWeight() > 0)
      {
        usertoUpdate.setPlayerWeight(userModified.getPlayerWeight());
      }
      if (userModified.getPlayerHeight() != null && userModified.getPlayerHeight() > 0)
      {
        usertoUpdate.setPlayerHeight(userModified.getPlayerHeight());
      }

      PlayerRole playerRole = null;
      if (userModified.getPlayerRole().isIdGreatherThanZero())
      {
        playerRole = factory.getPlayerRoleDAO().load(userModified.getPlayerRole().getId());
      }
      usertoUpdate.setPlayerRole(playerRole);

      PlayerFoot playerFoot = null;
      if (userModified.getPlayerFoot().isIdGreatherThanZero())
      {
        playerFoot = factory.getPlayerFootDAO().load(userModified.getPlayerFoot().getId());
      }
      usertoUpdate.setPlayerFoot(playerFoot);

      FootballTeam footballTeam = null;
      if (userModified.getFootballTeam().isIdGreatherThanZero())
      {
        footballTeam = factory.getFootballTeamDAO().load(userModified.getFootballTeam().getId());
      }
      usertoUpdate.setFootballTeam(footballTeam);

      if (userModified.getPlayerShirtNumber() != null && userModified.getPlayerShirtNumber() > 0)
      {
        usertoUpdate.setPlayerShirtNumber(userModified.getPlayerShirtNumber());
      }

      if (userModified.getPhysicalCondition().isIdGreatherThanZero())
      {
        usertoUpdate.setPhysicalCondition(userModified.getPhysicalCondition());
      }
      usertoUpdate.setPhysicalConditionDetail(userModified.getPhysicalConditionDetail());

      usertoUpdate.setInfoAnnounce(userModified.getInfoAnnounce());
      usertoUpdate.setInfoDream(userModified.getInfoDream());
      usertoUpdate.setInfoFavouritePlayer(userModified.getInfoFavouritePlayer());
      usertoUpdate.setInfoHobby(userModified.getInfoHobby());
      usertoUpdate.setMarketEnabled(userModified.getMarketEnabled());

      country = idBirthdayCountry > 0 ? factory.getCountryDAO().load(idBirthdayCountry) : null;
      usertoUpdate.setBirthdayCountry(country);

      province = idBirthdayProvince > 0 ? factory.getProvinceDAO().load(idBirthdayProvince) : null;
      usertoUpdate.setBirthdayProvince(province);

      city = idBirthdayCity > 0 ? factory.getCityDAO().load(idBirthdayCity) : null;
      usertoUpdate.setBirthdayCity(city);

      country = idNationalityCountry > 0 ? factory.getCountryDAO().load(idNationalityCountry) : null;
      usertoUpdate.setNationalityCountry(country);

      factory.getUserDAO().makePersistent(usertoUpdate);
      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));

      InfoProvider.removeUserInfo(usertoUpdate.getId());
      UserContext.getInstance().reload(usertoUpdate.getId());
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e.toString(), e);
      return false;
    }
    return true;
  }

  /**
   * effettua login utente
   * @param email
   * @param password
   * @param rememberMe indica se creare cookie per accesso utente
   * @return user se valido, altrimenti "null"
   */
  public static User login(String email, String password, boolean rememberMe)
  {
    User currentUser = null;
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    try
    {
      currentUser = DAOFactory.getInstance().getUserDAO().login(email, password);
      if (currentUser == null || currentUser.getEnumUserStatus().equals(EnumUserStatus.Pending))
      {
        return currentUser;
      }

      currentUser = updateLastInfo(currentUser, request);

      String idFacebook = UserContext.getInstance().getFacebookIdUserTemporary();
      if (StringUtils.isNotBlank(idFacebook))
      {
        currentUser.setFacebookIdUser(idFacebook);
        UserContext.getInstance().setFacebookIdUserTemporary("");
      }
      
      String accessTokenFacebook = UserContext.getInstance().getTemporaryAccessTokenFacebook();
      if (StringUtils.isNotBlank(accessTokenFacebook))
      {
        currentUser.setFacebookAccessToken(accessTokenFacebook);
        UserContext.getInstance().setTemporaryAccessTokenFacebook("");
      }

      currentUser = DAOFactory.getInstance().getUserDAO().makePersistent(currentUser);

      //TODO: migliorare spostando in un manager
      Audit audit = new Audit();
      audit.setCobrandCode(UserContext.getInstance().getCurrentCobrand().getCode());
      audit.setEnumAuditType(EnumAuditType.UserLogin);
      audit.setIdUser(currentUser.getId());
      audit.setIpAddress(currentUser.getLastIP());
      audit.setRefDateTime(currentUser.getLastLogin());
      DAOFactory.getInstance().getAuditDAO().makePersistent(audit);

      UserContext.getInstance().login(currentUser);

      if (rememberMe)
      {
        UserContext.getInstance().rememberMe(response, currentUser.getId(), currentUser.getPassword());
      }
      else
      {
        UserContext.getInstance().forgetMe(response);
      }
      UserContext.getInstance().rememberExtApp(response, currentUser.getId(), currentUser.getPassword(), UserContext.getInstance().getLanguage().getLanguage());
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }

    return currentUser;
  }

  /**
   * invia mail a seguito di richiesta password dimenticata
   * @param email
   * @param language
   * @return "true" in caso positivo
   */
  public static boolean changePasswordRequest(String email, Language language, Cobrand currentCobrand)
  {
    try
    {
      User currentUser = DAOFactory.getInstance().getUserDAO().getByEmail(email);
      if (currentUser == null)
      {
        return false;
      }

      currentUser.setCheckPassword(UtilManager.getRandomCode());
      currentUser.setCheckPasswordExpire(DateUtils.addHours(new Date(), AppContext.getInstance().getCheckPasswordExpireAfterHours()));

      DAOFactory.getInstance().getUserDAO().makePersistent(currentUser);

      Result<Boolean> rEmailSend = EmailManager.sendUserChangePasswordRequestEmail(currentUser, language, currentCobrand);
      if (!rEmailSend.isSuccessNotNull() || !rEmailSend.getValue())
      {
        return false;
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }

    return true;
  }

  /**
   * cambio password
   * @param idUser id utente
   * @param newPassword nuova password
   * @return "true" in caso positivo
   */
  public static boolean changePassword(int idUser, String newPassword)
  {
    try
    {
      User currentUser = DAOFactory.getInstance().getUserDAO().get(idUser);
      if (currentUser == null)
      {
        return false;
      }
      HttpServletRequest request = ServletActionContext.getRequest();
      currentUser = updateLastInfo(currentUser, request);
      currentUser.setPassword(newPassword);
      currentUser.setCheckPassword("");
      currentUser.setCheckPasswordExpire(null);
      currentUser = DAOFactory.getInstance().getUserDAO().makePersistent(currentUser);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return false;
    }
    return true;
  }

  /**
   * controlla che sia permesso vedere la figurina dell'utente (non anonimo)
   * @param id
   * @return "true" in caso positivo
   */
  public static boolean viewPictureCardEnabled(int id)
  {
    try
    {
      User currentUser = DAOFactory.getInstance().getUserDAO().getByIdAndNotAnonymous(id);
      if (currentUser == null)
      {
        return false;
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return false;
    }
    return true;
  }

  /**
   * recupera user da email
   * @param email
   * @return oggetto User
   */
  public static User getByEmail(String email)
  {
    try
    {
      return DAOFactory.getInstance().getUserDAO().getByEmail(email);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  /**
   * recupera User da id
   * @param id id utente
   * @return User
   */
  public static User getById(int id)
  {
    try
    {
      return DAOFactory.getInstance().getUserDAO().get(id);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  /**
   * aggiorna le informazioni di log dell'oggetto utente prendendole dalla request
   * @param currentUser
   * @param request
   * @return User
   */
  private static User updateLastInfo(User currentUser, HttpServletRequest request)
  {
    currentUser.setLastIP(request.getRemoteAddr());
    currentUser.setPrevLogin(currentUser.getLastLogin());
    currentUser.setLastLogin(new Date());
    return currentUser;
  }

  /**
   *
   * @param idUser id utente
   * @param enumPictureCardStatuses Enum che rappresenta gli stati della figurina (CURRENT,PENDING,REFUSED)
   * @return lista di figurine nello stato cercato dell'utente indicato
   */
  public static List<PictureCard> getPictureCards(int idUser, EnumPictureCardStatus... enumPictureCardStatuses)
  {
    try
    {
      return DAOFactory.getInstance().getPictureCardDAO().getByPicStatuses(idUser, enumPictureCardStatuses);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  /**
   *
   * @param idUser id utente
   * @return l'ultima figurina creata dall'utente
   */
  public static PictureCard getLastPictureCard(int idUser)
  {
    try
    {
      return DAOFactory.getInstance().getPictureCardDAO().getLastPic(idUser);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  /**
   *
   * @param fileName
   * @return figurina dal nome file
   */
  public static PictureCard getByFileName(String fileName)
  {
    PictureCard pic = null;
    try
    {
      pic = DAOFactory.getInstance().getPictureCardDAO().getByFileName(fileName);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
    }
    return pic;
  }

  /**
   * rimuove la figurina indicata
   * @param pictureCard
   * @return "true" in caso positivo
   */
  public static boolean removePictureCard(PictureCard pictureCard)
  {
    PictureCard picCtrl;
    int idPicCtrl = pictureCard.getId();
    try
    {
      DAOFactory.getInstance().getPictureCardDAO().makeTransient(pictureCard);
      picCtrl = DAOFactory.getInstance().getPictureCardDAO().get(idPicCtrl);
      if (picCtrl == null)
      {
        return true;
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
    }
    return false;
  }

  /**
   *
   * @param howMany   numero user da restituire
   * @return  user con picture in current status, anche utenti resi anonimi
   */
  public static List<Integer> getAllWithCurrentPic(int howMany)
  {

    return getAllWithCurrentPic(howMany, false);
  }

  /**
   *
   * @param howMany numero user da restituire
   * @param noAnonimous "true" se si vogliono solo utenti che non siano anonimi
   * @return lista di id utente con figurina in current status
   */
  public static List<Integer> getAllWithCurrentPic(int howMany, boolean noAnonimous)
  {
    List<Integer> userWithPic = new ArrayList<Integer>();
    try
    {
      userWithPic = DAOFactory.getInstance().getUserDAO().getAllWithCurrentPic(howMany, noAnonimous);
      return userWithPic;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
    }
    return userWithPic;
  }

  /**
   *
   * @param howMany se valorizzato limita la lista restituita
   * @param matchOrganizedNum limite minimo di match organizzati
   * @return lista id utente con figurina in stato current ,organizzatori con piu' di 2 partite organizzate
   * @throws Exception
   */
  public static List<Integer> getOrganizerWithCurrentPic(Integer howMany,int matchOrganizedNum)
  {
    List<Integer> organizerWithPic = new ArrayList<Integer>();
    try
    {
      organizerWithPic = DAOFactory.getInstance().getUserDAO().getOrganizerWithCurrentPic(howMany, matchOrganizedNum);
      return organizerWithPic;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
    }
    return organizerWithPic;
  }

  /**
   *
   * @param idSquad id squadra proprietario
   * @return lista di utenti (amici) a cui notificare l'apertura registrazione partite
   */
  public static List<User> getAllFriendsToNotifyMatchStart(int idSquad)
  {
    List<User> friendsList = new ArrayList<User>();
    try
    {
      friendsList = DAOFactory.getInstance().getUserDAO().getAllFriendsToNotifyMatchStart(idSquad);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return friendsList;
    }
    return friendsList;
  }
  /**
   *
   * @param limit numero di utenti da caricare
   * @return lista di utenti da invaire la mail di wakeUp
   */
  public static List<User> findUserToWakeUp(int limit)
  {
    List<User> friendsList = new ArrayList();
    try
    {
      friendsList = DAOFactory.getInstance().getUserDAO().findUserToWakeUp(limit);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return friendsList;
    }
    return friendsList;
  }

  /**
   *
   * @param idProvince id Provincia filtro
   * @return lista di 'id utente' di quella provincia
   */
  public static List<Integer> getAllOnMarket(Integer idProvince)
  {
    List<Integer> idUserList = new ArrayList<Integer>();
    try
    {
      idUserList = DAOFactory.getInstance().getUserDAO().getByIdProvinceAndOpenMarket(idProvince);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
    }
    return idUserList;
  }

  /**
   *
   * @param
   * @return aggiorna il campo PLAYED_MATCHES (tot partite giocate)
   */
  public static boolean updateAllPlayedMatches()
  {
    UserDAO userDao = DAOFactory.getInstance().getUserDAO();
    try
    {
      List<User> userAllList = userDao.getAllEnabled();
      for (User user : userAllList)
      {
        user.setPlayedMatches(countPlayed(user.getId()));
        userDao.makePersistent(user);
      }
    }
    catch (Exception ex)
    {
      logger.error("error updating  User.playedMatches", ex);
      return false;
    }
    return true;

  }

  /**
   *
   * @param idCurrentUser user d'interesse
   * @return numero partite giocate,0 in caso di erore o nessuna giocata
   */
  public static int countPlayed(int idCurrentUser)
  {
    List<Integer> idMatchList;
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getPlayed(idCurrentUser);
      if (idMatchList == null)
      {
        return 0;
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
    return idMatchList.size();
  }

  public static List<Integer> getUsersLight(String firstName,
          String lastName,
          int idCountry,
          int idProvince,
          int idCity,
          List<Integer> roles,
          String firstOrder,
          String secondOrder)
  {
    List<Integer> idUserList = new ArrayList<Integer>();
    try
    {
      idUserList = DAOFactory.getInstance().getUserDAO().getSearchLightOrdered(firstName, lastName, idCountry, idProvince, idCity, roles, firstOrder, secondOrder);
    }
    catch (Exception ex)
    {
      logger.error("error retriving idUserList", ex);
    }
    return idUserList;
  }

  public static List<Integer> getIdUserNotInMatch(int idMatch, String name, int idOwnerSquad)
  {
    List<Integer> idUserList = new ArrayList<Integer>();
    try
    {
      List<Object[]> objList = DAOFactory.getInstance().getUserDAO().getIdUserNotInMatch(idMatch, name, idOwnerSquad);
      for (Object[] obj : objList)
      {
        idUserList.add((Integer) obj[0]);
      }
    }
    catch (Exception ex)
    {
      logger.error("error retriving idUserList", ex);
    }
    return idUserList;
  }

  public static List<UserInfo> getUserInfosForAutoComplete(int idMatch, int idOwnerSquad, String name, int howMany, User userOwner)
  {
    List<UserInfo> userInfoList = new ArrayList<UserInfo>();
    List<Integer> idFriendsList = new ArrayList<Integer>();
    List<GuiPlayerInfo> guiPlayerInfoList = new ArrayList<GuiPlayerInfo>();
    MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);
    try
    {
      // <editor-fold defaultstate="collapsed" desc="lista amici">
      idFriendsList = DAOFactory.getInstance().getUserDAO().getIdFriends(idOwnerSquad, name, howMany);
      guiPlayerInfoList = GuiManager.prepareGuiPlayerFriendsInfoList(matchInfo, idFriendsList);
      for (GuiPlayerInfo guiPlayer : guiPlayerInfoList)
      {
        UserInfo userInfo = guiPlayer.getUserInfo();
        if (userInfo.isAnonymousEnabled() && StringUtils.containsIgnoreCase(userInfo.getCompleteSurname(), name))
        {
          continue;
        }
        if (guiPlayer.getStatus().equals(EnumPlayerStatus.Undefined)
                || guiPlayer.getStatus().equals(EnumPlayerStatus.UserCalled)
                || guiPlayer.getStatus().equals(EnumPlayerStatus.UserRequest)
                || guiPlayer.getType().equals(EnumPlayerType.Missing))
        {
          userInfoList.add(userInfo);
        }
      }
      // </editor-fold>

      if (userInfoList.size() < HOW_MANY_AUTOCOMPLETE_RESULTS)
      {
        // <editor-fold defaultstate="collapsed" desc="lista non amici stessa provincia">
        guiPlayerInfoList = GuiManager.prepareGuiPlayerFriendsInfoList(matchInfo,
                DAOFactory.getInstance().getUserDAO().getIdNotFriends(idFriendsList,
                name,
                HOW_MANY_AUTOCOMPLETE_RESULTS - userInfoList.size(),
                userOwner.getProvince().getId(),
                false,
                userOwner.getCountry().getId(),
                false));

        for (GuiPlayerInfo guiPlayer : guiPlayerInfoList)
        {
          UserInfo userInfo = guiPlayer.getUserInfo();
          if (userInfo.isAnonymousEnabled() && StringUtils.containsIgnoreCase(userInfo.getCompleteSurname(), name))
          {
            continue;
          }
          if (guiPlayer.getStatus().equals(EnumPlayerStatus.Undefined)
                  || guiPlayer.getStatus().equals(EnumPlayerStatus.UserCalled)
                  || guiPlayer.getStatus().equals(EnumPlayerStatus.UserRequest)
                  || guiPlayer.getType().equals(EnumPlayerType.Missing))
          {
            userInfoList.add(guiPlayer.getUserInfo());
          }
          if (userInfoList.size() == HOW_MANY_AUTOCOMPLETE_RESULTS)
          {
            break;
          }
        }
        // </editor-fold>
      }
      if (userInfoList.size() < HOW_MANY_AUTOCOMPLETE_RESULTS)
      {
        // <editor-fold defaultstate="collapsed" desc="lista non amici diversa provincia stessa nazione">
        guiPlayerInfoList = GuiManager.prepareGuiPlayerFriendsInfoList(matchInfo,
                DAOFactory.getInstance().getUserDAO().getIdNotFriends(idFriendsList,
                name,
                HOW_MANY_AUTOCOMPLETE_RESULTS - userInfoList.size(),
                userOwner.getProvince().getId(),
                true,
                userOwner.getCountry().getId(),
                false));
        for (GuiPlayerInfo guiPlayer : guiPlayerInfoList)
        {
          UserInfo userInfo = guiPlayer.getUserInfo();
          if (userInfo.isAnonymousEnabled() && StringUtils.containsIgnoreCase(userInfo.getCompleteSurname(), name))
          {
            continue;
          }
          if (guiPlayer.getStatus().equals(EnumPlayerStatus.Undefined)
                  || guiPlayer.getStatus().equals(EnumPlayerStatus.UserCalled)
                  || guiPlayer.getStatus().equals(EnumPlayerStatus.UserRequest)
                  || guiPlayer.getType().equals(EnumPlayerType.Missing))
          {
            userInfoList.add(guiPlayer.getUserInfo());
          }
          if (userInfoList.size() == HOW_MANY_AUTOCOMPLETE_RESULTS)
          {
            break;
          }
        }

        // </editor-fold>
      }
      if (userInfoList.size() < HOW_MANY_AUTOCOMPLETE_RESULTS)
      {
        // <editor-fold defaultstate="collapsed" desc="lista non amici diversa  nazione">
        guiPlayerInfoList = GuiManager.prepareGuiPlayerFriendsInfoList(matchInfo,
                DAOFactory.getInstance().getUserDAO().getIdNotFriends(idFriendsList,
                name,
                HOW_MANY_AUTOCOMPLETE_RESULTS - userInfoList.size(),
                userOwner.getProvince().getId(),
                true,
                userOwner.getCountry().getId(),
                true));
        for (GuiPlayerInfo guiPlayer : guiPlayerInfoList)
        {
          UserInfo userInfo = guiPlayer.getUserInfo();
          if (userInfo.isAnonymousEnabled() && StringUtils.containsIgnoreCase(userInfo.getCompleteSurname(), name))
          {
            continue;
          }
          if (guiPlayer.getStatus().equals(EnumPlayerStatus.Undefined)
                  || guiPlayer.getStatus().equals(EnumPlayerStatus.UserCalled)
                  || guiPlayer.getStatus().equals(EnumPlayerStatus.UserRequest)
                  || guiPlayer.getType().equals(EnumPlayerType.Missing))
          {
            userInfoList.add(guiPlayer.getUserInfo());
          }
          if (userInfoList.size() == HOW_MANY_AUTOCOMPLETE_RESULTS)
          {
            break;
          }
        }

        // </editor-fold>
      }

    }
    catch (Exception ex)
    {
      logger.error("error retriving idUserList", ex);
    }
    return userInfoList;
  }

  public static boolean setAllthirdOrganizedMatchDate()
  {
    List<Integer> organizers = new ArrayList<Integer>();
    try
    {
      organizers = DAOFactory.getInstance().getUserDAO().getOrganizerByFilters(0, 3,false,true);
      for (int idUser : organizers )
      {
        User user=UserManager.getById(idUser);
        MatchInfo matchInfo = MatchInfoManager.getMatchInfo(MatchManager.getByOrganizedDatePosition(idUser, 3));
        Date thirdOrganizedMatchDate= matchInfo.getRecordedMatchDate();
        if ( user.getThirdOrganizedMatch()==null || user.getThirdOrganizedMatch().compareTo(thirdOrganizedMatchDate)!=0)
        {
          user.setThirdOrganizedMatch(thirdOrganizedMatchDate);
          boolean success = UserManager.update(user);
          if (!success)
          {
            logger.error("error updating third organized match for users: " + idUser);
          }
        }
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
    }

    return true;
  }

  public static User getUserByIdFacebook(String idFacebook)
  {
    try
    {
      return DAOFactory.getInstance().getUserDAO().getUserByFacebookIdUser(idFacebook);
    }
    catch (Exception ex)
    {
      logger.error("Errore in getUserByFacebookIdUser", ex);
      return null;
    }
  }

  public static User getUserByEmail(String email)
  {
    try
    {
      return DAOFactory.getInstance().getUserDAO().getUserByEmail(email);
    }
    catch (Exception ex)
    {
      logger.error("Errore in getUserByEmail", ex);
      return null;
    }
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Gestione inviti --">
  /**
   *
   * @param invitationCode codice invito cercato
   * @return oggetto userInvitation
   */
  public static UserInvitation getUserInvitationByCode(String invitationCode)
  {
    try
    {
      return DAOFactory.getInstance().getUserInvitationDAO().getByCode(invitationCode);
    }
    catch (Exception ex)
    {
      logger.error("Errore in getUserInvitationByCode", ex);
      return null;
    }
  }

  /**
   *
   * @param invitationCode codice invito cercato
   * @return oggetto userInvitation
   */
  public static SpecialInvitation getSpecialInvitationByCode(String invitationCode)
  {
    try
    {
      return DAOFactory.getInstance().getSpecialInvitationDAO().getByCode(invitationCode);
    }
    catch (Exception ex)
    {
      logger.error("Errore in getUserInvitationByCode", ex);
      return null;
    }
  }
  // </editor-fold>
  
}
