package it.newmedia.database.ojb;

public class BrokerConnectionManager
{
  
  private BrokerConnectionManager()
  { }
  
  public static BrokerConnection getBrokerConnection()
  {
    return new BrokerConnection();
  }

  public static BrokerConnection getBrokerConnection(String jcdAlias)
  {
    return new BrokerConnection( jcdAlias );
  }
  
}
