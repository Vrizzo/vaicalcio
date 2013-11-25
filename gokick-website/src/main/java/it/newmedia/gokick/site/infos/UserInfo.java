package it.newmedia.gokick.site.infos;

import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.beans.VPlayMorePartner;
import it.newmedia.gokick.site.providers.PlayMorePartnerProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * Classe che gestisce le informazioni relative agli utenti quando queste devono essere visualizzate all'interno dell'applicazione
 */
public class UserInfo implements Serializable
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static final Logger logger = Logger.getLogger(UserInfo.class);

  private Boolean valid;

  private int id;

  private String name;

  private String surname;

  private String completeSurname;

  private String email;
  
  private int recordedMatches;

  private int recordedChallenges;

  private String city;

  private String country;

  private String natCountry;

  private int idCountry;

  private int idProvince;
  
  private int idCity;
  
  private int idNatCountry;

  private String province;

  private String birthdayCountry;

  private String birthdayProvince;

  private String birthdayCity;

  private String countryFlagName;

  private String playerRole;

  private int idPlayerRole;

  private String playerRoleKey;

  private String age;

  private boolean marketEnabled;

  private boolean squadMarketEnabled;

  private Date birthday;

  private String playerFoot;

  private String playerFootKeyName;

  private String footballTeam;

  private String playerShirtNumber;

  private String playerHeight;

  private String playerWeight;

  private String playerMainFeature;

  private String infoFavouritePlayer;

  private String infoHobby;

  private String infoDream;

  private String infoAnnounce;

  private int playedMatches;

  private int playedChallenges;

  private Date created;

  private String playerTitle;

  private String physicalConditionKey;

  private String idPhysicalCondition;

  private boolean inSquad;

  private boolean anonymousEnabled;

  private boolean alertOnMatchRegistrationOpen;

  private EnumUserStatus status;

  private String pictureCardFilename;

  private boolean pictureCardVisibleForOthers;

  private int postCount;

  private int invitationsAvailable;

  private String facebookIdUser;

  private String facebookAccessToken;

  private boolean facebookPostOnMatchCreation;

  private boolean facebookPostOnMatchRegistration;

  private boolean facebookPostOnMatchRecorded;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   *
   */
  public UserInfo()
  {
    this.valid = false;
  }

  /**
   *
   * @return
   */
  public Boolean isValid()
  {
    if (valid == null)
    {
      //TODO: aggiungere i controlli se servono
      valid = this.id > 0;
    }
    return valid;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >

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
   * @param id
   */
  public void setId(int id)
  {
    this.id = id;
    valid = null;
  }

  /**
   *
   * @return
   */
  public String getProvince()
  {
    return province;
  }

  /**
   *
   * @param province
   */
  public void setProvince(String province)
  {
    this.province = province;
    valid = null;
  }

  /**
   *
   * @return
   */
  public String getName()
  {
    return name;
  }

  /**
   *
   * @param name
   */
  public void setName(String name)
  {
    this.name = name;
    valid = null;
  }

  /**
   *
   * @return
   */
  public String getEmail()
  {
    return email;
  }

  /**
   *
   * @param email
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   *
   * @return
   */
  public int getRecordedMatches()
  {
    return recordedMatches;
  }

  /**
   *
   * @param recordedMatches
   */
  public void setRecordedMatches(int recordedMatches)
  {
    this.recordedMatches = recordedMatches;
    valid = null;
  }

  /**
   *
   * @return
   */
  public int getRecordedChallenges()
  {
    return recordedChallenges;
  }

  /**
   *
   * @param recordedChallenges
   */
  public void setRecordedChallenges(int recordedChallenges)
  {
    this.recordedChallenges = recordedChallenges;
    valid = null;
  }

  /**
   *
   * @return
   */
  public String getCity()
  {
    return city;
  }

  /**
   *
   * @param city
   */
  public void setCity(String city)
  {
    this.city = city;
    valid = null;
  }

  /**
   *
   * @return
   */
  public String getCountryFlagName()
  {
    return countryFlagName;
  }

  /**
   *
   * @param countryFlagName
   */
  public void setCountryFlagName(String countryFlagName)
  {
    this.countryFlagName = countryFlagName;
    valid = null;
  }

  /**
   *
   * @return
   */
  public String getPlayerRole()
  {
    return playerRole;
  }

  /**
   *
   * @param playerRole
   */
  public void setPlayerRole(String playerRole)
  {
    this.playerRole = playerRole;
    valid = null;
  }

  /**
   *
   * @return
   */
  public String getAge()
  {
    return age;
  }

  /**
   *
   * @param age
   */
  public void setAge(String age)
  {
    this.age = age;
    valid = null;
  }

  /**
   *
   * @return
   */
  public boolean isMarketEnabled()
  {
    return marketEnabled;
  }

  /**
   *
   * @param marketEnabled
   */
  public void setMarketEnabled(boolean marketEnabled)
  {
    this.marketEnabled = marketEnabled;
    valid = null;
  }

  /**
   *
   * @return
   */
  public boolean isSquadMarketEnabled()
  {
    return squadMarketEnabled;
  }

  /**
   *
   * @param squadMarketEnabled
   */
  public void setSquadMarketEnabled(boolean squadMarketEnabled)
  {
    this.squadMarketEnabled = squadMarketEnabled;
    valid = null;
  }

  /**
   *
   * @return
   */
  public String getSurname()
  {
    return surname;
  }

  /**
   *
   * @param surname
   */
  public void setSurname(String surname)
  {
    this.surname = surname;
    valid = null;
  }

  /**
   *
   * @return
   */
  public Date getBirthday()
  {
    return birthday;
  }

  /**
   *
   * @param birthday
   */
  public void setBirthday(Date birthday)
  {
    this.birthday = birthday;
    valid = null;
  }

  /**
   *
   * @return
   */
  public String getCountry()
  {
    return country;
  }

  /**
   *
   * @param country
   */
  public void setCountry(String country)
  {
    this.country = country;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getBirthdayCountry()
  {
    return birthdayCountry;
  }

  /**
   *
   * @param birthdayCountry
   */
  public void setBirthdayCountry(String birthdayCountry)
  {
    this.birthdayCountry = birthdayCountry;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getBirthdayProvince()
  {
    return birthdayProvince;
  }

  /**
   *
   * @param birthdayProvince
   */
  public void setBirthdayProvince(String birthdayProvince)
  {
    this.birthdayProvince = birthdayProvince;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getBirthdayCity()
  {
    return birthdayCity;
  }

  /**
   *
   * @param birthdayCity
   */
  public void setBirthdayCity(String birthdayCity)
  {
    this.birthdayCity = birthdayCity;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getPlayerFoot()
  {
    return playerFoot;
  }

  /**
   *
   * @param playerFoot
   */
  public void setPlayerFoot(String playerFoot)
  {
    this.playerFoot = playerFoot;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getFootballTeam()
  {
    return footballTeam;
  }

  /**
   *
   * @param footballTeam
   */
  public void setFootballTeam(String footballTeam)
  {
    this.footballTeam = footballTeam;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getPlayerShirtNumber()
  {
    return playerShirtNumber;
  }

  /**
   *
   * @param playerShirtNumber
   */
  public void setPlayerShirtNumber(String playerShirtNumber)
  {
    this.playerShirtNumber = playerShirtNumber;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getPlayerHeight()
  {
    return playerHeight;
  }

  /**
   *
   * @param playerHeight
   */
  public void setPlayerHeight(String playerHeight)
  {
    this.playerHeight = playerHeight;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getPlayerWeight()
  {
    return playerWeight;
  }

  /**
   *
   * @param playerWeight
   */
  public void setPlayerWeight(String playerWeight)
  {
    this.playerWeight = playerWeight;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getPlayerMainFeature()
  {
    return playerMainFeature;
  }

  /**
   *
   * @param playerMainFeature
   */
  public void setPlayerMainFeature(String playerMainFeature)
  {
    this.playerMainFeature = playerMainFeature;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getInfoFavouritePlayer()
  {
    return infoFavouritePlayer;
  }

  /**
   *
   * @param infoFavouritePlayer
   */
  public void setInfoFavouritePlayer(String infoFavouritePlayer)
  {
    this.infoFavouritePlayer = infoFavouritePlayer;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getInfoHobby()
  {
    return infoHobby;
  }

  /**
   *
   * @param infoHobby
   */
  public void setInfoHobby(String infoHobby)
  {
    this.infoHobby = infoHobby;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getInfoDream()
  {
    return infoDream;
  }

  /**
   *
   * @param infoDream
   */
  public void setInfoDream(String infoDream)
  {
    this.infoDream = infoDream;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public String getInfoAnnounce()
  {
    return infoAnnounce;
  }

  /**
   *
   * @param infoAnnounce
   */
  public void setInfoAnnounce(String infoAnnounce)
  {
    this.infoAnnounce = infoAnnounce;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public int getPlayedMatches()
  {
    return playedMatches;
  }

  /**
   *
   * @param playedMatches
   */
  public void setPlayedMatches(int playedMatches)
  {
    this.playedMatches = playedMatches;
    this.valid = null;//why?
  }

  /**
   *
   * @return
   */
  public int getPlayedChallenges()
  {
    return playedChallenges;
  }

  /**
   *
   * @param playedChallenges
   */
  public void setPlayedChallenges(int playedChallenges)
  {
    this.playedChallenges = playedChallenges;
    this.valid = null;//why?
  }

  /**
   *
   * @return
   */
  public String getPlayerTitle()
  {
    return playerTitle;
  }

  /**
   *
   * @param playerTitle
   */
  public void setPlayerTitle(String playerTitle)
  {
    this.playerTitle = playerTitle;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public Date getCreated()
  {
    return created;
  }

  /**
   *
   * @param created
   */
  public void setCreated(Date created)
  {
    this.created = created;
    this.valid = null;
  }

  /**
   *
   * @return
   */
  public boolean isWithBirthdayInfo()
  {
    return !this.getBirthdayCity().equals("");
  }

  /**
   *
   * @return
   */
  public int getIdCountry()
  {
    return idCountry;
  }

  /**
   *
   * @param idCountry
   */
  public void setIdCountry(int idCountry)
  {
    this.idCountry = idCountry;
  }
  
  /**
   *
   * @return
   */
  public int getIdProvince()
  {
    return idProvince;
  }
  
  /**
   *
   * @param idProvince
   */
  public void setIdProvince(int idProvince)
  {
    this.idProvince = idProvince;
  }
  
  /**
   *
   * @return
   */
  public int getIdCity()
  {
    return idCity;
  }
  
  /**
   *
   * @param idCity
   */
  public void setIdCity(int idCity)
  {
    this.idCity = idCity;
  }

  /**
   *
   * @return
   */
  public String getPhysicalConditionKey()
  {
    return physicalConditionKey;
  }

  /**
   *
   * @param physicalConditionKey
   */
  public void setPhysicalConditionKey(String physicalConditionKey)
  {
    this.physicalConditionKey = physicalConditionKey;
  }

  /**
   *
   * @return
   */
  public boolean isAnonymousEnabled()
  {
    return anonymousEnabled;
  }

  /**
   *
   * @param anonymousEnabled
   */
  public void setAnonymousEnabled(boolean anonymousEnabled)
  {
    this.anonymousEnabled = anonymousEnabled;
  }

  /**
   *
   * @return
   */
  public EnumUserStatus getStatus()
  {
    return status;
  }

  /**
   *
   * @param status
   */
  public void setStatus(EnumUserStatus status)
  {
    this.status = status;
  }

  /**
   *
   * @return
   */
  public String getPictureCardFilename()
  {
    return pictureCardFilename;
  }

  /**
   *
   * @param pictureCardFilename
   */
  public void setPictureCardFilename(String pictureCardFilename)
  {
    this.pictureCardFilename = pictureCardFilename;
  }

  /**
   *
   * @return
   */
  public boolean isWithPictureCardFilename()
  {
    return this.pictureCardFilename != null && !this.pictureCardFilename.isEmpty();
  }

  /**
   *
   * @return
   */
  public boolean isPictureCardVisibleForOthers()
  {
    return pictureCardVisibleForOthers;
  }

  /**
   *
   * @return
   */
  public int getPostCount()
  {
    return postCount;
  }

  /**
   *
   * @param postCount
   */
  public void setPostCount(int postCount)
  {
    this.postCount = postCount;
  }

  /**
   * Indica il numero di inviti effettivamente a disposizione dell'utente
   * (Valore inviti utente - inviti usati da altri utenti che si sono registrati)
   * @return il numero di inviti effettivamente a disposizione dell'utente
   */
  public int getInvitationsAvailable()
  {
    return invitationsAvailable;
  }

  /**
   *
   * @param invitationsAvailable
   */
  public void setInvitationsAvailable(int invitationsAvailable)
  {
    this.invitationsAvailable = invitationsAvailable;
  }

  /**
   *
   * @return
   */
  public String getFirstLastName()
  {
    return this.getName() + " " + this.getSurname();
  }

  /**
   * @return the idPhysicalCondition
   */
  public String getIdPhysicalCondition()
  {
    return idPhysicalCondition;
  }

  /**
   * @param idPhysicalCondition the idPhysicalCondition to set
   */
  public void setIdPhysicalCondition(String idPhysicalCondition)
  {
    this.idPhysicalCondition = idPhysicalCondition;
  }

  /**
   * @return the idPlayerRole
   */
  public int getIdPlayerRole()
  {
    return idPlayerRole;
  }

  /**
   * @param idPlayerRole the idPlayerRole to set
   */
  public void setIdPlayerRole(int idPlayerRole)
  {
    this.idPlayerRole = idPlayerRole;
  }

   /**
   * @return the playerRoleKey
   */
  public String getPlayerRoleKey()
  {
    return playerRoleKey;
  }

  /**
   * @param playerRoleKey the playerRoleKey to set
   */
  public void setPlayerRoleKey(String playerRoleKey)
  {
    this.playerRoleKey = playerRoleKey;
  }

  /**
   * @return the idNatCountry
   */
  public int getIdNatCountry()
  {
    return idNatCountry;
  }

  /**
   * @param idNatCountry the idNatCountry to set
   */
  public void setIdNatCountry(int idNatCountry)
  {
    this.idNatCountry = idNatCountry;
  }

  /**
   * @return the natCountry
   */
  public String getNatCountry()
  {
    return natCountry;
  }

  /**
   * @param natCountry the natCountry to set
   */
  public void setNatCountry(String natCountry)
  {
    this.natCountry = natCountry;
  }

  /**
   * @return the playerFootKeyName
   */
  public String getPlayerFootKeyName()
  {
    return playerFootKeyName;
  }

  /**
   * @param playerFootKeyName the playerFootKeyName to set
   */
  public void setPlayerFootKeyName(String playerFootKeyName)
  {
    this.playerFootKeyName = playerFootKeyName;
  }

  /**
   * @return the alertOnMatchRegistrationOpen
   */
  public boolean isAlertOnMatchRegistrationOpen()
  {
    return alertOnMatchRegistrationOpen;
  }

  /**
   * @param alertOnMatchRegistrationOpen the alertOnMatchRegistrationOpen to set
   */
  public void setAlertOnMatchRegistrationOpen(boolean alertOnMatchRegistrationOpen)
  {
    this.alertOnMatchRegistrationOpen = alertOnMatchRegistrationOpen;
  }

  public String getFacebookIdUser()
  {
    return facebookIdUser;
  }

  public void setFacebookIdUser(String facebookIdUser)
  {
    this.facebookIdUser = facebookIdUser;
  }

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
   */
  public boolean isFacebookPostOnMatchRecorded()
  {
    return facebookPostOnMatchRecorded;
  }

  public void setFacebookPostOnMatchRecorded(boolean facebookPostOnMatchRecorded)
  {
    this.facebookPostOnMatchRecorded = facebookPostOnMatchRecorded;
  }

  public boolean isFacebookUser()
  {
    return StringUtils.isNotBlank(this.facebookIdUser) && StringUtils.isNotBlank(this.facebookAccessToken);
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   * valorizza oggetto userInfo passandogli un player
   * @param player
   */
  public void load(Player player)
  {
    if (player == null)
      return;
    this.name = player.getOutFirstName();
    this.surname = player.getOutLastName();
    this.valid = true;
  }

  /**
   * @param birthDate
   * @return una Stringa contenente l'età in anni dalla data indicata
   */
  private String calculateAge(Date birthDate)
  {
    if (birthDate == null)
    {
      return "";
    }
    else
    {
      GregorianCalendar userBirthDate = new GregorianCalendar();
      userBirthDate.setTime(birthDate);
      GregorianCalendar currentDate = new GregorianCalendar();

      int tmpAge = currentDate.get(Calendar.YEAR) - userBirthDate.get(Calendar.YEAR);
      if (userBirthDate.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH))
      {
        tmpAge--;
      }
      else if (userBirthDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH))
      {
        if (userBirthDate.get(Calendar.DAY_OF_MONTH) > currentDate.get(Calendar.DAY_OF_MONTH))
        {
          tmpAge--;
        }
      }
      return String.valueOf(tmpAge);
    }
  }

  /**
   * valorizza le info relative ala figurina in base ai permesi esistenti
   * @param pictureCard
   */
  public void loadPictureCard(PictureCard pictureCard)
  {
    if (pictureCard == null)
    {
      this.pictureCardFilename = "";
      this.pictureCardVisibleForOthers = false;
    }
    else
    {
      this.pictureCardFilename = pictureCard.getFilename();
      this.pictureCardVisibleForOthers =
              ((this.status == EnumUserStatus.Enabled || this.status == EnumUserStatus.Pending)&&
              pictureCard.getEnumPictureCardStatus().getValue().equals(EnumPictureCardStatus.Current.getValue()) &&
              !this.anonymousEnabled);
    }
  }

  /**
   * @return the completeSurname
   */
  public String getCompleteSurname()
  {
    return completeSurname;
  }

  /**
   * @param completeSurname the completeSurname to set
   */
  public void setCompleteSurname(String completeSurname)
  {
    this.completeSurname = completeSurname;
  }

  public VPlayMorePartner getPlayMorePartner()
  {
    return PlayMorePartnerProvider.get(this.id);
  }
  // </editor-fold>
}
