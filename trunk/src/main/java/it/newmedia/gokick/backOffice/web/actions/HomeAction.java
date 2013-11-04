package it.newmedia.gokick.backOffice.web.actions;

/**
 *
 * Classe contenente l'azione per la gestione della home.
 */
public class HomeAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  @Override
  public String execute()
  {
    return SUCCESS;
  }


  public static void main(String[] args)
  {
    HomeAction action = new HomeAction();
    System.out.println(action.execute());
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  // </editor-fold>
}