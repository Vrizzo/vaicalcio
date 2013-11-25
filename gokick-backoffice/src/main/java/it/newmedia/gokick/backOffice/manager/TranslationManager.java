package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Translation;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce le traduzioni delle chiavi contenute all'interno dell'applicazione.
 */
public class TranslationManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static final String TRANSLATION_NOT_FOUND = "!!KEY %s NOT FOUND!!";
  private static final String TRANSLATION_LANGUAGE = "it";
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">

  private static final Logger logger = Logger.getLogger(TranslationManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  /**
   * Creates a new instance of TranslationManager
   */
  public TranslationManager()
  {
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  /**
   * Return a translation for passed key.
   * This method check if replacement is necessary and, in this case, do it!
   *
   * @param key Key to get translations
   * @param language Lingua per la traduzione da recuperare:ITALIANO
   * @return A translated string or message with info about
   * key not found
   */
  public static Translation getTranslation(String key)
  {
    Translation translation = null;
    {
      // Get it from db
      try
      {
        translation = DAOFactory.getInstance().getTranslationDAO().get(key, TRANSLATION_LANGUAGE);
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving translations [" + key + "/" + TRANSLATION_LANGUAGE + "]", ex);
      }
      if (translation == null)
      {
        translation = new Translation();
        translation.setKeyValue(String.format(TRANSLATION_NOT_FOUND, key)); //Build an empty object and put to cache
        logger.warn(String.format("Message key/idLanguage [%s/%s] not found on db", key, TRANSLATION_LANGUAGE));
      }   
    }
    return translation;
  }

  /**
   * Restituisce la traduzione per la chiave e la lingua passata
   * @param key Chiave da tradurre
   * @param language Lingua in cui tradurre
   * @return La stringa contenente la traduzione
   */
  public static String getTranslationValue(String key, Language language)
  {
    return getTranslation(key).getKeyValue();
  }

 
  // </editor-fold>
}
