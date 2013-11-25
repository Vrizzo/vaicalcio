package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.cache.InfoCache;
import it.newmedia.gokick.data.enums.EnumMessage;
import it.newmedia.gokick.data.enums.EnumUserSquadStatus;
import it.newmedia.gokick.data.hibernate.beans.*;
import it.newmedia.gokick.data.hibernate.dao.SquadDAO;
import it.newmedia.gokick.data.hibernate.dao.UserSquadDAO;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.hibernate.HibernateSessionHolder;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.results.Result;
import it.newmedia.utils.DateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni fatte sulle rose.
 */
public class SquadManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(SquadManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private SquadManager()
  {
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   * controlla e logga le associazioni sbagliata tra utenti e squadre di cui fanno parte
   */
  public static void checkUserSquad()
  {
    try
    {
      List<Squad> squadList = DAOFactory.getInstance().getSquadDAO().findAll();
      for (Squad squad : squadList)
      {

        for (UserSquad userSquad : squad.getUserSquadList())
        {
          if (userSquad.getOwner() || (!userSquad.getEnumUserSquadStatus().equals(EnumUserSquadStatus.Confirmed)))
          {
            continue;
          }


          int idSquad = userSquad.getUser().getFirstSquad().getId();

          logger.info("USERSQUAD where id Squad :" + idSquad + " and idUser : " + squad.getUser().getId() + "");

          int idUserSquad = DAOFactory.getInstance().getUserSquadDAO().getByIdSquadAndIdUserSquad(idSquad, squad.getUser().getId()); //controllo se c'è associazione
          if (idUserSquad <= 0)
          {
            logger.info("----------------------------" + idUserSquad + "-----------------------------------------------");
            logger.info("@@@@@@@@@ User " + squad.getUser().getId()
                    + " e " + userSquad.getUser().getId()
                    + " non hanno relazione corretta");
            logger.info("@@@@@@@@@ manca " + squad.getUser().getId()
                    + " in idSquad " + idSquad);
            logger.info("---------------------------------------------------------------------------");

          }

        }
      }
    }
    catch (Exception ex)
    {
      logger.error("error in checkFriendShip", ex);
    }

  }

  /**
   * controlla la relazione di amicizia tra 2 utenti
   * @param idOwnerUser
   * @param idCurrentUser
   * @return "true" se relazione positiva (sono amici)
   */
  public static boolean isFriendOf(int idOwnerUser, int idCurrentUser)
  {
    try
    {
      //TO-DO fare una sola query
      int idSquad = getUserFirstSquad(idOwnerUser).getId();     //tiro fuori idSquadDelproprietario
      int idUserSquad = DAOFactory.getInstance().getUserSquadDAO().getByIdSquadAndIdUserSquad(idSquad, idCurrentUser); //controllo se c'è associazione
      if (idUserSquad > 0)
      {
        return true;
      }
    }
    catch (Exception ex)
    {
      //logger.debug("error in SquadManager.isFriendOf");
      return false;
    }
    return false;
  }

  /**
   *
   * @param idCurrentUser id utente corrente
   * @param idUserToRelate id utente di cui controllare la relazione con quello corrente
   * @param idSquadCurrentUser id squadra utente corrente
   * @param idSquadUserToRelate id squadra utente di cui controllare la relazione con quello corrente
   * @return il valore dell'enum EnumUserSquadStatus che definisce la relazione, in caso di non relazione torna "" (stringa vuota)
   */
  public static String getUserRelationship(int idCurrentUser, int idUserToRelate, int idSquadCurrentUser, int idSquadUserToRelate)
  {
    try
    {
      UserSquad userSquad = DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(idUserToRelate, idSquadCurrentUser);
      if (userSquad != null)
      {
        return userSquad.getEnumUserSquadStatus().getValue();
      }
      else
      {
        userSquad = DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(idCurrentUser, idSquadUserToRelate);
        if (userSquad != null)
        {
          return userSquad.getEnumUserSquadStatus().getValue();
        }
      }
    }
    catch (Exception ex)
    {
      return "";
    }
    return "";
  }

  /**
   *
   * @param idUser id utente
   * @return la squadra dell'utente specificato
   */
  public static Squad getUserFirstSquad(int idUser)
  {
    Squad squad;

    try
    {
      squad = DAOFactory.getInstance().getSquadDAO().getFirstByIdUser(idUser);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }

    return squad;
  }

  /**
   * salva una squadra
   * @param squad squadra da salvare
   * @return "true" in caso positivo
   */
  public static boolean save(Squad squad)
  {
    Date txDurationTime = new Date();
    Session session = HibernateSessionHolder.beginTransaction();
    try
    {
      UserSquad userSquad = null;
      boolean isInsert = false;

      SquadDAO squadDAO = DAOFactory.getInstance().getSquadDAO();
      UserSquadDAO userSquadDAO = DAOFactory.getInstance().getUserSquadDAO();

      if (squad.getId() == null || squad.getId() < 0)
      {
        isInsert = true;
      }
      squadDAO.makePersistent(squad);

      if (isInsert)
      {
        userSquad = new UserSquad();
        userSquad.setUser(squad.getUser());
        userSquad.setSquad(squad);
        userSquad.setEnumUserSquadStatus(EnumUserSquadStatus.Confirmed);
        userSquad.setOwner(true);
        userSquadDAO.makePersistent(userSquad);
      }

      String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__SQUAD_INFO, squad.getId());
      InfoCache.removeFromCache(keyOnCache);
      UserContext.getInstance().resetFirstSquad();

      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return false;
    }

    return true;
  }

  /**
   * invita utente ad una squadra, manda messaggio in bacheca e via mail se richiesto
   * @param idUserToInvite id user da invitare
   * @param invitingUser id user convocante
   * @param language
   * @return User invitato, null in caso di errore
   */
  public static User inviteUser(int idUserToInvite, User invitingUser, Language language, Cobrand currentCobrand)
  {
    User userToInvite = null;
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    try
    {
      Squad squad = UserContext.getInstance().getFirstSquad();

      if (squad == null)
      {
        logger.error("Found null squad in squadMAnager.inviteUser");
        throw new Exception("Found null squad in squadMAnager.inviteUser");
      }

      userToInvite = DAOFactory.getInstance().getUserDAO().get(idUserToInvite);
      if (userToInvite == null)
      {
        logger.error("Found null userToInvite in squadMAnager.inviteUser");
        throw new Exception("Found null squad in squadMAnager.inviteUser");
      }

      UserSquad userSquad = DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(idUserToInvite, squad.getId());
      if (userSquad != null)
      {
        logger.info("user : " + idUserToInvite + " già associato a squadra :" + squad.getId());
        return userToInvite;
      }

      //Invite user
      userSquad = new UserSquad();
      userSquad.setOwner(false);
      userSquad.setSquad(squad);
      userSquad.setUser(userToInvite);
      userSquad.setEnumUserSquadStatus(EnumUserSquadStatus.Invited);
      DAOFactory.getInstance().getUserSquadDAO().makePersistent(userSquad);

      //Save message
      boolean success = MessageManager.saveSquadMessage(EnumMessage.SquadInviteReceived.getValue(),
              invitingUser, userToInvite, language, currentCobrand);
      if (!success)
      {
        logger.error("error sendin invitinToSquad message in squadManager line:260");
      }

      //Send email
      if (userToInvite.getAlertOnSquadRequest())
      {
        Result<Boolean> rEmailSend = EmailManager.sendInviteNotifyEmail(userToInvite, invitingUser, currentCobrand);
        if (!rEmailSend.isSuccessNotNull() || !rEmailSend.getValue())
        {
          logger.error("error sendin invitinToSquad mail in squadManager line:270");
        }
      }
      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return null;
    }

    return userToInvite;
  }

  /**
   * acceta invito a squadra e manda messaggio in bacheca e mail se richiesto
   * @param idUserRequesting id user richiedente
   * @param currentUser id user corrente
   * @param language
   * @return User utente richiedente
   */
  public static User acceptInvite(int idUserRequesting, User currentUser, Language language, Cobrand currentCobrand)
  {
    User userRequesting = null;
    Date txDurationTime = new Date();
    Session session = HibernateSessionHolder.beginTransaction();
    try
    {

      //Confirm requesting user
      Squad squadRequesting = DAOFactory.getInstance().getSquadDAO().getFirstByIdUser(idUserRequesting);
      if (squadRequesting == null)
      {
        HibernateSessionHolder.rollbackTransaction();
        return null;
      }
      UserSquad userSquadRequesting = DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(currentUser.getId(), squadRequesting.getId());
      if (userSquadRequesting == null)
      {
        HibernateSessionHolder.rollbackTransaction();
        return null;
      }
      userSquadRequesting.setEnumUserSquadStatus(EnumUserSquadStatus.Confirmed);
      DAOFactory.getInstance().getUserSquadDAO().makePersistent(userSquadRequesting);

      //Add current user
      Squad currentUserSquad = DAOFactory.getInstance().getSquadDAO().getFirstByIdUser(currentUser.getId());
      if (currentUserSquad == null)
      {
        HibernateSessionHolder.rollbackTransaction();
        return null;
      }
      userRequesting = DAOFactory.getInstance().getUserDAO().get(idUserRequesting);
      if (userRequesting == null)
      {
        HibernateSessionHolder.rollbackTransaction();
        return null;
      }
      if (DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(userRequesting.getId(), currentUserSquad.getId()) == null)
      {
        UserSquad userSquadCurrentUser = new UserSquad();
        userSquadCurrentUser.setOwner(false);
        userSquadCurrentUser.setSquad(currentUserSquad);
        userSquadCurrentUser.setUser(userRequesting);
        userSquadCurrentUser.setEnumUserSquadStatus(EnumUserSquadStatus.Confirmed);

        DAOFactory.getInstance().getUserSquadDAO().makePersistent(userSquadCurrentUser);
      }

      //Save message
      boolean success = MessageManager.saveSquadMessage(EnumMessage.SquadInviteAccepted.getValue(), userRequesting, currentUser, language, currentCobrand);
      if (!success)
      {
        logger.error("error sendin Accept invite To Squad message in squadManager line:342");
      }

      //manda mail
      if (userRequesting.getAlertOnSquadInsert())
      {
        Result<Boolean> rEmailSend = EmailManager.sendAcceptUserInSquadNotifyEmail(userRequesting, currentUser, currentCobrand);
        if (!rEmailSend.isSuccessNotNull() || !rEmailSend.getValue())
        {
          logger.error("error sendin Accept invite To Squad mail in squadManager line:351");
        }
      }

      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));

      InfoProvider.removeSquadInfo(currentUserSquad.getId());
      InfoProvider.removeSquadInfo(squadRequesting.getId());
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return null;
    }

    return userRequesting;
  }

  /**
   * rifiuta invito ad una squadra e manda messaggio in bacheca
   * @param idUserRequesting id user richiedente
   * @param currentIdUser id utente corrente (che rifiuta)
   * @param language
   * @return user richiedente
   */
  public static User notAcceptInvite(int idUserRequesting, int currentIdUser, Language language, Cobrand currentCobrand)
  {
    User userRequesting = null;
    Date txDurationTime = new Date();
    HibernateSessionHolder.beginTransaction();
    try
    {
      Squad squadRequesting = DAOFactory.getInstance().getSquadDAO().getFirstByIdUser(idUserRequesting);
      if (squadRequesting == null)
      {
        HibernateSessionHolder.rollbackTransaction();
        return null;
      }
      UserSquad userSquadRequesting = DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(currentIdUser, squadRequesting.getId());
      if (userSquadRequesting == null)
      {
        HibernateSessionHolder.rollbackTransaction();
        return null;
      }
      userRequesting = DAOFactory.getInstance().getUserDAO().get(idUserRequesting);
      if (userRequesting == null)
      {
        HibernateSessionHolder.rollbackTransaction();
        return null;
      }

      //Save message
      User currentUser = DAOFactory.getInstance().getUserDAO().get(currentIdUser);
      boolean success = MessageManager.saveSquadMessage(EnumMessage.SquadInviteNotAccepted.getValue(), userRequesting, currentUser, language, currentCobrand);
      if (!success)
      {
        logger.error("error sendin not Accept invite To Squad message in squadManager line:407");
      }

      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));

      DAOFactory.getInstance().getUserSquadDAO().makeTransient(userSquadRequesting, false);
      Squad squadCurrentUser = DAOFactory.getInstance().getSquadDAO().getFirstByIdUser(currentIdUser);
      InfoProvider.removeSquadInfo(squadCurrentUser.getId());
      InfoProvider.removeSquadInfo(squadRequesting.getId());
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return null;
    }

    return userRequesting;
  }

  /**
   * rimuove invito effetuato verso un utente e salva messaggio in bacheca
   * @param idUserInvited id invitato
   * @param currentUser id utente convocante
   * @param language
   * @return user invitato
   */
  public static User removeInvite(int idUserInvited, User currentUser, Language language, Cobrand currentCobrand)
  {

    User userInvited = null;
    Date txDurationTime = new Date();
    Session session = HibernateSessionHolder.beginTransaction();
    try
    {
      Squad squad = UserContext.getInstance().getFirstSquad();
      if (squad == null)
      {
        logger.error("Found null squad in squadMAnager.removeInvite");
        throw new Exception("Found null squad in squadMAnager.inviteUser");
      }

      UserSquad userSquad = DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(idUserInvited, squad.getId());
      if (userSquad == null)
      {
        logger.error("user : " + idUserInvited + " già rimosso da squadra :" + squad.getId());
        userInvited = UserManager.getById(idUserInvited);
        return userInvited;
      }

      userInvited = userSquad.getUser();
      if (userInvited == null)
      {
        logger.error("Found null userInvited in squadMAnager.removeInvite");
        throw new Exception("Found null userInvited in squadMAnager.inviteUser");
      }

      //Save message
      boolean success = MessageManager.saveSquadMessage(EnumMessage.SquadRemoveInvitation.getValue(), currentUser, userInvited, language, currentCobrand);
      if (!success)
      {
        logger.error("error removing invite To Squad message in squadManager line:463");
      }

      DAOFactory.getInstance().getUserSquadDAO().makeTransient(userSquad, false);

      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));

      InfoProvider.removeSquadInfo(squad.getId());

    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return null;
    }

    return userInvited;
  }

  /**
   * rimuove user dagli amici (squadra)
   * @param idUserToRemove id utente da eliminare
   * @param currentUser id utente corrente (proprietario squadra)
   * @param language
   * @return user da rimuovere
   */
  public static User removeUser(int idUserToRemove, User currentUser, Language language, Cobrand currentCobrand)
  {
    User userToRemove;
    Date txDurationTime = new Date();
    Session session = HibernateSessionHolder.beginTransaction();
    try
    {
      //Remove user to remove
      Squad currentSquad = UserContext.getInstance().getFirstSquad();
      if (currentSquad == null)
      {
        logger.error("Found null Squad in squadMAnager.removeUser");
        throw new Exception("Found null Squad in squadMAnager.removeUser");
      }

      UserSquad userSquadCurrent = DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(idUserToRemove, currentSquad.getId());
      if (userSquadCurrent == null)
      {
          logger.info("user : " + idUserToRemove + " già rimosso da squadra :" + currentSquad.getId());
          userToRemove = UserManager.getById(idUserToRemove);
          return userToRemove;
      }

      userToRemove = userSquadCurrent.getUser();
      if (userToRemove == null)
      {
        logger.error("Found null userToRemove in squadMAnager.removeUser");
        throw new Exception("Found null userToRemove in squadMAnager.removeUser");
      }
      DAOFactory.getInstance().getUserSquadDAO().makeTransient(userSquadCurrent, false);

      //Remove user current
      Squad userToRemoveSquad = DAOFactory.getInstance().getSquadDAO().getFirstByIdUser(idUserToRemove);
      if (userToRemoveSquad == null)
      {
        HibernateSessionHolder.rollbackTransaction();
        return null;
      }

      UserSquad userSquadToRemove = DAOFactory.getInstance().getUserSquadDAO().getByIdUserAndIdSquad(currentUser.getId(), userToRemoveSquad.getId());
      if (userSquadToRemove == null)
      {
        HibernateSessionHolder.rollbackTransaction();
        return null;
      }
      DAOFactory.getInstance().getUserSquadDAO().makeTransient(userSquadToRemove, false);

      //Save message
      boolean success = MessageManager.saveSquadMessage(EnumMessage.SquadRemoveUser.getValue(), currentUser, userToRemove, language, currentCobrand);
      if (!success)
      {
        logger.error("error sendin remove user from Squad message in squadManager line:536");
      }
      HibernateSessionHolder.commitTransaction();
      logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));

      String keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__SQUAD_INFO, currentSquad.getId());
      InfoCache.removeFromCache(keyOnCache);

      keyOnCache = String.format(Constants.KEY_ON_CACHE_TEMPLATE_TWO_PARAM, Constants.KEY_ON_CACHE__SQUAD_INFO, userToRemoveSquad.getId());
      InfoCache.removeFromCache(keyOnCache);
    }
    catch (Exception e)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(e, e);
      return null;
    }

    return userToRemove;
  }

  /**
   * invita piu' utenti
   * @param idUserToInviteList lista utenti da invitare
   * @param invitingUser user convocante
   * @param language
   * @return numero utenti invitati
   */
  public static int inviteUserMultiple(List idUserToInviteList, User invitingUser, Language language, Cobrand currentCobrand)
  {
    int count = 0;
    try
    {
      Squad squad = UserContext.getInstance().getFirstSquad();
      if (squad == null)
      {
        return count;
      }

      for (int i = 0; i < idUserToInviteList.size(); i++)
      {
        int idUserToInvite = Integer.parseInt(idUserToInviteList.get(i).toString());
        User userToInvite = DAOFactory.getInstance().getUserDAO().get(idUserToInvite);
        if (userToInvite == null)
        {
          continue;
        }
        Squad squadInvited = SquadManager.getUserFirstSquad(idUserToInvite);
        Date txDurationTime = new Date();
        HibernateSessionHolder.beginTransaction();
        try
        {
          boolean relatedToInviter = DAOFactory.getInstance().getUserSquadDAO().checkUserSquadAlreadyRelated(squad.getId(), idUserToInvite);
          boolean relatedToInvited = DAOFactory.getInstance().getUserSquadDAO().checkUserSquadAlreadyRelated(squadInvited.getId(), invitingUser.getId());
          if (!relatedToInviter && !relatedToInvited)
          {
            UserSquad userSquad = new UserSquad();
            userSquad.setOwner(false);
            userSquad.setSquad(squad);
            userSquad.setUser(userToInvite);
            userSquad.setEnumUserSquadStatus(EnumUserSquadStatus.Invited);
            DAOFactory.getInstance().getUserSquadDAO().makePersistent(userSquad);
            //Save message
            MessageManager.saveSquadMessage(EnumMessage.SquadInviteReceived.getValue(), invitingUser, userToInvite, language, currentCobrand);
          }
          HibernateSessionHolder.commitTransaction();
          logger.info("Tx duration time: " + DateUtil.printElapsedTime(txDurationTime));
        }
        catch(Exception ex)
        {
          HibernateSessionHolder.rollbackTransaction();
          continue;
        }
        count++;

        if (userToInvite.getAlertOnSquadRequest())
        {
          EmailManager.sendInviteNotifyEmail(userToInvite, invitingUser, currentCobrand);
        }
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return count;
    }

    return count;
  }

  /**
   *
   * @param idSquad squadra da controllare
   * @return lista di ID di user  confermati nella squadra indicata
   */
  public static List getAllConfirmedUserByIdSquad(int idSquad)
  {
    return getAllConfirmedUserByIdSquad(idSquad, false);
  }

  /**
   *
   * @param idSquad squadra da controllare
   * @param onMarket se true aggiunge filtro "users sul mercato"
   * @return lista di ID di user  confermati nella squadra indicata
   */
  public static List getAllConfirmedUserByIdSquad(int idSquad, boolean onMarket)
  {
    List friendsIdList = new ArrayList();
    try
    {
      friendsIdList = DAOFactory.getInstance().getUserSquadDAO().getAllConfirmedUserByIdSquad(idSquad, onMarket);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return friendsIdList;
    }
    return friendsIdList;
  }

  /**
   *
   * @param idSquad id squadra
   * @param idUserTorelate id utente di cui indagare l'esitenza di relazione
   * @return lista di id utente relazionati con la squadra indicata
   */
  public static List getAllUserWithRelationByIdSquad(int idSquad, int idUserTorelate)
  {
    List<Integer> friendsIdList = new ArrayList<Integer>();
    try
    {
      List<Object[]> objList = DAOFactory.getInstance().getUserSquadDAO().getAllIdUserWithRelationByIdSquad(idSquad, idUserTorelate);
      for (Object[] obj : objList)
      {
        if (!friendsIdList.contains((Integer) obj[0]))
        {
          friendsIdList.add((Integer) obj[0]);
        }
        if (!friendsIdList.contains((Integer) obj[1]))
        {
          friendsIdList.add((Integer) obj[1]);
        }
      }

    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return friendsIdList;
    }
    return friendsIdList;
  }

  /**
   *
   * @param idUser
   * @return numero delle richieste d'amicizia ricevute
   */
  public static Integer countAllRequestsReceived(int idUser)
  {

    try
    {
      return DAOFactory.getInstance().getUserSquadDAO().countRequestsReceivedByIdUser(idUser);

    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }

  }

  /**
   *
   * @param idUser id utente di cui cercare le richieste d'amicizia pending
   * @return numero delle richieste in stato pending (invited, request)
   */
  public static Integer countPendingRelations(int idUser)
  {
    try
    {
      return DAOFactory.getInstance().getUserSquadDAO().countPendingRelations(idUser);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
  }

  /**
   * numero delle amicizie confermate
   * @param idUser
   * @return
   */
  public static Integer countRelationsConfirmed(int idUser)
  {
    try
    {
      return DAOFactory.getInstance().getUserSquadDAO().countRelationsConfirmed(idUser);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
  }
  // </editor-fold>
}
