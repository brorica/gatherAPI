package brorica.gather.dto.team;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamRequest {
    private String name;
    private String introduce;
}
