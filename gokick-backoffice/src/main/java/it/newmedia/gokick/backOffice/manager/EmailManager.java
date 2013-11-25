package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.enums.EnumEmailConfigurationType;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.backOffice.providers.EmailProvider;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.net.HtmlEmailHelper;
import it.newmedia.results.Result;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello l'invio delle email.
 */
public class EmailManager {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  public static final String EMAIL_SEPARATOR = ",";
  public static final String HTML_LINE_SEPARATOR = "<br/>";
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(EmailManager.class);
  
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private EmailManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public static Result<Boolean> sendEmailToUser(User userToInsert, Language locale, String subject, String body)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(EnumEmailConfigurationType.BackofficeNotificationMail, locale);
      // To
      htmlEmailHelper.addTo(userToInsert.getEmail(), userToInsert.getFirstName() + " " + userToInsert.getLastName());

      //sunject,body
      htmlEmailHelper.setSubject(subject);
      htmlEmailHelper.setMsg(body);

      // Invio dell'email
      rSend = htmlEmailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logger.error(rSend.getErrorMessage());
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending email to Users", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

  public static Result<Boolean> sendEmailToSportCenter(SportCenter sportCenteToInsert, Language locale, String subject, String body)
  {
    HtmlEmailHelper htmlEmailHelper;
    Result<String> rSend;

    try
    {
      htmlEmailHelper = (HtmlEmailHelper) EmailProvider.getEmailHelper(EnumEmailConfigurationType.BackofficeNotificationMail, locale);
      // To
      htmlEmailHelper.addTo(sportCenteToInsert.getEmail(), sportCenteToInsert.getName());

      //sunject,body
      htmlEmailHelper.setSubject(subject);
      htmlEmailHelper.setMsg(body);

      // Invio dell'email
      rSend = htmlEmailHelper.send();
      if (!rSend.isSuccessNotNull())
      {
        logger.error(rSend.getErrorMessage());
        return new Result<Boolean>(false, false);
      }
    }
    catch (Exception ex)
    {
      logger.error("Error sending  email to SportCenter", ex);
      return new Result<Boolean>(ex);
    }

    return new Result<Boolean>(true, true);
  }

 
  // </editor-fold>
}
