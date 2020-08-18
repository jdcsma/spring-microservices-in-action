package jun.microservices.licensingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 因为示例中使用了多种客户端类型，因此在代码中包含了
 *
 * @EnableDiscoveryClient 和 @EnableFeignClients。
 * 但是，在使用支持 Ribbon 的 RestTemplate 时，并不需要
 * 用到 @EnableDiscoveryClient 和 @EnableFeignClients，
 * 因此可以将它们移除。
 */
@SpringBootApplication
@EnableDiscoveryClient      // 激活 Spring Cloud DiscoveryClient
@EnableFeignClients         // 激活 Spring Cloud FeignClient
public class LicensingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
    }
}
