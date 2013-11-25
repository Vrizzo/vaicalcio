package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.guibean.GuiPlayerInfo;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.managers.GuiManager;
import it.newmedia.gokick.site.managers.MatchInfoManager;
import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.gokick.site.managers.PlayerManager;
import java.util.Date;
import java.util.List;

/**
 *
 * Classe contenente le azioni per visualizzare la lista degli utenti registrati al match, essendo organizzatore la possibilità di rimuoverli
 */
public class matchRegisteredPlayersAction extends AuthenticationBaseAction
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idMatch;
  private int idPlayerToRemove;
  private int idUserOwner;
  private boolean userOwner;
  private int maxPlayers;
  private List<GuiPlayerInfo> playersRegisteredList;
  private int playersToAdd;
  private String userOwnerMobile;
  private String namePlayerToRemove;
  private boolean missing;
  private boolean matchDone;


  
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">

  @Override
  public String execute()
  {
      
      MatchInfo matchInfo=MatchInfoManager.getMatchInfo(idMatch);
      if(matchInfo.getMobileUserOwner()!=null)
        this.userOwnerMobile=matchInfo.getMobileUserOwner().trim();
      this.idUserOwner=matchInfo.getIdUserOwner();
      this.maxPlayers=matchInfo.getMaxPlayers();
      //se la partita è avvenuta l'utente non deve poter modificare nulla, il ctrl evita che palayerStatus.jsp venga caricata
      if (matchInfo.getMatchStart().before(new Date()) || matchInfo.isCanceled())
      this.matchDone=true;

      if ( this.getIdUserOwner() == this.getCurrentUser().getId())
        this.userOwner=true;
      
      if( this.idPlayerToRemove > 0 )
      {         
          if ( this.userOwner )
          {
            if (this.missing) //non cancella, cambia stato al player
            {
              Player playerToUpdate=PlayerManager.getPlayer(idPlayerToRemove);
              playerToUpdate.setEnumPlayerType(EnumPlayerType.Missing);
              PlayerManager.save(playerToUpdate,this.idMatch);
            }
            else
            {
              MatchManager.deletePlayerFromMatch(this.idMatch, this.idPlayerToRemove);
            }
          }
      }
  
   this.playersRegisteredList= GuiManager.getGuiPlayersInfoListRegisteredByMatch(idMatch,false);

   this.playersToAdd=maxPlayers - playersRegisteredList.size();

   for (int i=0; i< playersToAdd;i++)
   {
       playersRegisteredList.add(new GuiPlayerInfo());
   }
  
    return SUCCESS;
  }

  public String openPopUp()
  {
    return Constants.STRUTS_RESULT_NAME__OPEN_POPUP;
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

    public List<GuiPlayerInfo> getPlayerRegisteredList()
    {
        return playersRegisteredList;
    }


    public void setPlayerRegisteredList(List<GuiPlayerInfo> playerRegisteredList)
    {
        this.playersRegisteredList = playerRegisteredList;
    }

  
    public int getIdPlayerToRemove()
    {
        return idPlayerToRemove;
    }

    
    public void setIdPlayerToRemove(int idPlayerToRemove)
    {
        this.idPlayerToRemove = idPlayerToRemove;
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
     * @return the maxPlayers
     */
    public int getMaxPlayers()
    {
        return maxPlayers;
    }

    /**
     * @param maxPlayers the maxPlayers to set
     */
    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    /**
     * @return the userOwner
     */
    public boolean isUserOwner()
    {
        return userOwner;
    }

    /**
     * @param userOwner the userOwner to set
     */
    public void setUserOwner(boolean userOwner)
    {
        this.userOwner = userOwner;
    }

    /**
     * @return the userOwnerMobile
     */
    public String getUserOwnerMobile()
    {
        return userOwnerMobile;
    }

    /**
     * @param userOwnerMobile the userOwnerMobile to set
     */
    public void setUserOwnerMobile(String userOwnerMobile)
    {
        this.userOwnerMobile = userOwnerMobile;
    }
  /**
   * @return the namePlayerToRemove
   */
  public String getNamePlayerToRemove()
  {
    return namePlayerToRemove;
  }

  /**
   * @param namePlayerToRemove the namePlayerToRemove to set
   */
  public void setNamePlayerToRemove(String namePlayerToRemove)
  {
    this.namePlayerToRemove = namePlayerToRemove;
  }

  /**
   * @param missing the missing to set
   */
  public void setMissing(boolean missing)
  {
    this.missing = missing;
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
