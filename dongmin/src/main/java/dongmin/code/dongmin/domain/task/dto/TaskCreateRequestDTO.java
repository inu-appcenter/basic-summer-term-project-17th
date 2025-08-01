package dongmin.code.dongmin.domain.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskCreateRequestDTO {

    @NotBlank(message = "이름은 필수")
    private String taskName;

    private String taskLink;
}
