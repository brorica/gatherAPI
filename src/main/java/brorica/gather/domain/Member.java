package brorica.gather.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends EntityDate {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "MEMBER_NAME", nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "MEMBER_INTRODUCE")
    private String introduce;


    public Member(String name, String email, String introduce) {
        this.name = name;
        this.email = email;
        this.introduce = introduce;
    }
}
