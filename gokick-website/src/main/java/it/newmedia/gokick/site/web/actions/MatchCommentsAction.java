package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiMatchInfo;
import it.newmedia.gokick.site.guibean.GuiPlayerInfo;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.MatchCommentInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.*;
import it.newmedia.gokick.site.managers.SecurityManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.Date;
import java.util.List;

import it.newmedia.web.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * Classe contenente le azioni per gestire la visualizzazione ,il salvataggio,la preview e l'aggiornamento dei commenti alle partite
 */
public class MatchCommentsAction extends AuthenticationBaseAction implements Preparable {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  private final static String CONFIRM_MESSAGE__MATCH_MESSAGE_INSERTED = "inserted";
  private final static String CONFIRM_MESSAGE__MATCH_MESSAGE_DELETED = "deleted";
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(MatchCommentsAction.class);
  private int idMatchComment;
  private int idMatch;
  private Match currentMatch;
  private String matchStartDateString;
  //Inutile fare la query per contarli se poi bisogna comunque caricarli tutti, usiamo direttaemente al lista caricata
  //private int countPost;
  private List<MatchCommentInfo> matchCommentInfoList;
  private String comment;
  private String message;
  private String info;
  private boolean close;
  private UserInfo previewUserInfo;
  private UserInfo matchOwnerUser;
  private String previewDate;
  private int idSavedMessage;
  private boolean ancorError;
  private boolean ownerUser;


  private GuiMatchInfo guiMatchInfo;

  private int idPlayerReporter;

  private List<GuiPlayerInfo> playersRegisteredList;

  private UserInfo userInfoReporter;

  private boolean isRegistered;

  private Player playerToRegister;

