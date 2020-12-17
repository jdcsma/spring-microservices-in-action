package jun.microservices.util;

import lombok.Data;


@Data
public class UserContext {

    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTHORIZATION_TOKEN = "Authorization";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORGANIZATION_ID = "tmx-organization-id";

    private String correlationId;
    private String authorizationToken;
    private String userId;
    private String organizationId;
}
