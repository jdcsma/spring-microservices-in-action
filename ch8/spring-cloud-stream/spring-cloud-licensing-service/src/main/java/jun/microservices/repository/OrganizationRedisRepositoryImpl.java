package jun.microservices.repository;

import jun.microservices.transfer.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;


@Repository
public class OrganizationRedisRepositoryImpl implements OrganizationRedisRepository {

    private static final String HASH_NAME = "organization";

    private final RedisTemplate<String, Organization> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    public OrganizationRedisRepositoryImpl(
            RedisTemplate<String, Organization> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    @Override
    public Organization findByOrganizationId(String organizationId) {
        return (Organization) this.hashOperations.get(HASH_NAME, organizationId);
    }

    @Override
    public void deleteByOrganizationId(String organizationId) {
        this.hashOperations.delete(HASH_NAME, organizationId);
    }

    @Override
    public void saveOrganization(Organization organization) {
        this.hashOperations.put(HASH_NAME, organization.getOrganizationId(), organization);
    }
}
