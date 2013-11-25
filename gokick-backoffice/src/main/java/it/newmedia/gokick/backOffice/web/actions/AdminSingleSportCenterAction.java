package it.newmedia.gokick.backOffice.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.backOffice.UserContext;
import it.newmedia.gokick.backOffice.guibean.GuiSportCenter;
import it.newmedia.gokick.backOffice.manager.CacheManager;
import it.newmedia.gokick.backOffice.manager.CityManager;
import it.newmedia.gokick.backOffice.manager.CountryManager;
import it.newmedia.gokick.backOffice.manager.DateManager;
import it.newmedia.gokick.backOffice.manager.GuiManager;
import it.newmedia.gokick.backOffice.manager.ProvinceManager;
import it.newmedia.gokick.backOffice.manager.SportCenterManager;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 *
 * Classe contenente le azioni per gestire la ricerca di utenti 
 */
public class AdminSingleSportCenterAction extends ABaseActionSupport implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>
  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idSportCenter;
  private GuiSportCenter guiSportCenter;
  private List<String> EnumUserStatusList;
  private String[] statusList;
  private int idCountry;
  private int idProvince;
  private int idCity;
  private String convEndDate;
  private String convStartDate;

  // </editor-fold>
  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public void validate()
  {
    BigDecimal d = BigDecimal.ZERO;
    String latStr = guiSportCenter.getLatitudeStr();
    String longStr = guiSportCenter.getLongitudeStr();
    if (latStr != null && !latStr.isEmpty())
    {
      try
      {
        if (latStr.contains(","))
        {
          latStr = latStr.replace(",", ".");
        }

        d = new BigDecimal(latStr);
      }
      catch (NumberFormatException nfex)
      {
        addFieldError("guiSportCenter.latitudeStr", "formato campo non valido (es. 42.345573)");
      }
      if (longStr == null || longStr.isEmpty())
      {
        addFieldError("guiSportCenter.longitudeStr", "valore longitudine mancante");
      }
    }

    if (longStr != null && !longStr.isEmpty())
    {
      try
      {
        if (longStr.contains(","))
        {
          longStr = longStr.replace(",", ".");
        }
        d = new BigDecimal(longStr);
      }
      catch (NumberFormatException nfex)
      {
        addFieldError("guiSportCenter.longitudeStr", "formato campo non valido (es. 42.345573)");
      }
      if (latStr == null || latStr.isEmpty())
      {
        addFieldError("guiSportCenter.latitudeStr", "valore latitudine mancante");
      }
    }
  }

  @Override
  public void prepare()
  {
    if (UserContext.getInstance().getIdCountryFilter() > 0)
    {
      this.idCountry = UserContext.getInstance().getIdCountryFilter();
    }
    this.statusList = new String[3];
    this.statusList[0] = Constants.SPORTCENTER_STATUS_ONLINE;
    this.statusList[1] = Constants.SPORTCENTER_STATUS_STANBY;
    this.statusList[2] = Constants.SPORTCENTER_STATUS_CANCELLATO;
  }

  @Override
  public String input()
  {
    SportCenter sportCenter = SportCenterManager.get(idSportCenter);
    this.guiSportCenter = GuiManager.buildGuiSportCenter(sportCenter);
    return SUCCESS;
  }

  public String update()
  {
    SportCenter sportCenter = SportCenterManager.get(guiSportCenter.getId());
    if (StringUtils.isNotEmpty(guiSportCenter.getStatus()) && !guiSportCenter.getStatus().equals(Constants.SPORTCENTER_STATUS_STANBY))
    {
      if (guiSportCenter.getStatus().equals(Constants.SPORTCENTER_STATUS_CANCELLATO))
      {
        sportCenter.setEnabled(false);
        sportCenter.setApproved(true);
      }
      if (guiSportCenter.getStatus().equals(Constants.SPORTCENTER_STATUS_ONLINE))
      {
        sportCenter.setApproved(true);
        sportCenter.setEnabled(true);
      }
    }
    else
    {
      sportCenter.setApproved(false);
      sportCenter.setEnabled(false);
    }

    Country country = CountryManager.getCountry(guiSportCenter.getCountry().getId());
    Province province = ProvinceManager.getProvince(guiSportCenter.getProvince().getId());
    City city = CityManager.getCity(guiSportCenter.getCity().getId());

    sportCenter.setCountry(country);
    sportCenter.setProvince(province);
    sportCenter.setCity(city);

    if (guiSportCenter.isConventioned())
    {
      try
      {

        String[] fPattern =
        {
          DateManager.FORMAT_PATTERN_9
        };
        if (StringUtils.isNotEmpty(this.convStartDate))
        {
          sportCenter.setConventionFrom(DateUtils.parseDate(convStartDate, fPattern));
        }
        if (StringUtils.isNotEmpty(this.convEndDate))
        {
          sportCenter.setConventionTo(DateUtils.parseDate(convEndDate, fPattern));
        }
      }
      catch (ParseException ex)
      {
        logger.error(ex, ex);
      }
    }
    else
    {
      sportCenter.setConventionFrom(null);
      sportCenter.setConventionTo(null);
    }

    String latStr = guiSportCenter.getLatitudeStr();
    String longStr = guiSportCenter.getLongitudeStr();

    if (latStr.contains(","))
    {
      latStr = latStr.replace(",", ".");
    }
    if (latStr == null || latStr.isEmpty())
    {
      sportCenter.setLatitude(BigDecimal.ZERO);
    }
    else
    {
      sportCenter.setLatitude(new BigDecimal(Double.valueOf(latStr)));
    }

    if (longStr.contains(","))
    {
      longStr = longStr.replace(",", ".");
    }
    if (longStr == null || longStr.isEmpty())
    {
      sportCenter.setLongitude(BigDecimal.ZERO);
    }
    else
    {
      sportCenter.setLongitude(new BigDecimal(Double.valueOf(longStr)));
    }

    sportCenter.setName(guiSportCenter.getName());
    sportCenter.setEmail(guiSportCenter.getEmail());
    sportCenter.setTelephone(guiSportCenter.getTelephone());
    sportCenter.setWebSite(guiSportCenter.getWebSite());


    SportCenterManager.update(sportCenter);
    boolean removedCache = CacheManager.removeWebSiteSportCenterInfo(guiSportCenter.getId());
    if (!removedCache)
    {
      logger.error("error removinc InfoCache in AdminsingleSportCenterAction");
    }
    return Constants.STRUTS_RESULT_NAME__UPDATED;

  }

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  /**
   * @return the EnumUserStatusList
   */
  public List<String> getEnumUserStatusList()
  {
    return EnumUserStatusList;
  }

  /**
   * @return the idSportCenter
   */
  public int getIdSportCenter()
  {
    return idSportCenter;
  }

  /**
   * @param idSportCenter the idSportCenter to set
   */
  public void setIdSportCenter(int idSportCenter)
  {
    this.idSportCenter = idSportCenter;
  }

  /**
   * @return the statusList
   */
  public String[] getStatusList()
  {
    return statusList;
  }

  /**
   * @param statusList the statusList to set
   */
  public void setStatusList(String[] statusList)
  {
    this.statusList = statusList;
  }

  /**
   * @return the guiSportCenter
   */
  public GuiSportCenter getGuiSportCenter()
  {
    return guiSportCenter;
  }

  /**
   * @param guiSportCenter the guiSportCenter to set
   */
  public void setGuiSportCenter(GuiSportCenter guiSportCenter)
  {
    this.guiSportCenter = guiSportCenter;
  }

  /**
   * @return the idCountry
   */
  public int getIdCountry()
  {
    return idCountry;
  }

  /**
   * @param idCountry the idCountry to set
   */
  public void setIdCountry(int idCountry)
  {
    this.idCountry = idCountry;
  }

  /**
   * @return the idProvince
   */
  public int getIdProvince()
  {
    return idProvince;
  }

  /**
   * @param idProvince the idProvince to set
   */
  public void setIdProvince(int idProvince)
  {
    this.idProvince = idProvince;
  }

  /**
   * @return the idCity
   */
  public int getIdCity()
  {
    return idCity;
  }

  /**
   * @param idCity the idCity to set
   */
  public void setIdCity(int idCity)
  {
    this.idCity = idCity;
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
      countryList=CountryManager.getAllWithSportCenters(0);
    }
    return countryList;
  }

  public List<Province> getProvinceList()
  {
    return ProvinceManager.getAll(guiSportCenter.getCountry().getId());
  }

  public List<City> getCityList()
  {
    if (this.guiSportCenter.getProvince().getId() > 0)
    {
      return CityManager.getAll(guiSportCenter.getProvince().getId());
    }
    else
    {
      return new ArrayList<City>();
    }
  }

  /**
   * @return the convEndDate
   */
  public String getConvEndDate()
  {
    return convEndDate;
  }

  /**
   * @param convEndDate the convEndDate to set
   */
  public void setConvEndDate(String convEndDate)
  {
    this.convEndDate = convEndDate;
  }

  /**
   * @return the convStartDate
   */
  public String getConvStartDate()
  {
    return convStartDate;
  }

  /**
   * @param convStartDate the convStartDate to set
   */
  public void setConvStartDate(String convStartDate)
  {
    this.convStartDate = convStartDate;
  }
  // </editor-fold>
}
