package code.joonseo.domain.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDto {

    private String name;
    private String part;
    private Double gen;

    private Long id;
    private String password;
    private String email;
    private String phoneNumber;

    public UserInfoDto() {
    }
}
