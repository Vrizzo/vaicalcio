package it.newmedia.gokick.backOffice;

import com.opensymphony.xwork2.ActionContext;
import it.newmedia.gokick.backOffice.manager.CountryManager;
import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.data.hibernate.beans.User;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.StrutsStatics;

/**
 *
 * E' l'oggetto che rappresenta il contesto dell'utente all'interno della propria sessione di lavoro
 */
public class UserContext
{
  public static final String FRANCE = "france";
  public static final int ID_COUNTRY_FILTER_FRANCE = 75;

  //<editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static final String KEY_ON_SESSION__USER_CONTEXT = "KEY_ON_SESSION__USER_CONTEXT";
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members - Getters/Setters --">
  private List<User> userReceiverList;
  private List<SportCenter> sportCenterReceiverList;
  private List<Match> matchReceiverList;
  private int idCountryFilter;
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   * Crea una nuova istanza di UserContext inizializzando i suoi membri
   */
  private UserContext()
  {
    HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().getActionInvocation().getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
    
    if(request.isUserInRole(FRANCE))
    {
      this.idCountryFilter=ID_COUNTRY_FILTER_FRANCE;
    }
      
    else
      this.idCountryFilter=0; 
  }
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  public static UserContext getInstance()
  {
    
    Map session = ActionContext.getContext().getSession();
    UserContext userContext = (UserContext) session.get(KEY_ON_SESSION__USER_CONTEXT);
    if (userContext == null)
    {
      userContext = new UserContext();
      session.put(KEY_ON_SESSION__USER_CONTEXT, userContext);
    }
    return userContext;
  }

  // </editor-fold>
  
  //<editor-fold defaultstate="collapsed" desc="Getters/Setters ">
  
  /**
   * @return the userReceiverList
   */
  public List<User> getUserReceiverList()
  {
    return userReceiverList;
  }
  
  public int getIdCountryFilter()
  {
    return idCountryFilter;
  }


  /**
   * @param userReceiverList the userReceiverList to set
   */
  public void setUserReceiverList(List<User> userReceiverList)
  {
    this.userReceiverList = userReceiverList;
  }

  /**
   * @return the sportCenterReceiverList
   */
  public List<SportCenter> getSportCenterReceiverList()
  {
    return sportCenterReceiverList;
  }

  /**
   * @param sportCenterReceiverList the sportCenterReceiverList to set
   */
  public void setSportCenterReceiverList(List<SportCenter> sportCenterReceiverList)
  {
    this.sportCenterReceiverList = sportCenterReceiverList;
  }
  
  /**
   * @return the matchReceiverList
   */
  public List<Match> getMatchReceiverList()
  {
    return matchReceiverList;
  }

  /**
   * @param matchReceiverList the matchReceiverList to set
   */
  public void setMatchReceiverList(List<Match> matchReceiverList)
  {
    this.matchReceiverList = matchReceiverList;
  }
  // </editor-fold>

}
