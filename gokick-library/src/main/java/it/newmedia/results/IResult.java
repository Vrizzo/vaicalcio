package it.newmedia.results;

public interface IResult<T>
{
  
  // <editor-fold defaultstate="collapsed" desc="-- Methods --">
  
  Boolean isSuccess();
  
  T getValue();
  
  String getErrorMessage();
  
  Exception getErrorException();
  
  // </editor-fold>
}
