package it.newmedia.gokick.backOffice.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * Classe per la gestione della Sessione di Hibernate.
 */
public class HibernateSessionHolder
{
  private static SessionFactory sessionFactory;

  public static void sessionPerRequestBegin()
  {
    if (sessionFactory == null)
    {
      sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    Session session = getSessionPerRequestSession();
    if (session == null)
    {
      throw new IllegalStateException("Hibernate Session is NULL");
    }
    session.beginTransaction();
  }

  public static void sessionPerRequestCommit()
  {
    Session session = getSessionPerRequestSession();

    if (session == null)
    {
      throw new IllegalStateException("Hibernate Session is NULL");
    }

    if (session.getTransaction() == null)
    {
      throw new IllegalStateException("Hibernate Transaction is NULL");
    }

    if (session.getTransaction().isActive() == false)
    {
      throw new IllegalStateException("NO ACTIVE Transaction");
    }
    session.getTransaction().commit();
  }

  public static void sessionPerRequestRollback()
  {
    Session session = getSessionPerRequestSession();

    if (session == null)
    {
      throw new IllegalStateException("Hibernate Session is NULL");
    }

    if (session.getTransaction() == null)
    {
      throw new IllegalStateException("Hibernate Transaction is NULL");
    }

    if (session.getTransaction().isActive() == false)
    {
      throw new IllegalStateException("NO ACTIVE Transaction");
    }
    session.getTransaction().rollback();
  }

  public static Session getSessionPerRequestSession()
  {
    return sessionFactory.getCurrentSession();
  }

  public static Session beginTransaction()
  {
    sessionPerRequestCommit();
    sessionPerRequestBegin();
    return null;
  }

  public static void rollbackTransaction()
  {
    sessionPerRequestRollback();
    sessionPerRequestBegin();
  }

  public static void commitTransaction()
  {
    sessionPerRequestCommit();
    sessionPerRequestBegin();
  }

}
