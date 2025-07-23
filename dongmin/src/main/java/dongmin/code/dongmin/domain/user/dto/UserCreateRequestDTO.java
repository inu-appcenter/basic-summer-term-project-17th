package dongmin.code.dongmin.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateRequestDTO {

    @NotBlank(message = "이름은 필수")
    private String name;

    @Email
    @NotBlank(message = "이메일은 필수")
    private String email;

    @NotBlank(message = "패스워드는 필수")
    private String password;

    private String part;
    private Double gen;
    private String phoneNumber;
    private LocalDate joinDate;

}
