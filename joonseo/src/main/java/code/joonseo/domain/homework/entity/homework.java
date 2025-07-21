package code.joonseo.domain.homework.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "homework")
@Getter
@Entity
public class homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;

    @Column(name = "task_name")
    private String taskName;


}
