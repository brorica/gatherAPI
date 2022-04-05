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
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name", nullable = false, unique = true)
    private String name;

    @Lob @Basic(fetch=FetchType.LAZY)
    @Column(name = "team_introduce")
    private byte[] introduce;

    @OneToMany(mappedBy = "team")
    private Set<MemberList> members = new HashSet<>();

    public Team(String name, byte[] introduce) {
        this.name = name;
        this.introduce = introduce;
    }
}
