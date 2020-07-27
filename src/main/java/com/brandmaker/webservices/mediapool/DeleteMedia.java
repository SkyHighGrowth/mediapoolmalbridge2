
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteMedia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteMedia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deleteMediaData" type="{http://brandmaker.com/webservices/MediaPool/}deleteMediaArgument" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteMedia", propOrder = {
    "deleteMediaData"
})
public class DeleteMedia {

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/", required = true)
    protected DeleteMediaArgument deleteMediaData;

    /**
     * Gets the value of the deleteMediaData property.
     * 
     * @return
     *     possible object is
     *     {@link DeleteMediaArgument }
     *     
     */
    public DeleteMediaArgument getDeleteMediaData() {
        return deleteMediaData;
    }

    /**
     * Sets the value of the deleteMediaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeleteMediaArgument }
     *     
     */
    public void setDeleteMediaData(DeleteMediaArgument value) {
        this.deleteMediaData = value;
    }

}
