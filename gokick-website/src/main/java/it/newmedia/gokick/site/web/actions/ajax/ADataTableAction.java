package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.web.actions.ABaseActionSupport;
import it.newmedia.gokick.site.web.datatable.ADataTable;

/**
 * estesa dalle Action che gestiscono la creazione delle tabelle AJAX utilizzate nell'applicazione
 *
 */
public abstract class ADataTableAction<T extends ADataTable> extends ABaseActionSupport
{
  protected int totalResultsReturned;
  protected int totalResultsAvailable;
  protected int startIndex;
  protected String sort;
  protected String dir;
  protected int pageSize;
  protected String dataTableKey;
  protected T dataTable;

  // <editor-fold defaultstate="collapsed" desc="-- GETTERS/SETTERS --">
  public T getDataTable()
  {
    return dataTable;
  }

  public String getDataTableKey()
  {
    return dataTableKey;
  }

  public void setDataTableKey(String dataTableKey)
  {
    this.dataTableKey = dataTableKey;
  }

  public String getDir()
  {
    return dir;
  }

  public void setDir(String dir)
  {
    this.dir = dir;
  }

  public int getPageSize()
  {
    return pageSize;
  }

  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }

  public String getSort()
  {
    return sort;
  }

  public void setSort(String sort)
  {
    this.sort = sort;
  }

  public int getStartIndex()
  {
    return startIndex;
  }

  public void setStartIndex(int startIndex)
  {
    this.startIndex = startIndex;
  }

  public int getTotalResultsAvailable()
  {
    return totalResultsAvailable;
  }

  public void setTotalResultsAvailable(int totalResultsAvailable)
  {
    this.totalResultsAvailable = totalResultsAvailable;
  }

  public int getTotalResultsReturned()
  {
    return totalResultsReturned;
  }

  public void setTotalResultsReturned(int totalResultsReturned)
  {
    this.totalResultsReturned = totalResultsReturned;
  }

  // </editor-fold>

}
