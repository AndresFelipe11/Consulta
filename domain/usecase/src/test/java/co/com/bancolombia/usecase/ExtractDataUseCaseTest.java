package co.com.bancolombia.usecase;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.message.BusinessErrorMessage;
import co.com.bancolombia.model.extractdata.ExtractData;
import co.com.bancolombia.model.responsedata.ResponseData;
import co.com.bancolombia.model.responsedata.gateways.ResponseDataRepository;
import co.com.bancolombia.usecase.extractdata.ExtractDataUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

class ExtractDataUseCaseTest {

    @Mock
    ResponseDataRepository responseDataRepository;

    ExtractDataUseCase extractDataUseCase;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        extractDataUseCase = new ExtractDataUseCase(responseDataRepository);

    }

    @Test
    void execute1() throws Exception {
        // Arrange
        String message = "AM2GAF5462020956094136                    11009560941        0000000000000000005120100001 TS000120231220095609413603S0000                                                           55578787872200000001796144310"; // (tu mensaje completo)

        ExtractData extractData = ExtractData.builder()
                .traceTransacion("555787878722")
                .documentNumber("17961443")
                .documentType("1")
                .build();

        ResponseData responseDataMock = ResponseData.builder().build();
        Mockito.when(responseDataRepository.responseData(extractData)).thenReturn(Mono.just(responseDataMock));


        Mono<ResponseData> responseDataMock1= extractDataUseCase.execute(message);

        //lega null
        StepVerifier.create(responseDataMock1).expectComplete();
    }
    @Test
    void execute() throws Exception {
        // Arrange
        String message = "AM2GAF5462020956094136                    11009560941        0000000000000000005120100001 TS000120231220095609413603S0000                                                           5557878787220000000179614431"; // (tu mensaje completo)

        ExtractData extractData = ExtractData.builder()
                .traceTransacion("555787878722")
                .documentNumber("17961443")
                .documentType("1")
                .build();

        ResponseData responseDataMock = ResponseData.builder().build();
        Mockito.when(responseDataRepository.responseData(extractData)).thenReturn(Mono.just(responseDataMock));

        // Act and Assert

//          extractDataUseCase es null

        Mono<ResponseData> responseDataMock1= extractDataUseCase.execute(message);

        //lega null
        StepVerifier.create(responseDataMock1).expectComplete();
    }


    @Test
    void executeInvalidMessage() throws Exception {

        //arrange
        String message = "11009560941        0000000000000000005120100001 TS000120231220095609413603S0000                                                           5557878787220000000179614431";

        ExtractData extractData = ExtractData.builder()
                .traceTransacion("555787878722")
                .documentNumber("17961443")
                .documentType("1")
                .build();

        //act
        Mockito.when(responseDataRepository.responseData(extractData)).thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.INVALID_MESSAGE)));

        // assert

        Mono<ResponseData> result = extractDataUseCase.execute(message);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    void executeInvalidPayload() throws Exception {

        //arrange
        String message = "AM2GAF5462020956094136                    11009560941        0000000000000000005120100001 TS000120231220095609413603S0000                                                           55578787872200000001796144L1";

        ExtractData extractData = ExtractData.builder()
                .traceTransacion("555787878722")
                .documentNumber("17961443")
                .documentType("1")
                .build();

        //act
        Mockito.when(responseDataRepository.responseData(extractData)).thenReturn(Mono.error(new BusinessException(BusinessErrorMessage.INVALID_PAYLOAD)));

        // assert

        Mono<ResponseData> result = extractDataUseCase.execute(message);

        StepVerifier.create(result)
                .expectError(BusinessException.class)
                .verify();
    }
}