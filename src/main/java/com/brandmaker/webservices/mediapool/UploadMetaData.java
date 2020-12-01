
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadMetaData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadMetaData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uploadMetaData" type="{http://brandmaker.com/webservices/MediaPool/v2/}uploadMetadataArgumentVersion2" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadMetaData", propOrder = {
    "uploadMetaData"
})
public class UploadMetaData {

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/v2/", required = true)
    protected UploadMetadataArgumentVersion2 uploadMetaData;

    /**
     * Gets the value of the uploadMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link UploadMetadataArgumentVersion2 }
     *     
     */
    public UploadMetadataArgumentVersion2 getUploadMetaData() {
        return uploadMetaData;
    }

    /**
     * Sets the value of the uploadMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadMetadataArgumentVersion2 }
     *     
     */
    public void setUploadMetaData(UploadMetadataArgumentVersion2 value) {
        this.uploadMetaData = value;
    }

}
