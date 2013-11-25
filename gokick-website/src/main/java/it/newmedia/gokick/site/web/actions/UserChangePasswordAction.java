package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.managers.UtilManager;
import it.newmedia.gokick.site.managers.UserManager;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione per gestire il cambio della password da parte dell'utente in seguito a richiesta password dimenticata
 */
public class UserChangePasswordAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserChangePasswordAction.class);

  private int id;

  private String checkPassword;

  private String newPassword;

  private String repeatPassword;

  private boolean errorsPresent;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public String check()
  {
    if (this.id <= 0 || this.checkPassword == null || this.checkPassword.isEmpty())
    {
      addActionMessage(getText("error.user.changePassword.missingParam"));
      this.errorsPresent = true;
      return SUCCESS;
    }

    User currentUser = UtilManager.getUserByIdAndCheckPassword(this.id, this.checkPassword);
    if (currentUser == null)
    {
      addActionMessage(getText("error.user.changePassword.missingUser"));
      this.errorsPresent = true;
      return SUCCESS;
    }

    if (currentUser.getCheckPasswordExpire().before(new Date()))
    {
      addActionMessage(getText("error.user.changePassword.exipred"));
      this.errorsPresent = true;
      return SUCCESS;
    }

    this.errorsPresent = false;
    return SUCCESS;
  }

  public String save()
  {
    if (this.id <= 0 || this.checkPassword == null || this.checkPassword.isEmpty())
    {
      addActionMessage(getText("error.user.changePassword.missingParam"));
      this.errorsPresent = true;
      return SUCCESS;
    }

    User currentUser = UtilManager.getUserByIdAndCheckPassword(this.id, this.checkPassword);
    if (currentUser == null)
    {
      addActionMessage(getText("error.user.changePassword.missingUser"));
      this.errorsPresent = true;
      return SUCCESS;
    }

    if (currentUser.getCheckPasswordExpire().before(new Date()))
    {
      addActionMessage(getText("error.user.changePassword.exipred"));
      this.errorsPresent = true;
      return SUCCESS;
    }
   
    /* Validazione password */
    if ((this.newPassword == null) || (this.newPassword.length() == 0))
    {
      addFieldError("changePasswordError", getText("error.password.required"));
      return INPUT;
    }
    else
    {
      if (this.newPassword.length() < 6)
      {
        addFieldError("currentUser.password", getText("error.password.invalid"));
        return INPUT;
      }
    }

    /* Validazione conferma password */
    if ((this.repeatPassword == null) || (this.repeatPassword.length() == 0))
    {
      addFieldError("changePasswordError", getText("error.repeatPassword.required"));
      return INPUT;
    }
    else
    {
      if (!this.repeatPassword.equals(this.newPassword))
      {
        addFieldError("changePasswordError", getText("error.repeatPassword.invalid"));
        return INPUT;
      }
    }

    boolean changePassword = UserManager.changePassword(this.id, newPassword);
    if (!changePassword)
      return ERROR;
    return SUCCESS;
  }


  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public String getNewPassword()
  {
    return newPassword;
  }

  public void setNewPassword(String newPassword)
  {
    this.newPassword = newPassword;
  }

  public String getRepeatPassword()
  {
    return repeatPassword;
  }

  public void setRepeatPassword(String repeatPassword)
  {
    this.repeatPassword = repeatPassword;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getCheckPassword()
  {
    return checkPassword;
  }

  public void setCheckPassword(String checkPassword)
  {
    this.checkPassword = checkPassword;
  }

  public boolean isErrorsPresent()
  {
    return errorsPresent;
  }

  public void setErrorsPresent(boolean errorsPresent)
  {
    this.errorsPresent = errorsPresent;
  }

  // </editor-fold>
}