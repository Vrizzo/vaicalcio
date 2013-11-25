package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione delle figurine. Contiene tutti i metodi che lavorano sulla tabella PICTURE_CARD.
 */
public class PictureCardDAO extends AGenericDAO < PictureCard, Integer >
{

  /**
   *
   * @param session
   */
  public PictureCardDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return numero figurine per utente
   * @throws Exception
   */
  public int getCountByIdUser(int idUser) throws Exception
  {
    Criteria criteria = getSession().createCriteria(PictureCard.class);
    criteria.setProjection(Projections.rowCount());
    criteria.add(Restrictions.eq("user.id", idUser));
    return ((Integer)criteria.list().get(0)).intValue();
  }

  /**
   *
   * @param enumPictureCardStatus enum stato d'interesse
   * @return lista figurine per stato
   * @throws Exception
   */
  public List<PictureCard> getAllByStatus(EnumPictureCardStatus enumPictureCardStatus) throws Exception
  {
    Criteria criteria = getSession().createCriteria(PictureCard.class);
    criteria.add(Restrictions.eq("pictureCardStatus", enumPictureCardStatus.getValue()));
    return criteria.list();
  }

  /**
   *
   * @param pictureCardStatus enum stato d'interesse
   * @return lista figurine per valore dell'enum che ne definisce lo stato
   * @throws Exception
   */
  public List<PictureCard> getAllByStatusValue(String pictureCardStatus,int idCountryFilter) throws Exception
  {
    Criteria criteria = getSession().createCriteria(PictureCard.class);
    criteria.add(Restrictions.eq("pictureCardStatus", pictureCardStatus));
    if(idCountryFilter > 0)
    {
      criteria.createCriteria("user").add(Restrictions.eq("country.id", idCountryFilter));
    }
    criteria.addOrder(Order.desc("created"));
    return criteria.list();
  }

  /**
   *
   * @param idUser utente d'interesse
   * @param enumPictureCardStatus enum stato figurina
   * @return figurina utente per stato specificato
   * @throws Exception
   */
  public PictureCard getByStatus(int idUser,EnumPictureCardStatus enumPictureCardStatus) throws Exception
  {
    Criteria criteria = getSession().createCriteria(PictureCard.class);
    criteria.add(Restrictions.eq("user.id", idUser));
    criteria.add(Restrictions.eq("pictureCardStatus", enumPictureCardStatus.getValue()));
    criteria.setMaxResults(1);
    return (PictureCard) criteria.uniqueResult();
  }

  /**
   *
   * @param idPicture  id to exclude
   * @param idUser  owner User
   * @param enumPictureCardStatus  choose from CURRENT PENDING or REFUSED
   * @return (if exist another) picture with status "enumPictureCardStatus" with idUser "idUser" and id different from "idPicture"
   * @throws Exception
   */
  public PictureCard getByStatus(int idPicture,int idUser,EnumPictureCardStatus enumPictureCardStatus) throws Exception
  {
    Criteria criteria = getSession().createCriteria(PictureCard.class);
    criteria.add(Restrictions.eq("user.id", idUser));
    criteria.add(Restrictions.ne("id", idPicture));
    criteria.add(Restrictions.eq("pictureCardStatus", enumPictureCardStatus.getValue()));
    criteria.setMaxResults(1);
    return (PictureCard) criteria.uniqueResult();
  }


  /**
   *
   * @param idUser utente d'interesse
   * @return figurina dell'utente in stato CURRENT
   * @throws Exception
   */
  public PictureCard getCurrent(int idUser) throws Exception
  {
    return getByStatus(idUser, EnumPictureCardStatus.Current);
  }

  /**
   *
   * @param idUser utente d'interesse
   * @param pictureCardStatuses stati d'interesse
   * @return lista figurine per utente e stai specificati, ordinate per creazione desc
   * @throws Exception
   */
  public List<PictureCard> getByPicStatuses(int idUser,EnumPictureCardStatus[] pictureCardStatuses) throws Exception
  {
    Criteria criteria = getSession().createCriteria(PictureCard.class);
    criteria.add(Restrictions.eq("user.id", idUser));
    if (pictureCardStatuses != null && pictureCardStatuses.length > 0)
    {
      String[] pictureCardStatusesValues = new String[pictureCardStatuses.length];
      for (int i = 0; i < pictureCardStatuses.length; i++)
      {
        pictureCardStatusesValues[i] = pictureCardStatuses[i].getValue();
      }
      criteria.add(Restrictions.in("pictureCardStatus", pictureCardStatusesValues));
    }
    criteria.addOrder(Order.desc("created"));
    return criteria.list();
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return ultima figurina inserita dall'utente
   * @throws Exception
   */
  public PictureCard getLastPic(int idUser) throws Exception
  {
    Criteria criteria = getSession().createCriteria(PictureCard.class);
    criteria.add(Restrictions.eq("user.id", idUser));
    criteria.addOrder(Order.desc("created"));
    criteria.setMaxResults(1);
    return (PictureCard) criteria.uniqueResult();
  }

  /**
   *
   * @param fileName nome file figurina d'interesse
   * @return figurina per nome file
   * @throws Exception
   */
  public PictureCard getByFileName(String fileName) throws Exception
  {
    Criteria criteria = getSession().createCriteria(PictureCard.class);
    criteria.add(Restrictions.eq("filename", fileName));
    criteria.setMaxResults(1);
    return (PictureCard) criteria.uniqueResult();
  }

  /**
   *
   * @param idUser utente d'interesse
   * @return lista figurine per l'utente
   * @throws Exception
   */
  public List<PictureCard> getAllByIdUser(int idUser) throws Exception
  {
    String sql = " FROM PictureCard AS p WHERE p.user.id = :idUser";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("idUser", idUser);
    return (List<PictureCard>)query.list();
  }

  /**
   *
   * @param pictureStatuses stato d'interesse figurina
   * @return numero figurine nello stato specificato
   * @throws Exception
   */
  public int getCount(EnumPictureCardStatus[] pictureStatuses) throws Exception
  {
    return getCount(0, pictureStatuses);
  }
  
  public int getCount(int idCountryFilter, EnumPictureCardStatus[] pictureStatuses) throws Exception
  {
    Criteria criteria = getSession().createCriteria(PictureCard.class);
    criteria.setProjection(Projections.rowCount());

    if(idCountryFilter > 0)
    {
      criteria.createCriteria("user").add(Restrictions.eq("country.id", idCountryFilter));
    }
    
    if (pictureStatuses != null && pictureStatuses.length > 0)
    {
      String[] pictureStatusesValues = new String[pictureStatuses.length];
      for (int i = 0; i < pictureStatuses.length; i++)
      {
        pictureStatusesValues[i] = pictureStatuses[i].getValue();
      }
      criteria.add(Restrictions.in("pictureCardStatus", pictureStatusesValues));
    }
    
    return ((Integer) criteria.list().get(0)).intValue();
  }
}
