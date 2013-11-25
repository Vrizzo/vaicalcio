package it.newmedia.gokick.data.hibernate.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * Classe che rappresenta l'oggetto V_PLAYER_MATCH_CHALLANGE_ARCHIVED che fa riferimento alla vista V_PLAYER_MATCH_CHALLANGE_ARCHIVED.
 *
 * @hibernate.class
 * table="V_PLAYER_MATCH_CHALLENGE_ARCHIVED"
 */
public class VPlayerMatchChallangeArchived
{

  private int idPlayer;
  private Integer idUser;
  private String playerStatus;
  private String playerType;
  private int goals;
  private int ownGoals;
  private BigDecimal vote;
  private boolean isMatch;
  private boolean isChallange;
  private int idGame;
  private int idUserOwner;
  private Date gameStart;
  private int gameGoalsOne;
  private int gameGoalsTwo;


  /**
   * @hibernate.id
   * column="ID_PLAYER"
   * generator-class="native"
   * unsaved-value="null"
   */
  public int getIdPlayer()
  {
    return idPlayer;
  }

  public void setIdPlayer(int idPlayer)
  {
    this.idPlayer = idPlayer;
  }
    
  /**
   * @hibernate.property
   * column="IS_CHALLANGE"
   */
  public boolean isIsChallange()
  {
    return isChallange;
  }

  public void setIsChallange(boolean isChallange)
  {
    this.isChallange = isChallange;
  }

  /**
   * @hibernate.property
   * column="IS_MATCH"
   */
  public boolean isIsMatch()
  {
    return isMatch;
  }

  public void setIsMatch(boolean isMatch)
  {
    this.isMatch = isMatch;
  }

  /**
   * @hibernate.property
   * column="GAME_GOALS_ONE"
   */
  public int getGameGoalsOne()
  {
    return gameGoalsOne;
  }

  public void setGameGoalsOne(int gameGoalsOne)
  {
    this.gameGoalsOne = gameGoalsOne;
  }

  /**
   * @hibernate.property
   * column="GAME_GOALS_TWO"
   */
  public int getGameGoalsTwo()
  {
    return gameGoalsTwo;
  }

  public void setGameGoalsTwo(int gameGoalsTwo)
  {
    this.gameGoalsTwo = gameGoalsTwo;
  }

  /**
   * @hibernate.property
   * column="GAME_START"
   */
  public Date getGameStart()
  {
    return gameStart;
  }

  public void setGameStart(Date gameStart)
  {
    this.gameStart = gameStart;
  }

  /**
   * @hibernate.property
   * column="GOALS"
   */
  public int getGoals()
  {
    return goals;
  }

  public void setGoals(int goals)
  {
    this.goals = goals;
  }

  /**
   * @hibernate.property
   * column="ID_GAME"
   */
  public int getIdGame()
  {
    return idGame;
  }

  public void setIdGame(int idGame)
  {
    this.idGame = idGame;
  }

  /**
   * @hibernate.property
   * column="ID_USER"
   */
  public Integer getIdUser()
  {
    return idUser;
  }

  public void setIdUser(Integer idUser)
  {
    this.idUser = idUser;
  }

  /**
   * @hibernate.property
   * column="ID_USER_OWNER"
   */
  public int getIdUserOwner()
  {
    return idUserOwner;
  }

  public void setIdUserOwner(int idUserOwner)
  {
    this.idUserOwner = idUserOwner;
  }

  /**
   * @hibernate.property
   * column="OWN_GOALS"
   */
  public int getOwnGoals()
  {
    return ownGoals;
  }

  public void setOwnGoals(int ownGoals)
  {
    this.ownGoals = ownGoals;
  }

  /**
   * @hibernate.property
   * column="PLAYER_STATUS"
   */
  public String getPlayerStatus()
  {
    return playerStatus;
  }

  public void setPlayerStatus(String playerStatus)
  {
    this.playerStatus = playerStatus;
  }

  /**
   * @hibernate.property
   * column="PLAYER_TYPE"
   */
  public String getPlayerType()
  {
    return playerType;
  }

  public void setPlayerType(String playerType)
  {
    this.playerType = playerType;
  }

  /**
   * @hibernate.property
   * column="VOTE"
   */
  public BigDecimal getVote()
  {
    return vote;
  }

  public void setVote(BigDecimal vote)
  {
    this.vote = vote;
  }

}
