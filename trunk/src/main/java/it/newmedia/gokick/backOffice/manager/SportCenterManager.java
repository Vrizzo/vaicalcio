package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.hibernate.beans.MatchType;
import it.newmedia.gokick.data.hibernate.beans.PitchCover;
import it.newmedia.gokick.data.hibernate.beans.PitchType;
import it.newmedia.gokick.data.hibernate.beans.SportCenter;
import it.newmedia.gokick.backOffice.Constants;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;


/**
 *
 * Classe manager che gestisce ad alto livello le azioni fatte dall'utente.
 */
public class SportCenterManager {


  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(SportCenterManager.class);

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private SportCenterManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  public static List<SportCenter> getAll(int idCountry, int idProvince, int idCity)
  {
    try
    {
      return DAOFactory.getInstance().getSportCenterDAO().getAll(idCountry, idProvince, idCity);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<SportCenter>();
    }
  }

  public static List<MatchType> getAllMatchType()
  {
    List<MatchType> matchTypeList=new ArrayList<MatchType>();
    try
    {
      matchTypeList = DAOFactory.getInstance().getMatchTypeDAO().getAll();
      Iterator<MatchType> matchTypeIt = matchTypeList.iterator();
      while (matchTypeIt.hasNext())
      {
        MatchType matchType = matchTypeIt.next();
        if (matchType.getId() < 5 || matchType.getId()==10 )
                matchTypeIt.remove();
      }

      return matchTypeList;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static List<PitchType> getAllPitchType()
  {
    List<PitchType> pitchTypeList=new ArrayList<PitchType>();
    try
    {
      pitchTypeList = DAOFactory.getInstance().getPitchTypeDAO().findAll();

      return pitchTypeList;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static List<PitchCover> getAllPitchCover()
  {
    List<PitchCover> pitchCoverList=new ArrayList<PitchCover>();
    try
    {
      pitchCoverList = DAOFactory.getInstance().getPitchCoverDAO().findAll();

      return pitchCoverList;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static List<SportCenter> getAllSportCenters(int idCountryFilter)
  {
    List<SportCenter> sportCenterList=new ArrayList<SportCenter>();
    try
    {
      sportCenterList = DAOFactory.getInstance().getSportCenterDAO().getAllFetchUser(idCountryFilter);
      return sportCenterList;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static List<SportCenter> getAllByParameters( Integer idCountry,
                                                      Integer idProvince,
                                                      Integer idCity,
                                                      String name,
                                                      int idMatchtype,
                                                      int idPitchCover,
                                                      String status,
                                                      boolean justConventioned)
  {
    List<SportCenter> sportCenterList=new ArrayList<SportCenter>();
    boolean searchStatus=false;
    boolean approved=false;
    boolean enabled=false;
    
    if (StringUtils.isNotEmpty(status) && !status.equals(Constants.SPORTCENTER_STATUS_TUTTI))
    {
        searchStatus=true;
        if (status.equals(Constants.SPORTCENTER_STATUS_CANCELLATO))
            approved=true;

        if (status.equals(Constants.SPORTCENTER_STATUS_ONLINE))
        {
            approved=true;
            enabled=true;
        }
    }
    
    
    try
    {
      sportCenterList = DAOFactory.getInstance().getSportCenterDAO().getAllByParameters(idCountry, idProvince, idCity, name, idMatchtype, idPitchCover, approved, enabled, searchStatus, justConventioned);
      return sportCenterList;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }



  public static int getCountAll(int idCountryFilter)
  {
    int count=0;
    try
    {
      count = DAOFactory.getInstance().getSportCenterDAO().getCountAll(idCountryFilter);
      return count;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
  }

  public static int getCountAllByStatus(String status,int idCountryFilter)
  {
    int count=0;
    boolean approved=false;
    boolean enabled=false;
    try
    {
      if (status.equals(Constants.SPORTCENTER_STATUS_CANCELLATO))
        approved=true;
      if (status.equals(Constants.SPORTCENTER_STATUS_ONLINE))
      {
        approved=true;
        enabled=true;
      }
      count = DAOFactory.getInstance().getSportCenterDAO().getCountAllbyStatus(approved, enabled,idCountryFilter);
      return count;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
  }

  public static int getCountAllConvetioned(int idCountryFilter)
  {
    int count=0;
    try
    {
      count = DAOFactory.getInstance().getSportCenterDAO().getCountAllConvetioned(idCountryFilter);
      return count;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return 0;
    }
  }

  public static SportCenter get(int idSportCenter)
  {
    SportCenter sportCenter=new SportCenter();
    try
    {
      sportCenter = DAOFactory.getInstance().getSportCenterDAO().get(idSportCenter);
      return sportCenter;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static boolean update(SportCenter sportCenterModified)
  {
    SportCenter sportCentertoUpdate=new SportCenter();
    try
    {

      sportCentertoUpdate = DAOFactory.getInstance().getSportCenterDAO().get(sportCenterModified.getId());

      sportCentertoUpdate.setName(sportCenterModified.getName());
      sportCentertoUpdate.setAddress(sportCenterModified.getAddress());
      sportCentertoUpdate.setApproved(sportCenterModified.getApproved());
      sportCentertoUpdate.setEmail(sportCenterModified.getEmail());
      sportCentertoUpdate.setLatitude(sportCenterModified.getLatitude());      
      sportCentertoUpdate.setLongitude(sportCenterModified.getLongitude());
      sportCentertoUpdate.setEnabled(sportCenterModified.getEnabled());
      sportCentertoUpdate.setTelephone(sportCenterModified.getTelephone());
      sportCentertoUpdate.setWebSite(sportCenterModified.getWebSite());
      
      //if (sportCenterModified.getConventionFrom()!=null)
        sportCentertoUpdate.setConventionFrom(sportCenterModified.getConventionFrom());
      //if (sportCenterModified.getConventionTo()!=null)
        sportCentertoUpdate.setConventionTo(sportCenterModified.getConventionTo());


      if (sportCenterModified.getCountry()!=null)
        sportCentertoUpdate.setCountry(sportCenterModified.getCountry());
      if (sportCenterModified.getProvince()!=null)
        sportCentertoUpdate.setProvince(sportCenterModified.getProvince());
      if (sportCenterModified.getCity()!=null)
        sportCentertoUpdate.setCity(sportCenterModified.getCity());


      return true;
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return false;
    }
  }

  public static List<Object[]> getAllProjection(int idCountry, int idProvince, int idCity)
  {
    try
    {
      return DAOFactory.getInstance().getSportCenterDAO().getAllProjection(idCountry, idProvince, idCity);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ArrayList<Object[]>();
    }
  }

  public static List<SportCenter> getByIdSportCenterList(List<Integer> idSportCenterList)
  {
    try
    {
      return  DAOFactory.getInstance().getSportCenterDAO().getByIdSportCenterList(idSportCenterList);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }


 
  // </editor-fold>
}
