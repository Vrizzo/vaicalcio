package it.newmedia.database.ojb;

import org.apache.log4j.Logger;
//import org.apache.ojb.broker.PBKey;
//import org.apache.ojb.broker.PersistenceBroker;
//import org.apache.ojb.broker.PersistenceBrokerFactory;

public class BrokerConnection
{

//  private static final Logger logger = Logger.getLogger(BrokerConnection.class);
//
//  private String jcdAlias = null;
//  private PersistenceBroker persistenceBroker = null;
//  //private static int connectionNumber = 0;
//
//  public BrokerConnection()
//  {
//  }
//
//  public BrokerConnection(String jcdAlias)
//  {
//    this.jcdAlias = jcdAlias;
//  }
//
//  public PersistenceBroker getBroker()
//  {
//    if (persistenceBroker == null)
//    {
//      try
//      {
//        if (jcdAlias == null)
//        {
//          persistenceBroker = PersistenceBrokerFactory.defaultPersistenceBroker();
//        } else
//        {
//          PBKey pbKey = new PBKey(jcdAlias);
//          persistenceBroker = PersistenceBrokerFactory.createPersistenceBroker(pbKey);
//        }
//        //connectionNumber++;
//        //logger.debug("connectionNumber[" + connectionNumber + " ]  ---->" + persistenceBroker.hashCode());
//      } catch (Throwable t)
//      {
//        logger.error("Error getting broker", t);
//      }
//    }
//    return persistenceBroker;
//  }
//
//  public void closeBroker()
//  {
//    if (persistenceBroker != null && !persistenceBroker.isClosed())
//    {
//      //int hashCode = persistenceBroker.hashCode();
//      persistenceBroker.close();
//      persistenceBroker = null;
//      //connectionNumber--;
//      //logger.debug("connectionNumber[" + connectionNumber + "]  ---->" + hashCode);
//    }
//  }
}
