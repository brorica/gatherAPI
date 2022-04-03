package brorica.gather.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "TEAM_NAME")
    private String name;

    @Column(name = "TEAM_INTRODUCE")
    private String introduce;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
