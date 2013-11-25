package it.newmedia.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * Classe che definisce tutti i metodi per la gestione di una immagine caricata.
 */
public class ImageProcessor
{

  public enum VerticalAlign
  {

    Top,
    Center,
    Bottom
  }

  public enum HorizontalAlign
  {

    Left,
    Center,
    Right
  }

  public static BufferedImage byteArrayToBufferedImage(byte[] inputBytes) throws Exception
  {
    return inputStreamToBufferedImage(new ByteArrayInputStream(inputBytes));
  }

  public static BufferedImage inputStreamToBufferedImage(InputStream inputStream) throws Exception
  {
    return ImageIO.read(inputStream);
  }

  public static BufferedImage fileToBufferedImage(File inputFile) throws Exception
  {
    return ImageIO.read(inputFile);
  }

  public static byte[] anyFormatToJpeg(byte[] inputBytes) throws Exception
  {
    return anyFormatToJpeg(byteArrayToBufferedImage(inputBytes));
  }

  public static byte[] anyFormatToJpeg(InputStream inputStream) throws Exception
  {
    return anyFormatToJpeg(inputStreamToBufferedImage(inputStream));
  }

  public static byte[] anyFormatToJpeg(BufferedImage inputImage) throws Exception
  {
    // encode as jpg: this method does not permit to control compression!!! The implementation commenent (donw) manage compression
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try
    {
      ImageIO.write(inputImage, "jpg", outputStream);
      outputStream.close();
    }
    catch (Exception ex)
    {
      throw new Exception("Cannot write outputStream", ex);
    }

    return outputStream.toByteArray();

//            // Find a jpeg writer
//            ImageWriter writer = null;
//            Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
//            if (iter.hasNext()) {
//                writer = (ImageWriter)iter.next();
//            }
//
//           // Prepare output stream
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
//            writer.setOutput(ios);
//
//            // Set the compression quality
//            ImageWriteParam iwparam = writer.getDefaultWriteParam();
//            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT) ;
//            iwparam.setCompressionQuality(1);
//
//            // Write the image
//            writer.write(null, new IIOImage(inputImage, null, null), iwparam);
//
//            // Cleanup
//            ios.flush();
//            writer.dispose();
//            ios.close();
//
//            return outputStream.toByteArray();
  }

  public static BufferedImage resize(BufferedImage inputImage, int newWidth, int newHeight, boolean lockAspectRatio, boolean enlargeIfSmaller) throws Exception
  {
    // if no need to resize, return input. NOTE: this is not correct: shiuld return a copy!
    if (inputImage.getHeight() <= newHeight &&
            inputImage.getWidth() <= newWidth &&
            !enlargeIfSmaller)
    {
      return inputImage;
    }

    int newEffectiveWidth;
    int newEffectiveHeight;

    if (!lockAspectRatio)
    {
      newEffectiveWidth = newWidth;
      newEffectiveHeight = newHeight;
    }
    else
    {
      // calculate rations
      double newRatio = (double) newWidth / (double) newHeight;
      double inputRatio = (double) inputImage.getWidth() / (double) inputImage.getHeight();

      // must resize on WIDTH (height calculated)
      if (newRatio < inputRatio)
      {
        newEffectiveWidth = newWidth;
        newEffectiveHeight = (int) (newWidth / inputRatio);
      }
      else
      {
        // must resize on HEIGHT (width calculated)
        newEffectiveHeight = newHeight;
        newEffectiveWidth = (int) (newEffectiveHeight * inputRatio);
      }
    }

    // prepare output image
    BufferedImage newImage = new BufferedImage(
            newEffectiveWidth,
            newEffectiveHeight,
            inputImage.getType());

    // get graphics object used to write output image
    Graphics2D g = newImage.createGraphics();
//    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//
//
    g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    //g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);





    //graphics2D.setColor(Color.WHITE);
    //graphics2D.fillRect(0, 0, newWidth, newHeight);
    g.drawImage(
            inputImage,
            0,
            0,
            newEffectiveWidth,
            newEffectiveHeight,
            Color.WHITE,
            null);

    return newImage;
  }

