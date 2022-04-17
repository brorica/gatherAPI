package brorica.gather.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "team")
public class Team extends EntityDate {

    @OneToMany(
        mappedBy = "team",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private final List<TeamMember> members = new ArrayList<>();
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    @Column(name = "team_name", nullable = false, unique = true)
    private String name;
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "team_introduce")
    private String introduce;

    public Team(String name, String introduce) {
        this.name = name;
        this.introduce = introduce;
    }

    public void addMember(Member member, Role role) {
        TeamMember teamMember = new TeamMember(this, member, role);
        members.add(teamMember);
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
