
package com.brandmaker.webservices.theme;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ThemeWebServiceFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ThemeWebServiceFault">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="usedMediaIds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="web2printIds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThemeWebServiceFault", propOrder = {
    "usedMediaIds",
    "web2PrintIds",
    "message"
})
public class ThemeWebServiceFault {

    protected String usedMediaIds;
    @XmlElement(name = "web2printIds")
    protected String web2PrintIds;
    protected String message;

    /**
     * Gets the value of the usedMediaIds property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsedMediaIds() {
        return usedMediaIds;
    }

    /**
     * Sets the value of the usedMediaIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsedMediaIds(String value) {
        this.usedMediaIds = value;
    }

    /**
     * Gets the value of the web2PrintIds property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeb2PrintIds() {
        return web2PrintIds;
    }

    /**
     * Sets the value of the web2PrintIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeb2PrintIds(String value) {
        this.web2PrintIds = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

}
