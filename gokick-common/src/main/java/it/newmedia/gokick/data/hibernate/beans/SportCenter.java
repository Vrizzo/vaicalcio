package it.newmedia.gokick.data.hibernate.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
/**
 *
 * Classe che rappresenta l'oggetto SportCenter che fa riferimento alla tabella SPORTS_CENTERS.
 *
 * @hibernate.class
 * table="SPORTS_CENTERS"
 */
public class SportCenter extends ABean
{
  private Country country;
  private Province province;
  private City city; 
  private User userAuthor;
  private String name;
  private String address;
  private String cap;
  private String telephone;
  private String webSite;
  private String email;
  private BigDecimal latitude;
  private BigDecimal longitude;
  private Date conventionFrom;
  private Date conventionTo;
  private String description;
  private String transports;
  private String promo;
  private Boolean enabled;
  private Date changed;
  private Date created;
  private Boolean approved;
  private Set<Pitch> pitchList;
  private Set<Match> matchList;
  
  

  /**
   * @hibernate.id
   * column="ID_SPORTS_CENTER"
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
   * column="ADDRESS"
   */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

      /**
   * @hibernate.property
   * column="CAP"
   */
    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

      /**
   * @hibernate.property
   * column="CHANGED"
   */
    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }

    /**
   * @hibernate.many-to-one
   * column="ID_CITY"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.City"
   */
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    /**
   * @hibernate.property
   * column="CONVENTION_FROM"
   */
    public Date getConventionFrom() {
        return conventionFrom;
    }

    public void setConventionFrom(Date conventionFrom) {
        this.conventionFrom = conventionFrom;
    }

   /**
   * @hibernate.property
   * column="CONVENTION_TO"
   */
    public Date getConventionTo() {
        return conventionTo;
    }

    public void setConventionTo(Date conventionTo) {
        this.conventionTo = conventionTo;
    }

    /**
   * @hibernate.many-to-one
   * column="ID_COUNTRY"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.Country"
   */
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    /**
   * @hibernate.property
   * column="CREATED"
   */
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

     /**
   * @hibernate.property
   * column="DESCRIPTION"
   */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

     /**
   * @hibernate.property
   * column="EMAIL"
   */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
   * @hibernate.property
   * column="LATITUDE"
   */
  public BigDecimal getLatitude()
  {
    return latitude;
  }
  
  public void setLatitude(BigDecimal latitude)
  {
    this.latitude = latitude;
  }

  /**
   * @hibernate.property
   * column="LONGITUDE"
   */
  public BigDecimal getLongitude()
  {
    return longitude;
  }
  
  public void setLongitude(BigDecimal longitude)
  {
    this.longitude = longitude;
  }

     /**
   * @hibernate.property
   * column="ENABLED"
   */
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

     /**
   * @hibernate.property
   * column="NAME"
   */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     /**
   * @hibernate.property
   * column="PROMO"
   */
    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    /**
   * @hibernate.many-to-one
   * column="ID_PROVINCE"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.Province"
   */
    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

      /**
   * @hibernate.property
   * column="TELEPHONE"
   */
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

   /**
   * @hibernate.property
   * column="TRANSPORTS"
   */
    public String getTransports() {
        return transports;
    }

    public void setTransports(String transports) {
        this.transports = transports;
    }
    
    /**
   * @hibernate.property
   * column="APPROVED"
   */
    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    /**
   * @hibernate.many-to-one
   * column="ID_USER_AUTHOR"
   * cascade="none"
   * not-null="true"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   */
    public User getUserAuthor() {
        return userAuthor;
    }

    public void setUserAuthor(User userAuthor) {
        this.userAuthor = userAuthor;
    }

    /**
     * @hibernate.property
     * column="WEB_SITE"
     */
    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    /**
     * @hibernate.set
     * inverse="true"
     * lazy="true"
     * outer-join="true"
     * @hibernate.key
     * column="ID_SPORTS_CENTER"
     * @hibernate.one-to-many
     * class="it.newmedia.gokick.data.hibernate.beans.Pitch"
     *
     * @return Ritorna la lista dei campi
     * associati al centro sportivo
   */
    public Set<Pitch> getPitchList() {
        return pitchList;
    }

    public void setPitchList(Set<Pitch> pitchList) {
        this.pitchList = pitchList;
    }
 
  /**
   * @hibernate.set
   * lazy="true"
   * @hibernate.key
   * column="ID_SPORTS_CENTER"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.Match"
   *
   * @return La lista delle partite per un centro sportivo
   */
  public Set<Match> getMatchList()
  {
    return this.matchList;
  }

  public void setMatchList(Set<Match> matchList)
  {
    this.matchList = matchList;
  }
  
}
