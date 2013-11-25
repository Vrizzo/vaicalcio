package it.newmedia.utils;

import it.newmedia.results.Result;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 *
 * @author ssalmaso
 */
public class Convert
{

  /** Creates a new instance of Convert */
  public Convert()
  {
  }

  /**
   * Try to convert a string to Integer
   * @param number String to convert
   * @return A Result<Integer> with value or error message
   */
  public static Result<Integer> parseInt(String number)
  {
    try
    {
      int result = Integer.parseInt(number);
      return new Result<Integer>(new Integer(result), true);
    }
    catch (Exception ex)
    {
      return new Result<Integer>(ex);
    }
  }

  /**
   * Try to convert a string to Integer
   * @param number String to convert
   * @param defaultValue default value if conversion fails
   * @return An int with value or default value
   */
  public static int parseInt(String number, int defaultValue)
  {
    try
    {
      if( StringUtils.isBlank(number) )
        return defaultValue;
      return Integer.parseInt(number);
    }
    catch (Exception ex)
    {
      return defaultValue;
    }
  }

  /**
   * Try to convert a string to Long
   * @param number String to convert
   * @return A Result<Long> with value or error message
   */
  public static Result<Long> parseLong(String number)
  {
    try
    {
      long result = Long.parseLong(number);
      return new Result<Long>(new Long(result), true);
    }
    catch (Exception ex)
    {
      return new Result<Long>(ex);
    }
  }

  /**
   * Try to convert a string to long
   * @param number String to convert
   * @param defaultValue default value if conversion fails
   * @return An long with value or default value
   */
  public static long parseLong(String number, int defaultValue)
  {
    try
    {
      return Long.parseLong(number);
    }
    catch (Exception ex)
    {
      return defaultValue;
    }
  }

  /**
   * Convert give char array to byte array
   * @param chars array to convert
   * @return a new byte array
   */
  public static byte[] toByteArray(char[] chars)
  {
    return toByteArray(chars, 0, chars.length);
  }

  /**
   * Convert give char array to byte array
   * @param chars array to convert
   * @param start convert given char array from start (to length)
   * @param length convert given char array (from start) to length
   * @return a new byte array
   */
  public static byte[] toByteArray(char[] chars, int start, int length)
  {
    if (chars == null)
    {
      return null;
    }
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; i++)
    {
      bytes[i] = (byte) chars[i + start];
    }
    return bytes;
  }

  /**
   * Parse given string to BigDecimal according default settings (decimal and grouping info)
   * @param number String to convert
   * @return A Resul obj with valid BigDecimal or info about error
   */
  public static Result<BigDecimal> parseBigDecimal(String number)
  {
    return parseBigDecimal(number, null, null);
  }

  /**
   * Parse given string to BigDecimal according decimal and grouping info
   * @param number String to convert
   * @param groupSeparator Number group separator, may be empty orn null
   * @param decimalSeparator Decimal separator, may be empty or null
   * @return A Resul obj with valid BigDecimal or info about error
   */
  public static Result<BigDecimal> parseBigDecimal(String number, String groupSeparator, String decimalSeparator)
  {
    try
    {
    if (StringUtils.isNotEmpty(groupSeparator))
    {
      number = StringUtils.replace(number, groupSeparator, "");
    }
    if (StringUtils.isNotEmpty(decimalSeparator))
    {
      number = StringUtils.replace(number, decimalSeparator, ".");
    }
    BigDecimal result = NumberUtils.createBigDecimal(number);
    if (result != null)
    {
      return new Result<BigDecimal>(result, true);
    }
    else
    {
      return new Result<BigDecimal>("Cannot convert given String to BigDecimal");
    }
    }catch(Exception ex)
      {
      return new Result<BigDecimal>(ex);
    }
  }

  public static String printBigDecimal(BigDecimal number, String groupSeparator, String decimalSeparator)
  {
    return printBigDecimal(number, groupSeparator, decimalSeparator, null);
  }

  public static String printBigDecimal(BigDecimal number, String groupSeparator, String decimalSeparator, String pattern)
  {
    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    if (StringUtils.isNotEmpty(groupSeparator))
    {
      dfs.setGroupingSeparator(CharUtils.toChar(groupSeparator));
    }
    if (StringUtils.isNotEmpty(decimalSeparator))
    {
      dfs.setDecimalSeparator(CharUtils.toChar(decimalSeparator));
    }
    if (StringUtils.isEmpty(pattern))
    {
      if (StringUtils.isEmpty(groupSeparator))
      {
        pattern = "#.#";
      }
      else
      {
        pattern = "#,###.#";
      }
    }
    DecimalFormat decimalFormat = new DecimalFormat(pattern, dfs);
    return decimalFormat.format(number);
  }

  public static void main(String[] args)
  {

    String test = "";
    test = Convert.printBigDecimal(new BigDecimal(123.45), "", "");
    System.out.println("Convert.printBigDecimal(new BigDecimal(123.45), \"\", \"\"); ->" + test);
    test = Convert.printBigDecimal(new BigDecimal(1234567.890), ".", ",", "#.0000");
    System.out.println("Convert.printBigDecimal(new BigDecimal(1234567.890), \".\", \",\",\"#.0000\"); X->" + test);
    test = Convert.printBigDecimal(new BigDecimal(1234567.890), ",", ".");
    System.out.println("Convert.printBigDecimal(new BigDecimal(1234567.890), \",\", \".\"); ->" + test);
    test = Convert.printBigDecimal(new BigDecimal(1234567.890), ".", "");
    System.out.println("Convert.printBigDecimal(new BigDecimal(1234567.890), \".\", \"\"); ->" + test);
    test = Convert.printBigDecimal(new BigDecimal(1234567.890), "", "");
    System.out.println("Convert.printBigDecimal(new BigDecimal(1234567.890), \",\", \",\"); ->" + test);
    test = Convert.printBigDecimal(new BigDecimal(1234567.890), "", "");
    System.out.println("Convert.printBigDecimal(new BigDecimal(1234567.890), \".\", \".\"); ->" + test);
  }
}
