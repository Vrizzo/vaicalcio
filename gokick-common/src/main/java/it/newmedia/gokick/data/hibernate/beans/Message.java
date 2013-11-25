package it.newmedia.gokick.data.hibernate.beans;

import java.util.Date;

/**
 *
 * Classe che rappresenta l'oggetto Message che fa riferimento alla tabella MESSAGES.
 *
 * @hibernate.class
 * table="MESSAGES"
 */
public class Message extends AExtendedCreatedBean
{

  private User userOwner;
  private String textDetail;

  public Message()
  {
    this.approved = true;
    this.deleted = false;
    this.created = new Date();
  }



  /**
   * @hibernate.id
   * column="ID_MESSAGE"
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
   * @hibernate.many-to-one
   * column="ID_USER_OWNER"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   */
  public User getUserOwner()
  {
    return userOwner;
  }

  public void setUserOwner(User userOwner)
  {
    this.userOwner = userOwner;
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
  

}
