package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.SpecialInvitation;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione degli inviti "speciali"
 */
public class SpecialInvitationDAO extends AGenericDAO < SpecialInvitation, Integer >
{

  /**
   *
   * @param session
   */
  public SpecialInvitationDAO(Session session)
  {
    super(session);
  }

 

  /**
   * Se esiste restituisce l'invito con il codice richiesto
   * @param invitationCode Codice dell'invito da cercare
   * @return L'invito trovato o null se non trovato
   * @throws Exception
   */
  public SpecialInvitation getByCode(String invitationCode) throws Exception
  {
    return findEntityByCriteria(
            Restrictions.eq("code", invitationCode),
            Restrictions.eq("enable", true)
            );
  }


}
