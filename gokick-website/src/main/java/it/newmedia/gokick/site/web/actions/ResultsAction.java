package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.hibernate.beans.*;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiCalendarInfo;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.infos.StatisticInfo;
import it.newmedia.gokick.site.managers.*;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 *
 * Classe contenente le azioni per gestire la visualizzazzione delle parite passate,
 * con i relativi risultati se archiviate, se organizzatore anche di poterle pagellare.
 * dal calendario è possibile accedere alle pagine di dettaglio partita, archiviazione e commenti avendo i dovuti permessi
 *
 */
public class ResultsAction extends AuthenticationBaseAction implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  //TODO decidere a quale indirizzo mandare il link delle partite non ancora archiviate
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(ResultsAction.class);

  private int filterIdCountry;

  private List<Country> countryList;

  private int filterIdProvince;

  private List<Province> provinceList;

  private int filterIdCity;

  private List<City> cityList;

  private int filterIdSportCenter;

  private List<SportCenter> sportCenterList;

  private String searchFilterSummaryText;

  private boolean preformedSearch;

  private boolean playedSearch;

  private boolean organizedSearch;

  StatisticInfo currentUserStatisticInfo;

  private int convenedMatchNumber;

  private int playedMatchNumber;

  private int organizedMatchNumber;

  private List<MatchInfo> matchInfoNotRecordedList;

  private List<MatchInfo> matchInfoList;

  private List<GuiCalendarInfo> calendarInfoNotRecordedList;

  private List<GuiCalendarInfo> calendarInfoList;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public void prepare()
  {
    this.filterIdCountry = UserContext.getInstance().getUser().getCountry().getId();
    this.filterIdProvince = UserContext.getInstance().getUser().getProvince().getId();
  }

  public String viewResults()
  {
   
    this.currentUserStatisticInfo = StatisticInfoManager.getByIdUserAndPeriod(UserContext.getInstance().getUser().getId(), Constants.STATISTIC_PERIOD__ALL);

    //Recupero la lista di tutti gli amici dell'utente corrente
    List<Integer> friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());

    Date dateLimit = DateUtils.addMonths(new Date(), -1*AppContext.getInstance().getMaxMonthsOffsetForResults());
    //Recupero le partite da visualizzare nel calendario
    this.matchInfoList = MatchInfoManager.getBySearchParameters(this.filterIdCountry,
                                                                this.filterIdProvince,
                                                                this.filterIdCity,
                                                                this.filterIdSportCenter,
                                                                UserContext.getInstance().getUser().getId(),
                                                                friendsIdList,
                                                                dateLimit,
                                                                true,
                                                                true);
    
    this.calendarInfoList = new ArrayList<GuiCalendarInfo>();
    
    int convenedMatchCount = 0;
    for (MatchInfo matchInfo : matchInfoList)
    {
      if (!friendsIdList.contains(matchInfo.getIdUserOwner()) && !matchInfo.isSquadOutEnable())
      {
        Player player = MatchManager.getPlayer(matchInfo.getId(), UserContext.getInstance().getUser().getId());
        if (player!=null && player.getEnumPlayerType()!=EnumPlayerType.Missing && player.getEnumPlayerStatus()!=EnumPlayerStatus.UserCalled)
        {    //se non sono amico e non stato MISSING, non devo visualizzare in archivio le partite private
          this.calendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
          if (!matchInfo.isCanceled())
          {
            convenedMatchCount++;
          }
        }
      }
      else
      {
        this.calendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
        if (!matchInfo.isCanceled())
        {
          convenedMatchCount++;
        }
      }

    }
    
    this.playedMatchNumber=0;
    this.organizedMatchNumber=0;

    //Recupero i totali riepilogativi
    for(GuiCalendarInfo guiCalendarInfo : calendarInfoList)
    {
      if(guiCalendarInfo.isCurrentUserOwner() 
             && guiCalendarInfo.getMatchInfo().getMatchStart().before(new Date())
                && !guiCalendarInfo.getMatchInfo().isCanceled() 
              )
          this.organizedMatchNumber++;
      if(guiCalendarInfo.isCurrentUserRegistered() &&
              guiCalendarInfo.getMatchInfo().getMatchStart().before(new Date()))
          this.playedMatchNumber++;
    }
    
    
    this.convenedMatchNumber = convenedMatchCount;

    //Compongo il testo che riepiloga i parametri di visualizzazione del calendario
    if (this.convenedMatchNumber == 1)
      this.searchFilterSummaryText = getText("label.suggerita");
    else
      this.searchFilterSummaryText = getText("label.suggerite");

    this.preformedSearch = false;

    return SUCCESS;
  }

  public String viewLastTwoMonth()
  {
    Calendar  endDate = new GregorianCalendar();
    endDate.roll(Calendar.MONTH, -2);
    Date end = endDate.getTime();
    this.currentUserStatisticInfo = StatisticInfoManager.getByIdUserAndPeriod(UserContext.getInstance().getUser().getId(), Constants.STATISTIC_PERIOD__ALL);

    //Recupero la lista di tutti gli amici dell'utente corrente
    List<Integer> friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());

    //Recupero le partite da visualizzare nel calendario
    this.matchInfoList = MatchInfoManager.getBySearchParameters(this.filterIdCountry,
                                                                this.filterIdProvince,
                                                                this.filterIdCity,
                                                                this.filterIdSportCenter,
                                                                UserContext.getInstance().getUser().getId(),
                                                                friendsIdList,
                                                                end,
                                                                true,
                                                                true);

    this.calendarInfoList = new ArrayList<GuiCalendarInfo>();

    int convenedMatchCount = 0;
    for (MatchInfo matchInfo : matchInfoList)
    {
      if (!friendsIdList.contains(matchInfo.getIdUserOwner()) && !matchInfo.isSquadOutEnable())
      {
        Player player = MatchManager.getPlayer(matchInfo.getId(), UserContext.getInstance().getUser().getId());
        if (player!=null && player.getEnumPlayerType()!=EnumPlayerType.Missing && player.getEnumPlayerStatus()!=EnumPlayerStatus.UserCalled)
        {    //se non sono amico e non stato MISSING, non devo visualizzare in archivio le partite private
          this.calendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
          if (!matchInfo.isCanceled())
          {
            convenedMatchCount++;
          }
        }
      }
      else
      {
        this.calendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
        if (!matchInfo.isCanceled())
        {
          convenedMatchCount++;
        }
      }
    }

    this.playedMatchNumber=0;
    this.organizedMatchNumber=0;

    //Recupero i totali riepilogativi
    for(GuiCalendarInfo guiCalendarInfo : calendarInfoList)
    {
      if(guiCalendarInfo.isCurrentUserOwner()
             && guiCalendarInfo.getMatchInfo().getMatchStart().before(new Date())
                && !guiCalendarInfo.getMatchInfo().isCanceled()
              )
          this.organizedMatchNumber++;
      if(guiCalendarInfo.isCurrentUserRegistered() &&
              guiCalendarInfo.getMatchInfo().getMatchStart().before(new Date()))
          this.playedMatchNumber++;
    }


    this.convenedMatchNumber = convenedMatchCount;

    //Compongo il testo che riepiloga i parametri di visualizzazione del calendario
    if (this.convenedMatchNumber == 1)
      this.searchFilterSummaryText = getText("label.suggerita");
    else
      this.searchFilterSummaryText = getText("label.suggerite");

    this.preformedSearch = false;

    return SUCCESS;
  }

  public String searchResult()
  {
    this.currentUserStatisticInfo = StatisticInfoManager.getByIdUserAndPeriod(UserContext.getInstance().getUser().getId(), Constants.STATISTIC_PERIOD__ALL);

    //Recupero la lista di tutti gli amici dell'utente corrente
    List<Integer> friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());

    Date dateLimit = DateUtils.addMonths(new Date(), -1*AppContext.getInstance().getMaxMonthsOffsetForResults());
    this.matchInfoList = MatchInfoManager.getBySearchParameters(this.filterIdCountry,
            this.filterIdProvince,
            this.filterIdCity,
            this.filterIdSportCenter,
            UserContext.getInstance().getUser().getId(),
            friendsIdList,
            dateLimit,
            false,
            true);

    this.calendarInfoList = new ArrayList<GuiCalendarInfo>();
    int convenedMatchCount = 0;
    for (MatchInfo matchInfo : matchInfoList)
    {
        //partita a cui mi sono iscritto
        if (!friendsIdList.contains(matchInfo.getIdUserOwner()) && !matchInfo.isSquadOutEnable())
        {
          Player player = MatchManager.getPlayer(matchInfo.getId(), UserContext.getInstance().getUser().getId());
          if (player!=null && player.getEnumPlayerType()!=EnumPlayerType.Missing && player.getEnumPlayerStatus()!=EnumPlayerStatus.UserCalled)
          {    //se non sono amico e non stato MISSING, non devo visualizzare in archivio le partite private
            this.calendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
            if (!matchInfo.isCanceled())
            {
              convenedMatchCount++;
            }
          }
        }
        else
        {
          this.calendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
          if (!matchInfo.isCanceled())
          {
            convenedMatchCount++;
          }
        }
      //}
    }

    //Recupero i totali riepilogativi
    this.convenedMatchNumber = convenedMatchCount;//this.calendarInfoList.size();
    this.playedMatchNumber = MatchInfoManager.countPlayed(UserContext.getInstance().getUser().getId());
    this.organizedMatchNumber = MatchInfoManager.countOrganizedRecorded(UserContext.getInstance().getUser().getId());

    //Compongo il testo che riepiloga i parametri di visualizzazione del calendario
    this.searchFilterSummaryText = getText("label.partita.a");
    if (this.filterIdCity > 0)
    {
      City city = CityManager.getCity(this.filterIdCity);
      this.searchFilterSummaryText += String.format(" %1$s", city != null ? city.getName() : "");
    }
    else if (this.filterIdProvince > 0)
    {
      Province province = ProvinceManager.getProvince(this.filterIdProvince);
      this.searchFilterSummaryText += String.format(" %1$s %2$s", province != null ? province.getName() : "", getText("label.eProvincia"));
    }
    else
    {
      if (this.convenedMatchNumber == 1)
        this.searchFilterSummaryText = getText("label.suggerita");
      else
        this.searchFilterSummaryText = getText("label.suggerite");
    }
    if (this.filterIdSportCenter > 0)
    {
      SportCenter sportCenter = SportCenterManager.getSportCenter(this.filterIdSportCenter);
      this.searchFilterSummaryText += String.format(" %1$s %2$s", getText("label.campo"), sportCenter != null ? sportCenter.getName() : "");
    }

    this.preformedSearch = true;

    return SUCCESS;
  }

  public String viewPlayedMatch()
  {
    //Recupero la lista di tutti gli amici dell'utente corrente
    List<Integer> friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());

    this.currentUserStatisticInfo = StatisticInfoManager.getByIdUserAndPeriod(UserContext.getInstance().getUser().getId(), Constants.STATISTIC_PERIOD__ALL);

    this.matchInfoList = MatchInfoManager.getPlayed(UserContext.getInstance().getUser().getId());

    this.calendarInfoList = new ArrayList<GuiCalendarInfo>();
    for (MatchInfo matchInfo : matchInfoList)
    {
      this.calendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
    }

    //Recupero i totali riepilogativi
    this.convenedMatchNumber = this.matchInfoList.size();
    this.playedMatchNumber = this.matchInfoList.size();
    this.organizedMatchNumber = MatchInfoManager.countOrganizedRecorded(UserContext.getInstance().getUser().getId());

    //Compongo il testo che riepiloga i parametri di visualizzazione del calendario
    this.searchFilterSummaryText = getText("label.partitegiocate");

    this.playedSearch = true;

    return SUCCESS;
  }

  public String viewOrganizedMatch()
  {
    //Recupero la lista di tutti gli amici dell'utente corrente
    List<Integer> friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());

    this.currentUserStatisticInfo = StatisticInfoManager.getByIdUserAndPeriod(UserContext.getInstance().getUser().getId(), Constants.STATISTIC_PERIOD__ALL);

    this.matchInfoList = MatchInfoManager.getOrganizedRecorded(UserContext.getInstance().getUser().getId());

    this.calendarInfoList = new ArrayList<GuiCalendarInfo>();
    for (MatchInfo matchInfo : matchInfoList)
    {
      this.calendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
    }

    //Recupero i totali riepilogativi
    this.convenedMatchNumber = this.matchInfoList.size();
    this.playedMatchNumber = MatchInfoManager.countPlayed(UserContext.getInstance().getUser().getId());
    this.organizedMatchNumber = this.matchInfoList.size();

    //Compongo il testo che riepiloga i parametri di visualizzazione del calendario
    this.searchFilterSummaryText = getText("label.partiteorganizzate");

    this.organizedSearch = true;

    return SUCCESS;
  }

  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public StatisticInfo getCurrentUserStatisticInfo()
  {
    return currentUserStatisticInfo;
  }

  public void setCurrentUserStatisticInfo(StatisticInfo currentUserStatisticInfo)
  {
    this.currentUserStatisticInfo = currentUserStatisticInfo;
  }

  public List<Country> getCountryList()
  {
    if (this.countryList == null || this.countryList.size() == 0)
    {
      this.countryList = CountryManager.getAllWithPastMatches();
    }
    return countryList;
  }

