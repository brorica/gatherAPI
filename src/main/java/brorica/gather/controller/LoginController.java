package brorica.gather.controller;

import brorica.gather.config.SessionConst;
import brorica.gather.domain.Member;
import brorica.gather.dto.member.LoginRequest;
import brorica.gather.service.LoginService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
  
    private final int sessionDuration = 1800;

    @PostMapping("/api/login")
    public ResponseEntity login(
        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
        @RequestBody LoginRequest body, HttpServletRequest request) {
        Member loginMember = loginService.login(body);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getId());
        session.setMaxInactiveInterval(sessionDuration);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok().build();
    }
}
