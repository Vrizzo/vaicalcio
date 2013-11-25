package it.newmedia.gokick.data.hibernate.beans;

import it.newmedia.gokick.data.enums.EnumSex;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Classe che rappresenta l'oggetto USER che fa riferimento alla tabella USERS.
 *
 * @hibernate.class
 * table="USERS"
 */
public class User extends ACreatedBean
{
  private String facebookIdUser;
  
  private String facebookAccessToken;
  
  private Country country;

  private Province province;

  private City city;

  private Country birthdayCountry;

  private Province birthdayProvince;

  private City birthdayCity;

  private Country nationalityCountry;

  private PlayerRole playerRole;

  private FootballTeam footballTeam;

  private PlayerFoot playerFoot;

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private Date birthDay;

  private String address;

  private String cap;

  private String mobile;

  private String telephone;

  private String sex;

  private Integer playerHeight;

  private Integer playerWeight;

  private String playerTitle;

  private String playerMainFeature;

  private Integer playerShirtNumber;

  private String infoFavouritePlayer;

  private String infoHobby;

  private String infoDream;

  private String infoAnnounce;

  private boolean marketEnabled;

  private boolean generalTermsAccepted;

  private boolean privacyAccepted;

  private boolean newsletterEnabled;

  private boolean organizeEnabled;

  private String lastIP;

  private Date lastLogin;

  private Date prevLogin;

  private Date lastVisitMessages;

  private Date prevVisitMessages;

  private Date thirdOrganizedMatch;

  private String userStatus;

  private String forumNickname;

  private boolean alertOnMessages;

  private boolean alertOnSquadRequest;

  private boolean alertOnSquadInsert;

  private boolean alertOnRegistrationStart;

  private boolean alertOnChange;

  private boolean alertOnSquadOutAccepted;

  private boolean alertOnReportCreated;

  private boolean anonymousEnabled;

  private int recordedMatches;

  private int recordedChallenges;

  private int playedMatches;

  private int playedChallenges;

  private String checkPending;

  private String checkPassword;

  private String invited_by;

  private Date checkPasswordExpire;

  private Set<Squad> squads;

  private Set<PictureCard> pictureCards;

  private Set<UsersPermission> userPermissions;

  private PhysicalCondition physicalCondition;

  private String physicalConditionDetail;

  private int maxInvitations;

  private Set<UserInvitation> userInvitations;

  private boolean facebookPostOnMatchCreation;

  private boolean facebookPostOnMatchRegistration;

  private boolean facebookPostOnMatchRecorded;

  private String cobrandCode;

    /**
   * @hibernate.id
   * column="ID_USER"
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
   * column="ID_COUNTRY"
   * cascade="none"
   * not-null="true"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.Country"
   */
  public Country getCountry()
  {
    return country;
  }

