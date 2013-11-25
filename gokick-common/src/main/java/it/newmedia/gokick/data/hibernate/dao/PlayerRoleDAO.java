package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.PlayerRole;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * DAO per la gestione delle rose. Contiene tutti i metodi che lavorano sulla tabella SQUADS.
 */
public class PlayerRoleDAO extends AGenericDAO < PlayerRole, Integer >
{

  /**
   *
   * @param session
   */
  public PlayerRoleDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @return lista di ruoli disponibili
   * @throws Exception
   */
  public List<PlayerRole> getAll() throws Exception
  {
    String sql = " FROM PlayerRole AS pr ORDER BY pr.position ASC";
    Query query = getSession().createQuery(sql.toString());
    return query.list();
  }
 
}
