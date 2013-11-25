package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.managers.UtilManager;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.utils.DataValidator;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione per gestire la richiesta del cambio della password dimenticata
 */
public class UserChangePasswordRequestAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserChangePasswordRequestAction.class);
  private String email;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String input()
  {
    return INPUT;
  }


  @Override
  public void validate()
  {
    /* Validazione email */
    if (this.email == null || this.email.length() == 0)
    {
      addFieldError("changePasswordError", getText("error.email.required"));
    }
    else
    {
      if (!DataValidator.checkEmail(this.email))
      {
        addFieldError("changePasswordError", getText("error.email.invalid"));
      }
      else
      {
        boolean exist = UtilManager.checkExistingEmail(this.email, -1);
        if (!exist)
        {
          addFieldError("changePasswordError", getText("error.email.notpresent"));
        }
        else
        {
          User user = UserManager.getByEmail(this.email);
          if( !user.getEnumUserStatus().equals(EnumUserStatus.Enabled))
          {
            addFieldError("changePasswordError", getText("error.email.userNotEnabled"));
          }

        }
      }
    }
  }

  @Override
  public String execute()
  {
    boolean success = UserManager.changePasswordRequest(this.email, getCurrentObjLanguage(), getCurrentCobrand());
    if (!success)
    {
      return ERROR;
    }
    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  // </editor-fold>
}