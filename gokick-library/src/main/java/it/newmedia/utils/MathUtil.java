package it.newmedia.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MathUtil
{
  public static BigDecimal HUNDRED = new BigDecimal(100);

  /**
   * Divide "dividend" value with "divisor"
   * Default scale is 2 and RoundingMode.HALF_UP
   * @param dividend A not null BigDecimal instance
   * @param divisor An int value not equal 0
   * @return Result of divide operation
   */
  public static BigDecimal divide(BigDecimal dividend, int divisor)
  {
    return MathUtil.divide(dividend, divisor, RoundingMode.HALF_UP);
  }

  public static BigDecimal divide(BigDecimal dividend, int divisor, RoundingMode roundingMode)
  {
    return MathUtil.divide(dividend, divisor, roundingMode, dividend.scale());
  }

  public static BigDecimal divide(BigDecimal dividend, int divisor, RoundingMode roundingMode, int scale)
  {
    BigDecimal divisorBigDecimal = new BigDecimal(divisor);
    return dividend.divide(divisorBigDecimal, scale, roundingMode);
  }

  public static BigDecimal percentage(BigDecimal amount, BigDecimal percentage)
  {
    BigDecimal result = amount.multiply(percentage);
    return result.divide(HUNDRED, BigDecimal.ROUND_HALF_EVEN);
  }

  public static BigDecimal percentage(BigDecimal amount, BigDecimal percentage, int scale)
  {
    BigDecimal result = amount.multiply(percentage);
    return result.divide(HUNDRED, scale, RoundingMode.UP);
  }

  public static void main(String[] args)
  {
    try
    {
      BigDecimal a = new BigDecimal("10.58");
      System.out.println("a vale " + a.toPlainString());

      BigDecimal b = new BigDecimal(1);
      System.out.println("b vale " + b.toPlainString());

      BigDecimal c = a.divide(b);
      System.out.println("c vale " + c.toPlainString());

      BigDecimal d = divide(a, b.intValue());
      System.out.println("d vale " + d.toPlainString());
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
}
