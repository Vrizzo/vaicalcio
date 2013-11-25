package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * Classe contenente l'azione per visualizzare la figurina caricata dall'utente.
 */
public class ViewPictureCardAction extends ABaseActionSupport {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idUser;
  private String pictureCardFilename;
  private boolean emptyPictureCard;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    this.emptyPictureCard = false;
    UserInfo userInfo = InfoProvider.getUserInfo(this.idUser);

    // <editor-fold defaultstate="collapsed" desc="-- exGetPicture --"  >


//    if (UserContext.getInstance().getUser().getId() == userInfo.getId())
//    {
//      // Visualizza la figurina dell'utente attuale anche se lo stato è PENDING
//      try
//      {
//        if (!userInfo.isWithPictureCardFilename())
//        {
//          this.emptyPictureCard = true;
//          return SUCCESS;
//        }
//        this.pictureCardFilename = userInfo.getPictureCardFilename();
//      }
//      catch (Exception e)
//      {
//        this.emptyPictureCard = true;
//        return SUCCESS;
//      }
//    }
//    else
//    {
// </editor-fold>
    
      if (UserContext.getInstance().isPending())
      {
        this.pictureCardFilename = userInfo.getPictureCardFilename();
        return SUCCESS;
      }
      else if (!UserContext.getInstance().isLoggedIn())
      {
        this.emptyPictureCard = true;
        return SUCCESS;
      }
      else if (!userInfo.isPictureCardVisibleForOthers())
      {
        this.emptyPictureCard = true;
        return SUCCESS;
      }
      this.pictureCardFilename = userInfo.getPictureCardFilename();
      return SUCCESS;
  }

  public InputStream getInputStream()
  {
    try
    {
      String pictureCardFilePath;
      if (this.emptyPictureCard)
      {
        pictureCardFilePath = AppContext.getInstance().getPictureCardEmptyFilename();
      }
      else
      {
        pictureCardFilePath = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), this.pictureCardFilename);
      }
      File file = new File(pictureCardFilePath);
      if (file.exists())
      {
        return new FileInputStream(file);
      }
      else
      {
        return new FileInputStream(AppContext.getInstance().getPictureCardEmptyFilename());
      }
    }
    catch(FileNotFoundException fex)
    {
      logger.error(fex.getMessage());
      return new ByteArrayInputStream(new byte[]{});
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return new ByteArrayInputStream(new byte[]{});
    }
  }

  public String viewUserPlayerPictureCard()
  {
     this.emptyPictureCard = false;

     PictureCard pictureCard = new PictureCard();

     pictureCard=UserManager.getLastPictureCard(this.idUser);

      if (pictureCard!=null && pictureCard.getEnumPictureCardStatus()!=EnumPictureCardStatus.Undefined)
      {
        this.pictureCardFilename = pictureCard.getFilename();
        return SUCCESS;
      }
      else
      {
        this.emptyPictureCard = true;
        return SUCCESS;
      }    
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public int getIdUser()
  {
    return idUser;
  }

  public void setIdUser(int idUser)
  {
    this.idUser = idUser;
  }

  public String getPictureCardFilename()
  {
    return pictureCardFilename;
  }

  public void setPictureCardFilename(String pictureCardFilename)
  {
    this.pictureCardFilename = pictureCardFilename;
  }

  public boolean isEmptyPictureCard()
  {
    return emptyPictureCard;
  }

  public void setEmptyPictureCard(boolean emptyPictureCard)
  {
    this.emptyPictureCard = emptyPictureCard;
  }

  // </editor-fold>
}