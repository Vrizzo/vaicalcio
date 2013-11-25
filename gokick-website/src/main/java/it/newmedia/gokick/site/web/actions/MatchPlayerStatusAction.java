package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.enums.EnumUserSquadStatus;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.data.hibernate.beans.UserSquad;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.gokick.site.managers.PlayerManager;
import it.newmedia.gokick.site.managers.SquadManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.site.managers.SecurityManager;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente le azioni per visualizzare e gestire lo stato d'iscrizione dell'utente corrente alla partita visualizzata
 */
public class MatchPlayerStatusAction extends AuthenticationBaseAction implements Preparable {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idMatch;
  private int idPlayerToRegister;
  private int idUserToRegister;
  private int userToRegisterRole;
  private String userToRegisterMobile;
  private int idCurrentPlayer;
  private boolean registered;
  private boolean squadOutUser;
  private boolean out;
  private MatchInfo matchInfo;
  private String blockType;
  private boolean userRequest;
  private boolean removeEnable;
  private boolean userOwner;
  private int playersToAdd;
  private int squadOutRegisteredCount;
  private String email;
  private Date requestDate;
  private boolean deleteMobileVisibile;
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

    User currentUser = this.getCurrentUser();
    setMatchInfo(InfoProvider.getMatchInfo(idMatch));

    //se la partita è avvenuta l'utente non deve poter modificare nulla, il ctrl evita che palayerStatus.jsp venga caricata
    if (this.matchInfo.getMatchStart().before(new Date()) || this.matchInfo.isCanceled())
      this.matchDone=true;
   
    this.email=currentUser.getEmail();

    List<Player> playersRegisteredList = MatchManager.getMatchPlayerList(idMatch,false, EnumPlayerStatus.OwnerRegistered,
            EnumPlayerStatus.UserRegistered,
            EnumPlayerStatus.UserRequestRegistered,
            EnumPlayerStatus.Out);
    int playersRegistered = playersRegisteredList.size();

    this.setPlayersToAdd(this.matchInfo.getMaxPlayers() - playersRegistered);

    // <editor-fold defaultstate="collapsed" desc="-- Recupera stato player corrente e numero di SquadUser Registrati --"  >
    Squad ownerSquad = SquadManager.getUserFirstSquad(matchInfo.getIdUserOwner());

    int userSquadRegisteredCount = 0;
    this.squadOutUser = true;


    for (UserSquad userSquad : ownerSquad.getUserSquadList())
    {
      if (userSquad.getUser().getId().equals(currentUser.getId()) &&  userSquad.getEnumUserSquadStatus()==EnumUserSquadStatus.Confirmed)
      {
        this.squadOutUser = false;
      }
      for (Player player : playersRegisteredList)
      {
        if (player.getUser() != null && (userSquad.getUser().getId() == player.getUser().getId()))
        {
          userSquadRegisteredCount++;
        }
      }
    }

    this.squadOutRegisteredCount = playersRegistered - userSquadRegisteredCount;

    Player player = MatchManager.getPlayer(this.idMatch, currentUser.getId());
    if (player == null)
    {
      this.setRegistered(false);
      this.userToRegisterRole = currentUser.getPlayerRole() != null ? currentUser.getPlayerRole().getId() : 0;
      this.userToRegisterMobile = currentUser.getMobile();
    }
    else
    {
      if (player.getEnumPlayerStatus() == EnumPlayerStatus.OwnerRegistered ||
              player.getEnumPlayerStatus() == EnumPlayerStatus.UserRegistered ||
              player.getEnumPlayerStatus() == EnumPlayerStatus.UserRequestRegistered)
      {
        this.setRegistered(true);
      }
      else if (player.getEnumPlayerStatus() == EnumPlayerStatus.UserRequest)
      {
        this.setUserRequest(true);
        this.requestDate=player.getRequestDate();
      }
      else if (player.getEnumPlayerStatus() == EnumPlayerStatus.Out)
      {
        this.setOut(true);
      }
      else
      {
        this.setRegistered(false);
      }

      this.idCurrentPlayer = player.getId();
      this.userToRegisterRole = player.getPlayerRole() != null ? player.getPlayerRole().getId() : 0;
      this.userToRegisterMobile = player.getMobile();
    }

    if (currentUser.getId() == this.matchInfo.getIdUserOwner())
    {
      this.setUserOwner(true);
      this.userToRegisterMobile = matchInfo.getMobileUserOwner();
    }
    // </editor-fold>

