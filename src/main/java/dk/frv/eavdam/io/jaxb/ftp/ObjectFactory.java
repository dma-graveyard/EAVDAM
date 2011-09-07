//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.07 at 03:43:49 PM EEST 
//


package dk.frv.eavdam.io.jaxb.ftp;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dk.frv.eavdam.io.jaxb.ftp package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FtpConfig_QNAME = new QName("http://eavdam.frv.dk/schema", "ftpConfig");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dk.frv.eavdam.io.jaxb.ftp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FtpConfig }
     * 
     */
    public FtpConfig createFtpConfig() {
        return new FtpConfig();
    }

    /**
     * Create an instance of {@link Ftp }
     * 
     */
    public Ftp createFtp() {
        return new Ftp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FtpConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eavdam.frv.dk/schema", name = "ftpConfig")
    public JAXBElement<FtpConfig> createFtpConfig(FtpConfig value) {
        return new JAXBElement<FtpConfig>(_FtpConfig_QNAME, FtpConfig.class, null, value);
    }

}
