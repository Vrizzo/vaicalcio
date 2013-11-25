package it.newmedia.gokick.data.hibernate.beans;

import java.util.Date;

/**
 *
 * @hibernate.class
 * table="SPECIAL_INVITATIONS"
 */
public class SpecialInvitation extends ACreatedBean
{
  private String code;
  private String description;
  private boolean enable;
 
  /**
   * @hibernate.id
   * column="ID_SPECIAL_INVITATION"
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
   * column="CODE"
   */
  public String getCode()
  {
    return code;
  }
  public void setCode(String code)
  {
    this.code = code;
  }

  /**
   * @hibernate.property
   * column="DESCRIPTION"
   */
  public String getDescription()
  {
    return description;
  }
  public void setDescription(String description)
  {
    this.description = description;
  }

  /**
  * @hibernate.property
  * column="ENABLE"
  */
  public boolean isEnable()
  {
    return enable;
  }
  public void setEnable(boolean enable)
  {
    this.enable = enable;
  }

}