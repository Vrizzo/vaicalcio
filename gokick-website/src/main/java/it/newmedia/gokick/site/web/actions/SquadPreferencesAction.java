package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.site.web.actions.user.UserEnableAction;
import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.SquadManager;
import java.util.Date;
import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente le azioni fatte dall'utente per la gestione dei dati della sua rosa.
 * (nome squadra,giorni di gioco,anonimato squadra.. etc. etc.)
 */
public class SquadPreferencesAction extends AuthenticationBaseAction
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserEnableAction.class);

  private String name;

  private String description;

  private boolean playingWeekdaysMon;

  private boolean playingWeekdaysTue;

  private boolean playingWeekdaysWed;

  private boolean playingWeekdaysThu;

  private boolean playingWeekdaysFri;

  private boolean playingWeekdaysSat;

  private boolean playingWeekdaysSun;

  private boolean hiddenEnabled;

  private String marketEnabled;

  private String webSite;

  private String photoSite;

  private String videoSite;

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public String input()
  {
    Squad currentSquad = UserContext.getInstance().getFirstSquad();
    if (currentSquad != null)
    {
      this.name = currentSquad.getName();
      this.description = currentSquad.getDescription();
      this.hiddenEnabled = currentSquad.getHiddenEnabled();
      this.playingWeekdaysMon = currentSquad.getPlayingWeekdays().charAt(0) == '1';
      this.playingWeekdaysTue = currentSquad.getPlayingWeekdays().charAt(1) == '1';
      this.playingWeekdaysWed = currentSquad.getPlayingWeekdays().charAt(2) == '1';
      this.playingWeekdaysThu = currentSquad.getPlayingWeekdays().charAt(3) == '1';
      this.playingWeekdaysFri = currentSquad.getPlayingWeekdays().charAt(4) == '1';
      this.playingWeekdaysSat = currentSquad.getPlayingWeekdays().charAt(5) == '1';
      this.playingWeekdaysSun = currentSquad.getPlayingWeekdays().charAt(6) == '1';
      if (currentSquad.getMarketEnabled())
      {
        this.marketEnabled = "1";
      }
      else
      {
        this.marketEnabled = "0";
      }
      this.webSite = currentSquad.getWebSite();
      this.photoSite = currentSquad.getPhotoSite();
      this.videoSite = currentSquad.getVideoSite();
    }

    return INPUT;
  }

  @Override
  public void validate()
  {
    //Validazione descrizione
    //per ora nessun dato della rosa è obbligatorio
    /*
    if (StringUtils.isEmpty(this.description))
    {
    addFieldError("description", getText("error.description.required"));
    }
     */
    //Validazione giorni
    /*
    if (!this.playingWeekdaysMon &&
    !this.playingWeekdaysTue &&
    !this.playingWeekdaysWed &&
    !this.playingWeekdaysThu &&
    !this.playingWeekdaysFri &&
    !this.playingWeekdaysSat &&
    !this.playingWeekdaysSun)
    {
    addFieldError("playingWeekdays", getText("error.playingWeekdays.required"));
    }
     */
  }

  public String save()
  {
    Squad currentSquad = UserContext.getInstance().getFirstSquad();

    if (currentSquad == null)
    {
      currentSquad = new Squad();
    }
    currentSquad.setUser(UserContext.getInstance().getUser());
    currentSquad.setName(this.name);
    currentSquad.setDescription(this.description);
    String playingWeekdays = BooleanUtils.toIntegerObject(this.playingWeekdaysMon).toString();
    playingWeekdays += BooleanUtils.toIntegerObject(this.playingWeekdaysTue).toString();
    playingWeekdays += BooleanUtils.toIntegerObject(this.playingWeekdaysWed).toString();
    playingWeekdays += BooleanUtils.toIntegerObject(this.playingWeekdaysThu).toString();
    playingWeekdays += BooleanUtils.toIntegerObject(this.playingWeekdaysFri).toString();
    playingWeekdays += BooleanUtils.toIntegerObject(this.playingWeekdaysSat).toString();
    playingWeekdays += BooleanUtils.toIntegerObject(this.playingWeekdaysSun).toString();
    currentSquad.setPlayingWeekdays(playingWeekdays);
    currentSquad.setHiddenEnabled(this.hiddenEnabled);
    currentSquad.setWebSite(this.webSite);
    currentSquad.setPhotoSite(this.photoSite);
    currentSquad.setVideoSite(this.videoSite);
    if (this.getMarketEnabled() == null || this.getMarketEnabled().equalsIgnoreCase("0"))
    {
      currentSquad.setMarketEnabled(false);
    }
    else
    {
      currentSquad.setMarketEnabled(true);
    }
    currentSquad.setPosition(0);
    currentSquad.setCreated(new Date());

    boolean success = SquadManager.save(currentSquad);
    if (!success)
    {
      return ERROR;
    }

    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public boolean isPlayingWeekdaysMon()
  {
    return playingWeekdaysMon;
  }

  public void setPlayingWeekdaysMon(boolean playingWeekdaysMon)
  {
    this.playingWeekdaysMon = playingWeekdaysMon;
  }

  public boolean isPlayingWeekdaysTue()
  {
    return playingWeekdaysTue;
  }

  public void setPlayingWeekdaysTue(boolean playingWeekdaysTue)
  {
    this.playingWeekdaysTue = playingWeekdaysTue;
  }

  public boolean isPlayingWeekdaysWed()
  {
    return playingWeekdaysWed;
  }

  public void setPlayingWeekdaysWed(boolean playingWeekdaysWed)
  {
    this.playingWeekdaysWed = playingWeekdaysWed;
  }

  public boolean isPlayingWeekdaysThu()
  {
    return playingWeekdaysThu;
  }

  public void setPlayingWeekdaysThu(boolean playingWeekdaysThu)
  {
    this.playingWeekdaysThu = playingWeekdaysThu;
  }

  public boolean isPlayingWeekdaysFri()
  {
    return playingWeekdaysFri;
  }

  public void setPlayingWeekdaysFri(boolean playingWeekdaysFri)
  {
    this.playingWeekdaysFri = playingWeekdaysFri;
  }

  public boolean isPlayingWeekdaysSat()
  {
    return playingWeekdaysSat;
  }

  public void setPlayingWeekdaysSat(boolean playingWeekdaysSat)
  {
    this.playingWeekdaysSat = playingWeekdaysSat;
  }

  public boolean isPlayingWeekdaysSun()
  {
    return playingWeekdaysSun;
  }

  public void setPlayingWeekdaysSun(boolean playingWeekdaysSun)
  {
    this.playingWeekdaysSun = playingWeekdaysSun;
  }

  public String isMarketEnabled()
  {
    return getMarketEnabled();
  }

  public void setMarketEnabled(String marketEnabled)
  {
    this.marketEnabled = marketEnabled;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public boolean isHiddenEnabled()
  {
    return hiddenEnabled;
  }

  public void setHiddenEnabled(boolean hiddenEnabled)
  {
    this.hiddenEnabled = hiddenEnabled;
  }

  public String getMarketEnabled()
  {
    return marketEnabled;
  }

  public String getWebSite()
  {
    return webSite;
  }

  public void setWebSite(String webSite)
  {
    this.webSite = webSite;
  }

  public String getPhotoSite()
  {
    return photoSite;
  }

  public void setPhotoSite(String photoSite)
  {
    this.photoSite = photoSite;
  }

  public String getVideoSite()
  {
    return videoSite;
  }

  public void setVideoSite(String videoSite)
  {
    this.videoSite = videoSite;
  }

  // </editor-fold>
}
