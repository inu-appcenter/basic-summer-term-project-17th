package dongmin.code.dongmin.domain.user.dto;

import dongmin.code.dongmin.domain.user.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String userName;
    private String part;
    private Double gen;
    private String phoneNumber;
    private String joinDate;

    // Entity -> DTO, DTO 반환
    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setPart(user.getPart());
        userDTO.setGen(user.getGen());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setJoinDate(user.getJoinDate());
        return userDTO;
    }
}
