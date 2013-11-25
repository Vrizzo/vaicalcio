package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.providers.InfoProvider;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Classe contenente l'azione per visualizzare l'avatar dell'utente in base alle condizioni esistenti
 */
public class ViewAvatarAction extends ABaseActionSupport
{
  // ------------------------------ FIELDS ------------------------------

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idUser;
  private String avatarFilename;
  private boolean emptyAvatar;

  // --------------------- GETTER / SETTER METHODS ---------------------

  public String getAvatarFilename()
  {
    return avatarFilename;
  }

  public void setAvatarFilename(String avatarFilename)
  {
    this.avatarFilename = avatarFilename;
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

  public boolean isEmptyAvatar()
  {
    return emptyAvatar;
  }

  public void setEmptyAvatar(boolean emptyAvatar)
  {
    this.emptyAvatar = emptyAvatar;
  }

  // ------------------------ INTERFACE METHODS ------------------------


  // --------------------- Interface Action ---------------------

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    this.emptyAvatar = false;
    try
    {
      //Se l'id è minore o uguale a 0 non esite un utente....
      if (this.idUser <= 0)
      {
        this.emptyAvatar = true;
        return SUCCESS;
      }
      //Se non si è loggato non si può vedere l'avatar
      if (!UserContext.getInstance().isLoggedIn())
      {
        this.emptyAvatar = true;
        return SUCCESS;
      }

      UserInfo userInfo = InfoProvider.getUserInfo(this.idUser);
      if (UserContext.getInstance().getUser().getId() == userInfo.getId())
      {
        // Visualizza l'avatar dell'utente attuale anche se lo stato è PENDING
        if (!userInfo.isWithPictureCardFilename())
        {
          this.emptyAvatar = true;
          return SUCCESS;
        }
        this.avatarFilename = String.format("%1$s_a.jpg", userInfo.getPictureCardFilename().replace(".jpg", ""));
      }
      else
      {
        // Visualizza l'avatar di un altro utente
        if (!userInfo.isPictureCardVisibleForOthers())
        {
          this.emptyAvatar = true;
          return SUCCESS;
        }
        this.avatarFilename = String.format("%1$s_a.jpg", userInfo.getPictureCardFilename().replace(".jpg", ""));
      }
    }
    catch (Exception e)
    {
      this.emptyAvatar = true;
      return SUCCESS;
    }

    return SUCCESS;
  }

  // -------------------------- OTHER METHODS --------------------------

  public InputStream getInputStream()
  {
    try
    {
      String pictureCardFilePath;
      if (this.emptyAvatar)
      {
        pictureCardFilePath = AppContext.getInstance().getAvatarEmptyFilename();
      }
      else
      {
        pictureCardFilePath = String.format("%1$s%2$s", AppContext.getInstance().getAvatarPath(), this.avatarFilename);
      }
      File file = new File(pictureCardFilePath);
      if (file.exists())
      {
        return new FileInputStream(file);
      }
      else
      {
        return new FileInputStream(AppContext.getInstance().getAvatarEmptyFilename());
      }
    }
    catch (FileNotFoundException fex)
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

  // </editor-fold>
}