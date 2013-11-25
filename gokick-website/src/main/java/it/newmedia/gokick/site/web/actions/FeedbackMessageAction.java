package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.data.hibernate.beans.UserComment;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.managers.MessageManager;
import it.newmedia.gokick.site.managers.UserCommentManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.util.Date;
import it.newmedia.gokick.site.managers.SecurityManager;

/**
 *
 * Classe contenente le azioni per l'inserimento dei feedback e dei commenti privati verso un utente.
 */
public class FeedbackMessageAction extends AuthenticationBaseAction
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idUser;
  private int idSelectFairplay;
  private int idSelectReliability;
  private int idSelectTech;
  private String textDetail;
  private String privateMessage;
  private String tab;
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">

  @Override
  public String execute()
  {   
    return SUCCESS;
  }

  public String insertFeedback()
  {
    //search UserComment if exist

    UserComment userComment;
    userComment = UserCommentManager.find(this.idUser, UserContext.getInstance().getUser().getId());
    if (userComment==null)
    {
      userComment = new UserComment();
      User userAuthor=new User();
      userComment.setUserAuthor(userAuthor);
      userComment.getUserAuthor().setId(UserContext.getInstance().getUser().getId());
      userComment.setIdUser(this.idUser);
    }
      
    userComment.setFairplay(this.idSelectFairplay);
    userComment.setReliability(this.idSelectReliability);
    userComment.setTech(this.idSelectTech);
    userComment.setTextDetail(this.textDetail);
    userComment.setApproved(false);
    userComment.setCreated(new Date());

    //save
    if (SecurityManager.canAddFeedback(this.idUser))
    {
      //save
      UserCommentManager.save(userComment,idUser);
      
    }   
    return Constants.STRUTS_RESULT_NAME__INSERTED;
  }

  public String insertPrivateMessage()
  {
    //search UserComment if exist
    MessageManager.savePrivateMessage(InfoProvider.getUserInfo(idUser).getId(),getPrivateMessage());
    return Constants.STRUTS_RESULT_NAME__INSERTED;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  /**
   * @return the idUser
   */
  public int getIdUser()
  {
    return idUser;
  }

  /**
   * @param idUser the idUser to set
   */
  public void setIdUser(int idUser)
  {
    this.idUser = idUser;
  }

  /**
   * @return the idSelectFairplay
   */
  public int getIdSelectFairplay()
  {
    return idSelectFairplay;
  }

  /**
   * @param idSelectFairplay the idSelectFairplay to set
   */
  public void setIdSelectFairplay(int idSelectFairplay)
  {
    this.idSelectFairplay = idSelectFairplay;
  }

  /**
   * @return the idSelectReliability
   */
  public int getIdSelectReliability()
  {
    return idSelectReliability;
  }

  /**
   * @param idSelectReliability the idSelectReliability to set
   */
  public void setIdSelectReliability(int idSelectReliability)
  {
    this.idSelectReliability = idSelectReliability;
  }

  /**
   * @return the idSelectTech
   */
  public int getIdSelectTech()
  {
    return idSelectTech;
  }

  /**
   * @param idSelectTech the idSelectTech to set
   */
  public void setIdSelectTech(int idSelectTech)
  {
    this.idSelectTech = idSelectTech;
  }

  /**
   * @return the textDetail
   */
  public String getTextDetail()
  {
    return textDetail;
  }

  /**
   * @param textDetail the textDetail to set
   */
  public void setTextDetail(String textDetail)
  {
    this.textDetail = textDetail;
  }
  
  /**
   * @return the privateMessage
   */
  public String getPrivateMessage()
  {
    return privateMessage;
  }

  /**
   * @param privateMessage the privateMessage to set
   */
  public void setPrivateMessage(String privateMessage)
  {
    this.privateMessage = privateMessage;
  }

  /**
   * @return the tab
   */
  public String getTab()
  {
    return tab;
  }

  /**
   * @param tab the tab to set
   */
  public void setTab(String tab)
  {
    this.tab = tab;
  }


  // </editor-fold>
}
