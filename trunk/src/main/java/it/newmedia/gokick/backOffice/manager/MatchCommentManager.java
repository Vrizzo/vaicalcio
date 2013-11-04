package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import org.apache.log4j.Logger;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni per gestire i commenti alle partite.
 */
public class MatchCommentManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(MatchCommentManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private MatchCommentManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public static int countMatchComments(int idMatch)
  {
    try
    {
      return DAOFactory.getInstance().getMatchCommentDAO().countByIdMatch(idMatch);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

 

  // </editor-fold>
}
