package it.newmedia.gokick.site.guibean;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.providers.TranslationProvider;
import java.util.Date;

/**
 *Classe che contine le informazioni del giocatore della partita in oggetto quando devono essere visualizzate all'interno dell'applicazione
 * @author ggeroldi
 */
public class GuiPlayerInfo extends AGuiBean
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  private UserInfo userInfo;

  private int id;

  private boolean removeEnable;

  private boolean ownerUser;

  private boolean currentUser;

  private boolean reporter;

  private EnumPlayerStatus status;

  private EnumPlayerType type;

  private String role;

  private String mobile;

  private Date requestDate;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --"  >
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
  public UserInfo getUserInfo()
  {
    return userInfo;
  }

  /**
   * @return the removedEnable
   */
  public boolean isRemoveEnable()
  {
    return removeEnable;
  }

  /**
   * @param removedEnable the removedEnable to set
   */
  public void setRemoveEnable(boolean removedEnable)
  {
    this.removeEnable = removedEnable;
  }

  /**
   * @return the ownerUser
   */
  public boolean isOwnerUser()
  {
    return ownerUser;
  }

  /**
   * @param ownerUser the ownerUser to set
   */
  public void setOwnerUser(boolean ownerUser)
  {
    this.ownerUser = ownerUser;
  }

  /**
   * @return the reporter
   */
  public boolean isReporter()
  {
    return reporter;
  }

  /**
   * @param reporter the reporter to set
   */
  public void setReporter(boolean reporter)
  {
    this.reporter = reporter;
  }

  /**
   *
   * @return
   */
  public boolean isCurrentUser()
  {
    return currentUser;
  }

  /**
   * @param currentUser the currentUser to set
   */
  public void setCurrentUser(boolean currentUser)
  {
    this.currentUser = currentUser;
  }

  /**
   *
   * @return
   */
  public EnumPlayerStatus getStatus()
  {
    return status;
  }

  /**
   *
   * @param status
   */
  public void setStatus(EnumPlayerStatus status)
  {
    this.status = status;
  }

  /**
   * @return the role
   */
  public String getRole()
  {
    return role;
  }

  /**
   * @param role the role to set
   */
  public void setRole(String role)
  {
    this.role = role;
  }

  /**
   * @return the mobile
   */
  public String getMobile()
  {
    return mobile;
  }

  /**
   *
   * @return
   */
  public Date getRequestDate()
  {
    return requestDate;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id)
  {
    this.id = id;
  }

  /**
   * @return the type
   */
  public EnumPlayerType getType()
  {
    return type;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  /**
   *
   */
  public GuiPlayerInfo()
  {
    this.role = "";
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   * valorizza l'oggetto GuiPlayerInfo con i paramtri passati
   * @param player
   * @param userInfo
   * @param matchInfo
   */
  public void load(Player player, UserInfo userInfo, MatchInfo matchInfo)
  {
    if (player != null)
    {
      this.setId((int) player.getId());
      this.reporter = player.getReporterEnabled();
      this.status = player.getEnumPlayerStatus();
      if (player.getEnumPlayerType()!=null)
        this.type=player.getEnumPlayerType();
      this.mobile = player.getMobile();
      this.requestDate = player.getRequestDate();
      if (player.getPlayerRole() != null)
      {
          this.setRole(TranslationProvider.getTranslation(player.getPlayerRole().getKeyName(), UserContext.getInstance().getLanguage(), UserContext.getInstance().getCurrentCobrand()).getKeyValue());
      }
    }
    else
    {
      this.status = EnumPlayerStatus.Undefined;
    }

      this.userInfo = userInfo;

      User userCurrent = UserContext.getInstance().getUser();
      if (userInfo.getId() == userCurrent.getId())
      {
        this.setCurrentUser(true);
      }

      if (this.getUserInfo()!=null && matchInfo.getIdUserOwner() == (this.getUserInfo().getId()))
      {
        this.ownerUser = true;
      }

    this.valid = true;
   
}
 
  // </editor-fold>
}
