package co.com.bancolombia.mq.listener;

import co.com.bancolombia.commons.jms.mq.MQListener;
import co.com.bancolombia.usecase.extractdata.ExtractDataUseCase;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class SampleMQMessageListener {
    private final ExtractDataUseCase extractDataUseCase;

    // For fixed queues
    @MQListener
    public Mono<Void> process(Message message) throws Exception {
        String text = ((TextMessage) message).getText();
        extractDataUseCase.execute(text);
        return Mono.empty();
    }

}
