package jun.microservices.licensingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {

    @Value("${service.property}")
    private String serviceProperty;

    public String getServiceProperty() {
        return serviceProperty;
    }
}
