package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.Squad;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione delle rose. Contiene tutti i metodi che lavorano sulla tabella SQUADS.
 */
public class SquadDAO extends AGenericDAO < Squad, Integer >
{

  /**
   *
   * @param session
   */
  public SquadDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param idUser utente d' interesse
   * @return numero squadre per utente
   * @throws Exception
   */
  public int getCountByIdUser(int idUser) throws Exception
  {
    Criteria criteria = getSession().createCriteria(Squad.class);
    criteria.setProjection(Projections.rowCount());
    criteria.add(Restrictions.eq("user.id", idUser));
    return ((Integer)criteria.list().get(0)).intValue();
  }

  /**
   *
   * @param idUser user d'interesse
   * @return la prima squadra per l'utente
   * @throws Exception
   */
  public Squad getFirstByIdUser(int idUser) throws Exception
  {
    return findEntityByCriteria(
            Restrictions.eq("user.id", idUser));
  }

  /**
   *
   * @param idProvince filtro provincia
   * @param idUser utente d'interesse
   * @return lista di id squadre secondo i filtri specificati
   * @throws Exception
   */
  public List getLimitByIdProvinceAndOpenMarket(int idProvince,int idUser) throws Exception
  {
    String sql = "SELECT s.id FROM Squad AS s WHERE s.user.province.id = :idProvince AND s.marketEnabled = :marketEnabled AND s.hiddenEnabled = :hiddenEnabled AND s.user.id<> :idUser ORDER BY s.user.lastLogin DESC";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idProvince", idProvince);
    query.setParameter("marketEnabled", true);
    query.setParameter("hiddenEnabled", false);
    query.setParameter("idUser", idUser);
    query.setMaxResults(10);
    return query.list();
  }

  /**
   *
   * @param idUser utente d'interessa
   * @return id squadra
   * @throws Exception
   */
  public Integer getByIdUser(int idUser) throws Exception
  {
    Criteria crit = getSession().createCriteria(Squad.class);
    crit.setProjection(Projections.id());
    crit.add(Restrictions.eq("user.id", idUser));
    return (Integer) crit.uniqueResult();
  }
}
