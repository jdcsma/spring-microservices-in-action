package jun.microservices.authentication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ServiceConfiguration {

    @Value("${service.property}")
    private String serviceProperty;

    @Value("${signing.key}")
    private String signingKey;

    public String getServiceProperty() {
        return this.serviceProperty;
    }

    public String getSigningKey() {
        return this.signingKey;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
