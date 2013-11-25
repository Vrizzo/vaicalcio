package it.newmedia.social.facebook.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "ReadPostResponse")
public class ReadPostResponse extends AResponse
{

  protected List<ReadPostResponseData> posts;

  @XmlElement(name = "posts")
  public List<ReadPostResponseData> getPosts()
  {
    return posts;
  }

  public void setPosts(List<ReadPostResponseData> posts)
  {
    this.posts = posts;
  }

  public ReadPostResponse()
  {
    super();
    this.posts = new ArrayList<ReadPostResponseData>();
  }

  public ReadPostResponse(String errorMessage)
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
