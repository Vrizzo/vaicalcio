package it.newmedia.gokick.site;

import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.data.hibernate.beans.Country;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.SiteConfiguration;
import it.newmedia.gokick.site.guibean.GuiStatisticPeriod;
import it.newmedia.gokick.site.infos.*;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.site.providers.SiteConfigurationProvider;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 *
 * E' l'oggetto che rappresenta il contesto dell'applicazione
 */
public class AppContext
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_PHOTO_POSITION_TOP = "configuration.playerPhotoPositionTop";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_PHOTO_POSITION_LEFT = "configuration.playerPhotoPositionLeft";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_PHOTO_WIDTH = "configuration.playerPhotoWidth";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_PHOTO_HEIGHT = "configuration.playerPhotoHeight";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_NAME_POSITION_TOP = "configuration.playerNamePositionTop";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_NAME_POSITION_LEFT = "configuration.playerNamePositionLeft";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_NAME_WIDTH = "configuration.playerNameWidth";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_NAME_HEIGHT = "configuration.playerNameHeight";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PICTURE_CARD_PATH = "configuration.pictureCardPath";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__AVATAR_PATH = "configuration.avatarPath";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PICTURE_CARD_EMPTY_FILENAME = "configuration.pictureCardEmptyFilename";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__AVATAR_EMPTY_FILENAME = "configuration.avatarEmptyFilename";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__CHECK_PASSWORD_EXPIRE_AFTER_HOURS = "configuration.checkPasswordExpireAfterHours";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__AVATAR_MASK_IMAGE_PATH = "configuration.avatarMaskImagePath";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PICTURE_COMMENT_PATH = "configuration.pictureCommentPath";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PICTURE_COMMENT_SUFFIX_NAME = "configuration.pictureCommentSuffixName";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PICTURE_COMMENT_VIRTUAL_PATH = "configuration.pictureCommentVirtualPath";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_PHOTO_AVATAR_WIDTH = "configuration.playerPhotoAvatarWidth";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PLAYER_PHOTO_AVATAR_HEIGHT = "configuration.playerPhotoAvatarHeight";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PIC_CARD_UPLOADED_FILE_SIZE_LIMIT = "pictureCard.uploadedFile.sizeLimit";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__PIC_CARD_UPLOADED_FILE_FORMAT_TYPE = "pictureCard.uploadedFile.formatType";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__ARCHIVE_MATCH_EDITABLE_NUMBER_OF_DAYS = "archiveMatch.editableNumbersOfDays";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__CANCEL_CACHE_URL = "configuration.cancelCacheUrl";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__HOW_MANY_OUTERS = "configuration.howManyOuters";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__CREATE_PIC_URL = "configuration.createPicUrl";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__EDIT_NAME_PIC_URL = "configuration.editNamePicUrl";
  /**
   *
   */
  public final static String CONFIGURATION_KEY__HOW_MANY_ITEM = "configuration.displayTables.howManyItems";
  
  public final static String CONFIGURATION_KEY__URL_FACEBOOK_APP = "configuration.facebookApp.url";
  public final static String CONFIGURATION_KEY__REDIRECT_FACEBOOK_APP = "configuration.facebookApp.redirect";
  public final static String CONFIGURATION_KEY__FACEBOOK_WS_URL = "configuration.facebookWs.url";
  public final static String CONFIGURATION_KEY__FACEBOOK_WS_TIMEOUT = "configuration.facebookWs.timeout";
  private static final String CONFIGURATION_KEY__FACEBOOK_WS_USERNAME = "configuration.facebookWs.username" ;
  private static final String CONFIGURATION_KEY__FACEBOOK_WS_PASSWORD = "configuration.facebookWs.password" ;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static AppContext instance = null;

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
  /**
   *
   * @return
   */
  public static AppContext getInstance()
  {
    if (instance == null)
    {
      instance = new AppContext();
    }

    return instance;
  }

  // <editor-fold defaultstate="collapsed" desc="-- Info --"  >
  /**
   *
   * @return il numero di iscritti al sito con stato Enabled
   */
  public int getGoKickerCount()
  {
    return InfoProvider.getGoKickerCount();
  }

  /**
   * @return il numero di centri sportivi nel sito APPROVED e ENABLED
   */
  public int getSportCenterCount()
  {
    return InfoProvider.getSportCenterCount();
  }

  /**
   *
   * @return la lista di tutte le nazioni orinate per POSITION , NAME
   */
  public List<Country> getAllCountry()
  {
    return InfoProvider.getAllCountry();
  }

  /**
   *
   * @param language lingua corrente
   * @return la lista di tutti i ruoli
   */
  public List<PlayerRoleInfo> getAllPlayerRoleInfo(Language language, Cobrand currentCobrand)
  {
    return InfoProvider.getAllPlayerRoleInfo(language, currentCobrand);
  }

  /**
   *
   * @param language lingua corrente
   * @return la lista di tutti i motivi di segnalazione abuso
   */
  public List<AbuseReasonInfo> getAllAbuseReasons(Language language, Cobrand currentCobrand)
  {
    return InfoProvider.getAllAbuseReasons(language, currentCobrand);
  }

  /**
   * @param language lingua corrente
   * @return la lista di tutti i tipi di copertura dei campi
   */
  public List<PitchCoverInfo> getAllPitchCoverInfo(Language language, Cobrand currentCobrand)
  {
    return InfoProvider.getAllPitchCoverInfo(language, currentCobrand);
  }

  /**
   *
   * @param language lingua corrente
   * @return la lista di tutti i tipi di campi (Gratuito,Pagamento,...)
   */
  public List<PitchTypeInfo> getAllPitchTypeInfo(Language language, Cobrand currentCobrand)
  {
    return InfoProvider.getAllPitchTypeInfo(language, currentCobrand);
  }

  /**
   *
   * @param language lingua corrente
   * @return la lista di tutti i tipi di partite che si possono giocare
   */
  public List<MatchTypeInfo> getAllMatchTypeInfo(Language language, Cobrand currentCobrand)
  {
    return InfoProvider.getAllMatchTypeInfo(language, currentCobrand);
  }

  /**
   * @param language la lingua corrente
   * @return la lista di tutti i filtri delle statistiche
   */
  public List<GuiStatisticPeriod> getStatisticPeriodFilter(Language language, Cobrand currentCobrand)
  {
    return InfoProvider.getStatisticPeriodFilter(language, currentCobrand);
  }

  /**
   *
   * @return la lista dei linguaggi ENABLED ordinata per Position
   */
  public List<Language> getLanguageList()
  {
    return InfoProvider.getLanguageList();
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Site configurations --"  >
  /**
   *
   * @return
   */
  public String getCancelCacheUrl()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__CANCEL_CACHE_URL);
    return siteConfiguration.getValueConf();
  }

  /**
   *
   * @return numero di esterni max da poter iscrivere alla partita
   */
  public int getHowManyOuters()
  {
    int howMany = SiteConfigurationProvider.get(CONFIGURATION_KEY__HOW_MANY_OUTERS,2);
    return howMany;
  }

  /**
   *
   * @return numero di risultati da visualizzare (display tables)
   */
  public int getHowManyItems()
  {
    int howMany = SiteConfigurationProvider.get(CONFIGURATION_KEY__HOW_MANY_ITEM,20);
    return howMany;
  }

  public String getFacebookUrl()
  {
    return SiteConfigurationProvider.get(CONFIGURATION_KEY__URL_FACEBOOK_APP, "");
  }
  
  public String getRedirectUrlFacebook(Cobrand currentCobrand)
  {
    StringBuilder prefix=new StringBuilder(currentCobrand.getSiteUrl());
    return prefix.append(SiteConfigurationProvider.get(CONFIGURATION_KEY__REDIRECT_FACEBOOK_APP, "")).toString();
  }

  /**
   *
   * @return
   */
  public String getCreatePicUrl()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__CREATE_PIC_URL);
    return siteConfiguration.getValueConf();
  }

  /**
   *
   * @return
   */
  public String getEditNamePicUrl()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__EDIT_NAME_PIC_URL);
    return siteConfiguration.getValueConf();
  }

  /**
   *
   * @return
   */
  public String getGoogleMapAddressUrl()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__GOOGLEMAPS_ADDRESS_URL, "");
  }
  
  public String getGoogleMapLatLonUrl()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__GOOGLEMAPS_LATLON_URL, "");
  }
  
  /**
   *
   * @return
   */
  public int getUploadedImageWidth()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__UPLOADED_IMAGE_WIDTH, Constants.UPLOADED_IMAGE_WIDTH_DEFAULT);
  }
  
  /**
   *
   * @return
   */
  public int getUploadedImageHeight()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__UPLOADED_IMAGE_HEIGHT, Constants.UPLOADED_IMAGE_HEIGHT_DEFAULT);
  }

  /**
   *
   * @return
   */
  public String getMaskImagePath()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__MASK_IMAGE_PATH, "");
  }

  /**
   *
   * @return
   */
  public String getPlayerNameFontName()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__PLAYER_NAME_FONT_NAME, Constants.PLAYER_NAME_FONT_NAME_DEFAULT);
  }

  /**
   *
   * @return
   */
  public int getPlayerPhotoPositionTop()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PLAYER_PHOTO_POSITION_TOP);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.PLAYER_PHOTO_POSITION_TOP_DEFAULT;
  }

  /**
   *
   * @return
   */
  public int getPlayerPhotoPositionLeft()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PLAYER_PHOTO_POSITION_LEFT);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.PLAYER_PHOTO_POSITION_LEFT_DEFAULT;
  }

  /**
   *
   * @return
   */
  public int getPlayerPhotoWidth()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PLAYER_PHOTO_WIDTH);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.PLAYER_PHOTO_WIDTH_DEFAULT;
  }

  /**
   *
   * @return
   */
  public int getPlayerPhotoHeight()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PLAYER_PHOTO_HEIGHT);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.PLAYER_PHOTO_HEIGHT_DEFAULT;
  }

  /**
   *
   * @return
   */
  public int getPlayerNamePositionTop()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PLAYER_NAME_POSITION_TOP);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.PLAYER_NAME_POSITION_TOP_DEFAULT;
  }

  /**
   *
   * @return
   */
  public int getPlayerNamePositionLeft()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PLAYER_NAME_POSITION_LEFT);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.PLAYER_NAME_POSITION_LEFT_DEFAULT;
  }

  /**
   *
   * @return
   */
  public int getPlayerNameWidth()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PLAYER_NAME_WIDTH);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.PLAYER_NAME_WIDTH_DEFAULT;
  }

  /**
   *
   * @return
   */
  public int getPlayerNameHeight()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PLAYER_NAME_HEIGHT);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.PLAYER_NAME_HEIGHT_DEFAULT;
  }

  /**
   *
   * @return
   */
  public String getPictureCardPath()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PICTURE_CARD_PATH);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  /**
   *
   * @return
   */
  public String getAvatarPath()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__AVATAR_PATH);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  /**
   *
   * @return
   */
  public String getPictureCardEmptyFilename()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PICTURE_CARD_EMPTY_FILENAME);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  /**
   *
   * @return
   */
  public String getAvatarEmptyFilename()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__AVATAR_EMPTY_FILENAME);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  /**
   *
   * @return
   */
  public int getCheckPasswordExpireAfterHours()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__CHECK_PASSWORD_EXPIRE_AFTER_HOURS);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.CHECK_PASSWORD_EXPIRE_AFTER_HOURS_DEFAULT;
  }

  /**
   * 
   * @return
   */
  public String getAvatarMaskImagePath()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__AVATAR_MASK_IMAGE_PATH);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  /**
   *
   * @return
   */
  public String getPictureCommentPath()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PICTURE_COMMENT_PATH);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  /**
   *
   * @return
   */
  public String getPictureCommentSuffixName()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PICTURE_COMMENT_SUFFIX_NAME);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  /**
   *
   * @return
   */
  public String getPictureCommentvirtualPath()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PICTURE_COMMENT_VIRTUAL_PATH);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  /**
   *
   * @return
   */
  public int getPlayerPhotoAvatarWidth()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PLAYER_PHOTO_AVATAR_WIDTH);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.PLAYER_PHOTO_AVATAR_WIDTH_DEFAULT;
  }

  /**
   *
   * @return
   */
  public int getPlayerPhotoAvatarHeight()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PLAYER_PHOTO_AVATAR_HEIGHT);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.PLAYER_PHOTO_AVATAR_HEIGHT_DEFAULT;
  }

  /**
   *
   * @return
   */
  public int getArchiveMatchEditableNumbersOfDays()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__ARCHIVE_MATCH_EDITABLE_NUMBER_OF_DAYS);
    return siteConfiguration != null ? Integer.parseInt(siteConfiguration.getValueConf()) : Constants.ARCHIVE_MATCH_EDITABLE_NUMBER_OF_DAYS;
  }

  /**
   *
   * @return
   */
  public long getPicCardUploadedFileSizeLimit()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PIC_CARD_UPLOADED_FILE_SIZE_LIMIT);
    return siteConfiguration != null ? Long.parseLong(siteConfiguration.getValueConf()) : Constants.PIC_CARD_UPLOADED_FILE_SIZE_LIMIT_DEFAULT;
  }

  /**
   *
   * @return
   */
  public String getPicCardUploadedFileFormatType()
  {
    SiteConfiguration siteConfiguration = SiteConfigurationProvider.getSiteConfiguration(CONFIGURATION_KEY__PIC_CARD_UPLOADED_FILE_FORMAT_TYPE);
    return siteConfiguration != null ? siteConfiguration.getValueConf() : "";
  }

  /**
   *
   * @return
   */
  public int getInvitationsInitial()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__INVITATIONS_INITIAL, Constants.INITIAL_INVITATIONS_DEFAULT);
  }

  /**
   *
   * @return
   */
  public boolean getInvitationsEnabled()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__INVITATIONS_ENABLED, true);
  }
  
  //<editor-fold defaultstate="collapsed" desc="MAIL CONTACT IMPORTER CONFIGURATION">
  
  public String getImportContactURL(int idNationalityCountry)
  {
    //Attenzione!
    //Alla chiave viene appeso il suffisso della lingua
    String importUrl = SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__IMPORT_CONTACTS_URL + "_" + idNationalityCountry, "");
    if( StringUtils.isNotBlank(importUrl))
    {
      return importUrl;
    }
    //Se non trovo quello della lingua scelta, ritorno quello italiano 8...se non cìè ci sarà un errore...)
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__IMPORT_CONTACTS_URL + 107, "");
  }

  public String getImportContactStep()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__IMPORT_CONTACTS_STEP, "");
  }

  public String getImportContactImporter()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__IMPORT_CONTACTS_IMPORTER, "");
  }

  public int getMaxMonthsOffsetForResults()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__MAX_MONTHS_OFFSET_FOR_RESULTS, Constants.MAX_MONTHS_OFFSET_FOR_RESULTS_DEFAULT);
  }

  public int getMaxUserResults()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__MAX_USER_FOR_RESULTS, Constants.MAX_USER_FOR_RESULTS_DEFAULT);
  }

  public boolean getUseNewSessionMethods()
  {
    return SiteConfigurationProvider.get(SiteConfigurationProvider.CONFIGURATION_KEY__USE_NEWSESSION_METHODS, Constants.USE_NEWSESSION_METHODS_DEFAULT);
  }

  //</editor-fold>
  
  
  // </editor-fold>


  // </editor-fold>  


  public String getFacebookWebServiceUrl()
  {
    return SiteConfigurationProvider.get(CONFIGURATION_KEY__FACEBOOK_WS_URL, "");
  }

  public int getFacebookWebServiceTimeout()
  {
    return SiteConfigurationProvider.get(CONFIGURATION_KEY__FACEBOOK_WS_TIMEOUT, 30000);
  }

  public String getFacebookWebServiceUsername()
  {
    return SiteConfigurationProvider.get(CONFIGURATION_KEY__FACEBOOK_WS_USERNAME, "");
  }

  public String getFacebookWebServicePassword()
  {
    return SiteConfigurationProvider.get(CONFIGURATION_KEY__FACEBOOK_WS_PASSWORD, "");
  }
}
