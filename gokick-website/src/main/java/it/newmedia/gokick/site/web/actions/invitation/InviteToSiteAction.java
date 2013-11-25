package it.newmedia.gokick.site.web.actions.invitation;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiContact;
import it.newmedia.gokick.site.managers.EmailManager;
import it.newmedia.gokick.site.managers.ImportContactManager;
import it.newmedia.gokick.site.managers.UserManager;
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
 * Classe contenente le azioni per invitare non iscritti al sito, tramite mail
 */
public class InviteToSiteAction extends AInviteAction implements Preparable
{

  private ArrayList<GuiContact> guiContacts;
  private boolean sendInviteOk;

  
  @Override
  public void prepare()
  {
    this.freeText = getText("message.invitaSito");
  }

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

  @Override
  public String execute()
  {
    List<String> mails = EmailManager.split(this.addresses);
    for (String mailTo : mails)
    {
      Result<Boolean> rEmailSend = EmailManager.inviteFriendsToSite(
              this.getCurrentUserInfo(),
              UserContext.getInstance().getUser().getEmail(),
              mailTo,
              freeText,
              this.getCurrentObjLanguage(),
              getCurrentCobrand());
      if (rEmailSend.getValue() == true)
      {
        addActionMessage(mailTo);
      }
      else
      {
        logger.error(rEmailSend.getErrorMessage());
        addActionError(mailTo);
      }
    }
    
    String lastMessage = getText("messagge.importContacts.invited");
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
        return INPUT;
      }
    }
    return "importSuccess";
  }

  public String sendInviteToContacts()
  {
    for (String mailTo : contacts)
    {
      sendInviteFriendToSite(mailTo);
    }
    if (sendInviteOk)
    {
      String lastMessage = getText("messagge.importContacts.invited");
      UserContext.getInstance().setLastMessage(lastMessage);
    }
    return SUCCESS;
  }
  
   private void sendInviteFriendToSite(String mailTo)
  {
    Result<Boolean> rEmailSend = EmailManager.inviteFriendsToSite(
              this.getCurrentUserInfo(),
              UserContext.getInstance().getUser().getEmail(),
              mailTo,
              freeText,
              this.getCurrentObjLanguage(),
              getCurrentCobrand());
    if (rEmailSend.getValue() == true)
    {
       sendInviteOk=true;
    }
  }

  
  
  //<editor-fold defaultstate="collapsed" desc="GETTERS/SETTERS">
  public ArrayList<GuiContact> getGuiContacts()
  {
    return guiContacts;
  }

  public void setGuiContacts(ArrayList<GuiContact> guiContacts)
  {
    this.guiContacts = guiContacts;
  }
  //</editor-fold>

  /**
   * @return the sendInviteOk
   */
  public boolean isSendInviteOk()
  {
    return sendInviteOk;
  }
}
