package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.MatchTypeInfo;
import it.newmedia.gokick.site.infos.PitchCoverInfo;
import it.newmedia.gokick.site.infos.PitchTypeInfo;
import it.newmedia.gokick.site.infos.SportCenterInfo;
import it.newmedia.gokick.site.managers.CityManager;
import it.newmedia.gokick.site.managers.CountryManager;
import it.newmedia.gokick.site.managers.ProvinceManager;
import it.newmedia.gokick.site.managers.SportCenterManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.site.web.datatable.DataTableFactory;
import it.newmedia.gokick.site.web.datatable.SportCenterInfoDataTable;
import it.newmedia.utils.DataValidator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente le azioni per gestire la ricerca di e scelta di un centro sportivo per una partita, oppure di inserirne uno nuovo
 */
public class SportCenterAction extends AuthenticationBaseAction
{
  //<editor-fold defaultstate="collapsed" desc="-- Members --">

  private String filterName;

  private int filterIdCountry = -1;

  private int filterIdProvince = -1;

  private int filterIdCity = -1;

  private int filterIdPitchCover;

  private int filterIdMatchType;

  private int idCountryForInsert = -1;

  private int idProvinceForInsert = -1;

  private int idCityForInsert = -1;

  private int idPitchTypeForInsert = -1;

  private SportCenter sportCenterToInsert;

  private SportCenterInfo sportCenterInfoInserted;

  private int idSportCenterSelected;

  private List<Country> countryList;

  private List<Province> provinceList;

  private List<SportCenterInfo> sportCenterInfolist;

  private String cityInfoName;

  private String provinceInfoName;

  private String dataTableKey;

  private SportCenterInfoDataTable dataTable;

  private int ctrlSearch;



  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public int getFilterIdCity()
  {
    return filterIdCity;
  }

  public void setFilterIdCity(int filterIdCity)
  {
    this.filterIdCity = filterIdCity;
  }

  public int getFilterIdCountry()
  {
    return filterIdCountry;
  }

  public void setFilterIdCountry(int filterIdCountry)
  {
    this.filterIdCountry = filterIdCountry;
  }

  public int getFilterIdPitchCover()
  {
    return filterIdPitchCover;
  }

  public void setFilterIdPitchCover(int filterIdPitchCover)
  {
    this.filterIdPitchCover = filterIdPitchCover;
  }

  public int getFilterIdMatchType()
  {
    return filterIdMatchType;
  }

  public void setFilterIdMatchType(int filterIdMatchType)
  {
    this.filterIdMatchType = filterIdMatchType;
  }

  public int getFilterIdProvince()
  {
    return filterIdProvince;
  }

  public void setFilterIdProvince(int filterIdProvince)
  {
    this.filterIdProvince = filterIdProvince;
  }

  public String getFilterName()
  {
    return filterName;
  }

  public void setFilterName(String filterName)
  {
    this.filterName = filterName;
  }

  public int getIdSportCenterSelected()
  {
    return idSportCenterSelected;
  }

  public void setIdSportCenterSelected(int idSportCenterSelected)
  {
    this.idSportCenterSelected = idSportCenterSelected;
  }

  public SportCenterInfoDataTable getDataTable()
  {
    return dataTable;
  }

  public int getCountConventionedSportCenter()
  {
    return CollectionUtils.countMatches(this.dataTable.getFullResults(), PredicateUtils.invokerPredicate("isConventioned"));
  }

  public int getCountSportCenter()
  {
    return dataTable.getTotalResultsAvailable();
  }

  public List<City> getCityList()
  {
    if (this.filterIdProvince > 0)
    {
      List<City> cityList=CityManager.getAllWithSportCenters(filterIdProvince);
      cityList=CityManager.addCity(cityList, UserContext.getInstance().getUser().getCity());
      return cityList;
    }
    else
    {
      return new ArrayList<City>();
    }
  }

  public List<Province> getProvinceList()
  {
    if (this.filterIdCountry > 0)
    {
      if (this.provinceList == null || this.provinceList.size() == 0)
      {
        this.provinceList = ProvinceManager.getAllWithSportCenters(filterIdCountry);
        this.provinceList=ProvinceManager.addProvince(this.provinceList, UserContext.getInstance().getUser().getProvince());
      }
    }
    else
    {
      this.provinceList = new ArrayList<Province>();
    }
    return provinceList;
  }

  public List<Country> getCountryList()
  {
    //System.out.println("Sono in getCountryList e devo ricaricare? " + ((this.countryList == null || this.countryList.size() == 0) ? "Si" : "No"));
    if (this.countryList == null || this.countryList.size() == 0)
    {
      this.countryList = CountryManager.getAllWithSportCenters();
      this.countryList = CountryManager.addCountry(countryList, UserContext.getInstance().getUser().getCountry());
    }
    return countryList;
  }

