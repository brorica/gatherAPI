package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.Role;
import brorica.gather.domain.Team;
import brorica.gather.domain.TeamMember;
import brorica.gather.repository.MemberRepository;
import brorica.gather.repository.TeamMemberQueryDSL;
import brorica.gather.repository.TeamMemberRepository;
import brorica.gather.repository.TeamRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeamMemberTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamMemberService teamMemberService;
    @Autowired
    TeamMemberRepository teamMemberRepository;
    @Autowired
    TeamMemberQueryDSL teamMemberQueryDSL;

    @AfterEach
    void deleteAll() {
        memberRepository.deleteAll();
        teamRepository.deleteAll();
        teamMemberRepository.deleteAll();
    }

    @Test
    public void 모임생성자는모임장() {
        // given
        Member member = createMember("name", "email");
        Team team = createTeam("team");

        // when
        memberRepository.save(member);
        teamRepository.save(team);
        teamMemberService.createTeam(team, member);

        // then
        TeamMember teamMember = teamMemberService.findTeamMember(team, member);
        Assertions.assertEquals(teamMember.getRole(), Role.MANAGER);
    }

    @Test
    public void 중복가입방지() {
        // given
        Member member = createMember("name", "email");
        Team team = createTeam("team");

        // when
        memberRepository.save(member);
        teamRepository.save(team);
        teamMemberService.joinMember(team, member);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            teamMemberService.joinMember(team, member);
        });
    }

    @Test
    public void 모임탈퇴() {
        // given
        Member member = createMember("name", "email");
        Team team = createTeam("team");

        // when
        memberRepository.save(member);
        teamRepository.save(team);
        teamMemberService.joinMember(team, member);
        teamMemberService.leftTeam(team, member);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            teamMemberService.findTeamMember(team, member);
        });
    }

    @Test
    public void 부매니저변경() {
        // given
        Member member = createMember("name", "email");
        Team team = createTeam("team");

        // when
        memberRepository.save(member);
        teamRepository.save(team);
        teamMemberService.joinMember(team, member);
        teamMemberService.changeRole(team, member, Role.SUB_MANAGER);

        // then
        TeamMember teamMember = teamMemberService.findTeamMember(team, member);
        Assertions.assertEquals(teamMember.getRole(), Role.SUB_MANAGER);
    }

    public Member createMember(String name, String email) {
        return new Member(name, email, "password", "introduce");
    }

    public Team createTeam(String name) {
        String introduce = "test introduce";
        return new Team(name, introduce);
    }
}
