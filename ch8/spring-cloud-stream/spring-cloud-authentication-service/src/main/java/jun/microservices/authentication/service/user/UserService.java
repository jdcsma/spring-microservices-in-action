package jun.microservices.authentication.service.user;

import jun.microservices.authentication.domain.user.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;


@Validated
public interface UserService extends UserDetailsService {

    @Override
    UserPrincipal loadUserByUsername(String username);

    void saveUser(UserPrincipal principal);
}
