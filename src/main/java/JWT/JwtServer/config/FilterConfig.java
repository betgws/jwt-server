package JWT.JwtServer.config;


import JWT.JwtServer.fillter.MyFillterOne;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFillterOne> filter1(){
        FilterRegistrationBean<MyFillterOne> bean = new FilterRegistrationBean<>(new MyFillterOne());
        bean.addUrlPatterns("/*");// 모든 url에서 다 실행해라
        bean.setOrder(0); // 낮은 번호가 가장 먼저 실행됨

        return bean;

    }
}
