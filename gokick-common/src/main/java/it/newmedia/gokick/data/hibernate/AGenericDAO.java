package it.newmedia.gokick.data.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

/**
 *
 * Classe astratta dell'interfaccia generic DAO.
 * @param <T> tipo dell'oggetto che verrà gestito.
 * @param <ID> tipo della chiave primaria dell'oggetto.
 */
public abstract class AGenericDAO<T, ID extends Serializable> implements IGenericDAO<T, ID>
{

  // <editor-fold defaultstate="collapsed" desc="--- Members ---">
  public static final Logger logger = Logger.getLogger(AGenericDAO.class);
  private Class<T> persistentClass;
  private Session session;
  // </editor-fold>

  @SuppressWarnings("unchecked")
  public AGenericDAO(Session session)
  {
    this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    this.session = session;
  }

  public Class<T> getPersistentClass()
  {
    return persistentClass;
  }

  protected Session getSession()
  {
    //System.out.println("Transazione: " + (session.getTransaction()!=null) + " isActive: " + (session.getTransaction().isActive()));
    return session;
  }

  public void flush() throws Exception
  {
    try
    {
      session.flush();
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      throw exception;
    }
  }

  public void clear() throws Exception
  {
    try
    {
      session.clear();

    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      throw exception;
    }
  }

  // <editor-fold defaultstate="collapsed" desc="-- Insert or Save ---">
  @SuppressWarnings("unchecked")
  public T makePersistent(T entity) throws Exception
  {
    return makePersistent(entity, false);
  }

  @SuppressWarnings("unchecked")
  private T makePersistent(T entity, boolean transactional) throws Exception
  {
    try
    {
      if (transactional)
      {
        session.beginTransaction();
      }

      session.saveOrUpdate(entity);

      if (transactional)
      {
        session.getTransaction().commit();
      }

      return entity;
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      if (transactional)
      {
        session.getTransaction().rollback();
      }
      throw exception;
    }
  }

  @SuppressWarnings("unchecked")
  public List<T> makePersistent(List<T> entityList) throws Exception
  {
    return makePersistent(entityList, false);
  }

  @SuppressWarnings("unchecked")
  private List<T> makePersistent(List<T> entityList, boolean transactional) throws Exception
  {
    if (CollectionUtils.isEmpty(entityList))
    {
      return entityList;
    }
    try
    {
      if (transactional)
      {
        session.beginTransaction();
      }
      for (Object object : entityList)
      {
        session.saveOrUpdate(object);
      }
      if (transactional)
      {
        session.getTransaction().commit();
      }
      return entityList;
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      if (transactional)
      {
        session.getTransaction().rollback();
      }
      throw exception;
    }
  }

  @SuppressWarnings("unchecked")
  public void makeTransientByQuery(String queryHql) throws Exception
  {
    makeTransientByQuery(queryHql, false);
  }

  @SuppressWarnings("unchecked")
  public void makeTransientByQuery(String queryHql, boolean transactional) throws Exception
  {

    try
    {
      if (transactional)
      {
        session.beginTransaction();
      }
      Query query = session.createQuery(queryHql);
      query.executeUpdate();
      if (transactional)
      {
        session.getTransaction().commit();
      }
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      if (transactional)
      {
        session.getTransaction().rollback();
      }
      throw exception;
    }

  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Delete ---">
  @SuppressWarnings("unchecked")
  public void makeTransient(T entity) throws Exception
  {
    makeTransient(entity, false);
  }

  @SuppressWarnings("unchecked")
  public void makeTransient(T entity, boolean transactional) throws Exception
  {
    try
    {
      if (transactional)
      {
        session.beginTransaction();
      }
      session.delete(entity);
      if (transactional)
      {
        session.getTransaction().commit();
      }
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      if (transactional)
      {
        session.getTransaction().rollback();
      }
      throw exception;
    }

  }

  @SuppressWarnings("unchecked")
  public List<T> makeTransient(List<T> entityList) throws Exception
  {
    return makeTransient(entityList, false);
  }

  @SuppressWarnings("unchecked")
  private List<T> makeTransient(List<T> entityList, boolean transactional) throws Exception
  {
    if (CollectionUtils.isEmpty(entityList))
    {
      return entityList;
    }
    try
    {
      if (transactional)
      {
        session.beginTransaction();
      }
      for (Object object : entityList)
      {
        session.delete(object);
      }
      if (transactional)
      {
        session.getTransaction().commit();
      }
      return entityList;
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      if (transactional)
      {
        session.getTransaction().rollback();
      }
      throw exception;
    }
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Retrieve Single Entity ---">
  @SuppressWarnings("unchecked")
  public T findById(ID id, boolean lock) throws Exception
  {
    try
    {
      T entity;
      if (lock)
      {
        entity = (T) session.load(getPersistentClass(), id, LockMode.UPGRADE);
      }
      else
      {
        entity = (T) session.load(getPersistentClass(), id);
      }
      return entity;
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      throw exception;
    }
  }

  @SuppressWarnings("unchecked")
  public T get(ID id) throws Exception
  {
    try
    {
      T entity;

      entity = (T) session.get(getPersistentClass(), id);

      return entity;
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      throw exception;
    }
  }

  @SuppressWarnings("unchecked")
  public T load(ID id) throws Exception
  {
    try
    {
      T entity;

      entity = (T) session.load(getPersistentClass(), id);

      return entity;
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      throw exception;
    }
  }

  /**
   * Use this inside subclasses as a convenience method.
   */
  @SuppressWarnings("unchecked")
  protected T findEntityByCriteria(Criterion... criterion) throws Exception
  {
    try
    {
      Criteria crit = session.createCriteria(getPersistentClass());
      for (Criterion c : criterion)
      {
        crit.add(c);
      }
      crit.setMaxResults(1);
      return (T) crit.uniqueResult();
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      throw exception;
    }
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="--- Retrieve List of Entities ---">
  @SuppressWarnings("unchecked")
  public List<T> findAll() throws Exception
  {
    return findByCriteria();
  }

  /**
   * Use this inside subclasses as a convenience method.
   */
  @SuppressWarnings("unchecked")
  protected List<T> findByCriteria(Criterion... criterion) throws Exception
  {
    try
    {
      Criteria crit = session.createCriteria(getPersistentClass());
      for (Criterion c : criterion)
      {
        crit.add(c);
      }
      return crit.list();
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      throw exception;
    }
  }

  /**
   * Use this inside subclasses as a convenience method.
   */
  @SuppressWarnings("unchecked")
  protected List<T> findByCriteria(Order[] orderBy, Criterion... criterion) throws Exception
  {
    try
    {
      Criteria crit = session.createCriteria(getPersistentClass());
      for (Criterion c : criterion)
      {
        crit.add(c);
      }
      if (orderBy != null && orderBy.length > 0)
      {
        for (Order order : orderBy)
        {
          crit.addOrder(order);
        }
      }
      return crit.list();
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      throw exception;
    }
  }

  @SuppressWarnings("unchecked")
  protected List<T> findOrderBy(Order... orderBy) throws Exception
  {
    try
    {
      Criteria crit = session.createCriteria(getPersistentClass());
      for (Order o : orderBy)
      {
        crit.addOrder(o);
      }
      
      return crit.list();
    }
    catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
      throw exception;
    }
  }
  // </editor-fold>

}
