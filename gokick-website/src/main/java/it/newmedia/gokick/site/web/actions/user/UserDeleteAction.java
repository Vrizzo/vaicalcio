package it.newmedia.gokick.site.web.actions.user;

import it.newmedia.gokick.site.web.actions.*;
import it.newmedia.gokick.data.hibernate.beans.Message;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.data.hibernate.beans.UserInvitation;
import it.newmedia.gokick.data.hibernate.beans.UserSquad;
import it.newmedia.gokick.data.hibernate.dao.CityDAO;
import it.newmedia.gokick.data.hibernate.dao.MessageDAO;
import it.newmedia.gokick.data.hibernate.dao.PictureCardDAO;
import it.newmedia.gokick.data.hibernate.dao.SquadDAO;
import it.newmedia.gokick.data.hibernate.dao.UserDAO;
import it.newmedia.gokick.data.hibernate.dao.UserInvitationDAO;
import it.newmedia.gokick.data.hibernate.dao.UserSquadDAO;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.managers.UtilManager;

import java.util.Date;
import java.util.List;

import it.newmedia.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione per eliminare un utente.(non utilizzata?)
 */
public class UserDeleteAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private String email;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    User userToDelete;
    if (StringUtils.isBlank(this.email))
    {
      addActionMessage(getText("errore.utente.cancellazione.parametroAssente"));
      return SUCCESS;
    }

    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    try
    {
      DAOFactory factory = DAOFactory.getInstance();
      UserDAO userDAO = factory.getUserDAO();

      userToDelete = userDAO.getByEmail(this.email);
      if (userToDelete == null)
      {
        addActionMessage(getText("errore.utente.cancellazione.utenteAssente"));
        HibernateSessionHolder.rollbackTransaction();
        return SUCCESS;
      }

      PictureCardDAO pictureCardDAO = factory.getPictureCardDAO();
      for (PictureCard pictureCard : userToDelete.getPictureCards())
      {
        pictureCardDAO.makeTransient(pictureCard);
      }

      //Non è necessario cancellare le Squad dato
      //che nelle impostazioni di Hibernate
      //c'è * cascade="all-delete-orphan"

      //Non è necessario cancellare le UserSquad di ogni Squad dato
      //che nelle impostazioni di Hibernate
      //c'è * cascade="all-delete-orphan"

      //Non è necessario cancellare le UserInvitation dato
      //che nelle impostazioni di Hibernate
      //c'è * cascade="all-delete-orphan"

      MessageDAO messageDAO = factory.getMessageDAO();
      List<Message> messageList = messageDAO.getByIdUserOwner(userToDelete.getId());
      for (Message message : messageList)
      {
        messageDAO.makeTransient(message);
      }

      UserInvitationDAO userInvitationDAO = factory.getUserInvitationDAO();
      UserInvitation userInvitation = userInvitationDAO.getByIdUser(userToDelete.getId());
      if( userInvitation != null)
      {
        userInvitation.setUser(null);
      }

      userDAO.makeTransient(userToDelete, false);

      //TODO: questa parte non dovrebbe servire
      //La logica dei contatori non esiste più verificare e eliminare anche dal db totuser...
      if (userToDelete.getCountry() != null)
      {
        UtilManager.decreaseCountryUserTot(userToDelete.getCountry().getId());
      }
      if (userToDelete.getProvince() != null)
      {
        UtilManager.decreaseProvinceUserTot(userToDelete.getProvince().getId());
      }
      if (userToDelete.getCity() != null)
      {
        CityDAO cityDAO = factory.getCityDAO();
        cityDAO.decreaseTotUsers(userToDelete.getCity().getId());
      }


      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return ERROR;
    }

    addActionMessage(getText("messaggio.utente.cancellazione.conferma"));

    UserContext.getInstance().reset();

    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }
  // </editor-fold>
}
