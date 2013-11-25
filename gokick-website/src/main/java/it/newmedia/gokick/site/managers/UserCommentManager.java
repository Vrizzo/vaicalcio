package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.UserComment;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.providers.InfoProvider;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni relative ai commenti verso gli utenti.
 */
public class UserCommentManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserCommentManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private UserCommentManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  
  /**
   * salva il feeedback indicato
   * @param userComment feedback
   * @return "true" in caso positivo
   */
  public static boolean save(UserComment userComment,int idUser)
  { 
    try
    {
      DAOFactory.getInstance().getUserCommentDAO().makePersistent(userComment);
      //remove from cache
      InfoProvider.removeFeedbackInfo(idUser);
    } catch (Exception e)
    {
      logger.error("error savin userComment", e);
      return false;
    }
    return true;
  }

  /**
   * cerca il feedback per autore e destinatario
   * @param idUser utente destinatario
   * @param idUserAuthor utente autore
   * @return oggetto UserComment (feedback)
   */
  public static UserComment find(int idUser,int idUserAuthor)
  {
    UserComment userComment= new UserComment();
    try
    {
      userComment = DAOFactory.getInstance().getUserCommentDAO().find(idUser, idUserAuthor);
    } catch (Exception e)
    {
      logger.error("error searching comment", e);
      return null;
    }
    return userComment;
  }

  
  // </editor-fold>
}
