package com.example.demo.model.repository;

import com.example.demo.model.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void findByEmail() {
        memberRepository.save(new Member(null, "user@hanpass.com", "", false, null, LocalDateTime.now()));
        memberRepository.save(new Member(null, "admin@hanpass.com", "", false, null, LocalDateTime.now()));

        Member member = memberRepository.findByEmailAndDeletedFalse("user@hanpass.com");
        Assert.assertNotNull(member);
    }

    @Test
    public void findByEmailAndDeletedFalse() {
        Member user = new Member(null, "user@hanpass.com", "", false, null, LocalDateTime.now());
        user = memberRepository.save(user);

        user.setDeleted(true);
        System.out.println("[delete] User=" + user.toString());
        memberRepository.save(user);

        Member member = memberRepository.findByEmailAndDeletedFalse("user@hanpass.com");
        Assert.assertNull(member);
    }

    @Test
    public void findAllByEmailAndDeletedTrue() {
        Member member1 = new Member(null, "user@hanpass.com", "", true, null, LocalDateTime.now());
        member1 = memberRepository.save(member1);

        Member member2 = new Member(null, "user@hanpass.com", "", false, null, LocalDateTime.now());
        member2 = memberRepository.save(member2);

        member2.setDeleted(true);
        member2 = memberRepository.save(member2);

        List<Member> members = memberRepository.findAllByEmailAndDeletedTrue("user@hanpass.com");
        Assert.assertTrue(members.size() == 2);
    }

}
