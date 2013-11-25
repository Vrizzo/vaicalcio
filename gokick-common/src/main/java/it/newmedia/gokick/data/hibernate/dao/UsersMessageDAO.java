package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.UsersMessage;
import org.hibernate.Session;

/**
 *
 * DAO per la gestione dei messaggi. Contiene tutti i metodi che lavorano sulla tabella USERS_MESSAGES.
 */
public class UsersMessageDAO extends AGenericDAO < UsersMessage, Integer >
{

  /**
   *
   * @param session
   */
  public UsersMessageDAO(Session session)
  {
    super(session);
  }

}
