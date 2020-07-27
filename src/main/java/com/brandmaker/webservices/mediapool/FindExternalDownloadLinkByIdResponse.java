
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for findExternalDownloadLinkByIdResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findExternalDownloadLinkByIdResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalDownloadLink" type="{http://brandmaker.com/webservices/MediaPool/}externalDownloadLink" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findExternalDownloadLinkByIdResponse", propOrder = {
    "externalDownloadLink"
})
public class FindExternalDownloadLinkByIdResponse {

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/")
    protected ExternalDownloadLink externalDownloadLink;

    /**
     * Gets the value of the externalDownloadLink property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalDownloadLink }
     *     
     */
    public ExternalDownloadLink getExternalDownloadLink() {
        return externalDownloadLink;
    }

    /**
     * Sets the value of the externalDownloadLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalDownloadLink }
     *     
     */
    public void setExternalDownloadLink(ExternalDownloadLink value) {
        this.externalDownloadLink = value;
    }

}
