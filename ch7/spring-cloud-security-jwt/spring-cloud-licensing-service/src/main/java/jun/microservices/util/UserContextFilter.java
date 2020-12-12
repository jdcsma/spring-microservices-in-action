package jun.microservices.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class UserContextFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        // 从 HTTP 首部解析出相关信息，并设置到 UserContext 中。
        // 在 UserContext 中的信息将会在 UserContextInterceptor 被添加到 HTTP 请求做的首部中，
        // 以达到在多个服务之间传递的目的。

        UserContextHolder.getContext().setCorrelationId(
                httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        UserContextHolder.getContext().setAuthorizationToken(
                httpServletRequest.getHeader(UserContext.AUTHORIZATION_TOKEN));
        UserContextHolder.getContext().setUserId(
                httpServletRequest.getHeader(UserContext.USER_ID));
        UserContextHolder.getContext().setOrganizationId(
                httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));

        logger.info("correlation id:{}", UserContextHolder.getContext().getCorrelationId());
        logger.info("authorization token:{}", UserContextHolder.getContext().getAuthorizationToken());
        logger.info("user id:{}", UserContextHolder.getContext().getUserId());
        logger.info("organization id:{}", UserContextHolder.getContext().getOrganizationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
