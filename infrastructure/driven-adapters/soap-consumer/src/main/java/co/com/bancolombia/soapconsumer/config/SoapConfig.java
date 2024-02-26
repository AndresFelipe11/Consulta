//package co.com.bancolombia.soapconsumer.config;
//
//import co.com.bancolombia.soapconsumer.SoapConsumer;
//
//import org.apache.hc.client5.http.classic.HttpClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.http.client.support.BasicAuthenticationInterceptor;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.ws.client.core.WebServiceTemplate;
//import org.springframework.ws.transport.http.HttpComponentsMessageSender;
//
//import javax.net.ssl.SSLContext;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyStore;
//import java.util.Base64;
//
//import org.apache.http.ssl.SSLContexts;
//import org.apache.http.ssl.SSLContextBuilder;
//
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//
//
//@Configuration
//public class SoapConfig {
//
//    @Value("${keystore.path}")
//    private String keyStorePath;
//
//    @Value("${keystore.password}")
//    private String keyStorePassword;
//
//    @Value("${soap.username}")
//    private String username;
//
//    @Value("${soap.password}")
//    private String password;
//
//    @Value("${soap.url}")
//    private String webServiceUrl;
//
////    @Bean
////    public Jaxb2Marshaller marshaller(){
////        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
////        marshaller.setContextPath("co.com.bancolombia.soapconsumer.tdc");
////        return marshaller;
////    }
////
////    @Bean
////    public SoapConsumer soapConsumer(Jaxb2Marshaller jaxb2Marshaller){
////        SoapConsumer soapClient = new SoapConsumer();
////        soapClient.setMarshaller(jaxb2Marshaller);
////        soapClient.setUnmarshaller(jaxb2Marshaller);
////        soapClient.setWebServiceTemplate(webServiceTemplate(jaxb2Marshaller));
////        return soapClient;
////    }
////
////    @Bean
////    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller jaxb2Marshaller) {
////        WebServiceTemplate template = new WebServiceTemplate();
////        template.setMarshaller(jaxb2Marshaller);
////        template.setUnmarshaller(jaxb2Marshaller);
////        template.setMessageSender(httpComponentsMessageSender());
////        return template;
////    }
////
////    @Bean
////    public HttpComponentsMessageSender httpComponentsMessageSender() {
////        return new HttpComponentsMessageSender();
////    }
//
//    @Bean
//    public RestTemplate restTemplate() throws Exception {
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Add Basic Authentication
//        ClientHttpRequestInterceptor interceptor = new BasicAuthenticationInterceptor(username, password);
//        restTemplate.getInterceptors().add(interceptor);
//
//        String auth = username + ":" + password;
//        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
//        String authHeader = "Basic " + new String(encodedAuth);
//        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.TEXT_XML);
//        headers.setConnection("Keep-Alive");
//        headers.add("SOAPAction", "");
//        headers.add("Authorization", authHeader);
//
//
//        KeyStore keyStore = KeyStore.getInstance("JKS");
//        try (InputStream inputStream = new FileInputStream(keyStorePath)) {
//            keyStore.load(inputStream, keyStorePassword.toCharArray());
//        }
//
//        // Add Custom Headers
//        SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(keyStore, null).build();
//
//        CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//
//        // Add any custom headers if needed
//        restTemplate.setInterceptors(java.util.List.of((request, body, execution) -> {
//            request.getHeaders().addAll(headers);
//            return execution.execute(request, body);
//        }));
//
//        // Load JKS
//
//
//
////        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory((HttpClient) httpClient);
////        restTemplate.setRequestFactory(factory);
//
//        return restTemplate;
//    }
//
//    }
//
