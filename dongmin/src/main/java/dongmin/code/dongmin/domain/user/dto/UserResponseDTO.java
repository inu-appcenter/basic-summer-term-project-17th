package dongmin.code.dongmin.domain.user.dto;

import dongmin.code.dongmin.domain.user.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String part;
    private Double gen;
    private String phoneNumber;
    private LocalDate joinDate;

    @Builder
    public UserResponseDTO(Long id, String name, String email,String part,
                           Double gen, String phoneNumber, LocalDate joinDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.part = part;
        this.gen = gen;
        this.phoneNumber = phoneNumber;
        this.joinDate = joinDate;
    }

    public static UserResponseDTO create(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .part(user.getPart())
                .gen(user.getGen())
                .phoneNumber(user.getPhoneNumber())
                .joinDate(user.getJoinDate())
                .build();
    }
}
