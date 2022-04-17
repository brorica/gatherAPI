package brorica.gather.repository;

import brorica.gather.domain.Member;
import brorica.gather.domain.Team;
import brorica.gather.domain.TeamMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String name);

    @Query("select tm from TeamMember tm where tm.team = ?1 and tm.member = ?2")
    Optional<TeamMember> findByTeamMember(Team team, Member member);
}
