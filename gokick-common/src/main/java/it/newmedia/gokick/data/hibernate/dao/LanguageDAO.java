package it.newmedia.gokick.data.hibernate.dao;

import it.newmedia.gokick.data.hibernate.AGenericDAO;
import it.newmedia.gokick.data.hibernate.beans.Language;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * DAO per la gestione delle lingue usate. Contiene tutti i metodi che lavorano sulla tabella LANGUAGES.
 */
public class LanguageDAO extends AGenericDAO < Language, Integer >
{

  /**
   *
   * @param session
   */
  public LanguageDAO(Session session)
  {
    super(session);
  }

  /**
   *
   * @param language lingiagio d'interesse
   * @return oggetto Language
   */
  public Language getByLanguage(String language)
  {
    Criteria criteria = getSession().createCriteria(Language.class);
    criteria.add(Restrictions.eq("language", language));
    return (Language)criteria.uniqueResult();
  }

  /**
   *
   * @param locale request.locale d'interesse
   * @return oggetto Language
   */
  public Language getByLocale(String locale)
  {
    Criteria criteria = getSession().createCriteria(Language.class);
    criteria.add(Restrictions.eq("localeStr", locale));
    return (Language)criteria.uniqueResult();
  }

  /**
   *
   * @return la lista dei linguaggi ENABLED ordinata per Position
   * @throws Exception
   */
  public List<Language> getAllEnabled() throws Exception
  {
    Criteria crit = getSession().createCriteria(Language.class);
    crit.add(Restrictions.eq("enabled", true));
    crit.addOrder(Order.asc("position"));
    return crit.list();
  }
}
