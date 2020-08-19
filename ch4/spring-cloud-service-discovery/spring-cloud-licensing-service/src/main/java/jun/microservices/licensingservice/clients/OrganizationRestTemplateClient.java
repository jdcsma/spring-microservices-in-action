package jun.microservices.licensingservice.clients;

import jun.microservices.licensingservice.transfer.Organization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class OrganizationRestTemplateClient {

    private static final Logger logger = LogManager.getLogger();

    private final RestTemplate restTemplate;

    @Autowired
    public OrganizationRestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Organization getOrganization(String organizationId) {

        // 在使用支持 Ribbon 的 RestTemplate 时，使用 Eureka 服务 ID 来构建目标 URL，
        // 而不是在 RestTemplate 调用中使用服务的物理位置。
        final String serviceUrl = "http://organization-service/v1/organizations/{organizationId}";

        logger.info("!!!! SERVICE URL:  " + serviceUrl);

        ResponseEntity<Organization> restExchange =
                this.restTemplate.exchange(
                        serviceUrl, HttpMethod.GET,
                        null, Organization.class,
                        organizationId);

        return restExchange.getBody();
    }
}
