package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiStatisticPeriod;
import it.newmedia.gokick.site.infos.PlayerRoleInfo;
import it.newmedia.gokick.site.infos.StatisticInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.CityManager;
import it.newmedia.gokick.site.managers.CountryManager;
import it.newmedia.gokick.site.managers.ProvinceManager;
import it.newmedia.gokick.site.managers.StatisticInfoManager;
import it.newmedia.gokick.site.web.datatable.ADataTable;
import it.newmedia.gokick.site.web.datatable.DataTableFactory;
import it.newmedia.gokick.site.web.datatable.StatisticInfoDataTable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente le azioni per gestire la visualizzazione e la ricerca parametrica degli utenti.
 */
public class UserAllAction extends AuthenticationBaseAction implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private User currentUser;
  private int searchAreaFlag;
  private String searchArea;
  private List<StatisticInfo> statisticInfoList;
  private List countPlayersRoles;
  private String[] userCheckList;
  private String message;
  private boolean errorInvReq;
  private boolean advanceSearch;
  private String firstName;
  private String lastName;
  private String minAge;
  private String maxAge;
  private int idCountry;
  private int idProvince;
  private int idCity;
  private boolean chkGk;
  private boolean chkDf;
  private boolean chkCc;
  private boolean chkAt;
  private boolean onlyMarketEnabled;
  private List<GuiStatisticPeriod> statisticPeriodList;
  private String statisticPeriod;
  private String dataTableKey;
  private StatisticInfoDataTable dataTable;

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public void prepare()
  {
    this.idCountry = UserContext.getInstance().getUser().getCountry().getId();
    this.idProvince = UserContext.getInstance().getUser().getProvince().getId();
    this.statisticPeriodList = AppContext.getInstance().getStatisticPeriodFilter(getCurrentObjLanguage(), getCurrentCobrand());
    if (this.statisticPeriod == null || this.statisticPeriod.equalsIgnoreCase(Constants.STATISTIC_PERIOD__NONE))
    {
      this.statisticPeriod = Constants.STATISTIC_PERIOD__ALL;
    }
   }

  public String viewAll()
  {
    this.currentUser = UserContext.getInstance().getUser();
    
    this.statisticInfoList = StatisticInfoManager.getBySearchParameters("",
            "",
            null,
            null,
            this.currentUser.getCountry().getId(),
            this.currentUser.getProvince().getId(),
            0,
            null,
            this.onlyMarketEnabled,
            this.statisticPeriod,
            false,
            EnumUserStatus.Enabled.getValue());

    //YUI datatable
    if (this.getDataTableKey() == null || this.getDataTableKey().isEmpty())
    {
    this.setDataTableKey(DataTableFactory.buildDataTableKey());
    }
    this.setDataTable(DataTableFactory.getStatisticInfoDataTable(this.getDataTableKey()));
    this.getDataTable().setSort(StatisticInfoDataTable.PARAM_SORT_COLUMN__REGISTRATION);
    this.getDataTable().setDir(ADataTable.SORT_DIR__DESCENDING);
    this.getDataTable().loadAndSortFullResults(this.statisticInfoList);
    
    this.countPlayersRoles = countPlayersByRoles();

    this.searchAreaFlag = 1;
    this.searchArea = this.currentUser.getProvince().getName();
    this.advanceSearch = false;return SUCCESS;
  }

  public String searchUser()
  {
    this.currentUser = UserContext.getInstance().getUser();

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

    this.statisticInfoList = StatisticInfoManager.getBySearchParameters(this.firstName,
            this.lastName,
            minDate == null ? null : minDate.getTime(),
            maxDate == null ? null : maxDate.getTime(),
            this.idCountry,
            this.idProvince,
            this.idCity,
            roles,
            this.onlyMarketEnabled,
            this.statisticPeriod,
            false,
            EnumUserStatus.Enabled.getValue());

    //YUI datatable
    if (this.getDataTableKey() == null || this.getDataTableKey().isEmpty())
        this.setDataTableKey(DataTableFactory.buildDataTableKey());

    this.setDataTable(DataTableFactory.getStatisticInfoDataTable(this.getDataTableKey()));
    this.getDataTable().setSort(StatisticInfoDataTable.PARAM_SORT_COLUMN__NAME);
    this.getDataTable().setDir(ADataTable.SORT_DIR__ASCENDING);
    this.getDataTable().loadAndSortFullResults(this.statisticInfoList);

    this.countPlayersRoles = countPlayersByRoles();

    if (this.idCity > 0)
    {
      this.searchAreaFlag = 2;
      City city = CityManager.getCity(this.idCity);
      if (city != null)
      {
        this.searchArea = city.getName();
      }
    } else if (this.idProvince > 0)
    {
      this.searchAreaFlag = 1;
      Province province = ProvinceManager.getProvince(this.idProvince);
      if (province != null)
      {
        this.searchArea = province.getName();
      }
    }
    else if (this.idCountry > 0)
    {
      this.searchAreaFlag = 0;
      Country country = CountryManager.getCountry(this.idCountry);
      if (country != null)
      {
        this.searchArea = country.getName();
      }
    }
    else
    {
      this.searchAreaFlag = -1;
    }
    this.advanceSearch = true;

    return SUCCESS;
  }

  private List countPlayersByRoles()
  {
    ArrayList countPlayersByRolesList = new ArrayList();

    List<PlayerRoleInfo> playerRoleInfoList = AppContext.getInstance().getAllPlayerRoleInfo(getCurrentObjLanguage(), getCurrentCobrand());
    if (playerRoleInfoList == null || playerRoleInfoList.size() <= 0)
    {
      return countPlayersByRolesList;
    }

    for (PlayerRoleInfo playerRoleInfo : playerRoleInfoList)
    {
      int count = 0;
      for (int i = 0; i < this.statisticInfoList.size(); i++)
      {
        UserInfo userInfo = this.statisticInfoList.get(i).getUserInfo();
        if (userInfo.getIdPlayerRole()==playerRoleInfo.getId())
        {
          count++;
        }
      }
      countPlayersByRolesList.add(count);
    }
    
    return countPlayersByRolesList;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >

  // <editor-fold defaultstate="collapsed" desc="completamento geoTendine">
  public List<Country> getCountyList()
  {
    List<Country> list = CountryManager.getAllWithUsers();
    list = CountryManager.addCountry(list, UserContext.getInstance().getUser().getCountry());
    return list;
  }

  public List<Province> getProvinceList()
  {
    List<Province> list = ProvinceManager.getAllWithUsers(idCountry);
    if (this.idCountry==UserContext.getInstance().getUser().getCountry().getId())
      list = ProvinceManager.addProvince(list, UserContext.getInstance().getUser().getProvince());
    return list;
  }

  public List<City> getCityList()
  {
    if (this.idProvince > 0)
    {
      List<City> list = CityManager.getAllWithUsers(idProvince);
       if (this.idProvince==UserContext.getInstance().getUser().getProvince().getId())
        //Add always user's country in which plays
        list = CityManager.addCity(list, UserContext.getInstance().getUser().getCity());
      return list;
    }
    else
    {
      return new ArrayList<City>();
    }
  }
  // </editor-fold>

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

  public String getSearchArea()
  {
    return searchArea;
  }

  public void setSearchArea(String searchArea)
  {
    this.searchArea = searchArea;
  }

  public List<StatisticInfo> getStatisticInfoList()
  {
    return statisticInfoList;
  }

  public void setStatisticInfoList(List<StatisticInfo> statisticInfoList)
  {
    this.statisticInfoList = statisticInfoList;
  }

  public List getCountPlayersRoles()
  {
    return countPlayersRoles;
  }

  public void setCountPlayersRoles(List countPlayersRoles)
  {
    this.countPlayersRoles = countPlayersRoles;
  }

  public String[] getUserCheckList()
  {
    return userCheckList;
  }

  public void setUserCheckList(String[] userCheckList)
  {
    this.userCheckList = userCheckList;
  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public boolean isAdvanceSearch()
  {
    return advanceSearch;
  }

  public void setAdvanceSearch(boolean advanceSearch)
  {
    this.advanceSearch = advanceSearch;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
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

  public boolean isOnlyMarketEnabled()
  {
    return onlyMarketEnabled;
  }

  public void setOnlyMarketEnabled(boolean onlyMarketEnabled)
  {
    this.onlyMarketEnabled = onlyMarketEnabled;
  }

  public int getSearchAreaFlag()
  {
    return searchAreaFlag;
  }

  public void setSearchAreaFlag(int searchAreaFlag)
  {
    this.searchAreaFlag = searchAreaFlag;
  }

  public boolean isErrorInvReq()
  {
    return errorInvReq;
  }

  public void setErrorInvReq(boolean errorInvReq)
  {
    this.errorInvReq = errorInvReq;
  }

  public String getStatisticPeriod()
  {
    return statisticPeriod;
  }

  public void setStatisticPeriod(String statisticPeriod)
  {
    this.statisticPeriod = statisticPeriod;
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
   * @return the dataTableKey
   */
  public String getDataTableKey()
  {
    return dataTableKey;
  }

  /**
   * @param dataTableKey the dataTableKey to set
   */
  public void setDataTableKey(String dataTableKey)
  {
    this.dataTableKey = dataTableKey;
  }

  /**
   * @return the dataTable
   */
  public StatisticInfoDataTable getDataTable()
  {
    return dataTable;
  }

  /**
   * @param dataTable the dataTable to set
   */
  public void setDataTable(StatisticInfoDataTable dataTable)
  {
    this.dataTable = dataTable;
  }
  // </editor-fold>
}
