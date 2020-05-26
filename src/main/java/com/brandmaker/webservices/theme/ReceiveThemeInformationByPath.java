
package com.brandmaker.webservices.theme;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for receiveThemeInformationByPath complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="receiveThemeInformationByPath">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="themeName" type="{http://brandmaker.com/webservices/Theme/}themeName" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "receiveThemeInformationByPath", propOrder = {
    "themeName"
})
public class ReceiveThemeInformationByPath {

    @XmlElement(namespace = "http://brandmaker.com/webservices/Theme/")
    protected ThemeName themeName;

    /**
     * Gets the value of the themeName property.
     * 
     * @return
     *     possible object is
     *     {@link ThemeName }
     *     
     */
    public ThemeName getThemeName() {
        return themeName;
    }

    /**
     * Sets the value of the themeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThemeName }
     *     
     */
    public void setThemeName(ThemeName value) {
        this.themeName = value;
    }

}
