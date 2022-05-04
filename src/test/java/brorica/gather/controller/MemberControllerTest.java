package brorica.gather.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import brorica.gather.config.SessionConst;
import brorica.gather.dto.member.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeTestClass
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(MemberController.class)
            .build();
    }

    @Test
    public void 멤버가입성공() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("name", "email", "password");

        // when, then
        mockMvc.perform(post("/api/member/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberRequest)))
            .andExpect(status().isOk());
    }

    @Test
    public void 이메일중복가입() throws Exception {
        // given
        MemberRequest memberRequest1 = new MemberRequest("name1", "email1", "password1");
        MemberRequest memberRequest2 = new MemberRequest("name2", "email1", "password1");

        // when
        mockMvc.perform(post("/api/member/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberRequest1)));

        // then
        mockMvc.perform(post("/api/member/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberRequest2)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void 회원정보조회() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("name", "email", "password");
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute(SessionConst.LOGIN_MEMBER, "test");

        // when
        mockMvc.perform(post("/api/member/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberRequest)));

        // then
        mockMvc.perform(post("/api/member/info")
            .session(mockSession)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberRequest)))
            .andExpect(
                content().json("{\"name\":\"name\",\"email\":\"email\",\"introduce\":\"\"}"));
    }
}