package brorica.gather.controller;

import brorica.gather.config.SessionConst;
import brorica.gather.domain.Member;
import brorica.gather.domain.Team;
import brorica.gather.dto.member.MemberResponse;
import brorica.gather.dto.team.TeamRequest;
import brorica.gather.service.MemberService;
import brorica.gather.service.TeamMemberService;
import brorica.gather.service.TeamService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final MemberService memberService;
    private final TeamMemberService teamMemberService;

    @PostMapping("/api/team/create")
    public ResponseEntity<TeamRequest> createTeam(@RequestBody TeamRequest req, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long memberId = SessionConst.getLoginMemberId(session);
        Member member = memberService.findMember(memberId);
        Team savedTeam = teamService.save(req.toTeam());
        teamMemberService.createTeam(savedTeam, member);
        return ResponseEntity.ok()
            .body(req);
    }
}
