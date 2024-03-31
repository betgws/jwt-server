package JWT.JwtServer.fillter;

import jakarta.servlet.*;

import java.io.IOException;

public class MyFillterOne implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("필터 1");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
