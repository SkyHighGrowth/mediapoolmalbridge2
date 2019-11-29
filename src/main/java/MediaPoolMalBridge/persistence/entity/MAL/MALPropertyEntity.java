package MediaPoolMalBridge.persistence.entity.MAL;

import MediaPoolMalBridge.clients.MAL.properties.client.model.MALProperty;
import MediaPoolMalBridge.persistence.entity.AbstractEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "mal_property",
        indexes = { @Index(columnList = "property_id, updated"),
                    @Index( columnList = "brand, updated" ) },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"property_id"})})
public class MALPropertyEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "property_id")
    private String propertyId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "url")
    private String url;

    @Column(name = "vanity_url")
    private String vanityUrl;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "brand")
    private String brand;

    @Column(name = "parent_brand")
    private String parentBrand;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "md5_checksum")
    private String md5Checksum;

    @Column(name = "status")
    private String status = "1";

    @Column(name = "primary_property_photo")
    private int primaryPropertyImage;

    @Column( name = "created" )
    @CreationTimestamp
    private LocalDateTime created;

    @Version
    @Column( name = "updated" )
    @UpdateTimestamp
    private LocalDateTime updated;

    public MALPropertyEntity() {
    }

    public MALPropertyEntity(final MALProperty malProperty) {
        this.propertyId = malProperty.getPropertyId();
        this.name = malProperty.getName();
        this.address = malProperty.getAddress();
        this.address2 = malProperty.getAddress2();
        this.state = malProperty.getState();
        this.zip = malProperty.getZip();
        this.city = malProperty.getCity();
        this.country = malProperty.getCountry();
        this.url = malProperty.getUrl();
        this.vanityUrl = malProperty.getVanityUrl();
        this.telephone = malProperty.getTelephone();
        this.brand = malProperty.getBrand();
        this.parentBrand = malProperty.getParentBrand();
        this.latitude = malProperty.getLatitude();
        this.longitude = malProperty.getLongitude();
        this.primaryPropertyImage = malProperty.getPrimaryPropertyImage();
        this.md5Checksum = malProperty.getMd5Checksum();
        this.status = malProperty.getStatus();
    }

    public void update(final MALProperty malProperty) {
        this.name = malProperty.getName();
        this.address = malProperty.getAddress();
        this.address2 = malProperty.getAddress2();
        this.state = malProperty.getState();
        this.zip = malProperty.getZip();
        this.city = malProperty.getCity();
        this.country = malProperty.getCountry();
        this.url = malProperty.getUrl();
        this.vanityUrl = malProperty.getVanityUrl();
        this.telephone = malProperty.getTelephone();
        this.brand = malProperty.getBrand();
        this.parentBrand = malProperty.getParentBrand();
        this.latitude = malProperty.getLatitude();
        this.longitude = malProperty.getLongitude();
        this.primaryPropertyImage = malProperty.getPrimaryPropertyImage();
        this.md5Checksum = malProperty.getMd5Checksum();
        this.status = malProperty.getStatus();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
