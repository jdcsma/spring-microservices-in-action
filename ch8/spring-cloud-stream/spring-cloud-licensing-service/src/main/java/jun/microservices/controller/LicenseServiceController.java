package jun.microservices.controller;

import jun.microservices.entity.License;
import jun.microservices.service.LicenseService;
import jun.microservices.util.UserContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "v1/licenses")
public class LicenseServiceController {

    private static final Logger logger = LogManager.getLogger();

    private final LicenseService licenseService;

    @Autowired
    public LicenseServiceController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
    public List<License> getLicenses(
            @PathVariable("organizationId") String organizationId) {
        logger.info("Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return this.licenseService.getLicenses(organizationId);
    }
}
