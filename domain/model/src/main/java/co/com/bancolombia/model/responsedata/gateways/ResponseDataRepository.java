package co.com.bancolombia.model.responsedata.gateways;

import co.com.bancolombia.model.extractdata.ExtractData;
import co.com.bancolombia.model.responsedata.ResponseData;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public interface ResponseDataRepository {

    Mono<ResponseData> responseData(ExtractData extractData) throws Exception;

}
