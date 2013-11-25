package it.newmedia.gokick.data.hibernate.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Classe che rappresenta l'oggetto Match che fa riferimento alla tabella MATCHES.
 *
 * @hibernate.class
 * table="MATCHES"
 */
public class Match extends ABean
{
  private User userOwner;

  private String emailUserOwner;

  private String mobileUserOwner;

  private Date matchStart;

  private String notes;

  private Date registrationStart;

  private boolean directlyRegistration;

  private boolean squadOutEnabled;

  private boolean registrationClosed;

  private boolean acceptTermination;

  private Date acceptTerminationLimit;

  private Integer maxPlayers;

  private Integer maxSquadOutPlayers;

  private String sportCenterName;

  private String sportCenterAddress;

  private String sportCenterCity;

  private String teamNameOne;

  private String teamNameTwo;

  private String teamShirtTwo;

  private String teamShirtOne;

  private String teamGoalsOne;

  private String teamGoalsTwo;

  private String comment;

  private boolean recorded;

  private Date recordedDate;

  private boolean canceled;

  private boolean notified;

  private Date lastComment;

  private Date created;

  private Set<Player> playerList = new HashSet<Player>();

  private Set<MatchComment> commentList = new HashSet<MatchComment>();

  private MatchType matchType;

  private SportCenter sportCenter;

  /**
   * @hibernate.id
   * column="ID_MATCH"
   * generator-class="native"
   * unsaved-value="null"
   */
  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_USER_OWNER"
   * cascade="none"
   * not-null="true"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   */
  public User getUserOwner()
  {
    return userOwner;
  }

  public void setUserOwner(User userOwner)
  {
    this.userOwner = userOwner;
  }

  /**
   * @hibernate.property
   * column="EMAIL_USER_OWNER"
   */
  public String getEmailUserOwner()
  {
    return emailUserOwner;
  }

  public void setEmailUserOwner(String emailUserOwner)
  {
    this.emailUserOwner = emailUserOwner;
  }

  /**
   * @hibernate.property
   * column="MOBILE_USER_OWNER"
   */
  public String getMobileUserOwner()
  {
    return mobileUserOwner;
  }

  public void setMobileUserOwner(String mobileUserOwner)
  {
    this.mobileUserOwner = mobileUserOwner;
  }

  /**
   * @hibernate.property
   * column="MATCH_START"
   */
  public Date getMatchStart()
  {
    return matchStart;
  }

  public void setMatchStart(Date matchStart)
  {
    this.matchStart = matchStart;
  }

  /**
   * @hibernate.property
   * column="ACCEPT_TERMINATION"
   */
  public boolean isAcceptTermination()
  {
    return acceptTermination;
  }

  public void setAcceptTermination(boolean acceptTermination)
  {
    this.acceptTermination = acceptTermination;
  }

  /**
   * @hibernate.property
   * column="ACCEPT_TERMINATION_LIMIT"
   */
  public Date getAcceptTerminationLimit()
  {
    return acceptTerminationLimit;
  }

  public void setAcceptTerminationLimit(Date acceptTerminationLimit)
  {
    this.acceptTerminationLimit = acceptTerminationLimit;
  }

  /**
   * @hibernate.property
   * column="NOTES"
   */
  public String getNotes()
  {
    return notes;
  }

  public void setNotes(String notes)
  {
    this.notes = notes;
  }

  /**
   * @hibernate.property
   * column="DIRECTLY_REGISTRATION"
   */
  public boolean isDirectlyRegistration()
  {
    return directlyRegistration;
  }

  public void setDirectlyRegistration(boolean directlyRegistration)
  {
    this.directlyRegistration = directlyRegistration;
  }

  /**
   * @hibernate.property
   * column="REGISTRATION_START"
   */
  public Date getRegistrationStart()
  {
    return registrationStart;
  }

  public void setRegistrationStart(Date registrationStart)
  {
    this.registrationStart = registrationStart;
  }

  /**
   * @hibernate.property
   * column="SQUAD_OUT_ENABLED"
   */
  public boolean isSquadOutEnabled()
  {
    return squadOutEnabled;
  }

  public void setSquadOutEnabled(boolean squadOutEnabled)
  {
    this.squadOutEnabled = squadOutEnabled;
  }

  /**
   * @hibernate.property
   * column="REGISTRATION_CLOSED"
   */
  public boolean getRegistrationClosed()
  {
    return registrationClosed;
  }

  public void setRegistrationClosed(boolean registrationClosed)
  {
    this.registrationClosed = registrationClosed;
  }

  /**
   * @hibernate.property
   * column="MAX_PLAYERS"
   */
  public Integer getMaxPlayers()
  {
    return maxPlayers;
  }

  public void setMaxPlayers(Integer maxPlayers)
  {
    this.maxPlayers = maxPlayers;
  }

  /**
   * @hibernate.property
   * column="MAX_SQUAD_OUT_PLAYERS"
   */
  public Integer getMaxSquadOutPlayers()
  {
    return maxSquadOutPlayers;
  }

  public void setMaxSquadOutPlayers(Integer maxSquadOutPlayers)
  {
    this.maxSquadOutPlayers = maxSquadOutPlayers;
  }

  /**
   * @hibernate.property
   * column="SPORTS_CENTER_NAME"
   */
  public String getSportCenterName()
  {
    return sportCenterName;
  }

