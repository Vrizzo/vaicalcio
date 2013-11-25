package it.newmedia.gokick.backOffice.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.MatchType;
import it.newmedia.gokick.data.hibernate.beans.PitchCover;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.backOffice.UserContext;
import it.newmedia.gokick.backOffice.guibean.GuiSportCenter;
import it.newmedia.gokick.backOffice.manager.CityManager;
import it.newmedia.gokick.backOffice.manager.CountryManager;
import it.newmedia.gokick.backOffice.manager.GuiManager;
import it.newmedia.gokick.backOffice.manager.ProvinceManager;
import it.newmedia.gokick.backOffice.manager.SportCenterManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Classe contenente le azioni per gestire la ricerca di utenti 
 */
public class AdminSportCentersAction extends ABaseActionSupport implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>
  
  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private List<Integer> sportCenterCheckList;
  private boolean sendToAll;
  private String receiverType;
  private List<MatchType> matchTypeList;
  private List<PitchCover> pitchCoverList;
  private List<GuiSportCenter> guiSportCenterList;
  private String name;
  private int idMatchType;
  private int idPitchCover;
  private int idCountry;
  private int idProvince;
  private int idCity;
  private boolean advanceSearch;
  private int sportCenterTot;
  private int sportCenterOffLine;
  private int sportCenterConventioned;
  private String[] statusList;
  private String status;
  private boolean chkConventioned;
  private boolean chkReported;  
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

    this.matchTypeList = SportCenterManager.getAllMatchType();
    this.pitchCoverList = SportCenterManager.getAllPitchCover();

    this.statusList = new String[3];
    this.statusList[0] = Constants.SPORTCENTER_STATUS_ONLINE;
    this.statusList[1] = Constants.SPORTCENTER_STATUS_STANBY;
    this.statusList[2] = Constants.SPORTCENTER_STATUS_CANCELLATO;

    this.sportCenterTot = SportCenterManager.getCountAll(idCountryFilter);
    this.sportCenterOffLine = SportCenterManager.getCountAllByStatus(Constants.SPORTCENTER_STATUS_STANBY,idCountryFilter)
            + SportCenterManager.getCountAllByStatus(Constants.SPORTCENTER_STATUS_CANCELLATO,idCountryFilter);
    this.sportCenterConventioned = SportCenterManager.getCountAllConvetioned(idCountryFilter);
  }

  public String viewAll()
  {    
    this.advanceSearch = false;
    List<SportCenter> sportCenterList = SportCenterManager.getAllSportCenters(this.idCountry);
    UserContext.getInstance().setSportCenterReceiverList(sportCenterList);
    this.guiSportCenterList = GuiManager.buildGuiSportCenterList(sportCenterList);
    return SUCCESS;
  }

  public String searchSportCenters()
  {
    this.advanceSearch = true;
    List<SportCenter> sportCenterList = SportCenterManager.getAllByParameters(idCountry,
            idProvince,
            idCity,
            name,
            idMatchType,
            idPitchCover,
            status,
            chkConventioned);
    UserContext.getInstance().setSportCenterReceiverList(sportCenterList);
    this.guiSportCenterList = GuiManager.buildGuiSportCenterList(sportCenterList);
    return SUCCESS;
  }

  public String loadReceiverList()
  {
    if (!this.sendToAll)
    {
      UserContext.getInstance().setSportCenterReceiverList(SportCenterManager.getByIdSportCenterList(this.sportCenterCheckList));
    }
    this.receiverType = "sportCenters";
    return Constants.STRUTS_RESULT_NAME__SENDMESSAGES;
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
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
    return CountryManager.getAllWithSportCenters(idCountry);
  }

  public List<Province> getProvinceList()
  {
    return ProvinceManager.getAllWithSportCenters(idCountry);
  }

  public List<City> getCityList()
  {
    if (this.idProvince > 0)
    {
      return CityManager.getAllWithSportCenters(idProvince);
    }
    else
    {
      return new ArrayList<City>();
    }
  }

  /**
   * @return the matchTypelist
   */
  public List<MatchType> getMatchTypeList()
  {
    return matchTypeList;
  }

  /**
   * @return the pitchCoverList
   */
  public List<PitchCover> getPitchCoverList()
  {
    return pitchCoverList;
  }

  /**
   * @return the idPitchCover
   */
  public int getIdPitchCover()
  {
    return idPitchCover;
  }

  /**
   * @param idPitchCover the idPitchCover to set
   */
  public void setIdPitchCover(int idPitchCover)
  {
    this.idPitchCover = idPitchCover;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the idMatchType
   */
  public int getIdMatchType()
  {
    return idMatchType;
  }

  /**
   * @param idMatchType the idMatchType to set
   */
  public void setIdMatchType(int idMatchType)
  {
    this.idMatchType = idMatchType;
  }

  /**
   * @return the sportCenterTot
   */
  public int getSportCenterTot()
  {
    return sportCenterTot;
  }

  /**
   * @return the guiSportCenterList
   */
  public List<GuiSportCenter> getGuiSportCenterList()
  {
    return guiSportCenterList;
  }

  /**
   * @return the statusList
   */
  public String[] getStatusList()
  {
    return statusList;
  }

  /**
   * @return the status
   */
  public String getStatus()
  {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status)
  {
    this.status = status;
  }

  /**
   * @return the chkReported
   */
  public boolean isChkReported()
  {
    return chkReported;
  }

  /**
   * @param chkReported the chkReported to set
   */
  public void setChkReported(boolean chkReported)
  {
    this.chkReported = chkReported;
  }

  /**
   * @return the chkConventioned
   */
  public boolean isChkConventioned()
  {
    return chkConventioned;
  }

  /**
   * @param chkConventioned the chkConventioned to set
   */
  public void setChkConventioned(boolean chkConventioned)
  {
    this.chkConventioned = chkConventioned;
  }

  /**
   * @return the sportCenterOffLine
   */
  public int getSportCenterOffLine()
  {
    return sportCenterOffLine;
  }

  /**
   * @return the sportCenterConventioned
   */
  public int getSportCenterConventioned()
  {
    return sportCenterConventioned;
  }

  /**
   * @return the sportCenterCheckList
   */
  public List<Integer> getSportCenterCheckList()
  {
    return sportCenterCheckList;
  }

  /**
   * @param sportCenterCheckList the sportCenterCheckList to set
   */
  public void setSportCenterCheckList(List<Integer> sportCenterCheckList)
  {
    this.sportCenterCheckList = sportCenterCheckList;
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
   * @param receiverType the receiverType to set
   */
  public void setReceiverType(String receiverType)
  {
    this.receiverType = receiverType;
  }
  // </editor-fold>

  
}
