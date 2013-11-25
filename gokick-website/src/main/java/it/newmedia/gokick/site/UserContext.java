package it.newmedia.gokick.site;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.*;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.LanguageManager;
import it.newmedia.gokick.site.managers.MatchCommentManager;
import it.newmedia.gokick.site.managers.SquadManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.security.encryption.MD5;
import it.newmedia.web.WebUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * E' l'oggetto che rappresenta il contesto dell'utente all'interno della propria sessione di lavoro
 */
public class UserContext
{

  //<editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static final String KEY_ON_SESSION__USER_CONTEXT = "KEY_ON_SESSION__USER_CONTEXT";
  /**
   *
   */
  protected static final Logger logger = Logger.getLogger(UserContext.class);
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members - Getters/Setters --">
  private User user;

  private Language language;

  private boolean loggedIn;

  private Squad firstSquad;

  private HashMap<Integer , Date> lastViewComment;

  private int idFirstSquad;

  private String goToUrl;

  private String lastMessage;

  private String invitationCode;

  private String facebookIdUserTemporary;
  
  private String temporaryAccessTokenFacebook;

  /**
   * E' l'oggetto che corrisponde all'entità USERS nel database.
   * Nel caso di utente non autenticato è una nuova istanza che quindi non ha corrispondenza nel database
   * @return L'utente corrente
   */
  public User getUser()
  {
    return user;
  }

  /**
   * Indica la lingua corrente in cui visualizzare il sito
   * @return La lingua corrente
   */
  public Language getLanguage()
  {
    String currentLanguage = ActionContext.getContext().getSession().get(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE).toString();
    if (language == null || !StringUtils.equals(this.language.getLanguage(), currentLanguage))
    {
      this.language = LanguageManager.getByLanguage(currentLanguage);
      WebUtil.setCookieSession(ServletActionContext.getResponse(), Constants.COOKIE_EXTAPP_USER_LANG, this.language.getLanguage());
    }
    return this.language;
  }

  /**
   * Indica se l'utente ha effettuato il login all'apllicazione
   * @return True se l'utente si è loggato correttamente
   */
  public boolean isLoggedIn()
  {
    return this.loggedIn && this.getUser().getId() != null;
  }

  /**
   * Indica se l'utente è organizzatore
   * @return True se l'utente si è organizzatore
   */
  public boolean isOrganizer()
  {
    return this.loggedIn && this.getUser().getOrganizeEnabled();
  }

  /**
   * Indica lo stato corrispondente ad un utente che si è registrato am ancora non ha attivato la
   * sua registrazione cliccando sull link dell'email che ha ricevuto
   * @return True se lo stato dell'utente è PENDING
   */
  public boolean isPending()
  {
    return this.user.getEnumUserStatus().equals(EnumUserStatus.Pending);
  }

  /**
   * Restituisce la prima rosa di un utente
   * @return La prima rosa di un utente
   */
  public Squad getFirstSquad()
  {
    if (this.firstSquad == null && this.idFirstSquad == 0)
    {
      Squad squad = SquadManager.getUserFirstSquad(user.getId());
      this.firstSquad = squad;
      if (squad != null)
      {
        this.idFirstSquad = squad.getId();
      }
      else
      {
        this.idFirstSquad = -1;
      }
    }

    return this.firstSquad;
  }

  /**
   * @return the lastViewComment
   */
  public HashMap<Integer , Date> getLastViewComment()
  {
    if (this.lastViewComment==null)
    {
      List<UserLastViewComment> lastViewCommentList = MatchCommentManager.getUserLastViewComments(user.getId());
      lastViewComment=new HashMap<Integer, Date>();
      for(UserLastViewComment userLastViewComment : lastViewCommentList)
      {
        lastViewComment.put(userLastViewComment.getIdMatch(), userLastViewComment.getLastView());
      }
    }
    return this.lastViewComment;
  }

