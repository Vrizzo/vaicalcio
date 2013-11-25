package it.newmedia.gokick.site.infos;

import java.io.Serializable;
import org.apache.log4j.Logger;

/**
 *
 * @author ggeroldi
 */
public abstract class AInfo implements Serializable
{
  //<editor-fold defaultstate="collapsed" desc="-- Members --">
  /**
   *
   */
  protected static final Logger logger = Logger.getLogger(AInfo.class);
  /**
   * Indica se l'oggetto che si sta considerando è valido o no.
   * Nel getter isValid viene valutato questo membro. Se null devono essere fatti
   * i controlli del caso e, di conseguenza, verrà impostato a true o false
   */
  protected Boolean valid = null;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getter/Setter --">
  /**
   *
   * @return
   */
  public abstract Boolean isValid();
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Constructors --">
  // </editor-fold>

  //<editor-fold defaultstate="collapsed" desc="-- Public Methods --">
  // </editor-fold>


}
