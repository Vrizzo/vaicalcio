package it.newmedia.gokick.site;

/**
 *
 * Classe che definisce le costanti comuni che vengono utilizzate all'interno del progetto
 */
public class Constants
{
  //CANCEL ACCOUNT (USER IN STATUS DELETED)
  public static final String USER_DELETED_FIRSTNAME = "User";
  public static final String USER_DELETED_LASTNAME = "Deleted";
  public static final String USER_DELETED_NULLFIELD = "";
  public static final String USER_DELETED_EMAIL = "deleted@gokick.it";

  public static final String USER_ID_AUTOLOG = "u";
  public static final String USER_PWD_AUTOLOG = "p";
  

  public static final String LANGUAGE_IT = "it";
  public static final String LOCALE_IT = "it_IT";

  //UserPlayerAction
  public static final String LABEL_USERPLAYER_ES_TITLESCHEDA = "label.userPlayerEsTitlescheda";
  public static final String LABEL_USERPLAYER_ES_CARATTERISTICA = "label.userPlayerEsCaratteristica";

  public final static String STRUTS_RESULT_NAME__EXCEPTION = "exception";
  public final static String STRUTS_RESULT_NAME__ACCESS_DENIED = "accessDenied";
  public final static String STRUTS_RESULT_NAME__GO_TO_URL = "goToUrl";
  public final static String STRUTS_RESULT_NAME__LOGGED_IN = "loggedIn";
  public final static String STRUTS_RESULT_NAME__NOT_LOGGED_IN = "notLoggedIn";
  public final static String STRUTS_RESULT_NAME__INFO = "info";
  public final static String STRUTS_RESULT_NAME__SAVE_DRAFT = "saveDraft";
  public final static String STRUTS_RESULT_NAME__ARCHIVE_MATCH = "archive";
  public final static String STRUTS_RESULT_NAME__REPORT_MATCH = "report";
  public final static String STRUTS_RESULT_NAME__SUCCESS_SEARCH = "successSearch";
  public final static String STRUTS_RESULT_NAME__MATCH_INSERTED = "matchInserted";
  public final static String STRUTS_RESULT_NAME__MATCH_UPDATED = "matchUpdated";
  public final static String STRUTS_RESULT_NAME__MATCH_MESSAGE_INSERTED = "matchCommentInserted";
  public final static String STRUTS_RESULT_NAME__MATCH_MESSAGE_DELETED = "matchCommentDeleted";
  public final static String STRUTS_RESULT_NAME__MATCH_MESSAGE_EDIT = "matchCommentEdit";
  public final static String STRUTS_RESULT_NAME__MATCH_MESSAGE_PREVIEW = "matchCommentPreview";
  public final static String STRUTS_RESULT_NAME__INSERTED= "inserted";
  public final static String STRUTS_RESULT_NAME__OPEN_POPUP = "openPopUp";
  public final static String STRUTS_RESULT_NAME__GOTO_INPUT = "gotoInput";
  public final static String STRUTS_RESULT_NAME__ACCOUNT_UPDATED = "accountUpdated";
 
  public static final String PICTURE_CARD_FILE_FORMAT = "0000000";
  public static final String PICTURE_CARD_FILE_INDEX_FORMAT = "00";

  public static final String COUNTRY_FLAG_IMAGE_PREFIX = "country_flag_";

  public static final String DATE_FORMAT__DDMMYYYYHHMM = "dd/MM/yyyy HH:mm";
  public static final String DATE_FORMAT__DDMMYYYY = "dd/MM/yyyy";
  public static final String DATE_FORMAT__EEEDDMMM = "EEE dd MMM";
  public static final String DATE_FORMAT__EEEEEEEEEEEDDMMMM = "EEEEEEEEEEE dd MMMM";
  public static final String DATE_FORMAT__EEEEEEEEEEEDDMMMMYYYY = "EEEEEEEEEEE dd MMMM yyyy";
  public static final String DATE_FORMAT__DDMMMMYYYY = "dd MMMM yyyy";
  public static final String DATE_FORMAT__DDMMMYY_HHMM = "dd MMM yy HH:mm";
  public static final String DATE_FORMAT__HHMM = "HH:mm";
  public static final String DATE_FORMAT__YY = "yy";
  public static final String DATE_FORMAT__YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";


