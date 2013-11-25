package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.backOffice.AppContext;
import it.newmedia.net.HttpConnection;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni fatte sulle rose.
 */
public class CacheManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(CacheManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private CacheManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
 public static void removeWebSiteCacheInfo()
 {
    try
    {
      HttpConnection httpConn = new HttpConnection(AppContext.getInstance().getCancelInfoCacheUrl());
      httpConn.doPost("");
      if (httpConn.getResponseCode() != HttpConnection.HTTP_RESPONSECODE_200_OK)
      {
        logger.error("error deleting infoCahe");
      }
    }
    catch (IOException ex)
    {
      logger.error("error deleting infoCahe",ex );
    }
  }

 public static void removeWebSiteUserInfo(int idUser)
 {
    try
    {
      String url = AppContext.getInstance().getCancelUserInfoUrl();
      url= url.replace("###id###", Integer.toString(idUser));
      HttpConnection httpConn = new HttpConnection(url);
      httpConn.doPost("");
      if (httpConn.getResponseCode() != HttpConnection.HTTP_RESPONSECODE_200_OK)
      {
        logger.error("error deleting userInfoCahe");
      }
    }
    catch (IOException ex)
    {
      logger.error("error deleting userInfoCahe",ex );
    }
  }

 public static boolean removeWebSiteSportCenterInfo(int idSportCenter)
 {
    try
    {
      String url = AppContext.getInstance().getCancelSportCenterInfoUrl();
      url= url.replace("###id###", Integer.toString(idSportCenter));
      HttpConnection httpConn = new HttpConnection(url);
      httpConn.doPost("");
      if (httpConn.getResponseCode() != HttpConnection.HTTP_RESPONSECODE_200_OK)
      {
        logger.error("error deleting userInfoCahe");
        return false;
      }
    }
    catch (IOException ex)
    {
      logger.error("error deleting userInfoCahe",ex );
      return false;
    }
    return true;
  }

 public static boolean removeAllWebSiteCache()
 {
    try
    {
        HttpConnection httpConn = new HttpConnection(AppContext.getInstance().getCancelCacheUrl());
        httpConn.doPost("");
        if (httpConn.getResponseCode() != HttpConnection.HTTP_RESPONSECODE_200_OK)
        {
            logger.error("error deleting AllCache , code: " + httpConn.getResponseCode() );
            return false;
        }
        return true;
    }
    catch (IOException ex)
    {
        logger.error("error deleting AllCache", ex );
        return false;
    }
  }
  // </editor-fold>
}
