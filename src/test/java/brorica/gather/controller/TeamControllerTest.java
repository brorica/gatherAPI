package brorica.gather.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;

import brorica.gather.config.SessionConst;
import brorica.gather.domain.Member;
import brorica.gather.dto.member.MemberRequest;
import brorica.gather.dto.team.TeamRequest;
import brorica.gather.repository.MemberRepository;
import brorica.gather.service.MemberService;
import brorica.gather.service.TeamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @BeforeTestClass
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(TeamController.class)
            .build();
    }

    @Test
    public void 팀생성() throws Exception {
        // given
        Member member = createMember("name", "email");
        TeamRequest teamRequest = new TeamRequest("team", "");
        MockHttpSession mockSession = new MockHttpSession();

        // when
        Member savedMember = memberService.save(member);
        mockSession.setAttribute(SessionConst.LOGIN_MEMBER, savedMember.getId());

        // then
        mockMvc.perform(post("/api/team/create")
            .session(mockSession)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(teamRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 존재하지않은_사용자가_팀생성() throws Exception {
        // given
        Member member = createMember("name", "email");
        TeamRequest teamRequest = new TeamRequest("team", "");
        MockHttpSession mockSession = new MockHttpSession();

        // when
        Member savedMember = memberService.save(member);
        mockSession.setAttribute(SessionConst.LOGIN_MEMBER, -1L);

        // then
        mockMvc.perform(post("/api/team/create")
            .session(mockSession)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(teamRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    public Member createMember(String name, String email) {
        return new Member(name, email, "password", "introduce");
    }
}