package it.newmedia.social.facebook.dto;


import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class ARequest extends ABaseDto
{
  protected Login login;

  public Login getLogin()
  {
    return login;
  }

  public void setLogin(Login login)
  {
    this.login = login;
  }
}
