package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Classe manager che gestisce ad alto livello le azioni relative ai linguaggi utilizzati nel sito
 * @author ggeroldi
 */
public class LanguageManager
{
  //<editor-fold defaultstate="collapsed" desc="CONSTANTS">
  private static final String DEFAULT_LANGUAGE = "en";
  private static final String GERMAN_LANGUAGE = "de";
  private static final String ITALIAN_LANGUAGE = "it";
  private static final String ENGLISH_LANGUAGE = "en";
  private static final String SPANISH_LANGUAGE = "es";
  private static final String FRENCH_LANGUAGE = "fr";
  private static final String PORTUGUESE_LANGUAGE = "pt";
  private static final String GERMANY = "Germany";
  private static final String ITALY = "Italy";
  private static final String AUSTRIA = "Austria";
  private static final String ENGLAND = "United Kingdom";
  private static final String USA = "United States";
  private static final String FRANCE = "France";
  private static final String SPAIN = "Spain";
  private static final String PORTUGAL = "Portugal";
  private static final String SENEGAL = "Senegal";
  private static final String TUNISIA = "Tunisia";
  private static final String CHILE = "Chile";
  private static final String ARGENTINA = "Argentina";
  private static final String VENEZUELA = "Venezuela";
  private static final String ECUADOR = "Ecuador";
  private static final String BOLIVIA = "Bolivia";
  private static final String PARAGUAY = "Paraguay";
  private static final String URUGUAY = "Uruguay";
  private static final String PERU = "Peru";
  private static final String COLOMBIA = "Colombia";
  private static final String MEXICO = "Mexico";
  //</editor-fold>
  
  //<editor-fold defaultstate="collapsed" desc="-- Members --">

  private static Logger logger = Logger.getLogger(LanguageManager.class);
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Constructors --">
  // </editor-fold>
  
  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
//  /**
//   * Restituisce l'oggetto language in funzione della stringa passata.
//   * Attenzione! Se non trova la lingua restituisce l'italiano come default che
//   * DEVE sempre esistere!!
//   * @param localeValue Stringa relativa al linguaggio "it", "en"....
//   * @return L'oggetto richiesto
//   */
//  public static Language getByLocale(String localeValue)
//  {
//    try
//    {
//      Language language = DAOFactory.getInstance().getLanguageDAO().getByLanguage(localeValue);
//      if (language == null || !language.isEnabled())
//      {
//        //recupero la lingua italiana come default
//        language = DAOFactory.getInstance().getLanguageDAO().getByLanguage(Constants.LANGUAGE_IT);
//      }
//      if (language == null)
//      {
//        //Questa situazione è "impossibile". nel db deve esistere la lingua italiana
//        logger.error("Db non contain [it] language!!. Add it to use site");
//      }
//      return language;
//    }
//    catch (Exception e)
//    {
//      logger.error("Error retrieving a valid language object for current session!!!");
//    }
//    return null;
//  }

  /**
   * Restituisce l'oggetto language in funzione della stringa passata.
   * Attenzione! Se non trova la lingua restituisce l'italiano come default che
   * DEVE sempre esistere!!
   * @param languageValue Stringa relativa al linguaggio "it", "en"....
   * @return L'oggetto richiesto
   */
  public static Language getByLanguage(String languageValue)
  {
    try
    {
      Language language = DAOFactory.getInstance().getLanguageDAO().getByLanguage(languageValue);
      if (language == null || !language.isEnabled())
      {
        //recupero la lingua italiana come default
        language = DAOFactory.getInstance().getLanguageDAO().getByLanguage(Constants.LANGUAGE_IT);
      }
      if (language == null)
      {
        //Questa situazione è "impossibile". nel db deve esistere la lingua italiana
        logger.error("Db non contain [it] language!!. Add it to use site");
      }
      return language;
    }
    catch (Exception ex)
    {
      logger.error("Error retrieving language for value " + languageValue + ". application cannot continue!", ex);
      return null;
    }
  }

  /**
   *
   * @return lista di linguaggi ENABLED
   */
  public static List<Language> getAllEnabled()
  {
    try
    {
      return DAOFactory.getInstance().getLanguageDAO().getAllEnabled();
    }
    catch (Exception ex)
    {
      logger.error("Error retrieving languages", ex);
      return new ArrayList<Language>();
    }
  }
  
