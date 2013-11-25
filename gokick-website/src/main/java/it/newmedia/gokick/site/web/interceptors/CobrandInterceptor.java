package it.newmedia.gokick.site.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.web.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URL;

/**
 * Si occupa di capire e impostare il cobrnad corrente in funzione del
 * dominio dell'applicazione
 */
public class CobrandInterceptor extends AbstractInterceptor
{

  public static final Logger logger = Logger.getLogger(CobrandInterceptor.class);

  @Override
  public String intercept(ActionInvocation actionInvocation) throws Exception
  {
    try
    {
      HttpServletResponse response = (HttpServletResponse) actionInvocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
      HttpServletRequest request = (HttpServletRequest) actionInvocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);

      String currentDomain = URI.create(request.getRequestURL().toString()).getHost();
      Cobrand currentCobrand = InfoProvider.getCobrandByDomain(currentDomain);
      if( currentCobrand == null )
      {
        logger.error("No cobrand defined for domain: " + currentDomain);
        return Constants.STRUTS_RESULT_NAME__EXCEPTION;
      }

      //Imposto il parametro che sarà usato per restituire i contenuti
      // restituiti da http://www.gokick.org/blog/service (gestione gokick)
      setServletPath(request);

      String requestUrl = WebUtil.getRequestUrl(request);
      if(StringUtils.containsIgnoreCase(requestUrl, "/cobrand/"))
      {
        String goToUrl = StringUtils.replace(requestUrl, "/cobrand/", "/");
        UserContext.getInstance().setGoToUrl(goToUrl);
        String redirectTo = currentCobrand.getHomePageUrl();
        String refereDomain;
        try
        {
          refereDomain = URI.create(request.getHeader("referer")).getHost();
        }
        catch(Exception ex)
        {
          refereDomain = "";
        }
        if(StringUtils.equalsIgnoreCase(refereDomain, currentDomain))
        {
          redirectTo = currentCobrand.getSiteUrl();
        }
        response.sendRedirect(redirectTo);
        logger.debug(String.format("[%s] [referer: %s] redirect: %s, and after goToUrl: %s", currentCobrand.getCode(), refereDomain, redirectTo, goToUrl));
        return null;
      }
      request.setAttribute("currentCobrand",  currentCobrand);
      request.setAttribute("currentCobrandCode",  currentCobrand != null ? currentCobrand.getCode() : "NO_COBRAND");
      //logger.debug(String.format("[%s] [%s] %s", currentCobrand.getCode(), currentDomain, requestUrl));

      return actionInvocation.invoke();
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return Constants.STRUTS_RESULT_NAME__EXCEPTION;
    }
  }

  private void setServletPath(HttpServletRequest request)
  {
    String servletPath = request.getServletPath();
    servletPath = StringUtils.substringBefore(servletPath, ".");
    servletPath = StringUtils.substringBefore(servletPath, "!");
    servletPath = StringUtils.remove(servletPath, "/");
    request.setAttribute("currentServletPath",  servletPath);
  }

}
