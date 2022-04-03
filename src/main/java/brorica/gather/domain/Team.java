package brorica.gather.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    @Column(name = "TEAM_NAME")
    private String name;

    @Column(name = "TEAM_INTRODUCE")
    private String introduce;

    @OneToMany(mappedBy = "team")
    private Set<MemberList> members = new HashSet<>();

    @OneToMany(mappedBy = "team")
    private Set<Category> categories = new HashSet<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
