package com.example.demo.service;

import com.example.demo.ServiceException;
import com.example.demo.contract.RegisterMemberRequest;
import com.example.demo.contract.RegisterMemberResponse;
import com.example.demo.ServiceError;
import com.example.demo.model.Member;
import com.example.demo.model.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterMemberServiceImpl implements RegisterMemberService {

    private MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(RegisterMemberServiceImpl.class);

    @Autowired
    public RegisterMemberServiceImpl(final MemberRepository memberRepository,
                                     final PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public RegisterMemberResponse execute(final RegisterMemberRequest request) throws ServiceException {
        String email = request.getEmail();
        if (memberRepository.findByEmailAndDeletedFalse(email) != null) {
            logger.error("[execute] Provided email address already exist.");
            throw new ServiceException(ServiceError.EMAIL_ALREADY_EXIST, new IllegalStateException());
        }

        String password = request.getPassword();
        Member member = Member.of(email, passwordEncoder.encode(password));
        member = memberRepository.save(member);
        logger.debug("[execute] Member={}", member.toString());

        return RegisterMemberResponse.from(member.getId());
    }

}
