package code.joonseo.domain.homework.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "homework")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "submission_date", nullable = false)
    private String submissionDate;

    @Builder
    private homework(Long taskId, Long userId, String taskName, String submissionDate) {
        this.taskId = taskId;
        this.userId = userId;
        this.taskName = taskName;
        this.submissionDate = submissionDate;
    }

    public static homework create(Long taskId, Long userId, String taskName, String submissionDate) {
        return homework.builder()
                .taskId(taskId)
                .userId(userId)
                .taskName(taskName)
                .submissionDate(submissionDate)
                .build();
    }

}
