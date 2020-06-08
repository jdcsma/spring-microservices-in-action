package jun.microservices.licensingservice.controllers;

import jun.microservices.licensingservice.entities.License;
import jun.microservices.licensingservice.services.LicenseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    private static final Logger logger = LogManager.getLogger(LicenseServiceController.class);

    private LicenseService licenseService;

    @Autowired
    public void setLicenseService(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<License> getLicenses(
            @PathVariable("organizationId") String organizationId) {
        return this.licenseService.getLicenses(organizationId);
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        return this.licenseService.getLicense(organizationId, licenseId);
    }

    @RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
    public License getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId,
            @PathVariable("clientType") String clientType) {
        return this.licenseService.getLicense(organizationId, licenseId, clientType);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveLicense(@Valid @RequestBody License license,
                              Errors errors, Map<String, Object> model) {
        if (errors.hasErrors()) {
            logger.error(errors.toString());
            return;
        }
        this.licenseService.saveLicense(license);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteLicense(@PathVariable long id) {
        this.licenseService.deleteLicense(id);
        return "This is the delete.";
    }
}
