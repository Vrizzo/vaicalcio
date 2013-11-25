package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Translation;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione delle città. Contiene tutti i metodi che lavorano sulla tabella CITIES.
 */
public class TranslationDAO extends AGenericDAO<Translation, Integer>
{

  /**
   *
   * @param session
   */
  public TranslationDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param keyName chiave
   * @param language lingua 
   * @return traduzione per la chiave e lingua specificata
   * @throws Exception
   */
  public Translation get(String keyName, Language language) throws Exception
  {
    return findEntityByCriteria(
            Restrictions.eq("keyName", keyName),
            Restrictions.eq("language", language.getLanguage()));
  }

  /**
   *
   * @param keyName chiave
   * @param language lingua
   * @return traduzione per la chiave e lingua specificata
   * @throws Exception
   */
  public Translation get(String keyName, String language) throws Exception
  {
    return findEntityByCriteria(
            Restrictions.eq("keyName", keyName),
            Restrictions.eq("language", language));
  }
}
