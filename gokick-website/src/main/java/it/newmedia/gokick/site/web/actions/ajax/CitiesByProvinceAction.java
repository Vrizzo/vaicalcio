package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.CityManager;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *Classe contenente le azioni per recuperare una lista di oggetti contenti id città e nome, secondo i parametri specificati
 * 
 */
public class CitiesByProvinceAction extends ABaseActionSupport {

  private int idProvince;
  private int onlyWithUsers;
  private int onlyWithSportCenters;
  private int withMatches;
  private int past;
  private int usersOnMarket;
  private List<Object[]> currentCitiesList;

  @Override
  public void validate()
  {
    super.validate();
    //Ripulisco e ignoro gli errori che potrei aver ricevuto, per esempio problemi di conversione dei parametri in request.
    //Essendo interi varranno 0!!
    clearErrors();
  }

  @Override
  public String execute()
  {
    this.currentCitiesList = new ArrayList<Object[]>();
    try
    {
      if (this.idProvince > 0)
      {
        if (this.withMatches == 1)
        {
          if(past==1)
            this.currentCitiesList = CityManager.getAllWithPastMatchesProjection(this.idProvince);
          else
            this.currentCitiesList = CityManager.getAllWithMatchesProjection(this.idProvince);
        }
        else
        {
          this.currentCitiesList = CityManager.getAllProjection(
                  this.idProvince,
                  this.onlyWithUsers == 1,
                  this.onlyWithSportCenters == 1,
                  this.usersOnMarket == 1);
        }
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
    return SUCCESS;
  }

  // <editor-fold defaultstate="collapsed" desc="-- GETTERS/SETTERS --">
  public List<Object[]> getCurrentCitiesList()
  { //UserContext.getInstance().isLoggedIn() --> aggiunto per via dell'utente in registrazione...
    if (UserContext.getInstance().isLoggedIn()
            && this.idProvince==UserContext.getInstance().getUser().getProvince().getId()
              && this.past!=1)
      this.currentCitiesList=CityManager.addCityObject(currentCitiesList, UserContext.getInstance().getUser().getCity());
    return currentCitiesList;
  }

  public int getIdProvince()
  {
    return idProvince;
  }

  public void setIdProvince(int idProvince)
  {
    this.idProvince = idProvince;
  }

  public int getWithMatches()
  {
    return withMatches;
  }

  public void setWithMatches(int withMatches)
  {
    this.withMatches = withMatches;
  }

  public int getOnlyWithSportCenters()
  {
    return onlyWithSportCenters;
  }

  public void setOnlyWithSportCenters(int onlyWithSportCenters)
  {
    this.onlyWithSportCenters = onlyWithSportCenters;
  }

  public int getOnlyWithUsers()
  {
    return onlyWithUsers;
  }

  public void setOnlyWithUsers(int onlyWithUsers)
  {
    this.onlyWithUsers = onlyWithUsers;
  }

  /**
   * @param past the past to set
   */
  public void setPast(int past)
  {
    this.past = past;
  }

  /**
   * @param usersOnMarket the usersOnMarket to set
   */
  public void setUsersOnMarket(int usersOnMarket)
  {
    this.usersOnMarket = usersOnMarket;
  }
  // </editor-fold>

  
}
