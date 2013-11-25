package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.VPlayerMatchChallangeArchived;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione della vista. Contiene tutti i metodi che lavorano sulla tabella V_PLAYER_MATCH_CHALLANGE_ARCHIVED.
 */
public class VPlayerMatchChallangeArchivedDAO extends AGenericDAO<VPlayerMatchChallangeArchived, Integer>
{

  public VPlayerMatchChallangeArchivedDAO(Session session)
  {
    super(session);
  }

  /**
   * Return una lista di oggetti in base ai parametri di ricerca
   * @param idUser id dell'utente di cui avere i dati
   * @param startDate data di inizio del periodo interessato
   * @param endDate data di fine del periodo interessato
   * @return una lista di oggetti in base ai parametri di ricerca
   */
  public List<VPlayerMatchChallangeArchived> getAllByParams(int idUser, Date startDate, Date endDate) throws Exception
  {
    Criteria criteria = getSession().createCriteria(VPlayerMatchChallangeArchived.class);
    criteria.add(Restrictions.between("gameStart", startDate, endDate));
    criteria.add(Restrictions.eq("idUser", idUser));
    return criteria.list();
  }
}
