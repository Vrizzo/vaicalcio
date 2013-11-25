package it.newmedia.social.facebook.dto;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "AResponse")
public abstract class AResponse extends ABaseDto
{
  protected boolean success;
  protected String errorMessage;

  public AResponse()
  {
    this.success = false;
    this.errorMessage = "";
  }

  /**
   * Get the value of success
   *
   * @return the value of success
   */
  public boolean isSuccess()
  {
    return success;
  }

  /**
   * Set the value of success
   *
   * @param success new value of success
   */
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
}
