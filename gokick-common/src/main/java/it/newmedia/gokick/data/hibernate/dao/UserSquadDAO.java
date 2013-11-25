package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.enums.EnumUserSquadStatus;
import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.UserSquad;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione degli utenti delle rose. Contiene tutti i metodi che lavorano sulla tabella USER_SQUADS.
 */
public class UserSquadDAO extends AGenericDAO< UserSquad, Integer>
{

  /**
   *
   * @param session
   */
  public UserSquadDAO(Session session)
  {
    super(session);
  }

  /**
   * Return la lista degli id delle rose che hanno richiesto l'amicizia all'utente specificato
   * @param idUser id dell'utente a cui è stata chiesta l'amicizia
   * @return la lista degli id degli utenti che hanno richiesto l'amicizia
   */
  public List getRequestsReceivedByIdUser(int idUser)
  {
    String sql = "SELECT us.squad.id FROM UserSquad AS us WHERE us.user.id = :idUser AND us.owner = :owner AND us.userSquadStatus = :userSquadStatusRequest";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idUser", idUser);
    query.setParameter("owner", false);
    query.setParameter("userSquadStatusRequest", EnumUserSquadStatus.Invited.getValue());
    return query.list();
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return numero inviti a squadre ricevuti
   */
  public Integer countRequestsReceivedByIdUser(int idUser)
  {
    Criteria crit = getSession().createCriteria(UserSquad.class);
    crit.add(Restrictions.eq("user.id", idUser));
    crit.add(Restrictions.eq("owner", false));
    crit.add(Restrictions.eq("userSquadStatus", EnumUserSquadStatus.Invited.getValue()));
    crit.setProjection(Projections.rowCount());
    return ((Integer) crit.list().get(0)).intValue();

  }

  /**
   *
   * @param idUser
   * @return il numero di relazioni in sospeso (richieste di amicizia effettutate o ricevute)
   */
  public Integer countPendingRelations(int idUser)
  {
    Criteria crit = getSession().createCriteria(UserSquad.class);
    crit.add(Restrictions.eq("owner", false));

    crit.createAlias("squad", "sq");

    Criterion isInvited = Restrictions.eq("user.id", idUser);
    Criterion hasInvited = Restrictions.eq("sq.user.id", idUser);

    crit.add(Restrictions.or(isInvited, hasInvited));

    Criterion statusInvited = Restrictions.eq("userSquadStatus", EnumUserSquadStatus.Invited.getValue());
    Criterion statusRequest = Restrictions.eq("userSquadStatus", EnumUserSquadStatus.Request.getValue());
    crit.add(Restrictions.or(statusInvited, statusRequest));

    crit.setProjection(Projections.rowCount());
    return ((Integer) crit.list().get(0)).intValue();
  }

  /**
   *
   * @param idUser
   * @return di quanti user sono amico confermato
   */
  public Integer countRelationsConfirmed(int idUser)
  {
    Criteria crit = getSession().createCriteria(UserSquad.class);
    crit.add(Restrictions.eq("user.id", idUser));
    crit.add(Restrictions.eq("owner", false));
    crit.add(Restrictions.eq("userSquadStatus", EnumUserSquadStatus.Confirmed.getValue()));
    crit.setProjection(Projections.rowCount());
    return ((Integer) crit.list().get(0)).intValue();
  }

  /**
   * Return la lista degli id delle associazioni relative all'utente specificato
   * @param idUser id dell'utente a cui è stata chiesta l'amicizia
   * @return la lista degli id delle associazioni relative all'utente specificato
   */
  public List getAllByIdUser(int idUser)
  {
    String sql = "SELECT us.id FROM UserSquad AS us WHERE us.user.id = :idUser";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idUser", idUser);
    return query.list();
  }

  /**
   * Return la lista degli id degli utenti a cui il proprietario della rosa corrente ha richiesto l'amicizia
   * @param idSquad id della rosa dell'utente
   * @return la lista degli id degli utenti a cui il proprietario della rosa corrente ha richiesto l'amicizia
   */
  public List getAllRequestsMadeByIdSquad(int idSquad)
  {
    String sql = "SELECT us.user.id FROM UserSquad AS us WHERE us.squad.id = :idSquad AND us.owner = :owner AND us.userSquadStatus = :userSquadStatusInvited";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idSquad", idSquad);
    query.setParameter("owner", false);
    query.setParameter("userSquadStatusInvited", EnumUserSquadStatus.Invited.getValue());
    return query.list();
  }

  /**
   * Return la lista degli id degli utenti appartenenti ad una rosa
   * @param idSquad id della rosa
   * @return la lista degli id degli utenti appartenenti
   */
  public List getAllConfirmedUserByIdSquad(int idSquad)
  {
    return getAllConfirmedUserByIdSquad(idSquad, false);
  }

  /**
   * Return la lista degli id degli utenti appartenenti ad una rosa
   * @param idSquad id della rosa
   * @return la lista degli id degli utenti appartenenti
   */
  public List getAllConfirmedUserByIdSquad(int idSquad, boolean onMarket)
  {
    String sql = "SELECT us.user.id FROM UserSquad AS us WHERE us.squad.id = :idSquad AND us.userSquadStatus = :userSquadStatusConfirmed ";
    if (onMarket)
    {
      sql += " AND us.user.marketEnabled = :onMarket ";
    }

    sql += " ORDER BY us.owner DESC, us.user.firstName ASC , us.user.lastName ASC  ";

    Query query = getSession().createQuery(sql.toString());
    
    query.setParameter("idSquad", idSquad);
    
    if (onMarket)
    {
      query.setParameter("onMarket", onMarket);
    }
    
    query.setParameter("userSquadStatusConfirmed", EnumUserSquadStatus.Confirmed.getValue());
    
    return query.list();
  }

  /**
   * 
   * @param idSquad squadra d'interesse
   * @param idUserToRelate user d'interesse
   * @return lista oggeti contenti gli id utente relazionati di una squadra
   */
  public List<Object[]> getAllIdUserWithRelationByIdSquad(int idSquad, int idUserToRelate)
  {
    Criteria criteria = getSession().createCriteria(UserSquad.class);

    criteria.createAlias("squad", "s");
    criteria.setProjection(Projections.projectionList().
            add(Projections.property("s.user.id")).
            add(Projections.property("user.id")));

    criteria.add(Restrictions.or(Restrictions.eq("squad.id", idSquad),
            Restrictions.eq("user.id", idUserToRelate)));
    return criteria.list();
  }

  /**
   *
   * @param idSquad squadra d'interesse
   * @return numero utenti confermati nella squadra
   */
  public Integer getConfirmedUserByIdSquad(int idSquad)
  {
    Criteria criteria = getSession().createCriteria(UserSquad.class);
    criteria.setProjection(Projections.rowCount());
    criteria.add(Restrictions.eq("userSquadStatus", EnumUserSquadStatus.Confirmed.getValue()));
    criteria.add(Restrictions.eq("squad.id", idSquad));
    criteria.add(Restrictions.eq("owner", false));
    return ((Integer) criteria.list().get(0)).intValue();
  }

  /**
   * Return l'associazione tra la rosa e l'utente
   * @param idUser id dell'utente
   * @param idSquad id della rosa
   * @return l'associazione tra la rosa e l'utente
   */
  public UserSquad getByIdUserAndIdSquad(int idUser, int idSquad)
  {
    Criteria criteria = getSession().createCriteria(UserSquad.class);
    criteria.add(Restrictions.eq("user.id", idUser));
    criteria.add(Restrictions.eq("squad.id", idSquad));
    criteria.setMaxResults(1);
    return (UserSquad) criteria.uniqueResult();
  }

  /**
   *
   * @param idSquad squadra d'interesse
   * @param idUserSquad id UserSquad
   * @return id utente
   * @throws Exception
   */
  public int getByIdSquadAndIdUserSquad(int idSquad, int idUserSquad) throws
          Exception
  {
    String sql = "SELECT us.user.id FROM UserSquad AS us WHERE  us.squad.id = :idSquad AND us.user.id = :idUserSquad";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idSquad", idSquad);
    query.setParameter("idUserSquad", idUserSquad);
    return (Integer) (query.uniqueResult() != null ? query.uniqueResult() : -1);
  }

  /**
   * Return se l'utente è già relazionato con questa rosa
   * @param idSquad id della rosa
   * @param idUser id dell'utente
   * @return se l'utente è già relazionato con questa rosa
   */
  public boolean checkUserSquadAlreadyRelated(int idSquad, int idUser)
  {
    String sql = "FROM UserSquad AS us WHERE us.squad.id = :idSquad AND us.user.id = :idUser";
    Query query = getSession().createQuery(sql);
    query.setParameter("idSquad", idSquad);
    query.setParameter("idUser", idUser);
    return (query.list().isEmpty()) ? false : true;
  }
}