//  public void setCounrtyList(List<Country> countryList)
//  {
//    this.countryList = countryList;
//  }

  public List<Province> getProvinceList()
  {
    if (this.provinceList == null || this.provinceList.size() == 0)
    {
      if(this.filterIdCountry > 0)
        return ProvinceManager.getAllWithPastMatches(this.filterIdCountry);
    }
    return provinceList;
  }

//  public void setProvinceList(List<Province> provinceList)
//  {
//    this.provinceList = provinceList;
//  }

  public List<City> getCityList()
  {
    if (this.cityList == null || this.cityList.size() == 0)
    {
      if (this.filterIdProvince > 0)
        return CityManager.getAllWithSportCenters(this.filterIdProvince);
    }
    return cityList;
  }

//  public void setCityList(List<City> cityList)
//  {
//    this.cityList = cityList;
//  }

  public List<SportCenter> getSportCenterList()
  {
    if (this.sportCenterList == null || this.sportCenterList.size() == 0)
    {
      if (this.filterIdCity > 0)
      {
        this.sportCenterList = SportCenterManager.getAll(-1, -1, this.filterIdCity);
      }
      else if (this.filterIdProvince > 0)
      {
        this.sportCenterList = SportCenterManager.getAll(-1, this.filterIdProvince, -1);
      }
      else
      {
        this.sportCenterList = SportCenterManager.getAll(-1, -1, -1);
      }
    }

    return sportCenterList;
  }

