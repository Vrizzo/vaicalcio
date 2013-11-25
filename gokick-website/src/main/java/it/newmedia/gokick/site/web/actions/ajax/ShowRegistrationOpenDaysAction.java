package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.managers.DateManager;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;


/**
 *Classe contenente le azioni per visualizzare il tempo che manca all'aprirsi delle iscrizioni
 *
 */
public class ShowRegistrationOpenDaysAction extends ABaseActionSupport
{

  private String  registrationOpenDate;
 
  @Override
  public String execute()
  {
    if (!registrationOpenDate.equals(""))
    {
      this.setRegistrationOpenDate(DateManager.showDaysTo(registrationOpenDate, Constants.DATE_FORMAT__DDMMYYYYHHMM,DateManager.FORMAT_DATE_14));
    }
    return SUCCESS;
  }

  // <editor-fold defaultstate="collapsed" desc="-- GETTER/SETTER --"> 
  /**
   * @return the registrationOpenDate
   */
  public String getRegistrationOpenDate()
  {
    return registrationOpenDate;
  }

  /**
   * @param registrationOpenDate the registrationOpenDate to set
   */
  public void setRegistrationOpenDate(String registrationOpenDate)
  {
    this.registrationOpenDate = registrationOpenDate;
  }
  // </editor-fold>
}
