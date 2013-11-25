package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Translation;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.providers.TranslationProvider;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.newmedia.utils.Convert;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello la visualizzazione delle date in formati e lingua specificati
 */
public class DateManager
{

  /**
   *
   */
  public static final String FORMAT_DATE_1 = "format.date_1";

  /**
   *
   */
  public static final String FORMAT_DATE_2 = "format.date_2";

  /**
   *
   */
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
   *
   */
  public static final String FORMAT_DATE_9 = "format.date_9";

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

  /**
   * mer 30/7 ore 16:30
   */
  public static final String FORMAT_DATE_16 = "format.date_16";

  /**
   * Sab 2 dicembre
   */
  public static final String FORMAT_DATE_17 = "format.date_17";

  /**
   * 30 set 05 11:20
   */
  public static final String FORMAT_DATE_18 = "format.date_18";

  /**
   * Mercoledi'
   */
  public static final String FORMAT_DATE_19 = "format.date_19";

  /**
   * 22 Novembre h 18:00
   */
  public static final String FORMAT_DATE_20 = "format.date_20";

  // <editor-fold defaultstate="collapsed" desc="-- Members --">

  private static Logger logger = Logger.getLogger(DateManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private DateManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  /**
   *
   * @param dateStr stringa la data finale
   * @param formatIn formato in entrata
   * @param formatKey formato in uscita
   * @return tempo che intercorre tra la data attuale e quella indicata (finale)
   */
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

  /**
   *
   * @param date data finale
   * @param formatKey formato in uscita
   * @return tempo che intercorre tra la data attuale e quella indicata (finale)
   */
  public static String showDaysTo(Date date, String formatKey)
  {
      Translation formatOutTrans = TranslationProvider.getTranslation(formatKey, UserContext.getInstance().getLanguage(), UserContext.getInstance().getCurrentCobrand());
      String rest = DurationFormatUtils.formatPeriod(new Date().getTime(), date.getTime(), formatOutTrans.getKeyValue());
      return rest;
  }


  /**
   *
   * @param dateStr stringa contente la data in entrata
   * @param formatIn formato in entrata
   * @param formatKey formato in uscita
   * @return data formattata
   */
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

  /**
   *
   * @param date data in entrata
   * @param formatKey formato in uscita
   * @return data formattata
   */
  public static String showDate(Date date, String formatKey)
  {
    String dataOut = "";
    Translation formatOutTrans = TranslationProvider.getTranslation(formatKey, UserContext.getInstance().getLanguage(), UserContext.getInstance().getCurrentCobrand());
    dataOut = String.format(UserContext.getInstance().getLanguage().getLocale(), formatOutTrans.getKeyValue(), date);

    return capitalizeDate(dataOut,formatKey);
  }

  /**
   *
   * @param date data in entrata
   * @param formatKey formato in uscita
   * @param language lingua
   * @return data formattata tradotta
   */
  public static String showDate(Date date, String formatKey,Language language, Cobrand currentCobrand)
  {
    String dataOut = "";
    Translation formatOutTrans = TranslationProvider.getTranslation(formatKey, language, currentCobrand);
    dataOut = String.format(language.getLocale(), formatOutTrans.getKeyValue(), date);

    return capitalizeDate(dataOut,formatKey);
  }

  /**
   *
   * @param dataOut :data da formattare in stringa
   * @param formatKey :specifiche formattazione da DB
   * @return capitalizzazione della stringa in uscita a seconda della formatKey indicata
   */
  public static String capitalizeDate(String dataOut,String formatKey)
  {
    int code = Convert.parseInt(StringUtils.substringAfterLast(formatKey, "_"), 1);
    switch(code)
    {
      case 1:
      case 2:
      case 8:
      case 10:
      case 12:
        return WordUtils.capitalize(StringUtils.substringBefore(dataOut," ")) + " " + StringUtils.substringAfter(dataOut," ");
      case 7:
        return StringUtils.substringBefore(dataOut," ") + " " + WordUtils.capitalize(StringUtils.substringAfter(dataOut," "));
      case 4:
      case 13:
        return WordUtils.capitalize(dataOut);
      case 20:
        return dataOut.toLowerCase();
      default:
        return dataOut;
    }
  }

  // </editor-fold>
}
