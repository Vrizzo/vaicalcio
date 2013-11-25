package it.newmedia.net;

import it.newmedia.results.Result;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HtmlEmailHelper extends AEmailHelper
{

  List<String[]> attachments;

  public HtmlEmailHelper(String hostName)
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
    if( files!=null && files.length>0)
    {
      for(File file : files)
        addAttachment(file.getAbsolutePath(), file.getName());
    }
  }

  @Override
  public Result<String> send()
  {
    HtmlEmail email = new HtmlEmail();

    try
    {
      prepareEmail(email);
    }
    catch( Exception ex )
    {
      return new Result<String>("Error preparing email [" + ex.getMessage() + "]", ex);
    }

    if( this.msg == null || this.msg.length() < 1 )
    {
      return new Result<String>("Cannot send email with empty body message!");
    }
    else
    {
      try
      {
        email.setHtmlMsg(this.msg);
      }
      catch( EmailException ex )
      {
        return new Result<String>("Error setting body msg", ex);
      }
    }

    // <editor-fold defaultstate="collapsed" desc="-- Attachments --"  >
    if( this.attachments.size() > 0 )
    {
      try
      {
        EmailAttachment ea = null;
        for( String[] attachment : this.attachments )
        {
          ea = new EmailAttachment();
          ea.setPath(attachment[0]);
          if( attachment[1].length() > 0 )
          {
            ea.setName(attachment[1]);
          }
          if( attachment[2].length() > 0 )
          {
            ea.setName(attachment[2]);
          }
          ea.setDisposition(EmailAttachment.ATTACHMENT);
          email.attach(ea);
        }
      }
      catch( Exception ex )
      {
        return new Result<String>("Error on attachments", ex);
      }
    }
    // </editor-fold>

    try
    {
      return new Result<String>("Message sent! " + email.send(), true);
    }
    catch( EmailException ex )
    {
      return new Result<String>("Error sending email [" + ex.getMessage() + "]", ex);
    }
  }

  @Override
  public boolean isHtml()
  {
    return true;
  }


  // </editor-fold>
  public static void main(String[] args)
  {
    try
    {
      System.out.println("Sending email...");
      HtmlEmailHelper emailHelper = new HtmlEmailHelper("authsmtp.newmediagroup.it");
      emailHelper.setAuthentication("smtp@newmediagroup.it", "E2NQj3m5");
      emailHelper.setSslEnabled(false);
      emailHelper.setSmtpPort(25);
      
      emailHelper.setFrom("pippo@newmediagroup.it", "pippo");
      emailHelper.addTo("stefano.salmaso@newmedia.it", "VI");
      emailHelper.setSubject("Test invio");
      emailHelper.setMsg("<b>Questo</b> è un messaggio di prova");

      emailHelper.sendAndForget();
      System.out.println("Fatto... premi un tasto");

      System.in.read();

//      Result<String> result = emailHelper.send();
//
//      if( result.isSuccessNotNull() )
//      {
//        System.out.println("Message sent! " + result.getValue());
//      }
//      else
//      {
//        System.out.println("Error sending message: " + result.getErrorMessage());
//      }
    }
    catch( Exception exception )
    {
      exception.printStackTrace();
    }
  }
  // </editor-fold>
}
