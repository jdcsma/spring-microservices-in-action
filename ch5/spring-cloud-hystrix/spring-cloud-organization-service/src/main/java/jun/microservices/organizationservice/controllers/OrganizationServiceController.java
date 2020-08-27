package jun.microservices.organizationservice.controllers;

import jun.microservices.organizationservice.entities.Organization;
import jun.microservices.organizationservice.services.OrganizationService;
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
        return organizationService.getOrganization(organizationId);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.POST)
    public void saveOrganization(
            @PathVariable("organizationId") String organizationId,
            @Valid @RequestBody Organization org) {
        organizationService.saveOrganization(org);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(
            @PathVariable("organizationId") String organizationId,
            @RequestParam("id") long id) {
        organizationService.deleteOrganization(id);
    }
}
