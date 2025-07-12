package dongmin.code.dongmin.domain.task.repository;

import dongmin.code.dongmin.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
