package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.MatchComment;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.data.hibernate.beans.UserLastViewComment;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.MatchCommentInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni relative ai commenti alle partite.
 */
public class MatchCommentManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(MatchCommentManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private MatchCommentManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   *
   * @param idMatch match in oggetto
   * @return numero dei commenti relativi al match in oggetto
   */
  public static int countMatchComments(int idMatch)
  {
    try
    {
      return DAOFactory.getInstance().getMatchCommentDAO().countByIdMatch(idMatch);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  /**
   * aggiorna l'oggetto UserLastViewComment per l'utente ed il match specificati dall'id
   * @param idUser id utente
   * @param idMatch match in oggetto
   * @return "true" in caso di successo
   */
  public static boolean updateLastViewComment(int idUser,int idMatch)
  {
    StringBuilder sb = new StringBuilder();
    try
    {
      sb.append(System.getProperty("line.separator"));
      sb.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>updateLastViewComment(idUser = ")
              .append(idUser)
              .append(" , idMatch = ")
              .append(idMatch)
              .append(" )<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
              .append(System.getProperty("line.separator"));
      
      UserLastViewComment lastViewComment=getLastViewComment(idUser,idMatch);

      sb.append("UserLastViewComment == null? " + lastViewComment==null);

      if (lastViewComment==null)
      {
        lastViewComment = new UserLastViewComment();
        lastViewComment.setIdUser(idUser);
        lastViewComment.setIdMatch(idMatch);
      }
      lastViewComment.setLastView(new Date());
      DAOFactory.getInstance().getUserLastViewCommentDAO().makePersistent(lastViewComment);

      sb.append(System.getProperty("line.separator"));
      sb.append(lastViewComment.getId());
      sb.append(System.getProperty("line.separator"));
      sb.append("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< -- END -- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
       sb.append(System.getProperty("line.separator"));

      InfoProvider.removeUserInfo(idUser);
      return true;
      }
      catch (Exception e)
      {
        logger.error(e, e);
        //logger.error(sb.toString());
        return false;
      }
  }

  /**
   *
   * @param idUser id dell'utente d'interesse
   * @param idMatch  id del match in oggetto
   * @return UserLastViewComment se esiste
   */
  public static UserLastViewComment getLastViewComment(int idUser,int idMatch)
  {
    return DAOFactory.getInstance().getUserLastViewCommentDAO().get(idUser,idMatch);
  }

  /**
   *
   * @param idUser id user interessato
   * @return lista degli oggetti UserLastViewComment
   */
  public static List<UserLastViewComment> getUserLastViewComments(int idUser)
  {
    return DAOFactory.getInstance().getUserLastViewCommentDAO().get(idUser);
  }

  /**
   *
   * @param idMatchComment id del commento
   * @return oggetto MatchCommentInfo, se non esiste lo memorizza in cache
   */
  public static MatchCommentInfo getMatchCommentInfo(int idMatchComment)
  {
    return InfoProvider.getMatchCommentInfo(idMatchComment);
  }

  /**
   *
   * @param idMatch id match d'interesse
   * @return lista di oggetti MatchCommentInfo relativi ad un match, se non esistono li memorizza in cache
   */
  public static List<MatchCommentInfo> getMatchCommentInfoList(int idMatch)
  {
    List<MatchCommentInfo> matchCommentInfoList = new ArrayList<MatchCommentInfo>();
    List<Integer> idMatchCommentList;
    try
    {
      idMatchCommentList = DAOFactory.getInstance().getMatchCommentDAO().getByIdMatch(idMatch);
      for (int i = 0; i < idMatchCommentList.size(); i++)
      {
        MatchCommentInfo matchCommentInfo = InfoProvider.getMatchCommentInfo(idMatchCommentList.get(i));
        matchCommentInfoList.add(matchCommentInfo);
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }

    return matchCommentInfoList;
  }

  /**
   * salva commento al match
   * @param commentText testo del commento
   * @param match match relativo
   * @param userOwner proprietario commento
   * @return id del commento inserito, 0 in caso di errori
   */
  public static int save(String commentText, Match match, User userOwner)
  {
    MatchComment matchComment = new MatchComment();
    try
    {
      matchComment.setMatch(match);
      matchComment.setUser(userOwner);
      matchComment.setTextDetail(commentText);
      matchComment.setApproved(true);
      matchComment.setDeleted(false);
      matchComment.setCreated(new Date());
      Date now = new Date();
      match.setLastComment(now);

      UserLastViewComment lastViewComment=DAOFactory.getInstance().getUserLastViewCommentDAO().get(userOwner.getId(),match.getId());
      if (lastViewComment==null)
      {
        lastViewComment = new UserLastViewComment();
        lastViewComment.setIdUser(userOwner.getId());
        lastViewComment.setIdMatch(match.getId());
      }

      lastViewComment.setLastView(now);

      DAOFactory.getInstance().getMatchCommentDAO().makePersistent(matchComment);
      DAOFactory.getInstance().getUserLastViewCommentDAO().makePersistent(lastViewComment);

      InfoProvider.removeUserInfo(userOwner.getId());
      InfoProvider.removeMatchInfo(match.getId());
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }

    return matchComment.getId();
  }

  /**
   * cancella commento al match ed elimina userInfo per l'utente che lo ha inserito
   * @param idMatchComment id del commento da eliminare
   * @param idUserOwner id dell'utente proprioetario
   * @return "true" in caso di successo
   */
  public static boolean delete(int idMatchComment, int idUserOwner)
  {
    try
    {
      MatchComment matchComment = DAOFactory.getInstance().getMatchCommentDAO().get(idMatchComment);

      if (matchComment.getDeleted() || !matchComment.getApproved())
        return false;

      matchComment.setDeleted(true);

      DAOFactory.getInstance().getMatchCommentDAO().makePersistent(matchComment);

      InfoProvider.removeUserInfo(idUserOwner);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }

    return true;
  }

  /**
   * aggiorna un commento esistente
   * @param idMatchComment id del commento
   * @param commentText testo che sostituirà quello esistente
   * @return id del commento aggiornato, 0 in caso di errore
   */
  public static int update(int idMatchComment, String commentText)
  {
    MatchComment matchComment = new MatchComment();
    try
    {
      matchComment = DAOFactory.getInstance().getMatchCommentDAO().get(idMatchComment);

      if (matchComment.getDeleted() || !matchComment.getApproved())
        return 0;

      matchComment.setTextDetail(commentText);

      //Match currentMatch=DAOFactory.getInstance().getMatchDAO().get(matchComment.getMatch().getId());
      //currentMatch.setLastComment(new Date());
      DAOFactory.getInstance().getMatchCommentDAO().makePersistent(matchComment);

      InfoProvider.removeMatchCommentInfo(idMatchComment);
      //InfoProvider.removeMatchInfo(currentMatch.getId());
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }

    return matchComment.getId();
  }

  // </editor-fold>
}
