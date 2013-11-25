package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiCalendarDayInfo;
import it.newmedia.gokick.site.guibean.GuiCalendarInfo;
import it.newmedia.gokick.site.infos.MatchInfo;
import it.newmedia.gokick.site.managers.CityManager;
import it.newmedia.gokick.site.managers.CountryManager;
import it.newmedia.gokick.site.managers.DateManager;
import it.newmedia.gokick.site.managers.MatchInfoManager;
import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.gokick.site.managers.ProvinceManager;
import it.newmedia.gokick.site.managers.SportCenterManager;
import it.newmedia.gokick.site.managers.SquadManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;

/**
 *
 * Classe contenente le azioni per gestire il calendario dell'utente delle parite future, se organizzatore anche di quelle ancora da pagellare.
 * dal calendario è possibile accedere alle pagine di dettaglio partita e commenti
 */
public class CalendarAction extends AuthenticationBaseAction implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private String message;
  private int filterIdCountry;
  private List<Country> countryList;
  private int filterIdProvince;
//  private List<Province> provinceList;
  private int filterIdCity;
//  private List<City> cityList;
  private int filterIdSportCenter;
//  private List<SportCenter> sportCenterList;
  private boolean filterOnlyFriendsMatch;
  private String searchFilterSummaryText;
  private boolean preformedSearch;
  private int allPlayableMatchNumber;
  private int convenedMatchNumber;
  private int friendsMatchNumber;
  private int registeredMatchNumber;
  private int organizedMatchNumber;
  private List<GuiCalendarDayInfo> guiCalendarDayInfoNotRecordedList;
  private List<GuiCalendarInfo> guiCalendarInfoList;
  private List<GuiCalendarDayInfo> guiCalendarDayInfoList;
  private boolean defaultSearch;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public void prepare()
  {
    this.filterIdCountry = UserContext.getInstance().getUser().getCountry().getId();
    this.filterIdProvince = UserContext.getInstance().getUser().getProvince().getId();
  }

  public String searchMatch()
  {
//    User currentUser = UserContext.getInstance().getUser();
//
//    //Recupero la lista di tutti gli amici dell'utente corrente
//    List friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());
//
//    //Recupero le partite ancora da archiviare
//    List<MatchInfo> matchInfoNotRecordedList = MatchInfoManager.getNotRecorded(currentUser.getId());
//    this.guiCalendarInfoNotRecordedList = new ArrayList<GuiCalendarInfo>();
//    for (MatchInfo matchInfo : matchInfoNotRecordedList)
//    {
//      this.guiCalendarInfoNotRecordedList.add(new GuiCalendarInfo(matchInfo));
//    }
//    this.guiCalendarDayInfoNotRecordedList = createCalendar(this.guiCalendarInfoNotRecordedList);
//
//    //Recupero le partite da visualizzare nel calendario
//
//    List<MatchInfo> matchInfoList = MatchInfoManager.getConvened(friendsIdList, currentUser.getProvince().getId(), currentUser.getId());
//    this.guiCalendarInfoList = new ArrayList<GuiCalendarInfo>();
//    int convenedMatchCount = 0;
//    for (MatchInfo matchInfo : matchInfoList)
//    {
//      if (matchInfo.getIdUserOwner() == currentUser.getId())
//      {
//        //partita di cui sono l'organizzatore
//        this.guiCalendarInfoList.add(new GuiCalendarInfo(matchInfo));
//        if (!matchInfo.isCanceled())
//        {
//          convenedMatchCount++;
//        }
//      }
//      else if (MatchManager.getPlayer(matchInfo.getId(), currentUser.getId()) != null)
//      {
//        //partita a cui sono già iscritto
//        this.guiCalendarInfoList.add(new GuiCalendarInfo(matchInfo));
//        if (!matchInfo.isCanceled())
//        {
//          convenedMatchCount++;
//        }
//      }
//        //partita a cui non sono iscritto ma con posti disponibili
//      else if (friendsIdList.contains(matchInfo.getIdUserOwner()) && !matchInfo.isCanceled())
//      {
//          //partita a cui non sono iscritto organizzata da amici
//          this.guiCalendarInfoList.add(new GuiCalendarInfo(matchInfo));
//          convenedMatchCount++;
//      }
//      else
//      {
//          //partita a cui non sono iscritto organizzata non da amici ma aperta a tutti
//          if (//!matchInfo.isRegistrationClosed() &&
//                  //(matchInfo.getRegistrationStart() == null || matchInfo.getRegistrationStart().before(new Date())) &&
//                   matchInfo.isSquadOutEnable())//&& !matchInfo.isCanceled())
//          {
//            this.guiCalendarInfoList.add(new GuiCalendarInfo(matchInfo));
//            convenedMatchCount++;
//          }
//      }
//
//    }
//    this.guiCalendarDayInfoList = createCalendar(this.guiCalendarInfoList);
//
//    //Recupero i totali riepilogativi
//    this.allPlayableMatchNumber = MatchInfoManager.countAllPlayable();
//    this.convenedMatchNumber = convenedMatchCount;
//    this.friendsMatchNumber = MatchInfoManager.countFriends(friendsIdList, currentUser.getId());//==getFriendConvened
//    this.registeredMatchNumber = MatchInfoManager.countRegistered(currentUser.getId());
//    this.organizedMatchNumber = MatchInfoManager.countOrganized(currentUser.getId());
//
//    //Compongo il testo che riepiloga i parametri di visualizzazione del calendario
//    if (convenedMatchCount == 1)
//      this.searchFilterSummaryText = getText("label.suggerita");
//    else
//      this.searchFilterSummaryText = getText("label.suggerite");
//
//    this.preformedSearch = false;

    return SUCCESS;
  }

  public String viewCalendar()
  {
    //Recupero la lista di tutti gli amici dell'utente corrente
    List<Integer> friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());

    buildNotRecordedList(friendsIdList);

    //Recupero le partite da visualizzare nel calendario
    if (this.defaultSearch)
    {
      this.preformedSearch = false;
    }
    else
    {
      this.preformedSearch = true;
    }
    List<MatchInfo> matchInfoList = MatchInfoManager.getBySearchParameters(this.filterIdCountry,
            this.filterIdProvince,
            this.filterIdCity,
            this.filterIdSportCenter,
            this.getCurrentIdUser(),
            friendsIdList,
            this.defaultSearch,
            false);

    this.guiCalendarInfoList = new ArrayList<GuiCalendarInfo>();
    int convenedMatchCount = 0;
    //


    for (MatchInfo matchInfo : matchInfoList)
    {     //caso partite dei non amici,private...
      if (!friendsIdList.contains(matchInfo.getIdUserOwner()) && !matchInfo.isSquadOutEnable())
      {   //...se sono iscritto
        Player player = MatchManager.getPlayer(matchInfo.getId(), UserContext.getInstance().getUser().getId());
        //...e non MISSING
        if (player != null && player.getEnumPlayerType() != EnumPlayerType.Missing)
        {     //se non sono amico e non stato MISSING, non devo visualizzare in archivio le partite private
          this.guiCalendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
          if (!matchInfo.isCanceled())
          {
            convenedMatchCount++;
          }
        }
      }
      else
      {  //tutte lengthaltre (partite degli amici o pubbliche)
        this.guiCalendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
        if (!matchInfo.isCanceled())
        {
          convenedMatchCount++;
        }
      }
    }


    this.guiCalendarDayInfoList = createCalendar(this.guiCalendarInfoList);

    //Recupero i totali riepilogativi
    this.allPlayableMatchNumber = MatchInfoManager.countAllPlayable();
    this.convenedMatchNumber = convenedMatchCount;
    this.friendsMatchNumber = MatchInfoManager.countFriends(friendsIdList, UserContext.getInstance().getUser().getId());
    this.registeredMatchNumber = MatchInfoManager.countRegistered(UserContext.getInstance().getUser().getId());
    this.organizedMatchNumber = MatchInfoManager.countOrganized(UserContext.getInstance().getUser().getId());

    //Compongo il testo che riepiloga i parametri di visualizzazione del calendario
    this.searchFilterSummaryText = getText("label.partita.a");
    if (this.filterIdCity > 0)
    {
      City city = CityManager.getCity(this.filterIdCity);
      this.searchFilterSummaryText += String.format(" %1$s", city != null ? city.getName() : "");
    }
    else
    {
      Province province = ProvinceManager.getProvince(this.filterIdProvince);
      this.searchFilterSummaryText += String.format(" %1$s %2$s", province != null ? province.getName() : "", getText("label.eProvincia"));
    }
    if (this.filterIdSportCenter > 0)
    {
      SportCenter sportCenter = SportCenterManager.getSportCenter(this.filterIdSportCenter);
      this.searchFilterSummaryText += String.format(" %1$s %2$s", getText("label.campo"), sportCenter != null ? sportCenter.getName() : "");
    }

    return SUCCESS;
  }

  public String viewFriendsMatch()
  {
    //Recupero la lista di tutti gli amici dell'utente corrente
    List<Integer> friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());

    //Recupero le partite ancora da archiviare
    List<MatchInfo> matchInfoNotRecordedList = MatchInfoManager.getNotRecorded(UserContext.getInstance().getUser().getId());
    ArrayList<GuiCalendarInfo> guiCalendarInfoNotRecordedList = new ArrayList<GuiCalendarInfo>();
    for (MatchInfo matchInfo : matchInfoNotRecordedList)
    {
      guiCalendarInfoNotRecordedList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
    }

    this.guiCalendarDayInfoNotRecordedList = createCalendar(guiCalendarInfoNotRecordedList);

    //Recupero le partite da visualizzare nel calendario
    List<MatchInfo> matchInfoList = MatchInfoManager.getFriends(friendsIdList, UserContext.getInstance().getUser().getId());
    this.guiCalendarInfoList = new ArrayList<GuiCalendarInfo>();
    for (MatchInfo matchInfo : matchInfoList)
    {
      this.guiCalendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
    }
    this.guiCalendarDayInfoList = createCalendar(this.guiCalendarInfoList);

    //Recupero i totali riepilogativi
    this.allPlayableMatchNumber = MatchInfoManager.countAllPlayable();
    this.convenedMatchNumber = this.guiCalendarInfoList.size();
    this.friendsMatchNumber = this.guiCalendarInfoList.size();
    this.registeredMatchNumber = MatchInfoManager.countRegistered(UserContext.getInstance().getUser().getId());
    this.organizedMatchNumber = MatchInfoManager.countOrganized(UserContext.getInstance().getUser().getId());

    //Compongo il testo che riepiloga i parametri di visualizzazione del calendario
    this.searchFilterSummaryText = getText("label.partiteamici");

    this.preformedSearch = true;

    return SUCCESS;
  }

  public String viewRegisteredMatch()
  {
    //Recupero la lista di tutti gli amici dell'utente corrente
    List<Integer> friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());

    //Recupero le partite ancora da archiviare
    List<MatchInfo> matchInfoNotRecordedList = MatchInfoManager.getNotRecorded(UserContext.getInstance().getUser().getId());
    ArrayList<GuiCalendarInfo> guiCalendarInfoNotRecordedList = new ArrayList<GuiCalendarInfo>();
    for (MatchInfo matchInfo : matchInfoNotRecordedList)
    {
      guiCalendarInfoNotRecordedList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
    }
    this.guiCalendarDayInfoNotRecordedList = createCalendar(guiCalendarInfoNotRecordedList);

    //Recupero le partite da visualizzare nel calendario
    List<MatchInfo> matchInfoList = MatchInfoManager.getRegistered(UserContext.getInstance().getUser().getId());
    this.guiCalendarInfoList = new ArrayList<GuiCalendarInfo>();
    for (MatchInfo matchInfo : matchInfoList)
    {
      this.guiCalendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
    }
    this.guiCalendarDayInfoList = createCalendar(this.guiCalendarInfoList);

    //Recupero i totali riepilogativi
    this.allPlayableMatchNumber = MatchInfoManager.countAllPlayable();
    this.convenedMatchNumber = this.guiCalendarInfoList.size();
    this.friendsMatchNumber = MatchInfoManager.countFriends(friendsIdList, UserContext.getInstance().getUser().getId());
    this.registeredMatchNumber = this.guiCalendarInfoList.size();
    this.organizedMatchNumber = MatchInfoManager.countOrganized(UserContext.getInstance().getUser().getId());

    //Compongo il testo che riepiloga i parametri di visualizzazione del calendario
    this.searchFilterSummaryText = getText("label.partiteiscritto");

    this.preformedSearch = true;

    return SUCCESS;
  }

  public String viewOrganizedMatch()
  {
    //Recupero la lista di tutti gli amici dell'utente corrente
    List<Integer> friendsIdList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());

    //Recupero le partite ancora da archiviare
    List<MatchInfo> matchInfoNotRecordedList = MatchInfoManager.getNotRecorded(UserContext.getInstance().getUser().getId());
    ArrayList<GuiCalendarInfo> guiCalendarInfoNotRecordedList = new ArrayList<GuiCalendarInfo>();
    for (MatchInfo matchInfo : matchInfoNotRecordedList)
    {
      guiCalendarInfoNotRecordedList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
    }
    this.guiCalendarDayInfoNotRecordedList = createCalendar(guiCalendarInfoNotRecordedList);

    //Recupero le partite da visualizzare nel calendario
    List<MatchInfo> matchInfoList = MatchInfoManager.getOrganized(UserContext.getInstance().getUser().getId());
    this.guiCalendarInfoList = new ArrayList<GuiCalendarInfo>();
    for (MatchInfo matchInfo : matchInfoList)
    {
      this.guiCalendarInfoList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
    }
    this.guiCalendarDayInfoList = createCalendar(this.guiCalendarInfoList);

    //Recupero i totali riepilogativi
    this.allPlayableMatchNumber = MatchInfoManager.countAllPlayable();
    this.convenedMatchNumber = this.guiCalendarInfoList.size();
    this.friendsMatchNumber = MatchInfoManager.countFriends(friendsIdList, UserContext.getInstance().getUser().getId());
    this.registeredMatchNumber = MatchInfoManager.countRegistered(UserContext.getInstance().getUser().getId());
    this.organizedMatchNumber = this.guiCalendarInfoList.size();

    //Compongo il testo che riepiloga i parametri di visualizzazione del calendario
    this.searchFilterSummaryText = getText("label.partiteorganizzate");

    this.preformedSearch = true;

    return SUCCESS;
  }

  private void buildNotRecordedList(List<Integer> friendsIdList)
  {
    //Recupero le partite ancora da archiviare
    List<MatchInfo> matchInfoNotRecordedList = MatchInfoManager.getNotRecorded(this.getCurrentIdUser());
    ArrayList<GuiCalendarInfo> guiCalendarInfoNotRecordedList = new ArrayList<GuiCalendarInfo>();
    for (MatchInfo matchInfo : matchInfoNotRecordedList)
    {
      guiCalendarInfoNotRecordedList.add(new GuiCalendarInfo(matchInfo, friendsIdList));
    }
    this.guiCalendarDayInfoNotRecordedList = createCalendar(guiCalendarInfoNotRecordedList);
  }

  private List<GuiCalendarDayInfo> createCalendar(List<GuiCalendarInfo> guiCalendarInfoList)
  {
    int count = -1;
    Date tmpDate = null;
    List<GuiCalendarDayInfo> newGuiCalendarDayInfoList = new ArrayList<GuiCalendarDayInfo>();

    for (GuiCalendarInfo guiCalendarInfo : guiCalendarInfoList)
    {
      Date currentDate = guiCalendarInfo.getMatchInfo().getMatchStart();
      if ((tmpDate != null) && DateUtils.isSameDay(tmpDate, currentDate))
      {
        newGuiCalendarDayInfoList.get(count).getGuiCalendarInfoList().add(guiCalendarInfo);
      }
      else
      {
        GuiCalendarDayInfo guiCalendarDayInfo = new GuiCalendarDayInfo();
        if (DateUtils.isSameDay(new Date(), currentDate))
        {
          guiCalendarDayInfo.setCorrespondingDate(getText("label.calendar.date.today"));
        }
        else if (DateUtils.isSameDay(DateUtils.addDays(new Date(), 1), currentDate))
        {
          guiCalendarDayInfo.setCorrespondingDate(getText("label.calendar.date.tomorrow"));
        }
        else
        {
          guiCalendarDayInfo.setCorrespondingDate(DateManager.showDate(currentDate, DateManager.FORMAT_DATE_8));
        }
        guiCalendarDayInfo.getGuiCalendarInfoList().add(guiCalendarInfo);

        guiCalendarDayInfo.setListSize(getGuiCalendarInfoList() != null ? getGuiCalendarInfoList().size() : 0);
        newGuiCalendarDayInfoList.add(guiCalendarDayInfo);
        tmpDate = currentDate;
        count++;
      }
    }
    return newGuiCalendarDayInfoList;
  }

  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public List<City> getCityList()
  {

    if (this.filterIdProvince > 0)
    {
      List<City> list = CityManager.getAllWithMatches(filterIdProvince);
      //Add always user's country in which plays
      if (this.filterIdProvince == UserContext.getInstance().getUser().getProvince().getId())
      {
        list = CityManager.addCity(list, UserContext.getInstance().getUser().getCity());
      }
      return list;
    }
    else
    {
      return new ArrayList<City>();
    }
  }

  public List<Province> getProvinceList()
  {
    List<Province> list = ProvinceManager.getAllWithMatches(this.filterIdCountry);
    if (this.filterIdCountry == UserContext.getInstance().getUser().getCountry().getId())
    {
      list = ProvinceManager.addProvince(list, UserContext.getInstance().getUser().getProvince());
    }

    return list;
  }

  public List<Country> getCountryList()
  {
    if (this.countryList == null || this.countryList.size() == 0)
    {
      this.countryList = CountryManager.getAllWithMatches();
      countryList = CountryManager.addCountry(countryList, UserContext.getInstance().getUser().getCountry());
      return countryList;
    }
    return countryList;
  }

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

  public boolean isFilterOnlyFriendsMatch()
  {
    return filterOnlyFriendsMatch;
  }

  public void setFilterOnlyFriendsMatch(boolean filterOnlyFriendsMatch)
  {
    this.filterOnlyFriendsMatch = filterOnlyFriendsMatch;
  }

  public List<SportCenter> getSportCenterList()
  {
//    if (this.sportCenterList == null || this.sportCenterList.size() == 0)
//    {
//      if (this.filterIdCity > 0)
//      {
//        this.sportCenterList = SportCenterManager.getAll(-1, -1, this.filterIdCity);
//      }
//      else if (this.filterIdProvince > 0)
//      {
//        this.sportCenterList = SportCenterManager.getAll(-1, this.filterIdProvince, -1);
//      }
//    }
//    return sportCenterList;
    if (this.filterIdCity > 0)
    {
      return SportCenterManager.getAll(-1, -1, this.filterIdCity);
    }
    else if (this.filterIdProvince > 0)
    {
      return SportCenterManager.getAll(-1, this.filterIdProvince, -1);
    }


    return new ArrayList<SportCenter>();
  }

  public String getSearchFilterSummaryText()
  {
    return searchFilterSummaryText;
  }

  public void setSearchFilterSummaryText(String searchFilterSummaryText)
  {
    this.searchFilterSummaryText = searchFilterSummaryText;
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

  public int getRegisteredMatchNumber()
  {
    return registeredMatchNumber;
  }

  public void setRegisteredMatchNumber(int registeredMatchNumber)
  {
    this.registeredMatchNumber = registeredMatchNumber;
  }

  public boolean isPreformedSearch()
  {
    return preformedSearch;
  }

  public void setPreformedSearch(boolean preformedSearch)
  {
    this.preformedSearch = preformedSearch;
  }

  public List<GuiCalendarDayInfo> getGuiCalendarDayInfoList()
  {
    return guiCalendarDayInfoList;
  }

  public void setGuiCalendarDayInfoList(List<GuiCalendarDayInfo> guiCalendarDayInfoList)
  {
    this.guiCalendarDayInfoList = guiCalendarDayInfoList;
  }

  public List<GuiCalendarInfo> getGuiCalendarInfoList()
  {
    return guiCalendarInfoList;
  }

  public void setGuiCalendarInfoList(List<GuiCalendarInfo> guiCalendarInfoList)
  {
    this.guiCalendarInfoList = guiCalendarInfoList;
  }

  public int getAllPlayableMatchNumber()
  {
    return allPlayableMatchNumber;
  }

  public void setAllPlayableMatchNumber(int allPlayableMatchNumber)
  {
    this.allPlayableMatchNumber = allPlayableMatchNumber;
  }

  public List<GuiCalendarDayInfo> getGuiCalendarDayInfoNotRecordedList()
  {
    return guiCalendarDayInfoNotRecordedList;
  }

  public void setGuiCalendarDayInfoNotRecordedList(List<GuiCalendarDayInfo> guiCalendarDayInfoNotRecordedList)
  {
    this.guiCalendarDayInfoNotRecordedList = guiCalendarDayInfoNotRecordedList;
  }

  public int getFriendsMatchNumber()
  {
    return friendsMatchNumber;
  }

  public void setFriendsMatchNumber(int friendsMatchNumber)
  {
    this.friendsMatchNumber = friendsMatchNumber;
  }

  /**
   * @return the defaultSearch
   */
  public boolean isDefaultSearch()
  {
    return defaultSearch;
  }

  /**
   * @param defaultSearch the defaultSearch to set
   */
  public void setDefaultSearch(boolean defaultSearch)
  {
    this.defaultSearch = defaultSearch;
  }
  // </editor-fold>
}
