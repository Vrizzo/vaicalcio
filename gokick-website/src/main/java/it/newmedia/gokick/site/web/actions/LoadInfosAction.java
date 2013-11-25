package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.managers.MatchInfoManager;
import it.newmedia.gokick.site.managers.SportCenterManager;
import it.newmedia.gokick.site.managers.StatisticInfoManager;
import it.newmedia.gokick.site.managers.UserInfoManager;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 *
 * Classe contenente le azioni per gestire il caricamento degli oggetti info in cahce
 */
public class LoadInfosAction extends ABaseActionSupport implements Preparable
{
  
  // <editor-fold defaultstate="collapsed" desc="-- CONSTANTS --">
  private static Logger logger = Logger.getLogger(LoadInfosAction.class);
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- MEMBERS --">
  private String result;
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- PUBLIC METHODS --">
  @Override
  public void prepare()
  {
  }

  public String AllInfos()
  {
    loadUserInfos();
    loadStatistInfos();
    loadMatchInfos();
    loadSportCenterInfos();
    result = "OK";
    return SUCCESS;
  }

  public String statisticInfos()
  {
    loadStatistInfos();
    result = "OK";
    return SUCCESS;
  }

  public String userInfos()
  {
    System.out.println("Start: " + new Date());
    loadUserInfos();
    result = "OK";
    System.out.println("End:  " + new Date());
    return SUCCESS;
  }

  public String matchInfos()
  {
    loadMatchInfos();
    result = "OK";
    return SUCCESS;
  }

  public String sportCenterInfos()
  {
    loadSportCenterInfos();
    result = "OK";
    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- PRIVATE METHODS --">

  private void loadSportCenterInfos()
  {
    SportCenterManager.getAllSportCenterInfo();
  }

  private void loadUserInfos()
  {
    UserInfoManager.getAll();
  }

  private void loadMatchInfos()
  {
     MatchInfoManager.getAll();
  }

  private void loadStatistInfos()
  {
     StatisticInfoManager.getBySearchParameters( "",
                                                "",
                                                null,
                                                null,
                                                0,
                                                0,
                                                0,
                                                null,
                                                true,
                                                Constants.STATISTIC_PERIOD__ALL,
                                                false,
                                                EnumUserStatus.Enabled.getValue());
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- GETTERS/SETTERS --">

  /**
   * @return the result
   */
  public String getResult()
  {
    return result;
  }
  // </editor-fold>

}
