
package com.brandmaker.webservices.theme;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSortedThemeTreeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSortedThemeTreeResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="theme" type="{http://brandmaker.com/webservices/Theme/}themesResult" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSortedThemeTreeResponse", propOrder = {
    "theme"
})
public class GetSortedThemeTreeResponse {

    @XmlElement(namespace = "http://brandmaker.com/webservices/Theme/")
    protected ThemesResult theme;

    /**
     * Gets the value of the theme property.
     * 
     * @return
     *     possible object is
     *     {@link ThemesResult }
     *     
     */
    public ThemesResult getTheme() {
        return theme;
    }

    /**
     * Sets the value of the theme property.
     * 
     * @param value
     *     allowed object is
     *     {@link ThemesResult }
     *     
     */
    public void setTheme(ThemesResult value) {
        this.theme = value;
    }

}
