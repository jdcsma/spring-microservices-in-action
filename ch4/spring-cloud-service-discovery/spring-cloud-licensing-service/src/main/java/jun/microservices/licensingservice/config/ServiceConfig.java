package jun.microservices.licensingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceConfig {

    @Value("${service.property}")
    private String serviceProperty;

    public String serviceProperty() {
        return this.serviceProperty;
    }
}
