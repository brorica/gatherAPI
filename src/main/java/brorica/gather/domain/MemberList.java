package brorica.gather.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_list")
@EqualsAndHashCode
public class MemberList extends EntityDate {

    @Id
    @GeneratedValue
    @Column(name = "member_list_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime secessionAt;

    public MemberList(Team team, Member member, Role role) {
        this.team = team;
        this.member = member;
        this.role = role;
    }
}
