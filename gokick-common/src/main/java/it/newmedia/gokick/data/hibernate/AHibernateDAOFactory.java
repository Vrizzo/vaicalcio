package it.newmedia.gokick.data.hibernate;

import it.newmedia.gokick.data.hibernate.dao.*;
import org.hibernate.Session;

/**
 *
 * Classe astratta che estesa da DAOFactory e deve contenere tutti i metodi che restitiranno i gestori per ogni tipo di oggetto presente sul DB.
 */
public abstract class AHibernateDAOFactory
{

  public PlayerDAO getPlayerDAO()
  {
    return (PlayerDAO) instantiateDAO(PlayerDAO.class);
  }

  public MatchDAO getMatchDAO()
  {
    return (MatchDAO) instantiateDAO(MatchDAO.class);
  }

  public EmailConfigurationDAO getEmailConfigurationDAO()
  {
    return (EmailConfigurationDAO) instantiateDAO(EmailConfigurationDAO.class);
  }

  public UserDAO getUserDAO()
  {
    return (UserDAO) instantiateDAO(UserDAO.class);
  }

  public CountryDAO getCountryDAO()
  {
    return (CountryDAO) instantiateDAO(CountryDAO.class);
  }

  public ProvinceDAO getProvinceDAO()
  {
    return (ProvinceDAO) instantiateDAO(ProvinceDAO.class);
  }

  public CityDAO getCityDAO()
  {
    return (CityDAO) instantiateDAO(CityDAO.class);
  }

  public TranslationDAO getTranslationDAO()
  {
    return (TranslationDAO) instantiateDAO(TranslationDAO.class);
  }

  public SiteConfigurationDAO getSiteConfigurationDAO()
  {
    return (SiteConfigurationDAO) instantiateDAO(SiteConfigurationDAO.class);
  }

  public PictureCardDAO getPictureCardDAO()
  {
    return (PictureCardDAO) instantiateDAO(PictureCardDAO.class);
  }

  public SquadDAO getSquadDAO()
  {
    return (SquadDAO) instantiateDAO(SquadDAO.class);
  }

  public UserLastViewCommentDAO getUserLastViewCommentDAO()
  {
    return (UserLastViewCommentDAO) instantiateDAO(UserLastViewCommentDAO.class);
  }

  public MatchCommentDAO getMatchCommentDAO()
  {
    return (MatchCommentDAO) instantiateDAO(MatchCommentDAO.class);
  }

  public UserSquadDAO getUserSquadDAO()
  {
    return (UserSquadDAO) instantiateDAO(UserSquadDAO.class);
  }

  public UserInvitationDAO getUserInvitationDAO()
  {
    return (UserInvitationDAO) instantiateDAO(UserInvitationDAO.class);
  }

  public PlayerRoleDAO getPlayerRoleDAO()
  {
    return (PlayerRoleDAO) instantiateDAO(PlayerRoleDAO.class);
  }

  public AbuseReasonDAO getAbuseReasonDAO()
  {
    return (AbuseReasonDAO) instantiateDAO(AbuseReasonDAO.class);
  }

  public PlayerFootDAO getPlayerFootDAO()
  {
    return (PlayerFootDAO) instantiateDAO(PlayerFootDAO.class);
  }

  public StatisticDAO getStatisticDAO()
  {
    return (StatisticDAO) instantiateDAO(StatisticDAO.class);
  }

  public LanguageDAO getLanguageDAO()
  {
    return (LanguageDAO) instantiateDAO(LanguageDAO.class);
  }

  public MessageDAO getMessageDAO()
  {
    return (MessageDAO) instantiateDAO(MessageDAO.class);
  }

  public MatchTypeDAO getMatchTypeDAO()
  {
    return (MatchTypeDAO) instantiateDAO(MatchTypeDAO.class);
  }

  public UserCommentDAO getUserCommentDAO()
  {
    return (UserCommentDAO) instantiateDAO(UserCommentDAO.class);
  }

  public SportCenterDAO getSportCenterDAO()
  {
    return (SportCenterDAO) instantiateDAO(SportCenterDAO.class);
  }

  public PitchCoverDAO getPitchCoverDAO()
  {
    return (PitchCoverDAO) instantiateDAO(PitchCoverDAO.class);
  }

  public PitchTypeDAO getPitchTypeDAO()
  {
    return (PitchTypeDAO) instantiateDAO(PitchTypeDAO.class);
  }

  public FootballTeamDAO getFootballTeamDAO()
  {
    return (FootballTeamDAO) instantiateDAO(FootballTeamDAO.class);
  }

  public UsersPermissionDAO getUsersPermissionDAO()
  {
    return (UsersPermissionDAO) instantiateDAO(UsersPermissionDAO.class);
  }

  public VPlayerMatchChallangeArchivedDAO getVPlayerMatchChallangeArchivedDAO()
  {
    return (VPlayerMatchChallangeArchivedDAO) instantiateDAO(VPlayerMatchChallangeArchivedDAO.class);
  }

  public SpecialInvitationDAO getSpecialInvitationDAO()
  {
    return (SpecialInvitationDAO) instantiateDAO(SpecialInvitationDAO.class);
  }

  private AGenericDAO instantiateDAO(Class daoClass)
  {
    try
    {
      AGenericDAO dao = (AGenericDAO) daoClass.getConstructor(Session.class).newInstance(getCurrentSession());
      return dao;
    }
    catch (Exception ex)
    {
      throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
    }
  }

  public UsersMessageDAO getUsersMessageDAO()
  {
    return (UsersMessageDAO) instantiateDAO(UsersMessageDAO.class);
  }

  public CobrandDAO getCobrandDAO()
  {
    return (CobrandDAO) instantiateDAO(CobrandDAO.class);
  }

  public AuditDAO getAuditDAO()
  {
    return (AuditDAO) instantiateDAO(AuditDAO.class);
  }

  protected abstract Session getCurrentSession();

  public VPlayMorePartnerDAO getVPlayMorePartnerDAO()
  {
    return (VPlayMorePartnerDAO) instantiateDAO(VPlayMorePartnerDAO.class);
  }
}

