package it.newmedia.net;

import it.newmedia.results.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TextEmailHelper extends AEmailHelper
{

  List<String[]> attachments;

  public TextEmailHelper(String hostName)
  {
    super(hostName);
    this.attachments = new ArrayList<String[]>();
  }

  public void addAttachment(String pathname)
  {
    this.addAttachment(pathname, "");
  }

  public void addAttachment(String pathname, String name)
  {
    this.addAttachment(pathname, name, "");
  }

  public void addAttachment(String pathname, String name, String description)
  {
    pathname = pathname == null ? "" : pathname;
    name = name == null ? "" : name;
    description = description == null ? "" : description;
    this.attachments.add(new String[]
            {
              pathname,
              name,
              description
            });
  }

  public void addAttachment(File[] files)
  {
    if (files != null && files.length > 0)
    {
      for (File file : files)
      {
        addAttachment(file.getAbsolutePath(), file.getName());
      }
    }
  }

  @Override
  public Result<String> send()
  {
    MultiPartEmail email = new MultiPartEmail();
    try
    {
      prepareEmail(email);
    }
    catch (Exception ex)
    {
      return new Result<String>("Error preparing email [" + ex.getMessage() + "]", ex);
    }

    if (this.msg == null || this.msg.length() < 1)
    {
      return new Result<String>("Cannot send email with empty body message!");
    }
    else
    {
      try
      {
        email.setMsg(this.msg);
      }
      catch (EmailException ex)
      {
        return new Result<String>("Error setting body msg", ex);
      }
    }

    if (this.attachments != null || this.attachments.size() > 0)
    {
      for (String[] strings : attachments)
      {
        EmailAttachment emailAttachment = createEmailAttachment(strings[0], strings[1], strings[2]);
        if (emailAttachment != null)
        {
          try
          {
            email = email.attach(emailAttachment);
          }
          catch (EmailException ex)
          {
            return new Result<String>("Error setting attachment", ex);
          }
        }
      }
    }

    try
    {
      return new Result<String>("Message sent! " + email.send(), true);
    }
    catch (EmailException ex)
    {
      return new Result<String>("Error sending email [" + ex.getMessage() + "]", ex);
    }
  }

  @Override
  public boolean isHtml()
  {
    return false;
  }

  // <editor-fold defaultstate="collapsed" desc="-- Private Method --">
  private EmailAttachment createEmailAttachment(String attachmentPath, String attachmentName, String attachmentDescription)
  {
    if (StringUtils.isEmpty(attachmentPath) || StringUtils.isEmpty(attachmentName))
    {
      return null;
    }

    // Create the attachment
    EmailAttachment emailAttachment = new EmailAttachment();
    emailAttachment.setPath(attachmentPath);
    emailAttachment.setName(attachmentName);
    emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
    if (StringUtils.isNotEmpty(attachmentDescription))
    {
      emailAttachment.setDescription(attachmentDescription);
    }
    return emailAttachment;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Main Method --"  >
  public static void main(String[] args)
  {
    try
    {
      System.out.println("Sending email...");
      TextEmailHelper emailHelper = new TextEmailHelper("smtp.gokick.it");
      emailHelper.setAuthentication("noreply/gokick.it", "maradona");
      emailHelper.setSslEnabled(false);
      emailHelper.setSmtpPort(25);

      emailHelper.setFrom("noreply@gokick.it");
      emailHelper.addTo("Stefano <stefano.salmaso@newmedia.it>");
      emailHelper.setSubject("Test invio");
      emailHelper.setMsg("<b>Questo</b> è un messaggio di prova");

      Result<String> result = emailHelper.send();

      if (result.isSuccessNotNull())
      {
        System.out.println("Message sent! " + result.getValue());
      }
      else
      {
        System.out.println("Error sending message: " + result.getErrorMessage());
      }
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
  // </editor-fold>
}
