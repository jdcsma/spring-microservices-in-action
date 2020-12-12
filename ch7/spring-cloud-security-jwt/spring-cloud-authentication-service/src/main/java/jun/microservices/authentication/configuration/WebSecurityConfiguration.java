package jun.microservices.authentication.configuration;

import jun.microservices.authentication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class WebSecurityConfiguration
        extends WebSecurityConfigurerAdapter implements Ordered {

    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDetailsService(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Used in OAuth2Config
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Use UserService instead
//    @Bean
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return super.userDetailsServiceBean();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 设置当前定制安全策略允许访问的 URL。
        http.requestMatchers().antMatchers("/client", "/user")
                .and()
                .authorizeRequests().antMatchers("/client", "/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("john.carnell")
//                .password("{noop}password1")
//                .roles("USER")
//                .and()
//                .withUser("william.woodward")
//                .password("{noop}password2")
//                .roles("USER", "ADMIN");
        auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder)
                .and()
                .eraseCredentials(true);
    }

    @Override
    public int getOrder() {
        return 2; // Before ResourceServerConfiguration
    }
}
