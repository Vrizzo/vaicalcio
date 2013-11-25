package it.newmedia.gokick.site.web.actions.social;

import it.newmedia.gokick.site.web.actions.*;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.net.HttpConnection;
import it.newmedia.results.Result;
import it.newmedia.web.WebUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class FacebookAction extends ABaseActionSupport
{
  // ------------------------------ FIELDS ------------------------------

  //<editor-fold defaultstate="collapsed" desc="CONSTANTS">
  public static final String FB_CONNECT_SUCCESS = "dialogUrl";
  public static final String FB_CONNECT_RETURN_ONSITE = "returnOnSite";
  public static final String REQUEST_FACEBOOK_CODE = "code";
  public static final String SESSION_FACEBOOK_STATE = "state";
  private static final String EMPTY_FIELD = "null";
  //</editor-fold>
  //<editor-fold defaultstate="collapsed" desc="MEMBERS">
  private String dialogUrl;

  // --------------------- GETTER / SETTER METHODS ---------------------

  //</editor-fold>
  //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
  public String getDialogUrl()
  {
    return dialogUrl;
  }

  public void setDialogUrl(String dialogUrl)
  {
    this.dialogUrl = dialogUrl;
  }

  // -------------------------- OTHER METHODS --------------------------

  //</editor-fold>
  //<editor-fold defaultstate="collapsed" desc="ACTION METHODS">
  public String facebookLogin()
  {
    try
    {
      User currentUser = null;
      String appId = UserContext.getInstance().getCurrentCobrand().getFacebookAppId();
      String appSecret = UserContext.getInstance().getCurrentCobrand().getFacebookAppCode();
      String redirectUrl = AppContext.getInstance()
                                     .getRedirectUrlFacebook(getCurrentCobrand());
      String encodedUrl = WebUtil.encode(redirectUrl);

      HttpServletRequest request = ServletActionContext.getRequest();
      String code = WebUtil.getRequestParamAsString(REQUEST_FACEBOOK_CODE, request, "");

      if (StringUtils.isEmpty(code))
      {
        request.getSession()
               .setAttribute(SESSION_FACEBOOK_STATE, RandomStringUtils.randomAlphanumeric(8));
        this.dialogUrl = AppContext.getInstance()
                                   .getFacebookUrl() + "&client_id=" + appId + "&redirect_uri=" + encodedUrl + "&state=" + request.getSession()
                                                                                                                                  .getAttribute(
                                                                                                                                          SESSION_FACEBOOK_STATE);
        return FB_CONNECT_SUCCESS;
      }

      String requestState = WebUtil.getRequestParamAsString(SESSION_FACEBOOK_STATE, request, "");
      String sessionState = request.getSession()
                                   .getAttribute(SESSION_FACEBOOK_STATE)
                                   .toString();

      if (!requestState.equals(sessionState))
      {
        logger.error("The state does not match. You may be a victim of CSRF.");
        return ERROR;
      }
      this.dialogUrl = "/calendar!viewCalendar.action";

      String accessToken = readAccessToken(appId, encodedUrl, appSecret, code);
      logger.debug("Primo   access_token: " + accessToken);
      //richiamo il metodo con l'access token ricevuto per "prolungare" la durata dell'access token
      accessToken = readAccessToken(appId, encodedUrl, appSecret, code, accessToken);
      logger.debug("Secondo access_token: " + accessToken);
      Result<JSONObject> rUser = readUserInfo(accessToken);
      if (!rUser.isSuccessNotNull())
      {
        logger.error(rUser.getErrorException(), rUser.getErrorException());
        return ERROR;
      }

      String idFacebookUserValue = getValueFromJSONObject(rUser.getValue(), "id");
      if (StringUtils.isEmpty(idFacebookUserValue))
      {
        logger.error("Error retrieving facebook ID");
        return ERROR;
      }

      currentUser = UserManager.getUserByIdFacebook(idFacebookUserValue);
      if (currentUser != null) //utente già associato
      {
        if (currentUser.getEnumUserStatus()
                       .equals(EnumUserStatus.Enabled) == false)
        {
          UserContext.getInstance()
                     .reset();
          addFieldError("loginError", getText("error.login.pending")); //TO-DO: cambiare messaggio
          return FB_CONNECT_RETURN_ONSITE;
        }
        else
        {
          //Uso questo metodo così si aggiornano tutti i dati (last update, last ip...)
          //e imposto il token in modo che venga aggiornato
          UserContext.getInstance()
                     .setTemporaryAccessTokenFacebook(accessToken);
          UserManager.login(currentUser.getEmail(), currentUser.getPassword(), false);
          return FB_CONNECT_RETURN_ONSITE;
        }
      }
      else //prima volta
      {
        String emailFacebbokUserValue = getValueFromJSONObject(rUser.getValue(), "email");
        currentUser = UserManager.getUserByEmail(emailFacebbokUserValue);
        if (currentUser != null)
        {
          if (currentUser.getEnumUserStatus()
                         .equals(EnumUserStatus.Enabled) == false)
          {
            UserContext.getInstance()
                       .reset();
            addFieldError("loginError", getText("error.login.pending")); //TO-DO: cambiare messaggio
            return FB_CONNECT_RETURN_ONSITE;
          }
          else
          {
            //Uso questo metodo così si aggiornano tutti i dati (last update, last ip...)
            //e imposto il token in modo che venga aggiornato
            UserContext.getInstance()
                       .setFacebookIdUserTemporary(idFacebookUserValue);
            UserContext.getInstance()
                       .setTemporaryAccessTokenFacebook(accessToken);
            UserManager.login(currentUser.getEmail(), currentUser.getPassword(), false);
            return FB_CONNECT_RETURN_ONSITE;
          }
        }
        else
        {
          UserContext.getInstance()
                     .setFacebookIdUserTemporary(idFacebookUserValue);
          UserContext.getInstance()
                     .setTemporaryAccessTokenFacebook(accessToken);
          this.dialogUrl = "/facebookRegistration.action";
          return FB_CONNECT_RETURN_ONSITE;
        }
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return ERROR;
    }
  }

  private static String readAccessToken(String appId, String redirectUri, String appSecret, String code)
  {
    return readAccessToken(appId, redirectUri, appSecret, code, "");
  }
  private static String readAccessToken(String appId, String redirectUri, String appSecret, String code, String existingAccessToken)
  {
    String accessToken = "";
    try
    {
      StringBuilder tokenUrlSb = new StringBuilder().append("https://graph.facebook.com/oauth/access_token")
                                           .append("?client_id=")
                                           .append(appId)
                                           .append("&redirect_uri=")
                                           .append(redirectUri)
                                           .append("&client_secret=")
                                           .append(appSecret)
                                           .append("&code=")
                                           .append(code);
      if( StringUtils.isNotBlank(existingAccessToken))
      {
        tokenUrlSb.append("&grant_type=fb_exchange_token")
                  .append("&fb_exchange_token=")
                  .append(existingAccessToken);
      }
      HttpConnection connection = new HttpConnection(tokenUrlSb.toString());
      connection.doGet();

      if (connection.getResponseCode() == HttpConnection.HTTP_RESPONSECODE_200_OK)
      {
        accessToken = StringUtils.substringBetween(connection.getResponse(), "access_token=", "&expires");
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return "";
    }
    return accessToken;
  }

  private static Result<JSONObject> readUserInfo(String accessToken)
  {
    try
    {
      String url = String.format("https://graph.facebook.com/me?fields=id,first_name,last_name,birthday,email,picture&access_token=%s", accessToken);

      HttpConnection connection = new HttpConnection(url);
      connection.doGet();

      JSONObject jo = (JSONObject) JSONSerializer.toJSON(connection.getResponse());

      return new Result<JSONObject>(jo, true);
    }
    catch (Exception ex)
    {
      return new Result<JSONObject>(ex);
    }
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="PRIVATE METHODS">
  private static String getValueFromJSONObject(JSONObject jo, String key)
  {
    String value = "";
    try
    {
      if (jo != null)
      {
        value = jo.getString(key);
        if (value.equalsIgnoreCase(EMPTY_FIELD) == false)
        {
          return value;
        }
      }
    }
    catch (JSONException ex)
    {
      logger.error(ex, ex);
    }
    return value;
  }

  //</editor-fold>
}
