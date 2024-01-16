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

        JAXBElement<ConsultaMarcacionesResponse> responseObject =  (JAXBElement<ConsultaMarcacionesResponse>)getWebServiceTemplate().marshalSendAndReceive("http://localhost:8088/consultaMarcaciones", request);

        ResponseData responseData = new ResponseData();
        BeanUtils.copyProperties(responseObject.getValue(), responseData);
        responseData.setInconsistencias(mapInconsistencias(responseObject.getValue().getInconsistencias()));

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
