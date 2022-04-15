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

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name", nullable = false, unique = true)
    private String name;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "team_introduce")
    private String introduce;

    @OneToMany(
        mappedBy = "team",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private final List<MemberList> members = new ArrayList<>();

    public Team(String name, String introduce) {
        this.name = name;
        this.introduce = introduce;
    }

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

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
