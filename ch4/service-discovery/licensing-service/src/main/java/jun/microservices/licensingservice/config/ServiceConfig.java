package jun.microservices.licensingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {

    @Value("${service.property}")
    private String serviceProperty;

    public String getServiceProperty() {
        return serviceProperty;
    }
}
