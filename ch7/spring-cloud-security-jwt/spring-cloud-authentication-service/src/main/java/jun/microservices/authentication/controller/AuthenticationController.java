package jun.microservices.authentication.controller;

import jun.microservices.authentication.domain.client.ClientPrincipal;
import jun.microservices.authentication.domain.user.UserAuthority;
import jun.microservices.authentication.domain.user.UserPrincipal;
import jun.microservices.authentication.service.client.ClientService;
import jun.microservices.authentication.service.user.UserService;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RestController
public class AuthenticationController {

    private final static Logger logger = LogManager.getLogger();

    private UserService userService;

    private ClientService clientService;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/user_authorities", produces = "application/json")
    public Map<String, Object> user(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", user.getUserAuthentication().getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(
                user.getUserAuthentication().getAuthorities()));
        return userInfo;
    }

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public void client(@RequestBody ClientForm client) {

        ClientPrincipal principal = new ClientPrincipal();

        principal.setClientId(client.getClientId());
        principal.setScope(client.getScope());
        principal.setAuthorizedGrantTypes(client.getAuthorizedGrantTypes());

        if (client.getSecret() != null && client.getSecret().length() > 0) {
            principal.setHashedSecret(
                    this.encoder.encode(client.getSecret()).
                            getBytes(StandardCharsets.UTF_8));
        }

        this.clientService.saveClient(principal);
    }

    @Data
    public static class ClientForm {

        private String clientId;
        private String secret;
        private String scope;
        private String authorizedGrantTypes;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void user(@RequestBody UserForm userForm) {

        UserPrincipal principal = new UserPrincipal();

        principal.setUsername(userForm.getUsername());
        principal.setAccountNonExpired(userForm.isAccountNonExpired());
        principal.setAccountNonLocked(userForm.isAccountNonLocked());
        principal.setCredentialsNonExpired(userForm.isCredentialsNonExpired());
        principal.setEnabled(userForm.isEnabled());

        if (userForm.getPassword() != null && userForm.getPassword().length() > 0) {
            principal.setHashedPassword(
                    this.encoder.encode(userForm.getPassword()).
                            getBytes(StandardCharsets.UTF_8));
        }

        Set<UserAuthority> authorities = new HashSet<>();
        String[] tokens = StringUtils.commaDelimitedListToStringArray(
                userForm.getAuthorities());
        for (String token : tokens) {
            authorities.add(new UserAuthority(token));
        }
        principal.setAuthorities(authorities);

        this.userService.saveUser(principal);
    }

    @Data
    public static class UserForm {

        private String username;
        private String password;
        private String authorities;
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;
        private boolean enabled;
    }
}
