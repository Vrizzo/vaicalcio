package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.FootballTeam;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * DAO per la gestione delle FootballTeam. Contiene tutti i metodi che lavorano sulla tabella FOOTBALL_TEAMS.
 */
public class FootballTeamDAO extends AGenericDAO < FootballTeam, Integer >
{

  /**
   *
   * @param session
   */
  public FootballTeamDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @return lista di squadre professioniste ordinate per nome
   * @throws Exception
   */
  public List<FootballTeam> getAllOrdered() throws Exception
  {
    Criteria crit = getSession().createCriteria(FootballTeam.class);
    crit.addOrder(Order.asc("name"));
    return crit.list();
  }

  
}
