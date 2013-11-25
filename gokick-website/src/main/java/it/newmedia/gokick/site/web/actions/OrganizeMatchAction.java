package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.Preparable;
import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.MatchType;
import it.newmedia.gokick.data.hibernate.beans.PlayerRole;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.MatchTypeInfo;
import it.newmedia.gokick.site.infos.PlayerRoleInfo;
import it.newmedia.gokick.site.infos.SportCenterInfo;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.*;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.util.Utils;
import it.newmedia.results.Result;
import it.newmedia.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

/**
 *
 * Classe contenente le azioni per l'organizzazione delle partite, l'aggiornamento e l'annullamento
 */
public class OrganizeMatchAction extends AuthenticationBaseAction implements Preparable
{
  public static final int AVATAR_FOR_ROWS = 11;
  public static final int ROWS_FOR_TABLE = 9;

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  public static final int SUBSCRIPTIONS_OPENED_IN_DATE = 3;
  public static final int SUBSCRIPTIONS_OPENED = 2;
  public static final int SUBSCRIPTIONS_CLOSED = 1;
  public static final int ID_PLAYER_ROLE_ORGANIZER = 0;
  public static final int DEFAULT__SUB_REP_OPENED_DATE_DAY = 7;
  public static final int DEFAULT__SUB_REP_OPENED_DATE_HOURS = 9;
  // </editor-fold>
  
  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idMatch;
  private Match currentMatch;
  private int currentPlayerCount;
  private String matchStartDate;
  private String matchStartHour;
  private String matchStartMinute;
  private Date matchStart;
  private int idSportCenter;
  private int idMatchType;
  private String matchType;
  private int matchParticipantsNumber;
  private String mobile;
  //private String email;
  private String notes;
  private Boolean registrationOpen;
  private boolean directlyRegistration;
  private int maxSquadOut;
  private Boolean accepTermination;
  private int accepTerminationMaxHours;
  private Date accepTerminationEndDate;
  private boolean repetitive;
  private String endRepetitiveDate;
  private int subNotRep;
  private boolean subRep;
  private String subNotRepDate;
  private Date startRegistrationDate;
  private int subRepDateDay;
  private int subRepDateHours;
  private int subRepDateMinutes;
  private int idPlayerRole;
  private List maxSquadOutList;
  private List matchParticipantsNumberList;
  private List numberOfDaysList;
  private List hoursList;
  private List<String> minutesList;
  private String labelMatchStart; //per OrganizeMatchReview
  private String labelRegistrationDate; //per OrganizeMatchReview
  private String labelRegisterDate; //per OrganizeMatch
  private String labelRepDate; //per OrganizeMatch
  private boolean canOrganizeMatch;
  private List<List<List<UserInfo>>> fatherOrganizerList;
  private String presentationText;
  private int totOrganizers;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="ACTION METHODS">
  @Override
  public void prepare() throws Exception
  {
    this.canOrganizeMatch = UserContext.getInstance().getUser().getOrganizeEnabled();

    this.matchParticipantsNumberList = new ArrayList();
    this.maxSquadOutList = new ArrayList();
    this.numberOfDaysList = new ArrayList();
    for (int i = 0; i < 31; i++)
    {
      this.numberOfDaysList.add(i);
    }
    this.hoursList = new ArrayList();
    for (int i = 0; i < 24; i++)
    {
      this.hoursList.add(String.format("%02d", i));
    }
    this.minutesList = new ArrayList();
    for (int i = 0; i < 60; i +=5)
    {
      this.minutesList.add(String.format("%02d", i));
    }
  }

