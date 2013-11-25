package it.newmedia.gokick.site.providers;

import com.opensymphony.xwork2.ActionContext;
import it.newmedia.gokick.cache.FeedbackCache;
import it.newmedia.gokick.cache.InfoCache;
import it.newmedia.gokick.cache.MatchCommentCache;
import it.newmedia.gokick.cache.StatisticCache;
import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.enums.EnumUserStatus;
import it.newmedia.gokick.data.hibernate.beans.*;
import it.newmedia.gokick.data.hibernate.dao.*;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.guibean.GuiSquadInfo;
import it.newmedia.gokick.site.guibean.GuiStatisticPeriod;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.infos.*;
import it.newmedia.gokick.site.managers.LanguageManager;
import it.newmedia.gokick.util.Utils;
import it.newmedia.net.HttpConnection;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Classe che gestisce le info contenute all'interno dell'applicazione.
 */
public class InfoProvider
{
  // ------------------------------ FIELDS ------------------------------

  private static final String EMPTY_FIELD = "";

  private static final Logger logger = Logger.getLogger(InfoProvider.class);

  // -------------------------- STATIC METHODS --------------------------

  /**
   * cancella tutta la cache
   */
  public static void cancelCache()
  {
    try
    {
      HttpConnection httpConn = new HttpConnection(AppContext.getInstance().getCancelCacheUrl());
      httpConn.doPost("");
      if (httpConn.getResponseCode() != HttpConnection.HTTP_RESPONSECODE_200_OK)
      {
        logger.error("error deleting cache");
      }
    }
    catch (IOException ex)
    {
      logger.error("error deleting cache", ex);
    }
  }

