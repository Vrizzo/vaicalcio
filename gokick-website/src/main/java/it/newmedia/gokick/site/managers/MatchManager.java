package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.enums.EnumMessage;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.hibernate.beans.*;
import it.newmedia.gokick.data.hibernate.dao.MatchDAO;
import it.newmedia.gokick.data.hibernate.dao.PlayerDAO;
import it.newmedia.gokick.data.hibernate.dao.PlayerRoleDAO;
import it.newmedia.gokick.data.hibernate.dao.UserDAO;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.results.Result;
import it.newmedia.utils.DateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.*;
import java.util.logging.Level;

/**
 * Classe manager che gestisce ad alto livello le azioni per gestire l'organizzazione della partite.
 */
public class MatchManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(MatchManager.class);


  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private MatchManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  /**
   * registra uno o piu' utenti ad un match
   *
   * @param idMatch                   match in oggetto
   * @param team                      nome squadra destinazione
   * @param squadUserToRegisterList   lista amici da registrare
   * @param userRequestToRegisterList lista user in richiesta da registrare
   * @param maxPlayerOut              num max esterni
   * @param playerOutNameList         lista nomi degli esterni
   * @param playerOutRoleList         lista ruoli degli esterni
   * @return numero di utenti registrati
   */
  public static int registerUserMultipleToMatch(int idMatch, String team, Integer[] squadUserToRegisterList, Integer[] userRequestToRegisterList, int maxPlayerOut, List<String> playerOutNameList, List<Integer> playerOutRoleList)
  {
    int playerBefore = 0;
    int playerAfter = 0;
    int count = 0;
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    Match match = new Match();

    //    StringBuilder sb = new StringBuilder();
    //    sb.append(System.getProperty("line.separator"));
    //    sb.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>registerUserMultipleToMatch DEBUG<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<").append(System.getProperty("line.separator"));

    try
    {
      MatchDAO matchDao = DAOFactory.getInstance().getMatchDAO();
      match = matchDao.get(idMatch);
      UserDAO userDao = DAOFactory.getInstance().getUserDAO();
      PlayerRoleDAO playerRoleDAO = DAOFactory.getInstance().getPlayerRoleDAO();
      Player playerToregister = null;
      playerBefore = MatchManager.countPlayersNoCache(idMatch);
      int playersAdded = 0;
      //      sb.append("id Match : ").append(idMatch).append(System.getProperty("line.separator"));


      // <editor-fold defaultstate="collapsed" desc="registrazione amico">
      if (squadUserToRegisterList != null && squadUserToRegisterList.length > 0)
      {
        count = match.getPlayerList().size() + 1;
        for (int i = 0; i < squadUserToRegisterList.length; i++)
        {
          User user = userDao.get(squadUserToRegisterList[i]);

          //          sb.append("id User Amico : ").append(squadUserToRegisterList[i]).append(System.getProperty("line.separator"));

          if (user == null)
          {
            logger.warn("Cannot add a null user to match. IdMatch: " + idMatch + ", idUser: " + squadUserToRegisterList[i]);
            break;
          }
          //caso in cui exNonAmico abbia fatto richiesta per il match e nel frattempo sia diventato amico
          //recupero il player in stao USER_REQUEST (ancora da non amico) elo faccio diventare USER_REGISTERED (da amico)
          playerToregister = getPlayer(idMatch, user.getId());

          //          sb.append("ID player iniziale: ").append(playerToregister!=null ? playerToregister.getId() : 0).append(System.getProperty("line.separator"));

          if (playerToregister == null)   //i Player Amico in condizioni standard non è presente in tab PLAYERS
          {
            playerToregister = new Player();
            playerToregister.setUser(user);
            playerToregister.setReporterEnabled(false);
            playerToregister.setPlayerRole(user.getPlayerRole());
          }
          if (team == null || team.equals(""))
          {
            playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
          }
          else
          {
            if (team.equalsIgnoreCase(EnumPlayerType.TeamOne.getValue()))
            {
              playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
            }
            else if (team.equalsIgnoreCase(EnumPlayerType.TeamTwo.getValue()))
            {
              playerToregister.setEnumPlayerType(EnumPlayerType.TeamTwo);
            }

          }
          playerToregister.setEnumPlayerStatus(EnumPlayerStatus.UserRegistered);
          playerToregister.setRequestDate(new Date());
          playerToregister.setPosition(count); //numero alto per farlo comparire come ultimo in lista pagelle

          match.getPlayerList().add(playerToregister);
          playersAdded++;
          count++;
        }
      }
      // </editor-fold>

      // <editor-fold defaultstate="collapsed" desc="non amico in richiesta">
      if (userRequestToRegisterList != null && userRequestToRegisterList.length > 0)  //non amico in richiesta
      {
        count = match.getPlayerList().size() + 1;
        for (int idUser : userRequestToRegisterList)
        {
          //          sb.append("id User non Amico : ").append(idUser).append(System.getProperty("line.separator"));

          Set<Player> playerList = match.getPlayerList();
          for (Player player : playerList)
          {
            if (player.getUser() != null && player.getUser().getId() == idUser && (player.getPlayerStatus().equals(EnumPlayerStatus.UserRequest.getValue()) || player.getPlayerStatus()
                                                                                                                                                                     .equals(EnumPlayerStatus.UserRequestRegistered
                                                                                                                                                                                     .getValue())))    //player esistente (caso User_request)
            {
              //              sb.append("ID player non Amico iniziale: ").append(player.getId()!=null ? player.getId() : 0).append(System.getProperty("line.separator"));

              player.setEnumPlayerStatus(EnumPlayerStatus.UserRequestRegistered);
              player.setRequestDate(new Date());
              match.getPlayerList().add(player);
              playerToregister = player;
              if (team == null)
              {
                playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
              }
              else
              {
                if (team.equalsIgnoreCase(EnumPlayerType.TeamOne.getValue()))
                {
                  playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
                }
                else if (team.equalsIgnoreCase(EnumPlayerType.TeamTwo.getValue()))
                {
                  playerToregister.setEnumPlayerType(EnumPlayerType.TeamTwo);
                }
              }
              playerToregister.setPosition(count); //numero alto per farlo comparire come ultimo in lista pagelle

              match.getPlayerList().add(playerToregister);
              playersAdded++;
              count++;
            }
          }
        }
      }
      // </editor-fold>

      // <editor-fold defaultstate="collapsed" desc="registrazione esterni">
      if (maxPlayerOut > 0 && playerOutNameList != null && playerOutRoleList != null)  //
      {
        count = match.getPlayerList().size() + 1;
        for (int i = 0; i < maxPlayerOut; i++)
        {
          //          sb.append("Giocatore esterno OUT").append(System.getProperty("line.separator"));

          playerToregister = new Player();
          playerToregister.setOutFirstName(playerOutNameList.get(i));
          playerToregister.setEnumPlayerStatus(EnumPlayerStatus.Out);
          playerToregister.setRequestDate(new Date());
          if (playerOutRoleList.get(i) != null && playerOutRoleList.get(i) > 0)
          {
            playerToregister.setPlayerRole(playerRoleDAO.load(playerOutRoleList.get(i)));
          }
          playerToregister.setReporterEnabled(false);
          if (team == null)
          {
            playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
          }
          else
          {
            if (team.equalsIgnoreCase(EnumPlayerType.TeamOne.getValue()))
            {
              playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
            }
            else if (team.equalsIgnoreCase(EnumPlayerType.TeamTwo.getValue()))
            {
              playerToregister.setEnumPlayerType(EnumPlayerType.TeamTwo);
            }
            else
            {
              playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
            }
          }
          playerToregister.setPosition(count); //numero alto per farlo comparire come ultimo in lista pagelle

          match.getPlayerList().add(playerToregister);
          playersAdded++;
          count++;
        }
      }
      // </editor-fold>

      if (!SecurityManager.canAddPlayer(match, playersAdded))
      {
        throw new Exception("players to add are more than max players");
      }
      //DAOFactory.getInstance().getPlayerDAO().makePersistent(playerToregister);
      //      sb.append("ID player finale: ").append(playerToregister.getId()).append(System.getProperty("line.separator"));
      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      //      sb.append(e.getMessage()).append(System.getProperty("line.separator"));
      return -1;
    }


    Iterator<Player> iterator = match.getPlayerList().iterator();
    while (iterator.hasNext())
    {
      Player player = iterator.next();
      if (player.getEnumPlayerType() == EnumPlayerType.Missing || player.getEnumPlayerStatus() == EnumPlayerStatus.UserCalled)
      {
        continue;
      }
      playerAfter++;
    }
    int playerAdded = playerAfter - playerBefore;
    if (playerAdded > 0)
    {
      InfoProvider.removeMatchInfo(idMatch);
    }
    return playerAfter - playerBefore;
  }

  public static int registerOutersToMatch(int idMatch, String team, int howMany, List<String> playerOutNameList, List<Integer> playerOutRoleList)
  {
    return registerOutersToMatch(idMatch, team, howMany, playerOutNameList, playerOutRoleList, false);
  }


  /**
   * registra uno o piu' utenti esterni
   *
   * @param idMatch           match in oggetto
   * @param team              nome squadra destinazione
   * @param howMany           lista amici da registrare
   * @param playerOutNameList lista user in richiesta da registrare
   * @param playerOutRoleList num max esterni
   * @param playerOutNameList lista nomi degli esterni
   * @param playerOutRoleList lista ruoli degli esterni
   * @return numero di utenti registrati
   */
  public static int registerOutersToMatch(int idMatch, String team, int howMany, List<String> playerOutNameList, List<Integer> playerOutRoleList, boolean archived)
  {
    int playerBefore = 0;
    int playerAfter = 0;
    int count = 0;
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    Match match = new Match();

    //    StringBuilder sb = new StringBuilder();
    //    sb.append(System.getProperty("line.separator"));
    //    sb.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>registerUserMultipleToMatch DEBUG<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<").append(System.getProperty("line.separator"));

    try
    {
      MatchDAO matchDao = DAOFactory.getInstance().getMatchDAO();
      match = matchDao.get(idMatch);
      PlayerRoleDAO playerRoleDAO = DAOFactory.getInstance().getPlayerRoleDAO();
      Player playerToregister = null;
      playerBefore = MatchManager.countPlayersNoCache(idMatch);
      int playersAdded = 0;
      //      sb.append("id Match : ").append(idMatch).append(System.getProperty("line.separator"));

      // <editor-fold defaultstate="collapsed" desc="registrazione esterni">
      if (howMany > 0 && playerOutNameList != null && playerOutRoleList != null)  //
      {
        count = match.getPlayerList().size() + 1;
        for (int i = 0; i < howMany; i++)
        {
          //          sb.append("Giocatore esterno OUT").append(System.getProperty("line.separator"));
          playerToregister = new Player();
          playerToregister.setOutFirstName(playerOutNameList.get(i));
          playerToregister.setEnumPlayerStatus(EnumPlayerStatus.Out);
          playerToregister.setRequestDate(new Date());
          if (playerOutRoleList.get(i) != null && playerOutRoleList.get(i) > 0)
          {
            playerToregister.setPlayerRole(playerRoleDAO.load(playerOutRoleList.get(i)));
          }
          playerToregister.setReporterEnabled(false);
          if (team == null)
          {
            playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
          }
          else
          {
            if (team.equalsIgnoreCase(EnumPlayerType.TeamOne.getValue()))
            {
              playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
            }
            else if (team.equalsIgnoreCase(EnumPlayerType.TeamTwo.getValue()))
            {
              playerToregister.setEnumPlayerType(EnumPlayerType.TeamTwo);
            }
            else
            {
              playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
            }
          }
          playerToregister.setPosition(count); //numero alto per farlo comparire come ultimo in lista pagelle

          match.getPlayerList().add(playerToregister);
          playersAdded++;
          count++;
        }
      }
      // </editor-fold>

      if (!archived)
      {
        if (!SecurityManager.canAddPlayer(match, playersAdded))
        {
          throw new Exception("players to add are more than max players");
        }
      }

      //DAOFactory.getInstance().getPlayerDAO().makePersistent(playerToregister);
      //      sb.append("ID player finale: ").append(playerToregister.getId()).append(System.getProperty("line.separator"));
      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      //      sb.append(e.getMessage()).append(System.getProperty("line.separator"));
      return -1;
    }
    Iterator<Player> iterator = match.getPlayerList().iterator();
    while (iterator.hasNext())
    {
      Player player = iterator.next();
      if (player.getEnumPlayerType() == EnumPlayerType.Missing || player.getEnumPlayerStatus() == EnumPlayerStatus.UserCalled)
      {
        continue;
      }
      playerAfter++;
    }
    int playerAdded = playerAfter - playerBefore;
    if (playerAdded > 0)
    {
      InfoProvider.removeMatchInfo(idMatch);
    }
    return playerAfter - playerBefore;
  }

  /**
   * registra uno o piu' user ad un match
   *
   * @param idMatch              match in oggetto
   * @param team                 nome squadra destinazione
   * @param idUserToRegisterList lista user da registrare
   * @return numero di utenti registrati
   */
  public static int registerUsersToMatch(int idMatch, String team, List<Integer> idUserToRegisterList)
  {
    return registerUsersToMatch(idMatch, team, idUserToRegisterList, false);
  }

  public static int registerUsersToMatch(int idMatch, String team, List<Integer> idUserToRegisterList, boolean archived)
  {
    int playerBefore = 0;
    int playerAfter = 0;
    int count = 0;
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    Match match = new Match();

    //    StringBuilder sb = new StringBuilder();
    //    sb.append(System.getProperty("line.separator"));
    //    sb.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>registerUserMultipleToMatch DEBUG<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<").append(System.getProperty("line.separator"));

    try
    {
      MatchDAO matchDao = DAOFactory.getInstance().getMatchDAO();
      match = matchDao.get(idMatch);
      UserDAO userDao = DAOFactory.getInstance().getUserDAO();
      Player playerToregister = null;
      playerBefore = MatchManager.countPlayersNoCache(idMatch);
      int playersAdded = 0;
      //      sb.append("id Match : ").append(idMatch).append(System.getProperty("line.separator"));


      // <editor-fold defaultstate="collapsed" desc="registrazione user">
      if (idUserToRegisterList != null && idUserToRegisterList.size() > 0)
      {
        count = match.getPlayerList().size() + 1;
        for (int i = 0; i < idUserToRegisterList.size(); i++)
        {
          User user = userDao.get(idUserToRegisterList.get(i));

          //          sb.append("id User Amico : ").append(squadUserToRegisterList[i]).append(System.getProperty("line.separator"));

          if (user == null)
          {
            logger.warn("Cannot add a null user to match. IdMatch: " + idMatch + ", idUser: " + idUserToRegisterList.get(i));
            break;
          }
          //caso in cui exNonAmico abbia fatto richiesta per il match e nel frattempo sia diventato amico
          //recupero il player in stao USER_REQUEST (ancora da non amico) elo faccio diventare USER_REGISTERED (da amico)
          playerToregister = getPlayer(idMatch, user.getId());

          //          sb.append("ID player iniziale: ").append(playerToregister!=null ? playerToregister.getId() : 0).append(System.getProperty("line.separator"));

          if (playerToregister == null)   //i Player Amico in condizioni standard non è presente in tab PLAYERS
          {
            playerToregister = new Player();
            playerToregister.setUser(user);
            playerToregister.setReporterEnabled(false);
            playerToregister.setPlayerRole(user.getPlayerRole());
          }
          if (team == null || team.equals(""))
          {
            playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
          }
          else
          {
            if (team.equalsIgnoreCase(EnumPlayerType.TeamOne.getValue()))
            {
              playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
            }
            else if (team.equalsIgnoreCase(EnumPlayerType.TeamTwo.getValue()))
            {
              playerToregister.setEnumPlayerType(EnumPlayerType.TeamTwo);
            }

          }
          playerToregister.setEnumPlayerStatus(EnumPlayerStatus.UserRegistered);
          playerToregister.setRequestDate(new Date());
          playerToregister.setPosition(count); //numero alto per farlo comparire come ultimo in lista pagelle

          match.getPlayerList().add(playerToregister);
          playersAdded++;
          count++;
        }
      }
      // </editor-fold>

      //      controllo numero giocatori max solo se la partita non è stata giocata
      if (!archived)
      {
        if (!SecurityManager.canAddPlayer(match, playersAdded))
        {
          throw new Exception("players to add are more than max players");
        }
      }


      //DAOFactory.getInstance().getPlayerDAO().makePersistent(playerToregister);
      //      sb.append("ID player finale: ").append(playerToregister.getId()).append(System.getProperty("line.separator"));
      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      //      sb.append(e.getMessage()).append(System.getProperty("line.separator"));
      return -1;
    }


    Iterator<Player> iterator = match.getPlayerList().iterator();
    while (iterator.hasNext())
    {
      Player player = iterator.next();
      if (player.getEnumPlayerType() == EnumPlayerType.Missing || player.getEnumPlayerStatus() == EnumPlayerStatus.UserCalled)
      {
        continue;
      }
      playerAfter++;
    }
    int playerAdded = playerAfter - playerBefore;
    if (playerAdded > 0)
    {
      InfoProvider.removeMatchInfo(idMatch);
    }
    return playerAfter - playerBefore;
  }

  /**
   * aggiorna un player di un match
   *
   * @param idMatch              match in oggetto
   * @param IdUser               id user del player aggiornare
   * @param userToRegisterRole   ruolo
   * @param userToRegisterMobile num mobile
   * @return "true" se a buon fine
   */
  public static boolean updateMatchPlayer(int idMatch, int IdUser, int userToRegisterRole, String userToRegisterMobile)
  {
    try
    {
      PlayerRoleDAO playerRoleDAO = DAOFactory.getInstance().getPlayerRoleDAO();
      Player player = MatchManager.getPlayer(idMatch, IdUser);

      player.setPlayerRole(playerRoleDAO.load(userToRegisterRole));

      if (SecurityManager.isUserOwner(IdUser, idMatch))
      {
        for (Match match : player.getMatchList())
        {
          if (match.getId() == idMatch)
          {
            match.setMobileUserOwner(userToRegisterMobile);
          }
          InfoProvider.removeMatchInfo(idMatch);
        }
      }
      else
      {
        player.setMobile(userToRegisterMobile);
      }
      InfoProvider.removeMatchInfo(idMatch);
      return MatchManager.updatePlayer(player);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }

  }

  /**
   * @param match
   * @param playerRole
   * @param userInfoSquadList
   * @return
   */
  public static boolean save(Match match, PlayerRole playerRole, List<UserInfo> userInfoSquadList, Cobrand currentCobrand)
  {
    List<Match> list = new ArrayList<Match>();
    list.add(match);
    return save(list, playerRole, userInfoSquadList, currentCobrand);
  }

  /**
   * salva uno o piu' match e manda mail notifica ad amici
   *
   * @param matchList         lista di match da salvare
   * @param playerRole        eventuale ruolo dell'organizzatore
   * @param userInfoSquadList lista amici
   * @return "true" se a buon fine
   */
  public static boolean save(List<Match> matchList, PlayerRole playerRole, List<UserInfo> userInfoSquadList, Cobrand currentCobrand)
  {
    try
    {
      for (Match match : matchList)
      {
        //Set del ruolo dell'organizzatore della partita
        if (playerRole != null)
        {
          Player player = new Player();
          player.setUser(match.getUserOwner());
          player.setPlayerRole(playerRole);
          player.setEnumPlayerStatus(EnumPlayerStatus.OwnerRegistered);
          player.setRequestDate(new Date());
          player.setReporterEnabled(false);
          player.setEnumPlayerType(EnumPlayerType.TeamOne);
          match.getPlayerList().add(player);
        }
        match.setNotified(false);
        DAOFactory.getInstance().getMatchDAO().makePersistent(match);
        //invio mail apertura iscrizioni ad amici
        if (!match.getRegistrationClosed() && match.getRegistrationStart() == null)
        {
          Result<Boolean> rSend = EmailManager.sendMatchRegistrationOpenEmail(match, userInfoSquadList, currentCobrand);
          if (!rSend.isSuccessNotNull() || !rSend.getValue())
          {
            logger.error("Error sending mail for registration Open (MatchManager.java", rSend.getErrorException());
          }
          else
          {
            match.setNotified(true);
          }
        }
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }
    return true;
  }

  /**
   * aggiorna un match
   *
   * @param match             match da aggiornare
   * @param sendModifyAdvice  indica se inviare notifica di match modificato
   * @param sendArchiveAdvice indica se inviare notifica di match archiviato
   * @return "true" se a buon fine
   */
  public static boolean update(Match match, boolean sendModifyAdvice, boolean sendArchiveAdvice, Language currentLanguage, Cobrand currentCobrand)
  {
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    MatchDAO matchDao = DAOFactory.getInstance().getMatchDAO();
    try
    {
      matchDao.makePersistent(match);

      List<Player> playerList = getMatchPlayerList(match.getId(), true, EnumPlayerStatus.OwnerRegistered, EnumPlayerStatus.UserRegistered, EnumPlayerStatus.UserRequestRegistered);
      for (Player player : playerList)
      {
        if (player.getPlayerType().equals(EnumPlayerType.Missing.getValue()))
        {
          continue;
        }
        User user = player.getUser();

        if (sendArchiveAdvice)
        {
          //Save message
          boolean success = MessageManager.saveMatchMessage(EnumMessage.MatchArchived.getValue(), match.getMatchStart(), DateManager.FORMAT_DATE_13, currentLanguage, currentCobrand);
          if (!success)
          {
            HibernateSessionHolder.rollbackTransaction();
            return false;
          }
          //sendEmail to who has permission on
          if (user.getAlertOnReportCreated())
          {
            Result<Boolean> rEmailSend = EmailManager.sendMatchArchivedNotifyEmail(user, match.getMatchStart(), match.getId(), currentCobrand);
            if (!rEmailSend.isSuccessNotNull() || !rEmailSend.getValue())
            {
              HibernateSessionHolder.rollbackTransaction();
              return false;
            }
          }
        }
      }
      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return false;
    }
    finally
    {
      InfoProvider.removeMatchInfo(match.getId());
    }
    return true;
  }

  /**
   * cancella un match
   *
   * @param match match da eliminare
   * @return "true" se a buon fine
   */
  public static boolean cancel(Match match, Language currentLanguage, Cobrand currentCobrand)
  {
    Date txDurationTime = new Date();
    Session session = HibernateSessionHolder.beginTransaction();
    MatchDAO matchDao = DAOFactory.getInstance().getMatchDAO();
    try
    {
      matchDao.makePersistent(match);
      List<Player> playerList = getMatchPlayerList(match.getId(), true, EnumPlayerStatus.OwnerRegistered, EnumPlayerStatus.UserRegistered, EnumPlayerStatus.UserRequestRegistered);
      for (Player player : playerList)
      {
        if (player.getReporterEnabled())
        {
          player.setReporterEnabled(false);  //partita annullata, il pagellatore diventa di default(userOwner)
        }
        User user = player.getUser();
        //Save message
        boolean success = MessageManager.saveMatchMessage(EnumMessage.MatchCancelled.getValue(), match.getMatchStart(), Constants.DATE_FORMAT__EEEEEEEEEEEDDMMMM, currentLanguage, currentCobrand);
        if (!success)
        {
          HibernateSessionHolder.rollbackTransaction();
          return false;
        }

        //Send email
        if (user != null && user.getAlertOnChange())
        {
          Result<Boolean> rEmailSend = EmailManager.sendMatchCancelledNotifyEmail(user, match.getMatchStart(), DateManager.FORMAT_DATE_13, match.getId(), currentCobrand);
          if (!rEmailSend.isSuccessNotNull() || !rEmailSend.getValue())
          {
            HibernateSessionHolder.rollbackTransaction();
            return false;
          }
        }
      }

      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));

    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return false;
    }

    InfoProvider.removeMatchInfo(match.getId());

    return true;
  }

  /**
   * setta lo user con incarico di pagellatore
   *
   * @param idMatch          match in oggetto
   * @param idPlayerToReport id player da incaricare
   * @return "true" se a buon fine
   */
  public static boolean setReporter(int idMatch, int idPlayerToReport)
  {
    try
    {
      MatchDAO matchDAO = DAOFactory.getInstance().getMatchDAO();
      Match match = matchDAO.get(idMatch);
      for (Player player : match.getPlayerList())
      {
        player.setReporterEnabled(player.getId() == idPlayerToReport);
      }
      matchDAO.makePersistent(match);
      InfoProvider.removeMatchInfo(idMatch);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }
    return true;
  }

  /**
   * recupera il player con incarico di pagellatore per il match
   *
   * @param idMatch match d'interesse
   * @return Player pagellatore o null
   */
  public static Player getReporter(int idMatch)
  {
    try
    {
      PlayerDAO playerDAO = DAOFactory.getInstance().getPlayerDAO();
      return playerDAO.getReporterByMatch(idMatch);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }
  }

  /**
   * @param idUser id user d'interesse
   * @return List di id Match dove lo user è reporter o organizzatore
   */
  public static List<Integer> getOwnerOrReporter(int idUser)
  {
    List<Integer> idMatchList = new ArrayList<Integer>();
    try
    {
      idMatchList = DAOFactory.getInstance().getMatchDAO().getOwnerOrReporter(idUser);
      return idMatchList;
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return idMatchList;
    }
  }

  /**
   * @param idUser  id user d'interesse
   * @param idMatch id match in oggetto
   * @return true se user selelzionato è pagellatore del match
   */
  public static boolean isUserReporter(int idUser, int idMatch)
  {
    try
    {
      Player reporter = getReporter(idMatch);
      if (reporter == null)
      {
        return false;
      }
      else if (reporter.getUser().getId() == idUser)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }
  }

  /**
   * aggiunge player ad un match
   *
   * @param idMatch          id del match
   * @param idUser           id del player
   * @param enumPlayerStatus stato del player da aggiungere
   * @param idPlayerRole     ruolo del player da aggiungere
   * @param mobile           del player se specificato
   * @return "true" se a buon fine
   */
  public static boolean addPlayer(int idMatch, int idUser, EnumPlayerStatus enumPlayerStatus, int idPlayerRole, String mobile)
  {
    //StringBuilder sb = new StringBuilder();
    try
    {
      //      sb.append(System.getProperty("line.separator"));
      //      sb.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ADD PLAYER DEBUGGING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<").append(System.getProperty("line.separator"));
      //      sb.append("arrivo da matchPlayerStatusAction si aggiunge un player (richiesta o iscrizione)")
      //        .append(System.getProperty("line.separator"));
      User currentUser = UserContext.getInstance().getUser();
      MatchDAO matchDao = DAOFactory.getInstance().getMatchDAO();
      UserDAO userDao = DAOFactory.getInstance().getUserDAO();
      PlayerRoleDAO playerRoleDAO = DAOFactory.getInstance().getPlayerRoleDAO();

      //      sb.append("ID user : ").append(idUser).append("  match: ").append(idMatch).append(System.getProperty("line.separator"));

      Match match = matchDao.get(idMatch);
      //caso di player esistente es:UserRequest e partita con iscrizioni da valuta a dirette,player Missing
      Player playerToregister = MatchManager.getPlayer(idMatch, idUser);

      //      sb.append("ID player iniziale: ").append(playerToregister!=null ? playerToregister.getId() : 0).append(System.getProperty("line.separator"));

      if (playerToregister == null)
      {
        playerToregister = new Player();
        playerToregister.setUser(userDao.get(idUser));
        playerToregister.setRequestDate(new Date());
      }

      if (enumPlayerStatus.equals(EnumPlayerStatus.OwnerRegistered) ||
              enumPlayerStatus.equals(EnumPlayerStatus.UserRegistered) ||
              enumPlayerStatus.equals(EnumPlayerStatus.UserRequestRegistered))
      {
        playerToregister.setRequestDate(new Date());
      }

      if (idPlayerRole > 0)
      {
        playerToregister.setPlayerRole(playerRoleDAO.load(idPlayerRole));
      }

      //      sb.append("ID playerRole: ").append(playerToregister.getPlayerRole().getId()).append(System.getProperty("line.separator"));

      playerToregister.setEnumPlayerStatus(enumPlayerStatus);
      playerToregister.setReporterEnabled(false);
      playerToregister.setEnumPlayerType(EnumPlayerType.TeamOne);
      playerToregister.setMobile(mobile);

      //      sb.append("enumPlayerStatus: ").append(playerToregister.getPlayerStatus()).append(System.getProperty("line.separator"));

      if (enumPlayerStatus != EnumPlayerStatus.UserRequest && !SecurityManager.canAddPlayer(match, 1)) //check se il posto è ancora disponibile
      {
        throw new Exception("players to add are more than max players");
      }

      //TO_CHECK
      if (SecurityManager.isUserOwner(idUser, idMatch))//currentUser.getId(), idMatch))
      {
        match.setMobileUserOwner(mobile);
        DAOFactory.getInstance().getMatchDAO().makePersistent(match);
      }

      match.getPlayerList().add(playerToregister);

      DAOFactory.getInstance().getPlayerDAO().makePersistent(playerToregister);

      //Avvio la procedura per scrivere un post che dice "Mi sono iscritto..."
      if( enumPlayerStatus == EnumPlayerStatus.UserRegistered ||enumPlayerStatus == EnumPlayerStatus.UserRequest)
      {
        FacebookManager.postOnMatchRegistration(idMatch, idUser);
      }
      InfoProvider.removeMatchInfo(idMatch);

      //      sb.append("ID player finale: ").append(playerToregister.getId()).append(System.getProperty("line.separator"));

      return true;
    }
    catch (Exception e)
    {
      logger.error(e, e);
      //sb.append(e.getMessage()).append(System.getProperty("line.separator"));
      return false;
    }
    //    finally
    //    {
    ////      sb.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    //      //logger.info(sb.toString());
    //    }
  }

  /**
   * registra un player in richiesta alla partita
   *
   * @param idMatch  id del match
   * @param idPlayer id del player
   * @return true se a buon fine
   */
  public static boolean registerPlayer(int idMatch, int idPlayer, Cobrand currentCobrand)
  {
    try
    {
      User user = null;
      MatchDAO matchDAO = DAOFactory.getInstance().getMatchDAO();
      Match match = matchDAO.get(idMatch);
      Player playerToAdd = new Player();
      for (Player player : match.getPlayerList())
      {
        if (player.getId() == idPlayer)
        {
          //caso in cui user è in attesa di valutazione e nel frattempo è diventato amico
          if (SquadManager.isFriendOf(match.getUserOwner().getId(), player.getUser().getId()))
          {
            player.setEnumPlayerStatus(EnumPlayerStatus.UserRegistered);
          }
          else
          {
            player.setEnumPlayerStatus(EnumPlayerStatus.UserRequestRegistered);
          }
          player.setRequestDate(new Date());
          playerToAdd = player;
          user = (player.getUser() != null ? UserManager.getById(player.getUser().getId()) : null);
          break;
        }
      }

      if (!SecurityManager.canAddPlayer(match, 1)) //check se il posto è ancora disponibile
      {
        throw new Exception("players to add are more than max players");
      }
      matchDAO.makePersistent(match);
      matchDAO.flush();
      if (user != null && user.getAlertOnSquadOutAccepted() == true)
      {
        MatchInfo matchInfo = InfoProvider.getMatchInfo(idMatch);
        Language language = UserContext.getInstance().getLanguage();
        Result<Boolean> rEmailSend = EmailManager.sendRequestRegisteredUserToMatchNotifyEmail(matchInfo, user, currentCobrand);
      }

      return true;
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }
    finally
    {
      //ripulisco la cache!
      InfoProvider.removeMatchInfo(idMatch);
    }
  }

  /**
   * @param idMatch        id del match
   * @param withMissing    indica se contare player in stato MISSING
   * @param playerStatuses indica quali stati dei player includere nel conteggio
   * @return num player
   */
  public static int countMatchPlayers(int idMatch, boolean withMissing, EnumPlayerStatus... playerStatuses)
  {
    try
    {
      return DAOFactory.getInstance().getPlayerDAO().countPlayerByMatch(idMatch, withMissing, playerStatuses);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  /**
   * @param idMatch id del match
   * @return num player registrati al match non in stato "missing", il dato è preso da db tramite nuova sessione, non dalla cache
   */
  public static int countPlayersNoCache(int idMatch)
  {
    int players = 0;
    Session newSess = HibernateSessionHolder.getSessionPerRequestSession().getSessionFactory().openSession();
    try
    {

      players = DAOFactory.getInstance()
                          .getPlayerDAO()
                          .countPlayerByMatch(idMatch, false, new EnumPlayerStatus[]{EnumPlayerStatus.Out, EnumPlayerStatus.OwnerRegistered, EnumPlayerStatus.UserRegistered, EnumPlayerStatus.UserRequestRegistered}, newSess);


    }
    catch (Exception e)
    {
      logger.error("error counting players", e);
      return players;
    }
    finally
    {
      if (newSess != null)
      {
        newSess.close();
      }
    }
    return players;
  }

  /**
   * @param idMatch id del match
   * @return num player registrati al match non in stato "missing", il dato è preso da db tramite nuova sessione, non dalla cache
   */
  public static int countPlayersOutNoCache(int idMatch)
  {
    int players = 0;
    Session newSess = HibernateSessionHolder.getSessionPerRequestSession().getSessionFactory().openSession();
    try
    {

      players = DAOFactory.getInstance().getPlayerDAO().countPlayerByMatch(idMatch, false, new EnumPlayerStatus[]{EnumPlayerStatus.Out}, newSess);


    }
    catch (Exception e)
    {
      logger.error("error counting players", e);
      return players;
    }
    finally
    {
      if (newSess != null)
      {
        newSess.close();
      }
    }
    return players;
  }

  /**
   * @param idMatch        id del match
   * @param withMissing    indica se includer i player in stato MISSING
   * @param playerStatuses indica quali stati dei player includere nel conteggio
   * @return lista dei player del match
   */
  public static List<Player> getMatchPlayerList(int idMatch, boolean withMissing, EnumPlayerStatus... playerStatuses)
  {
    try
    {
      return DAOFactory.getInstance().getPlayerDAO().getPlayerListByMatch(idMatch, withMissing, playerStatuses);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Player>();
    }
  }

  /**
   * @param idMatch        id del match
   * @param withMissing    indica se includer i player in stato MISSING
   * @param playerStatuses indica quali stati dei player includere nel conteggio
   * @return lista dei player del match ordinata per nome
   */
  public static List<Player> getPlayerListOrderByName(int idMatch, boolean withMissing, EnumPlayerStatus... playerStatuses)
  {
    try
    {
      return DAOFactory.getInstance().getPlayerDAO().getPlayerListByMatch(idMatch, withMissing, playerStatuses, true);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Player>();
    }
  }

  /**
   * cancella player dal match
   *
   * @param idMatch  id del match
   * @param idPlayer id del player da eliminare
   * @return "true" se a buon fine
   */
  public static boolean deletePlayerFromMatch(int idMatch, int idPlayer)
  {
    try
    {
      Match match = DAOFactory.getInstance().getMatchDAO().get(idMatch);
      Player playerToRemove = null;
      for (Player player : match.getPlayerList())
      {
        if (player.getId() == idPlayer)
        {
          playerToRemove = player;
          break;
        }
      }
      if (playerToRemove != null)
      {
        match.getPlayerList().remove(playerToRemove);
        DAOFactory.getInstance().getPlayerDAO().makeTransient(playerToRemove, false);
        InfoProvider.removeMatchInfo(idMatch);

      }
      return true;
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }
  }

  /**
   * @param idMatch id del match
   * @param idUser  id dello user
   * @return un player
   */
  public static Player getPlayer(int idMatch, int idUser)
  {
    //Player player = null;
    try
    {
      return DAOFactory.getInstance().getPlayerDAO().getPlayer(idMatch, idUser);
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
    return null;
  }

  /**
   * aggiorna un player
   *
   * @param player player da aggiornare
   * @return "true" se a buon fine
   */
  public static boolean updatePlayer(Player player)
  {
    try
    {
      DAOFactory.getInstance().getPlayerDAO().makePersistent(player);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }
    return true;
  }

  /**
   * @param idMatch id del match
   * @return match
   */
  public static Match getById(int idMatch)
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().get(idMatch);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }
  }

  /**
   * restituisce l'id del prossimo match a cui l'utente è iscritto
   *
   * @param idUser
   * @return id match
   */
  public static int getNextOne(int idUser)
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getNextOne(idUser);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  /**
   * restituisce l'id del prossimo match a cui l'utente è iscritto
   *
   * @param idUser
   * @return id match
   */
  public static int getByOrganizedDatePosition(int idUser, int position)
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getByOrganizedDatePosition(idUser, position);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  /**
   * restituisce l'id dell'ultimo match archiviato a cui ha partecipato lo user
   *
   * @param idUser id user
   * @return id match
   */
  public static int getLastValidResult(int idUser)
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getLastValidResult(idUser);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  /**
   * @return lista di match da notificare
   */
  public static List<Match> getMatchToNotify()
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getMatchesToNotify();
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Match>();
    }
  }

  /**
   * @param idUserOwner id organizzatore
   * @param startDate   data inizio periodo
   * @param endDate     data fine periodo
   * @return lista di id match di partite in cui l'organizzatore non è giocatore
   */
  public static List<Integer> getWithOwnerNotPlayer(int idUserOwner, Date startDate, Date endDate)
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getWithOwnerNotPlayer(idUserOwner, startDate, endDate);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Integer>();
    }
  }

  /**
   * @param idUserOwner id organizzatore
   * @param startDate   data inizio periodo
   * @param endDate     data fine periodo
   * @return num di match  in cui l'organizzatore non è giocatore
   */
  public static int countOwnerMatchNotPlayer(int idUserOwner, Date startDate, Date endDate)
  {
    try
    {

      return DAOFactory.getInstance().getMatchDAO().countOwnerMatch(idUserOwner, startDate, endDate) - DAOFactory.getInstance().getMatchDAO().countOwnerMatchPlayer(idUserOwner, startDate, endDate);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  public static List<Integer> getRecorded()
  {
    try
    {
      return DAOFactory.getInstance().getMatchDAO().getRecorded();
    }
    catch (Exception ex)
    {
      java.util.logging.Logger.getLogger(MatchManager.class.getName()).log(Level.SEVERE, null, ex);
      return new ArrayList<Integer>();
    }

  }


  // </editor-fold>
}
