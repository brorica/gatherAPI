package brorica.gather.dto.teamMember;

import brorica.gather.domain.TeamMember;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamMemberResponses {

    private final List<TeamMemberResponse> teamMemberResponses = new ArrayList<>();

    public TeamMemberResponses(List<TeamMember> teamMembers) {
        for (TeamMember teamMember : teamMembers) {
            teamMemberResponses.add(
                new TeamMemberResponse(teamMember.getMember().getName(), teamMember.getRole()));
        }
    }
}
