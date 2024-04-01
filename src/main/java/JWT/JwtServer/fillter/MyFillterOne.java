package JWT.JwtServer.fillter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class MyFillterOne implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(servletRequest, servletResponse);
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;


        if(req.getMethod().equals("POST")){

            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);

            System.out.println("필터 1");

            if(headerAuth.equals("cos")) {
                // 토큰이 cos이거라고 생각하면 됨. id, pw가 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답해줌
                // 요청할 때 마다 header에 Authorization에 value 값으로 토큰을 가지고 옴
                // 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰인지 검증만 하면 됨 (RSA,HS256)
                filterChain.doFilter(req,res);
            }
            else{
                PrintWriter out = res.getWriter();
                out.println("인증 안됨");
            }

        }




    }


}
