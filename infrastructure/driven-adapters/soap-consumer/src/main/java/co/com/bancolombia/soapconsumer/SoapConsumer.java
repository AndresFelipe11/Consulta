package co.com.bancolombia.soapconsumer;

import co.com.bancolombia.model.extractdata.ExtractData;
import co.com.bancolombia.model.responsedata.ResponseData;
import co.com.bancolombia.model.responsedata.gateways.ResponseDataRepository;
import co.com.bancolombia.soapconsumer.tdc.ConsultaMarcacionesRequest;
import co.com.bancolombia.soapconsumer.tdc.ConsultaMarcacionesResponse;
import co.com.bancolombia.soapconsumer.tdc.Inconsistencias;
import co.com.bancolombia.soapconsumer.tdc.RequestHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import reactor.core.publisher.Mono;

import javax.xml.namespace.QName;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;


@Service
@Primary
@RequiredArgsConstructor
public class SoapConsumer extends WebServiceGatewaySupport implements ResponseDataRepository {
    @Value("${soap.username}")
    private String username;

    @Value("${soap.password}")
    private String password;

    @Override
    public Mono<ResponseData> responseData(ExtractData extractData) {

        ConsultaMarcacionesRequest request = createConsultaMarcacionesRequest(extractData);

        RequestHeader requestHeader = mapRequestHeader(username, password);

        ConsultaMarcacionesResponse consultaMarcacionesResponse = (ConsultaMarcacionesResponse)
                getWebServiceTemplate().marshalSendAndReceive
                        ("https://miportafoliouat.transunion.co/ConsultasMarcacionesWS/services/ConsultaMarcaciones",
                                request, new HeaderDocument(requestHeader));


//        WebServiceMessageCallback requestCallback = message -> {
//            SoapMessage soapMessage = (SoapMessage) message;
//            SoapHeader soapHeader = soapMessage.getSoapHeader();
//
//            String auth = username + ":" + password;
//            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
//            String authHeader = "Basic " + new String(encodedAuth);
//
//            QName authQName = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Authorization", "soapenv");
//            SoapHeaderElement authElement = soapHeader.addHeaderElement(authQName);
//            authElement.setText(authHeader);
//            System.out.println("authElement: " + authElement);
//            Iterator<SoapHeaderElement> headerElements = soapHeader.examineAllHeaderElements();
//            while (headerElements.hasNext()) {
//                SoapHeaderElement headerElement = headerElements.next();
//                QName name = headerElement.getName();
//                String textContent = headerElement.getText();
//
//                System.out.println("Nombre del elemento: " + name);
//                System.out.println("Contenido del elemento: " + textContent);
//            }
//        };
//
//
//        ConsultaMarcacionesResponse consultaMarcacionesResponse = (ConsultaMarcacionesResponse) getWebServiceTemplate()
//                .marshalSendAndReceive("https://miportafoliouat.transunion.co/ConsultasMarcacionesWS/services/ConsultaMarcaciones", request, requestCallback);

        ResponseData response = mapResponseData(consultaMarcacionesResponse);

         return Mono.just(response);
        }

    protected RequestHeader mapRequestHeader(String username, String password){
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setUsername(username);
        requestHeader.setPassword(password);
        return requestHeader;
    }
    private String encodeBase64(String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes());
    }

    public ResponseData mapResponseData(ConsultaMarcacionesResponse consultaMarcacionesResponse) {
        return ResponseData.builder()
                .CATS(consultaMarcacionesResponse.getCATS())
                .GMF(consultaMarcacionesResponse.getGMF())
                .apellido1(consultaMarcacionesResponse.getApellido1())
                .apellido2(consultaMarcacionesResponse.getApellido2())
                .ciudadDireccion(consultaMarcacionesResponse.getCiudadDireccion())
                .codigoCiiu(consultaMarcacionesResponse.getCodigoCiiu())
                .direccion(consultaMarcacionesResponse.getDireccion())
                .entidadCATS(consultaMarcacionesResponse.getEntidadCATS())
                .entidadGMF(consultaMarcacionesResponse.getEntidadGMF())
                .estado(consultaMarcacionesResponse.getEstado())
                .fecha(consultaMarcacionesResponse.getFecha())
                .fechaExpedicion(consultaMarcacionesResponse.getFechaExpedicion())
                .genero(consultaMarcacionesResponse.getGenero())
                .generoInconsistencias(consultaMarcacionesResponse.isGeneroInconsistencias())
                .hora(consultaMarcacionesResponse.getHora())
                .lugarExpedicion(consultaMarcacionesResponse.getLugarExpedicion())
                .nombre(consultaMarcacionesResponse.getNombre())
                .nombre1(consultaMarcacionesResponse.getNombre1())
                .nombre2(consultaMarcacionesResponse.getNombre2())
                .numeroIdentificacion(consultaMarcacionesResponse.getNumeroIdentificacion())
                .respuestaConsulta(consultaMarcacionesResponse.getRespuestaConsulta())
                .tipoIdentificacion(consultaMarcacionesResponse.getTipoIdentificacion())
                .inconsistencias(mapInconsistencias(consultaMarcacionesResponse.getInconsistencias()))
                .build();
    }


    public ConsultaMarcacionesRequest createConsultaMarcacionesRequest(ExtractData extractData) {
        ConsultaMarcacionesRequest request = new ConsultaMarcacionesRequest();
        request.setIdCifin("5557");
        request.setCodigoProducto("207502");
        request.setTipoIdentificacion(extractData.getDocumentType());
        request.setNumeroIdentificacion(extractData.getDocumentNumber());
        request.setPassword("Blm93rrangel.");
        request.setMotivoConsulta("22");
        return request;
    }

    private co.com.bancolombia.model.inconsistenciasdto.Inconsistencias mapInconsistencias(Inconsistencias inconsistencias) {
        co.com.bancolombia.model.inconsistenciasdto.Inconsistencias inconsistenciasDto =
                new co.com.bancolombia.model.inconsistenciasdto.Inconsistencias();
        inconsistenciasDto.setCodigo(inconsistencias.getCodigo());
        inconsistenciasDto.setDescripcion(inconsistencias.getDescripcion());
        return inconsistenciasDto;
    }
}

