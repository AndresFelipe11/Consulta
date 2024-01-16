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
    public Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("co.com.bancolombia.soapconsumer.tdc");
        return marshaller;
    }

    @Bean
    public SoapConsumer soapConsumer(Jaxb2Marshaller jaxb2Marshaller){
        SoapConsumer soapClient = new SoapConsumer();
        soapClient.setMarshaller(jaxb2Marshaller);
        soapClient.setUnmarshaller(jaxb2Marshaller);
        soapClient.setWebServiceTemplate(webServiceTemplate(jaxb2Marshaller));
        return soapClient;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller jaxb2Marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(jaxb2Marshaller);
        template.setUnmarshaller(jaxb2Marshaller);
        template.setMessageSender(httpComponentsMessageSender());
        return template;
    }

    @Bean
    public HttpComponentsMessageSender httpComponentsMessageSender() {
        return new HttpComponentsMessageSender();
    }

}
