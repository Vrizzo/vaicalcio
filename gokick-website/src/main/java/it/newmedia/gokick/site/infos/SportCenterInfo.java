package it.newmedia.gokick.site.infos;


import it.newmedia.gokick.data.hibernate.beans.Pitch;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

/**
 * Classe che gestisce le informazioni relative ai centri sportivi quando queste devono essere visualizzate all'interno dell'applicazione
 * @author ggeroldi
 */
public class SportCenterInfo extends AInfo
{
  public static final String GOOGLEMAP_PARAMS_SEPARATOR = ",";
  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >

  /**
   *
   */
  public static final Comparator<SportCenterInfo> NAME_ORDER =
          new Comparator<SportCenterInfo>()
          {

            public int compare(SportCenterInfo o1, SportCenterInfo o2)
            {
              if (o1.conventioned && !o2.conventioned)
                return -1;
              if (!o1.conventioned && o2.conventioned)
                return 1;
              return o1.getName().compareToIgnoreCase(o2.getName());
            }

          };

          /**
           *
           */
          public static final Comparator<SportCenterInfo> ADDRESS_ORDER =
          new Comparator<SportCenterInfo>()
          {

            public int compare(SportCenterInfo o1, SportCenterInfo o2)
            {
              if (o1.conventioned && !o2.conventioned)
                return -1;
              if (!o1.conventioned && o2.conventioned)
                return 1;
              return o1.getAddress().compareToIgnoreCase(o2.getAddress());
            }

          };

          /**
           *
           */
          public static final Comparator<SportCenterInfo> CITY_ORDER =
          new Comparator<SportCenterInfo>()
          {

            public int compare(SportCenterInfo o1, SportCenterInfo o2)
            {
              if (o1.conventioned && !o2.conventioned)
                return -1;
              if (!o1.conventioned && o2.conventioned)
                return 1;
              return o1.getCityName().compareToIgnoreCase(o2.getCityName());
            }

          };

          /**
           *
           */
          public static final Comparator<SportCenterInfo> PROVINCE_ORDER =
          new Comparator<SportCenterInfo>()
          {

            public int compare(SportCenterInfo o1, SportCenterInfo o2)
            {
              if (o1.conventioned && !o2.conventioned)
                return -1;
              if (!o1.conventioned && o2.conventioned)
                return 1;
              return o1.getProvinceName().compareToIgnoreCase(o2.getProvinceName());
            }

          };

          /**
           *
           */
          public static final Comparator<SportCenterInfo> MATCHTYPEAVAILABLE_ORDER =
          new Comparator<SportCenterInfo>()
          {

            public int compare(SportCenterInfo o1, SportCenterInfo o2)
            {
              if (o1.conventioned && !o2.conventioned)
                return -1;
              if (!o1.conventioned && o2.conventioned)
                return 1;
              return o1.getMatchTypeAvailable().compareTo(o2.getMatchTypeAvailable());
            }

          };
   
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  private int id;

  private boolean conventioned;

  private String name;

  private String cityName;

  private String provinceName;

  private String provinceAbbreviation;
  
  private String country;

  private String address;

  private String telephone;

  private String matchTypeAvailable;

  private String googleMapsUrl;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --"  >

  /**
   *
   * @return
   */
  public String getAddress()
  {
    return address;
  }

  /**
   *
   * @return
   */
  public int getId()
  {
    return id;
  }

  /**
   *
   * @return
   */
  public boolean isConventioned()
  {
    return conventioned;
  }

  /**
   *
   * @return
   */
  public String getName()
  {
    return name;
  }

  /**
   *
   * @return
   */
  public String getCityName()
  {
    return cityName;
  }

  /**
   *
   * @return
   */
  public String getProvinceName()
  {
    return provinceName;
  }

  /**
   *
   * @return
   */
  public String getProvinceAbbreviation()
  {
    return provinceAbbreviation;
  }

  /**
   *
   * @return
   */
  public String getCityProvinceName()
  {
    return String.format("%1$s (%2$s)", this.cityName, this.provinceAbbreviation);
  }

