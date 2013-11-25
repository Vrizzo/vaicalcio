package it.newmedia.database.ojb.managers;

// <editor-fold defaultstate="collapsed" desc="-- Imports --">

import it.newmedia.database.ojb.BrokerConnection;
import it.newmedia.database.ojb.BrokerConnectionManager;
import it.newmedia.database.ojb.beans.ABeanLong;
import it.newmedia.results.Result;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryByIdentity;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.util.ObjectModification;

// </editor-fold>

public class BeanOjbManagerLong<T extends ABeanLong>
{
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  
  protected final Logger logger = Logger.getLogger( BeanOjbManagerLong.class  );
  protected BrokerConnection brokerConnection = null;
  protected boolean isBrockerSupplied = false;
  protected Class realClass = null;
  
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  
  public BeanOjbManagerLong( Class c, BrokerConnection bc )
  {
    this( c );
    
    brokerConnection = bc;
    isBrockerSupplied = true;
  }
  
  public BeanOjbManagerLong( Class c )
  {
    realClass = c;
  }
  
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  
  public  Result<T> get( Long id )
  {
    return get( id.longValue(), false );
  }
  
  public  Result<T> get( Long id, boolean retrieveAllReferences )
  {
    return get( id.intValue(), retrieveAllReferences );
  }
  
  public  Result<T> get( long id )
  {
    return get( id, false );
  }
  
  public Result<T> get(long id, boolean retrieveAllReferences )
  {
    try
    {
      T o = (T) realClass.newInstance();
      o.setUniqueId(id );
      Query query  = new QueryByIdentity(o );
      
      getBrokerConnectionIfNotSupplied(false);
      T result = (T) brokerConnection.getBroker().getObjectByQuery(query );
      if( result!= null && retrieveAllReferences )
        brokerConnection.getBroker().retrieveAllReferences(result);
      return new Result<T>(result, true );
    }
    catch(Exception ex)
    {
      logger.error("Error getting object: " + ex.getMessage(), ex );
      return new Result<T>(ex );
    }
    finally
    {
      closeBrokerConnectionIfNotSupplied(false);
    }
  }
  
  public Result<T> getByQuery( Query query )
  {
    return this.getByQuery(query, false);
  }
  
  public Result<T> getByQuery( Query query, boolean retrieveAllReferences )
  {
    try
    {
      getBrokerConnectionIfNotSupplied(false);
      T result = (T) brokerConnection.getBroker().getObjectByQuery(query );
      if( result!= null && retrieveAllReferences )
        brokerConnection.getBroker().retrieveAllReferences(result);
      return new Result<T>(result, true );
    }
    catch(Exception ex)
    {
      logger.error("Error getting object: " + ex.getMessage(), ex );
      return new Result<T>(ex );
    }
    finally
    {
      closeBrokerConnectionIfNotSupplied(false);
    }
  }
  
  public Result<List<T>> getAll( )
  {
    try
    {
      Criteria criteria = new Criteria();
      QueryByCriteria queryByCriteria = QueryFactory.newQuery( realClass, criteria, true );
      
      getBrokerConnectionIfNotSupplied(false);
      
      return new Result<List<T>>( (List<T>) brokerConnection.getBroker().getCollectionByQuery( queryByCriteria ), true );
    }
    catch( Exception ex)
    {
      logger.error("Error getting list: " + ex.getMessage(), ex );
      return new Result<List<T>>( ex );
    }
    finally
    {
      closeBrokerConnectionIfNotSupplied(false);
    }
  }
  
  /**
   * Return a collection of specific object
   * according to query passed
   * ATTENTION!!! This method return always a valid list!!
   * (May be null only on exception)
   * @param query A valid OJB query
   * @return A List<> of specific object request.
   * ATTENTION!!! This method return always a valid list!!
   * (May be null only on exception)
   */
  public Result<List<T>> getAllByQuery( Query query )
  {
    try
    {
      getBrokerConnectionIfNotSupplied(false);
      
      List<T> list = (List<T>) brokerConnection.getBroker().getCollectionByQuery(query );
      if( list == null )
        list = new ArrayList<T>();
      return new Result<List<T>>( list, true );
    }
    catch(Exception ex)
    {
      logger.error("Error getting list: " + ex.getMessage(), ex );
      return new Result<List<T>>(ex );
    }
    finally
    {
      closeBrokerConnectionIfNotSupplied(false);
    }
  }
  
