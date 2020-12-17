package jun.microservices.repository;

import jun.microservices.entity.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganizationRepository extends
        CrudRepository<Organization, Long>,
        OrganizationRepositoryExtension {
}
