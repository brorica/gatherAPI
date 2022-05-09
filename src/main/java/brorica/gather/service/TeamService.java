package brorica.gather.service;

import brorica.gather.domain.Team;
import brorica.gather.repository.TeamRepository;
import java.util.NoSuchElementException;
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
    public Team save(Team team) {
        validateDuplicateTeamName(team);
        return teamRepository.save(team);
    }

    @Transactional(readOnly = false)
    public void disband(Team team) {
        findTeam(team.getId());
        teamRepository.delete(team);
    }

    @Transactional(readOnly = false)
    public void changeIntroduce(Team team, String newIntroduce) {
        Team findTeam = findTeam(team.getId());
        findTeam.setIntroduce(newIntroduce);
    }

    private void validateDuplicateTeamName(Team team) {
        if (teamRepository.findByName(team.getName()).isPresent()) {
            throw new IllegalStateException("중복된 모임명입니다.");
        }
    }

    public Team findTeam(Long teamId) {
        Optional<Team> findTeam = teamRepository.findById(teamId);
        if (findTeam.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 모임입니다.");
        }
        return findTeam.get();
    }

    public Team findTeam(String name) {
        Optional<Team> findTeam = teamRepository.findByName(name);
        if (findTeam.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 모임입니다.");
        }
        return findTeam.get();
    }
}
