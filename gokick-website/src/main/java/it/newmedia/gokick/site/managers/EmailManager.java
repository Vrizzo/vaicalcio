package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.enums.EnumEmailConfigurationType;
import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.data.hibernate.beans.UserInvitation;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.infos.AbuseReasonInfo;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.providers.EmailProvider;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.net.AEmailHelper;
import it.newmedia.net.HtmlEmailHelper;
import it.newmedia.results.Result;
import it.newmedia.security.encryption.MD5;
import it.newmedia.utils.DateUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Classe manager che gestisce ad alto livello l'invio delle email.
 */
public class EmailManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  /**
   *
   */
  public static final String EMAIL_SEPARATOR = ",";
  /**
   *
   */
  public static final String HTML_LINE_SEPARATOR = "<br/>";
  private static final String REPLACE_USER_ID = "###USER_ID###";
  private static final String REPLACE_USER_PASSWORD = "###USER_PASSWORD###";
  private static final String REPLACE_USER_NAME = "###USER_NAME###";
  private static final String REPLACE_USER_OWNER_NAME = "###USER_OWNER_NAME###";
  private static final String REPLACE_USER_CITY = "###USER_CITY###";
  private static final String REPLACE_USER_PROVINCE = "###USER_PROVINCE###";
  private static final String REPLACE_USER_NATION = "###USER_NATION###";
  private static final String REPLACE_INVITING_USER_NAME = "###INVITING_USER_NAME###";
  private static final String REPLACE_COMPLETE_USER_NAME = "###COMPLETE_USER_NAME###";
  private static final String REPLACE_USER_EMAIL = "###USER_EMAIL###";
  private static final String REPLACE_CHECK_PENDING = "###CHECK_PENDING###";
  private static final String REPLACE_CHECK_PASSWORD = "###CHECK_PASSWORD###";
  private static final String REPLACE_CURRENTUSER_NAME = "###CURRENTUSER_NAME###";
  private static final String REPLACE_MATCH_TYPE_NAME = "###MATCH_TYPE_NAME###";
  private static final String REPLACE_MATCH_START_DAY = "###MATCH_START_DAY###";
  private static final String REPLACE_MATCH_START_HOUR = "###MATCH_START_HOUR###";
  private static final String REPLACE_MATCH_START_DATE = "###MATCH_START_DATE###";
  private static final String REPLACE_BECOME_ORGANIZER_DATE = "###BECOME_ORGANIZER_DATE###";
  private static final String REPLACE_SPORTCENTER_ID = "###SPORTCENTER_ID###";
  private static final String REPLACE_SPORTCENTER_NAME = "###SPORTCENTER_NAME###";
  private static final String REPLACE_MATCH_TO_PLAY = "###MATCH_TO_PLAY###";
  private static final String REPLACE_SPORTCENTER_COUNTRY = "###SPORTCENTER_COUNTRY###";
  private static final String REPLACE_SPORTCENTER_PROVINCE = "###SPORTCENTER_PROVINCE###";
  private static final String REPLACE_SPORTCENTER_CITY = "###SPORTCENTER_CITY###";
  private static final String REPLACE_SPORTCENTER_ADDRESS = "###SPORTCENTER_ADDRESS###";
  private static final String REPLACE_SPORTCENTER_TYPE = "###SPORTCENTER_TYPE###";
  private static final String REPLACE_SPORTCENTER_PHONE = "###SPORTCENTER_PHONE###";
  private static final String REPLACE_SPORTCENTER_EMAIL = "###SPORTCENTER_EMAIL###";
  private static final String REPLACE_SPORTCENTER_WEB_SITE = "###SPORTCENTER_WEB_SITE###";
  private static final String REPLACE_FREE_TEXT = "###FREE_TEXT###";
  private static final String REPLACE_MATCH_ID = "###MATCH_ID###";
  private static final String REPLACE_PLAY_MORE_EXPIRE_DAYS = "###PLAY_MORE_EXPIRE_DAYS###";
  private static final String REPLACE_PLAY_MORE_EXPIRE_DAYS_DIFF = "###PLAY_MORE_EXPIRE_DAYS_DIFF###";
  private static final String DEFAULT_LANGUAGE = "en";
  private static final String GERMAN_LANGUAGE = "de";
  private static final String ITALIAN_LANGUAGE = "it";
  private static final String ENGLISH_LANGUAGE = "en";
  private static final String SPANISH_LANGUAGE = "es";
  private static final String FRENCH_LANGUAGE = "fr";
  private static final String PORTUGUESE_LANGUAGE = "pt";
  private static final String GERMANY = "Germany";
  private static final String ITALY = "Italy";
  private static final String AUSTRIA = "Austria";
  private static final String ENGLAND = "United Kingdom";
  private static final String USA = "United States";
  private static final String FRANCE = "France";
  private static final String SPAIN = "Spain";
  private static final String PORTUGAL = "Portugal";
  private static final String SENEGAL = "Senegal";
  private static final String TUNISIA = "Tunisia";
  private static final String CHILE = "Chile";
  private static final String ARGENTINA = "Argentina";
  private static final String VENEZUELA = "Venezuela";
  private static final String ECUADOR = "Ecuador";
  private static final String BOLIVIA = "Bolivia";
  private static final String PARAGUAY = "Paraguay";
  private static final String URUGUAY = "Uruguay";
  private static final String PERU = "Peru";
  private static final String COLOMBIA = "Colombia";
  private static final String MEXICO = "Mexico";
