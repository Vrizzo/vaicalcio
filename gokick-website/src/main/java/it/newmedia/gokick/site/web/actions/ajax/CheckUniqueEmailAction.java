package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.web.actions.ABaseActionSupport;

/**
 *Classe contenente le azioni per controllare l'esistenza di una mail a db in fase di registrazione
 *
 */
public class CheckUniqueEmailAction extends ABaseActionSupport
{

  //private static Logger logger = Logger.getLogger(CheckUniqueEmailAction.class);
  private Integer idUser;
  private String email;
  private Boolean unique;
    

  @Override
  public String execute()
  {
    try
    {
       this.unique = !DAOFactory.getInstance().getUserDAO().checkExistingEmail(this.email, this.getIdUser());
    }
    catch (Exception e)
    {
      logger.error(e, e);
    }
    return SUCCESS;
  }

  // <editor-fold defaultstate="collapsed" desc="-- GETTERS/SETTERS --">
  public Integer getIdUser()
  {
    return idUser;
  }

  public void setIdUser(Integer idUser)
  {
    this.idUser = idUser;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
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
