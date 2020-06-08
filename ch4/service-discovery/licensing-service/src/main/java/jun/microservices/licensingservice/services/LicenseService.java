package jun.microservices.licensingservice.services;

import jun.microservices.licensingservice.entities.License;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface LicenseService {

    List<License> getLicenses(@NotNull String organizationId);

    License getLicense(@NotNull String organizationId,
                       @NotNull String licenseId);

    License getLicense(@NotNull String organizationId,
                       @NotNull String licenseId,
                       @NotNull String clientType);

    void saveLicense(@Valid License license);

    void deleteLicense(long id);
}