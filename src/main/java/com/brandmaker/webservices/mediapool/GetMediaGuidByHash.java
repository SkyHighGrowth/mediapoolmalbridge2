
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getMediaGuidByHash complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getMediaGuidByHash">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mediaGuidByHashData" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMediaGuidByHash", propOrder = {
    "mediaGuidByHashData"
})
public class GetMediaGuidByHash {

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/", required = true)
    protected String mediaGuidByHashData;

    /**
     * Gets the value of the mediaGuidByHashData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaGuidByHashData() {
        return mediaGuidByHashData;
    }

    /**
     * Sets the value of the mediaGuidByHashData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaGuidByHashData(String value) {
        this.mediaGuidByHashData = value;
    }

}
