package brorica.gather.controller;

import brorica.gather.config.SessionConst;
import brorica.gather.domain.Member;
import brorica.gather.domain.Team;
import brorica.gather.dto.team.CreateTeamRequest;
import brorica.gather.dto.team.JoinTeamRequest;
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
    public ResponseEntity<CreateTeamRequest> createTeam(@RequestBody CreateTeamRequest req,
        HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long memberId = SessionConst.getLoginMemberId(session);
        Member member = memberService.findMember(memberId);
        Team savedTeam = teamService.save(req.toTeam());
        teamMemberService.createTeam(savedTeam, member);
        return ResponseEntity.ok()
            .body(req);
    }

    @PostMapping("/api/team/join")
    public ResponseEntity<CreateTeamRequest> joinTeam(@RequestBody JoinTeamRequest req,
        HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long memberId = SessionConst.getLoginMemberId(session);
        Member member = memberService.findMember(memberId);
        Team findTeam = teamService.findTeam(req.getTeamId());
        teamMemberService.joinMember(findTeam, member);
        return ResponseEntity.ok().build();
    }
}
