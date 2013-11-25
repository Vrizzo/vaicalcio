package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.enums.EnumEmailConfigurationType;
import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.EmailConfiguration;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * DAO per la gestione delle email. Contiene tutti i metodi che lavorano sulla tabella EMAIL_CONFIGURATIONS.
 */
public class EmailConfigurationDAO extends AGenericDAO < EmailConfiguration, Integer >
{

  /**
   *
   * @param session
   */
  public EmailConfigurationDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param emailConfigurationType configurazione d'interesse
   * @return oggetto EmailConfiguration, che definisce la configurazione email da utilizzare
   * @throws Exception
   */
  public EmailConfiguration get(EnumEmailConfigurationType emailConfigurationType) throws Exception
  {                     
    String sql = " FROM EmailConfiguration AS e WHERE e.emailConfigurationType = :emailConfigurationType AND e.enabled = :enabled ";
    Query query = getSession().createQuery(sql.toString());
    query.setParameter("emailConfigurationType", emailConfigurationType.getValue());
    query.setParameter("enabled", true);
    query.setMaxResults(1);
    return  (EmailConfiguration)query.uniqueResult();
  }
  
}
