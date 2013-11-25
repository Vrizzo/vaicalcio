package it.newmedia.gokick.site.web.actions.user;

import it.newmedia.gokick.site.web.actions.*;
import it.newmedia.gokick.cache.InfoCache;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.managers.CityManager;
import it.newmedia.gokick.site.managers.UtilManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione per abilitare un utente in stato pending dopo essersi registrato
 */
public class UserEnableAction extends ABaseActionSupport
{
// ------------------------------ FIELDS ------------------------------

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int id;
  private String checkPending;

// --------------------- GETTER / SETTER METHODS ---------------------

  public String getCheckPending()
  {
    return checkPending;
  }

  public void setCheckPending(String checkPending)
  {
    this.checkPending = checkPending;
  }

// </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Action ---------------------

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    User currentUser;
    if (this.id <= 0 || this.checkPending == null || this.checkPending.isEmpty())
    {
      addActionMessage(getText("error.user.enable.missingParam"));
      return SUCCESS;
    }

    currentUser = UtilManager.getUserByIdAndCheckPending(this.id, this.checkPending);
    if (currentUser == null)
    {
      addActionMessage(getText("error.user.enable.missingUser"));
      return SUCCESS;
    }
    if (!currentUser.getUserStatus().equals(EnumUserStatus.Pending.getValue()))
    {
      addActionMessage(getText("error.user.enable.missingUser"));
      return SUCCESS;
    }

    currentUser.setCheckPending("");
    currentUser.setUserStatus(EnumUserStatus.Enabled.getValue());
    try
    {
      DAOFactory.getInstance().getUserDAO().makePersistent(currentUser);
    } catch (Exception e)
    {
      logger.error(e, e);
      return ERROR;
    }

    if (currentUser.getCountry() != null)
    {
      UtilManager.increaseCountryUserTot(currentUser.getCountry().getId());
    }
    if (currentUser.getProvince() != null)
    {
      UtilManager.increaseProvinceUserTot(currentUser.getProvince().getId());
    }
    if (currentUser.getCity() != null)
    {
      CityManager.increaseTotUsers(currentUser.getCity().getId());
    }

    addActionMessage(getText("message.user.enable.confirm"));

    InfoCache.removeFromCache(Constants.KEY_ON_CACHE__GOKICKER_COUNT);
    InfoProvider.removeUserInfo(this.id);
    

    return SUCCESS;
  }

  // </editor-fold>
}