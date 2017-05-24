package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.Player;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione dei player. Contiene tutti i metodi che lavorano sulla tabella PLAYERS.
 */
public class PlayerDAO extends AGenericDAO<Player, Integer> {

  /**
   *
   * @param session
   */
  public PlayerDAO(Session session)
  {
    super(session);
  }

  /**
   * Return la lista dei player appartenenti ad una partita
   * @param idMatch id della partita
   * @param withMissing 
   * @return Return la lista dei player appartenenti ad una partita
   * @throws Exception
   */
  public List<Player> getPlayerListByMatch(int idMatch,boolean withMissing) throws Exception
  {
    return getPlayerListByMatch(idMatch,withMissing, null);
  }

  /**
   * Return la lista dei player appartenenti ad una partita in base a degli stati
   * @param idMatch id della partita
   * @param withMissing
   * @param playerStatuses lista degli stati degli utenti che interessano
   * @return la lista dei player appartenenti ad una partita in base a degli stati
   * @throws Exception
   */
  public List<Player> getPlayerListByMatch(int idMatch,boolean withMissing, EnumPlayerStatus[] playerStatuses) throws Exception
  {
    return getPlayerListByMatch(idMatch, withMissing, playerStatuses,false);
  }

  /**
   * Return la lista dei player appartenenti ad una partita in base a degli stati
   * @param idMatch id della partita
   * @param withMissing
   * @param playerStatuses lista degli stati degli utenti che interessano
   * @param orderByName 
   * @return la lista dei player appartenenti ad una partita in base a degli stati
   * @throws Exception
   */
  public List<Player> getPlayerListByMatch(int idMatch,boolean withMissing, EnumPlayerStatus[] playerStatuses,boolean orderByName) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Player.class);
    if (playerStatuses != null && playerStatuses.length > 0)
    {
      String[] playerStatusValues = new String[playerStatuses.length];
      for (int i = 0; i < playerStatuses.length; i++)
      {
        playerStatusValues[i] = playerStatuses[i].getValue();
      }
      criteria.add(Restrictions.in("playerStatus", playerStatusValues));
    }
    if (withMissing==false)
      criteria.add(Restrictions.ne("playerType", EnumPlayerType.Missing.getValue()));
    criteria.createCriteria("matchList").add(Restrictions.eq("id", idMatch));

    if (orderByName)
    {
        criteria.createAlias("user", "us");
        criteria.addOrder(Order.asc("us.firstName"));
    }
    else
        criteria.addOrder(Order.asc("requestDate"));

    return criteria.list();
  }
  
  /**
   * Return il conteggio dei player appartenenti ad una partita in base a degli stati
   * @param idMatch id della partita
   * @param withMissing
   * @param playerStatuses lista degli stati degli utenti che interessano
   * @return il conteggio dei player appartenenti ad una partita in base a degli stati
   * @throws Exception
   */
  public int countPlayerByMatch(int idMatch,
                                boolean withMissing,
                                EnumPlayerStatus[] playerStatuses,
                                Session sess) throws Exception
  {
    Criteria criteria = sess.createCriteria(Player.class);
    criteria.setProjection(Projections.rowCount());
    if (playerStatuses != null && playerStatuses.length > 0)
    {
      String[] playerStatusValues = new String[playerStatuses.length];
      for (int i = 0; i < playerStatuses.length; i++)
      {
        playerStatusValues[i] = playerStatuses[i].getValue();
      }
      criteria.add(Restrictions.in("playerStatus", playerStatusValues));
    }
    if (withMissing==false)
      criteria.add(Restrictions.ne("playerType", EnumPlayerType.Missing.getValue()));
    criteria.createCriteria("matchList").add(Restrictions.eq("id", idMatch));
    return ((Integer) criteria.list().get(0)).intValue();
  }

  /**
   * Return il conteggio dei player appartenenti ad una partita in base a degli stati
   * @param idMatch id della partita
   * @param withMissing
   * @param playerStatuses lista degli stati degli utenti che interessano
   * @return il conteggio dei player appartenenti ad una partita in base a degli stati
   * @throws Exception
   */
  public int countPlayerByMatch(int idMatch,
                                boolean withMissing,
                                EnumPlayerStatus[] playerStatuses) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Player.class);
    criteria.setProjection(Projections.rowCount());
    if (playerStatuses != null && playerStatuses.length > 0)
    {
      String[] playerStatusValues = new String[playerStatuses.length];
      for (int i = 0; i < playerStatuses.length; i++)
      {
        playerStatusValues[i] = playerStatuses[i].getValue();
      }
      criteria.add(Restrictions.in("playerStatus", playerStatusValues));
    }
    if (withMissing==false)
      criteria.add(Restrictions.ne("playerType", EnumPlayerType.Missing.getValue()));
    
    criteria.createCriteria("matchList").add(Restrictions.eq("id", idMatch));
    return ((Integer) criteria.list().get(0)).intValue();
  }

  /**
   * Return il pagellatore della partita
   * @param idMatch id della partita
   * @return il pagellatore della partita
   * @throws Exception
   */
  public Player getReporterByMatch(int idMatch) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Player.class);
    criteria.createCriteria("matchList").add(Restrictions.eq("id", idMatch));
    criteria.add(Restrictions.eq("reporterEnabled", true));
    return criteria.list().isEmpty() ? null : (Player) criteria.list().get(0);
  }

  /**
   *
   * @param idMatch match d'interesse
   * @return lista di giocatori che hanno fatto richiesta per giocare
   * @throws Exception
   */
  public List getAllRequestUserByIdMatch(int idMatch) throws Exception
  {
    String[] playerStatusesValues={EnumPlayerStatus.UserRequest.getValue(),EnumPlayerStatus.UserRequestRegistered.getValue()};
    Criteria criteria = getSession().createCriteria(Player.class);
    criteria.createCriteria("matchList").add(Restrictions.eq("id", idMatch));
    //criteria.add(Restrictions.eq("playerStatus", EnumPlayerStatus.UserRequest.getValue()));
    criteria.add(Restrictions.in("playerStatus", playerStatusesValues));
    criteria.createAlias("user", "us");
    criteria.addOrder(Order.asc("us.firstName"));

    return criteria.list();

  }


  /**
   *
   * @param idMatch match d'interesse
   * @return lista di giocatori che sono stati convocati
   * @throws Exception
   */
  public List getIdUserByPlayerStatus(int idMatch,String[] playerStatusesValues) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Player.class);
    criteria.createCriteria("matchList").add(Restrictions.eq("id", idMatch));
    //criteria.add(Restrictions.eq("playerStatus", EnumPlayerStatus.UserRequest.getValue()));
    criteria.add(Restrictions.in("playerStatus", playerStatusesValues));
    criteria.createAlias("user", "us");
    criteria.setProjection(Projections.property("us.id"));
    criteria.addOrder(Order.asc("us.firstName")).addOrder(Order.asc("us.lastName"));
    return criteria.list();

  }



  /**
   *
   * @param idMatch match d'interesse
   * @param idUser utente d'interesse
   * @return Player (giocatore)
   * @throws Exception
   */
  public Player getPlayer(int idMatch,int idUser) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Player.class);
    criteria.add(Restrictions.eq("user.id", idUser));
    criteria.createCriteria("matchList").add(Restrictions.eq("id", idMatch));
    List results = criteria.list();
    return results.size() > 0 ? (Player)results.get(0) : null;
  }


  public List<Integer> findLastUserIsPlayer(Integer id, int i)
  {
    return null;
  }
}
