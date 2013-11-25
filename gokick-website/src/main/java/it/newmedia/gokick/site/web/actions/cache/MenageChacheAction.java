package it.newmedia.gokick.site.web.actions.cache;

import it.newmedia.gokick.cache.InfoCache;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.infos.SportCenterInfo;
import it.newmedia.gokick.site.infos.UserInfo;

/**
Classe contenente le azioni per cacnellare specifici elementi della cache
 *
 */
public class MenageChacheAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="MEMBERS">
  private int idUser;
  private int idMatch;
  private int idMatchComment;
  private int idSquad;
  private int idSportCenter;
  private String security;
  private String result;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="PUBLIC METHODS">
  @Override
  public String execute()
  {
    if (idUser > 0)
    {
      InfoProvider.removeUserInfo(idUser);
      String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__USER_INFO, idUser);
      //Check for data in cache...
      UserInfo userInfo = (UserInfo) InfoCache.getFromCache(keyOnCache);

      if (userInfo != null)
      {
        result = "KO";
      }
      else
      {
        result = "OK";
      }
    }
    if (idSportCenter > 0)
    {
      InfoProvider.removeSportCenterInfo(idSportCenter);
      String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__SPORT_CENTER_INFO, idSportCenter);
      //Check for data in cache...
      SportCenterInfo sportCenterInfo = (SportCenterInfo) InfoCache.getFromCache(keyOnCache);

      if (sportCenterInfo != null)
      {
        result = "KO";
      }
      else
      {
        result = "OK";
      }
    }
    return SUCCESS;
  }

  public String removeUserInfo()
  {
    InfoProvider.removeUserInfo(idUser);
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__USER_INFO, idUser);
    //Check for data in cache...
    UserInfo userInfo = (UserInfo) InfoCache.getFromCache(keyOnCache);

    if (userInfo != null)
    {
      result = "OK";
    }
    else
    {
      result = "KO";
    }

    return SUCCESS;
  }

  public String removeMatchInfo()
  {
    InfoProvider.removeMatchInfo(idMatch);
    return SUCCESS;
  }

  public String removeMatchCommentInfo()
  {
    InfoProvider.removeMatchCommentInfo(idMatchComment);
    return SUCCESS;
  }

  public String removeSquadInfo()
  {
    InfoProvider.removeSquadInfo(idSquad);
    return SUCCESS;
  }

  public String removeSportCenterInfo()
  {
    InfoProvider.removeSportCenterInfo(idSportCenter);
    return SUCCESS;
  }

  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="GETTERS/SETTERS">
  public void setIdUser(int idUser)
  {
    this.idUser = idUser;
  }

  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  public void setIdMatchComment(int idMatchComment)
  {
    this.idMatchComment = idMatchComment;
  }

  public void setIdSquad(int idSquad)
  {
    this.idSquad = idSquad;
  }

  public void setIdSportCenter(int idSportCenter)
  {
    this.idSportCenter = idSportCenter;
  }

  /**
   * @param security the security to set
   */
  public void setSecurity(String security)
  {
    this.security = security;
  }

  /**
   * @return the result
   */
  public String getResult()
  {
    return result;
  }

  /**
   * @param result the result to set
   */
  public void setResult(String result)
  {
    this.result = result;
  }
  // </editor-fold>
}//END CLASS

