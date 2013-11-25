package it.newmedia.gokick.data.hibernate.beans;

import it.newmedia.gokick.data.enums.EnumPlayerStatus;
import it.newmedia.gokick.data.enums.EnumPlayerType;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Classe che rappresenta l'oggetto Player che fa riferimento alla tabella PLAYERS.
 *
 * @hibernate.class
 * table="PLAYERS"
 * mutable="true"
 * @hibernate.cache
 * usage="read-write"
 */
public class Player extends ABean
{

  public static final Comparator<Player> POSITION_ORDER =
          new Comparator<Player>()
          {

            public int compare(Player o1, Player o2)
            {
              if (o1.getPosition() < o2.getPosition())
                return -1;
              if (o1.getPosition() == o2.getPosition())
                return 0;
              return 1;
            }

          };

  public static final Comparator<Player> TEAM_POSITION_ORDER =
          new Comparator<Player>()
          {

            public int compare(Player o1, Player o2)
            {
              if (o1.getEnumPlayerType() == o2.getEnumPlayerType())
              {
                if (o1.getPosition() < o2.getPosition())
                  return -1;
                if (o1.getPosition() == o2.getPosition())
                  return 0;
                return 1;
              }
              else
              {
                switch(o1.getEnumPlayerType())
                {
                  case Missing:
                    return 1;
                  case TeamOne:
                    return -1;
                  case TeamTwo:
                  default:
                    if( o2.getEnumPlayerType() == EnumPlayerType.TeamOne )
                      return 1;
                    else
                      return -1;
                }
              }
            }
          };

  private String playerStatus;

  private Date requestDate;

  private Date cancellationDate;

  private boolean reporterEnabled;

  private Integer goals;

  private Integer ownGoals;

  private String playerType;

  private int position;

  private String outFirstName;

  private String outLastName;

  private String outReference;

  private PlayerRole playerRole;

  private User user;

  private Set<Match> matchList = new HashSet<Match>();

  private String mobile;

  private BigDecimal vote = new BigDecimal(-1);

  private String review;

  /**
   * @hibernate.id
   * column="ID_PLAYER"
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

  public EnumPlayerStatus getEnumPlayerStatus()
  {
    if (this.playerStatus == null)
    {
      return EnumPlayerStatus.Undefined;
    }
    return EnumPlayerStatus.getEnum(this.playerStatus);
  }

  public void setEnumPlayerStatus(EnumPlayerStatus enumPlayerStatus)
  {
    this.playerStatus = enumPlayerStatus.getValue();
  }

  /**
   * @hibernate.property
   * column="REQUEST_DATE"
   */
  public Date getRequestDate()
  {
    return requestDate;
  }

  public void setRequestDate(Date requestDate)
  {
    this.requestDate = requestDate;
  }

  /**
   * @hibernate.property
   * column="CANCELLATION_DATE"
   */
  public Date getCancellationDate()
  {
    return cancellationDate;
  }

  public void setCancellationDate(Date cancellationDate)
  {
    this.cancellationDate = cancellationDate;
  }

  /**
   * @hibernate.property
   * column="REPORTER_ENABLED"
   */
  public boolean getReporterEnabled()
  {
    return reporterEnabled;
  }

  public void setReporterEnabled(boolean reporterEnabled)
  {
    this.reporterEnabled = reporterEnabled;
  }

  /**
   * @hibernate.property
   * column="GOALS"
   */
  public Integer getGoals()
  {
    return goals;
  }

  public void setGoals(Integer goals)
  {
    this.goals = goals;
  }

  /**
   * @hibernate.property
   * column="OWN_GOALS"
   */
  public Integer getOwnGoals()
  {
    return ownGoals;
  }

  public void setOwnGoals(Integer ownGoals)
  {
    this.ownGoals = ownGoals;
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

  public EnumPlayerType getEnumPlayerType()
  {
    if (this.playerType == null)
    {
      return EnumPlayerType.Missing;
    }
    return EnumPlayerType.getEnum(this.playerType);
  }

  public void setEnumPlayerType(EnumPlayerType enumPlayerType)
  {
    this.playerType = enumPlayerType.getValue();
  }

  /**
   * @hibernate.property
   * column="POSITION"
   */
  public int getPosition()
  {
    return position;
  }

  public void setPosition(int position)
  {
    this.position = position;
  }

  /**
   * @hibernate.property
   * column="OUT_FIRST_NAME"
   */
  public String getOutFirstName()
  {
    return outFirstName;
  }

  public void setOutFirstName(String outFirstName)
  {
    this.outFirstName = outFirstName;
  }

  /**
   * @hibernate.property
   * column="OUT_LAST_NAME"
   */
  public String getOutLastName()
  {
    return outLastName;
  }

  public void setOutLastName(String outLastName)
  {
    this.outLastName = outLastName;
  }

  /**
   * @hibernate.property
   * column="OUT_REFERENCE"
   */
  public String getOutReference()
  {
    return outReference;
  }

  public void setOutReference(String outReference)
  {
    this.outReference = outReference;
  }

  /**
   *
   * @return
   * @hibernate.set
   * table="PLAYERS_TO_MATCHES"
   * cascade="none"
   * inverse="true"
   * lazy="true"
   * fetch="join"
   *
   * @hibernate.key
   * column="ID_PLAYER"
   *
   * @hibernate.many-to-many
   * class="it.newmedia.gokick.data.hibernate.beans.Match"
   * column="ID_MATCH"
   * outer-join="true"
   */
  public Set<Match> getMatchList()
  {
    return matchList;
  }

  public void setMatchList(Set<Match> matchList)
  {
    this.matchList = matchList;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_PLAYER_ROLE"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
   * class="it.newmedia.gokick.data.hibernate.beans.PlayerRole"
   */
  public PlayerRole getPlayerRole()
  {
    return playerRole;
  }

  public void setPlayerRole(PlayerRole playerRole)
  {
    this.playerRole = playerRole;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_USER"
   * cascade="none"
   * not-null="false"
   * lazy="proxy"
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
   * column="MOBILE"
   */
  public String getMobile()
  {
    return mobile;
  }

  /**
   * @param mobile the mobile to set
   */
  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }

  /**
   * @hibernate.property
   * column="REVIEW"
   */
  public String getReview()
  {
    return review;
  }

  public void setReview(String review)
  {
    this.review = review;
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

  @Override
  public String toString()
  {
    return String.format("id %s,Type: %s, Status: %s, Pos: %s", this.id, this.getEnumPlayerType(), this.getEnumPlayerStatus(), this.getPosition());
  }

  public Player()
  {
    this.setGoals(0);
    this.setOwnGoals(0);
  }


}
