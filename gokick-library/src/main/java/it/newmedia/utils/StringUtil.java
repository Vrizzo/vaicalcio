/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.newmedia.utils;

/**
 *
 * @author vrizzo
 */
public class StringUtil
{
  public static String toSeoString(String input)
  {
     StringBuffer result = new StringBuffer();
    input = input.toLowerCase();
   
    for( int i=0; i < input.length(); i++ )
    {
      char currentChar = input.charAt(i);
      int val = (int)currentChar;
      if( (currentChar >= 'a' && currentChar <= 'z') || (currentChar >= '0' && currentChar <= '9') )
        result.append(currentChar);
      else
      {
        switch(currentChar)
        {
          default:
            result.append('-');
            break;
        }
      }
    }

    return result.toString().replaceAll("(-{2,})", "-").replaceAll("(-+$)", "");
  }

}
