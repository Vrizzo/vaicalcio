package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiPlayerInfo;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.managers.EmailManager;
import it.newmedia.gokick.site.managers.GuiManager;
import it.newmedia.gokick.site.managers.MatchInfoManager;
import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.results.Result;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente le azioni per registrare un amico o un utento esterno ad una propria partita
 */
public class MatchManageRegistrationsAction extends AuthenticationBaseAction implements Preparable
{

  public static final String ACTIVEPAGE_GOKICKERS = "gokickers";
  public static final String ACTIVEPAGE_OUTERS = "outers";

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  // private static Logger logger = Logger.getLogger(UserEnableAction.class);
  private String freeText;
  private String team;
  private String activePage;
  private int idMatch;
  private int idUserToAdd;
  private int idUserOwner;
  private int maxPlayers;
  private int playersToAdd;
  private int idPlayerToRemove;
  private boolean matchDone;
  private boolean userOwner;
  private boolean userRegistration;
  private boolean registerOk;
  private List<GuiPlayerInfo> guiPlayerInfoList;
  private List<Integer> idUserToAddList;

  private List playersToAddList;
  private List<String> playersToAddNameList;
  private List roleToAddList;
  private List<Integer> playersRoleToAddList;
  private int selectedNumberPlayersToAdd;
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  public void prepare() throws Exception
  {
  }

