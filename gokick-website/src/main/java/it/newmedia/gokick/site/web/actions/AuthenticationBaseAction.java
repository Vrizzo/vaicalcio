package it.newmedia.gokick.site.web.actions;

/**
 *
 * Classe astratta di base che estente ABaseActionSupport e serve per filtrare le action che richedono di essere autenticati.
 */
public class AuthenticationBaseAction extends ABaseActionSupport
{

  private String authenticationRequired = Boolean.TRUE.toString();

  public String getAuthenticationRequired()
  {
    return authenticationRequired;
  }

  public void setAuthenticationRequired(String authenticationRequired)
  {
    //System.out.println("Devo mettere:" + authenticationRequired);
    this.authenticationRequired = authenticationRequired;
  }
}
