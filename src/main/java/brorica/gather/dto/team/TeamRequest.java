package brorica.gather.dto.team;

import brorica.gather.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequest {
    private String name;
    private String introduce;

    public Team toTeam() {
        return new Team(name, introduce);
    }
}
