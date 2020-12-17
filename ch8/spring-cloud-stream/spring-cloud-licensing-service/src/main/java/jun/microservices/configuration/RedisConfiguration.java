package jun.microservices.configuration;

import jun.microservices.transfer.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class RedisConfiguration {

    private final ServiceConfiguration serviceConfiguration;

    @Autowired
    public RedisConfiguration(ServiceConfiguration serviceConfiguration) {
        this.serviceConfiguration = serviceConfiguration;
    }

    /**
     * Instead by org.springframework.boot.autoconfigure.data.redis.JedisConnectionConfiguration
     * <p>
     * Auto-configured with spring-boot when spring-boot-starter-data-redis on classpath.
     *
     * <pre>
     * &#64ConfigurationProperties(prefix = "spring.redis")
     * public class RedisProperties {
     *     // ...
     * }
     *
     * &#64Configuration(proxyBeanMethods = false)
     * &#64ConditionalOnClass({ GenericObjectPool.class, JedisConnection.class, Jedis.class })
     * class JedisConnectionConfiguration extends RedisConnectionConfiguration {
     *
     *      &#64Bean
     *      &#64ConditionalOnMissingBean(RedisConnectionFactory.class)
     *      JedisConnectionFactory redisConnectionFactory(
     * 			ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers)
     * 			throws UnknownHostException {
     * 		    return createJedisConnectionFactory(builderCustomizers);
     *      }
     * </pre>
     */
    //    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(this.serviceConfiguration.getRedisHost());
        factory.setPort(this.serviceConfiguration.getRedisPort());
        factory.setPassword(this.serviceConfiguration.getRedisPassword());
        return factory;
    }

    @Bean
    public RedisTemplate<String, Organization> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Organization> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
