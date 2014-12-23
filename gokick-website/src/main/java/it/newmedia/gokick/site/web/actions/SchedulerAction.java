package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.site.managers.scheduler.MatchNotifyManager;
import it.newmedia.gokick.site.managers.scheduler.PlayMorePartnerNotifyManager;
import it.newmedia.gokick.site.managers.scheduler.WakeUpUserManager;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione per la gestione della gestione schedulata dell'invio mail di notifica,
 * chiamata da un batch sul server in modo periodico.l'azione fa partire un nuovo thread
 *
 */
public class SchedulerAction extends ABaseActionSupport
{
  private static Logger logger = Logger.getLogger(SchedulerAction.class);

  @Override
  public String execute()
  {
    return SUCCESS;
  }

  public String sendMatchNotifyMail()
  {
    MatchNotifyManager matchNotifyManager = new MatchNotifyManager();
    Thread tm=new Thread(matchNotifyManager);
    tm.start();
    return SUCCESS;
  }

  public String sendPlayMorePartnerNotifyMail()
  {
    PlayMorePartnerNotifyManager playMorePartnerNotifyManager = new PlayMorePartnerNotifyManager();
    Thread tm=new Thread(playMorePartnerNotifyManager);
    tm.start();
    return SUCCESS;
  }

  public String wakeUpUserNotifyMail()
  {
    WakeUpUserManager wakeUpUserManager = new WakeUpUserManager();
    wakeUpUserManager.run();
//    Thread tm = new Thread(wakeUpUserManager);
//    tm.start();
    return SUCCESS;
  }

}