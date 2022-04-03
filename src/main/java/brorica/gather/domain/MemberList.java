package brorica.gather.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberList {

    @Id @GeneratedValue
    @Column(name = "MEMBER_LIST_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime joinedAt;
    private LocalDateTime secessionAt;

}
