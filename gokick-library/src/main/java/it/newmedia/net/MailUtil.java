package it.newmedia.net;

import it.newmedia.results.Result;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.mail.internet.InternetAddress;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class MailUtil
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  public MailUtil()
  {
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Getters/Setters --">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Methods --">
  // <editor-fold defaultstate="collapsed" desc="-- SIMPLE TEXT --">
  public static Result<String> sendSimpleTextEmail(String hostName, String fromAddress, String toAddress, String subject, String msg)
  {
    return MailUtil.sendSimpleTextEmail(hostName, fromAddress, toAddress, "", "", subject, msg);
  }

  public static Result<String> sendSimpleTextEmail(String hostName, String fromAddress, String toAddress, String ccAddress, String bccAddress, String subject, String msg)
  {
    return MailUtil.sendSimpleTextEmail(hostName, fromAddress, "", toAddress, "", "", "", "", "", subject, msg);
  }

  public static Result<String> sendSimpleTextEmail(String hostName, String fromAddress, String fromName, String toAddress, String toName, String ccAddress, String ccName, String bccAddress, String bccName, String subject, String msg)
  {
    // <editor-fold defaultstate="collapsed" desc="-- From --">
    InternetAddress from;
    try
    {
      if (fromName != null && fromName.length() > 0)
        from = new InternetAddress(fromAddress, fromName);
      else
        from = new InternetAddress(fromAddress);
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on from address", ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- To --">
    List<InternetAddress> toList = new ArrayList<InternetAddress>();
    try
    {
      if (toAddress != null && toAddress.length() > 0)
      {
        if (toName != null && toName.length() > 0)
          toList.add(new InternetAddress(toAddress, toName));
        else
          toList.add(new InternetAddress(toAddress));
      }
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on to address", ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Cc --">
    List<InternetAddress> ccList = new ArrayList<InternetAddress>();
    try
    {
      if (ccAddress != null && ccAddress.length() > 0)
      {
        if (ccName != null && ccName.length() > 0)
          ccList.add(new InternetAddress(ccAddress, ccName));
        else
          ccList.add(new InternetAddress(ccAddress));
      }
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on cc address", ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Bcc --">
    List<InternetAddress> bccList = new ArrayList<InternetAddress>();
    try
    {
      if (bccAddress != null && bccAddress.length() > 0)
      {
        if (bccName != null && bccName.length() > 0)
          bccList.add(new InternetAddress(bccAddress, bccName));
        else
          bccList.add(new InternetAddress(bccAddress));
      }
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on bcc address", ex);
    }
    // </editor-fold>

    if (toList.size() == 0 && ccList.size() == 0 && bccList.size() == 0)
      return new Result<String>("Cannot send email without to, cc or bcc addresses!");

    return MailUtil.sendSimpleTextEmail(hostName, from, toList, ccList, bccList, subject, msg);
  }

  public static Result<String> sendSimpleTextEmail(String hostName, String fromAddress, String toAddress, List ccAddress, List bccAddress, String subject, String msg)
  {
    // <editor-fold defaultstate="collapsed" desc="-- From --">
    InternetAddress from;
    try
    {
      from = new InternetAddress(fromAddress);
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on from address", ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- To --">
    List<InternetAddress> toList = new ArrayList<InternetAddress>();
    try
    {
      if (toAddress != null && toAddress.length() > 0)
      {
        toList.add(new InternetAddress(toAddress));
      }
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on to address", ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Cc --">
    List<InternetAddress> ccList = new ArrayList<InternetAddress>();
    try
    {
      if (ccAddress != null && ccAddress.size() > 0)
      {
        for (int i = 0; i < ccList.size(); i++)
        {
          ccList.add(new InternetAddress(ccAddress.get(i).toString()));
        }
      }
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on cc address", ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Bcc --">
    List<InternetAddress> bccList = new ArrayList<InternetAddress>();
    try
    {
      if (bccAddress != null && bccAddress.size() > 0)
      {
        for (int i = 0; i < ccList.size(); i++)
        {
          ccList.add(new InternetAddress(bccAddress.get(i).toString()));
        }
      }
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on bcc address", ex);
    }
    // </editor-fold>

    if (toList.size() == 0 && ccList.size() == 0 && bccList.size() == 0)
      return new Result<String>("Cannot send email without to, cc or bcc addresses!");

    return MailUtil.sendSimpleTextEmail(hostName, from, toList, ccList, bccList, subject, msg);
  }

  public static Result<String> sendSimpleTextEmail(String hostName, InternetAddress from, List<InternetAddress> toList, List<InternetAddress> ccList, List<InternetAddress> bccList, String subject, String msg)
  {
    return sendSimpleTextEmail(hostName, from, toList, ccList, bccList, subject, msg, null, null);
  }

  public static Result<String> sendSimpleTextEmail(String hostName, InternetAddress from, List<InternetAddress> toList, List<InternetAddress> ccList, List<InternetAddress> bccList, String subject, String msg, String userName, String password)
  {
    try
    {
      SimpleEmail email = new SimpleEmail();

      if (userName != null && userName.length()>0 && password != null)
        email.setAuthentication(userName, password);

      email.setHostName(hostName);
      if (from.getPersonal() != null)
        email.setFrom(from.getAddress(), from.getPersonal());
      else
        email.setFrom(from.getAddress());

      if (toList != null && toList.size() > 0)
        email.setTo(toList);

      if (ccList != null && ccList.size() > 0)
        email.setCc(ccList);

      if (bccList != null && bccList.size() > 0)
        email.setBcc(bccList);

      email.setSubject(subject);

      if (msg == null || msg.length() < 1)
        return new Result<String>("Cannot send email with empty body message!");

      email.setMsg(msg);

      return new Result<String>("Message sent! " + email.send(), true);
    }
    catch (EmailException ex)
    {
      return new Result<String>(ex);
    }
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- HTML TEXT --">
  public static Result<String> sendHtmlEmail(String hostName, String fromAddress, String toAddress, String subject, String msg)
  {
    return MailUtil.sendHtmlEmail(hostName, fromAddress, toAddress, "", "", subject, msg);
  }

  public static Result<String> sendHtmlEmail(String hostName, String fromAddress, String toAddress, String ccAddress, String bccAddress, String subject, String msg)
  {
    return MailUtil.sendHtmlEmail(hostName, fromAddress, "", toAddress, "", "", "", "", "", subject, msg);
  }

  public static Result<String> sendHtmlEmail(String hostName, String fromAddress, String fromName, String toAddress, String toName, String ccAddress, String ccName, String bccAddress, String bccName, String subject, String msg)
  {
    return MailUtil.sendHtmlEmail(hostName, fromAddress, fromName, toAddress, toName, ccAddress, ccName, bccAddress, bccName, subject, msg, null);
  }

  public static Result<String> sendHtmlEmail(String hostName, String fromAddress, String fromName, String toAddress, String toName, String ccAddress, String ccName, String bccAddress, String bccName, String subject, String msg, File[] attachments)
  {
    return MailUtil.sendHtmlEmail(hostName, fromAddress, fromName, toAddress, toName, ccAddress, ccName, bccAddress, bccName, subject, msg, attachments , null, null);
  }

  public static Result<String> sendHtmlEmail(String hostName, String fromAddress, String fromName, String toAddress, String toName, String ccAddress, String ccName, String bccAddress, String bccName, String subject, String msg, File[] attachments , String userName, String password)
  {
    // <editor-fold defaultstate="collapsed" desc="-- From --">
    InternetAddress from;
    try
    {
      if (fromName != null && fromName.length() > 0)
        from = new InternetAddress(fromAddress, fromName);
      else
        from = new InternetAddress(fromAddress);
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on from address", ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- To --">
    List<InternetAddress> toList = new ArrayList<InternetAddress>();
    try
    {
      if (toAddress != null && toAddress.length() > 0)
      {
        if (toName != null && toName.length() > 0)
          toList.add(new InternetAddress(toAddress, toName));
        else
          toList.add(new InternetAddress(toAddress));
      }
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on to address", ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Cc --">
    List<InternetAddress> ccList = new ArrayList<InternetAddress>();
    try
    {
      if (ccAddress != null && ccAddress.length() > 0)
      {
        if (ccName != null && ccName.length() > 0)
          ccList.add(new InternetAddress(ccAddress, ccName));
        else
          ccList.add(new InternetAddress(ccAddress));
      }
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on cc address", ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Bcc --">
    List<InternetAddress> bccList = new ArrayList<InternetAddress>();
    try
    {
      if (bccAddress != null && bccAddress.length() > 0)
      {
        if (bccName != null && bccName.length() > 0)
          bccList.add(new InternetAddress(bccAddress, bccName));
        else
          bccList.add(new InternetAddress(bccAddress));
      }
    }
    catch (Exception ex)
    {
      return new Result<String>("Error on bcc address", ex);
    }
    // </editor-fold>

    if (toList.size() == 0 && ccList.size() == 0 && bccList.size() == 0)
      return new Result<String>("Cannot send email without to, cc or bcc addresses!");

    return MailUtil.sendHtmlEmail(hostName, from, toList, ccList, bccList, subject, msg, attachments, userName, password);
  }

  public static Result<String> sendHtmlEmail(String hostName, InternetAddress from, List<InternetAddress> toList, List<InternetAddress> ccList, List<InternetAddress> bccList, String subject, String msg)
  {
    return MailUtil.sendHtmlEmail(hostName, from, toList, ccList, bccList, subject, msg, null);
  }

  public static Result<String> sendHtmlEmail(String hostName, InternetAddress from, List<InternetAddress> toList, List<InternetAddress> ccList, List<InternetAddress> bccList, String subject, String msg, File[] attachments)
  {
    return sendHtmlEmail(hostName, from, toList, ccList, bccList, subject, msg, attachments, null, null);
  }

  public static Result<String> sendHtmlEmail(String hostName, InternetAddress from, List<InternetAddress> toList, List<InternetAddress> ccList, List<InternetAddress> bccList, String subject, String msg, File[] attachments, String userName, String password)
  {
    try
    {
      HtmlEmail email = new HtmlEmail();

      if (userName != null && userName.length()>0 && password != null)
        email.setAuthentication(userName, password);
      
      email.setHostName(hostName);
      if (from.getPersonal() != null)
        email.setFrom(from.getAddress(), from.getPersonal());
      else
        email.setFrom(from.getAddress());

      if (toList != null && toList.size() > 0)
        email.setTo(toList);

      if (ccList != null && ccList.size() > 0)
        email.setCc(ccList);

      if (bccList != null && bccList.size() > 0)
        email.setBcc(bccList);

      email.setSubject(subject);

      if (msg == null || msg.length() < 1)
        return new Result<String>("Cannot send email with empty body message!");

      email.setHtmlMsg(msg);

      if (attachments != null)
      {
        EmailAttachment ea = null;
        for (File attachment : attachments)
        {
          ea = new EmailAttachment();
          ea.setPath(attachment.getAbsolutePath());
          ea.setName(attachment.getName());
          ea.setDisposition(EmailAttachment.ATTACHMENT);
          email.attach(ea);
        }
      }

      return new Result<String>("Message sent! " + email.send(), true);
    }
    catch (EmailException ex)
    {
      return new Result<String>(ex);
    }
  }

  // </editor-fold>
  public static void main(String[] args)
  {
    try
    {
      System.out.println("Sending email...");
      String host = "mail.newmedia.it";
      String fromAddress = "davide.compagnone@newmedia.it";
      String toAddress = "davide.compagnone@newmedia.it";   

      //File[] attachmentList = new File("C:/data").listFiles();
      Result<String> result = MailUtil.sendHtmlEmail(host, fromAddress, "", toAddress, "", "", "", "", "", "Test da NewmediaLibrary-1.1", "<hr><b>Questo e' il corpo del messaggio</b><hr> n° à");
      if (result.isSuccessNotNull())
        System.out.println("Message sent! " + result.getValue());
      else
        System.out.println("Error sending message: " + result.getErrorMessage());
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
  // </editor-fold>
}
