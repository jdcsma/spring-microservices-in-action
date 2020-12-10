package jun.microservices.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import jun.microservices.clients.OrganizationFeignClient;
import jun.microservices.configurations.ServiceConfiguration;
import jun.microservices.entities.License;
import jun.microservices.repositories.LicenseRepository;
import jun.microservices.transfers.Organization;
import jun.microservices.utils.UserContext;
import jun.microservices.utils.UserContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class LicenseServiceImpl implements LicenseService {

    private static final Logger logger = LogManager.getLogger();

    private final ServiceConfiguration serviceConfiguration;
    private final LicenseRepository licenseRepository;
    private final OrganizationFeignClient organizationFeignClient;
    private final RestTemplate organizationRestTemplate;

    @Autowired
    public LicenseServiceImpl(
            ServiceConfiguration serviceConfiguration, LicenseRepository licenseRepository,
            OrganizationFeignClient organizationFeignClient,
            RestTemplate organizationRestTemplate) {
        this.serviceConfiguration = serviceConfiguration;
        this.licenseRepository = licenseRepository;
        this.organizationFeignClient = organizationFeignClient;
        this.organizationRestTemplate = organizationRestTemplate;
    }

    @Override
    @Transactional
    // @See com.netflix.hystrix.HystrixCommand
    @HystrixCommand(
            fallbackMethod = "buildFallbackLicenses",
            threadPoolKey = "getLicensesThreadPool",
            // @see com.netflix.hystrix.HystrixThreadPoolProperties
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10"),
                    @HystrixProperty(name = "maxQueueSize", value = "5")
            },
            // @see com.netflix.hystrix.HystrixCommandProperties
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
    public List<License> getLicenses(String organizationId) {

        logger.info("Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

//        this.randomRunLong();

        List<License> licenses = this.licenseRepository.findByOrganizationId(organizationId);
        licenses.forEach(this::retrieveOrganizationInfo);
        return licenses;
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "buildFallbackLicense")
    public License getLicense(String organizationId, String licenseId) {

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

//    private void retrieveOrganizationInfo(License license) {
//        Organization organization =
//                this.organizationFeignClient.getOrganization(
//                        license.getOrganizationId());
//        license.setOrganizationName(organization.getName());
//        license.setContactName(organization.getContactName());
//        license.setContactEmail(organization.getContactEmail());
//        license.setContactPhone(organization.getContactPhone());
//        if (StringUtils.isEmpty(license.getComment())) {
//            license.setComment(
//                    serviceConfiguration.getLocalConfiguration().getPrefix() + " " +
//                            serviceConfiguration.getServiceProperty());
//        }
//    }

    private void retrieveOrganizationInfo(License license) {

        // 在使用支持 Ribbon 的 RestTemplate 时，使用 Eureka 服务 ID 来构建目标 URL，
        // 而不是在 RestTemplate 调用中使用服务的物理位置。
        final String serviceUrl = "http://gateway-service/api/organization/v1/organizations/{organizationId}";

        logger.info("!!!! SERVICE URL:  " + serviceUrl);

        UserContextHolder.getContext().setOrganizationId(license.getOrganizationId());

        ResponseEntity<Organization> restExchange =
                this.organizationRestTemplate.exchange(
                        serviceUrl, HttpMethod.GET,
                        null, Organization.class,
                        license.getOrganizationId());

        Organization organization = restExchange.getBody();

        if (organization == null) {
            return;
        }

        license.setOrganizationName(organization.getName());
        license.setContactName(organization.getContactName());
        license.setContactEmail(organization.getContactEmail());
        license.setContactPhone(organization.getContactPhone());
        if (StringUtils.isEmpty(license.getComment())) {
            license.setComment(
                    serviceConfiguration.getLocalConfiguration().getPrefix() + " " +
                            serviceConfiguration.getServiceProperty());
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

    private List<License> buildFallbackLicenses(String organizationId) {
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
