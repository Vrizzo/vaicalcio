package it.newmedia.social.facebook.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "APost")
@XmlTransient
public abstract class APost extends ABaseDto
{
  protected String message;
  protected String name;
  protected String link;
  protected String caption;
  protected String description;
  protected String picture;

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getLink()
  {
    return link;
  }

  public void setLink(String link)
  {
    this.link = link;
  }

  public String getCaption()
  {
    return caption;
  }

  public void setCaption(String caption)
  {
    this.caption = caption;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getPicture()
  {
    return picture;
  }

  public void setPicture(String picture)
  {
    this.picture = picture;
  }

  @Override
  public String toString()
  {
    return new ToStringBuilder(this, CUSTOM_STYLE).append("message", message)
                                    .append("name", name)
                                    .append("link", link)
                                    .append("caption", caption)
                                    .append("description", description)
                                    .append("picture", picture)
                                    .toString();
  }
}
