package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.Role;
import brorica.gather.domain.Team;
import brorica.gather.domain.TeamMember;
import brorica.gather.repository.TeamMemberQueryDSL;
import brorica.gather.repository.TeamMemberRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 모임내의 회원 관리를 위한 Service
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberQueryDSL teamMemberQueryDSL;

    @Transactional(readOnly = false)
    public void joinMember(Team team, Member member) {
        validateDuplicateJoinMember(team.getId(), member.getId());
        teamMemberRepository.save(new TeamMember(team, member, Role.GENERAL));
    }

    @Transactional(readOnly = false)
    public void createTeam(Team team, Member member) {
        validateDuplicateJoinMember(team.getId(), member.getId());
        teamMemberRepository.save(new TeamMember(team, member, Role.MANAGER));
    }

    @Transactional(readOnly = false)
    public void leftTeam(Team team, Member member) {
        TeamMember teamMember = findTeamMember(team, member);
        teamMemberRepository.delete(teamMember);
    }

    @Transactional(readOnly = false)
    public void changeRole(Team team, Member member, Role role) {
        TeamMember teamMember = findTeamMember(team, member);
        teamMember.setRole(role);
    }

    private void validateDuplicateJoinMember(Long teamId, Long memberId) {
        if (teamMemberQueryDSL.exist(teamId, memberId).isPresent()) {
            throw new IllegalStateException("이미 가입된 멤버입니다.");
        }
    }

    public TeamMember findTeamMember(Team team, Member member) {
        Optional<TeamMember> findTeamMember = teamMemberQueryDSL
            .exist(team.getId(), member.getId());
        if (findTeamMember.isEmpty()) {
            throw new NoSuchElementException("해당 회원은 이 모임에 가입되지 않았습니다.");
        }
        return findTeamMember.get();
    }

    public List<TeamMember> getTeamMembers(Long teamId) {
        return teamMemberQueryDSL.getTeamMemberList(teamId);
    }
}
