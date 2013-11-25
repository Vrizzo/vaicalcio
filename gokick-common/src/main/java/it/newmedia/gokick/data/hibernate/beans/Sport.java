package it.newmedia.gokick.data.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Classe che rappresenta l'oggetto Sport che fa riferimento alla tabella SPORTS.
 *
 * @hibernate.class
 * table="SPORTS"
 */
public class Sport extends ALabelKeyBean
{

  private Set<Pitch> pitchList = new HashSet<Pitch>();

  /**
   * @hibernate.id
   * column="ID_SPORT"
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
   *
   * @return
   * @hibernate.set
   * table="PITCHES_TO_SPORTS"
   * cascade="none"
   * inverse="true"
   * lazy="true"
   *
   * @hibernate.key
   * column="ID_SPORT"
   *
   * @hibernate.many-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.Pitch"
   * column="ID_PITCH"
   */
  public Set<Pitch> getPitchList()
  {
    return pitchList;
  }

  public void setPitchList(Set<Pitch> pitchList)
  {
    this.pitchList = pitchList;
  }
}