    boolean playerStatusVisible = SecurityManager.isPlayerStatusVisible(currentUser, this.getMatchInfo(),player);

    // <editor-fold defaultstate="collapsed" desc="-- choose BLOCKTYPE --"  >
    if (playerStatusVisible)
    {
      if (this.isRegistered() && !player.getEnumPlayerType().equals(EnumPlayerType.Missing))
      {
         this.setBlockType("a");  //Sei iscritto
      }
      else   //non registrato
      {
        if (!this.matchInfo.isSquadOutEnable() || this.isUserOwner())  //match privato
        {
          if (!this.isSquadOutUser())   //potresti essere in request (ex partita pubblica con richieste)ma non amico
            this.setBlockType("b");//Gioca
          if (this.isUserRequest()) //richiesta già inoltrata
            this.setBlockType("d");   //in attesa
        }
        else  //match pubblico
        {
          if (!this.isSquadOutUser() && !this.userRequest)  // amico o exNonAmico che non era in attesa di valutazione
          {
            this.setBlockType("b");
          }  //Gioca
          else                        //non amico
          {
            if (this.matchInfo.isDirectlyRegistration()) //iscrizioni dirette
            {
              if (player!=null && player.getEnumPlayerType().equals(EnumPlayerType.Missing))
              {
                this.setBlockType("b");  //Gioca //caso player in Missing
              }
              if (this.matchInfo.getMaxSquadOutPlayers() > this.squadOutRegisteredCount)
              {
                this.setBlockType("b");  //Gioca
              }
              else
              {
                this.setBlockType("e");  //RAggiunto num max non Amici iscritti 
              }
            }
            else   //permessi per non amici
            {
              if (this.isUserRequest()) //richiesta già inoltrata
              {
                if(this.squadOutUser)
                  this.setBlockType("d");   //in attesa
                else
                  this.setBlockType("b");   //gioca caso UserRequest ora amico
              }
              else
              {
                this.setBlockType("c");   // richiedi
              }
              if (player!=null && player.getEnumPlayerType()==EnumPlayerType.Missing)
                this.setBlockType("c");   // richiedi
            }
          }
        }
      }
    }
    // </editor-fold>

    this.removeEnable = SecurityManager.isPlayerStatusRemovable(currentUser, this.matchInfo);

    this.deleteMobileVisibile=true;
    if ( StringUtils.isBlank(this.userToRegisterMobile))
      this.deleteMobileVisibile=false;

