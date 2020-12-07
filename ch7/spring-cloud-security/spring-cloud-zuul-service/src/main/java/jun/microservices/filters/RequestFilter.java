package jun.microservices.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import jun.microservices.utils.UserContext;
import jun.microservices.utils.UserContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class RequestFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 2; // After RequestFilter
    private static final boolean SHOULD_FILTER = true;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {

        UserContext userContext = UserContextHolder.getContext();

        if (StringUtils.isEmpty(FilterUtils.getAuthorizationToken())) {
            if (!StringUtils.isEmpty(userContext.getAuthorizationToken())) {
                logger.debug("add 'Authorization' {}  to header",
                        userContext.getAuthorizationToken());
                FilterUtils.setAuthorizationToken(userContext.getAuthorizationToken());
            }
        }

        if (StringUtils.isEmpty(FilterUtils.getUserId()) &&
                !StringUtils.isEmpty(userContext.getUserId())) {
            logger.debug("add '{}' {} to header", FilterUtils.USER_ID,
                    userContext.getUserId());
            FilterUtils.setUserId(userContext.getUserId());
        }

        if (StringUtils.isEmpty(userContext.getOrganizationId()) &&
                !StringUtils.isEmpty(userContext.getOrganizationId())) {
            logger.debug("add '{}' {}  to header", FilterUtils.ORGANIZATION_ID,
                    userContext.getOrganizationId());
            FilterUtils.setOrganizationId(userContext.getOrganizationId());
        }

        logger.info("request - uri:{}", RequestContext.getCurrentContext().getRequest().getRequestURI());
        logger.info("request header - authorization:{}", RequestContext.getCurrentContext().getRequest().getHeader("Authorization"));
        logger.info("zuul request - authorization token:{}", FilterUtils.getAuthorizationToken());
        logger.info("zuul request - user id:{}", FilterUtils.getUserId());
        logger.info("zuul request - organization id:{}", FilterUtils.getOrganizationId());

        return null;
    }
}
