package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.SquadManager;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;

import java.util.List;

/**
 * Classe contenente le azioni per invitare una lista di utenti a diventare amici
 */
public class InviteUsersAction extends ABaseActionSupport
{

  private List idUserToInviteList;
  private String confirmMessage;

  @Override
  public String execute()
  {
    if( !UserContext.getInstance().isLoggedIn())
    {
      this.confirmMessage = "";
      return SUCCESS;
    }
    try
    {
      if (this.idUserToInviteList == null || this.idUserToInviteList.size() == 0)
      {
        this.confirmMessage = "";
        return SUCCESS;
      }

      int count = SquadManager.inviteUserMultiple(this.idUserToInviteList, UserContext.getInstance().getUser(), getCurrentObjLanguage(), getCurrentCobrand());

      this.confirmMessage = getText("messaggio.amicizia.invitoUtenteMultiplo");
      this.confirmMessage = this.confirmMessage.replace(Constants.REPLACEMENT__USER_COUNT, String.valueOf(count));

      UserContext.getInstance().setLastMessage(confirmMessage);
      return SUCCESS;

    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
    return SUCCESS;
  }

  // <editor-fold defaultstate="collapsed" desc="-- GETTERS/SETTERS --">
  public String getConfirmMessage()
  {
    return confirmMessage;
  }

  public void setConfirmMessage(String confirmMessage)
  {
    this.confirmMessage = confirmMessage;
  }

  public List getIdUserToInviteList()
  {
    return idUserToInviteList;
  }

  public void setIdUserToInviteList(List idUserToInviteList)
  {
    this.idUserToInviteList = idUserToInviteList;
  }
  // </editor-fold>


}
