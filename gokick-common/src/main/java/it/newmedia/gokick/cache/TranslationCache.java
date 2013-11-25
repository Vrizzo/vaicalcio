package it.newmedia.gokick.cache;

import it.newmedia.gokick.data.hibernate.beans.Translation;
import java.io.Serializable;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce la cache per le traduzioni,associando una chiave (key) all'oggetto da memorizzare
 */
public class TranslationCache
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">

  private static final Logger logger = Logger.getLogger(TranslationCache.class);
  private static final String CACHE_NAME = "it.newmedia.gokick.cache.TranslationCache";
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

  private TranslationCache()
  {
  }

  // </editor-fold>

  public static void putInCache(String key, Translation message)
  {
    Element element;
    if (cache != null && message != null)
    {
      element = new Element((Serializable) key, (Serializable) message);
      cache.put(element);
    }
  }

  public static Translation getFromCache(String key) throws CacheException
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
        return (Translation) element.getValue();
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
