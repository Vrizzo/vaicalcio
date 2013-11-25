package it.newmedia.gokick.backOffice.providers;

import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import it.newmedia.gokick.data.enums.EnumEmailConfigurationType;
import it.newmedia.gokick.data.hibernate.beans.EmailConfiguration;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Translation;
import it.newmedia.net.AEmailHelper;
import it.newmedia.net.HtmlEmailHelper;
import it.newmedia.net.TextEmailHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce la creazione dell'email che verrà poi inviata tramite EmailManager.
 */
public class EmailProvider {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  public static final String SPLIT_CHAR_NAME_EMAIL = ",";
  private static final String SPLIT_CHAR_ELEMENT = ";";
  public static final String LANGUAGE_IT = "it";
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static final Logger logger = Logger.getLogger(EmailProvider.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  /**
   * Creates a new instance of EmailProvider
   */
  public EmailProvider()
  {
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  
  /**
   * Restituisce un oggetto EmailHelper popolato
   * @param emailConfigurationType è la chiave per recuperare l'oggetto email
   * @param language è la lingua del sito e serve per tradurre l'oggetto e il corpo
   * @return un oggetto HtmlEmailHelper se l'email sarà Html oppure un oggetto TextEmailHelper se è un'email di testo semplice
   * se non viene trovata una chiave corrispondente restituisce NULL
   */
  public static AEmailHelper getEmailHelper(EnumEmailConfigurationType emailConfigurationType, Language language) throws Exception
  {
    return getEmailHelper(emailConfigurationType, language, -1);
  }

  /**
   * Restituisce un oggetto EmailHelper popolato
   * @param emailConfigurationType è la chiave per recuperare l'oggetto email
   * @param language è la lingua del sito e serve per tradurre l'oggetto e il corpo
   * @param idUser Id dell'utente con il quale fare il replace dei tag presenti nei messaggi usati per l'oggetto e il corpo dell'email
   * @return un oggetto HtmlEmailHelper se l'email sarà Html oppure un oggetto TextEmailHelper se è un'email di testo semplice
   * se non viene trovata una chiave corrispondente restituisce NULL
   */
  public static AEmailHelper getEmailHelper(EnumEmailConfigurationType emailConfigurationType, Language language, int idUser) throws Exception
  {
    EmailConfiguration emailConfiguration;
    AEmailHelper aEmailHelper = null;

    if (language == null || StringUtils.isEmpty(language.getLanguage()))
    {
      language =DAOFactory.getInstance().getLanguageDAO().getByLanguage(LANGUAGE_IT);
    }

    emailConfiguration = DAOFactory.getInstance().getEmailConfigurationDAO().get(emailConfigurationType);
    if (emailConfiguration == null)
    {
      return null;
    }

    // Is Html
    if (emailConfiguration.isHtmlFormatEnabled())
    {
      aEmailHelper = new HtmlEmailHelper(emailConfiguration.getSmptServer());
    }
    else
    {
      aEmailHelper = new TextEmailHelper(emailConfiguration.getSmptServer());
    }

    // From
    if (StringUtils.isNotBlank(emailConfiguration.getFromName()))
    {
      aEmailHelper.setFrom(emailConfiguration.getFromEmail(), emailConfiguration.getFromName());
    }
    else
    {
      aEmailHelper.setFrom(emailConfiguration.getFromEmail());
    }

    // To
    if (emailConfiguration.getTo().length() > 0)
    {
      String[] toList = emailConfiguration.getTo().trim().split(SPLIT_CHAR_ELEMENT);
      for (int i = 0; i < toList.length; i++)
      {
        String[] toListDetails = toList[i].split(SPLIT_CHAR_NAME_EMAIL);
        if (toListDetails.length == 2)
        {
          aEmailHelper.addTo(toListDetails[1], toListDetails[0]);
        }
        else
        {
          aEmailHelper.addTo(toListDetails[0]);
        }
      }
    }

    // Cc
    if (emailConfiguration.getCc().length() > 0)
    {
      String[] ccList = emailConfiguration.getCc().trim().split(SPLIT_CHAR_ELEMENT);
      for (int i = 0; i < ccList.length; i++)
      {
        String[] ccListDetails = ccList[i].split(SPLIT_CHAR_NAME_EMAIL);
        if (ccListDetails.length == 2)
        {
          aEmailHelper.addCc(ccListDetails[1], ccListDetails[0]);
        }
        else
        {
          aEmailHelper.addCc(ccListDetails[0]);
        }
      }
    }

    // Bcc
    if (emailConfiguration.getBcc().length() > 0)
    {
      String[] bccList = emailConfiguration.getBcc().trim().split(SPLIT_CHAR_ELEMENT);
      for (int i = 0; i < bccList.length; i++)
      {
        String[] bccListDetails = bccList[i].split(SPLIT_CHAR_NAME_EMAIL);
        if (bccListDetails.length == 2)
        {
          aEmailHelper.addBcc(bccListDetails[1], bccListDetails[0]);
        }
        else
        {
          aEmailHelper.addBcc(bccListDetails[0]);
        }
      }
    }

    Translation translation = null;
    // Oggetto
    //translation = TranslationProvider.getTranslation(emailConfiguration.getKeyNameSubject(), language, idUser);
    //aEmailHelper.setSubject(translation.getKeyValue());

    //Corpo del messaggio
    //translation = TranslationProvider.getTranslation(emailConfiguration.getKeyNameBody(), language, idUser);
    //aEmailHelper.setMsg(translation.getKeyValue());

    // Username e password dell'smtp
    if (emailConfiguration.isSmtpAuthenticationEnabled())
    {
      aEmailHelper.setAuthentication(emailConfiguration.getSmtpUsername(), emailConfiguration.getSmtpPassword());
    }

    // Smtp Use SSL enabled
    aEmailHelper.setSslEnabled(emailConfiguration.isSmtpSslEnabled());
    // Smtp port
    aEmailHelper.setSmtpPort(emailConfiguration.getSmtpPort());

    return aEmailHelper;
  }
  // </editor-fold>
}
