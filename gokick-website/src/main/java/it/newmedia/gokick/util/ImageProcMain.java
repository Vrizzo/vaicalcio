package it.newmedia.gokick.util;

import it.newmedia.gokick.site.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;

/**
 *
 * Classe che definisce tutti i metodi per la creazione della figurina.
 */
public class ImageProcMain
{

  public static byte[] getBytesFromFile(File file) throws IOException
  {
    InputStream is = new FileInputStream(file);

    // Get the size of the file
    long length = file.length();

    if (length > Integer.MAX_VALUE)
    {
      // File is too large
    }

    // Create the byte array to hold the data
    byte[] bytes = new byte[(int) length];

    // Read in the bytes
    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
    {
      offset += numRead;
    }

    // Ensure all the bytes have been read in
    if (offset < bytes.length)
    {
      throw new IOException("Could not completely read file " + file.getName());
    }

    // Close the input stream and return bytes
    is.close();
    return bytes;
  }

  public static byte[] createMiniatureAsJpeg(
          File inputPlayerPhoto,
          int cropX,
          int cropY,
          int cropWidth,
          int cropHeight,
          String inputPlayerName) throws Exception
  {
    return createMiniatureAsJpeg(
            ImageProc.fileToBufferedImage(inputPlayerPhoto),
            cropX,
            cropY,
            cropWidth,
            cropHeight,
            inputPlayerName);
  }

  public static byte[] createMiniatureAsJpeg(
          BufferedImage inputPlayerPhoto,
          int cropX,
          int cropY,
          int cropWidth,
          int cropHeight,
          String inputPlayerName) throws Exception
  {
    // ### TMP: theese values should be from external settings source
    String maskImagePath = AppContext.getInstance().getMaskImagePath();
    String playerNameFontName = AppContext.getInstance().getPlayerNameFontName();
    int playerPhotoPositionTop = AppContext.getInstance().getPlayerPhotoPositionTop();
    int playerPhotoPositionLeft = AppContext.getInstance().getPlayerPhotoPositionLeft();
    int playerPhotoWidth = AppContext.getInstance().getPlayerPhotoWidth();
    int playerPhotoHeight = AppContext.getInstance().getPlayerPhotoHeight();
    int playerNameFontStyle = Font.BOLD;
    Color playerNameFontColor = new Color(11,11,11);
    int playerNamePositionTop = AppContext.getInstance().getPlayerNamePositionTop();
    int playerNamePositionLeft = AppContext.getInstance().getPlayerNamePositionLeft();
    int playerNameWidth = AppContext.getInstance().getPlayerNameWidth();
    int playerNameHeight = AppContext.getInstance().getPlayerNameHeight();
    Color playerNameBgColor = Color.WHITE;

    // ### OK, let's start processing.
    // We will do the following:
    // - crop and resize player photo
    // - create player name image
    // - enlarge player photo basing on mask dimensions
    // - enlarge player name image basing on mask dimensions
    // - overlap photo and name
    // - overlap mask

    // ### LOAD MASK
    BufferedImage imgMask = null;
    File fMask = new File(maskImagePath);
    try
    {
      imgMask = ImageProc.fileToBufferedImage(fMask);
    }
    catch (Exception ex)
    {
      throw ex;
    }

    // ### CROP INPUT IMAGE
    BufferedImage imgPlayerPhoto = ImageProc.cropByDimensions(inputPlayerPhoto, cropX, cropY, cropWidth, cropHeight);
    // ...and resize
    imgPlayerPhoto = ImageProc.resize(imgPlayerPhoto, playerPhotoWidth, playerPhotoHeight, false, true);
    // ...and add borders
    imgPlayerPhoto = ImageProc.addBorders(
            imgPlayerPhoto,
            Color.white, // doesn't matter: il will be overlapped by mask
            playerPhotoPositionTop,
            imgMask.getWidth() - imgPlayerPhoto.getWidth() - playerPhotoPositionLeft,
            0,
            playerNamePositionLeft);

    // ### CREATE PLAYER NAME IMAGE
    BufferedImage imgPlayerName = ImageProc.stringToImage(
            inputPlayerName,
            playerNameFontName,
            playerNameFontColor,
            playerNameFontStyle,
            true,
            ImageProc.VerticalAlign.Center,
            ImageProc.HorizontalAlign.Center,
            playerNameWidth,
            playerNameHeight,
            playerNameBgColor);
    
    // ...and add borders
    imgPlayerName = ImageProc.addBorders(
            imgPlayerName,
            playerNameBgColor,
            imgMask.getHeight() - imgPlayerName.getHeight() - playerNamePositionTop, //- 9, //GG per centrare nome player
            imgMask.getWidth() - imgPlayerName.getWidth() - playerNamePositionLeft,
            playerNamePositionTop , // +7,
            playerNamePositionLeft);

//    byte[] miniature = ImageProc.anyFormatToJpeg(imgPlayerName);
//    FileUtils.writeByteArrayToFile(new File("E:\\temp\\imgPlayerNameBorders.jpg"), miniature);

    // ### MERGE IMAGES
    BufferedImage imgOutput = ImageProc.overlap(
            imgPlayerName,
            imgPlayerPhoto,
            ImageProc.VerticalAlign.Top,
            ImageProc.HorizontalAlign.Center,
            1,
            playerNameBgColor);

    // ### APPLY MASK
    imgOutput = ImageProc.overlap(
            imgOutput,
            imgMask,
            ImageProc.VerticalAlign.Center, // mask and bgImage should be same size so no matter about align
            ImageProc.HorizontalAlign.Center, // mask and bgImage should be same size so no matter about align
            1,
            Color.white);

//    miniature = ImageProc.anyFormatToJpeg(imgOutput);
//    FileUtils.writeByteArrayToFile(new File("E:\\temp\\imgPlayerFinal.jpg"), miniature);


    return ImageProc.anyFormatToJpeg(imgOutput);
  }

