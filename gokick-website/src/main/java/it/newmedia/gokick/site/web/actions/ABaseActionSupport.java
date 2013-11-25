package it.newmedia.gokick.site.web.actions;

import com.opensymphony.xwork2.ActionSupport;
import it.newmedia.gokick.data.hibernate.beans.Cobrand;
import it.newmedia.gokick.data.hibernate.beans.Language;
import it.newmedia.gokick.data.hibernate.beans.Translation;
import it.newmedia.gokick.data.hibernate.beans.User;
import it.newmedia.gokick.site.AppContext;
import it.newmedia.gokick.site.UserContext;
import it.newmedia.gokick.site.infos.UserInfo;
import it.newmedia.gokick.site.managers.DateManager;
import it.newmedia.gokick.site.managers.LanguageManager;
import it.newmedia.gokick.site.providers.InfoProvider;
import it.newmedia.gokick.site.providers.TranslationProvider;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * Classe di base che estente ActionSupport e gestisce/contiene tutte le informazioni che verranno poi utilizzate in tutte le action che la estenderanno.
 */
public class ABaseActionSupport extends ActionSupport implements SessionAware
{

  // <editor-fold defaultstate="collapsed" desc="-- Constants --">
  private static final String TRANSLATION_ARG_NOVALUE = "<!null>";
  /**
   * L'azione di Struts è fallita
   * Visualizza una pagina di errore
   * Da usare nel caso l'errore si verifichi in una pagina aperta come popup
   */
  public final static String ERROR_POPUP = "errorPopup";
  /**
   * Definisce la stringa che corrisponde ad una result che riporta in home page l'utente
   */
  public final static String HOME = "home";
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Members --"  >

  /**
   * sistema di logging utilizzato per tenere sotto controllo l'applicazione
   */
  protected static final Logger logger = Logger.getLogger(ABaseActionSupport.class);

  private String lastMessage;

  private UserInfo currentUserInfo;

  private User currentUser;

