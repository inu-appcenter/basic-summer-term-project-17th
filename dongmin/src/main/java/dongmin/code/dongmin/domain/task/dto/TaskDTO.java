package dongmin.code.dongmin.domain.task.dto;

import dongmin.code.dongmin.domain.task.entity.Task;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskDTO {

    private Long taskId;
    private String taskName;
    private String taskLink;
    private LocalDate submitDate;
    private String userName;
    private Long userId;

    @Builder
    private TaskDTO(Long taskId, String taskName, String taskLink, LocalDate submitDate, String userName, Long userId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskLink = taskLink;
        this.submitDate = submitDate;
        this.userName = userName;
        this.userId = userId;
    }

    public static TaskDTO create(Task task){
        return TaskDTO.builder()
                .taskId(task.getTaskId())
                .taskName(task.getTaskName())
                .taskLink(task.getTaskLink())
                .submitDate(task.getSubmitDate())
                .userName(task.getUserName())
                .userId(task.getUser().getId())
                .build();
    }
}
