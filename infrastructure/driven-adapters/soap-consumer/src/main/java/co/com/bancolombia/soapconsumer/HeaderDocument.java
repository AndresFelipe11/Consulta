package co.com.bancolombia.soapconsumer;

import co.com.bancolombia.soapconsumer.tdc.RequestHeader;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import java.io.IOException;

public class HeaderDocument implements WebServiceMessageCallback {

    private RequestHeader requestHeader;

    public HeaderDocument(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    @Override
    public void doWithMessage(WebServiceMessage message) throws IOException {
        SoapHeader soapHeader = ((SoapMessage)message).getSoapHeader();

        try {
            JAXBContext context = JAXBContext.newInstance(RequestHeader.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(requestHeader, soapHeader.getResult());

        } catch (JAXBException e) {
            throw new IOException("error while marshalling authentication.");
        }
    }

}
