package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import it.newmedia.gokick.backOffice.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.data.hibernate.beans.UsersPermission;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;


/**
 *
 * Classe manager che gestisce ad alto livello le azioni fatte dall'utente.
 */
public class UserManager {


  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private UserManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  public static User loadUserFromDb(int id)
  {
    try
    {
      User userToreload = DAOFactory.getInstance().getUserDAO().get(id);
      return userToreload;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static User loadUserNoCache(int id)
  {
    try
    {
      User userToreload = DAOFactory.getInstance().getUserDAO().findById(id, true);
      return userToreload;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static int  getCountAll(int idCountryFilter)
  {
    int userAll=0;
    try
    {
      userAll = DAOFactory.getInstance().getUserDAO().getAllCount(idCountryFilter);
      return userAll;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return userAll;
    }
  }

  public static User getByEmail(String email)
  {
    try
    {
      return DAOFactory.getInstance().getUserDAO().getByEmail(email);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static List<User> getAll()
  {
    try
    {
      List<User> userList = DAOFactory.getInstance().getUserDAO().findAll();
      return userList;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  /**
   *
   * @return all users ordered by lastLogin Desc
   */
  public static List<User> getAllOrderedByLastLog()
  {
    try
    {
      List<User> userList = DAOFactory.getInstance().getUserDAO().findOrderByLastLogin();
      return userList;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  /**
   *
   * @return all users ordered by lastLogin Desc
   */
  public static List<User> getAllWithPermission()
  {
    return getAllWithPermission(0);
  }
  
  public static List<User> getAllWithPermission(int idCountryFilter)
  {
    try
    {
      List<User> userList = DAOFactory.getInstance().getUserDAO().getAllWithPermissions(idCountryFilter);
      return userList;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static List<User> getUserBySearchParameters(String firstName,
                                                     String lastName,
                                                     Date minBirthDate,
                                                     Date maxBirthDate,
                                                     int idCountry,
                                                     int idProvince,
                                                     int idCity,
                                                     List<Integer> roles,
                                                     boolean marketEnabled,
                                                     boolean searchNewsLetterEnabled,
                                                     String status,
                                                     String permission)
  {
    try
    {
      List<User> userList = DAOFactory.getInstance().getUserDAO().getUserBySearchParameters(
            firstName,
            lastName,
            minBirthDate ,
            maxBirthDate,
            idCountry,
            idProvince,
            idCity,
            roles,
            marketEnabled,
            searchNewsLetterEnabled,
            status,
            permission);

      return userList;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static PictureCard getLastPictureCard(int idUser)
  {
    try
    {
      return DAOFactory.getInstance().getPictureCardDAO().getLastPic(idUser);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static int getInviteUsed(int idUser)
  {
    try
    {
      return DAOFactory.getInstance().getUserInvitationDAO().getCountUsed(idUser);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
  }

  public static boolean update(User userModified,List<UsersPermission> usersPermissionListModified)
  {
    HibernateSessionHolder.beginTransaction();
    try
    {

      int idUser=userModified.getId();
      User usertoUpdate = DAOFactory.getInstance().getUserDAO().get(idUser);

      usertoUpdate.setUserStatus(userModified.getUserStatus());
      usertoUpdate.setOrganizeEnabled(userModified.getOrganizeEnabled());
      usertoUpdate.setMaxInvitations(userModified.getMaxInvitations());

      DAOFactory.getInstance().getUserDAO().makePersistent(usertoUpdate);

      List<UsersPermission> usersPermissionsList = DAOFactory.getInstance().getUsersPermissionDAO().get(idUser);
      if (usersPermissionsList.size()>0)
        DAOFactory.getInstance().getUsersPermissionDAO().makeTransient(usersPermissionsList);
      if (usersPermissionListModified!=null)
        DAOFactory.getInstance().getUsersPermissionDAO().makePersistent(usersPermissionListModified);
     
      HibernateSessionHolder.commitTransaction();

    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return false;
    }
    return true;
  }

  public static List<User> getByIdUserList(List<Integer> idUserList)
  {
    try
    {
      return  DAOFactory.getInstance().getUserDAO().getByIdUserList(idUserList);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }


 
  // </editor-fold>
}
