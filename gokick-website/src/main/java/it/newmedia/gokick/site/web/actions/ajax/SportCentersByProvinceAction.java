package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.managers.SportCenterManager;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;
import java.util.List;

/**
 Classe contenente le azioni per recuperare una lista di oggetti contenti id Sport center e nome, di una provincia, secondo i parametri specificati
 *
 */
public class SportCentersByProvinceAction extends ABaseActionSupport
{

  private int idProvince;
  private List<Object[]> currentSportCentersList;

  @Override
  public String execute()
  {
    try
    {
      this.currentSportCentersList = SportCenterManager.getAllProjection(-1, this.idProvince, -1);
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

  public int getIdProvince()
  {
    return idProvince;
  }

  public void setIdProvince(int idProvince)
  {
    this.idProvince = idProvince;
  }
}
