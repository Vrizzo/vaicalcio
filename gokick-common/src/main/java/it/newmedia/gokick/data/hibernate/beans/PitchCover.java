package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe che rappresenta l'oggetto PitchCover che fa riferimento alla tabella PITCH_COVERS.
 *
 * @hibernate.class
 * table="PITCH_COVERS"
 * mutable="false"
 * @hibernate.cache
 * usage="read-only"
 */
public class PitchCover extends ALabelKeyBean
{
  /**
   * @hibernate.id
   * column="ID_PITCH_COVER"
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
