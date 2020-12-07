package jun.microservices.authentication.services.user;

import jun.microservices.authentication.domain.user.UserPrincipal;
import jun.microservices.authentication.mapper.user.UserAuthorityMapper;
import jun.microservices.authentication.mapper.user.UserPrincipalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
public class UserServiceImpl implements UserService {

    private UserPrincipalMapper userPrincipalMapper;
    private UserAuthorityMapper userAuthorityMapper;

    @Autowired
    public void setUserPrincipalMapper(UserPrincipalMapper userPrincipalMapper) {
        this.userPrincipalMapper = userPrincipalMapper;
    }

    @Autowired
    public void setUserAuthorityMapper(UserAuthorityMapper userAuthorityMapper) {
        this.userAuthorityMapper = userAuthorityMapper;
    }

    @Transactional("UserTransactionManager")
    @Override
    public UserPrincipal loadUserByUsername(String username) {
        return this.userPrincipalMapper.loadUserByUsername(username);
    }

    @Transactional("UserTransactionManager")
    @Override
    public void saveUser(UserPrincipal principal) {
        this.userPrincipalMapper.saveUser(principal);
        principal.getAuthorities().forEach(
                a -> a.setUserId(principal.getUserId()));
        this.userAuthorityMapper.saveAuthority(
                new ArrayList<>(principal.getAuthorities()));
    }
}
