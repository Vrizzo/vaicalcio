package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiMatchInfo;
import it.newmedia.gokick.site.guibean.GuiPlayerInfo;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni relative alla visualizzazione dei dati delle rose.
 */
public class GuiManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(GuiManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >

  private GuiManager()
  {
  }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  /**
   * setta un oggetto GuiMatch
   * @param idMatch match in oggetto
   * @return un oggetto GuiMatch
   */
  public static GuiMatchInfo getGuiMatchInfo(int idMatch)
  {
    GuiMatchInfo guiMatchInfo = new GuiMatchInfo();

    MatchInfo matchInfo = MatchInfoManager.getMatchInfo(idMatch);

    User currentUser = UserContext.getInstance().getUser();

    UserInfo ownerUserInfo = InfoProvider.getUserInfo(matchInfo.getIdUserOwner());
    guiMatchInfo.setOwnerUserInfo(ownerUserInfo);

    if (matchInfo.getIdUserOwner() == currentUser.getId())
    {
      guiMatchInfo.setOwnerUser(true);
    }
    
    guiMatchInfo.loadMatchInfo(matchInfo);

    return guiMatchInfo;
  }

  /**
   *
   * @param idMatch match in oggetto
   * @return numero degli utanti registrati al match in oggetto
   */
  public static int countRegisteredByMatch(int idMatch)
  {
    return MatchManager.countMatchPlayers(idMatch,false, EnumPlayerStatus.OwnerRegistered, EnumPlayerStatus.UserRegistered, EnumPlayerStatus.UserRequestRegistered, EnumPlayerStatus.Out);
  }

  /**
   *
   * @param idMatch match in oggetto
   * @return numero dei commenti al match in oggetto
   */
  public static int countCommentsByMatch(int idMatch)
  {
    return MatchCommentManager.countMatchComments(idMatch);
  }

  /**
   *
   * @param idMatch match in oggetto
   * @param withMissing true:con user in stato "Missing"
   * @return una lista di oggetti GuiPlayerInfo degli utenti registrati
   */
  public static List<GuiPlayerInfo> getGuiPlayersInfoListRegisteredByMatch(int idMatch,boolean withMissing)
  {

    MatchInfo matchInfo = MatchInfoManager.getMatchInfo(idMatch);

    List<Player> playersRegisteredList = MatchManager.getMatchPlayerList(
            idMatch,
            withMissing,
            EnumPlayerStatus.OwnerRegistered,
            EnumPlayerStatus.UserRegistered,
            EnumPlayerStatus.UserRequestRegistered,
            EnumPlayerStatus.Out);

    return buildGuiPlayerInfoList(matchInfo, playersRegisteredList);

  }

  /**
   *
   * @param idMatch match in oggetto
   * @param withMissing true:con user in stato "Missing"
   * @return una lista di oggetti GuiPlayerInfo degli utenti registrati non outers
   */
  public static List<GuiPlayerInfo> getGuiPlayersInfoListUsersRegisteredByMatch(int idMatch,boolean withMissing)
  {

    MatchInfo matchInfo = MatchInfoManager.getMatchInfo(idMatch);

    List<Player> playersRegisteredList = MatchManager.getMatchPlayerList(
            idMatch,
            withMissing,
            EnumPlayerStatus.OwnerRegistered,
            EnumPlayerStatus.UserRegistered,
            EnumPlayerStatus.UserRequestRegistered);

    return buildGuiPlayerInfoList(matchInfo, playersRegisteredList);

  }

  /**
   *
   * @param idMatch match in oggetto
   * @return una lista di oggetti GuiPlayerInfo degli utenti che han fatto richiesta di registrazione al match
   */
  public static List<GuiPlayerInfo> getGuiPlayersInfoListRequestByMatch(int idMatch)
  {
    MatchInfo matchInfo = MatchInfoManager.getMatchInfo(idMatch);

    List<Player> playersRequestList = MatchManager.getMatchPlayerList(idMatch,false, EnumPlayerStatus.UserRequest);

    return buildGuiPlayerInfoList(matchInfo, playersRequestList);
  }

  /**
   *
   * @param matchInfo oggetto matchInfo del match relativo
   * @param playerList lista di user (player in realtà) registrati
   * @return una lista di oggetti GuiPlayerInfo 
   */
  public static List<GuiPlayerInfo> buildGuiPlayerInfoList(MatchInfo matchInfo, List<Player> playerList)
  {
    List<GuiPlayerInfo> guiPlayerInfoRegisteredList = new ArrayList<GuiPlayerInfo>();

    GuiPlayerInfo guiPlayerInfo;
    for (Player player : playerList)
    {
      UserInfo userInfo = null;
      if (player.getUser() != null)
      {
        userInfo = InfoProvider.getUserInfo(player.getUser().getId());
      }
      else
      {
        userInfo = UserInfoManager.getUserInfo(player);
      }
      guiPlayerInfo = new GuiPlayerInfo();
      guiPlayerInfo.load(player, userInfo, matchInfo);
      guiPlayerInfoRegisteredList.add(guiPlayerInfo);
    }

    return guiPlayerInfoRegisteredList;
  }

  /**
   *
   * @param matchInfo oggeetto matchInfo del match relativo
   * @param idUserSquadList lista degli id utenti amici dell'organizzatore del match
   * @return lista di oggetti guiPlayerInfo
   */
  public static List<GuiPlayerInfo> prepareGuiPlayerFriendsInfoList(MatchInfo matchInfo, List<Integer> idUserSquadList)
  {
    List<GuiPlayerInfo> guiPlayerInfoList = new ArrayList<GuiPlayerInfo>();
    GuiPlayerInfo guiPlayerInfo;

    Player player;
    for (int idUserSquad : idUserSquadList)
    {
      UserInfo userInfo = InfoProvider.getUserInfo(idUserSquad);
      player = MatchManager.getPlayer(matchInfo.getId(), userInfo.getId());

      guiPlayerInfo = new GuiPlayerInfo();
      guiPlayerInfo.load(player, userInfo, matchInfo);
      guiPlayerInfoList.add(guiPlayerInfo);
    }

    return guiPlayerInfoList;
  }

// </editor-fold>
}
