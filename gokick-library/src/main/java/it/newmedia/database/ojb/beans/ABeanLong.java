package it.newmedia.database.ojb.beans;

/* */
import java.io.Serializable;

public abstract class ABeanLong extends Object implements Serializable
{
  public abstract long getUniqueId();
  public abstract void setUniqueId( long id );  
}
