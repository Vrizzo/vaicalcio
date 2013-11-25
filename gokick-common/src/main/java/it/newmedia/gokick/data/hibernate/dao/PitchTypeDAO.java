package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.PitchType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * DAO per la gestione dei tipi campo. Contiene tutti i metodi che lavorano sulla tabella PICH_TYPE.
 */
public class PitchTypeDAO extends AGenericDAO < PitchType, Integer >
{

  /**
   *
   * @param session
   */
  public PitchTypeDAO(Session session)
  {
    super(session);
  }

  
  /**
   *
   * @return lista dei tipi di campo disponibili
   * @throws Exception
   */
  public List<PitchType> getAllOrdered() throws Exception
  {
    Criteria criteria = getSession().createCriteria(PitchType.class);
    criteria.addOrder(Order.asc("position"));
    return criteria.list();
  }
  
}
