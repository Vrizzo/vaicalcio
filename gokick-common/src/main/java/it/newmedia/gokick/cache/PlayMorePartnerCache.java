package it.newmedia.gokick.cache;

import it.newmedia.gokick.data.hibernate.beans.VPlayMorePartner;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 *
 * Classe che gestisce la cache per le allo stato dell'utente rispetto all'associazione PlayMore.
 */
public class PlayMorePartnerCache
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">

  private static final Logger logger = Logger.getLogger(PlayMorePartnerCache.class);
  private static final String CACHE_NAME = "it.newmedia.gokick.cache.PlayMorePartnerCache";
  private static Cache cache;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">

  static
  {
    try
    {
      cache = CacheManager.getInstance().getCache(CACHE_NAME);
    } catch (Exception exception)
    {
      logger.error(exception.toString(), exception);
    }
  }

  private PlayMorePartnerCache()
  {
  }

  // </editor-fold>

  public static void putInCache(String key, VPlayMorePartner vPlayMorePartner)
  {
    Element element;
    if (cache != null && vPlayMorePartner != null)
    {
      element = new Element((Serializable) key, (Serializable) vPlayMorePartner);
      cache.put(element);
    }
  }

  public static VPlayMorePartner getFromCache(String key) throws CacheException
  {
    Element element;

    if (cache != null)
    {
      try
      {
        element = cache.get((Serializable) key);
      } catch (Exception ex)
      {
        logger.error(ex.getMessage(), ex);
        return null;
      }
      if (element != null)
      {
        return (VPlayMorePartner) element.getValue();
      }
    }
    return null;
  }

  public static void removeFromCache(String key)
  {
    if (cache != null)
    {
      cache.remove(key);
    }
  }
}
