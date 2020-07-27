
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getMediaDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getMediaDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mediaDetailsData" type="{http://brandmaker.com/webservices/MediaPool/}getMediaDetailsArgument" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMediaDetails", propOrder = {
    "mediaDetailsData"
})
public class GetMediaDetails {

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/", required = true)
    protected GetMediaDetailsArgument mediaDetailsData;

    /**
     * Gets the value of the mediaDetailsData property.
     * 
     * @return
     *     possible object is
     *     {@link GetMediaDetailsArgument }
     *     
     */
    public GetMediaDetailsArgument getMediaDetailsData() {
        return mediaDetailsData;
    }

    /**
     * Sets the value of the mediaDetailsData property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetMediaDetailsArgument }
     *     
     */
    public void setMediaDetailsData(GetMediaDetailsArgument value) {
        this.mediaDetailsData = value;
    }

}
