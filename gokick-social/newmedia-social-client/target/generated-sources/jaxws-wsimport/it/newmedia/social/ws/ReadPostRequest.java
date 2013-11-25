
package it.newmedia.social.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReadPostRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadPostRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.social.newmedia.it/}ABaseDto">
 *       &lt;sequence>
 *         &lt;element name="login" type="{http://ws.social.newmedia.it/}Login" minOccurs="0"/>
 *         &lt;element name="idUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accessToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadPostRequest", propOrder = {
    "login",
    "idUser",
    "accessToken"
})
public class ReadPostRequest
    extends ABaseDto
{

    protected Login login;
    protected String idUser;
    protected String accessToken;

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
     * Gets the value of the idUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * Sets the value of the idUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUser(String value) {
        this.idUser = value;
    }

    /**
     * Gets the value of the accessToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the value of the accessToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessToken(String value) {
        this.accessToken = value;
    }

}
