package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Statistic;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 *
 * DAO per la gestione delle statistiche. Contiene tutti i metodi che lavorano sulla tabella STATISTICS.
 */
public class StatisticDAO extends AGenericDAO<Statistic, Integer>
{

  /**
   *
   * @param session
   */
  public StatisticDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param idUser utente d'interesse
   * @param startDate inizio periodo ricerca
   * @param endDate fine periodo ricerca
   * @return lista di statistiche per i criteri filtrati
   * @throws Exception
   */
  public List getByIdAndPeriod(int idUser, Date startDate, Date endDate) throws Exception
  {
    Criteria criteria  = getSession().createCriteria(Statistic.class);
    criteria.add(Restrictions.eq("user.id", idUser));
    if (startDate != null)
    {
      criteria.add(Restrictions.ge("refDate", startDate));
    }
    if (endDate != null)
    {
      criteria.add(Restrictions.le("refDate", endDate));
    }
    return criteria.list();
  }

  /**
   *
   * @param idUser utente d'interesse
   * @param startDate inizio periodo ricerca
   * @param endDate fine periodo ricerca
   * @return lista di statistiche per i criteri filtrati, con i soli dati utili nel backoffice
   * @throws Exception
   */
  public List getByIdAndPeriodBackOff(int idUser, Date startDate, Date endDate) throws Exception
  {
    Criteria criteria  = getSession().createCriteria(Statistic.class);
    criteria.add(Restrictions.eq("user.id", idUser));
    if (startDate != null)
      criteria.add(Restrictions.ge("refDate", startDate));
    if (endDate != null)
      criteria.add(Restrictions.le("refDate", endDate));
    
    criteria.setProjection(Projections.projectionList().add(Projections.property("id"), "id")
                                                       .add(Projections.property("user.id"), "idUser")
                                                       .add(Projections.property("allMissing"), "allMissing")
                                                       .add(Projections.property("matchTot"), "matchTot")
                                                       .add(Projections.property("matchWin"), "matchWin")
                                                       .add(Projections.property("matchDraw"), "matchDraw")
                                                       .add(Projections.property("matchLose"), "matchLose")
                                                       .add(Projections.property("challangeTot"), "challangeTot")
                                                       .add(Projections.property("challangeWin"), "challangeWin")
                                                       .add(Projections.property("challangeDraw"), "challangeDraw")
                                                       .add(Projections.property("challangeLose"), "challangeLose")
                                                       .add(Projections.property("matchOwner"), "matchOwner")
                                                       .add(Projections.property("challangeOwner"), "challangeOwner"));
    criteria.setResultTransformer(new AliasToBeanResultTransformer(Statistic.class));
    return criteria.list();
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return lista statistiche per utente
   * @throws Exception
   */
  public List getByUser(int idUser) throws Exception
  {
    Criteria criteria  = getSession().createCriteria(Statistic.class);
    criteria.add(Restrictions.eq("user.id", idUser));
    return criteria.list();
  }

  /**
    *
    * @param idUser
    * @return Array[0]=match giocati e Array[1]=sfide giocate
    * @throws Exception
    */
   public List getPlayed(int idUser) throws Exception
  {
    Criteria crit  = getSession().createCriteria(Statistic.class);
    ProjectionList projectList = Projections.projectionList();
    projectList.add(Projections.sum("matchTot"));
    projectList.add(Projections.sum("challangeTot"));
    crit.add(Restrictions.eq("user.id", idUser));
    crit.setProjection( projectList );
    return crit.list();
  }


}
