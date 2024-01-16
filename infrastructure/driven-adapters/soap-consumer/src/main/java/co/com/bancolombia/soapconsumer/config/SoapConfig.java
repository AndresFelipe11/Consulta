package co.com.bancolombia.soapconsumer.config;

import co.com.bancolombia.soapconsumer.SoapConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("co.com.bancolombia.soapconsumer.tdc");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller jaxb2Marshaller) {
        WebServiceTemplate template = new WebServiceTemplate(jaxb2Marshaller);
        // Configura otras propiedades del WebServiceTemplate si es necesario
        // Por ejemplo, timeouts, interceptores, etc.
        template.setMessageSender(new HttpComponentsMessageSender());
        return template;
    }

    @Bean
    public SoapConsumer soapClient(Jaxb2Marshaller jaxb2Marshaller, WebServiceTemplate webServiceTemplate) {
        SoapConsumer soapClient = new SoapConsumer();
        soapClient.setMarshaller(jaxb2Marshaller);
        soapClient.setUnmarshaller(jaxb2Marshaller);
        soapClient.setWebServiceTemplate(webServiceTemplate);
        return soapClient;
    }
}
