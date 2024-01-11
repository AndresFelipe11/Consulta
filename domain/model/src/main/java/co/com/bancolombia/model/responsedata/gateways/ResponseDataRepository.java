package co.com.bancolombia.model.responsedata.gateways;

import co.com.bancolombia.model.extractdata.ExtractData;
import co.com.bancolombia.model.responsedata.ResponseData;
import reactor.core.publisher.Mono;

public interface ResponseDataRepository {

    Mono<ResponseData> responseData(ExtractData extractData);

}
