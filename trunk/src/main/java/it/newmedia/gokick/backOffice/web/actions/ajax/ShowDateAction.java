package it.newmedia.gokick.backOffice.web.actions.ajax;

import it.newmedia.gokick.backOffice.manager.DateManager;
import it.newmedia.gokick.backOffice.manager.TranslationManager;
import it.newmedia.gokick.backOffice.web.actions.ABaseActionSupport;
import it.newmedia.gokick.data.hibernate.beans.Translation;
import javax.sound.midi.Transmitter;

/**
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
      Translation transFormatOut = TranslationManager.getTranslation(this.formatOut);
      this.dataOut = DateManager.showDate(this.data, this.formatIn, "%1$ta " + transFormatOut.getKeyValue());
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
