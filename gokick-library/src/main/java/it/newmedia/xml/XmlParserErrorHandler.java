/*
 * DomParserErrorHandler.java
 *
 * Created on 19 marzo 2007, 17.11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.newmedia.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Error Handler for xml parser
 */
public class XmlParserErrorHandler implements ErrorHandler
{
    public void warning(SAXParseException e)
    throws SAXException
    {
    }
    public void error(SAXParseException e)
    throws SAXException
    {
    }
    public void fatalError(SAXParseException e)
    throws SAXException
    {
    }
 
}
