package dongmin.code.dongmin.domain.task.dto;

import dongmin.code.dongmin.domain.task.entity.Task;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskResponseDTO {

    private Long taskId;
    private String taskName;
    private String taskLink;
    private LocalDate submitDate;
    private String userName;

    @Builder
    private TaskResponseDTO(Long taskId, String taskName, String taskLink, LocalDate submitDate, String userName) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskLink = taskLink;
        this.submitDate = submitDate;
        this.userName = userName;
    }

    public static TaskResponseDTO create(Task task){
        return TaskResponseDTO.builder()
                .taskId(task.getTaskId())
                .taskName(task.getTaskName())
                .taskLink(task.getTaskLink())
                .submitDate(task.getSubmitDate())
                .userName(task.getUserName())
                .build();
    }
}
