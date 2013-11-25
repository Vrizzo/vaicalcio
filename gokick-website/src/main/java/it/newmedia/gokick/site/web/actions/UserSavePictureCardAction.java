package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.data.enums.EnumPictureCardStatus;
import it.newmedia.gokick.data.hibernate.beans.PictureCard;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.Constants;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.hibernate.DAOFactory;
import it.newmedia.net.HttpConnection;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente l'azione per fare il resize e il salvataggio della figurina di un utente.
 */
public class UserSavePictureCardAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private int x1;
  private int x2;
  private int y1;
  private int y2;
  private int width;
  private int height;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    // Verifico se l'utente può accedere a questa pagina
    User currentUser = UserContext.getInstance().getUser();
    if (!UserContext.getInstance().isPending())
    {
      return Constants.STRUTS_RESULT_NAME__ACCESS_DENIED;
    }

    try
    {
      //BufferedImage biPictureCard = ((BufferedImage) getSession().get(Constants.SESSION_KEY__PICTURE_CARD_FILE));
      Float ratio = (Float)(getSession().get(Constants.SESSION_KEY__PICTURE_CARD_FILE_RATIO));

//      byte[] miniature = ImageProcMain.createMiniatureAsJpeg(biPictureCard,
//              this.x1,
//              this.y1,
//              this.width,
//              this.height,
//              currentUser.getFirstName() + " " + currentUser.getLastName());
//
//      byte[] avatar = ImageProcMain.createMiniatureAvatarAsJpeg(biPictureCard,
//              this.x1,
//              this.y1,
//              this.width,
//              this.height);

      int index = DAOFactory.getInstance().getPictureCardDAO().getCountByIdUser((currentUser.getId())) + 1;

      DecimalFormat decFormat = new DecimalFormat(Constants.PICTURE_CARD_FILE_FORMAT);
      DecimalFormat decFormatIndex = new DecimalFormat(Constants.PICTURE_CARD_FILE_INDEX_FORMAT);
      String filenameMiniature = String.format("%1$s_%2$s.jpg", decFormat.format(currentUser.getId()), decFormatIndex.format(index));
      String filenameAvatar = String.format("%1$s_%2$s_a.jpg", decFormat.format(currentUser.getId()), decFormatIndex.format(index));
      String filePathMiniature = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), filenameMiniature);
      String filePathAvatar = String.format("%1$s%2$s", AppContext.getInstance().getAvatarPath(), filenameAvatar);

      String filename = String.format("%1$s_%2$s.jpg", "uploadedPic", UserContext.getInstance().getUser().getId());
      String fileOriginalPath = String.format("%1$s%2$s", AppContext.getInstance().getPictureCardPath(), filename);

//      FileUtils.writeByteArrayToFile(new File(filePathMiniature), miniature);
//      FileUtils.writeByteArrayToFile(new File(filePathAvatar), avatar);

      // <editor-fold defaultstate="collapsed" desc="-- CreatePicConnection --">
      Float cordX1=(float)(this.x1)* ratio;
      Float cordX2=(float)(this.x2)* ratio;
      Float cordY1=(float)(this.y1)* ratio;
      Float cordY2=(float)(this.y2)* ratio;

      Map paramsMap=new HashMap();
      paramsMap.put("origPath", fileOriginalPath);
      paramsMap.put("cardPath", filePathMiniature);
      paramsMap.put("avatarPath", filePathAvatar);
      paramsMap.put("name", currentUser.getFirstName() + " " +currentUser.getLastName());
      paramsMap.put("x1", StringUtils.substringBefore(cordX1.toString(),"."));
      paramsMap.put("x2", StringUtils.substringBefore(cordX2.toString(),"."));
      paramsMap.put("y1", StringUtils.substringBefore(cordY1.toString(),"."));
      paramsMap.put("y2", StringUtils.substringBefore(cordY2.toString(),"."));
      HttpConnection httpConn = new HttpConnection(AppContext.getInstance().getCreatePicUrl());
      httpConn.doPost(paramsMap);

      if (httpConn.getResponseCode() != HttpConnection.HTTP_RESPONSECODE_200_OK)
      {
          logger.error("error connecting php script, response code :" + httpConn.getResponse());
      }
      if (httpConn.getResponse().equals("0"))
      {
          logger.error("error creatinc Pic");
      }
      if (httpConn.getResponse().equals("1"))
      {
          //logger.error("created Pic");
      }
      

//      logger.error(     ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>UserSavePictureCardAction.execute<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n"
//                        + "| responseCode: "    + httpConn.getResponseCode()                                    + "\n"
//                        + "| origPath: "        + fileOriginalPath                                              + "\n"
//                        + "| cardPath: "        + filePathMiniature                                             + "\n"
//                        + "| avatarPath: "      + filePathAvatar                                                + "\n"
//                        + "| name: "            + currentUser.getFirstName() + " " +currentUser.getLastName()   + "\n"
//                        + "| x1: "              + StringUtils.substringBefore(cordX1.toString(),".")            + "\n"
//                        + "| x2: "              + StringUtils.substringBefore(cordX2.toString(),".")            + "\n"
//                        + "| y1: "              + StringUtils.substringBefore(cordY1.toString(),".")            + "\n"
//                        + "| xy2 "              + StringUtils.substringBefore(cordY2.toString(),".")            + "\n" 
//                        + "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
      // </editor-fold>

      PictureCard pictureCard = new PictureCard();
      pictureCard.setUser(currentUser);
      pictureCard.setFilename(filenameMiniature);
      //pictureCard.setApproved(false);
      //pictureCard.setCurrent(true);
      pictureCard.setPictureCardStatus(EnumPictureCardStatus.Pending.getValue());
      pictureCard.setCreated(new Date());
      DAOFactory.getInstance().getPictureCardDAO().makePersistent(pictureCard);

      //cancella upload
      File file = new File(fileOriginalPath);
          if (file.exists()) FileUtils.forceDelete(file);
    }
    catch (Exception e)
    {
      logger.error(e, e);
      return Constants.STRUTS_RESULT_NAME__EXCEPTION;
    }

    return SUCCESS;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
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