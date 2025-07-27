package code.joonseo.domain.homework.repository;

import code.joonseo.domain.homework.entity.homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends JpaRepository<homework, Long> {
}
