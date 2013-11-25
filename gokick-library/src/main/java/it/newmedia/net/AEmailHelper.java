package it.newmedia.net;

import it.newmedia.results.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.mail.MultiPartEmail;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public abstract class AEmailHelper
{

  public static final String CHARSET_UTF8 = "UTF-8";

  public static final String DEFAULT_ADDRESS_SEPARATOR = ";";
  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >

  String hostName;

  String username;

  String password;

  boolean sslEnabled;

  int smtpPort;

  String fromAddress;

  String fromName;

  HashMap<String, String> toList;

  HashMap<String, String> ccList;

  HashMap<String, String> bccList;

  HashMap<String, String> replyToList;

  String subject;

  String msg;

  String charset;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  private AEmailHelper()
  {
  }

  protected AEmailHelper(String hostName)
  {
    this.hostName = hostName;
    this.toList = new HashMap<String, String>();
    this.ccList = new HashMap<String, String>();
    this.bccList = new HashMap<String, String>();
    this.replyToList = new HashMap<String, String>();
    this.charset = CHARSET_UTF8;
    this.sslEnabled = false;
    this.smtpPort = 25;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public void setFrom(String address)
  {
    this.setFrom(address, null);
  }

  public void setFrom(String address, String name)
  {
    this.fromAddress = address;
    if (name != null)
    {
      this.fromName = name;
    }
  }

  /**
   * Change from name without modify email from
   * @param name The name to show when email is received
   */
  public void setFromName(String name)
  {
    this.fromName = name;
  }

  /**
   * Append to from name without modify email from
   * @param name The name to append to current name show when email is received
   */
  public void setFromNameConcat(String name)
  {
    this.fromName = StringUtils.isBlank(this.fromName) ? name : this.fromName + name;
  }
  
  public void setAuthentication(String username, String password)
  {
    this.username = username;
    this.password = password;
  }

  public void setSmtpPort(int smtpPort)
  {
    this.smtpPort = smtpPort;
  }

  public void setSslEnabled(boolean sslEnabled)
  {
    this.sslEnabled = sslEnabled;
  }

  public String getSubject()
  {
    return subject;
  }

  public void setSubject(String subject)
  {
    this.subject = subject;
  }

  public String getMsg()
  {
    return msg;
  }

  public void setMsg(String msg)
  {
    this.msg = msg;
  }

  public void setCharset(String charset)
  {
    this.charset = charset;
  }

  public void addTo(String address)
  {
    addTo(address, null);
  }

  public void addTo(String address, String name)
  {
    putIntoList(this.toList, address, name);
  }

  public void addToFromString(String addressStr, String sep)
  {
    List<String> addressList = parseStringToAddress(addressStr, sep);
    for (String address : addressList)
    {
      this.addTo(address);
    }
  }

  public void addCc(String address)
  {
    addCc(address, null);
  }

  public void addCc(String address, String name)
  {
    putIntoList(this.ccList, address, name);
  }

  public void addCcFromString(String addressStr, String sep)
  {
    List<String> addressList = parseStringToAddress(addressStr, sep);
    for (String address : addressList)
    {
      this.addCc(address);
    }
  }

  public void addBcc(String address)
  {
    addBcc(address, null);
  }

  public void addBcc(String address, String name)
  {
    putIntoList(this.bccList, address, name);
  }

  public void addBccFromString(String addressStr, String sep)
  {
    List<String> addressList = parseStringToAddress(addressStr, sep);
    for (String address : addressList)
    {
      this.addBcc(address);
    }
  }

  public void addReplyTo(String address)
  {
    addReplyTo(address, null);
  }

  public void addReplyTo(String address, String name)
  {
    putIntoList(this.replyToList, address, name);
  }

  public abstract Result<String> send();

  public abstract boolean isHtml();

  public void sendAndForget()
  {
    EmailWorker worker = new EmailWorker(this);
    new Thread(worker).start();
  }
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Private Methos --"  >
  protected void prepareEmail(MultiPartEmail email) throws Exception
  {
    email.setHostName(this.hostName);
    email.setCharset(this.charset);

    if (this.username != null && this.username.length() > 0 && this.password != null)
    {
      email.setAuthentication(this.username, this.password);
    }

    email.setSSL(this.sslEnabled);

//    if( this.sslEnabled )
//    {
//      //email.setSslSmtpPort(Integer.toString(this.smtpPort));
//      email.setSmtpPort(this.smtpPort);
//    }
//    else
//    {
//      email.setSmtpPort(this.smtpPort);
//    }
    email.setSmtpPort(this.smtpPort);

    email.setSubject(this.subject);

    // <editor-fold defaultstate="collapsed" desc="-- From --">
    try
    {
      if (StringUtils.isNotBlank(this.fromName))
      {
        email.setFrom(this.fromAddress, this.fromName);
      }
      else
      {
        email.setFrom(this.fromAddress);
      }
    }
    catch (Exception ex)
    {
      throw new Exception("Error on from address: " + ex.getMessage(), ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- To --">
    try
    {
      if (this.toList.size() > 0)
      {
        email.setTo(buildInternetAddressList(this.toList));
      }
    }
    catch (Exception ex)
    {
      throw new Exception("Error on to address: " + ex.getMessage(), ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Cc --">
    try
    {
      if (this.ccList.size() > 0)
      {
        email.setCc(buildInternetAddressList(this.ccList));
      }
    }
    catch (Exception ex)
    {
      throw new Exception("Error on cc address: " + ex.getMessage(), ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Bcc --">
    try
    {
      if (this.bccList.size() > 0)
      {
        email.setBcc(buildInternetAddressList(this.bccList));
      }
    }
    catch (Exception ex)
    {
      throw new Exception("Error on bcc address: " + ex.getMessage(), ex);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- ReplyTo --">
    try
    {
      if (this.replyToList.size() > 0)
      {
        email.setReplyTo(buildInternetAddressList(this.replyToList));
      }
    }
    catch (Exception ex)
    {
      throw new Exception("Error on replyTo address: " + ex.getMessage(), ex);
    }
    // </editor-fold>

  }

  private void putIntoList(HashMap<String, String> hm, String address, String name)
  {
    if (address == null)
    {
      return;
    }
    hm.put(address, name);
  }

  private List<InternetAddress> buildInternetAddressList(HashMap<String, String> hm) throws UnsupportedEncodingException, AddressException
  {
    List<InternetAddress> list = new ArrayList<InternetAddress>();
    for (Entry<String, String> e : hm.entrySet())
    {
      if (e.getValue() != null && e.getValue().length() > 0)
      {
        list.add(new InternetAddress(e.getKey(), e.getValue()));
      }
      else
      {
        list.add(new InternetAddress(e.getKey()));
      }
    }
    return list;
  }

  private List<String> parseStringToAddress(String addressesStr, String separator)
  {
    List<String> list = new ArrayList<String>();
    if (StringUtils.isEmpty(addressesStr))
    {
      return list;
    }
    if (StringUtils.isEmpty(separator))
    {
      separator = DEFAULT_ADDRESS_SEPARATOR;
    }

    String[] addresses = StringUtils.split(addressesStr, separator);
    if (addresses.length > 0)
    {
      for (String address : addresses)
      {
        String addressToAdd = StringUtils.trimToEmpty(address);
        if (StringUtils.isNotEmpty(addressToAdd))
          list.add(addressToAdd);
      }
    }
    return list;
  }
  // </editor-fold>

  private class EmailWorker implements Runnable
  {
    private AEmailHelper emailHelper;

    public EmailWorker(AEmailHelper emailHelper)
    {
      this.emailHelper = emailHelper;
    }

    @Override
    public void run()
    {
      try
      {
        int waitFor = RandomUtils.nextInt()%3000;
        System.out.println("EmailHelper -> aspetto per " + waitFor);
        Thread.sleep(waitFor);
        Result<String> result = this.emailHelper.send();
        System.out.println("EmailHelper -> " + (result.isSuccessNotNull() ? "OK " + result.getValue() : "KO " + result.getErrorMessage()));
      }
      catch (InterruptedException e)
      {
        System.out.println("EmailHelper error -> " + e);
      }
    }
  }
}
