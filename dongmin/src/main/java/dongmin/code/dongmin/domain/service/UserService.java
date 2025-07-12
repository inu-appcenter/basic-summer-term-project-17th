package dongmin.code.dongmin.domain.service;

import dongmin.code.dongmin.domain.user.dto.UserDTO;
import dongmin.code.dongmin.domain.user.entity.User;
import dongmin.code.dongmin.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return UserDTO.toUserDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll(){
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            userDTOList.add(UserDTO.toUserDTO(user));
        }
        return userDTOList;
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.deleteById(id);
    }

    @Transactional
    public void save(UserDTO userDTO) {
        User user = User.toUser(userDTO);
        userRepository.save(user);
    }

    @Transactional
    public void update(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.updateUser(userDTO);
    }
}