  @Override
  public String input()
  {
    if (!this.canOrganizeMatch && !(this.idMatch > 0))
    {
      return INPUT;
    }

    this.canOrganizeMatch = true;
    if (this.idMatch > 0)
    {
      try
      {
        this.currentMatch = DAOFactory.getInstance().getMatchDAO().get(this.idMatch);
      }
      catch (Exception e)
      {
        this.currentMatch = null;
      }
      if (this.currentMatch == null)
      {
        addActionMessage(getText("error.impossibileVisualizzareDatiPartita"));
        return Constants.STRUTS_RESULT_NAME__INFO;
      }
    }

    if (this.currentMatch == null)
    {
      //insert
      this.mobile = UserContext.getInstance().getUser().getMobile();
      //this.email = UserContext.getInstance().getUser().getEmail();
      if (UserContext.getInstance().getUser().getPlayerRole() != null)
      {
        this.idPlayerRole = UserContext.getInstance().getUser().getPlayerRole().getId();
      }
      this.directlyRegistration = false;
      this.repetitive = false;
      this.subNotRep = SUBSCRIPTIONS_OPENED;
      this.subRep = true;
      this.subRepDateDay = DEFAULT__SUB_REP_OPENED_DATE_DAY;
      this.subRepDateHours = DEFAULT__SUB_REP_OPENED_DATE_HOURS;
      Date initStartDate = DateUtils.addHours(new Date(), 1);
      this.matchStartDate =  FastDateFormat.getInstance(Constants.DATE_FORMAT__DDMMYYYY).format(initStartDate);
      this.matchStartHour = FastDateFormat.getInstance("HH").format(initStartDate);
      this.matchStartMinute = "00";
    }
    else
    {
//      if (this.currentMatch.getCanceled())
//      {
//        return Constants.STRUTS_RESULT_NAME__INFO;
//      }
      if (!this.currentMatch.getUserOwner().getId().equals(UserContext.getInstance().getUser().getId()))
      {
        return Constants.STRUTS_RESULT_NAME__INFO;
      }
      //update

      //this.currentPlayerCount = this.currentMatch.getPlayerList().size();

      this.currentPlayerCount = MatchManager.countMatchPlayers(idMatch, false,
              EnumPlayerStatus.OwnerRegistered,
              EnumPlayerStatus.UserRegistered,
              EnumPlayerStatus.UserRequestRegistered,
              EnumPlayerStatus.Out);

      int value = this.currentMatch.getMatchType().getId() * 2;
      if (this.currentPlayerCount > value)
      {
        value = this.currentPlayerCount;
      }
      int endValue = (this.currentMatch.getMatchType().getId() * 4) - value;

      if (endValue <= 0)
      {
        this.matchParticipantsNumberList.add(value);
      }
      else
      {
        for (int j = 0; j <= endValue; j++)
        {
          this.matchParticipantsNumberList.add(value);
          value++;
        }
      }


      for (int j = 0; j < this.currentMatch.getMaxPlayers(); j++)
      {
        this.maxSquadOutList.add(j + 1);
      }

      this.matchStart = this.currentMatch.getMatchStart();
      this.matchStartDate =  FastDateFormat.getInstance(Constants.DATE_FORMAT__DDMMYYYY).format(this.currentMatch.getMatchStart());
      this.matchStartHour = FastDateFormat.getInstance("HH").format(this.currentMatch.getMatchStart());
      this.matchStartMinute = FastDateFormat.getInstance("mm").format(this.currentMatch
                                                                              .getMatchStart());
      this.idSportCenter = this.currentMatch.getSportCenter().getId();
      this.idMatchType = this.currentMatch.getMatchType().getId();
      this.matchType = getText(this.currentMatch.getMatchType().getKeyName());
      this.matchParticipantsNumber = this.currentMatch.getMaxPlayers();
      this.mobile = this.currentMatch.getMobileUserOwner();
      //this.email = this.currentMatch.getEmailUserOwner();
      this.notes = this.currentMatch.getNotes();
      this.registrationOpen = this.currentMatch.isSquadOutEnabled();
      this.directlyRegistration = this.currentMatch.isDirectlyRegistration();
      this.maxSquadOut = this.currentMatch.getMaxSquadOutPlayers();
      this.accepTermination = this.currentMatch.isAcceptTermination();
      if (this.currentMatch.isAcceptTermination())
      {
        this.accepTerminationMaxHours = (int) DateUtil.getDiffHours(this.currentMatch.getMatchStart(), this.currentMatch.getAcceptTerminationLimit());
      }
      else
      {
        this.accepTerminationMaxHours = 0;
      }
      if (this.currentMatch.getRegistrationStart() == null)
      {
        if (this.currentMatch.getRegistrationClosed())
        {
          this.subNotRep = SUBSCRIPTIONS_CLOSED;
        }
        else
        {
          this.subNotRep = SUBSCRIPTIONS_OPENED;
        }
      }
      else
      {
        this.subNotRep = SUBSCRIPTIONS_OPENED_IN_DATE;
        this.startRegistrationDate = this.currentMatch.getRegistrationStart();
        this.labelRegistrationDate = (DateManager.showDate(this.startRegistrationDate, DateManager.FORMAT_DATE_1));
      }
      this.labelMatchStart = DateManager.showDate(this.matchStart, DateManager.FORMAT_DATE_2);
    }

    return INPUT;
  }

  public String welcome()
  {
    List<UserInfo> userInfoList=new ArrayList<UserInfo>();
    userInfoList=UserInfoManager.getUserInfoList(UserManager.getOrganizerWithCurrentPic(0, 3));
    this.totOrganizers=userInfoList.size();
    List<List<UserInfo>> organizerList= new ArrayList<List<UserInfo>>();
    fatherOrganizerList=new ArrayList<List<List<UserInfo>>>();
    List<UserInfo> organizerPartialList = new ArrayList<UserInfo>();
    Iterator<UserInfo> itUserInfo=userInfoList.iterator();
    while(itUserInfo.hasNext())
    {
      UserInfo uInfo = itUserInfo.next();
      organizerPartialList.add(uInfo);
      if (organizerPartialList.size()==AVATAR_FOR_ROWS || !itUserInfo.hasNext())
      {
        organizerList.add(organizerPartialList);
        organizerPartialList=new ArrayList<UserInfo>();
      }
      if (organizerList.size()==ROWS_FOR_TABLE || !itUserInfo.hasNext())
      {
        fatherOrganizerList.add(organizerList);
        organizerList=new ArrayList<List<UserInfo>>();
      }
    } 
    return SUCCESS;
  }

  public String organizeMatchTutorial()
  {
    return SUCCESS;
  }

  public String sendPresentation()
  {
    User user=UserContext.getInstance().getUser();
    user.setOrganizeEnabled(true);
    boolean success = UserManager.update(user);
    if(!success)
    {
      return INPUT;
    }
    else
    {
      InfoProvider.removeUserInfo(user.getId());
      //TODO relazioni con statisticInfo?
      Result<Boolean> mailSent = EmailManager.sendNewOrganizerConfirm(user, getCurrentCobrand());
      if(!mailSent.isSuccessNotNull())
      {
        logger.error("error sending newOrganizerMAil to user : " + user.getId());
        logger.error(mailSent.getErrorMessage(),mailSent.getErrorException());
      }
      mailSent = EmailManager.sendNewOrganizerToBackOffice(user,this.presentationText, getCurrentCobrand());
      if(!mailSent.isSuccessNotNull())
      {
        logger.error("error sending newOrganizerMAil to backoffice for user : " + user.getId());
        logger.error(mailSent.getErrorMessage(),mailSent.getErrorException());
      }
    }
    String message = getText("message.organizeFirstMatch");
    UserContext.getInstance().setLastMessage(message);
    return SUCCESS;
  }

