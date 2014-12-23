package it.newmedia.gokick.site.managers.scheduler;


import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.data.hibernate.beans.Match;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.managers.EmailManager;
import it.newmedia.gokick.site.managers.MatchManager;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WakeUpUserManager implements Runnable
{
  private static final Logger logger = Logger.getLogger(PlayMorePartnerNotifyManager.class);




  public void run()
  {
    HibernateSessionHolder.sessionPerRequestBegin();

    List<User> usersToWakeUp = findUserToWakeUp();

    for (User user : usersToWakeUp)
    {
      Set<Match> matchListToPlay = findBestFiveMatchToPlay(user);

      if (matchListToPlay.isEmpty())
      {
        logger.info("no match to play for user " + user.getId());
        continue;
      }
      Cobrand currentCobrand = InfoProvider.getCobrandByCode(user.getCobrandCode());
      tryToSendEmail(currentCobrand, user, matchListToPlay);
      try
      {
        Thread.sleep(4000);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }

    HibernateSessionHolder.sessionPerRequestCommit();
  }

  private void tryToSendEmail(Cobrand currentCobrand, User user, Set<Match> match)
  {
    try
    {
      logger.info("tryToSendEmail to " + user.getEmail() + " for " + match.size() + "match to play");
      EmailManager.sendWakeUpMail(user, currentCobrand, match);

      User toUpdate = UserManager.getById(user.getId());
      toUpdate.setPrevWakeUpEmailSent(new Date());
      DAOFactory.getInstance().getUserDAO().makePersistent(toUpdate);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private Set<Match> findBestFiveMatchToPlay(User user)
  {
    Set<Match> matchList = new HashSet<Match>();
    matchList.addAll(MatchManager.findMatchToPlayByLatestSportCenterUsed(user));
    matchList.addAll(MatchManager.findMatchToPlayByCity(user.getCity()));
    return matchList;
  }

  private List<User> findUserToWakeUp()
  {
    return UserManager.findUserToWakeUp(10);
  }


}
