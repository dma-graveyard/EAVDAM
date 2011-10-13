//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.10.13 at 03:08:04 PM EEST 
//


package dk.frv.eavdam.io.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for directionalAntenna complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="directionalAntenna">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="heading" type="{http://eavdam.frv.dk/schema}heading"/>
 *         &lt;element name="fieldOfViewAngle" type="{http://eavdam.frv.dk/schema}heading"/>
 *         &lt;element name="gain" type="{http://eavdam.frv.dk/schema}nonNegativeDouble"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "directionalAntenna", propOrder = {
    "heading",
    "fieldOfViewAngle",
    "gain"
})
public class DirectionalAntenna {

    protected int heading;
    protected int fieldOfViewAngle;
    protected double gain;

    /**
     * Gets the value of the heading property.
     * 
     */
    public int getHeading() {
        return heading;
    }

    /**
     * Sets the value of the heading property.
     * 
     */
    public void setHeading(int value) {
        this.heading = value;
    }

    /**
     * Gets the value of the fieldOfViewAngle property.
     * 
     */
    public int getFieldOfViewAngle() {
        return fieldOfViewAngle;
    }

    /**
     * Sets the value of the fieldOfViewAngle property.
     * 
     */
    public void setFieldOfViewAngle(int value) {
        this.fieldOfViewAngle = value;
    }

    /**
     * Gets the value of the gain property.
     * 
     */
    public double getGain() {
        return gain;
    }

    /**
     * Sets the value of the gain property.
     * 
     */
    public void setGain(double value) {
        this.gain = value;
    }

}
