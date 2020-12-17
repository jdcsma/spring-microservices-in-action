package jun.microservices.service;

import jun.microservices.entity.Organization;
import jun.microservices.event.source.SimpleSourceBean;
import jun.microservices.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    private SimpleSourceBean simpleSourceBean;

    @Autowired
    public void setOrganizationRepository(
            OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Autowired
    public void setSimpleSourceBean(SimpleSourceBean simpleSourceBean) {
        this.simpleSourceBean = simpleSourceBean;
    }

    @Override
    @Transactional
    public Organization getOrganization(String organizationId) {
        return this.organizationRepository.findByOrganizationId(organizationId);
    }

    @Override
    @Transactional
    public void saveOrganization(Organization organization) {
        this.organizationRepository.saveOrganization(organization);
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        this.organizationRepository.updateOrganization(organization);
        this.simpleSourceBean.publishOrganizationChange(
                "UPDATE", organization.getOrganizationId());
    }

    @Override
    @Transactional
    public void deleteByOrganizationId(String organizationId) {
        this.organizationRepository.deleteByOrganizationId(organizationId);
        this.simpleSourceBean.publishOrganizationChange(
                "DELETE", organizationId);
    }
}
