package jun.microservices.licensingservice.services;

import jun.microservices.licensingservice.config.ServiceConfig;
import jun.microservices.licensingservice.entities.License;
import jun.microservices.licensingservice.repositories.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LicenseServiceImpl implements LicenseService {

    private LicenseRepository licenseRepository;

    private ServiceConfig serviceConfig;

    @Autowired
    public void setLicenseRepository(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Autowired
    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Override
    @Transactional
    public List<License> getLicenses(String organizationId) {
        List<License> licenses = this.licenseRepository.findByOrganizationId(organizationId);
        licenses.forEach(l -> l.setComment(serviceConfig.getServiceProperty()));
        return licenses;
    }

    @Override
    @Transactional
    public License getLicense(String organizationId, String licenseId) {
        License license = this.licenseRepository.findByOrganizationIdAndLicenseId(
                organizationId, licenseId);
        if (license != null) {
            license.setComment(this.serviceConfig.getServiceProperty());
        }
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
}
