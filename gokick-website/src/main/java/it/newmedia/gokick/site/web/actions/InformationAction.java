package it.newmedia.gokick.site.web.actions;

import org.apache.log4j.Logger;

/**
 *
 * Classe contenente l'azione per visualizzare un messaggio informativo,come messaggi d'errore relativi a permessi non posseduti
 */
public class InformationAction extends ABaseActionSupport
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static Logger logger = Logger.getLogger(InformationAction.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private String info;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  public String getInfo()
  {
    return info;
  }

  public void setInfo(String info)
  {
    this.info = info;
  }
  // </editor-fold>
}