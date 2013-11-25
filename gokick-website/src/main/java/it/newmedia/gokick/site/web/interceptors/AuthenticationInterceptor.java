package it.newmedia.gokick.site.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.web.actions.AuthenticationBaseAction;
import it.newmedia.web.WebUtil;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

/**
 *
 * Classe per la gestione dell'autenticazione dell'utente.
 */
public class AuthenticationInterceptor extends AbstractInterceptor
{

  public static final Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

  @Override
  public String intercept(ActionInvocation actionInvocation) throws Exception
  {
    try
    {
      HttpServletRequest request = (HttpServletRequest) actionInvocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
      Object action = actionInvocation.getAction();
      
      if (action instanceof AuthenticationBaseAction
              && BooleanUtils.toBoolean(((AuthenticationBaseAction) action).getAuthenticationRequired())
              && UserContext.getInstance().isLoggedIn() == false)
      {

        UserContext.getInstance().setGoToUrl(WebUtil.getRequestUrl(request));
        //logger.debug("La pagina richiede l'autenticazione e l'utente non è loggato [" + actionInvocation.getAction().toString() + "]");
        return ActionSupport.LOGIN;
      }
      //logger.debug("L'utente è loggato o la pagina non richiede l'autenticazione [" + actionInvocation.getAction().toString() + "]");
      return actionInvocation.invoke();
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return ActionSupport.ERROR;
    }
    finally
    {
    }
  }

}
