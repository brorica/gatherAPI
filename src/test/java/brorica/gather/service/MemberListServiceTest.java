package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.MemberList;
import brorica.gather.domain.Role;
import brorica.gather.domain.Team;
import brorica.gather.repository.MemberListRepository;
import brorica.gather.repository.MemberRepository;
import brorica.gather.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class MemberListServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    TeamService teamService;
    @Autowired
    MemberListRepository memberListRepository;

    @Test
    public void 구성원추가() {
        // given
        Member member = createMember("member1", "email1");
        Team team = createTeam("team1");

        // when
        memberService.save(member);
        teamService.save(team);
        team.addMember(member);
        List<MemberList> members = memberListRepository.findByTeamId(team.getId());
        MemberList findMember = members.stream()
            .filter(id -> id.getMember().getId().equals(member.getId()))
            .findAny()
            .get();

        // then
        Assertions.assertEquals(member.getId(), findMember.getMember().getId());
    }

    @Test
    public void 모임_내에서_특정_구성원_찾기() {
        // given
        Member member1 = createMember("member1", "email1");
        Member member2 = createMember("member2", "email2");
        Member member3 = createMember("member3", "email3");
        Team team1 = createTeam("team1");
        Team team2 = createTeam("team2");

        // when
        memberService.save(member1);
        memberService.save(member2);
        memberService.save(member3);
        teamService.save(team1);
        teamService.save(team2);
        team1.addMember(member1);
        team1.addMember(member3);
        team2.addMember(member2);

        List<MemberList> members = memberListRepository.findByTeamId(team1.getId());
        MemberList findMember = members.stream()
            .filter(id -> id.getMember().getId().equals(member3.getId()))
            .findAny()
            .get();

        // then
        Assertions.assertEquals(member3.getId(), findMember.getMember().getId());
    }

    @Test
    public void 모임생성시_모임장확인() {
        // given
        Team team = createTeam("team1");
        Member member = createMember("member1", "email1");

        // when
        memberService.save(member);
        teamService.save(team);
        team.memberCreateTeam(member);

        List<MemberList> members = memberListRepository.findByTeamId(team.getId());
        MemberList findMember = members.stream()
            .filter(teamId -> teamId.getTeam().getId().equals(team.getId()))
            .filter(role -> role.getRole().equals(Role.MANAGER))
            .findAny()
            .get();

        // then
        Assertions.assertEquals(member.getId(), findMember.getMember().getId());
    }

    public Member createMember(String name, String email) {
        return new Member(name, email, "introduce");
    }

    public Team createTeam(String name) {
        String introduce = "test introduce";
        return new Team(name, introduce.getBytes());
    }
}
