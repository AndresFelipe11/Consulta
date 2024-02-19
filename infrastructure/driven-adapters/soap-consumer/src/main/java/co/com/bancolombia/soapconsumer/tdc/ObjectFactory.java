package co.com.bancolombia.soapconsumer.tdc;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;


@XmlRegistry
public class ObjectFactory {

    private final static QName _RequestHeader_QNAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "requestHeader");

    /**
     * Create an instance of {@link ConsultaMarcacionesRequest }
     */
    public ConsultaMarcacionesRequest createConsultaMarcacionesRequest() {
        return new ConsultaMarcacionesRequest();
    }

    /**
     * Create an instance of {@link RequestHeader }
     *
     */
    public RequestHeader createRequestHeader() {
        return new RequestHeader();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestHeader }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/soap/envelope/", name = "requestHeader")
    public JAXBElement<RequestHeader> createRequestHeader(RequestHeader value) {
        return new JAXBElement<RequestHeader>(_RequestHeader_QNAME, RequestHeader.class, null, value);
    }

    /**
     * Create an instance of {@link Inconsistencias }
     */
    public Inconsistencias createInconsistencias() {
        return new Inconsistencias();
    }

    /**
     * Create an instance of {@link ConsultaMarcacionesResponse }
     */
    public ConsultaMarcacionesResponse createConsultaMarcacionesResponse() {
        return new ConsultaMarcacionesResponse();
    }
}

