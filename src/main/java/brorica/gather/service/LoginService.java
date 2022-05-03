package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.repository.MemberRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String email, String password) {
        Optional<Member> findMember = memberRepository.findByEmail(email)
            .filter(member -> member.getPassword().equals(password));
        if (findMember.isEmpty()) {
            throw new NoSuchElementException("가입되지 않은 회원입니다.");
        }
        return findMember.get();
    }
}
