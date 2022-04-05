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
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "member_introduce")
    private String introduce;


    public Member(String name, String email, String introduce) {
        this.name = name;
        this.email = email;
        this.introduce = introduce;
    }
}
