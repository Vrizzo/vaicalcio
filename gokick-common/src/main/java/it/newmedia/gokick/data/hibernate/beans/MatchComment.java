package it.newmedia.gokick.data.hibernate.beans;


/**
 *
 * Classe che rappresenta l'oggetto MatchComment che fa riferimento alla tabella MATCH_COMMENTS.
 *
 * @hibernate.class
 * table="MATCH_COMMENTS"
 */
public class MatchComment extends AExtendedCreatedBean
{
  private User user;

  private Match match;

  private String textDetail;

  /**
   * @hibernate.id
   * column="ID_MATCH_COMMENT"
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
   * @hibernate.many-to-one
   * column="ID_MATCH"
   * cascade="none"
   * not-null="true"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.Match"
   */
  public Match getMatch()
  {
    return match;
  }

  public void setMatch(Match match)
  {
    this.match = match;
  }

  /**
   * @hibernate.property
   * column="TEXT_DETAIL"
   */
  public String getTextDetail()
  {
    return textDetail;
  }

  public void setTextDetail(String textDetail)
  {
    this.textDetail = textDetail;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_USER"
   * cascade="none"
   * not-null="true"
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
    
}
