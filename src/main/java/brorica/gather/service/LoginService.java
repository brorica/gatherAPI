package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.dto.member.LoginRequest;
import brorica.gather.repository.MemberRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(LoginRequest loginRequest) {
        Optional<Member> findMember = memberRepository.findByEmail(loginRequest.getEmail())
            .filter(member -> member.getPassword().equals(loginRequest.getPassword()));
        if (findMember.isEmpty()) {
            throw new NoSuchElementException("가입되지 않은 회원입니다.");
        }
        return findMember.get();
    }
}