  public List<City> getCityListForInsert()
  {
    if (this.idProvinceForInsert > 0)
      return CityManager.getAll(idProvinceForInsert);
    else
      return  new ArrayList<City>();
  }

  public List<Province> getProvinceListForInsert()
  {
    if (this.idCountryForInsert > 0)
    {
      if (this.provinceList == null || this.provinceList.size() == 0)
      {
        this.provinceList = ProvinceManager.getAll(idCountryForInsert);
      }
    }
    else
    {
      this.provinceList = new ArrayList<Province>();
    }
    return provinceList;
  }

  public List<Country> getCountryListForInsert()
  {
    return AppContext.getInstance().getAllCountry();
  }

  public int getIdCityForInsert()
  {
    return idCityForInsert;
  }

  public void setIdCityForInsert(int idCityForInsert)
  {
    this.idCityForInsert = idCityForInsert;
  }

  public int getIdCountryForInsert()
  {
    return idCountryForInsert;
  }

  public void setIdCountryForInsert(int idCountryForInsert)
  {
    this.idCountryForInsert = idCountryForInsert;
  }

  public int getIdProvinceForInsert()
  {
    return idProvinceForInsert;
  }

  public int getIdPitchTypeForInsert()
  {
    return idPitchTypeForInsert;
  }

  public void setIdPitchTypeForInsert(int idPitchTypeForInsert)
  {
    this.idPitchTypeForInsert = idPitchTypeForInsert;
  }

  public SportCenter getSportCenterToInsert()
  {
    return sportCenterToInsert;
  }

  public void setSportCenterToInsert(SportCenter sportCenterToInsert)
  {
    this.sportCenterToInsert = sportCenterToInsert;
  }

  public SportCenterInfo getSportCenterInfoInserted()
  {
    return sportCenterInfoInserted;
  }

  public void setIdProvinceForInsert(int idProvinceForInsert)
  {
    this.idProvinceForInsert = idProvinceForInsert;
  }

  public List<PitchCoverInfo> getPitchCoverList()
  {
    return AppContext.getInstance().getAllPitchCoverInfo(getCurrentObjLanguage(), getCurrentCobrand());
  }

  public List<PitchTypeInfo> getPitchTypeList()
  {
    return AppContext.getInstance().getAllPitchTypeInfo(getCurrentObjLanguage(), getCurrentCobrand());
  }

  public List<MatchTypeInfo> getMatchTypeList()
  {
    return AppContext.getInstance().getAllMatchTypeInfo(getCurrentObjLanguage(), getCurrentCobrand());
  }

  public String getCityInfoName()
  {
    return cityInfoName;
  }

  public String getProvinceInfoName()
  {
    return provinceInfoName;
  }

  public String getDataTableKey()
  {
    return dataTableKey;
  }

  public void setDataTableKey(String dataTableKey)
  {
    this.dataTableKey = dataTableKey;
  }

  public SportCenterInfo getSportCenterSelected()
  {
    return SportCenterManager.getSportCenterInfo(this.idSportCenterSelected);
  }

  public int getCtrlSearch()
  {
    return ctrlSearch;
  }

  public void setCtrlSearch(int ctrlSearch)
  {
    this.ctrlSearch = ctrlSearch;
  }

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  public String choose()
  {
    //System.out.println("Sono in SportCenterAction->choose");
    if (this.getSportCenterSelected().isValid())
    {
      //System.out.println("Posso chiudere... è stato selezionato un centro");
      return SUCCESS;
    }
    User currentUser = UserContext.getInstance().getUser();
    if (this.filterIdCountry == -1)
    {
      this.filterIdCountry = currentUser.getCountry().getId();
      this.filterIdProvince = currentUser.getProvince().getId();
      this.filterIdCity = 0;
    }
    sportCenterInfolist = SportCenterManager.searchSportCenterInfo(filterName,
                                                                          filterIdCountry,
                                                                          filterIdProvince,
                                                                          filterIdCity,
                                                                          filterIdMatchType,
                                                                          filterIdPitchCover);

    if (this.dataTableKey == null || this.dataTableKey.isEmpty())
        this.dataTableKey = DataTableFactory.buildDataTableKey();

    this.dataTable = DataTableFactory.getSportCenterInfoDataTable(this.dataTableKey);
    this.dataTable.loadAndSortFullResults(sportCenterInfolist);

    if (this.filterIdCity != 0)
    {
      City c = CityManager.getCity(this.filterIdCity);
      this.cityInfoName = (c != null) ? c.getName() : "";
      this.provinceInfoName = "";
    }
    else if (this.filterIdProvince != 0)
    {
      Province p = ProvinceManager.getProvince(this.filterIdProvince);
      this.provinceInfoName = (p != null) ? p.getName() : "";
      this.cityInfoName = "";
    }
    this.ctrlSearch=ctrlSearch +1;
    return SUCCESS;
  }

