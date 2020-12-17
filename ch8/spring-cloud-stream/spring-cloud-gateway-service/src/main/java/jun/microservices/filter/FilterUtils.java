package jun.microservices.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.util.StringUtils;


public class FilterUtils {

    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTHORIZATION_TOKEN = "Authorization";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORGANIZATION_ID = "tmx-organization-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";

    public static String getCorrelationId() {
        RequestContext context = RequestContext.getCurrentContext();
        String correlationId = context.getRequest().getHeader(CORRELATION_ID);
        if (StringUtils.isEmpty(correlationId)) {
            return context.getZuulRequestHeaders().get(CORRELATION_ID);
        }
        return correlationId;
    }

    public static void setCorrelationId(String correlationId) {
        RequestContext.getCurrentContext().
                addZuulRequestHeader(CORRELATION_ID, correlationId);
    }

    public static String getAuthorizationToken() {
        RequestContext context = RequestContext.getCurrentContext();
        String authorizationToken = context.getRequest().getHeader(AUTHORIZATION_TOKEN);
        if (StringUtils.isEmpty(authorizationToken)) {
            return context.getZuulRequestHeaders().get(AUTHORIZATION_TOKEN);
        }
        return authorizationToken;
    }

    public static void setAuthorizationToken(String authorizationToken) {
        RequestContext.getCurrentContext().
                addZuulRequestHeader(AUTHORIZATION_TOKEN, authorizationToken);
    }

    public static String getUserId() {
        RequestContext context = RequestContext.getCurrentContext();
        String userId = context.getRequest().getHeader(USER_ID);
        if (StringUtils.isEmpty(userId)) {
            return context.getZuulRequestHeaders().get(USER_ID);
        }
        return userId;
    }

    public static void setUserId(String userId) {
        RequestContext.getCurrentContext().
                addZuulRequestHeader(USER_ID, userId);
    }

    public static String getOrganizationId() {
        RequestContext context = RequestContext.getCurrentContext();
        String organizationId = context.getRequest().getHeader(ORGANIZATION_ID);
        if (StringUtils.isEmpty(organizationId)) {
            return context.getZuulRequestHeaders().get(ORGANIZATION_ID);
        }
        return organizationId;
    }

    public static void setOrganizationId(String organizationId) {
        RequestContext.getCurrentContext().
                addZuulRequestHeader(ORGANIZATION_ID, organizationId);
    }

    public static String getServiceId() {
        RequestContext context = RequestContext.getCurrentContext();
        if (StringUtils.isEmpty(context.get("serviceId"))) {
            // We might not have a service id if we are using a static, non-eureka route.
            return "";
        }
        return context.get("serviceId").toString();
    }
}
