package it.newmedia.social.facebook.dto;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(name = "ABaseDto")
public abstract class ABaseDto implements Serializable
{
  protected static final Logger logger = Logger.getLogger(ABaseDto.class);
  public static final ToStringStyle CUSTOM_STYLE = new CustomToStringStyle();

  private static final class CustomToStringStyle extends ToStringStyle
  {

    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor.</p>
     * <p/>
     * <p>Use the static constant rather than instantiating.</p>
     */
    CustomToStringStyle()
    {
      super();
      this.setUseShortClassName(true);
      this.setUseIdentityHashCode(false);
      this.setContentStart("[");
      this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
      this.setFieldSeparatorAtStart(true);
      this.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
    }

    /**
     * <p>Ensure <code>Singleton</code> after serialization.</p>
     *
     * @return the singleton
     */
    private Object readResolve()
    {
      return ABaseDto.CUSTOM_STYLE;
    }

  }

}
