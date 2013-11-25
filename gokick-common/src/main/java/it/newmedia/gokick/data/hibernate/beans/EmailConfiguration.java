package it.newmedia.gokick.data.hibernate.beans;

import it.newmedia.gokick.data.enums.EnumEmailConfigurationType;

/**
 *
 * Classe EmailConfiguration che rappresenta le email che possono essere inviate dall'applicazione.
 * Fa riferimento alla tabella EMAIL_CONFIGURATIONS
 *
 * @hibernate.class
 * table="EMAIL_CONFIGURATIONS"
 */
public class EmailConfiguration extends ABean
{

  private String emailConfigurationType;
  private String keyNameSubject;
  private String keyNameBody;
  private String smptServer;
  private boolean smtpAuthenticationEnabled;
  private String smtpUsername;
  private String smtpPassword;
  private int smtpPort;
  private boolean smtpSslEnabled;
  private String fromName;
  private String fromEmail;
  private String to;
  private String cc;
  private String bcc;
  private boolean htmlFormatEnabled;
  private String imagesFolderPath;
  private boolean enabled;


  /**
   * @hibernate.id
   * column="ID_EMAIL_CONFIGURATION"
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
   * column="EMAIL_CONFIGURATION_TYPE"
   */
  public String getEmailConfigurationType()
  {
    return emailConfigurationType;
  }

  public void setEmailConfigurationType(String emailConfigurationType)
  {
    this.emailConfigurationType = emailConfigurationType;
  }

  public EnumEmailConfigurationType getEnumEmailConfigurationType()
  {
    if (this.emailConfigurationType == null)
      return EnumEmailConfigurationType.Undefined;
    return EnumEmailConfigurationType.getEnum(this.emailConfigurationType);
  }

  public void setEnumEmailConfigurationType(EnumEmailConfigurationType enumEmailConfigurationType)
  {
    this.emailConfigurationType = enumEmailConfigurationType.getValue();
  }

  /**
   * @hibernate.property
   * column="KEY_NAME_SUBJECT"
   */
  public String getKeyNameSubject()
  {
    return keyNameSubject;
  }

  public void setKeyNameSubject(String keyNameSubject)
  {
    this.keyNameSubject = keyNameSubject;
  }

  /**
   * @hibernate.property
   * column="KEY_NAME_BODY"
   */
  public String getKeyNameBody()
  {
    return keyNameBody;
  }

  public void setKeyNameBody(String keyNameBody)
  {
    this.keyNameBody = keyNameBody;
  }

  /**
   * @hibernate.property
   * column="SMTP_SERVER"
   */
  public String getSmptServer()
  {
    return smptServer;
  }

  public void setSmptServer(String smptServer)
  {
    this.smptServer = smptServer;
  }

  /**
   * @hibernate.property
   * column="SMTP_AUTHENTICATION_ENABLED"
   */
  public boolean isSmtpAuthenticationEnabled()
  {
    return smtpAuthenticationEnabled;
  }

  public void setSmtpAuthenticationEnabled(boolean smtpAuthenticationEnabled)
  {
    this.smtpAuthenticationEnabled = smtpAuthenticationEnabled;
  }

  /**
   * @hibernate.property
   * column="SMTP_USER_NAME"
   */
  public String getSmtpUsername()
  {
    return smtpUsername;
  }

  public void setSmtpUsername(String smtpUsername)
  {
    this.smtpUsername = smtpUsername;
  }

  /**
   * @hibernate.property
   * column="SMTP_PASSWORD"
   */
  public String getSmtpPassword()
  {
    return smtpPassword;
  }

  public void setSmtpPassword(String smtpPassword)
  {
    this.smtpPassword = smtpPassword;
  }

  /**
   * @hibernate.property
   * column="SMTP_PORT"
   */
  public int getSmtpPort()
  {
    return smtpPort;
  }

  public void setSmtpPort(int smtpPort)
  {
    this.smtpPort = smtpPort;
  }

  /**
   * @hibernate.property
   * column="SMTP_SSL_ENABLED"
   */
  public boolean isSmtpSslEnabled()
  {
    return smtpSslEnabled;
  }

  public void setSmtpSslEnabled(boolean useSsl)
  {
    this.smtpSslEnabled = useSsl;
  }


  /**
   * @hibernate.property
   * column="FROM_NAME"
   */
  public String getFromName()
  {
    return fromName;
  }

  public void setFromName(String fromName)
  {
    this.fromName = fromName;
  }

  /**
   * @hibernate.property
   * column="FROM_EMAIL"
   */
  public String getFromEmail()
  {
    return fromEmail;
  }

  public void setFromEmail(String fromEmail)
  {
    this.fromEmail = fromEmail;
  }

  /**
   * @hibernate.property
   * column="TO"
   */
  public String getTo()
  {
    return to;
  }

  public void setTo(String to)
  {
    this.to = to;
  }

  /**
   * @hibernate.property
   * column="CC"
   */
  public String getCc()
  {
    return cc;
  }

  public void setCc(String cc)
  {
    this.cc = cc;
  }

  /**
   * @hibernate.property
   * column="BCC"
   */
  public String getBcc()
  {
    return bcc;
  }

  public void setBcc(String bcc)
  {
    this.bcc = bcc;
  }

  /**
   * @hibernate.property
   * column="HTML_FORMAT_ENABLED"
   */
  public boolean isHtmlFormatEnabled()
  {
    return htmlFormatEnabled;
  }

  public void setHtmlFormatEnabled(boolean htmlFormatEnabled)
  {
    this.htmlFormatEnabled = htmlFormatEnabled;
  }

  /**
   * @hibernate.property
   * column="IMAGES_FOLDER_PATH"
   */
  public String getImagesFolderPath()
  {
    return imagesFolderPath;
  }

  public void setImagesFolderPath(String imagesFolderPath)
  {
    this.imagesFolderPath = imagesFolderPath;
  }

  /**
   * @hibernate.property
   * column="ENABLED"
   */
  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }

}
