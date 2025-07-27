package dongmin.code.dongmin.domain.home.service;

import dongmin.code.dongmin.domain.user.dto.LoginRequestDTO;
import dongmin.code.dongmin.domain.user.dto.UserCreateRequestDTO;
import dongmin.code.dongmin.domain.user.entity.User;
import dongmin.code.dongmin.domain.user.repository.UserRepository;
import dongmin.code.dongmin.global.jwt.JwtToken;
import dongmin.code.dongmin.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public void signup(UserCreateRequestDTO userCreateRequestDTO) {
        userRepository.save(User.create(
                userCreateRequestDTO.getName(),
                userCreateRequestDTO.getEmail(),
                bCryptPasswordEncoder.encode(userCreateRequestDTO.getPassword()),
                userCreateRequestDTO.getPart(),
                userCreateRequestDTO.getGen(),
                userCreateRequestDTO.getPhoneNumber(),
                userCreateRequestDTO.getJoinDate()
        ));
    }

    @Transactional
    public JwtToken login(LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }
}
