package it.newmedia.gokick.data.hibernate.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * Classe che rappresenta l'oggetto STATISTIC che fa riferimento alla tabella STATISTICS.
 *
 * @hibernate.class
 * table="STATISTICS"
 */
public class Statistic extends ABean
{
  private Date refDate;
  private User user;
  private Integer allMissing;
  private Integer goalsTot;
  private Integer ownGoalsTot;
  private Integer matchTot;
  private Integer challangeTot;
  private Integer matchOwner;
  private Integer matchRecorded;
  private Integer challangeOwner;
  private Integer challangeRecorded;
  private BigDecimal voteTot;
  private Integer voteNum;
  private Integer matchWin;
  private Integer matchDraw;
  private Integer matchLose;
  private Integer challangeWin;
  private Integer challangeDraw;
  private Integer challangeLose;
  
  /**
   * @hibernate.id
   * column="ID_STATISTIC"
   * generator-class="native"
   * unsaved-value="null"
   */
  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * @hibernate.property
   * column="REF_DATE"
   */
  public Date getRefDate()
  {
    return refDate;
  }

  public void setRefDate(Date refDate)
  {
    this.refDate = refDate;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_USER"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.User"
   */
  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  /**
   * @hibernate.property
   * column="ALL_MISSING"
   */
  public Integer getAllMissing()
  {
    return allMissing;
  }

  public void setAllMissing(Integer allMissing)
  {
    this.allMissing = allMissing;
  }

  /**
   * @hibernate.property
   * column="GOALS_TOT"
   */
  public Integer getGoalsTot()
  {
    return goalsTot;
  }

  public void setGoalsTot(Integer goalsTot)
  {
    this.goalsTot = goalsTot;
  }

  /**
   * @hibernate.property
   * column="OWN_GOALS_TOT"
   */
  public Integer getOwnGoalsTot()
  {
    return ownGoalsTot;
  }

  public void setOwnGoalsTot(Integer ownGoalsTot)
  {
    this.ownGoalsTot = ownGoalsTot;
  }

  /**
   * @hibernate.property
   * column="MATCH_TOT"
   */
  public Integer getMatchTot()
  {
    return matchTot;
  }

  public void setMatchTot(Integer matchTot)
  {
    this.matchTot = matchTot;
  }

  /**
   * @hibernate.property
   * column="CHALLANGE_TOT"
   */
  public Integer getChallangeTot()
  {
    return challangeTot;
  }

  public void setChallangeTot(Integer challangeTot)
  {
    this.challangeTot = challangeTot;
  }

  /**
   * @hibernate.property
   * column="MATCH_OWNER"
   */
  public Integer getMatchOwner()
  {
    return matchOwner;
  }

  public void setMatchOwner(Integer matchOwner)
  {
    this.matchOwner = matchOwner;
  }

  /**
   * @hibernate.property
   * column="MATCH_RECORDED"
   */
  public Integer getMatchRecorded()
  {
    return matchRecorded;
  }

  public void setMatchRecorded(Integer matchRecorded)
  {
    this.matchRecorded = matchRecorded;
  }

  /**
   * @hibernate.property
   * column="CHALLANGE_OWNER"
   */
  public Integer getChallangeOwner()
  {
    return challangeOwner;
  }

  public void setChallangeOwner(Integer challangeOwner)
  {
    this.challangeOwner = challangeOwner;
  }

  /**
   * @hibernate.property
   * column="CHALLANGE_RECORDED"
   */
  public Integer getChallangeRecorded()
  {
    return challangeRecorded;
  }

  public void setChallangeRecorded(Integer challangeRecorded)
  {
    this.challangeRecorded = challangeRecorded;
  }

  /**
   * @hibernate.property
   * column="VOTE_TOT"
   */
  public BigDecimal getVoteTot()
  {
    return voteTot;
  }

  public void setVoteTot(BigDecimal voteTot)
  {
    this.voteTot = voteTot;
  }

  /**
   * @hibernate.property
   * column="VOTE_NUM"
   */
  public Integer getVoteNum()
  {
    return voteNum;
  }

  public void setVoteNum(Integer voteNum)
  {
    this.voteNum = voteNum;
  }

  /**
   * @hibernate.property
   * column="CHALLANGE_DRAW"
   */
  public Integer getChallangeDraw()
  {
    return challangeDraw;
  }

  public void setChallangeDraw(Integer challangeDraw)
  {
    this.challangeDraw = challangeDraw;
  }

  /**
   * @hibernate.property
   * column="CHALLANGE_LOSE"
   */
  public Integer getChallangeLose()
  {
    return challangeLose;
  }

  public void setChallangeLose(Integer challangeLose)
  {
    this.challangeLose = challangeLose;
  }

  /**
   * @hibernate.property
   * column="CHALLANGE_WIN"
   */
  public Integer getChallangeWin()
  {
    return challangeWin;
  }

  public void setChallangeWin(Integer challangeWin)
  {
    this.challangeWin = challangeWin;
  }

  /**
   * @hibernate.property
   * column="MATCH_DRAW"
   */
  public Integer getMatchDraw()
  {
    return matchDraw;
  }

  public void setMatchDraw(Integer matchDraw)
  {
    this.matchDraw = matchDraw;
  }

  /**
   * @hibernate.property
   * column="MATCH_LOSE"
   */
  public Integer getMatchLose()
  {
    return matchLose;
  }

  public void setMatchLose(Integer matchLose)
  {
    this.matchLose = matchLose;
  }

  /**
   * @hibernate.property
   * column="MATCH_WIN"
   */
  public Integer getMatchWin()
  {
    return matchWin;
  }

  public void setMatchWin(Integer matchWin)
  {
    this.matchWin = matchWin;
  }

}

