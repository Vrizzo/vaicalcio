package it.newmedia.gokick.data.hibernate.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Classe che rappresenta l'oggetto SQUAD che fa riferimento alla tabella SQUADS.
 *
 * @hibernate.class
 * table="SQUADS"
 */
public class Squad extends ABean
{
  private User user;
  private String name;
  private String description;
  private Boolean marketEnabled;
  private String webSite;
  private String photoSite;
  private String videoSite;
  private String playingWeekdays;
  private Integer position;
  private Boolean hiddenEnabled;
  private Date created;
  private Set<UserSquad> userSquadList;

  
  /**
   * @hibernate.id
   * column="ID_SQUAD"
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
   * column="ID_USER"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   */
  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  /**
   * @hibernate.property
   * column="NAME"
   */
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @hibernate.property
   * column="DESCRIPTION"
   */
  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  /**
   * @hibernate.property
   * column="MARKET_ENABLED"
   */
  public Boolean getMarketEnabled()
  {
    return marketEnabled;
  }

  public void setMarketEnabled(Boolean marketEnabled)
  {
    this.marketEnabled = marketEnabled;
  }

  /**
   * @hibernate.property
   * column="WEB_SITE"
   */
  public String getWebSite()
  {
    return webSite;
  }

  public void setWebSite(String webSite)
  {
    this.webSite = webSite;
  }

  /**
   * @hibernate.property
   * column="PHOTO_SITE"
   */
  public String getPhotoSite()
  {
    return photoSite;
  }

  public void setPhotoSite(String photoSite)
  {
    this.photoSite = photoSite;
  }

  /**
   * @hibernate.property
   * column="VIDEO_SITE"
   */
  public String getVideoSite()
  {
    return videoSite;
  }

  public void setVideoSite(String videoSite)
  {
    this.videoSite = videoSite;
  }

  /**
   * @hibernate.property
   * column="PLAYING_WEEKDAYS"
   */
  public String getPlayingWeekdays()
  {
    return playingWeekdays;
  }

  public void setPlayingWeekdays(String playingWeekdays)
  {
    this.playingWeekdays = playingWeekdays;
  }

  /**
   * @hibernate.property
   * column="POSITION"
   */
  public Integer getPosition()
  {
    return position;
  }

  public void setPosition(Integer position)
  {
    this.position = position;
  }

  /**
   * @hibernate.property
   * column="HIDDEN_ENABLED"
   */
  public Boolean getHiddenEnabled()
  {
    return hiddenEnabled;
  }

  public void setHiddenEnabled(Boolean hiddenEnabled)
  {
    this.hiddenEnabled = hiddenEnabled;
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
   * @hibernate.set
   * inverse="true"
   * lazy="true"
   * cascade="all-delete-orphan"
   * @hibernate.key
   * column="ID_SQUAD"
   * @hibernate.one-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.UserSquad"
   */
  public Set<UserSquad> getUserSquadList()
  {
    return userSquadList;
  }

  public void setUserSquadList(Set<UserSquad> userSquadList)
  {
    this.userSquadList = userSquadList;
  }

  public Squad()
  {
    this.userSquadList = new HashSet<UserSquad>();
  }



  // <editor-fold defaultstate="collapsed" desc="-- Methods --">
  public static Squad createDefault()
  {
      Squad squadDefault = new Squad();
      squadDefault.setName("");
      squadDefault.setDescription("");
      squadDefault.setPlayingWeekdays("0000000");
      squadDefault.setHiddenEnabled(false);
      squadDefault.setWebSite("");
      squadDefault.setPhotoSite("");
      squadDefault.setVideoSite("");
      squadDefault.setMarketEnabled(true);
      squadDefault.setPosition(0);
      squadDefault.setCreated(new Date());
      return squadDefault;
  }
  // </editor-fold>



}