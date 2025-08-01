package code.joonseo.domain.user.service;

import code.joonseo.domain.user.entity.User;
import code.joonseo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저 조회 실패"));

        return new UserDetailsImpl(user);
    }
}
