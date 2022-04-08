package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.MemberList;
import brorica.gather.domain.Team;
import brorica.gather.repository.MemberListRepository;
import brorica.gather.repository.MemberRepository;
import brorica.gather.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class MemberListTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    MemberListRepository memberListRepository;

    @Test
    public void 구성원추가() {
        // given
        Member member = new Member("name", "email", "introduce");
        Team team = new Team("name", null);

        // when
        memberRepository.save(member);
        teamRepository.save(team);
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
        Member member1 = new Member("name1", "email1", "introduce");
        Member member2 = new Member("name2", "email2", "introduce");
        Member member3 = new Member("name3", "email3", "introduce");
        Team team1 = new Team("team1", null);
        Team team2 = new Team("team2", null);

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        teamRepository.save(team1);
        teamRepository.save(team2);
        team1.addMember(member1);
        team1.addMember(member3);
        team2.addMember(member2);

        List<MemberList> members = memberListRepository.findByTeamId(team1.getId());
        MemberList findMember = members.stream()
            .filter(id -> id.getMember().getId().equals(member3.getId()))
            .findAny()
            .get();

        // then
        Assertions.assertEquals(member1.getId(), findMember.getMember().getId());
    }
}
