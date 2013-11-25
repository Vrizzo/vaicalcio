package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiCalendarInfo;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.DateManager;
import it.newmedia.gokick.site.managers.MatchInfoManager;
import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.gokick.site.managers.SquadManager;
import it.newmedia.gokick.site.managers.StatisticManager;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione per la gestione della home.recupera i dati riassuntivi dell'utente visualizzati in apertura
 */
public class HomeAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static Logger logger = Logger.getLogger(HomeAction.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private UserInfo userInfo;
  private int allPlayableMatchNumber;
  private int allPlayedNumber;
  private GuiCalendarInfo calendarInfo;
  private GuiCalendarInfo calendarResultInfo;
  private GuiCalendarInfo calendarOrganizingInfo;
  private String goToUrl;

  private List<GuiCalendarInfo> calendarOrganizingList;
  private int organizingListSize;
 
  private String nextMatchDate;
  private String nextMatchOrganizingDate;
  private Integer friendRequestsReceivedList;

  private List<UserInfo> userInfoWithPic;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    if( UserContext.getInstance().isWithGoToUrl())
    {
      this.goToUrl = UserContext.getInstance().getGoToUrl();
      UserContext.getInstance().resetGoToUrl();
      return Constants.STRUTS_RESULT_NAME__GO_TO_URL;
    }

    if (!UserContext.getInstance().isLoggedIn())
    {
      return Constants.STRUTS_RESULT_NAME__NOT_LOGGED_IN;
    }
    int idUser=UserContext.getInstance().getUser().getId();
    this.userInfo=InfoProvider.getUserInfo(idUser);

    //partite giocabili
    List<Integer> friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());

    this.allPlayableMatchNumber = MatchInfoManager.countAllVeryPlayable(UserContext.getInstance().getUser().getProvince().getId(),
                                                                        friendsIdList);

    //prossima partita
    MatchInfo nextMatchInfo=InfoProvider.getMatchInfo(MatchManager.getNextOne(idUser));
    if (nextMatchInfo.isValid())
    {
      this.calendarInfo=new GuiCalendarInfo(nextMatchInfo, friendsIdList);
      this.nextMatchDate=DateManager.showDate(calendarInfo.getMatchInfo().getMatchStart(), DateManager.FORMAT_DATE_17);
    }

    //played
    //this.allPlayedNumber=StatisticManager.getPlayed(idUser);
    this.allPlayedNumber=MatchInfoManager.countPlayed(idUser);
    
    //ultimo risultato
    MatchInfo resultMatchInfo=InfoProvider.getMatchInfo(MatchManager.getLastValidResult(idUser));
    if (resultMatchInfo.isValid())
    {
      this.calendarResultInfo=new GuiCalendarInfo(resultMatchInfo, friendsIdList);
    }
    
    //richieste amicizia
    this.friendRequestsReceivedList = SquadManager.countAllRequestsReceived(idUser);

    //partite che stai organizzando
    List<MatchInfo> matchInfoOrganizingList = MatchInfoManager.getOrganized(idUser);
    this.calendarOrganizingList = new ArrayList<GuiCalendarInfo>();
    for (MatchInfo matchInfo : matchInfoOrganizingList)
    {
      calendarOrganizingList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
    }
    //caso una sola partita
    this.organizingListSize=matchInfoOrganizingList.size();
    if (organizingListSize==1)
    {
      this.calendarOrganizingInfo=this.calendarOrganizingList.get(0);
      this.nextMatchOrganizingDate=DateManager.showDate(this.calendarOrganizingInfo.getMatchInfo().getMatchStart(), DateManager.FORMAT_DATE_17);
    }

    //recupero User con figurina Current
    List<Integer> idUserWithPic=UserManager.getAllWithCurrentPic(Constants.HOW_MANY_USERS_WITH_CURRENT_PIC, true);
    this.userInfoWithPic=new ArrayList<UserInfo>();
    for(Integer idUserPic : idUserWithPic )
    {
      UserInfo userInfoPic = InfoProvider.getUserInfo(idUserPic);
      this.userInfoWithPic.add(userInfoPic);
    }

    return Constants.STRUTS_RESULT_NAME__LOGGED_IN;
  }
  

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  /**
   * @return the userInfo
   */
  public UserInfo getUserInfo()
  {
    return userInfo;
  }
  
  /**
   * @return the allPlayableMatchNumber
   */
  public int getAllPlayableMatchNumber()
  {
    return allPlayableMatchNumber;
  }

  /**
   * @return the calendarInfo
   */
  public GuiCalendarInfo getCalendarInfo()
  {
    return calendarInfo;
  }

  /**
   * @return the nextMatchDate
   */
  public String getNextMatchDate()
  {
    return nextMatchDate;
  }

  /**
   * @return the calendarResultInfo
   */
  public GuiCalendarInfo getCalendarResultInfo()
  {
    return calendarResultInfo;
  }

  /**
   * @return the friendRequestsReceivedList
   */
  public Integer getFriendRequestsReceivedList()
  {
    return friendRequestsReceivedList;
  }
 
  /**
   * @return the organizingListSize
   */
  public int getOrganizingListSize()
  {
    return organizingListSize;
  }

  /**
   * @return the calendarOrganizingList
   */
  public List<GuiCalendarInfo> getCalendarOrganizingList()
  {
    return calendarOrganizingList;
  }

  /**
   * @return the nextMatchOrganizingDate
   */
  public String getNextMatchOrganizingDate()
  {
    return nextMatchOrganizingDate;
  }

  /**
   * @return the calendarOrganizingInfo
   */
  public GuiCalendarInfo getCalendarOrganizingInfo()
  {
    return calendarOrganizingInfo;
  }

  /**
   * @return the userInfoWithPic
   */
  public List<UserInfo> getUserInfoWithPic()
  {
    return userInfoWithPic;
  }
  /**
   * @return the allPlayedNumber
   */
  public int getAllPlayedNumber()
  {
    return allPlayedNumber;
  }

  public String getGoToUrl()
  {
    return goToUrl;
  }

  public void setGoToUrl(String goToUrl)
  {
    this.goToUrl = goToUrl;
  }
  // </editor-fold>
}