package it.newmedia.gokick.backOffice.web.actions;

import org.apache.commons.lang.StringUtils;
import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.backOffice.AppContext;
import it.newmedia.gokick.backOffice.UserContext;
import it.newmedia.gokick.backOffice.manager.EmailManager;
import it.newmedia.gokick.backOffice.manager.MessageManager;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.results.Result;
import java.lang.Boolean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Classe contenente l'azione per visualizzare la foto caricata dall'utente per la figurina.
 */
public class SendMessagesAction extends ABaseActionSupport implements Preparable {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private String receiverType;
  private int receiverPlace;
  private HashMap<Integer,String> receiverMapList;
  private String boardMessage;
  private String mailObject;
  private String mailBody;
  private String userPwd;
  private boolean hasErrors;
  private boolean messageSent;
  private List<String> mailsSent;
  private List<String> mailsNotSent;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public void prepare() throws Exception
  {

  }

  @Override
  public String input()
  {
    return INPUT;
  }

  public String send()
  {
    this.mailsSent=new ArrayList<String>();
    this.mailsNotSent=new ArrayList<String>();
    
    // <editor-fold defaultstate="collapsed" desc="-- MESSAGE FROM ADMINGOKICKERS AND ADMINPICTURES --">
      if (this.receiverType.equals("gokickers"))
      {
        List<User> userList = UserContext.getInstance().getUserReceiverList();
        if (this.receiverPlace==1) //bacheca
        {
          boolean sendMessage = MessageManager.sendMessageToUserList(userList, this.boardMessage);
          if (sendMessage)
                this.messageSent=true;
          else
                addActionError("errors sending Messages");
                this.hasErrors=true;
        }
        else if (this.receiverPlace==2) //email
        {
          for (User user : userList)
          {

              Result<Boolean> sendEmail = EmailManager.sendEmailToUser(user, null, this.mailObject, this.mailBody);
              if (sendEmail.getValue())
              {
                  this.messageSent=true;
                  this.mailsSent.add(user.getEmail());
              }
              else
              {
                  this.mailsNotSent.add("user id: " + user.getId() + ";  mail: " + user.getEmail());
              }
          }
         }
       }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- MESSAGE FROM ADMINSPORTCENTERS --">
     else if (this.receiverType.equals("sportCenters"))
     {
          List<SportCenter> sportCenterList = UserContext.getInstance().getSportCenterReceiverList();
          List<User> userList=new ArrayList<User>();
            for (SportCenter sportCenter : sportCenterList)
            {
              if(!userList.contains(sportCenter.getUserAuthor()))
                userList.add(sportCenter.getUserAuthor());
            }
          if (this.receiverPlace==4) //mail segnalatore
          {
            for (User user : userList)
            {
              Result<Boolean> sendEmail = EmailManager.sendEmailToUser(user, null, this.mailObject, this.mailBody);
              if (sendEmail.getValue())
              {
                  this.messageSent=true;
                  this.mailsSent.add(user.getEmail());
              }
              else
              {
                  this.mailsNotSent.add("user id: " + user.getId() + ";  mail: " + user.getEmail());
              }
            }
          }
          else if (this.receiverPlace==5) //mail campo
          {
            for (SportCenter sportCenter : sportCenterList)
            {
              Result<Boolean> sendEmail;
              if (StringUtils.isNotBlank(sportCenter.getEmail()))
                sendEmail = EmailManager.sendEmailToSportCenter(sportCenter, null, this.mailObject, this.mailBody);
              else
                sendEmail = new Result<Boolean>(false, false);

              if (sendEmail.getValue())
              {
                  this.messageSent=true;
                  this.mailsSent.add(sportCenter.getEmail());
              }
              else
              {
                 this.mailsNotSent.add("campo id: " + sportCenter.getId() + ";  mail: " + sportCenter.getEmail());
              }
            }
          }
          else if (this.receiverPlace==3) //bacheca
          {
            userList=new ArrayList<User>();
            for (SportCenter sportCenter : sportCenterList)
            {
              if(!userList.contains(sportCenter.getUserAuthor()))
                userList.add(sportCenter.getUserAuthor());
            }
            boolean sendMessage = MessageManager.sendMessageToUserList(userList, boardMessage);
            if (sendMessage)
              this.messageSent=true;
            else
              addActionError("errors sending Messages");
              this.hasErrors=true;
          }
     }




      // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- MESSAGE FROM ADMINMATCHES --">
      else if (this.receiverType.equals("matches"))
      {
          List<Match> matchList = UserContext.getInstance().getMatchReceiverList();
          if (this.receiverPlace==8) //mail organizzatore + mail iscritti
          {
            List<User> userList=new ArrayList<User>();
            for (Match match : matchList)
            {
              Iterator<Player> iPlayers = match.getPlayerList().iterator();
              while(iPlayers.hasNext())
              {
                Player player = iPlayers.next();
                    if ((player.getEnumPlayerStatus().equals(EnumPlayerStatus.OwnerRegistered) ||
                            player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRegistered) ||
                                player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequestRegistered)) && (!userList.contains(player.getUser())))
                    {
                        userList.add(player.getUser());
                    }
              }
            }
            
            for (User user : userList)
            {
              Result<Boolean> sendEmail;
              if (StringUtils.isNotBlank(user.getEmail()))
                sendEmail = EmailManager.sendEmailToUser(user, null, this.mailObject, this.mailBody);
              else
                sendEmail = new Result<Boolean>(false, false);

              if (sendEmail.getValue())
              {
                  this.messageSent=true;
                  this.mailsSent.add(user.getEmail());
              }
              else
              {
                  //addActionError(user.getEmail());
                  this.mailsNotSent.add("user id: " + user.getId() + ";  mail: " + user.getEmail());
                  //this.hasErrors=true;
              }
            }
          }
          else if (this.receiverPlace==9) //mail organizzatore
          {
            List<User> userList=new ArrayList<User>();
            for (Match match : matchList)
            {
              if(!userList.contains(match.getUserOwner()))
                  userList.add(match.getUserOwner());
            }
            for (User user : userList)
            {
              Result<Boolean> sendEmail;
              if (StringUtils.isNotBlank(user.getEmail()))
                sendEmail = EmailManager.sendEmailToUser(user, null, this.mailObject, this.mailBody);
              else
                sendEmail = new Result<Boolean>(false, false);

              if (sendEmail.getValue())
              {
                  this.messageSent=true;
                  this.mailsSent.add(user.getEmail());
              }
              else
              {
                //addActionError(user.getEmail());
                this.mailsNotSent.add("user id: " + user.getId() + ";  mail: " + user.getEmail());
                //this.hasErrors=true;
              }
            }
          }



