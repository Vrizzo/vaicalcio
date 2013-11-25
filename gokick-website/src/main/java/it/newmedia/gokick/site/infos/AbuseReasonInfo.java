package it.newmedia.gokick.site.infos;

/**
 *
 * Classe che gestisce le informazioni relative alle varie tipologie di motivi segnalazione abuso utente
 */
public class AbuseReasonInfo extends AInfo
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int id;
  private String translation;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   *
   */
  public AbuseReasonInfo()
  {
    this.valid = false;
  }

  /**
   *
   * @param id
   * @param name
   */
  public AbuseReasonInfo(int id, String translation)
  {
    this.valid = true;
    this.id = id;
    this.translation = translation;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  /**
   *
   * @return
   */
  public int getId()
  {
    return id;
  }

  /**
   *
   * @param id
   */
  public void setId(int id)
  {
    this.id = id;
  }

  
  /**
   *
   * @return
   */
  @Override
  public Boolean isValid()
  {
    return this.valid;
  }

  /**
   * @return the translation
   */
  public String getTranslation()
  {
    return translation;
  }

  /**
   * @param translation the translation to set
   */
  public void setTranslation(String translation)
  {
    this.translation = translation;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >  
  // </editor-fold>
}
