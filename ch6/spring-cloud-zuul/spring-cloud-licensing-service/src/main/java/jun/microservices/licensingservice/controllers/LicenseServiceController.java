package jun.microservices.licensingservice.controllers;

import jun.microservices.licensingservice.entities.License;
import jun.microservices.licensingservice.services.LicenseService;
import jun.microservices.licensingservice.utils.UserContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {

    private static final Logger logger = LogManager.getLogger();

    private final LicenseService licenseService;

    @Autowired
    public LicenseServiceController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<License> getLicenses(
            @PathVariable("organizationId") String organizationId) {
        logger.info("Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        try {
            return this.licenseService.getLicenses(organizationId);
        } catch (Exception e) {
            logger.error("exception:" + e.getClass().getTypeName());
            logger.error("message:" + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        logger.info("Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        try {
            return this.licenseService.getLicense(organizationId, licenseId);
        } catch (Exception e) {
            logger.error("exception:" + e.getClass().getTypeName());
            logger.error("message:" + e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveLicense(@Valid @RequestBody License license, Errors errors) {
        if (errors.hasErrors()) {
            logger.error(errors.toString());
            return;
        }
        this.licenseService.saveLicense(license);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLicense(@PathVariable long id) {
        this.licenseService.deleteLicense(id);
    }
}
