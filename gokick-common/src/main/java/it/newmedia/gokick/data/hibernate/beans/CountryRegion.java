package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe che rappresenta l'oggetto CountryRegion che fa riferimento alla tabella COUNTRY_REGIONS.
 *
 * @hibernate.class
 * table="COUNTRY_REGIONS"
 */
public class CountryRegion extends ABean
{
  private Country country;

  private String name;

  /**
   * @hibernate.id
   * column="ID_COUNTRY_REGION"
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
   * lazy="false"
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

}
