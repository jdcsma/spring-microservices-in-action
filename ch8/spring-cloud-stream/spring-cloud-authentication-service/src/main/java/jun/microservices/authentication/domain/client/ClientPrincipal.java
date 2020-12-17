package jun.microservices.authentication.domain.client;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;


public class ClientPrincipal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientId;
    private byte[] hashedSecret;
    private String scope;
    private String authorizedGrantTypes;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public byte[] getHashedSecret() {
        return this.hashedSecret;
    }

    public void setHashedSecret(byte[] hashedSecret) {
        this.hashedSecret = hashedSecret;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return this.authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getSecret() {
        return this.getHashedSecret() == null
                ? "" : new String(this.getHashedSecret(), StandardCharsets.UTF_8);
    }

    @Override
    public int hashCode() {
        return this.getClientId().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ClientPrincipal &&
                ((ClientPrincipal) other).getClientId().equals(this.getClientId());
    }

    @Override
    public String toString() {
        return this.getClientId();
    }
}
