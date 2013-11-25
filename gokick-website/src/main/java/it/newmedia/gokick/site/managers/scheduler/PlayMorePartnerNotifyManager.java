package it.newmedia.gokick.site.managers.scheduler;

import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.data.hibernate.beans.VPlayMorePartner;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.managers.EmailManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che definisce il thread per la gestione dell'invio mail di alert e reminder per gli associati di PlayMore
 * @author ggeroldi
 */
public class PlayMorePartnerNotifyManager implements Runnable
{
  private static final Logger logger = Logger.getLogger(PlayMorePartnerNotifyManager.class);

  long[] distanceForEmail = new long[]{30, 15, 7, 3, 0, -7, -15, -28};

  /**
   * Classe che controlla l'avvio di un thread che si occupa di mandare mail in modo specifico all'apertura delle iscrizioni partite
   */
  public PlayMorePartnerNotifyManager()
  {
  }

  public void run()
  {
    StringBuilder sb = new StringBuilder(1000);
    try
    {
      sb.append(SystemUtils.LINE_SEPARATOR).append("Inizio invio reminder play more..");
      List<VPlayMorePartner> allVPlayMorePartner = new ArrayList<VPlayMorePartner>();
      List<User> allUser = new ArrayList<User>();
      HashMap<String, Cobrand> allCobrand = new HashMap<String, Cobrand>();
      HibernateSessionHolder.sessionPerRequestBegin();
      try
      {
        List<Integer> idList = new ArrayList<Integer>();

        for (VPlayMorePartner vPlayMorePartner : DAOFactory.getInstance().getVPlayMorePartnerDAO().findAll())
        {
          sb.append(SystemUtils.LINE_SEPARATOR).append("Verifico: ").append(vPlayMorePartner);
          if( ArrayUtils.contains(distanceForEmail, vPlayMorePartner.getDistance()) && (vPlayMorePartner.isSuperstar() || vPlayMorePartner.isSupporter()))
          {
            allVPlayMorePartner.add(vPlayMorePartner);
            idList.add(vPlayMorePartner.getId());
            sb.append(SystemUtils.LINE_SEPARATOR).append("  Devo inviare!");
          }
        }
        if(idList.size() > 0)
        {
          allUser = DAOFactory.getInstance().getUserDAO().getByIdUserList(idList);
        }
        for (User user : allUser)
        {
          if( !allCobrand.containsKey(user.getCobrandCode()))
          {
            allCobrand.put(user.getCobrandCode(), InfoProvider.getCobrandByCode(user.getCobrandCode()));
          }
        }
      }
      catch (Exception e)
      {
        logger.error(e, e);
      }

      for (VPlayMorePartner vPlayMorePartner : allVPlayMorePartner)
      {
        User currentUser = (User)CollectionUtils.find(allUser, new BeanPropertyValueEqualsPredicate("id", vPlayMorePartner.getId()));
        Cobrand currentCobrand = allCobrand.get(currentUser.getCobrandCode());
        EmailManager.sendPlayMorePartnerNotify(currentUser, currentCobrand, vPlayMorePartner.getDistance() );
      }

      HibernateSessionHolder.sessionPerRequestCommit();
    }
    catch (Exception e)
    {
      logger.error("Invio playmore reminder", e);
      sb.append(SystemUtils.LINE_SEPARATOR).append("ERRORE: ").append(e.getMessage());
    }
    finally
    {
      sb.append(SystemUtils.LINE_SEPARATOR).append("Fine reminder play more..");
      logger.info(sb);
    }

  }





}



