package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.Role;
import brorica.gather.domain.Team;
import brorica.gather.domain.TeamMember;
import brorica.gather.repository.TeamMemberQueryDSL;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TeamMemberTest {

    @Autowired
    MemberService memberService;
    @Autowired
    TeamService teamService;
    @Autowired
    TeamMemberService teamMemberService;
    @Autowired
    TeamMemberQueryDSL teamMemberQueryDSL;

    @Test
    public void 모임생성자는모임장() {
        // given
        Member member = createMember("name", "email");
        Team team = createTeam("team");

        // when
        memberService.save(member);
        teamService.save(team);
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
        memberService.save(member);
        teamService.save(team);
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
        memberService.save(member);
        teamService.save(team);
        teamMemberService.joinMember(team, member);
        teamMemberService.leftTeam(team, member);

        // then
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            teamMemberService.findTeamMember(team, member);
        });
    }

    @Test
    public void 부매니저변경() {
        // given
        Member member = createMember("name", "email");
        Team team = createTeam("team");

        // when
        memberService.save(member);
        teamService.save(team);
        teamMemberService.joinMember(team, member);
        teamMemberService.changeRole(team, member, Role.SUB_MANAGER);

        // then
        TeamMember teamMember = teamMemberService.findTeamMember(team, member);
        Assertions.assertEquals(teamMember.getRole(), Role.SUB_MANAGER);
    }

    @Test
    public void 모임내회원수조회() {
        // given
        Member member1 = createMember("name1", "email1");
        Member member2 = createMember("name2", "email2");
        Team team = createTeam("team");

        // when
        memberService.save(member1);
        memberService.save(member2);
        teamService.save(team);
        teamMemberService.createTeam(team, member1);
        teamMemberService.createTeam(team, member2);

        // then
        List<TeamMember> teamMemberList = teamMemberQueryDSL.getTeamMemberList(team.getId());
        Assertions.assertEquals(teamMemberList.size(), 2);
    }

    public Member createMember(String name, String email) {
        return new Member(name, email, "password", "introduce");
    }

    public Team createTeam(String name) {
        String introduce = "test introduce";
        return new Team(name, introduce);
    }
}
