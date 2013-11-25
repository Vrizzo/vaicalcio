package it.newmedia.gokick.site.providers;

import it.newmedia.gokick.cache.PlayMorePartnerCache;
import it.newmedia.gokick.data.hibernate.beans.VPlayMorePartner;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce le informazioni relative allo stato dell'utente rispetto all'associazione PlayMore.
 */
public class PlayMorePartnerProvider
{

  private static Logger logger = Logger.getLogger(PlayMorePartnerProvider.class);

  /**
   * 
   * @param idUser id dell'utente
   * @return l'oggetto con le info relative allo stato dell'utente rispetto
   * all'associazione play more
   * 
   */
  public static VPlayMorePartner get(Integer idUser)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE, idUser);
    //Check for data in cache...
    VPlayMorePartner vPlayMorePartner = PlayMorePartnerCache.getFromCache(keyOnCache);

    if (vPlayMorePartner == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        vPlayMorePartner = DAOFactory.getInstance().getVPlayMorePartnerDAO().get(idUser);
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving VPlayMorePartner", ex);
      }
      if (vPlayMorePartner == null)
      {
        vPlayMorePartner = new VPlayMorePartner();
      }
      //put on cache
      PlayMorePartnerCache.putInCache(keyOnCache, vPlayMorePartner);
    }
    return vPlayMorePartner;
  }
}
