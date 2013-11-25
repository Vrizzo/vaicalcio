package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.UserInvitation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione degli utenti delle rose. Contiene tutti i metodi che lavorano sulla tabella USER_SQUADS.
 */
public class UserInvitationDAO extends AGenericDAO < UserInvitation, Integer >
{

  /**
   *
   * @param session
   */
  public UserInvitationDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param idUserOwner id utente proprietario
   * @return numero inviti usati
   * @throws Exception
   */
  public int getCountUsed(int idUserOwner) throws Exception
  {
    Criteria criteria = getSession().createCriteria(UserInvitation.class);
    criteria.setProjection(Projections.rowCount());
    criteria.add(Restrictions.eq("userOwner.id", idUserOwner));
    criteria.add(Restrictions.isNotNull("user"));
    return ((Integer)criteria.list().get(0)).intValue();
  }

  /**
   * Se esiste restituisce l'invito con il codice richiesto
   * @param invitationCode Codice dell'invito da cercare
   * @return L'invito trovato o null se non trovato
   * @throws Exception
   */
  public UserInvitation getByCode(String invitationCode) throws Exception
  {
    return findEntityByCriteria(
            Restrictions.eq("code", invitationCode)
            );
  }

  /**
   * Se esiste restituisce l'invito utilizzato dall'utente per iscriversi
   * @param idUser Id dell'utente che ha usato l'invito (NON che lo ha spedito...)
   * @return L'invito trovato o null se non trovato
   * @throws Exception
   */
  public UserInvitation getByIdUser(int idUser) throws Exception
  {
    return findEntityByCriteria(
            Restrictions.eq("user.id", idUser)
            );
  }

}
