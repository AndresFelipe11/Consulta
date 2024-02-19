package co.com.bancolombia.soapconsumer;

import co.com.bancolombia.model.extractdata.ExtractData;
import co.com.bancolombia.model.responsedata.ResponseData;
import co.com.bancolombia.soapconsumer.tdc.ConsultaMarcacionesRequest;
import co.com.bancolombia.soapconsumer.tdc.ConsultaMarcacionesResponse;
import co.com.bancolombia.soapconsumer.tdc.Inconsistencias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ws.client.core.WebServiceTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class SoapConsumerTest {


    @Mock
    private WebServiceTemplate webServiceTemplate;

    @InjectMocks
    private SoapConsumer soapConsumer;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void responseData() {

        ExtractData extractData = ExtractData.builder()
                .traceTransacion("123456789")
                .documentNumber("1053868313")
                .documentType("1")
                .build();

        ConsultaMarcacionesRequest request = new ConsultaMarcacionesRequest();
        request.setIdCifin("exampleval1");
        request.setCodigoProducto("exampleval2");
        request.setTipoIdentificacion(extractData.getDocumentType());
        request.setNumeroIdentificacion(extractData.getDocumentNumber());
        request.setPassword("exampleval4");
        request.setMotivoConsulta("exampleval5");




        ConsultaMarcacionesResponse consultaMarcacionesResponse = new ConsultaMarcacionesResponse();
        Inconsistencias inconsistencias = new Inconsistencias();
        inconsistencias.setCodigo("valor de codigo");
        inconsistencias.setDescripcion("valor de descripcion");
        consultaMarcacionesResponse.setInconsistencias(inconsistencias);
        consultaMarcacionesResponse.setCATS("valor de CATS");
        consultaMarcacionesResponse.setGMF("valor de GMF");
        consultaMarcacionesResponse.setApellido1("valor de apellido1");
        consultaMarcacionesResponse.setApellido2("valor de apellido2");
        consultaMarcacionesResponse.setCiudadDireccion("valor de ciudadDireccion");
        consultaMarcacionesResponse.setCodigoCiiu("valor de codigoCiiu");
        consultaMarcacionesResponse.setDireccion("valor de direccion");
        consultaMarcacionesResponse.setEntidadCATS("valor de entidadCATS");
        consultaMarcacionesResponse.setEntidadGMF("valor de entidadGMF");
        consultaMarcacionesResponse.setEstado("valor de estado");
        consultaMarcacionesResponse.setFecha("valor de fecha");
        consultaMarcacionesResponse.setFechaExpedicion("valor de fechaExpedicion");
        consultaMarcacionesResponse.setGenero("valor de genero");
        consultaMarcacionesResponse.setGeneroInconsistencias(true);
        consultaMarcacionesResponse.setHora("valor de hora");
        consultaMarcacionesResponse.setLugarExpedicion("valor de lugarExpedicion");
        consultaMarcacionesResponse.setNombre("valor de nombre");
        consultaMarcacionesResponse.setNombre1("valor de nombre1");
        consultaMarcacionesResponse.setNombre2("valor de nombre2");
        consultaMarcacionesResponse.setNumeroIdentificacion("valor de numeroIdentificacion");
        consultaMarcacionesResponse.setRespuestaConsulta("valor de respuestaConsulta");
        consultaMarcacionesResponse.setTipoIdentificacion("valor de tipoIdentificacion");


        Mockito.when(webServiceTemplate.marshalSendAndReceive("http://localhost:8088/consultaMarcaciones", request))
                .thenReturn(consultaMarcacionesResponse);


        SoapConsumer soapConsumer = new SoapConsumer();


        ResponseData responseData = soapConsumer.mapResponseData(consultaMarcacionesResponse);
        request=soapConsumer.createConsultaMarcacionesRequest(extractData);

        assert request.getIdCifin().equals("exampleval1");
        assert request.getCodigoProducto().equals("exampleval2");
        assert request.getTipoIdentificacion().equals("1");
        assert request.getNumeroIdentificacion().equals("1053868313");
        assert request.getPassword().equals("exampleval4");
        assert request.getMotivoConsulta().equals("exampleval5");

        Mono<ResponseData> responseDataMono = Mono.from(Mono.just(responseData));


        StepVerifier.create(responseDataMono)
                .expectNextMatches(responseData1 -> responseData1.getApellido1().equals("valor de apellido1"))
                .verifyComplete();
    }


    private Mono<ResponseData> createSimulatedResponse() {

        ResponseData responseData= ResponseData.builder()
                .CATS("valor de CATS")
                .GMF("valor de GMF")
                .apellido1("valor de apellido1")
                .apellido2("valor de apellido2")
                .ciudadDireccion("valor de ciudadDireccion")
                .codigoCiiu("valor de codigoCiiu")
                .direccion("valor de direccion")
                .entidadCATS("valor de entidadCATS")
                .entidadGMF("valor de entidadGMF")
                .estado("valor de estado")
                .fecha("valor de fecha")
                .fechaExpedicion("valor de fechaExpedicion")
                .genero("valor de genero")
                .generoInconsistencias(true)
                .hora("valor de hora")
                .lugarExpedicion("valor de lugarExpedicion")
                .nombre("valor de nombre")
                .nombre1("valor de nombre1")
                .nombre2("valor de nombre2")
                .numeroIdentificacion("valor de numeroIdentificacion")
                .respuestaConsulta("valor de respuestaConsulta")
                .tipoIdentificacion("valor de tipoIdentificacion")
                .build();

        return Mono.just(responseData);
    }
}