
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getMediaDetailsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getMediaDetailsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://brandmaker.com/webservices/MediaPool/v2/}getMediaDetailsResult" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMediaDetailsResponse", propOrder = {
    "_return"
})
public class GetMediaDetailsResponse {

    @XmlElement(name = "return", namespace = "http://brandmaker.com/webservices/MediaPool/v2/")
    protected GetMediaDetailsResult _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link GetMediaDetailsResult }
     *     
     */
    public GetMediaDetailsResult getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetMediaDetailsResult }
     *     
     */
    public void setReturn(GetMediaDetailsResult value) {
        this._return = value;
    }

}
