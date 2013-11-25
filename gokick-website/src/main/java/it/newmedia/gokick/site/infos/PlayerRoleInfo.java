package it.newmedia.gokick.site.infos;

/**
 *
 * Classe che gestisce le informazioni relative alle varie tipologie di ruolo per gli utenti devono essere visualizzate all'interno dell'applicazione
 */
public class PlayerRoleInfo extends AInfo
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int id;
  private String name;
  private String shotName;
  private String signName;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   *
   */
  public PlayerRoleInfo()
  {
    this.valid = false;
  }

  /**
   *
   * @param id
   * @param name
   */
  public PlayerRoleInfo(int id, String name)
  {
    this.valid = true;
    this.id = id;
    this.name = name;
    this.shotName = name.substring(0, 3);
    this.signName = name.substring(0, 1);
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
  public String getShotName()
  {
    return shotName;
  }

  /**
   *
   * @param shotName
   */
  public void setShotName(String shotName)
  {
    this.shotName = shotName;
  }

  /**
   *
   * @return
   */
  public String getSignName()
  {
    return signName;
  }

  /**
   *
   * @param signName
   */
  public void setSignName(String signName)
  {
    this.signName = signName;
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
