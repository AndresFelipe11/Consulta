package co.com.bancolombia.soapconsumer;

import co.com.bancolombia.model.extractdata.ExtractData;
import co.com.bancolombia.model.responsedata.ResponseData;
import co.com.bancolombia.model.responsedata.gateways.ResponseDataRepository;
import co.com.bancolombia.soapconsumer.tdc.ConsultaMarcacionesRequest;
import co.com.bancolombia.soapconsumer.tdc.ConsultaMarcacionesResponse;
import co.com.bancolombia.soapconsumer.tdc.Inconsistencias;
import jakarta.xml.bind.JAXBElement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import reactor.core.publisher.Mono;


@Service
@Primary
@RequiredArgsConstructor
public class SoapConsumer extends WebServiceGatewaySupport implements ResponseDataRepository {

    @Override
    public Mono<ResponseData> responseData(ExtractData extractData) {

        ConsultaMarcacionesRequest request = createConsultaMarcacionesRequest(extractData);

        ConsultaMarcacionesResponse consultaMarcacionesResponse = (ConsultaMarcacionesResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8088/consultaMarcaciones", request);

        ResponseData responseData = ResponseData.builder()
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

        return Mono.just(responseData);
    }

    private ConsultaMarcacionesRequest createConsultaMarcacionesRequest(ExtractData extractData) {
        ConsultaMarcacionesRequest request = new ConsultaMarcacionesRequest();
        request.setIdCifin("exampleval1");
        request.setCodigoProducto("exampleval2");
        request.setTipoIdentificacion(extractData.getDocumentType());
        request.setNumeroIdentificacion(extractData.getDocumentNumber());
        request.setPassword("exampleval4");
        request.setMotivoConsulta("exampleval5");
        return request;
    }

    private co.com.bancolombia.model.inconsistenciasdto.Inconsistencias mapInconsistencias(Inconsistencias inconsistencias) {
        co.com.bancolombia.model.inconsistenciasdto.Inconsistencias inconsistenciasDto =
                new co.com.bancolombia.model.inconsistenciasdto.Inconsistencias();
        BeanUtils.copyProperties(inconsistencias, inconsistenciasDto);
        return inconsistenciasDto;
    }
}
