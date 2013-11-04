package it.newmedia.gokick.backOffice.web.actions.ajax;


import it.newmedia.gokick.backOffice.manager.CityManager;
import it.newmedia.gokick.backOffice.web.actions.ABaseActionSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 */
public class CitiesByProvinceAction extends ABaseActionSupport {

  private int idProvince;
  private int onlyWithUsers;
  private int onlyWithSportCenters;
  private int withMatches;
  private List<Object[]> currentCitiesList;

  @Override
  public void validate()
  {
    super.validate();
    //Ripulisco e ignoro gli errori che potrei aver ricevuto, per esempio problemi di conversione dei parametri in request.
    //Essendo interi varranno 0!!
    clearErrors();
  }


  public String execute()
  {
    this.currentCitiesList = new ArrayList<Object[]>();
    try
    {
      if (this.idProvince > 0)
      {
        if (this.withMatches == 1)
        {
          this.currentCitiesList = CityManager.getAllWithMatchesProjection(this.idProvince);
        }
        else
        {
          this.currentCitiesList = CityManager.getAllProjection(
                  this.idProvince,
                  this.onlyWithUsers == 1,
                  this.onlyWithSportCenters == 1);
        }
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
    return SUCCESS;
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

  public List<Object[]> getCurrentCitiesList()
  {
    return currentCitiesList;
  }
}
