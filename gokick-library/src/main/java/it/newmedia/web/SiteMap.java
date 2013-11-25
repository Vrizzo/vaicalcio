package it.newmedia.web;

import it.newmedia.utils.DateUtil;
import it.newmedia.io.FileUtil;
import it.newmedia.results.Result;
import java.io.File;
import java.util.Date;

public class SiteMap
{
  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
  
  private static final int MAX_URL_NUMBER = 50000;
  private static final long MAX_FILE_SIZE = 10485760;
  
  
  private static final String XML_URLSET_OPEN = "" +
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
      "<urlset " +
      "xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" " +
      "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
      "xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 " +
      "http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\" >\n";
  private static final String XML_URLSET_CLOSE = "" +
      "</urlset>";
  
  private static final String XML_URL_OPEN = "<url>\n";
  private static final String XML_URL_CLOSE = "</url>\n";
  private static final String XML_LOC_OPEN = "<loc>";
  private static final String XML_LOC_CLOSE = "</loc>\n";
  private static final String XML_LASTMOD_OPEN = "<lastmod>";
  private static final String XML_LASTMOD_CLOSE = "</lastmod>\n";
  private static final String XML_CHANGEFREQ_OPEN = "<changefreq>";
  private static final String XML_CHANGEFREQ_CLOSE = "</changefreq>\n";
  private static final String XML_PRIORITY_OPEN = "<priority>";
  private static final String XML_PRIORITY_CLOSE = "</priority>\n";
  
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  
  private boolean lastmodWithTime;
  private StringBuffer sbXml = new StringBuffer();
  private int size;
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Getters/Setters --"  >
  
  public boolean isLastmodWithTime()
  {
    return lastmodWithTime;
  }
  
  public void setLastmodWithTime(boolean lastmodWithTime)
  {
    this.lastmodWithTime = lastmodWithTime;
  }
  
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Constructs --"  >
  
  /** Creates a new instance of SiteMap */
  public SiteMap()
  {
    try
    {
      this.sbXml = new StringBuffer(XML_URLSET_OPEN);
      this.size = 0;
    }
    catch(Exception ex)
    {
    }
  }
  
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Public Methods --"  >
  
  public int size()
  {
    return this.size;
  }
  
  /**
   * Add a new entry for a sitemap protocol
   * Node with lastmodand and node changefreq are not write
   * @param urlEntry url to add
   * @return A result with true if success otherwise false and info about error
   */
  public Result<Boolean> addSiteUrl( String urlEntry)
  {
    return this.addSiteUrl(urlEntry, null, null);
  }
  
  /**
   * Add a new entry for a sitemap protocol
   * Node with  changefreq are not write
   * @param urlEntry url to add
   * @param lastMod A date with info about last modification. 
   * According isLastmodWithTime, date are in 
   * short or extended format
   * @return A result with true if success otherwise false and info about error
   */
  public Result<Boolean> addSiteUrl( String urlEntry, Date lastMod )
  {
    return this.addSiteUrl(urlEntry, lastMod, null);
  }
  
  /**
   * Add a new entry for a sitemap protocol
   * Node with  changefreq are not write
   * @param urlEntry url to add
   * @param lastMod A date with info about last modification. 
   * According isLastmodWithTime, date are in 
   * short or extended format. If null nothing is write
   * @param changeFreq Enum with info about change frequency of urlEntry.
   * If null nothing is write
   * @return A result with true if success otherwise false and info about error
   */
  public Result<Boolean> addSiteUrl( String urlEntry, Date lastMod, Frequency changeFreq )
  {
    return this.addSiteUrl(urlEntry, lastMod, changeFreq, null);
  }
  
  /**
   * Add a new entry for a sitemap protocol
   * Node with  changefreq are not write
   * @return A result with true if success otherwise false and info about error
   * @param urlEntry url to add
   * @param lastMod A date with info about last modification. 
   * According isLastmodWithTime, date are in 
   * short or extended format. If null nothing is write
   * @param changeFreq Enum with info about change frequency of urlEntry.
   * If null nothing is write
   * @param priority Priority for urlEntry. If null nothing is write
   */
  public Result<Boolean> addSiteUrl( String urlEntry, Date lastMod, Frequency changeFreq, Priority priority)
  {
    try
    {
      // check data...
      if(urlEntry == null || urlEntry.equals(""))
        return new Result<Boolean>("urlEntry cannot be null or empty");
      if(this.size() >= MAX_URL_NUMBER)
        return new Result<Boolean>("Cannot add another urlEntry, max limit reached");
      
      int currentSbXmlSize = sbXml.length();
      
      sbXml.append(XML_URL_OPEN);
      // ...add <loc>encodedUrlEntry</loc> node as children of <url>
      sbXml.append(XML_LOC_OPEN);
      sbXml.append(encodeUrlEntry(urlEntry));
      sbXml.append(XML_LOC_CLOSE);
      
      // ...check and add (if not null) <lastmod>2008-04-22T14:12:01+00:00</lastmod> node as children of <url>
      if(lastMod != null)
      {
        sbXml.append(XML_LASTMOD_OPEN);
        sbXml.append(DateUtil.formatISO8601Date(lastMod, lastmodWithTime, lastmodWithTime ));
        sbXml.append(XML_LASTMOD_CLOSE);
      }
      
      // ...check and add (if not null) <changefreq>...see Frequency enum...</changefreq> node as children of <url>
      if(changeFreq != null)
      {
        sbXml.append(XML_CHANGEFREQ_OPEN);
        sbXml.append(changeFreq.getValue());
        sbXml.append(XML_CHANGEFREQ_CLOSE);
      }

      // ...check and add (if not null) <priority>...see Priority enum...</priority> node as children of <url>
      if(priority != null)
      {
        sbXml.append(XML_PRIORITY_OPEN);
        sbXml.append(priority.getValue());
        sbXml.append(XML_PRIORITY_CLOSE);
      }

      sbXml.append(XML_URL_CLOSE);
      
      if( this.sbXml.length() +  XML_URLSET_CLOSE.length() > MAX_FILE_SIZE )
      {
        sbXml.delete(currentSbXmlSize, sbXml.length());
        return new Result<Boolean>(String.format("Resulting file size exceeding max size (%s)", MAX_FILE_SIZE));
      }
      
      this.size++;
      return new Result<Boolean>(true, true);
    }
    catch (Exception ex)
    {
      return new Result<Boolean>(ex);
    }
  }
  