  public Result<T> save(T object)
  {
    Result<T> result = null;
    
    try
    {
      getBrokerConnectionIfNotSupplied(true);
      brokerConnection.getBroker().store(object);
      result = new Result<T>(object, true );
    }
    catch(Exception ex)
    {
      logger.error("Error saving object: " + ex.getMessage(), ex );
      result = new Result<T>(ex );
    }
    finally
    {
      closeBrokerConnectionIfNotSupplied(true, result.isSuccess());
    }
    
    return result;
  }
  
  public Result<T> update(T object)
  {
    Result<T> result = null;
    
    try
    {
      getBrokerConnectionIfNotSupplied(true);
      brokerConnection.getBroker().store(object, ObjectModification.UPDATE);
      result = new Result<T>(object, true );
    }
    catch(Exception ex)
    {
      logger.error("Error updating object: " + ex.getMessage(), ex );
      result = new Result<T>(ex );
    }
    finally
    {
      closeBrokerConnectionIfNotSupplied(true, result.isSuccess());
    }
    return result;
  }
  
  public Result<T> insert(T object)
  {
    Result<T> result = null;
    try
    {
      getBrokerConnectionIfNotSupplied(true);
      brokerConnection.getBroker().store(object, ObjectModification.INSERT);
      result = new Result<T>(object, true );
    }
    catch(Exception ex)
    {
      logger.error("Error inserting object: " + ex.getMessage(), ex );
      result = new Result<T>(ex );
    }
     catch(Throwable ex)
    {
      logger.error("Error Throwable inserting object: " + ex.getMessage(), ex );
      result = new Result<T>(new Exception(ex.getMessage()) );
    }
    finally
    {
      closeBrokerConnectionIfNotSupplied(true, result.isSuccess());
    }
    
    return result;
  }
  
  public Result<Boolean> delete(long id)
  {
    try
    {
      T o = (T) realClass.newInstance();
      o.setUniqueId(id );
      return this.delete(o);
    }
    catch( Exception ex)
    {
      logger.error("Error deleting object: " + ex.getMessage(), ex );
      return new Result<Boolean>(ex);
    }
  }
  
  public Result<Boolean> delete(T object)
  {
    Result<Boolean> result = null;
    
    try
    {
      getBrokerConnectionIfNotSupplied(true);
      brokerConnection.getBroker().delete(object);
      result = new Result<Boolean>( true, true );
    }
    catch( Exception ex)
    {
      logger.error("Error deleting object: " + ex.getMessage(), ex );
      result = new Result<Boolean>(ex);
    }
    finally
    {
      closeBrokerConnectionIfNotSupplied(true, result.isSuccess());
    }
    
    return result;
  }
  
  public boolean exist(long id)
  {
    Result<T> res = get(id);
    return res.isSuccessNotNull() != null;
  }
  
  public void clearOjbCache()
  {
    try
    {
      getBrokerConnectionIfNotSupplied(false);
      
      brokerConnection.getBroker().clearCache();
    }
    catch( Exception ex)
    {
      logger.error( "Error clearing the OJB Cache", ex );
    }
    finally
    {
      closeBrokerConnectionIfNotSupplied(false);
    }
  }
  
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Protected Methods --">
  
  protected void getBrokerConnectionIfNotSupplied(boolean openTransaction)
  {
    if(isBrockerSupplied)
    {
      return;
    }
    
    brokerConnection = BrokerConnectionManager.getBrokerConnection();
    if(openTransaction)
    {
      brokerConnection.getBroker().beginTransaction();
    }
  }
  
  protected void closeBrokerConnectionIfNotSupplied(boolean inTransaction, boolean commit)
  {
    if (!isBrockerSupplied && brokerConnection != null )
    {
      if(inTransaction)
      {
        if (commit)
        {
          brokerConnection.getBroker().commitTransaction();
        }
        else
        {
          brokerConnection.getBroker().abortTransaction();
        }
      }
      brokerConnection.closeBroker();
    }
  }
  
  protected void closeBrokerConnectionIfNotSupplied(boolean inTransaction)
  {
    closeBrokerConnectionIfNotSupplied(inTransaction, true );
  }
  
  // </editor-fold>
}