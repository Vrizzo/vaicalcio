/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.newmedia.gokick.site.web.listeners;

import com.opensymphony.xwork2.interceptor.I18nInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * Web application lifecycle listener.
 * @author Stevs
 */
public class SessionListener implements HttpSessionListener
{

  private static final Logger logger = Logger.getLogger(SessionListener.class);

  public void sessionCreated(HttpSessionEvent se)
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    //Language language = LanguageManager.getByLanguage(request.getLocale().getLanguage());
    se.getSession().setAttribute(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, request.getLocale().getLanguage());
  }

  public void sessionDestroyed(HttpSessionEvent se)
  {
    //nothing to do...
  }
}