  private Language currentLanguage;
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Getters/Setters --"  >
  /**
   *
   * @return
   */
  public String getLastMessage()
  {
    return lastMessage;
  }
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
   * @return Un oggetto di tipo Map con chiave/valore
   * corrispondente ai dati presenti nella sessione web dell'utente
   */
  public Map getSession()
  {
    return this.session;
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Current application infos --"  >
  /**
   *
   * @return l'istanza del contesto applicativo
   */
  public AppContext getAppContext()
  {
    return AppContext.getInstance();
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Current user infos --"  >
  /**
   *
   * @return l'istanza del contesto riferito all'utente corrente (loggato)
   */
  public UserContext getUserContext()
  {
    return UserContext.getInstance();
  }

  /**
   * Restituisce il cobrand corrente (riferito al sito
   * su cui si sta navigando)
   * @return il Cobrand corrente
   */
  public Cobrand getCurrentCobrand()
  {
    return getUserContext().getCurrentCobrand();
  }
  /**
   *
   * @return l'oggetto User preso dallo UserContext
   */
  public User getCurrentUser()
  {
    if (this.currentUser == null)
    {
      this.currentUser = this.getUserContext().getUser();
    }
    return this.currentUser;
  }

  /**
   * Restituisce l'id dell'utente corrente (loggato)
   * @return L'id dell'utente loggato
   */
  public int getCurrentIdUser()
  {
    if (this.getUserContext().isLoggedIn())
      return this.getCurrentUser().getId().intValue();
    //Nessun utente loggato
    return -1;
  }

  /**
   *
   * @return l'oggetto Language utilizzato dall'utente corrente
   */
  public Language getCurrentObjLanguage()
  {
    if (this.currentLanguage == null)
      this.currentLanguage = this.getUserContext().getLanguage();
    return this.currentLanguage;
  }

  /**
   *
   * @return la definizione della lingua (stringa) utilizzata dall'utente corrente
   */
  public String getCurrentLanguage()
  {
    return this.getCurrentObjLanguage().getLanguage();
  }

  /**
   *
   * @return l'oggetto UserInfo che contiene che contiene tutte le informazioni utili dell'utente corrente
   */
  public UserInfo getCurrentUserInfo()
  {
    if (this.currentUserInfo == null)
    {
      this.currentUserInfo = InfoProvider.getUserInfo(this.getCurrentUser().getId());
    }
    return this.currentUserInfo;
  }

  /**
   * Restituisce True se l'utente corrente risulta loggato sull'applicazione
   * @return true o false a seconda se l'utente sia loggato o meno
   */
  protected boolean isLoggedIn()
  {
    return this.getUserContext().isLoggedIn();
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Translations --"  >
  /**
   * Estende metodo standard di Struts 2 e restituisce
   * il messaggio tradotto in funzione della chiave passata
   * Se la traduzione prevede la sostituzioe dei tag dell'applicazione questa verrà effettuata
   * (i tag dell'applicazione possono essere legati solo al contesto dell'utente,
   * es: il nome dell'utente (tag ###NAME### ??)
   * @param key Stringa corrisopndente alla chiave della traduzione sul database
   * @return La Stringa tradotte e con le opportune sostituzioni, se necessario
   */
  @Override
  public String getText(String key)
  {
    return getTextCustom(key, new String[]
            {
            });
  }

  @Override
  public String getText(String key,String lang)
  {
    if (lang.isEmpty())
    {
      return getText(key);
    }
    else
    {
      return getTextCustomByLang( key, lang,new String[]{});
    }
     
  }

  /**
   *
   * @param aTextName
   * @param args
   * @return
   */
  @Override
  public String getText(String aTextName, List<?> args)
  {
    throw new NotImplementedException("Not Work! Use getTextCustom instead.");
  }

  
  /**
   * restituisce il messaggio tradotto in funzione della chiave passata
   * @param key chiave
   * @return getTextCustom()
   */
  public String getTextArgs(String key)
  {
    return getTextCustom(key, new String[]
            {
            });
  }

  /**
   * restituisce il messaggio tradotto in funzione della chiave passata e del valore da inserire nel testo ({0}) 
   *
   * @param key
   * @param args01 valore da inserire nel testo ({0})
   * @return
   */
  public String getTextArgs(String key, String args01)
  {
    return getTextCustom(key, args01);
  }

  /**
   * restituisce il messaggio tradotto in funzione della chiave passata e dei valori da inserire nel testo ({0},{1})
   * @param key chiave
   * @param args01  valore da inserire nel testo ({1})
   * @param args02  valore da inserire nel testo ({2})
   * @return
   */
  public String getTextArgs(String key, String args01, String args02)
  {
    return getTextCustom(key, args01, args02);
  }

  /**
   * restituisce il messaggio tradotto in funzione della chiave passata e dei valori da inserire nel testo ({0},{1},{2})
   * @param key chiave
   * @param args01  valore da inserire nel testo ({0})
   * @param args02  valore da inserire nel testo ({1})
   * @param args03  valore da inserire nel testo ({2})
   * @return
   */
  public String getTextArgs(String key, String args01, String args02, String args03)
  {
    return getTextCustom(key, args01, args02, args03);
  }

  /**
   * restituisce il messaggio tradotto in funzione della chiave passata e dei valori da inserire nel testo ({0},{1},{2},{3})
   * @param key chiave
   * @param args01  valore da inserire nel testo ({0})
   * @param args02  valore da inserire nel testo ({1})
   * @param args03  valore da inserire nel testo ({2})
   * @param args04  valore da inserire nel testo ({3})
   * @return
   */
  public String getTextArgs(String key, String args01, String args02, String args03, String args04)
  {
    return getTextCustom(key, args01, args02, args03, args04);
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
    Translation translation = TranslationProvider.getTranslation(key, this.getCurrentObjLanguage(), this.getCurrentIdUser(), getCurrentCobrand());
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

  private String getTextCustomByLang(String key,String country_id, String... argReplace)
  {
    if (country_id.trim().isEmpty())
      return getTextCustom( key, argReplace);
    
    int idCountry = Integer.parseInt(country_id);
    Language language = LanguageManager.chooseLanguage(idCountry);
    
    Translation translation = TranslationProvider.getTranslation(key, language, this.getCurrentIdUser(), getCurrentCobrand());
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

  
  /**
   * Torna true o false in funzione del fatto che il
   * testo associato alla chiave passata
   * debba essere "escapato" o meno
   * @param key Stringa corrisopndente alla chiave della traduzione sul database
   * @return True se deve essere fatto l'escape, altrimenti false
   */
  public String getTextEscapeHtml(String key)
  {
    Translation translation = TranslationProvider.getTranslation(key, this.getCurrentObjLanguage(), getCurrentCobrand());
    return Boolean.toString(!translation.isHtmlEnabled());
  }

  public String getTextEscapeHtml(String key,String country_id)
  {
     if (country_id.trim().isEmpty())
      return getTextEscapeHtml( key);
     
    int idCountry = Integer.parseInt(country_id);
    Language language = LanguageManager.chooseLanguage(idCountry);
    Translation translation = TranslationProvider.getTranslation(key, language, getCurrentCobrand());
    return Boolean.toString(!translation.isHtmlEnabled());
  }

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Date Format --">
  /**
   *
   * 
   * @param formatKey formato in uscita
   * @return stringa contenente la data, nel formato indicato
   */
  public String getDate(String formatKey)
  {
    return DateManager.showDate(new Date(), formatKey);
  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="-- Common action methods --"  >
  /**
   * restiruisce l'ultimo messaggio per l'utente (dall'applicazione) e lo rimuove dal contesto dell'utente corrente
   * @return
   */
  public String showLastMessage()
  {
    this.lastMessage = UserContext.getInstance().getLastMessage();
    UserContext.getInstance().removeLastMessage();
    return SUCCESS;
  }

  /**
   * 
   * @return lista dei linguaggi abilitati
   */
  public List<Language> getLanguageList()
  {
    return AppContext.getInstance().getLanguageList();
  }
  // </editor-fold>
}
