package it.newmedia.social.facebook.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "WritePostRequestData", propOrder = {"idUser", "accessToken", "message", "name", "link", "caption", "description", "picture"})
public class WritePostRequestData extends APost
{
  protected String idUser;
  protected String accessToken;

  public String getIdUser()
  {
    return idUser;
  }

  public void setIdUser(String idUser)
  {
    this.idUser = idUser;
  }

  public String getAccessToken()
  {
    return accessToken;
  }

  public void setAccessToken(String accessToken)
  {
    this.accessToken = accessToken;
  }

  @Override
  public String toString()
  {
    return new ToStringBuilder(this, CUSTOM_STYLE).append("idUser", idUser)
                                                  .append("accessToken", accessToken)
                                                  .append("basePost", super.toString())
                                                  .toString();
  }
}
