package it.newmedia.gokick.backOffice.web.actions;

import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.backOffice.AppContext;
import it.newmedia.gokick.backOffice.manager.PictureCardManager;
import it.newmedia.gokick.backOffice.manager.UserManager;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * Classe contenente l'azione per visualizzare la foto caricata dall'utente per la figurina.
 */
public class ViewPictureCardAction extends ABaseActionSupport {

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idUser;
  private int idPicture;
  private String pictureCardFilename;
  private boolean emptyPictureCard;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
      this.pictureCardFilename = UserManager.getLastPictureCard(this.idUser).getFilename();
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

  public String viewUserPictureCard()
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

  public String viewUserPictureCardById()
  {
     this.emptyPictureCard = false;

     PictureCard pictureCard = new PictureCard();

     pictureCard=PictureCardManager.getPictureCard(this.idPicture);

     if (pictureCard!=null)
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
  /**
   * @return the idPicture
   */
  public int getIdPicture()
  {
    return idPicture;
  }

  /**
   * @param idPicture the idPicture to set
   */
  public void setIdPicture(int idPicture)
  {
    this.idPicture = idPicture;
  }

  // </editor-fold>
}