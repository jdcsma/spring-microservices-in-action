package jun.microservices.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceConfiguration {

    @Value("${service.property}")
    @Getter
    @Setter
    private String serviceProperty;

    @Value("${signing.key}")
    @Getter
    @Setter
    private String signingKey;

    @Value("${redis.host}")
    @Getter
    @Setter
    private String redisHost;

    @Value("${redis.port}")
    @Getter
    @Setter
    private int redisPort;

    @Value("${redis.password}")
    @Getter
    @Setter
    private String redisPassword;
}
