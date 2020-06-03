package jun.microservices.licensingservice.controllers;

import jun.microservices.licensingservice.License;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController 告诉 Spring Boot 这是一个基于 REST 的服务，
 * 它将自动序列化/反序列化服务请求/响应到 JSON。
 */
@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    /**
     * 公开一个 GET HTTP 端点
     */
    @RequestMapping(value = "/{licenseId}",
            method = RequestMethod.GET)
    public License getLicenses(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        return new License()
                .withLicenseId(licenseId)
                .withProductName("Teleco")
                .withLicenseType("Seat")
                .withOrganizationId("TestOrg");
    }

}