  public String insert()
  {
    try
    {  //MatchType matchType...
      MatchType matchTypeInsert = DAOFactory.getInstance().getMatchTypeDAO().get(this.idMatchType);
      PlayerRole playerRole = null;

      List<UserInfo> userInfoSquadList = new ArrayList<UserInfo>();
      List<Integer> idFriendList = SquadManager.getAllConfirmedUserByIdSquad(UserContext.getInstance().getFirstSquad().getId());
      for (Integer idFriend : idFriendList)
      {
        userInfoSquadList.add(InfoProvider.getUserInfo(idFriend));
      }

      if (this.idPlayerRole != ID_PLAYER_ROLE_ORGANIZER)
      {
        playerRole = DAOFactory.getInstance().getPlayerRoleDAO().get(this.idPlayerRole);
      }

      if (this.repetitive)
      {
        GregorianCalendar repetitiveMatchDate = new GregorianCalendar();
        repetitiveMatchDate.setTime(Utils.getDateFromString(this.getMatchStartDateHourMinute(), Constants.DATE_FORMAT__DDMMYYYYHHMM));
        GregorianCalendar repetitiveEndDate = new GregorianCalendar();
        repetitiveEndDate.setTime(Utils.getDateFromString(this.endRepetitiveDate, Constants.DATE_FORMAT__DDMMYYYY));
        repetitiveEndDate.set(Calendar.HOUR_OF_DAY, 23);
        repetitiveEndDate.set(Calendar.MINUTE, 59);
        repetitiveEndDate.set(Calendar.SECOND, 59);
        List<Match> matchList = new ArrayList<Match>();
        while (repetitiveMatchDate.before(repetitiveEndDate))
        {
          //Set delle informazioni dell'utente che organizza la partita
          Match match = new Match();
          match.setUserOwner(UserContext.getInstance().getUser());
          //match.setEmailUserOwner(this.email);
          match.setMobileUserOwner(this.mobile);
          match.setNotes(this.notes);

          //Set delle informazioni relative al centro sportivo
          match.setSportCenter(DAOFactory.getInstance().getSportCenterDAO().load(this.idSportCenter));
          match.setSportCenterName(this.getSportCenterInfo().getName());
          match.setSportCenterAddress(this.getSportCenterInfo().getAddress());
          match.setSportCenterCity(this.getSportCenterInfo().getCityProvinceName());

          //Set della data di inizio della partita
          match.setMatchStart(repetitiveMatchDate.getTime());

          //Set del tipo di partita e del massimo dei partecipanti
          match.setMatchType(matchTypeInsert);
          match.setMaxPlayers(this.matchParticipantsNumber);

          //Set delle info sulla partita e sul numero massimo di fuorirosa
          match.setSquadOutEnabled(this.registrationOpen);
          match.setDirectlyRegistration(this.directlyRegistration);
          if (this.registrationOpen && this.directlyRegistration)
          {
            match.setMaxSquadOutPlayers(this.maxSquadOut);
          }
          else
          {
            match.setMaxSquadOutPlayers(0);
          }

          //Set delle info per la gestion delle disdette
          match.setAcceptTermination(this.accepTermination);
          if (this.accepTermination)
          {
            GregorianCalendar acceptTerminationLimit = new GregorianCalendar();
            acceptTerminationLimit.setTime(repetitiveMatchDate.getTime());
            acceptTerminationLimit.add(Calendar.HOUR_OF_DAY, -this.accepTerminationMaxHours);
            match.setAcceptTerminationLimit(acceptTerminationLimit.getTime());
          }

          //Set delle info per la gestion delle iscrizioni
          if (this.subRep)
          {
            match.setRegistrationClosed(false);
            GregorianCalendar registrationStartDate = new GregorianCalendar();
            registrationStartDate.setTime(repetitiveMatchDate.getTime());
            registrationStartDate.add(Calendar.DAY_OF_MONTH, -this.subRepDateDay);
            registrationStartDate.set(Calendar.HOUR, this.subRepDateHours);
            registrationStartDate.set(Calendar.MINUTE, this.subRepDateMinutes);
            registrationStartDate.set(Calendar.SECOND, 0);
            match.setRegistrationStart(registrationStartDate.getTime());
          }
          else
          {
            match.setRegistrationClosed(true);
          }

          match.setCreated(new Date());

          matchList.add(match);

          repetitiveMatchDate.add(Calendar.DAY_OF_MONTH, 7);
        }
        boolean success = MatchManager.save(matchList, playerRole, userInfoSquadList, getCurrentCobrand());
        if (!success)
        {
          return ERROR;
        }
        return SUCCESS;
      }
      else
      {

        //Set delle informazioni dell'utente che organizza la partita
        Match match = new Match();
        match.setUserOwner(UserContext.getInstance().getUser());
        //match.setEmailUserOwner(this.email);
        match.setMobileUserOwner(this.mobile);
        match.setNotes(this.notes);

        //Set delle informazioni relative al centro sportivo
        match.setSportCenter(DAOFactory.getInstance().getSportCenterDAO().load(this.idSportCenter));
        match.setSportCenterName(this.getSportCenterInfo().getName());
        match.setSportCenterAddress(this.getSportCenterInfo().getAddress());
        match.setSportCenterCity(this.getSportCenterInfo().getCityProvinceName());

        //Set della data di inizio della partita
        match.setMatchStart(Utils.getDateFromString(this.getMatchStartDateHourMinute(), Constants.DATE_FORMAT__DDMMYYYYHHMM));

        //Set del tipo di partita e del massimo dei partecipanti
        match.setMatchType(matchTypeInsert);
        match.setMaxPlayers(this.matchParticipantsNumber);

        //Set delle info sulla partita e sul numero massimo di fuorirosa
        match.setSquadOutEnabled(this.registrationOpen);
        match.setDirectlyRegistration(this.directlyRegistration);
        if (this.registrationOpen && this.directlyRegistration)
        {
          match.setMaxSquadOutPlayers(this.maxSquadOut);
        }
        else
        {
          match.setMaxSquadOutPlayers(0);
        }

        //Set delle info per la gestion delle disdette
        match.setAcceptTermination(this.accepTermination);
        if (this.accepTermination)
        {
          GregorianCalendar acceptTerminationLimit = new GregorianCalendar();
          acceptTerminationLimit.setTime(Utils.getDateFromString(this.getMatchStartDateHourMinute(), Constants.DATE_FORMAT__DDMMYYYYHHMM));
          acceptTerminationLimit.add(Calendar.HOUR_OF_DAY, -this.accepTerminationMaxHours);
          match.setAcceptTerminationLimit(acceptTerminationLimit.getTime());
        }

        //Set delle info per la gestion delle iscrizioni
        if (this.subNotRep == SUBSCRIPTIONS_OPENED_IN_DATE)
        {
          match.setRegistrationClosed(false);
          match.setRegistrationStart(Utils.getDateFromString(this.subNotRepDate, Constants.DATE_FORMAT__DDMMYYYYHHMM));
        }
        else
        {
          if (this.subNotRep == SUBSCRIPTIONS_OPENED)
          {
            match.setRegistrationClosed(false);
          }
          else
          {
            match.setRegistrationClosed(true);
          }
        }

        match.setCreated(new Date());

        boolean success = MatchManager.save(match, playerRole, userInfoSquadList, getCurrentCobrand());
        if (!success)
        {
          return ERROR;
        }
        this.idMatch = match.getId();
        String message = getText("messaggio.partitaPubblicata");
        message = message.replace(Constants.REPLACEMENT__MATCH_START_DATE, DateManager.showDate(match.getMatchStart(), DateManager.FORMAT_DATE_13));
        UserContext.getInstance().setLastMessage(message);
        //effettua le azioni su facebook
        FacebookManager.postOnMatchCreation(match.getId());
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return ERROR;
    }
    return Constants.STRUTS_RESULT_NAME__MATCH_INSERTED;
  }

  public String update()
  {
    try
    {
      //Get della partita che sto modificando
      this.currentMatch = DAOFactory.getInstance().getMatchDAO().get(this.idMatch);
      MatchType matchType = DAOFactory.getInstance().getMatchTypeDAO().get(this.idMatchType);
      if (this.currentMatch == null || this.currentMatch.getCanceled())
      {
        return Constants.STRUTS_RESULT_NAME__INFO;
      }
      if (!this.currentMatch.getUserOwner().getId().equals(UserContext.getInstance().getUser().getId()))
      {
        return Constants.STRUTS_RESULT_NAME__INFO;
      }

      //Aggiorno data e ora della partita
      this.currentMatch.setMatchStart(Utils.getDateFromString(this.getMatchStartDateHourMinute(), Constants.DATE_FORMAT__DDMMYYYYHHMM));

      //Set delle informazioni dell'utente che organizza la partita
      //this.currentMatch.setEmailUserOwner(this.email);
      this.currentMatch.setMobileUserOwner(this.mobile);
      this.currentMatch.setNotes(this.notes);

      //Set delle informazioni relative al centro sportivo
      this.currentMatch.setSportCenter(DAOFactory.getInstance().getSportCenterDAO().load(this.idSportCenter));
      this.currentMatch.setSportCenterName(this.getSportCenterInfo().getName());
      this.currentMatch.setSportCenterAddress(this.getSportCenterInfo().getAddress());
      this.currentMatch.setSportCenterCity(this.getSportCenterInfo().getCityProvinceName());

      //Set del tipo di partita e del massimo dei partecipanti
      this.currentMatch.setMatchType(matchType);
      this.currentMatch.setMaxPlayers(this.matchParticipantsNumber);

      //Set delle info sulla partita e sul numero massimo di fuorirosa
      this.currentMatch.setSquadOutEnabled(this.registrationOpen);
      this.currentMatch.setDirectlyRegistration(this.directlyRegistration);
      if (this.registrationOpen && this.directlyRegistration)
      {
        this.currentMatch.setMaxSquadOutPlayers(this.maxSquadOut);
      }
      else
      {
        this.currentMatch.setMaxSquadOutPlayers(0);
      }

      //Set delle info per la gestion delle disdette
      this.currentMatch.setAcceptTermination(this.accepTermination);
      if (this.accepTermination)
      {
        GregorianCalendar acceptTerminationLimit = new GregorianCalendar();
        acceptTerminationLimit.setTime(this.currentMatch.getMatchStart());
        acceptTerminationLimit.add(Calendar.HOUR_OF_DAY, -this.accepTerminationMaxHours);
        this.currentMatch.setAcceptTerminationLimit(acceptTerminationLimit.getTime());
      }
      else
      {
        this.currentMatch.setAcceptTerminationLimit(null);
      }

      //è cambiato lo stato delle iscrizioni?
      int oldRegistrationStatus;
      if (!this.currentMatch.getRegistrationClosed() && this.currentMatch.getRegistrationStart() != null)
      {
        oldRegistrationStatus = SUBSCRIPTIONS_OPENED_IN_DATE;
      }
      else if (!this.currentMatch.getRegistrationClosed() && this.currentMatch.getRegistrationStart() == null)
      {
        oldRegistrationStatus = SUBSCRIPTIONS_OPENED;
      }
      else
      {
        oldRegistrationStatus = SUBSCRIPTIONS_CLOSED;
      }


      //Set delle info per la gestion delle iscrizioni e dell'invio mail
      if (this.subNotRep == SUBSCRIPTIONS_OPENED_IN_DATE)
      {
        this.currentMatch.setRegistrationClosed(false);
        this.currentMatch.setRegistrationStart(Utils.getDateFromString(this.subNotRepDate, Constants.DATE_FORMAT__DDMMYYYYHHMM));
        //this.currentMatch.setNotified(false);    mail deve partire esclusivamente alla primissima apertura iscrizioni
        if ((!this.currentMatch.getNotified()) && oldRegistrationStatus == SUBSCRIPTIONS_OPENED_IN_DATE && this.currentMatch.getRegistrationStart().before(new Date()))
        {
          Result<Boolean> rSend = EmailManager.sendMatchRegistrationOpenEmail(currentMatch, getCurrentObjLanguage(), getCurrentCobrand());
        }
      }
      else
      {
        if (this.subNotRep == SUBSCRIPTIONS_OPENED)
        {
          this.currentMatch.setRegistrationClosed(false);
          this.currentMatch.setRegistrationStart(null);
          if (oldRegistrationStatus != SUBSCRIPTIONS_OPENED && (!currentMatch.getNotified()))  //  mail deve partire esclusivamente alla primissima apertura iscrizioni
          {
            Result<Boolean> rSend = EmailManager.sendMatchRegistrationOpenEmail(currentMatch, getCurrentObjLanguage(), getCurrentCobrand());
            if ((!this.currentMatch.getNotified()) && !rSend.isSuccessNotNull() || !rSend.getValue())
            {
              this.currentMatch.setNotified(false);
              logger.error("Error sending mail for registration Open (MatchManager.java", rSend.getErrorException());
            }
            else
            {
              this.currentMatch.setNotified(true);
            }
          }
        }
        else
        {
          this.currentMatch.setRegistrationClosed(true);
          this.currentMatch.setRegistrationStart(null);
          //this.currentMatch.setNotified(false);    mail deve partire esclusivamente alla primissima apertura iscrizioni
        }
      }

      boolean success = MatchManager.update(this.currentMatch, true, false, getCurrentObjLanguage(), getCurrentCobrand());
      if (!success)
      {
        return ERROR;
      }
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return ERROR;
    }

    String message = getText("message.match.updated");
    UserContext.getInstance().setLastMessage(message);

    return Constants.STRUTS_RESULT_NAME__MATCH_UPDATED;
  }

  public String cancel()
  {
    try
    {
      //Get della partita che sto annullando
      this.currentMatch = DAOFactory.getInstance().getMatchDAO().get(this.idMatch);
      if (this.currentMatch == null || this.currentMatch.getCanceled())
      {
        return Constants.STRUTS_RESULT_NAME__INFO;
      }
      if (!this.currentMatch.getUserOwner().getId().equals(UserContext.getInstance().getUser().getId()))
      {
        return Constants.STRUTS_RESULT_NAME__INFO;
      }

      this.currentMatch.setCanceled(true);


      boolean success = MatchManager.cancel(this.currentMatch, getCurrentObjLanguage(), getCurrentCobrand());
      if (!success)
      {
        return ERROR;
      }

      String message = getText("messaggio.partitaAnnullata");

      message = message.replace(Constants.REPLACEMENT__MATCH_START_DATE, DateManager.showDate(this.currentMatch.getMatchStart(), DateManager.FORMAT_DATE_13));

      UserContext.getInstance().setLastMessage(message);
      //getSession().put(Constants.SESSION_KEY__MATCH_CANCELLED_MESSAGE, message);

    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return ERROR;
    }

    return SUCCESS;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="VALIDATION METHODS">
  public void validateInsert()
  {
    Calendar minDate = Calendar.getInstance();
    Calendar maxDate = Calendar.getInstance();
    Date matchStartDateConv = null;
    maxDate.add(Calendar.YEAR, 1);
    if (this.getMatchStartDateHourMinute().length() <= Constants.STRING_LENGHT_0)
    {
      addFieldError("matchStartDate", getText("error.dataObbligatoria"));
    }
    else
    {
      matchStartDateConv = Utils.getDateFromString(this.getMatchStartDateHourMinute(), Constants.DATE_FORMAT__DDMMYYYYHHMM);
      //recupero e stampo date in Jsp in caso di validazione, altrimenti la perdo
      this.labelMatchStart = DateManager.showDate(this.getMatchStartDateHourMinute(), Constants.DATE_FORMAT__DDMMYYYYHHMM, DateManager.FORMAT_DATE_2);
      if (StringUtils.isNotEmpty(this.subNotRepDate))
      {
        this.labelRegisterDate = DateManager.showDate(this.subNotRepDate, Constants.DATE_FORMAT__DDMMYYYYHHMM, DateManager.FORMAT_DATE_1);
      }
      if (StringUtils.isNotEmpty(this.endRepetitiveDate))
      {
        this.labelRepDate = DateManager.showDate(this.endRepetitiveDate, Constants.DATE_FORMAT__DDMMYYYY, DateManager.FORMAT_DATE_2);
      }
      if (matchStartDateConv == null)
      {
        addFieldError("matchStartDate", getText("error.dataNonValida"));
      }
      else
      {
        if (matchStartDateConv.before(minDate.getTime()) || matchStartDateConv.after(maxDate.getTime()))
        {
          addFieldError("matchStartDate", getText("error.dataPeriodoSbagliato"));
        }
      }
    }

    if (this.idMatchType == Constants.INVALID_ID)
    {
      addFieldError("idMatchType", getText("error.tipoPartitaObbligatorio"));
    }
    else
    {
      if (this.matchParticipantsNumber <= Constants.NUMBER_VALUE_0)
      {
        addFieldError("matchParticipantsNumber", getText("error.numeroPartecipantiObbligatorio"));
      }
    }

    if ((this.mobile != null) && (this.mobile.length() > Constants.STRING_LENGHT_0))
    {
      if (!checkMobileCharacters(this.mobile))
      {
        addFieldError("mobile", getText("error.mobile.invalid"));
      }
    }

//    if ((this.email != null) && (this.email.length() > Constants.STRING_LENGHT_0))
//    {
//      if (!DataValidator.checkEmail(this.email))
//      {
//        addFieldError("email", getText("error.email.invalid"));
//      }
//    }

    if ((this.notes.length() > Constants.STRING_LENGHT_1000))
    {
      addFieldError("notes", getText("error.notes.invalid"));
    }

    if (this.registrationOpen == null)
    {
      addFieldError("registrationOpen", getText("error.registrationOpen"));
    }
    else if (this.registrationOpen && this.directlyRegistration)
    {
      if (this.maxSquadOut <= Constants.NUMBER_VALUE_0)
      {
        addFieldError("maxSquadOut", getText("error.massimoFuoriRosaObbligatorio"));
      }
    }

    if (this.accepTermination == null)
    {
      addFieldError("accepTermination", getText("error.accepTermination"));
    }

    if (!this.getSportCenterInfo().isValid())
    {
      addFieldError("idSportcenter", getText("error.selezionareIlCampo"));
    }

    if (this.repetitive)
    {
      if (this.endRepetitiveDate.length() <= Constants.STRING_LENGHT_0)
      {
        addFieldError("endRepetitiveDate", getText("error.dataObbligatoria"));
      }
      else
      {
        Date tmpDateConv = Utils.getDateFromString(this.endRepetitiveDate, Constants.DATE_FORMAT__DDMMYYYY);
        if (tmpDateConv == null)
        {
          addFieldError("endRepetitiveDate", getText("error.dataNonValida"));
        }
        else
        {
          if (matchStartDateConv != null)
          {
            GregorianCalendar maxRepetitiveDate = new GregorianCalendar();
            maxRepetitiveDate.setTime(matchStartDateConv);
            maxRepetitiveDate.add(Calendar.YEAR, 1);
            if (tmpDateConv.before(matchStartDateConv) || tmpDateConv.after(maxRepetitiveDate.getTime()))
            {
              addFieldError("endRepetitiveDate", getText("error.dataPeriodoSbagliato"));
            }
          }
        }
      }
    }
    else if (!this.repetitive && this.subNotRep == SUBSCRIPTIONS_OPENED_IN_DATE)
    {
      if (this.subNotRepDate.length() <= Constants.STRING_LENGHT_0)
      {
        addFieldError("subNotRepDate", getText("error.dataObbligatoria"));
      }
      else
      {
        Date tmpDateConv = Utils.getDateFromString(this.subNotRepDate, Constants.DATE_FORMAT__DDMMYYYYHHMM);
        if (tmpDateConv == null)
        {
          addFieldError("subNotRepDate", getText("error.dataNonValida"));
        }
        else
        {
          if (matchStartDateConv != null)
          {
            if (tmpDateConv.after(matchStartDateConv))
            {
              addFieldError("subNotRepDate", getText("error.dataPeriodoSbagliato"));
            }
          }
        }
      }
    }

    int value = this.idMatchType * 2;
    int endValue = (this.idMatchType * 4) - value;
    for (int j = 0; j <= endValue; j++)
    {
      this.matchParticipantsNumberList.add(value);
      value++;
    }

    for (int j = 0; j < this.matchParticipantsNumber; j++)
    {
      this.maxSquadOutList.add(j + 1);
    }
  }

  public void validateUpdate()
  {
    try
    {
      Calendar minDate = Calendar.getInstance();
      Calendar maxDate = Calendar.getInstance();
      Date matchStartDateConv = null;
      maxDate.add(Calendar.YEAR, 1);
      if (this.getMatchStartDateHourMinute().length() <= Constants.STRING_LENGHT_0)
      {
        addFieldError("matchStartDate", getText("error.dataObbligatoria"));
      }
      else
      {
        matchStartDateConv = Utils.getDateFromString(this.getMatchStartDateHourMinute(), Constants.DATE_FORMAT__DDMMYYYYHHMM);
        //recupero e stampo date in Jsp in caso di validazione, altrimenti la perdo
        this.labelMatchStart = DateManager.showDate(this.getMatchStartDateHourMinute(), Constants.DATE_FORMAT__DDMMYYYYHHMM, DateManager.FORMAT_DATE_2);
        if (StringUtils.isNotEmpty(this.subNotRepDate))
        {
          this.labelRegisterDate = DateManager.showDate(this.subNotRepDate, Constants.DATE_FORMAT__DDMMYYYYHHMM, DateManager.FORMAT_DATE_1);
        }
        if (StringUtils.isNotEmpty(this.endRepetitiveDate))
        {
          this.labelRepDate = DateManager.showDate(this.endRepetitiveDate, Constants.DATE_FORMAT__DDMMYYYY, DateManager.FORMAT_DATE_2);
        }
        if (matchStartDateConv == null)
        {
          addFieldError("matchStartDate", getText("error.dataNonValida"));
        }
        else
        {
          if (matchStartDateConv.before(minDate.getTime()) || matchStartDateConv.after(maxDate.getTime()))
          {
            addFieldError("matchStartDate", getText("error.dataPeriodoSbagliato"));
          }
        }
      }

      this.labelRegistrationDate = StringUtils.isBlank(this.subNotRepDate) ? this.subNotRepDate : DateManager.showDate(this.subNotRepDate, Constants.DATE_FORMAT__DDMMYYYYHHMM, DateManager.FORMAT_DATE_1);

      if (this.idMatchType == Constants.INVALID_ID)
      {
        addFieldError("idMatchType", getText("error.tipoPartitaObbligatorio"));
      }
      else
      {
        if (this.matchParticipantsNumber <= Constants.NUMBER_VALUE_0)
        {
          addFieldError("matchParticipantsNumber", getText("error.numeroPartecipantiObbligatorio"));
        }
      }

      if ((this.mobile != null) && (this.mobile.length() > Constants.STRING_LENGHT_0))
      {
        if (!checkMobileCharacters(this.mobile))
        {
          addFieldError("mobile", getText("error.mobile.invalid"));
        }
      }

      if ((this.notes.length() > Constants.STRING_LENGHT_1000))
      {
        addFieldError("notes", getText("error.notes.invalid"));
      }

      if (this.registrationOpen == null)
      {
        addFieldError("registrationOpen", getText("error.registrationOpen"));
      }
      else if (this.registrationOpen && this.directlyRegistration)
      {
        if (this.maxSquadOut <= Constants.NUMBER_VALUE_0)
        {
          addFieldError("maxSquadOut", getText("error.massimoFuoriRosaObbligatorio"));
        }
      }

      if (this.accepTermination == null)
      {
        addFieldError("accepTermination", getText("error.accepTermination"));
      }

      if (this.subNotRep == SUBSCRIPTIONS_OPENED_IN_DATE)
      {
        if (this.subNotRepDate.length() <= Constants.STRING_LENGHT_0)
        {
          addFieldError("subNotRepDate", getText("error.dataObbligatoria"));
        }
        else
        {
          Date tmpDateConv = Utils.getDateFromString(this.subNotRepDate, Constants.DATE_FORMAT__DDMMYYYYHHMM);
          if (tmpDateConv == null)
          {
            addFieldError("subNotRepDate", getText("error.dataNonValida"));
          }
          else
          {
            if (tmpDateConv.after(matchStartDateConv))
            {
              addFieldError("subNotRepDate", getText("error.dataPeriodoSbagliato"));
            }
          }
        }
      }

      int value = this.idMatchType * 2;
      if (this.currentPlayerCount > value)
      {
        value = this.currentPlayerCount;
      }

      int endValue = (this.idMatchType * 4) - value;
      if (endValue <= 0)
      {
        this.matchParticipantsNumberList.add(value);
      }
      else
      {
        for (int j = 0; j <= endValue; j++)
        {
          this.matchParticipantsNumberList.add(value);
          value++;
        }
      }

      for (int j = 0; j < this.matchParticipantsNumber; j++)
      {
        this.maxSquadOutList.add(j + 1);
      }
    }
    catch (Exception ex)
    {
      String message = String.format("Current IdUser: %s, IdMatch %s", UserContext.getInstance().getUser().getId(), this.idMatch);
      logger.error(message, ex);
      addFieldError("unexpectedError", getText("error.erroreImprevisto"));
    }
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="PRIVATE METHODS">
  private boolean checkMobileCharacters(String mobile)
  {
    String character;

    for (int i = 0; i < mobile.length(); i++)
    {
      character = String.valueOf(mobile.charAt(i));
      if (!StringUtils.isNumeric(character) && !character.equalsIgnoreCase("+"))
      {
        return false;
      }
    }

    return true;
  }

// </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public int getIdMatch()
  {
    return idMatch;
  }

  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  public Match getCurrentMatch()
  {
    return currentMatch;
  }

  public void setCurrentMatch(Match currentMatch)
  {
    this.currentMatch = currentMatch;
  }

  public Boolean getAccepTermination()
  {
    return accepTermination;
  }

  public void setAccepTermination(Boolean accepTermination)
  {
    this.accepTermination = accepTermination;
  }

  public int getAccepTerminationMaxHours()
  {
    return accepTerminationMaxHours;
  }

  public void setAccepTerminationMaxHours(int accepTerminationMaxHours)
  {
    this.accepTerminationMaxHours = accepTerminationMaxHours;
  }

  public String getEndRepetitiveDate()
  {
    return endRepetitiveDate;
  }

  public void setEndRepetitiveDate(String endRepetitiveDate)
  {
    this.endRepetitiveDate = endRepetitiveDate;
  }

  public String getMatchType()
  {
    return matchType;
  }

  public void setMatchType(String matchType)
  {
    this.matchType = matchType;
  }

//  public String getEmail()
//  {
//    return email;
//  }
//
//  public void setEmail(String email)
//  {
//    this.email = email;
//  }
  public int getIdMatchType()
  {
    return idMatchType;
  }

  public void setIdMatchType(int idMatchType)
  {
    this.idMatchType = idMatchType;
  }

  public int getMatchParticipantsNumber()
  {
    return matchParticipantsNumber;
  }

  public void setMatchParticipantsNumber(int matchParticipantsNumber)
  {
    this.matchParticipantsNumber = matchParticipantsNumber;
  }

  public String getMatchStartDateHourMinute()
  {
    return matchStartDate + " " + matchStartHour + ":" + matchStartMinute;
  }

  public String getMatchStartDate()
  {
    return matchStartDate;
  }

  public void setMatchStartDate(String matchStartDate)
  {
    this.matchStartDate = matchStartDate;
  }

  public String getMatchStartHour()
  {
    return matchStartHour;
  }

  public void setMatchStartHour(String matchStartHour)
  {
    this.matchStartHour = matchStartHour;
  }

  public String getMatchStartMinute()
  {
    return matchStartMinute;
  }

  public void setMatchStartMinute(String matchStartMinute)
  {
    this.matchStartMinute = matchStartMinute;
  }

  public List<MatchTypeInfo> getMatchTypeInfoList()
  {
    return AppContext.getInstance().getAllMatchTypeInfo(getCurrentObjLanguage(), getCurrentCobrand());
  }

  public List<PlayerRoleInfo> getPlayerRoleInfoList()
  {
    return AppContext.getInstance().getAllPlayerRoleInfo(getCurrentObjLanguage(), getCurrentCobrand());
  }

  public int getMaxSquadOut()
  {
    return maxSquadOut;
  }

  public void setMaxSquadOut(int maxSquadOut)
  {
    this.maxSquadOut = maxSquadOut;
  }

  public String getMobile()
  {
    return mobile;
  }

  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }

  public String getNotes()
  {
    return notes;
  }

  public void setNotes(String notes)
  {
    this.notes = notes;
  }

  public Boolean getRegistrationOpen()
  {
    return registrationOpen;
  }

  public void setRegistrationOpen(Boolean registrationOpen)
  {
    this.registrationOpen = registrationOpen;
  }

  public boolean isRepetitive()
  {
    return repetitive;
  }

  public void setRepetitive(boolean repetitive)
  {
    this.repetitive = repetitive;
  }

  public Date getAccepTerminationEndDate()
  {
    return accepTerminationEndDate;
  }

  public void setAccepTerminationEndDate(Date accepTerminationEndDate)
  {
    this.accepTerminationEndDate = accepTerminationEndDate;
  }

  public int getSubNotRep()
  {
    return subNotRep;
  }

  public void setSubNotRep(int subNotRep)
  {
    this.subNotRep = subNotRep;
  }

  public String getSubNotRepDate()
  {
    return subNotRepDate;
  }

  public void setSubNotRepDate(String subNotRepDate)
  {
    this.subNotRepDate = subNotRepDate;
  }

  public boolean isSubRep()
  {
    return subRep;
  }

  public void setSubRep(boolean subRep)
  {
    this.subRep = subRep;
  }

  public boolean isDirectlyRegistration()
  {
    return directlyRegistration;
  }

  public void setDirectlyRegistration(boolean directlyRegistration)
  {
    this.directlyRegistration = directlyRegistration;
  }

  public int getSubRepDateDay()
  {
    return subRepDateDay;
  }

  public void setSubRepDateDay(int subRepDateDay)
  {
    this.subRepDateDay = subRepDateDay;
  }

  public int getSubRepDateHours()
  {
    return subRepDateHours;
  }

  public void setSubRepDateHours(int subRepDateHours)
  {
    this.subRepDateHours = subRepDateHours;
  }

  public int getSubRepDateMinutes()
  {
    return subRepDateMinutes;
  }

  public void setSubRepDateMinutes(int subRepDateMinutes)
  {
    this.subRepDateMinutes = subRepDateMinutes;
  }

  public List getHoursList()
  {
    return hoursList;
  }

  public void setHoursList(List hoursList)
  {
    this.hoursList = hoursList;
  }

  public List getMatchParticipantsNumberList()
  {
    return matchParticipantsNumberList;
  }

  public void setMatchParticipantsNumberList(List matchParticipantsNumberList)
  {
    this.matchParticipantsNumberList = matchParticipantsNumberList;
  }

  public List getMinutesList()
  {
    return minutesList;
  }

  public List getNumberOfDaysList()
  {
    return numberOfDaysList;
  }

  public List getMaxSquadOutList()
  {
    return maxSquadOutList;
  }

  public void setMaxSquadOutList(List maxSquadOutList)
  {
    this.maxSquadOutList = maxSquadOutList;
  }

  public int getIdPlayerRole()
  {
    return idPlayerRole;
  }

  public void setIdPlayerRole(int idPlayerRole)
  {
    this.idPlayerRole = idPlayerRole;
  }

  public Date getMatchStart()
  {
    return matchStart;
  }

  public void setMatchStart(Date matchStart)
  {
    this.matchStart = matchStart;
  }

  public Date getStartRegistrationDate()
  {
    return startRegistrationDate;
  }

  public void setStartRegistrationDate(Date startRegistrationDate)
  {
    this.startRegistrationDate = startRegistrationDate;
  }

  public int getCurrentPlayerCount()
  {
    return currentPlayerCount;
  }

  public void setCurrentPlayerCount(int currentPlayerCount)
  {
    this.currentPlayerCount = currentPlayerCount;
  }

  public int getIdSportCenter()
  {
    return idSportCenter;
  }

  public void setIdSportCenter(int idSportCenter)
  {
    this.idSportCenter = idSportCenter;
  }

  public SportCenterInfo getSportCenterInfo()
  {
    return InfoProvider.getSportCenterInfo(this.idSportCenter);
  }

  /**
   * @return the labelMatchStart
   */
  public String getLabelMatchStart()
  {
    return labelMatchStart;
  }

  /**
   * @param labelMatchStart the labelMatchStart to set
   */
  public void setLabelMatchStart(String labelMatchStart)
  {
    this.labelMatchStart = labelMatchStart;
  }

  /**
   * @return the labelRegistrationdate
   */
  public String getLabelRegistrationDate()
  {
    return labelRegistrationDate;
  }

  /**
   * @param labelRegistrationDate the labelRegistrationDate to set
   */
  public void setLabelRegistrationDate(String labelRegistrationDate)
  {
    this.labelRegistrationDate = labelRegistrationDate;
  }

  /**
   * @return the labelRepDateDate
   */
  public String getLabelRegisterDate()
  {
    return labelRegisterDate;
  }

  /**
   * @return the labelRepDate
   */
  public String getLabelRepDate()
  {
    return labelRepDate;
  }

  /**
   * @return the canOrganizeMatch
   */
  public boolean isCanOrganizeMatch()
  {
    return canOrganizeMatch;
  }

  /**
   * @return the fatherOrganizerList
   */
  public List<List<List<UserInfo>>> getFatherOrganizerList()
  {
    return fatherOrganizerList;
  }

  /**
   * @param presentationText the presentationText to set
   */
  public void setPresentationText(String presentationText)
  {
    this.presentationText = presentationText;
  }

  /**
   * @return the totOrganizers
   */
  public int getTotOrganizers()
  {
    return totOrganizers;
  }

  

  
  // </editor-fold>
}
