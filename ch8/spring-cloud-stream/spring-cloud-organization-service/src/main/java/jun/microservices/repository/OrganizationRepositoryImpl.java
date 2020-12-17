package jun.microservices.repository;

import jun.microservices.entity.Organization;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;


public class OrganizationRepositoryImpl implements OrganizationRepositoryExtension {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Organization findByOrganizationId(String organizationId) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Organization> criteriaQuery =
                builder.createQuery(Organization.class);
        Root<Organization> root = criteriaQuery.from(Organization.class);
        criteriaQuery.select(root).where(
                builder.equal(root.get("organizationId"), organizationId));
        TypedQuery<Organization> typedQuery =
                this.entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public void saveOrganization(Organization organization) {
        this.entityManager.persist(organization);
    }

    @Override
    public void updateOrganization(Organization organization) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Organization> criteriaUpdate =
                builder.createCriteriaUpdate(Organization.class);
        Root<Organization> root = criteriaUpdate.from(Organization.class);
        criteriaUpdate.where(builder.equal(
                root.get("organizationId"), organization.getOrganizationId()));
        criteriaUpdate.set(root.get("name"), organization.getName());
        criteriaUpdate.set(root.get("contactName"), organization.getContactName());
        criteriaUpdate.set(root.get("contactEmail"), organization.getContactEmail());
        criteriaUpdate.set(root.get("contactPhone"), organization.getContactPhone());
        Query query = this.entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();
    }

    @Override
    public void deleteByOrganizationId(String organizationId) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<Organization> criteriaDelete =
                builder.createCriteriaDelete(Organization.class);
        Root<Organization> root = criteriaDelete.from(Organization.class);
        criteriaDelete.where(builder.equal(
                root.get("organizationId"), organizationId));
        Query query = this.entityManager.createQuery(criteriaDelete);
        query.executeUpdate();
    }
}
