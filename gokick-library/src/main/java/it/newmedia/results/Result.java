package it.newmedia.results;

public class Result<T> implements IResult
{
  // <editor-fold defaultstate="collapsed" desc="-- Members --">
  private T value = null;
  private boolean success = false;
  private String errorMessage = "";
  private Exception errorException = null;
  private StringBuilder debugInfo = null;

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Constructors --">
  /** Creates a new instance of Result */
  private Result()
  {
  }

  public Result(T value, boolean success)
  {
    this.value = value;
    this.success = success;
  }
  
  public Result(T value, boolean success, StringBuilder debugInfo)
  {
    this(value, success);
    this.debugInfo = debugInfo;
  }

  public Result(String errorMessage)
  {
    this(errorMessage, new Exception(errorMessage));
  }

  public Result(String errorMessage, StringBuilder debugInfo)
  {
    this(errorMessage);
    this.debugInfo = debugInfo;
  }

  public Result(Exception ex)
  {
    this(ex.getMessage(), ex);
  }

  public Result(Exception ex, StringBuilder debugInfo)
  {
    this(ex);
    this.debugInfo = debugInfo;
  }

  public Result(String errorMessage, Exception ex)
  {
    this.success = false;
    this.value = null;
    this.errorException = ex;
    this.errorMessage = errorMessage;
  }

  public Result(String errorMessage, Exception ex, StringBuilder debugInfo)
  {
    this(errorMessage, ex);
    this.debugInfo = debugInfo;
  }

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="-- Getters/Setters --">
  public T getValue()
  {
    return value;
  }

  public Boolean isSuccess()
  {
    return success;
  }

  public Boolean isSuccessNotNull()
  {
    return success && value != null;
  }

  public Exception getErrorException()
  {
    return errorException;
  }

  public String getErrorMessage()
  {
    return errorMessage;
  }
  
  public StringBuilder getDebugInfo()
  {
    return this.debugInfo!=null ? this.debugInfo : new StringBuilder();
  }
  // </editor-fold>
}
