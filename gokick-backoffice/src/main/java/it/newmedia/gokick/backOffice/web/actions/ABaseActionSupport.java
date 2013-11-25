package it.newmedia.gokick.backOffice.web.actions;

import com.opensymphony.xwork2.ActionSupport;

import it.newmedia.gokick.data.hibernate.beans.Translation;
import it.newmedia.gokick.backOffice.AppContext;
import it.newmedia.gokick.backOffice.UserContext;
import it.newmedia.gokick.backOffice.manager.TranslationManager;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Classe di base che estente ActionSupport e gestisce/contiene tutte le informazioni che verranno poi utilizzate in tutte le action che la estenderanno.
 */
public class ABaseActionSupport extends ActionSupport implements SessionAware {

  public final static String ERROR_POPUP = "errorPopup";
  protected static final Logger logger = Logger.getLogger(ABaseActionSupport.class);

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static final String TRANSLATION_ARG_NOVALUE = "<!null>";
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters/Setters --"  >

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- SessionAware implementation --"  >
  private Map session;

  /**
   * Implementazione del metodo presente nell'interfaccia SessionAware
   * @param map
   */
  public void setSession(Map map)
  {
    this.session = map;
  }

  /**
   * Metodo di comodità per ottenere la sessione associata alla richiesta dell'utente
   * La sessione è un oggetto di tipo Map dato che viene "intercettata da Struts 2
   * @return Un ogggetto di tipo Map con chive/valore
   * corrispondente ai dati presenti nella sessione web dell'utente
   */
  public Map getSession()
  {
    return this.session;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Current application infos --"  >
  public AppContext getAppContext()
  {
    return AppContext.getInstance();
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Current user infos --"  >
  public UserContext getUserContext()
  {
    return UserContext.getInstance();
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Common action methods --"  >

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Tranlstions --"  >
  @Override
  public String getText(String key)
  {
    return getTextCustom(key, new String[]
            {
            });
  }

  /**
   * Estende metodo standard di Struts 2 dando la possibilità di inserire
   * dei tag per i quali fare il replace (nel testo deve esserci {0}, {1}...) e restituisce
   * il messaggio tradotto in funzione della chiave passata
   * Se la traduzione prevede la sostituzioe dei tag dell'applicazione questa verrà effettuata
   * (i tag dell'applicazione possono essere legati solo al contesto dell'utente,
   * es: il nome dell'utente (tag ###NAME### ??)
   * @param key Stringa corrisopndente alla chiave della traduzione sul database
   * @param argReplace Serie di valori da inserire nel test ({0}, {1}..)
   * @return La Stringa tradotte e con le opportune sostituzioni, se necessario
   */
  private String getTextCustom(String key, String... argReplace)
  {
    Translation translation = TranslationManager.getTranslation(key);
    String result = translation.getKeyValue();
    if (argReplace != null && argReplace.length > 0)
    {
      for (int i = 0; i < argReplace.length; i++)
      {
        if (!TRANSLATION_ARG_NOVALUE.equals(argReplace[i]))
        {
          result = StringUtils.replace(result, String.format("{%s}", i), argReplace[i]);
        }
      }
    }
    //System.out.println("getTextCustom: key->" + key + ", argReplace->" + argReplace.length + ", risultato->" + result);
    return result;
  }
  // </editor-fold>
}