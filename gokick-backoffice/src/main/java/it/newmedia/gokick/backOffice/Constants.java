package it.newmedia.gokick.backOffice;

/**
 *
 * Classe che definisce le costanti comuni che vengono utilizzate all'interno del progetto
 */
public class Constants
{
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
  public final static String STRUTS_RESULT_NAME__UPDATED = "updated";
  public final static String STRUTS_RESULT_NAME__SENDMESSAGES = "sendMessages";

  public static final String PICTURE_CARD_FILE_FORMAT = "0000000";
  public static final String PICTURE_CARD_FILE_INDEX_FORMAT = "00";

  public static final int CHECK_PENDING_CODE_LENGHT = 10;
  
  public static final String COUNTRY_FLAG_IMAGE_PREFIX = "country_flag_";

  public static final String DATE_FORMAT__DDMMYYYYHHMM = "dd/MM/yyyy HH:mm";
  public static final String DATE_FORMAT__DDMMYYYY = "dd/MM/yyyy";
  public static final String DATE_FORMAT__EEEDDMMM = "EEE dd MMM";
  public static final String DATE_FORMAT__EEEEEEEEEEEDDMMMM = "EEEEEEEEEEE dd MMMM";
  public static final String DATE_FORMAT__EEEEEEEEEEEDDMMMMYYYY = "EEEEEEEEEEE dd MMMM yyyy";
  public static final String DATE_FORMAT__DDMMMMYYYY = "dd MMMM yyyy";
  public static final String DATE_FORMAT__HHMM = "HH:mm";

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

  public final static String SQUAD_PREFERENCES__PLAYING_WEEKDAYS_NONE = "0000000";

  public final static String STATISTIC_PERIOD__NONE = "nessuna";
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
  public final static int STRING_LENGHT_15 = 15;
  public final static int STRING_LENGHT_20 = 20;
  public final static int STRING_LENGHT_40 = 40;
  public final static int STRING_LENGHT_1000 = 1000;
  public final static int STRING_LENGHT_5000 = 5000;
  public final static int NUMBER_VALUE_0 = 0;
  public final static int INVALID_ID = 0;
  public final static String STRING_EMPTY = "";

  /**
   * Indica il valore dell'id nel database corrispondente al campo a pagamento.
   * Attenzione... se il valore su db cambia questa costante deve essere aggiornata
   */
  public final static int PITCH_TYPE_PAY = 2;

  public final static String COOKIE_USER_ID = "gokick_id";
  public final static String COOKIE_USER_PASSWORD = "gokick_password";

  public final static String SESSION_KEY__PICTURE_CARD_FILE = "pictureCardFile";
  public final static String SESSION_KEY__SQUAD_MANAGE_MESSAGE = "squadManageMessage";
  public final static String SESSION_KEY__MATCH_CANCELLED_MESSAGE = "matchCancelledMessage";
  public final static String SESSION_KEY__MATCH_PUBLISHED_MESSAGE = "matchPublishedMessage";
  public final static String SESSION_KEY__LAST_MESSAGE = "lastMessage";


  
  public final static String REQUEST_PARAM__INFO = "info";

  public final static String REPLACEMENT__USER_FIRSTNAME = "###FIRSTNAME###";
  public final static String REPLACEMENT__USER_LASTNAME = "###LASTNAME###";
  public final static String REPLACEMENT__USER_COUNT = "###COUNT###";
  public final static String REPLACEMENT__MATCH_START_DATE = "###MATCH_START_DATE###";
  public final static String REPLACEMENT__PLAYER_ROLE = "###PLAYER_ROLE###";
  public final static String REPLACEMENT__PLAYERS_REGISTERED_COUNT = "###PLAYERS_COUNT###";


  public static final String KEY_ON_CACHE_TEMPLATE = "%s";
  public static final String KEY_ON_CACHE_TEMPLATE_TWO_PARAM = "%s_%s";
  public static final String KEY_ON_CACHE_TEMPLATE_THREE_PARAM = "%s_%s_%s";
  public static final String KEY_ON_CACHE__GOKICKER_COUNT = "GoKickerCount";
  public static final String KEY_ON_CACHE__SPORTCENTER_COUNT = "SportCenterCount";
  public static final String KEY_ON_CACHE__ALL_COUNTRY = "AllCountry";
  public static final String KEY_ON_CACHE__ALL_PLAYER_ROLE = "AllPlayerRole";
  public static final String KEY_ON_CACHE__ALL_PITCH_COVER = "AllPitchCover";
  public static final String KEY_ON_CACHE__ALL_PITCH_TYPE = "AllPitchType";
  public static final String KEY_ON_CACHE__ALL_MATCH_TYPE = "AllMatchType";
  public static final String KEY_ON_CACHE__USER_INFO = "UserInfo";
  public static final String KEY_ON_CACHE__SQUAD_INFO = "SquadInfo";
  public static final String KEY_ON_CACHE__SPORT_CENTER_INFO = "SportCenterInfo";
  public static final String KEY_ON_CACHE__MATCH_INFO = "MatchInfo";
  public static final String KEY_ON_CACHE__STATISTIC_INFO = "StatisticInfo";
  public static final String KEY_ON_CACHE__FEEDBACK_INFO = "FeedbackInfo";

  public static final String SPORTCENTER_STATUS_ONLINE = "online";
  public static final String SPORTCENTER_STATUS_STANBY = "standby";
  public static final String SPORTCENTER_STATUS_CANCELLATO = "cancellato";
  public static final String SPORTCENTER_STATUS_TUTTI = "tutti";
}
