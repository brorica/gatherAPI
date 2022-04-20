package brorica.gather.dto;

import brorica.gather.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberRequest {

    private String name;
    private String email;
    private String password;

    public Member toMember() {
        return new Member(name, email, password, "");
    }
}
