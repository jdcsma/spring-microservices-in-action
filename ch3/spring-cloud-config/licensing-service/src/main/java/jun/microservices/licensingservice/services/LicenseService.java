package jun.microservices.licensingservice.services;

import jun.microservices.licensingservice.entities.License;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface LicenseService {

    License getLicense(String organizationId, String licenseId);

    @NotNull
    List<License> getLicensesByOrg(String organizationId);

    void saveLicense(@Valid License license);

    void deleteLicense(long id);

}
