package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.FootballTeam;
import it.newmedia.gokick.data.hibernate.beans.PhysicalCondition;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.PlayerRoleInfo;
import it.newmedia.gokick.site.managers.CityManager;
import it.newmedia.gokick.site.managers.CountryManager;
import it.newmedia.gokick.site.managers.ProvinceManager;
import it.newmedia.gokick.site.managers.UserManager;
import org.apache.commons.lang.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Classe contenente le azioni fatte dall'utente aggiornando informazioni nella scheda personale
 */
public class UserPlayerAction extends AuthenticationBaseAction implements Preparable {
  

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  //private static Logger logger = Logger.getLogger(UserPlayerAction.class);
  private User userPlayerToUpdate;
  private int idCountry;
  private int idProvince;
  private int idCity;
  private int idBirthdayCountry;
  private int idBirthdayProvince;
  private int idBirthdayCity;
  private int idNationalityCountry;
  private int bornDay;
  private int bornMonth;
  private int bornYear;
  private String picStatus;
  private String labelPicStatus;
  private boolean errorsPresent;
  private List<Country> natCountryList;
  private List<Province> provinceList;
  private List<City> cityList;
  private List<Integer> bornDayList;
  private List<Integer> bornYearList;
  private List<Integer> heightList;
  private List<Integer> weightList;
  private List<Integer> shirtNumberList;
  private List<FootballTeam> footballTeamList;
  private int idFootballteam;
  


  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public void prepare() throws Exception
  {
    User currentUser = this.getCurrentUser();
    /* Impostazione della nazione di default */
    if (currentUser.getCountry() != null)
    {
      this.setIdCountry((int) currentUser.getCountry().getId());
    }

    this.natCountryList=CountryManager.getAllCountry();

  }

  @Override
  public String input()
  {
    this.userPlayerToUpdate = UserManager.getById(this.getCurrentIdUser());

    try
    {
      //this.footballTeamList = DAOFactory.getInstance().getFootballTeamDAO().findAll();
      this.footballTeamList = DAOFactory.getInstance().getFootballTeamDAO().getAllOrdered();
    }
    catch (Exception ex)
    {
      logger.debug("error retriving FootballTeams",ex);
    }
    
    //this.idFootballteam = userPlayerToUpdate.getFootballTeam()!=null?userPlayerToUpdate.getFootballTeam().getId():0;

    this.setIdCountry(userPlayerToUpdate.getCountry().getId());
    this.setIdProvince((int) userPlayerToUpdate.getProvince().getId());
    this.setIdCity((int) userPlayerToUpdate.getCity().getId());

    this.setIdBirthdayCountry(userPlayerToUpdate.getBirthdayCountry() != null ? userPlayerToUpdate.getBirthdayCountry().getId() : 0);
    this.setIdBirthdayProvince((userPlayerToUpdate.getBirthdayProvince() != null ? userPlayerToUpdate.getBirthdayProvince().getId() : 0));
    this.setIdBirthdayCity((userPlayerToUpdate.getBirthdayCity() != null ? userPlayerToUpdate.getBirthdayCity().getId() : 0));

    this.setIdNationalityCountry((userPlayerToUpdate.getNationalityCountry() != null ? userPlayerToUpdate.getNationalityCountry().getId() : 0));

    if (this.userPlayerToUpdate.getPhysicalCondition() == null) //utenti anteriori al 18/11/09 potrebbe non avere la cond impostata
    {
      //TODO: ERRORE! Non usare id in questa maniera! Fare metodo che ritorna condizione di default in appcontext!
      PhysicalCondition physicalCondition = new PhysicalCondition();
      physicalCondition.setId(3);
      userPlayerToUpdate.setPhysicalCondition(physicalCondition);
    }
    
//    if (this.userPlayerToUpdate.getPlayerTitle().trim().equals(""))
//            this.userPlayerToUpdate.setPlayerTitle(this.getText(Constants.LABEL_USERPLAYER_ES_TITLESCHEDA));
//    if (this.userPlayerToUpdate.getPlayerMainFeature().trim().equals(""))
//            this.userPlayerToUpdate.setPlayerMainFeature(this.getText(Constants.LABEL_USERPLAYER_ES_CARATTERISTICA));
           
    if (userPlayerToUpdate.getBirthDay() != null)
    {
      Date dateBirth = userPlayerToUpdate.getBirthDay();
      GregorianCalendar birtGc = (GregorianCalendar) GregorianCalendar.getInstance();
      birtGc.setTime(dateBirth);
      this.bornDay = birtGc.get(GregorianCalendar.DAY_OF_MONTH);
      this.bornMonth = birtGc.get(GregorianCalendar.MONTH);
      this.bornYear = birtGc.get(GregorianCalendar.YEAR);
    }
    else
    {
      this.bornDay = -1;
      this.bornMonth = -1;
      this.bornYear = -1;
    }

    PictureCard pictureCard = new PictureCard();
    pictureCard = UserManager.getLastPictureCard(this.getCurrentIdUser());

    this.labelPicStatus = this.getText("label.info.pictureUndefined");
    this.picStatus = EnumPictureCardStatus.Undefined.getValue();
    if (pictureCard != null)
    {
      this.picStatus = pictureCard.getEnumPictureCardStatus().getValue();

      if (this.picStatus.equals(EnumPictureCardStatus.Current.getValue()))
      {
        this.labelPicStatus = this.getText("label.info.pictureCurrent");
      }
      else if (this.picStatus.equals(EnumPictureCardStatus.Pending.getValue()))
      {
        this.labelPicStatus = this.getText("label.info.picturePending");
      }
      else if (this.picStatus.equals(EnumPictureCardStatus.Refused.getValue()))
      {
        this.labelPicStatus = this.getText("label.info.pictureRefused");
      }
    }
    return INPUT;
  }

