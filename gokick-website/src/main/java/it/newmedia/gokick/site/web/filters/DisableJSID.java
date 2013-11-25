/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.newmedia.gokick.site.web.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;


/**
 *
 * @author ggeroldi  //filtro per togliere JsessionId dalle url (non utilizzato)
 */
public class DisableJSID implements Filter{

 private static Logger logger = Logger.getLogger(DisableJSID.class);

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException{
      // Controllo per agire solamente su richieste di tipo http
      if (!(request instanceof HttpServletRequest)){
         chain.doFilter(request, response);
         return;
      }

      HttpServletRequest httpRequest = (HttpServletRequest) request;
      HttpServletResponse httpResponse = (HttpServletResponse) response;

      //Invalidiamo la sessione se viene trovato l'identificativo nella url
      if (httpRequest.isRequestedSessionIdFromURL()){
         logger.info("Trovato identificativo di sessione nella url");
         HttpSession session = httpRequest.getSession();
         if (session != null){
            session.invalidate();
            logger.info("sessione invalidata");
         }
      }

      // Wrapper di httpResponse per aggirare l'URL encoding
      HttpServletResponseWrapper wrappedResponse =
                                              new HttpServletResponseWrapper(httpResponse){
         @Override
         public String encodeRedirectUrl(String url) { return url; }

         @Override
         public String encodeRedirectURL(String url) { return url; }

         @Override
         public String encodeUrl(String url) { return url; }

         @Override
         public String encodeURL(String url) { return url; }
      };

      chain.doFilter(request, wrappedResponse);
   }

   public void init(FilterConfig config) throws ServletException {}

   public void destroy() {}

}
