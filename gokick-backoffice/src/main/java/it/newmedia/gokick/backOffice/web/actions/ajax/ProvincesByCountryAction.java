package it.newmedia.gokick.backOffice.web.actions.ajax;


import it.newmedia.gokick.backOffice.manager.ProvinceManager;
import it.newmedia.gokick.backOffice.web.actions.ABaseActionSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class ProvincesByCountryAction extends ABaseActionSupport {


  //<editor-fold defaultstate="collapsed" desc="-- MEMBERS --">

  private int idCountry;
  private int onlyWithUsers;
  private int onlyWithSportCenters;
  private int withMatches;
  private List<Object[]> currentProvincesList;
  // </editor-fold>

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
          this.currentProvincesList = ProvinceManager.getAllWithMatchesProjection(this.idCountry);
        }
        else
        {
          this.currentProvincesList = ProvinceManager.getAllProjection(
                  this.idCountry,
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
  
  //<editor-fold defaultstate="collapsed" desc="-- GETTERS/SETTERS --">

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
  {
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
  // </editor-fold>

}
