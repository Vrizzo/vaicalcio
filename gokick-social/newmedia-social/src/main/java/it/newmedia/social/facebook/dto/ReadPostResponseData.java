package it.newmedia.social.facebook.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by IntelliJ IDEA.
 * User: ssalmaso
 * Date: 16/01/12
 * Time: 20.42
 * To change this template use File | Settings | File Templates.
 */
@XmlType(name = "ReadPostResponseData")
public class ReadPostResponseData extends APost
{
  protected String idPost;

  public String getIdPost()
  {
    return idPost;
  }

  public void setIdPost(String idPost)
  {
    this.idPost = idPost;
  }

  @Override
  public String toString()
  {
    return new ToStringBuilder(this, CUSTOM_STYLE).append("idPost", idPost)
                                    .append("basePost", super.toString())
                                    .toString();
  }
}
