package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.cache.InfoCache;
import it.newmedia.gokick.data.enums.EnumMessage;
import it.newmedia.gokick.data.enums.EnumUserSquadStatus;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.data.hibernate.beans.UserSquad;
import it.newmedia.gokick.data.hibernate.dao.SquadDAO;
import it.newmedia.gokick.data.hibernate.dao.UserSquadDAO;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import it.newmedia.gokick.backOffice.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.results.Result;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni fatte sulle rose.
 */
public class SquadManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(SquadManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private SquadManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
//  public static int countFriends(int idUser)
//  {
//    int idSquad=0;
//    int friendsCount=0;
//    try
//    {
//      idSquad= DAOFactory.getInstance().getSquadDAO().getByIdUser(idUser);
//      friendsCount = DAOFactory.getInstance().getUserSquadDAO().getConfirmedUserByIdSquad(idSquad);    // getAllConfirmedUserByIdSquad(idSquad).size();
//    }
//    catch (Exception ex)
//    {
//      logger.error(ex, ex);
//      return friendsCount;
//    }
//    return friendsCount;
//  }

  public static int countFriends(int idSquad)
  {

    int friendsCount=0;
    try
    {
      friendsCount = DAOFactory.getInstance().getUserSquadDAO().getConfirmedUserByIdSquad(idSquad);    // getAllConfirmedUserByIdSquad(idSquad).size();
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return friendsCount;
    }
    return friendsCount;
  }

  public static boolean isFriendOf(int idOwnerUser, int idCurrentUser)
  {
    try
    {
      //TO-DO fare una sola query
      int idSquad = getUserFirstSquad(idOwnerUser).getId();     //tiro fuori idSquadDelproprietario
      int idUserSquad = DAOFactory.getInstance().getUserSquadDAO().getByIdSquadAndIdUserSquad(idSquad, idCurrentUser); //controllo se c'è associazione
      if (idUserSquad > 0)
      {
        return true;
      }
    }
    catch (Exception ex)
    {
      //logger.debug("error in SquadManager.isFriendOf");
      return false;
    }
    return false;
  }

  public static String getUserRelationship(int idCurrentUser, int idUserToRelate, int idSquadCurrentUser, int idSquadUserToRelate)
  {
    try
    {
      UserSquad userSquad = DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(idUserToRelate, idSquadCurrentUser);
      if (userSquad != null)
      {
        return userSquad.getEnumUserSquadStatus().getValue();
      }
      else
      {
        userSquad = DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(idCurrentUser, idSquadUserToRelate);
        if (userSquad != null)
        {
          return userSquad.getEnumUserSquadStatus().getValue();
        }
      }
    }
    catch (Exception ex)
    {
      return "";
    }
    return "";
  }

  public static Squad getUserFirstSquad(int idUser)
  {
    Squad squad;

    try
    {
      squad = DAOFactory.getInstance().getSquadDAO().getFirstByIdUser(idUser);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }

    return squad;
  }

  public static List getAllConfirmedUserByIdSquad(int idSquad)
  {
    List friendsIdList = new ArrayList();
    try
    {
      friendsIdList = DAOFactory.getInstance().getUserSquadDAO().getAllConfirmedUserByIdSquad(idSquad);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return friendsIdList;
    }
    return friendsIdList;
  }
  // </editor-fold>
}
