//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.12.13 at 09:13:36 AM EET 
//


package dk.frv.eavdam.io.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * A class for storing EAVDAM user specific data.
 * 
 * <p>Java class for eavdamUser complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eavdamUser">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="organizationName" type="{http://eavdam.frv.dk/schema}nonEmptyString"/>
 *         &lt;element name="countryID" type="{http://eavdam.frv.dk/schema}countryID"/>
 *         &lt;element name="phone" type="{http://eavdam.frv.dk/schema}intlPhoneNumber"/>
 *         &lt;element name="fax" type="{http://eavdam.frv.dk/schema}intlPhoneNumber"/>
 *         &lt;element name="www" type="{http://eavdam.frv.dk/schema}httpUrl"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contact" type="{http://eavdam.frv.dk/schema}person"/>
 *         &lt;element name="technicalContact" type="{http://eavdam.frv.dk/schema}person"/>
 *         &lt;element name="visitingAddress" type="{http://eavdam.frv.dk/schema}address"/>
 *         &lt;element name="postalAddress" type="{http://eavdam.frv.dk/schema}address"/>
 *         &lt;any processContents='skip' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eavdamUser", propOrder = {
    "organizationName",
    "countryID",
    "phone",
    "fax",
    "www",
    "description",
    "contact",
    "technicalContact",
    "visitingAddress",
    "postalAddress",
    "any"
})
public class EavdamUser {

    @XmlElement(required = true)
    protected String organizationName;
    @XmlElement(required = true)
    protected String countryID;
    @XmlElement(required = true, nillable = true)
    protected String phone;
    @XmlElement(required = true, nillable = true)
    protected String fax;
    @XmlElement(required = true, nillable = true)
    protected String www;
    @XmlElement(required = true, nillable = true)
    protected String description;
    @XmlElement(required = true, nillable = true)
    protected Person contact;
    @XmlElement(required = true, nillable = true)
    protected Person technicalContact;
    @XmlElement(required = true, nillable = true)
    protected Address visitingAddress;
    @XmlElement(required = true, nillable = true)
    protected Address postalAddress;
    @XmlAnyElement
    protected List<Element> any;

    /**
     * Gets the value of the organizationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the value of the organizationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationName(String value) {
        this.organizationName = value;
    }

    /**
     * Gets the value of the countryID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryID() {
        return countryID;
    }

    /**
     * Sets the value of the countryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryID(String value) {
        this.countryID = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the www property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWww() {
        return www;
    }

    /**
     * Sets the value of the www property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWww(String value) {
        this.www = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setContact(Person value) {
        this.contact = value;
    }

    /**
     * Gets the value of the technicalContact property.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getTechnicalContact() {
        return technicalContact;
    }

    /**
     * Sets the value of the technicalContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setTechnicalContact(Person value) {
        this.technicalContact = value;
    }

    /**
     * Gets the value of the visitingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getVisitingAddress() {
        return visitingAddress;
    }

    /**
     * Sets the value of the visitingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setVisitingAddress(Address value) {
        this.visitingAddress = value;
    }

    /**
     * Gets the value of the postalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getPostalAddress() {
        return postalAddress;
    }

    /**
     * Sets the value of the postalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setPostalAddress(Address value) {
        this.postalAddress = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * 
     * 
     */
    public List<Element> getAny() {
        if (any == null) {
            any = new ArrayList<Element>();
        }
        return this.any;
    }

}
