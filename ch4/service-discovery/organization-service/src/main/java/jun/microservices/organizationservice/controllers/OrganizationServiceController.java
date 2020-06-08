package jun.microservices.organizationservice.controllers;

import jun.microservices.organizationservice.entities.Organization;
import jun.microservices.organizationservice.services.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "v1/organizations")
public class OrganizationServiceController {

    private OrganizationServiceImpl orgService;

    @Autowired
    public void setOrgService(OrganizationServiceImpl orgService) {
        this.orgService = orgService;
    }

    @RequestMapping(value = "/{organizationId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Organization getOrganization(
            @PathVariable("organizationId") String organizationId) {
        return orgService.getOrganization(organizationId);
    }

    @RequestMapping(value = "/{organizationId}",
            method = RequestMethod.POST)
    public void saveOrganization(
            @PathVariable("organizationId") String organizationId,
            @Valid @RequestBody Organization org) {
        orgService.saveOrganization(org);
    }

    @RequestMapping(value = "/{organizationId}",
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(
            @PathVariable("organizationId") String organizationId,
            @RequestParam("id") long id) {
        orgService.deleteOrganization(id);
    }
}