  /**
   *
   * @return
   */
  public String getTelephone()
  {
    return telephone;
  }

  /**
   *
   * @return
   */
  public String getMatchTypeAvailable()
  {
    return matchTypeAvailable;
  }

  /**
   *
   * @return
   */
  @Override
  public Boolean isValid()
  {
    return this.valid;
  }

  /**
   * @return the googleMapsUrl
   */
  public String getGoogleMapsUrl()
  {
    return googleMapsUrl;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  /**
   *
   */
  public SportCenterInfo()
  {
    this.valid = false;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   *
   * @param sportCenter
   */
  public void load(SportCenter sportCenter)
  {
    this.id = sportCenter.getId();
    this.name = sportCenter.getName();
    this.cityName = sportCenter.getCity().getName();
    this.provinceName = sportCenter.getProvince().getName();
    this.provinceAbbreviation = sportCenter.getProvince().getAbbreviation();
    this.country = sportCenter.getCountry().getName();
    this.address = sportCenter.getAddress();
    this.telephone = sportCenter.getTelephone();
    if (sportCenter.getConventionFrom() == null && sportCenter.getConventionTo() == null)
      this.conventioned = false;
    else if (sportCenter.getConventionFrom() == null && sportCenter.getConventionTo().compareTo(new Date()) > 0)
      this.conventioned = true;
    else if (sportCenter.getConventionFrom().compareTo(new Date()) < 0 && sportCenter.getConventionTo() == null)
      this.conventioned = true;
    else if (sportCenter.getConventionFrom().compareTo(new Date()) < 0 && sportCenter.getConventionTo().compareTo(new Date()) > 0)
      this.conventioned = true;
    else
      this.conventioned = false;

    Pitch[] pitches = sportCenter.getPitchList().toArray(new Pitch[0]);
    StringBuilder sb = new StringBuilder();
    for( int i=0; i < pitches.length; i++ )
    {
      sb.append((pitches[i].getMatchType().getTotTeamPlayer())/2);
      if( i < pitches.length-1 )
        sb.append(", ");
    }
    this.matchTypeAvailable = sb.toString();

    
    String googleURl;
    String params;
    
    if (sportCenter.getLatitude() != null && sportCenter.getLatitude().compareTo(BigDecimal.ZERO) != 0 &&
        sportCenter.getLongitude()!= null && sportCenter.getLongitude().compareTo(BigDecimal.ZERO) != 0)
    {
      googleURl= AppContext.getInstance().getGoogleMapLatLonUrl();
      String latLon = sportCenter.getLatitude()+GOOGLEMAP_PARAMS_SEPARATOR+sportCenter.getLongitude();
      googleURl=googleURl.replace(Constants.REPLACEMENT__SPORT_CENTER_ADDRESS,this.address);
      googleURl=googleURl.replace(Constants.REPLACEMENT__SPORT_CENTER_CITY, this.cityName);
      googleURl=googleURl.replace(Constants.REPLACEMENT__SPORT_CENTER_PROVINCE, this.provinceName);
      googleURl=googleURl.replace(Constants.REPLACEMENT__SPORT_CENTER_COUNTRY, this.country);
      googleURl=googleURl.replace(Constants.REPLACEMENT__SPORT_CENTER_NAME, this.name);
      googleURl=googleURl.replace(Constants.REPLACEMENT__LAT_LON,latLon);
      this.googleMapsUrl=googleURl;
    }
    else
    {
      googleURl= AppContext.getInstance().getGoogleMapAddressUrl();
      params = sportCenter.getAddress() +GOOGLEMAP_PARAMS_SEPARATOR+ sportCenter.getCity().getName()+GOOGLEMAP_PARAMS_SEPARATOR+ sportCenter.getProvince().getName();
      this.googleMapsUrl=googleURl.replace("###PARAMS###", params);
    }
        
    this.valid = true;
  }

  // </editor-fold>
}
