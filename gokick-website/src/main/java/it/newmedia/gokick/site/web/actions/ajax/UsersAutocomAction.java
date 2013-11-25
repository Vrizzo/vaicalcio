package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;
import java.util.ArrayList;
import java.util.List;

/**
 Classe contenente le azioni per recuperare una lista di oggetti contenti id Provincia e nome, secondo i parametri specificati
 *
 */
public class UsersAutocomAction extends ABaseActionSupport {

  private int idMatch;
  private List<UserInfo> userInfoList;
  private String searchString;
  private int searchLength;

    @Override
  public void validate()
  {
    super.validate();
    //Ripulisco e ignoro gli errori che potrei aver ricevuto, per esempio problemi di conversione dei parametri in request.
    //Essendo interi varranno 0!!
    clearErrors();
  }

  @Override
  public String execute()
  {
    this.searchLength=searchString.trim().length();
    this.userInfoList = new ArrayList<UserInfo>();
    try
    {
      UserContext currentUser=UserContext.getInstance();
      if (this.idMatch> 0)
      {
        userInfoList=UserManager.getUserInfosForAutoComplete(idMatch,currentUser.getIdFirstSquad(),searchString,5,currentUser.getUser());
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
    return SUCCESS;
  }

  /**
   * @return the idMatch
   */
  public int getIdMatch()
  {
    return idMatch;
  }

  /**
   * @param idMatch the idMatch to set
   */
  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  /**
   * @return the userInfoList
   */
  public List<UserInfo> getUserInfoList()
  {
    return userInfoList;
  }

  /**
   * @return the searchString
   */
  public String getSearchString()
  {
    return searchString;
  }

  /**
   * @param searchString the searchString to set
   */
  public void setSearchString(String searchString)
  {
    this.searchString = searchString;
  }

  /**
   * @return the searchLength
   */
  public int getSearchLength()
  {
    return searchLength;
  }

}
