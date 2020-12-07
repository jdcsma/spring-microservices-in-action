package jun.microservices.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;


public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution)
            throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(UserContext.AUTHORIZATION_TOKEN, UserContextHolder.getContext().getAuthorizationToken());
        headers.add(UserContext.USER_ID, UserContextHolder.getContext().getUserId());
        headers.add(UserContext.ORGANIZATION_ID, UserContextHolder.getContext().getOrganizationId());

        if (!StringUtils.isEmpty(UserContextHolder.getContext().getAuthorizationToken())) {
            logger.debug("add 'Authorization' {}  to header, it will is passed to downstream requests by zuul server",
                    UserContextHolder.getContext().getAuthorizationToken());
            headers.add("Authorization", UserContextHolder.getContext().getAuthorizationToken());
        }

        logger.info("[HttpRequest] uri:{}", request.getURI());
        logger.info("[HttpHeaders] authorization:{}", headers.getFirst("Authorization"));
        logger.info("[UserContext] correlation id:{}", headers.getFirst(UserContext.CORRELATION_ID));
        logger.info("[UserContext] authorization token:{}", headers.getFirst(UserContext.AUTHORIZATION_TOKEN));
        logger.info("[UserContext] user id:{}", headers.getFirst(UserContext.USER_ID));
        logger.info("[UserContext] organization id:{}", headers.getFirst(UserContext.ORGANIZATION_ID));

        return execution.execute(request, body);
    }
}
