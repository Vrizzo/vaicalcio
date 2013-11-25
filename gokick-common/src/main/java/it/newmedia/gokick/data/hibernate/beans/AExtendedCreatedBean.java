package it.newmedia.gokick.data.hibernate.beans;

import java.io.Serializable;

/**
 *
 * Classe astratta che definisce le proprietà APPROVED e DELETED estendendo la classe ACreatedBean.
 */
public abstract class AExtendedCreatedBean extends ACreatedBean implements Serializable
{

  protected Boolean approved;
  protected Boolean deleted;


  /**
   * @hibernate.property
   * column="APPROVED"
   */
  public Boolean getApproved()
  {
    return approved;
  }

  public void setApproved(Boolean approved)
  {
    this.approved = approved;
  }

  /**
   * @hibernate.property
   * column="DELETED"
   */
  public Boolean getDeleted()
  {
    return deleted;
  }

  public void setDeleted(Boolean deleted)
  {
    this.deleted = deleted;
  }

}