  /**
   * restituisce la lingua basandosi sul id country passato
   * @param idCountry 
   * @return language
   */
  public  static Language chooseLanguage(int idCountry)
  {
    
    Country country = CountryManager.getCountry(idCountry);
    String langCountry = country.getName();
    Language language;

    
    if (langCountry.equals(ITALY))
    {
      language = LanguageManager.getByLanguage(ITALIAN_LANGUAGE);
    }
    else if (langCountry.equals(GERMANY) || langCountry.equals(AUSTRIA))
    {
      language = LanguageManager.getByLanguage(GERMAN_LANGUAGE);
    }
    else if (langCountry.equals(ENGLAND) || langCountry.equals(USA))
    {
      language = LanguageManager.getByLanguage(ENGLISH_LANGUAGE);
    }
    else if (langCountry.equals(FRANCE) || langCountry.equals(SENEGAL) || langCountry.equals(TUNISIA))
    {
      language = LanguageManager.getByLanguage(FRENCH_LANGUAGE);
    }
    else if (langCountry.equals(SPAIN)
            || langCountry.equals(CHILE)
            || langCountry.equals(ARGENTINA)
            || langCountry.equals(VENEZUELA)
            || langCountry.equals(ECUADOR)
            || langCountry.equals(BOLIVIA)
            || langCountry.equals(URUGUAY)
            || langCountry.equals(PARAGUAY)
            || langCountry.equals(PERU)
            || langCountry.equals(COLOMBIA)
            || langCountry.equals(MEXICO))
    {
      language = LanguageManager.getByLanguage(SPANISH_LANGUAGE);
    }
    else if (langCountry.equals(PORTUGAL))
    {
      language = LanguageManager.getByLanguage(PORTUGUESE_LANGUAGE);
    }
    else
    {
      language = LanguageManager.getByLanguage(DEFAULT_LANGUAGE);
    }
    return language;
  }
  
  /**
   * restituisce la lingua basandosi sulla nazionalità dell'utente
   * @param user utente
   * @return language
   */
  public static Language chooseUserLanguage(User user)
  {
    Language language;

    String playingCountry = user.getCountry().getName();
    if (playingCountry.equals(ITALY))
    {
      language = LanguageManager.getByLanguage(ITALIAN_LANGUAGE);
    }
    else if (playingCountry.equals(GERMANY) || playingCountry.equals(AUSTRIA))
    {
      language = LanguageManager.getByLanguage(GERMAN_LANGUAGE);
    }
    else if (playingCountry.equals(ENGLAND) || playingCountry.equals(USA))
    {
      language = LanguageManager.getByLanguage(ENGLISH_LANGUAGE);
    }
    else if (playingCountry.equals(FRANCE) || playingCountry.equals(SENEGAL) || playingCountry.equals(TUNISIA))
    {
      language = LanguageManager.getByLanguage(FRENCH_LANGUAGE);
    }
    else if (playingCountry.equals(SPAIN)
            || playingCountry.equals(CHILE)
            || playingCountry.equals(ARGENTINA)
            || playingCountry.equals(VENEZUELA)
            || playingCountry.equals(ECUADOR)
            || playingCountry.equals(BOLIVIA)
            || playingCountry.equals(URUGUAY)
            || playingCountry.equals(PARAGUAY)
            || playingCountry.equals(PERU)
            || playingCountry.equals(COLOMBIA)
            || playingCountry.equals(MEXICO))
    {
      language = LanguageManager.getByLanguage(SPANISH_LANGUAGE);
    }
    else if (playingCountry.equals(PORTUGAL))
    {
      language = LanguageManager.getByLanguage(PORTUGUESE_LANGUAGE);
    }
    else
    {
      language = LanguageManager.getByLanguage(DEFAULT_LANGUAGE);
    }
    return language;
  }

  /**
   * restituisce la lingua basandosi sulla nazionalità dell'utente
   * @param country stringa contente il nome della country
   * @return language
   */
  public static Language chooseUserLanguage(String country)
  {
    Language language;

    String playingCountry = country;
    if (playingCountry.equals(ITALY))
    {
      language = LanguageManager.getByLanguage(ITALIAN_LANGUAGE);
    }
    else if (playingCountry.equals(GERMANY) || playingCountry.equals(AUSTRIA))
    {
      language = LanguageManager.getByLanguage(GERMAN_LANGUAGE);
    }
    else if (playingCountry.equals(ENGLAND) || playingCountry.equals(USA))
    {
      language = LanguageManager.getByLanguage(ENGLISH_LANGUAGE);
    }
    else if (playingCountry.equals(FRANCE) || playingCountry.equals(SENEGAL) || playingCountry.equals(TUNISIA))
    {
      language = LanguageManager.getByLanguage(FRENCH_LANGUAGE);
    }
    else if (playingCountry.equals(SPAIN)
            || playingCountry.equals(CHILE)
            || playingCountry.equals(ARGENTINA)
            || playingCountry.equals(VENEZUELA)
            || playingCountry.equals(ECUADOR)
            || playingCountry.equals(BOLIVIA)
            || playingCountry.equals(URUGUAY)
            || playingCountry.equals(PARAGUAY)
            || playingCountry.equals(PERU)
            || playingCountry.equals(COLOMBIA)
            || playingCountry.equals(MEXICO))
    {
      language = LanguageManager.getByLanguage(SPANISH_LANGUAGE);
    }
    else if (playingCountry.equals(PORTUGAL))
    {
      language = LanguageManager.getByLanguage(PORTUGUESE_LANGUAGE);
    }
    else
    {
      language = LanguageManager.getByLanguage(DEFAULT_LANGUAGE);
    }
    return language;
  }
     
  // </editor-fold>
  
  
  
  
  
}
