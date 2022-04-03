package brorica.gather.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "MEMBER_NAME")
    private String name;
    private String email;

    @Column(name = "MEMBER_INTRODUCE")
    private String introduce;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
