package it.newmedia.gokick.site.guibean;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.site.providers.TranslationProvider;
import it.newmedia.utils.DateUtil;
import it.newmedia.utils.MathUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/**
 * Classe che contine le informazioni della partite in oggetto quando devono essere visualizzate all'interno dell'applicazione
 * @author ggeroldi
 */
public class GuiCalendarInfo extends AGuiBean
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  private boolean registrationsOpen;

  private boolean registrationsOpenInFuture;

  private boolean currentUserOwner;

  private boolean currentUserFriendOwner;

  private boolean currentUserNotFriendOwner;

  private boolean currentUserRegistered;

  private int missingPlayers;

  private long missingDays;

  private long missingHours;

  private long missingMinutes;

  private boolean hasComments;

  private String currentUserResultText;

  private String currentUserVote;

  private String currentUserGoals;

  private MatchInfo matchInfo;

  private UserInfo userOwnerInfo;
  
  private boolean currentUserRequest;

  private boolean reportEditable;

  private boolean commentsToRead;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --"  >
  /**
   *
   * @return
   */
  public MatchInfo getMatchInfo()
  {
    return matchInfo;
  }

  /**
   *
   * @return
   */
  public UserInfo getUserOwnerInfo()
  {
    return userOwnerInfo;
  }

  /**
   *
   * @return
   */
  public boolean isCurrentUserOwner()
  {
    return currentUserOwner;
  }

  /**
   *
   * @param currentUserOwner
   */
  public void setCurrentUserOwner(boolean currentUserOwner)
  {
    this.currentUserOwner = currentUserOwner;
  }

  /**
   *
   * @return
   */
  public long getMissingDays()
  {
    return missingDays;
  }

  /**
   *
   * @return
   */
  public long getMissingMinutes()
  {
    return missingMinutes;
  }

  /**
   *
   * @return
   */
  public int getMissingPlayers()
  {
    return missingPlayers;
  }

  /**
   *
   * @return
   */
  public boolean isHasComments()
  {
    return hasComments;
  }

  /**
   *
   * @param hasComments
   */
  public void setHasComments(boolean hasComments)
  {
    this.hasComments = hasComments;
  }

  /**
   *
   * @return
   */
  public boolean isRegistrationsOpen()
  {
    return registrationsOpen;
  }

  /**
   *
   * @return
   */
  public boolean isCurrentUserRegistered()
  {
    return currentUserRegistered;
  }

  /**
   *
   * @return
   */
  public long getMissingHours()
  {
    return missingHours;
  }

  /**
   *
   * @return
   */
  public boolean isCurrentUserFriendOwner()
  {
    return currentUserFriendOwner;
  }

  /**
   *
   * @return
   */
  public boolean isCurrentUserNotFriendOwner()
  {
    return currentUserNotFriendOwner;
  }

  /**
   *
   * @return
   */
  public boolean getCanceled()
  {
    return this.matchInfo.isCanceled();
  }

  /**
   *
   * @return
   */
  public String getCurrentUserResultText()
  {
    return currentUserResultText;
  }

  /**
   *
   * @param currentUserResultText
   */
  public void setCurrentUserResultText(String currentUserResultText)
  {
    this.currentUserResultText = currentUserResultText;
  }

  /**
   *
   * @return
   */
  public String getCurrentUserVote()
  {
    return currentUserVote;
  }

  /**
   *
   * @param currentUserVote
   */
  public void setCurrentUserVote(String currentUserVote)
  {
    this.currentUserVote = currentUserVote;
  }

  /**
   *
   * @return
   */
  public String getCurrentUserGoals()
  {
    return currentUserGoals;
  }

  /**
   *
   * @param currentUserGoals
   */
  public void setCurrentUserGoals(String currentUserGoals)
  {
    this.currentUserGoals = currentUserGoals;
  }

  /**
   * @return the registrationsOpenInFuture
   */
  public boolean isRegistrationsOpenInFuture()
  {
    return registrationsOpenInFuture;
  }

  /**
   * @return the currentUserRequest
   */
  public boolean isCurrentUserRequest()
  {
    return currentUserRequest;
  }

  /**
   * @return the commentsToRead
   */
  public boolean isCommentsToRead()
  {
    return commentsToRead;
  }


  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  /**
   * costruisce l'oggetti Gui
   * @param matchInfo matchIndo della partita d'interesse
   * @param friendsIdList lista amici dello user corrente
   */
  public GuiCalendarInfo(MatchInfo matchInfo, List<Integer> friendsIdList)
  {
    int idCurrentUser=UserContext.getInstance().getUser().getId();
    int idCurrentMatch=matchInfo.getId();
    this.matchInfo = matchInfo;

    if (matchInfo.getLastComment()==null)
      this.commentsToRead=false;
    else
    {
      HashMap<Integer, Date> hMLastViewcomment =UserContext.getInstance().getLastViewComment();
      Date lastView = hMLastViewcomment.get(idCurrentMatch);
      if (lastView==null || lastView.before(this.matchInfo.getLastComment()))
         this.commentsToRead=true;
    }

    this.userOwnerInfo = InfoProvider.getUserInfo(this.matchInfo.getIdUserOwner());

    //int countRegistered = GuiManager.countRegisteredByMatch(idCurrentMatch);
    int countRegistered = matchInfo.getPlayersRegistered();
    //int countComments = GuiManager.countCommentsByMatch(idCurrentMatch);
    int countComments = matchInfo.getCountMatchComments();

    if (this.matchInfo.getIdUserOwner() == idCurrentUser)
    {
      this.currentUserOwner = true;
      this.currentUserFriendOwner = false;
      this.currentUserNotFriendOwner = false;
    }
    else
    {
      this.currentUserOwner = false;
      if (friendsIdList.contains(this.matchInfo.getIdUserOwner()))
      {
        this.currentUserFriendOwner = true;
        this.currentUserNotFriendOwner = false;
      }
      else
      {
        this.currentUserFriendOwner = false;
        this.currentUserNotFriendOwner = true;
      }
    }

    //Player player = MatchManager.getPlayer(idCurrentMatch, idCurrentUser);
    Player player = matchInfo.getPlayerByIdUser(idCurrentUser);

    if (player != null)
    {
      if (player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequest))
      {
        this.currentUserRequest = true;
      }
      else if (player.getEnumPlayerType().equals(EnumPlayerType.Missing) ||
                player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserCalled))
      {
        this.currentUserRegistered = false;
      }
      else
      {
        this.currentUserRegistered = true;
      }

      if (this.matchInfo.isCanceled())
      {
        this.currentUserRegistered = false;
        this.currentUserRequest = false;
      }

      if (this.getMatchInfo().isRecorded())
      {
        if (this.getMatchInfo().getResultText().equalsIgnoreCase(""))
        {
          this.currentUserResultText = TranslationProvider.getTranslationValue("label.match.draw", UserContext.getInstance().getLanguage(), UserContext.getInstance().getCurrentCobrand());
        }
        else if (this.getMatchInfo().getResultText().equalsIgnoreCase(player.getPlayerType()))
        {
          this.currentUserResultText = TranslationProvider.getTranslationValue("label.match.win", UserContext.getInstance().getLanguage(), UserContext.getInstance().getCurrentCobrand());
        }
        else
        {
          this.currentUserResultText = TranslationProvider.getTranslationValue("label.match.lose", UserContext.getInstance().getLanguage(), UserContext.getInstance().getCurrentCobrand());
        }
        BigDecimal userVote = MathUtil.divide(player.getVote(), 1, RoundingMode.HALF_UP, 1);

        this.currentUserVote = userVote.compareTo(BigDecimal.ZERO)>=0?  userVote.toString(): "";
        this.currentUserGoals = player.getGoals()!=null?player.getGoals().toString():"0";
      }
    }
    else
    {
      this.currentUserResultText = "";
      this.currentUserVote = "";
      this.currentUserGoals = "";
      this.currentUserRegistered = false;
    }

    if (this.matchInfo.isRegistrationClosed())
    {
      //registrazioni chiuse
      this.registrationsOpen = false;
      //this.missingPlayers = 0;
      this.missingPlayers = this.matchInfo.getMaxPlayers() - countRegistered;
      this.missingDays = 0;
      this.missingHours = 0;
      this.missingMinutes = 0;
    }
    else
    {
      if (this.matchInfo.getRegistrationStart() == null)
      {
        //registrazioni aperte
        this.registrationsOpen = true;
        this.missingPlayers = this.matchInfo.getMaxPlayers() - countRegistered;
        this.missingDays = 0;
        this.missingMinutes = 0;
      }
      else if ((this.matchInfo.getRegistrationStart()) != null && (this.matchInfo.getRegistrationStart().after(new Date())))
      {
        //registrazioni aperte con countdown
        this.missingPlayers = this.matchInfo.getMaxPlayers() - countRegistered;
        this.registrationsOpenInFuture=true;
        this.missingDays = DateUtil.getDiffDays(this.matchInfo.getRegistrationStart(), new Date());
        this.missingMinutes = 0;
        if (this.missingDays <= 0)
        {
          this.missingHours = DateUtil.getDiffHours(this.matchInfo.getRegistrationStart(), new Date());
          if (this.missingHours <= 0)
          {
            this.missingMinutes = DateUtil.getDiffMinutes(this.matchInfo.getRegistrationStart(), new Date());
          }
        }
      }
      else
      {
        //registrazioni aperte
        this.registrationsOpen = true;
        this.missingPlayers = this.matchInfo.getMaxPlayers() - countRegistered;
        this.missingDays = 0;
        this.missingHours = 0;
        this.missingMinutes = 0;
      }
    }

    this.hasComments = countComments > 0 ? true : false;
  }

  
  /**
   * @return "true" se la pagella della partita è editabile, date le condizioni
   */
  public boolean isReportEditable()
  {
    GregorianCalendar limitEditDate = new GregorianCalendar();
    if (this.matchInfo.getRecordedMatchDate()!=null)
    {
      limitEditDate.setTime(this.matchInfo.getRecordedMatchDate());
      limitEditDate.add(Calendar.DAY_OF_MONTH, AppContext.getInstance().getArchiveMatchEditableNumbersOfDays());
      if (limitEditDate.before(new GregorianCalendar()))
      {
        return false;
      }
    }
    return true;
  }
  
 
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  // </editor-fold>
}
