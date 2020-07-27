
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for webPublishedMedia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="webPublishedMedia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="webPublishedMediaData" type="{http://brandmaker.com/webservices/MediaPool/}webPublishedMediaArgument" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "webPublishedMedia", propOrder = {
    "webPublishedMediaData"
})
public class WebPublishedMedia {

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/")
    protected WebPublishedMediaArgument webPublishedMediaData;

    /**
     * Gets the value of the webPublishedMediaData property.
     * 
     * @return
     *     possible object is
     *     {@link WebPublishedMediaArgument }
     *     
     */
    public WebPublishedMediaArgument getWebPublishedMediaData() {
        return webPublishedMediaData;
    }

    /**
     * Sets the value of the webPublishedMediaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebPublishedMediaArgument }
     *     
     */
    public void setWebPublishedMediaData(WebPublishedMediaArgument value) {
        this.webPublishedMediaData = value;
    }

}
