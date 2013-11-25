package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Message;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.data.hibernate.dao.UserDAO;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.providers.TranslationProvider;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello il salvataggio dei messaggi relativi alle azioni fatte dagli utenti nel sito.
 */
public class MessageManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static final String REPLACE_USER_TO = "###USER_TO###";

  private static final String REPLACE_USER_FROM = "###USER_FROM###";

  private static final String REPLACE_MATCH_START_DATE = "###MATCH_START_DATE###";

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(MessageManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private MessageManager()
  {
  }

  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   * salva messaggio in bacheca per appartenenti ad una squadra
   * @param messageKey indica il tipo di messaggio (EnumMessage)
   * @param userFrom user mittente
   * @param userTo  user destinatario
   * @param language
   * @return "true" se a buon fine
   */
  public static boolean saveSquadMessage(String messageKey, User userFrom, User userTo,Language language, Cobrand currentCobrand)
  {
    try
    {
      String textDetail = TranslationProvider.getTranslationValue(messageKey, language, currentCobrand);
      if (userFrom == null)
      {
        userFrom = UserContext.getInstance().getUser();
      }
      textDetail = textDetail.replace(REPLACE_USER_FROM, userFrom.getFirstName() + " " + userFrom.getLastName());

      if (userTo != null)
      {
        textDetail = textDetail.replace(REPLACE_USER_TO, userTo.getFirstName() + " " + userTo.getLastName());
      }


      Message message = new Message();
      //TODO: Verificare se questo è un messaggio di sistema e quindi non ha un proprietario o deve averlo...!!
      message.setUserOwner(null);
      message.setTextDetail(textDetail);

      //TODO: I messaggi devono essere associati agli utenti a cui sono destinati!!
      //Tabella USERS_MESSAGES

      DAOFactory.getInstance().getMessageDAO().makePersistent(message);

    }
    catch (Exception ex)
    {
      logger.error("Error saving message", ex);
      return false;
    }
    return true;
  }

  /**
   *  salva messaggio in bacheca per partecipanti ad una partita
   * @param messageKey tipo messaggio (EnumMessage)
   * @param matchStartDate data inizio partita
   * @param matchStartDateFormat formato data in visualizzazione
   * @param language
   * @return "true" se a buon fine
   */
  public static boolean saveMatchMessage(String messageKey,Date matchStartDate, String matchStartDateFormat, Language language, Cobrand currentCobrand)
  {
    try
    {
      String textDetail = TranslationProvider.getTranslation(messageKey, language, currentCobrand).getKeyValue();
      textDetail = textDetail.replace(REPLACE_MATCH_START_DATE, DateManager.showDate(matchStartDate, matchStartDateFormat));

      Message message = new Message();
      //TODO: Verificare se questo è un messaggio di sistema e quindi non ha un proprietario o deve averlo...!!
      message.setUserOwner(null);
      message.setTextDetail(textDetail);

      //TODO: I messaggi devono essere associati agli utenti a cui sono destinati!!
      //Tabella USERS_MESSAGES

      DAOFactory.getInstance().getMessageDAO().makePersistent(message);

    }
    catch (Exception ex)
    {
      logger.error("Error saving message", ex);
      return false;
    }
    return true;
  }

  /**
   * salva messagio privato in bacheca verso utente
   * @param idUserFrom id user mittente
   * @param privateMessage testo messaggio
   * @return "true" se a buon fine
   */
  public static boolean savePrivateMessage( int idUserFrom, String privateMessage)
  {
    try
    {
      UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
      Message message = new Message();
      message.setUserOwner(userDAO.load(idUserFrom));
      message.setTextDetail(privateMessage);

      //TODO: I messaggi devono essere associati agli utenti a cui sono destinati!!
      //Tabella USERS_MESSAGES
      
      DAOFactory.getInstance().getMessageDAO().makePersistent(message);

    }
    catch (Exception ex)
    {
      logger.error("Error saving message", ex);
      return false;
    }
    return true;
  }

  // </editor-fold>
}
