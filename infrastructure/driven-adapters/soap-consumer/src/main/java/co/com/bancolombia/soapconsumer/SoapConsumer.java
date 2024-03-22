package co.com.bancolombia.soapconsumer;
import co.com.bancolombia.model.extractdata.ExtractData;
import co.com.bancolombia.model.responsedata.ResponseData;
import co.com.bancolombia.model.responsedata.gateways.ResponseDataRepository;
import co.com.bancolombia.soapconsumer.tdc.ConsultaMarcacionesRequest;
import co.com.bancolombia.soapconsumer.tdc.ConsultaMarcacionesResponse;
import co.com.bancolombia.soapconsumer.tdc.Inconsistencias;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import reactor.core.publisher.Mono;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import java.util.Base64;

@Service
@Configuration
@Primary
@RequiredArgsConstructor
public class SoapConsumer extends WebServiceGatewaySupport implements ResponseDataRepository {
    @Value("${soap.username}")
    private String username;
    @Value("${soap.password}")
    private String password;
    @Value("${soap.url}")
    private String url;
    @Value("${keystore.path}")
    private String keystoreFile;
    @Value("${keystore.format}")
    private String keystoreFormat;
    @Value("${keystore.password}")
    private String keystorePassword;
    @Value("${keystore.alias}")
    private String keyEntryAlias;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Mono<ResponseData> responseData(ExtractData extractData) throws Exception {

        ConsultaMarcacionesRequest request = createConsultaMarcacionesRequest(extractData);
//        String requestXML2 = "<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"></wsse:Security>";
//        String requestXML3 = "";
        String requestXML = "";

        XMLSigner xmlSigner = new XMLSigner(keystoreFile, keystoreFormat, keystorePassword, keystorePassword, keyEntryAlias);
        requestXML = convertToSOAPMessage(request);

        String requestXMLL = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://consultasmarcaciones.transunion.com\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <con:consultaMarcaciones soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "         <p_parametros xsi:type=\"dto:ParametrosDTO\" xmlns:dto=\"http://dto.consultasmarcaciones.transunion.com\">\n" +
                "            <codigoProducto xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">5557</codigoProducto>\n" +
                "            <idCifin xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">207502</idCifin>\n" +
                "            <motivoConsulta xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">22</motivoConsulta>\n" +
                "            <numeroIdentificacion xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">14245994</numeroIdentificacion>\n" +
                "            <password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">BLM93rrangel.</password>\n" +
                "            <secuenciaConsulta xsi:type=\"soapenc:long\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"></secuenciaConsulta>\n" +
                "            <tipoIdentificacion xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">1</tipoIdentificacion>\n" +
                "         </p_parametros>\n" +
                "      </con:consultaMarcaciones>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        Document doc = xmlSigner.sign(requestXMLL);
        System.out.println("doc"+doc.getDocumentElement().getTagName());
//        System.out.println(requestXML2);

//        SoapMessageSigner soapMessageSigner = new SoapMessageSigner(keystoreFile, keystoreFormat, keystorePassword, keystorePassword, keyEntryAlias);
//
//        try {
//            Document doc1 =soapMessageSigner.sign(requestXML2);
//            requestXML3 = soapMessageSigner.convertDocumentToString(doc1);
//            requestXML = convertToSOAPMessage(request);
//            Document doc = xmlSigner.sign(requestXML);
////            System.out.println(requestXML);
//        } catch (XmlSigningException e) {
//            throw new RuntimeException(e);
//        }

        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders headers = new HttpHeaders();
        headers.setConnection("Keep-Alive");
        headers.add("SOAPAction", "");
        headers.add("Authorization", authHeader);

            HttpEntity<String> httpEntity = new HttpEntity<>(requestXML, headers);

//            ResponseEntity<ConsultaMarcacionesResponse> responseEntity = restTemplate.exchange(
//                    url,
//                    HttpMethod.POST,
//                    httpEntity,
//                    ConsultaMarcacionesResponse.class
//            );
//
//
//            System.out.println(responseEntity.getBody());



//        ConsultaMarcacionesResponse consultaMarcacionesResponse = responseEntity.getBody();


//        ResponseData response = mapResponseData(consultaMarcacionesResponse);

        ResponseData responseData = new ResponseData();

         return Mono.just(responseData);
        }



    private String convertToSOAPMessage(ConsultaMarcacionesRequest request) {

        StringBuilder soapMessage = new StringBuilder();
        soapMessage.append("<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://consultasmarcaciones.transunion.com\">\n");
        soapMessage.append("<soapenv:Header>\n");
//        soapMessage.append(requestXML).append("\n");
        soapMessage.append("</soapenv:Header>\n");
        soapMessage.append("   <soapenv:Body>\n");
        soapMessage.append("      <con:consultaMarcaciones soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n");
        soapMessage.append("         <p_parametros xsi:type=\"dto:ParametrosDTO\" xmlns:dto=\"http://dto.consultasmarcaciones.transunion.com\">\n");
        soapMessage.append("            <codigoProducto xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">").append(request.getCodigoProducto()).append("</codigoProducto>\n");
        soapMessage.append("            <idCifin xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">").append(request.getIdCifin()).append("</idCifin>\n");
        soapMessage.append("            <motivoConsulta xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">").append(request.getMotivoConsulta()).append("</motivoConsulta>\n");
        soapMessage.append("            <numeroIdentificacion xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">").append(request.getNumeroIdentificacion()).append("</numeroIdentificacion>\n");
        soapMessage.append("            <password xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">").append(request.getPassword()).append("</password>\n");
        soapMessage.append("            <secuenciaConsulta xsi:type=\"soapenc:long\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"></secuenciaConsulta>\n");
        soapMessage.append("            <tipoIdentificacion xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">").append(request.getTipoIdentificacion()).append("</tipoIdentificacion>\n");
        soapMessage.append("         </p_parametros>\n");
        soapMessage.append("      </con:consultaMarcaciones>\n");
        soapMessage.append("   </soapenv:Body>\n");
        soapMessage.append("</soapenv:Envelope>");

        return soapMessage.toString();
    }

    public static Document convertStringToDocument(String soapString) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(soapString));
            return builder.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        request.setIdCifin("207502");
        request.setCodigoProducto("5557");
        request.setTipoIdentificacion(extractData.getDocumentType());
        request.setNumeroIdentificacion(extractData.getDocumentNumber());
        request.setPassword("BLM93rrangel.");
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



