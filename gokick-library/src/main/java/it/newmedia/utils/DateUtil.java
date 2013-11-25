package it.newmedia.utils;

// <editor-fold defaultstate="collapsed" desc="-- Imports --">
import it.newmedia.results.Result;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
// </editor-fold>
public class DateUtil {
    // <editor-fold defaultstate="collapsed" desc="-- Constants --">
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
    public DateUtil() {
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="-- Methods --">
    /**
     * Formats a date/time into a specific ISO8601 pattern.
     * @param date the date to format
     * @return the formatted date
     */
    public static String formatISO8601Date(Date date) {
        return DateUtil.formatISO8601Date(date, false);
    }

    /**
     * Formats a date/time into a specific ISO8601 pattern, with or without time.
     * @param date the date to format
     * @param printTime If true print also time
     * @return the formatted date
     */
    public static String formatISO8601Date(Date date, boolean printTime) {
        return DateUtil.formatISO8601Date(date, printTime, false);
    }

    /**
     * Formats a date/time into a specific ISO8601 pattern, with or without time.
     * @param date the date to format
     * @param printTime If true print also time
     * @param printTimezoneInfo If true print also timezone info
     * @return the formatted date
     */
    public static String formatISO8601Date(Date date, boolean printTime, boolean printTimezoneInfo) {
        if (printTime) {
            if (printTimezoneInfo) {
                return DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(date);
            } else {
                return DateFormatUtils.ISO_DATETIME_FORMAT.format(date);
            }
        } else {
            return DateFormatUtils.ISO_DATE_FORMAT.format(date);
        }

    }

    public static String formatDate(GregorianCalendar gcDate, String format) {
        return DateUtil.formatDate(gcDate.getTime(), format);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static GregorianCalendar parseDateGregorianCalendar(String stringDate, String format)
            throws Exception {
        return parseDateGregorianCalendar(stringDate, format, Locale.getDefault());
    }

    public static GregorianCalendar parseDateGregorianCalendar(String stringDate, String format, Locale locale)
            throws Exception {
        Date date = DateUtil.parseDate(stringDate, format);
        GregorianCalendar gcDate = new GregorianCalendar();
        gcDate.setTime(date);
        return gcDate;
    }

    public static Result<GregorianCalendar> parseDateGregorianCalendarResult(String stringDate, String format) {
        try {
            return new Result<GregorianCalendar>(DateUtil.parseDateGregorianCalendar(stringDate, format), true);
        } catch (Exception ex) {
            return new Result<GregorianCalendar>(ex);
        }
    }

    public static Date parseDate(String stringDate, String format)
            throws Exception {
        return parseDate(stringDate, format, Locale.getDefault());
    }

    public static Date parseDate(String stringDate, String format, Locale locale)
            throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, locale);
        return simpleDateFormat.parse(stringDate);
    }

    public static Result<Date> parseDateResult(String stringDate, String format) {
        try {
            return new Result<Date>(DateUtil.parseDate(stringDate, format), true);
        } catch (Exception ex) {
            return new Result<Date>(ex);
        }
    }

    public static long getDiffMillis(GregorianCalendar date1, GregorianCalendar date2) {
        return date1.getTimeInMillis() - date2.getTimeInMillis();
    }

    public static long getDiffMillis(Date date1, Date date2) {
        GregorianCalendar gcDate1 = new GregorianCalendar();
        gcDate1.setTime(date1);
        GregorianCalendar gcDate2 = new GregorianCalendar();
        gcDate2.setTime(date2);
        return DateUtil.getDiffMillis(gcDate1, gcDate2);
    }

    public static long getDiffSeconds(GregorianCalendar date1, GregorianCalendar date2) {
        long millis = DateUtil.getDiffMillis(date1, date2);
        return millis / 1000;
    }

    public static long getDiffSeconds(Date date1, Date date2) {
        GregorianCalendar gcDate1 = new GregorianCalendar();
        gcDate1.setTime(date1);
        GregorianCalendar gcDate2 = new GregorianCalendar();
        gcDate2.setTime(date2);
        return DateUtil.getDiffSeconds(gcDate1, gcDate2);
    }

    public static long getDiffMinutes(GregorianCalendar date1, GregorianCalendar date2) {
        long seconds = DateUtil.getDiffSeconds(date1, date2);
        return seconds / 60;
    }

