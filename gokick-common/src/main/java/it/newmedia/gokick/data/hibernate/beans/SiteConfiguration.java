package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe SiteConfiguration che rappresenta i parametri di configurazione utilizzati all'interno dell'applicazione.
 * Fa riferimento alla tabella SITE_CONFIGURATIONS
 *
 * @hibernate.class
 * table="SITE_CONFIGURATIONS"
 */
public class SiteConfiguration extends ABean
{
  private String keyConf;
  private String valueConf;
  private String description;


  /**
   * @hibernate.id
   * column="ID_SITE_CONFIGURATION"
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
   * column="KEY_CONF"
   */
  public String getKeyConf()
  {
    return keyConf;
  }

  public void setKeyConf(String keyConf)
  {
    this.keyConf = keyConf;
  }

  /**
   * @hibernate.property
   * column="VALUE_CONF"
   */
  public String getValueConf()
  {
    return valueConf;
  }

  public void setValueConf(String valueConf)
  {
    this.valueConf = valueConf;
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

}
