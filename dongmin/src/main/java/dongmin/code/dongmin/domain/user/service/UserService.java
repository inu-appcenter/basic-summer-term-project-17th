package dongmin.code.dongmin.domain.user.service;

import dongmin.code.dongmin.domain.user.dto.UserCreateRequestDTO;
import dongmin.code.dongmin.domain.user.dto.UserResponseDTO;
import dongmin.code.dongmin.domain.user.entity.User;
import dongmin.code.dongmin.domain.user.entity.UserDetailsImpl;
import dongmin.code.dongmin.domain.user.repository.UserRepository;
import dongmin.code.dongmin.global.exception.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static dongmin.code.dongmin.global.exception.error.CustomErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestApiException(USER_NOT_FOUND));

        return UserResponseDTO.create(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll(){
        List<User> userList = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        for (User user : userList) {
            userResponseDTOList.add(UserResponseDTO.create(user));
        }
        return userResponseDTOList;
    }

    @Transactional
    public void delete(Long id, UserDetailsImpl userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestApiException(USER_NOT_FOUND));

        if(!id.equals(userDetails.getUser().getId())){
            throw new RestApiException(DELETE_FORBIDDEN);
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, UserCreateRequestDTO userCreateRequestDTO, UserDetailsImpl userDetails) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestApiException(USER_NOT_FOUND));

        if(!id.equals(userDetails.getUser().getId())){
            throw new RestApiException(CHANGE_FORBIDDEN);
        }

        user.update(userCreateRequestDTO, bCryptPasswordEncoder.encode(userCreateRequestDTO.getPassword()));
    }
}
