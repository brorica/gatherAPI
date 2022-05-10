package brorica.gather.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import brorica.gather.dto.member.CreateMemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("name", "email", "password");

        // when, then
        mockMvc.perform(post("/api/member")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createMemberRequest)))
            .andExpect(status().isOk());
    }

    @Test
    public void 이메일중복가입() throws Exception {
        // given
        CreateMemberRequest createMemberRequest1 = new CreateMemberRequest("name1", "email1", "password1");
        CreateMemberRequest createMemberRequest2 = new CreateMemberRequest("name2", "email1", "password1");

        // when
        mockMvc.perform(post("/api/member")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createMemberRequest1)));

        // then
        mockMvc.perform(post("/api/member")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createMemberRequest2)))
            .andExpect(status().isBadRequest());
    }

    /**
     * 만약 이 테스트가 실패한다면 테이블에서
     *
     * @GeneratedValue(strategy = GenerationType.IDENTITY) 가 초기화 되지 않은 문제이므로 다른 ID 값을 넣어서 테스트
     */
    @Test
    public void 회원정보조회() throws Exception {
        // given
        CreateMemberRequest createMemberRequest = new CreateMemberRequest("name", "email", "password");

        // when
        mockMvc.perform(post("/api/member")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createMemberRequest)));

        // then
        mockMvc.perform(get("/api/member/3"))
            .andExpect(status().isOk());
    }
}