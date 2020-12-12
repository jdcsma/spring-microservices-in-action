package jun.microservices.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/*
@EnableCaching              // 在 Spring 中开启缓存，避免 loadClientByClientId 多次调用：
                            //  1) TokenEndpoint.postAccessToken
                            //       TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(parameters, authenticatedClient);
                            //  2) DefaultOAuth2RequestFactory.extractScopes
                            //       ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
                            // 3) ClientDetailsUserDetailsService.loadUserByUsername
                            //       clientDetails = clientDetailsService.loadClientByClientId(username);
                            // 4) AbstractTokenGranter.grant
                            //       ClientDetails client = clientDetailsService.loadClientByClientId(clientId);
 */
@EnableEurekaClient         // 在 Spring 服务中启用 Eureka 客户端
@EnableResourceServer
@EnableAuthorizationServer
@SpringBootApplication
@EnableTransactionManagement(
        order = 2,
        mode = AdviceMode.PROXY,
        proxyTargetClass = false
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
