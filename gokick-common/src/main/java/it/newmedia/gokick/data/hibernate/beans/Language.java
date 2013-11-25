package it.newmedia.gokick.data.hibernate.beans;

import java.util.Locale;

/**
 *
 * Classe che rappresenta l'oggetto Language che fa riferimento alla tabella LANGUAGES.
 *
 * @hibernate.class
 * table="LANGUAGES"
 * mutable="false"
 */
public class Language extends ABean
{
  private String language;

  private String label;

  private String localeStr;

  private int position;

  private boolean enabled;

  /**
   * @hibernate.id
   * column="ID_LANGUAGE"
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
   * column="LABEL"
   */
  public String getLabel()
  {
    return label;
  }

  public void setLabel(String label)
  {
    this.label = label;
  }

  /**
   * @hibernate.property
   * column="LOCALE"
   */
  public String getLocaleStr()
  {
    return localeStr;
  }

  public void setLocaleStr(String locale)
  {
    this.localeStr = locale;
  }

  public Locale getLocale()
  {
    return new Locale(this.language);
  }

  /**
   * @hibernate.property
   * column="POSITION"
   */
  public int getPosition()
  {
    return position;
  }

  public void setPosition(int position)
  {
    this.position = position;
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

  @Override
  public String toString()
  {
    return String.format("Language: %s, Locale: %s, Enabled: %s", this.language, this.localeStr, this.enabled);
  }



}
