package it.newmedia.gokick.backOffice.providers;

import it.newmedia.gokick.cache.SiteConfigurationCache;
import it.newmedia.gokick.data.hibernate.beans.SiteConfiguration;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce le configurazioni dell'applicazione.
 */
public class SiteConfigurationProvider
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  public final static String CONFIGURATION_KEY__SITE_URL = "configuration.siteUrl";
  public final static String CONFIGURATION_KEY__PWD_MAIL = "configuration.pwdBackOfficeMail";
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static final Logger logger = Logger.getLogger(SiteConfigurationProvider.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  /**
   * Creates a new instance of SiteConfigurationProvider
   */
  public SiteConfigurationProvider()
  {
  }

  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   * Return a site configuration for passed keyConf.
   *
   * @param keyConf Key to get site configuration
   * @return A site configuration with info about
   * site configuration not found
   */
  public static SiteConfiguration getSiteConfiguration(String keyConf)
  {
    try
    {
      return DAOFactory.getInstance().getSiteConfigurationDAO().get(keyConf);
    }
    catch (Exception ex)
    {
      logger.error("Error retrieving site configuration", ex);
      return null;
    }
  }

  // </editor-fold>
}
