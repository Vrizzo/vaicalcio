package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.AbuseReason;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * DAO per la gestione dei motivi di segnalazione abuso.
 */
public class AbuseReasonDAO extends AGenericDAO < AbuseReason, Integer >
{

  /**
   *
   * @param session
   */
  public AbuseReasonDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @return lista motivi abusi
   * @throws Exception
   */
  public List<AbuseReason> getAll() throws Exception
  {
    String sql = " FROM AbuseReason AS ab ORDER BY ab.position ASC";
    Query query = getSession().createQuery(sql.toString());
    return query.list();
  }
 
}
