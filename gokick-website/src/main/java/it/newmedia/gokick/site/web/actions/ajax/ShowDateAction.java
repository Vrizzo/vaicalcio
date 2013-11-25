package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.managers.DateManager;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;

/**
 *Classe contenente le azioni per visulaizzare una data nel formato specificato
 *
 *
 */
public class ShowDateAction extends ABaseActionSupport
{
  private String data;
  private String formatIn;
  private String formatOut;
  private String dataOut;

  @Override
  public String execute()
  {
    this.dataOut = DateManager.showDate(this.data, this.formatIn, this.formatOut);
    return SUCCESS;
  }

  // <editor-fold defaultstate="collapsed" desc="-- GETTER/SETTER --">
  /**
   * @return the data
   */
  public String getData()
  {
    return data;
  }

  /**
   * @param data the data to set
   */
  public void setData(String data)
  {
    this.data = data;
  }
  /**
   * @return the formatIn
   */
  public String getFormatIn()
  {
    return formatIn;
  }

  /**
   * @param formatIn the formatIn to set
   */
  public void setFormatIn(String formatIn)
  {
    this.formatIn = formatIn;
  }

  /**
   * @return the formatOut
   */
  public String getFormatOut()
  {
    return formatOut;
  }

  /**
   * @param formatOut the formatOut to set
   */
  public void setFormatOut(String formatOut)
  {
    this.formatOut = formatOut;
  }
  /**
   * @return the dataOut
   */
  public String getDataOut()
  {
    return dataOut;
  }


  // </editor-fold>

 
}
