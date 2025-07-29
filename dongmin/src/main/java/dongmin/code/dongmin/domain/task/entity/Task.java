package dongmin.code.dongmin.domain.task.entity;

import dongmin.code.dongmin.domain.task.dto.TaskCreateRequestDTO;
import dongmin.code.dongmin.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    private Task(Long taskId, String taskName, String taskLink, LocalDate submitDate, String userName, User user) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskLink = taskLink;
        this.submitDate = submitDate;
        this.userName = userName;
        this.user = user;
    }

    public static Task create(TaskCreateRequestDTO taskCreateRequestDTO, User user){
        return Task.builder()
                .taskName(taskCreateRequestDTO.getTaskName())
                .taskLink(taskCreateRequestDTO.getTaskLink())
                .submitDate(LocalDate.now())
                .userName(user.getName())
                .user(user)
                .build();
    }

    public void update(TaskCreateRequestDTO taskCreateRequestDTO) {
        this.taskName = taskCreateRequestDTO.getTaskName();
        this.taskLink = taskCreateRequestDTO.getTaskLink();
        this.submitDate = LocalDate.now();
    }
}
