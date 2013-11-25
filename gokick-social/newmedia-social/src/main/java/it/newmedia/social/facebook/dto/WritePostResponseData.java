package it.newmedia.social.facebook.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "WritePostResponseData", propOrder = {"success", "errorMessage", "idUser", "idPost"})
public class WritePostResponseData extends ABaseDto
{
  protected boolean success;
  protected String errorMessage;
  protected String idPost;
  protected String idUser;

  public boolean isSuccess()
  {
    return success;
  }

  public void setSuccess(boolean success)
  {
    this.success = success;
  }

  public String getErrorMessage()
  {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage)
  {
    this.errorMessage = errorMessage;
  }

  public String getIdPost()
  {
    return idPost;
  }

  public void setIdPost(String idPost)
  {
    this.idPost = idPost;
  }

  public String getIdUser()
  {
    return idUser;
  }

  public void setIdUser(String idUser)
  {
    this.idUser = idUser;
  }

  public WritePostResponseData()
  {
    this.errorMessage = "";
  }

  public WritePostResponseData(String errorMessage)
  {
    this();
    this.success = false;
    this.errorMessage = errorMessage;
  }

  public WritePostResponseData(String idPost, String idUser)
  {
    this();
    this.success = true;
    this.idPost = idPost;
    this.idUser = idUser;
  }

  @Override
  public String toString()
  {
    return new ToStringBuilder(this, CUSTOM_STYLE).append("success", success)
                                                  .append("errorMessage", errorMessage)
                                                  .append("idPost", idPost)
                                                  .append("idUser", idUser)
                                                  .toString();
  }
}
