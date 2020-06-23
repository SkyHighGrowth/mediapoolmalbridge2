
package com.brandmaker.webservices.theme;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createTheme complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createTheme">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="theme" type="{http://brandmaker.com/webservices/Theme/}theme" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createTheme", propOrder = {
    "theme"
})
public class CreateTheme {

    @XmlElement(namespace = "http://brandmaker.com/webservices/Theme/")
    protected Theme theme;

    /**
     * Gets the value of the theme property.
     * 
     * @return
     *     possible object is
     *     {@link Theme }
     *     
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Sets the value of the theme property.
     * 
     * @param value
     *     allowed object is
     *     {@link Theme }
     *     
     */
    public void setTheme(Theme value) {
        this.theme = value;
    }

}
