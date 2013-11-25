package it.newmedia.gokick.site.infos;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.MatchComment;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.DateManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.site.providers.TranslationProvider;
import it.newmedia.utils.DateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce le informazioni relative alle partite quando queste devono essere visualizzate all'interno dell'applicazione
 */
public class MatchInfo implements Serializable
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >

  /**
   *
   */
  public static final Comparator<MatchInfo> MATCH_START_ORDER =
          new Comparator<MatchInfo>()
          {

            public int compare(MatchInfo o1, MatchInfo o2)
            {
              if (o1.matchStart.before(o2.matchStart))
              {
                return -1;
              }
              if (o1.matchStart.after(o2.matchStart))
              {
                return 1;
              }
              return 0;
            }
          };
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static final Logger logger = Logger.getLogger(MatchInfo.class);
  private boolean valid;
  private int id;
  private int idUserOwner;
  private int recordedChallengesUserOwner;
  private int recordedMatchesUserOwner;
  private int idReporter;
  private int playersRegistered;
  private int countMatchComments;
  private int idSportCenter;
  private int matchTypeTotPlayers;
  private int maxPlayers;
  private int maxSquadOutPlayers;
  private String surnameUserOwner;
  private String nameUserOwner;
  private String mobileUserOwner;
  private String emailUserOwner;
  private String notes;
  private String matchStartDay;
  private String matchStartHour;
  private String matchStartYear;
  private String sportCenterName;
  private String sportCenterAddress;
  private String sportCenterCity;
  private String sportCenterProvinceAbbreviation;
  private String matchTypeName;
  private String resultGoal;
  private String resultText;
  private String dayTerminationLimit;
  private String timeTerminationLimit;
  private String googleMapUrl;
  private String reporter;
  private boolean squadOutEnable;
  private boolean directlyRegistration;
  private boolean acceptTermination;
  private boolean registrationClosed;
  private boolean recorded;
  private boolean canceled;
  private boolean complete;
  private Date matchStart;
  private Date acceptTerminationLimit;
  private Date registrationStart;
  private Date recordedMatchDate;
  private Date lastComment;
  private List<Player> playerList;

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   *
   */
  public MatchInfo()
  {
    this.valid = false;
  }

