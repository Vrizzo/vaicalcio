package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe che rappresenta l'oggetto PlayerFoot che fa riferimento alla tabella PLAYER_FEET.
 *
 * @hibernate.class
 * table="PLAYER_FEET"
 * mutable="false"
 * @hibernate.cache
 * usage="read-only"
 */
public class PlayerFoot extends ALabelKeyBean
{
  /**
   * @hibernate.id
   * column="ID_PLAYER_FOOT"
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
