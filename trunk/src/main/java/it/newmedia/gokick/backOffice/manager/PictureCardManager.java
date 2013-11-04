package it.newmedia.gokick.backOffice.manager;

import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.data.hibernate.dao.PictureCardDAO;
import it.newmedia.gokick.backOffice.AppContext;
import it.newmedia.gokick.backOffice.hibernate.DAOFactory;
import it.newmedia.gokick.backOffice.hibernate.HibernateSessionHolder;
import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe manager che gestisce le PictureCard.
 */
public class PictureCardManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(PictureCardManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private PictureCardManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  public static PictureCard getPictureCard(int idPicture)
  {
    try
    {
      return DAOFactory.getInstance().getPictureCardDAO().get(idPicture);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static PictureCard getLastPictureCard(int idUser)
  {
    try
    {
      return DAOFactory.getInstance().getPictureCardDAO().getLastPic(idUser);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static List<PictureCard> getAllByStatus(EnumPictureCardStatus enumPictureCardStatus)
  {
    try
    {
      return DAOFactory.getInstance().getPictureCardDAO().getAllByStatus(enumPictureCardStatus);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static List<PictureCard> getAllByStatusValue(String pictureCardStatus,int idCountryFilter)
  {
    try
    {
      return DAOFactory.getInstance().getPictureCardDAO().getAllByStatusValue(pictureCardStatus,idCountryFilter);
    }
    catch (Exception ex)
    {
      logger.error(ex, ex);
      return null;
    }
  }

  public static int countPictures(int idCountryFilter,EnumPictureCardStatus... pictureStatuses)
  {
    try
    {
      return DAOFactory.getInstance().getPictureCardDAO().getCount(idCountryFilter,pictureStatuses);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return 0;
    }
  }

  public static int updateMultiplyPicturesStatus(List<Integer> pictureCheckList, EnumPictureCardStatus enumPictureCardStatus)
  {

    PictureCard picture = new PictureCard();
    PictureCard exPicture = new PictureCard();
    int countUpdated = 0;
    try
    {
      HibernateSessionHolder.beginTransaction();
      PictureCardDAO pictureCardDAO = DAOFactory.getInstance().getPictureCardDAO();
      for (Integer idPicture : pictureCheckList)
      {

        picture = pictureCardDAO.get(idPicture);
        //caso update status a CURRENT
        if (enumPictureCardStatus == EnumPictureCardStatus.Current && picture.getEnumPictureCardStatus() != EnumPictureCardStatus.Current)
        {   //controllo se esite già una foto in current, che non sia la stessa

          exPicture = pictureCardDAO.getByStatus(picture.getId(),
                  picture.getUser().getId(),
                  EnumPictureCardStatus.Current);
          if (exPicture != null)  //se esiste lo cancello fisiscamente e da db
          {
            String filePathMiniature = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), exPicture.getFilename());
            File pictureCardFile = new File(filePathMiniature);
            if (pictureCardFile.exists())
            {
              pictureCardFile.delete();
            }
           
            String filenameAvatar = StringUtils.replace(filePathMiniature, ".", "_a.");
            File pictureAvatarFile = new File(filenameAvatar);
            if (pictureAvatarFile.exists())
            {
              pictureAvatarFile.delete();
            }
            pictureCardDAO.makeTransient(exPicture);
          }
        }
        picture.setEnumPictureCardStatus(enumPictureCardStatus);
        //CacheManager.removeWebSiteCacheInfo();
        CacheManager.removeWebSiteUserInfo(picture.getUser().getId());
      }
      countUpdated++;
      HibernateSessionHolder.commitTransaction();

    }
    catch (Exception ex)
    {
      HibernateSessionHolder.rollbackTransaction();
      logger.error(ex.getMessage(), ex);
    }

    return countUpdated;

  }

  // </editor-fold>
}
