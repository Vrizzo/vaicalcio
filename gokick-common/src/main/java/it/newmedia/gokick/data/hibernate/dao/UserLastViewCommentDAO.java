package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.UserLastViewComment;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione dei post alle partite.
 */
public class UserLastViewCommentDAO extends AGenericDAO < UserLastViewComment, Integer >
{

  /**
   *
   * @param session
   */
  public UserLastViewCommentDAO(Session session)
  {
    super(session);
  }


  /**
   *
   * @param idUser utente d'interesse
   * @param idMatch match d'interesse
   * @return UserLastViewComment che rappresenta l'ultimo commento letto dall'utente per il match specificato
   */
  public UserLastViewComment get(int idUser,int idMatch)
  {
    Criteria criteria = getSession().createCriteria(UserLastViewComment.class);
    criteria.add(Restrictions.eq("idUser", idUser));
    criteria.add(Restrictions.eq("idMatch", idMatch));
    return (UserLastViewComment) criteria.uniqueResult();
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return lista di UserLastViewComment per l'utente specificato
   */
  public List<UserLastViewComment> get(int idUser)
  {
    Criteria criteria = getSession().createCriteria(UserLastViewComment.class);
    criteria.add(Restrictions.eq("idUser", idUser));
    return criteria.list();
  }
}
