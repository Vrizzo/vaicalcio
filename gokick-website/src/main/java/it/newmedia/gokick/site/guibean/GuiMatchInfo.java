package it.newmedia.gokick.site.guibean;

import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import java.util.List;

/**
 * Classe che contine le informazioni della partite in oggetto quando devono essere visualizzate all'interno dell'applicazione
 * @author ggeroldi
 */
public class GuiMatchInfo extends AGuiBean
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  private MatchInfo matchInfo;

  private String statisticPeriod;

  private List<UserInfo> playersRequestList;

  private List<UserInfo> playersRegisteredList;

  private boolean ownerUser;

  private UserInfo ownerUserInfo;

  private boolean subscrictionsOpen;

  private boolean newComments;

  private int partecipantsNumberMissing;

  private int subscrictionsMissingDays;

  private int subscrictionsMissingHours;

  private int subscrictionsMissingMinutes;


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
   * @param matchInfo
   */
  public void setMatchInfo(MatchInfo matchInfo)
  {
    this.matchInfo = matchInfo;
  }

  /**
   *
   * @return
   */
  public boolean isNewComments()
  {
    return newComments;
  }

  /**
   *
   * @return
   */
  @Override
  public boolean isValid()
  {
    return this.matchInfo.isValid();
  }

  /**
   *
   * @param newComments
   */
  public void setNewComments(boolean newComments)
  {
    this.newComments = newComments;
  }

  /**
   *
   * @return
   */
  public boolean isOwnerUser()
  {
    return ownerUser;
  }

  /**
   *
   * @param ownerUser
   */
  public void setOwnerUser(boolean ownerUser)
  {
    this.ownerUser = ownerUser;
  }

  /**
   *
   * @return
   */
  public UserInfo getOwnerUserInfo()
  {
    return ownerUserInfo;
  }

  /**
   *
   * @param ownerUserInfo
   */
  public void setOwnerUserInfo(UserInfo ownerUserInfo)
  {
    this.ownerUserInfo = ownerUserInfo;
  }

  /**
   *
   * @return
   */
  public int getPartecipantsNumberMissing()
  {
    return partecipantsNumberMissing;
  }

  /**
   *
   * @param partecipantsNumberMissing
   */
  public void setPartecipantsNumberMissing(int partecipantsNumberMissing)
  {
    this.partecipantsNumberMissing = partecipantsNumberMissing;
  }

  /**
   *
   * @return
   */
  public List<UserInfo> getPlayersRegisteredList()
  {
    return playersRegisteredList;
  }

  /**
   *
   * @param playersRegisteredList
   */
  public void setPlayersRegisteredList(List<UserInfo> playersRegisteredList)
  {
    this.playersRegisteredList = playersRegisteredList;
  }

  /**
   *
   * @return
   */
  public List<UserInfo> getPlayersRequestList()
  {
    return playersRequestList;
  }

  /**
   *
   * @param playersRequestList
   */
  public void setPlayersRequestList(List<UserInfo> playersRequestList)
  {
    this.playersRequestList = playersRequestList;
  }

  /**
   *
   * @return
   */
  public String getStatisticPeriod()
  {
    return statisticPeriod;
  }

  /**
   *
   * @param statisticPeriod
   */
  public void setStatisticPeriod(String statisticPeriod)
  {
    this.statisticPeriod = statisticPeriod;
  }

  /**
   *
   * @return
   */
  public int getSubscrictionsMissingDays()
  {
    return subscrictionsMissingDays;
  }

  /**
   *
   * @param subscrictionsMissingDays
   */
  public void setSubscrictionsMissingDays(int subscrictionsMissingDays)
  {
    this.subscrictionsMissingDays = subscrictionsMissingDays;
  }

  /**
   *
   * @return
   */
  public int getSubscrictionsMissingHours()
  {
    return subscrictionsMissingHours;
  }

  /**
   *
   * @param subscrictionsMissingHours
   */
  public void setSubscrictionsMissingHours(int subscrictionsMissingHours)
  {
    this.subscrictionsMissingHours = subscrictionsMissingHours;
  }

  /**
   *
   * @return
   */
  public int getSubscrictionsMissingMinutes()
  {
    return subscrictionsMissingMinutes;
  }

  /**
   *
   * @param subscrictionsMissingMinutes
   */
  public void setSubscrictionsMissingMinutes(int subscrictionsMissingMinutes)
  {
    this.subscrictionsMissingMinutes = subscrictionsMissingMinutes;
  }

  /**
   *
   * @return
   */
  public boolean isSubscrictionsOpen()
  {
    return subscrictionsOpen;
  }

  /**
   *
   * @param subscrictionsOpen
   */
  public void setSubscrictionsOpen(boolean subscrictionsOpen)
  {
    this.subscrictionsOpen = subscrictionsOpen;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  /**
   *
   */
  public GuiMatchInfo()
  {
    this.statisticPeriod = Constants.STATISTIC_PERIOD__NONE;
  }

  /**
   *
   * @param statisticPeriod
   */
  public GuiMatchInfo(String statisticPeriod)
  {
    this.statisticPeriod = statisticPeriod;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   *
   * @param matchInfo
   */
  public void loadMatchInfo(MatchInfo matchInfo)
  {
    this.matchInfo = matchInfo;
  }

  // </editor-fold>
}
