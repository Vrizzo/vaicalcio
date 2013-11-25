package it.newmedia.gokick.data.hibernate;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Interfaccia di un generic DAO.
 * @param <T> tipo dell'oggetto che verra'  gestito.
 * @param <ID> tipo della chiave primaria dell'oggetto.
 */
public interface IGenericDAO<T, ID extends Serializable>
{

  T findById(ID id, boolean lock) throws Exception;

  List<T> findAll() throws Exception;
  
  T makePersistent(T entity) throws Exception;

  void makeTransient(T entity) throws Exception;

}

