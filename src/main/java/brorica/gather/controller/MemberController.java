package brorica.gather.controller;

import brorica.gather.domain.Member;
import brorica.gather.dto.member.CreateMemberRequest;
import brorica.gather.dto.member.GetMemberResponse;
import brorica.gather.service.MemberService;
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

    @PostMapping("/api/member")
    public ResponseEntity createMember(@RequestBody CreateMemberRequest req) {
        Member member = req.toMember();
        memberService.save(member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/member/{memberId}")
    public ResponseEntity<GetMemberResponse> getMemberInfo(@PathVariable long memberId) {
        Member member = memberService.findMember(memberId);
        return ResponseEntity.ok()
            .body(new GetMemberResponse(member));
    }
}
