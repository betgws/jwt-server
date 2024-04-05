package JWT.JwtServer.config.jwt;

import JWT.JwtServer.config.auth.PrincipalDetails;
import JWT.JwtServer.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;


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

        //1. username, password를 받는다.
        try {
           // BufferedReader br =request.getReader();
           // String input = null;
           // while ((input = br.readLine()) != null){
           //     System.out.println(input);
            //}
           // System.out.println(request.getInputStream());
            ObjectMapper om = new ObjectMapper(); // Json 객체를 파싱해줌
            User user = om.readValue(request.getInputStream(), User.class); // user 오브젝트에 담아줌
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());

            // PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // authentication 객체가 session영역에 저장됨 => 로그인이 되었다는 뜻.
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUser().getUsername());

            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }


}
