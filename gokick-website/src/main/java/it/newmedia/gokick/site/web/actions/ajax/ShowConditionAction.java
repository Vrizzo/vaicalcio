package it.newmedia.gokick.site.web.actions.ajax;

import it.newmedia.gokick.site.web.actions.ABaseActionSupport;

/**
 *Classe contenente le azioni per recuperare l'id della condizione fisica dell'utente
 *
 *
 */
public class ShowConditionAction extends ABaseActionSupport
{

  private int idCondition;


  @Override
  public String execute()
  {
    return SUCCESS;
  }

  // <editor-fold defaultstate="collapsed" desc="-- GETTER/SETTER --">
  public int getIdCondition()
  {
    return idCondition;
  }

  public void setIdCondition(int idCondition)
  {
    this.idCondition = idCondition;
  }

  // </editor-fold>
}
