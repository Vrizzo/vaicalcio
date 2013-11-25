package it.newmedia.gokick.backOffice.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.backOffice.AppContext;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.backOffice.UserContext;
import it.newmedia.gokick.backOffice.guibean.GuiStatisticPeriod;
import it.newmedia.gokick.backOffice.guibean.GuiUserBean;
import it.newmedia.gokick.backOffice.manager.CityManager;
import it.newmedia.gokick.backOffice.manager.CountryManager;
import it.newmedia.gokick.backOffice.manager.GuiManager;
import it.newmedia.gokick.backOffice.manager.ProvinceManager;
import it.newmedia.gokick.backOffice.manager.UserManager;
import it.newmedia.gokick.data.hibernate.beans.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente le azioni per gestire la ricerca di utenti 
 */
public class AdminUsersAction extends  ABaseActionSupport  implements Preparable
{

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private List<GuiUserBean> guiUserList;
  private List<Integer> userCheckList;
  private String statisticPeriod;
  private String minAge;
  private String maxAge;
  private boolean chkGk;
  private boolean chkDf;
  private boolean chkCc;
  private boolean chkAt;
  private String firstName;
  private String lastName;
  private int idCountry;
  private int idProvince;
  private int idCity;
  private boolean onlyMarketEnabled;
  private boolean advanceSearch;
  private List<GuiStatisticPeriod> statisticPeriodList;
  private int userTot;
  private List<String> enumUserStatusList;
  private String userStatus;
  private String permissionToSearch;
  private boolean chkNewsletter;
  private boolean sendToAll;
  private String receiverType;

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">

  @Override
  public void prepare()
  {
    if(UserContext.getInstance().getIdCountryFilter()>0)
    {
      this.idCountry=UserContext.getInstance().getIdCountryFilter();
    }
    this.enumUserStatusList= new ArrayList<String>();
    this.enumUserStatusList.add(EnumUserStatus.Enabled.getValue());
    this.enumUserStatusList.add(EnumUserStatus.Pending.getValue());
    this.enumUserStatusList.add(EnumUserStatus.Cancelled.getValue());
    this.enumUserStatusList.add(EnumUserStatus.Deleted.getValue());
    
    this.statisticPeriodList = AppContext.getInstance().getStatisticPeriodFilter();
    if (this.statisticPeriod == null )//|| this.statisticPeriod.equalsIgnoreCase(Constants.STATISTIC_PERIOD__NONE))
    {
      this.statisticPeriod = Constants.STATISTIC_PERIOD__NONE;
    }
    this.userTot=UserManager.getCountAll(idCountry);
  }

  public String viewAll()
  {
    this.advanceSearch = false;
    List<User> userList = UserManager.getAllWithPermission(UserContext.getInstance().getIdCountryFilter());
    UserContext.getInstance().setUserReceiverList(userList);
    this.guiUserList=GuiManager.buildGuiUserList(userList,this.statisticPeriod);

    return SUCCESS;
  }

  public String searchUsers()
  {
    
    this.advanceSearch = true;
    Calendar minDate = null;
    Calendar maxDate = null;
    boolean error = false;

    if (this.minAge != null && this.minAge.length() > 0)
    {
      if (!StringUtils.isNumeric(this.minAge) || Integer.valueOf(this.minAge) <= 0)
      {
        this.minAge = "";
        error = true;
      } else
      {
        minDate = Calendar.getInstance();
        minDate.add(Calendar.YEAR, -Integer.valueOf(this.minAge));
      }
    }
    if (this.maxAge != null && this.maxAge.length() > 0)
    {
      if (!StringUtils.isNumeric(this.maxAge) || Integer.valueOf(this.maxAge) <= 0)
      {
        this.maxAge = "";
        error = true;
      }
      else
      {
        maxDate = Calendar.getInstance();
        maxDate.add(Calendar.YEAR, -Integer.valueOf(this.maxAge) - 1);
      }
    }
    if (error)
    {
      addFieldError("ageInvalid", getText("error.invalidAge"));
    }

    List<Integer> roles = new ArrayList<Integer>();
    if (this.chkGk)
    {
      roles.add(Constants.PLAYER_ROLE_ID__GK);
    }
    if (this.chkDf)
    {
      roles.add(Constants.PLAYER_ROLE_ID__DF);
    }
    if (this.chkCc)
    {
      roles.add(Constants.PLAYER_ROLE_ID__CC);
    }
    if (this.chkAt)
    {
      roles.add(Constants.PLAYER_ROLE_ID__AT);
    }

    List<User> userList =UserManager.getUserBySearchParameters(
            this.getFirstName(),
            this.getLastName(),
            minDate == null ? null : minDate.getTime(),
            maxDate == null ? null : maxDate.getTime(),
            UserContext.getInstance().getIdCountryFilter()>0?UserContext.getInstance().getIdCountryFilter():this.idCountry,
            this.idProvince,
            this.idCity,
            roles,
            this.onlyMarketEnabled,
            this.chkNewsletter,
            this.userStatus,
            this.permissionToSearch);

    UserContext.getInstance().setUserReceiverList(userList);
    this.guiUserList = GuiManager.loadGuiUserList(userList,this.statisticPeriod);

    return SUCCESS;
  }

