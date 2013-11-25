package it.newmedia.gokick.data.hibernate.beans;

import java.util.Comparator;
import java.util.Set;

/**
 *
 * Classe che rappresenta l'oggetto CITY che fa riferimento alla tabella CITIES.
 *
 * @hibernate.class
 * table="CITIES"
 * mutable="false"
 * @hibernate.cache
 * usage="read-only"
 */
public class City extends ABean
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >

  public static final Comparator<City> POSITION_NAME_ORDER =
          new Comparator<City>()
          {

            public int compare(City o1, City o2)
            {
              if (o1.getName().compareToIgnoreCase(o2.getName()) < 1)
              {
                return -1;
              }
              if (o1.getName().compareToIgnoreCase(o2.getName()) > 0)
              {
                return 1;
              }
              return o1.getName().compareToIgnoreCase(o2.getName());
            }
          };
  // </editor-fold>
  private Province province;
  private String name;
  private Integer totUsers;
  private Set<User> userList;
  private Set<SportCenter> sportCenterList;

  /**
   * @hibernate.id
   * column="ID_CITY"
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
   * column="ID_PROVINCE"
   * cascade="none"
   * not-null="true"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.Province"
   */
  public Province getProvince()
  {
    return province;
  }

  public void setProvince(Province province)
  {
    this.province = province;
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
   * column="ID_CITY"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.SportCenter"
   *
   * @return La lista dei centri sportivi per una città
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
   * column="ID_CITY"
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
