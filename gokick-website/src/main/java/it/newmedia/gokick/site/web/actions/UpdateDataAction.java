package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.gokick.site.managers.StatisticManager;
import it.newmedia.gokick.site.managers.UserManager;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente le azioni per normalizzare le statistiche dei giocatori
 */
public class UpdateDataAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static Logger logger = Logger.getLogger(UpdateDataAction.class);
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private String result;
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">

  

  @Override
  public String input()
  {  
    return SUCCESS;
  }

   /**
   *
   * @return metodo usato per risistemare il PLAYED_MATCH nella tabella USERS in caso di necessità
   */
  public String updateUserPlayedMatches()
  {
    Boolean success = UserManager.updateAllPlayedMatches();
    result = "OK";
    return SUCCESS;
  }

  /**
   *
   * @return metodo usato  per risistemare le statistiche in caso di necessità
   */
  public String reCalcStat()//
  {
      List<Integer> idMatchList = MatchManager.getRecorded().subList(0, 1);

      for (Integer idMatch : idMatchList)
      {
        boolean calculate = StatisticManager.calculateStatistic(idMatch, true, false, getCurrentCobrand());
      }
      result = "OK";

    return SUCCESS;
  }



  /**
   *
   * @return metodo usato  per risistemare le date third Organized Match
   */
  public String reCalcThirdOrganizedDate()
  {

      boolean calculate = UserManager.setAllthirdOrganizedMatchDate();
      result = "OK";
      return SUCCESS;
  }

  
  @Override
  public void validate()
  {
    
  }

 


  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
   /**
   * @return the result
   */
  public String getResult()
  {
    return result;
  }

  // </editor-fold>
}