//  Calcio a 5
//  Mercoledì 17 Gennaio ore 14:00 vedi >>
//  Milanese Corvetto

  public static final String MATCH_TO_PLAY_TEMPLATE = " " +
    "###MATCH_TYPE_NAME###" +
    "<strong>###MATCH_START_DAY###</strong><a href=\"http://www.gokick.org/matchComments!viewAll.action?idMatch=8259\"> vedi >></a><br/>" +
    "###SPORTCENTER_NAME###<br/><br/>";
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(EmailManager.class);

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private EmailManager()
  {
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  /**
   * invia mail di registrazione all'utente
   *
   * @param userToregister
   * @param locale         lingua
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendUserRegistrationEmail(User userToregister, Language locale, Cobrand currentCobrand)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(EnumEmailConfigurationType.UserRegistration,
        locale, currentCobrand);
      // To
      htmlEmailHelper.addTo(userToregister.getEmail(),
        userToregister.getFirstName() + " " + userToregister.getLastName());

      // <editor-fold defaultstate="collapsed" desc="-- Replace --"  >
      String tmpMsg = htmlEmailHelper.getMsg();
      tmpMsg = tmpMsg.replace(REPLACE_USER_ID, userToregister.getId().toString());
      tmpMsg = tmpMsg.replace(REPLACE_CHECK_PENDING, userToregister.getCheckPending());
      tmpMsg = tmpMsg.replace(REPLACE_USER_NAME, userToregister.getFirstName());
      tmpMsg = tmpMsg.replace(REPLACE_USER_EMAIL, userToregister.getEmail());
      htmlEmailHelper.setMsg(tmpMsg);
      // </editor-fold>

      // Invio dell'email
      rSend = htmlEmailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending user registration email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  private static void logSendError(Result<String> rSend)
  {
    logSendError(rSend, null);
  }

  private static void logSendError(Result<String> rSend, String uid)
  {
    if (rSend.isSuccessNotNull())
    {
      //Questa condizione non dovrebbe mai verificarsi
      return;
    }
    StringBuilder sb = new StringBuilder();
    if (StringUtils.isNotBlank(uid))
    {
      sb.append("Uid:").append(uid).append("; ");
    }
    sb.append(rSend.getErrorMessage());
    //ExceptionUtils.
    Throwable t = rSend.getErrorException() != null ? rSend.getErrorException().getCause() : null;
    while (t != null)
    {
      sb.append(" -> ").append(t.getMessage());
      t = t.getCause();
    }
    logger.error(sb.toString());
  }

  /**
   * invia mail per invitare esterni ad iscriversi al sito
   *
   * @param userInfo  mail dell'utente che invita
   * @param fromEmail mail dell'utente che invita
   * @param mailTo    mail degli esterni
   * @param freeText  corpo della mail
   * @param language  lingua
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> inviteFriendsToSite(UserInfo userInfo, String fromEmail, String mailTo, String freeText,
                                                    Language language, Cobrand currentCobrand)
  {
    AEmailHelper emailHelper;
    Result<String> rSend;
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    try
    {
      UserInvitation invitation = new UserInvitation();
      invitation.setUserOwner(DAOFactory.getInstance().getUserDAO().load(userInfo.getId()));
      invitation.setCreated(new Date());
      invitation.setCode(UtilManager.getRandomCode());
      DAOFactory.getInstance().getUserInvitationDAO().makePersistent(invitation);

      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.InviteFriendsToSite, language,
        currentCobrand, userInfo.getId());
      //from
      emailHelper.setFromName(userInfo.getFirstLastName());
      //reply to
      emailHelper.addReplyTo(fromEmail, userInfo.getFirstLastName());
      // To
      emailHelper.addTo(mailTo);
      // Message

      // Faccio il replace delle informazioni aggiuntive
      if (emailHelper.isHtml())
      {
        String osLineSeparator = SystemUtils.LINE_SEPARATOR != null ? SystemUtils.LINE_SEPARATOR : "\n";
        freeText = StringUtils.replace(freeText, osLineSeparator, HTML_LINE_SEPARATOR);
      }

      String message = emailHelper.getMsg();
      message = StringUtils.replace(message, Constants.REPLACEMENT__FREE_TEXT, freeText);
      message = StringUtils.replace(message, Constants.REPLACEMENT__INVITATION_CODE, invitation.getCode());
      emailHelper.setMsg(message);
      // Invio dell'email
      rSend = emailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        HibernateSessionHolder.rollbackTransaction();
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
    }
    catch (Exception ex)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error("Invio email invito al sito fallito", ex);
      return new Result<Boolean>(ex);
    }
    return new Result<Boolean>(true, true);
  }

  /**
   * invia mail a utenti per notificargli la possibiltà di entrare in un match
   *
   * @param userInfo  di colui che invita
   * @param matchInfo della partita selezionata
   * @param mailTo    mail dei mittenti
   * @param freeText  corpo della mail
   * @param language  lingua
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> inviteFriendsToMatch(UserInfo userInfo, MatchInfo matchInfo, String mailTo,
                                                     String freeText, Language language, Cobrand currentCobrand)
  {
    AEmailHelper emailHelper;
    Result<String> rSend;
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    try
    {
      User userTo = UserManager.getByEmail(mailTo);
      UserInvitation invitation = new UserInvitation();
      invitation.setUserOwner(DAOFactory.getInstance().getUserDAO().load(userInfo.getId()));
      invitation.setCreated(new Date());
      invitation.setCode(UtilManager.getRandomCode());
      DAOFactory.getInstance().getUserInvitationDAO().makePersistent(invitation);

      if (userTo != null)
      {
        language = LanguageManager.chooseUserLanguage(userTo);
      }
      else
      {
        language = LanguageManager.getByLanguage(DEFAULT_LANGUAGE);
      }

      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.InviteFriendsToMatch, language,
        currentCobrand, userInfo.getId());
      //from
      emailHelper.setFromName(userInfo.getFirstLastName());
      //reply to
      emailHelper.addReplyTo(userInfo.getEmail(), userInfo.getFirstLastName());
      // To
      emailHelper.addTo(mailTo);
      // Message

      // Faccio il replace delle informazioni aggiuntive
      if (emailHelper.isHtml())
      {
        String osLineSeparator = SystemUtils.LINE_SEPARATOR != null ? SystemUtils.LINE_SEPARATOR : "\n";
        freeText = StringUtils.replace(freeText, osLineSeparator, HTML_LINE_SEPARATOR);
      }

      String subject = emailHelper.getSubject();
      subject = subject.replace(Constants.REPLACEMENT__MATCH_START_DATE,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_4, language, currentCobrand));
      emailHelper.setSubject(subject);

      String message = emailHelper.getMsg();

      if (userTo != null)
      {
        message = replaceCredentialAutoLog(message, userTo.getId(), userTo.getPassword());
      }
      else //se l'invito è fatto ad un esterno
      {
        message = replaceCredentialAutoLog(message, 0, "");
      }

      message = StringUtils.replace(message, Constants.REPLACEMENT__FREE_TEXT, freeText);
      message = StringUtils.replace(message, Constants.REPLACEMENT__INVITATION_CODE, invitation.getCode());

      message = message.replace(Constants.REPLACEMENT__MATCH_ID_MATCH, Integer.toString(matchInfo.getId()));
      message = message.replace(Constants.REPLACEMENT__MATCH_TYPE_NAME, matchInfo.getMatchTypeName());
      message = message.replace(Constants.REPLACEMENT__MATCH_START_DATE,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_4, language, currentCobrand));
      message = message.replace(Constants.REPLACEMENT__MATCH_START_HOUR,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_5, language, currentCobrand));
      message = message.replace(Constants.REPLACEMENT__SPORT_CENTER_NAME, matchInfo.getSportCenterName());
      message = message.replace(Constants.REPLACEMENT__SPORT_CENTER_CITY, matchInfo.getSportCenterCity());
      message = message.replace(Constants.REPLACEMENT__SPORT_CENTER_ADDRESS, matchInfo.getSportCenterAddress());

      emailHelper.setMsg(message);
      // Invio dell'email
      rSend = emailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        HibernateSessionHolder.rollbackTransaction();
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
    }
    catch (Exception ex)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error("Invio email invito al sito fallito", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia mail a utenti esterni per notificargli la possibiltà di entrare in un match
   *
   * @param userInfo  di colui che invita
   * @param matchInfo della partita selezionata
   * @param mailTo    mail dei mittenti
   * @param freeText  corpo della mail
   * @param language  lingua
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> inviteOutersToMatch(UserInfo userInfo, MatchInfo matchInfo, String mailTo,
                                                    String freeText, Language language, Cobrand currentCobrand)
  {
    AEmailHelper emailHelper;
    Result<String> rSend;
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    try
    {
      User userTo = UserManager.getByEmail(mailTo);

      // Faccio il replace delle informazioni aggiuntive

      if (userTo != null)
      {
        language = LanguageManager.chooseUserLanguage(userTo);
        return inviteGokickersToMatch(userInfo, userTo, matchInfo, freeText, language, currentCobrand);
      }
      UserInvitation invitation = new UserInvitation();
      invitation.setUserOwner(DAOFactory.getInstance().getUserDAO().load(userInfo.getId()));
      invitation.setCreated(new Date());
      invitation.setCode(UtilManager.getRandomCode());
      DAOFactory.getInstance().getUserInvitationDAO().makePersistent(invitation);

      if (userTo != null)
      {
        language = LanguageManager.chooseUserLanguage(userTo);
      }
      else
      {
        language = LanguageManager.getByLanguage(DEFAULT_LANGUAGE);
      }

      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.InviteOutersToMatch, language,
        currentCobrand, userInfo.getId());
      //from
      emailHelper.setFromName(userInfo.getFirstLastName());
      //reply to
      emailHelper.addReplyTo(userInfo.getEmail(), userInfo.getFirstLastName());
      // To
      emailHelper.addTo(mailTo);
      // Message

      // Faccio il replace delle informazioni aggiuntive
      if (emailHelper.isHtml())
      {
        String osLineSeparator = SystemUtils.LINE_SEPARATOR != null ? SystemUtils.LINE_SEPARATOR : "\n";
        freeText = StringUtils.replace(freeText, osLineSeparator, HTML_LINE_SEPARATOR);
      }

      String subject = emailHelper.getSubject();
      subject = subject.replace(Constants.REPLACEMENT__MATCH_START_DATE,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_4, language, currentCobrand));
      emailHelper.setSubject(subject);

      String message = emailHelper.getMsg();

      if (userTo != null)
      {
        message = replaceCredentialAutoLog(message, userTo.getId(), userTo.getPassword());
      }
      else //se l'invito è fatto ad un esterno
      {
        message = replaceCredentialAutoLog(message, 0, "");
      }

      message = StringUtils.replace(message, Constants.REPLACEMENT__FREE_TEXT, freeText);
      message = StringUtils.replace(message, Constants.REPLACEMENT__INVITATION_CODE, invitation.getCode());

      message = message.replace(Constants.REPLACEMENT__MATCH_ID_MATCH, Integer.toString(matchInfo.getId()));
      message = message.replace(Constants.REPLACEMENT__MATCH_TYPE_NAME, matchInfo.getMatchTypeName());
      message = message.replace(Constants.REPLACEMENT__MATCH_START_DATE,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_4, language, currentCobrand));
      message = message.replace(Constants.REPLACEMENT__MATCH_START_HOUR,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_5, language, currentCobrand));
      message = message.replace(Constants.REPLACEMENT__SPORT_CENTER_NAME, matchInfo.getSportCenterName());
      message = message.replace(Constants.REPLACEMENT__SPORT_CENTER_CITY, matchInfo.getSportCenterCity());
      message = message.replace(Constants.REPLACEMENT__SPORT_CENTER_ADDRESS, matchInfo.getSportCenterAddress());

      emailHelper.setMsg(message);
      // Invio dell'email
      rSend = emailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        HibernateSessionHolder.rollbackTransaction();
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
    }
    catch (Exception ex)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error("Invio email invito al sito fallito", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia mail a Gokickers per notificargli la possibiltà di entrare in un match
   *
   * @param userInfo       di colui che invita
   * @param userTo         Utente invitato
   * @param matchInfo      info sulla partita
   * @param freeText       corpo della mail
   * @param language       lingua
   * @param currentCobrand cobrand del sito su cui si sta navigando
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> inviteGokickersToMatch(UserInfo userInfo, User userTo, MatchInfo matchInfo,
                                                       String freeText, Language language, Cobrand currentCobrand)
  {
    AEmailHelper emailHelper;
    Result<String> rSend;
    //HibernateSessionHolder.beginTransaction();
    try
    {

      language = LanguageManager.chooseUserLanguage(userTo);

      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.InviteGokickersToMatch, language,
        currentCobrand, userInfo.getId());
      //from
      emailHelper.setFromName(userInfo.getFirstLastName());
      //reply to
      emailHelper.addReplyTo(userInfo.getEmail(), userInfo.getFirstLastName());
      // To
      emailHelper.addTo(userTo.getEmail());
      // Message

      // Faccio il replace delle informazioni aggiuntive
      if (emailHelper.isHtml())
      {
        String osLineSeparator = SystemUtils.LINE_SEPARATOR != null ? SystemUtils.LINE_SEPARATOR : "\n";
        freeText = StringUtils.replace(freeText, osLineSeparator, HTML_LINE_SEPARATOR);
      }

      String subject = emailHelper.getSubject();
      subject = subject.replace(Constants.REPLACEMENT__MATCH_START_DATE,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_4, language, currentCobrand));
      emailHelper.setSubject(subject);

      String message = emailHelper.getMsg();

      if (userTo != null)
      {
        message = replaceCredentialAutoLog(message, userTo.getId(), userTo.getPassword());
      }
      else //se l'invito è fatto ad un esterno
      {
        message = replaceCredentialAutoLog(message, 0, "");
      }

      message = StringUtils.replace(message, Constants.REPLACEMENT__FREE_TEXT, freeText);
      //message = StringUtils.replace(message, Constants.REPLACEMENT__INVITATION_CODE, invitation.getCode());

      message = message.replace(Constants.REPLACEMENT__MATCH_ID_MATCH, Integer.toString(matchInfo.getId()));
      message = message.replace(Constants.REPLACEMENT__MATCH_TYPE_NAME, matchInfo.getMatchTypeName());
      message = message.replace(Constants.REPLACEMENT__MATCH_START_DATE,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_4, language, currentCobrand));
      message = message.replace(Constants.REPLACEMENT__MATCH_START_HOUR,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_5, language, currentCobrand));
      message = message.replace(Constants.REPLACEMENT__SPORT_CENTER_NAME, matchInfo.getSportCenterName());
      message = message.replace(Constants.REPLACEMENT__SPORT_CENTER_CITY, matchInfo.getSportCenterCity());
      message = message.replace(Constants.REPLACEMENT__SPORT_CENTER_ADDRESS, matchInfo.getSportCenterAddress());

      emailHelper.setMsg(message);
      // Invio dell'email
      //Visto che ormai ci sono moltissime email uso l'invio asincrono!
      //quindi questo metodo restituira sempre true
      new EmailAsynch(emailHelper).send();
      //HibernateSessionHolder.commitTransaction();
    }
    catch (Exception ex)
    {
      //HibernateSessionHolder.rollbackTransaction();
      logger.error("Invio email invito al sito fallito", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia mail giocatori di un match
   *
   * @param idUserOwner    di colui che invita
   * @param matchInfo      della partita selezionata
   * @param mailTo         mail del destinatario
   * @param freeText       corpo della mail
   * @param currentCobrand del sito che si sta navigando
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> contactMatchPlayers(int idUserOwner, MatchInfo matchInfo, String mailTo,
                                                    String freeText, Cobrand currentCobrand)
  {
    AEmailHelper emailHelper;
    Result<String> rSend;
    UserInfo userInfo = InfoProvider.getUserInfo(idUserOwner);
    User userTo = UserManager.getByEmail(mailTo);
    try
    {
      Language language = LanguageManager.chooseUserLanguage(userTo);
      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.ContactMatchPlayers, language,
        currentCobrand, userInfo.getId());
      //from
      emailHelper.setFromName(userInfo.getFirstLastName());
      //reply to
      emailHelper.addReplyTo(userInfo.getEmail(), userInfo.getFirstLastName());
      // To
      emailHelper.addTo(mailTo);
      // Message
      // Faccio il replace delle informazioni aggiuntive

      String subject = emailHelper.getSubject();
      subject = subject.replace(Constants.REPLACEMENT__MATCH_START_DATE,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_4, language, currentCobrand));
      emailHelper.setSubject(subject);

      String message = emailHelper.getMsg();

      if (userTo != null)
      {
        message = replaceCredentialAutoLog(message, userTo.getId(), userTo.getPassword());
      }
      else //se l'invito è fatto ad un esterno
      {
        message = replaceCredentialAutoLog(message, 0, "");
      }

      if (emailHelper.isHtml())
      {
        String osLineSeparator = SystemUtils.LINE_SEPARATOR != null ? SystemUtils.LINE_SEPARATOR : "\n";
        freeText = StringUtils.replace(freeText, osLineSeparator, HTML_LINE_SEPARATOR);
      }

      message = StringUtils.replace(message, Constants.REPLACEMENT__FREE_TEXT, freeText);
      message = message.replace(Constants.REPLACEMENT__MATCH_ID_MATCH, Integer.toString(matchInfo.getId()));
      message = message.replace(REPLACE_USER_OWNER_NAME, userInfo.getFirstLastName());
      emailHelper.setMsg(message);

      // Invio dell'email
      //Visto che ormai ci sono moltissimi amici a cui mandare l'email uso l'invio asincrono!
      //quindi questo metodo restituira sempre true
      new EmailAsynch(emailHelper).send();
    }
    catch (Exception ex)
    {
      logger.error("Invio email contatta players  fallito user: " + userTo.getId() + " mail: " + mailTo, ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * Invia l'email relativa alla richiesta di avere degli inviti da parte di un utente non loggato
   *
   * @param mailFrom    Mail del richiedente
   * @param freeText    testo inserito dal richiedente
   * @param requestType Scelta del richiedente in merito alla tipologia di supporto al sito
   * @param language
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> inviteRequest(String mailFrom, String freeText, String requestType, Language language,
                                              Cobrand currentCobrand)
  {
    AEmailHelper emailHelper;
    Result<String> rSend;
    try
    {
      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.InviteRequest, language, currentCobrand);
      // To
      // definito nella configurazione di questa email
      //from
      emailHelper.setFrom(mailFrom, mailFrom);
      //reply to
      emailHelper.addReplyTo(mailFrom, mailFrom);

      // <editor-fold defaultstate="collapsed" desc="-- Replace --"  >

      if (emailHelper.isHtml())
      {
        String osLineSeparator = SystemUtils.LINE_SEPARATOR != null ? SystemUtils.LINE_SEPARATOR : "\n";
        freeText = StringUtils.replace(freeText, osLineSeparator, HTML_LINE_SEPARATOR);
      }


      String tmpMsg = emailHelper.getMsg();
      tmpMsg = StringUtils.replace(tmpMsg, Constants.REPLACEMENT__EMAIL, mailFrom);
      tmpMsg = StringUtils.replace(tmpMsg, Constants.REPLACEMENT__FREE_TEXT, freeText);
      tmpMsg = StringUtils.replace(tmpMsg, Constants.REPLACEMENT__INVITE_REQUEST_TYPE, requestType);
      emailHelper.setMsg(tmpMsg);
      // </editor-fold>

      // Invio dell'email
      rSend = emailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Si è verificato un'errore durante l'invio di una richiesta di inviti", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * Invia l'email di notifica modifiche ad un match
   *
   * @param user                 destinatario
   * @param matchStartDate       data di inzio della partita
   * @param matchStartDateFormat formato della data in uscita
   * @param language
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendMatchModifiedNotifyEmail(User user, Date matchStartDate,
                                                             String matchStartDateFormat, Language language,
                                                             Cobrand currentCobrand)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(EnumEmailConfigurationType.MatchModified,
        language, currentCobrand);

      // To
      htmlEmailHelper.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());

      // Replace
      String tmpMsg = htmlEmailHelper.getMsg();
      tmpMsg = tmpMsg.replace(REPLACE_USER_NAME, user.getFirstName());
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_START_DATE,
        DateManager.showDate(matchStartDate, matchStartDateFormat, language, currentCobrand));
      htmlEmailHelper.setMsg(tmpMsg);

      // Invio dell'email
      rSend = htmlEmailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending match modified notify email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia Email agli amici per la notifica di apertura iscrizioni match
   *
   * @param match             partita in oggetto
   * @param userInfoSquadList lista di oggetti userInfo degli amici (destinatari)
   * @param currentCobrand    che si riferisce al sito su cui sta navigando l'utente
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendMatchRegistrationOpenEmail(Match match, List<UserInfo> userInfoSquadList,
                                                               Cobrand currentCobrand)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;
    try
    {
      for (UserInfo userInfo : userInfoSquadList)
      {
        if (!userInfo.isAlertOnMatchRegistrationOpen())
        {
          continue;
        }
        String password = UserManager.getById(userInfo.getId()).getPassword();
        Language language = LanguageManager.chooseUserLanguage(userInfo.getCountry());
        htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(
          EnumEmailConfigurationType.MatchRegistrationOpenNotify, language, currentCobrand);

        // To
        htmlEmailHelper.addTo(userInfo.getEmail(), userInfo.getName() + " " + userInfo.getSurname());

        // Replace
        String tmpMsg = htmlEmailHelper.getMsg();
        tmpMsg = tmpMsg.replace(REPLACE_USER_NAME, userInfo.getName());
        tmpMsg = replaceCredentialAutoLog(tmpMsg, userInfo.getId(), password);
        tmpMsg = tmpMsg.replace(REPLACE_USER_OWNER_NAME,
          match.getUserOwner().getFirstName() + " " + match.getUserOwner().getLastName());
        tmpMsg = tmpMsg.replace(REPLACE_MATCH_START_DATE,
          DateManager.showDate(match.getMatchStart(), DateManager.FORMAT_DATE_13, language, currentCobrand));
        tmpMsg = tmpMsg.replace(REPLACE_MATCH_ID, Integer.toString(match.getId()));

        htmlEmailHelper.setMsg(tmpMsg);

        // Invio dell'email
        //Visto che ormai ci sono moltissimi amici a cui mandare l'email uso l'invio asincrono!
        //quindi questo metodo restituira sempre true
        new EmailAsynch(htmlEmailHelper).send();
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending match registration open notify email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia Email agli amici per la notifica di apertura iscrizioni match
   *
   * @param match           parita in oggetto
   * @param currentLanguage
   * @param currentCobrand  che si riferisce al sito su cui sta navigando l'utente
   * @return sendMatchRegistrationOpenEmail (matchList,language)
   */
  public static Result<Boolean> sendMatchRegistrationOpenEmail(Match match, Language currentLanguage,
                                                               Cobrand currentCobrand)
  {
    List<Match> matchList = new ArrayList<Match>();
    matchList.add(match);
    return sendMatchRegistrationOpenEmail(matchList, currentLanguage, currentCobrand);
  }

  /**
   * invia Email agli amici per la notifica di apertura iscrizioni a piu' match, setta la lingua in base al destinatario
   *
   * @param matchList       lista di match
   * @param currentLanguage
   * @param currentCobrand  che si riferisce al sito su cui sta navigando l'utente
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendMatchRegistrationOpenEmail(List<Match> matchList, Language currentLanguage,
                                                               Cobrand currentCobrand)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      //Per sicurezza verifico che ci sia un linguaggio e un cobrand (se chiamati dallo scheduler non ci sono!
      if (currentLanguage == null)
      {
        currentLanguage = LanguageManager.getByLanguage(ITALIAN_LANGUAGE);
      }
      if (currentCobrand == null)
      {
        currentCobrand = InfoProvider.getCobrandByCode("GOKICK");
      }
      for (Match match : matchList)
      {
        //se la partita è passata
        if (match.getNotified() || match.getMatchStart().before(new Date()))
        {
          continue;
        }
        //se l'apertura iscrizioni è futura
        if (match.getRegistrationStart() != null && match.getRegistrationStart().after(new Date()))
        {
          continue;
        }

        List<User> userList = UserManager.getAllFriendsToNotifyMatchStart(match.getUserOwner().getFirstSquad().getId());

        for (User user : userList)
        {

          if (user.getAlertOnRegistrationStart())
          {
            Language language = LanguageManager.chooseUserLanguage(user);
            htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(
              EnumEmailConfigurationType.MatchRegistrationOpenNotify, language, currentCobrand);

            // To
            htmlEmailHelper.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());

            // Replace
            String tmpMsg = htmlEmailHelper.getMsg();
            tmpMsg = tmpMsg.replace(REPLACE_USER_NAME, user.getFirstName());
            tmpMsg = replaceCredentialAutoLog(tmpMsg, user.getId(), user.getPassword());
            tmpMsg = tmpMsg.replace(REPLACE_USER_OWNER_NAME,
              match.getUserOwner().getFirstName() + " " + match.getUserOwner().getLastName());
            tmpMsg = tmpMsg.replace(REPLACE_MATCH_START_DATE,
              DateManager.showDate(match.getMatchStart(), DateManager.FORMAT_DATE_13, language, currentCobrand));
            tmpMsg = tmpMsg.replace(REPLACE_MATCH_ID, Integer.toString(match.getId()));

            htmlEmailHelper.setMsg(tmpMsg);

            // Invio dell'email
            //Visto che ormai ci sono moltissimi amici a cui mandare l'email uso l'invio asincrono!
            //quindi questo metodo restituira sempre true
            new EmailAsynch(htmlEmailHelper).send();
          }
        }
        match.setNotified(true);
        MatchManager.update(match, false, false, currentLanguage, currentCobrand);
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending match registration open notify email", ex);
      return new Result<Boolean>(false, false);
    }
    return new Result<Boolean>(true, true);
  }

  /**
   * invia Email di notifica cancellazione match
   *
   * @param user                 destinatario
   * @param matchStartDate       data inzio partita
   * @param matchStartDateFormat formato data in uscita
   * @param idMatch              match in oggetto
   * @param currentCobrand       che si riferisce al sito su cui sta navigando l'utente
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendMatchCancelledNotifyEmail(User user, Date matchStartDate,
                                                              String matchStartDateFormat, int idMatch,
                                                              Cobrand currentCobrand)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      Language language = LanguageManager.chooseUserLanguage(user);
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(EnumEmailConfigurationType.MatchCancelled,
        language, currentCobrand);

      // To
      htmlEmailHelper.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());

      // Replace
      String tmpMsg = htmlEmailHelper.getMsg();
      tmpMsg = tmpMsg.replace(REPLACE_USER_NAME, user.getFirstName());
      tmpMsg = replaceCredentialAutoLog(tmpMsg, user.getId(), user.getPassword());
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_START_DATE,
        DateManager.showDate(matchStartDate, matchStartDateFormat, language, currentCobrand));
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_ID, Integer.toString(idMatch));

      htmlEmailHelper.setMsg(tmpMsg);

      // Invio dell'email
      //Visto che ormai ci sono moltissime email uso l'invio asincrono!
      //quindi questo metodo restituira sempre true
      new EmailAsynch(htmlEmailHelper).send();
    }
    catch (Exception ex)
    {
      logger.error("Error sending match cancelled notify email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia Email di notifica archiviazione match
   *
   * @param user           destinatario
   * @param matchStartDate data inzio partita
   * @param idMatch        id della partita
   * @param currentCobrand che si riferisce al sito su cui sta navigando l'utente
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendMatchArchivedNotifyEmail(User user, Date matchStartDate, Integer idMatch,
                                                             Cobrand currentCobrand)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    Language language = LanguageManager.chooseUserLanguage(user);
    try
    {
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(EnumEmailConfigurationType.MatchArchived,
        language, currentCobrand);

      // To
      htmlEmailHelper.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());//user.getEmail()

      // Replace
      String tmpMsg = htmlEmailHelper.getMsg();
      tmpMsg = tmpMsg.replace(REPLACE_USER_NAME, user.getFirstName());
      tmpMsg = replaceCredentialAutoLog(tmpMsg, user.getId(), user.getPassword());
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_START_DATE,
        DateManager.showDate(matchStartDate, DateManager.FORMAT_DATE_13, language, currentCobrand));
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_ID, Integer.toString(idMatch));
      htmlEmailHelper.setMsg(tmpMsg);

      String tmpSubj = htmlEmailHelper.getSubject();
      tmpSubj = tmpSubj.replace(REPLACE_MATCH_START_DATE,
        DateManager.showDate(matchStartDate, DateManager.FORMAT_DATE_9, language, currentCobrand));
      tmpSubj = tmpSubj.replace(REPLACE_MATCH_START_DAY,
        DateManager.showDate(matchStartDate, DateManager.FORMAT_DATE_19, language, currentCobrand));
      htmlEmailHelper.setSubject(tmpSubj);
      // Invio dell'email
      //Visto che ormai ci sono moltissimi email da inviare uso l'invio asincrono!
      //quindi questo metodo restituira sempre true
      new EmailAsynch(htmlEmailHelper).send();
    }
    catch (Exception ex)
    {
      logger.error("Error sending match archived notify email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia mail all'utente quando richiede un cambio della password
   *
   * @param user     destinatario
   * @param language
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendUserChangePasswordRequestEmail(User user, Language language, Cobrand currentCobrand)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(
        EnumEmailConfigurationType.UserChangePasswordRequest, language, currentCobrand);

      // To
      htmlEmailHelper.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());

      // Replace
      String tmpMsg = htmlEmailHelper.getMsg();
      tmpMsg = tmpMsg.replace(REPLACE_USER_ID, user.getId().toString());
      tmpMsg = tmpMsg.replace(REPLACE_CHECK_PASSWORD, user.getCheckPassword());
      tmpMsg = tmpMsg.replace(REPLACE_USER_NAME, user.getFirstName());
      htmlEmailHelper.setMsg(tmpMsg);

      // Invio dell'email
      rSend = htmlEmailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending user change password request email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia Email all'utente per notificargli che è stato invitato in una rosa
   *
   * @param userToInvite   destinario
   * @param userInviting   utente che invita
   * @param currentCobrand che si riferisce al sito su cui sta navigando l'utente
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendInviteNotifyEmail(User userToInvite, User userInviting, Cobrand currentCobrand)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      Language language = LanguageManager.chooseUserLanguage(userToInvite);
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(EnumEmailConfigurationType.UserInviteNotify,
        language, currentCobrand);

      // To
      htmlEmailHelper.addTo(userToInvite.getEmail(), userToInvite.getFirstName() + " " + userToInvite.getLastName());

      // Replace
      String tmpMsg = htmlEmailHelper.getMsg();
      tmpMsg = replaceCredentialAutoLog(tmpMsg, userToInvite.getId(), userToInvite.getPassword());
      tmpMsg = tmpMsg.replace(REPLACE_USER_NAME, userToInvite.getFirstName());
      tmpMsg = tmpMsg.replace(REPLACE_INVITING_USER_NAME,
        userInviting.getFirstName() + " " + userInviting.getLastName());
      htmlEmailHelper.setMsg(tmpMsg);

      // Invio dell'email
      //Visto che ormai ci sono moltissimi amici a cui mandare l'email uso l'invio asincrono!
      //quindi questo metodo restituira sempre true
      new EmailAsynch(htmlEmailHelper).send();
    }
    catch (Exception ex)
    {
      logger.error("Error sending invite notify email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia Email all'utente inserito in una rosa
   *
   * @param userRequesting richiedente
   * @param ownerUser      proprietario della rosa
   * @param currentCobrand che si riferisce al sito su cui sta navigando l'utente
   * @return un oggetto Result "true" in caso di successo, altrimenti contenente la stringa d'errore
   */
  public static Result<Boolean> sendAcceptUserInSquadNotifyEmail(User userRequesting, User ownerUser,
                                                                 Cobrand currentCobrand)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      Language language = LanguageManager.chooseUserLanguage(userRequesting);
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(
        EnumEmailConfigurationType.AcceptUserInSquadNotify, language, currentCobrand);

      // To
      htmlEmailHelper.addTo(userRequesting.getEmail(),
        userRequesting.getFirstName() + " " + userRequesting.getLastName());

      // Replace
      String tmpMsg = htmlEmailHelper.getMsg();
      tmpMsg = replaceCredentialAutoLog(tmpMsg, userRequesting.getId(), userRequesting.getPassword());
      tmpMsg = tmpMsg.replace(REPLACE_USER_NAME, userRequesting.getFirstName());
      tmpMsg = tmpMsg.replace(REPLACE_INVITING_USER_NAME, ownerUser.getFirstName() + " " + ownerUser.getLastName());
      htmlEmailHelper.setMsg(tmpMsg);

      // Invio dell'email
      rSend = htmlEmailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending invite notify email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia mail  di evvenuta registrazione al match a user che ne hanno fatto richiesta
   *
   * @param matchInfo      oggetto matchInfo del match in oggetto
   * @param user           utente registrato
   * @param currentCobrand che si riferisce al sito su cui sta navigando l'utente
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendRequestRegisteredUserToMatchNotifyEmail(MatchInfo matchInfo, User user,
                                                                            Cobrand currentCobrand)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      Language language = LanguageManager.chooseUserLanguage(user);
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(
        EnumEmailConfigurationType.UserRequestRegisteredToMatchNotify, language, currentCobrand);

      // To
      htmlEmailHelper.addTo(user.getEmail());

      // <editor-fold defaultstate="collapsed" desc="-- Replace --"  >
      String tmpSubject = htmlEmailHelper.getSubject();
      tmpSubject = tmpSubject.replace(REPLACE_MATCH_START_DAY,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_15, language, currentCobrand));
      htmlEmailHelper.setSubject(tmpSubject);

      String tmpMsg = htmlEmailHelper.getMsg();
      tmpMsg = replaceCredentialAutoLog(tmpMsg, user.getId(), user.getPassword());
      tmpMsg = tmpMsg.replace(REPLACE_CURRENTUSER_NAME,
        matchInfo.getNameUserOwner() + " " + matchInfo.getSurnameUserOwner());
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_TYPE_NAME, matchInfo.getMatchTypeName());
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_START_DAY,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_13, language, currentCobrand));
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_START_HOUR, matchInfo.getMatchStartHour());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_NAME, matchInfo.getSportCenterName());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_CITY, matchInfo.getSportCenterCity());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_ADDRESS, matchInfo.getSportCenterAddress());
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_ID, Integer.toString(matchInfo.getId()));
      htmlEmailHelper.setMsg(tmpMsg);
      // </editor-fold>

      // Invio dell'email
      rSend = htmlEmailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending match inviteFriend email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia mail  di evvenuta registrazione al match
   *
   * @param matchInfo      oggetto matchInfo del match in oggetto
   * @param idUser         id dell'utente registrato
   * @param freeText       testo da aggiungere
   * @param currentCobrand che si riferisce al sito su cui sta navigando l'utente
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendRegisteredUserToMatchEmail(MatchInfo matchInfo, Integer idUser, String freeText,
                                                               Cobrand currentCobrand)
  {
    AEmailHelper emailHelper;
    Result<String> rSend;
    User user = UserManager.getById(idUser);

    try
    {
      Language language = LanguageManager.chooseUserLanguage(user.getCountry().getName());
      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.UserRegisteredToMatchNotify, language,
        currentCobrand);

      UserInfo userOwner = InfoProvider.getUserInfo(matchInfo.getIdUserOwner());

      // To
      emailHelper.addTo(user.getEmail());
      //reply to
      emailHelper.addReplyTo(userOwner.getEmail(), userOwner.getFirstLastName());
      //from
      emailHelper.setFromName(userOwner.getFirstLastName());
      emailHelper.setFrom(userOwner.getEmail(), userOwner.getFirstLastName());

      // <editor-fold defaultstate="collapsed" desc="-- Replace --"  >

      if (emailHelper.isHtml())
      {
        String osLineSeparator = SystemUtils.LINE_SEPARATOR != null ? SystemUtils.LINE_SEPARATOR : "\n";
        freeText = StringUtils.replace(freeText, osLineSeparator, HTML_LINE_SEPARATOR);
      }

      String tmpSubject = emailHelper.getSubject();
      tmpSubject = tmpSubject.replace(REPLACE_MATCH_START_DATE,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_13, language, currentCobrand));
      emailHelper.setSubject(tmpSubject);

      String tmpMsg = emailHelper.getMsg();
      tmpMsg = replaceCredentialAutoLog(tmpMsg, user.getId(), user.getPassword());

      tmpMsg = tmpMsg.replace(REPLACE_FREE_TEXT, freeText);
      tmpMsg = tmpMsg.replace(REPLACE_USER_OWNER_NAME,
        matchInfo.getNameUserOwner() + " " + matchInfo.getSurnameUserOwner());
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_TYPE_NAME, matchInfo.getMatchTypeName());
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_START_DATE,
        DateManager.showDate(matchInfo.getMatchStart(), DateManager.FORMAT_DATE_13, language, currentCobrand));
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_START_HOUR, matchInfo.getMatchStartHour());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_NAME, matchInfo.getSportCenterName());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_CITY, matchInfo.getSportCenterCity());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_ADDRESS, matchInfo.getSportCenterAddress());
      tmpMsg = tmpMsg.replace(REPLACE_MATCH_ID, Integer.toString(matchInfo.getId()));
      emailHelper.setMsg(tmpMsg);
      // </editor-fold>

      // Invio dell'email
      //Visto che ormai ci sono moltissimi amici a cui mandare l'email uso l'invio asincrono!
      //quindi questo metodo restituira sempre true
      new EmailAsynch(emailHelper).send();
    }
    catch (Exception ex)
    {
      logger.error("Error sending match inviteFriend email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * Invia l'email di notifica relativa all'inserimento di un nuovo campo (centro sportivo)
   *
   * @param sportCenter   Centro sportivo appena inserito
   * @param nameTypePitch Tipo Campo
   * @param language
   * @param idUserAuthor  id dell'autore dell'inserimento
   * @return Ritorna un oggetto Result con le informazioni in merito all'esito dell'operazione
   */
  public static Result<Boolean> sendSportCenterInsertedEmail(SportCenter sportCenter, String nameTypePitch,
                                                             Language language, Cobrand currentCobrand,
                                                             int idUserAuthor)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(EnumEmailConfigurationType.SportCenterInserted,
        language, currentCobrand);
      // To
      // definito nella configurazione di questa email

      UserInfo userInfo = InfoProvider.getUserInfo(idUserAuthor);

      // <editor-fold defaultstate="collapsed" desc="-- Replace --"  >
      String tmpMsg = htmlEmailHelper.getMsg();
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_ID, sportCenter.getId().toString());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_NAME, sportCenter.getName());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_COUNTRY, sportCenter.getCountry().getName());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_PROVINCE,
        sportCenter.getProvince().getName() + " (" + sportCenter.getProvince().getAbbreviation() + ")");
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_CITY, sportCenter.getCity().getName());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_ADDRESS, sportCenter.getAddress());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_TYPE, nameTypePitch);
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_PHONE, sportCenter.getTelephone());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_EMAIL, sportCenter.getEmail());
      tmpMsg = tmpMsg.replace(REPLACE_SPORTCENTER_WEB_SITE, sportCenter.getWebSite());
      tmpMsg = tmpMsg.replace(REPLACE_USER_ID, Integer.toString(idUserAuthor));
      tmpMsg = tmpMsg.replace(REPLACE_USER_NAME, userInfo.getName() + " " + userInfo.getSurname());
      tmpMsg = tmpMsg.replace(REPLACE_USER_EMAIL, userInfo.getEmail());
      htmlEmailHelper.setMsg(tmpMsg);
      // </editor-fold>

      // Invio dell'email
      rSend = htmlEmailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending sportCenter inserted notification email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  public static Result<Boolean> sendAbuseNotificatioEmail(int idUserAbusing, int idUserNotifing, int idReason,
                                                          String freeText, Cobrand currentCobrand)
  {
    UserInfo userInfoAbusing = InfoProvider.getUserInfo(idUserAbusing);
    UserInfo userInfoNotifing = InfoProvider.getUserInfo(idUserNotifing);
    List<AbuseReasonInfo> AbuseReasonInfoList = AppContext.getInstance().getAllAbuseReasons(
      LanguageManager.getByLanguage(DEFAULT_LANGUAGE), currentCobrand);
    AbuseReasonInfo reason = new AbuseReasonInfo();
    for (AbuseReasonInfo abuseReason : AbuseReasonInfoList)
    {
      if (abuseReason.getId() == idReason)
      {
        reason = abuseReason;
        break;
      }
    }
    AEmailHelper emailHelper;
    Result<String> rSend;

    try
    {
      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.NotifyUserAbuse,
        LanguageManager.getByLanguage(DEFAULT_LANGUAGE), currentCobrand);
      // To
      // definito nella configurazione di questa email

      // <editor-fold defaultstate="collapsed" desc="-- Replace --"  >

      if (emailHelper.isHtml())
      {
        String osLineSeparator = SystemUtils.LINE_SEPARATOR != null ? SystemUtils.LINE_SEPARATOR : "\n";
        freeText = StringUtils.replace(freeText, osLineSeparator, HTML_LINE_SEPARATOR);
      }

      String tmpSubject = emailHelper.getSubject();
      tmpSubject = tmpSubject.replace("###ID_USER_ABUSING###", Integer.toString(idUserAbusing));
      tmpSubject = tmpSubject.replace("###COMPLETE_NAME_USER_ABUSING###",
        userInfoAbusing.getName() + " " + userInfoAbusing.getSurname());
      emailHelper.setSubject(tmpSubject);

      String tmpMsg = emailHelper.getMsg();
      tmpMsg = tmpMsg.replace("###ID_USER_ABUSING###", Integer.toString(idUserAbusing));
      tmpMsg = tmpMsg.replace("###COMPLETE_NAME_USER_ABUSING###",
        userInfoAbusing.getName() + " " + userInfoAbusing.getSurname());
      tmpMsg = tmpMsg.replace("###CITY_USER_ABUSING###", userInfoAbusing.getCity());
      tmpMsg = tmpMsg.replace("###PROVINCE_USER_ABUSING###", userInfoAbusing.getProvince());
      tmpMsg = tmpMsg.replace("###COUNTRY_USER_ABUSING###", userInfoAbusing.getCountry());
      tmpMsg = tmpMsg.replace("###REASON###", reason.getTranslation());
      tmpMsg = tmpMsg.replace("###FREE_TEXT###", freeText);
      tmpMsg = tmpMsg.replace("###DATE###", new Date().toString());
      tmpMsg = tmpMsg.replace("###ID_USER_NOTIFIER###", Integer.toString(idUserNotifing));
      tmpMsg = tmpMsg.replace("###COMPLETE_NAME_NOTIFIER###",
        userInfoNotifing.getName() + " " + userInfoNotifing.getSurname());
      tmpMsg = tmpMsg.replace("###CITY_USER_NOTIFIER###", userInfoNotifing.getCity());
      tmpMsg = tmpMsg.replace("###PROVINCE_USER_NOTIFIER###", userInfoNotifing.getProvince());
      tmpMsg = tmpMsg.replace("###COUNTRY_USER_NOTIFIER###", userInfoNotifing.getCountry());
      emailHelper.setMsg(tmpMsg);
      // </editor-fold>

      emailHelper.addReplyTo(userInfoNotifing.getEmail());

      // Invio dell'email
      rSend = emailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending sportCenter inserted notification email", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  // <editor-fold defaultstate="collapsed" desc="ORGANIZED MATCH RELATED MAILS">

  /**
   * invia mail a utenti per notificargli la conferma di richiesta possibilità organizzazione partite
   *
   * @param user colui che ha fatto richiesta
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendNewOrganizerConfirm(User user, Cobrand currentCobrand)
  {
    AEmailHelper emailHelper;
    Result<String> rSend;

    try
    {
      Language language = LanguageManager.chooseUserLanguage(user);
      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.UserNewOrganizerConfirm, language,
        currentCobrand);
      //from
      //emailHelper.setFromName(userInfo.getFirstLastName());
      //reply to
      //emailHelper.addReplyTo(userInfo.getEmail(), userInfo.getFirstLastName());
      // To
      emailHelper.addTo(user.getEmail());
      // Message


      String message = emailHelper.getMsg();

      message = StringUtils.replace(message, REPLACE_USER_NAME, user.getFirstName());

      emailHelper.setMsg(message);
      // Invio dell'email
      rSend = emailHelper.send();
      if (!rSend.isSuccessNotNull())
      {

        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Invio email conferma richiesta nuovo organizzatore", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  /**
   * invia mail a utenti per notificargli la conferma di richiesta possibilità organizzazione partite
   *
   * @param user colui che ha fatto richiesta
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendNewOrganizerToBackOffice(User user, String freeText, Cobrand currentCobrand)
  {
    AEmailHelper emailHelper;
    Result<String> rSend;
    try
    {
      Language language = LanguageManager.getByLanguage(ITALIAN_LANGUAGE);
      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.BackofficeNewOrganizer, language,
        currentCobrand);
      String userName = user.getFirstName() + " " + user.getLastName();
      //from
      emailHelper.setFrom(user.getEmail(), userName);
      //reply to
      emailHelper.addReplyTo(user.getEmail());
      // To
      //emailHelper.addTo(user.getEmail());
      // Message

      String subject = emailHelper.getSubject();
      subject = StringUtils.replace(subject, REPLACE_COMPLETE_USER_NAME, userName);
      subject = StringUtils.replace(subject, REPLACE_USER_CITY, user.getCity().getName());
      subject = StringUtils.replace(subject, REPLACE_USER_PROVINCE, user.getProvince().getName());
      subject = StringUtils.replace(subject, REPLACE_USER_NATION, user.getCountry().getName());
      emailHelper.setSubject(subject);
      String message = emailHelper.getMsg();
      message = StringUtils.replace(message, REPLACE_FREE_TEXT, freeText);
      message = StringUtils.replace(message, REPLACE_USER_ID, user.getId().toString());
      message = StringUtils.replace(message, REPLACE_COMPLETE_USER_NAME,
        user.getFirstName() + " " + user.getLastName());
      message = StringUtils.replace(message, REPLACE_USER_EMAIL, user.getEmail());
      message = StringUtils.replace(message, REPLACE_BECOME_ORGANIZER_DATE,
        DateManager.showDate(new Date(), DateManager.FORMAT_DATE_15, language, currentCobrand));
      message = StringUtils.replace(message, REPLACE_USER_CITY, user.getCity().getName());
      message = StringUtils.replace(message, REPLACE_USER_PROVINCE, user.getProvince().getName());
      message = StringUtils.replace(message, REPLACE_USER_NATION, user.getCountry().getName());

      emailHelper.setMsg(message);
      // Invio dell'email
      rSend = emailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logSendError(rSend);
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Invio email conferma richiesta nuovo organizzatore", ex);
      return new Result<Boolean>(ex);
    }
    return new Result<Boolean>(true, true);
  }

  /**
   * invia mail a utenti per notificargli la conferma di richiesta possibilità organizzazione partite
   *
   * @param user colui che ha fatto richiesta
   * @return Un oggetto result con l'esito dell'operazione ed eventuali informazioni di errore in caso di fallimento
   */
  public static Result<Boolean> sendThirdMatchOrganized(User user, Cobrand currentCobrand)
  {
    AEmailHelper emailHelper;
    Result<String> rSend;

    try
    {
      Language language = LanguageManager.chooseUserLanguage(user);
      emailHelper = EmailProvider.getEmailHelper(EnumEmailConfigurationType.UserThirdOrganizedMatch, language,
        currentCobrand);
      //from
      //emailHelper.setFromName(userInfo.getFirstLastName());
      //reply to
      //emailHelper.addReplyTo(userInfo.getEmail(), userInfo.getFirstLastName());
      // To
      emailHelper.addTo(user.getEmail());
      // Message

      String message = emailHelper.getMsg();

      message = StringUtils.replace(message, REPLACE_USER_NAME, user.getFirstName());

      emailHelper.setMsg(message);
      // Invio dell'email
      //Visto che ormai ci sono moltissimi amici a cui mandare l'email uso l'invio asincrono!
      //quindi questo metodo restituira sempre true
      new EmailAsynch(emailHelper).send();
    }
    catch (Exception ex)
    {
      logger.error("errore in Invio email nitifica entrata in Dream team", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="UTILITY METHODS">

  /**
   * Suddivide il testo passato in un lista di stringhe
   * Vengono eliminati i ritorni a capo "\r\n" e le righe vuote
   *
   * @param emailText Testo da controllare
   * @return L'array di stringhe risultatnte
   */
  public static List<String> split(String emailText)
  {
    String osLineSeparator = SystemUtils.LINE_SEPARATOR != null ? SystemUtils.LINE_SEPARATOR : "\n";
    List<String> results = new ArrayList<String>();
    emailText = StringUtils.replace(emailText, osLineSeparator, EMAIL_SEPARATOR);
    String[] mails = StringUtils.split(emailText, EMAIL_SEPARATOR);
    for (String mail : mails)
    {
      mail = mail.trim();
      if (StringUtils.isNotBlank(mail))
      {
        results.add(mail);
      }
    }
    return results;
  }

  /**
   * restituisce la lingua basandosi sulla nazionalità dell'utente
   *
   * @param country stringa contente il nome della country
   * @return language
   */

  /**
   * restituisce il messaggio sostituendo alle stringhe apposite id e pwd utente (criptata con Md5)
   *
   * @param tmpMsg messaggio a cui effettuare il replace
   * @param idUser id utente al quale arriva la mail
   * @param pwd    password id utente al quale arriva la mail
   * @return Stringa contenente il mess con sostituzioni applicate
   */
  private static String replaceCredentialAutoLog(String tmpMsg, Integer idUser, String pwd)
  {
    tmpMsg = tmpMsg.replace(REPLACE_USER_ID, idUser.toString());
    tmpMsg = tmpMsg.replace(REPLACE_USER_PASSWORD, MD5.getHashString(pwd));
    return tmpMsg;
  }

  public static void sendPlayMorePartnerNotify(User currentUser, Cobrand currentCobrand, long distance)
  {
    try
    {
      Language language = LanguageManager.chooseUserLanguage(currentUser);

      //Imposto il tipo di email da mandare
      EnumEmailConfigurationType emailType = EnumEmailConfigurationType.PlayMorePartnerNotifyGreaterThanZero;
      if (distance == 0)
      {
        emailType = EnumEmailConfigurationType.PlayMorePartnerNotifyEqualZero;
      }
      else if (distance < 0)
      {
        emailType = EnumEmailConfigurationType.PlayMorePartnerNotifyLessThanZero;
      }
      HtmlEmailHelper htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(emailType, language,
        currentCobrand, currentUser.getId());

      // To
      htmlEmailHelper.addTo(currentUser.getEmail(), currentUser.getFirstName() + " " + currentUser.getLastName());

      // Replace
      String tmpSubject = htmlEmailHelper.getSubject();
      //il nome viene già messo in automatico
      tmpSubject = tmpSubject.replace(REPLACE_PLAY_MORE_EXPIRE_DAYS, Long.toString(Math.abs(distance)));
      tmpSubject = tmpSubject.replace(REPLACE_PLAY_MORE_EXPIRE_DAYS_DIFF, Long.toString(30 - Math.abs(distance)));
      htmlEmailHelper.setSubject(tmpSubject);

      String tmpMsg = htmlEmailHelper.getMsg();
      //in nome viene già messo in automatico
      tmpMsg = tmpMsg.replace(REPLACE_PLAY_MORE_EXPIRE_DAYS, Long.toString(Math.abs(distance)));
      tmpMsg = tmpMsg.replace(REPLACE_PLAY_MORE_EXPIRE_DAYS_DIFF, Long.toString(30 - Math.abs(distance)));
      htmlEmailHelper.setMsg(tmpMsg);

      StringBuilder sbDebug = new StringBuilder();
      sbDebug.append(SystemUtils.LINE_SEPARATOR).append("###").append("IdUser: ").append(currentUser.getId()).append(
        ", ").append(currentUser.getFirstName()).append(" ").append(currentUser.getEmail()).append(
        SystemUtils.LINE_SEPARATOR).append(htmlEmailHelper.getSubject()).append(SystemUtils.LINE_SEPARATOR).append(
        "###").append("###").append(htmlEmailHelper.getMsg()).append(SystemUtils.LINE_SEPARATOR).append(
        SystemUtils.LINE_SEPARATOR);
      logger.debug(sbDebug);
      new EmailAsynch(htmlEmailHelper).send();
    }
    catch (Exception ex)
    {
      //HibernateSessionHolder.rollbackTransaction();
      logger.error("Invio email soci play more fallito", ex);
    }
  }

  public static void  sendWakeUpMail(User user, Cobrand currentCobrand, Set<Match> match) throws Exception
  {

    HtmlEmailHelper htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(
      EnumEmailConfigurationType.MatchWakeUpUser, LanguageManager.chooseUserLanguage(user), currentCobrand);
    String msg = htmlEmailHelper.getMsg();
    msg = replaceCredentialAutoLog(msg, user.getId(), user.getPassword());
    msg = msg.replace(REPLACE_CURRENTUSER_NAME,  user.getFirstName());
    msg = msg.replace(REPLACE_MATCH_TO_PLAY, getMatchString(match, user, currentCobrand));

    htmlEmailHelper.addTo(user.getEmail());
    htmlEmailHelper.setMsg(msg);
    htmlEmailHelper.send();
  }

  private static String getMatchString(Set<Match> matchList, User user, Cobrand currentCobrand)
  {
    StringBuilder matchListToPlay = new StringBuilder();
    for (Match match : matchList)
    {

      String matchToPlay = MATCH_TO_PLAY_TEMPLATE;


      matchToPlay = matchToPlay.replace(EmailManager.REPLACE_MATCH_TYPE_NAME, match.getMatchType().getLabel());

      matchToPlay = matchToPlay.replace(REPLACE_MATCH_START_DAY,
        DateManager.showDate(match.getMatchStart(), DateManager.FORMAT_DATE_3, LanguageManager.chooseUserLanguage(user),
          currentCobrand));

      matchToPlay = matchToPlay.replace(REPLACE_MATCH_ID, Integer.toString(match.getId()));
      matchToPlay = matchToPlay.replace(REPLACE_SPORTCENTER_NAME, match.getSportCenterName());
      matchToPlay = matchToPlay.replace(REPLACE_SPORTCENTER_ADDRESS, match.getSportCenterAddress());
      matchToPlay = matchToPlay.replace(REPLACE_SPORTCENTER_CITY, match.getSportCenterCity());
      matchListToPlay.append(matchToPlay);
    }
    return matchListToPlay.toString();

  }
  // </editor-fold>

  private static class EmailAsynch
  {
    private AEmailHelper emailHelper;
    private String uid;
    private Date start;

    public EmailAsynch(AEmailHelper emailHelper)
    {
      this.emailHelper = emailHelper;
      this.uid = RandomStringUtils.randomAlphanumeric(10);
      this.start = new Date();
    }

    public void send()
    {
      Runnable worker = new Runnable()
      {
        @Override
        public void run()
        {
          int waitFor = RandomUtils.nextInt() % 5000;
          logger.debug(uid + " " + "EmailAsynch run and wait for ms " + waitFor);
          try
          {
            Thread.sleep(waitFor);
          }
          catch (InterruptedException e)
          {
          }
          logger.debug(uid + " " + "Send email with subject: " + emailHelper.getSubject());
          Result<String> rSend = emailHelper.send();
          if (rSend.isSuccessNotNull())
          {
            if (logger.isInfoEnabled())
            {
              logger.info(uid + " " + DateUtil.printElapsedTime(start) + " OK: " + rSend.getValue());
            }
          }
          else
          {
            logSendError(rSend, uid + " " + DateUtil.printElapsedTime(start));
          }
        }
      };
      new Thread(worker).start();
    }
  }
}//End Class

