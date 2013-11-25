package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.site.AppContext;
import it.newmedia.net.HttpConnection;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni fatte dall'utente.
 */
public class ImportContactManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(ImportContactManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public static String importContact(String importUrl, String email, String password, String provider, String step, String importer)
  {
    logger.debug("OpenInviter import url: " + importUrl);
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("email_box", email);
    parameters.put("password_box", password);
    parameters.put("provider_box", provider);
    parameters.put("step", step);
    parameters.put("import", importer);
    try
    {
      HttpConnection connection = new HttpConnection(importUrl);
      connection.setFollowRedirects(false);
      connection.doPost(parameters);
      if (connection.getResponseCode() == HttpConnection.HTTP_RESPONSECODE_200_OK)
      {
        return connection.getResponse();
      }
      logger.error("Wrong response from [" + importUrl + "] -> " + connection.getResponse());
      return "";
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      return "";
    }
  }
  // </editor-fold>

  public static void main(String[] args)
  {
    String importUrl = "";
    String email = "nms.devel@gmail.com";
    String password = "devel.nms";
    String provider = "gmail_fr";
    String step = "get_contacts";
    String importer = "Import Contacts";
    
            
    //System.out.println("Inizio");
    String result = ImportContactManager.importContact(importUrl, email, password, provider, step, importer);
    //System.out.println(result);
    //System.out.println("Fine");
  }
}
