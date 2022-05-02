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
        findMember(member.getId());
        memberRepository.delete(member);
    }

    @Transactional(readOnly = false)
    public void changeIntroduce(Member member, String newIntroduce) {
        Member findMember = findMember(member.getId());
        findMember.setIntroduce(newIntroduce);
    }

    public Member findMember(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 멤버입니다.");
        }
        return findMember.get();
    }

    public Member findMember(String name) {
        Optional<Member> findMember = memberRepository.findByName(name);
        if (findMember.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 멤버입니다.");
        }
        return findMember.get();
    }

    public Member findMemberByEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (findMember.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 멤버입니다.");
        }
        return findMember.get();
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
}
