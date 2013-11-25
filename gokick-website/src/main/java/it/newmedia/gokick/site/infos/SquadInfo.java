package it.newmedia.gokick.site.infos;

import it.newmedia.gokick.site.managers.SquadManager;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce le informazioni relative alle rose quando queste devono essere visualizzate all'interno dell'applicazione
 */
public class SquadInfo implements Serializable
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static final Logger logger = Logger.getLogger(SquadInfo.class);
  private boolean valid;
  private int id;
  private int idOwner;
  private String ownerName;
  private int ownerRecordedMatches;
  private int ownerRecordedChallenges;
  private String city;
  private boolean market;
  private String name;
  private String description;
  private String webSite;
  private String photoSite;
  private String videoSite;
  private List playersRolesCount;
  private String playingWeekdays;
  private String playersTot;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   *
   */
  public SquadInfo()
  {
    this.valid = false;
  }

  /**
   *
   * @param id
   * @param idOwner
   * @param ownerFirstName
   * @param ownerLastName
   * @param ownerRecordedMatches
   * @param ownerRecordedChallenges
   * @param city
   * @param market
   * @param name
   * @param description
   * @param webSite
   * @param photoSite
   * @param videoSite
   * @param playingWeekdays
   */
  public SquadInfo(int id, int idOwner, String ownerFirstName, String ownerLastName, int ownerRecordedMatches, int ownerRecordedChallenges, String city, boolean market, String name, String description, String webSite, String photoSite, String videoSite, String playingWeekdays)
  {
    this.valid = true;
    this.id = id;
    this.idOwner = idOwner;
    this.ownerName = String.format("%1$s %2$s", ownerFirstName, ownerLastName);
    this.ownerRecordedMatches = ownerRecordedMatches;
    this.ownerRecordedChallenges = ownerRecordedChallenges;
    this.city = city;
    this.market = market;
    this.name = name;
    this.description = description;
    this.webSite = webSite;
    this.photoSite = photoSite;
    this.videoSite = videoSite;
    this.playingWeekdays = playingWeekdays;
    this.playersTot = String.valueOf(SquadManager.getAllConfirmedUserByIdSquad(this.id).size());
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
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
   * @param valid
   */
  public void setValid(boolean valid)
  {
    this.valid = valid;
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
   * @param id
   */
  public void setId(int id)
  {
    this.id = id;
  }

  /**
   *
   * @return
   */
  public String getOwnerName()
  {
    return ownerName;
  }

  /**
   *
   * @param ownerName
   */
  public void setOwnerName(String ownerName)
  {
    this.ownerName = ownerName;
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
  }

  /**
   *
   * @return
   */
  public String getDescription()
  {
    return description;
  }

  /**
   *
   * @param description
   */
  public void setDescription(String description)
  {
    this.description = description;
  }

  /**
   *
   * @return
   */
  public String getWebSite()
  {
    return webSite;
  }

  /**
   *
   * @param webSite
   */
  public void setWebSite(String webSite)
  {
    this.webSite = webSite;
  }

  /**
   *
   * @return
   */
  public String getPhotoSite()
  {
    return photoSite;
  }

  /**
   *
   * @param photoSite
   */
  public void setPhotoSite(String photoSite)
  {
    this.photoSite = photoSite;
  }

  /**
   *
   * @return
   */
  public String getVideoSite()
  {
    return videoSite;
  }

  /**
   *
   * @param videoSite
   */
  public void setVideoSite(String videoSite)
  {
    this.videoSite = videoSite;
  }

  /**
   *
   * @return
   */
  public boolean isMarket()
  {
    return market;
  }

  /**
   *
   * @param market
   */
  public void setMarket(boolean market)
  {
    this.market = market;
  }

  /**
   *
   * @return
   */
  public String getPlayingWeekdays()
  {
    return playingWeekdays;
  }

  /**
   *
   * @param playingWeekdays
   */
  public void setPlayingWeekdays(String playingWeekdays)
  {
    this.playingWeekdays = playingWeekdays;
  }

  /**
   *
   * @return
   */
  public List getPlayersRolesCount()
  {
    return playersRolesCount;
  }

  /**
   *
   * @param playersRolesCount
   */
  public void setPlayersRolesCount(List playersRolesCount)
  {
    this.playersRolesCount = playersRolesCount;
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
  }

  /**
   *
   * @return
   */
  public int getIdOwner()
  {
    return idOwner;
  }

  /**
   *
   * @param idOwner
   */
  public void setIdOwner(int idOwner)
  {
    this.idOwner = idOwner;
  }

  /**
   *
   * @return
   */
  public int getOwnerRecordedMatches()
  {
    return ownerRecordedMatches;
  }

  /**
   *
   * @param ownerRecordedMatches
   */
  public void setOwnerRecordedMatches(int ownerRecordedMatches)
  {
    this.ownerRecordedMatches = ownerRecordedMatches;
  }

  /**
   *
   * @return
   */
  public int getOwnerRecordedChallenges()
  {
    return ownerRecordedChallenges;
  }

  /**
   *
   * @param ownerRecordedChallenges
   */
  public void setOwnerRecordedChallenges(int ownerRecordedChallenges)
  {
    this.ownerRecordedChallenges = ownerRecordedChallenges;
  }

  /**
   *
   * @return
   */
  public String getPlayersTot()
  {
    return playersTot;
  }

  /**
   *
   * @param playersTot
   */
  public void setPlayersTot(String playersTot)
  {
    this.playersTot = playersTot;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >  
  // </editor-fold>
}
