package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.web.datatable.DataTableFactory;
import it.newmedia.gokick.site.web.datatable.SportCenterInfoDataTable;

/**
 *Action che gestisce la creazione della tabella AJAX specifica per gli SportCenters
 *
 */
public class SportCenterDataTableAction extends ADataTableAction<SportCenterInfoDataTable>
{
  @Override
  public String execute()
  {
    try
    {
      if( this.dataTableKey == null || this.dataTableKey.isEmpty() )
      {
        logger.error("Cannot execute a DataTableAction without a valid [dataTableKey]");
        return SUCCESS;
      }
      this.dataTable = DataTableFactory.getSportCenterInfoDataTable(this.dataTableKey);
      this.dataTable.setDir(this.getDir());
      this.dataTable.setSort(this.getSort());
      this.dataTable.setStartIndex(this.getStartIndex());
      this.dataTable.apply();
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
    return SUCCESS;
  }

}
