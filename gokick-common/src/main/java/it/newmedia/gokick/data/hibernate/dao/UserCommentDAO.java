package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.UserComment;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * DAO per la gestione dei feedback USER_COMMENTS.
 */
public class UserCommentDAO extends AGenericDAO < UserComment, Integer >
{

  /**
   *
   * @param session
   */
  public UserCommentDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param idUser utente destinatario
   * @return lista commenti (feedback) verso un utente
   * @throws Exception
   */
  public List<UserComment> getByIdUser(int idUser) throws Exception
  {
    
    String sql = "FROM UserComment WHERE idUser = :idUser";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idUser", idUser);
    return query.list();
  }

  /**
   *
   * @param idUser utente destinatario
   * @param idUserAuthor utente autore
   * @return commento (feedback) relativo al destinatario ed autore specificati
   * @throws Exception
   */
  public UserComment find(int idUser,int idUserAuthor) throws Exception
  {

    String sql = "FROM UserComment WHERE idUser = :idUser AND userAuthor.id = :idUserAuthor ";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idUser", idUser);
    query.setParameter("idUserAuthor", idUserAuthor);
    return (UserComment) ((query.uniqueResult() == null) ? null : query.uniqueResult());
  }
 
}
