package brorica.gather.controller;

import brorica.gather.dto.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
            .alwaysExpect(MockMvcResultMatchers.status().isOk())
            .build();
    }

    @Test
    public void 멤버가입성공() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("name1", "email1", "password1");

        // when
        String json = objectMapper.writeValueAsString(memberRequest);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 이메일중복가입() throws Exception {
        // given
        MemberRequest memberRequest1 = new MemberRequest("name1", "email1", "password1");
        MemberRequest memberRequest2 = new MemberRequest("name2", "email1", "password1");

        // when
        String json1 = objectMapper.writeValueAsString(memberRequest1);
        String json2 = objectMapper.writeValueAsString(memberRequest2);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json1));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json2))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}