package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiMatchInfo;
import it.newmedia.gokick.site.guibean.GuiPlayerInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.DateManager;
import it.newmedia.gokick.site.managers.GuiManager;
import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.gokick.site.managers.PlayerManager;
import it.newmedia.gokick.site.managers.SecurityManager;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Classe contenente le azioni per visualizzare le informazioni relative ad un match, con ripepilogo dettagli partita, stato d'iscrizione dell'utente,
 * se l'utente è organizzatore anche la possibilità di gestire le iscrizioni dei propri giocatori
 */
public class MatchAction extends AuthenticationBaseAction implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private GuiMatchInfo guiMatchInfo;

  private int idMatch;

  private int idPlayerReporter;

  private List<GuiPlayerInfo> playersRegisteredList;

  private UserInfo userInfoReporter;

  private boolean isRegistered;

  private Player playerToRegister;

  private int idPlayerToReporter;

  private String message;

  private boolean squadOutEnable;

  private int maxSquadOutPlayers;

  private String acceptTerminationDay;

  private String acceptTerminationTime;

  private boolean acceptTermination;

  private int countPost;

  private String registrationDateString;

  private boolean registrationClosed;

  private boolean matchDone;

  private int playersToAdd;


  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public void prepare()
  {
  }

  @Override
  public String execute()
  {

    User currentUser = UserContext.getInstance().getUser();

    boolean canViewMatch = SecurityManager.canViewMatch(this.idMatch, currentUser.getId());
    if (!canViewMatch)
    {
      this.message = ("idMatchError");
      return Constants.STRUTS_RESULT_NAME__INFO;
    }

    this.guiMatchInfo = GuiManager.getGuiMatchInfo(idMatch);  //controllo partita esistente

    //se la partita è avvenuta l'utente non deve poter modificare nulla, il ctrl evita che palayerStatus.jsp venga caricata
    if (this.guiMatchInfo .getMatchInfo().getMatchStart().before(new Date()) || this.guiMatchInfo .getMatchInfo().isCanceled())
      this.matchDone=true;


    //TODO: perchè matchinfo non contiene direttamente l'elenco dei post?
    this.countPost = GuiManager.countCommentsByMatch(this.idMatch);

    this.registrationClosed=guiMatchInfo.getMatchInfo().isRegistrationClosed();
    if (this.guiMatchInfo.getMatchInfo().getRegistrationStart()!=null)
    {
      this.registrationDateString = DateManager.showDate(this.guiMatchInfo.getMatchInfo().getRegistrationStart(), DateManager.FORMAT_DATE_3);
      if (this.guiMatchInfo.getMatchInfo().getRegistrationStart().after(new Date()))
        this.registrationClosed = true;
    }

    int players = MatchManager.countMatchPlayers( idMatch,
                                                  false,
                                                  EnumPlayerStatus.Out,
                                                  EnumPlayerStatus.OwnerRegistered,
                                                  EnumPlayerStatus.UserRegistered,
                                                  EnumPlayerStatus.UserRequestRegistered);

    this.guiMatchInfo.getMatchInfo().setPlayersRegistered(players);

    this.playersToAdd=this.guiMatchInfo.getMatchInfo().getMaxPlayers() - players;

    return SUCCESS;
  }

  public String loadMatchDetails()
  {
    if( !UserContext.getInstance().isLoggedIn())
    {
      return SUCCESS;
    }

    User currentUser = UserContext.getInstance().getUser();

    this.guiMatchInfo = GuiManager.getGuiMatchInfo(idMatch);  //controllo partita esistente

    //se la partita è avvenuta o annullata l'utente non deve poter modificare nulla, il ctrl evita che palayerStatus.jsp venga caricata
    if (this.guiMatchInfo .getMatchInfo().getMatchStart().before(new Date()) || this.guiMatchInfo .getMatchInfo().isCanceled())
      this.matchDone=true;

    this.squadOutEnable = guiMatchInfo.getMatchInfo().isSquadOutEnable();
    this.maxSquadOutPlayers = guiMatchInfo.getMatchInfo().getMaxSquadOutPlayers();
    this.acceptTermination = this.guiMatchInfo.getMatchInfo().isAcceptTermination();

    if (this.isAcceptTermination())  //recupero data e orario
    {
      this.acceptTerminationTime = DateManager.showDate(guiMatchInfo.getMatchInfo().getAcceptTerminationLimit(), DateManager.FORMAT_DATE_16);
      //se disdette scadute setto a falso la loro accettazione
      if (guiMatchInfo.getMatchInfo().getAcceptTerminationLimit().before(new Date()))
      {
        this.acceptTermination = false;
      }
    }

    this.playersRegisteredList = GuiManager.getGuiPlayersInfoListRegisteredByMatch(idMatch,false);  //va a settare il pagellaro

    Iterator iterator = this.playersRegisteredList.iterator();
    GuiPlayerInfo guiPlayerInfoit = new GuiPlayerInfo();
    while (iterator.hasNext())
    {
      guiPlayerInfoit = (GuiPlayerInfo) iterator.next();
      if (guiPlayerInfoit.getStatus() == EnumPlayerStatus.Out)
      {
        iterator.remove();
      }
    }

    UserInfo userInfo;
    this.userInfoReporter = this.guiMatchInfo.getOwnerUserInfo();
    GuiPlayerInfo guiOwnerReporter = new GuiPlayerInfo();
    guiOwnerReporter.load(null, userInfoReporter, guiMatchInfo.getMatchInfo());
    guiOwnerReporter.setId(0);

    boolean isOwnerRegistered = false;
    for (GuiPlayerInfo guiPlayerInfo : playersRegisteredList)
    {
      if (guiPlayerInfo.isReporter())
      {
        //TODO: non è un giro un po' complicato?!?
        //userInfo = UserInfoManager.getUserInfo(guiPlayerInfo.getUserInfo().getId());
        this.userInfoReporter = guiPlayerInfo.getUserInfo();
        this.idPlayerReporter = guiPlayerInfo.getId();
      }
      if ((guiPlayerInfo.getUserInfo().getId()) == currentUser.getId())
      {
        this.isRegistered = true;
      }
      if (guiPlayerInfo.isOwnerUser())
      {
        isOwnerRegistered = true;
      }
    }

    if (isOwnerRegistered == false)
      this.playersRegisteredList.add(guiOwnerReporter);

    return SUCCESS;
  }

  public String setReporter()
  {

    this.guiMatchInfo = GuiManager.getGuiMatchInfo(idMatch);  //controllo partita esistente

    if (this.getIdPlayerToReporter() > 0 && this.guiMatchInfo.isOwnerUser())
    {
      MatchManager.setReporter(this.idMatch, this.idPlayerToReporter);
      String lastMessage = getText("messagge.reporterChoosed");
      Player player = PlayerManager.getPlayer(this.idPlayerToReporter);
      lastMessage = lastMessage.replace(Constants.REPLACEMENT__PLAYER_REPORTER, (player.getUser().getFirstName() + " " + player.getUser().getLastName()));
      UserContext.getInstance().setLastMessage(lastMessage);
      
    }
    return SUCCESS;
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

  public GuiMatchInfo getGuiMatchInfo()
  {
    return guiMatchInfo;
  }

  public void setGuiMatchInfo(GuiMatchInfo guiMatchInfo)
  {
    this.guiMatchInfo = guiMatchInfo;
  }

  /**
   * @return the playerVote
   */
  public int getIdPlayerReporter()
  {
    return idPlayerReporter;
  }

  /**
   * @param playerVote the playerVote to set
   */
  public void setgetIdPlayerReporter(int idPlayerReporter)
  {
    this.idPlayerReporter = idPlayerReporter;
  }

  /**
   * @return the playerRegisteredList
   */
  public List<GuiPlayerInfo> getPlayersRegisteredList()
  {
    return playersRegisteredList;
  }

  /**
   * @param playerRegisteredList the playerRegisteredList to set
   */
  public void setPlayersRegisteredList(List<GuiPlayerInfo> playersRegisteredList)
  {
    this.playersRegisteredList = playersRegisteredList;
  }

  /**
   * @return the userInforeporter
   */
  public UserInfo getUserInfoReporter()
  {
    return userInfoReporter;
  }

  /**
   * @param userInforeporter the userInforeporter to set
   */
  public void setUserInfoReporter(UserInfo userInfoReporter)
  {
    this.userInfoReporter = userInfoReporter;
  }

  /**
   * @return the isRegistered
   */
  public boolean isIsRegistered()
  {
    return isRegistered;
  }

  /**
   * @param isRegistered the isRegistered to set
   */
  public void setIsRegistered(boolean isRegistered)
  {
    this.isRegistered = isRegistered;
  }

  /**
   * @return the playerToRegister
   */
  public Player getPlayerToRegister()
  {
    return playerToRegister;
  }

  /**
   * @param playerToRegister the playerToRegister to set
   */
  public void setPlayerToRegister(Player playerToRegister)
  {
    this.playerToRegister = playerToRegister;
  }

  /**
   * @return the idPlayerToReporter
   */
  public int getIdPlayerToReporter()
  {
    return idPlayerToReporter;
  }

  public void setIdPlayerToReporter(int idPlayerToReporter)
  {
    this.idPlayerToReporter = idPlayerToReporter;
  }

  /**
   * @return the message
   */
  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public boolean isSquadOutEnable()
  {
    return squadOutEnable;
  }

  public int getMaxSquadOutPlayers()
  {
    return maxSquadOutPlayers;
  }

  /**
   * @return the acceptTerminationDay
   */
  public String getAcceptTerminationDay()
  {
    return acceptTerminationDay;
  }

  /**
   * @return the acceptTerminationTime
   */
  public String getAcceptTerminationTime()
  {
    return acceptTerminationTime;
  }

  /**
   * @return the acceptTermination
   */
  public boolean isAcceptTermination()
  {
    return acceptTermination;
  }

  /**
   * @return the countPost
   */
  public int getCountPost()
  {
    return countPost;
  }

  /**
   * @return the registrationDateString
   */
  public String getRegistrationDateString()
  {
    return registrationDateString;
  }

  /**
   * @return the registrationClosed
   */
  public boolean isRegistrationClosed()
  {
    return registrationClosed;
  }

  /**
   * @return the matchDone
   */
  public boolean isMatchDone()
  {
    return matchDone;
  }
  /**
   * @return the playersToAdd
   */
  public int getPlayersToAdd()
  {
    return playersToAdd;
  }
  
  // </editor-fold>
}