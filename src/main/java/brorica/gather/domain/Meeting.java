package brorica.gather.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Meeting {

    @Id @GeneratedValue
    @Column(name = "MEETING_ID")
    private Long id;

    @Column(name = "MEETING_NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    private String spot;
    private String content;
    private Integer maximum;
    private LocalDateTime startAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
