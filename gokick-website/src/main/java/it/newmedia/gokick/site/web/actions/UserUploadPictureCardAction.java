package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.site.AppContext;
import java.io.File;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.util.ImageProc;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione per caricare la figurina di un utente (in fase di registrazione)
 */
public class UserUploadPictureCardAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  public static final int CROP_HEIGHT_PIXEL_SIZE = 178;
  public static final int CROP_WIDTH_PIXEL_SIZE = 165;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserUploadPictureCardAction.class);

  private File pictureCardFile;

  private String pictureCardFileContentType;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String input()
  {
    // Verifico se l'utente può accedere a questa pagina
    if (!UserContext.getInstance().isPending())
    {
      return Constants.STRUTS_RESULT_NAME__ACCESS_DENIED;
    }

    return INPUT;
  }

  @Override
  public String execute()
  {
    //Verifico se l'utente può accedere a questa pagina
    if (!UserContext.getInstance().isPending())
    {
      return Constants.STRUTS_RESULT_NAME__ACCESS_DENIED;
    }

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
      return INPUT;
    }
    if (this.pictureCardFile == null || this.pictureCardFile.getName().length() == 0)
    {
      addFieldError("pictureCardFile", getText("error.uploadFile.required"));
      return INPUT;
    }
    else if (!this.pictureCardFile.isFile())
    {
      addFieldError("pictureCardFile", getText("error.uploadFile.invalid"));
      return INPUT;
    }
    if (this.pictureCardFile.length() > AppContext.getInstance().getPicCardUploadedFileSizeLimit())
    {
      addFieldError("pictureCardFile", getText("error.uploadFile.pictureCardSizeLimit"));
      return INPUT;
    }
    String[] formatTypes = AppContext.getInstance().getPicCardUploadedFileFormatType().split(";");
    boolean validFormatType = false;
    for(int i=0; i<formatTypes.length; i++)
    {
      if(this.pictureCardFileContentType.equalsIgnoreCase(formatTypes[i]))
      {
        validFormatType = true;
        break;
      }
    }
    if (!validFormatType)
    {
      addFieldError("pictureCardFile", getText("error.uploadFile.pictureCardImageType"));
      return INPUT;
    }

    try
    {
      BufferedImage biPictureCard = ImageIO.read(this.pictureCardFile);
      if (biPictureCard.getHeight() < CROP_HEIGHT_PIXEL_SIZE || biPictureCard.getWidth() < CROP_WIDTH_PIXEL_SIZE)
      {
        addFieldError("pictureCardFile", getText("error.uploadFile.pictureCardTooSmall"));
        return INPUT;
      }
      
      float ratio;
      if (biPictureCard.getHeight() > biPictureCard.getWidth())
      {
        ratio= (float ) AppContext.getInstance().getUploadedImageHeight()/(float )biPictureCard.getHeight();
        if ((biPictureCard.getWidth() * ratio) < CROP_WIDTH_PIXEL_SIZE)
        {
          addFieldError("pictureCardFile", getText("error.uploadFile.pictureCardratio"));
          return INPUT;
        }
      }
      else if (biPictureCard.getWidth() > biPictureCard.getHeight())
      {
        ratio= (float ) AppContext.getInstance().getUploadedImageWidth()/(float )biPictureCard.getWidth();
        if ((biPictureCard.getHeight() * ratio) < CROP_HEIGHT_PIXEL_SIZE)
        {
          addFieldError("pictureCardFile", getText("error.uploadFile.pictureCardratio"));
          return INPUT;
        }
      }
      
      int originalHeight = biPictureCard.getHeight();

      if (biPictureCard.getHeight() > AppContext.getInstance().getUploadedImageHeight() || 
              biPictureCard.getWidth() > AppContext.getInstance().getUploadedImageWidth())
      {
        biPictureCard = ImageProc.resize(biPictureCard, AppContext.getInstance().getUploadedImageWidth(), AppContext.getInstance().getUploadedImageHeight(), true, true);
      }
      else
      {
        BufferedImage newImage;
        if(biPictureCard.getType()==0 || biPictureCard.getType()==6)  //se è PNG con trasparenza
        {
            // <editor-fold defaultstate="collapsed" desc="elabora PNG">
                      newImage = new BufferedImage(
                      biPictureCard.getWidth(),
                      biPictureCard.getHeight(),
                      BufferedImage.TYPE_INT_RGB);

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

                      g.drawImage(biPictureCard,0,0,biPictureCard.getWidth(),biPictureCard.getHeight(),Color.WHITE,null);

                      biPictureCard = newImage;
                      // </editor-fold>
        }
      }

      ratio = (float ) originalHeight / (float ) biPictureCard.getHeight();
      getSession().put(Constants.SESSION_KEY__PICTURE_CARD_FILE, biPictureCard);
      getSession().put(Constants.SESSION_KEY__PICTURE_CARD_FILE_RATIO, ratio);
      
      String filenameMiniature = String.format("%1$s_%2$s.jpg", "uploadedPic", UserContext.getInstance().getUser().getId());
      String filePathMiniature = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), filenameMiniature);
      FileUtils.copyFile(this.pictureCardFile,new File(filePathMiniature));
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return ERROR;
    }

    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
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


  // </editor-fold>
}