package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.UsersPermission;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione dei permessi.
 */
public class UsersPermissionDAO extends AGenericDAO < UsersPermission, Integer >
{

  /**
   *
   * @param session
   */
  public UsersPermissionDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return lista di permessi per l'utente indicato
   */
  public List<UsersPermission> get(int idUser)
  {
    Criteria criteria = getSession().createCriteria(UsersPermission.class);
    criteria.add(Restrictions.eq("user.id", idUser));
    return criteria.list();
  }
}
