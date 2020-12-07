package jun.microservices.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class UserContextFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        UserContextHolder.getContext().setCorrelationId(
                httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        UserContextHolder.getContext().setUserId(
                httpServletRequest.getHeader(UserContext.USER_ID));
        UserContextHolder.getContext().setAuthorizationToken(
                httpServletRequest.getHeader(UserContext.AUTHORIZATION_TOKEN));
        UserContextHolder.getContext().setOrganizationId(
                httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));

        String authorizationToken = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorizationToken) && authorizationToken.startsWith("Bearer")) {
            UserContextHolder.getContext().setAuthorizationToken(authorizationToken);
        }

        logger.info("request - uri:{}", httpServletRequest.getRequestURI());
        logger.info("request header - authorization:{}", httpServletRequest.getHeader("Authorization"));
        logger.info("user context - correlation id:{}", UserContextHolder.getContext().getCorrelationId());
        logger.info("user context - authorization token:{}", UserContextHolder.getContext().getAuthorizationToken());
        logger.info("user context - user id:{}", UserContextHolder.getContext().getUserId());
        logger.info("user context - organization id:{}", UserContextHolder.getContext().getOrganizationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
