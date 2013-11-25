package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.MatchTypeInfo;
import it.newmedia.gokick.site.infos.PitchCoverInfo;
import it.newmedia.gokick.site.infos.SportCenterInfo;
import it.newmedia.gokick.site.managers.CityManager;
import it.newmedia.gokick.site.managers.CountryManager;
import it.newmedia.gokick.site.managers.ProvinceManager;
import it.newmedia.gokick.site.managers.SportCenterManager;
import it.newmedia.gokick.site.web.datatable.DataTableFactory;
import it.newmedia.gokick.site.web.datatable.SportCenterInfoDataTable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;

/**
 *
 * Classe contenente le azioni per gestire la visualizzazione e ricerca specifica di centri sportivi
 */
public class SportCenterAllAction extends AuthenticationBaseAction implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int searchAreaFlag;
  private String searchArea;
  private List<SportCenterInfo> sportCenterInfoList;
  private int conventionedSportCenterCount;
  private boolean advanceSearch;
  private String name;
  private int idCountry;
  private int idProvince;
  private int idCity;
  private int idMatchType;
  private int idPitchCover;

  private String dataTableKey;
  private SportCenterInfoDataTable dataTable;
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public void prepare()
  {
    this.idCountry = UserContext.getInstance().getUser().getCountry().getId();
    this.idProvince = UserContext.getInstance().getUser().getProvince().getId();
  }

  public String viewAll()
  {
    this.sportCenterInfoList = SportCenterManager.searchSportCenterInfo("",
            UserContext.getInstance().getUser().getCountry().getId(),
            UserContext.getInstance().getUser().getProvince().getId(),
            -1,
            -1,
            -1);
    //YUI datatable
    if (this.dataTableKey == null || this.dataTableKey.isEmpty())
        this.dataTableKey = DataTableFactory.buildDataTableKey();

    this.dataTable = DataTableFactory.getSportCenterInfoDataTable(this.dataTableKey);
    this.dataTable.loadAndSortFullResults(this.sportCenterInfoList);



    this.sportCenterInfoList = orderSportCenterInfoList();

    this.conventionedSportCenterCount = CollectionUtils.countMatches(this.sportCenterInfoList, PredicateUtils.invokerPredicate("isConventioned"));

    this.searchAreaFlag = 1;
    this.searchArea = UserContext.getInstance().getUser().getProvince().getName();
    this.advanceSearch = false;

    return SUCCESS;
  }

  public String searchSportCenter()
  {
    this.sportCenterInfoList = SportCenterManager.searchSportCenterInfo(this.name,
            this.idCountry,
            this.idProvince,
            this.idCity,
            this.idMatchType,
            this.idPitchCover);
    //YUI datatable
    if (this.dataTableKey == null || this.dataTableKey.isEmpty())
        this.dataTableKey = DataTableFactory.buildDataTableKey();

    this.dataTable = DataTableFactory.getSportCenterInfoDataTable(this.dataTableKey);
    this.dataTable.loadAndSortFullResults(this.sportCenterInfoList);


    this.sportCenterInfoList = orderSportCenterInfoList();

    this.conventionedSportCenterCount = CollectionUtils.countMatches(this.sportCenterInfoList, PredicateUtils.invokerPredicate("isConventioned"));

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
    } else if (this.idCountry > 0)
    {
      this.searchAreaFlag = 0;
      Country country = CountryManager.getCountry(this.idCountry);
      if (country != null)
      {
        this.searchArea = country.getName();
      }
    } else
    {
      this.searchAreaFlag = -1;
    }

    this.advanceSearch = true;

    return SUCCESS;
  }

  private List<SportCenterInfo> orderSportCenterInfoList()
  {
    List<SportCenterInfo> tmpList = new ArrayList<SportCenterInfo>();
    for (SportCenterInfo sportCenterInfo : this.sportCenterInfoList)
    {
      if (sportCenterInfo.isConventioned())
      {
        tmpList.add(sportCenterInfo);
      }
    }
    for (SportCenterInfo sportCenterInfo : this.sportCenterInfoList)
    {
      if (!sportCenterInfo.isConventioned())
      {
        tmpList.add(sportCenterInfo);
      }
    }
    return tmpList;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public List<Country> getCountyList()
  {
    List<Country> list = CountryManager.getAllWithSportCenters();
    //Add always user's country in which plays
    list = CountryManager.addCountry(list, UserContext.getInstance().getUser().getCountry());
    return list;
  }

  public List<Province> getProvinceList()
  {
    List<Province> list = ProvinceManager.getAllWithSportCenters(idCountry);
    if (this.idCountry==UserContext.getInstance().getUser().getCountry().getId())
      //Add always user's country in which plays
      list = ProvinceManager.addProvince(list, UserContext.getInstance().getUser().getProvince());
    return list;
  }

  public List<City> getCityList()
  {
    List<City> list = CityManager.getAllWithSportCenters(idProvince);
    if (this.idProvince==UserContext.getInstance().getUser().getProvince().getId())
    //Add always user's country in which plays
      list = CityManager.addCity(list, UserContext.getInstance().getUser().getCity());
    return list;
  }

  public List<MatchTypeInfo> getMatchTypeList()
  {
    return AppContext.getInstance().getAllMatchTypeInfo(getCurrentObjLanguage(), getCurrentCobrand());
  }

  public List<PitchCoverInfo> getPitchCoverList()
  {
    return AppContext.getInstance().getAllPitchCoverInfo(getCurrentObjLanguage(), getCurrentCobrand());
  }

  public int getConventionedSportCenterCount()
  {
    return conventionedSportCenterCount;
  }

  public void setConventionedSportCenterCount(int conventionedSportCenterCount)
  {
    this.conventionedSportCenterCount = conventionedSportCenterCount;
  }

  public boolean isAdvanceSearch()
  {
    return advanceSearch;
  }

  public void setAdvanceSearch(boolean advanceSearch)
  {
    this.advanceSearch = advanceSearch;
  }

  public int getIdCity()
  {
    return idCity;
  }

  public void setIdCity(int idCity)
  {
    this.idCity = idCity;
  }

  public int getIdCountry()
  {
    return idCountry;
  }

  public void setIdCountry(int idCountry)
  {
    this.idCountry = idCountry;
  }

  public int getIdPitchCover()
  {
    return idPitchCover;
  }

  public void setIdPitchCover(int idPitchCover)
  {
    this.idPitchCover = idPitchCover;
  }

  public int getIdMatchType()
  {
    return idMatchType;
  }

  public void setIdMatchType(int idMatchType)
  {
    this.idMatchType = idMatchType;
  }

  public int getIdProvince()
  {
    return idProvince;
  }

  public void setIdProvince(int idProvince)
  {
    this.idProvince = idProvince;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getSearchArea()
  {
    return searchArea;
  }

  public void setSearchArea(String searchArea)
  {
    this.searchArea = searchArea;
  }

  public int getSearchAreaFlag()
  {
    return searchAreaFlag;
  }

  public void setSearchAreaFlag(int searchAreaFlag)
  {
    this.searchAreaFlag = searchAreaFlag;
  }

  public List<SportCenterInfo> getSportCenterInfoList()
  {
    return sportCenterInfoList;
  }

  public void setSportCenterInfoList(List<SportCenterInfo> sportCenterInfoList)
  {
    this.sportCenterInfoList = sportCenterInfoList;
  }
  public SportCenterInfoDataTable getDataTable()
  {
    return dataTable;
  }

  public void setDataTable(SportCenterInfoDataTable dataTable)
  {
    this.dataTable = dataTable;
  }

  public String getDataTableKey()
  {
    return dataTableKey;
  }

  public void setDataTableKey(String dataTableKey)
  {
    this.dataTableKey = dataTableKey;
  }
  
  // </editor-fold>
}