  public static byte[] createMiniatureAvatarAsJpeg(
          BufferedImage inputPlayerPhoto,
          int cropX,
          int cropY,
          int cropWidth,
          int cropHeight) throws Exception
  {
    String avatarMaskImagePath = AppContext.getInstance().getAvatarMaskImagePath();
    int playerPhotoPositionTop = AppContext.getInstance().getPlayerPhotoPositionTop();
    int playerPhotoPositionLeft = AppContext.getInstance().getPlayerPhotoPositionLeft();
    int playerPhotoWidth = AppContext.getInstance().getPlayerPhotoAvatarWidth();
    int playerPhotoHeight = AppContext.getInstance().getPlayerPhotoAvatarHeight();

    // ### LOAD MASK
    BufferedImage imgMask = null;
    File fMask = new File(avatarMaskImagePath);
    try
    {
      imgMask = ImageProc.fileToBufferedImage(fMask);
    } catch (Exception ex)
    {
      throw ex;
    }

    // ### CROP INPUT IMAGE
    BufferedImage imgPlayerPhoto = ImageProc.cropByDimensions(inputPlayerPhoto, cropX, cropY, cropWidth, cropHeight);
    // ...and resize
    imgPlayerPhoto = ImageProc.resize(imgPlayerPhoto, playerPhotoWidth, playerPhotoHeight, false, true);
    // ...and crop again
    imgPlayerPhoto = ImageProc.cropByDimensions(imgPlayerPhoto, 0, 0, playerPhotoWidth, playerPhotoHeight);
//    // ...and add borders
//    imgPlayerPhoto = ImageProc.addBorders(
//            imgPlayerPhoto,
//            Color.white, // doesn't matter: il will be overlapped by mask
//            playerPhotoPositionTop,
//            imgMask.getWidth() - imgPlayerPhoto.getWidth() - playerPhotoPositionLeft,
//            playerPhotoPositionTop,
//            0);

    // ### APPLY MASK
    BufferedImage imgOutput = ImageProc.overlap(
            imgPlayerPhoto,
            imgMask,
            ImageProc.VerticalAlign.Center, // mask and bgImage should be same size so no matter about align
            ImageProc.HorizontalAlign.Center, // mask and bgImage should be same size so no matter about align
            1,
            Color.white);

    return ImageProc.anyFormatToJpeg(imgOutput);
  }
}
