package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;
import org.apache.log4j.Logger;

/**
 *
 *Classe contenente le azioni per controllare l'esistenza di un nickName (del forum) a db in fase di registrazione
 */
public class CheckUniqueForumNicknameAction extends ABaseActionSupport
{
  //private static Logger logger = Logger.getLogger(CheckUniqueForumNicknameAction.class);
  private Integer idUser;
  private String forumNickname;
  private Boolean unique;
    
  @Override
  public String execute()
  {
    try
    {
       this.unique = !DAOFactory.getInstance().getUserDAO().checkExistingForumNickname(this.forumNickname, this.idUser);
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
    return SUCCESS;
  }

  // <editor-fold defaultstate="collapsed" desc="-- GETTRES/SETTERS --">
  public Integer getIdUser()
  {
    return idUser;
  }

  public void setIdUser(Integer idUser)
  {
    this.idUser = idUser;
  }

  public String getForumNickname()
  {
    return forumNickname;
  }

  public void setForumNickname(String forumNickname)
  {
    this.forumNickname = forumNickname;
  }

  public Boolean getUnique()
  {
    return unique;
  }

  public void setUnique(Boolean unique)
  {
    this.unique = unique;
  }

  // </editor-fold>

  
}
