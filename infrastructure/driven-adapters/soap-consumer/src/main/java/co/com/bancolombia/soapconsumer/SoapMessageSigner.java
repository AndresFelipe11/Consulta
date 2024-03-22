package co.com.bancolombia.soapconsumer;


import jakarta.xml.bind.annotation.XmlElement;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.CredentialException;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.components.crypto.Merlin;

public class SoapMessageSigner {

    private final String keystoreFile;
    private final String keystoreFormat;
    private final String keystorePassword;
    private final String privateKeyPassword;
    private final String keyEntryAlias;

    private final XMLSignatureFactory signatureFactory;

    public SoapMessageSigner(String keystoreFile, String keystoreFormat, String keystorePassword,
                             String privateKeyPassword, String keyEntryAlias) {

        this.keystoreFile = keystoreFile;
        this.keystoreFormat = keystoreFormat;
        this.keystorePassword = keystorePassword;
        this.privateKeyPassword = privateKeyPassword;
        this.keyEntryAlias = keyEntryAlias;

        // Instantiate a DOM XMLSignatureFactory object to produce the enveloped signature
        signatureFactory = XMLSignatureFactory.getInstance("DOM");
    }

    public Document sign(String signXml) throws XmlSigningException {

//        ImprovedWSSecSignature wssSign = new ImprovedWSSecSignature();
//        wssSign.setSignatureAlgorithm("http://www.w3.org/2000/09/xmldsig#rsa-sha1");
//        wssSign.setDigestAlgo("http://www.w3.org/2000/09/xmldsig#sha1");
//        wssSign.setUseSingleCertificate(true);
//        wssSign.setSigCanonicalization(WSConstants.C14N_EXCL_OMIT_COMMENTS);


        // STEP 1

        // Create a Reference to the document being signed by specifying an empty URI value to represent the entire
        // document. Additionally, specify the SHA256 digest algorithm and use the ENVELOPED Transform
        Reference ref;
        try {


            ref = signatureFactory.newReference("", signatureFactory.newDigestMethod(DigestMethod.SHA1, null),
                    Collections.singletonList(signatureFactory.newTransform(WSConstants.C14N_EXCL_OMIT_COMMENTS, (TransformParameterSpec) null)),
                    null, null);



            // Create the SignedInfo
            SignedInfo signedInfo =
                    signatureFactory.newSignedInfo(signatureFactory.newCanonicalizationMethod("http://www.w3.org/2001/10/xml-exc-c14n#", (C14NMethodParameterSpec) null),
                            signatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                            Collections.singletonList(ref));
            // STEP 2

            // Load the KeyStore and retrieve the signing key and certificate
            KeyStore ks = KeyStore.getInstance(keystoreFormat);

            ks.load(getFileFromResourceAsStream(keystoreFile), keystorePassword.toCharArray());
            KeyStore.PrivateKeyEntry keyEntry =
                    (KeyStore.PrivateKeyEntry) ks.getEntry(keyEntryAlias,
                            new KeyStore.PasswordProtection(privateKeyPassword.toCharArray()));
            X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

            // Create the KeyInfo containing the X509Data
            KeyInfoFactory kif = signatureFactory.getKeyInfoFactory();
          //mostrar elementos del keyinfo
            X509IssuerSerial issuerSerial = kif.newX509IssuerSerial(
                    cert.getIssuerX500Principal().getName(),
                    cert.getSerialNumber()
            );




            List<Object> x509Content = new ArrayList<>();
            x509Content.add(cert.getSubjectX500Principal().getName());
            x509Content.add(cert);
            X509Data xd = kif.newX509Data(Collections.singletonList(issuerSerial));
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));




//            X509Data xd = kif.newX509Data(x509Content);
//            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

            // STEP 3

            // Create an instance of the document that will be signed
            DocumentBuilderFactory dbf = getDocumentBuilderFactory();
            dbf.setNamespaceAware(true);

            //TO IMPL , temp change to be compilable
            Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(signXml.getBytes()));

            // Initialize a DOMSignContext by providing the RSA PrivateKey and identifying the parent element of the resulting
            // XMLSignature
            DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement());

            // Instantiate the XMLSignature object without signing it yet
            XMLSignature signature = signatureFactory.newXMLSignature(signedInfo, ki);

            // Marshal, generate, and sign the enveloped signature
            signature.sign(dsc);

