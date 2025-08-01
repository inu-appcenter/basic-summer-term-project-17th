package dongmin.code.dongmin.domain.user.service;

import dongmin.code.dongmin.domain.user.entity.User;
import dongmin.code.dongmin.domain.user.entity.UserDetailsImpl;
import dongmin.code.dongmin.domain.user.repository.UserRepository;
import dongmin.code.dongmin.global.exception.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static dongmin.code.dongmin.global.exception.error.CustomErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) {
        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new RestApiException(USER_NOT_FOUND));

        return new UserDetailsImpl(user);
    }

}
