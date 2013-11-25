package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe che rappresenta l'oggetto UsersMessage che fa riferimento alla tabella USERS_MESSAGES.
 *
 * @hibernate.class
 * table="USERS_MESSAGES"
 */
public class UsersMessage extends ABean
{
  private int idUser;
  private int idMessage;
  private Boolean seen;

  public UsersMessage()
  {
   
  }

  /**
   * @hibernate.id
   * column="ID_USER_MESSAGE"
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
   * column="ID_MESSAGE"
   */
  public int getIdMessage()
  {
    return idMessage;
  }
  
  public void setIdMessage(int idMessage)
  {
    this.idMessage = idMessage;
  }

  /**
   * @hibernate.property
   * column="SEEN"
   */
  public Boolean getSeen()
  {
    return seen;
  }
  
  public void setSeen(Boolean seen)
  {
    this.seen = seen;
  }


}
