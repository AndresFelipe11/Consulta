package co.com.bancolombia.soapconsumer;

import co.com.bancolombia.model.extractdata.ExtractData;
import co.com.bancolombia.model.responsedata.ResponseData;
import co.com.bancolombia.model.responsedata.gateways.ResponseDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import reactor.core.publisher.Mono;


@Service
@Primary
@RequiredArgsConstructor
public class SoapConsumer extends WebServiceGatewaySupport implements ResponseDataRepository {


    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("co.com.bancolombia.soapconsumer"); // Establece el contexto correcto
        return marshaller;
    }


    @Override
    public Mono<ResponseData> responseData(ExtractData extractData) {

        ObjectRequest request = new ObjectRequest();
        request.setIdCifin("exampleval1");
        request.setCodigoProducto("exampleval2");
        request.setTipoIdentificacion(extractData.getDocumentType());
        request.setNumeroIdentificacion(extractData.getDocumentNumber());
        request.setPassword("exampleval4");
        request.setMotivoConsulta("exampleval5");


        Object responseObject = getWebServiceTemplate().marshalSendAndReceive("http://localhost:8088/consultaMarcaciones", request);

        ObjectResponse objectResponse = (ObjectResponse) responseObject;
        ResponseData responseData = ResponseData.builder()
                .CATS(objectResponse.getCATS())
                .GMF(objectResponse.getGMF())
                .apellido1(objectResponse.getApellido1())
                .apellido2(objectResponse.getApellido2())
                .ciudadDireccion(objectResponse.getCiudadDireccion())
                .codigoCiiu(objectResponse.getCodigoCiiu())
                .direccion(objectResponse.getDireccion())
                .entidadCATS(objectResponse.getEntidadCATS())
                .entidadGMF(objectResponse.getEntidadGMF())
                .estado(objectResponse.getEstado())
                .fecha(objectResponse.getFecha())
                .fechaExpedicion(objectResponse.getFechaExpedicion())
                .genero(objectResponse.getGenero())
                .hora(objectResponse.getHora())
                .inconsistencias(objectResponse.getInconsistencias().toBuilder()
                        .codigo(objectResponse.getInconsistencias().getCodigo())
                        .descripcion(objectResponse.getInconsistencias().getDescripcion())
                        .build())
                .lugarExpedicion(objectResponse.getLugarExpedicion())
                .nombre(objectResponse.getNombre())
                .nombre1(objectResponse.getNombre1())
                .nombre2(objectResponse.getNombre2())
                .numeroIdentificacion(objectResponse.getNumeroIdentificacion())
                .generoInconsistencias(objectResponse.getGeneroInconsistencias())
                .respuestaConsulta(objectResponse.getRespuestaConsulta())
                .tipoIdentificacion(objectResponse.getTipoIdentificacion())
                .build();

        return Mono.just(responseData);
    }
}
