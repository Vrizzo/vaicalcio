package it.newmedia.gokick.site.infos;

/**
 *
 * Classe che gestisce le informazioni relative alle varie tipologie di ruolo per gli utenti devono essere visualizzate all'interno dell'applicazione
 */
public class PitchTypeInfo extends AInfo
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int id;
  private String name;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   *
   */
  public PitchTypeInfo()
  {
    this.valid = false;
  }

  /**
   *
   * @param id
   * @param name
   */
  public PitchTypeInfo(int id, String name)
  {
    this.valid = true;
    this.id = id;
    this.name = name;
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
  public String getName()
  {
    return name;
  }

  /**
   *
   * @param name
   */
  public void setName(String name)
  {
    this.name = name;
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

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >  
  // </editor-fold>
}
