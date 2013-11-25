package it.newmedia.gokick.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author ggeroldi
 */
public class Utils
{

  /**
   *
   * @param birthDate data di nascita
   * @return l'età riferita alla data passata
   */
  public static String getAgefromDate(Date birthDate)
  {
    if (birthDate == null)
    {
      return "";
    } else
    {
      GregorianCalendar userBirthDate = new GregorianCalendar();
      userBirthDate.setTime(birthDate);
      GregorianCalendar currentDate = new GregorianCalendar();

      int tmpAge = currentDate.get(Calendar.YEAR) - userBirthDate.get(Calendar.YEAR);
      if (userBirthDate.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH))
      {
        tmpAge--;
      } else if (userBirthDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH))
      {
        if (userBirthDate.get(Calendar.DAY_OF_MONTH) > currentDate.get(Calendar.DAY_OF_MONTH))
        {
          tmpAge--;
        }
      }
      return String.valueOf(tmpAge);
    }
  }

  /**
   *
   * @param dateInStringFormat data (stringa)
   * @param dateFormat formato dateInStringFormat
   * @return la data in tipo Date da quella in stringa a
   */
  public static Date getDateFromString(String dateInStringFormat, String dateFormat)
  {
    Date date = null;
    if (dateInStringFormat == null || dateInStringFormat.length() <= 0)
    {
      return date;
    } else
    {
      try
      {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat(dateFormat);
        date = simpleDateformat.parse(dateInStringFormat);
      }
      catch (Exception e)
      {
        return date;
      }
      return date;
    }
  }
}
