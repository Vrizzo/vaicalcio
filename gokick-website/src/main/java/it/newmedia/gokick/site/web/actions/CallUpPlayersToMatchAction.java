package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.hibernate.beans.*;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiPlayerInfo;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.*;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.results.Result;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Classe contenente le azioni per registrare un amico o un utento esterno ad una propria partita
 */
public class CallUpPlayersToMatchAction extends AuthenticationBaseAction implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  // private static Logger logger = Logger.getLogger(UserEnableAction.class);
  private List<Integer> userCheckList;
  private List<String> userMailList;

  private int idMatch;

  private List<GuiPlayerInfo> guiPlayerInfoList;

  private boolean playedMatch;
  private boolean privateMatch;

  private int idCountry;
  private int idProvince;
  private int idCity;

  private boolean chkGk;
  private boolean chkDf;
  private boolean chkCc;
  private boolean chkAt;

  private String firstName;
  private String lastName;

  private boolean advancedSearch;

  private String titleProvince;
  private String emailOwner;
  private String freeText;
  private String activePage;

  private boolean callUpOk;


  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">

  public void prepare() throws Exception
  {
    this.idCountry = UserContext.getInstance().getUser().getCountry().getId();
    this.idProvince = UserContext.getInstance().getUser().getProvince().getId();
    Province province = ProvinceManager.getProvince(this.idProvince);
    this.titleProvince=province.getName();
    this.emailOwner= UserContext.getInstance().getUser().getEmail();
  }

  public String viewUsersToCall()
  {
    Province province = ProvinceManager.getProvince(this.idProvince);
    if (idProvince > 0)
      this.titleProvince=province.getName();

    MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);
    if(matchInfo.isSquadOutEnable())
    {
      loadUsersList();
    }
    else
    {
      this.guiPlayerInfoList=new ArrayList<GuiPlayerInfo>();
      this.privateMatch=true;
    }

    return SUCCESS;
  }

  public void validateCallUpUsers()
  {
    boolean hasError=false;
    
    if (hasError)
    {
      loadUsersList();
    }
    
  }

  public String callUpUsers()
  {
    MatchInfo matchInfo = InfoProvider.getMatchInfo(this.idMatch);
    if (!matchInfo.isValid())
    {
      return ERROR_POPUP;
    }
    for (String mailTo : userMailList)
    {
      User userPlayer = UserManager.getByEmail(mailTo);
      MatchManager.addPlayer( this.idMatch,
                              userPlayer.getId(),
                              EnumPlayerStatus.UserCalled,
                              userPlayer.getPlayerRole().getId(),
                              null);
      sendInviteGokickersToMatch(this.getCurrentUserInfo(),userPlayer, matchInfo, mailTo);
    }
    this.callUpOk=true;
    String message = getText("message.convocazioni.inviate");
    UserContext.getInstance().setLastMessage(message);
    return SUCCESS;
  }

  public String calledUp()
  {
    // <editor-fold defaultstate="collapsed" desc="RUOLI">
    List<Integer> roles = new ArrayList<Integer>();
    if (this.chkGk)
    {
      roles.add(Constants.PLAYER_ROLE_ID__GK);
    }
    if (this.isChkDf())
    {
      roles.add(Constants.PLAYER_ROLE_ID__DF);
    }
    if (this.chkCc)
    {
      roles.add(Constants.PLAYER_ROLE_ID__CC);
    }
    if (this.isChkAt())
    {
      roles.add(Constants.PLAYER_ROLE_ID__AT);
    }
    // </editor-fold>

    List idUserSquadList = PlayerManager.getCalledUpIdUser(idMatch);
    Province province = ProvinceManager.getProvince(this.idProvince);

    this.titleProvince=province.getName();

    MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);

    guiPlayerInfoList=new ArrayList<GuiPlayerInfo>();
    this.guiPlayerInfoList = GuiManager.prepareGuiPlayerFriendsInfoList(matchInfo, idUserSquadList);

    return SUCCESS;
  }

  public String friends()
  {
    // <editor-fold defaultstate="collapsed" desc="RUOLI">
    List<Integer> roles = new ArrayList<Integer>();
    if (this.chkGk)
    {
      roles.add(Constants.PLAYER_ROLE_ID__GK);
    }
    if (this.isChkDf())
    {
      roles.add(Constants.PLAYER_ROLE_ID__DF);
    }
    if (this.chkCc)
    {
      roles.add(Constants.PLAYER_ROLE_ID__CC);
    }
    if (this.isChkAt())
    {
      roles.add(Constants.PLAYER_ROLE_ID__AT);
    }
    // </editor-fold>

    Squad firstSquad = UserContext.getInstance().getFirstSquad();

    List idUserSquadList = SquadManager.getAllConfirmedUserByIdSquad(firstSquad.getId(),true);

    MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);

    Province province = ProvinceManager.getProvince(this.idProvince);

    this.titleProvince=province.getName();

    guiPlayerInfoList=new ArrayList<GuiPlayerInfo>();
    this.guiPlayerInfoList = GuiManager.prepareGuiPlayerFriendsInfoList(matchInfo, idUserSquadList);

    return SUCCESS;
  }

  public String viewRegistered()
  {
    this.guiPlayerInfoList= GuiManager.getGuiPlayersInfoListUsersRegisteredByMatch(idMatch,false);
    return SUCCESS;
  }

  public String contactRegistered()
  {
    this.callUpOk=true;
    MatchInfo matchInfo = InfoProvider.getMatchInfo(this.idMatch);
    for (String mailTo : userMailList)
    {
      EmailManager.contactMatchPlayers(UserContext.getInstance().getUser().getId(),
                                                                    matchInfo,
                                                                    mailTo,
                                                                    freeText,
                                                                    getCurrentCobrand());
      String message = getText("message.messaggio.inviato");
      UserContext.getInstance().setLastMessage(message);
  }
    return SUCCESS;
  }

  private void loadUsersList()
  {
    // <editor-fold defaultstate="collapsed" desc="RUOLI">
    List<Integer> roles = new ArrayList<Integer>();
    if (this.chkGk)
    {
      roles.add(Constants.PLAYER_ROLE_ID__GK);
    }
    if (this.isChkDf())
    {
      roles.add(Constants.PLAYER_ROLE_ID__DF);
    }
    if (this.chkCc)
    {
      roles.add(Constants.PLAYER_ROLE_ID__CC);
    }
    if (this.isChkAt())
    {
      roles.add(Constants.PLAYER_ROLE_ID__AT);
    }
    // </editor-fold>

    List idUserSquadList = UserManager.getUsersLight(this.firstName, this.lastName, idCountry, idProvince, idCity, roles, "playedMatches", "created");

    //Province province = ProvinceManager.getProvince(this.idProvince);

    //this.titleProvince=province.getName();

    MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);

    guiPlayerInfoList=new ArrayList<GuiPlayerInfo>();
    this.guiPlayerInfoList = GuiManager.prepareGuiPlayerFriendsInfoList(matchInfo, idUserSquadList);
  }

  private void sendInviteGokickersToMatch(UserInfo userInfo,User userToInvite, MatchInfo matchInfo, String mailTo)
  {
    Result<Boolean> rEmailSend = EmailManager.inviteGokickersToMatch(userInfo, userToInvite ,matchInfo, this.getFreeText(), this.getCurrentObjLanguage(), getCurrentCobrand());
    if (rEmailSend.getValue() == true)
    {
      addActionMessage(mailTo);
    }
    else
    {
      logger.error(rEmailSend.getErrorMessage());
      addActionError(mailTo);
    }
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- completamento geoTendine --">
  public List<Country> getCountyList()
  {
    List<Country> list = CountryManager.getAllWithUsersOnMarket();
    //list = CountryManager.addCountry(list, UserContext.getInstance().getUser().getCountry());
    return list;
  }

  public List<Province> getProvinceList()
  {
    List<Province> list = ProvinceManager.getAllWithUsersOnMarket(getIdCountry());
//    if (this.getIdCountry()==UserContext.getInstance().getUser().getCountry().getId())
//      list = ProvinceManager.addProvince(list, UserContext.getInstance().getUser().getProvince());
    return list;
  }

  public List<City> getCityList()
  {
    List<City> citylist=new ArrayList<City>();
    if (this.getIdProvince() > 0)
    {
      citylist = CityManager.getAllWithUsersOnMarket(getIdProvince());
       //if (this.getIdProvince()==UserContext.getInstance().getUser().getProvince().getId())
        //Add always user's country in which plays
        //list = CityManager.addCity(list, UserContext.getInstance().getUser().getCity());
    }
    return citylist;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- GETTERS/SETTERS --">
  public List<Integer> getUserCheckList()
  {
    return userCheckList;
  }
  public void setUserCheckList(List<Integer> userCheckList)
  {
    this.userCheckList = userCheckList;
  }

  public int getIdMatch()
  {
    return idMatch;
  }
  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  
  public List<GuiPlayerInfo> getGuiPlayerInfoList()
  {
    return guiPlayerInfoList;
  }
  public boolean isPlayedMatch()
  {
    return playedMatch;
  }

  public void setPlayedMatch(boolean playedMatch)
  {
    this.playedMatch = playedMatch;
  }

  public int getIdCountry()
  {
    return idCountry;
  }

  public void setIdCountry(int idCountry)
  {
    this.idCountry = idCountry;
  }

  public int getIdProvince()
  {
    return idProvince;
  }
  public void setIdProvince(int idProvince)
  {
    this.idProvince = idProvince;
  }

  public int getIdCity()
  {
    return idCity;
  }
  public void setIdCity(int idCity)
  {
    this.idCity = idCity;
  }

  public boolean isChkGk()
  {
    return chkGk;
  }

  public void setChkGk(boolean chkGk)
  {
    this.chkGk = chkGk;
  }

  public boolean isChkCc()
  {
    return chkCc;
  }

  public void setChkCc(boolean chkCc)
  {
    this.chkCc = chkCc;
  }

  /**
   * @return the chkDf
   */
  public boolean isChkDf()
  {
    return chkDf;
  }

  /**
   * @param chkDf the chkDf to set
   */
  public void setChkDf(boolean chkDf)
  {
    this.chkDf = chkDf;
  }

  /**
   * @return the chkAt
   */
  public boolean isChkAt()
  {
    return chkAt;
  }

  /**
   * @param chkAt the chkAt to set
   */
  public void setChkAt(boolean chkAt)
  {
    this.chkAt = chkAt;
  }

  /**
   * @return the firstName
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  /**
   * @return the advancedSearch
   */
  public boolean isAdvancedSearch()
  {
    return advancedSearch;
  }

  /**
   * @param advancedSearch the advancedSearch to set
   */
  public void setAdvancedSearch(boolean advancedSearch)
  {
    this.advancedSearch = advancedSearch;
  }

  /**
   * @return the titleProvince
   */
  public String getTitleProvince()
  {
    return titleProvince;
  }

  /**
   * @return the emailOwner
   */
  public String getEmailOwner()
  {
    return emailOwner;
  }

  /**
   * @param freeText the freeText to set
   */
  public void setFreeText(String freeText)
  {
    this.freeText = freeText;
  }

  /**
   * @return the freeText
   */
  public String getFreeText()
  {
    return freeText;
  }

  /**
   * @return the userMailList
   */
  public List<String> getUserMailList()
  {
    return userMailList;
  }

  /**
   * @param userMailList the userMailList to set
   */
  public void setUserMailList(List<String> userMailList)
  {
    this.userMailList = userMailList;
  }

  /**
   * @return the callUpOk
   */
  public boolean isCallUpOk()
  {
    return callUpOk;
  }

  /**
   * @return the activePage
   */
  public String getActivePage()
  {
    return activePage;
  }

  /**
   * @param activePage the activePage to set
   */
  public void setActivePage(String activePage)
  {
    this.activePage = activePage;
  }

  /**
   * @return the privateMatch
   */
  public boolean isPrivateMatch()
  {
    return privateMatch;
  }

  // </editor-fold>
}
