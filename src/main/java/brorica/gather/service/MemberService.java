package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public Long save(Member member) {
        validateDuplicateMemberName(member);
        validateDuplicateMemberEmail(member);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional(readOnly = false)
    public void remove(Member member) {
        memberRepository.remove(member);
    }

    public Member findMember(Long id) {
        return memberRepository.findById(id);
    }

    public void validateDuplicateMemberName(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("중복된 이름입니다.");
        }
    }

    public void validateDuplicateMemberEmail(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("중복된 이메일입니다.");
        }
    }
}
