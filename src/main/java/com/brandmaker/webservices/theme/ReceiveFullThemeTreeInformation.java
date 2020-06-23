
package com.brandmaker.webservices.theme;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for receiveFullThemeTreeInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="receiveFullThemeTreeInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="themeId" type="{http://brandmaker.com/webservices/Theme/}themeId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "receiveFullThemeTreeInformation", propOrder = {
    "themeId"
})
public class ReceiveFullThemeTreeInformation {

    @XmlElement(namespace = "http://brandmaker.com/webservices/Theme/")
    protected Integer themeId;

    /**
     * Gets the value of the themeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getThemeId() {
        return themeId;
    }

    /**
     * Sets the value of the themeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setThemeId(Integer value) {
        this.themeId = value;
    }

}