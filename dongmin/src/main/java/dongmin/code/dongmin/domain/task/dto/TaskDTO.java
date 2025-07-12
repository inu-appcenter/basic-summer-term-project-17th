package dongmin.code.dongmin.domain.task.dto;

import dongmin.code.dongmin.domain.task.entity.Task;
import dongmin.code.dongmin.domain.user.dto.UserDTO;
import dongmin.code.dongmin.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long taskId;
    private String taskName;
    private String taskLink;
    private LocalDate submitDate;
    private String userName;
    private Long userId;

    // Entity -> DTO, DTO 반환
    public static TaskDTO toTaskDTO(Task task) {
        if (task == null) {
            return null;
        }
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setTaskName(task.getTaskName());
        taskDTO.setTaskLink(task.getTaskLink());
        taskDTO.setSubmitDate(task.getSubmitDate());
        taskDTO.setUserName(task.getUser().getUserName());
        taskDTO.setUserId(task.getUser().getId());
        return taskDTO;
    }
}
