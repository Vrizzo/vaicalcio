package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.PitchCover;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * DAO per la gestione delle coperture campi. Contiene tutti i metodi che lavorano sulla tabella PITCH_COVER.
 */
public class PitchCoverDAO extends AGenericDAO < PitchCover, Integer >
{

  /**
   *
   * @param session
   */
  public PitchCoverDAO(Session session)
  {
    super(session);
  }

  
  /**
   *
   * @return lista dei tipi di copertuta campi
   * @throws Exception
   */
  public List<PitchCover> getAllOrdered() throws Exception
  {
    Criteria criteria = getSession().createCriteria(PitchCover.class);
    criteria.addOrder(Order.asc("position"));
    return criteria.list();
  }
  
}
