
package it.newmedia.social.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReadPostResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadPostResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.social.newmedia.it/}AResponse">
 *       &lt;sequence>
 *         &lt;element name="posts" type="{http://ws.social.newmedia.it/}ReadPostResponseData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadPostResponse", propOrder = {
    "posts"
})
public class ReadPostResponse
    extends AResponse
{

    protected List<ReadPostResponseData> posts;

    /**
     * Gets the value of the posts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the posts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReadPostResponseData }
     * 
     * 
     */
    public List<ReadPostResponseData> getPosts() {
        if (posts == null) {
            posts = new ArrayList<ReadPostResponseData>();
        }
        return this.posts;
    }

}
