package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumSex;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.PlayerRoleInfo;
import it.newmedia.gokick.site.managers.CityManager;
import it.newmedia.gokick.site.managers.ProvinceManager;
import it.newmedia.gokick.site.managers.UtilManager;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.utils.DataValidator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente le azioni fatte dall'utente:inserimento in fase di registrazione e aggiornamento
 */
public class UserAction extends ABaseActionSupport implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private List<Country> countyList;

  private List<Province> provinceList;

  private List<City> cityList;

  private List<EnumSex> enumSexList;

  private String repeatPassword;

  private String newPassword;

  private String oldPassword;

  private int idCountry;

  private int idProvince;

  private int idCity;

  private int captchaValue1;

  private int captchaValue2;

  private Integer captchaValueSum;

  private boolean errorsPresent;

  private User userToUpdate;

  private User userToInsert;

  private String invitationErrorCode;

  private List<PlayerRoleInfo> playerRoleList;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  @Override
  public void prepare() throws Exception
  {
    User currentUser = this.getCurrentUser();

    /* Impostazione della nazione di default */
    if (currentUser.getCountry() != null)
    {
      this.idCountry = currentUser.getCountry().getId();
    }
    this.playerRoleList=AppContext.getInstance().getAllPlayerRoleInfo(getCurrentObjLanguage(), getCurrentCobrand());//UtilManager.getPlayerRoleNoCache(UserContext.getInstance().getLanguage());
  }

  @Override
  public String input()
  {
    if (this.isLoggedIn())
    {
      this.userToUpdate = UserManager.getById(this.getCurrentIdUser());
      this.idCountry = userToUpdate.getCountry().getId();
      this.idProvince = userToUpdate.getProvince().getId();
      this.idCity = userToUpdate.getCity().getId();
    }
    else
    {
      //Se è previsto l'utilizzo degli inviti e l'utente non ne ha uno mando a pagina di errore
      if (AppContext.getInstance().getInvitationsEnabled() && StringUtils.isBlank(this.getUserContext().getInvitationCode()))
      {
        this.invitationErrorCode = "invitationErrorMandatory";
        return Constants.STRUTS_RESULT_NAME__INFO;
      }
    }

    
    return INPUT;
  }

  public void validateInsert()
  {
    if( userToInsert == null )
      return;
    /* Validazione nome */
    if (StringUtils.isBlank(userToInsert.getFirstName()))
    {
      addFieldError("userToInsert.firstName", getText("error.firstName.required"));
    }
    else
    {
      userToInsert.setFirstName(userToInsert.getFirstName().trim());
      if ((userToInsert.getFirstName().length() < Constants.STRING_LENGHT_2) || (userToInsert.getFirstName().length() > Constants.STRING_LENGHT_20))
      {
        addFieldError("userToInsert.firstName", getText("error.firstName.invalidLenght"));
      }
      else if (!checkNameCharacters(userToInsert.getFirstName()))
      {
        addFieldError("userToInsert.firstName", getText("error.firstName.invalid"));
      }
    }

    /* Validazione ruolo */
    if (userToInsert.getPlayerRole().getId()==0)
    {
      addFieldError("userToInsert.playerRole", getText("error.role.required"));
    }
    

    /* Validazione cognome */
    if (StringUtils.isBlank(userToInsert.getLastName()))
    {
      addFieldError("userToInsert.lastName", getText("error.lastName.required"));
    }
    else
    {
      userToInsert.setLastName(userToInsert.getLastName().trim());
      if ((userToInsert.getLastName().length() < Constants.STRING_LENGHT_2) || (userToInsert.getLastName().length() > Constants.STRING_LENGHT_20))
      {
        addFieldError("userToInsert.lastName", getText("error.lastName.invalidLenght"));
      }
      else if (!checkNameCharacters(userToInsert.getLastName()))
      {
        addFieldError("userToInsert.lastName", getText("error.lastName.invalid"));
      }
    }

    /* Validazione cap */
    if (StringUtils.isBlank(userToInsert.getCap()))
    {
      addFieldError("userToInsert.cap", getText("error.cap.required"));
    }
    else
    {
      userToInsert.setCap(userToInsert.getCap().trim());
      if (userToInsert.getCap().length() > Constants.STRING_LENGHT_10)
      {
        addFieldError("userToInsert.cap", getText("error.cap.invalidLenght"));
      }
      else if (!StringUtils.isAlphanumeric(userToInsert.getCap()))
      {
        addFieldError("userToInsert.cap", getText("error.cap.invalid"));
      }
    }

    /* Validazione forum nickname */
//    if ((userToInsert.getForumNickname() == null) || (userToInsert.getForumNickname().length() == Constants.STRING_LENGHT_0))
//    {
//      addFieldError("userToInsert.forumNickname", getText("error.forumNickname.required"));
//    }
//    else
//    {
//      userToInsert.setForumNickname(userToInsert.getForumNickname().trim());
//      if ((userToInsert.getForumNickname().length() < Constants.STRING_LENGHT_2) || (userToInsert.getForumNickname().length() > Constants.STRING_LENGHT_15))
//      {
//        addFieldError("userToInsert.forumNickname", getText("error.forumNickname.invalidLenght"));
//      }
//      else if (!checkNameCharacters(userToInsert.getForumNickname(), true))
//      {
//        addFieldError("userToInsert.forumNickname", getText("error.forumNickname.invalid"));
//      }
//      else
//      {
//        boolean exist = UtilManager.checkExistingForumNickname(userToInsert.getForumNickname(), -1);
//        if (exist)
//        {
//          addFieldError("currenuserToInserttUser.forumNickname", getText("error.forumNickname.notunique"));
//        }
//      }
//    }

    /* Validazione nazione */
    if (this.idCountry <= Constants.INVALID_ID)
    {
      addFieldError("userToInsert.country", getText("error.country.required"));
    }

    /* Validazione provincia */
    if (this.idProvince <= Constants.INVALID_ID)
    {
      addFieldError("userToInsert.province", getText("error.province.required"));
    }

    /* Validazione città */
    if (this.idCity <= Constants.INVALID_ID)
    {
      addFieldError("userToInsert.city", getText("error.city.required"));
    }

    /* Validazione email */
    if (StringUtils.isBlank(userToInsert.getEmail()))
    {
      addFieldError("userToInsert.email", getText("error.email.required"));
    }
    else
    {
      if (!DataValidator.checkEmail(userToInsert.getEmail()))
      {
        addFieldError("userToInsert.email", getText("error.email.invalid"));
      }
      else
      {
        boolean exist = UtilManager.checkExistingEmail(userToInsert.getEmail(), -1);
        if (exist)
        {
          addFieldError("userToInsert.email", getText("error.email.notunique"));
        }
      }
    }

    /* Validazione password */
    if (StringUtils.isBlank(userToInsert.getPassword()))
    {
      addFieldError("userToInsert.password", getText("error.password.required"));
    }
    else
    {
      if (userToInsert.getPassword().length() < Constants.STRING_LENGHT_6 || userToInsert.getPassword().length() > Constants.STRING_LENGHT_20)
      {
        addFieldError("userToInsert.password", getText("error.password.invalid"));
      }
    }

    /* Validazione conferma password */
    if (StringUtils.isBlank(this.repeatPassword))
    {
      addFieldError("repeatPassword", getText("error.repeatPassword.required"));
    }
    else
    {
      if (!this.repeatPassword.equals(userToInsert.getPassword()))
      {
        addFieldError("repeatPassword", getText("error.repeatPassword.invalid"));
      }
    }

    /* Validazione accettazione termini generali */
    if (!userToInsert.getGeneralTermsAccepted())
    {
      addFieldError("userToInsert.generalTermsAccepted", getText("error.generalTermsAccepted.required"));
    }

    /* Validazione accettazione privacy */
    if (!userToInsert.getPrivacyAccepted())
    {
      addFieldError("userToInsert.privacyAccepted", getText("error.privacyAccepted.required"));
    }

    /* Validazione captcha */
    if ((this.captchaValueSum == null) || ((this.captchaValue1 + this.captchaValue2) != this.captchaValueSum))
    {
      addFieldError("captchaValueSum", getText("errorCaptchaValueSum"));
    }

    /*validazione cell*/
    if (StringUtils.isNotBlank(this.userToInsert.getMobile()))
    {
      if (!checkMobileCharacters(this.userToInsert.getMobile()))
      {
        addFieldError("mobile", getText("error.mobile.invalid"));
      }
    }

    if (this.hasErrors())
    {
      if ((this.captchaValueSum != null) && ((this.captchaValue1 + this.captchaValue2) == this.captchaValueSum))
      {
        addFieldError("captchaValueSum", getText("errorCaptchaValueSum"));
      }
      this.errorsPresent = true;
    }
    else
    {
      this.errorsPresent = false;
    }

    this.captchaValue1 = (int) (Math.random() * 10) + 1;
    this.captchaValue2 = (int) (Math.random() * 10) + 1;
    this.captchaValueSum = null;
  }

  public void validateUpdate()
  {
    if( userToUpdate == null )
      return;
    //User currentUser = this.getCurrentUser();
     /* Validazione nome */
    if ((userToUpdate.getFirstName() == null) || (userToUpdate.getFirstName().length() == Constants.STRING_LENGHT_0))
    {
      addFieldError("userToUpdate.firstName", getText("error.firstName.required"));
    }
    else
    {
      userToUpdate.setFirstName(userToUpdate.getFirstName().trim());
      if ((userToUpdate.getFirstName().length() < Constants.STRING_LENGHT_2) || (userToUpdate.getFirstName().length() > Constants.STRING_LENGHT_20))
      {
        addFieldError("userToUpdate.firstName", getText("error.firstName.invalidLenght"));
      }
      else if (!checkNameCharacters(userToUpdate.getFirstName()))
      {
        addFieldError("userToUpdate.firstName", getText("error.firstName.invalid"));
      }
    }

    /* Validazione cognome */
    if ((userToUpdate.getLastName() == null) || (userToUpdate.getLastName().length() == Constants.STRING_LENGHT_0))
    {
      addFieldError("userToUpdate.lastName", getText("error.lastName.required"));
    }
    else
    {
      userToUpdate.setLastName(userToUpdate.getLastName().trim());
      if ((userToUpdate.getLastName().length() < Constants.STRING_LENGHT_2) || (userToUpdate.getLastName().length() > Constants.STRING_LENGHT_20))
      {
        addFieldError("userToUpdate.lastName", getText("error.lastName.invalidLenght"));
      }
      else if (!checkNameCharacters(userToUpdate.getLastName()))
      {
        addFieldError("userToUpdate.lastName", getText("error.lastName.invalid"));
      }
    }

    /* Validazione cap */
    if ((userToUpdate.getCap() == null) || (userToUpdate.getCap().length() == Constants.STRING_LENGHT_0))
    {
      addFieldError("userToUpdate.cap", getText("error.cap.required"));
    }
    else
    {
      userToUpdate.setCap(userToUpdate.getCap().trim());
      if (userToUpdate.getCap().length() > Constants.STRING_LENGHT_10)
      {
        addFieldError("userToUpdate.cap", getText("error.cap.invalidLenght"));
      }
      else if (!StringUtils.isAlphanumeric(userToUpdate.getCap()))
      {
        addFieldError("userToUpdate.cap", getText("error.cap.invalid"));
      }
    }

    /* Validazione forum nickname */

//    if ((userToUpdate.getForumNickname() == null) || (userToUpdate.getForumNickname().length() == Constants.STRING_LENGHT_0))
//    {
//      addFieldError("userToUpdate.forumNickname", getText("error.forumNickname.required"));
//    }
//    else
//    {
//      userToUpdate.setForumNickname(userToUpdate.getForumNickname().trim());
//      if ((userToUpdate.getForumNickname().length() < Constants.STRING_LENGHT_2) || (userToUpdate.getForumNickname().length() > Constants.STRING_LENGHT_15))
//      {
//        addFieldError("userToUpdate.forumNickname", getText("error.forumNickname.invalidLenght"));
//      }
//      else if (!checkNameCharacters(userToUpdate.getForumNickname()))
//      {
//        addFieldError("userToUpdate.forumNickname", getText("error.forumNickname.invalid"));
//      }
//      else
//      {
//        boolean exist = UtilManager.checkExistingForumNickname(userToUpdate.getForumNickname(), userToUpdate.getId());
//        if (exist)
//        {
//          addFieldError("userToUpdate.forumNickname", getText("error.forumNickname.notunique"));
//        }
//      }
//    }

    /* Validazione nazione */
    if (this.idCountry <= Constants.INVALID_ID)
    {
      addFieldError("userToUpdate.country", getText("error.country.required"));
    }

    /* Validazione provincia */
    if (this.idProvince <= Constants.INVALID_ID)
    {
      addFieldError("userToUpdate.province", getText("error.province.required"));
    }

    /* Validazione città */
    if (this.idCity <= Constants.INVALID_ID)
    {
      addFieldError("userToUpdate.city", getText("error.city.required"));
    }

    /* Validazione email */
    if ((userToUpdate.getEmail() == null) || (userToUpdate.getEmail().length() == Constants.STRING_LENGHT_0))
    {
      addFieldError("userToUpdate.email", getText("error.email.required"));
    }
    else
    {
      if (!DataValidator.checkEmail(userToUpdate.getEmail()))
      {
        addFieldError("userToUpdate.email", getText("error.email.invalid"));
      }
      else
      {
        boolean exist = UtilManager.checkExistingEmail(userToUpdate.getEmail(), userToUpdate.getId());
        if (exist)
        {
          addFieldError("userToUpdate.email", getText("error.email.notunique"));
        }
      }
    }

    /* Validazione password */
    if (!(newPassword.equals("") && repeatPassword.equals("")))
    {
      boolean exist = UtilManager.checkUserPassword(this.oldPassword, userToUpdate.getId());
      if (!exist)
      {
        addFieldError("oldPassword", getText("error.password.errata"));
      }

      /* Validazione conferma password */
      if ((this.newPassword.equals("")) || (this.newPassword.length() == Constants.STRING_LENGHT_0))
      {
        addFieldError("newPassword", getText("error.password.required"));
      }
      else
      {
        if (this.newPassword.equals(oldPassword))
        {
          addFieldError("newPassword", getText("error.password.used"));
        }
        if (this.newPassword.length() < Constants.STRING_LENGHT_6 || this.newPassword.length() > Constants.STRING_LENGHT_20)
        {
          addFieldError("newPassword", getText("error.password.invalid"));
        }
      }
      if (!this.repeatPassword.equals(this.newPassword))
      {
        addFieldError("repeatPassword", getText("error.repeatPassword.invalid"));
      }
    }

    /*validazione cell*/
    if ((this.userToUpdate.getMobile() != null) && (this.userToUpdate.getMobile().length() > Constants.STRING_LENGHT_0))
    {
      if (!checkMobileCharacters(this.userToUpdate.getMobile()))
      {
        addFieldError("mobile", getText("error.mobile.invalid"));
      }
    }


    if (this.hasErrors())
    {
      this.errorsPresent = true;
    }
    else
    {
      this.errorsPresent = false;
    }


  }

  public String insert()
  {
    //Se è previsto l'utilizzo degli inviti e l'utente non ne ha uno mando a pagina di errore
    if (AppContext.getInstance().getInvitationsEnabled() && StringUtils.isBlank(this.getUserContext().getInvitationCode()))
    {
      this.invitationErrorCode = "invitationErrorMandatory";
      return Constants.STRUTS_RESULT_NAME__INFO;
    }
    if( userToInsert == null )
      return Constants.STRUTS_RESULT_NAME__GOTO_INPUT;
    String idFacebook = UserContext.getInstance().getFacebookIdUserTemporary();
    String accessTokenFacebook = UserContext.getInstance().getTemporaryAccessTokenFacebook();
    boolean success = UserManager.insert(userToInsert, this.idCountry, this.idProvince, this.idCity, getCurrentObjLanguage(), getCurrentCobrand(), this.getUserContext().getInvitationCode(), idFacebook, accessTokenFacebook);
    if (!success)
    {
      UserContext.getInstance().reset();
      return ERROR;
    }
    UserContext.getInstance().setFacebookIdUserTemporary("");
    return SUCCESS;
  }

  public String update()
  {
    if( userToUpdate == null )
      return Constants.STRUTS_RESULT_NAME__GOTO_INPUT;
    boolean success = UserManager.update(userToUpdate, this.idCountry, this.idProvince, this.idCity, this.newPassword);
    if (!success)
    {
      UserContext.getInstance().reset();
      return ERROR;
    }
    String message = getText("message.modificheSalvate");
    UserContext.getInstance().setLastMessage(message);
    return Constants.STRUTS_RESULT_NAME__ACCOUNT_UPDATED;
  }

  private boolean checkNameCharacters(String name)
  {
    return checkNameCharacters(name, false);
  }

  private boolean checkNameCharacters(String name, boolean acceptNumber)
  {
    String character;

    for (int i = 0; i < name.length(); i++)
    {
      character = String.valueOf(name.charAt(i));
      if (!StringUtils.isAlphaSpace(character) && !character.equalsIgnoreCase("'"))
      {
        if (acceptNumber && StringUtils.isNumeric(character))
        {
          continue;
        }
        return false;
      }
    }

    return true;
  }

  private boolean checkMobileCharacters(String mobile)
  {
    String character;

    for (int i = 0; i < mobile.length(); i++)
    {
      character = String.valueOf(mobile.charAt(i));
      if (!StringUtils.isNumeric(character) && !character.equalsIgnoreCase("+"))
      {
        return false;
      }
    }

    return true;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public List<Country> getCountyList()
  {
    if (this.countyList == null || this.countyList.size() == 0)
    {
      this.countyList = AppContext.getInstance().getAllCountry();
    }
    return countyList;
  }

  public List<Province> getProvinceList()
  {
    if (this.idCountry > 0)
    {
      if (this.provinceList == null || this.provinceList.size() == 0)
      {
        this.provinceList = ProvinceManager.getAll(idCountry);
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
    if (this.idProvince > 0)
    {
      if (this.cityList == null || this.cityList.size() == 0)
      {
        this.cityList = CityManager.getAll(idProvince);
      }
    }
    else
    {
      this.cityList = new ArrayList<City>();
    }
    return cityList;
  }

  public List<EnumSex> getEnumSexList()
  {
    return enumSexList;
  }

  public String getRepeatPassword()
  {
    return repeatPassword;
  }

  public void setRepeatPassword(String repeatPassword)
  {
    this.repeatPassword = repeatPassword;
  }

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

  public int getCaptchaValue1()
  {
    if (this.captchaValue1 == 0)
    {
      this.captchaValue1 = (int) (Math.random() * 10) + 1;
    }
    return captchaValue1;
  }

  public void setCaptchaValue1(int captchaValue1)
  {
    this.captchaValue1 = captchaValue1;
  }

  public int getCaptchaValue2()
  {
    if (this.captchaValue2 == 0)
    {
      this.captchaValue2 = (int) (Math.random() * 10) + 1;
    }
    return captchaValue2;
  }

  public void setCaptchaValue2(int captchaValue2)
  {
    this.captchaValue2 = captchaValue2;
  }

  public Integer getCaptchaValueSum()
  {
    return captchaValueSum;
  }

  public void setCaptchaValueSum(Integer captchaValueSum)
  {
    this.captchaValueSum = captchaValueSum;
  }

  public boolean isErrorsPresent()
  {
    return errorsPresent;
  }

  public void setErrorsPresent(boolean errorsPresent)
  {
    this.errorsPresent = errorsPresent;
  }

  public String getNewPassword()
  {
    return newPassword;
  }

  public void setNewPassword(String newPassword)
  {
    this.newPassword = newPassword;
  }

  public String getOldPassword()
  {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword)
  {
    this.oldPassword = oldPassword;
  }

  public User getUserToUpdate()
  {
    return userToUpdate;
  }

  public void setUserToUpdate(User userToUpdate)
  {
    this.userToUpdate = userToUpdate;
  }

  public User getUserToInsert()
  {
    return userToInsert;
  }

  public void setUserToInsert(User userToInsert)
  {
    this.userToInsert = userToInsert;
  }

  public String getInvitationErrorCode()
  {
    return invitationErrorCode;
  }
  
  /**
   * @return the playerRoleList
   */
  public List<PlayerRoleInfo> getPlayerRoleList()
  {
    return playerRoleList;
  }

  // </editor-fold>
}
