package it.newmedia.gokick.site.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import org.apache.log4j.Logger;

/**
 * Struts 2 interceptor.
 * Si occupa di creare e rendere disponibile una sessione di hibernate
 * per tutta la durata di una richiesta http
 * 
 */
public class HibernateSessionInterceptor extends AbstractInterceptor
{

  public static final Logger logger = Logger.getLogger(HibernateSessionInterceptor.class);

  @Override
  public String intercept(ActionInvocation actionInvocation) throws Exception
  {
    try
    {
      try
      {
        HibernateSessionHolder.sessionPerRequestBegin();
        //logger.debug("La sessione di hibernate è aperta [" + actionInvocation.getAction().toString() + "]");
      }
      catch (Exception e)
      {
        logger.fatal("Impossibile aprire la sessione di hibernate [" + actionInvocation.getAction().toString() + "]", e);
      }
      String result = actionInvocation.invoke();

      HibernateSessionHolder.sessionPerRequestCommit();
      //logger.debug("La sessione di hibernate è chiusa [" + actionInvocation.getAction().toString() + "]");

      return result;
    }
    catch (Exception e)
    {
      HibernateSessionHolder.sessionPerRequestRollback();
      e.printStackTrace();
      return ActionSupport.ERROR;
    }
  }

}
