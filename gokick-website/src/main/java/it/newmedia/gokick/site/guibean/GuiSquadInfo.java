package it.newmedia.gokick.site.guibean;

import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.PlayerRoleInfo;
import it.newmedia.gokick.site.infos.SquadInfo;
import it.newmedia.gokick.site.infos.StatisticInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.StatisticInfoManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che contine le informazioni sulla squadra dell'user in oggetto  per poterle  visualizzare all'interno dell'applicazione
 * @author ggeroldi
 */
public class GuiSquadInfo extends AGuiBean
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  private SquadInfo squadInfo;
  private String statisticPeriod;
  private List<StatisticInfo> statisticInfoList;
  private int playersTot;
  private List playersRolesCount;
  private StatisticInfo ownerStatisticInfo;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --"  >
  /**
   *
   * @return
   */
  public SquadInfo getSquadInfo()
  {
    return squadInfo;
  }

  /**
   *
   * @param squadInfo
   */
  public void setSquadInfo(SquadInfo squadInfo)
  {
    this.squadInfo = squadInfo;
  }

  /**
   *
   * @return
   */
  public List<StatisticInfo> getStatisticInfoList()
  {
    return StatisticInfoManager.getAllByIdSquadAndPeriod(this.squadInfo.getId(), this.statisticPeriod);
  }

  /**
   *
   * @return
   */
  public String getStatisticPeriod()
  {
    return statisticPeriod;
  }

  /**
   *
   * @param statisticPeriod
   */
  public void setStatisticPeriod(String statisticPeriod)
  {
    this.statisticPeriod = statisticPeriod;
  }

  /**
   *
   * @return
   */
  public List getPlayersRolesCount()
  {
    if (this.statisticInfoList == null)
    {
      this.statisticInfoList = StatisticInfoManager.getAllByIdSquadAndPeriod(this.squadInfo.getId(), this.statisticPeriod);
    }
    return countPlayersByRoles();
  }

  /**
   *
   * @param playersRolesCount
   */
  public void setPlayersRolesCount(List playersRolesCount)
  {
    this.playersRolesCount = playersRolesCount;
  }

  /**
   *
   * @return
   */
  public int getPlayersTot()
  {
    if (this.statisticInfoList == null)
    {
      this.statisticInfoList = StatisticInfoManager.getAllByIdSquadAndPeriod(this.squadInfo.getId(), this.statisticPeriod);
    }
    return this.statisticInfoList.size();
  }

  /**
   *
   * @param playersTot
   */
  public void setPlayersTot(int playersTot)
  {
    this.playersTot = playersTot;
  }

  /**
   *
   * @return
   */
  public StatisticInfo getOwnerStatisticInfo()
  {

    return statisticInfoList.get(0);
  }

  /**
   *
   * @param ownerStatisticInfo
   */
  public void setOwnerStatisticInfo(StatisticInfo ownerStatisticInfo)
  {
    this.ownerStatisticInfo = ownerStatisticInfo;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  /**
   *
   */
  public GuiSquadInfo()
  {
    this.statisticPeriod = Constants.STATISTIC_PERIOD__NONE;
  }

  /**
   *
   * @param statisticPeriod
   */
  public GuiSquadInfo(String statisticPeriod)
  {
    this.statisticPeriod = statisticPeriod;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

 /**
 *
 * @return una lista di interi rappresentanti il numero di giocatori per ruolo
 */
  private List countPlayersByRoles()
  {
    ArrayList countPlayersByRolesList = new ArrayList();

    List<PlayerRoleInfo> playerRoleInfoList = AppContext.getInstance().getAllPlayerRoleInfo(UserContext.getInstance().getLanguage(), UserContext.getInstance().getCurrentCobrand());
    if (playerRoleInfoList == null || playerRoleInfoList.size() <= 0)
    {
      return countPlayersByRolesList;
    }

    for (PlayerRoleInfo playerRoleInfo : playerRoleInfoList)
    {
      int count = 0;
      for (int i = 0; i < this.statisticInfoList.size(); i++)
      {
        UserInfo userInfo = this.statisticInfoList.get(i).getUserInfo();
        if (userInfo.getPlayerRole().equalsIgnoreCase(playerRoleInfo.getName()))
        {
          count++;
        }
      }
      countPlayersByRolesList.add(count);
    }

    return countPlayersByRolesList;
  }

  // </editor-fold>
}