  public String loadReceiverList()
  {
    if (!this.sendToAll)
    {
        UserContext.getInstance().setUserReceiverList(UserManager.getByIdUserList(userCheckList));
    }
    this.receiverType="gokickers";
    return Constants.STRUTS_RESULT_NAME__SENDMESSAGES;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  /**
   * @return the guiUserList
   */
  public List<GuiUserBean> getGuiUserList()
  {
    return guiUserList;
  }

  public String getStatisticPeriod()
  {
    return statisticPeriod;
  }

  public void setStatisticPeriod(String statisticPeriod)
  {
    this.statisticPeriod = statisticPeriod;
  }

  public String getMinAge()
  {
    return minAge;
  }

  public void setMinAge(String minAge)
  {
    this.minAge = minAge;
  }

  public String getMaxAge()
  {
    return maxAge;
  }

  public void setMaxAge(String maxAge)
  {
    this.maxAge = maxAge;
  }

  public boolean isChkAt()
  {
    return chkAt;
  }

  public void setChkAt(boolean chkAt)
  {
    this.chkAt = chkAt;
  }

  public boolean isChkDf()
  {
    return chkDf;
  }

  public void setChkDf(boolean chkDf)
  {
    this.chkDf = chkDf;
  }

  public boolean isChkGk()
  {
    return chkGk;
  }

  public void setChkGk(boolean chkGk)
  {
    this.chkGk = chkGk;
  }

  public boolean isChkCc()
  {
    return chkCc;
  }

  public void setChkCc(boolean chkCc)
  {
    this.chkCc = chkCc;
  }

  /**
   * @return the firstName
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public Integer getIdCountry()
  {
    return idCountry;
  }

  public void setIdCountry(Integer idCountry)
  {
    this.idCountry = idCountry;
  }

  public Integer getIdProvince()
  {
    return idProvince;
  }

  public void setIdProvince(Integer idProvince)
  {
    this.idProvince = idProvince;
  }

  public Integer getIdCity()
  {
    return idCity;
  }

  public void setIdCity(Integer idCity)
  {
    this.idCity = idCity;
  }

  public boolean isOnlyMarketEnabled()
  {
    return onlyMarketEnabled;
  }

  public void setOnlyMarketEnabled(boolean onlyMarketEnabled)
  {
    this.onlyMarketEnabled = onlyMarketEnabled;
  }

  public boolean isAdvanceSearch()
  {
    return advanceSearch;
  }

  public void setAdvanceSearch(boolean advanceSearch)
  {
    this.advanceSearch = advanceSearch;
  }

  public List<Country> getCountyList()
  {
    List<Country> countryList = new ArrayList<Country>();
    if(UserContext.getInstance().getIdCountryFilter()>0)
    {
      countryList.add(CountryManager.getCountry(UserContext.getInstance().getIdCountryFilter()));
    }
    else
    {
      countryList =  CountryManager.getAllWithUsers();
    }
    return countryList;
  }
    
  public List<Province> getProvinceList()
  {
    if (this.idProvince > 0 || UserContext.getInstance().getIdCountryFilter()>0)
    {
      return ProvinceManager.getAllWithUsers(idCountry);
    }
    else
    {
      return new ArrayList<Province>();
    }
  }
  
  public List<City> getCityList()
  {
    if (this.idProvince > 0)
    {
      return CityManager.getAllWithUsers(idProvince);
    }
    else
    {
      return new ArrayList<City>();
    }
  }

  public List<GuiStatisticPeriod> getStatisticPeriodList()
  {
    return statisticPeriodList;
  }

  public void setStatisticPeriodList(List<GuiStatisticPeriod> statisticPeriodList)
  {
    this.statisticPeriodList = statisticPeriodList;
  }

  /**
   * @return the userTot
   */
  public int getUserTot()
  {
    return userTot;
  }

  /**
   * @return the enumUserStatusList
   */
  public List<String> getEnumUserStatusList()
  {
    return enumUserStatusList;
  }

  /**
   * @return the userStatus
   */
  public String getUserStatus()
  {
    return userStatus;
  }

  /**
   * @param userStatus the userStatus to set
   */
  public void setUserStatus(String userStatus)
  {
    this.userStatus = userStatus;
  }

  /**
   * @return the chkNewsletter
   */
  public boolean isChkNewsletter()
  {
    return chkNewsletter;
  }

  /**
   * @param chkNewsletter the chkNewsletter to set
   */
  public void setChkNewsletter(boolean chkNewsletter)
  {
    this.chkNewsletter = chkNewsletter;
  }

  /**
   * @return the permissionToSearch
   */
  public String getPermissionToSearch()
  {
    return permissionToSearch;
  }

  /**
   * @param permissionToSearch the permissionToSearch to set
   */
  public void setPermissionToSearch(String permissionToSearch)
  {
    this.permissionToSearch = permissionToSearch;
  }

  /**
   * @return the sendToAll
   */
  public boolean isSendToAll()
  {
    return sendToAll;
  }

  /**
   * @param sendToAll the sendToAll to set
   */
  public void setSendToAll(boolean sendToAll)
  {
    this.sendToAll = sendToAll;
  }

  /**
   * @return the receiverType
   */
  public String getReceiverType()
  {
    return receiverType;
  }
  
  /**
   * @return the userCheckList
   */
  public List<Integer> getUserCheckList()
  {
    return userCheckList;
  }

  /**
   * @param userCheckList the userCheckList to set
   */
  public void setUserCheckList(List<Integer> userCheckList)
  {
    this.userCheckList = userCheckList;
  }

  

  

  // </editor-fold>

}
