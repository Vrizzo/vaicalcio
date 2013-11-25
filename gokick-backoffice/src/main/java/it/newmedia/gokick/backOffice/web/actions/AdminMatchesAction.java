package it.newmedia.gokick.backOffice.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.backOffice.UserContext;
import it.newmedia.gokick.backOffice.guibean.GuiCalendarInfo;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.backOffice.manager.CityManager;
import it.newmedia.gokick.backOffice.manager.CountryManager;
import it.newmedia.gokick.backOffice.manager.MatchManager;
import it.newmedia.gokick.backOffice.manager.ProvinceManager;
import it.newmedia.gokick.backOffice.manager.SportCenterManager;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente le azioni per gestire la ricerca di utenti 
 */
public class AdminMatchesAction extends ABaseActionSupport implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>
  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idCountry;
  private int idProvince;
  private int idCity;
  private int idSportCenter;
  private boolean advanceSearch;
  private List<GuiCalendarInfo> guiCalendarInfoList;
  private int matchTot;
  private int futureMatches;
  private String type;
  private List<String> typeList;
  private String time;
  private List<String> timeList;
  private boolean onlyCanceled;
  private List<Integer> matchCheckList;
  private String[] matchFoundList;
  private boolean sendToAll;
  private String receiverType;
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public void prepare()
  {
    int idCountryFilter=UserContext.getInstance().getIdCountryFilter();
    if (idCountryFilter > 0)
    {
      this.idCountry = UserContext.getInstance().getIdCountryFilter();
    }
    this.matchTot = MatchManager.getAllCount(idCountryFilter);
    this.futureMatches = MatchManager.getAllFutureCount(idCountryFilter);
    this.typeList = new ArrayList<String>(3);
    this.typeList.add("partite");
    this.typeList.add("sfide");
    this.timeList = new ArrayList<String>(3);
    this.timeList.add("future");
    this.timeList.add("passate");
  }

  public String viewAll()
  {
    this.advanceSearch = false;
    List<Object[]> objectList = MatchManager.getAllOrderedByDate(idCountry);
    //List<Match> matchList=MatchManager.getAllOrderedByDateStart();
    this.guiCalendarInfoList = new ArrayList<GuiCalendarInfo>();

    int ctrl = 0;
    this.matchFoundList = new String[objectList.size()];
    
    for (Object[] obj : objectList)
    {
      this.matchFoundList[ctrl] = ((Integer) (obj[0])).toString();
      this.guiCalendarInfoList.add(new GuiCalendarInfo(obj));
      ctrl++;
    }
    return SUCCESS;
  }

  public String searchMatches()
  {
    this.advanceSearch = true;
    List<Object[]> objectList = MatchManager.getAllOrderedByDate(
            idCountry,
            idProvince,
            idCity,
            idSportCenter,
            this.time.equals("passate"),
            this.time.equals("future"),
            this.onlyCanceled);

    this.guiCalendarInfoList = new ArrayList<GuiCalendarInfo>();
    int ctrl = 0;
    this.matchFoundList = new String[objectList.size()];
    for (Object[] obj : objectList)
    {
      this.matchFoundList[ctrl] = ((Integer) (obj[0])).toString();
      this.guiCalendarInfoList.add(new GuiCalendarInfo(obj));
      ctrl++;
    }

    return SUCCESS;
  }

  public String loadReceiverList()
  {
    if (this.isSendToAll())
    {
      matchCheckList = new ArrayList<Integer>();
      for (String idMatch : matchFoundList)
      {
        matchCheckList.add(Integer.parseInt(idMatch.trim()));
      }
    }
    UserContext.getInstance().setMatchReceiverList(MatchManager.getByIdList(matchCheckList));
    this.receiverType = "matches";
    return Constants.STRUTS_RESULT_NAME__SENDMESSAGES;
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public Integer getIdCountry()
  {
    return idCountry;
  }

  public void setIdCountry(Integer idCountry)
  {
    this.setIdCountry((int) idCountry);
  }

  public Integer getIdProvince()
  {
    return idProvince;
  }

  public void setIdProvince(Integer idProvince)
  {
    this.setIdProvince((int) idProvince);
  }

  public Integer getIdCity()
  {
    return idCity;
  }

  public void setIdCity(Integer idCity)
  {
    this.setIdCity((int) idCity);
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
      countryList=CountryManager.getAllWithMatches();
    }
    return countryList;
  }

  public List<Province> getProvinceList()
  {
    if (this.idCountry > 0)
    {
      return ProvinceManager.getAllWithMatches(idCountry);
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
      return CityManager.getAllWithMatches(idProvince);
    }
    else
    {
      return new ArrayList<City>();
    }
  }

  public List<SportCenter> getSportCenterList()
  {

    if (this.idCity > 0)
    {
      return SportCenterManager.getAll(-1, -1, this.idCity);
    }
    else if (this.idProvince > 0)
    {
      return SportCenterManager.getAll(-1, this.idProvince, -1);
    }
    return new ArrayList<SportCenter>();
  }

  /**
   * @return the matchTot
   */
  public int getMatchTot()
  {
    return matchTot;
  }

  /**
   * @return the futureMatches
   */
  public int getFutureMatches()
  {
    return futureMatches;
  }

  /**
   * @return the guiCalendarInfoList
   */
  public List<GuiCalendarInfo> getGuiCalendarInfoList()
  {
    return guiCalendarInfoList;
  }

  /**
   * @return the typeList
   */
  public List<String> getTypeList()
  {
    return typeList;
  }

  /**
   * @return the type
   */
  public String getType()
  {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type)
  {
    this.type = type;
  }

  /**
   * @return the time
   */
  public String getTime()
  {
    return time;
  }

  /**
   * @param time the time to set
   */
  public void setTime(String time)
  {
    this.time = time;
  }

  /**
   * @return the timeList
   */
  public List<String> getTimeList()
  {
    return timeList;
  }

  /**
   * @param idCountry the idCountry to set
   */
  public void setIdCountry(int idCountry)
  {
    this.idCountry = idCountry;
  }

  /**
   * @param idProvince the idProvince to set
   */
  public void setIdProvince(int idProvince)
  {
    this.idProvince = idProvince;
  }

  /**
   * @param idCity the idCity to set
   */
  public void setIdCity(int idCity)
  {
    this.idCity = idCity;
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
   * @return the onlyCanceled
   */
  public boolean isOnlyCanceled()
  {
    return onlyCanceled;
  }

  /**
   * @param onlyCanceled the onlyCanceled to set
   */
  public void setOnlyCanceled(boolean onlyCanceled)
  {
    this.onlyCanceled = onlyCanceled;
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
   * @return the matchCheckList
   */
  public List<Integer> getMatchCheckList()
  {
    return matchCheckList;
  }

  /**
   * @param matchCheckList the matchCheckList to set
   */
  public void setMatchCheckList(List<Integer> matchCheckList)
  {
    this.matchCheckList = matchCheckList;
  }

  /**
   * @return the matchFoundList
   */
  public String[] getMatchFoundList()
  {
    return matchFoundList;
  }

  /**
   * @param matchFoundList the matchFoundList to set
   */
  public void setMatchFoundList(String[] matchFoundList)
  {
    this.matchFoundList = StringUtils.split(matchFoundList[0], ",");
  }
  // </editor-fold>
}
