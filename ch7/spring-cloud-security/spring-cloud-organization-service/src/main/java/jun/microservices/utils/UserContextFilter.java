package jun.microservices.utils;

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

        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        UserContextHolder.getContext().setAuthorizationToken(httpServletRequest.getHeader(UserContext.AUTHORIZATION_TOKEN));
        UserContextHolder.getContext().setOrganizationId(httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));

        logger.info("uri:{}", httpServletRequest.getRequestURI());
        logger.info("authorization:{}", httpServletRequest.getHeader("Authorization"));
        logger.info("correlation id:{}", UserContextHolder.getContext().getCorrelationId());
        logger.info("auth token:{}", UserContextHolder.getContext().getAuthorizationToken());
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
