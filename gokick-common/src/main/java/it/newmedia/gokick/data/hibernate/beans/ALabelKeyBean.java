package it.newmedia.gokick.data.hibernate.beans;

import java.io.Serializable;

/**
 *
 * Classe astratta che definisce le proprietà LABEL, KEY_NAME e POSITION per i beans.
 */
public abstract class ALabelKeyBean extends ABean implements Serializable
{

  private String label;
  private String keyName;
  private Integer position;


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
  public String getKeyName()
  {
    return keyName;
  }

  public void setKeyName(String keyName)
  {
    this.keyName = keyName;
  }

  /**
   * @hibernate.property
   * column="POSITION"
   */
  public Integer getPosition()
  {
    return position;
  }

  public void setPosition(Integer position)
  {
    this.position = position;
  }

}
