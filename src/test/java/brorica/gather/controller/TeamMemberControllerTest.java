package brorica.gather.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import brorica.gather.domain.Member;
import brorica.gather.domain.Team;
import brorica.gather.service.MemberService;
import brorica.gather.service.TeamMemberService;
import brorica.gather.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TeamMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TeamService teamService;

    @BeforeTestClass
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(TeamMemberControllerTest.class)
            .build();
    }

    @Test
    public void 모임내회원리스트조회() throws Exception {
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
        mockMvc.perform(get("/api/teamMembers/" + team.getId().toString()))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public Member createMember(String name, String email) {
        return new Member(name, email, "password", "introduce");
    }

    public Team createTeam(String name) {
        String introduce = "test introduce";
        return new Team(name, introduce);
    }
}
