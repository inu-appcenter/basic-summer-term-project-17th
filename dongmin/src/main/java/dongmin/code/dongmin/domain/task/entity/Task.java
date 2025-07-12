package dongmin.code.dongmin.domain.task.entity;

import dongmin.code.dongmin.domain.task.dto.TaskDTO;
import dongmin.code.dongmin.domain.user.dto.UserDTO;
import dongmin.code.dongmin.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "task")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_link")
    private String taskLink;

    @Column(name = "submit_date")
    private LocalDate submitDate;

    @Column(name = "user_name")
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // DTO -> Entity, Entity 반환
    public static Task toTask(TaskDTO taskDTO, User user) {
        Task task = new Task();
        task.taskName = taskDTO.getTaskName();
        task.taskLink = taskDTO.getTaskLink();
        task.submitDate = LocalDate.now();
        task.userName = user.getUserName();
        task.user = user;
        return task;
    }

    // DTO에 담긴 정보로 업데이트
    public void updateTask(TaskDTO taskDTO, User user) {
        this.taskName = taskDTO.getTaskName();
        this.taskLink = taskDTO.getTaskLink();
        this.submitDate = LocalDate.now();
        this.userName = user.getUserName();
        this.user = user;
    }
}
