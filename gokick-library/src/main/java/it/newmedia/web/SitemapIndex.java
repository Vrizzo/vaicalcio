package it.newmedia.web;

import it.newmedia.utils.DateUtil;
import it.newmedia.io.FileUtil;
import it.newmedia.results.Result;
import java.io.File;
import java.util.Date;

public class SitemapIndex
{
  /*
   * Example of a Sitemap Index file
   *   <?xml version="1.0" encoding="UTF-8"?>
   *   <sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
   *   <sitemap>
   *   <loc>http://www.example.com/sitemap1.xml.gz</loc>
   *   <lastmod>2004-10-01T18:23:17+00:00</lastmod>
   *   </sitemap>
   *   <sitemap>
   *   <loc>http://www.example.com/sitemap2.xml.gz</loc>
   *   <lastmod>2005-01-01</lastmod>
   *   </sitemap>
   *   </sitemapindex>
   */

  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
  private static final int MAX_SITEMAP_NUMBER = 1000;
  private static final long MAX_FILE_SIZE = 10485760;
  private static final String XML_SITEMAP_INDEX_OPEN = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n";
  private static final String XML_SITEMAP_INDEX_CLOSE = "" + "</sitemapindex>";
  private static final String XML_SITEMAP_OPEN = "<sitemap>\n";
  private static final String XML_SITEMAP_CLOSE = "</sitemap>\n";
  private static final String XML_LOC_OPEN = "<loc>";
  private static final String XML_LOC_CLOSE = "</loc>\n";
  private static final String XML_LASTMOD_OPEN = "<lastmod>";
  private static final String XML_LASTMOD_CLOSE = "</lastmod>\n";
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
  public SitemapIndex()
  {
    try
    {
      this.sbXml = new StringBuffer(XML_SITEMAP_INDEX_OPEN);
      this.size = 0;
    } catch (Exception ex)
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
  public Result<Boolean> addSitemap(String urlEntry)
  {
    return this.addSitemap(urlEntry, null);
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
  public Result<Boolean> addSitemap(String urlEntry, Date lastMod)
  {
    try
    {
      // check data...
      if (urlEntry == null || urlEntry.equals(""))
      {
        return new Result<Boolean>("urlEntry cannot be null or empty");
      }
      if (this.size() >= MAX_SITEMAP_NUMBER)
      {
        return new Result<Boolean>("Cannot add another urlEntry, max limit reached");
      }

      int currentSbXmlSize = sbXml.length();

      sbXml.append(XML_SITEMAP_OPEN);
      // ...add <loc>encodedUrlEntry</loc> node as children of <url>
      sbXml.append(XML_LOC_OPEN);
      sbXml.append(encodeUrlEntry(urlEntry));
      sbXml.append(XML_LOC_CLOSE);

      // ...check and add (if not null) <lastmod>2008-04-22T14:12:01+00:00</lastmod> node as children of <url>
      if (lastMod != null)
      {
        sbXml.append(XML_LASTMOD_OPEN);
        sbXml.append(DateUtil.formatISO8601Date(lastMod, lastmodWithTime, lastmodWithTime));
        sbXml.append(XML_LASTMOD_CLOSE);
      }

      sbXml.append(XML_SITEMAP_CLOSE);

      if (this.sbXml.length() + XML_SITEMAP_CLOSE.length() > MAX_FILE_SIZE)
      {
        sbXml.delete(currentSbXmlSize, sbXml.length());
        return new Result<Boolean>(String.format("Resulting file size exceeding max size (%s)", MAX_FILE_SIZE));
      }

      this.size++;
      return new Result<Boolean>(true, true);
    } catch (Exception ex)
    {
      return new Result<Boolean>(ex);
    }
  }

  public String toString()
  {
    return sbXml.toString() + XML_SITEMAP_INDEX_CLOSE;
  }

  public Result<Boolean> writeFile(String filepath)
  {
    Result<Boolean> result = FileUtil.writeFile(this.toString(), filepath, FileUtil.FILE_ENCODING_UTF_8);
    if (!result.isSuccessNotNull())
    {
      return result;
    }
    File file = new File(filepath);
    if (!file.exists())
    {
      return new Result<Boolean>("Cannot read file size for [" + filepath + "]. File does not exist!");
    }
    long length = file.length();
    if (length > MAX_FILE_SIZE)
    {
      return new Result<Boolean>(String.format("Resulting file size exceeding max size (%s)", MAX_FILE_SIZE));
    }
    return new Result<Boolean>(true, true);
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Private Methods --"  >
  private String encodeUrlEntry(String urlEntry)
  {
    StringBuffer res = new StringBuffer();
    char[] chars = urlEntry.toCharArray();
    for (char c : chars)
    {
      switch (c)
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
  public static void main(String[] args)
  {
    SitemapIndex sitemapIndex;

    try
    {
      sitemapIndex = new SitemapIndex();
      System.out.println("Creo:");
      System.out.println(sitemapIndex);
      Result<Boolean> res;
      sitemapIndex.addSitemap("http://www.example.it/index.html");
      System.out.println("Size vale " + sitemapIndex.size());

      sitemapIndex.addSitemap("http://www.example.it/index.html&a=ab<rt");
      System.out.println("Size vale " + sitemapIndex.size());

      sitemapIndex.addSitemap("http://www.example.it/index.html", new Date());
      System.out.println("Size vale " + sitemapIndex.size());

      Result<Boolean> result = sitemapIndex.writeFile("c:/temp/sitemap.xml");
      System.out.println("Scrivo: " + (result.isSuccessNotNull() ? "OK" : result.getErrorMessage()));
    } catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
}
