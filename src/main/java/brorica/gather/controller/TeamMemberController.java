package brorica.gather.controller;

import brorica.gather.domain.TeamMember;
import brorica.gather.dto.teamMember.TeamMemberResponses;
import brorica.gather.service.TeamMemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @GetMapping("/api/teamMembers/{teamId}")
    public ResponseEntity<TeamMemberResponses> getMemberList(@PathVariable long teamId) {
        List<TeamMember> teamMembers = teamMemberService.getTeamMembers(teamId);
        TeamMemberResponses teamMemberResponses = new TeamMemberResponses(teamMembers);
        return new ResponseEntity<>(teamMemberResponses, HttpStatus.OK);
    }
}
