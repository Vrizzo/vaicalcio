package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiPlayerInfo;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.managers.GuiManager;
import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.Date;
import java.util.List;

/**
 *
 * Classe contenente le azioni per visualizzare la lista degli utenti non amici in stato Request come giocatori del match, essendo organizzatore la possibilità di rimuoverli
 */
public class matchRequestPlayersAction extends AuthenticationBaseAction implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idMatch;

  private int idPlayerToRegister;

  private int idUserOwner;

  private int playersToAdd;

  private List<GuiPlayerInfo> playersRequestList;

  private boolean squadOutEnable;

  private boolean directlyRegistration;

  private boolean matchDone;
  
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public void prepare()
  {
  }

  @Override
  public String execute()
  {
      MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);
      this.idUserOwner = matchInfo.getIdUserOwner();
      this.squadOutEnable=matchInfo.isSquadOutEnable();
      this.setDirectlyRegistration(matchInfo.isDirectlyRegistration());
    List<Player> matchPlayerRegisteredList = MatchManager.getMatchPlayerList(idMatch,false, EnumPlayerStatus.OwnerRegistered, EnumPlayerStatus.UserRegistered, EnumPlayerStatus.UserRequestRegistered, EnumPlayerStatus.Out);
        
    //se la partita è avvenuta l'utente non deve iscrivere Request
    if (matchInfo.getMatchStart().before(new Date()) || matchInfo.isCanceled())
      this.matchDone=true;

    setPlayersToAdd((matchPlayerRegisteredList != null) ? (matchInfo.getMaxPlayers() - matchPlayerRegisteredList.size()) : matchInfo.getMaxPlayers());

    boolean added=false;

    if (this.idPlayerToRegister > 0)
    {
      if (this.getIdUserOwner() == this.getCurrentUser().getId())
      {
        added = MatchManager.registerPlayer(this.idMatch, this.idPlayerToRegister, getCurrentCobrand());
      }
    }

    if(!added && this.idPlayerToRegister > 0)
    {
      String message = getText("messagge.dbError");
      UserContext.getInstance().setLastMessage(message);
    }
    else if(added && this.idPlayerToRegister > 0)
    {
      String message = getText("messagge.playersRegistered");
      message = message.replace(Constants.REPLACEMENT__PLAYERS_REGISTERED_COUNT, Integer.toString(1));
      UserContext.getInstance().setLastMessage(message);
    }


    this.playersRequestList = GuiManager.getGuiPlayersInfoListRequestByMatch(idMatch);
    //logger.debug("matchRequestPlayersAction Trovati: +" + this.playersRequestList.size());

    return SUCCESS;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public int getIdMatch()
  {
    return idMatch;
  }

  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  public List<GuiPlayerInfo> getPlayersRequestList()
  {
    return playersRequestList;
  }

  public void setPlayersRequestList(List<GuiPlayerInfo> playersRequestList)
  {
    this.playersRequestList = playersRequestList;
  }

  public int getIdPlayerToRegister()
  {
    return idPlayerToRegister;
  }

  public void setIdPlayerToRegister(int idPlayerToRegister)
  {
    this.idPlayerToRegister = idPlayerToRegister;
  }

  /**
   * @return the idUserOwner
   */
  public int getIdUserOwner()
  {
    return idUserOwner;
  }

  /**
   * @param idUserOwner the idUserOwner to set
   */
  public void setIdUserOwner(int idUserOwner)
  {
    this.idUserOwner = idUserOwner;
  }

  /**
   * @return the playersTot
   */
  public int getPlayersTot()
  {
    return playersRequestList.size();
  }

    /**
     * @return the playersToAdd
     */
    public int getPlayersToAdd()
    {
        return playersToAdd;
    }

    /**
     * @param playersToAdd the playersToAdd to set
     */
    public void setPlayersToAdd(int playersToAdd)
    {
        this.playersToAdd = playersToAdd;
    }

  /**
   * @return the squadOutEnable
   */
  public boolean isSquadOutEnable()
  {
    return squadOutEnable;
  }

  /**
   * @param squadOutEnable the squadOutEnable to set
   */
  public void setSquadOutEnable(boolean squadOutEnable)
  {
    this.squadOutEnable = squadOutEnable;
  }

  /**
   * @return the directlyRegistration
   */
  public boolean isDirectlyRegistration()
  {
    return directlyRegistration;
  }

  /**
   * @param directlyRegistration the directlyRegistration to set
   */
  public void setDirectlyRegistration(boolean directlyRegistration)
  {
    this.directlyRegistration = directlyRegistration;
  }

  /**
   * @return the matchDone
   */
  public boolean isMatchDone()
  {
    return matchDone;
  }

  
  // </editor-fold>
}
