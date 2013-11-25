package it.newmedia.gokick.site.providers;

import it.newmedia.gokick.cache.SiteConfigurationCache;
import it.newmedia.gokick.data.hibernate.beans.SiteConfiguration;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.utils.Convert;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce le configurazioni dell'applicazione.
 */
public class SiteConfigurationProvider {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  /**
   *
   */
  public final static String CONFIGURATION_KEY__UPLOADED_IMAGE_WIDTH = "configuration.uploadedImageWidth";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__UPLOADED_IMAGE_HEIGHT = "configuration.uploadedImageHeight";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__GOOGLEMAPS_ADDRESS_URL = "configuration.goggleMapAddressUrl";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__GOOGLEMAPS_LATLON_URL = "configuration.goggleMapLatLonUrl";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__IMPORT_CONTACTS_URL = "configuration.import.contact.url";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__IMPORT_CONTACTS_STEP = "config.import.contact.step";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__IMPORT_CONTACTS_IMPORTER = "config.import.contact.importer";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__MASK_IMAGE_PATH = "configuration.maskImagePath";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_NAME_FONT_NAME = "configuration.playerNameFontName";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__INVITATIONS_INITIAL = "configuration.invitationsInitial";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__INVITATIONS_ENABLED = "configuration.invitationsEnabled";

  /**
   * Numero dimesi da mostrare nei risultati come intervaalo massimo
   */
  public final static String CONFIGURATION_KEY__MAX_MONTHS_OFFSET_FOR_RESULTS = "configuration.maxMonthsOffsetForResults";

  /**
   * Indica se devono essere usati i nuovi metodi che usano una nuova sessione per effettuare le operazioni
   * con i dao
   */
  public final static String CONFIGURATION_KEY__USE_NEWSESSION_METHODS = "configuration.useNewSessionMethods";

  /**
   * Numero di utenti massimi che deve essere riportato nei risultati
   */
  public static final String CONFIGURATION_KEY__MAX_USER_FOR_RESULTS = "configuration.maxUserForResults";

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(SiteConfigurationProvider.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  /**
   * 
   * @param keyConf chiave
   * @return un oggetto siteConfiguration per la chiave passata, se esiste
   * 
   */
  public static SiteConfiguration getSiteConfiguration(String keyConf)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE, keyConf);
    //Check for data in cache...
    SiteConfiguration siteConfiguration = SiteConfigurationCache.getFromCache(keyOnCache);

    if (siteConfiguration == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        siteConfiguration = DAOFactory.getInstance().getSiteConfigurationDAO().get(keyConf);
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving site configuration", ex);
      }
      if (siteConfiguration == null)
      {
        logger.warn(String.format("Message keyConf [%s] not found on db", keyConf));
      }
      //put on cache
      SiteConfigurationCache.putInCache(keyOnCache, siteConfiguration);
    }
    return siteConfiguration;
  }

  /**
   *
   * @param key chiave
   * @param defaultValue valore di default
   * @return il valore (numerico) della chiave passata, se non esiste restitutisce il valore di default inducato
   */
  public static int get(String key, int defaultValue)
  {
    SiteConfiguration siteConfiguration = getSiteConfiguration(key);
    return siteConfiguration != null ? Convert.parseInt(siteConfiguration.getValueConf(), defaultValue) : defaultValue;
  }

  /**
   *
   * @param key chiave
   * @param defaultValue valore di default
   * @return il valore (stringa) della chiave passata, se non esiste restitutisce il valore di default inducato
   */
  public static String get(String key, String defaultValue)
  {
    SiteConfiguration siteConfiguration = getSiteConfiguration(key);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : defaultValue;
  }

  /**
   *
   * @param key chiave
   * @param defaultValue valore di default
   * @return "true" se il valore della chiave passata è 1, "false" se è 0, valore di default negli altri casi
   */
  public static boolean get(String key, boolean defaultValue)
  {
    SiteConfiguration siteConfiguration = getSiteConfiguration(key);
    if (siteConfiguration == null)
    {
      return defaultValue;
    }
    else
    {
      String value = siteConfiguration.getValueConf();
      if (value.equals("1"))
      {
        return true;
      }
      else if (value.equals("0"))
      {
        return false;
      }
      else
      {
        logger.warn("SiteConciguration contiene un valore errato per [" + key + "] Il valore deve essere 1 o 0");
        return defaultValue;
      }
    }
  }
  // </editor-fold>
}
