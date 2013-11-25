package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.SiteConfiguration;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione delle configurazioni dell'applicazione. Contiene tutti i metodi che lavorano sulla tabella SITE_CONFIGURATIONS.
 */
public class SiteConfigurationDAO extends AGenericDAO<SiteConfiguration, Integer>
{

  /**
   *
   * @param session
   */
  public SiteConfigurationDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param keyConf chiave configurazione cercata
   * @return configurazione dell'applizazione relativa alla chiave indicata
   * @throws Exception
   */
  public SiteConfiguration get(String keyConf) throws Exception
  {
    return findEntityByCriteria(
            Restrictions.eq("keyConf", keyConf));
  }

}
