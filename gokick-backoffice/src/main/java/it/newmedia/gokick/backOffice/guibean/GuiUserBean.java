package it.newmedia.gokick.backOffice.guibean;

import it.newmedia.gokick.backOffice.AppContext;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.data.hibernate.beans.Statistic;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.backOffice.manager.DateManager;
import it.newmedia.gokick.data.enums.EnumUserSquadStatus;
import it.newmedia.gokick.data.hibernate.beans.UserSquad;
import it.newmedia.security.encryption.MD5;
import it.newmedia.utils.MathUtil;
import it.newmedia.web.WebUtil;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author ggeroldi
 */
public class GuiUserBean extends AGuiBean
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  private User user;
  private String userAge;
  private int friendsCount;
  private String dateReg;
  private String dateLastLog;
  private PictureCard pictureCard;

  private boolean empty;

//  private String year;
  private int allTot;
  private BigDecimal reliability;
  private int monthPlayed;
  private int allWin;
  private int allDraw;
  private int allLose;
  private int goalsTot;
//  private int ownGoalsTot;
//  private BigDecimal averageGoal;
//  private BigDecimal averageVote;
  private int matchTot;
  private int matchWin;
  private int matchDraw;
  private int matchLose;
  private int challangeTot;
  private int challangeWin;
  private int challangeDraw;
  private int challangeLose;
  private int matchStored;
  private int matchOrganized;
  private int challangeStored;
  private int challangeOrganized;
  private int allPlayed;
  private int allOrganized;


  // </editor-fold>
    
  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --"  >
  /**
   * @return the user
   */
  public User getUser()
  {
    return user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user)
  {
    this.user = user;
  }

  /**
   * @return the userAge
   */
  public String getUserAge()
  {
    return userAge;
  }

  /**
   * @return the empty
   */
  public boolean isEmpty()
  {
    return empty;
  }

  /**
   * @return the allTot
   */
  public int getAllTot()
  {
    return allTot;
  }

  /**
   * @return the reliability
   */
  public BigDecimal getReliability()
  {
    try
    {
      return reliability.setScale(0, RoundingMode.UP);

    }
    catch (Exception e)
    {
      logger.error("Error Scale number Reliability in StatisticInfo.", e);
    }
    return BigDecimal.ZERO;
  }

  /**
   * @return the goalsTot
   */
  public int getGoalsTot()
  {
    return goalsTot;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  public GuiUserBean()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public void load(User user,List<Statistic> statisticList)
  {
      this.user = user;
      this.userAge=getAgefromDate(user.getBirthDay());
      //this.friendsCount=SquadManager.countFriends(user.getFirstSquad().getId());
      this.friendsCount=0;
      Iterator<UserSquad> itUserSquad =(user.getFirstSquad().getUserSquadList()).iterator();
      while (itUserSquad.hasNext())
      {
        UserSquad userSquad = itUserSquad.next();
        if (userSquad.getUserSquadStatus().equals(EnumUserSquadStatus.Confirmed.getValue()) &&
                !userSquad.getOwner())
          friendsCount=friendsCount+1;

      }
      this.dateReg=DateManager.showDate(user.getCreated(), "%1$td/%1$tm/%1$ty");
      this.dateLastLog=DateManager.showDate(user.getLastLogin(), "%1$td/%1$tm/%1$ty");

      List<PictureCard> pictureCardList=new ArrayList<PictureCard>(user.getPictureCards());
      Comparator<PictureCard> c = PictureCard.CREATED_ORDER;
      Collections.sort(pictureCardList, c);
      Collections.reverse(pictureCardList);
      //this.pictureCard=UserManager.getLastPictureCard(this.user.getId());
      this.pictureCard=pictureCardList.size()>0 ? pictureCardList.get(0): null;
      

      if(statisticList.isEmpty())
      {   
        this.empty = true;
//        this.year = "";
        this.reliability = BigDecimal.valueOf(100); //user che non ha statistiche parte da 100%
//        this.averageGoal = BigDecimal.ZERO;
//        this.averageVote = BigDecimal.ZERO;
      }
      else
        this.calculateStatistic(statisticList);
      this.valid = true;
  }

  public void load(User user)
  {
      this.user = user;
      this.userAge=getAgefromDate(user.getBirthDay());
      //this.friendsCount=SquadManager.countFriends(user.getFirstSquad().getId());
      this.dateReg=DateManager.showDate(user.getCreated(), "%1$td/%1$tm/%1$ty");
      this.dateLastLog=DateManager.showDate(user.getLastLogin(), "%1$td/%1$tm/%1$ty");

      // <editor-fold defaultstate="collapsed" desc="-- Pics --">

//      List<PictureCard> pictureCardList=new ArrayList<PictureCard>(user.getPictureCards());
//      Comparator<PictureCard> c = PictureCard.CREATED_ORDER;
//      Collections.sort(pictureCardList, c);
//      Collections.reverse(pictureCardList);
//      //this.pictureCard=UserManager.getLastPictureCard(this.user.getId());
//      this.pictureCard=pictureCardList.size()>0 ? pictureCardList.get(0): null;
//      this.empty = true;




    // </editor-fold>


//        this.year = "";
        //this.reliability = BigDecimal.valueOf(100); //user che non ha statistiche parte da 100%
//        this.averageGoal = BigDecimal.ZERO;
//        this.averageVote = BigDecimal.ZERO;
      
      this.valid = true;
  }


  public void calculateStatistic (List<Statistic> statisticList)
  {
//    BigDecimal voteTot = new BigDecimal(0);
    int allMissing = 0;
//    int voteNum = 0;

    this.empty = false;

    for (Statistic statistic : statisticList)
    {
      allMissing += statistic.getAllMissing();
//      this.goalsTot += statistic.getGoalsTot();
//      this.ownGoalsTot += statistic.getOwnGoalsTot();
//      voteNum += statistic.getVoteNum();
//      voteTot = voteTot.add(statistic.getVoteTot());
      this.matchTot += statistic.getMatchTot();
      this.matchWin += statistic.getMatchWin();
      this.matchDraw += statistic.getMatchDraw();
      this.matchLose += statistic.getMatchLose();
      this.challangeTot += statistic.getChallangeTot();
      this.challangeWin += statistic.getChallangeWin();
      this.challangeDraw += statistic.getChallangeDraw();
      this.challangeLose += statistic.getChallangeLose();
//      this.matchStored += statistic.getMatchRecorded();
      this.matchOrganized += statistic.getMatchOwner();
//      this.challangeStored += statistic.getChallangeRecorded();
      this.challangeOrganized += statistic.getChallangeOwner();
    }

    if (statisticList.size() > 0)
    {
//      Date refDate = statisticList.get(0).getRefDate();
//      SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
//      this.year = simpleDateformat.format(refDate);
    }
    this.allWin = this.getMatchWin() + this.getChallangeWin();
    this.allDraw = this.getMatchDraw() + this.getChallangeDraw();
    this.allLose = this.getMatchLose() + this.getChallangeLose();
    this.allPlayed = this.allWin + this.allDraw + this.allLose;
    this.allOrganized = this.matchOrganized + this.challangeOrganized;
    this.allTot = this.getMatchTot() + this.getChallangeTot();
    this.monthPlayed = this.allPlayed / statisticList.size();

    BigDecimal tmp;
    if (this.allTot == 0 && allMissing == 0)
    {
      this.reliability = BigDecimal.valueOf(100 );
    }
    else
    {
      tmp = new BigDecimal(allMissing);
      tmp = MathUtil.divide(tmp, this.allTot + allMissing, RoundingMode.HALF_UP, 2);
      this.reliability = BigDecimal.valueOf(100).subtract(tmp.multiply(BigDecimal.valueOf(100)));
    }
//    tmp = new BigDecimal(this.getGoalsTot());
//    if (this.getAllTot() != 0)
//    {
//      this.averageGoal = MathUtil.divide(tmp, this.getAllTot(), RoundingMode.HALF_UP, 1);
//    } else
//    {
//      this.averageGoal = BigDecimal.ZERO;
//    }
//    if (voteNum != 0)
//    {
//      this.averageVote = MathUtil.divide(voteTot, voteNum, RoundingMode.HALF_UP, 1);
//    }
//    else
//    {
//      this.averageVote =  BigDecimal.ZERO;
//    }
  }

  public String getAgefromDate(Date birthDate)
  {
    if (birthDate == null)
    {
      return "";
    } else
    {
      GregorianCalendar userBirthDate = new GregorianCalendar();
      userBirthDate.setTime(birthDate);
      GregorianCalendar currentDate = new GregorianCalendar();

      int tmpAge = currentDate.get(Calendar.YEAR) - userBirthDate.get(Calendar.YEAR);
      if (userBirthDate.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH))
      {
        tmpAge--;
      } else if (userBirthDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH))
      {
        if (userBirthDate.get(Calendar.DAY_OF_MONTH) > currentDate.get(Calendar.DAY_OF_MONTH))
        {
          tmpAge--;
        }
      }
      return String.valueOf(tmpAge);
    }
  }

  /**
   * @return the monthPlayed
   */
  public int getMonthPlayed()
  {
    return monthPlayed;
  }

  /**
   * @return the friendsCount
   */
  public int getFriendsCount()
  {
    return friendsCount;
  }

  /**
   * @return the dateReg
   */
  public String getDateReg()
  {
    return dateReg;
  }

  /**
   * @return the dateLastLog
   */
  public String getDateLastLog()
  {
    return dateLastLog;
  }

  /**
   * @return the allWin
   */
  public int getAllWin()
  {
    return allWin;
  }

  /**
   * @return the allDraw
   */
  public int getAllDraw()
  {
    return allDraw;
  }

  /**
   * @return the allLose
   */
  public int getAllLose()
  {
    return allLose;
  }

  /**
   * @return the matchTot
   */
  public int getMatchTot()
  {
    return matchTot;
  }

  /**
   * @return the matchWin
   */
  public int getMatchWin()
  {
    return matchWin;
  }

  /**
   * @return the matchDraw
   */
  public int getMatchDraw()
  {
    return matchDraw;
  }

  /**
   * @return the matchLose
   */
  public int getMatchLose()
  {
    return matchLose;
  }

  /**
   * @return the challangeTot
   */
  public int getChallangeTot()
  {
    return challangeTot;
  }

  /**
   * @return the challangeWin
   */
  public int getChallangeWin()
  {
    return challangeWin;
  }

  /**
   * @return the challangeDraw
   */
  public int getChallangeDraw()
  {
    return challangeDraw;
  }

  /**
   * @return the challangeLose
   */
  public int getChallangeLose()
  {
    return challangeLose;
  }

  /**
   * @return the matchStored
   */
  public int getMatchStored()
  {
    return matchStored;
  }

  /**
   * @return the matchOrganized
   */
  public int getMatchOrganized()
  {
    return matchOrganized;
  }

  /**
   * @return the challangeStored
   */
  public int getChallangeStored()
  {
    return challangeStored;
  }

  /**
   * @return the challangeOrganized
   */
  public int getChallangeOrganized()
  {
    return challangeOrganized;
  }

  /**
   * @return the allPlayed
   */
  public int getAllPlayed()
  {
    return allPlayed;
  }

  /**
   * @return the pictureCard
   */
  public PictureCard getPictureCard()
  {
    return pictureCard;
  }

  public String getUserPasswordMD5()
  {
    return MD5.getHashString(this.getUser().getPassword());
  }

  public String getUserDetailsUrl()
  {
    //javascript:%20openPopupUserDetails('/GoKickWebSite/userDetails.action;jsessionid=041AF0AA8D530D8DDF8FE69DBCB9CF60?idUser=165&tab=scheda','Giordano%20Geroldi%20At_Newmedia');
    //                                    /GoKickWebSite/userDetails.action%3FidUser%3D167&tab=scheda','Cigarbox At_Hotmail');
    try
    {
      return String.format("%s/backOfficeLogin.action?id=%s&key=%s&goToUrl=%s",
              AppContext.getInstance().getSiteUrl(),
              this.getUser().getId(),
              this.getUserPasswordMD5(),
              WebUtil.encode( "userDetails.action?idUser=" + this.getUser().getId() + "&tab=scheda","UTF-8")
              );
    }
    catch (UnsupportedEncodingException unsupportedEncodingException)
    {
      return "Error creating UserDetailsUrl";
    }
  }
  
  /**
   * @return the allOrganized
   */
  public int getAllOrganized()
  {
    return allOrganized;
  }


  // </editor-fold>
}
