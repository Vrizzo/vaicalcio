package it.newmedia.gokick.site.infos;

import java.io.Serializable;
import org.apache.log4j.Logger;

/**
 *
 * Classe che gestisce le informazioni relative alle varie tipologie di partita
 */
public class MatchTypeInfo implements Serializable
{

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static final Logger logger = Logger.getLogger(MatchTypeInfo.class);
  private boolean valid;
  private int id;
  private String name;
  private int totPlayersNumber;

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /**
   *
   */
  public MatchTypeInfo()
  {
    this.valid = false;
  }

  /**
   *
   * @param id
   * @param name
   * @param totPlayersNumber
   */
  public MatchTypeInfo(int id, String name, int totPlayersNumber)
  {
    this.valid = true;
    this.id = id;
    this.name = name;
    this.totPlayersNumber = totPlayersNumber;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters / Setters --"  >
  /**
   *
   * @return
   */
  public boolean isValid()
  {
    return valid;
  }

  /**
   *
   * @param valid
   */
  public void setValid(boolean valid)
  {
    this.valid = valid;
  }

  /**
   *
   * @return
   */
  public int getId()
  {
    return id;
  }

  /**
   *
   * @param id
   */
  public void setId(int id)
  {
    this.id = id;
  }

  /**
   *
   * @return
   */
  public String getName()
  {
    return name;
  }

  /**
   *
   * @param name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   *
   * @return
   */
  public int getTotPlayersNumber()
  {
    return totPlayersNumber;
  }

  /**
   *
   * @param totPlayersNumber
   */
  public void setTotPlayersNumber(int totPlayersNumber)
  {
    this.totPlayersNumber = totPlayersNumber;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >  
  // </editor-fold>
}
