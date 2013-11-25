package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.dao.PlayerDAO;
import it.newmedia.gokick.data.hibernate.dao.PlayerRoleDAO;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni per gestire i giocatori delle partite.
 */
public class PlayerManager {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(PlayerManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private PlayerManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   *
   * @param idPlayer id del Player richiesto
   * @return oggetto Player
   */
  public static Player getPlayer(int idPlayer)
  {
    Player player = null;
    try
    {
      player = DAOFactory.getInstance().getPlayerDAO().get(idPlayer);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }
    return player;
  }

  /**
   *
   * @param idPlayer id player in oggetto
   * @return lo stato (EnumPlayerStatus) del Player
   */
  public static EnumPlayerStatus getPlayerStatus(int idPlayer)
  {
    EnumPlayerStatus status;
    try
    {
      status = ((Player) DAOFactory.getInstance().getPlayerDAO().get(idPlayer)).getEnumPlayerStatus();
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }
    return status;
  }

  /**
   *
   * @param idRole id ruolo
   * @return la chiave (KeyName) del ruolo richiesto
   */
  public static String getPlayerRole(int idRole)
  {
    PlayerRoleDAO playerRoleDAO = DAOFactory.getInstance().getPlayerRoleDAO();
    String playerRole;
    try
    {
      playerRole = playerRoleDAO.get(idRole).getKeyName();
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }
    return playerRole;
  }

  /**
   * salva un Player
   * @param player Player da salvare
   * @return "true" se a buon fine
   */
  public static boolean save(Player player)
  {
    PlayerDAO playerDAO = DAOFactory.getInstance().getPlayerDAO();
    try
    {
      playerDAO.makePersistent(player);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return false;
    }

    return true;
  }

  /**
   * salva un Player
   * @param player Player da salvare
   * @return "true" se a buon fine
   */
  public static boolean save(Player player,int idMatch)
  {
    PlayerDAO playerDAO = DAOFactory.getInstance().getPlayerDAO();
    try
    {
      playerDAO.makePersistent(player);
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
   * salva una lista di Player
   * @param playerList lista di player
   * @return "true" se a buon fine
   */
  public static boolean saveList(List<Player> playerList)
  {
    PlayerDAO playerDAO = DAOFactory.getInstance().getPlayerDAO();
    try
    {
      for (Player player : playerList)
      {
        Player tmpPlayer = playerDAO.get(player.getId());
        if (tmpPlayer == null)
        {
          continue;
        }
        tmpPlayer.setGoals(player.getGoals());
        tmpPlayer.setVote(player.getVote());
        tmpPlayer.setPlayerRole(player.getPlayerRole());
        tmpPlayer.setOwnGoals(player.getOwnGoals());
        tmpPlayer.setReview(player.getReview());
        playerDAO.makePersistent(player);
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
   *
   * @param idMatch id del match in oggetto
   * @return lista di Player che hanno fatto richiesta ad una partita
   */
  public static List getAllRequestUserByIdMatch(int idMatch)
  {

    try
    {
      return DAOFactory.getInstance().getPlayerDAO().getAllRequestUserByIdMatch(idMatch);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return new ArrayList();
    }
  }

  /**
   *
   * @param idMatch id del match in oggetto
   * @return lista di Player che sono stati convocati
   */
  public static List<Integer> getCalledUpIdUser(int idMatch)
  {
    try
    {
      String[] playerStatusesValues={EnumPlayerStatus.UserCalled.getValue()};
      return DAOFactory.getInstance().getPlayerDAO().getIdUserByPlayerStatus(idMatch, playerStatusesValues);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return new ArrayList();
    }
  }
  // </editor-fold>
}
