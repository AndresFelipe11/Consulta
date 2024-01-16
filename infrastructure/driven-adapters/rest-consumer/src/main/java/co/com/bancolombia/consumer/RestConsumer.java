//package co.com.bancolombia.consumer;
//
//import co.com.bancolombia.model.extractdata.ExtractData;
//import co.com.bancolombia.model.responsedata.ResponseData;
//import co.com.bancolombia.model.responsedata.gateways.ResponseDataRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Service
//@RequiredArgsConstructor
//public class RestConsumer implements ResponseDataRepository {
//    private final WebClient client;
//
//    @Override
//    public Mono<ResponseData> responseData(ExtractData extractData) {
//        ObjectRequest request = ObjectRequest.builder()
//                .idCifin("exampleval1")
//                .codigoProducto("exampleval2")
//                .motivoConsulta("exampleval2")
//                .password("exampleval2")
//                .numeroIdentificacion(extractData.getDocumentNumber())
//                .tipoIdentificacion(extractData.getDocumentType())
//                .build();
//        return client
//                .post()
//                .uri("/v3/1c742be1-03a2-4321-877c-2cf60e4bb1e6")
////                .uri("/v3/f0d0c3a2-1a43-4a41-b911-bb2c30334123")
//                .body(Mono.just(request), ObjectRequest.class)
//                .retrieve()
//                .bodyToMono(ObjectResponse.class).map(objectResponse -> ResponseData.builder()
//                        .CATS(objectResponse.getCATS())
//                        .GMF(objectResponse.getGMF())
//                        .apellido1(objectResponse.getApellido1())
//                        .apellido2(objectResponse.getApellido2())
//                        .ciudadDireccion(objectResponse.getCiudadDireccion())
//                        .codigoCiiu(objectResponse.getCodigoCiiu())
//                        .direccion(objectResponse.getDireccion())
//                        .entidadCATS(objectResponse.getEntidadCATS())
//                        .entidadGMF(objectResponse.getEntidadGMF())
//                        .estado(objectResponse.getEstado())
//                        .fecha(objectResponse.getFecha())
//                        .fechaExpedicion(objectResponse.getFechaExpedicion())
//                        .genero(objectResponse.getGenero())
//                        .hora(objectResponse.getHora())
//                        .inconsistencias(objectResponse.getInconsistencias().toBuilder()
//                                .codigo(objectResponse.getInconsistencias().getCodigo())
//                                .descripcion(objectResponse.getInconsistencias().getDescripcion())
//                                .build())
//                        .lugarExpedicion(objectResponse.getLugarExpedicion())
//                        .nombre(objectResponse.getNombre())
//                        .nombre1(objectResponse.getNombre1())
//                        .nombre2(objectResponse.getNombre2())
//                        .numeroIdentificacion(objectResponse.getNumeroIdentificacion())
//                        .generoInconsistencias(objectResponse.getGeneroInconsistencias())
//                        .respuestaConsulta(objectResponse.getRespuestaConsulta())
//                        .tipoIdentificacion(objectResponse.getTipoIdentificacion())
//                        .build());
//    }
//}