// </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  /**
   *
   * @return
   */
  public boolean isAcceptTermination()
  {
    return acceptTermination;
  }

  /**
   *
   * @return
   */
  public Date getAcceptTerminationLimit()
  {
    return acceptTerminationLimit;
  }

  /**
   *
   * @return
   */
  public boolean isDirectlyRegistration()
  {
    return directlyRegistration;
  }

  /**
   *
   * @return
   */
  public String getEmailUserOwner()
  {
    return emailUserOwner;
  }

  /**
   *
   * @return
   */
  public int getId()
  {
    return id;
  }

  /**
   *
   * @return
   */
  public int getIdUserOwner()
  {
    return idUserOwner;
  }

  /**
   *
   * @return
   */
  public Date getMatchStart()
  {
    return matchStart;
  }

  /**
   *
   * @return
   */
  public String getMatchStartDay()
  {
    return matchStartDay;
  }

  /**
   *
   * @return
   */
  public String getMatchStartHour()
  {
    return matchStartHour;
  }

  /**
   *
   * @return
   */
  public String getMatchTypeName()
  {
    return matchTypeName;
  }

  /**
   *
   * @return
   */
  public int getMatchTypeTotPlayers()
  {
    return matchTypeTotPlayers;
  }

  /**
   *
   * @return
   */
  public int getMaxPlayers()
  {
    return maxPlayers;
  }

  /**
   *
   * @return
   */
  public int getMaxSquadOutPlayers()
  {
    return maxSquadOutPlayers;
  }

  /**
   *
   * @return
   */
  public String getMobileUserOwner()
  {
    return mobileUserOwner;
  }

  /**
   *
   * @return
   */
  public String getNotes()
  {
    return notes;
  }

  /**
   *
   * @return
   */
  public boolean isRegistrationClosed()
  {
    return registrationClosed;
  }

  /**
   *
   * @return
   */
  public Date getRegistrationStart()
  {
    return registrationStart;
  }

  /**
   *
   * @return
   */
  public int getIdSportCenter()
  {
    return idSportCenter;
  }

  /**
   *
   * @return
   */
  public String getSportCenterAddress()
  {
    return sportCenterAddress;
  }

  /**
   *
   * @return
   */
  public String getSportCenterCity()
  {
    return sportCenterCity;
  }

  /**
   *
   * @return
   */
  public String getSportCenterProvinceAbbreviation()
  {
    return sportCenterProvinceAbbreviation;
  }

  /**
   *
   * @return
   */
  public String getSportCenterName()
  {
    return sportCenterName;
  }

  /**
   *
   * @return
   */
  public boolean isSquadOutEnable()
  {
    return squadOutEnable;
  }

  /**
   *
   * @return
   */
  public boolean isValid()
  {
    return valid;
  }

  /**
   *
   * @return
   */
  public String getNameUserOwner()
  {
    return nameUserOwner;
  }

  /**
   *
   * @return
   */
  public int getRecordedChallengesUserOwner()
  {
    return recordedChallengesUserOwner;
  }

  /**
   *
   * @return
   */
  public int getRecordedMatchesUserOwner()
  {
    return recordedMatchesUserOwner;
  }

  /**
   *
   * @return
   */
  public String getSurnameUserOwner()
  {
    return surnameUserOwner;
  }

  /**
   *
   * @return
   */
  public boolean isRecorded()
  {
    return recorded;
  }

  /**
   *
   * @return
   */
  public boolean isCanceled()
  {
    return this.canceled;
  }

  /**
   *
   * @return
   */
  public String getResultGoal()
  {
    return resultGoal;
  }

  /**
   *
   * @param resultGoal
   */
  public void setResultGoal(String resultGoal)
  {
    this.resultGoal = resultGoal;
  }

  /**
   *
   * @return
   */
  public String getResultText()
  {
    return resultText;
  }

  /**
   *
   * @param resultText
   */
  public void setResultText(String resultText)
  {
    this.resultText = resultText;
  }

  /**
   *
   * @return
   */
  public String getMatchStartYear()
  {
    return matchStartYear;
  }

  /**
   *
   * @param matchStartYear
   */
  public void setMatchStartYear(String matchStartYear)
  {
    this.matchStartYear = matchStartYear;
  }

  /**
   * @return the dayTerminationLimit
   */
  public String getDayTerminationLimit()
  {
    return dayTerminationLimit;
  }

  /**
   * @return the timeTerminationLimit
   */
  public String getTimeTerminationLimit()
  {
    return timeTerminationLimit;
  }

  /**
   * @return the googleMapUrl
   */
  public String getGoogleMapUrl()
  {
    return googleMapUrl;
  }

  /**
   * @return the reporter
   */
  public String getReporter()
  {
    return reporter;
  }

  /**
   * @return the recordedMatchDate
   */
  public Date getRecordedMatchDate()
  {
    return recordedMatchDate;
  }

  /**
   * @return the idReporter
   */
  public int getIdReporter()
  {
    return idReporter;
  }

  /**
   * @return the lastComment
   */
  public Date getLastComment()
  {
    return lastComment;
  }

  /**
   * @param lastComment the lastComment to set
   */
  public void setLastComment(Date lastComment)
  {
    this.lastComment = lastComment;
  }

  /**
   * @return the playersRegistered
   */
  public int getPlayersRegistered()
  {
    return playersRegistered;
  }

  /**
   * @return the countMatchComments
   */
  public int getCountMatchComments()
  {
    return countMatchComments;
  }

  public List<Player> getPlayerList()
  {
    return playerList;
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   *
   * @param match
   */
  public void load(Match match)
  {
    this.id = match.getId();
    this.idUserOwner = match.getUserOwner().getId();
    this.nameUserOwner = match.getUserOwner().getFirstName();
    this.surnameUserOwner = match.getUserOwner().getLastName();

    this.countMatchComments = 0;
    for (MatchComment matchComment : match.getCommentList())
    {
      if (matchComment.getApproved() && !(matchComment.getDeleted()))
      {
        this.countMatchComments = this.getCountMatchComments() + 1;
      }
    }
    playerList = new ArrayList<Player>();
    playerList.addAll(match.getPlayerList());

    this.setPlayersRegistered(0);
    Player reporterPl = null;
    for (Player player : match.getPlayerList())
    {
      if (!player.getEnumPlayerType().equals(EnumPlayerType.Missing) && (player.getEnumPlayerStatus().equals(EnumPlayerStatus.OwnerRegistered)
              || player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRegistered)
              || player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequestRegistered)
              || player.getEnumPlayerStatus().equals(EnumPlayerStatus.Out)))
      {
        this.setPlayersRegistered(this.playersRegistered + 1);
      }

      if (player.getReporterEnabled())
      {
        reporterPl = new Player();
        reporterPl = player;
      }

    }


    //Player reporterPl = MatchManager.getReporter(match.getId());
    if (reporterPl != null)
    {
      this.reporter = reporterPl.getUser().getFirstName() + " " + reporterPl.getUser().getLastName();
      this.idReporter = reporterPl.getUser().getId();
    }
    else
    {
      this.reporter = this.nameUserOwner + " " + this.surnameUserOwner;
      this.idReporter = this.idUserOwner;
    }

    this.recordedMatchesUserOwner = match.getUserOwner().getRecordedMatches();
    this.recordedChallengesUserOwner = match.getUserOwner().getRecordedChallenges();
    this.mobileUserOwner = match.getMobileUserOwner();
    this.emailUserOwner = match.getEmailUserOwner();
    this.notes = match.getNotes();
    this.matchStart = match.getMatchStart();
    this.matchStartDay = DateManager.showDate(match.getMatchStart(), DateManager.FORMAT_DATE_8);
    this.matchStartHour = DateUtil.formatDate(match.getMatchStart(), Constants.DATE_FORMAT__HHMM);
    this.matchStartYear = DateUtil.formatDate(match.getMatchStart(), Constants.DATE_FORMAT__YY);
    this.idSportCenter = match.getSportCenter().getId();
    this.sportCenterName = match.getSportCenterName();
    this.sportCenterAddress = match.getSportCenterAddress();
    this.sportCenterCity = match.getSportCenterCity();
    this.sportCenterProvinceAbbreviation = match.getSportCenter().getProvince().getAbbreviation();

    this.matchTypeName = TranslationProvider.getTranslationValue(match.getMatchType().getKeyName(), UserContext.getInstance().getLanguage(), UserContext.getInstance().getCurrentCobrand());
    this.matchTypeTotPlayers = match.getMatchType().getTotTeamPlayer() / 2;
    this.maxPlayers = match.getMaxPlayers();
    this.squadOutEnable = match.isSquadOutEnabled();
    this.directlyRegistration = match.isDirectlyRegistration();
    this.maxSquadOutPlayers = match.getMaxSquadOutPlayers();
    this.acceptTermination = match.isAcceptTermination();
    this.acceptTerminationLimit = match.getAcceptTerminationLimit();
    this.complete = (this.playersRegistered >= this.maxPlayers);

    if (match.getLastComment() != null)
    {
      this.lastComment = match.getLastComment();
    }

    if (this.acceptTermination != false)
    {
      this.dayTerminationLimit = DateUtil.formatDate(acceptTerminationLimit, "dd/MM");
      this.timeTerminationLimit = DateUtil.formatDate(acceptTerminationLimit, "HH:mm");
    }

    this.registrationClosed = match.getRegistrationClosed();
    this.registrationStart = match.getRegistrationStart();

    if (match.getRecorded())
    {
      this.resultGoal = String.format("%1$s-%2$s", match.getTeamGoalsOne(), match.getTeamGoalsTwo());
      this.resultText = "";
      int goalTeamOne = Integer.valueOf(match.getTeamGoalsOne());
      int goalTeamTwo = Integer.valueOf(match.getTeamGoalsTwo());
      if (goalTeamOne > goalTeamTwo)
      {
        this.resultText = EnumPlayerType.TeamOne.getValue();
      }
      else if (goalTeamOne < goalTeamTwo)
      {
        this.resultText = EnumPlayerType.TeamTwo.getValue();
      }
      if (match.getRecordedDate() != null)
      {
        this.recordedMatchDate = match.getRecordedDate();
      }
    }
    this.recorded = match.getRecorded();
    this.canceled = match.getCanceled();
    
    SportCenterInfo spInfo = InfoProvider.getSportCenterInfo(match.getSportCenter().getId());
    
    this.googleMapUrl = spInfo.getGoogleMapsUrl();

    this.valid = true;
  }

  /**
   * @param playersRegistered the playersRegistered to set
   */
  public void setPlayersRegistered(int playersRegistered)
  {
    this.playersRegistered = playersRegistered;
  }

  /**
   * @return the complete
   */
  public boolean isComplete()
  {
    return complete;
  }

  public Player getPlayerByIdUser(int idUser)
  {
    for (Player p : getPlayerList())
    {
      if (p.getUser() != null && p.getUser().getId() == idUser)
      {
        return p;
      }
    }
    return null;
  }
  // </editor-fold>
}
