package jun.microservices.service;

import jun.microservices.entity.Organization;
import jun.microservices.repository.OrganizationRepository;
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
        return this.organizationRepository.findByOrganizationId(organizationId);
    }

    @Override
    @Transactional
    public void saveOrganization(Organization organization) {
        this.organizationRepository.save(organization);
    }

    @Override
    @Transactional
    public void deleteOrganization(long id) {
        this.organizationRepository.deleteById(id);
    }
}
