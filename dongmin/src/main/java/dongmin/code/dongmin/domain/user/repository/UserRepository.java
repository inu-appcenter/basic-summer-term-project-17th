package dongmin.code.dongmin.domain.repository;

import dongmin.code.dongmin.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
