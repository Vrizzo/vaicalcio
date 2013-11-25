package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe che rappresenta l'oggetto PlayerRole che fa riferimento alla tabella PLAYER_ROLES.
 *
 * @hibernate.class
 * table="PLAYER_ROLES"
 */
public class PlayerRole extends ALabelKeyBean
{
  /**
   * @hibernate.id
   * column="ID_PLAYER_ROLE"
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

}
