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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
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

        // then
        Assertions.assertEquals(team.getId(), members.get(0).getTeam().getId());
    }
}