  public void setCountry(Country country)
  {
    this.country = country;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_PROVINCE"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.Province"
   */
  public Province getProvince()
  {
    return province;
  }

  public void setProvince(Province province)
  {
    this.province = province;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_CITY"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.City"
   */
  public City getCity()
  {
    return city;
  }

  public void setCity(City city)
  {
    this.city = city;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_BIRTHDAY_COUNTRY"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.Country"
   */
  public Country getBirthdayCountry()
  {
    return birthdayCountry;
  }

  public void setBirthdayCountry(Country birthdayCountry)
  {
    this.birthdayCountry = birthdayCountry;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_BIRTHDAY_PROVINCE"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.Province"
   */
  public Province getBirthdayProvince()
  {
    return birthdayProvince;
  }

  public void setBirthdayProvince(Province birthdayProvince)
  {
    this.birthdayProvince = birthdayProvince;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_BIRTHDAY_CITY"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.City"
   */
  public City getBirthdayCity()
  {
    return birthdayCity;
  }

  public void setBirthdayCity(City birthdayCity)
  {
    this.birthdayCity = birthdayCity;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_NATIONALITY_COUNTRY"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.Country"
   */
  public Country getNationalityCountry()
  {
    return nationalityCountry;
  }

  public void setNationalityCountry(Country nationalityCountry)
  {
    this.nationalityCountry = nationalityCountry;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_PLAYER_ROLE"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.PlayerRole"
   */
  public PlayerRole getPlayerRole()
  {
    return playerRole;
  }

  public void setPlayerRole(PlayerRole playerRole)
  {
    this.playerRole = playerRole;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_FAVOURITE_FOOTBALL_TEAM"
   * cascade="none"
   * not-null="false"
   * lazy="no-proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.FootballTeam"
   */
  public FootballTeam getFootballTeam()
  {
    return footballTeam;
  }

  public void setFootballTeam(FootballTeam footballTeam)
  {
    this.footballTeam = footballTeam;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_PLAYER_FOOT"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.PlayerFoot"
   */
  public PlayerFoot getPlayerFoot()
  {
    return playerFoot;
  }

  public void setPlayerFoot(PlayerFoot playerFoot)
  {
    this.playerFoot = playerFoot;
  }

  /**
   * @hibernate.property
   * column="EMAIL"
   */
  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   * @hibernate.property
   * column="PHYSICAL_CONDITION_DETAIL"
   */
  public String getPhysicalConditionDetail()
  {
    return physicalConditionDetail;
  }

  public void setPhysicalConditionDetail(String physicalConditionDetail)
  {
    this.physicalConditionDetail = physicalConditionDetail;
  }

  /**
   * @hibernate.property
   * column="PASSWORD"
   */
  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  /**
   * @hibernate.property
   * column="FIRST_NAME"
   */
  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * @hibernate.property
   * column="LAST_NAME"
   */
  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  /**
   * @hibernate.property
   * column="BIRTHDAY"
   */
  public Date getBirthDay()
  {
    return birthDay;
  }

  public void setBirthDay(Date birthDay)
  {
    this.birthDay = birthDay;
  }

  /**
   * @hibernate.property
   * column="ADDRESS"
   */
  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  /**
   * @hibernate.property
   * column="CAP"
   */
  public String getCap()
  {
    return cap;
  }

  public void setCap(String cap)
  {
    this.cap = cap;
  }

  /**
   * @hibernate.property
   * column="MOBILE"
   */
  public String getMobile()
  {
    return mobile;
  }

  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }

  /**
   * @hibernate.property
   * column="TELEPHONE"
   */
  public String getTelephone()
  {
    return telephone;
  }

  public void setTelephone(String telephone)
  {
    this.telephone = telephone;
  }

  /**
   * @hibernate.property
   * column="SEX"
   */
  public String getSex()
  {
    return sex;
  }

  public void setSex(String sex)
  {
    this.sex = sex;
  }

  public EnumSex getEnumSex()
  {
    if (this.sex == null)
      return EnumSex.Undefined;
    return EnumSex.getEnum(this.sex);
  }

  public void setEnumSex(EnumSex enumSex)
  {
    this.sex = enumSex.getValue();
  }

  /**
   * @hibernate.property
   * column="PLAYER_HEIGHT"
   */
  public Integer getPlayerHeight()
  {
    return playerHeight;
  }

  public void setPlayerHeight(Integer playerHeight)
  {
    this.playerHeight = playerHeight;
  }

  /**
   * @hibernate.property
   * column="PLAYER_WEIGHT"
   */
  public Integer getPlayerWeight()
  {
    return playerWeight;
  }

  public void setPlayerWeight(Integer playerWeight)
  {
    this.playerWeight = playerWeight;
  }

  /**
   * @hibernate.property
   * column="PLAYER_TITLE"
   */
  public String getPlayerTitle()
  {
    return playerTitle;
  }

  public void setPlayerTitle(String playerTitle)
  {
    this.playerTitle = playerTitle;
  }

  /**
   * @hibernate.property
   * column="PLAYER_MAIN_FEATURE"
   */
  public String getPlayerMainFeature()
  {
    return playerMainFeature;
  }

  public void setPlayerMainFeature(String playerMainFeature)
  {
    this.playerMainFeature = playerMainFeature;
  }

  /**
   * @hibernate.property
   * column="PLAYER_SHIRT_NUMBER"
   */
  public Integer getPlayerShirtNumber()
  {
    return playerShirtNumber;
  }

  public void setPlayerShirtNumber(Integer playerShirtNumber)
  {
    this.playerShirtNumber = playerShirtNumber;
  }

  /**
   * @hibernate.property
   * column="INFO_FAVOURITE_PLAYER"
   */
  public String getInfoFavouritePlayer()
  {
    return infoFavouritePlayer;
  }

  public void setInfoFavouritePlayer(String infoFavouritePlayer)
  {
    this.infoFavouritePlayer = infoFavouritePlayer;
  }

  /**
   * @hibernate.property
   * column="INFO_HOBBY"
   */
  public String getInfoHobby()
  {
    return infoHobby;
  }

  public void setInfoHobby(String infoHobby)
  {
    this.infoHobby = infoHobby;
  }

  /**
   * @hibernate.property
   * column="INFO_DREAM"
   */
  public String getInfoDream()
  {
    return infoDream;
  }

  public void setInfoDream(String infoDream)
  {
    this.infoDream = infoDream;
  }

  /**
   * @hibernate.property
   * column="INFO_ANNOUNCE"
   */
  public String getInfoAnnounce()
  {
    return infoAnnounce;
  }

  public void setInfoAnnounce(String infoAnnounce)
  {
    this.infoAnnounce = infoAnnounce;
  }

  /**
   * @hibernate.property
   * column="MARKET_ENABLED"
   */
  public boolean getMarketEnabled()
  {
    return marketEnabled;
  }

  public void setMarketEnabled(boolean marketEnabled)
  {
    this.marketEnabled = marketEnabled;
  }

  /**
   * @hibernate.property
   * column="GENERAL_TERMS_ACCEPTED"
   */
  public boolean getGeneralTermsAccepted()
  {
    return generalTermsAccepted;
  }

  public void setGeneralTermsAccepted(boolean generalTermsAccepted)
  {
    this.generalTermsAccepted = generalTermsAccepted;
  }

  /**
   * @hibernate.property
   * column="PRIVACY_ACCEPTED"
   */
  public boolean getPrivacyAccepted()
  {
    return privacyAccepted;
  }

  public void setPrivacyAccepted(boolean privacyAccepted)
  {
    this.privacyAccepted = privacyAccepted;
  }

  /**
   * @hibernate.property
   * column="NEWSLETTER_ENABLED"
   */
  public boolean getNewsletterEnabled()
  {
    return newsletterEnabled;
  }

  public void setNewsletterEnabled(boolean newsletterEnabled)
  {
    this.newsletterEnabled = newsletterEnabled;
  }

  /**
   * @hibernate.property
   * column="ORGANIZE_ENABLED"
   */
  public boolean getOrganizeEnabled()
  {
    return organizeEnabled;
  }

  public void setOrganizeEnabled(boolean organizeEnabled)
  {
    this.organizeEnabled = organizeEnabled;
  }

  /**
   * @hibernate.property
   * column="LAST_IP"
   */
  public String getLastIP()
  {
    return lastIP;
  }

  public void setLastIP(String lastIP)
  {
    this.lastIP = lastIP;
  }

  /**
   * @hibernate.property
   * column="LAST_LOGIN"
   */
  public Date getLastLogin()
  {
    return lastLogin;
  }

  public void setLastLogin(Date lastLogin)
  {
    this.lastLogin = lastLogin;
  }

  /**
   * @hibernate.property
   * column="PREV_LOGIN"
   */
  public Date getPrevLogin()
  {
    return prevLogin;
  }

  public void setPrevLogin(Date prevLogin)
  {
    this.prevLogin = prevLogin;
  }

  /**
   * @hibernate.property
   * column="LAST_VISIT_MESSAGES"
   */
  public Date getLastVisitMessages()
  {
    return lastVisitMessages;
  }

  public void setLastVisitMessages(Date lastVisitMessages)
  {
    this.lastVisitMessages = lastVisitMessages;
  }

  /**
   * @hibernate.property
   * column="PREV_VISIT_MESSAGES"
   */
  public Date getPrevVisitMessages()
  {
    return prevVisitMessages;
  }

  public void setPrevVisitMessages(Date prevVisitMessages)
  {
    this.prevVisitMessages = prevVisitMessages;
  }

  /**
   * @hibernate.property
   * column="USER_STATUS"
   */
  public String getUserStatus()
  {
    return userStatus;
  }

  public void setUserStatus(String userStatus)
  {
    this.userStatus = userStatus;
  }

  public EnumUserStatus getEnumUserStatus()
  {
    if (this.userStatus == null)
      return EnumUserStatus.Undefined;
    return EnumUserStatus.getEnum(this.userStatus);
  }

  public void setEnumUserStatus(EnumUserStatus enumUserStatus)
  {
    this.userStatus = enumUserStatus.getValue();
  }

  /**
   * @hibernate.property
   * column="FORUM_NICKNAME"
   */
  public String getForumNickname()
  {
    return forumNickname;
  }

  public void setForumNickname(String forumNickname)
  {
    this.forumNickname = forumNickname;
  }

  /**
   * @hibernate.property
   * column="ALERT_ON_MESSAGES"
   */
  public boolean getAlertOnMessages()
  {
    return alertOnMessages;
  }

  public void setAlertOnMessages(boolean alertOnMessages)
  {
    this.alertOnMessages = alertOnMessages;
  }

  /**
   * @hibernate.property
   * column="ALERT_ON_SQUAD_REQUEST"
   */
  public boolean getAlertOnSquadRequest()
  {
    return alertOnSquadRequest;
  }

  public void setAlertOnSquadRequest(boolean alertOnSquadRequest)
  {
    this.alertOnSquadRequest = alertOnSquadRequest;
  }

  /**
   * @hibernate.property
   * column="ALERT_ON_SQUAD_INSERT"
   */
  public boolean getAlertOnSquadInsert()
  {
    return alertOnSquadInsert;
  }

  public void setAlertOnSquadInsert(boolean alertOnSquadInsert)
  {
    this.alertOnSquadInsert = alertOnSquadInsert;
  }

  /**
   * @hibernate.property
   * column="ALERT_ON_REGISTRATION_START"
   */
  public boolean getAlertOnRegistrationStart()
  {
    return alertOnRegistrationStart;
  }

  public void setAlertOnRegistrationStart(boolean alertOnRegistrationStart)
  {
    this.alertOnRegistrationStart = alertOnRegistrationStart;
  }

  /**
   * @hibernate.property
   * column="ALERT_ON_CHANGES"
   */
  public boolean getAlertOnChange()
  {
    return alertOnChange;
  }

  public void setAlertOnChange(boolean alertOnChange)
  {
    this.alertOnChange = alertOnChange;
  }

  /**
   * @hibernate.property
   * column="ALERT_ON_SQUAD_OUT_ACCEPTED"
   */
  public boolean getAlertOnSquadOutAccepted()
  {
    return alertOnSquadOutAccepted;
  }

  public void setAlertOnSquadOutAccepted(boolean alertOnSquadOutAccepted)
  {
    this.alertOnSquadOutAccepted = alertOnSquadOutAccepted;
  }


 /**
   * @hibernate.property
   * column="ALERT_ON_REPORT_CREATED"
   */
   public boolean getAlertOnReportCreated()
  {
    return alertOnReportCreated;
  }
  public void setAlertOnReportCreated(boolean alertOnReportCreated)
  {
    this.alertOnReportCreated = alertOnReportCreated;
  }

  /**
   * @hibernate.property
   * column="ANONYMOUS_ENABLED"
   */
  public boolean getAnonymousEnabled()
  {
    return anonymousEnabled;
  }

  public void setAnonymousEnabled(boolean anonymousEnabled)
  {
    this.anonymousEnabled = anonymousEnabled;
  }

  /**
   * @hibernate.property
   * column="RECORDED_MATCHES"
   */
  public int getRecordedMatches()
  {
    return recordedMatches;
  }
  public void setRecordedMatches(int recordedMatches)
  {
    this.recordedMatches = recordedMatches;
  }

  /**
   * @hibernate.property
   * column="RECORDED_CHALLENGES"
   */
  public int getRecordedChallenges()
  {
    return recordedChallenges;
  }
  public void setRecordedChallenges(int recordedChallenges)
  {
    this.recordedChallenges = recordedChallenges;
  }

  /**
   * @hibernate.property
   * column="PLAYED_MATCHES"
   */
  public int getPlayedMatches()
  {
    return playedMatches;
  }
  public void setPlayedMatches(int playedMatches)
  {
    this.playedMatches = playedMatches;
  }

  /**
   * @hibernate.property
   * column="PLAYED_CHALLENGES"
   */
  public int getPlayedChallenges()
  {
    return playedChallenges;
  }
  public void setPlayedChallenges(int playedChallenges)
  {
    this.playedChallenges = playedChallenges;
  }

  /**
   * @hibernate.property
   * column="CHECK_PENDING"
   */
  public String getCheckPending()
  {
    return checkPending;
  }

  public void setCheckPending(String checkPending)
  {
    this.checkPending = checkPending;
  }

  /**
   * @hibernate.property
   * column="CHECK_PASSWORD"
   */
  public String getCheckPassword()
  {
    return checkPassword;
  }

  public void setCheckPassword(String checkPassword)
  {
    this.checkPassword = checkPassword;
  }

  /**
   * @hibernate.property
   * column="CHECK_PASSWORD_EXPIRE"
   */
  public Date getCheckPasswordExpire()
  {
    return checkPasswordExpire;
  }

  public void setCheckPasswordExpire(Date checkPasswordExpire)
  {
    this.checkPasswordExpire = checkPasswordExpire;
  }

  /**
   * @hibernate.set
   * inverse="true"
   * lazy="true"
   * cascade="all-delete-orphan"
   * @hibernate.key
   * column="ID_USER"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.Squad"
   */
  public Set<Squad> getSquads()
  {
    return squads;
  }

  public void setSquads(Set<Squad> squads)
  {
    this.squads = squads;
  }

  /**
   * @hibernate.set
   * inverse="true"
   * lazy="true"
   * @hibernate.key
   * column="ID_USER"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.PictureCard"
   */
  public Set<PictureCard> getPictureCards()
  {
    return pictureCards;
  }

  public void setPictureCards(Set<PictureCard> pictureCards)
  {
    this.pictureCards = pictureCards;
  }

  /**
   * @hibernate.set
   * inverse="true"
   * lazy="true"
   * @hibernate.key
   * column="ID_USER"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.UsersPermission"
   */
  public Set<UsersPermission> getUserPermissions()
  {
    return userPermissions;
  }

  public void setUserPermissions(Set<UsersPermission> userPermissions)
  {
    this.userPermissions = userPermissions;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_PHYSICAL_CONDITION"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.PhysicalCondition"
   */
  public PhysicalCondition getPhysicalCondition()
  {
    return physicalCondition;
  }

  public void setPhysicalCondition(PhysicalCondition physicalCondition)
  {
    this.physicalCondition = physicalCondition;
  }

  /**
   * @hibernate.set
   * inverse="true"
   * lazy="true"
   * cascade="all-delete-orphan"
   * @hibernate.key
   * column="ID_USER_OWNER"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.UserInvitation"
   */
  public Set<UserInvitation> getUserInvitations()
  {
    return userInvitations;
  }

  public void setUserInvitations(Set<UserInvitation> userInvitations)
  {
    this.userInvitations = userInvitations;
  }

  /**
   * @hibernate.property
   * column="MAX_INVITATIONS"
   *
   * @return Indica il numero massimo di inviti disponibili per l'utente
   * che non è il valore delle persone che ha invitato!
   */
  public int getMaxInvitations()
  {
    return maxInvitations;
  }

  public void setMaxInvitations(int maxInvitations)
  {
    this.maxInvitations = maxInvitations;
  }

  public Squad getFirstSquad()
  {
    if( this.squads == null )
      return null;
    if( this.squads.size() > 0 )
      return this.squads.iterator().next();
    return null;
  }

  public User()
  {
    this.squads = new HashSet<Squad>();
    //TODO: Eliminare se non serve
    //this.userSquadList = new HashSet<UserSquad>();
  }

  /**
   * @hibernate.property
   * column="INVITED_BY"
   * @return il codice speciale usato per invitare l'utente
   * o l'id utente invitante nel caso di invito normale
   */
  public String getInvited_by()
  {
    return invited_by;
  }
  public void setInvited_by(String invited_by)
  {
    this.invited_by = invited_by;
  }

  /**
   * @hibernate.property
   * column="THIRD_ORGANIZED_MATCH"
   */
  public Date getThirdOrganizedMatch()
  {
    return thirdOrganizedMatch;
  }
  public void setThirdOrganizedMatch(Date thirdOrganizedMatch)
  {
    this.thirdOrganizedMatch = thirdOrganizedMatch;
  }
  
  /**
   * @hibernate.property
   * column="FACEBOOK_ID_USER"
   */
  public String getFacebookIdUser()
  {
    return facebookIdUser;
  }
  
  public void setFacebookIdUser(String facebookIdUser)
  {
    this.facebookIdUser = facebookIdUser;
  }

  /**
   * @hibernate.property
   * column="FACEBOOK_ACCESS_TOKEN"
   */
  public String getFacebookAccessToken()
  {
    return facebookAccessToken;
  }

  public void setFacebookAccessToken(String facebookAccessToken)
  {
    this.facebookAccessToken = facebookAccessToken;
  }

  /**
   * Indica se deve essere fatto un post sulla bacheca FB
   * nel caso in cui l'utente crei una partita
   *
   * @hibernate.property
   * column="FACEBOOK_POST_ON_MATCH_CREATION"
   */
  public boolean isFacebookPostOnMatchCreation()
  {
    return facebookPostOnMatchCreation;
  }

  public void setFacebookPostOnMatchCreation(boolean facebookPostOnMatchCreation)
  {
    this.facebookPostOnMatchCreation = facebookPostOnMatchCreation;
  }

  /**
   * Indica se deve essere fatto un post sulla bacheca FB
   * nel caso in cui l'utente si iscrive a una partita
   *
   * @hibernate.property
   * column="FACEBOOK_POST_ON_MATCH_REGISTRATION"
   */
  public boolean isFacebookPostOnMatchRegistration()
  {
    return facebookPostOnMatchRegistration;
  }

  public void setFacebookPostOnMatchRegistration(boolean facebookPostOnMatchRegistration)
  {
    this.facebookPostOnMatchRegistration = facebookPostOnMatchRegistration;
  }

  /**
   * Indica se deve essere fatto un post sulla bacheca FB
   * nel caso in cui una partita in cui a giocato venga pagellata
   *
   * @hibernate.property
   * column="FACEBOOK_POST_ON_MATCH_RECORDED"
   */
  public boolean isFacebookPostOnMatchRecorded()
  {
    return facebookPostOnMatchRecorded;
  }

  public void setFacebookPostOnMatchRecorded(boolean facebookPostOnMatchRecorded)
  {
    this.facebookPostOnMatchRecorded = facebookPostOnMatchRecorded;
  }

    /**
     * @hibernate.property
     * column="COBRAND_CODE"
     */
    public String getCobrandCode()
    {
        return cobrandCode;
    }

    public void setCobrandCode(String cobrandCode)
    {
        this.cobrandCode = cobrandCode;
    }

    public boolean isFacebookUser()
  {
    return StringUtils.isNotBlank(this.facebookIdUser) && StringUtils.isNotBlank(this.facebookAccessToken);
  }


}
