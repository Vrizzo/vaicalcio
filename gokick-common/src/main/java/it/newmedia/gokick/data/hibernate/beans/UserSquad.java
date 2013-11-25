package it.newmedia.gokick.data.hibernate.beans;

import it.newmedia.gokick.data.enums.EnumUserSquadStatus;

/**
 *
 * Classe che rappresenta l'oggetto UserSquad che fa riferimento alla tabella USERS_SQUADS.
 *
 * @hibernate.class
 * table="USERS_SQUADS"
 */
public class UserSquad extends ABean
{

  private User user;

  private Squad squad;

  private String userSquadStatus;

  private Boolean owner;

  /**
   * @hibernate.id
   * column="ID_USER_SQUAD"
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
   * column="ID_USER"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
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
   * @hibernate.many-to-one
   * column="ID_SQUAD"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.Squad"
   */
  public Squad getSquad()
  {
    return squad;
  }

  public void setSquad(Squad squad)
  {
    this.squad = squad;
  }

  /**
   * @hibernate.property
   * column="USER_SQUAD_STATUS"
   */
  public String getUserSquadStatus()
  {
    return userSquadStatus;
  }

  public void setUserSquadStatus(String userSquadStatus)
  {
    this.userSquadStatus = userSquadStatus;
  }

  public EnumUserSquadStatus getEnumUserSquadStatus()
  {
    if (this.userSquadStatus == null)
      return EnumUserSquadStatus.Undefined;
    return EnumUserSquadStatus.getEnum(this.userSquadStatus);
  }

  public void setEnumUserSquadStatus(EnumUserSquadStatus enumUserSquadStatus)
  {
    this.userSquadStatus = enumUserSquadStatus.getValue();
  }

  /**
   * @hibernate.property
   * column="OWNER"
   */
  public Boolean getOwner()
  {
    return owner;
  }

  public void setOwner(Boolean owner)
  {
    this.owner = owner;
  }

}
