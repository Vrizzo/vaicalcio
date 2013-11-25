package it.newmedia.gokick.site.web.actions.user;

import it.newmedia.gokick.site.web.actions.*;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.UserManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione relativa alla login per dell'utente.
 */
public class UserLoginAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private String email;
  private String password;
  private boolean rememberMe;
  private String goToUrl;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public void validate()
  {
    if ( StringUtils.isEmpty(this.email) || StringUtils.isEmpty(this.password))
    {
      if( UserContext.getInstance().isWithGoToUrl() )
        addFieldError("loginError", getText("error.login.goToUrl"));
      else
        addFieldError("loginError", getText("error.login.emailpassword"));
    }
  }

  @Override
  public String execute()
  {
    User currentUser = UserManager.login(this.email, this.password, this.rememberMe);
    if (currentUser == null)
    {
      UserContext.getInstance().reset();
      addFieldError("loginError", getText("error.login.notvalid"));
      return INPUT;
    }
    else if (currentUser.getEnumUserStatus().equals(EnumUserStatus.Pending))
    {
      UserContext.getInstance().reset();
      addFieldError("loginError", getText("error.login.pending"));
      return INPUT;
    }
    if( UserContext.getInstance().isWithGoToUrl())
    {
      this.goToUrl = UserContext.getInstance().getGoToUrl();
      UserContext.getInstance().resetGoToUrl();
      return Constants.STRUTS_RESULT_NAME__GO_TO_URL;
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

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public boolean isRememberMe()
  {
    return rememberMe;
  }

  public void setRememberMe(boolean rememberMe)
  {
    this.rememberMe = rememberMe;
  }
  
  public String getGoToUrl()
  {
    return goToUrl;
  }
  // </editor-fold>
}
