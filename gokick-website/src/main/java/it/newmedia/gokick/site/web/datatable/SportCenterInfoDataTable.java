package it.newmedia.gokick.site.web.datatable;

import it.newmedia.gokick.site.infos.SportCenterInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe che gestisce l'ordinamento dei contenuti della tabella AJAX che visualizza gli SportCenters (campi)
 * @author ggeroldi
 */
public class SportCenterInfoDataTable extends ADataTable<SportCenterInfo>
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__NAME = "Name";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__CITY = "CityName";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__ADDRESS = "Address";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__MATCHTYPEAVAILABLE = "MatchTypeAvailable";
  /**
   *
   */
  public static final String PARAM_SORT_COLUMN__MATCHTYPEAVAILABLESCALL = "MatchTypeAvailableSCAll";

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  protected void sortFullResults()
  {
    try
    {
      Comparator<SportCenterInfo> c = null;
      if( this.getSort().equals(PARAM_SORT_COLUMN__ADDRESS) )
          c = SportCenterInfo.ADDRESS_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__CITY) )
          c = SportCenterInfo.CITY_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__MATCHTYPEAVAILABLE) )
          c = SportCenterInfo.MATCHTYPEAVAILABLE_ORDER;
      else if( this.getSort().equals(PARAM_SORT_COLUMN__MATCHTYPEAVAILABLESCALL) )
          c = SportCenterInfo.MATCHTYPEAVAILABLE_ORDER;
      else
          c = SportCenterInfo.NAME_ORDER;

      if (this.getDir().equals(SORT_DIR__DESCENDING) )
      {
        c = Collections.reverseOrder(c);
        List<SportCenterInfo> fullList = this.getFullResults();
        List<SportCenterInfo> listUp = new ArrayList<SportCenterInfo>();
        List<SportCenterInfo> listDown = new ArrayList<SportCenterInfo>();
        for (SportCenterInfo sportCenterInfo : fullList)
        {
          if (sportCenterInfo.isConventioned())
            listUp.add(sportCenterInfo);
          else
            listDown.add(sportCenterInfo);
        }
        Collections.sort(listUp, c);
        Collections.sort(listDown, c);
        fullList.clear();
        fullList.addAll(listUp);
        fullList.addAll(listDown);
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
