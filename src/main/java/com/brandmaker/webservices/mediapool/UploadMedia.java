
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadMedia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadMedia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uploadMediaData" type="{http://brandmaker.com/webservices/MediaPool/}uploadMediaArgument" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadMedia", propOrder = {
    "uploadMediaData"
})
public class UploadMedia {

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/", required = true)
    protected UploadMediaArgument uploadMediaData;

    /**
     * Gets the value of the uploadMediaData property.
     * 
     * @return
     *     possible object is
     *     {@link UploadMediaArgument }
     *     
     */
    public UploadMediaArgument getUploadMediaData() {
        return uploadMediaData;
    }

    /**
     * Sets the value of the uploadMediaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadMediaArgument }
     *     
     */
    public void setUploadMediaData(UploadMediaArgument value) {
        this.uploadMediaData = value;
    }

}
