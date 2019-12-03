package MediaPoolMalBridge.clients.MAL.properties.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Part of {@link MALGetPropertiesResponse}
 */
public class MALProperty {

    @SerializedName("property_id")
    private String propertyId;

    private String name;

    private String address;

    @SerializedName("address_2")
    private String address2;

    private String state;

    private String zip;

    private String city;

    private String country;

    private String url;

    @SerializedName("vanity_url")
    private String vanityUrl;

    private String telephone;

    private String brand;

    @SerializedName("parent_brand")
    private String parentBrand;

    private String latitude;

    private String longitude;

    @SerializedName("primary_property_image")
    private int primaryPropertyImage;

    @SerializedName("md5_checksum")
    private String md5Checksum;

    private String status;


    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVanityUrl() {
        return vanityUrl;
    }

    public void setVanityUrl(String vanityUrl) {
        this.vanityUrl = vanityUrl;
    }

    public int getPrimaryPropertyImage() {
        return primaryPropertyImage;
    }

    public void setPrimaryPropertyImage(int primaryPropertyImage) {
        this.primaryPropertyImage = primaryPropertyImage;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getParentBrand() {
        return parentBrand;
    }

    public void setParentBrand(String parentBrand) {
        this.parentBrand = parentBrand;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMd5Checksum() {
        return md5Checksum;
    }

    public void setMd5Checksum(String md5Checksum) {
        this.md5Checksum = md5Checksum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
