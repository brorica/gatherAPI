package brorica.gather.repository;

import brorica.gather.domain.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String name);
}
