package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.UserManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.util.ImageProc;
import it.newmedia.net.HttpConnection;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Classe contenente le azioni di visualizzazione,inserimento, rimozione, salvataggio e aggiornamento delle figurine da parte dell'utente
 */
public class PictureCardAction extends ABaseActionSupport
{


  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  public static final int CROP_HEIGHT_PIXEL_SIZE = 178;
  public static final int CROP_WIDTH_PIXEL_SIZE = 165;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int idUser;
  private String pictureCardFilename;
  private boolean emptyPictureCard;
  private File pictureCardFile;
  private String pictureCardFileContentType;

  private int x1;
  private int x2;
  private int y1;
  private int y2;
  private int width;
  private int height;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >

  public String newImage()
  {
    return "newImageSuccess";
  }

  public String chooseImage()
  {
    //Workaround! Se il file di cui viene fatto l'upload supera il limite definito in
    //struts.properties il messaggio è in inglese e quindi qui viene "sistemato"
    Map<String, List<String>> errors = this.getFieldErrors();
    List<String> errorMessages = errors.remove("pictureCardFile");
    if (errorMessages != null && errorMessages.get(0).startsWith("File too large"))
    {
      errorMessages.clear();
      errorMessages.add(getText("error.uploadFile.pictureCardSizeLimit"));
      errors.put("pictureCardFile", errorMessages);
      setFieldErrors(errors);
      return "newImageSuccess";
    }
    if (this.pictureCardFile == null || this.pictureCardFile.getName().length() == 0)
    {
      addFieldError("pictureCardFile", getText("error.uploadFile.required"));
      return "newImageSuccess";
    }
    else if (!this.pictureCardFile.isFile())
    {
      addFieldError("pictureCardFile", getText("error.uploadFile.invalid"));
      return "newImageSuccess";
    }
    if (this.pictureCardFile.length() > AppContext.getInstance().getPicCardUploadedFileSizeLimit())
    {
      addFieldError("pictureCardFile", getText("error.uploadFile.pictureCardSizeLimit"));
      return "newImageSuccess";
    }
    String[] formatTypes = AppContext.getInstance().getPicCardUploadedFileFormatType().split(";");
    boolean validFormatType = false;
    for (int i = 0; i < formatTypes.length; i++)
    {
      if (this.pictureCardFileContentType.equalsIgnoreCase(formatTypes[i]))
      {
        validFormatType = true;
        break;
      }
    }
    if (!validFormatType)
    {
      addFieldError("pictureCardFile", getText("error.uploadFile.pictureCardImageType"));
      return "newImageSuccess";
    }

    try
    {
      BufferedImage biPictureCard = ImageIO.read(this.pictureCardFile);
      if (biPictureCard.getHeight() < CROP_HEIGHT_PIXEL_SIZE || biPictureCard.getWidth() < CROP_WIDTH_PIXEL_SIZE)
      {
        addFieldError("pictureCardFile", getText("error.uploadFile.pictureCardTooSmall"));
        return "newImageSuccess";
      }

      float ratio;
      if (biPictureCard.getHeight() > biPictureCard.getWidth())
      {
        ratio = (float) AppContext.getInstance().getUploadedImageHeight() / (float) biPictureCard.getHeight();
        if ((biPictureCard.getWidth() * ratio) < CROP_WIDTH_PIXEL_SIZE)
        {
          addFieldError("pictureCardFile", getText("error.uploadFile.pictureCardratio"));
          return "newImageSuccess";
        }
      }
      else if (biPictureCard.getWidth() > biPictureCard.getHeight())
      {
        ratio = (float) AppContext.getInstance().getUploadedImageWidth() / (float) biPictureCard.getWidth();
        if ((biPictureCard.getHeight() * ratio) < CROP_HEIGHT_PIXEL_SIZE)
        {
          addFieldError("pictureCardFile", getText("error.uploadFile.pictureCardratio"));
          return "newImageSuccess";
        }
      }

      int originalHeight = biPictureCard.getHeight();

      if (biPictureCard.getHeight() > AppContext.getInstance().getUploadedImageHeight() || biPictureCard.getWidth() > AppContext.getInstance().getUploadedImageWidth())
      {
        biPictureCard = ImageProc.resize(biPictureCard, AppContext.getInstance().getUploadedImageWidth(), AppContext.getInstance().getUploadedImageHeight(), true, true);
      }
      else
      {
        BufferedImage newImage;
        if (biPictureCard.getType() == 0 || biPictureCard.getType() == 6)  //se è PNG con trasparenza
        {
          // <editor-fold defaultstate="collapsed" desc="elabora PNG">
          newImage = new BufferedImage(biPictureCard.getWidth(), biPictureCard.getHeight(), BufferedImage.TYPE_INT_RGB);

          byte[] bytesPictureCard = ImageProc.anyFormatToJpeg(newImage);
          newImage = ImageProc.byteArrayToBufferedImage(bytesPictureCard);

          // get graphics object used to write output image
          Graphics2D g = newImage.createGraphics();
          g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
          g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
          g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
          g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
          g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
          g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
          g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
          g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

          g.drawImage(biPictureCard, 0, 0, biPictureCard.getWidth(), biPictureCard.getHeight(), Color.WHITE, null);

          biPictureCard = newImage;
          // </editor-fold>
        }
      }

      this.width = biPictureCard.getWidth();
      this.height = biPictureCard.getHeight();

      ratio = (float) originalHeight / (float) this.height;
      getSession().put(Constants.SESSION_KEY__PICTURE_CARD_FILE, biPictureCard);
      getSession().put(Constants.SESSION_KEY__PICTURE_CARD_FILE_RATIO, ratio);

      String filenameMiniature = String.format("%1$s_%2$s.jpg", "uploadedPic", UserContext.getInstance().getUser().getId());
      String filePathMiniature = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), filenameMiniature);
      FileUtils.copyFile(this.pictureCardFile, new File(filePathMiniature));
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return ERROR;
    }
    return "chooseImageSuccess";
  }

  public String remove()
  {

    User currentUser = UserContext.getInstance().getUser();
    PictureCard pictureCard = new PictureCard();
    pictureCard = UserManager.getLastPictureCard(currentUser.getId());

    if (pictureCard != null)
    {
      // <editor-fold defaultstate="collapsed" desc="-- deleting image --"  >
      //cancello file
      String filenameMiniature = pictureCard.getFilename();
      String filenameAvatar = pictureCard.getFilename().replace(".", "_a.");
      String filePathMiniature = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), filenameMiniature);
      String filePathAvatar = String.format("%1$s%2$s", AppContext.getInstance().getAvatarPath(), filenameAvatar);
      File file;
      try
      {
        //cancello miniatura
        file = new File(filePathMiniature);
        if (file.exists())
        {
          FileUtils.forceDelete(file);
        }
        //cancello avatar
        file = new File(filePathAvatar);
        if (file.exists())
        {
          FileUtils.forceDelete(file);
        }
      }
      catch (IOException iOException)
      {
        logger.error(iOException);
      }
      //cancello da db
      UserManager.removePictureCard(pictureCard);

      // </editor-fold>
    }


    return "removeImageSuccess";
  }

  public String saveImage()
  {
    // Verifico se l'utente può accedere a questa pagina
    User currentUser = UserContext.getInstance().getUser();
    try
    {
      Float ratio = (Float) (getSession().get(Constants.SESSION_KEY__PICTURE_CARD_FILE_RATIO));

      PictureCard picCtrlId = UserManager.getLastPictureCard(currentUser.getId());
      int index = 1;
      if (picCtrlId != null)
      {
        String idIndex = picCtrlId.getFilename();
        StringBuffer sb = new StringBuffer(idIndex);
        String indexString = sb.substring(Constants.PICTURE_CARD_FILE_FORMAT.length() + 1, Constants.PICTURE_CARD_FILE_FORMAT.length() + 1 + Constants.PICTURE_CARD_FILE_INDEX_FORMAT.length());
        index = Integer.parseInt(indexString) + 1;
      }

      DecimalFormat decFormat = new DecimalFormat(Constants.PICTURE_CARD_FILE_FORMAT);
      DecimalFormat decFormatIndex = new DecimalFormat(Constants.PICTURE_CARD_FILE_INDEX_FORMAT);
      String filenameMiniature = String.format("%1$s_%2$s.jpg", decFormat.format(currentUser.getId()), decFormatIndex.format(index));
      String filenameAvatar = String.format("%1$s_%2$s_a.jpg", decFormat.format(currentUser.getId()), decFormatIndex.format(index));
      String filePathMiniature = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), filenameMiniature);
      String filePathAvatar = String.format("%1$s%2$s", AppContext.getInstance().getAvatarPath(), filenameAvatar);

      String filename = String.format("%1$s_%2$s.jpg", "uploadedPic", UserContext.getInstance().getUser().getId());
      String fileOriginalPath = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), filename);

      File file;
      PictureCard picCtrl = UserManager.getByFileName(filenameMiniature);
      if (picCtrl != null)
      {
        file = new File(filePathMiniature);
        FileUtils.forceDelete(file);
        DAOFactory.getInstance().getPictureCardDAO().makeTransient(picCtrl);
      }

      // <editor-fold defaultstate="collapsed" desc="-- CreatePicConnection --">
      Float cordX1 = (float) (this.x1) * ratio;
      Float cordX2 = (float) (this.x2) * ratio;
      Float cordY1 = (float) (this.y1) * ratio;
      Float cordY2 = (float) (this.y2) * ratio;

      Map paramsMap = new HashMap();
      paramsMap.put("origPath", fileOriginalPath);
      paramsMap.put("cardPath", filePathMiniature);
      paramsMap.put("avatarPath", filePathAvatar);
      paramsMap.put("name", currentUser.getFirstName() + " " + currentUser.getLastName());
      paramsMap.put("x1", StringUtils.substringBefore(cordX1.toString(), "."));
      paramsMap.put("x2", StringUtils.substringBefore(cordX2.toString(), "."));
      paramsMap.put("y1", StringUtils.substringBefore(cordY1.toString(), "."));
      paramsMap.put("y2", StringUtils.substringBefore(cordY2.toString(), "."));
      HttpConnection httpConn = new HttpConnection(AppContext.getInstance().getCreatePicUrl());
      httpConn.doPost(paramsMap);

      if (httpConn.getResponseCode() != HttpConnection.HTTP_RESPONSECODE_200_OK)
      {
        logger.error("Error connecting php script, response code :" + httpConn.getResponse());
      }
      if (httpConn.getResponse().equals("0"))
      {
        logger.error("Error from php script, return 0");
      }
      // </editor-fold>
      PictureCard pictureCard = new PictureCard();
      pictureCard.setUser(currentUser);
      pictureCard.setFilename(filenameMiniature);
      pictureCard.setCreated(new Date());
      pictureCard.setPictureCardStatus(EnumPictureCardStatus.Pending.getValue());

      DAOFactory.getInstance().getPictureCardDAO().makePersistent(pictureCard);

      //cancella precedenti
      List<PictureCard> picList = UserManager.getPictureCards(currentUser.getId(), EnumPictureCardStatus.Pending, EnumPictureCardStatus.Refused);

      //cancella upload
      file = new File(fileOriginalPath);
      if (file.exists())
      {
        FileUtils.forceDelete(file);
      }

      if (picList.size() > 1)
      {
        for (int i = 1; i < picList.size(); i++)
        {
          filenameMiniature = picList.get(i).getFilename();
          filenameAvatar = picList.get(i).getFilename().replace(".", "_a.");
          filePathMiniature = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), filenameMiniature);
          filePathAvatar = String.format("%1$s%2$s", AppContext.getInstance().getAvatarPath(), filenameAvatar);

          //cancello miniatura
          file = new File(filePathMiniature);
          if (file.exists())
          {
            FileUtils.forceDelete(file);
          }

          //cancello avatar
          file = new File(filePathAvatar);
          if (file.exists())
          {
            FileUtils.forceDelete(file);
          }

          //cancello da db
          DAOFactory.getInstance().getPictureCardDAO().makeTransient(picList.get(i));
        }
      }
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return Constants.STRUTS_RESULT_NAME__EXCEPTION;
    }

    return "saveImageSuccess";
  }

  public String viewPlayerPictureCard()
  {
    this.emptyPictureCard = false;
    UserInfo userInfo = InfoProvider.getUserInfo(this.idUser);

    // Visualizza la figurina di un altro utente
    if (!UserContext.getInstance().isLoggedIn())
    {
      this.emptyPictureCard = true;
      return SUCCESS;
    }

    if (!userInfo.isPictureCardVisibleForOthers())
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

  public String viewUserPlayerPictureCard()
  {
    this.emptyPictureCard = false;

    PictureCard pictureCard = new PictureCard();

    pictureCard = UserManager.getLastPictureCard(this.idUser);

    if (pictureCard != null && pictureCard.getEnumPictureCardStatus() != EnumPictureCardStatus.Undefined)
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

  public File getPictureCardFile()
  {
    return pictureCardFile;
  }

  public void setPictureCardFile(File pictureCardFile)
  {
    this.pictureCardFile = pictureCardFile;
  }

  public String getPictureCardFileContentType()
  {
    return pictureCardFileContentType;
  }

  public void setPictureCardFileContentType(String pictureCardFileContentType)
  {
    this.pictureCardFileContentType = pictureCardFileContentType;
  }

  public int getX1()
  {
    return x1;
  }

  public void setX1(int x1)
  {
    this.x1 = x1;
  }

  public int getX2()
  {
    return x2;
  }

  public void setX2(int x2)
  {
    this.x2 = x2;
  }

  public int getY1()
  {
    return y1;
  }

  public void setY1(int y1)
  {
    this.y1 = y1;
  }

  public int getY2()
  {
    return y2;
  }

  public void setY2(int y2)
  {
    this.y2 = y2;
  }

  public int getWidth()
  {
    return width;
  }

  public void setWidth(int width)
  {
    this.width = width;
  }

  public int getHeight()
  {
    return height;
  }

  public void setHeight(int height)
  {
    this.height = height;
  }


  // </editor-fold>
}