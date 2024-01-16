package co.com.bancolombia.soapconsumer.tdc;
import jakarta.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {


    /**
     * Create an instance of {@link ConsultaMarcacionesRequest }
     */
    public ConsultaMarcacionesRequest createConsultaMarcacionesRequest() {
        return new ConsultaMarcacionesRequest();
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