//  public void setSportCenterList(List<SportCenter> sportCenterList)
//  {
//    this.sportCenterList = sportCenterList;
//  }


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

  public int getFilterIdProvince()
  {
    return filterIdProvince;
  }

  public void setFilterIdProvince(int filterIdProvince)
  {
    this.filterIdProvince = filterIdProvince;
  }

  public int getFilterIdSportCenter()
  {
    return filterIdSportCenter;
  }

  public void setFilterIdSportCenter(int filterIdSportCenter)
  {
    this.filterIdSportCenter = filterIdSportCenter;
  }

  public List<MatchInfo> getMatchInfoList()
  {
    return matchInfoList;
  }

  public void setMatchInfoList(List<MatchInfo> matchInfoList)
  {
    this.matchInfoList = matchInfoList;
  }

  public String getSearchFilterSummaryText()
  {
    return searchFilterSummaryText;
  }

  public void setSearchFilterSummaryText(String searchFilterSummaryText)
  {
    this.searchFilterSummaryText = searchFilterSummaryText;
  }

  public List<GuiCalendarInfo> getCalendarInfoList()
  {
    return calendarInfoList;
  }

  public void setCalendarInfoList(List<GuiCalendarInfo> calendarInfoList)
  {
    this.calendarInfoList = calendarInfoList;
  }

  public List<MatchInfo> getMatchInfoNotRecordedList()
  {
    return matchInfoNotRecordedList;
  }

  public void setMatchInfoNotRecordedList(List<MatchInfo> matchInfoNotRecordedList)
  {
    this.matchInfoNotRecordedList = matchInfoNotRecordedList;
  }

  public int getConvenedMatchNumber()
  {
    return convenedMatchNumber;
  }

  public void setConvenedMatchNumber(int convenedMatchNumber)
  {
    this.convenedMatchNumber = convenedMatchNumber;
  }

  public int getOrganizedMatchNumber()
  {
    return organizedMatchNumber;
  }

  public void setOrganizedMatchNumber(int organizedMatchNumber)
  {
    this.organizedMatchNumber = organizedMatchNumber;
  }

  public int getPlayedMatchNumber()
  {
    return playedMatchNumber;
  }

  public void setPlayedMatchNumber(int playedMatchNumber)
  {
    this.playedMatchNumber = playedMatchNumber;
  }

  public List<GuiCalendarInfo> getCalendarInfoNotRecordedList()
  {
    return calendarInfoNotRecordedList;
  }

  public void setCalendarInfoNotRecordedList(List<GuiCalendarInfo> calendarInfoNotRecordedList)
  {
    this.calendarInfoNotRecordedList = calendarInfoNotRecordedList;
  }

  public boolean isPreformedSearch()
  {
    return preformedSearch;
  }

  public void setPreformedSearch(boolean preformedSearch)
  {
    this.preformedSearch = preformedSearch;
  }

  /**
   * @return the playedSearch
   */
  public boolean isPlayedSearch()
  {
    return playedSearch;
  }

  /**
   * @return the organizedSearch
   */
  public boolean isOrganizedSearch()
  {
    return organizedSearch;
  }
  // </editor-fold>
}
