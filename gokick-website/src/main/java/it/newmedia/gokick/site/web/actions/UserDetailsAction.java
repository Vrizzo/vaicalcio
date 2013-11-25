package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.enums.EnumUserSquadStatus;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.data.hibernate.beans.UserComment;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiSquadInfo;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.AbuseReasonInfo;
import it.newmedia.gokick.site.infos.FeedbackInfo;
import it.newmedia.gokick.site.infos.StatisticInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.EmailManager;
import it.newmedia.gokick.site.managers.SquadInfoManager;
import it.newmedia.gokick.site.managers.SquadManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.site.web.datatable.DataTableFactory;
import it.newmedia.gokick.site.web.datatable.StatisticInfoDataTable;
import it.newmedia.results.Result;
import it.newmedia.utils.MathUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione per la visualizzazione della scheda di un utente, comprendente pagina di riepilogo personale,rosa,feedback e statistiche
 */
public class UserDetailsAction extends AuthenticationBaseAction
{
  //TODO: deve estendere AuthenticationBaseAction

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserDetailsAction.class);

  private int idUser;

  private String tab;

  private boolean close;

  private boolean invite;

  private String relationshipStatusMessage;

  private UserInfo userToShow;

  private Squad userSquadInfo;

  private GuiSquadInfo squadInfoToShow;

  private List<StatisticInfo> statisticInfoList;

  private StatisticInfo statisticInfoTot;

  private FeedbackInfo feedback;

  private UserComment lastFeedback;

  private String lastDateFeedback;

  private boolean selfUser;

  private String userMessage;

  private boolean squadVisible;

  private int friendListSise;

  private boolean userDeleted;
  
  private String dataTableKey;

  private StatisticInfoDataTable dataTable;

  private List<AbuseReasonInfo> abuseReasonsList;
  private int idSelectedAbuseReason;
  private String abuseText;
  private boolean abuseMailSent;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {

    if (this.close == true)
    {
      return SUCCESS;
    }

    if (this.idUser <= 0 || StringUtils.isBlank(this.tab))
    {
      return ERROR;
    }

    // <editor-fold defaultstate="collapsed" desc="-- Dati utente --">

    try
    {
      this.userToShow = InfoProvider.getUserInfo(this.idUser);
      if (this.userToShow == null)
      {
        return ERROR;
      }
      //utente anonimo
      if (this.userToShow.isAnonymousEnabled())
        return SUCCESS;
      
      //utente cancellato
      if (this.userToShow.getStatus().equals(EnumUserStatus.Deleted))
      {
        this.userDeleted=true;
        return SUCCESS;
      }
        

      if (this.idUser == UserContext.getInstance().getUser().getId())
      {
        this.selfUser = true;
      }
//
      this.userSquadInfo = DAOFactory.getInstance().getSquadDAO().getFirstByIdUser(this.idUser);

      Squad SquadUserToRelate = SquadManager.getUserFirstSquad(this.userToShow.getId());
      String userRelationship = SquadManager.getUserRelationship(UserContext.getInstance().getUser().getId(),
              this.userToShow.getId(),
              UserContext.getInstance().getFirstSquad().getId(),
              SquadUserToRelate.getId());
      if (this.userToShow.getId() == UserContext.getInstance().getUser().getId())
      {
        this.relationshipStatusMessage = "";
        this.invite = false;
      }
      else
      {
        if (userRelationship.equals(EnumUserSquadStatus.Confirmed.getValue()))
        {
          this.relationshipStatusMessage = getText("label.friendRelationship.confirm");
          this.invite = false;
        }
        else if (userRelationship.equals(EnumUserSquadStatus.Invited.getValue()) || userRelationship.equals(EnumUserSquadStatus.Request.getValue()))
        {
          this.relationshipStatusMessage = getText("label.friendRelationship.pending");
          this.invite = false;
        }
        else if (userRelationship.equals(""))
        {
          this.relationshipStatusMessage = "";
          this.invite = true;
        }
      }

      this.squadVisible = true;
      if (this.userSquadInfo.getHiddenEnabled() && !userRelationship.equals(EnumUserSquadStatus.Confirmed.getValue()))
      {
        this.squadVisible = false;
      }


      this.squadInfoToShow = SquadInfoManager.getGuiSquadInfo(userSquadInfo);
      if (this.squadInfoToShow == null)
      {
        return ERROR;
      }
      squadInfoToShow.setStatisticPeriod(Constants.STATISTIC_PERIOD__ALL);//STATISTIC_PERIOD__LAST_TWELVE_MONTH);

      //YUI datatable
      if (this.getDataTableKey() == null || this.getDataTableKey().isEmpty())
          this.setDataTableKey(DataTableFactory.buildDataTableKey());

      this.setDataTable(DataTableFactory.getStatisticInfoDataTable(this.getDataTableKey()));
      this.getDataTable().loadFullResults(this.squadInfoToShow.getStatisticInfoList());

      this.friendListSise = squadInfoToShow.getPlayersTot();
      Collections.sort(this.squadInfoToShow.getStatisticInfoList(), StatisticInfo.ORDER_NAME);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return ERROR;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Statistiche --">

    this.statisticInfoList = new ArrayList<StatisticInfo>();
    StatisticInfo statisticInfo = InfoProvider.getStatisticInfo(this.idUser, Constants.STATISTIC_PERIOD__YEAR_CURRENT);
    if (!statisticInfo.isEmpty())
    {
      this.statisticInfoList.add(statisticInfo);
    }
    statisticInfo = InfoProvider.getStatisticInfo(this.idUser, Constants.STATISTIC_PERIOD__YEAR_LESS_ONE);
    if (!statisticInfo.isEmpty())
    {
      this.statisticInfoList.add(statisticInfo);
    }
    statisticInfo = InfoProvider.getStatisticInfo(this.idUser, Constants.STATISTIC_PERIOD__YEAR_LESS_TWO);
    if (!statisticInfo.isEmpty())
    {
      this.statisticInfoList.add(statisticInfo);
    }
    statisticInfo = InfoProvider.getStatisticInfo(this.idUser, Constants.STATISTIC_PERIOD__YEAR_LESS_THREE);
    if (!statisticInfo.isEmpty())
    {
      this.statisticInfoList.add(statisticInfo);
    }
    statisticInfo = InfoProvider.getStatisticInfo(this.idUser, Constants.STATISTIC_PERIOD__YEAR_LESS_FOUR);
    if (!statisticInfo.isEmpty())
    {
      this.statisticInfoList.add(statisticInfo);
    }

    if (this.statisticInfoList.size() > 0)
    {
      this.statisticInfoTot = new StatisticInfo();
      for (StatisticInfo statInfo : this.statisticInfoList)
      {
        this.statisticInfoTot.setMonthPlayed(this.statisticInfoTot.getMonthPlayed() + statInfo.getMonthPlayed());
        this.statisticInfoTot.setReliability(this.statisticInfoTot.getReliability().add(statInfo.getReliability()));
        this.statisticInfoTot.setAverageGoal(this.statisticInfoTot.getAverageGoal().add(statInfo.getAverageGoal()));
        this.statisticInfoTot.setAverageVote(this.statisticInfoTot.getAverageVote().add(statInfo.getAverageVote()));
        this.statisticInfoTot.setGoalsTot(this.statisticInfoTot.getGoalsTot() + statInfo.getGoalsTot());
        this.statisticInfoTot.setOwnGoalsTot(this.statisticInfoTot.getOwnGoalsTot() + statInfo.getOwnGoalsTot());
        this.statisticInfoTot.setMatchTot(this.statisticInfoTot.getMatchTot() + statInfo.getMatchTot());
        this.statisticInfoTot.setMatchWin(this.statisticInfoTot.getMatchWin() + statInfo.getMatchWin());
        this.statisticInfoTot.setMatchDraw(this.statisticInfoTot.getMatchDraw() + statInfo.getMatchDraw());
        this.statisticInfoTot.setMatchLose(this.statisticInfoTot.getMatchLose() + statInfo.getMatchLose());
        this.statisticInfoTot.setChallangeTot(this.statisticInfoTot.getChallangeTot() + statInfo.getChallangeTot());
        this.statisticInfoTot.setChallangeWin(this.statisticInfoTot.getChallangeWin() + statInfo.getChallangeWin());
        this.statisticInfoTot.setChallangeDraw(this.statisticInfoTot.getChallangeDraw() + statInfo.getChallangeDraw());
        this.statisticInfoTot.setChallangeLose(this.statisticInfoTot.getChallangeLose() + statInfo.getChallangeLose());
        this.statisticInfoTot.setMatchStored(this.statisticInfoTot.getMatchStored() + statInfo.getMatchStored());
        this.statisticInfoTot.setMatchOrganized(this.statisticInfoTot.getMatchOrganized() + statInfo.getMatchOrganized());
        this.statisticInfoTot.setChallangeStored(this.statisticInfoTot.getChallangeStored() + statInfo.getChallangeStored());
        this.statisticInfoTot.setChallangeOrganized(this.statisticInfoTot.getChallangeOrganized() + statInfo.getChallangeOrganized());
      }
      this.statisticInfoTot.setAllTot(this.statisticInfoTot.getMatchTot() + this.statisticInfoTot.getChallangeTot());
      this.statisticInfoTot.setAllWin(this.statisticInfoTot.getMatchWin() + this.statisticInfoTot.getChallangeWin());
      this.statisticInfoTot.setAllDraw(this.statisticInfoTot.getMatchDraw() + this.statisticInfoTot.getChallangeDraw());
      this.statisticInfoTot.setAllLose(this.statisticInfoTot.getMatchLose() + this.statisticInfoTot.getChallangeLose());
      this.statisticInfoTot.setMonthPlayed(this.statisticInfoTot.getMonthPlayed() / this.statisticInfoList.size());
      BigDecimal tmp = MathUtil.divide(this.statisticInfoTot.getReliability(), this.statisticInfoList.size(), RoundingMode.HALF_UP, 1);
      this.statisticInfoTot.setReliability(tmp);
      tmp = MathUtil.divide(this.statisticInfoTot.getAverageGoal(), this.statisticInfoList.size(), RoundingMode.HALF_UP, 1);
      this.statisticInfoTot.setAverageGoal(tmp);
      tmp = MathUtil.divide(this.statisticInfoTot.getAverageVote(), this.statisticInfoList.size(), RoundingMode.HALF_UP, 1);
      this.statisticInfoTot.setAverageVote(tmp);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Feedback NON USATA--"  >

//    this.feedback = InfoProvider.getFeedbackInfo(this.idUser);
//    UserComment userComment = UserCommentManager.find(this.idUser, UserContext.getInstance().getUser().getId());
//    if (userComment != null)
//    {
//      this.setLastFeedback(userComment);
//      this.lastDateFeedback = DateManager.showDate(userComment.getCreated(), DateManager.FORMAT_DATE_4);
//    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ABUSE">

    this.abuseReasonsList=AppContext.getInstance().getAllAbuseReasons(getCurrentObjLanguage(), getCurrentCobrand());

    // </editor-fold>

    return SUCCESS;
  }

  public String sendAbuseNotify()
  {
    Result<Boolean> sent=EmailManager.sendAbuseNotificatioEmail( this.idUser,
                                                                 getCurrentIdUser(),
                                                                this.idSelectedAbuseReason,
                                                                this.abuseText, getCurrentCobrand());
    this.setAbuseMailSent(sent.isSuccessNotNull());
    return SUCCESS;
  }


  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public int getIdUser()
  {
    return idUser;
  }

  public void setIdUser(int idUser)
  {
    this.idUser = idUser;
  }

  public String getTab()
  {
    return tab;
  }

  public void setTab(String tab)
  {
    this.tab = tab;
  }

  public boolean isInvite()
  {
    return invite;
  }

  public void setInvite(boolean invite)
  {
    this.invite = invite;
  }

  public boolean isClose()
  {
    return close;
  }

  public void setClose(boolean close)
  {
    this.close = close;
  }

  public String getRelationshipStatusMessage()
  {
    return relationshipStatusMessage;
  }

  public void setRelationshipStatusMessage(String relationshipStatusMessage)
  {
    this.relationshipStatusMessage = relationshipStatusMessage;
  }

  public UserInfo getUserToShow()
  {
    return userToShow;
  }

  public void setUserToShow(UserInfo userToShow)
  {
    this.userToShow = userToShow;
  }

  public Squad getUserSquadInfo()
  {
    return userSquadInfo;
  }

  public void setUserSquadInfo(Squad userSquadInfo)
  {
    this.userSquadInfo = userSquadInfo;
  }

  public List<StatisticInfo> getStatisticInfoList()
  {
    return statisticInfoList;
  }

  public void setStatisticInfoList(List<StatisticInfo> statisticInfoList)
  {
    this.statisticInfoList = statisticInfoList;
  }

  public StatisticInfo getStatisticInfoTot()
  {
    return statisticInfoTot;
  }

  public void setStatisticInfoTot(StatisticInfo statisticInfoTot)
  {
    this.statisticInfoTot = statisticInfoTot;
  }

  public GuiSquadInfo getSquadInfoToShow()
  {
    return squadInfoToShow;
  }

  public void setSquadInfoToShow(GuiSquadInfo squadInfoToShow)
  {
    this.squadInfoToShow = squadInfoToShow;
  }

  /**
   * @return the feedback
   */
  public FeedbackInfo getFeedback()
  {
    return feedback;
  }

  /**
   * @return the lastFeedback
   */
  public UserComment getLastFeedback()
  {
    return lastFeedback;
  }

  /**
   * @param lastFeedback the lastFeedback to set
   */
  public void setLastFeedback(UserComment lastFeedback)
  {
    this.lastFeedback = lastFeedback;
  }

  /**
   * @return the lastDateFeedback
   */
  public String getLastDateFeedback()
  {
    return lastDateFeedback;
  }

  /**
   * @return the selfUser
   */
  public boolean isSelfUser()
  {
    return selfUser;
  }

  /**
   * @return the userMessage
   */
  public String getUserMessage()
  {
    return userMessage;
  }

  /**
   * @param userMessage the userMessage to set
   */
  public void setUserMessage(String userMessage)
  {
    this.userMessage = userMessage;
  }

  /**
   * @return the squadVisible
   */
  public boolean isSquadVisible()
  {
    return squadVisible;
  }

  /**
   * @return the friendListSise
   */
  public int getFriendListSise()
  {
    return friendListSise;
  }
  
  /**
   * @return the userDeleted
   */
  public boolean isUserDeleted()
  {
    return userDeleted;
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

  /**
   * @return the abuseReasons
   */
  public List<AbuseReasonInfo> getAbuseReasonsList()
  {
    return abuseReasonsList;
  }

  /**
   * @return the abuseText
   */
  public String getAbuseText()
  {
    return abuseText;
  }

  /**
   * @param abuseText the abuseText to set
   */
  public void setAbuseText(String abuseText)
  {
    this.abuseText = abuseText;
  }

  /**
   * @return the abuseMailSent
   */
  public boolean isAbuseMailSent()
  {
    return abuseMailSent;
  }

  /**
   * @return the idSelectedAbuseReason
   */
  public int getIdSelectedAbuseReason()
  {
    return idSelectedAbuseReason;
  }

  /**
   * @param idSelectedAbuseReason the idSelectedAbuseReason to set
   */
  public void setIdSelectedAbuseReason(int idSelectedAbuseReason)
  {
    this.idSelectedAbuseReason = idSelectedAbuseReason;
  }

  /**
   * @param abuseMailSent the abuseMailSent to set
   */
  public void setAbuseMailSent(boolean abuseMailSent)
  {
    this.abuseMailSent = abuseMailSent;
  }
  

  // </editor-fold>
}
