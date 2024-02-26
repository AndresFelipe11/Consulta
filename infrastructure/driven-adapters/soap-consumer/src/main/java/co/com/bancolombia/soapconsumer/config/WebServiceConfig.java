package co.com.bancolombia.soapconsumer.config;


import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class WebServiceConfig {

    @Value("${soap.url}")
    private String webServiceUrl;

    @Value("${soap.username}")
    private String username;

    @Value("${soap.password}")
    private String password;

    @Value("${keystore.path}")
    private String keyStorePath;

    @Value("${keystore.password}")
    private String keyStorePassword;

    @Bean
    public RestTemplate restTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Add Basic Authentication
        restTemplate.getInterceptors().add(new BasicAuthInterceptor(username, password));

        // Load JKS
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream inputStream = new FileInputStream(keyStorePath)) {
            keyStore.load(inputStream, keyStorePassword.toCharArray());
        }

        // Set up SSL context with the loaded keystore
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(keyStore, new TrustSelfSignedStrategy())
                .build();

        // Set up HttpClient with SSL context
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        // Set up RestTemplate to use HttpClient with SSL
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        new HttpComponentsClientHttpRequestFactory();

        return restTemplate;
    }
}
