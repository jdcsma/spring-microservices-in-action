package jun.microservices.licensingservice.clients;

import jun.microservices.licensingservice.transfer.Organization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class OrganizationDiscoveryClient {

    private static final Logger logger = LogManager.getLogger();

    private final DiscoveryClient discoveryClient;

    @Autowired
    public OrganizationDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * 通过引入 DiscoveryClient，完成了使用 Ribbon 来构建服务消费者的过程。然而，
     * 在实际运用中，只有在服务需要查询 Ribbon 以了解哪些服务和服务实例已经通过它注册时，
     * 才应该使用 DiscoveryClient。下面代码存在以下几个问题。
     *
     *  - 没有利用 Ribbon 的客户端负载均衡——尽管通过直接调用 DiscoveryClient 可以获得
     *  服务列表，但是要调用返回的哪些服务实例就成了开发人员的责任。
     *
     *  - 开发人员做了太多的工作——现在，开发人员必须构建一个用来调用服务的 URL。尽管这是一件
     *  小事，但是编写的代码越少意味着需要调试的代码就越少。
     *
     *  善于观察的 Spring 开发人员可能已经注意到，上述代码中直接实例化了 RestTemplate 类。
     *  这与正常的 Spring REST 调用相反，通常情况下，开发人员会利用 Spring 框架，通过 @Autowired
     *  注解将 RestTemplate 注入使用 RestTemplate 的类中。
     *
     *  代码中实例化了 RestTemplate 类，这是因为一旦在应用程序类中通过 @EnableDiscoveryClient 注解
     *  启用了 Spring DiscoveryClient，由 Spring 框架管理的所有 RestTemplate 都将注入一个启用了
     *  Ribbon 的拦截器，这个拦截器将改变使用 RestTemplate 类创建 URL 的行为。直接实例化 RestTemplate
     *  类可以避免这种行为。
     */
    public Organization getOrganization(String organizationId) {

        // 获取组织服务的所有实例的列表
        List<ServiceInstance> instances =
                discoveryClient.getInstances("organization-service");

        if (instances.size() == 0) {
            logger.error("organization-service instance not found.");
            return null;
        }

        final String serviceUrl = String.format(
                "%s/v1/organizations/{organizationId}",
                instances.get(0).getUri().toString());

        logger.info("!!!! SERVICE URL:  " + serviceUrl);

        // 使用标准的 Spring REST 模板类去调用服务
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        serviceUrl, HttpMethod.GET,
                        null, Organization.class,
                        organizationId);

        return restExchange.getBody();
    }
}
