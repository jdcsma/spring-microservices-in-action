package jun.microservices.licensingservice.clients;

import jun.microservices.licensingservice.transfer.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Netflix 的 Feign 客户端库是 Spring 启用 Ribbon 的 RestTemplate 类的替代方案。
 * Feign 库采用不同的方式调用 REST 服务，方法是让开发人员首先定义一个 Java 接口，
 * 然后使用 Spring Cloud 注解来标注接口，以映射 Ribbon 将要调用的基于 Eureka 的
 * 服务。Spring Cloud 框架将动态生成一个代理类，用于调用目标 REST 服务。
 */
@FeignClient("organization-service")        // 使用 @FeignClient 注解标识服务
public interface OrganizationFeignClient {

    // 使用 @RequestMapping 注解来定义端点的路径和动作
    // 使用 @PathVariable 来定义传入端点的参数
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/organizations/{organizationId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
