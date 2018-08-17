package com.example.demo.service;

import com.example.demo.ServiceException;
import com.example.demo.contract.RegisterMemberRequest;
import com.example.demo.contract.RegisterMemberResponse;
import com.example.demo.model.Member;
import com.example.demo.model.repository.MemberRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RegisterMemberServiceTests {

    private RegisterMemberService registerMemberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        registerMemberService = new RegisterMemberServiceImpl(memberRepository, passwordEncoder);
    }

    @Test
    public void execute() {
        final String email = "test@hanpass.com";
        final String password = DigestUtils.sha256Hex("p@assw0rd");

        Mockito.when(memberRepository.findByEmailAndDeletedFalse(Mockito.any())).thenReturn(null);

        Member member = Member.of(email, password);
        member.setId(1L);
        Mockito.when(memberRepository.save(Mockito.any())).thenReturn(member);

        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn(password);

        RegisterMemberRequest request = new RegisterMemberRequest();
        request.setEmail(email);
        request.setPassword(password);
        RegisterMemberResponse response = registerMemberService.execute(request);

        Assert.assertTrue(response.getMemberSeq() != null);
    }

    @Test(expected = ServiceException.class)
    public void execute_emailAlreadyExist() {
        final String email = "test@hanpass.com";
        final String password = DigestUtils.sha256Hex("p@assw0rd");

        Member member = Member.of(email, password);
        member.setId(1L);

        Mockito.when(memberRepository.findByEmailAndDeletedFalse(Mockito.any())).thenReturn(member);

        RegisterMemberRequest request = new RegisterMemberRequest();
        request.setEmail(email);
        request.setPassword(password);
        registerMemberService.execute(request);
    }

}
