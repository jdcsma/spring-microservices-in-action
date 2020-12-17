package jun.microservices.repository;

import jun.microservices.entity.Organization;


public interface OrganizationRepositoryExtension {

    Organization findByOrganizationId(String organizationId);

    void saveOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteByOrganizationId(String organizationId);
}
