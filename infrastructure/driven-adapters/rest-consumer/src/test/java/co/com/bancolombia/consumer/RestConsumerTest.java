//package co.com.bancolombia.consumer;
//
//
//import co.com.bancolombia.model.extractdata.ExtractData;
//import okhttp3.mockwebserver.MockResponse;
//import okhttp3.mockwebserver.MockWebServer;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.test.StepVerifier;
//import java.io.IOException;
//
//
//public class RestConsumerTest {
//
//    private static RestConsumer restConsumer;
//
//    private static MockWebServer mockBackEnd;
//
//
//    @BeforeAll
//    static void setUp() throws IOException {
//        mockBackEnd = new MockWebServer();
//        mockBackEnd.start();
//        var webClient = WebClient.builder().baseUrl(mockBackEnd.url("/").toString()).build();
//        restConsumer = new RestConsumer(webClient);
//    }
//
//    @AfterAll
//    static void tearDown() throws IOException {
//
//        mockBackEnd.shutdown();
//    }
//    @Test
//    @DisplayName("Validate the function RestConsumer.")
//    void validateTestRestConsumer() {
//
//        ExtractData extractData = ExtractData.builder()
//                .traceTransacion("123456789")
//                .documentNumber("1053868313")
//                .documentType("1")
//                .build();
//
//        mockBackEnd.enqueue(new MockResponse()
//                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .setResponseCode(HttpStatus.OK.value())
//                .setBody("{\n" +
//                        "  \"CATS\": \"valor de CATS\",\n" +
//                        "  \"GMF\": \"valor de GMF\",\n" +
//                        "  \"apellido1\": \"valor de apellido1\",\n" +
//                        "  \"apellido2\": \"valor de apellido2\",\n" +
//                        "  \"ciudadDireccion\": \"valor de ciudadDireccion\",\n" +
//                        "  \"codigoCiiu\": \"valor de codigoCiiu\",\n" +
//                        "  \"direccion\": \"valor de direccion\",\n" +
//                        "  \"entidadCATS\": \"valor de entidadCATS\",\n" +
//                        "  \"entidadGMF\": \"valor de entidadGMF\",\n" +
//                        "  \"estado\": \"valor de estado\",\n" +
//                        "  \"fecha\": \"valor de fecha\",\n" +
//                        "  \"fechaExpedicion\": \"valor de fechaExpedicion\",\n" +
//                        "  \"genero\": \"valor de genero\",\n" +
//                        "  \"hora\": \"valor de hora\",\n" +
//                        "  \"inconsistencias\": {\n" +
//                        "    \"codigo\": \"valor de codigo de inconsistencias\",\n" +
//                        "    \"descripcion\": \"valor de descripcion de inconsistencias\"\n" +
//                        "  },\n" +
//                        "  \"lugarExpedicion\": \"valor de lugarExpedicion\",\n" +
//                        "  \"nombre\": \"valor de nombre\",\n" +
//                        "  \"nombre1\": \"valor de nombre1\",\n" +
//                        "  \"nombre2\": \"valor de nombre2\",\n" +
//                        "  \"numeroIdentificacion\": \"valor de numeroIdentificacion\",\n" +
//                        "  \"generoInconsistencias\": true,\n" +
//                        "  \"respuestaConsulta\": \"valor de respuestaConsulta\",\n" +
//                        "  \"tipoIdentificacion\": \"valor de tipoIdentificacion\"\n" +
//                        "}\n"));
//        var response = restConsumer.responseData(extractData);
//
//        StepVerifier.create(response)
//                .expectNextMatches(objectResponse -> objectResponse.getApellido1().equals("valor de apellido1"))
//
//                .verifyComplete();
//    }
//}