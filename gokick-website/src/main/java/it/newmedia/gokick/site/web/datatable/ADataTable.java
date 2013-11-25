package it.newmedia.gokick.site.web.datatable;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Viene estesa dalle classi che gestiscono i contenuti delle tabelle AJAX utilizzate nell'applicazione
 * @author ggeroldi
 * @param <T>
 */
public abstract class ADataTable<T>
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  /**
   *
   */
  protected static final Logger logger = Logger.getLogger(ADataTable.class);

  /**
   *
   */
  public static final String SORT_DIR__ASCENDING = "asc";
  /**
   *
   */
  public static final String SORT_DIR__DESCENDING = "desc";
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int startIndex = 0;

  private String sort;

  private String dir;

  private int pageSize = 50;

  private List<T> fullResults = new ArrayList<T>();

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --">
  /**
   *
   * @return
   */
  public List<T> getFullResults()
  {
    if( this.fullResults==null || this.fullResults.isEmpty())
      this.fullResults = new ArrayList<T>();
    return this.fullResults;
  }

  /**
   *
   * @param fullResults
   */
  public void loadAndSortFullResults(List<T> fullResults)
  {
    this.fullResults = fullResults;
    this.sortFullResults();
  }

  /**
   *
   * @param fullResults
   */
  public void loadFullResults(List<T> fullResults)
  {
    this.fullResults = fullResults;
  }

  /**
   *
   * @return
   */
  public List<T> getResults()
  {
    return this.getFullResults().subList(this.getStartIndex(), Math.min(this.getFullResults().size(), this.getStartIndex() + this.getPageSize()));
  }

  /**
   * Applica l'ordinamento alla lista completa
   * secondo i dati impostati (campo e direzione)
   */
  public void apply()
  {
    this.sortFullResults();
  }

  /**
   *
   * @return
   */
  public String getDir()
  {
    if( this.dir == null || this.dir.isEmpty() )
      this.dir = this.getDefaultSortDir();
    return dir;
  }

  /**
   *
   * @param dir
   */
  public void setDir(String dir)
  {
    this.dir = dir;
  }

  /**
   *
   * @return
   */
  public int getPageSize()
  {
    return pageSize;
  }

  /**
   *
   * @param pageSize
   */
  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }

  /**
   *
   * @return
   */
  public String getSort()
  {
    if( this.sort == null || this.sort.isEmpty() )
      this.sort = this.getDefaultSortColumn();
    return sort;
  }

  /**
   *
   * @param sort
   */
  public void setSort(String sort)
  {
    this.sort = sort;
  }

  /**
   *
   * @return
   */
  public int getStartIndex()
  {
    if( this.startIndex > this.getTotalResultsAvailable())
      this.startIndex = this.getTotalResultsAvailable();
    return startIndex;
  }

  /**
   *
   * @param startIndex
   */
  public void setStartIndex(int startIndex)
  {
    this.startIndex = startIndex;
  }

  /**
   *
   * @return
   */
  public int getCurrentPage()
  {
    return (this.getStartIndex() / this.getPageSize()) + 1;
  }

  /**
   *
   * @return
   */
  public int getTotalResultsAvailable()
  {
    return this.getFullResults().size();
  }

  /**
   *
   * @return
   */
  public int getTotalResultsReturned()
  {
    return Math.min(this.getFullResults().size(), this.getStartIndex() + this.getPageSize()) + this.getStartIndex();
  }

  /**
   *
   * @return
   */
  public String getDirAscending()
  {
    return SORT_DIR__ASCENDING;
  }

  /**
   *
   * @return
   */
  public String getDirDescending()
  {
    return SORT_DIR__DESCENDING;
  }
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   *
   */
  public ADataTable()
  {
  }

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Methods --">
  /**
   * ordina i dati della tabella
   */
  protected abstract void sortFullResults();

  /**
   * definisce la direzione dell'ordinamento iniziale
   * @return
   */
  protected abstract String getDefaultSortDir();

  /**
   * definisce la colonna su cui basare l'ordinamento iniziale
   * @return
   */
  protected abstract String getDefaultSortColumn();

  // </editor-fold>

}
