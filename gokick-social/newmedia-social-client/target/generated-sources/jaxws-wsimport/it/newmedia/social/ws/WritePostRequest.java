
package it.newmedia.social.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WritePostRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WritePostRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.social.newmedia.it/}ABaseDto">
 *       &lt;sequence>
 *         &lt;element name="login" type="{http://ws.social.newmedia.it/}Login" minOccurs="0"/>
 *         &lt;element name="posts" type="{http://ws.social.newmedia.it/}WritePostRequestData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WritePostRequest", propOrder = {
    "login",
    "posts"
})
public class WritePostRequest
    extends ABaseDto
{

    protected Login login;
    protected List<WritePostRequestData> posts;

    /**
     * Gets the value of the login property.
     * 
     * @return
     *     possible object is
     *     {@link Login }
     *     
     */
    public Login getLogin() {
        return login;
    }

    /**
     * Sets the value of the login property.
     * 
     * @param value
     *     allowed object is
     *     {@link Login }
     *     
     */
    public void setLogin(Login value) {
        this.login = value;
    }

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
     * {@link WritePostRequestData }
     * 
     * 
     */
    public List<WritePostRequestData> getPosts() {
        if (posts == null) {
            posts = new ArrayList<WritePostRequestData>();
        }
        return this.posts;
    }

}
