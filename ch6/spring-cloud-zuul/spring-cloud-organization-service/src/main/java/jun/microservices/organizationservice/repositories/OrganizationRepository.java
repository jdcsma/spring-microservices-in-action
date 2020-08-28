package jun.microservices.organizationservice.repositories;

import jun.microservices.organizationservice.entities.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganizationRepository extends
        CrudRepository<Organization, Long> {

    Organization findByOrganizationId(String organizationId);
}