  public final static int UPLOADED_IMAGE_WIDTH_DEFAULT = 623;
  public final static int UPLOADED_IMAGE_HEIGHT_DEFAULT = 476;
  public final static String PLAYER_NAME_FONT_NAME_DEFAULT = "Arial";
  public final static int PLAYER_PHOTO_POSITION_TOP_DEFAULT = 5;
  public final static int PLAYER_PHOTO_POSITION_LEFT_DEFAULT = 5;
  public final static int PLAYER_PHOTO_WIDTH_DEFAULT = 160;
  public final static int PLAYER_PHOTO_HEIGHT_DEFAULT = 173;
  public final static int PLAYER_NAME_POSITION_TOP_DEFAULT = 184;
  public final static int PLAYER_NAME_POSITION_LEFT_DEFAULT = 5;
  public final static int PLAYER_NAME_WIDTH_DEFAULT = 160;
  public final static int PLAYER_NAME_HEIGHT_DEFAULT = 22;
  public final static int CHECK_PASSWORD_EXPIRE_AFTER_HOURS_DEFAULT = 8;
  public final static int PLAYER_PHOTO_AVATAR_WIDTH_DEFAULT = 84;
  public final static int PLAYER_PHOTO_AVATAR_HEIGHT_DEFAULT = 91;
  public final static int ARCHIVE_MATCH_EDITABLE_NUMBER_OF_DAYS = 7;
  public final static long PIC_CARD_UPLOADED_FILE_SIZE_LIMIT_DEFAULT = 5100000L;
  public final static int INITIAL_INVITATIONS_DEFAULT = 0;

  public final static String SQUAD_PREFERENCES__PLAYING_WEEKDAYS_NONE = "0000000";

  public final static String STATISTIC_PERIOD__NONE = "";
  public final static String STATISTIC_PERIOD__ALL = "all";
  public final static String STATISTIC_PERIOD__LAST_MONTH = "lastMonth";
  public final static String STATISTIC_PERIOD__LAST_THREE_MONTH = "lastThreeMonth";
  public final static String STATISTIC_PERIOD__LAST_SIX_MONTH = "lastSixMonth";
  public final static String STATISTIC_PERIOD__LAST_TWELVE_MONTH = "lastTwelveMonth";
  public final static String STATISTIC_PERIOD__YEAR_CURRENT = "yearCurrent";
  public final static String STATISTIC_PERIOD__YEAR_LESS_ONE = "yearLessOne";
  public final static String STATISTIC_PERIOD__YEAR_LESS_TWO = "yearLessTwo";
  public final static String STATISTIC_PERIOD__YEAR_LESS_THREE = "yearLessThree";
  public final static String STATISTIC_PERIOD__YEAR_LESS_FOUR = "yearLessFour";

  public final static int CALENDAR_TYPE__MATCH = 1;
  public final static int CALENDAR_TYPE__CHALLENGE = 2;
  public final static int CALENDAR_TYPE__EVENT = 3;

  public final static int PLAYER_ROLE_ID__GK = 1;
  public final static int PLAYER_ROLE_ID__DF = 2;
  public final static int PLAYER_ROLE_ID__CC = 3;
  public final static int PLAYER_ROLE_ID__AT = 4;

  public final static int STRING_LENGHT_0 = 0;
  public final static int STRING_LENGHT_2 = 2;
  public final static int STRING_LENGHT_5 = 5;
  public final static int STRING_LENGHT_6 = 6;
  public final static int STRING_LENGHT_10 = 10;
  public final static int STRING_LENGHT_15 = 15;
  public final static int STRING_LENGHT_20 = 20;
  public final static int STRING_LENGHT_40 = 40;
  public final static int STRING_LENGHT_1000 = 1000;
  public final static int STRING_LENGHT_5000 = 5000;
  public final static int NUMBER_VALUE_0 = 0;
  public final static int INVALID_ID = 0;
  public final static int MAX_GOALS = 100;
  public final static String STRING_EMPTY = "";

  /**
   * Indica il valore dell'id nel database corrispondente al campo a pagamento.
   * Attenzione... se il valore su db cambia questa costante deve essere aggiornata
   */
  public final static int PITCH_TYPE_PAY = 2;

  public final static String COOKIE_USER_ID = "gokick_id";
  public final static String COOKIE_USER_PASSWORD = "gokick_password";

  public final static String COOKIE_EXTAPP_USER_ID = "extapp_id";
  public final static String COOKIE_EXTAPP_USER_KEY = "extapp_key";
  public final static String COOKIE_EXTAPP_USER_LANG = "extapp_lang";

  public final static String SESSION_KEY__PICTURE_CARD_FILE = "pictureCardFile";
  public final static String SESSION_KEY__PICTURE_CARD_FILE_RATIO = "pictureCardRatio";
  public final static String SESSION_KEY__PICTURE_CARD_FILE_PATH = "pictureCardPath";
  
  public final static String SESSION_KEY__SQUAD_MANAGE_MESSAGE = "squadManageMessage";
  public final static String SESSION_KEY__MATCH_CANCELLED_MESSAGE = "matchCancelledMessage";
  public final static String SESSION_KEY__MATCH_PUBLISHED_MESSAGE = "matchPublishedMessage";
  public final static String SESSION_KEY__LAST_MESSAGE = "lastMessage";
  
  public final static String REQUEST_PARAM__INFO = "info";


