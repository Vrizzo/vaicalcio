package it.newmedia.gokick.backOffice.guibean;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.backOffice.AppContext;
import it.newmedia.gokick.backOffice.manager.DateManager;
import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.MatchComment;
import it.newmedia.gokick.data.hibernate.beans.Player;
import it.newmedia.security.encryption.MD5;
import it.newmedia.utils.DateUtil;
import it.newmedia.web.WebUtil;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

public class GuiCalendarInfo extends AGuiBean
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  private boolean registrationsOpen;

  private boolean registrationsOpenInFuture;

  private int missingPlayers;

  private int idMatch;

  private long missingDays;

  private long missingHours;

  private long missingMinutes;

  private int commentsNumber;

  private Match match;

  private boolean reportEditable;

  private boolean past;

  private String reporter;

  private String matchDate;

  private String archiveMatchUrl;

  private String reportMatchUrl;

  private String viewCommentsUrl;

  private String viewMatchUrl;

  private String googleMapUrlUrl;

  private String sportCenterName;
  private String sportCenterProvince;
  private String sportCenterCity;
  private int playerForTeam;
  private int idUserOwner;
  private String userOwnerName;
  private boolean recorded;
  private boolean registrationsClosed;
  private boolean canceled;
  private Date recordedDate ;
  private Date dateMatch ;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  public GuiCalendarInfo(Match match)
  {
    this.match = match;

    if (match.getMatchStart().before(new Date()))
      this.past = true;

    this.commentsNumber = 0;
    for (MatchComment matchComment : match.getCommentList() )
    {
      if (matchComment.getApproved() && !matchComment.getDeleted())
        this.commentsNumber = this.commentsNumber+1;
    }

    try
    {
      if (this.past)
      {
       if (this.match.getRecorded())
          this.reportMatchUrl =  String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",  AppContext.getInstance().getSiteUrl(), this.getIdUserOwner(), this.getUserOwnerPasswordMD5(), WebUtil.encode("archiveMatch!report.action?idMatch=" + this.getIdMatch()));
       else
          this.archiveMatchUrl = String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",  AppContext.getInstance().getSiteUrl(), this.getIdUserOwner(), this.getUserOwnerPasswordMD5(), WebUtil.encode("archiveMatch!input.action?idMatch=" + this.getIdMatch()));
      }
      else
      {
          this.viewMatchUrl =    String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",  AppContext.getInstance().getSiteUrl(), this.getIdUserOwner(), this.getUserOwnerPasswordMD5(), WebUtil.encode("viewMatch.action?idMatch=" + this.getIdMatch()));
      }

      if (this.commentsNumber > 0)
        this.viewCommentsUrl = String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",  AppContext.getInstance().getSiteUrl(), this.getIdUserOwner(), this.getUserOwnerPasswordMD5(), WebUtil.encode("matchComments!viewAll.action?idMatch=" + this.getIdMatch()));

      this.googleMapUrlUrl = AppContext.getInstance().getGoogleMapUrl()
                          + (match.getSportCenter()!=null? match.getSportCenter().getAddress(): this.match.getSportCenterAddress()) + ","
                          + (match.getSportCenter()!=null? match.getSportCenter().getCity().getName(): match.getSportCenterCity()) + ","
                          + (match.getSportCenter()!=null? match.getSportCenter().getProvince().getName():match.getSportCenterCity());

    }
    catch (UnsupportedEncodingException ex)
    {
      Logger.getLogger(GuiCalendarInfo.class.getName()).log(Level.SEVERE, null, ex);
    }

    this.matchDate=DateManager.showDate(match.getMatchStart());

    int countRegistered=0;
    for(Player player : match.getPlayerList())
    {
      if (player.getReporterEnabled())
      {
        this.reporter = player.getUser().getFirstName() + " " + player.getUser().getLastName();
      }
      else
      {
        this.reporter = this.match.getUserOwner().getFirstName() + " " + this.match.getUserOwner().getLastName();
      }

      if (player.getEnumPlayerStatus().equals(EnumPlayerStatus.OwnerRegistered) ||
              player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRegistered) ||
                player.getEnumPlayerStatus().equals(EnumPlayerStatus.UserRequestRegistered) ||
                  player.getEnumPlayerStatus().equals(EnumPlayerStatus.Out))
      {
        countRegistered=countRegistered+1;
      }
    }
    
    


    if (this.match.getRegistrationClosed())
    {
      //registrazioni chiuse
      this.registrationsOpen = false;
      //this.missingPlayers = 0;
      this.missingPlayers = this.match.getMaxPlayers() - countRegistered;
      this.missingDays = 0;
      this.missingHours = 0;
      this.missingMinutes = 0;
    }
    else
    {
      if (this.match.getRegistrationStart() == null)
      {
        //registrazioni aperte
        this.registrationsOpen = true;
        this.missingPlayers = this.match.getMaxPlayers() - countRegistered;
        this.missingDays = 0;
        this.missingMinutes = 0;
      }
      else if ((this.match.getRegistrationStart()) != null && (this.match.getRegistrationStart().after(new Date())))
      {
        //registrazioni aperte con countdown
        this.missingPlayers = this.match.getMaxPlayers() - countRegistered;
        this.registrationsOpenInFuture = true;
        this.missingDays = DateUtil.getDiffDays(this.match.getRegistrationStart(), new Date());
        this.missingMinutes = 0;
        if (this.missingDays <= 0)
        {
          this.missingHours = DateUtil.getDiffHours(this.match.getRegistrationStart(), new Date());
          if (this.missingHours <= 0)
          {
            this.missingMinutes = DateUtil.getDiffMinutes(this.match.getRegistrationStart(), new Date());
          }
        }
      }
      else
      {
        //registrazioni aperte
        this.registrationsOpen = true;
        this.missingPlayers = this.match.getMaxPlayers() - countRegistered;
        this.missingDays = 0;
        this.missingHours = 0;
        this.missingMinutes = 0;
      }
    }
    



  }

  public GuiCalendarInfo(Object obj[])
  {
    Date matchStart=(Date)(obj[4]);
    Date registrationStart=(Date)(obj[7]);
    this.idUserOwner = ((Integer)(obj[11]));
    String md5UserOwnerPwd=getUserOwnerPasswordMD5((String)(obj[12]));
    this.idMatch = (Integer)(obj[0]);
    int matchIdSportCenter=(Integer)(obj[21]);
    this.missingPlayers = ((BigInteger)(obj[3])).intValue();
    this.playerForTeam = (Integer)(obj[24]) / 2;
    this.userOwnerName = (String) (obj[13]);
    this.recorded = (((Byte)(obj[5])).intValue()> 0);
    this.registrationsClosed = (((Byte)(obj[6])).intValue()>0);
    this.canceled = (((Byte)(obj[25])).intValue()>0);
    this.recordedDate=(Date)(obj[26]);



    if ( (matchStart).before(new Date()))
      this.past = true;

    this.commentsNumber = ((BigInteger)(obj[10])).intValue();

    try
    {
      if (this.past)
      {
       if (recorded)
       {
          this.reportMatchUrl =  String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",  AppContext.getInstance().getSiteUrl(), idUserOwner, md5UserOwnerPwd, WebUtil.encode("archiveMatch!report.action?idMatch=" + idMatch));
       }
       else
       {
          this.archiveMatchUrl = String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",  AppContext.getInstance().getSiteUrl(), idUserOwner, md5UserOwnerPwd, WebUtil.encode("archiveMatch!input.action?idMatch=" + idMatch));
       }
      }
      else
      {
          this.viewMatchUrl =    String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",  AppContext.getInstance().getSiteUrl(), idUserOwner, md5UserOwnerPwd, WebUtil.encode("viewMatch.action?idMatch=" + idMatch));
      }

      if (this.commentsNumber > 0)
      {
        this.viewCommentsUrl = String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",  AppContext.getInstance().getSiteUrl(), idUserOwner, md5UserOwnerPwd, WebUtil.encode("matchComments!viewAll.action?idMatch=" + idMatch));
      }
      
      this.sportCenterName=     matchIdSportCenter > 0 ? (String)(obj[17]) :(String)(obj[23]);
      this.sportCenterProvince= matchIdSportCenter > 0 ? (String)(obj[20]) :(String)(obj[9]);
      this.sportCenterCity=     matchIdSportCenter > 0 ? (String)(obj[19]) :(String)(obj[9]);
      this.googleMapUrlUrl = AppContext.getInstance().getGoogleMapUrl()
                          + (matchIdSportCenter > 0 ? (String)(obj[18]): (String)(obj[8])) + ","
                          + (matchIdSportCenter > 0 ? (String)(obj[19]): (String)(obj[9])) + ","
                          + (matchIdSportCenter > 0 ? (String)(obj[20]): (String)(obj[9]));

    }
    catch (UnsupportedEncodingException ex)
    {
      logger.error(ex.getMessage(),ex);
    }
    this.dateMatch=matchStart;
    this.matchDate=DateManager.showDate(matchStart);
    
    if (StringUtils.isNotBlank((String) (obj[22])))
    {
      this.reporter = (String) (obj[22]);
    }
    else
    {
      this.reporter = this.userOwnerName;
    }

    this.missingDays = 0;
    this.missingHours = 0;
    this.missingMinutes = 0;
    if (this.registrationsClosed)
    {
      //registrazioni chiuse
      this.registrationsOpen = false;
    }
    else
    {
      if (registrationStart==null)
      {
        //registrazioni aperte
        this.registrationsOpen = true;
      }
      else if (registrationStart!=null && (registrationStart).after(new Date()))
      {
        //registrazioni aperte con countdown
        this.registrationsOpenInFuture = true;
        this.missingDays = DateUtil.getDiffDays(registrationStart, new Date());
        if (this.missingDays <= 0)
        {
          this.missingHours = DateUtil.getDiffHours(registrationStart, new Date());
          if (this.missingHours <= 0)
          {
            this.missingMinutes = DateUtil.getDiffMinutes(registrationStart, new Date());
          }
        }
      }
      else
      {
        //registrazioni aperte
        this.registrationsOpen = true;
      }
    }


  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --"  >
  /**
   * @return the registrationsOpenInFuture
   */
  public boolean isRegistrationsOpenInFuture()
  {
    return registrationsOpenInFuture;
  }

  /**
   * @return the reportEditable
   */
  public boolean isReportEditable()
  {
    GregorianCalendar limitEditDate = new GregorianCalendar();
    if (this.getRecordedDate() != null)
    {
      limitEditDate.setTime(this.recordedDate);
      limitEditDate.add(Calendar.DAY_OF_MONTH, AppContext.getInstance().getArchiveMatchEditableNumbersOfDays());
      if (limitEditDate.before(new GregorianCalendar()))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * @return the match
   */
  public Match getMatch()
  {
    return match;
  }

  public long getMissingDays()
  {
    return missingDays;
  }

  public long getMissingMinutes()
  {
    return missingMinutes;
  }

  public int getMissingPlayers()
  {
    return missingPlayers;
  }

  public boolean isRegistrationsOpen()
  {
    return registrationsOpen;
  }

  public long getMissingHours()
  {
    return missingHours;
  }

  /**
   * @return the past
   */
  public boolean isPast()
  {
    return past;
  }

  /**
   * @return the reporter
   */
  public String getReporter()
  {
    return reporter;
  }

  /**
   * @return the commentsNumber
   */
  public int getCommentsNumber()
  {
    return commentsNumber;
  }

  public int getIdUserOwner()
  {
    return this.match.getUserOwner().getId();
  }

  public String getUserOwnerPasswordMD5()
  {
    return MD5.getHashString(this.match.getUserOwner().getPassword());
  }

  private String getUserOwnerPasswordMD5(String password)
  {
    return MD5.getHashString(password);
  }

  public int getIdMatch()
  {
    return this.idMatch;
  }

//  public String getViewMatchUrl()
//  {
//    //<a href="/GoKickWebSite/backOfficeLogin.action?id=${guiCalendar.idUserOwner}&key=${guiCalendar.userOwnerPasswordMD5}&goToUrl=viewMatch.action%3FidMatch=${guiCalendar.idMatch}" target="_blank">Vai..</a>
//    try
//    {
//      return String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",
//              AppContext.getInstance().getSiteUrl(),
//              this.getIdUserOwner(),
//              this.getUserOwnerPasswordMD5(),
//              WebUtil.encode("viewMatch.action?idMatch=" + this.getIdMatch()));
//    }
//    catch (UnsupportedEncodingException unsupportedEncodingException)
//    {
//      return "Error creating ViewMatchUrl";
//    }
//  }

//  public String getViewCommentsUrl()
//  {
//    //http://localhost:8080/GoKickWebSite/matchComments!viewAll.action?idMatch=225
//    http://localhost:8080/GoKickWebSite/matchComments!viewAll.action?idMatch=214
//    try
//    {
//      return String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",
//              AppContext.getInstance().getSiteUrl(),
//              this.getIdUserOwner(),
//              this.getUserOwnerPasswordMD5(),
//              WebUtil.encode("matchComments!viewAll.action?idMatch=" + this.getIdMatch()));
//    }
//    catch (UnsupportedEncodingException unsupportedEncodingException)
//    {
//      return "Error creating ViewCommentsUrl";
//    }
//  }

//  public String getReportMatchUrl()
//  {
//    //http://localhost:8080/GoKickWebSite/archiveMatch!report.action?idMatch=233
//    try
//    {
//      return String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",
//              AppContext.getInstance().getSiteUrl(),
//              this.getIdUserOwner(),
//              this.getUserOwnerPasswordMD5(),
//              WebUtil.encode("archiveMatch!report.action?idMatch=" + this.getIdMatch()));
//    }
//    catch (UnsupportedEncodingException unsupportedEncodingException)
//    {
//      return "Error creating ViewCommentsUrl";
//    }
//  }

//  public String getArchiveMatchUrl()
//  {
//    //http://localhost:8080/GoKickWebSite/archiveMatch!input.action?idMatch=234
//    try
//    {
//      return String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",
//              AppContext.getInstance().getSiteUrl(),
//              this.getIdUserOwner(),
//              this.getUserOwnerPasswordMD5(),
//              WebUtil.encode("archiveMatch!input.action?idMatch=" + this.getIdMatch()));
//    }
//    catch (UnsupportedEncodingException unsupportedEncodingException)
//    {
//      return "Error creating ArchiveUrl";
//    }
//  }

//  public String getGoogleMapUrlUrl()
//  {
//    return  AppContext.getInstance().getGoogleMapUrl()
//                          + (match.getSportCenter()!=null? match.getSportCenter().getAddress(): this.match.getSportCenterAddress()) + ","
//                          + (match.getSportCenter()!=null? match.getSportCenter().getCity().getName(): match.getSportCenterCity()) + ","
//                          + (match.getSportCenter()!=null? match.getSportCenter().getProvince().getName():match.getSportCenterCity());
//
//  }

  /**
   * @return the matchDate
   */
  public String getMatchDate()
  {
    return matchDate;
  }
  /**
   * @return the archiveMatchUrl
   */
  public String getArchiveMatchUrl()
  {
    return archiveMatchUrl;
  }

  /**
   * @param archiveMatchUrl the archiveMatchUrl to set
   */
  public void setArchiveMatchUrl(String archiveMatchUrl)
  {
    this.archiveMatchUrl = archiveMatchUrl;
  }

  /**
   * @return the reportMatchUrl
   */
  public String getReportMatchUrl()
  {
    return reportMatchUrl;
  }

  /**
   * @param reportMatchUrl the reportMatchUrl to set
   */
  public void setReportMatchUrl(String reportMatchUrl)
  {
    this.reportMatchUrl = reportMatchUrl;
  }

  /**
   * @return the viewCommentsUrl
   */
  public String getViewCommentsUrl()
  {
    return viewCommentsUrl;
  }

  /**
   * @param viewCommentsUrl the viewCommentsUrl to set
   */
  public void setViewCommentsUrl(String viewCommentsUrl)
  {
    this.viewCommentsUrl = viewCommentsUrl;
  }

  /**
   * @return the viewMatchUrl
   */
  public String getViewMatchUrl()
  {
    return viewMatchUrl;
  }

  /**
   * @param viewMatchUrl the viewMatchUrl to set
   */
  public void setViewMatchUrl(String viewMatchUrl)
  {
    this.viewMatchUrl = viewMatchUrl;
  }

  /**
   * @return the googleMapUrlUrl
   */
  public String getGoogleMapUrlUrl()
  {
    return googleMapUrlUrl;
  }

  /**
   * @param googleMapUrlUrl the googleMapUrlUrl to set
   */
  public void setGoogleMapUrlUrl(String googleMapUrlUrl)
  {
    this.googleMapUrlUrl = googleMapUrlUrl;
  }

  /**
   * @return the sportCenterName
   */
  public String getSportCenterName()
  {
    return sportCenterName;
  }

  /**
   * @return the sportCenterProvince
   */
  public String getSportCenterProvince()
  {
    return sportCenterProvince;
  }

  /**
   * @return the sportCenterCity
   */
  public String getSportCenterCity()
  {
    return sportCenterCity;
  }

  /**
   * @return the playerForTeam
   */
  public int getPlayerForTeam()
  {
    return playerForTeam;
  }

  /**
   * @return the userOwnerName
   */
  public String getUserOwnerName()
  {
    return userOwnerName;
  }

  /**
   * @return the recorded
   */
  public boolean isRecorded()
  {
    return recorded;
  }

  /**
   * @return the registrationsClosed
   */
  public boolean isRegistrationsClosed()
  {
    return registrationsClosed;
  }

  /**
   * @return the canceled
   */
  public boolean isCanceled()
  {
    return canceled;
  }

  /**
   * @return the recordedDate
   */
  public Date getRecordedDate()
  {
    return recordedDate;
  }

  /**
   * @return the dateMatch
   */
  public Date getDateMatch()
  {
    return dateMatch;
  }

 


  
  

  // </editor-fold>
}
