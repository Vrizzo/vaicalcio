package it.newmedia.gokick.backOffice.web.actions.ajax;

import it.newmedia.gokick.backOffice.manager.SportCenterManager;
import it.newmedia.gokick.backOffice.web.actions.ABaseActionSupport;
import java.util.List;

/**
 *
 * 
 */
public class SportCentersByCityAction extends ABaseActionSupport
{

  private int idCity;
  private List<Object[]> currentSportCentersList;

  public String execute()
  {
    try
    {
      this.currentSportCentersList = SportCenterManager.getAllProjection(-1, -1, this.idCity);
    } catch (Exception e)
    {
      logger.error(e, e);
    }
    return SUCCESS;
  }

  public List<Object[]> getCurrentSportCentersList()
  {
    return currentSportCentersList;
  }

  public void setCurrentSportCentersList(List<Object[]> currentSportCentersList)
  {
    this.currentSportCentersList = currentSportCentersList;
  }

  public int getIdCity()
  {
    return idCity;
  }

  public void setIdCity(int idCity)
  {
    this.idCity = idCity;
  }
}
