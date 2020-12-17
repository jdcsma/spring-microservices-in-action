package jun.microservices.controller;

import jun.microservices.entity.Organization;
import jun.microservices.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "v1/organizations")
public class OrganizationServiceController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationServiceController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Organization getOrganization(
            @PathVariable("organizationId") String organizationId) {
        return this.organizationService.getOrganization(organizationId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void saveOrganization(@Valid @RequestBody Organization org) {
        this.organizationService.saveOrganization(org);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public void updateOrganization(@RequestBody Organization org) {
        this.organizationService.updateOrganization(org);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("organizationId") String organizationId) {
        this.organizationService.deleteByOrganizationId(organizationId);
    }
}