    return SUCCESS;
  }

  public String playMatch()
  {
    String message ="";
    if (this.getIdUserToRegister() > 0 && SecurityManager.isPlayerStatusVisible(this.getCurrentUser(), this.idMatch) && !SecurityManager.isMatchFull(idMatch))
    {
      boolean added = MatchManager.addPlayer(this.idMatch,
              this.idUserToRegister,
              EnumPlayerStatus.UserRegistered,
              this.userToRegisterRole,
              this.userToRegisterMobile);
      if(added)
      {
        String role = PlayerManager.getPlayerRole(userToRegisterRole);
        message = getText("messaggio.registeredToMatchAsRole");
        message = message.replace(Constants.REPLACEMENT__PLAYER_ROLE, role != null ? this.getText(role) : "undefined");
      }
      else
      {
        message = getText("messagge.dbError");
      }
      UserContext.getInstance().setLastMessage(message);
      


    }
    return this.execute();
  }
  
  public String requestToplayMatch()
  {

    if (this.getIdUserToRegister() > 0 &&
            SecurityManager.isPlayerStatusVisible(this.getCurrentUser(), this.idMatch))
    {
      MatchManager.addPlayer(this.idMatch,
              this.idUserToRegister,
              EnumPlayerStatus.UserRequest,
              this.userToRegisterRole,
              this.userToRegisterMobile);

      UserContext.getInstance().setLastMessage(getText("message.userRequestToPlay"));
    }

    return this.execute();
  }

  public String updateMatchPlayer()
  {
    User currentUser = this.getCurrentUser();
    Player player = MatchManager.getPlayer(this.idMatch, currentUser.getId());
    matchInfo = InfoProvider.getMatchInfo(idMatch);
    if (SecurityManager.isPlayerStatusVisible(this.getCurrentUser(), matchInfo, player))
    {
      boolean update = MatchManager.updateMatchPlayer(this.idMatch, this.getCurrentUser().getId(),
              this.userToRegisterRole, this.userToRegisterMobile);
      if (!update)
      {
        logger.error("Error Updating Player in MatchPlayerStatusAction");
      }
      

      String message = getText("message.registrationdataModified");
      UserContext.getInstance().setLastMessage(message);
      
    }
    return this.execute();
  }

  public String removeCurrentPlayer()
  {
    Player player=PlayerManager.getPlayer(this.idCurrentPlayer);
    if (SecurityManager.isPlayerStatusRemovable(this.getCurrentUser(), this.idMatch, player ))
    {
      if (SecurityManager.isPlayerValidAndUserRequestAndSelf(this.idCurrentPlayer))
      {
        UserContext.getInstance().setLastMessage(getText("messagge.UserRequestSelfCheckOut"));
      }
      else
      {
        UserContext.getInstance().setLastMessage(getText("messagge.propietariocancella utente"));
      }

      boolean delete = MatchManager.deletePlayerFromMatch(this.idMatch, this.idCurrentPlayer);

      if (!delete)
      {
        UserContext.getInstance().removeLastMessage();
        logger.error("Error Deleting Player in MatchPlayerStatusAction");
      }
      else
      {
        String message="";
        if (player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequest))
        {
          message = getText("messagge.userRequestSelfCheckOut");
        }
        else
        {
          message = getText("messagge.selfCheckOut");
        }
        UserContext.getInstance().setLastMessage(message);
      }
      }
    return this.execute();
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

  public int getIdPlayerToRegister()
  {
    return idPlayerToRegister;
  }

  public void setIdPlayerToRegister(int idPlayerToRegister)
  {
    this.idPlayerToRegister = idPlayerToRegister;
  }

  public int getIdUserToRegister()
  {
    return idUserToRegister;
  }

  public void setIdUserToRegister(int idUserToRegister)
  {
    this.idUserToRegister = idUserToRegister;
  }

  public int getUserToRegisterRole()
  {
    return userToRegisterRole;
  }

  public void setUserToRegisterRole(int userToRegisterRole)
  {
    this.userToRegisterRole = userToRegisterRole;
  }

  public String getUserToRegisterMobile()
  {
    return userToRegisterMobile;
  }

  public void setUserToRegisterMobile(String userToRegisterMobile)
  {
    this.userToRegisterMobile = userToRegisterMobile;
  }

  public int getIdCurrentPlayer()
  {
    return idCurrentPlayer;
  }

  public void setIdCurrentPlayer(int idCurrentPlayer)
  {
    this.idCurrentPlayer = idCurrentPlayer;
  }

  public boolean isRegistered()
  {
    return registered;
  }

  public void setRegistered(boolean registered)
  {
    this.registered = registered;
  }

  public boolean isSquadOutUser()
  {
    return squadOutUser;
  }

  public void setSquadOutUser(boolean squadOutUser)
  {
    this.squadOutUser = squadOutUser;
  }

  public boolean isOut()
  {
    return out;
  }

  public void setOut(boolean out)
  {
    this.out = out;
  }

  public MatchInfo getMatchInfo()
  {
    return matchInfo;
  }

  public void setMatchInfo(MatchInfo matchInfo)
  {
    this.matchInfo = matchInfo;
  }

  public String getBlockType()
  {
    return blockType;
  }

  public void setBlockType(String blockType)
  {
    this.blockType = blockType;
  }

  public boolean isUserRequest()
  {
    return userRequest;
  }

  public void setUserRequest(boolean userRequest)
  {
    this.userRequest = userRequest;
  }

  public boolean isRemoveEnable()
  {
    return removeEnable;
  }

  public void setRemoveEnable(boolean removeEnable)
  {
    this.removeEnable = removeEnable;
  }

  public int getPlayersToAdd()
  {
    return playersToAdd;
  }

  public void setPlayersToAdd(int playersToAdd)
  {
    this.playersToAdd = playersToAdd;
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
   * @return the email
   */
  public String getEmail()
  {
    return email;
  }
  /**
   * @return the requestDate
   */
  public Date getRequestDate()
  {
    return requestDate;
  }
  /**
   * @return the deleteMobileVisibile
   */
  public boolean isDeleteMobileVisibile()
  {
    return deleteMobileVisibile;
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
