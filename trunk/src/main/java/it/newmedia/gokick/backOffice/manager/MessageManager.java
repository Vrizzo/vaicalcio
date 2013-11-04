package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.Message;
import it.newmedia.gokick.data.hibernate.dao.UserDAO;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import it.newmedia.gokick.backOffice.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.data.hibernate.beans.UsersMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
  
  public static boolean sendMessageToUsers(int idUserOwner, String privateMessage)
  {
    try
    {
      UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
      Message message = new Message();
      message.setUserOwner(userDAO.load(idUserOwner));
      message.setTextDetail(privateMessage);

      DAOFactory.getInstance().getMessageDAO().makePersistent(message);

    }
    catch (Exception ex)
    {
      logger.error("Error saving message", ex);
      return false;
    }
    return true;
  }

  public static boolean sendMessageToUserList(List<User> userList, String privateMessage)
  {

    //messaggio da associare a piu' utenti
      Message message=new Message();
      message.setTextDetail(privateMessage);
      message.setApproved(true);
      message.setCreated(new Date());

    try
    {
      HibernateSessionHolder.beginTransaction();
      //salvo il messaggio
      DAOFactory.getInstance().getMessageDAO().makePersistent(message);

      //salvo l'associazione
      List<UsersMessage> usersMessagesList = new ArrayList<UsersMessage>();
      for (User user : userList)
      {
        UsersMessage usersMessage = new UsersMessage();
        usersMessage.setIdMessage(message.getId());
        usersMessage.setIdUser(user.getId());
        usersMessage.setSeen(false);
        usersMessagesList.add(usersMessage);
        
      }
      DAOFactory.getInstance().getUsersMessageDAO().makePersistent(usersMessagesList);

      HibernateSessionHolder.commitTransaction();

    }
    catch (Exception ex)
    {
      logger.error("Error saving message in messageManager", ex);
      HibernateSessionHolder.rollbackTransaction();
      return false;
    }
    return true;
  }

  // </editor-fold>
}
