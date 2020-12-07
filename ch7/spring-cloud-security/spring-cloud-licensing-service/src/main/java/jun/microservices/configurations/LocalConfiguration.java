package jun.microservices.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties("local")
public class LocalConfiguration {

    private String prefix;
}
