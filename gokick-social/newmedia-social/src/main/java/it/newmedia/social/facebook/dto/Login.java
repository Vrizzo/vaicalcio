package it.newmedia.social.facebook.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Login", propOrder = {"username", "password"})
public class Login extends ABaseDto
{
  private String username;
  private  String password;

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  @Override
  public String toString()
  {
    return new ToStringBuilder(this, CUSTOM_STYLE ).
                                            append("username", username).
            append("password", password).
            toString();
  }
}
