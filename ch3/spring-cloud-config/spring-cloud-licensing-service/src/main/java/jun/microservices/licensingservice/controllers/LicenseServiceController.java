package jun.microservices.licensingservice.controllers;

import jun.microservices.licensingservice.entities.License;
import jun.microservices.licensingservice.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

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

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.PUT)
    public String updateLicense(@PathVariable("licenseId") String licenseId) {
        return "This is the put.";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveLicense(@Valid @RequestBody License license,
                              Errors errors, Map<String, Object> model) {
        if (!errors.hasErrors()) {
            this.licenseService.saveLicense(license);
        }
        return "This is the post.";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteLicense(@PathVariable long id) {
        this.licenseService.deleteLicense(id);
        return "This is the delete.";
    }
}
