package it.newmedia.gokick.site.infos;

import it.newmedia.gokick.data.hibernate.beans.Statistic;
import it.newmedia.utils.MathUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce le informazioni relative alle statistiche di un utente quando queste devono essere visualizzate all'interno dell'applicazione
 */
public class StatisticInfo implements Serializable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  /**
   *
   */
  public static final Comparator<StatisticInfo> NAME_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return o1.userInfo.getName().compareToIgnoreCase(o2.userInfo.getName());
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> NO_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return 0;
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> CITY_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return o1.userInfo.getCity().compareToIgnoreCase(o2.userInfo.getCity());
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> REGISTRATION_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          //return o1.userInfo.getCreated().compareTo(o2.userInfo.getCreated());
          return    o1.userInfo.getId() > o2.userInfo.getId() ?  1 :
                    o1.userInfo.getId() < o2.userInfo.getId() ? -1 : 0 ;
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> NATCOUNTRY_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return o1.userInfo.getNatCountry().compareToIgnoreCase(o2.userInfo.getNatCountry());
        }
      };


      /**
       *
       */
      public static final Comparator<StatisticInfo> ROLE_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return    o1.userInfo.getIdPlayerRole() > o2.userInfo.getIdPlayerRole() ? 1 :
                    o1.userInfo.getIdPlayerRole() < o2.userInfo.getIdPlayerRole()? -1 : 0 ;
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> LASTNAME_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return    o1.userInfo.getSurname().compareToIgnoreCase(o2.userInfo.getSurname());
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> AGE_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          int o1Age=Integer.parseInt(o1.userInfo.getAge().equals("")? "0" : o1.userInfo.getAge());
          int o2Age=Integer.parseInt(o2.userInfo.getAge().equals("")? "0" : o2.userInfo.getAge());
          return    o1Age > o2Age ? 1 :
                    o1Age < o2Age ? -1 : 0 ;
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> CONDITION_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return    Integer.parseInt(o1.userInfo.getIdPhysicalCondition()) > Integer.parseInt(o2.userInfo.getIdPhysicalCondition()) ? 1 :
                    Integer.parseInt(o1.userInfo.getIdPhysicalCondition()) < Integer.parseInt(o2.userInfo.getIdPhysicalCondition())? -1 : 0 ;
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> PLAYED_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return    o1.getAllTot() > o2.getAllTot() ? 1 :
                    o1.getAllTot() < o2.getAllTot() ? -1 : 0 ;
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> MONTHFREQUENCY_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return    o1.getMonthPlayed() > o2.getMonthPlayed() ? 1 :
                    o1.getMonthPlayed() < o2.getMonthPlayed() ? -1 : 0 ;
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> GOAL_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return    o1.getGoalsTot() > o2.getGoalsTot() ? 1 :
                    o1.getGoalsTot() < o2.getGoalsTot() ? -1 : 0 ;
        }
      };
      
      /**
       *
       */
      public static final Comparator<StatisticInfo> AVGOAL_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return    o1.getAverageGoal().compareTo(o2.getAverageGoal());
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> AVVOTE_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return    o1.getAverageVote().compareTo(o2.getAverageVote());
        }
      };

      /**
       *
       */
      public static final Comparator<StatisticInfo> RELIABILITY_ORDER =
      new Comparator<StatisticInfo>()
      {
        public int compare(StatisticInfo o1, StatisticInfo o2)
        {
          return    o1.getReliability().compareTo(o2.getReliability());
        }
      };
      
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static final Logger logger = Logger.getLogger(StatisticInfo.class);
  /**
   * 
   */
  public static final Comparator<StatisticInfo> ORDER_NAME = new Comparator<StatisticInfo>()
  {
    @Override
    public int compare(StatisticInfo statisticInfo1, StatisticInfo statisticInfo2)
    {
      int name = statisticInfo1.getUserInfo().getName().compareTo(statisticInfo2.getUserInfo().getName());
      return (name == 0) ? statisticInfo1.getUserInfo().getSurname().compareTo(statisticInfo2.getUserInfo().getSurname()) : name;
    }
  };
  private boolean valid;
  private boolean empty;
  private String year;
  private int allTot;
  private BigDecimal reliability;
  private int monthPlayed;
  private int allWin;
  private int allDraw;
  private int allLose;
  private int goalsTot;
  private int ownGoalsTot;
  private BigDecimal averageGoal;
  private BigDecimal averageVote;
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
  private UserInfo userInfo;


  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   *
   */
  public StatisticInfo()
  {
    this.valid = true;
    this.empty = true;
    this.year = "";
    //this.reliability = BigDecimal.ZERO;
    this.reliability = BigDecimal.valueOf(100); //user che non ha statistiche parte da 100%
    this.averageGoal = BigDecimal.ZERO;
    this.averageVote = BigDecimal.ZERO;
  }

  /**
   *
   * @param statisticList
   */
  public StatisticInfo(List<Statistic> statisticList)
  {
    BigDecimal voteTot = new BigDecimal(0);
    int allMissing = 0;
    int voteNum = 0;

    this.valid = true;
    this.empty = false;

    for (Statistic statistic : statisticList)
    {
      allMissing += statistic.getAllMissing();
      this.goalsTot += statistic.getGoalsTot();
      this.ownGoalsTot += statistic.getOwnGoalsTot();
      voteNum += statistic.getVoteNum();
      voteTot = voteTot.add(statistic.getVoteTot());
      this.matchTot += statistic.getMatchTot();
      this.matchWin += statistic.getMatchWin();
      this.matchDraw += statistic.getMatchDraw();
      this.matchLose += statistic.getMatchLose();
      this.challangeTot += statistic.getChallangeTot();
      this.challangeWin += statistic.getChallangeWin();
      this.challangeDraw += statistic.getChallangeDraw();
      this.challangeLose += statistic.getChallangeLose();
      this.matchStored += statistic.getMatchRecorded();
      this.matchOrganized += statistic.getMatchOwner();
      this.challangeStored += statistic.getChallangeRecorded();
      this.challangeOrganized += statistic.getChallangeOwner();
    }

    if (statisticList.size() > 0)
    {
      Date refDate = statisticList.get(0).getRefDate();
      SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
      this.year = simpleDateformat.format(refDate);
    }
    this.allTot = this.matchTot + this.challangeTot;
    this.monthPlayed = this.allTot / statisticList.size();
    this.allWin = this.matchWin + this.challangeWin;
    this.allDraw = this.matchDraw + this.challangeDraw;
    this.allLose = this.matchLose + this.challangeLose;
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
    tmp = new BigDecimal(this.goalsTot);
    if (this.allTot != 0)
    {
      this.averageGoal = MathUtil.divide(tmp, this.allTot, RoundingMode.HALF_UP, 1);
    } else
    {
      this.averageGoal = BigDecimal.ZERO;
    }
    if (voteNum != 0)
    {
      this.averageVote = MathUtil.divide(voteTot, voteNum, RoundingMode.HALF_UP, 1);
    }
    else
    {
      this.averageVote =  BigDecimal.ZERO;
    }
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  /**
   *
   * @return
   */
  public String getYear()
  {
    return year;
  }

  /**
   *
   * @param year
   */
  public void setYear(String year)
  {
    this.year = year;
  }

  /**
   *
   * @return
   */
  public int getAllTot()
  {
    return allTot;
  }

  /**
   *
   * @param allTot
   */
  public void setAllTot(int allTot)
  {
    this.allTot = allTot;
  }

  /**
   *
   * @return
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
   *
   * @param reliability
   */
  public void setReliability(BigDecimal reliability)
  {
    this.reliability = reliability;
  }

  /**
   *
   * @return
   */
  public int getMonthPlayed()
  {
    return monthPlayed;
  }

  /**
   *
   * @param monthPlayed
   */
  public void setMonthPlayed(int monthPlayed)
  {
    this.monthPlayed = monthPlayed;
  }

  /**
   *
   * @return
   */
  public int getAllWin()
  {
    return allWin;
  }

  /**
   *
   * @param allWin
   */
  public void setAllWin(int allWin)
  {
    this.allWin = allWin;
  }

  /**
   *
   * @return
   */
  public int getAllDraw()
  {
    return allDraw;
  }

  /**
   *
   * @param allDraw
   */
  public void setAllDraw(int allDraw)
  {
    this.allDraw = allDraw;
  }

  /**
   *
   * @return
   */
  public int getAllLose()
  {
    return allLose;
  }

  /**
   *
   * @param allLose
   */
  public void setAllLose(int allLose)
  {
    this.allLose = allLose;
  }

  /**
   *
   * @return
   */
  public int getGoalsTot()
  {
    return goalsTot;
  }

  /**
   *
   * @param goalsTot
   */
  public void setGoalsTot(int goalsTot)
  {
    this.goalsTot = goalsTot;
  }

  /**
   *
   * @return
   */
  public int getOwnGoalsTot()
  {
    return ownGoalsTot;
  }

  /**
   *
   * @param ownGoalsTot
   */
  public void setOwnGoalsTot(int ownGoalsTot)
  {
    this.ownGoalsTot = ownGoalsTot;
  }

  /**
   *
   * @return
   */
  public BigDecimal getAverageGoal()
  {
    try
    {
      BigDecimal avgGoal = averageGoal.setScale(1, RoundingMode.UP);

      if (avgGoal.compareTo(BigDecimal.ZERO)==0)
        return  averageGoal.setScale(0, RoundingMode.UP);

      int bdToInt = avgGoal.intValue();
      if (bdToInt==0) bdToInt=1;
      BigDecimal res = MathUtil.divide(avgGoal, bdToInt,RoundingMode.UP,1);

      if (res.compareTo(BigDecimal.ONE)==0)
        return averageGoal.setScale(0, RoundingMode.UP);
      if (res.compareTo(BigDecimal.ZERO)==1)
        return averageGoal.setScale(1, RoundingMode.UP);
      else
        return averageGoal.setScale(0, RoundingMode.UP);

      //return averageGoal.setScale(0, RoundingMode.UP);
    }
    catch (Exception e)
    {
      logger.error("Error Scale number AverageGoal in StatisticInfo.", e);
    }
    return BigDecimal.ZERO;
  }

  /**
   *
   * @param averageGoal
   */
  public void setAverageGoal(BigDecimal averageGoal)
  {
    this.averageGoal = averageGoal;
  }

  /**
   *
   * @return
   */
  public BigDecimal getAverageVote()
  {
    try
    {
      BigDecimal avgVote = averageVote.setScale(1, RoundingMode.UP);

      if (avgVote.compareTo(BigDecimal.ZERO)==0)
        return  averageVote.setScale(0, RoundingMode.UP);

      int bdToInt = avgVote.intValue();
      if (bdToInt==0)
        bdToInt=1;
      
      BigDecimal res = MathUtil.divide(avgVote, bdToInt , RoundingMode.UP,1);
      if (res.compareTo(BigDecimal.ONE)==0)
        return averageVote.setScale(0, RoundingMode.UP);
      if (res.compareTo(BigDecimal.ZERO)==1)
        return averageVote.setScale(1, RoundingMode.UP);
      else
        return averageVote.setScale(0, RoundingMode.UP);
    }
    catch (Exception e)
    {
      logger.error("Error Scale number AverageVote in StatisticInfo.", e);
    }
    return BigDecimal.ZERO;
  }

  /**
   *
   * @param averageVote
   */
  public void setAverageVote(BigDecimal averageVote)
  {
    this.averageVote = averageVote;
  }

  /**
   *
   * @return
   */
  public int getMatchTot()
  {
    return matchTot;
  }

  /**
   *
   * @param matchTot
   */
  public void setMatchTot(int matchTot)
  {
    this.matchTot = matchTot;
  }

  /**
   *
   * @return
   */
  public int getMatchWin()
  {
    return matchWin;
  }

  /**
   *
   * @param matchWin
   */
  public void setMatchWin(int matchWin)
  {
    this.matchWin = matchWin;
  }

  /**
   *
   * @return
   */
  public int getMatchDraw()
  {
    return matchDraw;
  }

  /**
   *
   * @param matchDraw
   */
  public void setMatchDraw(int matchDraw)
  {
    this.matchDraw = matchDraw;
  }

  /**
   *
   * @return
   */
  public int getMatchLose()
  {
    return matchLose;
  }

  /**
   *
   * @param matchLose
   */
  public void setMatchLose(int matchLose)
  {
    this.matchLose = matchLose;
  }

  /**
   *
   * @return
   */
  public int getChallangeTot()
  {
    return challangeTot;
  }

  /**
   *
   * @param challangeTot
   */
  public void setChallangeTot(int challangeTot)
  {
    this.challangeTot = challangeTot;
  }

  /**
   *
   * @return
   */
  public int getChallangeWin()
  {
    return challangeWin;
  }

  /**
   *
   * @param challangeWin
   */
  public void setChallangeWin(int challangeWin)
  {
    this.challangeWin = challangeWin;
  }

  /**
   *
   * @return
   */
  public int getChallangeDraw()
  {
    return challangeDraw;
  }

  /**
   *
   * @param challangeDraw
   */
  public void setChallangeDraw(int challangeDraw)
  {
    this.challangeDraw = challangeDraw;
  }

  /**
   *
   * @return
   */
  public int getChallangeLose()
  {
    return challangeLose;
  }

  /**
   *
   * @param challangeLose
   */
  public void setChallangeLose(int challangeLose)
  {
    this.challangeLose = challangeLose;
  }

  /**
   *
   * @return
   */
  public int getMatchStored()
  {
    return matchStored;
  }

  /**
   *
   * @param matchStored
   */
  public void setMatchStored(int matchStored)
  {
    this.matchStored = matchStored;
  }

  /**
   *
   * @return
   */
  public int getMatchOrganized()
  {
    return matchOrganized;
  }

  /**
   *
   * @param matchOrganized
   */
  public void setMatchOrganized(int matchOrganized)
  {
    this.matchOrganized = matchOrganized;
  }

  /**
   *
   * @return
   */
  public int getChallangeStored()
  {
    return challangeStored;
  }

  /**
   *
   * @param challangeStored
   */
  public void setChallangeStored(int challangeStored)
  {
    this.challangeStored = challangeStored;
  }

  /**
   *
   * @return
   */
  public int getChallangeOrganized()
  {
    return challangeOrganized;
  }

  /**
   *
   * @param challangeOrganized
   */
  public void setChallangeOrganized(int challangeOrganized)
  {
    this.challangeOrganized = challangeOrganized;
  }

  /**
   *
   * @return
   */
  public boolean isValid()
  {
    return valid;
  }

  /**
   *
   * @param valid
   */
  public void setValid(boolean valid)
  {
    this.valid = valid;
  }

  /**
   *
   * @return
   */
  public UserInfo getUserInfo()
  {
    return userInfo;
  }

  /**
   *
   * @param userInfo
   */
  public void setUserInfo(UserInfo userInfo)
  {
    this.userInfo = userInfo;
  }

  /**
   *
   * @return
   */
  public boolean isEmpty()
  {
    return empty;
  }

  /**
   *
   * @param empty
   */
  public void setEmpty(boolean empty)
  {
    this.empty = empty;
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  // </editor-fold>
}
