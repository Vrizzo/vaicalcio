package it.newmedia.gokick.site.web.actions.invitation;

import it.newmedia.gokick.site.managers.EmailManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.results.Result;
import it.newmedia.utils.DataValidator;
import org.apache.commons.lang.StringUtils;

/**
  * Classe contenente le azioni per richiedere un invito al sito (dalla home non loggati)
 * 
 */
public class InviteRequest extends AInviteAction {

  protected String mailFrom;
  protected String requestType;


  /**
   * @return the mailFrom
   */
  public String getMailFrom()
  {
    return mailFrom;
  }

  /**
   * @param mailFrom the mailFrom to set
   */
  public void setMailFrom(String mailFrom)
  {
    this.mailFrom = mailFrom;
  }

  public String getRequestType()
  {
    return requestType;
  }

  public void setRequestType(String requestType)
  {
    this.requestType = requestType;
  }

  @Override
  public String input()
  {
    this.requestType = "";
    return super.input();
  }

  @Override
  public void validate()
  {
    if (DataValidator.checkEmail(this.mailFrom) == false)
    {
      this.addFieldError("mailFrom", getText("error.email.invalid"));
    }
    if( StringUtils.isBlank(this.requestType))
    {
      this.addFieldError("requestType", getText("error.invitiRichiestaTipoPartecipazione"));
    }
  }

  @Override
  public String execute()
  {

    Result<Boolean> rEmailSend = EmailManager.inviteRequest(this.mailFrom, this.freeText, this.requestType, InfoProvider.getDefaultLanguage(), getCurrentCobrand());
    if (rEmailSend.getValue() == true)
    {
      addActionMessage(this.mailFrom);
    }
    else
    {
      logger.error(rEmailSend.getErrorMessage());
      addActionError(this.mailFrom);
    }
    //this.status = STATUS_SENT;
    return SUCCESS;
  }

}
