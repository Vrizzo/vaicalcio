package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.Audit;
import org.hibernate.Session;

/**
 *
 * DAO per la gestione degli audits (log). Contiene tutti i metodi che lavorano sulla tabella AUDITS.
 */
public class AuditDAO extends AGenericDAO <Audit, Integer >
{

  /**
   *
   * @param session
   */
  public AuditDAO(Session session)
  {
    super(session);
  }

}
