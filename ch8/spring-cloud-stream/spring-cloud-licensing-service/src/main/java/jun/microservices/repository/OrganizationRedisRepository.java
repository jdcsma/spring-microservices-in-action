package jun.microservices.repository;

import jun.microservices.transfer.Organization;


public interface OrganizationRedisRepository {

    Organization findByOrganizationId(String organizationId);

    void deleteByOrganizationId(String organizationId);

    void saveOrganization(Organization organization);
}
