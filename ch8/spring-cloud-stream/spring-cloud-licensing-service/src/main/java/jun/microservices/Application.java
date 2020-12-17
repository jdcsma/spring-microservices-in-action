package jun.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication
@EnableEurekaClient         // 在 Spring 服务中启用 Eureka 客户端
@EnableCircuitBreaker       // 在 Spring 服务中启用 Hystrix
@EnableResourceServer       // 在 Spring 服务中启用 Resource Protection
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
