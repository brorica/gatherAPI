package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.repository.MemberRepository;
import java.util.Optional;
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
        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = false)
    public void remove(Member member) {
        isMemberExist(member);
        memberRepository.delete(member);
    }

    @Transactional(readOnly = false)
    public void changeIntroduce(Member member) {
        isMemberExist(member);
        memberRepository.save(member);
    }

    public Optional<Member> findMember(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findMember(String name) {
        return memberRepository.findByName(name);
    }

    /**
     * 위의 name 겁색과 겹쳐서 만든 임시 메소드 로그인 controller에 대한 구현이 끝나면 삭제
     */
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    private void validateDuplicateMemberName(Member member) {
        if (memberRepository.findByName(member.getName()).isPresent()) {
            throw new IllegalStateException("중복된 이름입니다.");
        }
    }

    private void validateDuplicateMemberEmail(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalStateException("중복된 이메일입니다.");
        }
    }

    private void isMemberExist(Member member) {
        if (memberRepository.findById(member.getId()).isEmpty()) {
            throw new IllegalStateException("존재하지 않는 멤버입니다.");
        }
    }
}
