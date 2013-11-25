package it.newmedia.gokick.data.hibernate.beans;

import java.util.Date;

/**
 *
 * Classe che rappresenta l'oggetto UsersPermission che fa riferimento alla tabella USER_PERMISSIONS.
 *
 * @hibernate.class
 * table="USERS_PERMISSIONS"
 */
public class UsersPermission extends ABean
{
  private User user;
  private String permission;
  private Integer idCountry;
  private Integer idProvince;
  private Integer idCity;
  private Date fromDate;

  /**
   * @hibernate.id
   * column="ID_USER_PERMISSION"
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
   * @hibernate.many-to-one
   * column="ID_USER"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   */
  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  /**
   * @hibernate.property
   * column="PERMISSION"
   */
  public String getPermission()
  {
    return permission;
  }

  public void setPermission(String permission)
  {
    this.permission = permission;
  }

  /**
   * @hibernate.property
   * column="ID_COUNTRY"
   */
  public Integer getIdCountry()
  {
    return idCountry;
  }

  public void setIdCountry(Integer idCountry)
  {
    this.idCountry = idCountry;
  }

  /**
   * @hibernate.property
   * column="ID_PROVINCE"
   */
  public Integer getIdProvince()
  {
    return idProvince;
  }

  public void setIdProvince(Integer idProvince)
  {
    this.idProvince = idProvince;
  }

  /**
   * @hibernate.property
   * column="ID_CITY"
   */
  public Integer getIdCity()
  {
    return idCity;
  }

  public void setIdCity(Integer idCity)
  {
    this.idCity = idCity;
  }

  /**
   * @hibernate.property
   * column="FROM_DATE"
   */
  public Date getFromDate()
  {
    return fromDate;
  }

  public void setFromDate(Date fromDate)
  {
    this.fromDate = fromDate;
  }

  

}
