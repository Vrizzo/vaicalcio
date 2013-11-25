package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.data.hibernate.beans.PitchType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 *
 * DAO per la gestione dei cobrnd. Contiene tutti i metodi che lavorano sulla tabella COBRANDS.
 */
public class CobrandDAO extends AGenericDAO <Cobrand, Integer >
{

  /**
   *
   * @param session
   */
  public CobrandDAO(Session session)
  {
    super(session);
  }

}
