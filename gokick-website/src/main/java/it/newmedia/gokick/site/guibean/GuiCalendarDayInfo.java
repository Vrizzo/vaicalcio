package it.newmedia.gokick.site.guibean;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che contine le informazioni delle partite programmate in una stessa giornata quando devono essere visualizzate all'interno dell'applicazione
 * @author ggeroldi
 */
public class GuiCalendarDayInfo extends AGuiBean
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  private String correspondingDate;

  private List<GuiCalendarInfo> guiCalendarInfoList;

  private int listSize;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --"  >
  /**
   *
   * @return
   */
  public String getCorrespondingDate()
  {
    return correspondingDate;
  }

  /**
   *
   * @param correspondingDate
   */
  public void setCorrespondingDate(String correspondingDate)
  {
    this.correspondingDate = correspondingDate;
  }

  /**
   *
   * @return
   */
  public List<GuiCalendarInfo> getGuiCalendarInfoList()
  {
    //System.out.println(guiCalendarInfoList.size());
    return guiCalendarInfoList;
  }

  /**
   *
   * @param guiCalendarInfoList
   */
  public void setGuiCalendarInfoList(List<GuiCalendarInfo> guiCalendarInfoList)
  {
    this.guiCalendarInfoList = guiCalendarInfoList;
  }

  /**
   * @return the listSize
   */
  public int getListSize()
  {
    return guiCalendarInfoList.size();
  }

  /**
   * @param listSize the listSize to set
   */
  public void setListSize(int listSize)
  {
    this.listSize = listSize;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  /**
   *
   */
  public GuiCalendarDayInfo()
  {
    this.guiCalendarInfoList = new ArrayList<GuiCalendarInfo>();
  }

  

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  // </editor-fold>
}
