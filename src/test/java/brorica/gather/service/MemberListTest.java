package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.MemberList;
import brorica.gather.domain.Role;
import brorica.gather.domain.Team;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberListTest {

    @Autowired
    MemberService memberService;
    @Autowired
    TeamService teamService;

    @Test
    public void 구성원추가() {
        // given
        Member member = createMember("member1", "email1");
        Team team = createTeam("team1");

        // when
        memberService.save(member);
        teamService.save(team);

        team.addMember(member);
        Set<MemberList> members = team.getMembers();
        MemberList findMember = members.stream()
            .filter(list -> list.getMember().getId().equals(member.getId()))
            .findAny()
            .get();

        // then
        Assertions.assertEquals(member.getId(), findMember.getMember().getId());
    }

    @Test
    public void 가입한_모임개수조회() {
        // given
        Member member1 = createMember("member1", "email1");
        Team team1 = createTeam("team1");
        Team team2 = createTeam("team2");

        // when
        memberService.save(member1);
        teamService.save(team1);
        teamService.save(team2);

        team1.addMember(member1);
        team2.addMember(member1);

        Set<MemberList> belongs = member1.getBelongs();

        // then
        Assertions.assertEquals(2, belongs.size());
    }

    @Test
    public void 매니저등급확인() {
        // given
        Member member = createMember("member1", "email1");
        Team team = createTeam("team1");

        // when
        memberService.save(member);
        teamService.save(team);

        team.memberCreateTeam(member);

        Set<MemberList> members = team.getMembers();
        MemberList findMember = members.stream()
            .filter(list -> list.getMember().getId().equals(member.getId()))
            .findAny()
            .get();

        // then
        Assertions.assertEquals(Role.MANAGER, findMember.getRole());
    }

    public Member createMember(String name, String email) {
        return new Member(name, email, "password", "introduce");
    }

    public Team createTeam(String name) {
        String introduce = "test introduce";
        return new Team(name, introduce.getBytes());
    }
}
