package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.Role;
import brorica.gather.domain.Team;
import brorica.gather.domain.TeamMember;
import brorica.gather.repository.TeamMemberRepository;
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
    private final TeamMemberRepository teamMemberRepository;

    @Transactional(readOnly = false)
    public Long save(Team team) {
        validateDuplicateTeamName(team);
        return teamRepository.save(team).getId();
    }

    @Transactional(readOnly = false)
    public void join(Team team, Member member) {
        teamRepository.save(team);
    }

    @Transactional(readOnly = false)
    public void secession(Team team, Member member) {
        TeamMember teamMember = findTeamMember(team, member);
        teamMemberRepository.delete(teamMember);
    }

    @Transactional(readOnly = false)
    public void disband(Team team) {
        getTeam(team.getId());
        teamRepository.delete(team);
    }

    @Transactional(readOnly = false)
    public void changeIntroduce(Team team) {
        getTeam(team.getId());
        teamRepository.save(team);
    }

    public Optional<Team> findTeam(Long id) {
        return teamRepository.findById(id);
    }

    public Optional<Team> findTeam(String name) {
        return teamRepository.findByName(name);
    }

    public TeamMember findTeamMember(Team team, Member member) {
        Optional<TeamMember> findMemberList = teamRepository.findByTeamMember(team, member);
        if (findMemberList.isEmpty()) {
            throw new IllegalStateException("모임에 가입되지 않은 멤버입니다.");
        }
        return findMemberList.get();
    }

    private void validateDuplicateTeamName(Team team) {
        if (teamRepository.findByName(team.getName()).isPresent()) {
            throw new IllegalStateException("중복된 모임명입니다.");
        }
    }

    private void getTeam(Long teamId) {
        Optional<Team> findTeam = teamRepository.findById(teamId);
        if (findTeam.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 모임입니다.");
        }
    }
}
