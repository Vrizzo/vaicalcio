package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiSquadInfo;
import it.newmedia.gokick.site.guibean.GuiStatisticPeriod;
import it.newmedia.gokick.site.infos.SquadInfo;
import it.newmedia.gokick.site.infos.StatisticInfo;
import it.newmedia.gokick.site.managers.SquadInfoManager;
import it.newmedia.gokick.site.managers.SquadManager;
import it.newmedia.gokick.site.managers.StatisticInfoManager;
import it.newmedia.gokick.site.web.datatable.DataTableFactory;
import it.newmedia.gokick.site.web.datatable.StatisticInfoDataTable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Classe contenente le azioni per visualizzare le informazioni relative alla rosa dell'utente, richieste e inviti ad ultri utenti, visualizzazione di suggerimenti giocatori
 */
public class SquadAction extends AuthenticationBaseAction implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  
  private int friendsCount;
  private List<StatisticInfo> friendRequestsReceivedList;
  private List<StatisticInfo> friendRequestsMadeList;
  private List<StatisticInfo> userMarketEnabledList;
  private GuiSquadInfo guiSquadInfo;
  private String message;
  private List<GuiStatisticPeriod> statisticPeriodList;
  private String statisticPeriod;
  private boolean boxFriendRequestsVisible;
  private String dataTableKey;
  private StatisticInfoDataTable dataTable;

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public void prepare()
  {
    this.statisticPeriodList = AppContext.getInstance().getStatisticPeriodFilter(getCurrentObjLanguage(), getCurrentCobrand());
    if (this.statisticPeriod == null || this.statisticPeriod.equalsIgnoreCase(Constants.STATISTIC_PERIOD__NONE))
    {
      this.statisticPeriod = Constants.STATISTIC_PERIOD__ALL;//STATISTIC_PERIOD__LAST_THREE_MONTH;
    }
  }

  @Override
  public String execute()
  {
    if (getSession().get(Constants.SESSION_KEY__SQUAD_MANAGE_MESSAGE) != null)
    {
      this.message = (String) getSession().get(Constants.SESSION_KEY__SQUAD_MANAGE_MESSAGE);
      getSession().remove(Constants.SESSION_KEY__SQUAD_MANAGE_MESSAGE);
    }

    Squad firstSquad = UserContext.getInstance().getFirstSquad();

    int idUser = UserContext.getInstance().getUser().getId();
    this.friendRequestsReceivedList = StatisticInfoManager.getAllRequestsReceivedByIdUserOwnerAndPeriod(idUser, Constants.STATISTIC_PERIOD__LAST_TWELVE_MONTH);
    //this.friendRequestsMadeList = StatisticInfoManager.getAllRequestsMadeByIdUserOwnerAndPeriod(idUser, Constants.STATISTIC_PERIOD__LAST_TWELVE_MONTH);
    this.friendRequestsMadeList = StatisticInfoManager.getAllRequestsMadeBySquadOwnerAndPeriod(firstSquad, Constants.STATISTIC_PERIOD__LAST_TWELVE_MONTH);

    SquadInfo squadInfo = SquadInfoManager.getSquadInfoByIdSquad(firstSquad.getId());
    this.guiSquadInfo = new GuiSquadInfo(this.statisticPeriod);
    this.guiSquadInfo.setSquadInfo(squadInfo);
    
    //YUI datatable
    if (this.getDataTableKey() == null || this.getDataTableKey().isEmpty())
        this.setDataTableKey(DataTableFactory.buildDataTableKey());

    this.setDataTable(DataTableFactory.getStatisticInfoDataTable(this.getDataTableKey()));

    List<StatisticInfo> statisticInfoList = new ArrayList<StatisticInfo>();
    statisticInfoList=this.guiSquadInfo.getStatisticInfoList();

    this.getDataTable().loadFullResults(statisticInfoList);
    this.getDataTable().setPageSize(statisticInfoList.size());


    this.friendsCount = statisticInfoList.size() - 1;

    List userIdList = new ArrayList<Integer>();
    //List<StatisticInfo> statisticInfoList = this.guiSquadInfo.getStatisticInfoList();
    List<Integer> relationList = SquadManager.getAllUserWithRelationByIdSquad(firstSquad.getId(),idUser);
    for (Integer idUserRel : relationList)
    {
      userIdList.add(idUserRel);
    }
    this.userMarketEnabledList = StatisticInfoManager.getLimitUserOpenMarketByIdProvince(UserContext.getInstance().getUser().getProvince().getId(), userIdList, Constants.STATISTIC_PERIOD__LAST_TWELVE_MONTH);

    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public List<StatisticInfo> getFriendRequestsMadeList()
  {
    return friendRequestsMadeList;
  }

  public void setFriendRequestsMadeList(List<StatisticInfo> friendRequestsMadeList)
  {
    this.friendRequestsMadeList = friendRequestsMadeList;
  }

  public List<StatisticInfo> getFriendRequestsReceivedList()
  {
    return friendRequestsReceivedList;
  }

  public void setFriendRequestsReceivedList(List<StatisticInfo> friendRequestsReceivedList)
  {
    this.friendRequestsReceivedList = friendRequestsReceivedList;
  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public String getStatisticPeriod()
  {
    return statisticPeriod;
  }

  public void setStatisticPeriod(String statisticPeriod)
  {
    this.statisticPeriod = statisticPeriod;
  }

  public List<GuiStatisticPeriod> getStatisticPeriodList()
  {
    return statisticPeriodList;
  }

  public void setStatisticPeriodList(List<GuiStatisticPeriod> statisticPeriodList)
  {
    this.statisticPeriodList = statisticPeriodList;
  }

  public List<StatisticInfo> getUserMarketEnabledList()
  {
    return userMarketEnabledList;
  }

  public void setUserMarketEnabledList(List<StatisticInfo> userMarketEnabledList)
  {
    this.userMarketEnabledList = userMarketEnabledList;
  }

  public GuiSquadInfo getGuiSquadInfo()
  {
    return guiSquadInfo;
  }

  public void setGuiSquadInfo(GuiSquadInfo guiSquadInfo)
  {
    this.guiSquadInfo = guiSquadInfo;
  }

  public int getFriendsCount()
  {
    return friendsCount;
  }

  public void setFriendsCount(int friendsCount)
  {
    this.friendsCount = friendsCount;
  }

  /**
   * @return the boxFriendRequestsVisible
   */
  public boolean isBoxFriendRequestsVisible()
  {
    return boxFriendRequestsVisible;
  }

  /**
   * @param boxFriendRequestsVisible the boxFriendRequestsVisible to set
   */
  public void setBoxFriendRequestsVisible(boolean boxFriendRequestsVisible)
  {
    this.boxFriendRequestsVisible = boxFriendRequestsVisible;
  }

  /**
   * @return the dataTableKey
   */
  public String getDataTableKey()
  {
    return dataTableKey;
  }

  /**
   * @param dataTableKey the dataTableKey to set
   */
  public void setDataTableKey(String dataTableKey)
  {
    this.dataTableKey = dataTableKey;
  }

  /**
   * @return the dataTable
   */
  public StatisticInfoDataTable getDataTable()
  {
    return dataTable;
  }

  /**
   * @param dataTable the dataTable to set
   */
  public void setDataTable(StatisticInfoDataTable dataTable)
  {
    this.dataTable = dataTable;
  }

  

  // </editor-fold>
}
