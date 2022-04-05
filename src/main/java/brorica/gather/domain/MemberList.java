package brorica.gather.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_list")
public class MemberList implements Serializable {

    @Id @GeneratedValue
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
    
    private LocalDateTime joinedAt;
    private LocalDateTime secessionAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberList that = (MemberList) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getMember(), that.getMember()) &&
                Objects.equals(getTeam(), that.getTeam()) &&
                getRole() == that.getRole() && Objects.equals(getJoinedAt(), that.getJoinedAt()) &&
                Objects.equals(getSecessionAt(), that.getSecessionAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMember(), getTeam(), getRole(), getJoinedAt(), getSecessionAt());
    }

    public MemberList(Team team, Member member) {
        this.team = team;
        this.member = member;
    }
}
