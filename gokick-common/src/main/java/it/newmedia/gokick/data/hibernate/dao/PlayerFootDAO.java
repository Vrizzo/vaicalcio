package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.PlayerFoot;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * DAO per la gestione del "piede" dei giocatori. Contiene tutti i metodi che lavorano sulla tabella PLAYER_FEET.
 */
public class PlayerFootDAO extends AGenericDAO < PlayerFoot, Integer >
{

  /**
   *
   * @param session
   */
  public PlayerFootDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @return lista di "piedi" disponibili
   * @throws Exception
   */
  public List<PlayerFoot> getAll() throws Exception
  {
    return findByCriteria(new Order[]{Order.asc("position")});
  }
 
}
