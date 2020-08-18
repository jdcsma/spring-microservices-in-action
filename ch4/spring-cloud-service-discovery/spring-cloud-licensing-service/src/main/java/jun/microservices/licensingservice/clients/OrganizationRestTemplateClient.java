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
public class OrganizationRestTemplateClient extends OrganizationClient {

    private static final Logger logger = LogManager.getLogger();

    /**
     * 在使用支持 Ribbon 的 RestTemplate 时，使用 Eureka 服务 ID 来构建目标 URL，
     * 而不是在 RestTemplate 调用中使用服务的物理位置。
     */
    private static final String SERVICE_URL =
            "http://organization-service/v1/organizations/{organizationId}";

    private final RestTemplate restTemplate;

    @Autowired
    public OrganizationRestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Organization getOrganization(String organizationId) {

        logger.info("!!!! SERVICE URL:  " + SERVICE_URL);

        ResponseEntity<Organization> restExchange =
                this.restTemplate.exchange(
                        SERVICE_URL, HttpMethod.GET,
                        null, Organization.class,
                        organizationId);

        showStatusMessage(restExchange.getStatusCode(),
                "getOrganization", logger);

        return restExchange.getBody();
    }
}
