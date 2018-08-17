package com.example.demo.model.repository;

import com.example.demo.model.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void findAll() {
        LocalDateTime now = LocalDateTime.now();
        Account a1 = new Account(null, 1L, "001", "John Doe", "123456789", false, null, now, now);
        Account a2 = new Account(null, 1L, "002", "Martin Sheen", "987654321", false, null, now, now);
        accountRepository.saveAll(Arrays.asList(a1, a2));

        List<Account> founds = accountRepository.findAll();
        Assert.assertTrue(founds.size() == 2);
        for (Account a : founds) {
            System.out.println("[findAll] Account=" + a.toString());
        }
    }

    @Test
    public void findByNumberAndDeletedFalse() {
        LocalDateTime now = LocalDateTime.now();
        final String number = "123456789";
        Account a1 = new Account(null, 1L, "001", "John Doe", number, true, now, now, now);
        Account a2 = new Account(null, 1L, "001", "John Doe", number, false, null, now, now);
        accountRepository.saveAll(Arrays.asList(a1, a2));

        Account account = accountRepository.findByNumberAndDeletedFalse(number);
        Assert.assertNotNull(account);
        System.out.println("[findByNumberAndDeletedFalse] Account=" + account.toString());
    }

    @Test
    public void findAllByNumber() {
        LocalDateTime now = LocalDateTime.now();
        final String number = "123456789";
        Account a1 = new Account(null, 1L, "001", "John Doe", number, true, now, now, now);
        Account a2 = new Account(null, 1L, "001", "John Doe", number, false, null, now, now);
        accountRepository.saveAll(Arrays.asList(a1, a2));

        List<Account> accounts = accountRepository.findAllByNumber(number);
        Assert.assertTrue(accounts.size() == 2);
    }

    @Test
    public void findAllByMemberId() {
        LocalDateTime now = LocalDateTime.now();
        final String number1 = "123456789";
        final String number2 = "987654321";
        Account a1 = new Account(null, 1L, "001", "John Doe", number1, false, null, now, now);
        Account a2 = new Account(null, 1L, "002", "John Doe", number2, false, null, now, now);
        accountRepository.saveAll(Arrays.asList(a1, a2));

        List<Account> accounts = accountRepository.findAllByMemberId(1L);
        Assert.assertTrue(accounts.size() == 2);
    }

}
