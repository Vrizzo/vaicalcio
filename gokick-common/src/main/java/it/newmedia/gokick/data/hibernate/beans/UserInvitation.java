package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe UserInvitation che rappresenta il singolo invito effettuato da un utente.
 * Fa riferimento alla tabella USER_INVITATIONS.
 *
 * @hibernate.class
 * table="USER_INVITATIONS"
 */
public class UserInvitation extends ACreatedBean
{
  private User userOwner;
  private User user;
  private String code;
  
  
  /**
   * @hibernate.id
   * column="ID_USER_INVITATION"
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
   * not-null="true"
   * lazy="no-proxy"
   * fetch="join"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   *
   * E' l'utente che ha spedito l'invito
   * @return L'utente che è proprietario dell'invito
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
   * @hibernate.many-to-one
   * column="ID_USER"
   * cascade="none"
   * not-null="false"
   * lazy="no-proxy"
   * fetch="join"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   *
   * E' l'utente che ha accettato l'invito
   * @return Ritorna l'utente che ha accettato l'invito
   */
  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  /**
   * @hibernate.property
   * column="CODE"
   *
   * Codice univoco per identificare un invito
   * @return Ritorna il codice univoco per identificare un invito
   */
  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

}