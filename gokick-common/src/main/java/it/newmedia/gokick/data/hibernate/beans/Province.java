package it.newmedia.gokick.data.hibernate.beans;

import java.util.Comparator;
import java.util.Set;

/**
 *
 * Classe che rappresenta l'oggetto Province che fa riferimento alla tabella PROVINCES.
 *
 * @hibernate.class
 * table="PROVINCES"
 * mutable="false"
 * @hibernate.cache
 * usage="read-only"
 */
public class Province extends ABean
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >

  public static final Comparator<Province> POSITION_NAME_ORDER =
          new Comparator<Province>()
          {
            public int compare(Province o1, Province o2)
            {
              if ( o1.getName().compareToIgnoreCase(o2.getName()) < 1)
                return -1;
              if ( o1.getName().compareToIgnoreCase(o2.getName()) > 0)
                return 1;
              return o1.getName().compareToIgnoreCase(o2.getName());
            }
          };

  // </editor-fold>

  private Country country;

  private String name;

  private String abbreviation;

  private Integer totUsers;

  private Set<User> userList;

  private Set<SportCenter> sportCenterList;

  /**
   * @hibernate.id
   * column="ID_PROVINCE"
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
   * column="ID_COUNTRY"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.Country"
   */
  public Country getCountry()
  {
    return country;
  }

  public void setCountry(Country country)
  {
    this.country = country;
  }

  /**
   * @hibernate.property
   * column="NAME"
   */
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @hibernate.property
   * column="ABBREVIATION"
   */
  public String getAbbreviation()
  {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation)
  {
    this.abbreviation = abbreviation;
  }

  /**
   * @hibernate.property
   * column="TOT_USERS"
   */
  public Integer getTotUsers()
  {
    return totUsers;
  }

  public void setTotUsers(Integer totUsers)
  {
    this.totUsers = totUsers;
  }

  /**
   * @hibernate.set
   * inverse="false"
   * lazy="true"
   * @hibernate.key
   * column="ID_PROVINCE"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.SportCenter"
   *
   * @return La lista dei centri sportivi per una provincia
   */
  public Set<SportCenter> getSportCenterList()
  {
    return this.sportCenterList;
  }

  public void setSportCenterList(Set<SportCenter> sportCenterList)
  {
    this.sportCenterList = sportCenterList;
  }

  /**
   * @hibernate.set
   * inverse="false"
   * lazy="true"
   * @hibernate.key
   * column="ID_PROVINCE"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   *
   * @return La lista degli utenti per una nazione
   */
  public Set<User> getUserList()
  {
    return userList;
  }

  public void setUserList(Set<User> userList)
  {
    this.userList = userList;
  }

}
