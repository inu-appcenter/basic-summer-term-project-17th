package code.joonseo.domain.user.service;

import code.joonseo.domain.user.dto.res.UserInfoService;
import code.joonseo.domain.user.entity.User;
import code.joonseo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(UserInfoService dto) {
        User user = new User(null, dto.getName(), dto.getPart(), dto.getGen(), dto.getPhoneNumber(), dto.getEmail(), dto.getPassword());
        return userRepository.save(user);
    }

    public User login(Long id, String password) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
