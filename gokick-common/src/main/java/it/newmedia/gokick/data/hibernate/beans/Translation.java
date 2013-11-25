package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe che rappresenta le traduzioni dell'applicazione.
 * Fa riferimento alla tabella TRANSLATIONS
 *
 * @hibernate.class
 * table="TRANSLATIONS"
 */
public class Translation extends ABean
{
  private String language;
  private String translationType;
  private String keyName;
  private String keyValue;
  private boolean replacementEnabled;
  private boolean htmlEnabled;

  /**
   * @hibernate.id
   * column="ID_TRANSLATION"
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
   * column="LANGUAGE"
   */
  public String getLanguage()
  {
    return language;
  }

  public void setLanguage(String language)
  {
    this.language = language;
  }

  /**
   * @hibernate.property
   * column="TRANSLATION_TYPE"
   */
  public String getTranslationType()
  {
    return translationType;
  }
  
  public void setTranslationType(String transaltionType)
  {
    this.translationType = transaltionType;
  }

  /**
   * @hibernate.property
   * column="KEY_NAME"
   */
  public String getKeyName()
  {
    return keyName;
  }

  public void setKeyName(String keyName)
  {
    this.keyName = keyName;
  }

  /**
   * @hibernate.property
   * column="KEY_VALUE"
   */
  public String getKeyValue()
  {
    return keyValue;
  }

  public void setKeyValue(String keyValue)
  {
    this.keyValue = keyValue;
  }

  /**
   * @hibernate.property
   * column="REPLACEMENT_ENABLED"
   */
  public boolean isReplacementEnabled()
  {
    return replacementEnabled;
  }

  public void setReplacementEnabled(boolean replacementEnabled)
  {
    this.replacementEnabled = replacementEnabled;
  }

  /**
   * Indica se il messaggio contiee dei tag html
   * @return True se il messaggio contiene una
   * stringa in formato html, altrimenti False
   * @hibernate.property
   * column="HTML_ENABLED"
   */
  public boolean isHtmlEnabled()
  {
    return htmlEnabled;
  }

  public void setHtmlEnabled(boolean htmlEnabled)
  {
    this.htmlEnabled = htmlEnabled;
  }

  @Override
  public String toString()
  {
    return String.format("[%s / %s], html %s, repl %s", this.language, this.keyName, this.htmlEnabled, this.replacementEnabled);
  }
  
  public Translation copy()
  {
    Translation copy = new Translation();
    copy.setId(this.id);
    copy.setKeyName(this.keyName);
    copy.setKeyValue(this.keyValue);
    copy.setLanguage(this.language);
    copy.setReplacementEnabled(this.replacementEnabled);
    copy.setHtmlEnabled(this.htmlEnabled);
    copy.setTranslationType(this.translationType);
    return copy;
  }

  
}