  @Override
  public String input()
  {
    //System.out.println("Sono in SportCenterAction->input");
    this.idCountryForInsert = UserContext.getInstance().getUser().getCountry().getId();
    this.idProvinceForInsert = UserContext.getInstance().getUser().getProvince().getId();
    this.sportCenterInfoInserted = new SportCenterInfo();
    return INPUT;
  }

  public void validateInsert()
  {
    //System.out.println("Sono in SportCenterAction->validateInsert");

    /* Validazione nome */
    if (StringUtils.isEmpty(this.sportCenterToInsert.getName()))
    {
      addFieldError("sportCenterToInsert.name", getText("error.firstName.required"));
    }
    else if ((this.sportCenterToInsert.getName().length() < Constants.STRING_LENGHT_2) || (this.sportCenterToInsert.getName().length() > Constants.STRING_LENGHT_40))
    {
      addFieldError("sportCenterToInsert.name", getText("error.invalidLenght.40"));
    }

    /* Validazione indirizzo */
    if (StringUtils.isEmpty(this.sportCenterToInsert.getAddress()))
    {
      addFieldError("sportCenterToInsert.address", getText("error.address.required"));
    }
    else if ((this.sportCenterToInsert.getAddress().length() < Constants.STRING_LENGHT_2) || (this.sportCenterToInsert.getAddress().length() > Constants.STRING_LENGHT_40))
    {
      addFieldError("sportCenterToInsert.address", getText("error.invalidLenght.40"));
    }

    /* Validazione nazione */
    if (this.idCountryForInsert <= Constants.INVALID_ID)
    {
      addFieldError("idCountryForInsert", getText("error.country.required"));
    }

    /* Validazione provincia */
    if (this.idProvinceForInsert <= Constants.INVALID_ID)
    {
      this.idCityForInsert=0;
      addFieldError("idProvinceForInsert", getText("error.province.required"));
    }

    /* Validazione città */
    if (this.idCityForInsert <= Constants.INVALID_ID)
    {
      addFieldError("idCityForInsert", getText("error.city.required"));
    }

     /* Validazione telefono in ogni caso */
    if (!this.sportCenterToInsert.getTelephone().equals("") && !checkMobileCharacters(this.sportCenterToInsert.getTelephone()))
    {
      addFieldError("sportCenterToInsert.telephone", getText("error.mobile.invalid"));
    }

    /* Validazione tipo di campo */
    if (this.idPitchTypeForInsert <= Constants.INVALID_ID)
    {
      addFieldError("idPitchTypeForInsert", getText("error.pitchType.required"));
    }
    else
    {
      if (this.idPitchTypeForInsert == Constants.PITCH_TYPE_PAY)
      {
        /* Validazione telefono */

        if (StringUtils.isEmpty(this.sportCenterToInsert.getTelephone()))
        {
          addFieldError("sportCenterToInsert.telephone", getText("error.telephone.required"));
        }
        else if ((this.sportCenterToInsert.getTelephone().length() < Constants.STRING_LENGHT_2) || (this.sportCenterToInsert.getTelephone().length() > Constants.STRING_LENGHT_20))
        {
          addFieldError("sportCenterToInsert.telephone", getText("error.invalidLenght.20"));
        }
        

        /* Validazione email */
        if (!StringUtils.isEmpty(this.sportCenterToInsert.getEmail()) && !DataValidator.checkEmail(this.sportCenterToInsert.getEmail()))
        {
          addFieldError("sportCenterToInsert.email", getText("error.email.invalid"));
        }
      }
//      else
//      {
//        this.sportCenterToInsert.setTelephone("");
//        this.sportCenterToInsert.setEmail("");
//        this.sportCenterToInsert.setWebSite("");
//      }
    }
  }

  public String insert()
  {
    //System.out.println("Sono in SportCenterAction->insert");

    String namePitchType = "";
    for (PitchTypeInfo pitchType : getPitchTypeList())
      if (pitchType.getId() == getIdPitchTypeForInsert())
        namePitchType = pitchType.getName();

    boolean success = SportCenterManager.insert(
            this.sportCenterToInsert,
            namePitchType,
            this.idCountryForInsert,
            this.idProvinceForInsert,
            this.idCityForInsert,
            getCurrentIdUser(),
            getCurrentCobrand());
    if (!success)
    {
      return ERROR_POPUP;
    }
    this.sportCenterInfoInserted = InfoProvider.getSportCenterInfo(this.sportCenterToInsert.getId());
    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Private Methods --">
  private boolean checkMobileCharacters(String mobile)
  {
    String character;

    for (int i = 0; i < mobile.length(); i++)
    {
      character = String.valueOf(mobile.charAt(i));
      if (!StringUtils.isNumeric(character) && !character.equalsIgnoreCase("+") && !character.equalsIgnoreCase(" ") )
      {
        return false;
      }
    }

    return true;
  }
  /**
   * @return the sportCenterInfolist
   */
  public List<SportCenterInfo> getSportCenterInfolist()
  {
    return sportCenterInfolist;
  }
  // </editor-fold>
}
