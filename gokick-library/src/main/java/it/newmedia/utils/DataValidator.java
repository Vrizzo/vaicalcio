/*
 * DataValidator.java
 *
 * Created on 14 marzo 2007, 11.56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package it.newmedia.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// </editor-fold>
public class DataValidator
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --">

  public static final String REGEX_EMAIL = "[A-Za-z0-9\\._\\-~#]+@[A-Za-z0-9\\._\\-~#]+\\.[A-Za-z0-9\\._\\-~#]+";

  //public static final String REGEX_IS_INTEGER = "^-?[0-9]+$";
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  // <editor-fold defaultstate="collapsed" desc="-- Email --"  >
  public static boolean checkEmail(String email)
  {
    if ((email == null) || (email.length() <= 5))
    {
      return false;
    }
    Pattern p = Pattern.compile(REGEX_EMAIL);
    Matcher m = p.matcher(email); // get a matcher object
    return m.find();
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Date --"  >
  public static boolean isDate(String date, String format)
  {
    if ((date == null) || (date.length() < 0))
    {
      return false;
    }
    try
    {
      DateUtil.parseDate(date, format);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Number management --"  >
  // <editor-fold defaultstate="collapsed" desc="-- Integer --"  >
  /**
   * Check if input string is a Integer value
   * Value must be >= Integer.MIN_VALUE and <= Integer.MAX_VALUE
   * @param number String to check
   * @return True if number is an integer, otherwise false
   */
  public static boolean isInteger(String number)
  {
    try
    {
      Integer.parseInt(number);
      return true;
    }
    catch (Exception ex)
    {
      return false;
    }
  }

  /**
   * Check if input string is a Integer value
   * Value must be > 0 and <= Integer.MAX_VALUE
   * @param number String to check
   * @return True if number is an integer, otherwise false
   */
  public static boolean isIntegerGreaterThanZero(String number)
  {
    try
    {
      return Integer.parseInt(number) > 0;
    }
    catch (Exception ex)
    {
      return false;
    }
  }

  /**
   * Check if input string is a Integer value
   * Value must be >= 0 and <= Integer.MAX_VALUE
   * @param number String to check
   * @return True if number is an integer, otherwise false
   */
  public static boolean isIntegerGreaterEqualZero(String number)
  {
    try
    {
      return Integer.parseInt(number) >= 0;
    }
    catch (Exception ex)
    {
      return false;
    }
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Decimal --"  >
  /**
   * Check if input string is a Long value
   * Value must be >= Long.MIN_VALUE and <= Long.MAX_VALUE
   * @param number String to check
   * @return True if number is an long, otherwise false
   */
  public static boolean isDecimal(String number)
  {
    try
    {
      Double.parseDouble(number);
      return true;
    }
    catch (Exception ex)
    {
      return false;
    }
  }

  /**
   * Check if input string is a Long value
   * Value must be > 0 and <= Long.MAX_VALUE
   * @param number String to check
   * @return True if number is an long, otherwise false
   */
  public static boolean isDecimalGreaterThanZero(String number)
  {
    try
    {
      return Double.parseDouble(number) > 0;
    }
    catch (Exception ex)
    {
      return false;
    }
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Long --"  >
  /**
   * Check if input string is a Long value
   * Value must be >= Long.MIN_VALUE and <= Long.MAX_VALUE
   * @param number String to check
   * @return True if number is an long, otherwise false
   */
  public static boolean isLong(String number)
  {
    try
    {
      Long.parseLong(number);
      return true;
    }
    catch (Exception ex)
    {
      return false;
    }
  }

  /**
   * Check if input string is a Long value
   * Value must be > 0 and <= Long.MAX_VALUE
   * @param number String to check
   * @return True if number is an long, otherwise false
   */
  public static boolean isLongGreaterThanZero(String number)
  {
    try
    {
      return Long.parseLong(number) > 0;
    }
    catch (Exception ex)
    {
      return false;
    }
  }

  // </editor-fold>
  // </editor-fold>
  // </editor-fold>
}