    public static long getDiffMinutes(Date date1, Date date2) {
        GregorianCalendar gcDate1 = new GregorianCalendar();
        gcDate1.setTime(date1);
        GregorianCalendar gcDate2 = new GregorianCalendar();
        gcDate2.setTime(date2);
        return DateUtil.getDiffMinutes(gcDate1, gcDate2);
    }

    public static long getDiffHours(GregorianCalendar date1, GregorianCalendar date2) {
        long minutes = DateUtil.getDiffMinutes(date1, date2);
        return minutes / 60;
    }

    public static long getDiffHours(Date date1, Date date2) {
        GregorianCalendar gcDate1 = new GregorianCalendar();
        gcDate1.setTime(date1);
        GregorianCalendar gcDate2 = new GregorianCalendar();
        gcDate2.setTime(date2);
        return DateUtil.getDiffHours(gcDate1, gcDate2);
    }

    public static long getDiffDays(GregorianCalendar date1, GregorianCalendar date2) {
        return getDiffDays(date1, date2, false);
    }

    /**
     * Restituisce la differenza in giorni ignorando la parte decimale del risultato
     * 3gg e 4h torna 3
     * @param date1 data di partenza
     * @param date2 data di arrivo
     * @param useDaylightSavingTime se true ignora il cambio dell'ora solare legale
     * Se il parametro è true e viene fatta la differenza tra il 2 aprile e il 20 marzo il risultato sarà 13,
     * se false 12 (vengono perse 23 ore)
     * @return ritorna il numero di giorni di differenza tra le due date, può essere negativo
     */
    public static long getDiffDays(GregorianCalendar date1, GregorianCalendar date2, boolean useDaylightSavingTime) {
        if (useDaylightSavingTime) {
            date1.add(Calendar.MILLISECOND, date1.get(Calendar.DST_OFFSET));
            date2.add(Calendar.MILLISECOND, date2.get(Calendar.DST_OFFSET));
        }
        long hours = DateUtil.getDiffHours(date1, date2);
        return hours / 24;
    }

    public static long getDiffDays(Date date1, Date date2) {
        return getDiffDays(date1, date2, false);
    }

    public static long getDiffDays(Date date1, Date date2, boolean useDaylightSavingTime) {
        GregorianCalendar gcDate1 = new GregorianCalendar();
        gcDate1.setTime(date1);
        GregorianCalendar gcDate2 = new GregorianCalendar();
        gcDate2.setTime(date2);
        return DateUtil.getDiffDays(gcDate1, gcDate2, useDaylightSavingTime);
    }

    public static GregorianCalendar getGregorianCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public static java.sql.Date getSqlDate(GregorianCalendar date) {
        if (date == null) {
            return null;
        }
        return DateUtil.getSqlDate(date.getTime());
    }

    public static java.sql.Date getSqlDate(java.util.Date date) {
        if (date == null) {
            return null;
        } else {
            return new java.sql.Date(date.getTime());
        }
    }

    public static java.sql.Timestamp getSqlTimestamp(java.util.Date date) {
        if (date == null) {
            return null;
        } else {
            return new java.sql.Timestamp(date.getTime());
        }
    }

    public static String printElapsedTime(Date start) {
        Date now = new Date();
        long millis = DateUtil.getDiffMillis(now, start);
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        millis = millis % 1000;

        return String.format("%d:%02d.%03d", minutes, seconds, millis, start, now);
    }

    /**
     * Restituisce il primo giorno del mese rispetto
     * alla data passata. Di default ore, minuti, etc sono troncati
     * @param refDate la data di riferimento
     * @return la data risultato
     */
    public static Date firstOfMonth(Date refDate) 
    {
        return firstOfMonth(refDate, true);
    }

