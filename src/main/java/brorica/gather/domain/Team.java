package brorica.gather.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Team extends EntityDate {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "TEAM_NAME", nullable = false, unique = true)
    private String name;

    @Lob @Basic(fetch=FetchType.LAZY)
    @Column(name = "TEAM_INTRODUCE")
    private byte[] introduce;

    @OneToMany(mappedBy = "team")
    private Set<MemberList> members = new HashSet<>();

    public Team(String name, byte[] introduce) {
        this.name = name;
        this.introduce = introduce;
    }
}
