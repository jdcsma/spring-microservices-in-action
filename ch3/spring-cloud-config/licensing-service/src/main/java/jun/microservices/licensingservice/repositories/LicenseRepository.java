package jun.microservices.licensingservice.repositories;

import jun.microservices.licensingservice.entities.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends
        CrudRepository<License, Long> {

    List<License> findByOrganizationId(String organizationId);

    License findByOrganizationIdAndLicenseId(
            String organizationId, String licenseId);

}
