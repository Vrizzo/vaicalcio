package it.newmedia.gokick.data.hibernate.beans;

import it.newmedia.gokick.data.enums.EnumCobrandType;
import org.apache.commons.lang.StringUtils;

/**
 * Classe Cobrand che rappresenta le configurazioni relative ai possibili Cobrand di gokick
 * Fa riferimento alla tabella COBRANDS
 *
 * @hibernate.class table="COBRANDS"
 * mutable="false"
 * @hibernate.cache usage="read-only"
 */
public class Cobrand extends ABean
{
// ------------------------------ FIELDS ------------------------------

  private String code;
  private String domain;
  private String siteUrl;
  private String gatewayUrl;
  private String homePageUrl;
  private String googleAnalyticsKey;
  private String type;
  private String facebookAppId;
  private String facebookAppCode;

// --------------------- GETTER / SETTER METHODS ---------------------

  /**
   * @hibernate.property column="CODE"
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
   * @hibernate.property column="DOMAIN"
   */
  public String getDomain()
  {
    return domain;
  }

  public void setDomain(String domain)
  {
    this.domain = domain;
  }

  /**
   * @hibernate.property column="FACEBOOK_APP_CODE"
   */
  public String getFacebookAppCode()
  {
    return facebookAppCode;
  }

  public void setFacebookAppCode(String facebookAppCode)
  {
    this.facebookAppCode = facebookAppCode;
  }

  /**
   * @hibernate.property column="FACEBOOK_APP_ID"
   */
  public String getFacebookAppId()
  {
    return facebookAppId;
  }

  public void setFacebookAppId(String facebookAppId)
  {
    this.facebookAppId = facebookAppId;
  }

  /**
   * @hibernate.property column="GATEWAY_URL"
   */
  public String getGatewayUrl()
  {
    return gatewayUrl;
  }

  public void setGatewayUrl(String gatewayUrl)
  {
    this.gatewayUrl = gatewayUrl;
  }

  /**
   * @hibernate.property column="GOOGLE_ANALYTICS_KEY"
   */
  public String getGoogleAnalyticsKey()
  {
    return googleAnalyticsKey;
  }

  public void setGoogleAnalyticsKey(String googleAnalyticsKey)
  {
    this.googleAnalyticsKey = googleAnalyticsKey;
  }

  /**
   * @hibernate.property column="HOME_PAGE_URL"
   */
  public String getHomePageUrl()
  {
    return homePageUrl;
  }

  public void setHomePageUrl(String homePageUrl)
  {
    this.homePageUrl = homePageUrl;
  }

  /**
   * @hibernate.id column="ID_COBRAND"
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
   * @hibernate.property column="SITE_URL"
   */
  public String getSiteUrl()
  {
    return siteUrl;
  }

  public void setSiteUrl(String siteUrl)
  {
    this.siteUrl = siteUrl;
  }

  /**
   * @hibernate.property column="TYPE"
   */
  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

// ------------------------ CANONICAL METHODS ------------------------

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    Cobrand cobrand = (Cobrand) o;

    if (code != null ? !code.equals(cobrand.code) : cobrand.code != null)
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    return code != null ? code.hashCode() : 0;
  }

// -------------------------- OTHER METHODS --------------------------

  public EnumCobrandType getEnumCobrandType()
  {
    if (this.type == null)
    {
      return EnumCobrandType.Complete;
    }
    return EnumCobrandType.getEnum(this.type);
  }

  public String getGatewayUrl(boolean removeHttpPrefix)
  {
    return removeHttpPrefix ? StringUtils.removeStartIgnoreCase(gatewayUrl, "http://") : gatewayUrl;
  }

  public void setEnumCobrandType(EnumCobrandType enumCobrandType)
  {
    this.type = enumCobrandType.getValue();
  }
}
