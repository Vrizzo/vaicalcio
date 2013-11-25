package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiPlayerInfo;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.managers.GuiManager;
import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.gokick.site.managers.PlayerManager;
import it.newmedia.gokick.site.managers.SquadManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Classe contenente le azioni per registrare un amico o un utento esterno ad una propria partita
 */
public class RegisterFriendToMatch extends AuthenticationBaseAction
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  // private static Logger logger = Logger.getLogger(UserEnableAction.class);
//  private int[] userCheckList;
  private List<Integer> userCheckList;
  private List<Integer> userRequestCheckList;

//  private int[] userRequestCheckList;

  private int idMatch;

  private String team;

  private int friendRegistered;

  private List<GuiPlayerInfo> guiPlayerInfoList;

  private List<GuiPlayerInfo> guiPlayerRequestInfoList;

  private int playersToAdd;

  private List playersToAddList;

  private int selectedNumberPlayersToAdd;

  private List<String> playersToAddNameList;

  private List roleToAddList;

  private List<Integer> playersRoleToAddList;

  private boolean playedMatch;

  private boolean noValidate;

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public String input()
  {
    loadData();
    return SUCCESS;
  }

  @Override
  public String execute()
  {
    
    this.friendRegistered = MatchManager.registerUserMultipleToMatch(
            this.idMatch,
            this.team,
            this.userCheckList!=null?this.userCheckList.toArray(new Integer[0]):(new Integer[0]),
            this.userRequestCheckList!=null?this.userRequestCheckList.toArray(new Integer[0]):(new Integer[0]),
            this.selectedNumberPlayersToAdd,
            this.playersToAddNameList,
            this.playersRoleToAddList);

    if (this.friendRegistered > 0)
    {
      String message = getText("messagge.playersRegistered");
      message = message.replace(Constants.REPLACEMENT__PLAYERS_REGISTERED_COUNT, Integer.toString(this.friendRegistered));
      UserContext.getInstance().setLastMessage(message);
    }
    else if(this.friendRegistered == -1)
    {
      String message = getText("messagge.dbError");
      UserContext.getInstance().setLastMessage(message);
    }
    else
    {
      loadData();
    }

    return SUCCESS;
  }

  @Override
  public void validate()
  {
    if (this.noValidate == true)
      return; //controllo durante il sort della displaytable

    if ((this.userCheckList == null || this.userCheckList.isEmpty() ) && this.selectedNumberPlayersToAdd == 0 &&
            (this.userRequestCheckList == null || this.userRequestCheckList.isEmpty()))
    {
      addActionError("Selezionare qualcosa");
    }

//    if (this.selectedNumberPlayersToAdd > 0)
//    {
//      for (int i=0; i < this.selectedNumberPlayersToAdd; i++)
//      {
//        if ( StringUtils.isEmpty(playersToAddNameList.get(i)) )
//        {
//          addActionError("Riempire tutti i campi giocatore esterno");
//          break;
//        }
//      }
//    }
    if (this.hasActionErrors())
      loadData();
  }

  private void loadData()
  {
    Squad firstSquad = UserContext.getInstance().getFirstSquad();

    List idUserSquadList = SquadManager.getAllConfirmedUserByIdSquad(firstSquad.getId());

    MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);

    this.guiPlayerInfoList = GuiManager.prepareGuiPlayerFriendsInfoList(matchInfo, idUserSquadList);

    //List<Player> matchPlayerRegisteredList = MatchManager.getPlayerListOrderByName(idMatch,false, EnumPlayerStatus.OwnerRegistered, EnumPlayerStatus.UserRegistered, EnumPlayerStatus.UserRequestRegistered, EnumPlayerStatus.Out);
    int playersRegistered=MatchManager.countPlayersNoCache(idMatch);
    
    if (matchInfo.getMatchStart().before(new Date()))      //matchInfo.isRecorded()
    {
      this.setPlayedMatch(true);
      List userRequestList = PlayerManager.getAllRequestUserByIdMatch(idMatch);
      if (!userRequestList.isEmpty())
      {
        this.guiPlayerRequestInfoList = GuiManager.buildGuiPlayerInfoList(matchInfo, userRequestList);
      }
    }
    
    this.playersToAdd = matchInfo.getMaxPlayers() - playersRegistered;

    this.playersToAddList = new ArrayList();
    this.playersToAddNameList = new ArrayList<String>();
    this.roleToAddList = new ArrayList();
    this.playersRoleToAddList = new ArrayList<Integer>();
    for (int i = 0; i < (this.team==null ?  this.playersToAdd : 22); i++)   //se team è valorizzato siamo in archivia, l'iscrizione non deve essere piu' legata ai posti max
    {
      this.playersToAddList.add(i + 1);
      this.playersToAddNameList.add("");
      this.roleToAddList.add(i + 1);
      this.playersRoleToAddList.add(null);
    }

  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
