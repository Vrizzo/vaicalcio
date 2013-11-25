package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione dei messaggi. Contiene tutti i metodi che lavorano sulla tabella MESSAGES.
 */
public class MessageDAO extends AGenericDAO < Message, Integer >
{

  /**
   *
   * @param session
   */
  public MessageDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param idUserOwner utente destinatario
   * @return lista di messaggi per destinatario
   * @throws Exception
   */
  public List<Message> getByIdUserOwner(int idUserOwner) throws Exception
  {
    return findByCriteria(
            Restrictions.eq("userOwner.id", idUserOwner));
  }

}
