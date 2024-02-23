package co.com.bancolombia.mq.listener;

import co.com.bancolombia.usecase.extractdata.ExtractDataUseCase;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SampleMQMessageListenerTest {

    @Mock
    private TextMessage textMessage;
    @Mock
    ExtractDataUseCase extractDataUseCase;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processTest() throws Exception {
        // Mock de Message
        TextMessage textMessage = mock(TextMessage.class);
        when(textMessage.getText()).thenReturn("Mensaje de prueba");

        SampleMQMessageListener sampleMQMessageListener = new SampleMQMessageListener(extractDataUseCase);

        // Llamada al método bajo prueba
        Mono<Void> result = sampleMQMessageListener.process(textMessage);

        // Verificación de que ExtractDataUseCase.execute fue llamado con el texto del mensaje
        verify(extractDataUseCase).execute("Mensaje de prueba");

        // Verificación de que el resultado es Mono.empty()
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

}
