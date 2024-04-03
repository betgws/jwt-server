package JWT.JwtServer.config;

import JWT.JwtServer.config.jwt.JwtAuthenticationFilter;
import JWT.JwtServer.fillter.MyFillterOne;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

import static org.apache.tomcat.jni.SSLConf.apply;
import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;


@Configuration
@EnableWebSecurity
@AllArgsConstructor

public class SecurityConfig {



    private CorsFilter corsFilter;
    private CorsConfig corsConfig;





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http.addFilterBefore(new MyFillterOne(), BasicAuthenticationFilter.class); //Basic 뭐시기가 실행되기 전에 한다는 뜻
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
        .httpBasic(AbstractHttpConfigurer::disable); // 매번 요청할 때마다 ID랑 Password를 달고 옴
        http.addFilter(new JwtAuthenticationFilter(authenticationManager));






        return http.build();
    }




}
