package jun.microservices.authentication.mapper.user;

import jun.microservices.authentication.domain.user.UserAuthority;

import java.util.List;


public interface UserAuthorityMapper {

    List<UserAuthority> loadAuthorityByUserId(int userId);

    void saveAuthority(List<UserAuthority> authorities);
}
