/*
 * ASaxParserDefaultHandler.java
 *
 * Created on 19 aprile 2007, 7.25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.newmedia.xml;

import it.newmedia.utils.DateUtil;
import java.util.Date;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Stevs
 */
public abstract class ASaxParserDefaultHandler extends DefaultHandler
{
  protected String getAttribute( Attributes atts, String name, String defaultValue)
  {
    if( atts == null || atts.getLength() <=0 )
      return defaultValue;
    for (int i= 0; i < atts.getLength(); i++)
    {
      String currentName = atts.getLocalName(i);
      if( currentName.equals(name) )
        return atts.getValue(currentName);
    }
    return defaultValue;
  }
  
  protected int getAttribute( Attributes atts, String name, int defaultValue)
  {
    try
    {
      return Integer.parseInt(this.getAttribute(atts, name, ""));
    }
    catch(Exception ex)
    {
      return defaultValue;
    }
  }

  protected boolean getAttribute( Attributes atts, String name, boolean defaultValue)
  {
    try
    {
      return Boolean.parseBoolean(this.getAttribute(atts, name, ""));
    }
    catch(Exception ex)
    {
      return defaultValue;
    }
  }

  protected Date getAttribute(Attributes atts, String name, Date defaultValue, String formatDate)
  {
    try
    {
      return DateUtil.parseDate(this.getAttribute(atts, name, ""), formatDate);
    }
    catch(Exception exception)
    {
      return defaultValue;
    }
  }
  
}