//  public int[] getUserCheckList()
//  {
//    return userCheckList;
//  }
//
//  public void setUserCheckList(int[] userCheckList)
//  {
//    this.userCheckList = userCheckList;
//  }
//
//  public void setUserCheckList(boolean userCheckList)
//  {
//    //se non viene selezionato nessuna chkbox struts2 crca un metodo che accetta un array di stringhe anzichè di interi
//  }

//  public int[] getUserRequestCheckList()
//  {
//    return userRequestCheckList;
//  }
//
//  public void setUserRequestCheckList(int[] userRequestCheckList)
//  {
//    this.userRequestCheckList = userRequestCheckList;
//  }

  public int getIdMatch()
  {
    return idMatch;
  }

  /**
   * @param idMatch the idMatch to set
   */
  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  public String getTeam()
  {
    return team;
  }

  public void setTeam(String team)
  {
    this.team = team;
  }

  /**
   * @return the friendRegistered
   */
  public int getFriendRegistered()
  {
    return friendRegistered;
  }

  /**
   * @param friendRegistered the friendRegistered to set
   */
  public void setFriendRegistered(int friendRegistered)
  {
    this.friendRegistered = friendRegistered;
  }

  /**
   * @return the GuiPlayerInfoList
   */
  public List<GuiPlayerInfo> getGuiPlayerInfoList()
  {
    return guiPlayerInfoList;
  }

  /**
   * @return the playersToAdd
   */
  public int getPlayersToAdd()
  {
    return playersToAdd;
  }

  public void setPlayersToAdd(int playersToAdd)
  {
    this.playersToAdd = playersToAdd;
  }

  public List getPlayersToAddList()
  {
    return playersToAddList;
  }

  public List<String> getPlayersToAddNameList()
  {
    return playersToAddNameList;
  }

  public void setPlayersToAddNameList(List<String> playersToAddNameList)
  {
    this.playersToAddNameList = playersToAddNameList;
  }

  public List<Integer> getPlayersRoleToAddList()
  {
    return playersRoleToAddList;
  }

  public void setPlayersRoleToAddList(List<Integer> playersRoleToAddList)
  {
    this.playersRoleToAddList = playersRoleToAddList;
  }

  /**
   * @return the roleToAddList
   */
  public List getRoleToAddList()
  {
    return roleToAddList;
  }

  /**
   * @return the selectedNumberPlayersToAdd
   */
  public int getSelectedNumberPlayersToAdd()
  {
    return selectedNumberPlayersToAdd;
  }

  /**
   * @param selectedNumberPlayersToAdd the selectedNumberPlayersToAdd to set
   */
  public void setSelectedNumberPlayersToAdd(int selectedNumberPlayersToAdd)
  {
    this.selectedNumberPlayersToAdd = selectedNumberPlayersToAdd;
  }

  /**
   * @return the guiPlayerRequestInfoList
   */
  public List<GuiPlayerInfo> getGuiPlayerRequestInfoList()
  {
    return guiPlayerRequestInfoList;
  }

  /**
   * @return the playedMatch
   */
  public boolean isPlayedMatch()
  {
    return playedMatch;
  }

  /**
   * @param playedMatch the playedMatch to set
   */
  public void setPlayedMatch(boolean playedMatch)
  {
    this.playedMatch = playedMatch;
  }

  /**
   * @return the noValidate
   */
  public boolean isNoValidate()
  {
    return noValidate;
  }

  /**
   * @param noValidate the noValidate to set
   */
  public void setNoValidate(boolean noValidate)
  {
    this.noValidate = noValidate;
  }

  /**
   * @return the userCheckList
   */
  public List<Integer> getUserCheckList()
  {
    return userCheckList;
  }

  /**
   * @param userCheckList the userCheckList to set
   */
  public void setUserCheckList(List<Integer> userCheckList)
  {
    this.userCheckList = userCheckList;
  }

  /**
   * @return the userRequestCheckList
   */
  public List<Integer> getUserRequestCheckList()
  {
    return userRequestCheckList;
  }

  /**
   * @param userRequestCheckList the userRequestCheckList to set
   */
  public void setUserRequestCheckList(List<Integer> userRequestCheckList)
  {
    this.userRequestCheckList = userRequestCheckList;
  }

  // </editor-fold>
}
