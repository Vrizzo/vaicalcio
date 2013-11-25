package it.newmedia.gokick.data.enums;

/**
 *
 * Enum che definisce il sesso di uno user. Valori possibili M o F.
 */
public enum EnumSex
{

  /**
   *  Enum per un sesso indefinito
   */
  Undefined("Undefined"),
  /**
   *  Enum per il sesso maschio
   */
  Male("M"),
  /**
   *  Enum per il sesso femmina
   */
  Female("F");

  private String sex;

  /**
   * Restituisce la stringa associata ad un enum
   * @return La stringa corrispondente all'Enum
   */
  public String getValue()
  {
    return sex;
  }
  
  EnumSex(String sex)
  {
    this.sex = sex;   
  }
  
  /**
   * Restituisce un EnunSex in funzione della stringa passata
   * @param sex Stringa indicante il sesso
   * @return Un istanza di EnumSex valida. Se non è possibile
   * trasformare la stringa viene restituito EnumSex.Undefined
   */
  public static EnumSex getEnum( String sex )
  {
    for (EnumSex enumSex : EnumSex.values())
    {
      if( enumSex.getValue().equals(sex) )
        return enumSex;
    }
    return EnumSex.Undefined;
  }

}