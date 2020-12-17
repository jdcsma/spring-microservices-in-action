package jun.microservices.authentication.mapper.user;

import jun.microservices.authentication.domain.user.UserPrincipal;


public interface UserPrincipalMapper {

    UserPrincipal loadUserByUserId(int userId);

    UserPrincipal loadUserByUsername(String username);

    void saveUser(UserPrincipal principal);
}
