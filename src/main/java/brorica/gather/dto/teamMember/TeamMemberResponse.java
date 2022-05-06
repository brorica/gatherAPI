package brorica.gather.dto.teamMember;

import brorica.gather.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberResponse {

    private String memberName;
    private Role role;
}
