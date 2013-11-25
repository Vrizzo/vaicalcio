package it.newmedia.gokick.data.hibernate.beans;

/**
 *
 * Classe che rappresenta l'oggetto MatchType che fa riferimento alla tabella MATCH_TYPES.
 *
 * @hibernate.class
 * table="MATCH_TYPES"
 * mutable="false"
 * @hibernate.cache
 * usage="read-only"
 */
public class MatchType extends ALabelKeyBean
{

  private Integer totTeamPlayer;

  /**
   * @hibernate.id
   * column="ID_MATCH_TYPE"
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
   * column="TOT_TEAM_PLAYERS"
   */
  public Integer getTotTeamPlayer()
  {
    return totTeamPlayer;
  }

  public void setTotTeamPlayer(Integer totTeamPlayer)
  {
    this.totTeamPlayer = totTeamPlayer;
  }
}
