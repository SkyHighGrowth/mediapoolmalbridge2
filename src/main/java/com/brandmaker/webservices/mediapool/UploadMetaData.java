
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
 *         &lt;element name="uploadMetaData" type="{http://brandmaker.com/webservices/MediaPool/}uploadMetadataArgument"/>
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

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/", required = true)
    protected UploadMetadataArgument uploadMetaData;

    /**
     * Gets the value of the uploadMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link UploadMetadataArgument }
     *     
     */
    public UploadMetadataArgument getUploadMetaData() {
        return uploadMetaData;
    }

    /**
     * Sets the value of the uploadMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadMetadataArgument }
     *     
     */
    public void setUploadMetaData(UploadMetadataArgument value) {
        this.uploadMetaData = value;
    }

}
