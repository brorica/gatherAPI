package brorica.gather.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import brorica.gather.config.SessionConst;
import brorica.gather.dto.member.LoginRequest;
import brorica.gather.dto.member.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
class LoginControllerTest {

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
    public void 로그인성공() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("name", "email", "password");
        String createJson = objectMapper.writeValueAsString(memberRequest);
        LoginRequest loginRequest = new LoginRequest(memberRequest.getEmail(),
            memberRequest.getPassword());
        String loginJson = objectMapper.writeValueAsString(loginRequest);

        // when
        mockMvc.perform(post("/api/member/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createJson));

        // then
        mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginJson))
            .andExpect(status().isOk());
    }

    @Test
    public void 로그인실패() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("name", "email", "password");
        String createJson = objectMapper.writeValueAsString(memberRequest);
        LoginRequest loginRequest = new LoginRequest(memberRequest.getEmail(), "wrong password");
        String loginJson = objectMapper.writeValueAsString(loginRequest);

        // when
        mockMvc.perform(post("/api/member/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createJson));

        // then
        mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginJson))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void 로그아웃() throws Exception {
        // given
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute(SessionConst.LOGIN_MEMBER, "test");

        // when
        mockMvc.perform(post("/api/logout")
            .session(mockSession));

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            mockSession.getAttribute(SessionConst.LOGIN_MEMBER);
        });
    }
}