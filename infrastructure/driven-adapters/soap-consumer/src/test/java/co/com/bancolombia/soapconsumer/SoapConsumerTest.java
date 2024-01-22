package co.com.bancolombia.soapconsumer;

import co.com.bancolombia.model.extractdata.ExtractData;
import co.com.bancolombia.model.responsedata.ResponseData;
import co.com.bancolombia.soapconsumer.tdc.ConsultaMarcacionesRequest;
import co.com.bancolombia.soapconsumer.tdc.ConsultaMarcacionesResponse;
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

        Mono<ResponseData> simulatedResponse = createSimulatedResponse();

        ConsultaMarcacionesResponse consultaMarcacionesResponse = new ConsultaMarcacionesResponse();
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

        Mockito.when(soapConsumer.responseData(extractData)).thenReturn(simulatedResponse);


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