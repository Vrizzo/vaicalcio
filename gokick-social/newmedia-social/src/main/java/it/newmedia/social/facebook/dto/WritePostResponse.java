package it.newmedia.social.facebook.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "WritePostResponse")
public class WritePostResponse extends AResponse
{
  private List<WritePostResponseData> posts;

  @XmlElement(name="posts")
  public List<WritePostResponseData> getPosts()
  {
    return posts;
  }

  public void setPosts(List<WritePostResponseData> posts)
  {
    this.posts = posts;
  }

  public WritePostResponse()
  {
    super();
    this.posts = new ArrayList<WritePostResponseData>();
  }

  public WritePostResponse(String errorMessage)
  {
    this();
    this.success = false;
    this.errorMessage = errorMessage;
  }

  @Override
  public String toString()
  {
    return new ToStringBuilder(this, CUSTOM_STYLE).append("success", this.success)
                                                  .append("errorMessage", this.errorMessage)
                                                  .append("posts", posts)
                                                  .toString();
  }
}
