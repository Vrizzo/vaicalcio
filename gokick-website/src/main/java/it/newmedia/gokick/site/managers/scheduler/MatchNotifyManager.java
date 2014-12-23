package it.newmedia.gokick.site.managers.scheduler;

import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.managers.EmailManager;
import it.newmedia.gokick.site.managers.MatchManager;
import org.apache.log4j.Logger;

/**
 * Classe che definisce il thread per la gestione dell'invio mail di notifica
 * @author ggeroldi
 */
public class MatchNotifyManager implements Runnable
{

  private static final Logger logger = Logger.getLogger(MatchNotifyManager.class);

  //<editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   * Classe che controlla l'avvio di un thread che si occupa di mandare mail in modo specifico all'apertura delle iscrizioni partite
   */
  public MatchNotifyManager()
  {
  }

  public void run()
  {
    HibernateSessionHolder.sessionPerRequestBegin();
    EmailManager.sendMatchRegistrationOpenEmail(MatchManager.getMatchToNotify(), null, null);
    HibernateSessionHolder.sessionPerRequestCommit();
  }





}//end Class



