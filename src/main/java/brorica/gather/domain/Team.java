package brorica.gather.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "team")
public class Team extends EntityDate {

    @OneToMany(
        mappedBy = "team",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private final List<TeamMember> members = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")

    private Long id;
    @Column(name = "team_name", nullable = false, unique = true)

    private String name;

    @Column(name = "team_introduce")
    private String introduce;

    public Team(String name, String introduce) {
        this.name = name;
        this.introduce = introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