  @Override
  public String execute()
  {
    if(StringUtils.isBlank(this.team))
    {
      this.team=null;
    }

    MatchInfo matchInfo = MatchInfoManager.getMatchInfo(idMatch);
    this.idUserOwner = matchInfo.getIdUserOwner();
    this.maxPlayers = matchInfo.getMaxPlayers();
    //se la partita è avvenuta l'utente non deve poter modificare nulla, il ctrl evita che playerStatus.jsp venga caricata
    if (matchInfo.getMatchStart().before(new Date()) || matchInfo.isCanceled())
    {
      this.matchDone = true;
    }

    if (this.getIdUserOwner() == this.getCurrentUser().getId())
    {
      this.userOwner = true;
    }

    // <editor-fold defaultstate="collapsed" desc="REGISTRAZIONE USERS">
    if (userRegistration)
    {
      int usersRegistered = MatchManager.registerUsersToMatch(idMatch, team, idUserToAddList,StringUtils.isNotBlank(team));

      if (usersRegistered > 0)
      {
        String message = getText("messagge.playersRegistered");
        message = message.replace(Constants.REPLACEMENT__PLAYERS_REGISTERED_COUNT, Integer.toString(usersRegistered));
        UserContext.getInstance().setLastMessage(message);
      }
      else if (usersRegistered == -1)
      {
        String message = getText("messagge.dbError");
        UserContext.getInstance().setLastMessage(message);
      }
      this.registerOk = true;
      
      if(StringUtils.isBlank(team))
      {
          for (Integer idUser : idUserToAddList)
          {
            Result<Boolean> sendMail = EmailManager.sendRegisteredUserToMatchEmail(matchInfo, idUser, this.freeText, getCurrentCobrand());
            if (!sendMail.isSuccessNotNull())
            {
              logger.error("Error sending registration Mail to user: " + idUser );
            }
          }
      }
      
      return SUCCESS;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="RIMOZIONE USER DA LISTA">
    if (this.idPlayerToRemove > 0)
    {
      Iterator<Integer> iterator = idUserToAddList.iterator();
      while (iterator.hasNext())
      {
        int idToremove = iterator.next();
        if (idToremove == idPlayerToRemove)
        {
          iterator.remove();
        }
      }
    }
    // </editor-fold>

    if (idUserToAddList == null)
    {
      this.idUserToAddList = new ArrayList<Integer>();
    }

    if (idUserToAdd > 0)
    {
      if (!idUserToAddList.contains(idUserToAdd))
      {
        idUserToAddList.add(idUserToAdd);
      }
    }

    if (!idUserToAddList.isEmpty())
    {
      this.guiPlayerInfoList = GuiManager.prepareGuiPlayerFriendsInfoList(matchInfo, idUserToAddList);
    }
    else
    {
      this.guiPlayerInfoList = new ArrayList<GuiPlayerInfo>();
    }

    int playersCount = MatchManager.countPlayersNoCache(idMatch);
    this.playersToAdd = getMaxPlayers() - playersCount - idUserToAddList.size();

    for (int i = 0; i < playersToAdd; i++)
    {
      guiPlayerInfoList.add(new GuiPlayerInfo());
    }

    if(StringUtils.isNotBlank(team))
    {
      guiPlayerInfoList.add(new GuiPlayerInfo());
    }
    this.activePage = ACTIVEPAGE_GOKICKERS;
    return SUCCESS;
  }

  public String outers()
  {
    if(StringUtils.isBlank(this.team))
    {
      this.team=null;
    }

    MatchInfo matchInfo = MatchInfoManager.getMatchInfo(idMatch);
    this.idUserOwner = matchInfo.getIdUserOwner();
    this.maxPlayers = matchInfo.getMaxPlayers();
    //se la partita è avvenuta l'utente non deve poter modificare nulla, il ctrl evita che playerStatus.jsp venga caricata
    if (matchInfo.getMatchStart().before(new Date()) || matchInfo.isCanceled())
    {
      this.matchDone = true;
    }

    if (this.getIdUserOwner() == this.getCurrentUser().getId())
    {
      this.userOwner = true;
    }

    // <editor-fold defaultstate="collapsed" desc="iscrizione esterni">
    if (userRegistration)
    {
      int usersRegistered = MatchManager.registerOutersToMatch(idMatch, team,selectedNumberPlayersToAdd, playersToAddNameList, playersRoleToAddList,StringUtils.isNotBlank(team));

      if (usersRegistered > 0)
      {
        String message = getText("messagge.playersRegistered");
        message = message.replace(Constants.REPLACEMENT__PLAYERS_REGISTERED_COUNT, Integer.toString(usersRegistered));
        UserContext.getInstance().setLastMessage(message);
      }
      else if (usersRegistered == -1)
      {
        String message = getText("messagge.dbError");
        UserContext.getInstance().setLastMessage(message);
      }
      this.registerOk = true;
      return SUCCESS;
    }
     // </editor-fold>

    int outersMax = AppContext.getInstance().getHowManyOuters();
    int playersCount = MatchManager.countPlayersNoCache(idMatch);
    int playersOutCount= MatchManager.countPlayersOutNoCache(idMatch);
    int freePlaces = getMaxPlayers() - playersCount;
    int outerDeficit = outersMax - playersOutCount;
    this.playersToAdd=freePlaces > outerDeficit ? outerDeficit : freePlaces;

    this.playersToAddList = new ArrayList();
    this.playersToAddNameList = new ArrayList<String>();
    this.roleToAddList = new ArrayList();
    this.playersRoleToAddList = new ArrayList<Integer>();

    if(StringUtils.isNotBlank(team))
      playersToAdd=outersMax;

    for (int i = 0; i < playersToAdd; i++)
    {
      this.getPlayersToAddList().add(i + 1);
      this.playersToAddNameList.add("");
      this.roleToAddList.add(i + 1);
      this.playersRoleToAddList.add(null);
    }

    this.activePage = ACTIVEPAGE_OUTERS;
    return SUCCESS;
  }

  @Override
  public void validate()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
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

  public int getIdUserOwner()
  {
    return idUserOwner;
  }

  public int getMaxPlayers()
  {
    return maxPlayers;
  }

  public void setIdPlayerToRemove(int idPlayerToRemove)
  {
    this.idPlayerToRemove = idPlayerToRemove;
  }

  public boolean isMatchDone()
  {
    return matchDone;
  }

  public int getPlayersToAdd()
  {
    return playersToAdd;
  }

  public void setIdUserToAdd(int idUserToAdd)
  {
    this.idUserToAdd = idUserToAdd;
  }

  public List<Integer> getIdUserToAddList()
  {
    return idUserToAddList;
  }
  public void setIdUserToAddList(List<Integer> idUserToAddList)
  {
    this.idUserToAddList = idUserToAddList;
  }

  public void setUserRegistration(boolean userRegistration)
  {
    this.userRegistration = userRegistration;
  }

  public boolean isRegisterOk()
  {
    return registerOk;
  }

  public void setFreeText(String freeText)
  {
    this.freeText = freeText;
  }
  public String getActivePage()
  {
    return activePage;
  }
  public void setActivePage(String activePage)
  {
    this.activePage = activePage;
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

  public List getRoleToAddList()
  {
    return roleToAddList;
  }

  /**
   * @return the playersToAddList
   */
  public List getPlayersToAddList()
  {
    return playersToAddList;
  }

  /**
   * @param selectedNumberPlayersToAdd the selectedNumberPlayersToAdd to set
   */
  public void setSelectedNumberPlayersToAdd(int selectedNumberPlayersToAdd)
  {
    this.selectedNumberPlayersToAdd = selectedNumberPlayersToAdd;
  }

  /**
   * @return the team
   */
  public String getTeam()
  {
    return team;
  }

  /**
   * @param team the team to set
   */
  public void setTeam(String team)
  {
    this.team = team;
  }
  // </editor-fold>
}
