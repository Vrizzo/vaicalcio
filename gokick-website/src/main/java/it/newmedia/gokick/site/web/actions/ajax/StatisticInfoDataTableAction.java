package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.web.datatable.DataTableFactory;
import it.newmedia.gokick.site.web.datatable.StatisticInfoDataTable;

/**
 *Action che gestisce la creazione della tabella AJAX specifica per gli users
 *
 */
public class StatisticInfoDataTableAction extends ADataTableAction<StatisticInfoDataTable>
{
  private String linkPlayMorePartnerStatus;

  public String getLinkPlayMorePartnerStatus()
  {
    return linkPlayMorePartnerStatus;
  }

  public void setLinkPlayMorePartnerStatus(String linkPlayMorePartnerStatus)
  {
    this.linkPlayMorePartnerStatus = linkPlayMorePartnerStatus;
  }

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
      this.dataTable = DataTableFactory.getStatisticInfoDataTable(this.dataTableKey);
      this.dataTable.setDir(this.getDir());
      this.dataTable.setSort(this.getSort());
      this.dataTable.setStartIndex(this.getStartIndex());
      if (this.getSort()!=null)
        this.dataTable.apply();
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
    return SUCCESS;
  }


}
