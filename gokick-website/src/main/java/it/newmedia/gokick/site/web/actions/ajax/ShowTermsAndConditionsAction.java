package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.web.actions.ABaseActionSupport;

/**
 *Classe contenente le azioni per recuperare l'id della condizione fisica dell'utente
 *
 *
 */
public class ShowTermsAndConditionsAction extends ABaseActionSupport
{

  private String lang;


  @Override
  public String execute()
  {
    return SUCCESS;
  }

  // <editor-fold defaultstate="collapsed" desc="-- GETTER/SETTER --">
  public String getLang()
  {
    return lang;
  }

  public void setLang(String lang)
  {
    this.lang = lang;
  }

  // </editor-fold>
}
