package JWT.JwtServer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class RestApiController {
    @GetMapping("/home")
    public @ResponseBody String home(){

        return "<h1>home</h1>";
    }
}
