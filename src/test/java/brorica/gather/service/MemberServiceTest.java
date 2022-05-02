package brorica.gather.service;

import brorica.gather.domain.Member;
import brorica.gather.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void deleteAll() {
        memberRepository.deleteAll();
    }

    @Test
    public void 회원가입() {
        // given
        Member member = createMember("member1", "email1");
        // when
        memberService.save(member);
        // then
        Member findMember = memberService.findMember(member.getId());
        Assertions.assertEquals(member.getId(), findMember.getId());
    }

    @Test
    public void 회원탈퇴() {
        // given
        Member member = createMember("member1", "email1");

        // when
        memberService.save(member);
        memberService.remove(member);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.findMember(member.getId());
        });
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

    @Test
    public void 패스워드일치() {
        // given
        Member member = createMember("member1", "email1");

        // when
        memberService.save(member);

        // then
        Member findMember = memberService.findMember(member.getName());
        Assertions.assertEquals(member.getPassword(), findMember.getPassword());
    }

    @Test
    public void 패스워드불일치() {
        // given
        Member member = createMember("member1", "email1");

        // when
        memberService.save(member);

        // then
        Member findMember = memberService.findMember(member.getId());
        Assertions.assertNotEquals("different password", findMember.getPassword());
    }

    @Test
    public void 자기소개변경() {
        // given
        Member member = createMember("member1", "email1");
        String changeIntroduce = "change Introduce";

        // when
        memberService.save(member);
        memberService.changeIntroduce(member, changeIntroduce);

        // then
        Member findMember = memberService.findMember(member.getId());
        Assertions.assertEquals(changeIntroduce, findMember.getIntroduce());
    }

    public Member createMember(String name, String email) {
        return new Member(name, email, "password", "introduce");
    }
}