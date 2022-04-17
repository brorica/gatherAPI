package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.Role;
import brorica.gather.domain.Team;
import brorica.gather.domain.TeamMember;
import brorica.gather.repository.MemberRepository;
import brorica.gather.repository.TeamRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeamMemberTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamService teamService;
    @Autowired
    TeamRepository teamRepository;

    @AfterEach
    void deleteAll() {
        memberRepository.deleteAll();
        teamRepository.deleteAll();
    }


    @Test
    void 모임멤버추가() {
        // given
        Member member = createMember("member1", "email1");
        Team team = createTeam("team1");

        // when
        memberService.save(member);
        teamService.save(team, member);

        List<TeamMember> members = team.getMembers();
        TeamMember findMember = members.stream()
            .filter(list -> list.getMember().getId().equals(member.getId()))
            .findAny()
            .get();

        // then
        Assertions.assertEquals(member.getId(), findMember.getMember().getId());
    }

    @Test
    void 매니저등급확인() {
        // given
        Member member = createMember("member1", "email1");
        Team team = createTeam("team1");

        // when
        memberService.save(member);
        teamService.save(team, member);

        List<TeamMember> members = team.getMembers();
        TeamMember findMember = members.stream()
            .filter(list -> list.getMember().getId().equals(member.getId()))
            .findAny()
            .get();

        // then
        Assertions.assertEquals(Role.MANAGER, findMember.getRole());
    }

    @Test
    void 일반등급확인() {
        // given
        Member member1 = createMember("member1", "email1");
        Member member2 = createMember("member2", "email2");
        Team team = createTeam("team1");

        // when
        memberService.save(member1);
        memberService.save(member2);
        teamService.save(team, member1);
        teamService.join(team, member2);

        List<TeamMember> members = team.getMembers();
        TeamMember findMember = members.stream()
            .filter(list -> list.getMember().getId().equals(member2.getId()))
            .findAny()
            .get();

        // then
        Assertions.assertEquals(Role.GENERAL, findMember.getRole());
    }

    @Test
    void 모임멤버탈퇴() {
        // given
        Member member1 = createMember("member1", "email1");
        Member member2 = createMember("member2", "email2");
        Team team = createTeam("team1");

        // when
        memberService.save(member1);
        memberService.save(member2);
        teamService.save(team, member1);
        teamService.join(team, member2);
        teamService.secession(team, member2);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            teamService.findTeamMember(team, member2);
        });

    }

    public Member createMember(String name, String email) {
        return new Member(name, email, "password", "introduce");
    }

    public Team createTeam(String name) {
        String introduce = "test introduce";
        return new Team(name, introduce);
    }
}
