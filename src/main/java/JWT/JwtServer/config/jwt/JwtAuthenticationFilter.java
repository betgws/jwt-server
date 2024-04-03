package JWT.JwtServer.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// 스프링 시큐리티에서 이 필터가 있음
// login이라고 요청해서 username, password 전송하면 (Post)
// UsernamePass 어쩌거가 동작을 함
// 근데 security config 에서 form login 을 disable 해서 동작을 안했음.



// /login으로 요청이 오면 얘가 낚아챔
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    // 로그인 요청을 하면 로그인 시도를 위해서 실행되는 함수

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 1. username, password를 받아서
        // 2. 정상인지 아닌지 시도를 해본다. authenticaionManager로 로그인 시도를 하면 PrincipalDetalisService가 호출된다

        //3. PrincipalDetails를 세션에 담고 (권한 관리를 위해서)
        // 4. JWT 토근을 만들어서 응답해주면 됨
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");
        return super.attemptAuthentication(request, response);
    }
}
