
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for webPublishedMediaArgument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="webPublishedMediaArgument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mediaGUID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="publishedFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="publishedTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "webPublishedMediaArgument", propOrder = {
    "fileName",
    "mediaGUID",
    "publishedFrom",
    "publishedTo"
})
public class WebPublishedMediaArgument {

    protected String fileName;
    protected Integer mediaGUID;
    protected String publishedFrom;
    protected String publishedTo;

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the mediaGUID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMediaGUID() {
        return mediaGUID;
    }

    /**
     * Sets the value of the mediaGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMediaGUID(Integer value) {
        this.mediaGUID = value;
    }

    /**
     * Gets the value of the publishedFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublishedFrom() {
        return publishedFrom;
    }

    /**
     * Sets the value of the publishedFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublishedFrom(String value) {
        this.publishedFrom = value;
    }

    /**
     * Gets the value of the publishedTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublishedTo() {
        return publishedTo;
    }

    /**
     * Sets the value of the publishedTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublishedTo(String value) {
        this.publishedTo = value;
    }

}
