package it.newmedia.gokick.data.hibernate.beans;

import java.util.Date;

/**
 *
 * Classe UserComment che associa un messaggio spedito da un utente ai destinatari
 * Fa riferimento alla tabella USER_COMMENTS
 *
 * @hibernate.class
 * table="USER_COMMENTS"
 */
public class UserComment extends ABean
{
  private int idUser;
  private String textDetail;
  private int tech;
  private int reliability;
  private int fairplay;
  private boolean approved;
  private boolean deleted;
  private Date created;
  private User userAuthor;


  /**
   * @hibernate.id
   * column="ID_USER_COMMENT"
   * generator-class="native"
   * unsaved-value="null"
   */
  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * @hibernate.property
   * column="ID_USER"
   */
  public int getIdUser()
  {
    return idUser;
  }

  public void setIdUser(int idUser)
  {
    this.idUser = idUser;
  }

  /**
   * @hibernate.property
   * column="TEXT_DETAIL"
   */
  public String getTextDetail()
  {
    return textDetail;
  }

  public void setTextDetail(String textDetail)
  {
    this.textDetail = textDetail;
  }

  /**
   * @hibernate.property
   * column="TECH"
   */
  public int getTech()
  {
    return tech;
  }

  public void setTech(int tech)
  {
    this.tech = tech;
  }

  /**
   * @hibernate.property
   * column="RELIABILITY"
   */
  public int getReliability()
  {
    return reliability;
  }

  public void setReliability(int reliability)
  {
    this.reliability = reliability;
  }

  /**
   * @hibernate.property
   * column="FAIRPLAY"
   */
  public int getFairplay()
  {
    return fairplay;
  }

  public void setFairplay(int fairplay)
  {
    this.fairplay = fairplay;
  }
  
  /**
   * @hibernate.property
   * column="APPROVED"
   */
  public boolean isApproved()
  {
    return approved;
  }

  public void setApproved(boolean approved)
  {
    this.approved = approved;
  }

  /**
   * @hibernate.property
   * column="DELETED"
   */
  public boolean isDeleted()
  {
    return deleted;
  }

  public void setDeleted(boolean deleted)
  {
    this.deleted = deleted;
  }

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


  /**
   * @hibernate.many-to-one
   * column="ID_USER_AUTHOR"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * outer-join="true"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   */
   public User getUserAuthor()
  {
    return userAuthor;
  }

  public void setUserAuthor(User userAuthor)
  {
    this.userAuthor = userAuthor;
  }
  
}
