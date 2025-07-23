package dongmin.code.dongmin.domain.home.service;

import dongmin.code.dongmin.domain.user.dto.UserCreateRequestDTO;
import dongmin.code.dongmin.domain.user.entity.User;
import dongmin.code.dongmin.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
}
