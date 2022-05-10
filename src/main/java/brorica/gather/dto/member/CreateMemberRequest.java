package brorica.gather.dto.member;

import brorica.gather.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequest {

    private String name;
    private String email;
    private String password;

    public Member toMember() {
        return new Member(name, email, password, "");
    }
}
