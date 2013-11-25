package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe PhysicalCondition che rappresenta le traduzioni dell'applicazione.
 * Fa riferimento alla tabella PHYSICAL_CONDITIONS
 *
 * @hibernate.class
 * table="PHYSICAL_CONDITIONS"
 * mutable="false"
 * @hibernate.cache
 * usage="read-only"
 */
public class PhysicalCondition extends ALabelKeyBean
{

  /**
   * @hibernate.id
   * column="ID_PHYSICAL_CONDITION"
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