  private int idPlayerToReporter;

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

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public void prepare()
  {
    this.idMatch = WebUtil.getRequestParamAsInt("idMatch", ServletActionContext.getRequest(), 0);
    if( this.idMatch <= 0)
    {
      return;
    }
    User currentUser = UserContext.getInstance().getUser();

    boolean canViewMatch = SecurityManager.canViewMatch(this.idMatch, currentUser.getId());
    if (!canViewMatch)
    {
      this.message = ("idMatchError");
      //return Constants.STRUTS_RESULT_NAME__INFO;
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

    int players = MatchManager.countMatchPlayers(idMatch, false, EnumPlayerStatus.Out, EnumPlayerStatus.OwnerRegistered, EnumPlayerStatus.UserRegistered, EnumPlayerStatus.UserRequestRegistered);

    this.guiMatchInfo.getMatchInfo().setPlayersRegistered(players);

    this.playersToAdd=this.guiMatchInfo.getMatchInfo().getMaxPlayers() - players;
  }

  @Override
  public String input()
  {
    if (!viewPermission())
    {
      this.message = ("idMatchCommentError");
      return Constants.STRUTS_RESULT_NAME__INFO;
    }

    this.matchStartDateString = DateManager.showDate(this.currentMatch.getMatchStart(), DateManager.FORMAT_DATE_7);
    //Inutile fare la query per contarli se poi bisogna comunque caricarli tutti, usiamo direttaemente al lista caricata
    //this.countPost = GuiManager.countCommentsByMatch(this.idMatch);

    this.matchCommentInfoList = MatchCommentManager.getMatchCommentInfoList(this.idMatch);
    
    return INPUT;
  }

  public void validateSave()
  {
    viewPermission();

    if (this.comment.length() == Constants.STRING_LENGHT_0 || this.comment.trim().equals("<br />"))  //aggiunto da CKEditor + fireFox
    {
      this.ancorError=true;
      addFieldError("commentInvalid", getText("error.matchComment.TestoMancante"));
    }

    this.matchStartDateString = DateManager.showDate(this.currentMatch.getMatchStart(), DateManager.FORMAT_DATE_7);
    //Inutile fare la query per contarli se poi bisogna comunque caricarli tutti, usiamo direttaemente al lista caricata
    //this.countPost = GuiManager.countCommentsByMatch(this.idMatch);

    this.matchCommentInfoList = MatchCommentManager.getMatchCommentInfoList(this.idMatch);
  }

  public String viewAll()
  {
    if (!viewPermission())
    {
      this.message = ("idMatchCommentError");
      return Constants.STRUTS_RESULT_NAME__INFO;
    }

    this.matchStartDateString = DateManager.showDate(this.currentMatch.getMatchStart(), DateManager.FORMAT_DATE_7);
    //Inutile fare la query per contarli se poi bisogna comunque caricarli tutti, usiamo direttaemente al lista caricata
    //this.countPost = GuiManager.countCommentsByMatch(this.idMatch);

    this.matchCommentInfoList = MatchCommentManager.getMatchCommentInfoList(this.idMatch);

    this.matchOwnerUser=InfoProvider.getUserInfo(this.currentMatch.getUserOwner().getId());

    if (matchOwnerUser.getId()==UserContext.getInstance().getUser().getId())
      this.ownerUser=true;

    boolean updateLastViewComment = MatchCommentManager.updateLastViewComment(UserContext.getInstance().getUser().getId(),this.idMatch);

    UserContext.getInstance().resetLastViewComment();

    String messageComment="";
    if (this.info != null && this.info.length() > 0)
    {
      if (this.info.equalsIgnoreCase(CONFIRM_MESSAGE__MATCH_MESSAGE_INSERTED))
      {
        messageComment=getText("message.InseritoCommentoAlMatch");
      }
      else if (this.info.equalsIgnoreCase(CONFIRM_MESSAGE__MATCH_MESSAGE_DELETED))
      {
        messageComment=getText("message.EliminatoCommentoDelMatch");
      }
      UserContext.getInstance().setLastMessage(messageComment);
    }

    return SUCCESS;
  }

  public String save()
  {
    if (!viewPermission())
    {
      this.message = ("idMatchCommentError");
      return Constants.STRUTS_RESULT_NAME__INFO;
    }

    this.comment = StringUtils.left(this.comment, Constants.STRING_LENGHT_5000);

    this.setIdSavedMessage(MatchCommentManager.save(this.comment, this.currentMatch, UserContext.getInstance().getUser()));
    if (this.getIdSavedMessage() == 0)
    {
      addActionError(getText("error.matchComment.ImpossibileSalvare"));
      return INPUT;
    }
    this.message = CONFIRM_MESSAGE__MATCH_MESSAGE_INSERTED;
    return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_INSERTED;
  }

  public String delete()
  {
    if (this.idMatchComment <= 0)
    {
      addActionError(getText("error.matchComment.ImpossibileEliminare"));
      return INPUT;
    }

    int idCurrentUser = UserContext.getInstance().getUser().getId();

    MatchCommentInfo currentMatchCommentInfo = MatchCommentManager.getMatchCommentInfo(this.idMatchComment);
    if (currentMatchCommentInfo.getIdUserOwner() != idCurrentUser && currentMatchCommentInfo.getIdUserOwnerMatch() != idCurrentUser)
    {
      addActionError(getText("error.matchComment.ImpossibileEliminare"));
      return INPUT;
    }

    boolean success = MatchCommentManager.delete(currentMatchCommentInfo.getIdMatchComment(), idCurrentUser);
    if (!success)
    {
      addActionError(getText("error.matchComment.ImpossibileEliminare"));
      return INPUT;
    }

    this.idMatch = currentMatchCommentInfo.getIdMatch();
    this.message = CONFIRM_MESSAGE__MATCH_MESSAGE_DELETED;
    return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_DELETED;
  }

  public String edit()
  {
    if (this.idMatchComment <= 0)
    {
      addActionError(getText("error.matchComment.ImpossibileModificare"));
      return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_EDIT;
    }

    int idCurrentUser = UserContext.getInstance().getUser().getId();

    MatchCommentInfo currentMatchCommentInfo = MatchCommentManager.getMatchCommentInfo(this.idMatchComment);
    if (currentMatchCommentInfo.getIdUserOwner() != idCurrentUser)
    {
      addActionError(getText("error.matchComment.ImpossibileModificare"));
      return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_EDIT;
    }

    this.comment = currentMatchCommentInfo.getText();
    this.idSavedMessage = currentMatchCommentInfo.getIdMatchComment();

    return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_EDIT;
  }

  public String update()
  {
    if (this.idMatchComment <= 0)
    {
      addActionError(getText("error.matchComment.ImpossibileModificare"));
      return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_EDIT;
    }

    if (this.comment == null || this.comment.length() <= 0)
    {
      addActionError(getText("error.matchComment.TestoMancante"));
      return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_EDIT;
    }

    if (this.comment.length() > Constants.STRING_LENGHT_5000)
    {
      this.comment = this.comment.substring(Constants.STRING_LENGHT_0, Constants.STRING_LENGHT_5000 - 1);
    }

    int idCurrentUser = UserContext.getInstance().getUser().getId();

    MatchCommentInfo currentMatchCommentInfo = MatchCommentManager.getMatchCommentInfo(this.idMatchComment);
    if (currentMatchCommentInfo.getIdUserOwner() != idCurrentUser)
    {
      addActionError(getText("error.matchComment.ImpossibileModificare"));
      return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_EDIT;
    }
    
    this.idSavedMessage = MatchCommentManager.update(this.idMatchComment, this.comment);
    if (this.idSavedMessage==0)
    {
      addActionError(getText("error.matchComment.ImpossibileModificare"));
      return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_EDIT;
    }

    this.close = true;
    return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_EDIT;
  }

  public String preview()
  {
    this.previewUserInfo = InfoProvider.getUserInfo(UserContext.getInstance().getUser().getId());
    this.previewDate = DateManager.showDate(new Date(), DateManager.FORMAT_DATE_18,getCurrentObjLanguage(), getCurrentCobrand());

    return Constants.STRUTS_RESULT_NAME__MATCH_MESSAGE_PREVIEW;
  }

  private boolean viewPermission()
  {
    if (this.idMatch <= 0)
    {
      return false;
    }
    try
    {
      this.currentMatch = DAOFactory.getInstance().getMatchDAO().get(this.idMatch);
    }
    catch (Exception e)
    {
      return false;
    }
    if (this.currentMatch == null)
    {
      return false;
    }

    User currentUser=UserContext.getInstance().getUser();
    if (!SecurityManager.canViewMatch(this.idMatch, currentUser.getId()))
    {
      return false;
    }
    return true;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public List<MatchCommentInfo> getMatchCommentInfoList()
  {
    return matchCommentInfoList;
  }

  public void setMatchCommentInfoList(List<MatchCommentInfo> matchCommentInfoList)
  {
    this.matchCommentInfoList = matchCommentInfoList;
  }

  public int getCountPost()
  {
    return this.matchCommentInfoList.size();
  }

  public int getIdMatch()
  {
    return idMatch;
  }

  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  public Match getCurrentMatch()
  {
    return currentMatch;
  }

  public void setCurrentMatch(Match currentMatch)
  {
    this.currentMatch = currentMatch;
  }

  public String getMatchStartDateString()
  {
    return matchStartDateString;
  }

  public void setMatchStartDateString(String matchStartDateString)
  {
    this.matchStartDateString = matchStartDateString;
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public String getInfo()
  {
    return info;
  }

  public void setInfo(String info)
  {
    this.info = info;
  }

  public int getIdMatchComment()
  {
    return idMatchComment;
  }

  public void setIdMatchComment(int idMatchComment)
  {
    this.idMatchComment = idMatchComment;
  }

  public boolean isClose()
  {
    return close;
  }

  public void setClose(boolean close)
  {
    this.close = close;
  }

  public String getPreviewDate()
  {
    return previewDate;
  }

  public void setPreviewDate(String previewDate)
  {
    this.previewDate = previewDate;
  }

  public UserInfo getPreviewUserInfo()
  {
    return previewUserInfo;
  }

  public void setPreviewUserInfo(UserInfo previewUserInfo)
  {
    this.previewUserInfo = previewUserInfo;
  }
  
  /**
   * @return the idSavedMessage
   */
  public int getIdSavedMessage()
  {
    return idSavedMessage;
  }

  /**
   * @param idSavedMessage the idSavedMessage to set
   */
  public void setIdSavedMessage(int idSavedMessage)
  {
    this.idSavedMessage = idSavedMessage;
  }

  /**
   * @return the ancorError
   */
  public boolean isAncorError()
  {
    return ancorError;
  }

  /**
   * @return the matchOwnerUser
   */
  public UserInfo getMatchOwnerUser()
  {
    return matchOwnerUser;
  }

  /**
   * @return the ownerUser
   */
  public boolean isOwnerUser()
  {
    return ownerUser;
  }

  public String getCreated(String date)
  {
    
      return DateManager.showDate(date, Constants.DATE_FORMAT__YYYYMMDDHHMM, DateManager.FORMAT_DATE_18);
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
   * @param idPlayerReporter the playerVote to set
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
   * @param playersRegisteredList the playerRegisteredList to set
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
   * @param userInfoReporter the userInforeporter to set
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
