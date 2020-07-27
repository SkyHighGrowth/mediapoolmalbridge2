
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadMediaVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadMediaVersion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uploadMediaVersionData" type="{http://brandmaker.com/webservices/MediaPool/}uploadMediaVersionArgument" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadMediaVersion", propOrder = {
    "uploadMediaVersionData"
})
public class UploadMediaVersion {

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/", required = true)
    protected UploadMediaVersionArgument uploadMediaVersionData;

    /**
     * Gets the value of the uploadMediaVersionData property.
     * 
     * @return
     *     possible object is
     *     {@link UploadMediaVersionArgument }
     *     
     */
    public UploadMediaVersionArgument getUploadMediaVersionData() {
        return uploadMediaVersionData;
    }

    /**
     * Sets the value of the uploadMediaVersionData property.
     * 
     * @param value
     *     allowed object is
     *     {@link UploadMediaVersionArgument }
     *     
     */
    public void setUploadMediaVersionData(UploadMediaVersionArgument value) {
        this.uploadMediaVersionData = value;
    }

}
