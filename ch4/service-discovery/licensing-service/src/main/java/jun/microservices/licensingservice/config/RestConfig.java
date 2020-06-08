package jun.microservices.licensingservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    /**
     * @LoadBalanced 注解告诉 Spring Cloud 创建一个支持 Ribbon 的 RestTemplate 类。
     *
     * 注意：在 Spring Cloud 的早期版本中，RestTemplate 类默认自动支持 Ribbon。
     * 但是，自从 Spring Cloud 发布 Angel 版本之后，Spring Cloud 中的 RestTemplate
     * 就不再支持 Ribbon。如果要将 Ribbon 和 RestTemplate 一起使用，则必须使用 @LoadBalanced
     * 注解进行显示标注。
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
