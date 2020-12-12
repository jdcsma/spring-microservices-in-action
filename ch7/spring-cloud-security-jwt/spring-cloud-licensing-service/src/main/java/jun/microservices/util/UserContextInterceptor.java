package jun.microservices.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

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

        logger.info("correlation id:{}", headers.getFirst(UserContext.CORRELATION_ID));
        logger.info("authorization token:{}", headers.getFirst(UserContext.AUTHORIZATION_TOKEN));
        logger.info("user id:{}", headers.getFirst(UserContext.USER_ID));
        logger.info("organization id:{}", headers.getFirst(UserContext.ORGANIZATION_ID));

        return execution.execute(request, body);
    }
}
