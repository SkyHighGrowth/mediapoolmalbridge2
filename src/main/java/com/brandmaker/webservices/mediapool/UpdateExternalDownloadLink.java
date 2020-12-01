
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateExternalDownloadLink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateExternalDownloadLink">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="link" type="{http://brandmaker.com/webservices/MediaPool/v2/}externalDownloadLink" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateExternalDownloadLink", propOrder = {
    "link"
})
public class UpdateExternalDownloadLink {

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/v2/")
    protected ExternalDownloadLink link;

    /**
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalDownloadLink }
     *     
     */
    public ExternalDownloadLink getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalDownloadLink }
     *     
     */
    public void setLink(ExternalDownloadLink value) {
        this.link = value;
    }

}
