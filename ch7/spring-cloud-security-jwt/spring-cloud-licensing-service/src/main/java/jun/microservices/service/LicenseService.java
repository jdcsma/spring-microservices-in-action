package jun.microservices.service;

import jun.microservices.entity.License;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;


@Validated
public interface LicenseService {

    List<License> getLicenses(String organizationId);

    License getLicense(String organizationId, String licenseId);

    void saveLicense(@Valid License license);

    void deleteLicense(long id);
}
