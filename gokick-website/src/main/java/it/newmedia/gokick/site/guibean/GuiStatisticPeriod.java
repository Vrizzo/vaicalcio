package it.newmedia.gokick.site.guibean;

/**
 *
 * @author ggeroldi
 */
public class GuiStatisticPeriod extends AGuiBean
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
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
  public GuiStatisticPeriod()
  {
  }

  /**
   *
   * @param id
   * @param label
   */
  public GuiStatisticPeriod(String id, String label)
  {
    this.id = id;
    this.label = label;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  // </editor-fold>
}
