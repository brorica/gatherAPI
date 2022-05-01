package brorica.gather.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends EntityDate {

    @OneToMany(
        mappedBy = "member",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private final List<TeamMember> belongs = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
    
    private String password;
    @Column(name = "member_introduce")
    private String introduce;

    public Member(String name, String email, String password, String introduce) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.introduce = introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
