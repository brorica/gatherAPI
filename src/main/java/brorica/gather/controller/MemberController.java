package brorica.gather.controller;

import brorica.gather.domain.Member;
import brorica.gather.dto.MemberRequest;
import brorica.gather.dto.MemberResponse;
import brorica.gather.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/join")
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest req) {
        if (memberService.findMemberByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Member member = req.toMember();
        memberService.save(member);
        return ResponseEntity.ok()
            .body(new MemberResponse(member));
    }
}
