package jun.microservices.licensingservice.services;

import jun.microservices.licensingservice.clients.OrganizationDiscoveryClient;
import jun.microservices.licensingservice.clients.OrganizationFeignClient;
import jun.microservices.licensingservice.clients.OrganizationRestTemplateClient;
import jun.microservices.licensingservice.config.ServiceConfig;
import jun.microservices.licensingservice.dto.Organization;
import jun.microservices.licensingservice.entities.License;
import jun.microservices.licensingservice.repositories.LicenseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class LicenseServiceImpl implements LicenseService {

    private static final Logger logger = LogManager.getLogger(LicenseServiceImpl.class);

    private LicenseRepository licenseRepository;

    private ServiceConfig serviceConfig;

    OrganizationFeignClient organizationFeignClient;

    OrganizationRestTemplateClient organizationRestClient;

    OrganizationDiscoveryClient organizationDiscoveryClient;

    @Autowired
    public void setLicenseRepository(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Autowired
    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Autowired
    public void setOrganizationFeignClient(OrganizationFeignClient organizationFeignClient) {
        this.organizationFeignClient = organizationFeignClient;
    }

    @Autowired
    public void setOrganizationRestClient(OrganizationRestTemplateClient organizationRestClient) {
        this.organizationRestClient = organizationRestClient;
    }

    @Autowired
    public void setOrganizationDiscoveryClient(OrganizationDiscoveryClient organizationDiscoveryClient) {
        this.organizationDiscoveryClient = organizationDiscoveryClient;
    }

    @Override
    @Transactional
    public List<License> getLicenses(@NotNull String organizationId) {
        List<License> licenses = this.licenseRepository.findByOrganizationId(organizationId);
        final ServiceConfig sc = this.serviceConfig;
        licenses.forEach(l -> l.setComment(sc.getServiceProperty()));
        return licenses;
    }

    @Override
    @Transactional
    public License getLicense(@NotNull String organizationId, @NotNull String licenseId) {
        License license = this.licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.setComment(this.serviceConfig.getServiceProperty());
        return license;
    }

    @Override
    @Transactional
    public License getLicense(
            @NotNull String organizationId,
            @NotNull String licenseId,
            @NotNull String clientType) {
        License license = this.licenseRepository.
                findByOrganizationIdAndLicenseId(organizationId, licenseId);
        Organization organization = retrieveOrgInfo(organizationId, clientType);
        license.setOrganizationName(organization.getName());
        license.setContactName(organization.getContactName());
        license.setContactEmail(organization.getContactEmail());
        license.setContactPhone(organization.getContactPhone());
        license.setComment(serviceConfig.getServiceProperty());
        return license;
    }

    @Override
    @Transactional
    public void saveLicense(License license) {
        this.licenseRepository.save(license);
    }

    @Override
    @Transactional
    public void deleteLicense(long id) {
        this.licenseRepository.deleteById(id);
    }

    private Organization retrieveOrgInfo(
            String organizationId, String clientType) {

        Organization organization = null;

        switch (clientType) {
            case "feign":
                logger.info("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                logger.info("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                logger.info("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }
}
