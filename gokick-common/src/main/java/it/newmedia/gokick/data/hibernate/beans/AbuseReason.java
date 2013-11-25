package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe PhysicalCondition che rappresenta le traduzioni dell'applicazione.
 * Fa riferimento alla tabella ABUSE_REASONS
 *
 * @hibernate.class
 * table="ABUSE_REASONS"
 */
public class AbuseReason extends ALabelKeyBean
{

  /**
   * @hibernate.id
   * column="ID_ABUSE_REASON"
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
