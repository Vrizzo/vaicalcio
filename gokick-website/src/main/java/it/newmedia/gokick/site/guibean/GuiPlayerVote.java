package it.newmedia.gokick.site.guibean;

/**
 *Classe che contine le informazioni sul voto del giocate di una partita per poter essere visualizzate all'interno dell'applicazione
 * @author ggeroldi
 */
public class GuiPlayerVote extends AGuiBean
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >

  /**
   *
   */
  public final static String ID_WITHOUT_VOTE = "-0.5";
  /**
   *
   */
  public final static String ID_VOTE_NOT_ASSIGNED = "-1.0";
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  String id;
  String label;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --"  >
  /**
   * 
   * @return
   */
  public String getId()
  {
    return id;
  }

  /**
   *
   * @param id
   */
  public void setId(String id)
  {
    this.id = id;
  }

  /**
   *
   * @return
   */
  public String getLabel()
  {
    return label;
  }

  /**
   *
   * @param label
   */
  public void setLabel(String label)
  {
    this.label = label;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  /**
   *
   */
  public GuiPlayerVote()
  {
  }

  /**
   *
   * @param id
   * @param label
   */
  public GuiPlayerVote(String id, String label)
  {
    this.id = id;
    this.label = label;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  // </editor-fold>
}
