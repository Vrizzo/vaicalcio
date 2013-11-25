package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe che rappresenta l'oggetto FootballTeam che fa riferimento alla tabella FOOTBALL_TEAMS.
 *
 * @hibernate.class
 * table="FOOTBALL_TEAMS"
 */
public class FootballTeam extends ABean
{
  private Country country;
  private CountryRegion countryRegion;
  private String code;
  private String name;


  /**
   * @hibernate.id
   * column="ID_FOOTBALL_TEAM"
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
   * @hibernate.many-to-one
   * column="ID_COUNTRY_REGION"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.CountryRegion"
   */
  public CountryRegion getCountryRegion()
  {
    return countryRegion;
  }

  public void setCountryRegion(CountryRegion countryRegion)
  {
    this.countryRegion = countryRegion;
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