  public void setSportCenterName(String sportCenterName)
  {
    this.sportCenterName = sportCenterName;
  }

  /**
   * @hibernate.property
   * column="SPORTS_CENTER_ADDRESS"
   */
  public String getSportCenterAddress()
  {
    return sportCenterAddress;
  }

  public void setSportCenterAddress(String sportCenterAddress)
  {
    this.sportCenterAddress = sportCenterAddress;
  }

  /**
   * @hibernate.property
   * column="SPORTS_CENTER_CITY"
   */
  public String getSportCenterCity()
  {
    return sportCenterCity;
  }

  public void setSportCenterCity(String sportCenterCity)
  {
    this.sportCenterCity = sportCenterCity;
  }

  /**
   * @hibernate.property
   * column="TEAM_NAME_ONE"
   */
  public String getTeamNameOne()
  {
    return teamNameOne;
  }

  public void setTeamNameOne(String teamNameOne)
  {
    this.teamNameOne = teamNameOne;
  }

  /**
   * @hibernate.property
   * column="TEAM_NAME_TWO"
   */
  public String getTeamNameTwo()
  {
    return teamNameTwo;
  }

  public void setTeamNameTwo(String teamNameTwo)
  {
    this.teamNameTwo = teamNameTwo;
  }

  /**
   * @hibernate.property
   * column="TEAM_SHIRT_ONE"
   */
  public String getTeamShirtOne()
  {
    return teamShirtOne;
  }

  public void setTeamShirtOne(String teamShirtOne)
  {
    this.teamShirtOne = teamShirtOne;
  }

  /**
   * @hibernate.property
   * column="TEAM_SHIRT_TWO"
   */
  public String getTeamShirtTwo()
  {
    return teamShirtTwo;
  }

  public void setTeamShirtTwo(String teamShirtTwo)
  {
    this.teamShirtTwo = teamShirtTwo;
  }

  /**
   * @hibernate.property
   * column="TEAM_GOALS_ONE"
   */
  public String getTeamGoalsOne()
  {
    return teamGoalsOne;
  }

  public void setTeamGoalsOne(String teamGoalsOne)
  {
    this.teamGoalsOne = teamGoalsOne;
  }

  /**
   * @hibernate.property
   * column="TEAM_GOALS_TWO"
   */
  public String getTeamGoalsTwo()
  {
    return teamGoalsTwo;
  }

  public void setTeamGoalsTwo(String teamGoalsTwo)
  {
    this.teamGoalsTwo = teamGoalsTwo;
  }

  /**
   * @hibernate.property
   * column="COMMENT"
   */
  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  /**
   * @hibernate.property
   * column="RECORDED"
   */
  public boolean getRecorded()
  {
    return recorded;
  }

  public void setRecorded(boolean recorded)
  {
    this.recorded = recorded;
  }

  /**
   * @hibernate.property
   * column="RECORDED_DATE"
   */
  public Date getRecordedDate()
  {
    return recordedDate;
  }

  public void setRecordedDate(Date recordedDate)
  {
    this.recordedDate = recordedDate;
  }

  /**
   * @hibernate.property
   * column="CANCELED"
   */
  public boolean getCanceled()
  {
    return canceled;
  }

  public void setCanceled(boolean canceled)
  {
    this.canceled = canceled;
  }

  /**
   * @hibernate.property
   * column="NOTIFIED"
   */
  public boolean getNotified()
  {
    return notified;
  }

  public void setNotified(boolean notified)
  {
    this.notified = notified;
  }

  /**
   * @hibernate.property
   * column="LAST_COMMENT"
   */
  public Date getLastComment()
  {
    return lastComment;
  }

  public void setLastComment(Date lastComment)
  {
    this.lastComment = lastComment;
  }

  /**
   * @hibernate.property
   * column="CREATED"
   */
  public Date getCreated()
  {
    return created;
  }

  public void setCreated(Date created)
  {
    this.created = created;
  }

  /**
   *
   * @return
   * @hibernate.set
   * table="PLAYERS_TO_MATCHES"
   * cascade="all"
   * inverse="false"
   * lazy="true"
   * outer-join="false"
   * 
   * @hibernate.key
   * column="ID_MATCH"
   * 
   * @hibernate.many-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.Player"
   * column="ID_PLAYER"
   * outer-join="true"
   *
   */
  public Set<Player> getPlayerList()
  {
    return playerList;
  }

  public void setPlayerList(Set<Player> playerList)
  {
    this.playerList = playerList;
  }

  /**
   * @hibernate.set
   * inverse="true"
   * lazy="true"
   * @hibernate.key
   * column="ID_MATCH"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.MatchComment"
   */
  public Set<MatchComment> getCommentList()
  {
    return commentList;
  }

  public void setCommentList(Set<MatchComment> commentList)
  {
    this.commentList = commentList;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_MATCH_TYPE"
   * cascade="none"
   * not-null="true"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.MatchType"
   */
  public MatchType getMatchType()
  {
    return matchType;
  }

  public void setMatchType(MatchType matchType)
  {
    this.matchType = matchType;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_SPORTS_CENTER"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.SportCenter"
   */
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }

  public void setSportCenter(SportCenter sportCenter)
  {
    this.sportCenter = sportCenter;
  }

}
