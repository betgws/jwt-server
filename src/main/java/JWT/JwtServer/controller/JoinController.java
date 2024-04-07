package JWT.JwtServer.controller;


import JWT.JwtServer.dto.JoinDto;
import JWT.JwtServer.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;
    @PostMapping("/join")
    public String joinProcess(JoinDto joinDto){

        joinService.joinprocess(joinDto);

        return "ok";
    }
}
