package it.newmedia.gokick.data.hibernate.beans;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 *
 * Classe astratta che definisce le proprietà comuni a tutti i beans.
 */
public abstract class ABean extends Object implements Serializable
{

  protected Integer id;

  public abstract Integer getId();

  public abstract void setId(Integer id);

  public boolean isIdGreatherThanZero()
  {
    return this.id != null && this.id > 0;
  }
  protected static final long serialVersionUID = 00L;

}
