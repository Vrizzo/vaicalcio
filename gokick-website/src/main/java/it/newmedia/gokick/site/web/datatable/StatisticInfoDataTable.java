package it.newmedia.gokick.site.web.datatable;

import it.newmedia.gokick.site.infos.StatisticInfo;
import java.util.Collections;
import java.util.Comparator;

/**
 * Classe che gestisce l'ordinamento dei contenuti della tabella AJAX che visualizza gli users
 * @author ggeroldi
 */
public class StatisticInfoDataTable extends ADataTable<StatisticInfo>
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --">

  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__NAME = "Friend";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__COMPLETENAME = "CompleteName";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__EMPTY = "EmptySquad";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__FIRSTNAME = "FirstName";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__LASTNAME = "LastName";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__NATCOUNTRY = "Nationality";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__NATCOUNTRYSQUAD = "NationalitySquad";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__CITY = "City";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__ROLE = "Role";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__ROLESQUAD = "RoleSquad";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__PLAYED = "Played";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__PLAYEDSQUAD = "PlayedSquad";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__AGE = "Age";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__AGESQUAD = "AgeSquad";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__MONTHFREQUENCY = "Frequency";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__GOAL = "Goal";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__AVGOAL = "AvgGoal";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__AVVOTE = "AvgVote";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__RELIABILITY = "Reliability";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__RELIABILITYSQUAD = "ReliabilitySquad";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__CONDITION = "Condition";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__REGISTRATION = "Id";

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  protected void sortFullResults()
  {
    try
    {
      Comparator<StatisticInfo> c = null;
      if( this.getSort().equals(PARAM_SORT_COLUMN__NATCOUNTRY) )
          c = StatisticInfo.NATCOUNTRY_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__NATCOUNTRYSQUAD) )
          c = StatisticInfo.NATCOUNTRY_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__REGISTRATION) )
          c = StatisticInfo.REGISTRATION_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__CITY) )
          c = StatisticInfo.CITY_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__CONDITION) )
          c = StatisticInfo.CONDITION_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__LASTNAME) )
          c = StatisticInfo.LASTNAME_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__ROLE) )
          c = StatisticInfo.ROLE_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__ROLESQUAD) )
          c = StatisticInfo.ROLE_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__PLAYED) )
          c = StatisticInfo.PLAYED_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__PLAYEDSQUAD) )
          c = StatisticInfo.PLAYED_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__MONTHFREQUENCY) )
          c = StatisticInfo.MONTHFREQUENCY_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__GOAL) )
          c = StatisticInfo.GOAL_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__AVGOAL) )
          c = StatisticInfo.AVGOAL_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__AVVOTE) )
          c = StatisticInfo.AVVOTE_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__RELIABILITY) )
          c = StatisticInfo.RELIABILITY_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__RELIABILITYSQUAD) )
          c = StatisticInfo.RELIABILITY_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__AGE) )
          c = StatisticInfo.AGE_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__AGESQUAD) )
          c = StatisticInfo.AGE_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__EMPTY) )
          c = StatisticInfo.NO_ORDER;
      else
          c = StatisticInfo.NAME_ORDER;

      if (this.getDir().equals(SORT_DIR__DESCENDING) )
      {
        c = Collections.reverseOrder(c);
        Collections.sort(this.getFullResults(), c);
      }
      else
      {
        Collections.sort(this.getFullResults(), c);
      }
      
    }
    catch (Exception ex)
    {
      logger.error("Error executing sort on SportCenterInfoPaginatedList", ex);
    }
  }

  @Override
  protected String getDefaultSortDir()
  {
    return SORT_DIR__ASCENDING;
  }
  
  @Override
  protected String getDefaultSortColumn()
  {
      return PARAM_SORT_COLUMN__NAME;
  }
  // </editor-fold>


}
