package it.newmedia.gokick.site.web.actions.user;

import it.newmedia.gokick.site.web.actions.*;
import it.newmedia.gokick.site.UserContext;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione relativa al logout per dell'utente.ne cancella il contesto(userContext)
 */
public class UserLogoutAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserLogoutAction.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    UserContext.getInstance().reset();
    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  // </editor-fold>
}
