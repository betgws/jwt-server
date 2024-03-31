package JWT.JwtServer.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;


@Configuration
@EnableWebSecurity
@AllArgsConstructor

public class SecurityConfig {


    private CorsFilter corsFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(CsrfConfigurer::disable);
        http.authorizeHttpRequests(authorize ->
                authorize

                        .requestMatchers(new AntPathRequestMatcher("/api/v1/admin/**")).hasAnyRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/manager/**")).hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/user/**")).hasAnyRole("USER","MANAGER","ADMIN")

                        .anyRequest().permitAll()

        )
        .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// 세션을 사용하지 않갰다는 말
        .addFilter(corsFilter) //@CrossOrigin(인증X), 시큐리티 필터에 등록 인증(O)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable);




        return http.build();
    }
}
