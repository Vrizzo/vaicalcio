package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.ProvinceManager;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;
import java.util.ArrayList;
import java.util.List;

/**
 Classe contenente le azioni per recuperare una lista di oggetti contenti id Provincia e nome, secondo i parametri specificati
 *
 */
public class ProvincesByCountryAction extends ABaseActionSupport {

  private int idCountry;
  private int onlyWithUsers;
  private int onlyWithSportCenters;
  private int withMatches;
  private int usersOnMarket;
  private List<Object[]> currentProvincesList;
  private int past;

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
    this.currentProvincesList = new ArrayList<Object[]>();
    try
    {
      if (this.idCountry > 0)
      {
        if (this.withMatches == 1)
        {
          if (this.past==1)
            this.currentProvincesList = ProvinceManager.getAllWithPastMatchesProjection(idCountry);
          else
            this.currentProvincesList = ProvinceManager.getAllWithMatchesProjection(this.idCountry);
        }
        else
        {
          this.currentProvincesList = ProvinceManager.getAllProjection(
                  this.idCountry,
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

  public int getIdCountry()
  {
    return idCountry;
  }

  public void setIdCountry(int idCountry)
  {
    this.idCountry = idCountry;
  }

  public int getWithMatches()
  {
    return withMatches;
  }

  public void setWithMatches(int withMatches)
  {
    this.withMatches = withMatches;
  }

  public List<Object[]> getCurrentProvincesList()
  {   //UserContext.getInstance().isLoggedIn() --> aggiunto per via dell'utente in registrazione...
    if (UserContext.getInstance().isLoggedIn() 
            && this.idCountry==UserContext.getInstance().getUser().getCountry().getId()
              && this.past!=1)
      this.currentProvincesList=ProvinceManager.addProvinceObject(currentProvincesList, UserContext.getInstance().getUser().getProvince());
    return currentProvincesList;
  }
  
  /**
   * @param onlyWithUsers the onlyWithUsers to set
   */
  public void setOnlyWithUsers(int onlyWithUsers)
  {
    this.onlyWithUsers = onlyWithUsers;
  }

  /**
   * @param onlyWithSportCenters the onlyWithSportCenters to set
   */
  public void setOnlyWithSportCenters(int onlyWithSportCenters)
  {
    this.onlyWithSportCenters = onlyWithSportCenters;
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
}
