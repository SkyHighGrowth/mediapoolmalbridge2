
package com.brandmaker.webservices.mediapool;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for uploadMetadataArgument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadMetadataArgument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addAssociations" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="approve" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="approveDescription" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="articleDescription" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="articleNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="associations" type="{http://brandmaker.com/webservices/MediaPool/}themeDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="countryCodes" type="{http://brandmaker.com/webservices/MediaPool/}code" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="creatorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="designationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fontChecked" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="freeField1" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField10" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField11" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField12" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField13" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField14" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField15" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField16" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField17" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField18" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField19" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField2" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField20" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField3" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField4" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField5" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField6" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField7" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField8" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="freeField9" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hideIfNotValid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isHires" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="keepIfEmtpy" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="keyword" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="license" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="licenseName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mediaGuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mediaHash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mediaIsins" type="{http://brandmaker.com/webservices/MediaPool/}isin" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mediaNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mediaTitle" type="{http://brandmaker.com/webservices/MediaPool/}languageItem" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="official" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="platform" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="programVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="selectedAffiliate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="show" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strict" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="structuredKeywords" type="{http://brandmaker.com/webservices/MediaPool/}structuredKeywords" minOccurs="0"/>
 *         &lt;element name="validDateFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validDateTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="virtualDbName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadMetadataArgument", propOrder = {
    "addAssociations",
    "approve",
    "approveDescription",
    "articleDescription",
    "articleNumber",
    "associations",
    "countryCodes",
    "creatorName",
    "description",
    "designationType",
    "fileName",
    "fontChecked",
    "freeField1",
    "freeField10",
    "freeField11",
    "freeField12",
    "freeField13",
    "freeField14",
    "freeField15",
    "freeField16",
    "freeField17",
    "freeField18",
    "freeField19",
    "freeField2",
    "freeField20",
    "freeField3",
    "freeField4",
    "freeField5",
    "freeField6",
    "freeField7",
    "freeField8",
    "freeField9",
    "hideIfNotValid",
    "isHires",
    "keepIfEmtpy",
    "keyword",
    "license",
    "licenseName",
    "mediaGuid",
    "mediaHash",
    "mediaIsins",
    "mediaNumber",
    "mediaTitle",
    "official",
    "platform",
    "programVersion",
    "selectedAffiliate",
    "show",
    "strict",
    "structuredKeywords",
    "validDateFrom",
    "validDateTo",
    "virtualDbName"
})
@XmlSeeAlso({
    GetMediaDetailsResult.class
})
public class UploadMetadataArgument {

    protected Boolean addAssociations;
    protected String approve;
    @XmlElement(nillable = true)
    protected List<LanguageItem> approveDescription;
    @XmlElement(nillable = true)
    protected List<LanguageItem> articleDescription;
    protected String articleNumber;
    @XmlElement(nillable = true)
    protected List<ThemeDto> associations;
    @XmlElement(nillable = true)
    protected List<Code> countryCodes;
    protected String creatorName;
    @XmlElement(nillable = true)
    protected List<LanguageItem> description;
    protected String designationType;
    protected String fileName;
    protected String fontChecked;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField1;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField10;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField11;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField12;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField13;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField14;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField15;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField16;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField17;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField18;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField19;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField2;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField20;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField3;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField4;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField5;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField6;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField7;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField8;
    @XmlElement(nillable = true)
    protected List<LanguageItem> freeField9;
    protected String hideIfNotValid;
    protected String isHires;
    protected Boolean keepIfEmtpy;
    @XmlElement(nillable = true)
    protected List<LanguageItem> keyword;
    protected String license;
    protected String licenseName;
    protected String mediaGuid;
    protected String mediaHash;
    @XmlElement(nillable = true)
    protected List<Isin> mediaIsins;
    protected String mediaNumber;
    @XmlElement(nillable = true)
    protected List<LanguageItem> mediaTitle;
    protected Boolean official;
    protected String platform;
    protected String programVersion;
    protected String selectedAffiliate;
    protected String show;
    protected Boolean strict;
    protected StructuredKeywords structuredKeywords;
    protected String validDateFrom;
    protected String validDateTo;
    protected String virtualDbName;

    /**
     * Gets the value of the addAssociations property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAddAssociations() {
        return addAssociations;
    }

    /**
     * Sets the value of the addAssociations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAddAssociations(Boolean value) {
        this.addAssociations = value;
    }

    /**
     * Gets the value of the approve property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApprove() {
        return approve;
    }

    /**
     * Sets the value of the approve property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApprove(String value) {
        this.approve = value;
    }

    /**
     * Gets the value of the approveDescription property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the approveDescription property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApproveDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getApproveDescription() {
        if (approveDescription == null) {
            approveDescription = new ArrayList<LanguageItem>();
        }
        return this.approveDescription;
    }

    /**
     * Gets the value of the articleDescription property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the articleDescription property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArticleDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getArticleDescription() {
        if (articleDescription == null) {
            articleDescription = new ArrayList<LanguageItem>();
        }
        return this.articleDescription;
    }

    /**
     * Gets the value of the articleNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticleNumber() {
        return articleNumber;
    }

    /**
     * Sets the value of the articleNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticleNumber(String value) {
        this.articleNumber = value;
    }

    /**
     * Gets the value of the associations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the associations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssociations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ThemeDto }
     * 
     * 
     */
    public List<ThemeDto> getAssociations() {
        if (associations == null) {
            associations = new ArrayList<ThemeDto>();
        }
        return this.associations;
    }

