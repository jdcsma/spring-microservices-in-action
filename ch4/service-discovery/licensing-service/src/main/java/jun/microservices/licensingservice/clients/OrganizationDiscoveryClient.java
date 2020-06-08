package jun.microservices.licensingservice.clients;


import jun.microservices.licensingservice.dto.Organization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient extends OrganizationClient {

    private static final Logger logger = LogManager.getLogger(OrganizationDiscoveryClient.class);

    private DiscoveryClient discoveryClient;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public Organization getOrganization(String organizationId) {

        List<ServiceInstance> instances = discoveryClient.
                getInstances("organization-service");

        if (instances.size() == 0) {
            return null;
        }

        String serviceUrl = String.format(
                "%s/v1/organizations/{organizationId}",
                instances.get(0).getUri().toString());

        logger.info("!!!! SERVICE URL:  " + serviceUrl);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        serviceUrl, HttpMethod.GET,
                        null, Organization.class,
                        organizationId);

        showStatusMessage(restExchange.getStatusCode(),
                "getOrganization", logger);

        return restExchange.getBody();
    }
}
