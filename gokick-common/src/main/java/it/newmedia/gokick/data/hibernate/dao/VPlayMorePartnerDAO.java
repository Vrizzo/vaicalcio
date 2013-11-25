package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.VPlayMorePartner;
import org.hibernate.Session;

/**
 *
 * DAO per la gestione della vista. Contiene tutti i metodi che lavorano sulla tabella V_PLAYMORE_PARTNERS.
 */
public class VPlayMorePartnerDAO extends AGenericDAO<VPlayMorePartner, Integer>
{

  public VPlayMorePartnerDAO(Session session)
  {
    super(session);
  }

}
