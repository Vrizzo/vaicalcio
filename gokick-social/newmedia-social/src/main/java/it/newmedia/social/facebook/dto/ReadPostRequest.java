package it.newmedia.social.facebook.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ReadPostRequest", propOrder = { "login", "idUser", "accessToken"})
public class ReadPostRequest extends ARequest
{
  private String idUser;
  private String accessToken;

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
    return new ToStringBuilder(this, CUSTOM_STYLE).append("login", super.getLogin())
                                                  .append("idUser", idUser)
                                                  .append("accessToken", accessToken)
                                                  .toString();
  }
}
