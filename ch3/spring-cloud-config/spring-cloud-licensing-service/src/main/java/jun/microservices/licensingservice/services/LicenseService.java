package jun.microservices.licensingservice.services;

import jun.microservices.licensingservice.entities.License;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface LicenseService {

    @NotNull
    List<License> getLicenses(@NotNull String organizationId);

    License getLicense(@NotNull String organizationId, @NotNull String licenseId);

    void saveLicense(@Valid License license);

    void deleteLicense(long id);
}