package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.domain.MemberList;
import brorica.gather.domain.Team;
import brorica.gather.repository.TeamRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamServiceTest {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void 모임생성() {
        // given
        Team team = createTeam("team1");

        // when
        teamService.save(team);

        // then
        assertEquals(team, teamRepository.findOne(team.getId()));
    }

    @Test
    public void 중복되는모임명() {
        // given
        Team team1 = createTeam("team1");
        Team team2 = createTeam("team1");

        // when
        teamService.save(team1);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            teamService.save(team2);
        });
    }

    @Test
    public void 소개문일치확인() {
        // given
        Team team = createTeam("team1");

        // when
        teamService.save(team);
        Team findTeam = teamRepository.findOne(team.getId());

        // then
        assertEquals(team.getIntroduce(), findTeam.getIntroduce());
    }

    public Team createTeam(String name) {
        String introduce = "test introduce";
        return new Team(name, introduce.getBytes());
    }

}