  public static BufferedImage cropByAbsolutePositions(BufferedImage inputImage, int xStart, int yStart, int xEnd, int yEnd)
  {
    return ImageProcessor.cropByDimensions(inputImage, xStart, yStart, xEnd - xStart, yEnd - yStart);
  }

  public static BufferedImage cropByDimensions(BufferedImage inputImage, int xStart, int yStart, int xWidth, int yHeight)
  {
    return inputImage.getSubimage(xStart, yStart, xWidth, yHeight);
  }

  /**
   * Overlaps two images
   * @param backImage: image to be used as background
   * @param frontImage: image in foreground
   * @param verticalAlign: vertical alignement for overlapped images
   * @param horizontalAlign: horizontal alignement for overlapped images
   * @param frontImageAlpha: alpha for front image. Valid values from 0 (transparent) to 1 (no trasparency)
   * @param backImageBgFill: if back image is smaller than front image, the missing part is filled using this color
   * @return overlapped image
   * @throws java.lang.Exception
   */
  public static BufferedImage overlap(
          BufferedImage backImage,
          BufferedImage frontImage,
          VerticalAlign verticalAlign,
          HorizontalAlign horizontalAlign,
          float frontImageAlpha, // alpha from front image (from 0.0 to 1.0)
          Color backImageBgFill // if backimage must be enlarged (front is bigger), color for filling
          ) throws Exception
  {
    int newWidth = backImage.getWidth() >= frontImage.getWidth() ? backImage.getWidth() : frontImage.getWidth();
    int newHeight = backImage.getHeight() >= frontImage.getHeight() ? backImage.getHeight() : frontImage.getHeight();

    // create res image starting from back image
    BufferedImage res = backImage.getSubimage(0, 0, backImage.getWidth(), backImage.getHeight());

    int expandTop = 0;
    int expandRight = 0;
    int expandBottom = 0;
    int expandLeft = 0;

    // must be expanded vertically?
    if (res.getHeight() < newHeight)
    {
      switch (verticalAlign)
      {
        case Bottom:
          expandTop = newHeight - res.getHeight();
          break;
        case Top:
          expandBottom = newHeight - res.getHeight();
          break;
        default:
          expandTop = (newHeight - res.getHeight()) / 2;
          expandBottom = newHeight - res.getHeight() - expandTop;
          break;
      }

    }

    // must be expanded horizontally?
    if (res.getWidth() < newWidth)
    {
      switch (horizontalAlign)
      {
        case Right:
          expandLeft = newWidth - res.getWidth();
          break;
        case Left:
          expandRight = newWidth - res.getWidth();
          break;
        default:
          expandRight = (newWidth - res.getWidth()) / 2;
          expandLeft = newWidth - res.getWidth() - expandRight;
          break;
      }

    }

    // expand image if needed
    if (expandTop != 0 ||
            expandRight != 0 ||
            expandBottom != 0 ||
            expandLeft != 0)
    {
      res = ImageProcessor.addBorders(res, backImageBgFill, expandTop, expandRight, expandBottom, expandLeft);
    }

    // where is the front image to put?
    int overlapX = 0;
    int overlapY = 0;

    switch (verticalAlign)
    {
      case Top:
        overlapY = 0;
        break;
      case Bottom:
        overlapY = newHeight - frontImage.getHeight();
        break;
      default:
        overlapY = (newHeight - frontImage.getHeight()) / 2;
        break;
    }

    switch (horizontalAlign)
    {
      case Left:
        overlapX = 0;
        break;
      case Right:
        overlapX = newWidth - frontImage.getWidth();
        break;
      default:
        overlapX = (newWidth - frontImage.getWidth()) / 2;
        break;
    }

    // get graphic to do the real work
    Graphics2D g = res.createGraphics();
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, frontImageAlpha));

    // overlap!
    g.drawImage(frontImage, overlapX, overlapY, null);

    return res;
  }

  /**
   * Add borders to image
   * @param inputImage
   * @param borderColor
   * @param borderTop
   * @param borderRight
   * @param borderBottom
   * @param borderLeft
   * @return
   */
  public static BufferedImage addBorders(BufferedImage inputImage, Color borderColor, int borderTop, int borderRight, int borderBottom, int borderLeft)
  {
    BufferedImage res = new BufferedImage(
            inputImage.getWidth() + borderLeft + borderRight,
            inputImage.getHeight() + borderTop + borderBottom,
            BufferedImage.TYPE_INT_RGB);

    Graphics2D g = res.createGraphics();

    g.setColor(borderColor);
    g.fillRect(0, 0, res.getWidth(), res.getHeight());
    g.drawImage(inputImage, borderLeft, borderTop, null);

    return res;
  }

  /**
   *
   * @param inputString: string to be converted into image
   * @param fontName: font to be used
   * @param fontColor: font color
   * @param fontStyle: font style, use Font.BOLD, Font.ITALIC, Font.BOLD|Font.ITALIC o Font.PLAIN (see Font class constructor for detailed info)
   * @param useAntialiasing: antialiasing?
   * @param imgWidth: width of the generated image
   * @param imgHeight: height of the generated image
   * @param bgColor: bgcolor (use Color.Transulcent for transparency)
   * @return the string as image
   */
  public static BufferedImage stringToImage(
          String inputString,
          String fontName,
          Color fontColor,
          int fontStyle,
          boolean useAntialiasing,
          VerticalAlign stringVerticalAlign,
          HorizontalAlign stringHorizontalAlign,
          int imgWidth,
          int imgHeight,
          Color bgColor)
  {
    // create image
    BufferedImage res = new BufferedImage(
            imgWidth,
            imgHeight,
            BufferedImage.TYPE_INT_RGB);

    // create graphics
    Graphics2D g = res.createGraphics();

    // set bg
    g.setColor(bgColor);
    g.fillRect(0, 0, imgWidth, imgHeight);

    // set font details
    g.setColor(fontColor);
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, useAntialiasing ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

    // not let's find the font dimension to be used. We will use the simplest alghorithm (bad performances): start from 5px and add 1 until bounds are greater then image
    int currFontSize = 5;
    FontMetrics fm = null;
    Rectangle2D strBounds;
    int imgHeightReal = (int) (imgHeight * 0.9);
    do
    {
      currFontSize++;
      g.setFont(new Font(fontName, fontStyle, currFontSize));
      fm = g.getFontMetrics();
      strBounds = fm.getStringBounds(inputString, g);
    }
    while (strBounds.getWidth() < imgWidth && strBounds.getHeight() < imgHeightReal);

    // get prev size (last it too big)
    currFontSize--;
    g.setFont(new Font(fontName, fontStyle, currFontSize));
    fm = g.getFontMetrics();
    strBounds = fm.getStringBounds(inputString, g);

    // get string position
    int posX;
    int posY;

    switch (stringVerticalAlign)
    {
      case Bottom:
        posY = imgHeightReal;
        break;
      case Top:
        posY = (int) strBounds.getHeight();
        break;
      default:
        posY = imgHeightReal - ((imgHeightReal - (int) strBounds.getHeight()) / 2);
        break;
    }

    switch (stringHorizontalAlign)
    {
      case Left:
        posX = 0;
        break;
      case Right:
        posX = imgWidth - (int) strBounds.getWidth();
        break;
      default:
        posX = (res.getWidth() - (int) strBounds.getWidth()) / 2;
        break;
    }

    g.drawString(inputString, posX, posY);
    g.dispose();

    return res;
  }
}
