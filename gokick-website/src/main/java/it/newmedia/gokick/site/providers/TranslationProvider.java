package it.newmedia.gokick.site.providers;

import it.newmedia.gokick.cache.TranslationCache;
import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Translation;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce le traduzioni delle chiavi contenute all'interno dell'applicazione.
 */
public class TranslationProvider
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static final String KEY_ON_CACHE_TEMPLATE = "%s_%s";

  private static final String TRANSLATION_NOT_FOUND = "!!KEY %s NOT FOUND!!";
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">

  private static final Logger logger = Logger.getLogger(TranslationProvider.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  /**
   * Creates a new instance of TranslationProvider
   */
  public TranslationProvider()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  /**
   * Return a translation for passed key.
   * This method check if replacement is necessary and, in this case, do it!
   *
   * @param key Key to get translations
   * @param language Lingua per la traduzione da recuperare
   * @return A translated string or message with info about
   * key not found
   */
  public static Translation getTranslation(String key, Language language, Cobrand currentCobrand)
  {
    return getTranslation(key, language, -1, currentCobrand);
  }


  /**
   * Return a translation for passed key.
   * This method check if replacement is necessary and, in this case, do it!
   *
   * @param key Key to get translations
   * @param language Lingua per la traduzione da recuperare
   * @param idUser Id dell'utente per il quale fare l'autoreplacement
   * @return A translated string or message with info about
   * key not found
   */
  public static Translation getTranslation(String key, Language language, int idUser, Cobrand currentCobrand)
  {
    //build key
    String keyOnCache = String.format(KEY_ON_CACHE_TEMPLATE, key, language.getLanguage());
    //Check for data in cache...
    Translation translation = TranslationCache.getFromCache(keyOnCache);

    if (translation == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        translation = DAOFactory.getInstance().getTranslationDAO().get(key, language);
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving translations [" + key + "/" + language.getLanguage() + "]", ex);
      }
      if (translation == null)
      {
        translation = new Translation();
        translation.setKeyValue(String.format(TRANSLATION_NOT_FOUND, keyOnCache)); //Build an empty object and put to cache
        logger.warn(String.format("Message key/idLanguage [%s/%s] not found on db", key, language.getLanguage()));
      }

      //put on cache
      TranslationCache.putInCache(keyOnCache, translation);
    }
    if (translation.isReplacementEnabled())
    {
      translation = translation.copy();
      String translationValue = TranslationProvider.doAutoReplacement(translation.getKeyValue(), idUser, currentCobrand);
      translation.setKeyValue(translationValue);
    }

    return translation;
  }

  /**
   * Restituisce la traduzione per la chiave e la lingua passata
   * @param key Chiave da tradurre
   * @param language Lingua in cui tradurre
   * @return La stringa contenente la traduzione
   */
  public static String getTranslationValue(String key, Language language, Cobrand currentCobrand)
  {
    return getTranslation(key, language, currentCobrand).getKeyValue();
  }

  /**
   * Effettua il replace di alcuni tag predefiniti con i dati relativi all'utente passato
   * @param translation Stringa su cui effettuare il replace
   * @param idUser Id dell'utente con i quali dati effettuare il replace
   * @return La stringa passata con effettuati i replace, se effettuati
   */
  private static String doAutoReplacement(String translation, int idUser, Cobrand currentCobrand)
  {
    if (StringUtils.isBlank(translation))
    {
      return translation;
    }
    UserInfo userInfo = InfoProvider.getUserInfo(idUser);
    if (userInfo.isValid())
    {
      translation = StringUtils.replace(translation, Constants.AUTOREPLACEMENT__USER_FIRSTNAME, userInfo.getName());
      translation = StringUtils.replace(translation, Constants.AUTOREPLACEMENT__USER_LASTNAME, userInfo.getSurname());
      translation = StringUtils.replace(translation, Constants.AUTOREPLACEMENT__USER_COMPLETENAME, userInfo.getFirstLastName());
    }
    translation = StringUtils.replace(translation, Constants.AUTOREPLACEMENT__SITE_URL, currentCobrand.getGatewayUrl());
    return translation;
  }
  // </editor-fold>
}
