package jun.microservices.repositories;

import jun.microservices.entities.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    Organization findByOrganizationId(String organizationId);
}
