package it.newmedia.gokick.site.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.results.Result;
import it.newmedia.security.encryption.MD5;
import it.newmedia.web.WebUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * Classe per la gestione della presenza o meno nei cookie dei dati per l'autenticazione.
 */
public class RememberMeInterceptor extends AbstractInterceptor
{

  public static final Logger logger = Logger.getLogger(RememberMeInterceptor.class);

  @Override
  public String intercept(ActionInvocation actionInvocation) throws Exception
  {

    if (UserContext.getInstance().isLoggedIn())
    {
      return actionInvocation.invoke();
    }

    HttpServletRequest request = ServletActionContext.getRequest();

    // <editor-fold defaultstate="collapsed" desc="AUTO LOGIN">

    int idUserAutoLog = request.getParameter(Constants.USER_ID_AUTOLOG)!=null?
                        Integer.parseInt(request.getParameter(Constants.USER_ID_AUTOLOG)): 0 ;
    String pwdAutoLog = request.getParameter(Constants.USER_PWD_AUTOLOG)!=null?
                        request.getParameter(Constants.USER_PWD_AUTOLOG): "" ;
    
    if(idUserAutoLog > 0 && StringUtils.isNotBlank(pwdAutoLog))
    {
        User userAutoLog;
        try
        {
          userAutoLog = DAOFactory.getInstance().getUserDAO().get(idUserAutoLog);

          if (!pwdAutoLog.equals(MD5.getHashString(userAutoLog.getPassword())))
          {
            return actionInvocation.invoke();
          }
        }
        catch( Exception ex )
        {
          return actionInvocation.invoke();
        }

        UserManager.login(userAutoLog.getEmail(), userAutoLog.getPassword(), true);
        return actionInvocation.invoke();
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="-- Get idUser from cookie --">
    Result<Cookie> rCookie = WebUtil.getCookie(request, Constants.COOKIE_USER_ID);
    if( !rCookie.isSuccessNotNull() )
      return actionInvocation.invoke();
    int idUser = 0;
    try
    {
      idUser = Integer.parseInt(rCookie.getValue().getValue());
    }
    catch( Exception ex )
    {
      return actionInvocation.invoke();
    }
    if( idUser <= 0 )
      return actionInvocation.invoke();

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Get password(md5) from cookie --">
    rCookie = WebUtil.getCookie(request, Constants.COOKIE_USER_PASSWORD);
    if (!rCookie.isSuccessNotNull())
    {
      return actionInvocation.invoke();
    }
    String md5Password = rCookie.getValue().getValue() != null ? rCookie.getValue().getValue() : "";
    if (md5Password.equals(""))
    {
      return actionInvocation.invoke();
    }
    // </editor-fold>

    User user;
    try
    {
      user = DAOFactory.getInstance().getUserDAO().get(idUser);

      if (!md5Password.equals(MD5.getHashString(user.getPassword())))
      {
        return actionInvocation.invoke();
      }
    }
    catch( Exception ex )
    {
      return actionInvocation.invoke();
    }

    UserManager.login(user.getEmail(), user.getPassword(), true);
   
    return actionInvocation.invoke();
  }
}