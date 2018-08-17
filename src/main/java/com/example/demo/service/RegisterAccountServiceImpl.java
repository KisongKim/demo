package com.example.demo.service;

import com.example.demo.ServiceError;
import com.example.demo.ServiceException;
import com.example.demo.contract.RegisterAccountRequest;
import com.example.demo.contract.RegisterAccountResponse;
import com.example.demo.external.AccountHolderNameVerificationComponent;
import com.example.demo.model.Account;
import com.example.demo.model.Member;
import com.example.demo.model.repository.AccountRepository;
import com.example.demo.model.repository.BankRepository;
import com.example.demo.model.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegisterAccountServiceImpl implements RegisterAccountService {

    private MemberRepository memberRepository;

    private AccountRepository accountRepository;

    private BankRepository bankRepository;

    private AccountHolderNameVerificationComponent accountHolderNameVerificationComponent;

    private static final Logger logger = LoggerFactory.getLogger(RegisterAccountServiceImpl.class);

    @Autowired
    public RegisterAccountServiceImpl(final MemberRepository memberRepository,
                                      final AccountRepository accountRepository,
                                      final BankRepository bankRepository,
                                      final AccountHolderNameVerificationComponent accountHolderNameVerificationComponent) {
        this.memberRepository = memberRepository;
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
        this.accountHolderNameVerificationComponent = accountHolderNameVerificationComponent;
    }

    @Override
    @Transactional
    public RegisterAccountResponse execute(final RegisterAccountRequest request) throws ServiceException {
        // register account not allowed for deleted member
        final Long memberId = request.getMemberId();
        Member member = memberRepository.findById(memberId).filter(m -> m.getDeleted() == false).orElse(null);
        if (member == null) {
            logger.error("[execute] Member not found for member id={}", memberId);
            throw new ServiceException(ServiceError.NO_SUCH_USER, new IllegalStateException());
        }

        // account number can not be duplicated.
        String number = request.getNumber();
        if (accountRepository.findByNumberAndDeletedFalse(number) != null) {
            logger.error("[execute] Account already exist.");
            throw new ServiceException(ServiceError.ACCOUNT_ALREADY_EXIST, new IllegalStateException());
        }

        // bank must be valid
        String bankCode = request.getBankCode();
        if (bankRepository.findByCodeAndDeletedFalse(bankCode) == null) {
            logger.error("[execute] Bank not found for code={}", bankCode);
            throw new ServiceException(ServiceError.NO_SUCH_BANK, new IllegalStateException());
        }

        // account holder name check- The external bank union function is not provided in the demo application
        String holderName = request.getHolderName();
        Optional<String> match = accountHolderNameVerificationComponent.execute(bankCode, number, holderName);
        if (!match.isPresent() || !holderName.equalsIgnoreCase(match.get())) {
            logger.error("[execute] Account holder name mismatch");
            throw new ServiceException(ServiceError.ACCOUNT_HOLDER_NAME_MISMATCH, new IllegalStateException());
        }

        LocalDateTime now = LocalDateTime.now();
        Account account = new Account(null,
                member.getId(),
                bankCode,
                holderName,
                number,
                false,
                null,
                now,
                now);
        account = accountRepository.save(account);
        return RegisterAccountResponse.from(account.getId());
    }

}
