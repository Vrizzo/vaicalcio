package it.newmedia.gokick.cache;

import java.io.Serializable;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce la cache delle statistiche dei giocatori,associando una chiave (key) all'oggetto da memorizzare
 */
public class StatisticCache
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static final Logger logger = Logger.getLogger(StatisticCache.class);
  private static final String CACHE_NAME = "it.newmedia.gokick.cache.StatisticCache";
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

  private StatisticCache()
  {
  }

  // </editor-fold>
  
  public static void putInCache(String key, Object object)
  {
    Element element;

    if (cache != null && object != null)
    {
      element = new Element((Serializable) key, (Serializable) object);
      cache.put(element);
    }
  }

  public static Object getFromCache(String key) throws CacheException
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
        return element.getValue();
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

  public static void removeAll()
  {
    if (cache != null)
    {
      cache.removeAll();
    }
  }
}
