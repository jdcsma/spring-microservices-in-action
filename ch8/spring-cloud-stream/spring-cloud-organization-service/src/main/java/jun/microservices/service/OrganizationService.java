package jun.microservices.service;

import jun.microservices.entity.Organization;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


@Validated
public interface OrganizationService {

    Organization getOrganization(String organizationId);

    void saveOrganization(@Valid Organization organization);

    void updateOrganization(Organization organization);

    void deleteByOrganizationId(String organizationId);
}
