package brorica.gather.controller;

import brorica.gather.config.SessionConst;
import brorica.gather.domain.Member;
import brorica.gather.dto.member.MemberRequest;
import brorica.gather.dto.member.MemberResponse;
import brorica.gather.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/member/join")
    public ResponseEntity createMember(@RequestBody MemberRequest req) {
        Member member = req.toMember();
        memberService.save(member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/member/{memberId}")
    public ResponseEntity<MemberResponse> getMemberInfo(@PathVariable long memberId) {
        Member member = memberService.findMember(memberId);
        return ResponseEntity.ok()
            .body(new MemberResponse(member));
    }
}
