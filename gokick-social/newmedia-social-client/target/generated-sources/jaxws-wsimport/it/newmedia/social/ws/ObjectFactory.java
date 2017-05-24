
package it.newmedia.social.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.newmedia.social.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _WritePosts_QNAME = new QName("http://ws.social.newmedia.it/", "writePosts");
    private final static QName _WritePostsResponse_QNAME = new QName("http://ws.social.newmedia.it/", "writePostsResponse");
    private final static QName _ReadLastPosts_QNAME = new QName("http://ws.social.newmedia.it/", "readLastPosts");
    private final static QName _ReadLastPostsResponse_QNAME = new QName("http://ws.social.newmedia.it/", "readLastPostsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.newmedia.social.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReadPostRequest }
     * 
     */
    public ReadPostRequest createReadPostRequest() {
        return new ReadPostRequest();
    }

    /**
     * Create an instance of {@link WritePostRequest }
     * 
     */
    public WritePostRequest createWritePostRequest() {
        return new WritePostRequest();
    }

    /**
     * Create an instance of {@link ReadPostResponseData }
     * 
     */
    public ReadPostResponseData createReadPostResponseData() {
        return new ReadPostResponseData();
    }

    /**
     * Create an instance of {@link WritePostRequestData }
     * 
     */
    public WritePostRequestData createWritePostRequestData() {
        return new WritePostRequestData();
    }

    /**
     * Create an instance of {@link WritePostResponse }
     * 
     */
    public WritePostResponse createWritePostResponse() {
        return new WritePostResponse();
    }

    /**
     * Create an instance of {@link WritePostsResponse }
     * 
     */
    public WritePostsResponse createWritePostsResponse() {
        return new WritePostsResponse();
    }

    /**
     * Create an instance of {@link ReadPostResponse }
     * 
     */
    public ReadPostResponse createReadPostResponse() {
        return new ReadPostResponse();
    }

    /**
     * Create an instance of {@link ReadLastPostsResponse }
     * 
     */
    public ReadLastPostsResponse createReadLastPostsResponse() {
        return new ReadLastPostsResponse();
    }

    /**
     * Create an instance of {@link ReadLastPosts }
     * 
     */
    public ReadLastPosts createReadLastPosts() {
        return new ReadLastPosts();
    }

    /**
     * Create an instance of {@link WritePosts }
     * 
     */
    public WritePosts createWritePosts() {
        return new WritePosts();
    }

    /**
     * Create an instance of {@link WritePostResponseData }
     * 
     */
    public WritePostResponseData createWritePostResponseData() {
        return new WritePostResponseData();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WritePosts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.social.newmedia.it/", name = "writePosts")
    public JAXBElement<WritePosts> createWritePosts(WritePosts value) {
        return new JAXBElement<WritePosts>(_WritePosts_QNAME, WritePosts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WritePostsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.social.newmedia.it/", name = "writePostsResponse")
    public JAXBElement<WritePostsResponse> createWritePostsResponse(WritePostsResponse value) {
        return new JAXBElement<WritePostsResponse>(_WritePostsResponse_QNAME, WritePostsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadLastPosts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.social.newmedia.it/", name = "readLastPosts")
    public JAXBElement<ReadLastPosts> createReadLastPosts(ReadLastPosts value) {
        return new JAXBElement<ReadLastPosts>(_ReadLastPosts_QNAME, ReadLastPosts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadLastPostsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.social.newmedia.it/", name = "readLastPostsResponse")
    public JAXBElement<ReadLastPostsResponse> createReadLastPostsResponse(ReadLastPostsResponse value) {
        return new JAXBElement<ReadLastPostsResponse>(_ReadLastPostsResponse_QNAME, ReadLastPostsResponse.class, null, value);
    }

}
