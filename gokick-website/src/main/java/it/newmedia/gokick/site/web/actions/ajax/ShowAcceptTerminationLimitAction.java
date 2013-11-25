package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.managers.DateManager;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 *Classe contenente le azioni per visualizzare il tempo che manca allo scadere del'accetazione disdette
 *
 */
public class ShowAcceptTerminationLimitAction extends ABaseActionSupport
{

  private String  dateTo;
  private int hourLimit;


  @Override
  public String execute()
  {
     
    if (!dateTo.equals(""))
    {
      int day = Integer.parseInt(dateTo.substring(0,2));  //vedi data manager per Parse
      int month = Integer.parseInt(dateTo.substring(3,5))-1;
      int year = Integer.parseInt(dateTo.substring(6,10));
      int hours = Integer.parseInt(dateTo.substring(11,13));
      int minutes = Integer.parseInt(dateTo.substring(14,16));

      
      GregorianCalendar gc = new GregorianCalendar(year, month, day , hours, minutes, 0);
      gc.add(GregorianCalendar.HOUR, - hourLimit);

      this.dateTo=DateManager.showDate(new Date(gc.getTimeInMillis()), DateManager.FORMAT_DATE_11);
    }
  
    
    return SUCCESS;
  }




  // <editor-fold defaultstate="collapsed" desc="-- GETTER/SETTER --">
   /**
   * @return the dateTo
   */
  public String getDateTo()
  {
    return dateTo;
  }

  /**
   * @param dateTo the dateTo to set
   */
  public void setDateTo(String dateTo)
  {
    this.dateTo = dateTo;
  }

  /**
   * @return the hourLimit
   */
  public int getHourLimit()
  {
    return hourLimit;
  }

  /**
   * @param hourLimit the hourLimit to set
   */
  public void setHourLimit(int hourLimit)
  {
    this.hourLimit = hourLimit;
  }

  // </editor-fold>

 
}
