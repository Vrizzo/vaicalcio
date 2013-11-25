package it.newmedia.gokick.backOffice.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.backOffice.guibean.GuiUserBean;
import it.newmedia.gokick.backOffice.manager.CityManager;
import it.newmedia.gokick.backOffice.manager.CountryManager;
import it.newmedia.gokick.backOffice.manager.GuiManager;
import it.newmedia.gokick.backOffice.manager.ProvinceManager;
import it.newmedia.gokick.backOffice.manager.UserManager;
import it.newmedia.gokick.data.enums.EnumPermissions;
import it.newmedia.gokick.data.hibernate.beans.City;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Province;
import it.newmedia.gokick.data.hibernate.beans.UsersPermission;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Classe contenente le azioni per gestire la ricerca di utenti 
 */
public class AdminSingleUserAction extends  ABaseActionSupport  implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idUser;
  private GuiUserBean guiUser;
  private User userToUpdate;
  private List<String> EnumUserStatusList;
  
  private int inviteUsed;
  private boolean pictureChk;
  private boolean sportCenterChk;
  private boolean challengeChk;
  private boolean linksChk;
  private boolean newsChk;
  private boolean blogChk;

  private int idCountryPicturePermission;
  private int idCountrySportCenterPermission;
  private int idProvinceSportCenterPermission;
  private int idCitySportCenterPermission;
  private int idCountryChallengePermission;
  private int idProvinceChallengePermission;
  private int idCityChallengePermission;
  private int idCountryLinksPermission;

  private int maxInvitations;
  
  private List<Country> countryList;
  


  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">

  @Override
  public void prepare()
  {
    this.EnumUserStatusList= new ArrayList<String>();
    this.getEnumUserStatusList().add(EnumUserStatus.Enabled.getValue());
    this.getEnumUserStatusList().add(EnumUserStatus.Pending.getValue());
    this.getEnumUserStatusList().add(EnumUserStatus.Cancelled.getValue());
    this.getEnumUserStatusList().add(EnumUserStatus.Deleted.getValue());

  }

  @Override
  public String input()
  {
    this.userToUpdate=UserManager.loadUserNoCache(this.idUser);

    
    for (UsersPermission usersPermission : this.userToUpdate.getUserPermissions())
    {
      this.loadPermissiontoShow(usersPermission);
    }
    
    guiUser= GuiManager.buildGuiUser(userToUpdate, Constants.STATISTIC_PERIOD__ALL);

    this.inviteUsed=UserManager.getInviteUsed(userToUpdate.getId());

    return SUCCESS;
  }

  public String update()
  {
    this.userToUpdate.setMaxInvitations(this.maxInvitations);
    UserManager.update(this.userToUpdate,this.loadPermissiontoSave());
    return Constants.STRUTS_RESULT_NAME__UPDATED;
    
  }

  public void loadPermissiontoShow(UsersPermission userPermission)
  {

    if (userPermission.getPermission().equals(EnumPermissions.Figurine.getValue()))
    {
      this.pictureChk=true;
      this.idCountryPicturePermission=userPermission.getIdCountry()!=null ? userPermission.getIdCountry() : 0;
    }
    else if (userPermission.getPermission().equals(EnumPermissions.Campi.getValue()))
    {
      this.sportCenterChk=true;
      this.idCountrySportCenterPermission=userPermission.getIdCountry()!=null ? userPermission.getIdCountry() : 0;
      this.idProvinceSportCenterPermission=userPermission.getIdProvince()!=null ? userPermission.getIdProvince() : 0;
      this.idCitySportCenterPermission=userPermission.getIdCity()!=null ? userPermission.getIdCity() : 0;

    }
    else if (userPermission.getPermission().equals(EnumPermissions.Tornei.getValue()))
    {
      this.challengeChk=true;
      this.idCountryChallengePermission=userPermission.getIdCountry()!=null ? userPermission.getIdCountry() : 0;
      this.idProvinceChallengePermission=userPermission.getIdProvince()!=null ? userPermission.getIdProvince() : 0;
      this.idCityChallengePermission=userPermission.getIdCity()!=null ? userPermission.getIdCity() : 0;
    }
    else if (userPermission.getPermission().equals(EnumPermissions.Links.getValue()))
    {
      this.linksChk=true;
      this.idCountryLinksPermission=userPermission.getIdCountry()!=null ? userPermission.getIdCountry() : 0;
    }
    else if (userPermission.getPermission().equals(EnumPermissions.News.getValue()))
      this.newsChk=true;
    else if (userPermission.getPermission().equals(EnumPermissions.Blog.getValue()))
      this.blogChk=true;

  }

  public List<UsersPermission> loadPermissiontoSave()
  {
    List<UsersPermission> usersPermissionsList = new ArrayList<UsersPermission>();
    UsersPermission usersPermission;
    if  (pictureChk)
    {
      usersPermission=new UsersPermission();
      usersPermission.setUser(this.userToUpdate);
      usersPermission.setPermission(EnumPermissions.Figurine.getValue());
      usersPermission.setIdCountry(this.idCountryPicturePermission!=0 ? this.idCountryPicturePermission : null);
      usersPermissionsList.add(usersPermission);
    }
    if  (sportCenterChk)
    {
      usersPermission= new UsersPermission();
      usersPermission.setUser(this.userToUpdate);
      usersPermission.setPermission(EnumPermissions.Campi.getValue());
      usersPermission.setIdCountry(this.idCountrySportCenterPermission!=0 ? this.idCountrySportCenterPermission : null);
      usersPermission.setIdProvince(this.idProvinceSportCenterPermission!=0 ? this.idProvinceSportCenterPermission : null);
      usersPermission.setIdCity(this.idCitySportCenterPermission!=0 ? this.idCitySportCenterPermission : null);
      usersPermissionsList.add(usersPermission);
    }
    if  (challengeChk)
    {
      usersPermission= new UsersPermission();
      usersPermission.setUser(this.userToUpdate);
      usersPermission.setPermission(EnumPermissions.Tornei.getValue());
      usersPermission.setIdCountry(this.idCountryChallengePermission!=0 ? this.idCountryChallengePermission : null);
      usersPermission.setIdProvince(this.idProvinceChallengePermission!=0 ? this.idProvinceChallengePermission : null);
      usersPermission.setIdCity(this.idCityChallengePermission!=0 ? this.idCityChallengePermission : null);
      usersPermissionsList.add(usersPermission);
    }
    if  (linksChk)
    {
      usersPermission= new UsersPermission();
      usersPermission.setUser(this.userToUpdate);
      usersPermission.setPermission(EnumPermissions.Links.getValue());
      usersPermission.setIdCountry(this.idCountryLinksPermission!=0 ? this.idCountryLinksPermission : null);
      usersPermissionsList.add(usersPermission);
    }
    if  (newsChk)
    {
      usersPermission= new UsersPermission();
      usersPermission.setUser(this.userToUpdate);
      usersPermission.setPermission(EnumPermissions.News.getValue());
      usersPermissionsList.add(usersPermission);
    }
    if  (blogChk)
    {
      usersPermission= new UsersPermission();
      usersPermission.setUser(this.userToUpdate);
      usersPermission.setPermission(EnumPermissions.Blog.getValue());
      usersPermissionsList.add(usersPermission);
    }



    return usersPermissionsList;
  }

  
  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >

  public List<Province> getProvinceSportCenterList()
  {
    if (idCountrySportCenterPermission > 0)
      return ProvinceManager.getAll(idCountrySportCenterPermission);
    return new ArrayList<Province>();
  }

  public List<Province> getProvinceChallengeList()
  {
    if (idCountryChallengePermission > 0)
      return ProvinceManager.getAll(idCountryChallengePermission);
    return new ArrayList<Province>();
  }

  public List<City> getCitySportCenterList()
  {
    if (idProvinceSportCenterPermission > 0)
      return CityManager.getAll(idProvinceSportCenterPermission);
    return new ArrayList<City>();
  }

  public List<City> getCityChallengeList()
  {
    if (idProvinceChallengePermission > 0)
      return CityManager.getAll(idProvinceChallengePermission);
    return new ArrayList<City>();
  }

  /**
   * @return the idUser
   */
  public int getIdUser()
  {
    return idUser;
  }

  /**
   * @param idUser the idUser to set
   */
  public void setIdUser(int idUser)
  {
    this.idUser = idUser;
  }
  
  /**
   * @return the EnumUserStatusList
   */
  public List<String> getEnumUserStatusList()
  {
    return EnumUserStatusList;
  }
  
  public GuiUserBean getGuiUser()
  {
    return guiUser;
  }

  /**
   * @param guiUser the guiUser to set
   */
  public void setGuiUser(GuiUserBean guiUser)
  {
    this.guiUser = guiUser;
  }
  
  /**
   * @return the inviteUsed
   */
  public int getInviteUsed()
  {
    return inviteUsed;
  }

  /**
   * @return the userToUpdate
   */
  public User getUserToUpdate()
  {
    return userToUpdate;
  }

  /**
   * @param userToUpdate the userToUpdate to set
   */
  public void setUserToUpdate(User userToUpdate)
  {
    this.userToUpdate = userToUpdate;
  }

  /**
   * @return the challengeChk
   */
  public boolean isChallengeChk()
  {
    return challengeChk;
  }

  /**
   * @param challengeChk the challengeChk to set
   */
  public void setChallengeChk(boolean challengeChk)
  {
    this.challengeChk = challengeChk;
  }

  /**
   * @return the linksChk
   */
  public boolean isLinksChk()
  {
    return linksChk;
  }

  /**
   * @param linksChk the linksChk to set
   */
  public void setLinksChk(boolean linksChk)
  {
    this.linksChk = linksChk;
  }

  /**
   * @return the newsChk
   */
  public boolean isNewsChk()
  {
    return newsChk;
  }

  /**
   * @param newsChk the newsChk to set
   */
  public void setNewsChk(boolean newsChk)
  {
    this.newsChk = newsChk;
  }

  /**
   * @return the pictureChk
   */
  public boolean isPictureChk()
  {
    return pictureChk;
  }

  /**
   * @param pictureChk the pictureChk to set
   */
  public void setPictureChk(boolean pictureChk)
  {
    this.pictureChk = pictureChk;
  }

  /**
   * @return the sportCenterChk
   */
  public boolean isSportCenterChk()
  {
    return sportCenterChk;
  }

  /**
   * @param sportCenterChk the sportCenterChk to set
   */
  public void setSportCenterChk(boolean sportCenterChk)
  {
    this.sportCenterChk = sportCenterChk;
  }

  /**
   * @return the idCountryPicturePermission
   */
  public int getIdCountryPicturePermission()
  {
    return idCountryPicturePermission;
  }

  /**
   * @return the idCountrySportCenterPermission
   */
  public int getIdCountrySportCenterPermission()
  {
    return idCountrySportCenterPermission;
  }

  /**
   * @return the idProvinceSportCenterPermission
   */
  public int getIdProvinceSportCenterPermission()
  {
    return idProvinceSportCenterPermission;
  }

  /**
   * @return the idCitySportCenterPermission
   */
  public int getIdCitySportCenterPermission()
  {
    return idCitySportCenterPermission;
  }

  /**
   * @return the idCountryChallengePermission
   */
  public int getIdCountryChallengePermission()
  {
    return idCountryChallengePermission;
  }

  /**
   * @return the idProvinceChallengePermission
   */
  public int getIdProvinceChallengePermission()
  {
    return idProvinceChallengePermission;
  }

  /**
   * @return the idCityChallengePermission
   */
  public int getIdCityChallengePermission()
  {
    return idCityChallengePermission;
  }

  /**
   * @return the idCountryLinksPermission
   */
  public int getIdCountryLinksPermission()
  {
    return idCountryLinksPermission;
  }
  /**
   * @param idCountryPicturePermission the idCountryPicturePermission to set
   */
  public void setIdCountryPicturePermission(int idCountryPicturePermission)
  {
    this.idCountryPicturePermission = idCountryPicturePermission;
  }

  /**
   * @param idCountrySportCenterPermission the idCountrySportCenterPermission to set
   */
  public void setIdCountrySportCenterPermission(int idCountrySportCenterPermission)
  {
    this.idCountrySportCenterPermission = idCountrySportCenterPermission;
  }

  /**
   * @param idProvinceSportCenterPermission the idProvinceSportCenterPermission to set
   */
  public void setIdProvinceSportCenterPermission(int idProvinceSportCenterPermission)
  {
    this.idProvinceSportCenterPermission = idProvinceSportCenterPermission;
  }

  /**
   * @param idCitySportCenterPermission the idCitySportCenterPermission to set
   */
  public void setIdCitySportCenterPermission(int idCitySportCenterPermission)
  {
    this.idCitySportCenterPermission = idCitySportCenterPermission;
  }

  /**
   * @param idCountryChallengePermission the idCountryChallengePermission to set
   */
  public void setIdCountryChallengePermission(int idCountryChallengePermission)
  {
    this.idCountryChallengePermission = idCountryChallengePermission;
  }

  /**
   * @param idProvinceChallengePermission the idProvinceChallengePermission to set
   */
  public void setIdProvinceChallengePermission(int idProvinceChallengePermission)
  {
    this.idProvinceChallengePermission = idProvinceChallengePermission;
  }

  /**
   * @param idCityChallengePermission the idCityChallengePermission to set
   */
  public void setIdCityChallengePermission(int idCityChallengePermission)
  {
    this.idCityChallengePermission = idCityChallengePermission;
  }

  /**
   * @param idCountryLinksPermission the idCountryLinksPermission to set
   */
  public void setIdCountryLinksPermission(int idCountryLinksPermission)
  {
    this.idCountryLinksPermission = idCountryLinksPermission;
  }

  /**
   * @return the countryList
   */
  public List<Country> getCountryList()
  {

    if (countryList==null)
      countryList = CountryManager.getAllCountryOrdered();
    return countryList;
  }
  /**
   * @return the blogChk
   */
  public boolean isBlogChk()
  {
    return blogChk;
  }

  /**
   * @param blogChk the blogChk to set
   */
  public void setBlogChk(boolean blogChk)
  {
    this.blogChk = blogChk;
  }

  /**
   * @return the maxInvitations
   */
  public int getMaxInvitations()
  {
    return maxInvitations;
  }

  /**
   * @param maxInvitations the maxInvitations to set
   */
  public void setMaxInvitations(int maxInvitations)
  {
    this.maxInvitations = maxInvitations;
  }

  
  // </editor-fold>
}
