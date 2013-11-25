package it.newmedia.social.facebook.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "WritePostRequest", propOrder = {"login", "posts"})
public class WritePostRequest extends ARequest
{
  private List<WritePostRequestData> posts;


  @XmlElement(name = "posts" )
  public List<WritePostRequestData> getPosts()
  {
    return posts;
  }

  public void setPosts(List<WritePostRequestData> posts)
  {
    this.posts = posts;
  }

  @Override
  public String toString()
  {
    return new ToStringBuilder(this, CUSTOM_STYLE).append("posts", posts)
                                    .toString();
  }
}
