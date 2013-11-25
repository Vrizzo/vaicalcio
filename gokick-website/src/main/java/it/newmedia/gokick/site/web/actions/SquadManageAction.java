package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.SquadManager;
import it.newmedia.gokick.site.providers.InfoProvider;

/**
 *
 * Classe contenente le azioni per gestire le relazioni tra gli utenti e le rose.
 * invitare utenti ad unirsi,richiedere di unirsi ad una rosa, accettare/rimuovere inviti fatti o ricevuti,
 * rimuovere utenti dalla propria rosa
 */
public class SquadManageAction extends AuthenticationBaseAction
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idUser;
  private int idSquad;
  private String nameSurName;
  private boolean boxFriendRequestsVisible;
  private String dataTableKey;

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
   public String inviteUserBox()
  {
    if (this.idUser <= 0)
    {
      return ERROR;
    }
    UserInfo userInfo = InfoProvider.getUserInfo(idUser);
    this.nameSurName=userInfo.getName() + " " + userInfo.getSurname();

    return SUCCESS;
  }

  public String inviteUser()
  {
    if (this.idUser <= 0)
    {
      return ERROR;
    }

    User user = SquadManager.inviteUser(this.idUser, UserContext.getInstance().getUser(), getCurrentObjLanguage(), getCurrentCobrand());
    if (user == null)
    {
      return ERROR;
    }

//    String message = getText("messaggio.amicizia.invitoUtente"); //invito richiesto dal box userDetails
//    UserContext.getInstance().setLastMessage(message);   //non setto il messaggio, il box deve rimanere aperto

    return SUCCESS;
  }

  public String acceptInvite()
  {
    if (this.idUser <= 0)
    {
      return ERROR;
    }

    User user = SquadManager.acceptInvite(this.idUser, UserContext.getInstance().getUser(), getCurrentObjLanguage(), getCurrentCobrand());
    if (user == null)
    {
      return ERROR;
    }

    String message = getText("messaggio.amicizia.accettoRichiesta");
    message = message.replace(Constants.AUTOREPLACEMENT__USER_FIRSTNAME, user.getFirstName());
    message = message.replace(Constants.AUTOREPLACEMENT__USER_LASTNAME, user.getLastName());
    UserContext.getInstance().setLastMessage(message);

    this.boxFriendRequestsVisible=true;

    return SUCCESS;
  }

  public String notAcceptInvite()
  {
    if (this.idUser <= 0)
    {
      return ERROR;
    }

    User user = SquadManager.notAcceptInvite(this.idUser, this.getCurrentIdUser(), getCurrentObjLanguage(), getCurrentCobrand());
    if (user == null)
    {
      return ERROR;
    }

    String message = getText("messaggio.amicizia.rifiutoRichiesta");
    message = message.replace(Constants.AUTOREPLACEMENT__USER_FIRSTNAME, user.getFirstName());
    message = message.replace(Constants.AUTOREPLACEMENT__USER_LASTNAME, user.getLastName());
    UserContext.getInstance().setLastMessage(message);

    this.boxFriendRequestsVisible=true;

    return SUCCESS;
  }

  public String removeInvite()
  {
    if (this.idUser <= 0)
    {
      return ERROR;
    }

    User user = SquadManager.removeInvite(this.idUser, UserContext.getInstance().getUser(), getCurrentObjLanguage(), getCurrentCobrand());
    if (user == null)
    {
      return ERROR;
    }

    String message = getText("messaggio.amicizia.ritiraRichiesta");
    message = message.replace(Constants.AUTOREPLACEMENT__USER_FIRSTNAME, user.getFirstName());
    message = message.replace(Constants.AUTOREPLACEMENT__USER_LASTNAME, user.getLastName());
    UserContext.getInstance().setLastMessage(message);

    this.boxFriendRequestsVisible=true;

    return SUCCESS;
  }

  public String removeUser()
  {
    if (this.idUser <= 0)
    {
      return ERROR;
    }
    
    User user = SquadManager.removeUser(this.idUser, UserContext.getInstance().getUser(), getCurrentObjLanguage(), getCurrentCobrand());
    if (user == null)
    {
      return ERROR;
    }

    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public int getIdUser()
  {
    return idUser;
  }

  public void setIdUser(int idUser)
  {
    this.idUser = idUser;
  }

  public int getIdSquad()
  {
    return idSquad;
  }

  public void setIdSquad(int idSquad)
  {
    this.idSquad = idSquad;
  }
  /**
   * @return the nameSurName
   */
  public String getNameSurName()
  {
    return nameSurName;
  }
  /**
   * @return the boxFriendRequestsVisible
   */
  public boolean isBoxFriendRequestsVisible()
  {
    return boxFriendRequestsVisible;
  }
  /**
   * @return the dataTableKey
   */
  public String getDataTableKey()
  {
    return dataTableKey;
  }

  /**
   * @param dataTableKey the dataTableKey to set
   */
  public void setDataTableKey(String dataTableKey)
  {
    this.dataTableKey = dataTableKey;
  }

  // </editor-fold>
}
