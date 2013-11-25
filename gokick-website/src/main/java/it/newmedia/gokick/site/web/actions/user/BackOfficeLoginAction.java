package it.newmedia.gokick.site.web.actions.user;

import it.newmedia.gokick.site.web.actions.*;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.security.encryption.MD5;
import it.newmedia.web.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * Classe contenente l'azione relativa alla login per dell'utente, chiamata da backoffice
 */
public class BackOfficeLoginAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int id = 0;

  private String key = "";

  private String goToUrl;

  private String errorCode = "";

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    User currentUser = UserManager.getById(this.id);
    if (currentUser == null)
    {
      this.errorCode = "XXX";
      return Constants.STRUTS_RESULT_NAME__INFO;
    }
    if (!key.equals(MD5.getHashString(currentUser.getPassword())))
    {
      this.errorCode = "XXX";
      return Constants.STRUTS_RESULT_NAME__INFO;
    }
    if(StringUtils.isBlank(this.goToUrl) )
    {
      this.errorCode = "XXX";
      return Constants.STRUTS_RESULT_NAME__INFO;
    }
    UserManager.login(currentUser.getEmail(), currentUser.getPassword(), false);
    return Constants.STRUTS_RESULT_NAME__GO_TO_URL;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public String getGoToUrl()
  {
    return goToUrl;
  }

  public void setGoToUrl(String goToUrl)
  {
    this.goToUrl = goToUrl;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getKey()
  {
    return key;
  }

  public void setKey(String key)
  {
    this.key = key;
  }

  public String getErrorCode()
  {
    return errorCode;
  }

  // </editor-fold>
}
