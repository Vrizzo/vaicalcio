package it.newmedia.gokick.site.web.actions.invitation;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiContact;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.EmailManager;
import it.newmedia.gokick.site.managers.ImportContactManager;
import it.newmedia.gokick.site.managers.ProvinceManager;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.results.Result;
import it.newmedia.utils.DataValidator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente le azioni per invitare non iscritti ad un match, tramite mail
 */
public class InviteToMatchAction extends AInviteAction implements Preparable
{

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  // private static Logger logger = Logger.getLogger(UserEnableAction.class);
  private int idMatch;
  private String message;
  private Integer idProvince;
  private String titleProvince;
  private List<GuiContact> guiContacts;
  private boolean sendInviteOk;
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public void prepare()
  {
    this.idProvince = UserContext.getInstance().getUser().getProvince().getId();
    Province province = ProvinceManager.getProvince(this.idProvince);
    this.titleProvince = province.getName();
    this.freeText = getText("message.invitaSito");
  }
  
  @Override
  public String execute()
  {
    MatchInfo matchInfo = InfoProvider.getMatchInfo(this.idMatch);
    if (!matchInfo.isValid())
    {
      return ERROR_POPUP;
    }
    List<String> mails = EmailManager.split(this.addresses);
    if (this.getCurrentUserInfo().getInvitationsAvailable() > 0)
    {
      //Se l'utente ha inviti a disposizione le email vengono
      //inviate a tutti indifferentemente
      for (String mailTo : mails)
      {
        sendInviteFriendToMatch(this.getCurrentUserInfo(), matchInfo, mailTo);
      }
    }
    else
    {
      for (String mailTo : mails)
      {
        User user = UserManager.getByEmail(mailTo);
        if (user != null)
        {
          sendInviteFriendToMatch(this.getCurrentUserInfo(), matchInfo, mailTo);
        }
      }
    }
    sendInviteOk=true;
    String lastMessage = getText("message.convocazioni.inviate");
    UserContext.getInstance().setLastMessage(lastMessage);
    return SUCCESS;
  }

  public String importContacts()
  {
    guiContacts = new ArrayList<GuiContact>();
    TreeSet sortedContacts = new TreeSet<GuiContact>();

    String importUrl = AppContext.getInstance().getImportContactURL(UserContext.getInstance().getUser().getNationalityCountry().getId());
    String step = AppContext.getInstance().getImportContactStep();
    String importer = AppContext.getInstance().getImportContactImporter();
    String importContactStr = ImportContactManager.importContact(importUrl, this.account, this.password, this.provider, step, importer);
    if (importContactStr != null && !importContactStr.equals(""))
    {
      if (checkError(importContactStr))
      {
        String[] importContactSplit = importContactStr.split(",");
        for (String mail : importContactSplit)
        {
          GuiContact contact;
          String[] mailAndName = StringUtils.split(mail, "|");
          if (DataValidator.checkEmail(mailAndName[0]))
          {
            User userToCheck = UserManager.getByEmail(mailAndName[0]);
            if (mailAndName.length > 1)
            {
              String name = StringUtils.substringBefore(mailAndName[1], "@");
              name=name.replace(".", " ");
              contact = new GuiContact(mailAndName[0],name,userToCheck!=null);
            }
            else
            {
              String name = StringUtils.substringBefore(mailAndName[1], "@");
              name=name.replace(".", " ");
              contact = new GuiContact(mailAndName[0], name ,userToCheck!=null);
            }
            sortedContacts.add(contact);
          }
        }
        guiContacts = new ArrayList(sortedContacts);
        Comparator<GuiContact> c = GuiContact.NAME_ORDER;
        Collections.sort(guiContacts, c);

      }
      else
      {
        if (importContactStr.contains(ERROR_INVALID_PROVIDER))
        {
          importContactStr = DB_KEY_ERROR_INVALID_PROVIDER;
        }
        addFieldError("errorImport", getText("error.importContacts." + importContactStr));
        return ERROR;
      }

    }
    return "importSuccess";
  }
  
   public String sendInviteToContacts()
   {
    MatchInfo matchInfo = InfoProvider.getMatchInfo(this.idMatch);
    for (String mailTo : contacts)
    {
      sendInviteFriendToMatch(this.getCurrentUserInfo(), matchInfo, mailTo);
    }
    if (sendInviteOk)
    {
      String lastMessage = getText("message.convocazioni.inviate");
      UserContext.getInstance().setLastMessage(lastMessage);
    }
    return SUCCESS;
  }
  

  //<editor-fold defaultstate="collapsed" desc="validate">

  public void validateExecute()
  {
      List<String> mails = EmailManager.split(this.addresses);
      for (String mail : mails)
      {
        if (DataValidator.checkEmail(mail) == false)
        {
          addActionError(this.getTextArgs("error.email.singolaEmailNonValida", mail));
          addFieldError("mailsError", this.getTextArgs("error.email.singolaEmailNonValida", mail));
        }
      }
  }

  //</editor-fold>
  
  // </editor-fold>
  
  //<editor-fold defaultstate="collapsed" desc="-- Private Methods --">
  private void sendInviteFriendToMatch(UserInfo userInfo, MatchInfo matchInfo, String mailTo)
  {
    Result<Boolean> rEmailSend = EmailManager.inviteOutersToMatch(userInfo, matchInfo, mailTo, this.freeText, this.getCurrentObjLanguage(), getCurrentCobrand());
    if (rEmailSend.getValue() == true)
    {
       sendInviteOk=true;
    }
  }

  //</editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public int getIdMatch()
  {
    return idMatch;
  }

  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  public String getMessage()
  {
    return message;
  }

  /**
   * @return the titleProvince
   */
  public String getTitleProvince()
  {
    return titleProvince;
  }

  public List<GuiContact> getGuiContacts()
  {
    return guiContacts;
  }

  public void setGuiContacts(List<GuiContact> guiContacts)
  {
    this.guiContacts = guiContacts;
  }
  // </editor-fold>
  
  
  
  public static void main(String[] args)
  {
  }

  /**
   * @return the sendInviteOk
   */
  public boolean isSendInviteOk()
  {
    return sendInviteOk;
  }

  
}//END CLASS
