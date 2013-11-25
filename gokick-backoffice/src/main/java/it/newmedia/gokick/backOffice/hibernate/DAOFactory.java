package it.newmedia.gokick.backOffice.hibernate;


import it.newmedia.gokick.data.hibernate.AHibernateDAOFactory;
import org.hibernate.Session;

/**
 *
 * Classe che estende AHibernateDAOFactory.
 */
public class DAOFactory extends AHibernateDAOFactory
{

  private static DAOFactory instance;

  private DAOFactory()
  {
  }

  public static DAOFactory getInstance()
  {
    if(instance == null)
      instance = new DAOFactory();

    return instance;
  }

  @Override
  protected Session getCurrentSession()
  {
    return HibernateSessionHolder.getSessionPerRequestSession();
  }

}
