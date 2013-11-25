package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.cache.StatisticCache;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.*;
import it.newmedia.gokick.data.hibernate.dao.StatisticDAO;
import it.newmedia.gokick.data.hibernate.dao.UserDAO;
import it.newmedia.gokick.site.guibean.GuiPlayerVote;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.utils.DateUtil;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni per gestire l'aggiornamento delle statistiche.
 */
public class StatisticManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(StatisticManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private StatisticManager()
  {
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   * Ricalcola le statistiche degli utenti che hanno giocato la partita o la sfida
   * @param idGame id della partita o della sfida
   * @param isMatch true se idGame si riferisce ad una partita
   * @param isChallange true se idGame si riferisce ad una sfifa
   * @return true se è andato a buon fine, false se fallisce
   */
  public static boolean calculateStatistic(int idGame, boolean isMatch, boolean isChallange, Cobrand currentCobrand)
  {
    List<Player> playerList = null;
    int month = 0;
    int year = 0;
    Match match = new Match();

    if (isMatch)
    {
      //Recupero delle info relative alla partita
      match = MatchManager.getById(idGame);
      if (match == null)
      {
        return false;
      }
      //Recupero il mese e l'anno in cui si è disputata la partita
      GregorianCalendar matchStart = new GregorianCalendar();
      matchStart.setTime(match.getMatchStart());
      month = matchStart.get(Calendar.MONTH);
      year = matchStart.get(Calendar.YEAR);

      //Recupero dei giocatori che hanno partecipato alla partita
      playerList = MatchManager.getMatchPlayerList(match.getId(), true, EnumPlayerStatus.OwnerRegistered, EnumPlayerStatus.UserRegistered, EnumPlayerStatus.UserRequestRegistered);
    }
    else if (isChallange)
    {
      //TODO gestire il recupero dei dati in caso di sfida
    }
    else
    {
      return false;
    }

    try
    {
      List<Integer> idUserList = new ArrayList<Integer>();
      List<Statistic> statisticListToPersist = new ArrayList<Statistic>();
      List<User> userListToPersist = new ArrayList<User>();
      for (Player player : playerList)
      {
        idUserList.add(player.getUser().getId());
      }
      //controllo che tra i player ci sia l'organizzatore del match e che il match non sia già registrato, nel caso negativo lo aggiungo
      if (isMatch && !(idUserList.contains(match.getUserOwner().getId())))
      {
        idUserList.add(match.getUserOwner().getId());
      }

      for (Integer idUser : idUserList)
      {
        //calcolo statistiche solo per utenti Enabled
        User user = UserManager.getById(idUser);
        if (user.getUserStatus().equals(EnumUserStatus.Enabled.getValue()))
        {
          //Calcolo delle statistiche per questo utente
          Statistic userStatistic = calculateUserStatistic(user, year, month);
          statisticListToPersist.add(userStatistic);
          //Salvo tutto in una volta...
          //statisticDAO.makePersistent(userStatistic);
          //Aggiorno il numero di partite archiviate dall'utente
          user.setRecordedMatches(MatchInfoManager.countRecorded(user.getId()));
          user.setPlayedMatches(MatchInfoManager.countPlayed(user.getId()));
          //set third organized match date
          boolean setThirdOrganizedMatch=false;
          if (user.getId().compareTo(match.getUserOwner().getId()) == 0)
          {
            if (user.getThirdOrganizedMatch() == null && user.getRecordedMatches() == 3)
            {
              user.setThirdOrganizedMatch(new Date());
              setThirdOrganizedMatch=true;
              }
          }
          userListToPersist.add(user);
          //Salvo tutto in una volta...
          //userDAO.makePersistent(user);
          if (setThirdOrganizedMatch)
          {
            EmailManager.sendThirdMatchOrganized(user, currentCobrand);
          }
        }
      }
      //Faccio il lavoro su db in una transazione il più velocemente possibile
      Date txDurationTime = new Date();
      HibernateSessionHolder.beginTransaction();
      try
      {
        StatisticDAO txStatisticDAO = DAOFactory.getInstance().getStatisticDAO();
        UserDAO txUserDAO = DAOFactory.getInstance().getUserDAO();
        for (User user : userListToPersist)
        {
          txUserDAO.makePersistent(user);
        }
        for (Statistic statistic : statisticListToPersist)
        {
          txStatisticDAO.makePersistent(statistic);
        }
        HibernateSessionHolder.commitTransaction();
        logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
      }
      catch(Exception tex)
      {
        HibernateSessionHolder.rollbackTransaction();
        logger.error(tex, tex);
        return false;
      }
      for (User user : userListToPersist)
      {
        InfoProvider.removeUserInfo(user.getId());
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }

    //Svuoto la cache delle statistiche
    //TODO,verificare che si possano rimuovere solo quelle della lista interessata
    StatisticCache.removeAll();
    return true;
  }

  /**
   * restituisce la riga corrispondente alle statistiche del mese indicato per l'utente indicato
   * @param idUser dell'utente di cui mi interessano le statistiche
   * @param startDate data di inizio della  statistica (primo giorno del mese alle 0:0:00)
   * @param endDate data di fine della  statistica (primo giorno del mese alle 23:59:59)
   * @return la riga corrispondente alle statistiche del mese indicato per l'utente indicato
   */
  public static Statistic getUserMontlyStatistics(int idUser, Date startDate, Date endDate)
  {
    Statistic statistic = null;
    try
    {
      List<Statistic> statisticList = DAOFactory.getInstance().getStatisticDAO().getByIdAndPeriod(idUser, startDate, endDate);
      if (!statisticList.isEmpty())
      {
        statistic = statisticList.get(0);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex.getMessage(), ex);
      return null;
    }
    return statistic;
  }

  /**
   * @param idUser dell'utente di cui mi interessano le statistiche
   * @return cancella le statistiche per l'utente indicato
   */
  public static boolean deleteUserStatistics(int idUser)
  {
    try
    {
      List<Statistic> statisticList = DAOFactory.getInstance().getStatisticDAO().getByUser(idUser);
      for (Statistic statistic : statisticList)
      {
        DAOFactory.getInstance().getStatisticDAO().makeTransient(statistic);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex.getMessage(), ex);
      return false;
    }
    return true;
  }

  /**
   * restituisce la statistica dell'utente indicato per il mese dell'anno specificato
   * @param user l'utente
   * @param year l'anno
   * @param month il mese
   * @return la statistica dell'utente indicato per il mese dell'anno specificato
   */
  public static Statistic calculateUserStatistic(User user, int year, int month)
  {
    //Creo il periodo del quale modificare la statistica
    GregorianCalendar startDate = new GregorianCalendar(year, month, 1, 0, 0, 0);
    GregorianCalendar endDate = new GregorianCalendar(year, month, 1, 23, 59, 59);
    int lastDate = endDate.getActualMaximum(Calendar.DATE);
    endDate.set(Calendar.DATE, lastDate);

    //Verifico la presenza o meno della statistica per il mese richiesto dell'utente
    Statistic playerStatistic = getUserMontlyStatistics(user.getId(), startDate.getTime(), endDate.getTime());
    if (playerStatistic == null)
    {
      //Statistica non presente, ne creo una nuova
      playerStatistic = new Statistic();
      playerStatistic.setRefDate(new GregorianCalendar(year, month, 1, 12, 59, 59).getTime());
      playerStatistic.setUser(user);
    }
    playerStatistic.setAllMissing(0);
    playerStatistic.setGoalsTot(0);
    playerStatistic.setOwnGoalsTot(0);
    playerStatistic.setMatchTot(0);
    playerStatistic.setChallangeTot(0);
    playerStatistic.setMatchOwner(0);
    playerStatistic.setMatchRecorded(0);
    playerStatistic.setChallangeOwner(0);
    playerStatistic.setChallangeRecorded(0);
    playerStatistic.setVoteTot(new BigDecimal(0));
    playerStatistic.setVoteNum(0);
    playerStatistic.setMatchWin(0);
    playerStatistic.setMatchDraw(0);
    playerStatistic.setMatchLose(0);
    playerStatistic.setChallangeWin(0);
    playerStatistic.setChallangeDraw(0);
    playerStatistic.setChallangeLose(0);

    //Ricalcolo le statistiche del mese
    if (endDate.after(new Date()))
    {
      endDate = new GregorianCalendar();
    }
    List<VPlayerMatchChallangeArchived> pmcaList = UtilManager.getPmcaByParam(user.getId(), startDate.getTime(), endDate.getTime());
    int count = MatchManager.countOwnerMatchNotPlayer(user.getId(), startDate.getTime(), endDate.getTime());

    playerStatistic.setMatchOwner(count);// - pmcaList.size());
    playerStatistic.setMatchRecorded(count);// - pmcaList.size());

    for (VPlayerMatchChallangeArchived pmca : pmcaList)
    {
      if ((pmca.getPlayerStatus().equals(EnumPlayerStatus.OwnerRegistered.getValue()))
              || (pmca.getPlayerStatus().equals(EnumPlayerStatus.UserRegistered.getValue()))
              || (pmca.getPlayerStatus().equals(EnumPlayerStatus.UserRequestRegistered.getValue())))
      {
        if (pmca.getPlayerType().equals(EnumPlayerType.Missing.getValue()))
        {
          playerStatistic.setAllMissing(playerStatistic.getAllMissing() + 1);
        }
        else
        {
          if (pmca.isIsMatch())
          {
            //Ricalcolo i dati in relativi alle partite
            if (pmca.getIdUser() == pmca.getIdUserOwner())
            {
              playerStatistic.setMatchOwner(playerStatistic.getMatchOwner() + 1);
              playerStatistic.setMatchRecorded(playerStatistic.getMatchRecorded() + 1);
            }
            playerStatistic.setMatchTot(playerStatistic.getMatchTot() + 1);
            int teamGoalsOne = Integer.valueOf(pmca.getGameGoalsOne());
            int teamGoalsTwo = Integer.valueOf(pmca.getGameGoalsTwo());
            String matchWinner = "";
            if (teamGoalsOne > teamGoalsTwo)
            {
              matchWinner = EnumPlayerType.TeamOne.getValue();
            }
            else if (teamGoalsOne < teamGoalsTwo)
            {
              matchWinner = EnumPlayerType.TeamTwo.getValue();
            }
            if (matchWinner.equalsIgnoreCase(""))
            {
              playerStatistic.setMatchDraw(playerStatistic.getMatchDraw() + 1);
            }
            else if (pmca.getPlayerType().equalsIgnoreCase(matchWinner))
            {
              playerStatistic.setMatchWin(playerStatistic.getMatchWin() + 1);
            }
            else
            {
              playerStatistic.setMatchLose(playerStatistic.getMatchLose() + 1);
            }
          }
          else if (pmca.isIsChallange())
          {
            //Ricalcolo i dati in relativi alle sfide
            if (pmca.getIdUser() == pmca.getIdUserOwner())
            {
              playerStatistic.setChallangeOwner(playerStatistic.getChallangeOwner() + 1);
              playerStatistic.setChallangeRecorded(playerStatistic.getChallangeRecorded() + 1);
            }
            playerStatistic.setChallangeTot(playerStatistic.getChallangeTot() + 1);
            int teamGoalsOne = Integer.valueOf(pmca.getGameGoalsOne());
            int teamGoalsTwo = Integer.valueOf(pmca.getGameGoalsOne());
            String challangeWinner = "";
            if (teamGoalsOne > teamGoalsTwo)
            {
              challangeWinner = EnumPlayerType.TeamOne.getValue();
            }
            else if (teamGoalsOne < teamGoalsTwo)
            {
              challangeWinner = EnumPlayerType.TeamTwo.getValue();
            }
            if (challangeWinner.equalsIgnoreCase(""))
            {
              playerStatistic.setChallangeDraw(playerStatistic.getMatchDraw() + 1);
            }
            else if (pmca.getPlayerType().equalsIgnoreCase(challangeWinner))
            {
              playerStatistic.setChallangeWin(playerStatistic.getMatchWin() + 1);
            }
            else
            {
              playerStatistic.setChallangeLose(playerStatistic.getMatchLose() + 1);
            }
          }
          //Ricalcolo i dati comuni
          playerStatistic.setGoalsTot(playerStatistic.getGoalsTot() + pmca.getGoals());
          playerStatistic.setOwnGoalsTot(playerStatistic.getOwnGoalsTot() + pmca.getOwnGoals());
          if ((pmca.getVote().compareTo(new BigDecimal(GuiPlayerVote.ID_WITHOUT_VOTE)) != 0)
                  && (pmca.getVote().compareTo(new BigDecimal(GuiPlayerVote.ID_VOTE_NOT_ASSIGNED)) != 0))
          {
            playerStatistic.setVoteTot(playerStatistic.getVoteTot().add(pmca.getVote()));
            playerStatistic.setVoteNum(playerStatistic.getVoteNum() + 1);
          }
        }
      }
    }

    return playerStatistic;
  }

  /**
   *
   * @param idUser
   * @return numero di partite giocate
   */
  public static int getPlayed(int idUser)
  {
    try
    {
      List playedList = DAOFactory.getInstance().getStatisticDAO().getPlayed(idUser);
      Object[] playedArray = (Object[]) playedList.get(0);
      int matchTot = playedArray[0] != null ? (Integer) playedArray[0] : 0;
      int challengeTot = playedArray[1] != null ? (Integer) playedArray[1] : 0;
      return matchTot + challengeTot;
    }
    catch (Exception ex)
    {
      logger.error(ex.getMessage(), ex);
      return 0;
    }
  }
// </editor-fold>
}