          else if (this.receiverPlace==6) //bacheca organizzatore
          {
            List<User> userList=new ArrayList<User>();
            for (Match match : matchList)
            {
              if(!userList.contains(match.getUserOwner()))
                  userList.add(match.getUserOwner());
            }
            boolean sendMessage = MessageManager.sendMessageToUserList(userList, boardMessage);
            if (sendMessage)
              this.messageSent=true;
            else
              addActionError("errors sending Messages");
              this.hasErrors=true;
          }
          else if (this.receiverPlace==7) //bacheca organizzatore + bacheca iscritti
          {
            List<User> userList=new ArrayList<User>();
            for (Match match : matchList)
            {
              Iterator<Player> iPlayers = match.getPlayerList().iterator();
              while(iPlayers.hasNext())
              {
                Player player = iPlayers.next();
                if(!userList.contains(player.getUser()))
                {
                  if (player.getEnumPlayerStatus().equals(EnumPlayerStatus.OwnerRegistered) ||
                          player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRegistered) ||
                              player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequestRegistered))
                  userList.add(player.getUser());
                }
                
              }
            }
            boolean sendMessage = MessageManager.sendMessageToUserList(userList, boardMessage);
            if (sendMessage)
              this.messageSent=true;
            else
              addActionError("errors sending Messages");
              this.hasErrors=true;
          }
      }
      // </editor-fold>

     return INPUT;
  }

  @Override
  public void validate()
  {
    if (this.getReceiverPlace()==2 || this.getReceiverPlace()==4 || this.getReceiverPlace()==5)
    {
      if (StringUtils.isBlank(this.getMailObject()))
              addFieldError("mailObjError", "manca l'oggetto della mail");
      if (StringUtils.isBlank(this.getMailBody()))
        addFieldError("mailBodyError", "manca il corpo della mail");
    }
    else if (this.getReceiverPlace()==1 || this.getReceiverPlace()==3)
    {
      if (StringUtils.isBlank(this.boardMessage))
        addFieldError("boardTextError", "manca il testo del messaggio");
    }
    
    if (StringUtils.isBlank(this.userPwd))
      addFieldError("pwdError", "inserire la password");
    else if (!this.userPwd.equals(AppContext.getInstance().getSendMailPwd()))
      addFieldError("pwdError", "password errata");
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  /**
   * @return the receiverType
   */
  public String getReceiverType()
  {
    return receiverType;
  }

  /**
   * @param receiverType the receiverType to set
   */
  public void setReceiverType(String receiverType)
  {
    this.receiverType = receiverType;
  }

  /**
   * @return the receiverMapList
   */
  public HashMap<Integer, String> getReceiverMapList()
  {
    receiverMapList=new HashMap<Integer,String>();
    if (this.getReceiverType().equals("gokickers"))
    {
      receiverMapList.put(1, "bacheca Gokicker");
      receiverMapList.put(2, "email Gokicker");
    }
    else if (this.getReceiverType().equals("sportCenters"))
    {
      receiverMapList.put(3, "bacheca segnalatore");
      receiverMapList.put(4, "email segnalatore");
      receiverMapList.put(5, "email campo");
    }
    else if (this.getReceiverType().equals("matches"))
    {
      receiverMapList.put(6, "bacheca organizzattore");
      receiverMapList.put(9, "email organizzattore");
      receiverMapList.put(7, "bacheca organizzattore + bacheca gokickers iscritti");
      receiverMapList.put(8, "email organizzattore + email gokickers iscritti");
    }
    return receiverMapList;
  }

  /**
   * @param boardMessage the boardMessage to set
   */
  public void setBoardMessage(String boardMessage)
  {
    this.boardMessage = boardMessage;
  }

  /**
   * @param mailObject the mailObject to set
   */
  public void setMailObject(String mailObject)
  {
    this.mailObject = mailObject;
  }

  /**
   * @param userPwd the userPwd to set
   */
  public void setUserPwd(String userPwd)
  {
    this.userPwd = userPwd;
  }

  /**
   * @return the boardMessage
   */
  public String getBoardMessage()
  {
    return boardMessage;
  }

  /**
   * @return the userPwd
   */
  public String getUserPwd()
  {
    return userPwd;
  }
 
  /**
   * @return the mailObject
   */
  public String getMailObject()
  {
    return mailObject;
  }

  /**
   * @return the receiverPlace
   */
  public int getReceiverPlace()
  {
    return receiverPlace;
  }

  /**
   * @param receiverPlace the receiverPlace to set
   */
  public void setReceiverPlace(int receiverPlace)
  {
    this.receiverPlace = receiverPlace;
  }

  /**
   * @return the mailBody
   */
  public String getMailBody()
  {
    return mailBody;
  }

  /**
   * @param mailBody the mailBody to set
   */
  public void setMailBody(String mailBody)
  {
    this.mailBody = mailBody;
  }

  /**
   * @return the hasErrors
   */
  public boolean isHasErrors()
  {
    return hasErrors;
  }

  /**
   * @param hasErrors the hasErrors to set
   */
  public void setHasErrors(boolean hasErrors)
  {
    this.hasErrors = hasErrors;
  }
  
  /**
   * @return the messageSent
   */
  public boolean isMessageSent()
  {
    return messageSent;
  }

  /**
   * @return the mailsSent
   */
  public List<String> getMailsSent()
  {
    return mailsSent;
  }
  
  /**
   * @return the mailsNotSent
   */
  public List<String> getMailsNotSent()
  {
    return mailsNotSent;
  }
  // </editor-fold>

}