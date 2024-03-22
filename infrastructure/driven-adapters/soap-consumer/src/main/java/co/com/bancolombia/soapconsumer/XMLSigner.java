package co.com.bancolombia.soapconsumer;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.components.crypto.CredentialException;
import org.apache.ws.security.components.crypto.Merlin;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecTimestamp;
import org.apache.xmlbeans.XmlObject;
import org.springframework.xml.XmlException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class XMLSigner {

    private final String keystoreFile;
    private final String keystoreFormat;
    private final String keystorePassword;
    private final String privateKeyPassword;
    private final String keyEntryAlias;

    public XMLSigner(String keystoreFile, String keystoreFormat, String keystorePassword, String privateKeyPassword, String keyEntryAlias) {
        this.keystoreFile = keystoreFile;
        this.keystoreFormat = keystoreFormat;
        this.keystorePassword = keystorePassword;
        this.privateKeyPassword = privateKeyPassword;
        this.keyEntryAlias = keyEntryAlias;
    }

    public Document sign(String signXml)  {


        try {

        DocumentBuilderFactory dbf = getDocumentBuilderFactory();
        dbf.setNamespaceAware(true);
        InputSource is = new InputSource(new StringReader(signXml));
        Document doc = dbf.newDocumentBuilder().parse(is);
        addRootnode(doc);

        WSSecHeader secHeader = new WSSecHeader();
        secHeader.insertSecurityHeader(doc);

        StringWriter writer = null;



      List<StringToStringMap> parts = new ArrayList<StringToStringMap>();


        ImprovedWSSecSignature wssSign = new ImprovedWSSecSignature();

        wssSign.setSignatureAlgorithm("http://www.w3.org/2000/09/xmldsig#rsa-sha1");
        wssSign.setDigestAlgo("http://www.w3.org/2000/09/xmldsig#sha1");
        wssSign.setUseSingleCertificate(true);
        wssSign.setSigCanonicalization(WSConstants.C14N_EXCL_OMIT_COMMENTS);
        wssSign.setKeyIdentifierType(WSConstants.ISSUER_SERIAL);
        wssSign.setPrependSignature(true);
        wssSign.setUserInfo(keyEntryAlias, privateKeyPassword);

//        wssSign.setParts(parts);





        wssSign.build(doc, getCrypto(), secHeader);
        WSSecTimestamp timestamp = new WSSecTimestamp();


        writer = new StringWriter();
        serialize(doc, writer);


        timestamp.setTimeToLive(10000);
        timestamp.build(doc, secHeader);



//        addTimestamp(doc);
        String sigValue = digistValue(doc);
            System.out.println("sigValue  ---> "+sigValue);
        String xml= convertDocumentToString(doc);
        System.out.println(xml);

            return doc;
        }
        catch (Exception e) {

            throw new RuntimeException(e);
        }
    }



    public void addRootnode (Document doc) {

        NodeList nl = doc.getElementsByTagName("soapenv:Envelope");
        Element root = (Element) nl.item(0);
        System.out.println("root" + root.getNodeName());
        System.out.println("root?"+doc.getDocumentElement());


//        Element root = doc.createElement(rootName);
//        doc.appendChild(root);
    }

    public String digistValue(Document doc){

        NodeList nl = doc.getElementsByTagName("ds:Signature");
        Element Signature = (Element) nl.item(0);
        Element SignatureValue = (Element) Signature.getElementsByTagName("ds:SignatureValue").item(0);
//        System.out.println("SignatureValue"+SignatureValue.getTextContent());

//        System.out.println("Signature"+Signature.getNodeName());


        return SignatureValue.getTextContent();

    }





    public  String convertDocumentToString(Document document) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter stringWriter = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        return stringWriter.toString();
    }


    public static void serialize(Document dom, Writer writer) throws IOException {
        serialize( dom.getDocumentElement(), writer);
    }

    public static void serialize(Element elm, Writer writer) throws IOException {
        try {
            XmlObject xmlObject = XmlObject.Factory.parse(elm);
            xmlObject.save(writer);
        } catch (XmlException e) {
            throw new IOException(e.toString());
        } catch (org.apache.xmlbeans.XmlException e) {
            throw new RuntimeException(e);
        }
    }


    private DocumentBuilderFactory getDocumentBuilderFactory() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf;
    }


    public Merlin getCrypto() {

        Properties properties = new Properties();


        properties.put(Merlin.KEYSTORE_FILE, keystoreFile);
        properties.put(Merlin.KEYSTORE_ALIAS, keyEntryAlias);
        properties.put(Merlin.KEYSTORE_PASSWORD, keystorePassword);
        properties.put(Merlin.KEYSTORE_TYPE, keystoreFormat);
        properties.put(Merlin.KEYSTORE_PRIVATE_PASSWORD, privateKeyPassword);


        try {
            return new KeyMaterialCrypto(properties);
        } catch (CredentialException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static class KeyMaterialCrypto extends Merlin {
        private KeyMaterialCrypto(Properties properties) throws CredentialException, IOException {
            super(properties);
        }
    }

}
