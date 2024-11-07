package com.person.lsj.stock.configuration;

import com.person.lsj.stock.security.entrypoint.StockAuthenticationEntryPoint;
import com.person.lsj.stock.security.filter.FailAuthenticationFailureHandler;
import com.person.lsj.stock.security.filter.MyUserDetailsService;
import com.person.lsj.stock.security.filter.MyUsernamePasswordAuthenticationFilter;
import com.person.lsj.stock.security.handler.authenticate.MyAuthenticationSuccessHandler;
import com.person.lsj.stock.security.handler.csrf.CsrfAccessDeninedHandler;
import com.person.lsj.stock.security.session.CsrfSaveAuthenticationStrategy;
import com.person.lsj.stock.service.ITUserService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/token").permitAll()
                .requestMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.addFilterAt(myUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf((t) -> {
            t.ignoringRequestMatchers("/token");
        });
        http.exceptionHandling(t -> {
            t.defaultAccessDeniedHandlerFor(new CsrfAccessDeninedHandler(), AnyRequestMatcher.INSTANCE);
            t.defaultAccessDeniedHandlerFor(new AccessDeniedHandlerImpl(), new AntPathRequestMatcher("/**"));
        });
        http.securityContext(context -> context.securityContextRepository(new HttpSessionSecurityContextRepository()));

        // 验证失败，返回403，不做页面跳转，供前后端分离
        http.exceptionHandling(t -> t.authenticationEntryPoint(new StockAuthenticationEntryPoint()));
        return http.build();
    }

    @Bean
    public MyUsernamePasswordAuthenticationFilter UsernamePasswordAuthenticationFilter(AuthenticationConfiguration authenticationConfiguration) {
        try {
            MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter = new MyUsernamePasswordAuthenticationFilter();
            myUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/user/login");
            myUsernamePasswordAuthenticationFilter.setUsernameParameter("userName");
            myUsernamePasswordAuthenticationFilter.setPasswordParameter("password");
            myUsernamePasswordAuthenticationFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

            // 验证成功时，返回200，不做页面跳转，供前后端分离项目
            myUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());

            // 验证失败时，返回401，不做页面跳转，供前后端分离项目
            myUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(new FailAuthenticationFailureHandler());

            // 增加策略，登录成功后更新csrftoken跟SessionId
            List<SessionAuthenticationStrategy> sessionAuthenticationStrategies = new ArrayList<>();

            // 下面这个策略是移除session中的老csrfToken,生成新的放到request的Attribute中
            sessionAuthenticationStrategies.add(new CsrfAuthenticationStrategy(new HttpSessionCsrfTokenRepository()));

            // 下面这个是重新生成一个Session，把原来的数据放到新Session中
            sessionAuthenticationStrategies.add(new SessionFixationProtectionStrategy());

            // 下面这个是我自定义的，把新的csrfToken从request的Atribute中取出，放到Response的Header中
            sessionAuthenticationStrategies.add(new CsrfSaveAuthenticationStrategy());
            myUsernamePasswordAuthenticationFilter.setSessionAuthenticationStrategy(new CompositeSessionAuthenticationStrategy(sessionAuthenticationStrategies));

            myUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
            return myUsernamePasswordAuthenticationFilter;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public UserDetailsService userDetailsService(ITUserService userService) {
        return new MyUserDetailsService(userService);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
        encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_5());
        encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v4_1());
        encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256",
                new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
        encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_2());
        encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put(null, NoOpPasswordEncoder.getInstance());
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }
}