  /**
   * @param language lingua corrente
   * @return la lista ei motivi segnalazione abuso e memorizza in cache
   */
  public static List<AbuseReasonInfo> getAllAbuseReasons(Language language, Cobrand currentCobrand)
  {
    //    //build key
    //    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__ALL_ABUSE_REASONS,language);
    //    //Check for data in cache...
    //    List<AbuseReasonInfo> abuseReasonInfoList = (List<AbuseReasonInfo>) InfoCache.getFromCache(keyOnCache);
    List<AbuseReasonInfo> abuseReasonInfoList = null;

    if (abuseReasonInfoList == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        List<AbuseReason> abuseReasonList = DAOFactory.getInstance().getAbuseReasonDAO().getAll();
        abuseReasonInfoList = new ArrayList<AbuseReasonInfo>();
        for (AbuseReason abuseReason : abuseReasonList)
        {
          String abuseReasonName = TranslationProvider.getTranslationValue(abuseReason.getKeyName(), language, currentCobrand);
          AbuseReasonInfo abuseReasonInfo = new AbuseReasonInfo(abuseReason.getId(), abuseReasonName);
          abuseReasonInfoList.add(abuseReasonInfo);
        }
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving abuse reason list", ex);
      }
      if (abuseReasonInfoList == null)
      {
        return new ArrayList<AbuseReasonInfo>();
      }
      //put on cache
      //      InfoCache.putInCache(keyOnCache, abuseReasonInfoList);
    }
    return abuseReasonInfoList;
  }

  /**
   * @return la lista di tutte le nazioni orinate per POSITION , NAME e lo memorizza in cache
   */
  public static List<Country> getAllCountry()
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE, Constants.KEY_ON_CACHE__ALL_COUNTRY);
    //Check for data in cache...
    List<Country> countyList = (List<Country>) InfoCache.getFromCache(keyOnCache);

    if (countyList == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        countyList = DAOFactory.getInstance().getCountryDAO().getAllOrdered(false, false);
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving county list", ex);
      }
      if (countyList == null)
      {
        return new ArrayList<Country>();
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, countyList);
    }
    return countyList;
  }

  /**
   * @param language lingua corrente
   * @return la lista di tutti i tipi di partite che si possono giocare e le memorizza in cache
   */
  public static List<MatchTypeInfo> getAllMatchTypeInfo(Language language, Cobrand currentCobrand)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__ALL_MATCH_TYPE, language);
    //Check for data in cache...
    List<MatchTypeInfo> matchTypeInfoList = (List<MatchTypeInfo>) InfoCache.getFromCache(keyOnCache);

    if (matchTypeInfoList == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        List<MatchType> matchTypeList = DAOFactory.getInstance().getMatchTypeDAO().getAll();
        matchTypeInfoList = new ArrayList<MatchTypeInfo>();
        for (MatchType matchType : matchTypeList)
        {
          String matchTypeName = TranslationProvider.getTranslation(matchType.getKeyName(), language, currentCobrand).getKeyValue();
          MatchTypeInfo matchTypeInfo = new MatchTypeInfo(matchType.getId(), matchTypeName, matchType.getTotTeamPlayer());
          matchTypeInfoList.add(matchTypeInfo);
        }
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving match type list", ex);
      }
      if (matchTypeInfoList == null)
      {
        return new ArrayList<MatchTypeInfo>();
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, matchTypeInfoList);
    }
    return matchTypeInfoList;
  }

  /**
   * @param language lingua corrente
   * @return la lista di tutti i tipi di copertura dei campi e le memorizza in cache
   */
  public static List<PitchCoverInfo> getAllPitchCoverInfo(Language language, Cobrand currentCobrand)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__ALL_PITCH_COVER, language);
    //Check for data in cache...
    List<PitchCoverInfo> pitchCoverInfoList = (List<PitchCoverInfo>) InfoCache.getFromCache(keyOnCache);

    if (pitchCoverInfoList == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        List<PitchCover> pitchCoverList = DAOFactory.getInstance().getPitchCoverDAO().getAllOrdered();
        pitchCoverInfoList = new ArrayList<PitchCoverInfo>();
        for (PitchCover pitchCover : pitchCoverList)
        {
          String pitchCoverName = TranslationProvider.getTranslation(pitchCover.getKeyName(), language, currentCobrand).getKeyValue();
          PitchCoverInfo pitchCoverInfo = new PitchCoverInfo(pitchCover.getId(), pitchCoverName);
          pitchCoverInfoList.add(pitchCoverInfo);
        }
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving pitch cover list", ex);
      }
      if (pitchCoverInfoList == null)
      {
        return new ArrayList<PitchCoverInfo>();
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, pitchCoverInfoList);
    }
    return pitchCoverInfoList;
  }

  /**
   * @param language lingua corrente
   * @return la lista di tutti i tipi di campi e le memorizza in cache
   */
  public static List<PitchTypeInfo> getAllPitchTypeInfo(Language language, Cobrand currentCobrand)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__ALL_PITCH_TYPE, language);
    //Check for data in cache...
    List<PitchTypeInfo> pitchTypeInfoList = (List<PitchTypeInfo>) InfoCache.getFromCache(keyOnCache);

    if (pitchTypeInfoList == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        List<PitchType> pitchTypeList = DAOFactory.getInstance().getPitchTypeDAO().getAllOrdered();
        pitchTypeInfoList = new ArrayList<PitchTypeInfo>();
        for (PitchType pitchType : pitchTypeList)
        {
          String pitchTypeName = TranslationProvider.getTranslationValue(pitchType.getKeyName(), language, currentCobrand);
          PitchTypeInfo pitchTypeInfo = new PitchTypeInfo(pitchType.getId(), pitchTypeName);
          pitchTypeInfoList.add(pitchTypeInfo);
        }
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving pitch type list", ex);
      }
      if (pitchTypeInfoList == null)
      {
        return new ArrayList<PitchTypeInfo>();
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, pitchTypeInfoList);
    }
    return pitchTypeInfoList;
  }

  /**
   * @param language lingua corrente
   * @return la lista di tutti i ruoli e memorizza in cache
   */
  public static List<PlayerRoleInfo> getAllPlayerRoleInfo(Language language, Cobrand currentCobrand)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__ALL_PLAYER_ROLE, language);
    //Check for data in cache...
    List<PlayerRoleInfo> playerRoleInfoList = (List<PlayerRoleInfo>) InfoCache.getFromCache(keyOnCache);

    if (playerRoleInfoList == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        List<PlayerRole> playerRoleList = DAOFactory.getInstance().getPlayerRoleDAO().getAll();
        playerRoleInfoList = new ArrayList<PlayerRoleInfo>();
        for (PlayerRole playerRole : playerRoleList)
        {
          String playerRoleName = TranslationProvider.getTranslationValue(playerRole.getKeyName(), language, currentCobrand);
          PlayerRoleInfo playerRoleInfo = new PlayerRoleInfo(playerRole.getId(), playerRoleName);
          playerRoleInfoList.add(playerRoleInfo);
        }
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving player role list", ex);
      }
      if (playerRoleInfoList == null)
      {
        return new ArrayList<PlayerRoleInfo>();
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, playerRoleInfoList);
    }
    return playerRoleInfoList;
  }

  /**
   * @return linguaggi di default e li memorizza in cache
   */
  public static Language getDefaultLanguage()
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE, Constants.KEY_ON_CACHE__DEFAULT_LANGUAGE);
    //Check for data in cache...
    Language language = (Language) InfoCache.getFromCache(keyOnCache);

    if (language == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        language = DAOFactory.getInstance().getLanguageDAO().getByLanguage(Constants.LANGUAGE_IT);
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving default language", ex);
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, language);
    }
    return language;
  }

  /**
   * @param idUser
   * @return la lista dei feedback riguardanti l'utente indicato e la memorizza in cache
   */
  public static FeedbackInfo getFeedbackInfo(int idUser)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__FEEDBACK_INFO, idUser);
    //Check for data in cache...
    FeedbackInfo feedbackInfo = (FeedbackInfo) FeedbackCache.getFromCache(keyOnCache);
    if (feedbackInfo == null) // Not found in cache... add it
    {
      // Get it from db
      List<UserComment> userCommentList = null;
      try
      {
        userCommentList = DAOFactory.getInstance().getUserCommentDAO().getByIdUser(idUser);
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving sport center info", ex);
      }
      if (userCommentList.size() == 0)
      {
        return null;
      }

      // Set values
      feedbackInfo = new FeedbackInfo();
      feedbackInfo.load(userCommentList);

      //put on cache
      FeedbackCache.putInCache(keyOnCache, feedbackInfo);
    }
    return feedbackInfo;
  }

  /**
   * @return il numero di iscritti al sito Enabled e lo memorizza in cache
   */
  public static int getGoKickerCount()
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE, Constants.KEY_ON_CACHE__GOKICKER_COUNT);
    //Check for data in cache...
    Integer goKickerCount = (Integer) InfoCache.getFromCache(keyOnCache);

    if (goKickerCount == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        goKickerCount = DAOFactory.getInstance().getUserDAO().getCount();
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving GoKicker count", ex);
      }
      if (goKickerCount == null)
      {
        return 0;
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, goKickerCount);
    }
    return goKickerCount;
  }

  /**
   * @param squad squadra
   * @return oggetto GuiSquadInfo per la squadra indicata
   */
  public static GuiSquadInfo getGuiSquadInfo(Squad squad)
  {
    SquadInfo squadInfo;
    if (squad == null)
    {
      squadInfo = new SquadInfo();
    }
    else
    {
      squadInfo = InfoProvider.getSquadInfo(squad.getId());
      GuiSquadInfo guiSquadInfo = new GuiSquadInfo();
      guiSquadInfo.setSquadInfo(squadInfo);
    }

    GuiSquadInfo guiSquadInfo = new GuiSquadInfo();
    guiSquadInfo.setSquadInfo(squadInfo);
    guiSquadInfo.setStatisticPeriod(Constants.STATISTIC_PERIOD__LAST_TWELVE_MONTH);


    guiSquadInfo.getSquadInfo().setPlayersTot((squadInfo.getPlayersTot() != null) ? (String.valueOf(guiSquadInfo.getPlayersTot())) : "-");
    guiSquadInfo.getSquadInfo().setDescription((squadInfo.getDescription() != null) ? squadInfo.getDescription() : "-");
    guiSquadInfo.getSquadInfo().setPlayingWeekdays((squadInfo.getPlayingWeekdays() != null) ? squadInfo.getPlayingWeekdays() : "-");

    return guiSquadInfo;
  }

  /**
   * @return la lista dei linguaggi ENABLED ordinata per Position e la memorizza in cache
   */
  public static List<Language> getLanguageList()
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE, Constants.KEY_ON_CACHE__LANGUAGES);
    //Check for data in cache...
    List<Language> languageList = (List<Language>) InfoCache.getFromCache(keyOnCache);

    if (languageList == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        languageList = DAOFactory.getInstance().getLanguageDAO().getAllEnabled();
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving language list", ex);
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, languageList);
    }
    return languageList;
  }

  /**
   * @param idMatchComment
   * @return il commento alla partita indicato e lo memorizza in cache
   */
  public static MatchCommentInfo getMatchCommentInfo(int idMatchComment)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__MATCH_COMMENT_INFO, idMatchComment);
    //Check for data in cache...
    MatchCommentInfo matchCommentInfo = (MatchCommentInfo) MatchCommentCache.getFromCache(keyOnCache);
    if (matchCommentInfo == null) // Not found in cache... add it
    {
      // Get it from db
      MatchComment matchComment = null;
      try
      {
        matchComment = DAOFactory.getInstance().getMatchCommentDAO().get(idMatchComment);
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving match comment info", ex);
      }
      if (matchComment == null)
      {
        return null;
      }

      // Set values
      matchCommentInfo = new MatchCommentInfo();
      matchCommentInfo.load(matchComment);

      //put on cache
      MatchCommentCache.putInCache(keyOnCache, matchCommentInfo);
    }
    matchCommentInfo.setUserInfo(InfoProvider.getUserInfo(matchCommentInfo.getIdUserOwner()));
    return matchCommentInfo;
  }

  /**
   * @param idMatch
   * @return le informazioni relative a un Match e le memorizza in cache
   */
  public static MatchInfo getMatchInfo(int idMatch)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__MATCH_INFO, idMatch);
    //Check for data in cache...
    MatchInfo matchInfo = (MatchInfo) InfoCache.getFromCache(keyOnCache);
    if (matchInfo == null) // Not found in cache... add it
    {
      matchInfo = AppContext.getInstance().getUseNewSessionMethods() ? getMatchInfoNewSession(idMatch) : getMatchInfoStandardSession(idMatch);
      //put on cache
      InfoCache.putInCache(keyOnCache, matchInfo);
    }
    return matchInfo;
  }

  private static MatchInfo getMatchInfoNewSession(int idMatch)
  {
    Session session = null;
    try
    {
      // Get it from db
      session = HibernateSessionHolder.getNewSession();
      // Set values
      MatchInfo matchInfo = new MatchInfo();
      MatchDAO matchDAO = new MatchDAO(session);
      Match match = matchDAO.getForMatchInfo(idMatch);
      if (match == null)
      {
        return matchInfo;
      }
      matchInfo.load(match);
      session.close();
      return matchInfo;
    }
    catch (Exception ex)
    {
      logger.error("Error retrieving match info", ex);
      return new MatchInfo();
    }
    finally
    {
      try
      {
        if (session != null && session.isOpen())
        {
          session.close();
        }
      }
      catch (Exception e)
      {

      }
    }
  }

  private static MatchInfo getMatchInfoStandardSession(int idMatch)
  {
    // Get it from db
    try
    {
      if( idMatch <= 0)
      {
        //non può essere una partita valida
        return new MatchInfo();
      }
      //match = DAOFactory.getInstance().getMatchDAO().get(idMatch);
      Match match = DAOFactory.getInstance().getMatchDAO().getForMatchInfo(idMatch);
      MatchInfo matchInfo = new MatchInfo();
      matchInfo.load(match);
      // Set values
      return matchInfo;
    }
    catch (Exception ex)
    {
      logger.error("Error retrieving match info", ex);
      return new MatchInfo();
    }
  }

  /**
   * @return il numero di centri sportivi nel sito APPROVED e ENABLED e lo memorizza in cache
   */
  public static int getSportCenterCount()
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE, Constants.KEY_ON_CACHE__SPORTCENTER_COUNT);
    //Check for data in cache...
    Integer sportCenterCount = (Integer) InfoCache.getFromCache(keyOnCache);

    if (sportCenterCount == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        sportCenterCount = DAOFactory.getInstance().getSportCenterDAO().getCount();
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving GoKicker count", ex);
      }
      if (sportCenterCount == null)
      {
        return 0;
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, sportCenterCount);
    }
    return sportCenterCount;
  }

  /**
   * @param idSportCenter id del centro sportivo di cui si voglio le informazioni
   * @return le informazioni relative ad un centro sportivo e le memorizza in cache
   */
  public static SportCenterInfo getSportCenterInfo(int idSportCenter)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__SPORT_CENTER_INFO, idSportCenter);
    //Check for data in cache...
    SportCenterInfo sportCenterInfo = (SportCenterInfo) InfoCache.getFromCache(keyOnCache);
    if (sportCenterInfo == null) // Not found in cache... add it
    {
      // Get it from db
      SportCenter sportCenter = null;
      try
      {
        sportCenter = DAOFactory.getInstance().getSportCenterDAO().get(idSportCenter);
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving sport center info", ex);
      }
      if (sportCenter == null)
      {
        return new SportCenterInfo();
      }
      // Set values
      sportCenterInfo = new SportCenterInfo();
      sportCenterInfo.load(sportCenter);
      //put on cache
      InfoCache.putInCache(keyOnCache, sportCenterInfo);
    }
    return sportCenterInfo;
  }

  /**
   * @param idSquad id della rosa di cui voglio le informazioni
   * @return le informazioni relative ad una Squad e le memorizza in cache
   */
  public static SquadInfo getSquadInfo(int idSquad)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__SQUAD_INFO, idSquad);
    //Check for data in cache...
    SquadInfo squadInfo = (SquadInfo) InfoCache.getFromCache(keyOnCache);
    if (squadInfo == null) // Not found in cache... add it
    {
      // Get it from db
      Squad squad = null;
      try
      {
        squad = DAOFactory.getInstance().getSquadDAO().get(idSquad);
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving squad info", ex);
      }
      if (squad == null)
      {
        return new SquadInfo();
      }
      // Set values
      squadInfo = new SquadInfo(squad.getId(), squad.getUser().getId(), squad.getUser().getFirstName(), squad.getUser().getLastName(), squad.getUser().getRecordedMatches(), squad.getUser().getRecordedChallenges(), squad.getUser().getCity().getName(),
                                squad.getMarketEnabled(), squad.getName(), squad.getDescription(), squad.getWebSite(), squad.getPhotoSite(), squad.getVideoSite(), squad.getPlayingWeekdays());
      //put on cache
      InfoCache.putInCache(keyOnCache, squadInfo);
    }
    return squadInfo;
  }

  /**
   * @param idUser id dell'utente di cui voglio le statistiche
   * @param period il periodo di cui voglio le statistiche
   * @return le informazioni relative alle statistiche di un utente per un periodo e le memorizza in cache
   */
  public static StatisticInfo getStatisticInfo(int idUser, String period)
  {
    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_THREE_PARAM, Constants.KEY_ON_CACHE__STATISTIC_INFO, idUser, period);
    //Check for data in cache...
    StatisticInfo statisticInfo = (StatisticInfo) StatisticCache.getFromCache(keyOnCache);
    if (statisticInfo == null) // Not found in cache... add it
    {
      List<Statistic> statisticList = null;
      // Get it from db
      try
      {
        GregorianCalendar startDate = null;
        GregorianCalendar endDate = null;
        if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__LAST_MONTH))
        {
          startDate = new GregorianCalendar();
          startDate.add(Calendar.MONTH, -1);
          endDate = new GregorianCalendar();
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__LAST_THREE_MONTH))
        {
          startDate = new GregorianCalendar();
          startDate.add(Calendar.MONTH, -3);
          endDate = new GregorianCalendar();
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__LAST_SIX_MONTH))
        {
          startDate = new GregorianCalendar();
          startDate.add(Calendar.MONTH, -6);
          endDate = new GregorianCalendar();
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__LAST_TWELVE_MONTH))
        {
          startDate = new GregorianCalendar();
          startDate.add(Calendar.MONTH, -12);
          endDate = new GregorianCalendar();
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__YEAR_CURRENT))
        {
          GregorianCalendar tmp = new GregorianCalendar();
          startDate = new GregorianCalendar(tmp.get(Calendar.YEAR), 0, 1, 0, 0, 0);
          endDate = new GregorianCalendar();
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__YEAR_LESS_ONE))
        {
          GregorianCalendar tmp = new GregorianCalendar();
          startDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 1, 0, 1, 0, 0, 0);
          endDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 1, 11, 31, 23, 59, 59);
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__YEAR_LESS_TWO))
        {
          GregorianCalendar tmp = new GregorianCalendar();
          startDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 2, 0, 1, 0, 0, 0);
          endDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 2, 11, 31, 23, 59, 59);
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__YEAR_LESS_THREE))
        {
          GregorianCalendar tmp = new GregorianCalendar();
          startDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 3, 0, 1, 0, 0, 0);
          endDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 3, 11, 31, 23, 59, 59);
        }
        else if (period.equalsIgnoreCase(Constants.STATISTIC_PERIOD__YEAR_LESS_FOUR))
        {
          GregorianCalendar tmp = new GregorianCalendar();
          startDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 4, 0, 1, 0, 0, 0);
          endDate = new GregorianCalendar(tmp.get(Calendar.YEAR) - 4, 11, 31, 23, 59, 59);
        }
        if (startDate != null && endDate != null)
        {
          statisticList = DAOFactory.getInstance().getStatisticDAO().getByIdAndPeriod(idUser, startDate.getTime(), endDate.getTime());
        }
        else
        {
          statisticList = DAOFactory.getInstance().getStatisticDAO().getByIdAndPeriod(idUser, null, null);
        }
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving statistic info", ex);
      }
      if (statisticList.isEmpty())
      {
        // Set empty values
        statisticInfo = new StatisticInfo();
      }
      else
      {
        // Set values
        statisticInfo = new StatisticInfo(statisticList);
      }
      //put on cache
      StatisticCache.putInCache(keyOnCache, statisticInfo);
    }
    return statisticInfo;
  }

  /**
   * @param language la lingua corrente
   * @return la lista di tutti i filtri delle statistiche
   */
  public static List<GuiStatisticPeriod> getStatisticPeriodFilter(Language language, Cobrand currentCobrand)
  {
    GregorianCalendar currentDate = new GregorianCalendar();
    List<GuiStatisticPeriod> statisticPeriodFilterList = new ArrayList<GuiStatisticPeriod>();

    GuiStatisticPeriod guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__ALL, TranslationProvider.getTranslation("label.statisticperiod.all", language, currentCobrand).getKeyValue());
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__LAST_MONTH, TranslationProvider.getTranslation("label.statisticperiod.lastMonth", language, currentCobrand).getKeyValue());
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__LAST_THREE_MONTH, TranslationProvider.getTranslation("label.statisticperiod.threeMonth", language, currentCobrand).getKeyValue());
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__LAST_SIX_MONTH, TranslationProvider.getTranslation("label.statisticperiod.sixMonth", language, currentCobrand).getKeyValue());
    statisticPeriodFilterList.add(guiStatisticPeriod);
    guiStatisticPeriod = new GuiStatisticPeriod(Constants.STATISTIC_PERIOD__LAST_TWELVE_MONTH, TranslationProvider.getTranslation("label.statisticperiod.twelveMonth", language, currentCobrand).getKeyValue());
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

  /**
   * @param idUser id dell'utente di cui voglio le informazioni
   * @return le informazioni relative ad un utente e le memorizza in cache
   */
  public static UserInfo getUserInfo(int idUser)
  {
    //Non possono esistere utenti con id <= 0
    if (idUser <= 0)
    {
      return new UserInfo();
    }

    //build key
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__USER_INFO, idUser);
    //Check for data in cache...
    UserInfo userInfo = (UserInfo) InfoCache.getFromCache(keyOnCache);
    if (userInfo == null) // Not found in cache... add it
    {
      userInfo = AppContext.getInstance().getUseNewSessionMethods() ? getUserInfoNewSession(idUser) : getUserInfoStandardSession(idUser);
      InfoCache.putInCache(keyOnCache, userInfo);
    }
    return userInfo;
  }

  private static UserInfo getUserInfoStandardSession(int idUser)
  {
    // Get it from db
    User user = null;
    Squad squad = null;
    int countPost = 0;
    int invitationsAvailable = 0;
    try
    {
      user = DAOFactory.getInstance().getUserDAO().getFetched(idUser);
      if (user == null)
      {
        return new UserInfo();
      }
      //squad = DAOFactory.getInstance().getSquadDAO().getFirstByIdUser(idUser);
      squad = user.getFirstSquad();
      countPost = DAOFactory.getInstance().getMatchCommentDAO().countByIdUser(idUser);
      invitationsAvailable = user.getMaxInvitations() - DAOFactory.getInstance().getUserInvitationDAO().getCountUsed(idUser);
      if (invitationsAvailable < 0)
      {
        invitationsAvailable = 0;
      }
    }
    catch (Exception ex)
    {
      logger.error("Error retrieving user info: " + ex.getMessage());
    }
    boolean squadMarketEnabled = false;
    if (squad != null)
    {
      squadMarketEnabled = squad.getMarketEnabled() && !squad.getHiddenEnabled();
    }
    else
    {
      logger.error(String.format("Error on data about iduser %s: User must have almost one squad!!", idUser));
    }
    // Set values
    Language language = ActionContext.getContext() != null ? UserContext.getInstance().getLanguage() : LanguageManager.chooseUserLanguage(user);
    Cobrand currentCobrand = ActionContext.getContext() != null ? UserContext.getInstance().getCurrentCobrand() : getCobrandByCode(user.getCobrandCode());
    UserInfo userInfo = new UserInfo();
    userInfo.setId(user.getId());
    userInfo.setName(user.getFirstName());
    userInfo.setAnonymousEnabled(user.getAnonymousEnabled());

    if (userInfo.isAnonymousEnabled())
    {
      userInfo.setSurname(StringUtils.left(user.getLastName(), 1) + '.');
    }
    else
    {
      userInfo.setSurname(user.getLastName());
    }

    userInfo.setCompleteSurname(user.getLastName());

    userInfo.setEmail(user.getEmail());
    userInfo.setRecordedMatches(user.getRecordedMatches());
    userInfo.setRecordedChallenges(user.getRecordedChallenges());
    userInfo.setPlayedMatches(user.getPlayedMatches());
    userInfo.setPlayedChallenges(user.getPlayedChallenges());
    userInfo.setCity(user.getCity().getName());
    userInfo.setProvince(user.getProvince().getName());
    userInfo.setCountry(user.getCountry().getName());
    userInfo.setIdCountry(user.getCountry().getId());
    userInfo.setIdProvince(user.getProvince() != null ? user.getProvince().getId() : 0);
    userInfo.setIdCity(user.getCity() != null ? user.getCity().getId() : 0);
    if (user.getNationalityCountry() != null)
    {
      userInfo.setIdNatCountry(user.getNationalityCountry().getId());
      userInfo.setNatCountry(user.getNationalityCountry().getName());
    }
    userInfo.setCreated(user.getCreated());
    userInfo.setBirthdayCity((user.getBirthdayCity() != null) ? user.getBirthdayCity().getName() : EMPTY_FIELD);
    userInfo.setBirthdayProvince((user.getBirthdayProvince() != null) ? user.getBirthdayProvince().getName() : EMPTY_FIELD);
    userInfo.setBirthdayCountry((user.getBirthdayCountry() != null) ? user.getBirthdayCountry().getName() : EMPTY_FIELD);
    userInfo.setPlayerFoot((user.getPlayerFoot() == null) ? EMPTY_FIELD : TranslationProvider.getTranslation(user.getPlayerFoot().getKeyName(), language, currentCobrand).getKeyValue());
    userInfo.setPlayerFootKeyName((user.getPlayerFoot() == null) ? EMPTY_FIELD : user.getPlayerFoot().getKeyName());
    userInfo.setPlayerShirtNumber((user.getPlayerShirtNumber() != null) ? (String.valueOf(user.getPlayerShirtNumber())) : EMPTY_FIELD);
    userInfo.setPlayerRole(user.getPlayerRole() == null ? EMPTY_FIELD : TranslationProvider.getTranslation(user.getPlayerRole().getKeyName(), language, currentCobrand).getKeyValue());
    userInfo.setIdPlayerRole(user.getPlayerRole().getId());
    userInfo.setPlayerRoleKey(user.getPlayerRole().getKeyName());
    userInfo.setPlayerMainFeature(user.getPlayerMainFeature() == null ? EMPTY_FIELD : user.getPlayerMainFeature());
    userInfo.setPlayerShirtNumber((user.getPlayerShirtNumber() != null) ? (String.valueOf(user.getPlayerShirtNumber())) : EMPTY_FIELD);
    userInfo.setAge((user.getBirthDay() != null) ? Utils.getAgefromDate(user.getBirthDay()) : EMPTY_FIELD);
    userInfo.setPlayerHeight((user.getPlayerHeight() != null) ? String.valueOf(user.getPlayerHeight()) : EMPTY_FIELD);
    userInfo.setPlayerWeight((user.getPlayerWeight() != null) ? String.valueOf(user.getPlayerWeight()) : EMPTY_FIELD);
    userInfo.setFootballTeam((user.getFootballTeam() != null) ? user.getFootballTeam().getName() : EMPTY_FIELD);
    userInfo.setPhysicalConditionKey((user.getPhysicalCondition() != null) ? user.getPhysicalCondition().getKeyName() : "label.condizioneFisica.nonPervenuta");
    userInfo.setIdPhysicalCondition((user.getPhysicalCondition() != null) ? String.valueOf(user.getPhysicalCondition().getId()) : EMPTY_FIELD);
    userInfo.setInfoFavouritePlayer((user.getInfoFavouritePlayer() != null) ? user.getInfoFavouritePlayer() : EMPTY_FIELD);
    userInfo.setInfoDream((user.getInfoDream() != null) ? user.getInfoDream() : EMPTY_FIELD);
    userInfo.setInfoHobby((user.getInfoHobby() != null) ? user.getInfoHobby() : EMPTY_FIELD);
    userInfo.setInfoAnnounce((user.getInfoAnnounce() != null) ? user.getInfoAnnounce() : EMPTY_FIELD);
    userInfo.setInfoDream((user.getInfoDream() != null) ? user.getInfoDream() : EMPTY_FIELD);
    userInfo.setPlayerTitle((user.getPlayerTitle() != null) ? user.getPlayerTitle() : EMPTY_FIELD);
    //userInfo.setPlayedMatches(user.getRecordedMatches() + user.getRecordedChallenges());//SBAGLIATO TODO,sono le organizzate
    userInfo.setCountryFlagName(String.format("%1$s%2$s", Constants.COUNTRY_FLAG_IMAGE_PREFIX, user.getCountry().getId()));
    userInfo.setBirthday(user.getBirthDay());
    userInfo.setMarketEnabled(user.getMarketEnabled());
    userInfo.setSquadMarketEnabled(squadMarketEnabled);
    userInfo.setStatus(user.getEnumUserStatus());
    userInfo.setAlertOnMatchRegistrationOpen(user.getAlertOnRegistrationStart());
    //userInfo.setPlayedMatches(StatisticManager.getPlayed(userInfo.getId()));//Att è una query in piu'

    userInfo.setPostCount(countPost);
    userInfo.setInvitationsAvailable(invitationsAvailable);

    //Facebook
    userInfo.setFacebookIdUser(user.getFacebookIdUser());
    userInfo.setFacebookAccessToken(user.getFacebookAccessToken());
    userInfo.setFacebookPostOnMatchCreation(user.isFacebookPostOnMatchCreation());
    userInfo.setFacebookPostOnMatchRecorded(user.isFacebookPostOnMatchRecorded());
    userInfo.setFacebookPostOnMatchRegistration(user.isFacebookPostOnMatchRegistration());

    try
    {
      PictureCard pictureCard = null;
      if (user.getEnumUserStatus().equals(EnumUserStatus.Pending))
      {
        pictureCard = DAOFactory.getInstance().getPictureCardDAO().getByStatus(idUser, EnumPictureCardStatus.Pending);
        if (pictureCard != null)
        {
          userInfo.loadPictureCard(pictureCard);
        }
      }
      else
      {
        for (PictureCard pic : user.getPictureCards())
        {
          if (pic.getEnumPictureCardStatus().equals(EnumPictureCardStatus.Current))
          {
            userInfo.loadPictureCard(pic);
            break;
          }
        }
      }
    }
    catch (Exception ex)
    {
      logger.error("Error retrieving current picture card in getUserInfo()", ex);
    }
    return userInfo;

  }

  private static UserInfo getUserInfoNewSession(int idUser)
  {
    UserInfo userInfo;
    // Get it from db
    Session session = null;
    try
    {
      session = HibernateSessionHolder.getNewSession();
      // Set values
      // Get it from db
      User user = null;
      Squad squad = null;
      int countPost = 0;
      int invitationsAvailable = 0;
      try
      {
        user = new UserDAO(session).getFetched(idUser);
        if (user == null)
        {
          return new UserInfo();
        }
        squad = user.getFirstSquad();
        countPost = new MatchCommentDAO(session).countByIdUser(idUser);
        invitationsAvailable = user.getMaxInvitations() - new UserInvitationDAO(session).getCountUsed(idUser);
        if (invitationsAvailable < 0)
        {
          invitationsAvailable = 0;
        }
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving user info: " + ex.getMessage());
      }
      boolean squadMarketEnabled = false;
      if (squad != null)
      {
        squadMarketEnabled = squad.getMarketEnabled() && !squad.getHiddenEnabled();
      }
      else
      {
        logger.error(String.format("Error on data about iduser %s: User must have almost one squad!!", idUser));
      }
      // Set values
      Language language = ActionContext.getContext() != null ? UserContext.getInstance().getLanguage() : LanguageManager.chooseUserLanguage(user);
      Cobrand currentCobrand = ActionContext.getContext() != null ? UserContext.getInstance().getCurrentCobrand() : getCobrandByCode(user.getCobrandCode());
      userInfo = new UserInfo();
      userInfo.setId(user.getId());
      userInfo.setName(user.getFirstName());
      userInfo.setAnonymousEnabled(user.getAnonymousEnabled());

      if (userInfo.isAnonymousEnabled())
      {
        userInfo.setSurname(StringUtils.left(user.getLastName(), 1) + '.');
      }
      else
      {
        userInfo.setSurname(user.getLastName());
      }

      userInfo.setCompleteSurname(user.getLastName());

      userInfo.setEmail(user.getEmail());
      userInfo.setRecordedMatches(user.getRecordedMatches());
      userInfo.setRecordedChallenges(user.getRecordedChallenges());
      userInfo.setPlayedMatches(user.getPlayedMatches());
      userInfo.setPlayedChallenges(user.getPlayedChallenges());
      userInfo.setCity(user.getCity().getName());
      userInfo.setProvince(user.getProvince().getName());
      userInfo.setCountry(user.getCountry().getName());
      userInfo.setIdCountry(user.getCountry().getId());
      userInfo.setIdProvince(user.getProvince() != null ? user.getProvince().getId() : 0);
      userInfo.setIdCity(user.getCity() != null ? user.getCity().getId() : 0);
      if (user.getNationalityCountry() != null)
      {
        userInfo.setIdNatCountry(user.getNationalityCountry().getId());
        userInfo.setNatCountry(user.getNationalityCountry().getName());
      }
      userInfo.setCreated(user.getCreated());
      userInfo.setBirthdayCity((user.getBirthdayCity() != null) ? user.getBirthdayCity().getName() : EMPTY_FIELD);
      userInfo.setBirthdayProvince((user.getBirthdayProvince() != null) ? user.getBirthdayProvince().getName() : EMPTY_FIELD);
      userInfo.setBirthdayCountry((user.getBirthdayCountry() != null) ? user.getBirthdayCountry().getName() : EMPTY_FIELD);
      userInfo.setPlayerFoot((user.getPlayerFoot() == null) ? EMPTY_FIELD : TranslationProvider.getTranslation(user.getPlayerFoot().getKeyName(), language, currentCobrand).getKeyValue());
      userInfo.setPlayerFootKeyName((user.getPlayerFoot() == null) ? EMPTY_FIELD : user.getPlayerFoot().getKeyName());
      userInfo.setPlayerShirtNumber((user.getPlayerShirtNumber() != null) ? (String.valueOf(user.getPlayerShirtNumber())) : EMPTY_FIELD);
      userInfo.setPlayerRole(user.getPlayerRole() == null ? EMPTY_FIELD : TranslationProvider.getTranslation(user.getPlayerRole().getKeyName(), language, currentCobrand).getKeyValue());
      userInfo.setIdPlayerRole(user.getPlayerRole().getId());
      userInfo.setPlayerRoleKey(user.getPlayerRole().getKeyName());
      userInfo.setPlayerMainFeature(user.getPlayerMainFeature() == null ? EMPTY_FIELD : user.getPlayerMainFeature());
      userInfo.setPlayerShirtNumber((user.getPlayerShirtNumber() != null) ? (String.valueOf(user.getPlayerShirtNumber())) : EMPTY_FIELD);
      userInfo.setAge((user.getBirthDay() != null) ? Utils.getAgefromDate(user.getBirthDay()) : EMPTY_FIELD);
      userInfo.setPlayerHeight((user.getPlayerHeight() != null) ? String.valueOf(user.getPlayerHeight()) : EMPTY_FIELD);
      userInfo.setPlayerWeight((user.getPlayerWeight() != null) ? String.valueOf(user.getPlayerWeight()) : EMPTY_FIELD);
      userInfo.setFootballTeam((user.getFootballTeam() != null) ? user.getFootballTeam().getName() : EMPTY_FIELD);
      userInfo.setPhysicalConditionKey((user.getPhysicalCondition() != null) ? user.getPhysicalCondition().getKeyName() : "label.condizioneFisica.nonPervenuta");
      userInfo.setIdPhysicalCondition((user.getPhysicalCondition() != null) ? String.valueOf(user.getPhysicalCondition().getId()) : EMPTY_FIELD);
      userInfo.setInfoFavouritePlayer((user.getInfoFavouritePlayer() != null) ? user.getInfoFavouritePlayer() : EMPTY_FIELD);
      userInfo.setInfoDream((user.getInfoDream() != null) ? user.getInfoDream() : EMPTY_FIELD);
      userInfo.setInfoHobby((user.getInfoHobby() != null) ? user.getInfoHobby() : EMPTY_FIELD);
      userInfo.setInfoAnnounce((user.getInfoAnnounce() != null) ? user.getInfoAnnounce() : EMPTY_FIELD);
      userInfo.setInfoDream((user.getInfoDream() != null) ? user.getInfoDream() : EMPTY_FIELD);
      userInfo.setPlayerTitle((user.getPlayerTitle() != null) ? user.getPlayerTitle() : EMPTY_FIELD);
      //userInfo.setPlayedMatches(user.getRecordedMatches() + user.getRecordedChallenges());//SBAGLIATO TODO,sono le organizzate
      userInfo.setCountryFlagName(String.format("%1$s%2$s", Constants.COUNTRY_FLAG_IMAGE_PREFIX, user.getCountry().getId()));
      userInfo.setBirthday(user.getBirthDay());
      userInfo.setMarketEnabled(user.getMarketEnabled());
      userInfo.setSquadMarketEnabled(squadMarketEnabled);
      userInfo.setStatus(user.getEnumUserStatus());
      userInfo.setAlertOnMatchRegistrationOpen(user.getAlertOnRegistrationStart());
      //userInfo.setPlayedMatches(StatisticManager.getPlayed(userInfo.getId()));//Att è una query in piu'

      userInfo.setPostCount(countPost);
      userInfo.setInvitationsAvailable(invitationsAvailable);

      //Facebook
      userInfo.setFacebookIdUser(user.getFacebookIdUser());
      userInfo.setFacebookAccessToken(user.getFacebookAccessToken());
      userInfo.setFacebookPostOnMatchCreation(user.isFacebookPostOnMatchCreation());
      userInfo.setFacebookPostOnMatchRecorded(user.isFacebookPostOnMatchRecorded());
      userInfo.setFacebookPostOnMatchRegistration(user.isFacebookPostOnMatchRegistration());

      try
      {
        PictureCard pictureCard = null;
        if (user.getEnumUserStatus().equals(EnumUserStatus.Pending))
        {
          pictureCard = new PictureCardDAO(session).getByStatus(idUser, EnumPictureCardStatus.Pending);
          if (pictureCard != null)
          {
            userInfo.loadPictureCard(pictureCard);
          }
        }
        else
        {
          for (PictureCard pic : user.getPictureCards())
          {
            if (pic.getEnumPictureCardStatus().equals(EnumPictureCardStatus.Current))
            {
              userInfo.loadPictureCard(pic);
              break;
            }
          }
        }
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving current picture card in getUserInfo()", ex);
      }
      return userInfo;
    }
    catch (Exception ex)
    {
      userInfo = new UserInfo();
      logger.error("Error retrieving user info", ex);
    }
    finally
    {
      try
      {
        if (session != null && session.isOpen())
        {
          session.close();
        }
      }
      catch (Exception e)
      {

      }
    }
    return userInfo;
  }

  /**
   * rimuove la lista dei feedback dello user indicato dalla cache
   *
   * @param idUser
   */
  public static void removeFeedbackInfo(int idUser)
  {
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__FEEDBACK_INFO, idUser);
    FeedbackCache.removeFromCache(keyOnCache);
  }

  /**
   * rimuove il commento alla parita indicato
   *
   * @param idMatchComment
   */
  public static void removeMatchCommentInfo(int idMatchComment)
  {
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__MATCH_COMMENT_INFO, idMatchComment);
    MatchCommentCache.removeFromCache(keyOnCache);
  }

  /**
   * rimuove le info del Match indicato dalla cache
   *
   * @param idMatch
   */
  public static void removeMatchInfo(int idMatch)
  {
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__MATCH_INFO, idMatch);
    InfoCache.removeFromCache(keyOnCache);
    //System.out.println("rimossa key MatchInfo :" + keyOnCache);
  }

  /**
   * rimuove le info dello SportCenter indicato dalla cache
   *
   * @param idSportCenter
   */
  public static void removeSportCenterInfo(int idSportCenter)
  {
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__SPORT_CENTER_INFO, idSportCenter);
    InfoCache.removeFromCache(keyOnCache);
  }

  /**
   * rimuove le info della Squad indicata dalla cache
   *
   * @param idSquad
   */
  public static void removeSquadInfo(int idSquad)
  {
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__SQUAD_INFO, idSquad);
    InfoCache.removeFromCache(keyOnCache);
  }

  /**
   * rimuove le info dello User indicato dalla cache
   *
   * @param id
   */
  public static void removeUserInfo(int id)
  {
    String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__USER_INFO, id);
    InfoCache.removeFromCache(keyOnCache);
  }

  public static Cobrand getCobrandByDomain(String domain)
  {
    //build key
    String keyOnCache = Constants.KEY_ON_CACHE__COBRANDS_LIST;
    //Check for data in cache...
    List<Cobrand> cobrandList = (ArrayList<Cobrand>) InfoCache.getFromCache(keyOnCache);
    if (cobrandList == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        cobrandList = DAOFactory.getInstance().getCobrandDAO().findAll();
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving all cobrand list", ex);
        cobrandList = new ArrayList<Cobrand>();
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, cobrandList);
    }
    for (Cobrand cobrand : cobrandList)
    {
      if (StringUtils.equalsIgnoreCase(cobrand.getDomain(), domain))
      {
        return cobrand;
      }
    }
    return null;
  }

  public static Cobrand getCobrandByCode(String code)
  {
    //build key
    String keyOnCache = Constants.KEY_ON_CACHE__COBRANDS_LIST;
    //Check for data in cache...
    List<Cobrand> cobrandList = (ArrayList<Cobrand>) InfoCache.getFromCache(keyOnCache);
    if (cobrandList == null) // Not found in cache... add it
    {
      // Get it from db
      try
      {
        cobrandList = DAOFactory.getInstance().getCobrandDAO().findAll();
      }
      catch (Exception ex)
      {
        logger.error("Error retrieving all cobrand list", ex);
        cobrandList = new ArrayList<Cobrand>();
      }
      //put on cache
      InfoCache.putInCache(keyOnCache, cobrandList);
    }
    for (Cobrand cobrand : cobrandList)
    {
      if (StringUtils.equals(cobrand.getCode(), code))
      {
        return cobrand;
      }
    }
    return null;
  }

  // --------------------------- CONSTRUCTORS ---------------------------

  /**
   * Creates a new instance of InfoProvider
   */
  public InfoProvider()
  {
  }
}
