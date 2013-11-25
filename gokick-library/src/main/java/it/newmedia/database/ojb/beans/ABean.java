package it.newmedia.database.ojb.beans;

/* */
import java.io.Serializable;

public abstract class ABean extends Object implements Serializable
{
  public abstract int getUniqueId();
  public abstract void setUniqueId( int id );  
}