  /**
   * Restituisce l'url (che prevedeva l'autenticazione)
   * al quale un utente non loggato ha tentato di accedere
   * @return L'url ha cui l'utente ha tentato di accedere
   */
  public String getGoToUrl()
  {
    return goToUrl;
  }

  /**
   * Imposta l'url al quale un utente non loggato ha cercato di accedere
   * @param goToUrl
   */
  public void setGoToUrl(String goToUrl)
  {
    this.goToUrl = goToUrl;
  }

  /**
   * Indica se è presente un url al quale un
   * utente non loggato ha tentato di accedere
   * @return true se esite un url, altrimenti false
   */
  public boolean isWithGoToUrl()
  {
    return !StringUtils.isEmpty(this.goToUrl);
  }

  /**
   *
   * @return
   */
  public int getIdFirstSquad()
  {
    return idFirstSquad;
  }

  /**
   *
   * @param idFirstSquad
   */
  public void setIdFirstSquad(int idFirstSquad)
  {
    this.idFirstSquad = idFirstSquad;
  }

  /**
   *
   * @param lastMessage
   */
  public void setLastMessage(String lastMessage)
  {
    this.lastMessage = lastMessage;
  }

  /**
   *
   * @return
   */
  public String getLastMessage()
  {
    return lastMessage;
  }

  /**
   *
   */
  public void removeLastMessage()
  {
    this.lastMessage = "";
  }
  /**
   *
   * @return
   */
  public String getInvitationCode()
  {
    return invitationCode;
  }

  /**
   *
   * @param invitationCode
   */
  public void setInvitationCode(String invitationCode)
  {
    this.invitationCode = invitationCode;
  }

  public String getFacebookIdUserTemporary()
  {
    return facebookIdUserTemporary;
  }

  public void setFacebookIdUserTemporary(String facebookIdUser)
  {
    this.facebookIdUserTemporary = facebookIdUser;
  }

  public String getTemporaryAccessTokenFacebook()
  {
    return temporaryAccessTokenFacebook;
  }

  public void setTemporaryAccessTokenFacebook(String temporaryAccessTokenFacebook)
  {
    this.temporaryAccessTokenFacebook = temporaryAccessTokenFacebook;
  }

  /**
   * Restituisce il cobrand corrent sul quale l'utente sta navigando!
   * NON il cobrand dell'utente
   * @return il cobrand riferito all'url che si sta navigando
   */
  public Cobrand getCurrentCobrand()
  {
    return (Cobrand)ServletActionContext.getRequest().getAttribute("currentCobrand");
  }

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   * Crea una nuova istanza di UserContext inizializzando i suoi membri
   */
  private UserContext()
  {
    reset();
  }
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  
  /**
   *
   * @return lo userInfo dell'utente in sessione
   */
  public UserInfo getUserInfo()
  {
    return InfoProvider.getUserInfo(this.user.getId());
  }
  
  
  /**
   *
   * @return Usercontext in sessione, se non c'è lo crea e memorizza
   */
  public static UserContext getInstance()
  {
    Map session = ActionContext.getContext().getSession();
    UserContext userContext = (UserContext) session.get(KEY_ON_SESSION__USER_CONTEXT);
    if (userContext == null)
    {
      userContext = new UserContext();
      session.put(KEY_ON_SESSION__USER_CONTEXT, userContext);
    }
    return userContext;
  }

  /**
   *
   * @return "true" se ci sono messaggi (dell'applicazione) da visualizzare (es:"hai organizzato... , ti sei registrato come...)
   */
  public boolean isWithLastMessage()
  {
    return !StringUtils.isEmpty(this.lastMessage);
  }


  /**
   * Resetta il contesto corrente dell'utente.
   * La situazione è identica ad un nuovo visitatore che accede al sito per la prima volta
   */
  public void reset()
  {
    HttpServletResponse response = ServletActionContext.getResponse();

    this.user = new User();
    this.loggedIn = false;
    this.firstSquad = null;
    this.lastViewComment = null;
    this.idFirstSquad = 0;
    //L'id di facebook non deve essere resettato
    //this.facebookIdUserTemporary = "";
    this.lastMessage = "";
    this.invitationCode = "";
    this.forgetMe(response);
    this.forgetExtApp(response);
  }

