package it.newmedia.gokick.site.web.actions;

import it.newmedia.gokick.site.AppContext;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe contenente le azioni per l'inserimento dei commenti alle partite da parte degli utenti (integrazione CKEditor)
 */
public class CommentUploadAction extends ABaseActionSupport
{
  //<editor-fold defaultstate="collapsed" desc="-- Members --">

  private File upload;
  private int idUser;
  private int idMatch;
 
  private String commentImageVirtualPath;
  private String errorMessage;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- GETTERS/SETTERS --">
  /**
   * @return the upload
   */
  public File getUpload()
  {
    return upload;
  }

  /**
   * @param upload the upload to set
   */
  public void setUpload(File upload)
  {
    this.upload = upload;
  }

  /**
   * @return the commentImageVirtualPath
   */
  public String getCommentImageVirtualPath()
  {
    return commentImageVirtualPath;
  }

  /**
   * @param commentImageVirtualPath the commentImageVirtualPath to set
   */
  public void setCommentImageVirtualPath(String commentImageVirtualPath)
  {
    this.commentImageVirtualPath = commentImageVirtualPath;
  }

  /**
   * @return the idUser
   */
  public int getIdUser()
  {
    return idUser;
  }

  /**
   * @param idUser the idUser to set
   */
  public void setIdUser(int idUser)
  {
    this.idUser = idUser;
  }

  /**
   * @return the idMatch
   */
  public int getIdMatch()
  {
    return idMatch;
  }

  /**
   * @param idMatch the idMatch to set
   */
  public void setIdMatch(int idMatch)
  {
    this.idMatch = idMatch;
  }

  /**
   * @return the errorMessage
   */
  public String getErrorMessage()
  {
    return errorMessage;
  }
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  @Override
  public String execute()
  {
    Long size = upload.length();  
    if (size > 4000000)
    {
      this.commentImageVirtualPath="";
      this.errorMessage="il file non deve superare 4 Mb";
      return SUCCESS;
    }

    try
    {
      
      File copyTo = new File
              (AppContext.getInstance().getPictureCommentPath() + AppContext.getInstance().getPictureCommentSuffixName()  + this.idMatch + "_" + this.idUser + "_0.jpg");

      int picIndex=0;
      while(copyTo.exists())
      {
        picIndex++;
        String fileName= StringUtils.substringBeforeLast(copyTo.getName(),"_");
        fileName = AppContext.getInstance().getPictureCommentSuffixName() + this.idMatch + "_" + this.idUser +  "_" + Integer.toString(picIndex)+ ".jpg";
        copyTo = new File(AppContext.getInstance().getPictureCommentPath() + fileName);
      }
      FileUtils.copyFile(upload, copyTo);
      this.commentImageVirtualPath = AppContext.getInstance().getPictureCommentvirtualPath() + copyTo.getName();
      return SUCCESS;
    }

    catch (Exception ex)
    {
      this.commentImageVirtualPath="";
      this.errorMessage="Error uploading file";
      logger.error("Error uploadin pictureComment", ex);
      return SUCCESS;
    }
    
  }

  // </editor-fold>
}