    /**
     * Gets the value of the countryCodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the countryCodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCountryCodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Code }
     * 
     * 
     */
    public List<Code> getCountryCodes() {
        if (countryCodes == null) {
            countryCodes = new ArrayList<Code>();
        }
        return this.countryCodes;
    }

    /**
     * Gets the value of the creatorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * Sets the value of the creatorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatorName(String value) {
        this.creatorName = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getDescription() {
        if (description == null) {
            description = new ArrayList<LanguageItem>();
        }
        return this.description;
    }

    /**
     * Gets the value of the designationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesignationType() {
        return designationType;
    }

    /**
     * Sets the value of the designationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesignationType(String value) {
        this.designationType = value;
    }

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
     * Gets the value of the fontChecked property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontChecked() {
        return fontChecked;
    }

    /**
     * Sets the value of the fontChecked property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontChecked(String value) {
        this.fontChecked = value;
    }

    /**
     * Gets the value of the freeField1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField1() {
        if (freeField1 == null) {
            freeField1 = new ArrayList<LanguageItem>();
        }
        return this.freeField1;
    }

    /**
     * Gets the value of the freeField10 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField10 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField10().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField10() {
        if (freeField10 == null) {
            freeField10 = new ArrayList<LanguageItem>();
        }
        return this.freeField10;
    }

    /**
     * Gets the value of the freeField11 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField11 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField11().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField11() {
        if (freeField11 == null) {
            freeField11 = new ArrayList<LanguageItem>();
        }
        return this.freeField11;
    }

    /**
     * Gets the value of the freeField12 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField12 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField12().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField12() {
        if (freeField12 == null) {
            freeField12 = new ArrayList<LanguageItem>();
        }
        return this.freeField12;
    }

    /**
     * Gets the value of the freeField13 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField13 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField13().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField13() {
        if (freeField13 == null) {
            freeField13 = new ArrayList<LanguageItem>();
        }
        return this.freeField13;
    }

    /**
     * Gets the value of the freeField14 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField14 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField14().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField14() {
        if (freeField14 == null) {
            freeField14 = new ArrayList<LanguageItem>();
        }
        return this.freeField14;
    }

    /**
     * Gets the value of the freeField15 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField15 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField15().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField15() {
        if (freeField15 == null) {
            freeField15 = new ArrayList<LanguageItem>();
        }
        return this.freeField15;
    }

    /**
     * Gets the value of the freeField16 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField16 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField16().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField16() {
        if (freeField16 == null) {
            freeField16 = new ArrayList<LanguageItem>();
        }
        return this.freeField16;
    }

    /**
     * Gets the value of the freeField17 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField17 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField17().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField17() {
        if (freeField17 == null) {
            freeField17 = new ArrayList<LanguageItem>();
        }
        return this.freeField17;
    }

    /**
     * Gets the value of the freeField18 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField18 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField18().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField18() {
        if (freeField18 == null) {
            freeField18 = new ArrayList<LanguageItem>();
        }
        return this.freeField18;
    }

    /**
     * Gets the value of the freeField19 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField19 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField19().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField19() {
        if (freeField19 == null) {
            freeField19 = new ArrayList<LanguageItem>();
        }
        return this.freeField19;
    }

    /**
     * Gets the value of the freeField2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField2() {
        if (freeField2 == null) {
            freeField2 = new ArrayList<LanguageItem>();
        }
        return this.freeField2;
    }

    /**
     * Gets the value of the freeField20 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField20 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField20().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField20() {
        if (freeField20 == null) {
            freeField20 = new ArrayList<LanguageItem>();
        }
        return this.freeField20;
    }

    /**
     * Gets the value of the freeField3 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField3 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField3().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField3() {
        if (freeField3 == null) {
            freeField3 = new ArrayList<LanguageItem>();
        }
        return this.freeField3;
    }

    /**
     * Gets the value of the freeField4 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField4 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField4().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField4() {
        if (freeField4 == null) {
            freeField4 = new ArrayList<LanguageItem>();
        }
        return this.freeField4;
    }

    /**
     * Gets the value of the freeField5 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField5 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField5().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField5() {
        if (freeField5 == null) {
            freeField5 = new ArrayList<LanguageItem>();
        }
        return this.freeField5;
    }

    /**
     * Gets the value of the freeField6 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField6 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField6().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField6() {
        if (freeField6 == null) {
            freeField6 = new ArrayList<LanguageItem>();
        }
        return this.freeField6;
    }

    /**
     * Gets the value of the freeField7 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField7 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField7().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField7() {
        if (freeField7 == null) {
            freeField7 = new ArrayList<LanguageItem>();
        }
        return this.freeField7;
    }

    /**
     * Gets the value of the freeField8 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField8 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField8().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField8() {
        if (freeField8 == null) {
            freeField8 = new ArrayList<LanguageItem>();
        }
        return this.freeField8;
    }

    /**
     * Gets the value of the freeField9 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeField9 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeField9().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getFreeField9() {
        if (freeField9 == null) {
            freeField9 = new ArrayList<LanguageItem>();
        }
        return this.freeField9;
    }

    /**
     * Gets the value of the hideIfNotValid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHideIfNotValid() {
        return hideIfNotValid;
    }

    /**
     * Sets the value of the hideIfNotValid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHideIfNotValid(String value) {
        this.hideIfNotValid = value;
    }

    /**
     * Gets the value of the isHires property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHires() {
        return isHires;
    }

    /**
     * Sets the value of the isHires property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHires(String value) {
        this.isHires = value;
    }

    /**
     * Gets the value of the keepIfEmtpy property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepIfEmtpy() {
        return keepIfEmtpy;
    }

    /**
     * Sets the value of the keepIfEmtpy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepIfEmtpy(Boolean value) {
        this.keepIfEmtpy = value;
    }

    /**
     * Gets the value of the keyword property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyword property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyword().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getKeyword() {
        if (keyword == null) {
            keyword = new ArrayList<LanguageItem>();
        }
        return this.keyword;
    }

    /**
     * Gets the value of the license property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicense() {
        return license;
    }

    /**
     * Sets the value of the license property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicense(String value) {
        this.license = value;
    }

    /**
     * Gets the value of the licenseName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseName() {
        return licenseName;
    }

    /**
     * Sets the value of the licenseName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseName(String value) {
        this.licenseName = value;
    }

    /**
     * Gets the value of the mediaGuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaGuid() {
        return mediaGuid;
    }

    /**
     * Sets the value of the mediaGuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaGuid(String value) {
        this.mediaGuid = value;
    }

    /**
     * Gets the value of the mediaHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaHash() {
        return mediaHash;
    }

    /**
     * Sets the value of the mediaHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaHash(String value) {
        this.mediaHash = value;
    }

    /**
     * Gets the value of the mediaIsins property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mediaIsins property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMediaIsins().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Isin }
     * 
     * 
     */
    public List<Isin> getMediaIsins() {
        if (mediaIsins == null) {
            mediaIsins = new ArrayList<Isin>();
        }
        return this.mediaIsins;
    }

