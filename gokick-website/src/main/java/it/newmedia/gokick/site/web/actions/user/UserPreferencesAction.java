package it.newmedia.gokick.site.web.actions.user;

import it.newmedia.gokick.site.web.actions.*;
import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.SquadManager;
import it.newmedia.gokick.site.managers.UserManager;

/**
 *
 * Classe contenente le azioni per salvare le preferenze dell'utente riguardo notifiche , newsLetter ed anonimato
 */
public class UserPreferencesAction  extends AuthenticationBaseAction implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
private User userToUpdate;
private Squad squad;
 // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public void prepare() throws Exception
  {   
  }

  @Override
  public String input()
  {
    if (this.isLoggedIn())
    {
      this.userToUpdate = UserManager.getById(this.getCurrentIdUser());
      this.squad=SquadManager.getUserFirstSquad(this.getCurrentIdUser());
    }

    return INPUT;
  }

  @Override
  public void validate()
  {
    //User currentUser = this.getCurrentUser();
     /* Validazione nome */
//    if ((userPlayerToUpdate.getFirstName() == null) || (userPlayerToUpdate.getFirstName().length() == Constants.STRING_LENGHT_0))
//    {
//      addFieldError("userToUpdate.firstName", getText("error.firstName.required"));
//    }
//    

//    if (this.hasErrors())
//    {
//      this.errorsPresent = true;
//    }
//    else
//    {
//      this.errorsPresent = false;
//    }


  }

  public String update()
  {
    UserManager.updateUserPreference(userToUpdate,squad);
    String message = getText("message.preferenzeSalvate");
    UserContext.getInstance().setLastMessage(message);
    return SUCCESS;
  
    
  }
// </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >

  public User getUserToUpdate()
  {
    return userToUpdate;
  }

  public void setUserToUpdate(User userToUpdate)
  {
    this.userToUpdate = userToUpdate;
  }

  /**
   * @return the squad
   */
  public Squad getSquad()
  {
    return squad;
  }

  /**
   * @param squad the squad to set
   */
  public void setSquad(Squad squad)
  {
    this.squad = squad;
  }

  
  // </editor-fold>
}
