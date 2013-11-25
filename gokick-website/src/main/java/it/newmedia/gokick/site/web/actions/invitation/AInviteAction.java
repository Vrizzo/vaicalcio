package it.newmedia.gokick.site.web.actions.invitation;

import it.newmedia.gokick.site.web.actions.*;
import com.opensymphony.xwork2.Preparable;
import it.newmedia.utils.DataValidator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *Classe astratta estesa dalle action che gestiscono gli inviti al sito e alle partite
 * 
 */
public abstract class AInviteAction extends AuthenticationBaseAction 
{
  
  //<editor-fold defaultstate="collapsed" desc="CONSTANTS">
    public static final String MAILS_SEPARATOR = ",";
    //<editor-fold defaultstate="collapsed" desc="OPEN INVITER ERRORS">
    public static final String ERROR_GET = "err_get";
    public static final String ERROR_NO_CONTACT = "err_no_contact";
    public static final String ERROR_LOGIN = "err_login";
    public static final String ERROR_INVALID_PROVIDER = "is not a valid domain";
    public static final String DB_KEY_ERROR_INVALID_PROVIDER = "err_provider";
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="ERRORI DA OPEN INVITER NON USATI">
          //  i seguenti 3 non mi interessano perchè faccio la validazione da form
          //  private static final String ERROR_EMAIL = "err_email";   
          //  private static final String ERROR_PASSWORD = "err_password";
          //  private static final String ERROR_PROVIDER = "err_provider";
          //sembrerebbe non esistere
          //  private static final String ERROR_DOMAIN = "err_domain";
          //!importContactStr.equals(ERROR_EMAIL) && !importContactStr.equals(ERROR_PASSWORD) && !importContactStr.equals(ERROR_PROVIDER)  && !importContactStr.equals(ERROR_DOMAIN)
  //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="STATUS NORMAL INVITE">
    public final static String STATUS_TO_DO = "TODO";
    public final static String STATUS_SENT = "SENT";
    public final static String STATUS_SENT_ALL = "SENT_ALL";
    public final static String STATUS_SENT_NOTHING = "SENT_NOTHING";
    public final static String STATUS_SENT_PARTIAL = "SENT_PARTIAL";
    // </editor-fold>
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  protected String addresses;
  protected String freeText;
  protected String account;
  protected String password;
  protected String provider;
  protected List<String> contacts=new ArrayList<String>();

  // </editor-fold>
  
  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  
  @Override
  public String input()
  {
    return SUCCESS;
  }

  //check for openInviterErrors
  public boolean checkError(String importContactStr)
  {
    return !importContactStr.equals(ERROR_LOGIN)
            && (!importContactStr.equals(ERROR_NO_CONTACT) || importContactStr.length()==0)
            && !importContactStr.equals(ERROR_GET)
            && !importContactStr.contains(ERROR_INVALID_PROVIDER);
  }

  public void validateImportContacts()
  {
    if (StringUtils.isBlank(this.provider))
    {
      addFieldError("errorImport", getText("error.importContacts.emptyProvider"));
    }
    if (!this.hasErrors())
    {
      if (StringUtils.isBlank(this.account))
      {
        addFieldError("errorImport", getText("error.importContacts.emptyAccount"));
      }
    }
    if (!this.hasErrors())
    {
      if (StringUtils.isBlank(this.password))
      {
        addFieldError("errorImport", getText("error.importContacts.emptyPassword"));
      }
    }
    if (!this.hasErrors())
    {
      if (!DataValidator.checkEmail(this.account))
      {
        addFieldError("errorImport", getText("error.importContacts.invalidAccount"));
      }
    }

  }

  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public String getAddresses()
  {
    return addresses;
  }

  public void setAddresses(String addresses)
  {
    this.addresses = addresses;
  }

  /**
   * @return the freeText
   */
  public String getFreeText()
  {
    return freeText;
  }

  /**
   * @param freeText the freeText to set
   */
  public void setFreeText(String freeText)
  {
    this.freeText = freeText;
  }
  
  public String getAccount()
  {
    return account;
  }

  public void setAccount(String account)
  {
    this.account = account;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getProvider()
  {
    return provider;
  }

  public void setProvider(String provider)
  {
    this.provider = provider;
  }

  public void setContacts(ArrayList<String> contacts)
  {
    this.contacts = contacts;
  }
  // </editor-fold>
  
  
}
