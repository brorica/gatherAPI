package brorica.gather.controller;

import brorica.gather.domain.Member;
import brorica.gather.dto.member.LoginRequest;
import brorica.gather.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/login")
    public ResponseEntity login(@SessionAttribute(name = "session", required = false) @RequestBody LoginRequest body) {
        Member loginMember = loginService.login(body.getEmail(), body.getPassword());
        if (loginMember == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/test")
    public ResponseEntity test(@SessionAttribute(name = "session", required = false) @RequestBody LoginRequest body) {
        return ResponseEntity.ok().build();
    }
}
