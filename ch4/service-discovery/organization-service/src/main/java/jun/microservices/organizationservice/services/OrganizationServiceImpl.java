package jun.microservices.organizationservice.services;

import jun.microservices.organizationservice.entities.Organization;
import jun.microservices.organizationservice.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    @Autowired
    public void setOrganizationRepository(
            OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    @Transactional
    public Organization getOrganization(String organizationId) {
        return organizationRepository.findByOrganizationId(organizationId);
    }

    @Override
    @Transactional
    public void saveOrganization(Organization organization) {
        organizationRepository.save(organization);
    }

    @Override
    @Transactional
    public void deleteOrganization(long id) {
        organizationRepository.deleteById(id);
    }
}
