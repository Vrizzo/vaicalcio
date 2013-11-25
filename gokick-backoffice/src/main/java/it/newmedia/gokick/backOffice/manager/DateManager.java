package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.Translation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello il salvataggio dei messaggi relativi alle azioni fatte dagli utenti nel sito.
 */
public class DateManager
{

  public static final String FORMAT_DATE_1 = "format.date_1";

  public static final String FORMAT_DATE_2 = "format.date_2";

  public static final String FORMAT_DATE_3 = "format.date_3";

  /**
   * Giovedì 26 Novembre 2009
   */
  public static final String FORMAT_DATE_4 = "format.date_4";

  /**
   * Stampa HH:mm, es 13:57
   */
  public static final String FORMAT_DATE_5 = "format.date_5";

  /**
   * Stampa giorno/mese [dd/MM], es 01/12
   */
  public static final String FORMAT_DATE_6 = "format.date_6";

  /**
   * 29 ottobre 2009
   */
  public static final String FORMAT_DATE_7 = "format.date_7";

  /**
   * mer 3 set
   */
  public static final String FORMAT_DATE_8 = "format.date_8";

  /**
   * 27/11/2009
   */
  public static final String FORMAT_DATE_9 = "format.date_9";

  /**
   * 27/11/2009
   */
  public static final String FORMAT_PATTERN_9 = "dd/MM/yy";


  /**
   * Mercoledì 1 febbraio 2009
   */
  public static final String FORMAT_DATE_10 = "format.date_10";

  /**
   * mer 30/7 alle 16:30
   */
  public static final String FORMAT_DATE_11 = "format.date_11";

  /**
   * Mer 20 dic 05
   */
  public static final String FORMAT_DATE_12 = "format.date_12";

  /**
   * Mercoledì 1 febbraio
   */
  public static final String FORMAT_DATE_13 = "format.date_13";

   /**
   * 2 giorni e 1 ora
   */
  public static final String FORMAT_DATE_14 = "format.date_14";

   /**
   * 29/10/09 ore 20:00
   */
  public static final String FORMAT_DATE_15 = "format.date_15";

  // <editor-fold defaultstate="collapsed" desc="-- Members --">

  private static Logger logger = Logger.getLogger(DateManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private DateManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  public static String showDaysTo(String dateStr, String formatIn,String formatKey)
  {
    try
    {
      DateFormat formatOld = new SimpleDateFormat(formatIn);
      Date parsedDate = formatOld.parse(dateStr);
      return showDaysTo(parsedDate, formatKey);
    }
    catch (Exception ex)
    {
      logger.debug("error parsing date in ShowDateAction", ex);
      return "";
    }
  }

  public static String showDaysTo(Date date, String formatKey)
  {
      Translation formatOutTrans = TranslationManager.getTranslation(formatKey);
      String rest = DurationFormatUtils.formatPeriod(new Date().getTime(), date.getTime(), formatOutTrans.getKeyValue());
      return rest;
  }


  public static String showDate(String dateStr, String formatIn, String formatKey)
  {
    try
    {
      DateFormat formatOld = new SimpleDateFormat(formatIn);
      Date parsedDate = formatOld.parse(dateStr);
      return showDate(parsedDate, formatKey);
    }
    catch (Exception ex)
    {
      logger.debug("error parsing date in ShowDateAction", ex);
      return "";
    }
  }

  public static String showDate(Date date, String formatKey)
  {
    String dataOut = "";
    dataOut = String.format(new Locale("it","IT"), formatKey, date);
    return dataOut;
  }

  public static String showDate(Date date)
  {
    String dataOut = "";
    String formatKey="%1$ta %1$td %1$tb %1$ty";
    dataOut = String.format(new Locale("it","IT"), formatKey, date);

    return dataOut;
  }

  /**
   *
   * @param dataOut :data da formattare in stringa
   * @param formatKey :specifiche formattazione da DB
   * @return
   */
  public static String capitalizeDate(String dataOut,String formatKey)
  {
    if (FORMAT_DATE_4.equals(formatKey)|| FORMAT_DATE_13.equals(formatKey))
    {
      dataOut = WordUtils.capitalize(dataOut);
    }
    if (FORMAT_DATE_1.equals(formatKey) || FORMAT_DATE_2.equals(formatKey) || FORMAT_DATE_8.equals(formatKey) || FORMAT_DATE_10.equals(formatKey) )
    {
      String day=StringUtils.substringBefore(dataOut," ");
      String rest = StringUtils.substringAfter(dataOut," ");
      day=WordUtils.capitalize(day);
      dataOut = day + " " + rest;
    }
    if (FORMAT_DATE_7.equals(formatKey) )
    {
      String day=StringUtils.substringBefore(dataOut," ");
      String rest = StringUtils.substringAfter(dataOut," ");
      rest=WordUtils.capitalize(rest);
      dataOut = day + " " + rest;
    }
    return dataOut;
  }

  // </editor-fold>
}
