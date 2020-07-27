
package com.brandmaker.webservices.mediapool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for findExternalDownloadLinkByMediaGuidResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findExternalDownloadLinkByMediaGuidResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="externalDownloadLinks" type="{http://brandmaker.com/webservices/MediaPool/}externalDownloadLink" maxOccurs="unbounded" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findExternalDownloadLinkByMediaGuidResponse", propOrder = {
    "externalDownloadLinks"
})
public class FindExternalDownloadLinkByMediaGuidResponse {

    @XmlElement(namespace = "http://brandmaker.com/webservices/MediaPool/")
    protected List<ExternalDownloadLink> externalDownloadLinks;

    /**
     * Gets the value of the externalDownloadLinks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalDownloadLinks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalDownloadLinks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExternalDownloadLink }
     * 
     * 
     */
    public List<ExternalDownloadLink> getExternalDownloadLinks() {
        if (externalDownloadLinks == null) {
            externalDownloadLinks = new ArrayList<ExternalDownloadLink>();
        }
        return this.externalDownloadLinks;
    }

}
