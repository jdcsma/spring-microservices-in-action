package jun.microservices.licensingservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import jun.microservices.licensingservice.clients.OrganizationFeignClient;
import jun.microservices.licensingservice.config.ServiceConfig;
import jun.microservices.licensingservice.entities.License;
import jun.microservices.licensingservice.repositories.LicenseRepository;
import jun.microservices.licensingservice.transfer.Organization;
import jun.microservices.licensingservice.utils.UserContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class LicenseServiceImpl implements LicenseService {

    private static final Logger logger = LogManager.getLogger();

    private final ServiceConfig serviceConfig;
    private final LicenseRepository licenseRepository;
    private final OrganizationFeignClient organizationFeignClient;

    @Autowired
    public LicenseServiceImpl(
            ServiceConfig serviceConfig, LicenseRepository licenseRepository,
            OrganizationFeignClient organizationFeignClient) {
        this.serviceConfig = serviceConfig;
        this.licenseRepository = licenseRepository;
        this.organizationFeignClient = organizationFeignClient;
    }

    @Override
    @Transactional
    @HystrixCommand(
            fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "getLicensesThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10"),
                    @HystrixProperty(name = "maxQueueSize", value = "5")
            },
            commandProperties = {
//                    @HystrixProperty(
//                            name = "execution.isolation.thread.timeoutInMilliseconds",
//                            value = "12000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
            }
    )
    public List<License> getLicenses(@NotNull String organizationId) {

        logger.info("Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

//        this.randomRunLong();

        List<License> licenses = this.licenseRepository.findByOrganizationId(organizationId);
        licenses.forEach(this::retrieveOrganizationInfo);
        return licenses;
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "buildFallbackLicense")
    public License getLicense(@NotNull String organizationId, @NotNull String licenseId) {

        logger.info("Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

//        this.randomRunLong();

        License license = this.licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        this.retrieveOrganizationInfo(license);
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
        Organization organization =
                this.organizationFeignClient.getOrganization(
                        license.getOrganizationId());
        license.setOrganizationName(organization.getName());
        license.setContactName(organization.getContactName());
        license.setContactEmail(organization.getContactEmail());
        license.setContactPhone(organization.getContactPhone());
        if (StringUtils.isEmpty(license.getComment())) {
            license.setComment(serviceConfig.serviceProperty());
        }
    }

    private void randomRunLong() {
        Random rand = new Random();

        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if (randomNum == 3) {
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(11_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> licenses = new ArrayList<>();
        License license = new License();
        license.setOrganizationId(organizationId);
        license.setProductName("Sorry no licensing information currently available");
        licenses.add(license);
        return licenses;
    }

    private License buildFallbackLicense(String organizationId, String licenseId) {
        License license = new License();
        license.setLicenseId(licenseId);
        license.setOrganizationId(organizationId);
        license.setProductName("Sorry no licensing information currently available");
        return license;
    }
}