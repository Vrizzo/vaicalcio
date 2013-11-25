package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.managers.SportCenterManager;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;
import java.util.List;

/**
 *Classe contenente le azioni per recuperare una lista di oggetti contenti id Sport center e nome, di una città, secondo i parametri specificati
 *
 */
public class SportCentersByCityAction extends ABaseActionSupport
{

  private int idCity;
  private List<Object[]> currentSportCentersList;

  @Override
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
