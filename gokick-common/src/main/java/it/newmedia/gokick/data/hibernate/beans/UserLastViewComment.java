package it.newmedia.gokick.data.hibernate.beans;

import java.util.Date;

/**
 *
 * Classe che rappresenta l'oggetto UserLastViewComment che fa riferimento alla tabella USER_LASTVIEW_COMMENT.
 *
 * @hibernate.class
 * table="USER_LASTVIEW_COMMENT"
 */
public class UserLastViewComment extends ABean
{
  private int idUser;
  private int idMatch;
  private int idChallenge;
  private Date lastView;

  /**
   * @hibernate.id
   * column="ID_USER_LASTVIEW_COMMENT"
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
   * column="ID_MATCH"
   */
  public int getIdMatch()
  {
    return idMatch;
  }

  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  /**
   * @hibernate.property
   * column="ID_CHALLENGE"
   */
  public int getIdChallenge()
  {
    return idChallenge;
  }

  public void setIdChallenge(int idChallenge)
  {
    this.idChallenge = idChallenge;
  }

  /**
   * @hibernate.property
   * column="LAST_VIEW"
   */
  public Date getLastView()
  {
    return lastView;
  }

  public void setLastView(Date lastView)
  {
    this.lastView = lastView;
  }

}
