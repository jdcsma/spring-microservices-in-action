package jun.microservices.services;

import jun.microservices.entities.Organization;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


@Validated
public interface OrganizationService {

    Organization getOrganization(String organizationId);

    void saveOrganization(@Valid Organization organization);

    void deleteOrganization(long id);
}
