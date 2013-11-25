package it.newmedia.gokick.site.web.actions.user;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.site.web.actions.*;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.MatchInfoManager;
import it.newmedia.gokick.site.managers.PlayerManager;
import it.newmedia.gokick.site.managers.SquadManager;
import it.newmedia.gokick.site.managers.UserInfoManager;
import it.newmedia.gokick.site.managers.UserManager;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente l'azione relativa alla cancellazione dell'utente, per essere effettuata non deve: avere partite future organizzate
 * essere in richiesta o registrato a partite,avere inviti o richieste d'amicizia pendenti,avere amicizie in atto
 */
public class UserCancelAction extends ABaseActionSupport implements Preparable
{
  // <editor-fold defaultstate="collapsed" desc="-- Members --">

  private String password;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >


  public void prepare()
  {
  }

  @Override
  public String input()
  {
    return INPUT;
  }

  @Override
  public String execute()
  {
    boolean success = UserManager.cancel(UserContext.getInstance().getUser().getId());
    if (!success)
    {
      addFieldError("password", getText("userCancelAccount.impossibileCancellare"));
      return INPUT;
    }
    UserContext.getInstance().reset();
    return SUCCESS;
  }
  
  @Override
  public void validate()
  {
    if (StringUtils.isBlank(this.password))
    {
      addFieldError("password", getText("userCancelAccount.passwordMancante"));
      return;
    }
    if (!this.password.equals(UserContext.getInstance().getUser().getPassword()))
    {
      addFieldError("password", getText("userCancelAccount.passwordNonValida"));
      return;
    }
    if (hasFutureMatch())
    {
      addFieldError("futureMatchOrganized", getText("error.userCancelAccountfutureMatchOrg"));
     
    }
    if (isRegisteredOrRequest())
    {
      addFieldError("futureMatchRegisteredOrRequest", getText("error.userCancelAccountFutureMatchRegOrReq"));
      
    }
    if (hasPendingRelations())
    {
      addFieldError("pendingRelations", getText("error.userCancelAccountPendingRelations"));
     
    }
    if (hasFriends())
    {
      addFieldError("hasFriends", getText("error.userCancelAccountHasFriends"));
     
    }
  }

  public boolean hasFutureMatch()
  {
    int futMatchNumb = MatchInfoManager.countOrganized(UserContext.getInstance().getUser().getId());
    if (futMatchNumb > 0)
    { 
      return true;
    }
    return false;
  }

  public boolean isRegisteredOrRequest()
  {    
    if (MatchInfoManager.countWhereUserInPlayerList(UserContext.getInstance().getUser().getId()) > 0)
    {
      return true;
    }
    return false;
  }

  public boolean hasPendingRelations()
  {
    if (SquadManager.countPendingRelations(UserContext.getInstance().getUser().getId()) > 0)
    {
      return true;
    }
    return false;
  }

  public boolean hasFriends()
  {
    if (SquadManager.countRelationsConfirmed(UserContext.getInstance().getUser().getId()) > 0)
    {
      return true;
    }
    return false;
  }

  // </editor-fold>
}
