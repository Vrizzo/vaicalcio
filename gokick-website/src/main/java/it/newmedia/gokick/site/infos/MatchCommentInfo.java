package it.newmedia.gokick.site.infos;

import it.newmedia.gokick.data.hibernate.beans.MatchComment;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.DateManager;
import it.newmedia.utils.DateUtil;
import java.io.Serializable;
import java.util.Date;

/**
 * Classe che gestisce le informazioni relative ai commenti-partita quando queste devono essere visualizzate all'interno dell'applicazione
 * @author ggeroldi
 */
public class MatchCommentInfo implements Serializable
{
  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >

  private int idMatchComment;

  private int idMatch;

  private int idUserOwner;

  private int idUserOwnerMatch;

  private String date;

  private String text;

  private UserInfo userInfo;

  private Date dateTime;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters/Setters --"  >
  /**
   *
   * @return
   */
  public String getDate()
  {
    return date;
  }

  /**
   *
   * @param date
   */
  public void setDate(String date)
  {
    this.date = date;
  }

  /**
   *
   * @return
   */
  public String getText()
  {
    return text;
  }

  /**
   *
   * @param text
   */
  public void setText(String text)
  {
    this.text = text;
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
  public int getIdUserOwner()
  {
    return idUserOwner;
  }

  /**
   *
   * @param idUserOwner
   */
  public void setIdUserOwner(int idUserOwner)
  {
    this.idUserOwner = idUserOwner;
  }

  /**
   *
   * @return
   */
  public int getIdUserOwnerMatch()
  {
    return idUserOwnerMatch;
  }

  /**
   *
   * @param idUserOwnerMatch
   */
  public void setIdUserOwnerMatch(int idUserOwnerMatch)
  {
    this.idUserOwnerMatch = idUserOwnerMatch;
  }

  /**
   *
   * @return
   */
  public int getIdMatchComment()
  {
    return idMatchComment;
  }

  /**
   *
   * @param idMatchComment
   */
  public void setIdMatchComment(int idMatchComment)
  {
    this.idMatchComment = idMatchComment;
  }

  /**
   *
   * @return
   */
  public int getIdMatch()
  {
    return idMatch;
  }

  /**
   *
   * @param idMatch
   */
  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  /**
   * @return the dateTime
   */
  public Date getDateTime()
  {
    return dateTime;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructor --"  >
  /**
   *
   */
  public MatchCommentInfo()
  {
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  /**
   * valorizza l'oggetto MatchCommentInfo
   * @param matchComment
   */
  public void load(MatchComment matchComment)
  {
    this.date = DateManager.showDate(matchComment.getCreated(), DateManager.FORMAT_DATE_18);
    this.dateTime = matchComment.getCreated();
    this.text = matchComment.getTextDetail();
    this.idMatchComment = matchComment.getId();
    this.idMatch = matchComment.getMatch().getId();
    this.idUserOwner = matchComment.getUser().getId();
    this.idUserOwnerMatch = matchComment.getMatch().getUserOwner().getId();
  }

  /**
   *
   * @return la data di creazione commento nel formato specificato all'interno del metodo
   */
  public String getCreated()
  {
      return DateManager.showDate(new Date(), DateManager.FORMAT_DATE_18, UserContext.getInstance().getLanguage(), UserContext.getInstance().getCurrentCobrand());
  }
  
  
  // </editor-fold>
}
