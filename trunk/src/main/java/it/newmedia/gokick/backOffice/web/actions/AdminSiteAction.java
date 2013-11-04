package it.newmedia.gokick.backOffice.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.backOffice.UserContext;
import it.newmedia.gokick.backOffice.manager.CacheManager;

/**
 *
 * Classe contenente le azioni per gestire la ricerca di utenti 
 */
public class AdminSiteAction extends  ABaseActionSupport  implements Preparable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
 
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private String cancelCacheResponse;

  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">

  @Override
  public void prepare()
  {
    
  }
  
  public String input()
  {
    return SUCCESS;
  }

  
  public String cancelCache()
  {
    if (UserContext.getInstance().getIdCountryFilter()!=0)
    {
      return ERROR;
    }
        
    if (CacheManager.removeAllWebSiteCache())
    {
        this.setCancelCacheResponse("Cache cancellata");
    }
    else
    {
        this.setCancelCacheResponse("errore, dettagli nei Logs");
    }
    return SUCCESS;
  }

   // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  
  /**
   * @return the cancelCacheResponse
   */
  public String getCancelCacheResponse()
  {
    return cancelCacheResponse;
  }
  /**
   * @param cancelCacheResponse the cancelCacheResponse to set
   */
  public void setCancelCacheResponse(String cancelCacheResponse)
  {
    this.cancelCacheResponse = cancelCacheResponse;
  }

  
  // </editor-fold>
}
