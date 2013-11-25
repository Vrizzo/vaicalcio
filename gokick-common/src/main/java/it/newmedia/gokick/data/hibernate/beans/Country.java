package it.newmedia.gokick.data.hibernate.beans;

import java.util.Comparator;
import java.util.Set;

/**
 *
 * Classe che rappresenta l'oggetto Country che fa riferimento alla tabella COUNTRIES.
 *
 * @hibernate.class
 * table="COUNTRIES"
 * mutable="false"
 * @hibernate.cache
 * usage="read-only"
 */
public class Country extends ABean
{
  public static final Comparator<Country> POSITION_NAME_ORDER =
          new Comparator<Country>()
          {

            public int compare(Country o1, Country o2)
            {
              if (o1.getPosition() < o2.getPosition())
                return -1;
              if (o1.getPosition() > o2.getPosition())
                return 1;
              return o1.getName().compareToIgnoreCase(o2.getName());
            }
          };

  private String name;

  private Integer position;

  private Integer totUsers;

  private Set<User> userList;

  private Set<SportCenter> sportCenterList;

  private Set<Province> provinceList;

  /**
   * @hibernate.id
   * column="ID_COUNTRY"
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
   * column="ID_COUNTRY"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.SportCenter"
   *
   * @return La lista dei centri sportivi per una nazione
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
   * column="ID_COUNTRY"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.Province"
   *
   * @return La lista delle province per una nazione
   */
  public Set<Province> getProvinceList()
  {
    return this.provinceList;
  }

  public void setProvinceList(Set<Province> provinceList)
  {
    this.provinceList = provinceList;
  }

  /**
   * @hibernate.set
   * inverse="false"
   * lazy="true"
   * @hibernate.key
   * column="ID_COUNTRY"
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
