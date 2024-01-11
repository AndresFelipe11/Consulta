package co.com.bancolombia.usecase.extractdata;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.extractdata.ExtractData;
import co.com.bancolombia.model.extractdata.gateways.ExtractDataRepository;
import co.com.bancolombia.model.responsedata.ResponseData;
import co.com.bancolombia.model.responsedata.gateways.ResponseDataRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.*;

@RequiredArgsConstructor
public class ExtractDataUseCase {

private final ResponseDataRepository responseDataRepository;

    public Mono<ResponseData> execute(String message) {
        if (message.length() > 207) {
            String cargaUtil = message.substring(Math.min(message.length(), 180));
            System.out.println("cargaUtil: " + cargaUtil);
            String traceTransacion = cargaUtil.substring(0, 12);
            System.out.println("traceTransacion: " + traceTransacion);
            //condicion de que carga util debe ser numerica
            if (cargaUtil.substring(12).matches("[0-9]+")) {
                int documentNumberInt = Integer.parseInt(cargaUtil.substring(12, 27));
                String documentNumber = String.valueOf(documentNumberInt);
                System.out.println("documentNumber: " + documentNumber);
                int documentTypeInt = Integer.parseInt(Function.<String>identity()
                        .andThen(str -> str.length() >= 29 ? str.substring(27, 29) : str.substring(27))
                        .apply(cargaUtil));
                String documentType = String.valueOf(documentTypeInt);
                System.out.println("documentType: " + documentType);
                ExtractData extractData = ExtractData.builder()
                        .traceTransacion(traceTransacion)
                        .documentNumber(documentNumber)
                        .documentType(documentType)
                        .build();

                return responseDataRepository.responseData(extractData);
            }else {
                return Mono.error(new BusinessException(INVALID_PAYLOAD));
            }
        } else {
            return Mono.error(new BusinessException(INVALID_MESSAGE));
        }
    }


}
