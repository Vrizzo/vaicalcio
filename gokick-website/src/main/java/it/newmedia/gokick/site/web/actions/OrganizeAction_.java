package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.site.web.actions.user.UserEnableAction;
import it.newmedia.gokick.site.UserContext;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente le azioni per l'organizzazione delle attività.
 */
public class OrganizeAction_ extends AuthenticationBaseAction
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserEnableAction.class);
  private int squadUsersCount;

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public String execute()
  {
    if (UserContext.getInstance().getFirstSquad() != null)
    {
      this.squadUsersCount = UserContext.getInstance().getFirstSquad().getUserSquadList().size() - 1;
    }
    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public int getSquadUsersCount()
  {
    return squadUsersCount;
  }

  public void setSquadUsersCount(int squadUsersCount)
  {
    this.squadUsersCount = squadUsersCount;
  }
  // </editor-fold>
}
