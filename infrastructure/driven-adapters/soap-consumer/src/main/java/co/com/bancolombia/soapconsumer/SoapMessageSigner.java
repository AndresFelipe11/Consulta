package co.com.bancolombia.soapconsumer;

import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.WSSecSignature;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class SoapMessageSigner {

    public static Document signSoapMessage(String soapMessage, String keystorePath, String keystorePassword, String keyAlias) throws Exception {
        // Load the SOAP XML into a Document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        byte[] a =soapMessage.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream b = new ByteArrayInputStream(a);
        Document doc = db.parse(b);

        // Create a WSSecHeader. This will be where the security information is added.
        WSSecHeader secHeader = new WSSecHeader(doc);
        secHeader.insertSecurityHeader();

        // Configure the WSSecSignature
        WSSecSignature sign = new WSSecSignature(doc);
        sign.setUserInfo(keyAlias, keystorePassword);
        sign.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);

        // Load the keystore
        java.security.KeyStore keystore = java.security.KeyStore.getInstance("JKS");
        try (java.io.FileInputStream fis = new java.io.FileInputStream(keystorePath)) {
            keystore.load(fis, keystorePassword.toCharArray());
        }

        // Set the parts of the message you want to sign
//        sign.set(Collections.singletonList(new WSEncryptionPart(WSConstants.TIMESTAMP_TOKEN_LN, WSConstants.WSU_NS, "Element")));

        // Sign the SOAP message
//        sign.prepare((Crypto) keystore);
        sign.appendBSTElementToHeader();
//        sign.appendSignatureToHeader(secHeader);

        return doc;
    }
}
