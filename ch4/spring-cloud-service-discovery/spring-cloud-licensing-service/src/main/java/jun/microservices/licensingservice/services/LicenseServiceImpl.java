package jun.microservices.licensingservice.services;

import jun.microservices.licensingservice.clients.OrganizationDiscoveryClient;
import jun.microservices.licensingservice.clients.OrganizationFeignClient;
import jun.microservices.licensingservice.clients.OrganizationRestTemplateClient;
import jun.microservices.licensingservice.config.ServiceConfig;
import jun.microservices.licensingservice.entities.License;
import jun.microservices.licensingservice.repositories.LicenseRepository;
import jun.microservices.licensingservice.transfer.Organization;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;


@Service
public class LicenseServiceImpl implements LicenseService {

    private static final Logger logger = LogManager.getLogger();

    private final ServiceConfig serviceConfig;
    private final LicenseRepository licenseRepository;
    private final OrganizationFeignClient organizationFeignClient;
    private final OrganizationRestTemplateClient organizationRestClient;
    private final OrganizationDiscoveryClient organizationDiscoveryClient;

    @Autowired
    public LicenseServiceImpl(
            ServiceConfig serviceConfig, LicenseRepository licenseRepository,
            OrganizationFeignClient organizationFeignClient,
            OrganizationRestTemplateClient organizationRestClient,
            OrganizationDiscoveryClient organizationDiscoveryClient) {
        this.serviceConfig = serviceConfig;
        this.licenseRepository = licenseRepository;
        this.organizationFeignClient = organizationFeignClient;
        this.organizationRestClient = organizationRestClient;
        this.organizationDiscoveryClient = organizationDiscoveryClient;
    }

    @Override
    @Transactional
    public List<License> getLicenses(@NotNull String organizationId) {
        List<License> licenses = this.licenseRepository.findByOrganizationId(organizationId);
        licenses.forEach(this::retrieveOrganizationInfo);
        return licenses;
    }

    @Override
    @Transactional
    public License getLicense(@NotNull String organizationId, @NotNull String licenseId) {
        License license = this.licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        this.retrieveOrganizationInfo(license);
        return license;
    }

    @Override
    @Transactional
    public License getLicense(@NotNull String organizationId,
                              @NotNull String licenseId,
                              @NotNull String clientType) {
        License license = this.licenseRepository.
                findByOrganizationIdAndLicenseId(organizationId, licenseId);
        this.retrieveOrganizationInfo(license, clientType);
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

    private void retrieveOrganizationInfo(License license) {
        this.retrieveOrganizationInfo(license, "feign");
    }

    private void retrieveOrganizationInfo(License license, String client) {
        Organization organization = this.retrieveOrganizationInfo(
                license.getOrganizationId(), client);
        license.setOrganizationName(organization.getName());
        license.setContactName(organization.getContactName());
        license.setContactEmail(organization.getContactEmail());
        license.setContactPhone(organization.getContactPhone());
        if (StringUtils.isEmpty(license.getComment())) {
            license.setComment(serviceConfig.serviceProperty());
        }
    }

    private Organization retrieveOrganizationInfo(String organizationId, String clientType) {

        Organization organization;

        switch (clientType) {
            case "feign":
                logger.info("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "discovery":
                logger.info("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            case "rest":
            default:
                logger.info("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
        }

        return organization;
    }
}
