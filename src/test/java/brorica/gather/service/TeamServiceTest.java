package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.Team;
import brorica.gather.repository.MemberRepository;
import brorica.gather.repository.TeamRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeamServiceTest {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    Member member1, member2;

    @BeforeEach
    void createTestMembers() {
        member1 = new Member("name1", "email1", "password", "introduce");
        member2 = new Member("name2", "email2", "password", "introduce");

        memberService.save(member1);
        memberService.save(member2);
    }

    @AfterEach
    void deleteAll() {
        teamRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 모임생성() {
        // given
        Team team = createTeam("team");

        // when
        teamService.save(team, member1);

        // then
        Team foundTeam = teamService.findTeam(team.getId()).orElseThrow(IllegalStateException::new);
        Assertions.assertEquals(foundTeam.getId(), team.getId());
    }

    @Test
    void 중복된모임명() {
        // given
        Team team1 = createTeam("team1");
        Team team2 = createTeam("team1");

        // when
        teamService.save(team1, member1);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            teamService.save(team2, member1);
        });
    }

    @Test
    void 모임해체() {
        // given
        Team team = createTeam("team");

        // when
        teamService.save(team, member1);
        teamService.disband(team);

        // then
        Optional<Team> findTeam = teamService.findTeam(team.getId());
        Assertions.assertEquals(findTeam.isEmpty(), true);
    }

    @Test
    void 모임명검색() {
        // given
        Team team = createTeam("team");

        // when
        teamService.save(team, member1);

        // then
        Team findTeam = teamService.findTeam(team.getName()).get();
        Assertions.assertEquals(team.getId(), findTeam.getId());
    }

    @Test
    void 모임소개수정() {
        // given
        Team team = createTeam("team");
        String changeIntroduce = "change Introduce";

        // when
        teamService.save(team, member1);
        team.setIntroduce(changeIntroduce);
        teamService.changeIntroduce(team);

        // then
        Team findTeam = teamService.findTeam(team.getId()).get();
        Assertions.assertEquals(changeIntroduce, findTeam.getIntroduce());
    }

    public Team createTeam(String name) {
        String introduce = "test introduce";
        return new Team(name, introduce);
    }
}
