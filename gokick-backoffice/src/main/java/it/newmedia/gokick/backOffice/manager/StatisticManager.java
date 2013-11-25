package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.Statistic;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni per gestire l'aggiornamento delle statistiche.
 */
public class StatisticManager {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(StatisticManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private StatisticManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  
  /**
   * Return la riga corrispondente alle statistiche del mese indicato per l'utente indicato
   * @param idUser dell'utente di cui mi interessano le statistiche
   * @param startDate data di inizio della  statistica (primo giorno del mese alle 0:0:00)
   * @param endDate data di fine della  statistica (primo giorno del mese alle 23:59:59)
   * @return la riga corrispondente alle statistiche del mese indicato per l'utente indicato
   */
  public static Statistic getUserMontlyStatistics(int idUser, Date startDate, Date endDate)
  {
    Statistic statistic = null;
    try
    {
      List<Statistic> statisticList = DAOFactory.getInstance().getStatisticDAO().getByIdAndPeriod(idUser, startDate, endDate);
      if (!statisticList.isEmpty())
      {
        statistic = statisticList.get(0);
      }
    }
    catch (Exception ex)
    {
      logger.error(ex.getMessage(), ex);
      return null;
    }
    return statistic;
  }

 public static List<Statistic> getStatistic(int idUser, String period)
  {

      List<Statistic> statisticList = null;
      // Get it from db
      try
      {
        GregorianCalendar startDate = null;
        GregorianCalendar endDate = null;
        if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__LAST_MONTH))
        {
          startDate = new GregorianCalendar();
          startDate.add(Calendar.MONTH, -1);
          endDate = new GregorianCalendar();
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__LAST_THREE_MONTH))
        {
          startDate = new GregorianCalendar();
          startDate.add(Calendar.MONTH, -3);
          endDate = new GregorianCalendar();
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__LAST_SIX_MONTH))
        {
          startDate = new GregorianCalendar();
          startDate.add(Calendar.MONTH, -6);
          endDate = new GregorianCalendar();
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__LAST_TWELVE_MONTH))
        {
          startDate = new GregorianCalendar();
          startDate.add(Calendar.MONTH, -12);
          endDate = new GregorianCalendar();
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__YEAR_CURRENT))
        {
          GregorianCalendar tmp = new GregorianCalendar();
          startDate = new GregorianCalendar(tmp.get(Calendar.YEAR), 0, 1, 0, 0, 0);
          endDate = new GregorianCalendar();
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__YEAR_LESS_ONE))
        {
          GregorianCalendar tmp = new GregorianCalendar();
          startDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 1, 0, 1, 0, 0, 0);
          endDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 1, 11, 31, 23, 59, 59);
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__YEAR_LESS_TWO))
        {
          GregorianCalendar tmp = new GregorianCalendar();
          startDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 2, 0, 1, 0, 0, 0);
          endDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 2, 11, 31, 23, 59, 59);
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__YEAR_LESS_THREE))
        {
          GregorianCalendar tmp = new GregorianCalendar();
          startDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 3, 0, 1, 0, 0, 0);
          endDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 3, 11, 31, 23, 59, 59);
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__YEAR_LESS_FOUR))
        {
          GregorianCalendar tmp = new GregorianCalendar();
          startDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 4, 0, 1, 0, 0, 0);
          endDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 4, 11, 31, 23, 59, 59);
        }
        if (startDate != null && endDate != null)
        {
          statisticList = DAOFactory.getInstance().getStatisticDAO().getByIdAndPeriod(idUser, startDate.getTime(), endDate.getTime());
        }
        else
        {
          statisticList = DAOFactory.getInstance().getStatisticDAO().getByIdAndPeriod(idUser, null, null);
        }

      }
      catch (Exception ex)
      {
        logger.error("Error retrieving statistic in StatisticManager", ex);
      }

    return statisticList;
  }

// </editor-fold>
}
