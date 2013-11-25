package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.MatchType;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * DAO per la gestione dei tipi di partita. Contiene tutti i metodi che lavorano sulla tabella MATCH_TYPES.
 */
public class MatchTypeDAO extends AGenericDAO < MatchType, Integer >
{

  /**
   *
   * @param session
   */
  public MatchTypeDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @return lista dei tipi di match disponibili
   * @throws Exception
   */
  public List<MatchType> getAll() throws Exception
  {
    String sql = " FROM MatchType AS mt ORDER BY mt.position ASC";
    Query query = getSession().createQuery(sql.toString());
    return query.list();
  }  
}