  /**
   * Resetta la rosa attuale dell'utente.
   */
  public void resetFirstSquad()
  {
    this.firstSquad = null;
    this.idFirstSquad = 0;
  }

  /**
   * Resetta eventuali messaggi dell'applicazione (es:"hai organizzato... , ti sei registrato come...)
   */
  public void resetLastViewComment()
  {
    this.lastViewComment = null;
  }

  /**
   * Resetta l'url acui l'utente ha cercato
   * di accedere mentre non era loggato
   */
  public void resetGoToUrl()
  {
    this.goToUrl = "";
  }

  /**
   * Inserisce i dati nel contesto corrente dell'utente
   * In questo modo può accedere anche alle area che richiedono autenticazione
   * @param user Sono i dati dell'utente che si è loggato
   */
  public void login(User user)
  {
    this.user = user;
    this.loggedIn = true;
  }

  /**
   * ricarica lo User indicato nel contesto corrente dell'utente.
   * @param id
   */
  public void reload(int id)
  {
    try
    {
      User userToreload = DAOFactory.getInstance().getUserDAO().get(id);
      this.user = userToreload;
      if (!UserContext.getInstance().isPending())
        this.loggedIn = true;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
    }
  }

  /**
   * Mette nei cookie i valori necessari per la login
   * @param response HttpServletResponse corrente
   * @param id Id dell'utente
   * @param password La password dell'utente
   */
  public void rememberMe(HttpServletResponse response, int id, String password)
  {
    Cookie c;
    c = new Cookie(Constants.COOKIE_USER_ID, String.valueOf(id));
    c.setMaxAge(60 * 60 * 24 * 15);
    response.addCookie(c);
    c = new Cookie(Constants.COOKIE_USER_PASSWORD, MD5.getHashString(password));
    c.setMaxAge(60 * 60 * 24 * 15);
    response.addCookie(c);
  }

  /**
   * Toglie dai cookie i valori necessari per la login
   * @param response HttpServletResponse corrente
   */
  public void forgetMe(HttpServletResponse response)
  {
    Cookie c;
    c = new Cookie(Constants.COOKIE_USER_ID, "");
    c.setMaxAge(0);
    response.addCookie(c);
    c = new Cookie(Constants.COOKIE_USER_PASSWORD, "");
    c.setMaxAge(0);
    response.addCookie(c);
  }

  /**
   * Mette nei cookie i valori necessari per la login
   * @param response HttpServletResponse corrente
   * @param id Id dell'utente
   * @param password La password dell'utente
   * @param language
   */
  public void rememberExtApp(HttpServletResponse response, int id, String password, String language)
  {
    WebUtil.setCookieSession(response, Constants.COOKIE_EXTAPP_USER_ID, String.valueOf(id));
    String passwordMD5 = MD5.getHashString(MD5.getHashString(MD5.getHashString(password)));
    WebUtil.setCookieSession(response, Constants.COOKIE_EXTAPP_USER_KEY, passwordMD5);
    WebUtil.setCookieSession(response, Constants.COOKIE_EXTAPP_USER_LANG, language);
  }

  /**
   * Toglie dai cookie i valori necessari per la login
   * @param response HttpServletResponse corrente
   */
  public void forgetExtApp(HttpServletResponse response)
  {
    WebUtil.removeCookie(response, Constants.COOKIE_EXTAPP_USER_ID);
    WebUtil.removeCookie(response, Constants.COOKIE_EXTAPP_USER_KEY);
    WebUtil.removeCookie(response, Constants.COOKIE_EXTAPP_USER_LANG);
  }

  
  // </editor-fold>
}
