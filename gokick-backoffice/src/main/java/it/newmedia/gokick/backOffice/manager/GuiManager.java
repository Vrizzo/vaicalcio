package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.data.hibernate.beans.Statistic;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.backOffice.guibean.GuiSportCenter;
import it.newmedia.gokick.backOffice.guibean.GuiUserBean;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni per gestire la visualizzazione dei dati delle rose.
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

  public static List<GuiSportCenter> buildGuiSportCenterList(List<SportCenter> sportCenterList )
  {
    List<GuiSportCenter> guiSportCenterList = new ArrayList<GuiSportCenter>();


    for(SportCenter sportCenter : sportCenterList)
    {
      GuiSportCenter guiSportCenter=new GuiSportCenter();
      guiSportCenter.load(sportCenter);
      guiSportCenterList.add(guiSportCenter);
    }
    return guiSportCenterList;
  }

  public static GuiSportCenter buildGuiSportCenter(SportCenter sportCenter )
  {
      GuiSportCenter guiSportCenter=new GuiSportCenter();
      guiSportCenter.load(sportCenter);
      return guiSportCenter;
  }

  public static List<GuiUserBean> buildGuiUserList(String statisticPeriod)
  {
    List<GuiUserBean> guiUserBeanList = new ArrayList<GuiUserBean>();

    List<User> userList = UserManager.getAllWithPermission();

    for(User user : userList)
    {
      GuiUserBean guiUserBean= buildGuiUser(user, statisticPeriod);
      guiUserBeanList.add(guiUserBean);
    }
    return guiUserBeanList;
  }

  public static List<GuiUserBean> buildGuiUserList(List<User> userList ,String statisticPeriod)
  {
    List<GuiUserBean> guiUserBeanList = new ArrayList<GuiUserBean>();

    for(User user : userList)
    {
      GuiUserBean guiUserBean= buildGuiUser(user, statisticPeriod);
      guiUserBeanList.add(guiUserBean);
    }
    return guiUserBeanList;
  }

  public static GuiUserBean buildGuiUser(User user, String statisticPeriod)
  {

      GuiUserBean guiUserBean= new GuiUserBean();
      List<Statistic> statisticList = new ArrayList<Statistic>();
      if (!statisticPeriod.equals(Constants.STATISTIC_PERIOD__NONE))
      {
        statisticList = StatisticManager.getStatistic(user.getId(), statisticPeriod);
        guiUserBean.load(user,statisticList);
      }
      else
        guiUserBean.load(user);
      
    return guiUserBean;
  }

  public static List<GuiUserBean> getBySearchParameters(String firstName,
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
          String statisticPeriod,
          String PermissionToSearch)
  {
    List<GuiUserBean> guiUserBeanList = new ArrayList<GuiUserBean>();
    try
    {
      List<User> userList = DAOFactory.getInstance().getUserDAO().getUserBySearchParameters(
              firstName,
              lastName,
              minBirthDate,
              maxBirthDate,
              idCountry,
              idProvince,
              idCity,
              roles,
              marketEnabled,
              searchNewsLetterEnabled,
              status,PermissionToSearch);
      for (User user : userList)
      {
        GuiUserBean guiUserBean= new GuiUserBean();
        List<Statistic> statisticList = StatisticManager.getStatistic(user.getId(), statisticPeriod);
        guiUserBean.load(user,statisticList);
        guiUserBeanList.add(guiUserBean);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return guiUserBeanList;
    }
    return guiUserBeanList;
  }

  public static List<GuiUserBean> loadGuiUserList(List<User> userList,String statisticPeriod)
  {
    List<GuiUserBean> guiUserBeanList = new ArrayList<GuiUserBean>();
    try
    {
      List<Statistic> statisticList = new ArrayList<Statistic>();


      for (User user : userList)
      {
        GuiUserBean guiUserBean= new GuiUserBean();
        if (!statisticPeriod.equals(Constants.STATISTIC_PERIOD__NONE))
        {
          statisticList = StatisticManager.getStatistic(user.getId(), statisticPeriod);
          guiUserBean.load(user,statisticList);
        }
        else
          guiUserBean.load(user);

        guiUserBeanList.add(guiUserBean);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return guiUserBeanList;
    }
    return guiUserBeanList;
  }


 
// </editor-fold>
}
