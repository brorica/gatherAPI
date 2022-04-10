package brorica.gather.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "team")
public class Team extends EntityDate {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name", nullable = false, unique = true)
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "team_introduce")
    private byte[] introduce;

    @OneToMany(
        mappedBy = "team",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<MemberList> members = new HashSet<>();

    // 모임을 개설하는 멤버는 MANAGER 등급
    public void memberCreateTeam(Member member) {
        MemberList memberList = new MemberList(this, member, Role.MANAGER);
        members.add(memberList);
        member.getBelongs().add(memberList);
    }

    // 모임에 처음 가입할 땐 일반 등급
    public void addMember(Member member) {
        MemberList memberList = new MemberList(this, member, Role.GENERAL);
        members.add(memberList);
        member.getBelongs().add(memberList);
    }

    public Team(String name, byte[] introduce) {
        this.name = name;
        this.introduce = introduce;
    }
}