  public String toString()
  {
    return sbXml.toString() + XML_URLSET_CLOSE;
  }
  
  public Result<Boolean> writeFile(String filepath)
  {
    Result<Boolean> result = FileUtil.writeFile(this.toString(), filepath, FileUtil.FILE_ENCODING_UTF_8);
    if(!result.isSuccessNotNull())
      return result;
    File file = new File(filepath);
    if( !file.exists() )
      return new Result<Boolean>("Cannot read file size for [" + filepath + "]. File does not exist!");
    long length = file.length();
    if( length > MAX_FILE_SIZE )
      return new Result<Boolean>(String.format("Resulting file size exceeding max size (%s)", MAX_FILE_SIZE));
    return new Result<Boolean>(true, true);
  }
  
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Private Methods --"  >
  
  private String encodeUrlEntry(String urlEntry)
  {
    StringBuffer res = new StringBuffer();
    char[] chars = urlEntry.toCharArray();
    for(char c : chars)
    {
      switch(c)
      {
        case '&':
          res.append("&amp;");
          break;
        case '>':
          res.append("&gt;");
          break;
        case '<':
          res.append("&lt;");
          break;
        case '"':
          res.append("&quot;");
          break;
        case '\'':
          res.append("&apos;");
          break;
        default:
          res.append(c);
      }
    }
    //also trim white space
    return res.toString().trim();
  }
  
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Enums --"  >
  
  public static enum Frequency
  {
    Always("always"),
    Hourly("hourly"),
    Daily("daily"),
    Weekly("weekly"),
    Monthly("monthly"),
    Yearly("yearly"),
    Never("never"),
    Undefined("Undefined");
    
    String frequency;
    
    Frequency(String frequency)
    {
      this.frequency = frequency;
    }
    
    public String getValue()
    {
      return this.frequency;
    }
    
    public static Frequency getEnum( String frequency )
    {
      for (Frequency enumFrequency   : Frequency.values())
      {
        if( enumFrequency.getValue().equals(frequency) )
          return enumFrequency;
      }
      return Frequency.Undefined;
    }
    
  }
  
  public static enum Priority
  {
    zero("0.0"),
    dotOne("0.1"),
    dotTwo("0.2"),
    dotThree("0.3"),
    dotFour("0.4"),
    dotFive("0.5"),
    dotSix("0.6"),
    dotSeven("0.7"),
    dotEigth("0.8"),
    dotNine("0.9"),
    one("1.0"),
    Undefined("Undefined");
    
    String priority;
    
    Priority(String priority)
    {
      this.priority = priority;
    }
    
    public String getValue()
    {
      return this.priority;
    }
    
    public static Priority getEnum( String priority )
    {
      for (Priority enumPriority   : Priority.values())
      {
        if( enumPriority.getValue().equals(priority) )
          return enumPriority;
      }
      return Priority.Undefined;
    }
    
  }
  
  // </editor-fold>
  
  public static void main(String[] args)
  {
    SiteMap siteMap;
    
    try
    {
      siteMap = new SiteMap();
      System.out.println("Creo:");
      System.out.println(siteMap);
      Result<Boolean> res;
      siteMap.addSiteUrl("http://www.example.it/index.html");
      System.out.println("Size vale " + siteMap.size());

      siteMap.addSiteUrl("http://www.example.it/index.html&a=ab<rt");
      System.out.println("Size vale " + siteMap.size());

      siteMap.addSiteUrl("http://www.example.it/index.html", new Date());
      System.out.println("Size vale " + siteMap.size());
        
      siteMap.setLastmodWithTime(true);
      siteMap.addSiteUrl("http://www.example.it/index.html", new Date(), Frequency.Monthly);
      System.out.println("Size vale " + siteMap.size());
      
      siteMap.setLastmodWithTime(false);
      siteMap.addSiteUrl("http://www.example.it/index.html", new Date(), Frequency.Hourly, Priority.zero);
      siteMap.addSiteUrl("http://www.example.it/index.html", new Date(), Frequency.Always, Priority.dotSeven);
      siteMap.addSiteUrl("http://www.example.it/index.html", new Date(), Frequency.Never, Priority.one);
      System.out.println("Size vale " + siteMap.size());

      Result<Boolean> result = siteMap.writeFile("e:/temp/sitemap.xml");
      System.out.println("Scrivo: " +  (result.isSuccessNotNull() ? "OK" : result.getErrorMessage()));
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
  
}


