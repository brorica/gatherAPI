package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        // given
        Member member = createMember("member1", "email1");
        // when
        Long saveId = memberService.save(member);
        // then
        assertEquals(member, memberRepository.findById(saveId));
    }

    @Test
    public void 중복된이름탐지() {
        // given
        Member member1 = createMember("member1", "email1");
        Member member2 = createMember("member1", "email2");

        // when
        memberService.save(member1);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.save(member2);
        });
    }

    @Test
    public void 중복된이메일탐지() {
        // given
        Member member1 = createMember("member1", "email1");
        Member member2 = createMember("member2", "email1");

        // when
        memberService.save(member1);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.save(member2);
        });
    }

    public Member createMember(String name, String email) {
        return new Member(name, email, "introduce");
    }
}