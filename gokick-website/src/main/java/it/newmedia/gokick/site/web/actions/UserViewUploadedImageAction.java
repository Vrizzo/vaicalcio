package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.util.ImageProc;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * Classe contenente l'azione per visualizzare la foto caricata dall'utente per la figurina e permetterne il crop
 */
public class UserViewUploadedImageAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(UserViewUploadedImageAction.class);
  private BufferedImage biPictureCard;


  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    this.setBiPictureCard((BufferedImage) getSession().get(Constants.SESSION_KEY__PICTURE_CARD_FILE));
    return SUCCESS;
  }

  public InputStream getInputStream()
  {
    try
    {
      return new ByteArrayInputStream(ImageProc.anyFormatToJpeg(this.getBiPictureCard()));
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return null;
    }
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public BufferedImage getBiPictureCard()
  {
    return biPictureCard;
  }

  public void setBiPictureCard(BufferedImage biPictureCard)
  {
    this.biPictureCard = biPictureCard;
  }

  // </editor-fold>
}