  public final static String AUTOREPLACEMENT__SITE_URL = "@@@SITE_URL@@@";
  public final static String AUTOREPLACEMENT__USER_FIRSTNAME = "###FIRSTNAME###";
  public final static String AUTOREPLACEMENT__USER_LASTNAME = "###LASTNAME###";
  public final static String AUTOREPLACEMENT__USER_COMPLETENAME = "@@@COMPLETENAME@@@";

  //<editor-fold defaultstate="collapsed" desc="REPLACEMENT">
  public final static String REPLACEMENT__USER_COUNT = "###COUNT###";
  public final static String REPLACEMENT__MATCH_ID_MATCH = "###MATCH_ID_MATCH###";
  public final static String REPLACEMENT__MATCH_START_DATE = "###MATCH_START_DATE###";
  public final static String REPLACEMENT__MATCH_TYPE_NAME = "###MATCH_TYPE_NAME###";
  public final static String REPLACEMENT__MATCH_START_HOUR = "###MATCH_START_HOUR###";
  public final static String REPLACEMENT__PLAYER_ROLE = "###PLAYER_ROLE###";
  public final static String REPLACEMENT__PLAYER_REPORTER = "###PLAYER_REPORTER###";
  public final static String REPLACEMENT__PLAYERS_REGISTERED_COUNT = "###PLAYERS_COUNT###";
  public final static String REPLACEMENT__SPORT_CENTER_NAME = "###SPORT_CENTER_NAME###";
  public final static String REPLACEMENT__SPORT_CENTER_CITY = "###SPORT_CENTER_CITY###";
  public final static String REPLACEMENT__SPORT_CENTER_ADDRESS = "###SPORT_CENTER_ADDRESS###";
  public final static String REPLACEMENT__SPORT_CENTER_PROVINCE = "###SPORT_CENTER_PROVINCE###";
  public final static String REPLACEMENT__SPORT_CENTER_COUNTRY = "###SPORT_CENTER_COUNTRY###";

  public final static String REPLACEMENT__INVITATION_CODE = "###INVITATION_CODE###";
  public final static String REPLACEMENT__FREE_TEXT = "###FREE_TEXT###";
  public final static String REPLACEMENT__INVITE_REQUEST_TYPE = "###INVITE_REQUEST_TYPE####";

  public final static String REPLACEMENT__EMAIL = "###EMAIL####";
  public final static String REPLACEMENT__LAT_LON = "###LAT_LON###";
  //</editor-fold>
  
  public static final String KEY_ON_CACHE_TEMPLATE = "%s";
  public static final String KEY_ON_CACHE_TEMPLATE_TWO_PARAM = "%s_%s";
  public static final String KEY_ON_CACHE_TEMPLATE_THREE_PARAM = "%s_%s_%s";
  public static final String KEY_ON_CACHE__GOKICKER_COUNT = "GoKickerCount";
  public static final String KEY_ON_CACHE__SPORTCENTER_COUNT = "SportCenterCount";
  public static final String KEY_ON_CACHE__ALL_COUNTRY = "AllCountry";
  public static final String KEY_ON_CACHE__ALL_PLAYER_ROLE = "AllPlayerRole";
  public static final String KEY_ON_CACHE__ALL_ABUSE_REASONS = "AllAbuseReasons";
  public static final String KEY_ON_CACHE__ALL_PITCH_COVER = "AllPitchCover";
  public static final String KEY_ON_CACHE__ALL_PITCH_TYPE = "AllPitchType";
  public static final String KEY_ON_CACHE__ALL_MATCH_TYPE = "AllMatchType";
  public static final String KEY_ON_CACHE__USER_INFO = "UserInfo";
  public static final String KEY_ON_CACHE__SQUAD_INFO = "SquadInfo";
  public static final String KEY_ON_CACHE__SPORT_CENTER_INFO = "SportCenterInfo";
  public static final String KEY_ON_CACHE__MATCH_INFO = "MatchInfo";
  public static final String KEY_ON_CACHE__STATISTIC_INFO = "StatisticInfo";
  public static final String KEY_ON_CACHE__FEEDBACK_INFO = "FeedbackInfo";
  public static final String KEY_ON_CACHE__MATCH_COMMENT_INFO = "MatchCommentInfo";
  public static final String KEY_ON_CACHE__LANGUAGES = "Languages";
  public static final String KEY_ON_CACHE__DEFAULT_LANGUAGE = "DefaultLanguage";

  //homeAction
  public static final int HOW_MANY_USERS_WITH_CURRENT_PIC = 11;

  public static final String KEY_ON_CACHE__COBRANDS_LIST = "CobrandsList";
  public static final int MAX_MONTHS_OFFSET_FOR_RESULTS_DEFAULT = 6;
  public static final boolean USE_NEWSESSION_METHODS_DEFAULT = false;
  public static final int MAX_USER_FOR_RESULTS_DEFAULT = 500;
}
