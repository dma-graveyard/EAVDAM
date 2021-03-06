//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.13 at 09:13:36 AM EET 
//


package dk.frv.eavdam.io.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accessScheme.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="accessScheme">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FATDMA"/>
 *     &lt;enumeration value="RATDMA"/>
 *     &lt;enumeration value="CSTDMA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "accessScheme")
@XmlEnum
public enum AccessScheme {


    /**
     * FATDMA Access Scheme
     * 
     */
    FATDMA,

    /**
     * RATDMA Access Scheme
     * 
     */
    RATDMA,

    /**
     * CSTDMA Access Scheme
     * 
     */
    CSTDMA;

    public String value() {
        return name();
    }

    public static AccessScheme fromValue(String v) {
        return valueOf(v);
    }

}