//            WSSecHeader secHeader = new WSSecHeader();
//
//            try {
//                wssSign.build(doc, getCrypto(), secHeader);
//            } catch (WSSecurityException e) {
//                throw new RuntimeException(e);
//            }


            return doc;

        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | ParserConfigurationException |
                 MarshalException | XMLSignatureException | UnrecoverableEntryException | KeyStoreException |
                 IOException | CertificateException | SAXException e) {
            throw new XmlSigningException("Error signing the XML document", e);
        }
    }

    private DocumentBuilderFactory getDocumentBuilderFactory() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf;
    }

    private InputStream getFileFromResourceAsStream(String keystoreFile) throws FileNotFoundException {

        FileInputStream inputStream = new FileInputStream(keystoreFile);
        if (inputStream == null) {
            throw new IllegalArgumentException("El archivo " + keystoreFile + " no se encontró en el directorio de recursos.");
        } else {
            return inputStream;
        }
    }

    public String convertDocumentToString(Document doc) {
        try {

            doc = addTimestamp(doc);
            doc = addCanonicalizationMethod(doc);
            doc = addInclusiveNamespaces(doc);
            doc = addSecurityTokenReference(doc);
            doc = setPrefix(doc);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            // Configuración para eliminar los espacios en blanco
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            // Omitir la inclusión del encabezado XML
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xmlString = writer.getBuffer().toString();
            xmlString.replace("&#13;", "");

            return writer.getBuffer().toString();



        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public Document addTimestamp(Document doc){
        LocalDateTime currentTimeUTC = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expirationTimeUTC = currentTimeUTC.plus(10000000, ChronoUnit.MILLIS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String createdTime = currentTimeUTC.format(formatter);
        String expiresTime = expirationTimeUTC.format(formatter);

        NodeList nl = doc.getElementsByTagName("wsse:Security");
        Element wsseSecurity = (Element) nl.item(0);
        Node signature =wsseSecurity.getChildNodes().item(0);

        Element timestamp = doc.createElement("wsu:Timestamp");

        Element created = doc.createElement("wsu:Created");
        created.setTextContent(createdTime);
        timestamp.appendChild(created);

        Element expires = doc.createElement("wsu:Expires");
        expires.setTextContent(expiresTime);
        timestamp.appendChild(expires);

        wsseSecurity.insertBefore(timestamp,signature);

        return doc;
    }

    public Document addSecurityTokenReference(Document doc) {


        NodeList signature = doc.getElementsByTagName("Signature");
        Element keyinfo = (Element) signature.item(0).getChildNodes().item(2);
        Element X509Data = (Element) keyinfo.getChildNodes().item(0);
        X509Data.setPrefix("ds");

        for (int i = 0; i < X509Data.getChildNodes().getLength(); i++) {
            Element element = (Element) X509Data.getChildNodes().item(i);
            element.setPrefix("ds");
            for (int j = 0; j < element.getChildNodes().getLength(); j++) {
                Element element2 = (Element) element.getChildNodes().item(j);
                element2.setPrefix("ds");
            }
        }



        Node securityTokenReference = doc.createElement("wsse:SecurityTokenReference");
        securityTokenReference.appendChild(X509Data);


        keyinfo.appendChild(securityTokenReference);


        return doc;
    }

    public Document setPrefix (Document doc){

        NodeList signatures = doc.getElementsByTagName("Signature");
        Element signature = (Element) signatures.item(0);
        signature.setPrefix("ds");

        for (int i = 0; i < signature.getChildNodes().getLength(); i++) {
               Element element = (Element) signature.getChildNodes().item(i);
                element.setPrefix("ds");
        }


        NodeList signedInfo = doc.getElementsByTagName("ds:SignedInfo");
        Element signedInfoElement = (Element) signedInfo.item(0);
        signedInfoElement.setPrefix("ds");

        for (int i = 0; i < signedInfoElement.getChildNodes().getLength(); i++) {
            Element element = (Element) signedInfoElement.getChildNodes().item(i);
            element.setPrefix("ds");
        }

        return doc;
    }

    public Document addInclusiveNamespaces (Document doc) {

        NodeList reference = doc.getElementsByTagName("Reference");


        Element transforms = (Element) reference.item(0);
        System.out.println("REFERENCE"+reference.item(0));

        for (int i = 0; i < transforms.getChildNodes().getLength(); i++) {
            Element element = (Element) transforms.getChildNodes().item(i);
            element.setPrefix("ds");
        }

        Element inclusiveNamespaces = doc.createElement("ec:InclusiveNamespaces");
        inclusiveNamespaces.setAttribute("xmlns:ec", "http://www.w3.org/2001/10/xml-exc-c14n#");
        inclusiveNamespaces.setAttribute("PrefixList", "con xsd xsi");

        Element transform = (Element) transforms.getChildNodes().item(0).getChildNodes().item(0);
        transform.setPrefix("ds");
        transform.appendChild(inclusiveNamespaces);



        return doc;
    }



    public Document addCanonicalizationMethod (Document doc) {

        NodeList signedInfo = doc.getElementsByTagName("SignedInfo");
        Element reference = (Element) signedInfo.item(0).getChildNodes().item(2);
        reference.setAttribute("URI", "#id-492781FC73DD98BD0F170985309014510");
        System.out.println("Reference  ---"+reference.getNodeName());
        Element canonicalizationMethod = (Element) signedInfo.item(0).getChildNodes().item(0);

        canonicalizationMethod.setPrefix("ds");
        canonicalizationMethod.setAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");

        Element InclusiveNamespaces = doc.createElement("ec:InclusiveNamespaces");
        InclusiveNamespaces.setAttribute("PrefixList", "con soapenv xsd xsi");
        InclusiveNamespaces.setAttribute("xmlns:ec", "http://www.w3.org/2001/10/xml-exc-c14n#");

        canonicalizationMethod.appendChild(InclusiveNamespaces);

        return doc;
    }



}


