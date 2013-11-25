package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe che rappresenta l'oggetto PitchGround che fa riferimento alla tabella PITCH_GROUNDS.
 *
 * @hibernate.class
 * table="PITCH_GROUNDS"
 * mutable="false"
 * @hibernate.cache
 * usage="read-only"
 */
public class PitchGround extends ALabelKeyBean
{
  /**
   * @hibernate.id
   * column="ID_PITCH_GROUND"
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
