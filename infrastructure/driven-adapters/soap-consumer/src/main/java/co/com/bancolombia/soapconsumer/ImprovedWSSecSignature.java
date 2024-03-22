package co.com.bancolombia.soapconsumer;

import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.message.WSSecSignature;

import javax.xml.crypto.dsig.Reference;
import java.util.List;

public class ImprovedWSSecSignature extends WSSecSignature {

    // Set to false to enable strict layout compatbility
    // Defaults to wss4j behavior
    private boolean prependSignature = true;

    public ImprovedWSSecSignature() {
        super();
    }

    public ImprovedWSSecSignature(WSSConfig config) {
        super(config);
    }

    @Override
    public void computeSignature(List<Reference> referenceList) throws WSSecurityException {
        computeSignature(referenceList, prependSignature, null);
    }

    public boolean getPrependSignature() {
        return prependSignature;
    }

    public void setPrependSignature(boolean prependSignature) {
        this.prependSignature = prependSignature;
    }
}