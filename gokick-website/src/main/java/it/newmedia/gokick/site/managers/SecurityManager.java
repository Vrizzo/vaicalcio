/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.Date;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * classe che controlla le condizioni necessarie perchè l’utente possa accedere a determinate funzionalità
 * @author ggeroldi
 */
public class SecurityManager
{
  protected static final Logger logger = Logger.getLogger(SecurityManager.class);

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private SecurityManager()
  {
  }

  // </editor-fold>

  
  // <editor-fold defaultstate="collapsed" desc="-- MatchPlayerStatus --"  >

  /**
   * controlla se l'utente ha requisiti per visualizzare la partita
   * @param idMatch id parita
   * @param idUser id utente
   * @return "true" in caso positivo
   */
  public static boolean canViewMatch(int idMatch, int idUser)
    {
    MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);
    if (!matchInfo.isValid())     //controllo partita esistente
    {
      //logger.debug("idMatch is not valid (!matchInfo.isvalid");
      return false;
    }
    if (!matchInfo.isSquadOutEnable() && idUser!=matchInfo.getIdUserOwner())
    {
      if (!SquadManager.isFriendOf(matchInfo.getIdUserOwner(), idUser))
      {
          Player player = MatchManager.getPlayer(idMatch, idUser);
          if (player==null)
            return false;
      }
    }  
    return true;
    }

  /**
   * controlla se il giocatore è valido e in stato request e corrispondente all'utente loggato
   * @param idPlayer id giocatore
   * @return "true" in caso positivo
   */
  public static boolean isPlayerValidAndUserRequestAndSelf(int idPlayer)  //controlla che player sia  UserRequest e  se stess0
    {
      Player player = PlayerManager.getPlayer(idPlayer);
      if (player==null || player.getUser() == null)
      {
        return false;
      }
      if (!player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequest))
      {
        return false;
      }
      if (player.getUser().getId().intValue() != UserContext.getInstance().getUser().getId())
      {
        return false;
      }
        
      return true;
    }

  /**
   * controlla se il giocatore possa visualizzare la parte di gestione iscrizione alla partita
   * @param currentUser utente corrente
   * @param matchInfo oggetto matchInfo della partita in questione
   * @param player giocatore della partita
   * @return
   */
  public static boolean isPlayerStatusVisible(User currentUser,MatchInfo matchInfo,Player player)
    {
        if (isUserOwner(currentUser.getId(), matchInfo.getId()))
        {
            return true;
        }
        else
        {
            if     (matchInfo.getMatchStart().after(new Date()) &&     //se la partita non è stata ancora giocata
                   (!matchInfo.isRegistrationClosed()) &&               //se le registrazioni sono aperte
                   (!matchInfo.isRecorded() )  &&                       //se non è registrata
                   (!(matchInfo.getRegistrationStart()!=null ? matchInfo.getRegistrationStart().after(new Date()) : false)) //se sono iniziate le registrazioni,
                   )
            {
                return true;
            }
            if     (matchInfo.getMatchStart().after(new Date()) &&     //se la partita non è stata ancora giocata
                    player!=null &&
                    player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequest)
                    ) //il player è USER_REQUEST,disdette si, partita non iniziata
                   
            {
                return true;
            }
        }

     return false;
    }

    /**
     * controlla se il giocatore possa visualizzare la parte di gestione iscirzione alla partita (non utilizzato)
     * @param currentUser
     * @param matchInfo
     * @return "true" in caso positivo
     */
    public static boolean isPlayerStatusVisible(User currentUser,MatchInfo matchInfo)
    {
        if (isUserOwner(currentUser.getId(), matchInfo.getId()))
        {
            return true;
        }
        else
        {
            if (     matchInfo.getMatchStart().after(new Date()) &&     //se la partita non è stata ancora giocata
                   (!matchInfo.isRegistrationClosed()) &&               //se le registrazioni sono aperte
                   (!matchInfo.isRecorded() )  &&                       //se non è registrata
                   (!(matchInfo.getRegistrationStart()!=null ? matchInfo.getRegistrationStart().after(new Date()) : false)) //se sono iniziate le registrazioni,
                   )
            {
                return true;
            }
        }

     return false;
    }

    /**
     * controlla se il giocatore possa visualizzare la parte di gestione iscirzione alla partita (non utilizzato)
     * @param currentUser user in questione
     * @param idMatch id partita
     * @return "true" in caso positivo
     */
    public static boolean isPlayerStatusVisible(User currentUser, int idMatch)
    {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);

        if (isUserOwner(currentUser.getId(), idMatch))
        {
            return true;
        }
        else
        {
            if (     matchInfo.getMatchStart().after(new Date()) &&
                   (!matchInfo.isRegistrationClosed()) &&
                   (!matchInfo.isRecorded() )  &&
                   (!(matchInfo.getRegistrationStart()!=null ? matchInfo.getRegistrationStart().after(new Date()) : false))
                   )
            {
                return true;
            }
        }

     return false;
    }

    /**
     * controlla se il match ha esaurito i posti dichiarati
     * @param idMatch id partita
     * @return "true" in caso positivo
     */
    public static boolean isMatchFull(int idMatch)
    {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);
        matchInfo.getMaxPlayers();

        int registeredPlayerMatchCount=MatchManager.countMatchPlayers(idMatch,false, EnumPlayerStatus.Out,
                                                EnumPlayerStatus.OwnerRegistered,
                                                EnumPlayerStatus.UserRegistered,
                                                EnumPlayerStatus.UserRequestRegistered);

        if (registeredPlayerMatchCount >= matchInfo.getMaxPlayers())
        {
            return true;
        }
     return false;
    }

    /**
     * controlla se il Player può cancellarsi dalla partita (non utilizzato)
     * @param currentUser user da cancellare
     * @param idMatch id partita
     * @return "true" in caso positivo
     */
    public static boolean isPlayerStatusRemovable(User currentUser, int idMatch)
    {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);

        if ( isUserOwner(currentUser.getId(), idMatch) )
        {
            return true;
        }
        else
        {
            if (   (matchInfo.getAcceptTerminationLimit()!=null ? matchInfo.getAcceptTerminationLimit().after(new Date()) : false) &&  //se accetta disdette ed in tempo
                    matchInfo.getMatchStart().after(new Date()) &&
                   (!matchInfo.isRegistrationClosed()) &&
                   (!matchInfo.isRecorded() )  &&
                   (!(matchInfo.getRegistrationStart()!=null ? matchInfo.getRegistrationStart().after(new Date()) : false))
                   )
            {
                return true;
            }
        }

     return false;
    }

    /**
     * controlla se il Player può cancellarsi dalla partita
     * @param currentUser user che si vuole cancellare
     * @param idMatch id partita
     * @param player player da cancellare
     * @return "true" in caso positivo
     */
    public static boolean isPlayerStatusRemovable(User currentUser, int idMatch, Player player)
    {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);

        if ( isUserOwner(currentUser.getId(), idMatch) )
        {
            return true;
        }
        else
        {
            if (  ( (matchInfo.getAcceptTerminationLimit()!=null ? matchInfo.getAcceptTerminationLimit().after(new Date()) : false) &&  //se accetta disdette ed in tempo
                    matchInfo.getMatchStart().after(new Date()) &&
                   (!matchInfo.isRegistrationClosed()) &&
                   (!matchInfo.isRecorded() )  &&
                   (!(matchInfo.getRegistrationStart()!=null ? matchInfo.getRegistrationStart().after(new Date()) : false))
                  )
                  ||
                  (
                   ( matchInfo.getMatchStart().after(new Date()) &&
                   ( !matchInfo.isRegistrationClosed()) &&
                   ( !matchInfo.isRecorded() )  &&
                   (!(matchInfo.getRegistrationStart()!=null ? matchInfo.getRegistrationStart().after(new Date()) : false)) &&
                    player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequest) )
                  )
                )

            {
                return true;
            }
            if     (matchInfo.getMatchStart().after(new Date()) &&     //se la partita non è stata ancora giocata
                    player!=null &&
                    player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequest)
                    ) //il player è USER_REQUEST,disdette si, partita non iniziata

            {
                return true;
            }
        }
        return false;
   }

   /**
    * controlla se il Player può cancellarsi dalla partita
    * @param currentUser user che si vuole cancellare
    * @param matchInfo ogeetto matchInfo della partita in questione
    * @return "true" in caso positivo
    */
   public static boolean isPlayerStatusRemovable(User currentUser, MatchInfo matchInfo)
    {

        if ( isUserOwner(currentUser.getId(), matchInfo.getId()) )
        {
            return true;
        }
        else
        {
            if (   (matchInfo.getAcceptTerminationLimit()!=null ? matchInfo.getAcceptTerminationLimit().after(new Date()) : false) &&  //se accetta disdette ed in tempo
                    matchInfo.getMatchStart().after(new Date()) &&
                   (!matchInfo.isRegistrationClosed()) &&
                   (!matchInfo.isRecorded() )  &&
                   (!(matchInfo.getRegistrationStart()!=null ? matchInfo.getRegistrationStart().after(new Date()) : false))
                   )
            {
                return true;
            }
        }

     return false;
    }

   /**
    * controlla se lo user è organizzatore della partita
    * @param IdUser id user da controllare
    * @param idMatch id match
    * @return "true" in caso positivo
    */
   public static boolean isUserOwner(int IdUser, int idMatch)
    {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);

        if (IdUser==matchInfo.getIdUserOwner())
        {
            return true;
        }
       
     return false;
    }


// </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- UserDetails --"  >

   /**
    * controllo se lo user può aggiungere feedback(non è ses stesso)
    * @param idUser id user da controllare
    * @return "true" in caso positivo
    */
   public static boolean canAddFeedback(int idUser)
   {
    return (idUser!=UserContext.getInstance().getUser().getId());
   }

// </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- match secutirty --">
   public static boolean canAddPlayer(Match match,int playerToAdd)
   {
    try
    {
      Session newSess = HibernateSessionHolder.getSessionPerRequestSession().getSessionFactory().openSession();
      int registered = DAOFactory.getInstance().getPlayerDAO().countPlayerByMatch(match.getId(), false, 
                       new EnumPlayerStatus[]{EnumPlayerStatus.Out, EnumPlayerStatus.OwnerRegistered, EnumPlayerStatus.UserRegistered, EnumPlayerStatus.UserRequestRegistered},
                       newSess);
      if (match.getMaxPlayers() < (registered + playerToAdd))
      {
        return false;
      }
      return true;
    }
   
    catch (Exception ex)
    {
      java.util.logging.Logger.getLogger(SecurityManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    return true;
   }
   // </editor-fold>


}//end Class
