package jun.microservices.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceConfiguration {

    @Getter
    private LocalConfiguration localConfiguration;

    @Autowired
    public void setLocalConfiguration(LocalConfiguration localConfiguration) {
        this.localConfiguration = localConfiguration;
    }

    @Value("${service.property}")
    @Getter
    @Setter
    private String serviceProperty;

    @Value("${signing.key}")
    @Getter
    @Setter
    private String signingKey;


}