  @Override
  public void validate()
  {
    //User currentUser = this.getCurrentUser();
     /* Validazione nome */
//    if ((userPlayerToUpdate.getFirstName() == null) || (userPlayerToUpdate.getFirstName().length() == Constants.STRING_LENGHT_0))
//    {
//      addFieldError("userToUpdate.firstName", getText("error.firstName.required"));
//    }
//    else
//    {
//      userPlayerToUpdate.setFirstName(userPlayerToUpdate.getFirstName().trim());
//      if ((userPlayerToUpdate.getFirstName().length() < Constants.STRING_LENGHT_2) || (userPlayerToUpdate.getFirstName().length() > Constants.STRING_LENGHT_20))
//      {
//        addFieldError("userToUpdate.firstName", getText("error.firstName.invalidLenght"));
//      }
//      else if (!checkNameCharacters(userPlayerToUpdate.getFirstName()))
//      {
//        addFieldError("userToUpdate.firstName", getText("error.firstName.invalid"));
//      }
//    }
//    if (this.hasErrors())
//    {
//      this.errorsPresent = true;
//    }
//    else
//    {
//      this.errorsPresent = false;
//    }
  }

  public String update()
  {

    Date birthToModify = null;
    if (this.bornYear != -1 && this.bornMonth != -1 && this.bornDay != -1)
    {
      GregorianCalendar gcBirthToModify = new GregorianCalendar(this.bornYear, this.bornMonth, this.bornDay);
      birthToModify = gcBirthToModify.getTime();
    }
    
    if (this.userPlayerToUpdate.getPlayerTitle().trim().equals(this.getText(Constants.LABEL_USERPLAYER_ES_TITLESCHEDA)))
      this.userPlayerToUpdate.setPlayerTitle("");
    if (this.userPlayerToUpdate.getPlayerMainFeature().trim().equals(this.getText(Constants.LABEL_USERPLAYER_ES_CARATTERISTICA)))
      this.userPlayerToUpdate.setPlayerMainFeature("");

    boolean success = UserManager.userPlayerUpdate(this.userPlayerToUpdate, this.idBirthdayCountry, this.idBirthdayProvince, this.idBirthdayCity, birthToModify, this.idNationalityCountry);
    if (!success)
    {
      UserContext.getInstance().reset();
      return ERROR;
    }
    String message = getText("message.schedaSalvata");
    UserContext.getInstance().setLastMessage(message);
    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public List<Integer> getShirtNumberList()
  {
    if (this.shirtNumberList == null)
    {
      this.shirtNumberList = new ArrayList<Integer>();

      for (int i = 1; i < 100; i++)
      {
        shirtNumberList.add(i);
      }
    }
    return shirtNumberList;
  }

  public List<Integer> getWeightList()
  {
    if (this.weightList == null)
    {
      this.weightList = new ArrayList<Integer>();

      for (int i = 40; i < 121; i++)
      {
        weightList.add(i);
      }
    }
    return weightList;
  }

  public List<Integer> getHeightList()
  {
    if (this.heightList == null)
    {
//      BigDecimal bd=new BigDecimal(1.39);
//      BigDecimal bdAdd=new BigDecimal(0.01);
      this.heightList = new ArrayList<Integer>();

      for (int i = 140; i < 211; i++)
      {
//        bd=bd.add(bdAdd);
//        bd = bd.round(new MathContext(3, RoundingMode.HALF_UP));
        heightList.add(i);
      }
    }
    return heightList;
  }

  public List<Integer> getBornDayList()
  {
    if (this.bornDayList == null)
    {
      this.bornDayList = new ArrayList<Integer>();
      for (int i = 1; i < 32; i++)
      {
        bornDayList.add(i);
      }
    }
    return bornDayList;
  }

//  public List<String> getBornMonthList()
//  {
//    if (this.bornMonthList == null )
//    {
//      this.bornMonthList = new ArrayList<String>();
//      bornMonthList.add("Gennaio");
//      bornMonthList.add("Febbraio");
//      bornMonthList.add("Marzo");
//      bornMonthList.add("Aprile");
//      bornMonthList.add("Maggio");
//      bornMonthList.add("Giugno");
//      bornMonthList.add("Luglio");
//      bornMonthList.add("Agosto");
//      bornMonthList.add("Settembre");
//      bornMonthList.add("Ottobre");
//      bornMonthList.add("Novembre");
//      bornMonthList.add("Dicembre");
//    }
//    return bornMonthList;
//  }
  public List<Integer> getBornYearList()
  {
    if (this.bornYearList == null)
    {
      this.bornYearList = new ArrayList<Integer>();
      for (int i = 30; i < (new Date().getYear()) - 17; i++)
      {
        bornYearList.add(1900 + i);
      }
    }
    return bornYearList;
  }

  public List<Country> getNatCountryList()
  {
    
    return natCountryList;
  }

  public List<Province> getProvinceList()
  {
    if (this.idBirthdayCountry > 0)
    {
      if (this.provinceList == null || this.provinceList.size() == 0)
      {
        this.provinceList = ProvinceManager.getAll(idBirthdayCountry);
      }
    }
    else
    {
      this.provinceList = new ArrayList<Province>();
    }
    return provinceList;
  }

  public List<City> getCityList()
  {
    if (this.idBirthdayProvince > 0)
    {
      if (this.cityList == null || this.cityList.size() == 0)
      {
        this.cityList = CityManager.getAll(idBirthdayProvince);
      }
    }
    else
    {
      this.cityList = new ArrayList<City>();
    }
    return cityList;
  }

  /**
   * @return the userPlayerToUpdate
   */
  public User getUserPlayerToUpdate()
  {
    return userPlayerToUpdate;
  }

  /**
   * @param userPlayerToUpdate the userPlayerToUpdate to set
   */
  public void setUserPlayerToUpdate(User userPlayerToUpdate)
  {
    this.userPlayerToUpdate = userPlayerToUpdate;
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
   * @return the errorPresent
   */
  public boolean isErrorsPresent()
  {
    return errorsPresent;
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

  /**
   * @return the idBornCountry
   */
  public int getIdBirthdayCountry()
  {
    return idBirthdayCountry;
  }

  /**
   * @param idBornCountry the idBornCountry to set
   */
  public void setIdBirthdayCountry(int idBirthdayCountry)
  {
    this.idBirthdayCountry = idBirthdayCountry;
  }

  /**
   * @return the idBornProvince
   */
  public int getIdBirthdayProvince()
  {
    return idBirthdayProvince;
  }

  /**
   * @param idBornProvince the idBornProvince to set
   */
  public void setIdBirthdayProvince(int idBirthdayProvince)
  {
    this.idBirthdayProvince = idBirthdayProvince;
  }

  /**
   * @return the idBornCity
   */
  public int getIdBirthdayCity()
  {
    return idBirthdayCity;
  }

  /**
   * @param idBornCity the idBornCity to set
   */
  public void setIdBirthdayCity(int idBirthdayCity)
  {
    this.idBirthdayCity = idBirthdayCity;
  }

  /**
   * @return the bornDay
   */
  public int getBornDay()
  {
    return bornDay;
  }

  /**
   * @param bornDay the bornDay to set
   */
  public void setBornDay(int bornDay)
  {
    this.bornDay = bornDay;
  }

  /**
   * @return the bornMonth
   */
  public int getBornMonth()
  {
    return bornMonth;
  }

  /**
   * @param bornMonth the bornMonth to set
   */
  public void setBornMonth(int bornMonth)
  {
    this.bornMonth = bornMonth;
  }

  /**
   * @return the bornYear
   */
  public int getBornYear()
  {
    return bornYear;
  }

  /**
   * @param bornYear the bornYear to set
   */
  public void setBornYear(int bornYear)
  {
    this.bornYear = bornYear;
  }

  /**
   * @return the picStatus
   */
  public String getPicStatus()
  {
    return picStatus;
  }

  /**
   * @return the labelPicStatus
   */
  public String getLabelPicStatus()
  {
    return labelPicStatus;
  }

  /**
   * @return the idNationalityCountry
   */
  public int getIdNationalityCountry()
  {
    return idNationalityCountry;
  }

  /**
   * @param idNationalityCountry the idNationalityCountry to set
   */
  public void setIdNationalityCountry(int idNationalityCountry)
  {
    this.idNationalityCountry = idNationalityCountry;
  }
  /**
   * @return the footballTeamList
   */
  public List<FootballTeam> getFootballTeamList()
  {
    return footballTeamList;
  }
  /**
   * @return the idFootballteam
   */
  public int getIdFootballteam()
  {
    return idFootballteam;
  }

  /**
   * @param idFootballteam the idFootballteam to set
   */
  public void setIdFootballteam(int idFootballteam)
  {
    this.idFootballteam = idFootballteam;
  }
 
  // </editor-fold>
}
