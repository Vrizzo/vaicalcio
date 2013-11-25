
package it.newmedia.social.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for writePostsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="writePostsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="response" type="{http://ws.social.newmedia.it/}WritePostResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "writePostsResponse", propOrder = {
    "response"
})
public class WritePostsResponse {

    protected WritePostResponse response;

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link WritePostResponse }
     *     
     */
    public WritePostResponse getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link WritePostResponse }
     *     
     */
    public void setResponse(WritePostResponse value) {
        this.response = value;
    }

}
