package it.newmedia.gokick.backOffice.guibean;

import java.io.Serializable;
import org.apache.log4j.Logger;

public abstract class AGuiBean implements Serializable
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  protected static final Logger logger = Logger.getLogger(AGuiBean.class);
  protected boolean valid = false;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters/Setters --">
  public boolean isValid()
  {
    return this.valid;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --">
  // </editor-fold>
}
