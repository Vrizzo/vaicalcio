package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.MatchComment;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione dei messaggi relativi ad una partita. Contiene tutti i metodi che lavorano sulla tabella MATCH_COMMENTS.
 */
public class MatchCommentDAO extends AGenericDAO < MatchComment, Integer >
{

  /**
   *
   * @param session
   */
  public MatchCommentDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param idMatch match d'interesse
   * @return lista id messaggi per match indicato
   * @throws Exception
   */
  public List<Integer> getByIdMatch(int idMatch) throws Exception
  {
    Criteria criteria = getSession().createCriteria(MatchComment.class);
    criteria.setProjection(
            Projections.distinct(
            Projections.projectionList().
            add(Projections.id())));
    criteria.add(Restrictions.eq("match.id", idMatch));
    criteria.add(Restrictions.eq("approved", true));
    criteria.add(Restrictions.eq("deleted", false));
    criteria.addOrder(Order.asc("created"));
    return criteria.list();
  }

  /**
   *
   * @param idMatch id amtch d'interesse
   * @return numero comenti per match d'interesse
   * @throws Exception
   */
  public int countByIdMatch(int idMatch) throws Exception
  {
    Criteria criteria = getSession().createCriteria(MatchComment.class);
    criteria.setProjection(Projections.rowCount());
    criteria.add(Restrictions.eq("match.id", idMatch));
    criteria.add(Restrictions.eq("approved", true));
    criteria.add(Restrictions.eq("deleted", false));
    return ((Integer)criteria.list().get(0)).intValue();
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return numero commenti effettuati dall'utente
   * @throws Exception
   */
  public int countByIdUser(int idUser) throws Exception
  {
    Criteria criteria = getSession().createCriteria(MatchComment.class);
    criteria.setProjection(Projections.rowCount());
    criteria.add(Restrictions.eq("user.id", idUser));
    criteria.add(Restrictions.eq("approved", true));
    criteria.add(Restrictions.eq("deleted", false));
    return ((Integer)criteria.list().get(0)).intValue();
  }
  
}
