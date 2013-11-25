package it.newmedia.gokick.site.managers;

import it.newmedia.gokick.data.hibernate.beans.Squad;
import it.newmedia.gokick.site.infos.SquadInfo;
import it.newmedia.gokick.site.providers.InfoProvider;
import org.apache.log4j.Logger;
import it.newmedia.gokick.site.guibean.GuiSquadInfo;

/**
 *
 * Classe manager che gestisce ad alto livello le azioni per gestire la visualizzazione dei dati delle rose.
 */
public class SquadInfoManager
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private static Logger logger = Logger.getLogger(SquadInfoManager.class);
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Constructors --"  >
  private SquadInfoManager()
  {
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Methods --"  >
  /**
   *
   * @param idSquad id squadra(di amici)
   * @return oggetto squadInfo per la squad richiesta e la mette in cache in caso non ci fosse
   */
  public static SquadInfo getSquadInfoByIdSquad(int idSquad)
  {
    return InfoProvider.getSquadInfo(idSquad);
  }

  /**
   * costruisce e restituisce GuiSquadInfo per la squadra indicata
   * @param squad
   * @return oggeto GuiSquadInfo 
   */
  public static GuiSquadInfo getGuiSquadInfo(Squad squad)
  {
    return InfoProvider.getGuiSquadInfo(squad);
  }

  // </editor-fold>
}
