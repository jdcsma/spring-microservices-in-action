package jun.microservices.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@Entity
@Table(name = "license")
@XmlRootElement(name = "license")
@XmlAccessorType(XmlAccessType.NONE)
@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class License implements Serializable {

    private static final long serialVersionUID = 1;

    private String licenseId;

    private String organizationId;

    private String organizationName;

    private String contactName;

    private String contactPhone;

    private String contactEmail;

    private String productName;

    private String licenseType;

    private int licenseMax;

    private int licenseAllocated;

    private String comment;

    @Id
    @Column(name = "license_id")
    @XmlElement
    @JsonProperty
    public String getLicenseId() {
        return this.licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    @Basic
    @Column(name = "organization_id")
    @XmlElement
    @JsonProperty
    public String getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Transient
    @XmlElement
    @JsonProperty
    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Transient
    @XmlElement
    @JsonProperty
    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Transient
    @XmlElement
    @JsonProperty
    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Transient
    @XmlElement
    @JsonProperty
    public String getContactEmail() {
        return this.contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Basic
    @Column(name = "product_name")
    @XmlElement
    @JsonProperty
    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "license_type")
    @XmlElement
    @JsonProperty
    public String getLicenseType() {
        return this.licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    @Basic
    @Column(name = "license_max")
    @XmlElement
    @JsonProperty
    public Integer getLicenseMax() {
        return this.licenseMax;
    }

    public void setLicenseMax(Integer licenseMax) {
        this.licenseMax = licenseMax;
    }

    @Basic
    @Column(name = "license_allocated")
    @XmlElement
    @JsonProperty
    public Integer getLicenseAllocated() {
        return this.licenseAllocated;
    }

    public void setLicenseAllocated(Integer licenseAllocated) {
        this.licenseAllocated = licenseAllocated;
    }

    @Basic
    @Column(name = "comment")
    @XmlElement
    @JsonProperty
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