    /**
     * Gets the value of the mediaNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaNumber() {
        return mediaNumber;
    }

    /**
     * Sets the value of the mediaNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaNumber(String value) {
        this.mediaNumber = value;
    }

    /**
     * Gets the value of the mediaTitle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mediaTitle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMediaTitle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LanguageItem }
     * 
     * 
     */
    public List<LanguageItem> getMediaTitle() {
        if (mediaTitle == null) {
            mediaTitle = new ArrayList<LanguageItem>();
        }
        return this.mediaTitle;
    }

    /**
     * Gets the value of the official property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOfficial() {
        return official;
    }

    /**
     * Sets the value of the official property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOfficial(Boolean value) {
        this.official = value;
    }

    /**
     * Gets the value of the platform property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Sets the value of the platform property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlatform(String value) {
        this.platform = value;
    }

    /**
     * Gets the value of the programVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgramVersion() {
        return programVersion;
    }

    /**
     * Sets the value of the programVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgramVersion(String value) {
        this.programVersion = value;
    }

    /**
     * Gets the value of the selectedAffiliate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectedAffiliate() {
        return selectedAffiliate;
    }

    /**
     * Sets the value of the selectedAffiliate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectedAffiliate(String value) {
        this.selectedAffiliate = value;
    }

    /**
     * Gets the value of the show property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShow() {
        return show;
    }

    /**
     * Sets the value of the show property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShow(String value) {
        this.show = value;
    }

    /**
     * Gets the value of the strict property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStrict() {
        return strict;
    }

    /**
     * Sets the value of the strict property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStrict(Boolean value) {
        this.strict = value;
    }

    /**
     * Gets the value of the structuredKeywords property.
     * 
     * @return
     *     possible object is
     *     {@link StructuredKeywords }
     *     
     */
    public StructuredKeywords getStructuredKeywords() {
        return structuredKeywords;
    }

    /**
     * Sets the value of the structuredKeywords property.
     * 
     * @param value
     *     allowed object is
     *     {@link StructuredKeywords }
     *     
     */
    public void setStructuredKeywords(StructuredKeywords value) {
        this.structuredKeywords = value;
    }

    /**
     * Gets the value of the validDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidDateFrom() {
        return validDateFrom;
    }

    /**
     * Sets the value of the validDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidDateFrom(String value) {
        this.validDateFrom = value;
    }

    /**
     * Gets the value of the validDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidDateTo() {
        return validDateTo;
    }

    /**
     * Sets the value of the validDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidDateTo(String value) {
        this.validDateTo = value;
    }

    /**
     * Gets the value of the virtualDbName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVirtualDbName() {
        return virtualDbName;
    }

    /**
     * Sets the value of the virtualDbName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVirtualDbName(String value) {
        this.virtualDbName = value;
    }

}
