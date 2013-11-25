package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni per gestire la visualizzazione dei dati degli utenti.
 */
public class UserInfoManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserInfoManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private UserInfoManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  /**
   *
   * @param idProvince id provincia
   * @return lista di oggetti UserInfo per gli uteteni ENABLED della provincia indicata,se non ci sono li memorizza in cache
   */
  public static List<UserInfo> getAllByIdProvince(int idProvince)
  {
    List<UserInfo> userInfoList = new ArrayList<UserInfo>();
    try
    {
      List idUserList = DAOFactory.getInstance().getUserDAO().getAllByIdProvince(idProvince);
      for (int i = 0; i < idUserList.size(); i++)
      {
        UserInfo userInfo = InfoProvider.getUserInfo((Integer) idUserList.get(i));
        if (userInfo.isValid())
        {
          userInfoList.add(userInfo);
        }
      }
    } catch (Exception ex)
    {
      logger.error(ex, ex);
      return userInfoList;
    }
    return userInfoList;
  }

  public static List<UserInfo> getUserInfoList(List<Integer> idUserList)
  {
    List<UserInfo> userInfoList = new ArrayList<UserInfo>();
    for (int i = 0; i < idUserList.size(); i++)
    {
        UserInfo userInfo = InfoProvider.getUserInfo((Integer) idUserList.get(i));
        if (userInfo.isValid())
        {
          userInfoList.add(userInfo);
        }
    }
    return userInfoList;
  }

  /**
   *
   * @return lista di tutti oggetti UserInfo per gli uteteni ENABLED 
   */
  public static List<UserInfo> getAll()
  {
    List<UserInfo> userInfoList = new ArrayList<UserInfo>();
    try
    {
      List idUserList = DAOFactory.getInstance().getUserDAO().getAllId();
      for (int i = 0; i < idUserList.size(); i++)
      {
        UserInfo userInfo = InfoProvider.getUserInfo((Integer) idUserList.get(i));
        if (userInfo.isValid())
        {
          userInfoList.add(userInfo);
        }
      }
    } catch (Exception ex)
    {
      logger.error(ex, ex);
      return userInfoList;
    }
    return userInfoList;
  }

  /**
   *
   * @param player Player
   * @return oggetto userInfo per l'utente richiesto,se non c'è lo memorizza in cache
   */
  public static UserInfo getUserInfo(Player player)
  {
    UserInfo userInfo = new UserInfo();
    userInfo.load(player);
    return userInfo;
  }

  // </editor-fold>
}
