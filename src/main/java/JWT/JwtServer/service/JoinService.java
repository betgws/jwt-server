package JWT.JwtServer.service;


import JWT.JwtServer.dto.JoinDto;
import JWT.JwtServer.entity.UserEntity;
import JWT.JwtServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinprocess(JoinDto joinDto){

        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        boolean isExist = userRepository.existsByUsername(username);
        if(isExist){

            return;

        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
