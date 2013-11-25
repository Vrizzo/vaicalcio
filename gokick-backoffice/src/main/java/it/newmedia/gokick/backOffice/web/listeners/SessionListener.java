/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.newmedia.gokick.backOffice.web.listeners;

import com.opensymphony.xwork2.interceptor.I18nInterceptor;
import java.util.Locale;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

/**
 * Web application lifecycle listener.
 * @author Stevs
 */
public class SessionListener implements HttpSessionListener
{
  private static final Logger logger = Logger.getLogger(SessionListener.class);

  public void sessionCreated(HttpSessionEvent se)
  {
    se.getSession().setAttribute(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, Locale.ITALY);
  }

  public void sessionDestroyed(HttpSessionEvent se)
  {
    //nothing to do...
  }
}