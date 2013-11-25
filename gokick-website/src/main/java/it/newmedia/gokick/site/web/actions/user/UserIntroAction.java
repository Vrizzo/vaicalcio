package it.newmedia.gokick.site.web.actions.user;

import it.newmedia.gokick.data.hibernate.beans.SpecialInvitation;
import it.newmedia.gokick.data.hibernate.beans.UserInvitation;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.web.actions.*;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente l'azione relativa alla login dell'utente che si sta registrando.
 */
public class UserIntroAction extends ABaseActionSupport {
  
  // <editor-fold defaultstate="collapsed" desc="-- Members --">

  private String inviteKey;
  private String invitationErrorCode;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public String getInviteKey()
  {
    return inviteKey;
  }

  public void setInviteKey(String inviteKey)
  {
    this.inviteKey = inviteKey;
  }

  public String getInvitationErrorCode()
  {
    return invitationErrorCode;
  }

  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    if (this.isLoggedIn())
    {
      //L'utente è già loggato, ridirigo alla home page
      return HOME;
    }

    // <editor-fold defaultstate="collapsed" desc="inviti speciali">
    if(false)
    {
      return SUCCESS;
    }
    // </editor-fold>
    //UserContext.getInstance().reset();
    if (AppContext.getInstance().getInvitationsEnabled())
    {
      if (StringUtils.isBlank(this.inviteKey))
      {
        if (StringUtils.isBlank(UserContext.getInstance().getInvitationCode()))
        {
          //Siamo spiacenti, l'indirizzo inserito non è corretto
          UserContext.getInstance().reset();
          this.invitationErrorCode = "invitationErrorInvalid";
          return ERROR;
        }
        this.inviteKey=UserContext.getInstance().getInvitationCode();
      }

      SpecialInvitation specialInvitation = UserManager.getSpecialInvitationByCode(this.inviteKey);
      if (specialInvitation != null)
      {
        UserContext.getInstance().setInvitationCode(this.inviteKey);
        return SUCCESS;
      }

      UserInvitation userInvitation = UserManager.getUserInvitationByCode(this.inviteKey);
      if (userInvitation == null)
      {
        //Siamo spiacenti, l'indirizzo inserito non è corretto
        UserContext.getInstance().reset();
        this.invitationErrorCode = "invitationErrorInvalid";
        return ERROR;
      }
      if (userInvitation.getUser() != null)
      {
        //Siamo spiacenti, l'invito è già stato utilizzato
        UserContext.getInstance().reset();
        this.invitationErrorCode = "invitationErrorUsed";
        return ERROR;
      }
      UserInfo userInfoOwner = InfoProvider.getUserInfo(userInvitation.getUserOwner().getId());
      if (!userInfoOwner.isValid() || userInfoOwner.getInvitationsAvailable() <= 0)
      {
        //Siamo spiacenti, la persona che ti ha invitato ha esaurito gli inviti a disposizione
        UserContext.getInstance().reset();
        this.invitationErrorCode = "invitationErrorNotAvailable";
        return ERROR;
      }

      UserContext.getInstance().setInvitationCode(this.inviteKey);
    }
    return SUCCESS;
  }
  // </editor-fold>
}
