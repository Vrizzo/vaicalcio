
package it.newmedia.social.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for readLastPostsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="readLastPostsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="response" type="{http://ws.social.newmedia.it/}ReadPostResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "readLastPostsResponse", propOrder = {
    "response"
})
public class ReadLastPostsResponse {

    protected ReadPostResponse response;

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link ReadPostResponse }
     *     
     */
    public ReadPostResponse getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReadPostResponse }
     *     
     */
    public void setResponse(ReadPostResponse value) {
        this.response = value;
    }

}
