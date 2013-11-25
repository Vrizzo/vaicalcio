package it.newmedia.gokick.backOffice.guibean;

import it.newmedia.gokick.data.hibernate.beans.Pitch;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.backOffice.AppContext;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.User;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

public class GuiSportCenter 
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >

  public static final Comparator<GuiSportCenter> NAME_ORDER =
          new Comparator<GuiSportCenter>()
          {

            public int compare(GuiSportCenter o1, GuiSportCenter o2)
            {
              if (o1.isConventioned() && !o2.isConventioned())
                return -1;
              if (!o1.isConventioned() && o2.isConventioned())
                return 1;
              return o1.getName().compareTo(o2.getName());
            }

          };

  public static final Comparator<GuiSportCenter> ADDRESS_ORDER =
          new Comparator<GuiSportCenter>()
          {

            public int compare(GuiSportCenter o1, GuiSportCenter o2)
            {
              if (o1.isConventioned() && !o2.isConventioned())
                return -1;
              if (!o1.isConventioned() && o2.isConventioned())
                return 1;
              return o1.getAddress().compareTo(o2.getAddress());
            }

          };

  public static final Comparator<GuiSportCenter> CITY_ORDER =
          new Comparator<GuiSportCenter>()
          {

            public int compare(GuiSportCenter o1, GuiSportCenter o2)
            {
              if (o1.isConventioned() && !o2.isConventioned())
                return -1;
              if (!o1.isConventioned() && o2.isConventioned())
                return 1;
              return o1.getCityName().compareTo(o2.getCityName());
            }

          };

  public static final Comparator<GuiSportCenter> PROVINCE_ORDER =
          new Comparator<GuiSportCenter>()
          {

            public int compare(GuiSportCenter o1, GuiSportCenter o2)
            {
              if (o1.isConventioned() && !o2.isConventioned())
                return -1;
              if (!o1.isConventioned() && o2.isConventioned())
                return 1;
              return o1.getProvinceName().compareTo(o2.getProvinceName());
            }

          };

  public static final Comparator<GuiSportCenter> MATCHTYPEAVAILABLE_ORDER =
          new Comparator<GuiSportCenter>()
          {

            public int compare(GuiSportCenter o1, GuiSportCenter o2)
            {
              if (o1.isConventioned() && !o2.isConventioned())
                return -1;
              if (!o1.isConventioned() && o2.isConventioned())
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

  private String address;

  private String telephone;
  
  private String email;
  
  private String latitudeStr;
  
  private String longitudeStr;
  
  private String webSite;

  private String matchTypeAvailable;

  private String googleMapsUrl;

  private SportCenter sportCenter;
  
  private String status;
  
  private Date conventionFrom;
  
  private Date conventionTo;
  
  private User userAuthor;
  
  private Date created;
  
  private Country country;
  
  private Province province;
  
  private City city;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --"  >
  public int getId()
  {
    return id;
  }
    
  public void setId(int id)
  {
    this.id = id;
  }

  public boolean isConventioned()
  {
    return conventioned;
  }
  
  public void setConventioned(boolean conventioned)
  {
    this.conventioned = conventioned;
  }

  public String getName()
  {
    return name;
  }
    
  public void setName(String name)
  {
    this.name = name;
  }

  public String getCityName()
  {
    return cityName;
  }
    
  public void setCityName(String cityName)
  {
    this.cityName = cityName;
  }

  public String getProvinceName()
  {
    return provinceName;
  }
    
  public void setProvinceName(String provinceName)
  {
    this.provinceName = provinceName;
  }

  public String getProvinceAbbreviation()
  {
    return provinceAbbreviation;
  }
    
  public void setProvinceAbbreviation(String provinceAbbreviation)
  {
    this.provinceAbbreviation = provinceAbbreviation;
  }
  
  public String getAddress()
  {
    return address;
  }
    
  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getCityProvinceName()
  {
    return String.format("%1$s (%2$s)", this.cityName, this.provinceAbbreviation);
  }
    
  public String getTelephone()
  {
    return telephone;
  }
    
  public void setTelephone(String telephone)
  {
    this.telephone = telephone;
  }
    
  public String getEmail()
  {
    return email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getMatchTypeAvailable()
  {
    return matchTypeAvailable;
  }
    
  public void setMatchTypeAvailable(String matchTypeAvailable)
  {
    this.matchTypeAvailable = matchTypeAvailable;
  }

  public String getGoogleMapsUrl()
  {
    return googleMapsUrl;
  }
    
  public void setGoogleMapsUrl(String googleMapsUrl)
  {
    this.googleMapsUrl = googleMapsUrl;
  }
  
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }
  
  public void setSportCenter(SportCenter sportCenter)
  {
    this.sportCenter = sportCenter;
  }

  public String getStatus()
  {
    return status;
  }
      
  public void setStatus(String status)
  {
    this.status = status;
  }
      
  public String getLatitudeStr()
  {
    return latitudeStr;
  }

  public void setLatitudeStr(String latitudeStr)
  {
    this.latitudeStr = latitudeStr;
  }

  public String getLongitudeStr()
  {
    return longitudeStr;
  }

  public void setLongitudeStr(String longitudeStr)
  {
    this.longitudeStr = longitudeStr;
  }
    
  public String getWebSite()
  {
    return webSite;
  }
  
  public void setWebSite(String webSite)
  {
    this.webSite = webSite;
  }

  public Date getConventionFrom()
  {
    return conventionFrom;
  }

  public void setConventionFrom(Date conventionFrom)
  {
    this.conventionFrom = conventionFrom;
  }

  public Date getConventionTo()
  {
    return conventionTo;
  }

  public void setConventionTo(Date conventionTo)
  {
    this.conventionTo = conventionTo;
  }

  public User getUserAuthor()
  {
    return userAuthor;
  }

  public void setUserAuthor(User userAuthor)
  {
    this.userAuthor = userAuthor;
  }

  public Date getCreated()
  {
    return created;
  }

  public void setCreated(Date created)
  {
    this.created = created;
  }

  public Country getCountry()
  {
    return country;
  }

  public void setCountry(Country country)
  {
    this.country = country;
  }

  public Province getProvince()
  {
    return province;
  }

  public void setProvince(Province province)
  {
    this.province = province;
  }

  public City getCity()
  {
    return city;
  }

  public void setCity(City city)
  {
    this.city = city;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  public GuiSportCenter()
  {
    
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public void load(SportCenter sportCenter)
  {    
    this.setSportCenter(sportCenter);
    this.id = sportCenter.getId();
    this.name = sportCenter.getName();
    this.cityName = sportCenter.getCity().getName();
    this.provinceName = sportCenter.getProvince().getName();
    this.provinceAbbreviation = sportCenter.getProvince().getAbbreviation();
    this.address = sportCenter.getAddress();
    this.telephone = sportCenter.getTelephone();
    this.email = sportCenter.getEmail();
    this.conventionFrom = sportCenter.getConventionFrom();
    this.conventionTo = sportCenter.getConventionTo();
    this.userAuthor = sportCenter.getUserAuthor();
    this.created = sportCenter.getCreated();
    this.country = sportCenter.getCountry();
    this.city = sportCenter.getCity();
    this.province = sportCenter.getProvince();
    this.webSite = sportCenter.getWebSite();
    if (sportCenter.getLatitude()!=null)
    {
      if (sportCenter.getLatitude().compareTo(BigDecimal.ZERO) == 0)
      {
        this.latitudeStr = "";
      }
      else
      {
        this.latitudeStr = sportCenter.getLatitude().toString();
      }
      
    }
    if (sportCenter.getLongitude()!=null)
    {
      if (sportCenter.getLongitude().compareTo(BigDecimal.ZERO) == 0)
      {
        this.longitudeStr = "";
      }
      else
      {
        this.longitudeStr = sportCenter.getLongitude().toString();
      }
    }
    
    
    
    if (sportCenter.getConventionFrom() == null || sportCenter.getConventionTo() == null)
      this.setConventioned(false);
    else if (sportCenter.getConventionTo().compareTo(new Date()) >= 0)
      this.setConventioned(true);
    else
      this.setConventioned(false);

    this.setStatus(Constants.SPORTCENTER_STATUS_STANBY);
    if (sportCenter.getApproved() && sportCenter.getEnabled())
        this.setStatus(Constants.SPORTCENTER_STATUS_ONLINE);
    if (sportCenter.getApproved() && !sportCenter.getEnabled())
        this.setStatus(Constants.SPORTCENTER_STATUS_CANCELLATO);

    Pitch[] pitches = sportCenter.getPitchList().toArray(new Pitch[0]);
    StringBuilder sb = new StringBuilder();
    for( int i=0; i < pitches.length; i++ )
    {
      sb.append((pitches[i].getMatchType().getTotTeamPlayer())/2);
      if( i < pitches.length-1 )
        sb.append(", ");
    }
    this.setMatchTypeAvailable(sb.toString());

    this.setGoogleMapsUrl(AppContext.getInstance().getGoogleMapUrl()
                          + sportCenter.getAddress() + ","
                          + sportCenter.getCity().getName()+ ","
                          + sportCenter.getProvince().getName());    
  }
  // </editor-fold>

  

  

  

  

  

  

  

  

  

  

  

  
}
