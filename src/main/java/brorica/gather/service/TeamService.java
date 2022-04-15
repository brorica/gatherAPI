package brorica.gather.service;

import brorica.gather.domain.Team;
import brorica.gather.repository.TeamRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional(readOnly = false)
    public Long save(Team team) {
        validateDuplicateTeamName(team);
        return teamRepository.save(team).getId();
    }

    @Transactional(readOnly = false)
    public void disband(Team team) {
        isTeamExist(team);
        teamRepository.delete(team);
    }

    @Transactional(readOnly = false)
    public void changeIntroduce(Team team) {
        isTeamExist(team);
        teamRepository.save(team);
    }

    public Optional<Team> findTeam(Long id) {
        return teamRepository.findById(id);
    }

    public Optional<Team> findTeam(String name) {
        return teamRepository.findByName(name);
    }

    private void validateDuplicateTeamName(Team team) {
        if (teamRepository.findByName(team.getName()).isPresent()) {
            throw new IllegalStateException("중복된 모임명입니다.");
        }
    }

    private void isTeamExist(Team team) {
        if (teamRepository.findById(team.getId()).isEmpty()) {
            throw new IllegalStateException("존재하지 않는 모임입니다.");
        }
    }
}