    /**
     * Restituisce il primo giorno del mese rispetto
     * alla data passata
     * @param refDate la data di riferimento
     * @param truncateHours indica se ore, minuti... devono essere troncate (settate a 0)
     * @return la data risultato
     */
    public static Date firstOfMonth(Date refDate, boolean truncateHours) 
    {
        if (refDate == null) {
            throw new IllegalArgumentException("The refDate must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        if (truncateHours) {
            calendar = DateUtils.truncate(calendar, Calendar.DAY_OF_MONTH);
        }
        return calendar.getTime();
    }

    /**
     * Restituisce l'ultimo giorno del mese rispetto
     * alla data passata. Di default ore, minuti, etc sono troncati
     * @param refDate la data di riferimento
     * @return la data risultato
     */
    public static Date lastOfMonth(Date refDate) {
        return lastOfMonth(refDate, true);
    }

    /**
     * Restituisce l'ultimo giorno del mese rispetto
     * alla data passata
     * @param refDate la data di riferimento
     * @param truncateHours indica se ore, minuti... devono essere troncate (settate a 0)
     * @return la data risultato
     */
    public static Date lastOfMonth(Date refDate, boolean truncateHours) {
        if (refDate == null) {
            throw new IllegalArgumentException("The refDate must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);
        int lastDate = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, lastDate);
        if (truncateHours) {
            calendar = DateUtils.truncate(calendar, Calendar.DAY_OF_MONTH);
        }
        return calendar.getTime();
    }

    
    /**
     * Ritorna true se refDate è compreso tra lowerDate e upperDate
     * Gli estremi sono compresi
     * @param refDate la data da controllare
     * @param lowerDate il limite inferiore
     * @param upperDate il limite superiore
     * @return true se refDate è compreso tra lowerDate e upperDate
     */
    public static boolean isBetween(Date refDate, Date lowerDate, Date upperDate) 
    {
        if (refDate == null) 
        {
            throw new NullArgumentException("refDate");
        }
        return refDate.compareTo(lowerDate) >= 0 && refDate.compareTo(upperDate) <= 0;
    }
    // </editor-fold>

    public static void main(String[] args) {
        try {

            GregorianCalendar gc1 = new GregorianCalendar(2011, 2, 22);
            GregorianCalendar gc2 = new GregorianCalendar(2011, 2, 29);
            System.out.println("gc1-> " + gc1.getTime());
            System.out.println("gc1 offset-> " + gc1.get(Calendar.DST_OFFSET));
            System.out.println("gc2-> " + gc2.getTime());
            System.out.println("gc2 offset-> " + gc2.get(Calendar.DST_OFFSET));

            System.out.println("diff: " + DateUtil.getDiffDays(gc1, gc2, true));
            if (1 == 1) {
                return;
            }


            GregorianCalendar userBirthDate = new GregorianCalendar(2000, 11, 29);
            GregorianCalendar currentDate = new GregorianCalendar();

            int tmpAge = currentDate.get(Calendar.YEAR) - userBirthDate.get(Calendar.YEAR);
            if (userBirthDate.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH)) {
                tmpAge--;
            } else if (userBirthDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH)) {
                if (userBirthDate.get(Calendar.DAY_OF_MONTH) > currentDate.get(Calendar.DAY_OF_MONTH)) {
                    tmpAge--;
                }
            }
            System.out.println("Calcoalto: " + tmpAge);
            int ageBest = 0;
            Date tmp = DateUtils.addYears(userBirthDate.getTime(), 1);
            while (tmp.before(currentDate.getTime())) {
                ageBest++;
                tmp = DateUtils.addYears(tmp, 1);
            }
            System.out.println("Calcoalto BEST: " + ageBest);

//      System.out.println("DateUtil.formatISO8601Date(test, false, false);");
//      System.out.println(DateUtil.formatISO8601Date(test));
//      System.out.println(org.apache.commons.lang.time.DateFormatUtils.ISO_DATE_FORMAT.format(test));
//      System.out.println("");
//
//      System.out.println("DateUtil.formatISO8601Date(test, true, false); ");
//      System.out.println(DateUtil.formatISO8601Date(test, true));
//      System.out.println(org.apache.commons.lang.time.DateFormatUtils.ISO_DATETIME_FORMAT.format(test));
//      System.out.println("");
//
//      System.out.println("DateUtil.formatISO8601Date(test, false, true); ");
//      System.out.println(DateUtil.formatISO8601Date(test, true, true));
//      System.out.println(org.apache.commons.lang.time.DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(test));
//      System.out.println("");
//
//      System.out.println("DateUtil.formatISO8601Date(test, true, true); ");
//      System.out.println(DateUtil.formatISO8601Date(test, true, true));
//      System.out.println(org.apache.commons.lang.time.DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(test));
//      System.out.println("");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
