//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.07 at 03:43:47 PM EEST 
//


package dk.frv.eavdam.io.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for aisFixedStationStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="aisFixedStationStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OPERATIVE"/>
 *     &lt;enumeration value="OLD"/>
 *     &lt;enumeration value="PLANNED"/>
 *     &lt;enumeration value="SIMULATED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "aisFixedStationStatus")
@XmlEnum
public enum AisFixedStationStatus {


    /**
     * Indicator that the station is in operative use.
     * 
     */
    OPERATIVE,

    /**
     * Indicator that the station is not in operative use anymore and exists only as a history record.
     * 
     */
    OLD,

    /**
     * Indicator that the station is planned but is not in operational use.
     * 
     */
    PLANNED,

    /**
     * The station is a simulation.
     * 
     */
    SIMULATED;

    public String value() {
        return name();
    }

    public static AisFixedStationStatus fromValue(String v) {
        return valueOf(v);
    }

}