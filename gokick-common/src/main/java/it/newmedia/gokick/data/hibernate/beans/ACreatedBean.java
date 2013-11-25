package it.newmedia.gokick.data.hibernate.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Classe astratta che definisce la proprietà CREATED per i beans.
 */
public abstract class ACreatedBean extends ABean implements Serializable
{

  protected Date created;


  /**
   * @hibernate.property
   * column="CREATED"
   */
  public Date getCreated()
  {
    return created;
  }

  public void setCreated(Date created)
  {
    this.created = created;
  }

}
