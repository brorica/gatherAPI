package brorica.gather.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import brorica.gather.config.SessionConst;
import brorica.gather.dto.member.LoginRequest;
import brorica.gather.dto.member.CreateMemberRequest;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("name", "email", "password");
        LoginRequest loginRequest = new LoginRequest(createMemberRequest.getEmail(),
            createMemberRequest.getPassword());

        // when
        mockMvc.perform(post("/api/member")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createMemberRequest)));

        // then
        mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk());
    }

    @Test
    public void 로그인실패() throws Exception {
        // given
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("name", "email", "password");
        LoginRequest loginRequest = new LoginRequest(createMemberRequest.getEmail(), "wrong password");

        // when
        mockMvc.perform(post("/api/member")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createMemberRequest)));

        // then
        mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))
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