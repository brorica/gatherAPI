package brorica.gather.service;

import brorica.gather.domain.Team;
import brorica.gather.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional(readOnly = false)
    public Long save(Team team) {
        validateDuplicateTeamName(team);
        teamRepository.save(team);
        return team.getId();
    }

    public void validateDuplicateTeamName(Team team) {
        List<Team> findTeams = teamRepository.findByName(team.getName());
        if (!findTeams.isEmpty()) {
            throw new IllegalStateException("중복된 모임명입니다.");
        }
    }
}
