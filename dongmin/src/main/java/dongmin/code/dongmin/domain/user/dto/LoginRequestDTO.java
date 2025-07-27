package dongmin.code.dongmin.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDTO {

    @Email
    @NotBlank(message = "이메일은 필수입니다.")
    @Size(max = 50, message = "입력된 이메일의 길이가 너무 깁니다.")
    private String email;

    @NotBlank(message = "패스워드는 필수입니다.")
    @Size(max = 100, message = "입력된 비밀번호의 길이가 너무 깁니다.")
    private String password;

}
