package it.newmedia.gokick.site.web.datatable;

import javax.servlet.http.HttpSession;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * classe che ha la responsabilità di restituire la giusta dataTable (tabella AJAX) o di istanziarla se non esistesse, ed associarle nel caso una chiave randomica di sessione
 * @author ggeroldi
 */
public class DataTableFactory
{

  /**
   *
   */
  protected static final Logger logger = Logger.getLogger(ADataTable.class);

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  /**
   *
   * @param dataTableKey
   * @return
   */
  public static SportCenterInfoDataTable getSportCenterInfoDataTable(String dataTableKey)
  {
    return (SportCenterInfoDataTable) instantiateDataTable(dataTableKey, SportCenterInfoDataTable.class);
  }

  /**
   *
   * @param dataTableKey
   * @return
   */
  public static StatisticInfoDataTable getStatisticInfoDataTable(String dataTableKey)
  {
    return (StatisticInfoDataTable) instantiateDataTable(dataTableKey, StatisticInfoDataTable.class);
  }

  private static ADataTable instantiateDataTable(String dataTableKey, Class dataTableClass)
  {
    try
    {
      HttpSession session = ServletActionContext.getRequest().getSession();
      ADataTable dt = (ADataTable) session.getAttribute(dataTableKey);
      if (dt == null)
      {
        try
        {
          Class[] classParm = null;
          Object[] objectParm = null;
          dt = (ADataTable)dataTableClass.getConstructor(classParm).newInstance(objectParm);
          session.setAttribute(dataTableKey, dt);
        }
        catch (NoSuchMethodException ex)
        {
          logger.error(ex, ex);
        }
        catch (SecurityException ex)
        {
          logger.error(ex, ex);
        }
      }
      return dt;
    }
    catch (Exception ex)
    {
      throw new RuntimeException("Can not instantiate DAO: " + dataTableClass, ex);
    }
  }

  /**
   *
   * @return
   */
  public static String buildDataTableKey()
  {
    return RandomStringUtils.randomAlphanumeric(10);
  }

  // </editor-fold>
}
