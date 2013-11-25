package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe che rappresenta l'oggetto PitchType che fa riferimento alla tabella PITCH_TYPES.
 *
 * @hibernate.class
 * table="PITCH_TYPES"
 * mutable="false"
 * @hibernate.cache
 * usage="read-only"
 */
public class PitchType extends ABean
{

  private Integer id;
  private String label;
  private String keyName;
  private Integer position; 
  

  /**
   * @hibernate.id
   * column="ID_PITCH_TYPE"
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
   * column="LABEL"
   */
  public String getLabel()
  {
    return label;
  }

  public void setLabel(String label)
  {
    this.label = label;
  }

   /**
   * @hibernate.property
   * column="KEY_NAME"
   */
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

     /**
   * @hibernate.property
   * column="POSITION"
   */
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
