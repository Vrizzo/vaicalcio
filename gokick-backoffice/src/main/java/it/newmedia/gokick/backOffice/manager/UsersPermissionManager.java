package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import it.newmedia.gokick.data.hibernate.beans.UsersPermission;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni per gestire i permessi degli utenti relativi al backOffice.
 */
public class UsersPermissionManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UsersPermissionManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private UsersPermissionManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public static List<UsersPermission> getUserPermission(int idUser)
  {
      return DAOFactory.getInstance().getUsersPermissionDAO().get(idUser);
  }

 

  // </editor-fold>
}
