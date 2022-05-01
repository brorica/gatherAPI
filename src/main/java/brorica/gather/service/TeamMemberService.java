package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.Role;
import brorica.gather.domain.Team;
import brorica.gather.domain.TeamMember;
import brorica.gather.repository.TeamMemberRepository;
import brorica.gather.repository.TeamMemberQueryDSL;
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

    /**
     * 모임의 첫 멤버는 모임장이므로 MANAGER 권한
     */
    @Transactional(readOnly = false)
    public void FirstMember(Team team, Member member) {
        validateDuplicateJoinMember(team.getId(), member.getId());
        teamMemberRepository.save(new TeamMember(team, member, Role.MANAGER));
    }

    private void validateDuplicateJoinMember(Long teamId, Long memberId) {
        if (teamMemberQueryDSL.exist(teamId, memberId).isPresent()) {
            throw new IllegalStateException("이미 가입된 멤버입니다.");
        }
    }
}
