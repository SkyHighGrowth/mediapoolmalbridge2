
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadMetadataArgumentVersion2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadMetadataArgumentVersion2">
 *   &lt;complexContent>
 *     &lt;extension base="{http://brandmaker.com/webservices/MediaPool/v2/}uploadMetadataArgument">
 *       &lt;sequence>
 *         &lt;element name="creatorLogin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadMetadataArgumentVersion2", propOrder = {
    "creatorLogin"
})
public class UploadMetadataArgumentVersion2
    extends UploadMetadataArgument
{

    protected String creatorLogin;

    /**
     * Gets the value of the creatorLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatorLogin() {
        return creatorLogin;
    }

    /**
     * Sets the value of the creatorLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatorLogin(String value) {
        this.creatorLogin = value;
    }

}
