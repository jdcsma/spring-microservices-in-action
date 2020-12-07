package jun.microservices.clients;

import jun.microservices.transfers.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("organization-service")
public interface OrganizationFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/organizations/{organizationId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
