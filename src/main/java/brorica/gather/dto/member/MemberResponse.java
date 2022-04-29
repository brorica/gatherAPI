package brorica.gather.dto.member;

import brorica.gather.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {

    private String name;
    private String email;
    private String introduce;

    public MemberResponse(Member member) {
        name = member.getName();
        email = member.getEmail();
        introduce = member.getIntroduce();
    }
}
