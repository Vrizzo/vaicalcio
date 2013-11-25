package it.newmedia.gokick.backOffice;

import it.newmedia.gokick.data.hibernate.beans.SiteConfiguration;
import it.newmedia.gokick.backOffice.guibean.GuiStatisticPeriod;
import it.newmedia.gokick.backOffice.providers.SiteConfigurationProvider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * E' l'oggetto che rappresenta il contesto dell'applicazione
 */
public class AppContext
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  public final static String CONFIGURATION_KEY__UPLOADED_IMAGE_WIDTH = "configuration.uploadedImageWidth";

  public final static String CONFIGURATION_KEY__CANCEL_CACHE_URL = "configuration.cancelCacheUrl";

  public final static String CONFIGURATION_KEY__CANCEL_INFOCACHE_URL = "configuration.cancelInfoCacheUrl";

  public final static String CONFIGURATION_KEY__CANCEL_USERINFO_URL = "configuration.cancelUserInfoUrl";

  public final static String CONFIGURATION_KEY__CANCEL_SPORTCENTERINFO_URL = "configuration.cancelSportCenterInfoUrl";

  public final static String CONFIGURATION_KEY__UPLOADED_IMAGE_HEIGHT = "configuration.uploadedImageHeight";

  public final static String CONFIGURATION_KEY__PICTURE_CARD_PATH = "configuration.pictureCardPath";

  public final static String CONFIGURATION_KEY__PICTURE_CARD_EMPTY_FILENAME = "configuration.pictureCardEmptyFilename";

  public final static String CONFIGURATION_KEY__GOOGLEMAPS_URL = "configuration.goggleMapUrl";

  public final static String CONFIGURATION_KEY__ARCHIVE_MATCH_EDITABLE_NUMBER_OF_DAYS = "archiveMatch.editableNumbersOfDays";

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static AppContext instance = null;
  private String siteUrl = null;
  private String googleMapUrl = null;
  private int archiveMatchEditableNumbersOfDays = -1;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  /**
   * Creates a new instance of AppContext
   */
  private AppContext()
  {
  }

  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public static AppContext getInstance()
  {
    if (instance == null)
    {
      instance = new AppContext();
    }

    return instance;
  }

  // <editor-fold defaultstate="collapsed" desc="-- Utility --"  >
  public String getRandomAlphaNumericString()
  {
    return RandomStringUtils.random(Constants.CHECK_PENDING_CODE_LENGHT, true, true);
  }

  public List<GuiStatisticPeriod> getStatisticPeriodFilter()
  {
    GregorianCalendar currentDate = new GregorianCalendar();
    List<GuiStatisticPeriod> statisticPeriodFilterList = new ArrayList<GuiStatisticPeriod>();
    GuiStatisticPeriod guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__NONE, "nessuna");
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__ALL, "di sempre");
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__LAST_MONTH, "ultimo mese");
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__LAST_THREE_MONTH, "ultimi 3 mesi");
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__LAST_SIX_MONTH, "ultimi 6 mesi");
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__LAST_TWELVE_MONTH, "ultimi 12 mesi");
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__YEAR_CURRENT, String.valueOf(currentDate.get(Calendar.YEAR)));
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__YEAR_LESS_ONE, String.valueOf(currentDate.get(Calendar.YEAR) - 1));
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__YEAR_LESS_TWO, String.valueOf(currentDate.get(Calendar.YEAR) - 2));
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__YEAR_LESS_THREE, String.valueOf(currentDate.get(Calendar.YEAR) - 3));
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__YEAR_LESS_FOUR, String.valueOf(currentDate.get(Calendar.YEAR) - 4));
    statisticPeriodFilterList.add(guiStatisticPeriod);

    return statisticPeriodFilterList;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Site configurations --"  >
  public String getSiteUrl()
  {
    if (siteUrl == null)
    {
      SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(SiteConfigurationProvider.CONFIGURATION_KEY__SITE_URL);
      this.siteUrl=siteConfiguration != null ? siteConfiguration.getValueConf() : "" ;
      return siteUrl;
    }
    return siteUrl;
  }

  public String getGoogleMapUrl()
  {
    if (googleMapUrl == null)
    {
      SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__GOOGLEMAPS_URL);
      this.googleMapUrl=siteConfiguration != null ? siteConfiguration.getValueConf() : "";
      return googleMapUrl;
    }
    return googleMapUrl;
  }
  
  public int getArchiveMatchEditableNumbersOfDays()
  {
    if (archiveMatchEditableNumbersOfDays < 0)
    {
      SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__ARCHIVE_MATCH_EDITABLE_NUMBER_OF_DAYS);
      this.archiveMatchEditableNumbersOfDays=siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.ARCHIVE_MATCH_EDITABLE_NUMBER_OF_DAYS;
      return archiveMatchEditableNumbersOfDays;
    }
    return archiveMatchEditableNumbersOfDays;
  }

  public String getSendMailPwd()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(SiteConfigurationProvider.CONFIGURATION_KEY__PWD_MAIL);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  public String getCancelInfoCacheUrl()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__CANCEL_INFOCACHE_URL);
    return siteConfiguration.getValueConf();
  }

  public String getCancelUserInfoUrl()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__CANCEL_USERINFO_URL);
    return siteConfiguration.getValueConf();
  }

  public String getCancelSportCenterInfoUrl()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__CANCEL_SPORTCENTERINFO_URL);
    return siteConfiguration.getValueConf();
  }

  public String getCancelCacheUrl()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__CANCEL_CACHE_URL);
    return siteConfiguration.getValueConf();
  }

  public Integer getUploadedImageWidth()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__UPLOADED_IMAGE_WIDTH);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.UPLOADED_IMAGE_WIDTH_DEFAULT;
  }

  public Integer getUploadedImageHeight()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__UPLOADED_IMAGE_HEIGHT);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.UPLOADED_IMAGE_HEIGHT_DEFAULT;
  }

  public String getPictureCardPath()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PICTURE_CARD_PATH);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  public String getPictureCardEmptyFilename()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PICTURE_CARD_EMPTY_FILENAME);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  
  // </editor-fold>

  // </editor-fold>  
}
