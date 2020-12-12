package jun.microservices.authentication.domain.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;


@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class UserAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private long userId;

    @JsonProperty
    private String authority;

    private UserPrincipal user;

    public UserAuthority(String authority) {
        this.authority = authority;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public UserPrincipal getUser() {
        return this.user;
    }

    public void setUser(UserPrincipal user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserAuthority{" +
                "userId=" + userId +
                ", authority='" + authority +
                '}';
    }
}
